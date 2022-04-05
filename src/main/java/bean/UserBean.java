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
	private String s_question;
	private String s_answer;
	private String role;
	private String base64Image;
	//private byte[] image;

//	public byte[] getImage() {
//		return this.image;
//	}
//	
	
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
	
//	public Date getDobObj() throws ParseException {
////		String sDate1="31/12/1998";  
//	    Date date1=new SimpleDateFormat("yyyy-mm-dd").parse(dob);  
//	    System.out.println(date1 + "parse");
//	    return date1;
//	}
}
