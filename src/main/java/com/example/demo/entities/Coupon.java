package com.example.demo.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.example.demo.other.CouponType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Class coupons that defines the Entity COUPONS
 * @author Dor
 *
 */
@XmlRootElement
@Entity (name = "COUPON")
public class Coupon implements Serializable, Comparable<Coupon> {
	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY) 
	private long id;
	
	@Column (name = "TITLE")
	private String title;
	
	@Column (name = "START_DATE")
	private Date startDate;
	
	@Column (name = "END_DATE")
	private Date endDate;
	
	@Column (name = "AMOUNT")
	private int amount;
	
	@Column (name = "TYPE")
	private CouponType type;
	
	@Column (name = "MESSAGE")
	private String message;
	
	@Column (name = "PRICE")
	private double price;
	
	@Column (name = "IMAGE")
	private String image;
	
	
	
	/**
	 * empty constructor for coupon class
	 */
		
	public Coupon() {
			super();
	}

	
/**
	 * Constructor for coupon class
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param amount
	 * @param type
	 * @param message
	 * @param price
	 * @param image
	 */
			
			
		public Coupon( String title, String startDate, String endDate, int amount, CouponType type, String message,
				double price, String image) {
			super();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			this.title = title;
			
			try {
				this.startDate = df.parse(startDate);
				this.endDate = df.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			this.amount = amount;
			this.type = type;
			this.message = message;
			this.price = price;
			this.image = image;
			}
	
	
/**
 * list of customers that holds the customers that purchased this coupon
 * many to many annotation connects coupon and customer DB relations
 * 
 */
	
	@ManyToMany(mappedBy = "coupons") //, fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();

/**
 * a company object that holds the company that created this coupon
 * many to one annotation connects coupon and company DB relations
 * 
 */
	
	
	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name="COMPANY_ID")
    private Company company;	

/**
 * sets the company that created this coupon	
 * @param company
 */
	public void setCompany(Company company) {
		this.company = company;
	}
	
/**
 * adds a customer to the customers list that purchased this coupon.
 * @param c
 */
	
	public void addCustomer(Customer c) {
		this.customers.add(c);
	}	


/**
 * gets the coupon id	
 * @return
 */
	
	public long getId() {
		return id;
	}

/**
 * sets the coupon id	
 * @param id
 */
	public void setId(long id) {
		this.id = id;
	}

	
/**
 * gets the coupon title	
 * @return
 */
	
	public String getTitle() {
		return title;
	}

/**
 * set title	
 * @param title
 */
	
	public void setTitle(String title) {
		this.title = title;
	}

/**
 * gets the start date of the coupon	
 * @return
 */
	@JsonFormat(pattern="dd-MM-yyyy")
	public Date getStartDate() {
		return startDate;
	}

/**
 * sets the start date of the coupon	
 * @param startDate
 */
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

/**
 * gets the end date of the coupon	
 * @return
 */
	
	@JsonFormat(pattern="dd-MM-yyyy")
	public Date getEndDate() {
		return endDate;
	}

/**
 * sets the end date of the coupon	
 * @param endDate
 */
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

/**
 * gets the amount of this coupon	
 * @return
 */
	
	public int getAmount() {
		return amount;
	}

	
/**
 * 	sets the amount of this coupon
 * @param amount
 */
	
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

/**
 * gets the type of this coupon	
 * @return
 */
	
	public CouponType getType() {
		return type;
	}

/**
 * sets the type of this coupon	
 * @param type
 */
	
	public void setType(CouponType type) {
		this.type = type;
	}

/**
 * gets the message of this coupon	
 * @return
 */
	
	public String getMessage() {
		return message;
	}

/**
 * sets the message of this coupon	
 * @param message
 */
	
	public void setMessage(String message) {
		this.message = message;
	}

/**
 * gets the price of this coupon	
 * @return
 */
	
	public double getPrice() {
		return price;
	}

/**
 * sets the price of this coupon	
 * @param price
 */
	
	public void setPrice(double price) {
		this.price = price;
	}

/**
 * gets the image if this coupon	
 * @return
 */
	
	public String getImage() {
		return image;
	}

/**
 * sets the image of this coupon	
 * @param image
 */
	
	public void setImage(String image) {
		this.image = image;
	}


/**
 * converts the coupon object to String
 */

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image;
	}
//
///**
// * get the customers list that bought this coupon
// * @return
// */
//	public List<Customer> getCustomers() {
//		return customers;
//	}

/**
 * sets the customers list that purchased this coupon	
 * @param customers
 */
	
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public void setTypePic() {
		switch (this.type.toString()) {
		case "CLOTHING":
			this.image = "/company_page/assets/clothing.jpg";
			break;
		case "ELECTRONICS":
			this.image = "/company_page/assets/electronics.jpg";
			break;
		case "FOOD":
			this.image = "/company_page/assets/food.jpg";
			break;
		case "FLIGHTS":
			this.image = "/company_page/assets/flights.jpg";
			break;
		case "VACATIONS":
			this.image = "/company_page/assets/vacations.jpg";
			break;
		default:
			break;
		}
	}


	@Override
	public int compareTo(Coupon o) {

	return (int) (this.id - o.id);
}
	
	

}
