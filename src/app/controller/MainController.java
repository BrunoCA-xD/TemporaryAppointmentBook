package app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import app.model.BO.ContactBO;
import app.model.VO.ContactVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

public class MainController implements Initializable {

//	@FXML AnchorPane pnlContent;
	@FXML
	AnchorPane pnlDetails;
	@FXML
	TextField txtName;
	@FXML
	TextField txtCPF;
	@FXML
	TextField txtRG;
	@FXML
	TextField txtNickname;
	@FXML
	TextArea txtAddress;
	@FXML
	TextField txtEmail;
	@FXML
	TextField txtPhone;
	@FXML
	TextField txtWhatsapp;
	@FXML
	DatePicker dateLastCall;
	@FXML
	ListView<ContactVO> lstContact;
	@FXML
	TextField txtSearch;
	@FXML
	Button btnSearch;
	@FXML
	Button btnCloseDetails;
	@FXML
	Button btnSave;
	@FXML
	Button btnConfigCall;
	@FXML
	Button btnDelete;
	@FXML
	Button btnEdit;
	@FXML
	Button btnAdd;
	@FXML
	Button btnConfirmCall;
	@FXML
	Label lblDetailsTitle;

	ObservableList<ContactVO> observableListContacts = FXCollections.observableArrayList();
	ContactVO contact;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnCloseDetails.getStyleClass().add("btn");
		observableListContacts.setAll(new ContactBO().listAll());

		dateLastCall.setValue(LocalDate.now());
		lstContact.setCellFactory(a -> new ContactListCell());

		lstContact.setOnMouseClicked(MouseEvent -> {
			lstContactItemClicked(MouseEvent);
		});
		fillContactList();
	}

	@FXML
	public void btnCloseDetailsClicked() {
		if (pnlDetails.isVisible())
			pnlDetails.setVisible(false);
		clearDetailsField();
	}

	@FXML
	public void btnAddClicked() {
		if (!pnlDetails.isVisible())
			pnlDetails.setVisible(true);
		clearDetailsField();

		setPnlDetailsTitle("ADICIONAR CONTATO");
		contact = null;
		dateLastCall.setValue(LocalDate.now());

		enableOrDisableDetailsField(false);
		btnSave.setDisable(false);
		btnConfirmCall.setVisible(false);
	}
	
	@FXML
	public void btnEditClicked() {
		enableOrDisableDetailsField(false);
		btnSave.setDisable(false);
	}

	@FXML
	public void btnSaveClicked() {
		if (contact == null) {
			contact = new ContactVO(txtName.getText().trim(), txtCPF.getText().trim(), txtRG.getText().trim(),
					txtNickname.getText().trim(), txtAddress.getText().trim(), txtEmail.getText().trim(),
					txtPhone.getText().trim(), txtWhatsapp.getText().trim(), dateLastCall.getValue());
		}
		else {
			contact.setName(txtName.getText().trim());
			contact.setCPF(txtCPF.getText().trim());
			contact.setRG(txtRG.getText().trim());
			contact.setNickname(txtNickname.getText().trim());
			contact.setAddress(txtAddress.getText().trim());
			contact.setEmail(txtEmail.getText().trim());
			contact.setPhone(txtPhone.getText().trim());
			contact.setWhatsapp(txtWhatsapp.getText().trim());
			contact.setLastCall(dateLastCall.getValue());
		}
		new ContactBO().saveOrUpdate(contact);
		fillContactList();
	}
	
	@FXML
	public void btnConfirmCallClicked() {	
		System.out.println(LocalDate.now());
		contact.setLastCall(LocalDate.now());
		dateLastCall.setValue(LocalDate.now());
		new ContactBO().saveOrUpdate(contact);
		fillContactList();
	}
	@FXML
	public void btnDeleteClicked() {	
		
		new ContactBO().delete(contact);
		clearDetailsField();
		fillContactList();
	}
	private void lstContactItemClicked(MouseEvent mouseEvent) {
		int selected = lstContact.getSelectionModel().getSelectedIndex();
		contact = lstContact.getItems().get(selected);
		fillDetailsField(contact);
		enableOrDisableDetailsField(true);
		btnConfirmCall.setVisible(true);
	}

	private void setPnlDetailsTitle(String pnlTitle) {

		lblDetailsTitle.setText(pnlTitle);
	}

	private void fillDetailsField(ContactVO contact) {
		if (!pnlDetails.isVisible())
			pnlDetails.setVisible(true);
		txtName.setText(contact.getName());
		txtRG.setText(contact.getRG());
		txtCPF.setText(contact.getCPF());
		txtNickname.setText(contact.getNickname());
		txtAddress.setText(contact.getAddress());
		txtEmail.setText(contact.getEmail());
		txtPhone.setText(contact.getPhone());
		txtWhatsapp.setText(contact.getWhatsapp());
		System.out.println(contact.getLastCall());
		dateLastCall.setValue(contact.getLastCall());
		setPnlDetailsTitle("INFORMAÇÔES DO CONTATO");
		btnSave.setDisable(true);

	}

	private void clearDetailsField() {
		List<Node> nodes = pnlDetails.getChildren();
		nodes.forEach((n) -> {
			if (n instanceof TextField || n instanceof TextArea)
				((TextInputControl) n).setText("");

		});
	}

	private void enableOrDisableDetailsField(boolean isDisable) {
		List<Node> nodes = pnlDetails.getChildren();
		nodes.forEach((n) -> {
			if (n instanceof TextField || n instanceof TextArea)
				((TextInputControl) n).setDisable(isDisable);
			if (n instanceof DatePicker)
				n.setDisable(isDisable);
		});
	}

	private void fillContactList() {
		lstContact.getItems().clear();
		observableListContacts.setAll(new ContactBO().listAll());
		lstContact.setItems(observableListContacts);
	}
}

class ContactListCell extends ListCell<ContactVO> {
	@Override
	protected void updateItem(ContactVO item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText("");
			getStyleClass().clear();
			return;
		}
		setText(item.getName());
		if (LocalDate.now().isBefore(item.getLastCall())) {
			getStyleClass().addAll("withicon", "showing");
			getStylesheets().add(getClass().getResource("/style/StyleApp.css").toExternalForm());
		}
	}

	public ContactListCell() {
		super();
		setOnMouseClicked((MouseEvent event) -> {
			if (isEmpty()) {
				event.consume();
			}
		});
	}

}
