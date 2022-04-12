package utility;

import java.util.Random;

public class DataUtility {

	private static final Random RANDOM = new Random();
	 public static boolean isName(String val) {
		    String name = "^[A-Za-z ]*$";
		    if (val.matches(name)) {
		      return true;
		    } else {
		      return false;
		    }
		  }
	 
	 public static boolean isPassword(String val) {
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
		 String emailreg = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
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
		
		 int otp = RANDOM.nextInt(999999);
		 return String.format("%6d",otp);
	 }
	 
}
