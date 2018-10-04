package app.model.BO;

import java.util.List;

import app.model.DAO.ContactDAO;
import app.model.VO.ContactVO;

public class ContactBO {

	public List<ContactVO> listAll() {

		return new ContactDAO().getList();
	}

	public void saveOrUpdate(ContactVO objContact) {
		if (objContact.getId() == 0)
			new ContactDAO().save(objContact);
		else
			new ContactDAO().update(objContact);
	}

	public void delete(ContactVO contact) {
		new ContactDAO().delete(contact.getId());
	}
}
