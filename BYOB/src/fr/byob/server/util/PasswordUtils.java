package fr.byob.server.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public abstract class PasswordUtils {

	/**
	 * Digests the specified password using MD5 algorithm and produces an
	 * equivalent string
	 * 
	 * @param password
	 *            The original string to hash
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String hashPassword(String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = null;
		md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes("UTF-8"));
		byte raw[] = md.digest();
		String hash = (new BASE64Encoder()).encode(raw);
		return hash;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		System.out.println("=>" + PasswordUtils.hashPassword(args[0]) + "<=");
	}
}
