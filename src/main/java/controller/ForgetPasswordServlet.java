package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.EmailMessage;
import utility.EmailUtility;
import utility.ServletUtility;

/**
 * Servlet implementation class ForgetPasswordServlet
 */
public class ForgetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email").trim();
		EmailMessage emailbean = new EmailMessage();
		// UserBeans user = UserModel.FindUserPassword(email);
		emailbean.setTo(email);
		emailbean.setMessage("Hii this is test mail");
//		    emailbean.setMessage("Hii "+email+ "Your Password is: "+user.getPassword());
		try {

			EmailUtility.sendMail(emailbean);
			ServletUtility.setSuccessMessage("Mail has been sent successfully..", request);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			ServletUtility.setErrorMessage("Somting Wrong", request);
		}
		// forward
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

}
