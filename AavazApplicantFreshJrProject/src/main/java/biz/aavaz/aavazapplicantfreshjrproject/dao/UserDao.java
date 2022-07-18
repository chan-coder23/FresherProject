package biz.aavaz.aavazapplicantfreshjrproject.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import biz.aavaz.aavazapplicantfreshjrproject.model.Skill;
import biz.aavaz.aavazapplicantfreshjrproject.model.User;
import biz.aavaz.aavazapplicantfreshjrproject.util.HibernateUtil;

//!!!! For Freshers and Juniors data is accessed via plain JDBC
//!!!! For Mid Level and Senior Java Developers data access should be via hibernate
//!!!! Bonus: If you are a fresher or a junior - extra points will be given for hibernate
public class UserDao {
	private static SkillDao skillDao = new SkillDao();

	public List<User> getAll() {

		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			Transaction tx = session.beginTransaction();
			List<User> list = session.createQuery("select u from User u").list();
			tx.commit();
			session.close();
			return list;
		} catch (Exception ee) {
			System.out.println("Failed to find user");
		}
		return new ArrayList<>(0);
	}
//	public void printAll() {
//
//		try {
//			SessionFactory sf = HibernateUtil.getSessionFactory();
//			Session session = sf.openSession();
//			Transaction tx = session.beginTransaction();
//			session.createQuery("select u from User u")
//			.list()
//			.forEach(t -> System.out.println(t));
//			tx.commit();
//			session.close();
//		} catch (Exception ee) {
//			System.out.println("Failed to find user");
//		}
//	}
	public List<User> getAllBySkillName(String skillName) {

		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			Query<User> q = session.createQuery("select distinct u from User u join fetch u.skills sk where sk.name = :skillName");
			q.setParameter("skillName", skillName);
			List<User> users=q.list();
			session.close();
			return users;
		} catch (Exception ee) {
			System.out.println("Failed to find user");
		}
		return new ArrayList<>(0);
	}
	public void save(User user) {

		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			Transaction tx = session.beginTransaction();
			if (user.getId() > 0) {
				session.merge(user);
			} else {
//				Query<User> q = session.createQuery(
//						"select s from User s where s.first_name=:first_name and"
//						+ " s.last_name=:last_name and s.age=:age");
//				q.setParameter("first_name", user.getFirstName());
//				q.setParameter("last_name", user.getLastName());
//				q.setParameter("age", user.getAge());
				// english,1
				// hindi,1
				Query<Skill> q = session
						.createQuery("select s from Skill as s where concat(s.name,',',s.level) in (:skills)");
				System.out.println("Success!");
				q.setParameter("skills", user.getSkills().stream()
						.map(t -> t.getName().concat("," + t.getLevel()))
						.collect(Collectors.toList()));
				List<Skill> skills = q.list();
				user.setSkills(skills);
				session.persist(user);
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
			Query q = session.createQuery("delete from User where id = :id");
			q.setParameter("id", id);
			q.executeUpdate();
			tx.commit();
			session.close();
		} catch (Exception ee) {
			System.out.println("Failed to delete user");
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public User get(long id) {
		try {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();
			Query<User> q = session.createQuery("select s from SKill s where s.id=:id");
			q.setParameter("id", id);
			List<User> list = q.list();
			session.close();
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (Exception ee) {
			System.out.println("Failed to find user");
		}
		return null;
	}

}
