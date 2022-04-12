package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import service.UserService;
import service.UserServiceImpl;

public class ForgetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ForgetPasswordServlet.class);
	
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BasicConfigurator.configure();
		String email = request.getParameter("email").trim();
		String security_question = request.getParameter("security_question");
		String security_answer = request.getParameter("security_answer");
		
		logger.info("email = " + email);
		logger.info("question = " + security_question);
		logger.info("answer = " + security_answer);
		
		UserService userService = new UserServiceImpl();
		String msg = userService.authUserForgetPass(email, security_question, security_answer);
		
		if(msg == null) {
			logger.info("authenticated user");
			
			// send mail with otp
			String otp =  userService.sendOTPMail(email);
			
			userService.saveOTP(email, otp);
			
			request.setAttribute("email", email);
//			response.sendRedirect("enterOtp.jsp");
			request.getRequestDispatcher("enterOtp.jsp").forward(request, response);
		}
		else {
			logger.info("wrong user");
			logger.info(msg);
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("forgetpassword.jsp").forward(request, response);
			
		}
		
		
//		EmailMessageBean emailbean = new EmailMessageBean();
//		
//		
//		// get OTP
//		
//		emailbean.setTo(email);
//		emailbean.setMessage("Hii this is test mail");
//		
//		try {
//
//			EmailUtility emailutil = new EmailUtility();
//			emailutil.sendMail(emailbean);
//			ServletUtility.setSuccessMessage("Mail has been sent successfully..", request);
//
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			
//		}
//		// forward
//		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

}
