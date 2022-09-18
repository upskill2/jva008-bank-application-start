package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.annotations.ActiveRecord;
import com.luxoft.bankapp.annotations.ActiveRecordEntity;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.io.Serializable;

public abstract class AbstractAccount extends ActiveRecord implements Account, Serializable {
	
	private int id;
	protected double balance;
	
	public AbstractAccount(int id, double balance) {
		this.id = id;
		this.balance = balance;
	}

	@Override
	public void deposit(final double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot deposit a negative amount");
		}
		this.balance += amount;
	}

	@Override
	public void withdraw(final double amount) throws NotEnoughFundsException {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot withdraw a negative amount");
		}
		
		if (amount > maximumAmountToWithdraw()) {
			throw new NotEnoughFundsException(id, balance, amount, "Requested amount exceeds the maximum amount to withdraw");
		}
		
		this.balance -= amount;
	}

	@Override
	public double decimalValue(){

		return Math.round(balance);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public int hashCode() {
		return this.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account account = (Account) obj;
			return account.getId()==(this.id);
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {


		return "AbstractAccount{" +
				"id=" + id +
				", balance=" + balance +
				'}';
	}
	@Override
	public double getOverdraft() {
		return 0;
	}


}
