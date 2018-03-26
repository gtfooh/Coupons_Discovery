package com.example.demo.DAO;

import java.util.ArrayList;

import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CustomerNameTakenException;
import com.example.demo.exceptions.CustomerNotFoundException;

public interface CustomerDAO {
	public boolean createCustomer(Customer c) throws CustomerNameTakenException, InterruptedException;
	public boolean removeCustoemr(Customer c) throws CustomerNotFoundException, InterruptedException;
	public void updateCustomer(Customer c) throws CustomerNotFoundException, InterruptedException;
	public Customer getCustomer(long id) throws InterruptedException;
	public ArrayList<Customer> getAllCustomers() throws InterruptedException;
	public ArrayList<Coupon> getCoupons(Customer c) throws InterruptedException;
	public boolean login(String custName, String password) throws InterruptedException;
	
}