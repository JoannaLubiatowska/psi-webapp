package psi.webapp;

import org.apache.commons.codec.digest.DigestUtils;

public class StringUtils {

	public static String encrypt(String text) {
		return DigestUtils.sha256Hex(text);
	}
	
}
