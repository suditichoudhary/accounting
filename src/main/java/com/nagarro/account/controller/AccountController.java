package com.nagarro.account.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.account.config.AccountDBConfig;
import com.nagarro.account.models.AccountModel;
import com.nagarro.account.models.AccountRequestDataModel;
import com.nagarro.account.models.AccountValidationReturnModel;
import com.nagarro.account.utils.DateFormatUtil;

@Controller
public class AccountController {
	@Value("${application.user.username:defaultValue}")
	private String userName;
	@Value("${application.jsp.error:defaultValue}")
	private String error;
	@Value("${application.jsp.statement:defaultValue}")
	private String statement;

	/*
	 * Mainly responsible in fetching account statement and displaying it.
	 * Checkes if user can provide optional parameters
	 * Sets last 3 months date as default filter
	 * returns error or statement jsp depending upon result
	 * */
	@GetMapping("/getStatement")
	public ModelAndView captureAndvalidateAccountAdmin(HttpServletRequest httpRequest,HttpServletResponse httpResponse) {
		ModelAndView obj = new ModelAndView();
		try {
			String currentUserName;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				currentUserName = authentication.getName();  

				if(currentUserName.equalsIgnoreCase(userName) && (httpRequest.getParameter("datefrom")!=null || httpRequest.getParameter("dateto")!=null 
						|| httpRequest.getParameter("amountfrom")!=null || httpRequest.getParameter("amountto")!=null) ) {
					obj.setViewName(error);
					obj.addObject("Error", "Unauthorized Access!!!");
					return obj;
				}
			}

			AccountRequestDataModel accountRequestDataModel = new AccountRequestDataModel();
			accountRequestDataModel.setAccountId(httpRequest.getParameter("accid"));
			accountRequestDataModel.setDateFrom(httpRequest.getParameter("datefrom"));
			accountRequestDataModel.setDateTo(httpRequest.getParameter("dateto"));
			accountRequestDataModel.setAmountFrom(httpRequest.getParameter("amountfrom"));
			accountRequestDataModel.setAmountTo(httpRequest.getParameter("amountto"));

			AccountValidationReturnModel accountValidationReturnModel = validateAccount(accountRequestDataModel);
			if(accountValidationReturnModel.getError()) {
				obj.setViewName(error);
				obj.addObject("Error", accountValidationReturnModel.getMessage());
			}else {
				obj.setViewName(statement);
				obj.addObject("accountStatement",accountValidationReturnModel.getAccountModel());
			}
		}catch(Exception e) {
			obj.setViewName(error);
			obj.addObject("Error",e);
		}
		return obj;
	}

	/*
	 * Validate all the params and their values and return specific error
	 * if all the checks pass then fetch data from db and resturn the Accountodel
	 * */
	public AccountValidationReturnModel validateAccount(AccountRequestDataModel accountRequestDataModel) {
		AccountValidationReturnModel accountValidationReturnModel = new AccountValidationReturnModel();
		accountValidationReturnModel.setError(false);
		Long accountId = new Long("0");

		// check user session also
		BigDecimal amountFrom = new BigDecimal("0");
		BigDecimal amountTo = new BigDecimal("0");

		LocalDate dateFrom=null;
		LocalDate dateTo=null;

		if(StringUtils.isEmpty(accountRequestDataModel.getAccountId())) {
			// account id is mandatory
			accountValidationReturnModel.setError(true);
			accountValidationReturnModel.setMessage("Account Id is Mandatory.");
		}else if(!StringUtils.isEmpty(accountRequestDataModel.getAmountFrom()) && StringUtils.isEmpty(accountRequestDataModel.getAmountTo())) {
			// if only amount from came in request then error
			accountValidationReturnModel.setError(true);
			accountValidationReturnModel.setMessage("Amount To is necessary while specifying Amount From.");
		}else if(!StringUtils.isEmpty(accountRequestDataModel.getAmountTo()) && StringUtils.isEmpty(accountRequestDataModel.getAmountFrom())) {
			// if only amount To came in request then error
			accountValidationReturnModel.setError(true);
			accountValidationReturnModel.setMessage("Amount From is necessary while specifying Amount To.");
		}else if(!StringUtils.isEmpty(accountRequestDataModel.getDateFrom()) && StringUtils.isEmpty(accountRequestDataModel.getDateTo())) {
			// if only date from came in request then error
			accountValidationReturnModel.setError(true);
			accountValidationReturnModel.setMessage("Date To is necessary while specifying Date From.");
		}else if(!StringUtils.isEmpty(accountRequestDataModel.getDateTo()) && StringUtils.isEmpty(accountRequestDataModel.getDateFrom())) {
			// if only date To came in request then error
			accountValidationReturnModel.setError(true);
			accountValidationReturnModel.setMessage("Date From is necessary while specifying Date To.");
		}

		if(!StringUtils.isEmpty(accountRequestDataModel.getDateFrom()) && !StringUtils.isEmpty(accountRequestDataModel.getDateTo())) {
			// check date format
			try {
				if(DateFormatUtil.dateMatches(accountRequestDataModel.getDateFrom()) && DateFormatUtil.dateMatches(accountRequestDataModel.getDateTo())) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					dateFrom = LocalDate.parse(accountRequestDataModel.getDateFrom().replace(".", "/"), formatter);
					dateTo = LocalDate.parse(accountRequestDataModel.getDateTo().replace(".", "/"), formatter);
					if(dateFrom.compareTo(dateTo)>0) {
						// date from cannot be bigger than To
						accountValidationReturnModel.setError(true);
						accountValidationReturnModel.setMessage("Date from cannot be bigger than Date To");
					}
				}else {
					accountValidationReturnModel.setError(true);
					accountValidationReturnModel.setMessage("Date format should be (dd.mm.yyyy)");
				}
			}catch(Exception e) {
				// if amount cannot be parsed in a date
				accountValidationReturnModel.setError(true);
				accountValidationReturnModel.setMessage("Date format should be (dd.mm.yyyy)");
			}
		}

		if(!StringUtils.isEmpty(accountRequestDataModel.getAmountTo()) && !StringUtils.isEmpty(accountRequestDataModel.getAmountFrom())) {
			try {
				amountFrom = new BigDecimal(accountRequestDataModel.getAmountFrom());
				amountTo = new BigDecimal(accountRequestDataModel.getAmountTo());
				if(amountFrom.compareTo(amountTo)>0) {
					// amount from cannot be bigger than To
					accountValidationReturnModel.setError(true);
					accountValidationReturnModel.setMessage("Amount from cannot be bigger than Amount To");
				}
			}catch(Exception e) {
				// if amount cannot be parsed in a bigdecimal
				accountValidationReturnModel.setError(true);
				accountValidationReturnModel.setMessage("Amount should be a Number");
			}
		}

		try {
			accountId = new Long(accountRequestDataModel.getAccountId());
			if(accountId<1) {
				accountValidationReturnModel.setError(true);
				accountValidationReturnModel.setMessage("Account Id should be greater than 0");
			}
		}catch(Exception e) {
			// if amount cannot be parsed in a Long
			accountValidationReturnModel.setError(true);
			accountValidationReturnModel.setMessage("Account Id should be a Number");
		}

		if(amountFrom.equals(new BigDecimal("0")) && dateFrom==null) {
			// default case when no parameter then fetch last 3 months data
			dateFrom = LocalDate.now().plusMonths(-3);
			dateTo = LocalDate.now();
		}

		if(!accountValidationReturnModel.getError()) {
			// fetch data from db and set object

			AccountModel accountModel = AccountDBConfig.getConnectionWithAccess(accountId,dateFrom,dateTo,amountFrom,amountTo);
			//			NormalDBConfig.getConnectionWithAccess();

			if(accountModel!=null) {
				accountValidationReturnModel.setAccountModel(accountModel);
			}else {
				accountValidationReturnModel.setError(true);
				accountValidationReturnModel.setMessage("No Data/Match!!!");
			}
		}
		// System.out.println(accountValidationReturnModel.toString());
		return accountValidationReturnModel;
	}

}
