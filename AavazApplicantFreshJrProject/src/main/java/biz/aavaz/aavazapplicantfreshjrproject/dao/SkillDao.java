package biz.aavaz.aavazapplicantfreshjrproject.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import biz.aavaz.aavazapplicantfreshjrproject.model.Skill;
import biz.aavaz.aavazapplicantfreshjrproject.model.User;
import biz.aavaz.aavazapplicantfreshjrproject.util.HibernateUtil;

//!!!! For Freshers and Juniors data is accessed via plain JDBC
//!!!! For Mid Level and Senior Java Developers data access should be via hibernate
//!!!! Bonus: If you are a fresher or a junior - extra points will be given for hibernate
public class SkillDao {
	
	public List<Skill> getAll() {

		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			List<Skill> list = session.createQuery("select s from Skill s").list();
			session.close();
			return list;
		} catch (Exception ee) {
			System.out.println("Failed to find skills");
		}
		return new ArrayList<>(0);
	}

	public void save(Skill skill) {

		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			Transaction tx = session.beginTransaction();
			if (skill.getId() > 0) {
				session.merge(skill);
			} else {
				Query<Skill> q = session.createQuery(
						"select s from Skill s where s.name=:name and s.level=:level");
				q.setParameter("name", skill.getName());
				q.setParameter("level", skill.getLevel());
				List<Skill>skills = q.list();
				if(skill==null || skills.isEmpty()) {
					session.persist(skill);
					System.out.println("Success!");
				}else {
					System.out.println("Skill already Exists");
				}
				
			}
			tx.commit();
			session.close();
		} catch (Exception ee) {
			ee.printStackTrace();
			System.out.println("Failed to save skill");
		}
	}

	public void delete(Integer id) {

		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			Transaction tx = session.beginTransaction();
			Query q = session.createQuery("delete from Skill where id = :id");
			q.setParameter("id", id);
			q.executeUpdate();
			tx.commit();
			session.close();
		} catch (Exception ee) {
			System.out.println("Failed to find skill");
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Skill get(long id) {
		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			Query<Skill> q = session.createQuery("select s from Skill s where s.id=:id");
			q.setParameter("id", id);
			List<Skill> list = q.list();
			session.close();
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (Exception ee) {
			System.out.println("Failed to find skill");
		}
		return null;
	}
}
