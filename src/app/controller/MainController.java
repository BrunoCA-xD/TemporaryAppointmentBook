package app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import app.model.BO.ContactBO;
import app.model.VO.ContactVO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {

//	@FXML AnchorPane pnlContent;
	@FXML
	private AnchorPane pnlDetails;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtCPF;
	@FXML
	private TextField txtRG;
	@FXML
	private TextField txtNickname;
	@FXML
	private TextArea txtAddress;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtWhatsapp;
	@FXML
	private DatePicker dateLastCall;
	@FXML
	private ListView<ContactVO> lstContact;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnCloseDetails;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnConfigCall;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnConfirmCall;
	@FXML
	private Label lblDetailsTitle;

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
		numericField(txtCPF);
		addTextLimiter(txtCPF, 11);
		numericField(txtRG);
		addTextLimiter(txtRG, 9);
		numericField(txtPhone);
		addTextLimiter(txtPhone, 11);
		numericField(txtWhatsapp);
		addTextLimiter(txtWhatsapp, 11);

	}

	@FXML
	public void btnCloseDetailsClicked() {
		if (pnlDetails.isVisible())
			pnlDetails.setVisible(false);
		clearDetailsField();
		disableOrEnableControls(true);
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
		btnConfirmCall.setVisible(false);
		disableOrEnableControls(true);
		btnSave.setDisable(false);
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
		} else {
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
		contact.setLastCall(LocalDate.now());
		dateLastCall.setValue(LocalDate.now());
		new ContactBO().saveOrUpdate(contact);
		disableOrEnableControls(false);
		btnSave.setDisable(true);
		fillContactList();

	}

	@FXML
	public void btnDeleteClicked() {

		new ContactBO().delete(contact);
		clearDetailsField();
		disableOrEnableControls(true);
		fillContactList();
		btnAddClicked();
	}

	private void lstContactItemClicked(MouseEvent mouseEvent) {
		int selected = lstContact.getSelectionModel().getSelectedIndex();
		contact = lstContact.getItems().get(selected);
		fillDetailsField(contact);
		enableOrDisableDetailsField(true);
		disableOrEnableControls(false);
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
		dateLastCall.setValue(contact.getLastCall());
		setPnlDetailsTitle("INFORMAÇÔES DO CONTATO");
		disableOrEnableControls(true);

	}

	private void disableOrEnableControls(boolean isDisable) {
		btnEdit.setDisable(isDisable);
		btnDelete.setDisable(isDisable);
		btnSave.setDisable(isDisable);

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

	@FXML
	public void btnAddMouseEntered() {
		ImageView img = (ImageView) btnAdd.getChildrenUnmodifiable().get(0);
		img.setImage(new Image(getClass().getResourceAsStream("/icon/add_white.png")));
	}

	@FXML
	public void btnAddMouseExited() {
		ImageView img = (ImageView) btnAdd.getChildrenUnmodifiable().get(0);
		img.setImage(new Image(getClass().getResourceAsStream("/icon/add.png")));
	}

	@FXML
	public void btnEditMouseEntered() {
		ImageView img = (ImageView) btnEdit.getChildrenUnmodifiable().get(0);
		img.setImage(new Image(getClass().getResourceAsStream("/icon/edit_white.png")));
	}

	@FXML
	public void btnEditMouseExited() {
		ImageView img = (ImageView) btnEdit.getChildrenUnmodifiable().get(0);
		img.setImage(new Image(getClass().getResourceAsStream("/icon/edit.png")));
	}

	@FXML
	public void btnDeleteMouseEntered() {
		ImageView img = (ImageView) btnDelete.getChildrenUnmodifiable().get(0);
		img.setImage(new Image(getClass().getResourceAsStream("/icon/add_white.png")));
	}

	@FXML
	public void btnDeleteMouseExited() {
		ImageView img = (ImageView) btnDelete.getChildrenUnmodifiable().get(0);
		img.setImage(new Image(getClass().getResourceAsStream("/icon/add.png")));
	}

	public static void numericField(final TextField textField) {
		textField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					char ch = textField.getText().charAt(oldValue.intValue());
					if (!(ch >= '0' && ch <= '9')) {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
					}
				}
			}
		});
	}

	public static void addTextLimiter(final TextField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}
			}
		});
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
		if (LocalDate.now().isBefore(item.getLastCall())) {// TODO mudar a logica para veriricar o reminder
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
