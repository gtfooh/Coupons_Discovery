package com.example.demo.web;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entry.CouponClientFacade;
import com.example.demo.entry.CouponSystem;
import com.example.demo.exceptions.WrongClientTypeException;
import com.example.demo.exceptions.WrongPasswordException;
import com.example.demo.other.ClientType;

/**
 * Class LoginServlet
 * a service to login to the system
 * @author Dor
 *
 */
@Controller
public class LoginServlet {

@Autowired
CouponSystem sys;

/**
 * service to login to the system using input
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 * @throws WrongClientTypeException
 * @throws WrongPasswordException
 */

	@RequestMapping(value = "/loginservlet", method = RequestMethod.GET)
	public void login(
			HttpServletRequest request,HttpServletResponse response)
					throws ServletException, IOException, WrongClientTypeException, WrongPasswordException {
	
		String user = request.getParameter("user_name");
		String password = request.getParameter("password");
		ClientType type = ClientType.valueOf(request.getParameter("type"));
		CouponClientFacade facade = null;
		try {
			facade = sys.login(user,password,type);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (facade == null) {
			response.sendRedirect("/login.html");
		}
		else {
			Cookie userCookie = new Cookie("username",user);
			userCookie.setMaxAge(360);
			response.addCookie(userCookie);
			Cookie passCookie = new Cookie("password",password);
			passCookie.setMaxAge(360);
			response.addCookie(passCookie);
			Cookie typeCookie = new Cookie("type",type.toString());
			typeCookie.setMaxAge(360);
			response.addCookie(typeCookie);
			
			switch (type) {
			case ADMIN:
				request.getSession().setAttribute("adminfacade", facade);
				response.sendRedirect("admin_page/index.html");
				break;
			case COMPANY:
				request.getSession().setAttribute("companyfacade", facade);
				response.sendRedirect("company_page/index.html");
				break;
			case CUSTOMER:
				request.getSession().setAttribute("customerfacade", facade);
				response.sendRedirect("/customer_page/index.html");
				break;
			}
		}
	}
	
/**
 * Service to login to the system using cookies	
 * @param request
 * @param response
 * @param cookieUser
 * @param cookiePassword
 * @param cookieType
 * @throws ServletException
 * @throws IOException
 * @throws WrongClientTypeException
 * @throws WrongPasswordException
 */
	
	
	@RequestMapping(value = "/loginservlet-cookies", method = RequestMethod.GET)
	public void cookiesLogin(
			HttpServletRequest request,HttpServletResponse response,
			@CookieValue(name ="username", defaultValue = "0")String cookieUser,
			@CookieValue(name ="password", defaultValue = "0")String cookiePassword,
			@CookieValue(name ="type", defaultValue = "0")String cookieType)
					throws ServletException, IOException, WrongClientTypeException, WrongPasswordException {
		String user = cookieUser;
		String password = cookiePassword;
		ClientType type;
		if (!user.equals("0") && !password.equals("0")) {
			System.out.println("im here 1");
			user = cookieUser;
			System.out.println("user:" + user);
			password = cookiePassword;
			System.out.println("pass:" + password);
			type = ClientType.valueOf(cookieType);
			System.out.println("type:" + type.toString());
			CouponClientFacade facade = null;
			try {
				facade = sys.login(user,password,type);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (facade == null) {
			response.sendRedirect("login.html");
			}
			else {
				switch (type) {
				case ADMIN:
					request.getSession().setAttribute("adminfacade", facade);
					response.sendRedirect("/admin_page/index.html");
					break;
				case COMPANY:
					request.getSession().setAttribute("companyfacade", facade);
					response.sendRedirect("/company_page/index.html");
					break;
				case CUSTOMER:
					request.getSession().setAttribute("customerfacade", facade);
					response.sendRedirect("/customer_page/index.html");
					break;
				}
			}			
		}
		else response.sendRedirect("/login.html");
	}
	
/**
 * Service to logout from the system	
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 * @throws WrongClientTypeException
 * @throws WrongPasswordException
 */
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(
			HttpServletRequest request,HttpServletResponse response)
				throws ServletException, IOException, WrongClientTypeException, WrongPasswordException {
			response.addCookie(new Cookie("username","0"));
			response.addCookie(new Cookie("password","0"));
			response.addCookie(new Cookie("type","0"));
			response.sendRedirect("/login.html");
	}
}