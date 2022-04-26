package bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import utility.PasswordSecurity;


@Entity
@Table(name="user")
public class UserBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid;
	private String fname;
	private String lname;
	
	@Column(unique = true)
	private String email;
	private String phone;
	private String password;
	private String dob;
	private String designation;
	private String gender;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid_address")
	@Cascade(CascadeType.ALL)
	private List<AddressBean> addressList;
	private String s_question;
	private String s_answer;
	
//	@ColumnDefault("user")
	private String role = "user";

	private String base64Image;

	
	public String getBase64Image() {
		return base64Image;
	}
	
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
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
//		return addressList;
		return new ArrayList<AddressBean>(this.addressList);
	}
	public void setAddressList(List<AddressBean> addressList) {
		this.addressList = new ArrayList<AddressBean>(addressList);
//		this.addressList = addressList;
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
		return ps.encrypt(this.password);
	}
	
	// convert encrypted password to normal form and set password
	public void setPasswordString(String encPass) throws Exception {
		PasswordSecurity ps = new PasswordSecurity();
		setPassword(ps.decrypt(encPass));
	}

	public String getS_question() {
		return s_question;
	}

	public void setS_question(String s_question) {
		this.s_question = s_question;
	}

	public String getS_answer() {
		return s_answer;
	}

	public void setS_answer(String s_answer) {
		this.s_answer = s_answer;
	}
}
