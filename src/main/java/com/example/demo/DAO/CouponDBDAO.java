package com.example.demo.DAO;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.crud.CouponRepository;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.other.CouponType;

/**
 * class CouponDBDAO that connects the system to the DB and give orders to the DB 
 * @author Dor
 *
 */
@Component
public class CouponDBDAO implements CouponDAO {

	@Autowired
	CouponRepository couponRepo;
	@Autowired
	DBAccess dbaccess;

/** 
 * creates a given coupon in the DB	
 * @throws InterruptedException 
 */
	
	@Override
	public void createCoupon(Coupon c) throws InterruptedException {
	dbaccess.getConnection();
	couponRepo.save(c);
	dbaccess.returnConnection();
	}
	
/**
 * 	deletes a given coupon from the DB
 * @throws InterruptedException 
 */

	@Override
	public void removeCoupon(Coupon c) throws InterruptedException {
		dbaccess.getConnection();
		couponRepo.delete(c);
		dbaccess.returnConnection();
	}

/**
 * updates a given coupon in the DB	
 * @throws InterruptedException 
 */
	
	@Override
	public void updateCoupon(Coupon c) throws InterruptedException {
		dbaccess.getConnection();
		couponRepo.save(c);
		dbaccess.returnConnection();
	}

/**
 * gets a coupon from the DB with the given id	
 * @throws InterruptedException 
 */
	
	@Override
	public Coupon getCoupon(long id) throws InterruptedException {
		dbaccess.getConnection();
		Coupon coupon = couponRepo.findOne(id);	
		dbaccess.returnConnection();
		return coupon;
	}

/**
 * gets a list of all the coupons from the DB	
 * @throws InterruptedException 
 */
	
	@Override
	public ArrayList<Coupon> getAllCoupons() throws InterruptedException {
		dbaccess.getConnection();
		ArrayList<Coupon> couponList = (ArrayList<Coupon>) couponRepo.findAll();
		dbaccess.returnConnection();
		return  couponList;
	}
	
/**
 * 	get a list of all the coupons with the given type
 * @throws InterruptedException 
 */

	@Override
	public ArrayList<Coupon> getCouponsByType(CouponType type) throws InterruptedException {
		dbaccess.getConnection();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponRepo.findCouponByType(type);
		dbaccess.returnConnection();
		return coupons; 
	}
	
/**
 * get a list of all the coupons with the given type and company
 * @param type
 * @param c
 * @return
 * @throws InterruptedException 
 */
	
	public ArrayList<Coupon> getCouponsByTypeAndCompany(CouponType type, Company c) throws InterruptedException {
		dbaccess.getConnection();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponRepo.findCouponByTypeAndCompany(type, c);
		dbaccess.returnConnection();
		return coupons;
	}
	
/**
 * get a list of all the coupons with the given max price and Company id	
 * @param price
 * @param id
 * @return
 * @throws InterruptedException 
 */
	
	public ArrayList<Coupon> getCouponsByMaxPriceAndCompanyId(double price,long id) throws InterruptedException{
		dbaccess.getConnection();
		ArrayList<Coupon> coupons =	(ArrayList<Coupon>) couponRepo.findCouponsByMaxPriceAndCompanyId(price, id);
		dbaccess.returnConnection();
		return coupons;
	}
		
				
	
/**
 * 	get a coupon object from the DB with the given id and company
 * @param id
 * @param c
 * @return
 * @throws InterruptedException 
 */
	
	public Coupon getCouponByIdAndCompany(long id, Company c) throws InterruptedException {
		dbaccess.getConnection();
		Coupon coup = couponRepo.findCouponByIdAndCompany(id, c);
		dbaccess.returnConnection();
		return coup;
	}
	
/**
 * 	get a list of all the coupons with the  given company
 * @param c
 * @return
 * @throws InterruptedException 
 */
	public ArrayList<Coupon> getCouponsByCompany(Company c) throws InterruptedException{
		dbaccess.getConnection();
		ArrayList<Coupon> coupons =	(ArrayList<Coupon>) couponRepo.findCouponByCompany(c);	
		dbaccess.returnConnection();
		return coupons;
	}
	
/**
 *  get a list of coupons from the DB by given max date and id
 * @param date
 * @param id
 * @return
 * @throws InterruptedException 
 */
	
	public List<Coupon> getCouponsByMaxDateAndCompanyId (Date date, long id) throws InterruptedException{
		dbaccess.getConnection();
		List<Coupon> coupons = (List<Coupon>) couponRepo.findCouponsByMaxDateAndCompanyId(date, id);
		dbaccess.returnConnection();
		return coupons;
	}

	public List<Coupon> getCouponsByTypeAndCustomerName(CouponType type,String name) throws InterruptedException{
		dbaccess.getConnection();
		List<Coupon> coupons = couponRepo.findCouponsByCustomerNameAndCouponType(name, type);
		dbaccess.returnConnection();
		return coupons;
		}

	public List<Coupon> getCouponsByMaxPriceAndCustomerName(String name, double price) throws InterruptedException {
		dbaccess.getConnection();
		List<Coupon> coupons =couponRepo.findCouponsByCustomerNameAndMaxPrice(name, price);
		dbaccess.returnConnection();
		return coupons; 
	}
	
	public void deleteAllCouponsByCompany(Company c) throws InterruptedException {
		dbaccess.getConnection();
		couponRepo.deleteAllCompanyCoupons(c);
		dbaccess.returnConnection();
	}
	
	public Coupon getCouponByIdAndCustomerId(long coupId,long custId) throws InterruptedException {
		dbaccess.getConnection();
		Coupon coup =  couponRepo.getCouponByIdAndCustomerId(coupId, custId);
		dbaccess.returnConnection();
		return coup;
	}
}
