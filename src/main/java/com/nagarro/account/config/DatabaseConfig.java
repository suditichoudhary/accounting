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

public class DatabaseConfig {

	private static SessionFactory factory; 
	public static AccountModel getConnectionWithAccess(Long accId,LocalDate dateFrom,LocalDate dateTo,BigDecimal amountFrom,BigDecimal amountTo) {
		AccountModel accountModel = null;
		try {
			factory = new Configuration().configure().
					addAnnotatedClass(AccountModel.class).
					addAnnotatedClass(StatementModel.class).
					buildSessionFactory();

			DatabaseConfig dbConfig = new DatabaseConfig();

			accountModel = dbConfig.listAccounts();

			accountModel = applyFilters(accId,dateFrom,dateTo,amountFrom,amountTo,accountModel);

		} catch (Throwable e) { 
			System.err.println("Failed to establish a sessionFactory object : " + e);
			throw new ExceptionInInitializerError(e); 
		}
		return accountModel;
	}
	
	public AccountModel listAccounts(){
		Session session = factory.openSession();
		Transaction transac = null;
		AccountModel accountModel = null;

		try {
			transac = session.beginTransaction();
			String hql = "from AccountModel where ID = :acc"; // where StatementModel.statementId = 1";
			Query query = session.createQuery(hql);
			query.setParameter("acc", "1");
			List accounts = query.list(); 
			for (Iterator iterator = accounts.iterator(); iterator.hasNext();){
				accountModel = (AccountModel) iterator.next(); 
				System.out.print("Acc num : " + accountModel.getAccountNumber()); 
				System.out.print("Acc type : " + accountModel.getAccountType()); 
				System.out.println("Acc id : " + accountModel.getId());
				System.out.println("Statement : " + accountModel.getStatementList().toString());
				accountModel.setAccountNumber(HashingUtil.encryptString(accountModel.getAccountNumber()));
				System.out.println("hashed acc num : "+accountModel.getAccountNumber());
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
	
	public static AccountModel applyFilters(Long accId,LocalDate dateFrom,LocalDate dateTo,BigDecimal amountFrom,BigDecimal amountTo,AccountModel accountModel) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		AccountModel filteredModel = new AccountModel();
		filteredModel.setAccountNumber(accountModel.getAccountNumber());
		List<StatementModel> staList = new ArrayList<>();
		boolean flagPass;

		for(StatementModel stateM : accountModel.getStatementList()) {
			flagPass=false;

			if(dateFrom.compareTo(LocalDate.parse(stateM.getDate().replace(".", "/"), formatter))<0) {
				// cleared from check
				if(dateTo.compareTo(LocalDate.parse(stateM.getDate().replace(".", "/"), formatter))>=0) {
					//	cleared to check
					flagPass=true;
				}
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
