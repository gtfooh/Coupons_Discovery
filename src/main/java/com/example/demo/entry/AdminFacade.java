package com.example.demo.entry;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.CompanyDBDAO;
import com.example.demo.DAO.CouponDBDAO;
import com.example.demo.DAO.CustomerDBDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CompanyNameTakenException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CustomerNameTakenException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;


/**
 * class AdminFacade sends the basic administrators orders to the program
 * @author Dor
 *
 */
@Component
public class AdminFacade implements CouponClientFacade {

	@Autowired
	CompanyDBDAO companyDB;
	@Autowired
	CouponDBDAO couponDB;
	@Autowired
	CustomerDBDAO customerDB;
	
/**
 * checks the username and password of the Admin and returns this class if the credentials are true	
 */
	
	@Override
	public CouponClientFacade login(String name, String password)  {
		
		if (name.equals("Admin")&&password.equals("1234")) {
			return this;
		}
		else {
			System.out.println("Wrong Password or Username");
			return null;
		}
		
	}

/**
 * creates a given company in the DB
 * @param c
 * @throws CompanyNameTakenException
 * @throws InterruptedException
*/
	
	public void createCompany(Company c) throws InterruptedException {
		companyDB.createCompany(c);
	}
	
/**
 * removes a given company from the DB
 * @param c
 * @throws CompanyNotFoundException
 * @throws InterruptedException 
 */
	public void removeCompany(Company c) throws  InterruptedException {
		couponDB.deleteAllCouponsByCompany(c);
		companyDB.removeCompany(c);
	}
	
/**
 * updates a given company in the DB	
 * @param c
 * @throws CompanyNotFoundException
 * @throws InterruptedException 
 */
	public void updateCompany(Company c) throws CompanyNotFoundException, InterruptedException {
		companyDB.updateCompany(c);
	}
	
/**
 * get a company from the DB by id	
 * @param id
 * @return
 * @throws CompanyNotFoundException
 * @throws InterruptedException 
 */
	public Company getCompany(long id) throws  InterruptedException {
		return companyDB.getCompany(id);
	}
	
/**
 * get all the Companies from the DB	
 * @return
 * @throws CompanyNotFoundException
 * @throws InterruptedException 
 */
	public ArrayList<Company> getAllCompanies() throws InterruptedException{
		return companyDB.getAllCompanies();
	}
	
/**
 * creates a given customer in the DB	
 * @param c
 * @throws InterruptedException 
 * @throws CustomerNameTakenException
 */
	public void createCustomer(Customer c) throws InterruptedException {
		customerDB.createCustomer(c);
	}
	
	
/**
 * removes a given customer from the DB	
 * @param c
 * @throws InterruptedException 
 * @throws CustomerNotFoundException
 */
	
	public void removeCustomer(Customer c) throws InterruptedException {
		c.setCoupons(null);
		customerDB.removeCustoemr(c);
	}
	
/**
 * updates a given customer in the DB	
 * @param c
 * @throws CustomerNotFoundException
 * @throws InterruptedException 
 */
	
	public void updateCustomer(Customer c) throws CustomerNotFoundException, InterruptedException {
		customerDB.updateCustomer(c);
	}
	
/**
 * get a customer from the DB with the given id	
 * @param id
 * @return
 * @throws InterruptedException 
 */
	
	public Customer getCustomer(long id) throws InterruptedException {
		return customerDB.getCustomer(id);
	}
	
/**
 * 	get a list of all the customers from the DB
 * @return
 * @throws InterruptedException 
 */
	public ArrayList<Customer> getAllCustomers() throws InterruptedException{
		return customerDB.getAllCustomers();
	}

}
