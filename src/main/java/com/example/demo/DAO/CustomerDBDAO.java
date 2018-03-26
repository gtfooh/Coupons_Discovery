package com.example.demo.DAO;

import java.util.ArrayList;
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

/**
 * creates a given customer in the DB	
 * @throws InterruptedException 
 */
	
	@Override
	public boolean createCustomer(Customer c) throws InterruptedException {
		dbaccess.getConnection();
		if (customerRepo.findCustomerByCustomerName(c.getPassword())==null) {
			customerRepo.save(c);
			dbaccess.returnConnection();
			return true;
		}
		else {
			System.out.println("Customer name already taken");
			dbaccess.returnConnection();
			return false;
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
			customerRepo.delete(c);
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
		if (customerRepo.findOne(c.getId())!=null) {
			customerRepo.save(c);
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
		boolean result = customerRepo.existsByCustomerNameAndPassword(custName, password);
		dbaccess.returnConnection();
		return result;
	}

	
}
