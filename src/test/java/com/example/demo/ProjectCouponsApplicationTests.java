package com.example.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.demo.crud.CustomerRepository;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.entry.AdminFacade;
import com.example.demo.entry.CompanyFacade;
import com.example.demo.entry.CouponSystem;
import com.example.demo.entry.CustomerFacade;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.WrongClientTypeException;
import com.example.demo.other.ClientType;
import com.example.demo.other.CouponType;



@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class ProjectCouponsApplicationTests {


@Autowired
CouponSystem couponSystem; 
@Autowired
TestHelper testHelper;
@Autowired
CustomerRepository customerRepo;

/**
 * 2 company objects, 2 customer objects and 6 coupon objects created for tests
 */
	Company comp1 = new Company("Kishurit","1818","admin@kishurit.co.il");
	Company comp2 = new Company("JohnBryce","1234","office@jbh.co.il");
	Customer cust1 = new Customer("dor","123343");
	Customer cust2 = new Customer("user","1111");
	Date today = new Date();
	@SuppressWarnings("deprecation")
	Date date1 = new Date(2018,12,31);
	@SuppressWarnings("deprecation")
	Date date2 = new Date(2019,12,31);
	@SuppressWarnings("deprecation")
	Date date3 = new Date(2019,06,30);
	
	Coupon coup1 = new Coupon("Coupon1",today,date1,5,CouponType.CLOTHING,"cloting coupon", 100, null);
	Coupon coup2 = new Coupon("Coupon2",today,date2,7,CouponType.ELECTRONICS,"electronics coupon", 200, null);
	Coupon coup3 = new Coupon("Coupon3",today,date1,5,CouponType.FLIGHTS,"flights coupon", 400, null);		
	Coupon coup4 = new Coupon("Coupon4",today,date2,9,CouponType.CLOTHING,"cloting coupon", 150, null);
	Coupon coup5 = new Coupon("Coupon5",today,date1,10,CouponType.ELECTRONICS,"electronics coupon", 250, null);
	Coupon coup6 = new Coupon("Coupon6",today,date2,11,CouponType.FLIGHTS,"flights coupon", 450, null);

	@Test
	public void contextLoads() {
	}
/**
 * tests login, createCompay and getCompany methods from AdminFacade
 * @throws WrongClientTypeException
 * @throws InterruptedException
 */
	@Test
	public void test01() throws InterruptedException, WrongClientTypeException {	
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);	
		testHelper.compareCompanies(comp1,adminF.getCompany(comp1.getId()));
		
	}
	
/**
 * 	test removeCompany from AdminFacade
 * @throws WrongClientTypeException
 * @throws InterruptedException
 */
	@Test
	public void test02() throws WrongClientTypeException, InterruptedException {	
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.removeCompany(comp1);
		testHelper.nullTester(adminF.getCompany(comp1.getId()));
	}
/**
 * tests updateCompany from AdminFacade
 * @throws WrongClientTypeException
 * @throws InterruptedException
 * @throws CompanyNotFoundException
 */
	
	@Test
	public void test03() throws WrongClientTypeException, InterruptedException, CompanyNotFoundException  {	
			AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
			adminF.createCompany(comp1);
			comp1.setPassword("asd");
			adminF.updateCompany(comp1);
			testHelper.compareCompanies(comp1,adminF.getCompany(comp1.getId()));
	}	
	
	/**
	 * tests getAllCompanies from AdminFacade
	 * @throws InterruptedException
	 * @throws WrongClientTypeException
	 */
	
	@Test
	public void test04() throws WrongClientTypeException, InterruptedException {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		List<Company> l2 = new ArrayList<>();
		l2.add(comp1);
		l2.add(comp2);
		testHelper.companyListTester(adminF.getAllCompanies(), l2);
	}	
	
	/**
	 * tests createCustomer and getCustomer methods from AdminFacade
	 * @throws WrongClientTypeException
	 * @throws InterruptedException
	 */	
	
	
	@Test
	public void test05() throws InterruptedException, WrongClientTypeException{	
			AdminFacade adminF =(AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
			adminF.createCustomer(cust1);		
			testHelper.compareCustomers(cust1,adminF.getCustomer(cust1.getId()));
	}
	
/**
 * 	test removeCustomer from AdminFacade
 * @throws WrongClientTypeException
 * @throws InterruptedException
 * @throws CustomerNotFoundException 
 */
	
	@Test
	public void test06() throws WrongClientTypeException, InterruptedException {	
			AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
			adminF.createCustomer(cust1);
			adminF.removeCustomer(cust1);
			testHelper.nullTester(adminF.getCustomer(cust1.getId()));
		}
	
/**
 * tests updateCustomer from AdminFacade
 * @throws WrongClientTypeException
 * @throws InterruptedException
 * @throws CustomerNotFoundException 
 * @throws CompanyNotFoundException
 */
	@Test
	public void test07() throws InterruptedException, WrongClientTypeException, CustomerNotFoundException {	
			AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
			adminF.createCustomer(cust1);
			cust1.setPassword("asd");
			adminF.updateCustomer(cust1);
			testHelper.compareCustomers(cust1, adminF.getCustomer(cust1.getId()));
	}	
	
/**
 * tests getAllCustomers from AdminFacade
 * @throws WrongClientTypeException
 * @throws InterruptedException
 */
	@Test
	public void test08() throws WrongClientTypeException, InterruptedException{
		
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCustomer(cust1);
		adminF.createCustomer(cust2);
		List<Customer> l2 = new ArrayList<>();
		l2.add(cust1);
		l2.add(cust2);
		testHelper.customerListTester(adminF.getAllCustomers(), l2);	
	}
	
	
	/**
	 * tests login, createCoupon and getCoupon from CompanyFacade 
	 * @throws WrongClientTypeException
	 * @throws InterruptedException
	 * @throws CompanyNotFoundException
	 */
	
	@Test
	public void test09() throws WrongClientTypeException, InterruptedException, CompanyNotFoundException {

		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		compF.createCoupon(coup1);
		testHelper.compareCoupons(coup1, compF.getCoupon(coup1.getId()));
		
		
	}
	
	/**
	 * test removeCoupon from CompanyFacade
	 * @throws WrongClientTypeException
	 * @throws InterruptedException
	 * @throws CompanyNotFoundException
	 * @throws CouponNotFoundException
	 */
	
	
	@Test
	public void test10() throws WrongClientTypeException, InterruptedException, CompanyNotFoundException, CouponNotFoundException {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		compF.createCoupon(coup1);
		compF.removeCoupon(coup1);
		testHelper.nullTester(compF.getCoupon(coup1.getId()));	
	}

/**
 * tests updateCoupon from CompanFacade	
 * @throws WrongClientTypeException
 * @throws InterruptedException
 * @throws CompanyNotFoundException
 * @throws CouponNotFoundException
 */
	
	
	@Test
	public void test11() throws WrongClientTypeException, InterruptedException, CompanyNotFoundException, CouponNotFoundException {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		compF.createCoupon(coup1);
		coup1.setAmount(99);
		compF.updateCoupon(coup1);
		testHelper.compareCoupons(coup1,compF.getCoupon(coup1.getId()));	
	}

/**
 * tests getAllCoupons from CompanyFacade also tests that the method retrieves only the logged in company coupons.	
 * @throws WrongClientTypeException
 * @throws InterruptedException
 * @throws CompanyNotFoundException 0529986302
 * @throws CouponNotFoundException
 */
	
	@Test
	public void test12() throws WrongClientTypeException, InterruptedException, CompanyNotFoundException, CouponNotFoundException {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		CompanyFacade compF2 = (CompanyFacade) couponSystem.login("JohnBryce","1234",ClientType.COMPANY);
		compF.createCoupon(coup1);
		compF.createCoupon(coup2);
		compF.createCoupon(coup3);
		compF2.createCoupon(coup4);
		compF2.createCoupon(coup5);
		List<Coupon> l2 = new ArrayList<>();
		l2.add(coup1);
		l2.add(coup2);
		l2.add(coup3);
		testHelper.couponListTester(compF.getAllCoupons(), l2);
	}

/**
 * tests getCouponsbyType from CompanyFacade also tests that it retrieves only the current logged in company coupons 
 * @throws WrongClientTypeException
 * @throws InterruptedException
 * @throws CompanyNotFoundException
 * @throws CouponNotFoundException
 */
	
	@Test
	public void test13() throws WrongClientTypeException, InterruptedException, CompanyNotFoundException, CouponNotFoundException {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		CompanyFacade compF2 = (CompanyFacade) couponSystem.login("JohnBryce","1234",ClientType.COMPANY);
		coup1.setType(CouponType.CLOTHING);
		coup2.setType(CouponType.CLOTHING);
		coup3.setType(CouponType.ELECTRONICS);
		coup4.setType(CouponType.CLOTHING);
		coup5.setType(CouponType.FOOD);
		compF.createCoupon(coup1);
		compF.createCoupon(coup2);
		compF.createCoupon(coup3);
		compF2.createCoupon(coup4);
		compF2.createCoupon(coup5);
		List<Coupon> l2 = new ArrayList<>();
		l2.add(coup1);
		l2.add(coup2);
		testHelper.couponListTester(compF.getCouponsByType(CouponType.CLOTHING), l2);
	}
	
/**
 * tests getCouponsByMaxPrice from CompanyFacade
 * @throws WrongClientTypeException
 * @throws InterruptedException
 * @throws CompanyNotFoundException
 * @throws CouponNotFoundException
 */
	
	
	@Test
	public void test14() throws WrongClientTypeException, InterruptedException, CompanyNotFoundException, CouponNotFoundException {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		CompanyFacade compF2 = (CompanyFacade) couponSystem.login("JohnBryce","1234",ClientType.COMPANY);
		coup1.setPrice(500d);
		coup2.setPrice(600d);
		coup3.setPrice(400d);
		coup4.setPrice(400d);
		coup5.setPrice(500d);
		compF.createCoupon(coup1);
		compF.createCoupon(coup2);
		compF.createCoupon(coup3);
		compF2.createCoupon(coup4);
		compF2.createCoupon(coup5);
		List<Coupon> l2 = new ArrayList<>();
		l2.add(coup1);
		l2.add(coup3);
		testHelper.couponListTester(compF.getCouponsByMaxPrice(550d), l2);
	}
	
/**
 * tests getCouponsByMaxDate from CompanyFacade	
 * @throws WrongClientTypeException
 * @throws InterruptedException
 * @throws CompanyNotFoundException
 * @throws CouponNotFoundException
 */
	
	
	@Test
	public void test15() throws WrongClientTypeException, InterruptedException, CompanyNotFoundException, CouponNotFoundException {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		CompanyFacade compF2 = (CompanyFacade) couponSystem.login("JohnBryce","1234",ClientType.COMPANY);
		coup1.setEndDate(date1);
		coup2.setEndDate(date1);
		coup3.setEndDate(date2);
		coup4.setEndDate(date2);
		coup5.setEndDate(date1);
		compF.createCoupon(coup1);
		compF.createCoupon(coup2);
		compF.createCoupon(coup3);
		compF2.createCoupon(coup4);
		compF2.createCoupon(coup5);
		List<Coupon> l2 = new ArrayList<>();
		l2.add(coup1);
		l2.add(coup2);
		testHelper.couponListTester(compF.getCouponsByMaxEndDate(date3), l2);
	}
	
/**
 * tests login, purchaseCoupon and getCoupon from customerFacade
 * @throws InterruptedException
 * @throws WrongClientTypeException
 * @throws CompanyNotFoundException
 * @throws CustomerNotFoundException 
 * @throws CouponNotFoundException 
 */
	
	@Test
	public void test16() throws InterruptedException, WrongClientTypeException, CompanyNotFoundException, CouponNotFoundException, CustomerNotFoundException  {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		adminF.createCustomer(cust1);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		compF.createCoupon(coup1);
		CustomerFacade custF = (CustomerFacade) couponSystem.login("dor", "123343",ClientType.CUSTOMER);
		custF.purchaseCoupon(coup1);
		testHelper.compareCoupons(coup1,custF.getCoupon(coup1));
	
	}
	
/**
 * tests getAllPurhasedCoupons from CustomerFacade
 * @throws InterruptedException
 * @throws WrongClientTypeException
 * @throws CompanyNotFoundException
 * @throws CouponNotFoundException
 * @throws CustomerNotFoundException
 */
	
	@Test
	public void test17() throws InterruptedException, WrongClientTypeException, CompanyNotFoundException, CouponNotFoundException, CustomerNotFoundException  {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		adminF.createCustomer(cust1);
		adminF.createCustomer(cust2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		compF.createCoupon(coup1);
		compF.createCoupon(coup2);
		compF.createCoupon(coup3);
		compF.createCoupon(coup4);
		compF.createCoupon(coup5);
		CustomerFacade custF = (CustomerFacade) couponSystem.login("dor", "123343",ClientType.CUSTOMER);
		CustomerFacade custF2 = (CustomerFacade) couponSystem.login("user", "1111",ClientType.CUSTOMER);
		custF.purchaseCoupon(coup1);
		custF.purchaseCoupon(coup2);
		custF.purchaseCoupon(coup3);
		custF2.purchaseCoupon(coup4);
		custF2.purchaseCoupon(coup5);
		List<Coupon> l2 = new ArrayList<>();
		l2.add(coup1);
		l2.add(coup2);
		l2.add(coup3);
		testHelper.couponListTester(custF.getAllPurchasedCoupons(), l2);
	
	}
	
/**
 * tests getAllPurchasedCouponsByType from CustomerFacade
 * @throws InterruptedException
 * @throws WrongClientTypeException
 * @throws CompanyNotFoundException
 * @throws CouponNotFoundException
 * @throws CustomerNotFoundException
 */
	
	@Test
	public void test18() throws InterruptedException, WrongClientTypeException, CompanyNotFoundException, CouponNotFoundException, CustomerNotFoundException  {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		adminF.createCustomer(cust1);
		adminF.createCustomer(cust2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		coup1.setType(CouponType.CLOTHING);
		coup2.setType(CouponType.CLOTHING);
		coup3.setType(CouponType.ELECTRONICS);
		coup4.setType(CouponType.CLOTHING);
		coup5.setType(CouponType.FOOD);
		compF.createCoupon(coup1);
		compF.createCoupon(coup2);
		compF.createCoupon(coup3);
		compF.createCoupon(coup4);
		compF.createCoupon(coup5);
		CustomerFacade custF = (CustomerFacade) couponSystem.login("dor", "123343",ClientType.CUSTOMER);
		CustomerFacade custF2 = (CustomerFacade) couponSystem.login("user", "1111",ClientType.CUSTOMER);
		custF.purchaseCoupon(coup1);
		custF.purchaseCoupon(coup2);
		custF.purchaseCoupon(coup3);
		custF2.purchaseCoupon(coup4);
		custF2.purchaseCoupon(coup5);
		List<Coupon> l2 = new ArrayList<>();
		l2.add(coup1);
		l2.add(coup2);
		testHelper.couponListTester(custF.getAllPurchasedCouponsByType(CouponType.CLOTHING), l2);
	
	}

	
/**
 * tests ggetAllPurchasedCouponsByMaxPrice from CustomerFacade
 * @throws InterruptedException
 * @throws WrongClientTypeException
 * @throws CompanyNotFoundException
 * @throws CouponNotFoundException
 * @throws CustomerNotFoundException
 */
	
	@Test
	public void test19() throws InterruptedException, WrongClientTypeException, CompanyNotFoundException, CouponNotFoundException, CustomerNotFoundException  {
		AdminFacade adminF = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
		adminF.createCompany(comp1);
		adminF.createCompany(comp2);
		adminF.createCustomer(cust1);
		adminF.createCustomer(cust2);
		CompanyFacade compF = (CompanyFacade) couponSystem.login("Kishurit", "1818",ClientType.COMPANY);
		coup1.setPrice(500d);
		coup2.setPrice(600d);
		coup3.setPrice(400d);
		coup4.setPrice(400d);
		coup5.setPrice(500d);
		compF.createCoupon(coup1);
		compF.createCoupon(coup2);
		compF.createCoupon(coup3);
		compF.createCoupon(coup4);
		compF.createCoupon(coup5);
		CustomerFacade custF = (CustomerFacade) couponSystem.login("dor", "123343",ClientType.CUSTOMER);
		CustomerFacade custF2 = (CustomerFacade) couponSystem.login("user", "1111",ClientType.CUSTOMER);
		custF.purchaseCoupon(coup1);
		custF.purchaseCoupon(coup2);
		custF.purchaseCoupon(coup3);
		custF2.purchaseCoupon(coup4);
		custF2.purchaseCoupon(coup5);
		List<Coupon> l2 = new ArrayList<>();
		l2.add(coup1);
		l2.add(coup3);
		testHelper.couponListTester(custF.getAllPurchasedCouponsByMaxPrice(550d), l2);
	
	}
	
}
