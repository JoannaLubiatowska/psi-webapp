package psi.webapp.register;

import psi.webapp.StringUtils;
import psi.webapp.entity.AppDatabase;
import psi.webapp.entity.User;

public class RegisterService {

	private static RegisterService instance;

	private final AppDatabase database = AppDatabase.getInstance();

	public static RegisterService getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance = new RegisterService();
	}

	private RegisterService() {
	}

	public void register(RegistrationFormData formData) throws RegisterUserException {
		User user = User.builder().login(formData.getLogin()).password(StringUtils.encode(formData.getPassword()))
				.build();
		try {
			database.save(user);
		} catch (Exception e) {
			throw new RegisterUserException(e.getMessage());
		}
	}
}
