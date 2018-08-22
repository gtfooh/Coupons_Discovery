package com.example.demo.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.demo.other.CouponType;

public class WsCoupon implements Serializable {

	public String title;
	
	public String startDate;
	
	public String endDate;
	
	public int amount;
	
	public CouponType type;
	
	public String message;
	
	public double price;
	
	public String image;
	
	public DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public Coupon convertToCoupon() throws ParseException {
		
		Coupon result = new Coupon();
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(this.df.parse(startDate));
		cal.add(Calendar.DATE,1);
		result.setStartDate(cal.getTime());
		cal.setTime(this.df.parse(endDate));
		cal.add(Calendar.DATE,1);
		result.setEndDate(cal.getTime());
		
		result.setTitle(this.title);	
		result.setAmount(this.amount);
		result.setType(this.type);
		result.setMessage(this.message);
		result.setPrice(this.price);
		result.setImage(this.image);
		return result;
		
	}



	public WsCoupon() {
		super();
	}



	public WsCoupon(String title, String startDate, String endDate, int amount, CouponType type, String message,
			double price, String image, DateFormat df) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
		this.df = df;
	}
	
}
