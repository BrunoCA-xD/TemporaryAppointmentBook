package app.model.BO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import app.model.DAO.ContactDAO;
import app.model.VO.ContactVO;

public class ContactBO {

	public List<ContactVO> listAll() {
		List<ContactVO> lst = new ContactDAO().getList();
		lst.forEach(c -> {

			c.setLastCall(addOneDay(c.getLastCall()));
			c.setNextCall(addOneDay(c.getNextCall()));
		});
		return lst;
	}

	public List<ContactVO> searchName(String search) {
		List<ContactVO> lst = new ContactDAO().listSearch(search);
		lst.forEach(c -> {

			c.setLastCall(addOneDay(c.getLastCall()));
			c.setNextCall(addOneDay(c.getNextCall()));
		});
		return lst;
	}

	private LocalDate addOneDay(LocalDate dateToAdd) {
		if (dateToAdd == null)
			return null;
		GregorianCalendar gc = new GregorianCalendar();
		Date date = Date.from(dateToAdd.atStartOfDay(ZoneId.systemDefault()).toInstant());
		gc.setTime(date);
		gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
		date = gc.getTime();
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public LocalDate addInterval(LocalDate dateToAdd,int interval) {
		if (dateToAdd == null)
			return null;
	
		GregorianCalendar gc = new GregorianCalendar();
		Date date = Date.from(dateToAdd.atStartOfDay(ZoneId.systemDefault()).toInstant());
		gc.setTime(date);
		gc.add(GregorianCalendar.DAY_OF_MONTH, interval);
		date = gc.getTime();
		
		System.out.println(date);
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
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
