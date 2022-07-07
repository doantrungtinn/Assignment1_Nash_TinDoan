package com.nashtech.FutsalShop.Service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nashtech.FutsalShop.DTO.PersonDTO;
import com.nashtech.FutsalShop.model.Person;
import com.nashtech.FutsalShop.exception.ObjectAlreadyExistException;
import com.nashtech.FutsalShop.exception.ObjectNotFoundException;
import com.nashtech.FutsalShop.exception.ObjectPropertiesIllegalException;
import com.nashtech.FutsalShop.repository.PersonRepository;
import com.nashtech.FutsalShop.services.PersonService;
import com.nashtech.FutsalShop.services.impl.PersonServiceImpl;

@SpringBootTest
public class PersonServiceImplTest {

	@Mock
	PasswordEncoder encoder;

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	PersonService personService = new PersonServiceImpl(personRepository, encoder);

	private Person person1;
	private Person person2;
	private Person person3;
	private PersonDTO person1DTO;
	private PersonDTO person2DTO;
	List<Person> listPerson;
	private final int list_size = 3;

	@BeforeEach
	public void setup() {
		person1 = new Person(1, "test1@gmail.com", "123456", "Test 1", "ADMIN");
		person2 = new Person(2, "test2@gmail.com", "123456", "Test 2", "STAFF");
		person3 = new Person(3, "test3@gmail.com", "123456", "Test 3", "USER");
		person1DTO = new PersonDTO(1, "test1@gmail.com", "123456", "Test 1", "ADMIN");
		person2DTO = new PersonDTO(2, "test2@gmail.com", "123456", "Test 2", "STAFF");
		listPerson = new ArrayList<Person>(List.of(person1, person2, person3));
	}

	@Test
	public void retrievePersonsSuccess_Test() {
		when(personRepository.findAll()).thenReturn(listPerson);
		List<Person> listPerson_test = personService.retrievePersons();
		assertEquals(list_size, listPerson_test.size());
	}

	@Test
	public void createPersonSuccess_Test() {
		when(personRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
		when(personRepository.save(Mockito.any(Person.class))).thenReturn(person1);
		Person person_test = personService.createPerson(person1DTO);
		assertEquals(person1, person_test);
	}

	@Test
	public void updatePersonSuccess_Test() {
		when(personRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(person1));
		when(personRepository.save(Mockito.any(Person.class))).thenReturn(person1);
		assertTrue(personService.updatePerson(person1DTO));
	}

	@Test
	public void updatePersonSuccessWithEmailChange_Test() {
		List<Person> emptyList = new ArrayList<>();
		when(personRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(person1));
		when(personRepository.save(Mockito.any(Person.class))).thenReturn(person1);
		when(personRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(emptyList);
		assertTrue(personService.updatePerson(person2DTO));
	}

	@Test
	public void updatePersonFailedEmailIsUsed_Test() {
		when(personRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(person1));
		when(personRepository.save(Mockito.any(Person.class))).thenReturn(person1);
		when(personRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(listPerson);
		assertThrows(ObjectAlreadyExistException.class, () -> personService.updatePerson(person2DTO));
	}

	@Test
	public void checkExistEmailUpdateSuccess_Test() {
		List<Person> emptyList = new ArrayList<>();
		when(personRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(emptyList);
		assertTrue(personService.checkExistEmailUpdate(person1.getEmail(), person1.getId()));
	}

	@Test
	public void checkExistEmailUpdateFailed_Test() {
		when(personRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(listPerson);
		assertFalse(personService.checkExistEmailUpdate(person1.getEmail(), person1.getId()));
	}

	@Test
	public void checkExistEmailUpdateSuccessWithSameEmail_Test() {
		List<Person> personList_Test = new ArrayList<>();
		personList_Test.add(person1);
		when(personRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(personList_Test);
		assertTrue(personService.checkExistEmailUpdate(person1.getEmail(), person1.getId()));
	}

//	@Test
//	public void changePasswordSuccess_Test() {
//		person1.setStatus(true);
//		when(personRepository.findByEmail(person1.getEmail())).thenReturn(person1);
//		when(encoder.matches(person1.getPassword(), "123456")).thenReturn(true);
//		when(personRepository.save(person1)).thenReturn(person1);
//		assertEquals(person1, personService.changePassword(person1.getEmail(), person1.getPassword(), "newPass"));
//	}

	// OldPass not correct
	@Test
	public void changePasswordFailedIncorrectPass_Test() {
		person1.setStatus(true);
		when(personRepository.findByEmail(person1.getEmail())).thenReturn(person1);
		when(personRepository.save(person1)).thenReturn(person1);
		assertThrows(ObjectPropertiesIllegalException.class,
				() -> personService.changePassword(person1.getEmail(), person1.getPassword(), "newPass"));
	}

	// Account is disable
	@Test
	public void changePasswordFailedDisableAccount_Test() {
		when(personRepository.findByEmail(person1.getEmail())).thenReturn(person1);
		when(personRepository.save(person1)).thenReturn(person1);
		assertThrows(ObjectNotFoundException.class,
				() -> personService.changePassword(person1.getEmail(), person1.getPassword(), "newPass"));
	}
}
