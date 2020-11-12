package com.nagarro.account.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Table(name = "account")
//@SecondaryTable(name = "statement")
//,pkJoinColumns = @PrimaryKeyJoinColumn(name="ID",referencedColumnName="account_id"))
public class AccountModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	Long Id;
	
	@Column(name = "account_number")
	String accountNumber;
	
	@Column(name = "account_type")
	String accountType;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	List<StatementModel> statementList;
	
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public List<StatementModel> getStatementList() {
		return statementList;
	}
	public void setStatementList(List<StatementModel> statementList) {
		this.statementList = statementList;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	@Override
	public String toString() {
		return "AccountModel [Id=" + Id + ", accountNumber=" + accountNumber + ", accountType=" + accountType
				+ ", statementList=" + statementList + "]";
	}
	
	
	
}
