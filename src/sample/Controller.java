package sample;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


import javafx.scene.*;

import java.util.HashMap;

public class Controller {

	private PersonRegister personRegister = new PersonRegister();
	@FXML private TextField pNbr_textField;
	@FXML private TextField name_textField;
	public Controller(){
		HashMap<String, Person> register = new HashMap<>();
		personRegister.setPersonHashMap(register);
	}

	public void addPerson(ActionEvent event){
		String name = name_textField.getText();
		String pNbr = pNbr_textField.getText();
		try{
			if(pNbr.isEmpty() && name.isEmpty()){
				System.out.println("Name and pnbr is empty!");
			}else if(pNbr.isEmpty()) {
				System.out.println("pNbr is empty");
			}else if(name.isEmpty()){
				System.out.println("Name is empty!");
			}else{
				Person tmpPerson = new Person();
				tmpPerson.setName(name);
				tmpPerson.setpNbr(pNbr);
				personRegister.addPerson(tmpPerson);
				System.out.println("Person " + personRegister.findPerson(pNbr).getName() + " was added to list! ");
			}
		}catch (Exception e1){
			System.out.println("Error: " + e1);
		}
	}
	public void findPerson(ActionEvent event){
		String pNbr = pNbr_textField.getText();
		try{
			if(pNbr.isEmpty()){
				System.out.println("pNbr is empty!");
			}else if(personRegister.findPerson(pNbr) == null){
				System.out.println("Person doesnt exist!");
			}else{
				System.out.println(personRegister.findPerson(pNbr).getName());
			}
		}catch (Exception e1){
			System.out.println("Error: " + e1);
		}
	}



}
