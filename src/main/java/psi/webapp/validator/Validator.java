package psi.webapp.validator;

@FunctionalInterface
public interface Validator<T> {

	ValidationResult validate(T obj);
}
