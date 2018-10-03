package app.model.DAO;


import app.model.VO.ContactVO;

public class ContactDAO extends GenericsDAO<ContactVO, Integer> {
	public ContactDAO() {
		super(ContactVO.class);
	}
	
}
