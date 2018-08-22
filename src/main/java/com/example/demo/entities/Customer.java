package com.example.demo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Class customer that defines the Entity CUSTOMER
 * @author Dor
 *
 */
@XmlRootElement
@Entity (name = "CUSTOMER")
public class Customer implements Serializable , Comparable<Customer> {
	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column (name = "CUST_NAME")
	private String customerName;
	
	
	@Column (name = "PASSWORD")
	private String password;
	
	
/**
 * constructor for class customer
 * @param customerName
 * @param password
 */
	
	public Customer(String customerName, String password) {
		super();
		this.customerName = customerName;
		this.password = password;
	}

/**
 * empty constructor for customer class	
 * @return
 */
	
	public Customer() {
		super();
	}
	
/**
 * gets the id of this customer
 * @return
 */
	
	public long getId() {
		return id;
	}

/**
 * 	sets the id  of this customer
 * @param id
 */
	
	public void setId(long id) {
		this.id = id;
	}
	
/**
 * gets the customer name
 * @return
 */
	
	public String getCustomerName() {
		return customerName;
	}

/**
 * sets the customer name
 * @param customerName
 */
		
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

/**
 * gets the customers password	
 * @return
 */
	
	public String getPassword() {
		return password;
	}

/**
 * sets the customers password	
 * @param password
 */
	
	public void setPassword(String password) {
		this.password = password;
	}
	
/**
 * list of coupons that this customer purchased
 * many to many annotation connects the customer and coupon DB relations
 */
	 @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinTable(
	        name = "CUSTOMER_COUPON", 
	        joinColumns =  @JoinColumn(name = "CUSTOMER_ID") , 
	        inverseJoinColumns =  @JoinColumn(name = "COUPON_ID")
	    )
	    List<Coupon> coupons = new ArrayList <>();
	
///**
// * gets the  list of coupons that this customer purchased
// * @return
// */
//	 
//	 
//	public List<Coupon> getCoupons() {
//		return coupons;
//	}

/**
 * sets the list of coupons	that this customer purchased
 * @param coupons
 */
	
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

/**
 * converts the customer object to string	
 */
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerName=" + customerName + ", password=" + password + ", coupons="
				+ coupons + "]";
	}

public List<Coupon> fetchCoupons(List<Coupon> owned) {
	owned.addAll(this.coupons);
	return owned;
	
	// TODO Auto-generated method stub
	
}

public void deleteCoupons(List<Coupon> owned) {
	this.coupons.removeAll(owned);
	// TODO Auto-generated method stub
	
}

/**
 * Sorting strategy (Sort by id), implemented for Comparable interface.
 */

@Override
public int compareTo(Customer arg0) {
	// TODO Auto-generated method stub
	return (int)( this.id - arg0.id);
}

	
	

	
}
