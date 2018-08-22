package com.example.demo.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.entry.CustomerFacade;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.other.CouponType;
import com.example.demo.other.IncomeType;


/**
 * Class CustomerWebService 
 * a collection of services to manage a customer
 * @author Dor
 *
 */ 
@CrossOrigin("*")
@RestController
@RequestMapping("/customer-api")
public class CustomerWebService {

	@Autowired
	CustomerFacade ctxFacade;
	
	public CustomerFacade fakeLogin() throws InterruptedException {
		ctxFacade.setFakeCustomer();
		return this.ctxFacade;
	}
	
	CustomerFacade facade;
	

	private void addIncome(Customer c, IncomeType description, double amount) throws JSONException, IOException, org.json.JSONException {
		JSONObject json = new JSONObject();
		json.put("payerName", c.getCustomerName());
		json.put("description", description.toString());
		json.put("amount", amount);		
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost request = new HttpPost("http://localhost:8080/income-service/add");
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			httpClient.execute(request);
		} catch (Exception ex) {
			System.out.println("Add income failed");
		} finally {
			httpClient.close();
		}
	}


	
/**
 * Service to purchase a coupon	
 * @param request
 * @param c
 * @throws CouponNotFoundException
 * @throws CustomerNotFoundException
 * @throws InterruptedException
 * @throws org.json.JSONException 
 * @throws IOException 
 * @throws JSONException 
 */

	@RequestMapping (value = "/coupon" , method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public void purchaseCoupon(HttpServletRequest request,@RequestBody Coupon c) throws CouponNotFoundException, CustomerNotFoundException, InterruptedException, JSONException, IOException, org.json.JSONException {
			
//		this.facade = (CustomerFacade) request.getSession().getAttribute("customerfacade");
		this.facade = fakeLogin();
		this.facade.purchaseCoupon(c);
		this.addIncome(facade.currentCustomer, IncomeType.CUSTOMER_PURCHASE,c.getPrice());
	}
	
/**
 * service to get a specific coupon by id
 * @param request
 * @param id
 * @return
 */

	@RequestMapping (value = "/coupon/{id}" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public Coupon getCoupon(HttpServletRequest request,@PathVariable("id") long id) {
		Coupon result = null;
		try {
//			this.facade = (CustomerFacade) request.getSession().getAttribute("customerfacade");
			this.facade = fakeLogin();
			result = this.facade.getCoupon(id);
			System.out.println(result);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	
/**
 * service to get all customers purchased coupons	
 * @param request
 * @return
 * @throws InterruptedException 
 */
	@RequestMapping (value = "/coupon" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getAllPurchasedCoupons(HttpServletRequest request) throws InterruptedException {
//		this.facade = (CustomerFacade) request.getSession().getAttribute("customerfacade");
		this.facade = fakeLogin();
		List<Coupon> result = null;
		try {
			result = this.facade.getAllPurchasedCoupons();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
	
/**
 * service to get all customers purchased coupons by type
 * @param request
 * @param typetxt
 * @return
 * @throws InterruptedException 
 */
	
	@RequestMapping (value = "/coupon/by-type/{type}" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getAllPurchasedCouponsByType(HttpServletRequest request,@PathVariable("type") String typetxt) throws InterruptedException {
//		this.facade = (CustomerFacade) request.getSession().getAttribute("customerfacade");
		this.facade = fakeLogin();
		List<Coupon> result = null;
		CouponType type = CouponType.valueOf(typetxt);
		try {
			result = this.facade.getAllPurchasedCouponsByType(type);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
	
	
/**
 * service to get all customers purchased coupons by max price	
 * @param request
 * @param price
 * @return
 * @throws InterruptedException 
 */
	
	@RequestMapping (value = "/coupon/by-price/{price}" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getAllPurchasedCouponsByPrice(HttpServletRequest request,@PathVariable("price") double price) throws InterruptedException {
//		this.facade = (CustomerFacade) request.getSession().getAttribute("customerfacade");
		this.facade = fakeLogin();
		List<Coupon> result = null;
		try {
			result = this.facade.getAllPurchasedCouponsByMaxPrice(price);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;

	}
	
/**
 * service to get all coupons available to purchase	
 * @param request
 * @return
 */


	@RequestMapping (value = "/coupon/available" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getAvailableCoupons(HttpServletRequest request) {
		List<Coupon> result = null;
		try {
			this.facade = fakeLogin();
//			this.facade = (CustomerFacade) request.getSession().getAttribute("customerfacade");
			result = this.facade.getAvailableCoupons();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;

	}
	
/**
 * 	service to get all coupons available to purchase by max price
 * @param request
 * @param price
 * @return
 */
	
	
	@RequestMapping (value = "/coupon/available/by-price/{price}" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getAvailableCouponsByPrice(HttpServletRequest request,@PathVariable("price") double price) {
		List<Coupon> result = null;
		try {
//			this.facade = (CustomerFacade) request.getSession().getAttribute("customerfacade");
			this.facade = fakeLogin();
			result = this.facade.getAvailableCouponsByMaxPrice(price);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;

	}
	
/**
 * service to get all coupons available to purchase by a specific type	
 * @param request
 * @param typetxt
 * @return
 */
	
	@RequestMapping (value = "/coupon/available/by-type/{type}" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getAvailableCouponsByType(HttpServletRequest request,@PathVariable("type") String typetxt) {
		List<Coupon> result = null;
		CouponType type = CouponType.valueOf(typetxt);
		try {
//			this.facade = (CustomerFacade) request.getSession().getAttribute("customerfacade");
			this.facade = fakeLogin();
			result = this.facade.getAvailableCouponsByType(type);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;

	}
}
