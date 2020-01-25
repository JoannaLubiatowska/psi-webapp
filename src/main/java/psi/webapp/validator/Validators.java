package psi.webapp.validator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import lombok.Getter;
import psi.webapp.entity.AppDatabase;
import psi.webapp.user.RegistrationFormData;

public class Validators {
	@Getter
	private static final Collection<Validator<RegistrationFormData>> getRegistrationValidators = Collections.unmodifiableCollection(Arrays.asList(prepareUserWithLoginExistsValidator(), preparePasswordsEqualsValidator(), preparePasswordComplexityValidator()));
	

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
			//                                       (*)          (**)        (***)              (****)
			boolean correct = password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$");
			String message = correct ? null : "Hasło musi mieć: min. 6 znaków, min. jedną literę, min. jedną cyfrę oraz min. 1 znak specjalny (@$!%*#?&)";
			return new ValidationResult(correct, message);
		};
	}
}
