package com.example.demo.entry;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.example.demo.DAO.CompanyDBDAO;
import com.example.demo.DAO.CouponDBDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponAlreadyExistsException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.other.CouponType;

/**
 * class CompanyFacade sends the company orders to the program
 * scope("prototype") annotation creates a new copy of the class every time it is being called  
 * @author Dor
 *
 */

@Component
@Scope("prototype")
public class CompanyFacade implements CouponClientFacade {

@Autowired
CompanyDBDAO companyDB;

@Autowired
CouponDBDAO couponDB;


@Override
public String toString() {
	return "CompanyFacade [companyDB=" + companyDB + ", couponDB=" + couponDB + ", currentCompany=" + currentCompany
			+ "]";
}

/**
 * holds the current connected company to the specific Company facade copy
 */

public Company currentCompany;


public void setFakeCompany() throws InterruptedException {
	this.currentCompany = companyDB.getCompany(1);
}


/**
 * checks the user name and password that given for the company and returns this company facade copy if the credentials are true
 * @throws InterruptedException 
 */

	@Override
	public CouponClientFacade login(String name, String password) throws  InterruptedException {
		if (companyDB.login(name, password)) {
			currentCompany = companyDB.findCompanyByName(name);
			return this;
			
		}
		else {
			System.out.println("Wrong Password or Username");
			return null;
		}
	}

	
	/**
	 * creates a given coupon in the DB and connects it to the current logged in company
	 * @param c
	 * @throws CompanyNotFoundException
	 * @throws CouponAlreadyExistsException
	 * @throws InterruptedException 
	 */
	
	
	public boolean createCoupon(Coupon c) throws CompanyNotFoundException, InterruptedException, CouponAlreadyExistsException {
		if (couponDB.getCouponByTitle(c.getTitle()) ==null) {
			c.setCompany(currentCompany);
			couponDB.createCoupon(c);
			return true;
		
		}
		else {
			throw new CouponAlreadyExistsException("This coupon name is taken");
		}

/**
 * removes a given coupon from the DB and from the current logged in company coupons list
 */
	}
	public void removeCoupon(Coupon c) throws CouponNotFoundException, CompanyNotFoundException, InterruptedException {
		if (couponDB.getCouponByTitle(c.getTitle()) != null) {
			currentCompany.removeCoupon(c);
			c.setCompany(null);
			companyDB.updateCompany(currentCompany);
			couponDB.updateCoupon(c);
			couponDB.removeCoupon(c);
			couponDB.removeCoupon(c);
		}
		else throw new CouponNotFoundException("There is no such coupon");

/**
 * updates a given coupon in the DB		
 */
		
	}
	public void updateCoupon(Coupon c) throws CouponNotFoundException, InterruptedException {
		if (couponDB.getCouponByIdAndCompany(c.getId(), currentCompany) != null) {
			c.setCompany(currentCompany);
			couponDB.updateCoupon(c);
		}
		else throw new CouponNotFoundException("There is no such coupon");
	}

	
/**
 * get a coupon from the DB by a given id	
 * @param id
 * @return
 * @throws InterruptedException 
 * @throws CouponNotFoundException
 */
	
	public Coupon getCoupon(long id) throws InterruptedException {
		if (couponDB.getCouponByIdAndCompany(id, currentCompany)!=null) {
			return couponDB.getCoupon(id);
		}
		else {
			System.out.println("There is no such coupon");
			return null;
		}
		
	}

/**
 * get the list of all the coupons that the current company created
 * @return
 * @throws InterruptedException 
 */
	
	public List<Coupon> getAllCoupons() throws InterruptedException{
		return couponDB.getCouponsByCompany(currentCompany);
	}
/**
 * returns the list of all coupons that the current company created and are from the given type	
 * @param type
 * @return
 * @throws InterruptedException 
 */
	public List<Coupon> getCouponsByType(CouponType type) throws InterruptedException{
		return couponDB.getCouponsByTypeAndCompany(type, currentCompany);
	}
/**
 * returns the list of all coupons that the current company created and are not higher than the given price
 * @param price
 * @return
 * @throws InterruptedException 
 */
	public List<Coupon> getCouponsByMaxPrice(double price) throws InterruptedException{
		return couponDB.getCouponsByMaxPriceAndCompanyId(price, currentCompany.getId());
	}

/**
 * returns the list of all coupons that the current company created and their end date is lower than the given date	
 * @param date
 * @return
 * @throws InterruptedException 
 */
	
	public List<Coupon> getCouponsByMaxEndDate(Date date) throws InterruptedException{
		return couponDB.getCouponsByMaxDateAndCompanyId(date,currentCompany.getId());
		
	}

}
