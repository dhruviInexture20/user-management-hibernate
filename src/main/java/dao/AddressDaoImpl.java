package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import bean.AddressBean;
import utility.DBConnection;

public class AddressDaoImpl implements AddressDao {

	static final String addAddressQuery = "insert into user_address"
			+ "(userid, street_address, city, state, postal_code, country)" + "values(?,?,?,?,?,?)";

	@Override
	public void addAddress(List<AddressBean> list, int userid) {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt = null;
		int count = 0;
		try {

			stmt = conn.prepareStatement(addAddressQuery);
			while (count < list.size()) {
				AddressBean userAddress = list.get(count);
				stmt.setInt(1, userid);
				stmt.setString(2, userAddress.getStreetAddress());
				stmt.setString(3, userAddress.getCity());
				stmt.setString(4, userAddress.getState());
				stmt.setString(5, userAddress.getPostalCode());
				stmt.setString(6, userAddress.getCountry());
				stmt.addBatch();
				count++;
			}
			stmt.executeBatch();
		} catch (SQLException e) {

		}
	}
}
