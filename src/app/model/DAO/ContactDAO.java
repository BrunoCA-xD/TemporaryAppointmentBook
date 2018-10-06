package app.model.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import app.model.VO.ContactVO;
import app.util.HibernateUtil;

public class ContactDAO extends GenericsDAO<ContactVO, Integer> {
	public Session session;

	public ContactDAO() {
		super(ContactVO.class);
	}

	public List<ContactVO> listSearch(String search) {
		try {
			List<ContactVO> lstContatcs;
			session = HibernateUtil.getSessionFactory().openSession();// obtem
																		// uma
																		// sessao
			lstContatcs = session.createNativeQuery("SELECT * FROM contact where name like'%"+search+"%'"
					+ "order by name like'"+search+"%' DESC", ContactVO.class).list();
			if (lstContatcs.size() > 0) {
				return lstContatcs;
			}

		} catch (HibernateException e) {
			System.out.println("Problem on list " + e.getMessage());
		}
		return null;
	}
}
