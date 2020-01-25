package psi.webapp.user;

import lombok.Data;

@Data
public class UserPasswordsHistoryEntry {
	
	private final String passwordHash;
	private final Long passNo;
	private final Long currentPassNo;
}
