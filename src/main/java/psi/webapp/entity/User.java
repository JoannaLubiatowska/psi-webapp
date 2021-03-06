package psi.webapp.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

	private Long id;
	private String login;
	private String password;
	private Long correct;
	private Long incorrect;
}
