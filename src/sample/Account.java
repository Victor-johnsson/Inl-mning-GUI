package sample;

public class Account {
	private String accountNbr;
	private double balance;
	private Person owner;

	public String getAccountNbr() {
		return accountNbr;
	}

	public void setAccountNbr(String accountNbr) {
		this.accountNbr = accountNbr;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}
	public void credit(double amount){
		balance=balance+amount;
	}
	public void withdraw(double amount){
		balance=balance-amount;
	}
}
