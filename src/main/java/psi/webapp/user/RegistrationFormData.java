package psi.webapp.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationFormData {

	private final String login;
	private final String password;
	private final String repeatedPassword;
}
