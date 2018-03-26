package com.example.demo;

import java.util.List;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;


@Component
public class TestHelper {
	
	public void compareCompanies(Company c1,Company c2) {
		Assert.assertEquals(c1.getCompanyName(),c2.getCompanyName());
		Assert.assertEquals(c1.getEmail(),c2.getEmail());
		Assert.assertEquals(c1.getId(),c2.getId());
		Assert.assertEquals(c1.getPassword(),c2.getPassword());	
	}
	
	public void compareCustomers(Customer c1,Customer c2) {
		Assert.assertEquals(c1.getCustomerName(),c2.getCustomerName());
		Assert.assertEquals(c1.getId(),c2.getId());
		Assert.assertEquals(c1.getPassword(),c2.getPassword());	
	}
	
	public void compareCoupons(Coupon c1,Coupon c2) {
		Assert.assertEquals(c1.getId(),c2.getId());
		Assert.assertEquals(c1.getImage(),c2.getImage());
		Assert.assertEquals(c1.getMessage(),c2.getMessage());
		Assert.assertEquals((int)c1.getPrice(),(int)c2.getPrice());
		Assert.assertEquals(c1.getTitle(),c2.getTitle());
		Assert.assertEquals(c1.getAmount(),c2.getAmount());
//		Assert.assertEquals(c1.getEndDate(),c2.getEndDate());
//		Assert.assertEquals(c1.getStartDate(),c2.getStartDate());
		Assert.assertEquals(c1.getType(),c2.getType());
	}
	
	public void nullTester(Object o) {
		Assert.assertNull(o);
	}
	
	public void companyListTester(List<Company> l1,List<Company> l2) {
		for (int i = 0; i < l1.size(); i++) {
		compareCompanies(l1.get(i),l2.get(i));
		}
	}
		public void customerListTester(List<Customer> l1,List<Customer> l2) {
			for (int i = 0; i < l1.size(); i++) {
			compareCustomers(l1.get(i),l2.get(i));	
			}	
		}
		public void couponListTester(List<Coupon> l1,List<Coupon> l2) {
			for (int i = 0; i < l1.size(); i++) {
			compareCoupons(l1.get(i),l2.get(i));	
			}	
		}
}
