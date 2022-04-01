package bean;

import java.io.InputStream;
import java.util.List;

import utility.PasswordSecurity;


public class UserBean {
	private int userid;
	private String fname;
	private String lname;
	private String email;
	private String phone;
	private String password;
	private String dob;
	private String designation;
	private String gender;
	private InputStream profilepic;
	private List<AddressBean> addressList;
	private String role;
	
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public InputStream getProfilepic() {
		return profilepic;
	}
	public void setProfilepic(InputStream inputStream) {
		this.profilepic = inputStream;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getDob() {
		return dob;
	}
	public void setDob(String birthdate) {
		this.dob = birthdate;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<AddressBean> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressBean> addressList) {
		this.addressList = addressList;
	}
	public String getRole() {
		return role;
		
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	// return normal password
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	// return password in encrypted form
	public String getProtectedPassword() throws Exception {
		PasswordSecurity ps = new PasswordSecurity();
		return ps.decrypt(this.password);
	}
	
	// convert encrypted password to normal form and set password
	public void setPasswordString(String encPass) throws Exception {
		PasswordSecurity ps = new PasswordSecurity();
		setPassword(ps.decrypt(encPass));
	}
	
	
}
