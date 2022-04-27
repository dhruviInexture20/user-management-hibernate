package service;

import java.util.List;

import bean.UserBean;

import org.apache.log4j.BasicConfigurator;
import dao.UserDaoImpl;

public class AdminServiceImpl implements AdminService {
	
	
	
	@Override
	public List<UserBean> getList() throws Exception {
		
		BasicConfigurator.configure();
		UserDaoImpl userdao = new UserDaoImpl();
		
		List<UserBean> list = userdao.getListOfUsers();
		
		return list;
		
	}
	
}
