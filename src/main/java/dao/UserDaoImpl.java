package dao;


import java.util.List;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;


import bean.UserBean;
import utility.HibernateUtil;

public class UserDaoImpl implements UserDao {

	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public int addUser(UserBean user) {
		BasicConfigurator.configure();
		int i = 0;
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			i = (int) session.save(user);
			transaction.commit();
		}
		catch (Exception e) {
			logger.info(e);
		}
		
		return i;
	}


	@Override
	public UserBean getUser(String userEmail) {
			BasicConfigurator.configure();
			UserBean user = null;
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {

				user = (UserBean) session.createQuery("FROM UserBean U WHERE U.email = :userEmail").setParameter("userEmail", userEmail)
		                .uniqueResult();
				
			} catch (Exception e) {
				logger.info(e);
			}
			return user;
		}
	
	// return true if email is available in database
	// return false if email is not available
	@Override
	public boolean isEmailAvailable(String email) {
		boolean isAvailable = false;
		
		UserBean user = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			user = (UserBean) session.createQuery("FROM UserBean U WHERE U.email = :userEmail").setParameter("userEmail", email)
	                .uniqueResult();
			if(user != null) {
				isAvailable = true;
			}
			
		} catch (Exception e) {
			logger.info(e);
		}
		return isAvailable;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UserBean> getListOfUsers() throws Exception {
		BasicConfigurator.configure();
		Transaction transaction = null;
		List<UserBean> listOfUser = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			
			listOfUser = session.createQuery("from UserBean U WHERE role='user'").getResultList();
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			logger.error(e);
		}
		return listOfUser;
		
		
		
	}

	@Override
	public void updateUser(UserBean user) {
		BasicConfigurator.configure();
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// update user
			
			session.update(user);
			
			logger.info("user updated successfully......");
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			logger.error(e);
		}
		
	}		

	@Override
	public void deleteUser(int userid) {
		BasicConfigurator.configure();
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// Delete a user object
			UserBean user = session.get(UserBean.class, userid);
			logger.info(user.getUserid());
			if (user != null) {
				session.delete(user);
				logger.info("user deleted successfully");	
			}
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			logger.error(e);
		}
	}


	@Override
	public UserBean getUserByUserID(int userid) {

		BasicConfigurator.configure();
		UserBean user = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			user = (UserBean) session.createQuery("FROM UserBean U WHERE U.userid = :userid").setParameter("userid", userid)
	                .uniqueResult();
			
			
		} catch (Exception e) {
			logger.info(e);
		}
		return user;

	}

	@Override
	public boolean checkSecurityQnA(String userEmail, String security_question, String security_answer) {

		BasicConfigurator.configure();
		UserBean user = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			user = (UserBean) session.createQuery("FROM UserBean U WHERE U.email = :userEmail AND U.s_question = :question AND U.s_answer = :answer")
					.setParameter("userEmail", userEmail)
					.setParameter("question", security_question)
					.setParameter("answer", security_answer)
	                .uniqueResult();
			
			
			if(user != null) {
				return true;
			}
		} catch (Exception e) {
			logger.info(e);
		}

		return false;
	}

}
