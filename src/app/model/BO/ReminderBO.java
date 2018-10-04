package app.model.BO;

import java.util.List;

import app.model.DAO.ContactDAO;
import app.model.DAO.ReminderDAO;
import app.model.VO.ContactVO;
import app.model.VO.ReminderVO;

public class ReminderBO {

	public List<ReminderVO> listAll() {

		return new ReminderDAO().getList();
	}

	public void saveOrUpdate(ReminderVO objReminder) {
		if(objReminder.getId() == 0)
			new ReminderDAO().save(objReminder);
		else
			new ReminderDAO().update(objReminder);
	}
}
