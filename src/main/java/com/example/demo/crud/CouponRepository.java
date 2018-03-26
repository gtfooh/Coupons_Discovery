package com.example.demo.crud;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.other.CouponType;

public interface CouponRepository extends CrudRepository <Coupon, Long>{
	
	
/**
 * returns a coupon object from the DB with the given title
 * @param title
 * @return
 */
	
	public Coupon findCouponByTitle(String title);
/**
 * 	returns a coupon object from the DB with the given start date
 * @param date
 * @return
 */
	public Coupon findCouponByStartDate(Date date);
/**
 *returns a coupon object from the DB with the given end date
 * @param date
 * @return
 */
	public Coupon findCouponByEndDate(Date date);
/**
 * returns a list of all the coupon objects from the DB with the given amount
 * @param amount
 * @return
 */
	@Query("SELECT c FROM COUPON c where c.amount > :amount")
	public List<Coupon> findWhereAmountLargerThan(@Param("amount")int amount);
/**
 * 	returns a coupon object from the DB with the given amount
 * @param amount
 * @return
 */
	public Coupon findCouponByAmount(int amount);
/**
 * returns a list of all the coupon objects from the DB with the given Type	
 * @param type
 * @return
 */
	public List<Coupon> findCouponByType(CouponType type);
/**
 * 	returns a list of all the coupon objects from the DB with the given type and company
 * @param type
 * @param c
 * @return
 */
	public List<Coupon> findCouponByTypeAndCompany(CouponType type, Company c);
/**
 * 	returns a coupon object from the DB with the given message
 * @param message
 * @return
 */
	public Coupon findCouponByMessage(String message);
/**
 * 	returns a coupon object from the DB with the given price
 * @param price
 * @return
 */
	public Coupon findCouponByPrice(double price);
/**
 * 	returns a coupon object from the DB with the given image	
 * @param image
 * @return
 */
	public Coupon findCouponByImage(String image);

/**
 * returns a list of all the coupon objects from the DB with the given max price and company id
 * @param price
 * @param id
 * @return
 */

	@Query("SELECT c FROM COUPON c where c.price < :maxprice AND company_id = :companyid")
	public List<Coupon> findCouponsByMaxPriceAndCompanyId(@Param("maxprice")double price, @Param("companyid") long id );
/**
 * 	returns a coupon object from the DB with the given id and company
 * @param id
 * @param c
 * @return
 */
	public Coupon findCouponByIdAndCompany(long id, Company c);
/**
 * 	returns a coupon object from the DB with the given company
 * @param c
 * @return
 */
	public List<Coupon> findCouponByCompany(Company c);
/**
 * returns a list of all the coupon objects from the DB with the given end date and company id
 * @param date
 * @param id
 * @return
 */
	@Query("SELECT c FROM COUPON c where c.endDate < :enddate AND company_id = :companyid)")
	public List<Coupon> findCouponsByMaxDateAndCompanyId(@Param("enddate")Date date, @Param("companyid") long id );
/**
 * 	returns a list of all the coupon objects from the DB with the given type and customer name
 * @param name
 * @param type
 * @return
 */
	@Query("SELECT c FROM COUPON c JOIN c.customers cust WHERE cust.customerName = :name and c.type = :type" )
	public List<Coupon> findCouponsByCustomerNameAndCouponType(@Param("name")String name, @Param("type")CouponType type);
	
/**
 * 	returns a list of all the coupon objects from the DB with the given type and customer name
 * @param name
 * @param type
 * @return
 */
	@Query("SELECT c FROM COUPON c JOIN c.customers cust WHERE cust.customerName = :name and c.price <= :price" )
	public List<Coupon> findCouponsByCustomerNameAndMaxPrice(@Param("name")String name, @Param("price")double price);
			
	
	/**
	 * 	returns a list of all the coupon objects from the DB with the given customer name
	 * @param name
	 * @return
	 */
	
	@Query("SELECT c FROM COUPON c JOIN c.customers cust WHERE cust.customerName = :name" )
	public List<Coupon> findCouponsByCustomerName(@Param("name")String name);
	
	
	/**
	 * deletes all the expired coupons from the DB
	 * @param endDate
	 */
	
	@Transactional
	@Modifying
	@Query("DELETE COUPON c WHERE c.endDate < :date")
	public void deleteAllExpiredCoupons(@Param("date") Date endDate);
	
	
	
	/**
	 * deletes all the company coupons from the DB
	 * @param c
	 */
	
	@Transactional
	@Modifying
	@Query("DELETE COUPON c WHERE c.company = :company")
	public void deleteAllCompanyCoupons(@Param("company") Company c);
	
	@Query("SELECT c FROM COUPON c JOIN c.customers cu WHERE c.id = :coupId AND cu.id = :custId")
	public Coupon getCouponByIdAndCustomerId(@Param("coupId")long coupId,@Param("custId")long custId);
}
																																																																															