package com.example.demo.entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.CouponDBDAO;
import com.example.demo.DAO.CustomerDBDAO;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;
import com.example.demo.other.CouponType;

import ch.qos.logback.core.net.ssl.SSLConfigurableServerSocket;

/**
* class CourrentFacade sends the custoemr orders to the program
* scope("prototype") annotation creates a new copy of the class every time it is being called  
* @author Dor
*
*/

@Component
@Scope("prototype")
public class CustomerFacade implements CouponClientFacade {

@Autowired CustomerDBDAO customerDB;
@Autowired CouponDBDAO couponDB;

/**
 * holds the current logged in customer for this specific customerFacade copy
 */
private Customer currentCustomer;




/**
* checks the user name and password that given for the company and returns this customer facade copy if the credentials are true
 * @throws InterruptedException 
*/
	@Override
	public CouponClientFacade login(String name, String password) throws InterruptedException {
		if (customerDB.login(name, password)) {
			currentCustomer = customerDB.findCustomerByCustomerName(name);
			return this;
			
		}
		else {
			System.out.println("Wrong Password or username");
		return null;	
		}
	}
		
/**
 * purchases a given coupon by the logged in customer, also reduce amount in purchased coupon and check if coupon not expired 	
 * @param c
 * @return
 * @throws CouponNotFoundException
 * @throws CustomerNotFoundException
 * @throws InterruptedException 
 */
		
	
	public boolean purchaseCoupon(Coupon c) throws CouponNotFoundException, CustomerNotFoundException, InterruptedException {
		Date today = new Date();
		if (couponDB.getCoupon(c.getId()).getAmount()>0 && couponDB.getCoupon(c.getId()).getEndDate().after(today)) {
			if (couponDB.getCoupon(c.getId()) != null && couponDB.getCouponByIdAndCustomerId(c.getId(),currentCustomer.getId())== null) {
				List owned = new ArrayList<>();
				owned.addAll(currentCustomer.getCoupons());
				currentCustomer.getCoupons().removeAll(owned);
				customerDB.updateCustomer(currentCustomer);
				c.setAmount(c.getAmount()-1);
				owned.add(c);
				currentCustomer.setCoupons(owned);
				customerDB.updateCustomer(currentCustomer);
				return true;
			}
			else throw new CouponNotFoundException("There is no such coupon");
		}
		else {
			System.out.println("This cooupon is expired or out of stock");
			return false;
		}
	}
	

	public Coupon getCoupon(Coupon c) throws InterruptedException {
		Coupon coup = couponDB.getCouponByIdAndCustomerId(c.getId(),currentCustomer.getId());
		if (coup == null) {
			System.out.println("there is no such coupon");
		}	
			return coup;
	}
		
	
	
	
/**
 * gets the list of all the current logged in customer purchased coupons	
 * @return
 * @throws InterruptedException 
 */
	public List<Coupon> getAllPurchasedCoupons() throws InterruptedException{
		return customerDB.getCoupons(currentCustomer);
	}
	
/**
 * 	gets the list of all the current logged in customer purchased coupons from the given type
 * @param type
 * @return
 * @throws InterruptedException 
 */
	
	public List<Coupon> getAllPurchasedCouponsByType(CouponType type) throws InterruptedException{
		return couponDB.getCouponsByTypeAndCustomerName(type,currentCustomer.getCustomerName());
	}
	

/**
 * 	gets the list of all the current logged in customer purchased coupons with price lower than the given price
 * @param price
 * @return
 * @throws InterruptedException 
 */
	
	public List<Coupon> getAllPurchasedCouponsByMaxPrice(double price) throws InterruptedException{
		return couponDB.getCouponsByMaxPriceAndCustomerName(currentCustomer.getCustomerName(),price);
	}
		
}

