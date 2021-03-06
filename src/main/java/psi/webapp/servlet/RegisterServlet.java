package psi.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import psi.webapp.user.RegisterUserException;
import psi.webapp.user.RegistrationFormData;
import psi.webapp.user.UserService;

@WebServlet({ "/register" })
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = -5430285426354417271L;

	private final UserService userService = UserService.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RegistrationFormData formData = fillFormData(request);
			userService.registerNewUser(formData);
			request.setAttribute("message", "Nowy użytkownik został zarejestrowany. Teraz można się zalogować.");
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (RegisterUserException e) {
			request.setAttribute("message", e.getMessage());
			getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}

	private RegistrationFormData fillFormData(HttpServletRequest request) {
		return RegistrationFormData.builder().login(request.getParameter("login"))
				.password(request.getParameter("password")).repeatedPassword(request.getParameter("rp-password"))
				.build();
	}

}
