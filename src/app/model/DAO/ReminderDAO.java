package app.model.DAO;


import app.model.VO.ReminderVO;

public class ReminderDAO extends GenericsDAO<ReminderVO, Integer> {
	public ReminderDAO() {
		super(ReminderVO.class);
	}
	
}
