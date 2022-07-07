package com.nashtech.FutsalShop.services.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nashtech.FutsalShop.DTO.OrderImportDTO;
import com.nashtech.FutsalShop.DTO.OrderImportDetailDTO;
import com.nashtech.FutsalShop.model.Orderimportdetail;
import com.nashtech.FutsalShop.model.Orderimportdetail.OrderImportDetailsKey;
import com.nashtech.FutsalShop.model.Orderimport;
import com.nashtech.FutsalShop.model.Person;
import com.nashtech.FutsalShop.model.Product;
import com.nashtech.FutsalShop.exception.ObjectNotFoundException;
import com.nashtech.FutsalShop.exception.ObjectPropertiesIllegalException;
import com.nashtech.FutsalShop.repository.OrderImportRepository;
import com.nashtech.FutsalShop.services.OrderImportDetailService;
import com.nashtech.FutsalShop.services.OrderImportService;
import com.nashtech.FutsalShop.services.PersonService;
import com.nashtech.FutsalShop.services.ProductService;

@Service
public class OrderImportServiceImpl implements OrderImportService {
	@Autowired
	OrderImportRepository orderImportRepo;

	@Autowired
	ModelMapper mapper;

	@Autowired
	ProductService productService;

	@Autowired
	PersonService personService;

	@Autowired
	OrderImportDetailService orderImportDetailService;

	public OrderImportServiceImpl() {
		super();
	}

	public long countTotal() {
		return orderImportRepo.countByStatusNot(false);
	}

	public int getLatestId() {
		return orderImportRepo.findFirstByIdOrderByIdDesc();
	}
	
	public int generateNewId() {
		int id = orderImportRepo.findFirstByIdOrderByIdDesc()+1;
		while (orderImportRepo.existsById(id)) id++;
		return id;
	}
	
	@Override
	@Transactional
	public Orderimport createOrderImport(Orderimport orderImport, int userId) {
		if (orderImport.isStatus()) {
			changeProductQuantityByDetailList(orderImport.getOrderImportDetails(), true, userId);
		}
		orderImport.setId(generateNewId());
		orderImport.setTimeimport(LocalDateTime.now());
		return orderImportRepo.save(orderImport);
	}
	
	@Override
	@Transactional
	public Orderimport createOrderFromXLSS(MultipartFile reapExcelDataFile, String email) {
		Set<Orderimportdetail> detailList = new HashSet<Orderimportdetail>();
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		} catch (IOException e) {
			throw new ObjectNotFoundException("File not found");

		}
		XSSFSheet worksheet = workbook.getSheetAt(0);
		Person personImport = personService.getPerson(email);
		Orderimport orderImport = new Orderimport();
		orderImport.setTimeimport(LocalDateTime.now());
		orderImport.setEmployee(personImport);
		orderImport.setStatus(true);
		Orderimport orderImport_saved = orderImportRepo.save(orderImport);
		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			Orderimportdetail tempDetail = new Orderimportdetail();
			XSSFRow row = worksheet.getRow(i);
			OrderImportDetailsKey keyId = new OrderImportDetailsKey(orderImport_saved.getId(),
					row.getCell(0).getStringCellValue());
			Optional<Orderimportdetail> detailCheck = detailList.stream()
					.filter(detail -> detail.getId().equals(keyId)).findAny();
			if (detailCheck.isPresent())
				throw new ObjectPropertiesIllegalException("Error: File wrong format, duplicate product");
			tempDetail.setId(keyId);
			tempDetail.setAmmount((int) row.getCell(1).getNumericCellValue());
			tempDetail.setPrice((float) row.getCell(2).getNumericCellValue());
			Product product = productService.getProduct(keyId.getProductId()).orElse(null);
			if (product == null) {
				throw new ObjectNotFoundException("Product ID " + keyId.getProductId() + " not found!");
			}
			tempDetail.setProduct(product);
			tempDetail.setOrder(orderImport_saved);
			detailList.add(tempDetail);
		}
		if (detailList.isEmpty()) {
			deleteOrderImport(orderImport_saved.getId());
			throw new ObjectNotFoundException("Error: File empty");
		}
		orderImport_saved.setOrderImportDetails(detailList);
		System.out.println(orderImport_saved.toString());
		return orderImport_saved; // orderImportRepo.save(orderImport_saved);
	}

	@Override
	public Orderimport convertToEntity(OrderImportDTO orderImportDto) {
		Orderimport orderImport = mapper.map(orderImportDto, Orderimport.class);
		Person employee = personService.getPerson(orderImportDto.getEmployeeEmail());
		orderImport.setEmployee(employee);
		return orderImport;
	}

	@Override
	public OrderImportDTO convertToDto(Orderimport orderImport) {
		OrderImportDTO importDto = mapper.map(orderImport, OrderImportDTO.class);
		importDto.setEmployeeEmail(orderImport.getEmployee().getEmail());
		importDto.setEmployeeFullName(orderImport.getEmployee().getFullname());
		Double totalCost = 0.0;
		for (Orderimportdetail detail : orderImport.getOrderImportDetails()) {
			totalCost += detail.getAmmount() * detail.getPrice();
		}
		importDto.setTotalCost(totalCost);
		Set<Orderimportdetail> orderImportDetails = orderImport.getOrderImportDetails();
		Set<OrderImportDetailDTO> orderImportDetailsDto = orderImportDetails.stream()
				.map(orderImportDetailService::convertToDto).collect(Collectors.toSet());
		importDto.setOrderImportDetails(orderImportDetailsDto);
		return importDto;
	}

	@Override
	public List<Orderimport> getOrderImportPage(int num, int size) {
		Sort sortable = Sort.by("timeimport").descending();
		Pageable pageable = PageRequest.of(num, size, sortable);
		return orderImportRepo.findByStatusNot(pageable, false).stream().collect(Collectors.toList());
	}

	@Override
	public Orderimport findOrderImportById(int importId) {
		return orderImportRepo.findById(importId).orElse(null);
	}

	public List<Orderimport> searchOrderImportByEmployee(String keyword) {
		return orderImportRepo.searchImportByEmployee(keyword.toUpperCase());
	}

	private void changeProductQuantityByDetailList(Set<Orderimportdetail> importDetailList, boolean isAdd, int userId) {
		for (Orderimportdetail importDetail : importDetailList) {
			Product product = productService.getProduct(importDetail.getId().getProductId()).orElse(null);
			int productNewQuantity = product.getQuantity();
			if (isAdd) {
				productNewQuantity += importDetail.getAmmount();
			} else {
				productNewQuantity -= importDetail.getAmmount();
			}
			Person employee = personService.getPerson(userId).get();
			product.setEmployeeUpdate(employee);
			product.setQuantity(productNewQuantity);
			product.setUpdateDate(LocalDateTime.now());
			productService.updateProductWithoutCheckAnything(product);
		}
	}

	@Override
	@Transactional
	public Orderimport updateOrderImport(OrderImportDTO orderImportDto, int orderImportId, int userId) {
		Orderimport orderImport = findOrderImportById(orderImportId);

		if (!orderImport.isStatus() && orderImportDto.isStatus()) {
			changeProductQuantityByDetailList(orderImport.getOrderImportDetails(), true, userId);
		}

		orderImport.setStatus(true);

		return orderImportRepo.save(orderImport);
	}

	@Override
	public boolean deleteOrderImport(int orderImportId) {

		return orderImportRepo.findById(orderImportId).map(order -> {
			for (Orderimportdetail detail : order.getOrderImportDetails()) {
				productService.updateProductQuantityToCancel(detail.getId().getProductId(), detail.getAmmount() * (-1));
			}
//			PersonEntity person = personService.getPerson(order.getEmployee().getId()).get();
//			person.getOrdersImport().remove(order);
//			orderImportRepo.delete(order);
			order.setStatus(false);
			orderImportRepo.save(order);
			return true;
		}).orElse(false);
	}

	@Override
	public float purchaseCostByMonth(int month, int year) {
		Float result = orderImportRepo.purchaseCostByMonth(month, year);
		if (result == null)
			result = (float) 0;
		return result;
	}
	
	public List<Orderimport> getImportByProductId(String prodId){
		Sort sortable = Sort.by("timeimport").ascending();
		return orderImportRepo.findByOrderImportDetailsIdProductIdAndStatusNot(sortable, prodId, false);
	}
}
