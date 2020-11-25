package sample;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javafx.scene.*;

public class Controller {
	@FXML private TextField name_textField;
	public void pressButton(ActionEvent event){
		System.out.println(name_textField.getText());


	}
}
