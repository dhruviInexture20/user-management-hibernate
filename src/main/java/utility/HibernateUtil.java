package utility;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import bean.UserBean;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(HibernateUtil.class);
	public static SessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure().buildSessionFactory();
				
				return sessionFactory;

			} catch (Exception e) {
				logger.info(e);
			}
		}
		logger.info("SessionFactory  = "+ sessionFactory==null);
		return sessionFactory;
	}
}
		
		
