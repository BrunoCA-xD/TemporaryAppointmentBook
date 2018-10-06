package app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import app.model.BO.ReminderBO;
import app.model.VO.ReminderVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ReminderController implements Initializable {
	
	@FXML
	private ComboBox<ReminderVO> cboContact;
	
	@FXML
	private ComboBox<String> cboUnit;
	
	@FXML
	private DatePicker dpChooseDate;
	
	@FXML
	private ToggleGroup tgGroup;
	
	@FXML
	private RadioButton rbNextDate;
	
	@FXML 
	private RadioButton rbInterval;
	
	@FXML
	private TextField txtUnityNumber;
	
	private ReminderBO objReminderBO; 
	private ObservableList<ReminderVO> obsReminder;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		objReminderBO = new ReminderBO();
		fillContact();
	}
	
	public void fillContact() {
		obsReminder = FXCollections.observableArrayList(objReminderBO.listAll());
		cboContact.setItems(obsReminder);
	}
	
	public void cboContractChanged() {
		
	}

}
