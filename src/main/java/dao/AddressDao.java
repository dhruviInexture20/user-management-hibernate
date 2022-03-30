package dao;

import java.util.List;

import bean.AddressBean;

public interface AddressDao {
	void addAddress(List<AddressBean> list, int userid);
}
