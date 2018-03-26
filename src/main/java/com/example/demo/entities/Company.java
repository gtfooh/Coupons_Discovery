package com.example.demo.entities;

 
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * 
 * @author Dor
 *Class Company that defines the entity COMPANY
 */

@Entity (name = "COMPANY")
public class Company {
	

	@Id @GeneratedValue (strategy=GenerationType.IDENTITY) 
	private long id;
	
	@Column (name = "COMP_NAME")
	private String companyName;
	
	@Column (name = "PASSWORD")
	private String password;
	
	@Column (name = "EMAIL")
	private String email;
	
/**
 * Empty constructor for Company	
 */
	public Company() {
		super();
	}
	
	
/**
 * Constructor for Company	
 * @param companyName
 * @param password
 * @param email
 */
	public Company(String companyName, String password, String email) {
		super();
		this.companyName = companyName;
		this.password = password;
		this.email = email;
	}

/**
 * list of coupons that holds the company coupons
 * one to many annotation connects company and coupons DB relations 	
 */
		
	@OneToMany (fetch = FetchType.EAGER, mappedBy = "company", cascade = CascadeType.ALL)
	private List<Coupon> coupons;	
		

/**
 * Get the company ID
 * @return 
 */
	
	public long getId() {
		return id;
	}

/**
 * Set the company ID 
 * @param id
 */
	
	public void setId(long id) {
		this.id = id;
	}

/**
 * Get the company name
 * @return 
 */
	
	public String getCompanyName() {
		return companyName;
	}

	
/**
 * Set the company name	
 * @param companyName
 */
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

/**
 * get the company password	
 * @return
 */
	
	public String getPassword() {
		return password;
	}

/**
 * set the company password	
 * @param password
 */
	
	public void setPassword(String password) {
		this.password = password;
	}

/**
 * get the company email		
 * @return
 */
	
	public String getEmail() {
		return email;
	}

/**
 * set the company email	
 * @param email
 */
	
	public void setEmail(String email) {
		this.email = email;
	}

	
/**
 * converts the company object to string
 */
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons + "]";
	}

/** 
 * returns list of company coupons
 * @return
 */
	public List<Coupon> getCoupons() {
		return coupons;
	}

/**
 * sets the company coupons list	
 * @param coupon
 */
	public void setCoupons(List<Coupon> coupon) {
		this.coupons = coupon;
	}
	
/**
 * adds a coupon to company coupons list 	
 * @param c
 */
	
	public void addCoupon (Coupon c) {
		this.coupons.add(c);
	}

/**
 * removes a coupon from company coupons list	
 * @param c
 */
	public void removeCoupon (Coupon c) {
		this.coupons.remove(c);
	}

	
}
