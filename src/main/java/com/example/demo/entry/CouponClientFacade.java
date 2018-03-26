package com.example.demo.entry;

import com.example.demo.exceptions.WrongPasswordException;


public interface CouponClientFacade {
	public CouponClientFacade login(String name,String password) throws WrongPasswordException, InterruptedException;

}
