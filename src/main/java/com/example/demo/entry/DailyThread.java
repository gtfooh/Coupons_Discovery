package com.example.demo.entry;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.crud.CouponRepository;

public class DailyThread implements Runnable{

	
	@Autowired
	CouponRepository couponRepo;
	Date today;
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(86400000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		today = new Date();
		couponRepo.deleteAllExpiredCoupons(today);
		}
		
	}

}
