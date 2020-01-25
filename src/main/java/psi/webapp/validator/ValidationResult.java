package psi.webapp.validator;

import lombok.Data;

@Data
public class ValidationResult {
	
	private final boolean correct;
	private final String description;
}
