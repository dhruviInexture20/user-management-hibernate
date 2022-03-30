package dao;

import java.io.InputStream;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bean.AddressBean;
import bean.UserBean;
import controller.RegistrationServlet;
import utility.DBConnection;
import utility.PasswordSecurity;

public class UserDaoImpl implements UserDao {
	
	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
	
	static final String addQuery = "insert into userdata"
			+ "(fname, lname, gender, designation, email, phone, password, dob, profilepic) "
			+ "values(?,?,?,?,?,?,?,?,?)";
	
	static final String userIdQuery = "select max(userid) from userdata";
	static final String emailAvailability = "select email from userdata where email=?";
	@Override
	public int addUser(UserBean user) {
		int i = 0;
		BasicConfigurator.configure();
		Connection conn = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(addQuery);
			stmt.setString(1, user.getFname());
			stmt.setString(2, user.getLname());
			stmt.setString(3, user.getGender());
			stmt.setString(4, user.getDesignation());
			stmt.setString(5, user.getEmail());
			stmt.setString(6, user.getPhone());
			String password = user.getPassword();
			PasswordSecurity ps = new PasswordSecurity();
			
			stmt.setString(7, ps.encrypt(password));
//			stmt.setString(7, user.getPassword());
			stmt.setString(8, user.getDob());
			
			InputStream inputStream = user.getProfilepic();
			stmt.setBlob(9, inputStream);
			
			i = stmt.executeUpdate();
			AddressDaoImpl addressdao = new AddressDaoImpl();
			
			addressdao.addAddress(user.getAddressList(), getNextUserID());
			
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return i;
	}

	@Override
	public int getNextUserID() {
		Connection conn = DBConnection.getInstance().getConnection();
		BasicConfigurator.configure();
		int userid = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(userIdQuery);
			
			if(rs.next()) {
				userid = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			logger.error(e);
		}
		return userid;
	}
	
	static String fname = "fname";
	
	@Override
	public UserBean userLogin(String email) {
		BasicConfigurator.configure();
	    Connection conn;
	    UserBean user = null;
	    try {
	      conn = DBConnection.getInstance().getConnection();
	      PreparedStatement stmt = conn.prepareStatement("Select * from userdata where email=?");
	      stmt.setString(1,email);
//	      stmt.setString(2,password);
	      ResultSet rs = stmt.executeQuery();
	      if(rs.next()) {
	        user = new UserBean();
	        user.setUserid(rs.getInt("userid"));
	        user.setFname(rs.getString(fname));
	        user.setLname(rs.getString("lname"));
	        user.setGender(rs.getString("gender"));
	        user.setDesignation(rs.getString("designation"));
	        user.setEmail(rs.getString("email"));
	        user.setPhone(rs.getString("phone"));
	        user.setDob(rs.getString("dob"));
	        user.setRole(rs.getString("role"));
	        
	        user.setProfilepic(rs.getBinaryStream("profilepic"));
	        
	        String encPass = rs.getString("password");
	        
	        PasswordSecurity ps = new PasswordSecurity();
	        
	        user.setPassword( ps.decrypt(encPass) );
//	        user.setPassword(rs.getString("password"));
	        
	        // TODO get all addresses of user
	      }
	      
	    } catch (Exception e) {
	    	logger.error(e);
	    }
	    
	    return user;
	  }

	@Override
	public boolean checkEmailAvailability(String email) {
		Connection conn;
		ResultSet rs = null;
		boolean isAvailable = true;
		BasicConfigurator.configure();
		conn = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(emailAvailability);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				isAvailable = false;
			}
			
		} catch (SQLException e) {
			logger.error(e);
		}
		return isAvailable;
		
	}


}
