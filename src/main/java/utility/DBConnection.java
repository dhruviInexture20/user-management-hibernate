package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import service.AdminServiceImpl;


public class DBConnection {
	private static DBConnection conn = null;
	static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost/user_management";
	static final String USERNAME = "root";
	static final String PASSWORD = "admin";
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
		
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			}
		catch(SQLException e){
			logger.error(e);
		}
		catch(Exception e) {
			logger.error(e);
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
