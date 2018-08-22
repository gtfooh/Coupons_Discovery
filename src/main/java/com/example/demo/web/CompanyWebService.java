package com.example.demo.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.WsCoupon;
import com.example.demo.entry.CompanyFacade;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponAlreadyExistsException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.other.CouponType;
import com.example.demo.other.IncomeType;


/**
 * Class CompanyWebservice
 * a collection of services to manage a company
 * @author Dor
 *
 */

@CrossOrigin("*")
@RestController
@RequestMapping("/company-api")
public class CompanyWebService {
	
	@Autowired
	CompanyFacade ctxFacade;
	
	CompanyFacade facade;

	
	private CompanyFacade fakeLogin() throws InterruptedException {
		this.ctxFacade.setFakeCompany();
		return ctxFacade;
	}
	
	

	private void addIncome(Company c, IncomeType description, double amount) throws JSONException, IOException, org.json.JSONException {
		JSONObject json = new JSONObject();
		json.put("payerName", c.getCompanyName());
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
 * Service to createa a coupon	
 * @param request
 * @param coup
 * @throws CompanyNotFoundException
 * @throws InterruptedException
 * @throws CouponAlreadyExistsException
 * @throws ParseException
 * @throws IOException 
 * @throws JSONException 
 * @throws org.json.JSONException 
 */
	
	@RequestMapping (value = "/coupon" , method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createCoupon(@RequestBody WsCoupon coup) throws CompanyNotFoundException, InterruptedException, CouponAlreadyExistsException, ParseException, JSONException, IOException, org.json.JSONException {
		//			this.facade = (CompanyFacade) request.getSession().getAttribute("companyfacade");
			this.facade = this.fakeLogin();
			Coupon c = coup.convertToCoupon();
			c.setTypePic();
			facade.createCoupon(c);
			this.addIncome(facade.currentCompany, IncomeType.COMPANY_NEW_COUPON , 10);
		
	}
	
/**
 * Service to remove a coupon	
 * @param request
 * @param c
 */
	@RequestMapping (value = "/coupon" , method = RequestMethod.PATCH , consumes = MediaType.APPLICATION_JSON_VALUE)	
	public void removeCoupon(HttpServletRequest request,@RequestBody Coupon c) {
		try {
//			this.facade = (CompanyFacade) request.getSession().getAttribute("companyfacade");
			this.facade = this.fakeLogin();
			this.facade.removeCoupon(c);
		} catch ( InterruptedException | CouponNotFoundException | CompanyNotFoundException e) {
			e.printStackTrace();
		}
	}
	
/**
 * Service to update a coupon	
 * @param request
 * @param c
 * @throws InterruptedException 
 */
	
	@RequestMapping (value = "/coupon" , method = RequestMethod.PUT , consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateCoupon(HttpServletRequest request,@RequestBody Coupon c) throws InterruptedException {
//		this.facade = (CompanyFacade) request.getSession().getAttribute("companyfacade");
		this.facade = this.fakeLogin();
		try {
			c.setTypePic();
			this.facade.updateCoupon(c);
			this.addIncome(facade.currentCompany, IncomeType.COMPANY_UPDATE_COUPON,10);
		} catch ( InterruptedException | CouponNotFoundException | JSONException | IOException | org.json.JSONException e) {
			e.printStackTrace();
		}
	}
	
/**
 * service to get a specific coupon by id	
 * @param request
 * @param id
 * @return
 * @throws InterruptedException 
 */
	
	
	@RequestMapping (value = "/coupon/{id}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Coupon getCoupon(HttpServletRequest request,@PathVariable("id") long id) throws InterruptedException{
//		this.facade = (CompanyFacade) request.getSession().getAttribute("companyfacade");
		this.facade = this.fakeLogin();
		Coupon result = null;
		try {
			result = this.facade.getCoupon(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	
/**
 * service to get all company coupons	
 * @param request
 * @return
 * @throws InterruptedException 
 */
	
	@RequestMapping (value = "/coupon" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getAllCoupons(HttpServletRequest request) throws InterruptedException{

//		this.facade = (CompanyFacade) request.getSession().getAttribute("companyfacade");
		this.facade = this.fakeLogin();
		List<Coupon> list = null;
		try {
			list = this.facade.getAllCoupons();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Collections.sort(list);
		return list;	
	}
	
	
/**
 * service to get all company coupons by specific type	
 * @param request
 * @param typetext
 * @return
 * @throws InterruptedException 
 */
	
	@RequestMapping (value = "/coupon/by-type/{type}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getCouponByType(HttpServletRequest request,@PathVariable("type") String typetext) throws InterruptedException
	{
//		this.facade = (CompanyFacade) request.getSession().getAttribute("companyfacade");
		this.facade = this.fakeLogin();
		CouponType type = CouponType.valueOf(typetext);
		List<Coupon> result = null;
		try {
			result = this.facade.getCouponsByType(type);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}	
	
/**
 * service to get all company coupons by max price	
 * @param request
 * @param price
 * @return
 * @throws InterruptedException 
 */
	
	@RequestMapping (value = "/coupon/by-price/{maxprice}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getCouponByMaxPrice(HttpServletRequest request,@PathVariable("maxprice") double price) throws InterruptedException
	{
//		this.facade = (CompanyFacade) request.getSession().getAttribute("companyfacade");
		this.facade = this.fakeLogin();
		List<Coupon> result = null;
		try {
			result = this.facade.getCouponsByMaxPrice(price);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}	
	
/**
 * service to get all company coupons by max end date	
 * @param request
 * @param datetext
 * @return
 * @throws InterruptedException 
 */
	
	@RequestMapping (value = "/coupon/by-maxdate/{enddate}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Coupon> getCouponByMaxEndDate(HttpServletRequest request,@PathVariable("enddate") String datetext) throws InterruptedException
	{
//		this.facade = (CompanyFacade) request.getSession().getAttribute("companyfacade");
		this.facade = this.fakeLogin();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(datetext);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		List<Coupon> result = null;
		try {
			this.facade = (CompanyFacade) request.getSession().getAttribute("companyfacade");
			result = this.facade.getCouponsByMaxEndDate(date);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}	
}
