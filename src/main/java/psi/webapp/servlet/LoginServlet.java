package psi.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import psi.webapp.entity.User;
import psi.webapp.user.LoginFormData;
import psi.webapp.user.UserNotFoundException;
import psi.webapp.user.UserService;

@WebServlet({ "/login" })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 7141875761729407260L;
	
	private final UserService userService = UserService.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			LoginFormData formData = fillFormData(request);
			User user = userService.logIn(formData);
			HttpSession session = request.getSession(true);
			session.setAttribute("loggedUser", user);
			response.sendRedirect(".");
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof UserNotFoundException) ? "Niepoprawny login i/lub hasło." : e.getMessage());
			getServletContext().getRequestDispatcher("/").forward(request, response);
		}
	}

	private LoginFormData fillFormData(HttpServletRequest request) {
		return new LoginFormData(request.getParameter("login").toString(), request.getParameter("password").toString());
	}
	
	
}
