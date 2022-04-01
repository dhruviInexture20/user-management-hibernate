package service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.UserBean;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import dao.UserDaoImpl;

public class AdminServiceImpl implements AdminService {
	
	private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	
	@Override
	public List<UserBean> getList() throws Exception {
		
		BasicConfigurator.configure();
		UserDaoImpl userdao = new UserDaoImpl();
		
		ResultSet rs =  userdao.getListOfUsers();
		List<UserBean> list = new ArrayList<>();
		
		
		while(rs.next()) {
			UserBean user = new UserBean();
	        user.setUserid(rs.getInt("userid"));
	        user.setFname(rs.getString("fname"));
	        user.setLname(rs.getString("lname"));
	        user.setGender(rs.getString("gender"));
	        user.setDesignation(rs.getString("designation"));
	        user.setEmail(rs.getString("email"));
	        user.setPhone(rs.getString("phone"));
	        user.setDob(rs.getString("dob"));
	        user.setRole(rs.getString("role"));
	        
	        
//	        String encPass = rs.getString("password");
	        
//	        PasswordSecurity ps = new PasswordSecurity();
	        
//	        user.setPassword( ps.decrypt(encPass) );
	        list.add(user);
			
		}
		logger.info(list);
		
		return list;
		
	}
	
	
}
