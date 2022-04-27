package utility;


import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(HibernateUtil.class);
	public static SessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure().buildSessionFactory();
				
				return sessionFactory;

			} catch (Exception e) {
				logger.error(e);
			}
		}
		return sessionFactory;
	}
}
		
		
