package com.example.demo.DAO;

import java.util.List;

import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompanyNameTakenException;
import com.example.demo.exceptions.CompanyNotFoundException;

public interface CompanyDAO {
	public boolean createCompany(Company c) throws  CompanyNameTakenException, InterruptedException;
	public boolean removeCompany(Company c) throws CompanyNotFoundException, InterruptedException;
	public void updateCompany(Company c) throws CompanyNotFoundException, InterruptedException;
	public Company getCompany(long id) throws CompanyNotFoundException, InterruptedException;
	public List<Company> getAllCompanies() throws CompanyNotFoundException, InterruptedException;
	public List<Coupon> getCoupons(Company c) throws InterruptedException;
	
	

}
