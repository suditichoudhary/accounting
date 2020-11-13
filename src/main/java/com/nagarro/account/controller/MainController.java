package com.nagarro.account.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController 
{
	@Value("${application.jsp.home:defaultValue}")
	private String home;
	@Value("${application.jsp.error:defaultValue}")
	private String error;
	@Value("${application.jsp.statement:defaultValue}")
	private String statement;
	@Value("${application.jsp.accountuser:defaultValue}")
	private String accountUser;
	@Value("${application.jsp.accountadmin:defaultValue}")
	private String accountAdmin;
	@Value("${application.user.adminname:defaultValue}")
	private String adminUserName;
	
	// Home Controller : Home Screen redirects to account.
	@RequestMapping("/")
	public String home() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			if(currentUserName.equalsIgnoreCase(adminUserName))
				return accountAdmin;
			else return accountUser;
		}
		return accountUser;
	}

	// Home Controller : Home Screen redirects to account.
	@RequestMapping("/home")
	public String homepage() {
		return home;
	}

	@RequestMapping("/statement")
	public String statement() {
		return statement;
	}

	// Account Controller : Responsible of displaying filter screen based upon user.
	@RequestMapping("/account")
	public String account() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			if(currentUserName.equalsIgnoreCase(adminUserName))
				return accountAdmin;
			else return accountUser;
		}
		return accountUser;
	}
}
