package psi.webapp.user;

import java.sql.SQLException;

import psi.webapp.StringUtils;
import psi.webapp.entity.AppDatabase;
import psi.webapp.entity.User;

public class UserService {

	private static UserService instance;

	private final AppDatabase database = AppDatabase.getInstance();

	public static UserService getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance = new UserService();
	}

	private UserService() {
	}

	public void registerNewUser(RegistrationFormData formData) throws RegisterUserException {
		User user = User.builder().login(formData.getLogin()).password(StringUtils.encrypt(formData.getPassword()))
				.build();
		try {
			database.save(user);
		} catch (Exception e) {
			throw new RegisterUserException(e.getMessage());
		}
	}

	public User logIn(LoginFormData formData) throws UserNotFoundException, ClassNotFoundException {
		try {
			return database.getUserByCredentials(formData.getLogin(), StringUtils.encrypt(formData.getPassword()));
		} catch (SQLException e) {
			throw new UserNotFoundException(e);
		}
	}

	public void changeUserPassword(ChangePasswordFormData formData) throws ClassNotFoundException, UserNotFoundException, SQLException {
		User user = database.getUserById(formData.getUserId());
		user.setPassword(StringUtils.encrypt(formData.getNewPassword()));
		database.update(user);
	}
}
