package com.example.demo.DAO;

import java.util.ArrayList;
import java.util.List;

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
		
/**
 * creates a given company in the DB works only if there is no company with the same name	
 * @throws CompanyNameTakenException 
 * @throws InterruptedException 
 */
	
	public synchronized boolean createCompany(Company c) throws InterruptedException {
			dbaccess.getConnection();
			if (companyRepo.findCompanyByCompanyName(c.getCompanyName()) == null) {
			companyRepo.save(c);
			dbaccess.returnConnection();
			return true;
		}
		else
		{
			dbaccess.returnConnection();
			System.out.println("This company name is already taken");
			return false;
		}
	}
	
/**
 * removes a given company from the DB
 * @throws InterruptedException 
 */
	
	public synchronized boolean removeCompany(Company c) throws  InterruptedException {

		dbaccess.getConnection();
		if (companyRepo.findCompanyByCompanyName(c.getCompanyName()) != null) {
			companyRepo.delete(c);
		dbaccess.returnConnection();
		return true;
		}
		else
			{
			dbaccess.returnConnection();
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
			
			companyRepo.save(c);
			dbaccess.returnConnection();
		}
		else {
			dbaccess.returnConnection();
			throw new CompanyNotFoundException ("There is no such company");
		}
		
	}

/**
 * gets a company form the DB with the given id 	
 * @throws InterruptedException 
 */
	
	public synchronized Company getCompany(long id)throws InterruptedException {

		dbaccess.getConnection();
		Company company = companyRepo.findOne(id);
		dbaccess.returnConnection();
		if (company != null) {
			return company;
		}
		else {
			dbaccess.returnConnection();
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
		ArrayList<Company> companyList = (ArrayList<Company>) companyRepo.findAll();
		dbaccess.returnConnection();
		if (companyList.isEmpty()) {
			System.out.println("There are no companies");
		}
		return companyList;
		
		
	}
	
/**
 * 	get a list of coupons from the DB with the given company
 * @throws InterruptedException 
 */
	
	public List<Coupon> getCoupons(Company c) throws InterruptedException {
		dbaccess.getConnection();
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
		boolean result = companyRepo.existsByCompanyNameAndPassword(name,password);
		dbaccess.returnConnection();	
		return result;
		
	}
}
