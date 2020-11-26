package sample;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
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
			if(pNbr.isEmpty() && name.isEmpty()){
				mainTxtArea.setText("Name and Personal ID is empty!");
			}else if(pNbr.isEmpty()) {
				mainTxtArea.setText("Personal ID is empty");
			}else if(name.isEmpty()) {
				mainTxtArea.setText("Name is empty!");
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
			if(pNbr.isEmpty() && accountNbr.isEmpty()){
				mainTxtArea.setText("Personal ID & Name is empty!");
			}else if(pNbr.isEmpty()){
					mainTxtArea.setText("Personal ID is empty");
			}else if(accountNbr.isEmpty()){
				mainTxtArea.setText("AccountNbr is empty!");
			}else if(personRegister.findPerson(pNbr)==null){
				mainTxtArea.setText("Person doesnt exist!");
			}else if(personRegister.findAccount(pNbr,accountNbr)!= null){
				mainTxtArea.setText("Person already own this account!");
			}else if(allAccount.containsKey(accountNbr)){
				mainTxtArea.setText("Account already exists!");
			}else{
				System.out.println("Worked");
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

	@FXML private TextField amount_textField;
	public void creditOrWithdraw(ActionEvent event) {
		String accountNbr = accountNbr_textField.getText();
		String personalNbr = pNbr_textField.getText();
		if (creditRadioBtn.isSelected()) {
			double amount = abs(Double.valueOf(amount_textField.getText()));
			try {
				if (accountNbr.isEmpty()) {
					mainTxtArea.setText("Account number is empty! ");
				} else if (personalNbr.isEmpty()) {
					mainTxtArea.setText("Personal ID is empty! ");
				} else if (amount_textField.getText().isEmpty()) {
					mainTxtArea.setText("Amount is empty!");
				} else if (amount == 0) {
					mainTxtArea.setText("Amount was equal to zero, nothing was done");
				} else if (personRegister.findPerson(personalNbr) == null) {
					mainTxtArea.setText("Person doesn't exist in register");
				} else if (personRegister.findAccount(personalNbr, accountNbr) == null) {
					mainTxtArea.setText("This account doesn't exist on this person!");
				} else {
					personRegister.findAccount(personalNbr, accountNbr).credit(amount);
					System.out.println(personRegister.findAccount(personalNbr, accountNbr).getBalance());
				}
			} catch (Exception e1) {
				System.out.println("Error: " + e1);
			}
		} else if (withdrawRadioBtn.isSelected()) {
			double amount = abs(Double.valueOf(amount_textField.getText()));
			try {
				if (accountNbr.isEmpty()) {
					mainTxtArea.setText("Account number is empty! ");
				} else if (personalNbr.isEmpty()) {
					mainTxtArea.setText("Personal ID is empty! ");
				} else if (amount_textField.getText().isEmpty()) {
					mainTxtArea.setText("Amount is empty!");
				} else if (amount == 0) {
					mainTxtArea.setText("Amount was equal to zero, nothing was done");
				} else if (personRegister.findPerson(personalNbr) == null) {
					mainTxtArea.setText("Person doesn't exist in register");
				} else if (personRegister.findAccount(personalNbr, accountNbr) == null) {
					mainTxtArea.setText("This account doesn't exist on this person!");
				} else {
					personRegister.findAccount(personalNbr, accountNbr).withdraw(amount);
					System.out.println(personRegister.findAccount(personalNbr, accountNbr).getBalance());
				}
			} catch (Exception e1) {
				System.out.println("Error: " + e1);
			}
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
				mainTxtArea.setText("Person doesnt exist!");
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
