package bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="address")
public class AddressBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addressid;
	
//	private int userid;
	private String streetAddress;
	private String city;
	private String country;
	private String state;
	private String postalCode;
	
//	@ManyToOne
//	private UserBean user;
	
	public int getAddressid() {
		return addressid;
	}
	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}
//	public int getUserid() {
//		return userid;
//	}
//	public void setUserid(int userid) {
//		this.userid = userid;
//	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
