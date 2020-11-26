package sample;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;

import static java.lang.StrictMath.abs;

public class Controller {
	private final PersonRegister personRegister = new PersonRegister();
	private final HashMap<String,Account> allAccount;
	@FXML private TextField pNbr_textField;
	@FXML private TextField name_textField;
	@FXML private TextField accountNbr_textField;
	@FXML private TextArea mainTxtArea;
	@FXML private RadioButton creditRadioBtn;
	@FXML private RadioButton withdrawRadioBtn;
	@FXML private TextField amount_textField;

	public Controller(){
		HashMap<String, Person> register = new HashMap<>();
		personRegister.setPersonHashMap(register);
		this.allAccount= new HashMap<>();
	}

	public void addPerson(ActionEvent event){
		String name = name_textField.getText();
		String pNbr = pNbr_textField.getText();
		/* Testar så att inga nödvändiga fält är tomma.
		  Ifall de är tomma kommer metoden att avbrytas och skriva ut ett meddelande som användaren kan se!
		  Ifall allt gått rätt till skapas en ny person som läggs till i ett PersonRegister!
		 */
		try{
			if(pNbr.isEmpty() || name.isEmpty()){
				mainTxtArea.setText("Name or Personal ID is empty!");
			}else if(personRegister.findPerson(pNbr)!= null){
					mainTxtArea.setText("There already exist a person with this personal ID!");
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
		/* Testar så att inga nödvändiga fält är tomma.
		  Ifall de är tomma kommer metoden att avbrytas och skriva ut ett meddelande som användaren kan se!
		  Ifall allt gått rätt till skapas ett nytt konto som får en ägare, som läggs in i personens lista av konton,
		  samt läggs in i en lista över alla konton som finns för att inte kunna skapa dubbletter av konton.

		  Även om detta skulle kunna vara lugnt då ett konto endast går att komma åt från en person tolkade vi det som att ett konto endast kan tillhöra en person!
		 */
		try{
			if(pNbr.isEmpty() || accountNbr.isEmpty()){
				mainTxtArea.setText("Personal ID or Account Number is empty!");
			}else if(personRegister.findPerson(pNbr)==null){
				mainTxtArea.setText("Could not find a person with this personal ID!");
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
				mainTxtArea.setText("Could not find a person with this personal ID!");
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

	public void showButton(ActionEvent event){
		String pNbr = pNbr_textField.getText();
		try{
			if(pNbr.isEmpty()){
				mainTxtArea.setText("Personal ID is empty!");
			}else if(personRegister.findPerson(pNbr) == null){
				mainTxtArea.setText("Could not find a person with this personal ID!");
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
				mainTxtArea.setText("Could not find a person with this personal ID!");
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
