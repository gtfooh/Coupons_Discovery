package com.example.demo.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.exceptions.WrongClientTypeException;
import com.example.demo.exceptions.WrongPasswordException;
import com.example.demo.other.ClientType;

@Scope("singleton")
@Component
public class CouponSystem {
	
	@Autowired
	ApplicationContext ctx;
	@Autowired
	AdminFacade adminF;
	@Autowired
	CustomerFacade customerF;
	@Autowired
	CompanyFacade companyF;
	
	
	
	DailyThread dailyThread = new DailyThread();
	Thread t = new Thread(dailyThread);
	private boolean dailyOff = true;
	
	/**
	 * start the daily clean up thread if it is not running already;
	 */
	private void startDailyThread() {
		if (dailyOff) {
			t.start();
			dailyOff = false;
		}
		
	}
	
/**
 * login to the system returns a copy of a facade object according to given client type.	
 * @param name
 * @param password
 * @param type
 * @return
 * @throws WrongClientTypeException
 * @throws InterruptedException
 */
	public CouponClientFacade login(String name, String password,ClientType type) throws WrongClientTypeException, InterruptedException {
		switch (type) {
		case ADMIN:
			startDailyThread();
			return adminF.login(name, password);
		case COMPANY:
			startDailyThread();
			companyF = (CompanyFacade) ctx.getBean("companyFacade");
			return companyF.login(name, password);
		case CUSTOMER:
			startDailyThread();
			CustomerFacade customerF = (CustomerFacade) ctx.getBean("customerFacade");
			return customerF.login(name, password);
		default:
			throw new WrongClientTypeException("The client type " + type + " is not allowed");
		
		}

	}
	
	public void shutDownSystem() {
		
	}
}