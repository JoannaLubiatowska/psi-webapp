package psi.webapp.validator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import lombok.Getter;
import psi.webapp.StringUtils;
import psi.webapp.entity.AppDatabase;
import psi.webapp.user.ChangePasswordFormData;
import psi.webapp.user.RegistrationFormData;
import psi.webapp.user.UserPasswordsHistoryEntry;

public class Validators {
	@Getter
	private static final Collection<Validator<RegistrationFormData>> registrationValidators = Collections
			.unmodifiableCollection(Arrays.asList(prepareUserWithLoginExistsValidator(),
					preparePasswordsEqualsValidator(), preparePasswordComplexityValidator()));
	
	@Getter
	private static final Collection<Validator<ChangePasswordFormData>> changePasswordValidators = Collections
			.unmodifiableCollection(Arrays.asList(preprareOldPasswordCorrectValidator(), preparePasswordsEqualsForChangeValidator(),
					preparePasswordComplexityForChangeValidator(), preparePasswordSimilarThenLastForChangeValidator()));

	private static Validator<RegistrationFormData> prepareUserWithLoginExistsValidator() {
		return formData -> {
			boolean correct;
			String message;
			try {
				correct = !AppDatabase.getInstance().isUserWithLogin(formData.getLogin());
				message = correct ? null : "Użytkownik o podanej nazwie obecnie istnieje";
			} catch (Exception e) {
				correct = false;
				message = e.getMessage();
			}
			return new ValidationResult(correct, message);
		};
	}

	private static Validator<RegistrationFormData> preparePasswordsEqualsValidator() {
		return formData -> {
			boolean correct = formData.getPassword().equals(formData.getRepeatedPassword());
			String message = correct ? null : "Podane hasła nie są identyczne";
			return new ValidationResult(correct, message);
		};
	}

	private static Validator<RegistrationFormData> preparePasswordComplexityValidator() {
		return formData -> {
			String password = formData.getPassword();
			// (*) - zawiera przynajmniej jedną literę
			// (**) - zawiera przynajmniej jedną cyfrę
			// (***) - zawiera przynajmniej jeden znak specjalny (@$!%*#?&)
			// (****) - ma przynajmniej 6 cyfr
			// --------------------------------------(*)----------(**)---------(***)--------------(****)----------
			boolean correct = password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$");
			String message = correct ? null
					: "Hasło musi mieć: min. 6 znaków, min. jedną literę, min. jedną cyfrę oraz min. 1 znak specjalny (@$!%*#?&)";
			return new ValidationResult(correct, message);
		};
	}

	private static Validator<ChangePasswordFormData> preprareOldPasswordCorrectValidator() {
		return formData -> {
			boolean correct;
			String message;
			try {
				correct = AppDatabase.getInstance().hasUserSpecifiedPassword(formData.getUserId(), StringUtils.encrypt(formData.getOldPassword()));
				message = correct ? null : "Podano niepoprawne obecne hasło";
				
			} catch (Exception e) {
				correct = false;
				message = e.getMessage();
			}
			return new ValidationResult(correct, message);
		};
	}

	private static Validator<ChangePasswordFormData> preparePasswordsEqualsForChangeValidator() {
		return formData -> {
			boolean correct = formData.getNewPassword().equals(formData.getRepeatedPassword());
			String message = correct ? null : "Podane hasła nie są identyczne";
			return new ValidationResult(correct, message);
		};
	}

	private static Validator<ChangePasswordFormData> preparePasswordComplexityForChangeValidator() {
		return formData -> {
			String password = formData.getNewPassword();
			// (*) - zawiera przynajmniej jedną literę
			// (**) - zawiera przynajmniej jedną cyfrę
			// (***) - zawiera przynajmniej jeden znak specjalny (@$!%*#?&)
			// (****) - ma przynajmniej 6 cyfr
			// --------------------------------------(*)----------(**)---------(***)--------------(****)----------
			boolean correct = password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$");
			String message = correct ? null
					: "Hasło musi mieć: min. 6 znaków, min. jedną literę, min. jedną cyfrę oraz min. 1 znak specjalny (@$!%*#?&)";
			return new ValidationResult(correct, message);
		};
	}

	private static Validator<ChangePasswordFormData> preparePasswordSimilarThenLastForChangeValidator() {
		return formData -> {
			boolean correct;
			String message;
			try {
				UserPasswordsHistoryEntry entry = AppDatabase.getInstance().getComparisonWithEarlierUserPasswords(formData.getUserId(), StringUtils.encrypt(formData.getNewPassword()));
				correct = (entry.getCurrentPassNo() - entry.getPassNo() >= 5) || (entry.getPassNo() == 0);
				message = correct ? null : "Wprowadzone hasło występuje wśród 5 ostatnio użytych i nie może być teraz użyte";
			} catch (Exception e) {
				correct = false;
				message = e.getMessage();
			}
			return new ValidationResult(correct, message);
		};
	}
}
