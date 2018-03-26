package com.example.demo.crud;


import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.Customer;

public interface CustomerRepository extends CrudRepository <Customer, Long>{
	
/**
 * returns a customer object from the DB with the given customer name
 * @param name
 * @return
 */
	public Customer findCustomerByCustomerName(String name);
/**
 * returns a customer object from the DB with the given password
 * @param pwd
 * @return
 */
	public Customer findCustomerByPassword(String pwd);

/**
 * returns true if there is a customer with the given name and password in the DB
 * @param name
 * @param pwd
 * @return
 */
	public boolean existsByCustomerNameAndPassword(String name, String pwd);

	
}