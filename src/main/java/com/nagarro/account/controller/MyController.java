package com.nagarro.account.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController 
{
	@RequestMapping("/")
	public String home() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    if(currentUserName.equalsIgnoreCase("testadmin"))
		    	return "AccountAdmin.jsp";
		    else return "AccountUser.jsp";
		}
		return "AccountUser.jsp";
	}
	
	@RequestMapping("/home")
	public String homepage() {
		//System.out.println("Hello Suditi!");
		return "Home.jsp";
	}
	
	@RequestMapping("/login")
	public String login(ModelAndView model, String errorMsg,String logout) {
		if(errorMsg!=null) {
			model.addObject("errorMsg","Your username/password is wrong");
			
		}
		
		if(logout!=null) {
			model.addObject("msg","You have successfully logged out!");
		
		}
		return "Login.jsp";
	}
	
	@RequestMapping("/statement")
	public String statement() {
		return "Statement.jsp";
	}
	
	@RequestMapping("/account")
	public String account() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    if(currentUserName.equalsIgnoreCase("testadmin"))
		    	return "AccountAdmin.jsp";
		    else return "AccountUser.jsp";
		}
		return "AccountUser.jsp";
	}
	
	
//	@GetMapping("/login")
//	public String createSession(HttpSession httpSession,HttpRequest httprequest) {
//	//	httpSession.setAttribute("username", httprequest.get);
//		// remove all attributes
//	//	httpSession.invalidate();
//		return "Statement.jsp";
//	}
}
