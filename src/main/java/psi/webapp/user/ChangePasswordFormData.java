package psi.webapp.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordFormData {

	private final Long userId;
	private final String oldPassword;
	private final String newPassword;
	private final String repeatedPassword;
}
