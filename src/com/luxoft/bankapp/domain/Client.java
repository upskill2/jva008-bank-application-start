package com.luxoft.bankapp.domain;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class Client implements Serializable{
	
	private String name;
	private Gender gender;
	private String city;
	private Set<Account> accounts = new HashSet<>();
	private LocalDate birthday;

	public Client(String name, Gender gender, String city, LocalDate birthday) {
		this.name = name;
		this.gender = gender;
		this.city = city;
		this.birthday = birthday;
	}
	
	public void addAccount(final Account account) {
		accounts.add(account);
	}
	
	public String getName() {
		return name;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}
	
	public String getClientGreeting() {
		if (gender != null) {
			return gender.getGreeting() + " " + name;
		} else {
			return name;
		}
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(name).append(" + ").append(gender).append(" + birthday: ").append(birthday);
		System.out.println(sb);
		return getClientGreeting();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Client client = (Client) o;
		return accounts.equals(client.accounts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accounts);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public long convertDate(LocalDate initialDate){

		long duration = Duration.between(LocalDate.now().atStartOfDay(), initialDate.atStartOfDay()).toDays();

		LocalDate result = LocalDate.of(LocalDate.now().getYear(), initialDate.getMonth(),initialDate.getDayOfMonth());

		return duration;

	}

}
