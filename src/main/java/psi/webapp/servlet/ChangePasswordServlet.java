package psi.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import psi.webapp.entity.User;
import psi.webapp.user.ChangePasswordFormData;
import psi.webapp.user.UserService;

@WebServlet({ "/changePass" })
public class ChangePasswordServlet extends HttpServlet {

	private static final long serialVersionUID = -609980002103243226L;

	private final UserService userService = UserService.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ChangePasswordFormData formData = fillFormData(request);
			userService.changeUserPassword(formData);
			request.setAttribute("message", "Hasło zostało zmienione.");
			getServletContext().getRequestDispatcher("/").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("message", e.getMessage());
			getServletContext().getRequestDispatcher("/changePass.jsp").forward(request, response);
		}
	}

	private ChangePasswordFormData fillFormData(HttpServletRequest request) {
		return ChangePasswordFormData.builder().userId(((User) request.getSession().getAttribute("loggedUser")).getId())
				.oldPassword(request.getParameter("old-password")).newPassword(request.getParameter("new-password"))
				.repeatedPassword(request.getParameter("rp-new-password")).build();
	}
}
