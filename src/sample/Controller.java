package sample;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import javafx.scene.*;

import java.util.HashMap;

public class Controller {

	private PersonRegister personRegister = new PersonRegister();
	@FXML private TextField pNbr_textField;
	@FXML private TextField name_textField;
	@FXML private TextField accountNbr_textField;
	@FXML private TextArea mainTxtArea;
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
				mainTxtArea.setText("Name and pnbr is empty!");
			}else if(pNbr.isEmpty()) {
				mainTxtArea.setText("pNbr is empty");
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
	public void findPerson(ActionEvent event){
		String pNbr = pNbr_textField.getText();
		try{
			if(pNbr.isEmpty()){
				mainTxtArea.setText("pNbr is empty!");
			}else if(personRegister.findPerson(pNbr) == null){
				mainTxtArea.setText("Person doesnt exist!");
			}else{
				mainTxtArea.setText(personRegister.findPerson(pNbr).getName());
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
				mainTxtArea.setText("pNbr & name is empty!");
			}else if(pNbr.isEmpty()){
					mainTxtArea.setText("pNbr is empty");
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
				mainTxtArea.setText(personRegister.findAccount(pNbr,accountNbr).getAccountNbr() +
						"--->" + personRegister.findAccount(pNbr,accountNbr).getOwner().getName() + " was added!");
			}
		}catch (Exception e1){
			System.out.println("Error: " + e1);
		}
	}



}
