package com.example.demo.DAO;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.crud.CouponRepository;
import com.example.demo.crud.CustomerRepository;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CustomerNameTakenException;
import com.example.demo.exceptions.CustomerNotFoundException;


/**
 * class CouponDBDAO that connects the system to the DB and give orders to the DB 
 * @author Dor
 *
 */

@Component
public class CustomerDBDAO implements CustomerDAO {

	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	CouponRepository couponRepo;
	@Autowired
	DBAccess dbaccess;
	
	private static final Logger logger = LogManager.getLogger();
	

/**
 * creates a given customer in the DB	
 * @throws InterruptedException 
 * @throws CustomerNameTakenException 
 */
	
	@Override
	public void createCustomer(Customer c) throws InterruptedException, CustomerNameTakenException {
		dbaccess.getConnection();
		if (customerRepo.findCustomerByCustomerName(c.getCustomerName())==null) {
			logger.info("Creating customer ID: "+ c.getId());
			customerRepo.save(c);
			logger.info("Customer create success ID: "+ c.getId());
			dbaccess.returnConnection();
		}
		else {
			dbaccess.returnConnection();
			throw new CustomerNameTakenException("Customer name already taken");
		}
		
	}

/**
 * removes a given customer from the DB	
 * @throws InterruptedException 
 */
	
	@Override
	public boolean removeCustoemr(Customer c) throws InterruptedException {
		dbaccess.getConnection();
		if (customerRepo.findOne(c.getId())!=null) {
			logger.info("Deleting customer ID: "+ c.getId());
			customerRepo.delete(c);
			logger.info("Customer delete success ID: "+ c.getId());
			dbaccess.returnConnection();
			return true;
		}
		else {
			System.out.println("There is no such customer");
			dbaccess.returnConnection();
			return false;
		}
	}

/**
 * updates a given customer in the DB	
 * @throws InterruptedException 
 */
	
	@Override
	public void updateCustomer(Customer c) throws CustomerNotFoundException, InterruptedException {
		dbaccess.getConnection();
		if (customerRepo.findCustomerByCustomerName(c.getCustomerName())!=null) {
			logger.info("Updating customer ID: "+ c.getId());
			customerRepo.save(c);
			logger.info("Customer update success ID: "+ c.getId());
			dbaccess.returnConnection();
		}
		else {
			dbaccess.returnConnection();
			throw new CustomerNotFoundException("There is no such customer");
		}
	}
	
/**
 * get a customer from theDB with the given id	
 * @throws InterruptedException 
 */

	@Override
	public Customer getCustomer(long id) throws InterruptedException {
		dbaccess.getConnection();
		Customer customer = customerRepo.findOne(id);
		dbaccess.returnConnection();
		return customer;
	}
	
/**
 * get a list of all the customers in the DB	
 * @throws InterruptedException 
 */

	@Override
	public ArrayList<Customer> getAllCustomers() throws InterruptedException {
		dbaccess.getConnection();
		ArrayList<Customer> customerList = (ArrayList<Customer>) customerRepo.findAll();
		dbaccess.returnConnection();
		return customerList;
	}
/**
 * @throws InterruptedException 
 * 
 */
	@Override
	public ArrayList<Coupon> getCoupons(Customer c) throws InterruptedException {
		dbaccess.getConnection();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponRepo.findCouponsByCustomerName(c.getCustomerName());
		dbaccess.returnConnection();
		return coupons;
	}

//	public Customer findCustomerByCustomerNameAndPassword(String name, String password) throws InterruptedException {
//		dbaccess.getConnection();
//		Customer cust = customerRepo.findCustomerByCustomerNameAndPassword(name, password);
//		dbaccess.returnConnection();
//		return cust;
//		
//	}

	public Customer findCustomerByCustomerName(String name) throws InterruptedException {
		dbaccess.getConnection();
		Customer cust = customerRepo.findCustomerByCustomerName(name);
		dbaccess.returnConnection();
		return cust;
	}
	
/**
 * returns true if there is a customer with the given name and password in the DB	
 * @throws InterruptedException 
 */
	@Override
	public boolean login(String custName, String password) throws InterruptedException {
		dbaccess.getConnection();
		logger.info("Checking credentials");
		boolean result = customerRepo.existsByCustomerNameAndPassword(custName, password);
		dbaccess.returnConnection();
		return result;
	}

	
}
