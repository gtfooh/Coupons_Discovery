package com.example.demo.DAO;

import java.util.ArrayList;

import com.example.demo.entities.Coupon;
import com.example.demo.other.CouponType;


public interface CouponDAO {
	public void createCoupon(Coupon c) throws InterruptedException;
	public void removeCoupon(Coupon c) throws InterruptedException;
	public void updateCoupon(Coupon c) throws InterruptedException;
	public Coupon getCoupon(long id) throws InterruptedException;
	public ArrayList<Coupon> getAllCoupons() throws InterruptedException;
	public ArrayList<Coupon> getCouponsByType(CouponType type) throws InterruptedException;
	

	
}