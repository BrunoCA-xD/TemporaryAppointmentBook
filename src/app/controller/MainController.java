package app.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import app.model.BO.ContactBO;
import app.model.VO.ContactVO;
import app.util.ScreenUtil;
import app.util.ScreenUtil.OnChangeScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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

	private ObservableList<ContactVO> observableListContacts = FXCollections.observableArrayList();
	private ContactVO contact;
	private HashMap<String, Object> hmp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hmp = new HashMap<String, Object>();
		ScreenUtil.addOnChangeScreenListener(new OnChangeScreen() {
			public void onScreenChanged(String newScreen, HashMap<String, Object> hmap) {
				// por enquanto nada
			}
		});
		btnCloseDetails.getStyleClass().add("btn");
		observableListContacts.setAll(new ContactBO().listAll());

		dateLastCall.setValue(LocalDate.now());
		lstContact.setCellFactory(a -> new ContactListCell());

		lstContact.setOnMouseClicked(MouseEvent -> {
			lstContactItemClicked(MouseEvent);
		});
		fillContactList(null);
		ScreenUtil.numericField(txtCPF);
		ScreenUtil.addTextLimiter(txtCPF, 11);
		ScreenUtil.numericField(txtRG);
		ScreenUtil.addTextLimiter(txtRG, 9);
		ScreenUtil.numericField(txtPhone);
		ScreenUtil.addTextLimiter(txtPhone, 11);
		ScreenUtil.numericField(txtWhatsapp);
		ScreenUtil.addTextLimiter(txtWhatsapp, 11);

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
		fillContactList(null);
	}

	@FXML
	public void btnConfirmCallClicked() {
		contact.setLastCall(LocalDate.now());
		if (contact.getCallInterval() > 0)
			contact.setNextCall(new ContactBO().addInterval(LocalDate.now(), contact.getCallInterval()));
		dateLastCall.setValue(LocalDate.now());
		new ContactBO().saveOrUpdate(contact);
		disableOrEnableControls(false);
		btnSave.setDisable(true);
		fillContactList(null);

	}

	@FXML
	public void btnConfigCall(ActionEvent event) {
		hmp.put("contact", contact);
		new ScreenUtil().openNewWindow((Stage) pnlDetails.getScene().getWindow(), "Reminder", true, hmp);
		fillContactList(null);
	}

	@FXML
	public void btnDeleteClicked() {

		new ContactBO().delete(contact);
		clearDetailsField();
		disableOrEnableControls(true);
		fillContactList(null);
		btnAddClicked();
	}

	@FXML
	public void btnSearchClicked() {

		fillContactList(txtSearch.getText().trim());
	}

	private void lstContactItemClicked(MouseEvent mouseEvent) {
		int selected = lstContact.getSelectionModel().getSelectedIndex();
		if (selected > -1) {
			contact = lstContact.getItems().get(selected);
			fillDetailsField(contact);
			enableOrDisableDetailsField(true);
			disableOrEnableControls(false);
			btnConfirmCall.setVisible(true);
			btnConfigCall.setDisable(false);
		}

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
		btnConfigCall.setDisable(true);
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

	private void fillContactList(String search) {
		lstContact.getItems().clear();
		if (search == null)
			observableListContacts.setAll(new ContactBO().listAll());
		else
			observableListContacts.setAll(new ContactBO().searchName(search));
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

}

class ContactListCell extends ListCell<ContactVO> {

	@Override
	protected void updateItem(ContactVO item, boolean empty) {
		final Tooltip tooltip = new Tooltip();
		tooltip.setShowDelay(Duration.millis(1));
		super.updateItem(item, empty);
		if (item == null || empty) {
			getStyleClass().removeAll("withicon", "showing-warning", "showing-danger");
			setText("");
			setTooltip(null);
			return;
		}
		setText(item.getName());
		setStyle("-fx-font: 18px 'System'");
		if (item.getNextCall() == null) {
			getStyleClass().addAll("withicon", "showing-warning");
			tooltip.setText("Sem ligação agendada");
			setTooltip(tooltip);
			return;
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
			Date date = Date.from(item.getNextCall().atStartOfDay(ZoneId.systemDefault()).toInstant());
			tooltip.setText("Proxima ligação: " + formatter.format(date));
			setTooltip(tooltip);
		}
		if (LocalDate.now().isAfter(item.getNextCall())) {// TODO mudar a logica para veriricar o reminder
			getStyleClass().addAll("withicon", "showing-danger");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
			Date date = Date.from(item.getNextCall().atStartOfDay(ZoneId.systemDefault()).toInstant());
			tooltip.setText("Ligação atrasada!  " + formatter.format(date));
			setTooltip(tooltip);

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
