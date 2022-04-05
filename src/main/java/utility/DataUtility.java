package utility;

import java.util.Random;

public class DataUtility {

	
	 public static boolean isName(String val) {
		    String name = "^[A-Za-z ]*$";
		    if (val.matches(name)) {
		      return true;
		    } else {
		      return false;
		    }
		  }
	 
	 public static boolean isPassword(String val) {
		    //String passregex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$";
		    String passregex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
		    if (val.matches(passregex)) {
		      return true;
		    } else {
		      return false;
		    }
		  }
	 
	 public static boolean isPhoneNo(String val) {
		    String regex = "^[7-9][0-9]{9}$";
		    if (val.matches(regex)) {
		      return true;
		    } else {
		      return false;
		    }
		  }
	 public static boolean isNull(String val) {
		    if (val == null || val.trim().length() == 0) {
		      return true;
		    } else {
		      return false;
		    }
		  }
	 
	 public static boolean isEmail(String val) {
//		    String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		 String emailreg = "^[A-Za-z0-9+_.-]+@(.+)$";
		 if (!isNull(val)) {
		      try {
		        return val.matches(emailreg);
		      } catch (NumberFormatException e) {
		        return false;
		      }
		    } else {
		      return false;
		    }
	 }
	 
	 public static String generateOTP() {
		 Random random = new Random();
		 int otp = random.nextInt(999999);
		 return String.format("%6d",otp);
	 }
	 
}
