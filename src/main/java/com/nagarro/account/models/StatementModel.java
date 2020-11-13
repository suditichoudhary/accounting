package com.nagarro.account.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "statement")
public class StatementModel implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	Long statementId;

	@Column(name = "datefield")
	String date;

	@Column(name = "amount")
	String amount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id")
	AccountModel accountmodel;

	public Long getStatementId() {
		return statementId;
	}
	public void setStatementId(Long statementId) {
		this.statementId = statementId;
	}

	public String getAmount() {
		return amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	public AccountModel getAccountmodel() {
		return accountmodel;
	}
	public void setAccountmodel(AccountModel accountmodel) {
		this.accountmodel = accountmodel;
	}

	public Object clone()throws CloneNotSupportedException{  
		return (StatementModel)super.clone();  
	}
}
