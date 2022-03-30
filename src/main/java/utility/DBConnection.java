package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	private static DBConnection conn = null;
	static final String loadDriver = "com.mysql.cj.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost/user_management";
	static final String username = "root";
	static final String password = "admin";
	
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
//		String loadDriver = "com.mysql.cj.jdbc.Driver";
//		String URL = "jdbc:mysql://localhost/user_management";
//		String username = "root";
//		String password = "admin";
		
		try {
			Class.forName(loadDriver);
			conn = DriverManager.getConnection(URL, username, password);
			}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void getPreparedStmt(String query) {
		
	}
	
	
}
