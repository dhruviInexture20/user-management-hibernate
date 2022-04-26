package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.AddressBean;
import bean.UserBean;
import service.AdminService;
import service.UserService;
import service.UserServiceImpl;

/**
 * Servlet implementation class GetUserAddressServlet
 */
public class GetUserAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO get user address
		
		response.setContentType("text/html");
		PrintWriter out =  response.getWriter();
		HttpSession session = request.getSession(false);
		
		UserService service = new UserServiceImpl();
		String role = (String) session.getAttribute("role");
		List<AddressBean> addressList = new ArrayList<AddressBean>();
		
		if( role.equals("admin")) {
			// with userid
			String useridString = request.getParameter("userid");
			int userid = Integer.parseInt(useridString);
			UserBean user = service.getUserForEdit(userid);
			
			addressList = user.getAddressList();
//			addressList = service.getUserAddress(userid);
			
		}
		else if (role.equals("user")) {
			UserBean user =(UserBean) session.getAttribute("userData");
			addressList = user.getAddressList();
			
		}
		
		
		
		
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		out.print(gson.toJsonTree(addressList));
	}

}
