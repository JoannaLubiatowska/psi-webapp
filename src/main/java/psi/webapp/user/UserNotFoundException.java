package psi.webapp.user;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(Exception e) {
		super(e);
	}

	public UserNotFoundException() {
	}

	private static final long serialVersionUID = 1874720029030592944L;

}
