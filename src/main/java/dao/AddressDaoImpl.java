package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bean.AddressBean;
import utility.DBConnection;

public class AddressDaoImpl implements AddressDao {

	private static final Logger logger = LogManager.getLogger(AddressDaoImpl.class);
	static final String addAddressQuery = "insert into user_address"
			+ "(userid, street_address, city, state, postal_code, country)" + "values(?,?,?,?,?,?)";
	static final String getUserAddressQ = "select * from user_address where userid=?";
	
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
			logger.error(e);
		}
	}

	@Override
	public List<AddressBean> getAddress(int userid) {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<AddressBean> addressList = new ArrayList<>();
		AddressBean userAddress = null;
		
		try {
			stmt = conn.prepareStatement(getUserAddressQ);
			stmt.setInt(1, userid);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				userAddress = new AddressBean();
				userAddress.setUserid(rs.getInt("userid"));
				userAddress.setAddressid(rs.getInt("addressid"));
				userAddress.setStreetAddress(rs.getString("street_address"));
				userAddress.setState(rs.getString("state"));
				userAddress.setCity(rs.getString("city"));
				userAddress.setPostalCode(rs.getString("postal_code"));
				
				addressList.add(userAddress);
			}
			
			return addressList;
			
		} catch (SQLException e) {
			logger.error(e);
		}
		
		
		return null;
	}
}
