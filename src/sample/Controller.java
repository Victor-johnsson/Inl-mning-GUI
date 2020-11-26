package sample;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;

import static java.lang.StrictMath.abs;

public class Controller {
	private PersonRegister personRegister = new PersonRegister();
	@FXML private TextField pNbr_textField;
	@FXML private TextField name_textField;
	@FXML private TextField accountNbr_textField;
	@FXML private TextArea mainTxtArea;
	@FXML private RadioButton creditRadioBtn;
	@FXML private RadioButton withdrawRadioBtn;
	@FXML private TextField amount_textField;
	private HashMap<String,Account> allAccount;

	public Controller(){
		HashMap<String, Person> register = new HashMap<>();
		personRegister.setPersonHashMap(register);
		HashMap<String, Account> allAccounts = new HashMap<>();
		this.allAccount=allAccounts;
	}

	public void addPerson(ActionEvent event){
		String name = name_textField.getText();
		String pNbr = pNbr_textField.getText();
		try{
			if(pNbr.isEmpty() || name.isEmpty()){
				mainTxtArea.setText("Name or Personal ID is empty!");
			}else if(personRegister.findPerson(pNbr)!= null){
					mainTxtArea.setText("There already exist a person with this pNbr!");
			}else{
				Person tmpPerson = new Person();
				tmpPerson.setName(name);
				tmpPerson.setpNbr(pNbr);
				personRegister.addPerson(tmpPerson);
				mainTxtArea.setText(personRegister.findPerson(pNbr).getName() + " was added to list!");
			}
		}catch (Exception e1){
			System.out.println("Error: " + e1);
		}
	}
	public void addAccount(ActionEvent event){
		String pNbr = pNbr_textField.getText();
		String accountNbr = accountNbr_textField.getText();

		try{
			if(pNbr.isEmpty() || accountNbr.isEmpty()){
				mainTxtArea.setText("Personal ID or Account Number is empty!");
			}else if(personRegister.findPerson(pNbr)==null){
				mainTxtArea.setText("Person doesn't exist!");
			}else if(allAccount.containsKey(accountNbr)){
				mainTxtArea.setText("Account already exists!");
			}else{
				Account tmpAccount = new Account();
				tmpAccount.setAccountNbr(accountNbr);
				tmpAccount.setOwner(personRegister.findPerson(pNbr));
				personRegister.findPerson(pNbr).addAccount(tmpAccount);
				allAccount.put(tmpAccount.getAccountNbr(),tmpAccount);
				mainTxtArea.setText("Account: " + "'" +personRegister.findAccount(pNbr,accountNbr).getAccountNbr() + "'"+
						" was added to: " + personRegister.findAccount(pNbr,accountNbr).getOwner().getName() + "'s accounts!");
			}
		}catch (Exception e1){
			System.out.println("Error: " + e1);
		}
	}


	public void creditOrWithdraw(ActionEvent event) {
		String accountNbr = accountNbr_textField.getText();
		String personalNbr = pNbr_textField.getText();
		double amount = 0;
		if(!(amount_textField.getText().isEmpty())){
			amount = abs(Double.valueOf(amount_textField.getText()));
		}
		try {
			if (accountNbr.isEmpty() || personalNbr.isEmpty() || amount_textField.getText().isEmpty() ) {
				mainTxtArea.setText("One of the required fields is empty!");
			} else if (personRegister.findPerson(personalNbr) == null) {
				mainTxtArea.setText("Person doesn't exist in register");
			} else if (personRegister.findAccount(personalNbr, accountNbr) == null) {
				mainTxtArea.setText("Person doesn't own an account with this number!");
			} else if(creditRadioBtn.isSelected()){
				personRegister.findAccount(personalNbr, accountNbr).credit(amount);
				mainTxtArea.setText("Credited " + amount + " to account '"
						+ personRegister.findAccount(personalNbr,accountNbr).getAccountNbr() + "'" + "\n"+
						"New balance is: " + personRegister.findAccount(personalNbr, accountNbr).getBalance());
			}else if(withdrawRadioBtn.isSelected()) {
				personRegister.findAccount(personalNbr, accountNbr).withdraw(amount);
				mainTxtArea.setText("Withdrew " + amount + " from account '"
						+ personRegister.findAccount(personalNbr, accountNbr).getAccountNbr() + "'" + "\n"+
						"New balance is: " + personRegister.findAccount(personalNbr, accountNbr).getBalance());
			}
		} catch (Exception e1) {
			System.out.println("Error: " + e1);
		}
	}

	public void showAllAccounts(ActionEvent event){
		String pNbr = pNbr_textField.getText();
		try{
			if(pNbr.isEmpty()){
				mainTxtArea.setText("Personal ID is empty!");
			}else if(personRegister.findPerson(pNbr) == null){
				mainTxtArea.setText("Person doesnt exist!");
			}else{
				String personName = personRegister.findPerson(pNbr).getName();
				String allAccount ="";
				for (Account tmpAccount : personRegister.findPerson(pNbr).getAccounts().values()) {
					allAccount += "Account: " + tmpAccount.getAccountNbr() + " has balance: " + tmpAccount.getBalance() + "\n";
				}
				mainTxtArea.setText(personName + "'s accounts: " + "\n" + allAccount + "Total balance: " + personRegister.findPerson(pNbr).totBalance());
			}
		}catch (Exception e1){
			System.out.println("Error: " + e1);
		}
	}
	public void removePerson(ActionEvent event) {
		String pNbr = pNbr_textField.getText();
		try {
			if (pNbr.isEmpty()) {
				mainTxtArea.setText("Personal ID is empty!");
			} else if (personRegister.findPerson(pNbr) == null) {
				mainTxtArea.setText("Could not find person with this personal ID!");
			} else {
				Person tmp = personRegister.findPerson(pNbr);
				for (Account a:tmp.getAccounts().values()) {
					allAccount.remove(a.getAccountNbr(), a);
				}
				personRegister.removePerson(pNbr);
				mainTxtArea.setText(tmp.getName() + " was removed!");
			}
		} catch (Exception e1) {
			System.out.println("Error: " + e1);
		}
	}
}
