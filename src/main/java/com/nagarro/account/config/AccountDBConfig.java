package com.nagarro.account.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.nagarro.account.models.AccountModel;
import com.nagarro.account.models.StatementModel;
import com.nagarro.account.utils.HashingUtil;

public class AccountDBConfig {
	private static SessionFactory factory; 
	/*
	 * Establish DB connection 
	 * settings in hibernate.cfg.xml file
	 * AccountModel represents primary table (account)
	 * StatementModel represents secondary table (statement)
	 * */

	public static AccountModel getConnectionWithAccess(Long accId,LocalDate dateFrom,LocalDate dateTo,BigDecimal amountFrom,BigDecimal amountTo) {
		AccountModel accountModel = null;
		try {
			factory = new Configuration().configure().
					addAnnotatedClass(AccountModel.class).
					addAnnotatedClass(StatementModel.class).
					buildSessionFactory();

			AccountDBConfig accountDBConfig = new AccountDBConfig();

			accountModel = accountDBConfig.listAccounts();

			accountModel = applyFilters(accId,dateFrom,dateTo,amountFrom,amountTo,accountModel);

		} catch (Throwable e) { 
			System.err.println("Failed to connect to access DB : " + e);
			throw new ExceptionInInitializerError(e); 
		}
		return accountModel;
	}

	/*
	 * Fire query and fetch complete account data
	 * */

	@SuppressWarnings("rawtypes")
	public AccountModel listAccounts(){
		Session session = factory.openSession();
		Transaction transac = null;
		AccountModel accountModel = null;

		try {
			transac = session.beginTransaction();
			String hql = "from AccountModel where ID = :acc";
			Query query = session.createQuery(hql);
			query.setParameter("acc", "1");
			List accounts = query.list(); 
			for (Iterator iterator = accounts.iterator(); iterator.hasNext();){
				accountModel = (AccountModel) iterator.next(); 
				accountModel.setAccountNumber(HashingUtil.encryptString(accountModel.getAccountNumber()));
			}
			transac.commit();
		} catch (Exception e) {
			if (transac!=null) transac.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}

		return accountModel;
	}

	/*
	 * Applies filters based upon user search
	 * filter on date and amount
	 * return filtered data only
	 * */

	public static AccountModel applyFilters(Long accId,LocalDate dateFrom,LocalDate dateTo,BigDecimal amountFrom,BigDecimal amountTo,AccountModel accountModel) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		AccountModel filteredModel = new AccountModel();
		filteredModel.setAccountNumber(accountModel.getAccountNumber());
		List<StatementModel> staList = new ArrayList<>();
		boolean flagPass;

		for(StatementModel stateM : accountModel.getStatementList()) {
			flagPass=false;

			if(dateFrom!=null) {
				if(dateFrom.compareTo(LocalDate.parse(stateM.getDate().replace(".", "/"), formatter))<0) {
					// cleared from check bigger than dateFrom
					if(dateTo.compareTo(LocalDate.parse(stateM.getDate().replace(".", "/"), formatter))>=0) {
						//	cleared to check smaller than dateTo
						flagPass=true;
					}
				}
			}else {
				// no date filter specified
				flagPass=true;
			}
			if(flagPass && !amountFrom.equals(new BigDecimal("0"))) {
				if(amountFrom.compareTo(new BigDecimal(stateM.getAmount()))<0){
					if(amountTo.compareTo(new BigDecimal(stateM.getAmount()))>=0){
						// do nothing its already true
					}else {
						flagPass=false;
					}
				}else {
					flagPass=false;
				}
			}

			if(flagPass) {

				try {
					staList.add((StatementModel) stateM.clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}

		if(staList==null || staList.size()<1) {
			filteredModel=null;
		}else {
			filteredModel.setStatementList(staList);
		}
		return filteredModel;
	}


}
