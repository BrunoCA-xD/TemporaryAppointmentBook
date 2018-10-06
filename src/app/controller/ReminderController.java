package app.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import app.model.BO.ContactBO;
import app.model.VO.ContactVO;
import app.util.ScreenUtil;
import app.util.ScreenUtil.OnChangeScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReminderController implements Initializable {

	@FXML
	DatePicker dateNextCall;
	@FXML
	AnchorPane pnlRoot;
	@FXML
	Label lblContact;
	@FXML
	TextField txtInterval;

	private ContactVO contact;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ScreenUtil.addOnChangeScreenListener(new OnChangeScreen() {
			public void onScreenChanged(String newScreen, HashMap<String, Object> hmap) {
				contact = (ContactVO) hmap.get("contact");
				lblContact.setText("Contato: " + contact.getName());
				dateNextCall.setValue(contact.getNextCall());
				if (contact.getCallInterval() > 0) {
					txtInterval.setText(String.valueOf(contact.getCallInterval()));
				}
			}
		});

		ScreenUtil.numericField(txtInterval);
	}

	@FXML
	public void btnConfirmClicked(ActionEvent event) {
		if (dateNextCall.getValue() == null) {
			JOptionPane.showMessageDialog(null, "Selecione uma data para a proxima ligação");
			return;
		}
		int interval;
		try {
			interval = Integer.parseInt(txtInterval.getText().trim());
		} catch (Exception e) {
			interval = 0;
		}
		if (interval != 0) {
			contact.setCallInterval(interval);
		}
		contact.setNextCall(dateNextCall.getValue());
		new ContactBO().saveOrUpdate(contact);

		((Stage) pnlRoot.getScene().getWindow()).close();
	}

	@FXML
	public void btnGetBackClicked(ActionEvent event) {
		((Stage) pnlRoot.getScene().getWindow()).close();
	}

}
