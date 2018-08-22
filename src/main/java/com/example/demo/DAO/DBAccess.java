package com.example.demo.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DBAccess {
	
	private int connections = 5;
	private static final Logger logger = LogManager.getLogger();
	
	
	
	public synchronized DBAccess getConnection() throws InterruptedException {
		while (connections <= 0) {
			logger.warn("Waiting for db connection");
			System.out.println("There are no free connections, Please wait");
			wait();
		}
		
		connections = connections -1;
		logger.info("db connection recieved");
		return this;
	}
	public synchronized void  returnConnection() {
		connections = connections +1;
		logger.info("db connection returned");
		notify();
	}
	

}
