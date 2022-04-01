package service;

import java.util.List;

import bean.UserBean;

public interface AdminService {

	List<UserBean> getList() throws Exception;

}
