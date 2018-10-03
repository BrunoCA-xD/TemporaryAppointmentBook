package app.model.BO;

import java.util.List;

import app.model.DAO.ContactDAO;
import app.model.VO.ContactVO;

public class ContactBO {

	public List<ContactVO> listAll() {

		return new ContactDAO().getList();
	}

	public void save(ContactVO objContact) {
		System.out.println("booooraaaa");
		new ContactDAO().save(objContact);
	}
}
