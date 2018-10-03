package app.model.VO;

import java.beans.Transient;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "contact")
public class ContactVO {

	private int _id;
	private String _name;
	private String _cpf;
	private String _rg;
	private String _nickname;
	private String _address;
	private String _email;
	private String _phone;
	private String _whatsapp;
	private LocalDate _lastCall;
	

	public ContactVO() {
	}

	public ContactVO(String name, LocalDate lastCall) {
		_name = name;
		_lastCall = lastCall;
	}

	public ContactVO(String name, String cpf, String rg, String nickname, String address, String email, String phone,
			String whatsapp, LocalDate lastCall) {
		super();
		_name = name;
		_cpf = cpf;
		_rg = rg;
		_nickname = nickname;
		_address = address;
		_email = email;
		_phone = phone;
		_whatsapp = whatsapp;
		_lastCall = lastCall;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return _id;
	}

	public void setId(int id) {
		this._id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	@Column(name = "CPF")
	public String getCPF() {
		return _cpf;
	}

	public void setCPF(String cpf) {
		this._cpf = cpf;
	}

	@Column(name = "RG")
	public String getRG() {
		return _rg;
	}

	public void setRG(String rg) {
		this._rg = rg;
	}

	@Column(name = "NICKNAME")
	public String getNickname() {
		return _nickname;
	}

	public void setNickname(String nickname) {
		this._nickname = nickname;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return _address;
	}

	public void setAddress(String address) {
		this._address = address;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return _email;
	}

	public void setEmail(String email) {
		this._email = email;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return _phone;
	}

	public void setPhone(String phone) {
		this._phone = phone;
	}

	@Column(name = "WHATSAPP")
	public String getWhatsapp() {
		return _whatsapp;
	}

	public void setWhatsapp(String whatsapp) {
		this._whatsapp = whatsapp;
	}

	@Column(name = "LAST_CALL")
	public LocalDate getLastCall() {
		return _lastCall;
	}

	public void setLastCall(LocalDate lastCall) {
		this._lastCall = lastCall;
	}

}
