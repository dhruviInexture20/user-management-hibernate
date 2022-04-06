package dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mysql.cj.xdevapi.Result;

import bean.AddressBean;
import bean.UserBean;
import controller.RegistrationServlet;
import utility.DBConnection;
import utility.PasswordSecurity;

public class UserDaoImpl implements UserDao {
	
	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
	
	static final String addQuery = "insert into userdata"
			+ "(fname, lname, gender, designation, email, phone, password, dob, profilepic, question, answer) "
			+ "values(?,?,?,?,?,?,?,?,?,?,?)";
	
	static final String userIdQuery = "select max(userid) from userdata";
	static final String emailAvailability = "select email from userdata where email=?";
	static final String userByEmailQuery = "select * from userdata where email=?";
	static final String allUsersQuery = "select * from userdata where role='user'";
	static final String updateUserQuery = "update userdata set fname=?, lname=?, password=?, phone=?, designation=?, dob=?, question=?, answer=? where email=?";
	static final String deleteUserQuery = "delete from userdata where userid=?";
	static final String getUserEmailByIDQuery = "select email from userdata where userid=?";
	static final String authSecurityQnAQuery = "select question,answer from userdata where email=?";
	static final String resetPasswordQuery = "update userdata set password=? where email=?";
	static final String storeOTPQuery = "update userdata set otp=? where email=?";
	static final String getOTPQuery = "select otp from userdata where email=?";
	
	static String userid = "userid";
	static String fname = "fname";
	static String lname = "lname";
	static String gender = "gender";
	static String designation = "designation";
	static String email = "email";
	static String phone = "phone";
	static String dob = "dob";
	static String role = "role";
	static String question = "question";
	static String answer = "answer";
	
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
			stmt.setString(7, user.getProtectedPassword());
			stmt.setString(8, user.getDob());
			
			InputStream inputStream = user.getProfilepic();
			stmt.setBlob(9, inputStream);
			
			stmt.setString(10, user.getS_question());
			stmt.setString(11, user.getS_answer());
			
			i = stmt.executeUpdate();
			AddressDaoImpl addressdao = new AddressDaoImpl();
			
			addressdao.addAddressList(user.getAddressList(), getNextUserID());
			
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
	
	
	
	@Override
	public UserBean userLogin(String userEmail) {
		BasicConfigurator.configure();
	    Connection conn;
	    UserBean user = new UserBean();
	    try {
	      conn = DBConnection.getInstance().getConnection();
	      PreparedStatement stmt = conn.prepareStatement(userByEmailQuery);
	      stmt.setString(1, userEmail);
	      ResultSet rs = stmt.executeQuery();
	      if(rs.next()) {
//	        user = new UserBean();
	        user.setUserid(rs.getInt(userid));
	        user.setFname(rs.getString(fname));
	        user.setLname(rs.getString(lname));
	        user.setGender(rs.getString(gender));
	        user.setDesignation(rs.getString(designation));
	        user.setEmail(rs.getString(email));
	        user.setPhone(rs.getString(phone));	    
	        user.setDob(rs.getString(dob));
	        user.setRole(rs.getString(role));
	        user.setS_question(rs.getString(question));
	        user.setS_answer(rs.getString(answer));
	        
	        Blob blob = rs.getBlob("profilepic");
	        InputStream inputStream = blob.getBinaryStream();
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
 
	        while((bytesRead = inputStream.read(buffer))!= -1) {
	        	outputStream.write(buffer, 0, bytesRead);
	        }
	        byte[] imageBytes = outputStream.toByteArray();
	        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
	        user.setBase64Image(base64Image);
	      
	        user.setPasswordString(rs.getString("password"));
	        
	        // TODO get all addresses of user
	        
	        AddressDao addressDao = new AddressDaoImpl();
	        List<AddressBean> userAddressList = new ArrayList<>();
	        
	        userAddressList = addressDao.getAddress(rs.getInt("userid"));
	        
	        user.setAddressList(userAddressList);
	        
	      }
	      
	    } catch (Exception e) {
	    	logger.error(e);
	    }
	    
	    return user;
	  }

	
	// return true if email is available in database 
	// return false if email is not available
	@Override
	public boolean isEmailAvailable(String email) {
		Connection conn;
		ResultSet rs = null;
		boolean isAvailable = false;
		BasicConfigurator.configure();
		conn = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(emailAvailability);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				isAvailable = true;
			}
			
		} catch (SQLException e) {
			logger.error(e);
		}
		return isAvailable;
		
	}
	
	@Override
	public ResultSet getListOfUsers() {
		ResultSet rs = null;
		BasicConfigurator.configure();
		
		Connection conn = DBConnection.getInstance().getConnection();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(allUsersQuery);
		} catch (SQLException e) {
			logger.error(e);
		}
		
		return rs;
	
	}

	@Override
	public void updateUser(UserBean user) {
		// TODO Auto-generated method stub
		
		BasicConfigurator.configure();
		Connection conn = DBConnection.getInstance().getConnection();
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(updateUserQuery);
			
			stmt.setString(1, user.getFname());
			stmt.setString(2, user.getLname());
			stmt.setString(3, user.getProtectedPassword());
			stmt.setString(4, user.getPhone());
			stmt.setString(5, user.getDesignation());
			stmt.setString(6, user.getDob());
			stmt.setString(7, user.getS_question());
			stmt.setString(8, user.getS_answer());
			stmt.setString(9, user.getEmail());
			
			stmt.executeUpdate();
			logger.info("user data updated");
			
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void deleteUser(String userid) {
		BasicConfigurator.configure();
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(deleteUserQuery);
			stmt.setString(1, userid);
			stmt.execute();
			logger.info("user deleted");
			
		} catch (SQLException e) {
			logger.info(e);
		}
		
	}

	@Override
	public UserBean getUserByUserID(int userid) {
		
		BasicConfigurator.configure();
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;
		ResultSet rs = null;
		UserBean user = new UserBean();
		String userEmail = null;
		
		try {
			stmt = conn.prepareStatement(getUserEmailByIDQuery);
			stmt.setInt(1, userid);
			rs = stmt.executeQuery();
			logger.info("rs = " + rs);
			if(rs.next()) {
				userEmail = rs.getString(email);
				
			}
			user = userLogin(userEmail);
			
			
		} catch (SQLException e) {
			logger.error(e);
		}
		return user;
		
		
		
	}

	@Override
	public boolean checkSecurityQnA(String userEmail, String security_question, String security_answer) {
		
		BasicConfigurator.configure();
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;
		ResultSet rs = null;
		String queFromDB = null;
		String ansFromDB = null;
		
		try {
			stmt = conn.prepareStatement(authSecurityQnAQuery);
			stmt.setString(1, userEmail);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
			queFromDB = rs.getString("question");
			ansFromDB = rs.getString("answer");
			
			}
			
			if(security_question.equals(queFromDB) && security_answer.equals(ansFromDB)) {
				return true;
			}
			else {
				return false;
			}
					
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	@Override
	public void resetPass(String email, String newPassword) {
		BasicConfigurator.configure();
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(resetPasswordQuery);
			stmt.setString(1, newPassword);
			stmt.setString(2, email);
			
			logger.info(stmt);
			
			int i = stmt.executeUpdate();
			logger.info("Password reset successfully" + i);
			
		} catch (SQLException e) {
			logger.error(e);
		}
		
		
		
	}

	@Override
	public void storeOtp(String email, String otp) {
		BasicConfigurator.configure();
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(storeOTPQuery);
			stmt.setString(1, otp);
			stmt.setString(2, email);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public String getUserOTP(String email) {
		BasicConfigurator.configure();
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;
		String otp = null;
		
		try {
			stmt = conn.prepareStatement(getOTPQuery);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				logger.info(rs.getString("otp"));
				otp = rs.getString("otp");
				
			}
			
		} catch (SQLException e) {
			logger.error(e);
		}
		return otp;
		
		
	}


}
