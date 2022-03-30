package utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtility {

	static String Error = "Invalid data";
	static String SuccessMessage = "Done";

	public static void setErrorMessage(String msg, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(Error, msg);
	}

	public static String getErrorMessage(HttpServletRequest request, HttpServletResponse response) {
		String val = (String) request.getAttribute(Error);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		request.setAttribute(SuccessMessage, msg);
	}

	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(SuccessMessage);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

}
