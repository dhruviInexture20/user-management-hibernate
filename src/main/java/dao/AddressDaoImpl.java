package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bean.AddressBean;
import utility.DBConnection;

public class AddressDaoImpl implements AddressDao {

	private static Logger logger = Logger.getLogger(AddressDaoImpl.class);
	static final String addAddressQuery = "insert into user_address"
			+ "(userid, street_address, city, state, postal_code, country)" + "values(?,?,?,?,?,?)";
	static final String getUserAddressQ = "select * from user_address where userid=?";
	static final String deleteAddressByIdQuery = "delete from user_address where addressid=?";
	static final String updateAddressQuery = "update user_address set street_address=?, city=?, state=?, postal_code=?, country=? where addressid=?";

	@Override
	public void addAddressList(List<AddressBean> list, int userid) {
		
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
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
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

			while (rs.next()) {
				userAddress = new AddressBean();
//				userAddress.setUserid(rs.getInt("userid"));
				userAddress.setAddressid(rs.getInt("addressid"));
				userAddress.setStreetAddress(rs.getString("street_address"));
				userAddress.setState(rs.getString("state"));
				userAddress.setCountry(rs.getString("country"));
				userAddress.setCity(rs.getString("city"));
				userAddress.setPostalCode(rs.getString("postal_code"));

				addressList.add(userAddress);
			}
			return addressList;

		} catch (SQLException e) {
			logger.error(e);
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}

		return null;
	}

	@Override
	public void deleteAddressById(Integer addressid) {

		
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(deleteAddressByIdQuery);
			stmt.setInt(1, addressid);
			stmt.executeUpdate();
			logger.info("delete " + addressid);

		} catch (SQLException e) {
			logger.info(e);
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
	}

	@Override
	public void addAddress(AddressBean address, int userid) {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(addAddressQuery);
			stmt.setInt(1, userid);
			stmt.setString(2, address.getStreetAddress());
			stmt.setString(3, address.getCity());
			stmt.setString(4, address.getState());
			stmt.setString(5, address.getPostalCode());
			stmt.setString(6, address.getCountry());
			stmt.execute();
			logger.info("add address" + userid + "userid");

		} catch (SQLException e) {
			logger.info(e);
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		}

	@Override
	public void updateAddress(AddressBean address) {

		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(updateAddressQuery);

			stmt.setString(1, address.getStreetAddress());
			stmt.setString(2, address.getCity());
			stmt.setString(3, address.getState());
			stmt.setString(4, address.getPostalCode());
			stmt.setString(5, address.getCountry());
			stmt.setInt(6, address.getAddressid());
			stmt.executeUpdate();
			logger.info("user address updated");

		} catch (SQLException e) {
			logger.info(e);
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}

	}
}
