package com.example.demo.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Company;
import com.example.demo.entities.Customer;
import com.example.demo.entry.AdminFacade;
import com.example.demo.exceptions.CompanyNameTakenException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CustomerNameTakenException;
import com.example.demo.exceptions.CustomerNotFoundException;

/**
 * Class AdminWebservice a collection of services for system administration use
 * 
 * @author Dor
 *
 */

@CrossOrigin("*")
@RestController
@RequestMapping("/admin-api")
public class AdminWebservice {

	@Autowired
	AdminFacade ctxFacade;

	AdminFacade facade;

	private AdminFacade fakeLogin() {
		return ctxFacade;
	}

	/**
	 * Service to create a company
	 * 
	 * @param request
	 * @param c
	 * @throws JSONException
	 * @throws IOException
	 * @throws CompanyNameTakenException
	 * @throws InterruptedException 
	 */

	@RequestMapping(value = "/company", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createCompany(HttpServletRequest request, @RequestBody Company c) throws CompanyNameTakenException, InterruptedException {
		
			// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
			this.facade = this.fakeLogin();
			this.facade.createCompany(c);
		
	}

	/**
	 * Service to remove a company
	 * 
	 * @param request
	 * @param c
	 */

	@RequestMapping(value = "/company", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeCompany(HttpServletRequest request, @RequestBody Company c) {
		try {
			// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
			this.facade = this.fakeLogin();
			this.facade.removeCompany(c);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Service to get all the companies
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public List<Company> getAllCompanies(HttpServletRequest request) {
		// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
		this.facade = this.fakeLogin();
		List<Company> list = null;
		try {
			list = this.facade.getAllCompanies();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Collections.sort(list);
		return list;

	}

	/**
	 * service to get a specific company by id
	 * 
	 * @param request
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/company/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Company getCompany(HttpServletRequest request, @PathVariable("id") long id) {
		// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
		this.facade = this.fakeLogin();
		Company result = null;
		try {
			result = this.facade.getCompany(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * SErvice to update a company
	 * 
	 * @param request
	 * @param c
	 */

	@RequestMapping(value = "/company", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateCompany(HttpServletRequest request, @RequestBody Company c) {
		try {
			// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
			this.facade = this.fakeLogin();
			this.facade.updateCompany(c);
		} catch (InterruptedException | CompanyNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Service to Create a customer
	 * 
	 * @param request
	 * @param c
	 * @throws CustomerNameTakenException
	 */

	@RequestMapping(value = "/customer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createCustomer(HttpServletRequest request, @RequestBody Customer c) throws CustomerNameTakenException {
		try {
			// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
			this.facade = this.fakeLogin();
			this.facade.createCustomer(c);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Service to Remove a Customer
	 * 
	 * @param request
	 * @param c
	 */

	@RequestMapping(value = "/customer", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeCustomer(HttpServletRequest request, @RequestBody Customer c) {
		try {
			// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
			this.facade = this.fakeLogin();
			this.facade.removeCustomer(c);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Service that get all customers
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public List<Customer> getAllCustomers(HttpServletRequest request) {
		// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
		this.facade = this.fakeLogin();
		List<Customer> list = null;
		try {
			list = this.facade.getAllCustomers();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Collections.sort(list);
		return list;

	}

	/**
	 * Service to get a specific customer by id
	 * 
	 * @param request
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getCustomer(HttpServletRequest request, @PathVariable("id") long id) {
		Customer result = null;
		// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
		this.facade = this.fakeLogin();
		try {
			result = this.facade.getCustomer(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Service to update a customer
	 * 
	 * @param request
	 * @param c
	 */

	@RequestMapping(value = "/customer", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateCustomer(HttpServletRequest request, @RequestBody Customer c) {
		// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
		this.facade = this.fakeLogin();
		try {
			this.facade.updateCustomer(c);
		} catch (InterruptedException | CustomerNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * service to ShutDown the server
	 * 
	 * @param request
	 */

	@RequestMapping(value = "/shutdown", method = RequestMethod.GET)
	public void shutDownSystem(HttpServletRequest request) {
		// this.facade = (AdminFacade) request.getSession().getAttribute("adminfacade");
		this.facade = this.fakeLogin();
		this.facade.shutDown();
	}

}
