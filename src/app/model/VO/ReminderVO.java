package app.model.VO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="reminder")
public class ReminderVO {

	private int _id;
	private LocalDate _nextCall;
	private Set<ContactVO> _tbContact = new HashSet<ContactVO>();

	@Id
	@Column(name="id")
	public int getId() {
		return _id;
	}

	public void setId(int id) {
		this._id = id;
	}

	@Column(name="next_call")
	public LocalDate getNextCall() {
		return _nextCall;
	}

	public void setNextCall(LocalDate nextCall) {
		this._nextCall = nextCall;
	}
	@ManyToOne(targetEntity = ContactVO.class)
	@JoinColumn(name = "CONTACT")
	@Fetch(FetchMode.SELECT)
	public Set<ContactVO> getTbContact() {
		return _tbContact;
	}

	public void setTbContact(Set<ContactVO> _tbContact) {
		this._tbContact = _tbContact;
	}

}
