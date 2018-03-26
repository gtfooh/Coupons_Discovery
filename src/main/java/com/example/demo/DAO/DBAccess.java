package com.example.demo.DAO;

import org.springframework.stereotype.Component;
@Component
public class DBAccess {
	
	private int connections = 5;
	
	
	public synchronized DBAccess getConnection() throws InterruptedException {
		while (connections <= 0) {
			System.out.println("There are no free connections, Please wait");
			wait();
		}
		connections = connections -1;
		return this;
	}
	public synchronized void  returnConnection() {
		connections = connections +1;
		notify();
	}
	

}
