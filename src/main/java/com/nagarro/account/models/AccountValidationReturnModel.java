package com.nagarro.account.models;

public class AccountValidationReturnModel {
	private Boolean error;
	private String message;
	private AccountModel accountModel;
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public AccountModel getAccountModel() {
		return accountModel;
	}
	public void setAccountModel(AccountModel accountModel) {
		this.accountModel = accountModel;
	}
	@Override
	public String toString() {
		return "AccountValidationReturnModel [error=" + error + ", message=" + message + ", accountModel="
				+ accountModel + "]";
	}
	
	
	
}
