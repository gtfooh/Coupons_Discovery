package com.example.demo.DAO;

import java.util.ArrayList;
import java.util.List;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.crud.CompanyRepository;
import com.example.demo.crud.CouponRepository;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompanyNameTakenException;
import com.example.demo.exceptions.CompanyNotFoundException;


/**
 * class CompanyDBDAO that connects the system to the DB and give orders to the DB 
 * @author Dor
 *
 */

@Scope("prototype")
@Component
public class CompanyDBDAO implements CompanyDAO {
	

	@Autowired
	CompanyRepository companyRepo;
	@Autowired
	CouponRepository couponRepo;
	@Autowired
	DBAccess dbaccess;
	
	private static final Logger logger = LogManager.getLogger();
		
/**
 * creates a given company in the DB works only if there is no company with the same name	
 * @return 
 * @throws CompanyNameTakenException 
 * @throws InterruptedException 
 */
	
	public synchronized void createCompany(Company c) throws InterruptedException, CompanyNameTakenException {
			dbaccess.getConnection();
			if (companyRepo.findCompanyByCompanyName(c.getCompanyName()) == null && c.getCompanyName()!= null) {
			logger.info("Creating company, ID:" + c.getId());
			companyRepo.save(c);
			logger.info("Company create success");
			dbaccess.returnConnection();
	
		}
		else
		{
			dbaccess.returnConnection();
			logger.error("Company creation failed");
			throw new CompanyNameTakenException("This company name is already taken");
		}
	}
	
/**
 * removes a given company from the DB
 * @throws InterruptedException 
 */

	public synchronized boolean removeCompany(Company c) throws  InterruptedException {

		dbaccess.getConnection();
		if (companyRepo.findCompanyByCompanyName(c.getCompanyName()) != null) {
			logger.info("Deleting company, ID:" + c.getId());
			companyRepo.delete(c);
			logger.info("Company Delete success");
		dbaccess.returnConnection();
		return true;
		}
		else
			{
			dbaccess.returnConnection();
			logger.error("Company delete failed");
			System.out.println("There is no such company");
			return false;
			}
	}
	
/**
 * updates the given company in the DB only works if there is a company with the same id in the DB	
 * @throws InterruptedException 
 */
	
	public synchronized void updateCompany(Company c) throws CompanyNotFoundException, InterruptedException {
			dbaccess.getConnection();
		if (companyRepo.findCompanyByCompanyName(c.getCompanyName()) != null) {	
			logger.info("Updating company, ID:" + c.getId());
			companyRepo.save(c);
			logger.info("Company update sucess");
			dbaccess.returnConnection();
		}
		else {
			dbaccess.returnConnection();
			logger.error("Company update failed");
			throw new CompanyNotFoundException ("There is no such company");
		}
		
	}

/**
 * gets a company form the DB with the given id 	
 * @throws InterruptedException 
 */

	public synchronized Company getCompany(long id)throws InterruptedException {
		dbaccess.getConnection();
		logger.info("Getting company, ID:" + id);
		Company company = companyRepo.findOne(id);
		logger.info("Company get success");
		dbaccess.returnConnection();
		if (company != null) {
			return company;
		}
		else {
			dbaccess.returnConnection();
			logger.error("Company get failed");
			System.out.println("There is no such company");
			return null;
		}
	}
	
/**
 * get a list of all the companies from the DB	
 * @throws InterruptedException 
 */
	

	public ArrayList<Company> getAllCompanies() throws  InterruptedException {

		dbaccess.getConnection();
		logger.info("Getting all companies");
		ArrayList<Company> companyList = (ArrayList<Company>) companyRepo.findAll();
		dbaccess.returnConnection();
		if (companyList.isEmpty()) {
			logger.error("Getting comapnies failed");
			System.out.println("There are no companies");
		}
		else logger.info("Getting companies success");
		return companyList;
		
		
	}
	
/**
 * 	get a list of coupons from thge DB with the given company
 * @throws InterruptedException 
 */
	
	public List<Coupon> getCoupons(Company c) throws InterruptedException {
		dbaccess.getConnection();
		logger.info("Getting company (ID: " + c.getId() +") coupons");
		List<Coupon> cList = couponRepo.findCouponByCompany(c);
		dbaccess.returnConnection();
		return 	cList;
	}
	
/**
 * 	gets a company form the DB with the given name
 * @param name
 * @return
 * @throws InterruptedException 
 */
	
	public Company findCompanyByName(String name) throws InterruptedException {
		dbaccess.getConnection();
		Company c = companyRepo.findCompanyByCompanyName(name);
		dbaccess.returnConnection();
		return c;
	}
	
/**
 * gets a company form the DB with the given name and password	
 * @param name
 * @param password
 * @return
 * @throws InterruptedException 
 */
	
	public Company findCompanyByCompanyNameAndPassword(String name,String password) throws InterruptedException {
		dbaccess.getConnection();
		Company c = companyRepo.findCompanyByCompanyNameAndPassword(name, password);
		dbaccess.returnConnection();	
		return c;
	}

	/**
	 * returns true if there is a company with the given name and password in the DB
	 * @param name
	 * @param password
	 * @return
	 * @throws InterruptedException
	 */
	public boolean login(String name,String password) throws InterruptedException {
		dbaccess.getConnection();
		logger.info("Checking credentials");
		boolean result = companyRepo.existsByCompanyNameAndPassword(name,password);
		dbaccess.returnConnection();	
		return result;
		
	}
}
