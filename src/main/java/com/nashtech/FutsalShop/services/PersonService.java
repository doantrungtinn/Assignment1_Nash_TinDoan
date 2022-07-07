package com.nashtech.FutsalShop.services;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.nashtech.FutsalShop.DTO.PersonDTO;
import com.nashtech.FutsalShop.model.Person;

public interface PersonService {
	public List<Person> retrievePersons();

	public List<Person> getPersonsPage(int num, int size, String role);
	
	public List<Person> searchPerson(String keyword, String role);
	
	public List<Person> searchPersonRoleNot(String keyword, String role);

	public Optional<Person> getPerson(int id);

	public Person getPerson(String email);

	public Person createPerson(PersonDTO person);

	public boolean deletePerson(Person person);

	public boolean updatePerson(PersonDTO person);

	public int getTotalByRole(String role);

	public boolean checkExistEmailUpdate(String email, int id);
	
	public Person changePassword(String email, String oldPassword, String newPassword);
	
	public Person forgotPassword(String email, String newPassword);
	
	public void sendOTPEmail(String email) throws MessagingException;
	
	public String createOTP();
	
	public boolean checkOTP(String email, String otp);
}
