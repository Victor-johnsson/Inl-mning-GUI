package sample;

import java.util.HashMap;

public class Person {
	private String pNbr;
	private String name;
	private HashMap<String,Account> accounts = new HashMap<>();

	public String getpNbr() {
		return pNbr;
	}

	public void setpNbr(String pNbr) {
		this.pNbr = pNbr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void addAccount(Account a){
		accounts.put(a.getAccountNbr(),a);
	}
	public Account findAccount(String accountNbr){
		Account a;
		a=accounts.get(accountNbr);
		return a;
	}
	public double totBalance(){
		double totBalance=0;
		for (Account tmp: accounts.values()) {
			totBalance=totBalance+ tmp.getBalance();
		}
		return totBalance;
	}

	public HashMap<String, Account> getAccounts() {
		return accounts;
	}

}



