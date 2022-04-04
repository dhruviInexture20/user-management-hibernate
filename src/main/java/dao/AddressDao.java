package dao;

import java.util.List;

import bean.AddressBean;

public interface AddressDao {
	void addAddressList(List<AddressBean> list, int userid);

	List<AddressBean> getAddress(int int1);

	void deleteAddressById(Integer integer);

	void addAddress(AddressBean address, int userid);

	void updateAddress(AddressBean address);

}
