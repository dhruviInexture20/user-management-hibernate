package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserService;
import service.UserServiceImpl;

/**
 * Servlet implementation class VerifyOTPServlet
 */
public class VerifyOTPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
//		HttpSession session = request.getSession(false);
//		String otp =(String) session.getAttribute("otp");
		
		String email = request.getParameter("email");
//		String otp = request.getParameter("hidden_otp");
		int otpFromUser = Integer.parseInt(request.getParameter("otp"));
		
		UserService userService = new UserServiceImpl();
		int otp = userService.getOtpByEmail(email);
		request.setAttribute("email", email);
		
		
		if(otpFromUser == otp) {
			
//			response.sendRedirect("resetpassword.jsp");
			
			request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
			
		}
		else {
			// wrong otp
//			request.setAttribute("email", email);
//			request.setAttribute("otp", otp);
			request.setAttribute("msg", "wrong OTP");
			request.getRequestDispatcher("enterOtp.jsp").forward(request, response);
		}
		
	}

}
