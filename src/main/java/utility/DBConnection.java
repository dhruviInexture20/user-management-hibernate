package utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;



public class DBConnection {
	private static DBConnection conn = null;
	
	private static final Logger logger = LogManager.getLogger(DBConnection.class);
	
	private DBConnection() {
		
	}
	
	public static DBConnection getInstance() {
		if (conn == null) {
			conn = new DBConnection();
		}
		return conn;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		InputStream input = getClass().getResourceAsStream("config.properties");

		Properties prop = new Properties();
		try {
			prop.load(input);
			
			logger.info("username = " + prop.getProperty("db.username"));
			logger.info("password = " + prop.getProperty("db.password"));
			
			String driver = prop.getProperty("db.driver");
			String url =  prop.getProperty("db.url");
			String username = prop.getProperty("db.username");
			String password = prop.getProperty("db.password");
			
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			
		} catch (IOException | ClassNotFoundException | SQLException e) {
			logger.error(e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return conn;
	}

	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error(e);
		}
		
	}
}
