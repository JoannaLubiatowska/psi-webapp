package psi.webapp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtils {

	public static String encode(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
			text = new String(hash);
		} catch (NoSuchAlgorithmException ignored) {
		}
		return text;
	}
	
}
