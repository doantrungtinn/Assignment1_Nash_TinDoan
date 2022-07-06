package com.nashtech.FutsalShop.services;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.nashtech.FutsalShop.DTO.PersonDTO;
import com.nashtech.FutsalShop.model.person;

public interface PersonService {
	public List<person> retrievePersons();

	public List<person> getPersonsPage(int num, int size, String role);
	
	public List<person> searchPerson(String keyword, String role);
	
	public List<person> searchPersonRoleNot(String keyword, String role);

	public Optional<person> getPerson(int id);

	public person getPerson(String email);

	public person createPerson(PersonDTO person);

	public boolean deletePerson(person person);

	public boolean updatePerson(PersonDTO person);

	public int getTotalByRole(String role);

	public boolean checkExistEmailUpdate(String email, int id);
	
	public person changePassword(String email, String oldPassword, String newPassword);
	
	public person forgotPassword(String email, String newPassword);
	
	public void sendOTPEmail(String email) throws MessagingException;
	
	public String createOTP();
	
	public boolean checkOTP(String email, String otp);
}
