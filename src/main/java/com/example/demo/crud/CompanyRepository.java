package com.example.demo.crud;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Company;

public interface CompanyRepository extends CrudRepository <Company, Long>{
	
/**
 * returns a company object from the DB with the given name	
 * @param name
 * @return
 */
	public Company findCompanyByCompanyName(String name);
/**
 * returns a company object from the DB with the given password	
 * @param pwd
 * @return
 */
	public Company findCompanyByPassword(String pwd);
/**
 * 	returns a company object from the DB with the given email
 * @param email
 * @return
 */
	public Company findCompanyByEmail(String email);
/**
 * 	returns a company object from the DB with the given name and password
 * @param name
 * @param pwd
 * @return
 */
	public Company findCompanyByCompanyNameAndPassword(String name, String pwd);
	
	/**
	 * returns true if there is a company with the given name and password in the DB
	 * @param name
	 * @param pwd
	 * @return
	 */
	public boolean existsByCompanyNameAndPassword(String name, String pwd);

}
