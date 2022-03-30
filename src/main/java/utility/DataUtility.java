package utility;


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
		    String passregex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
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
	 
}