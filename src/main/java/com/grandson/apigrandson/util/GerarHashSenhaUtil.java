package com.grandson.apigrandson.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarHashSenhaUtil {

	public static String gerarHash(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
	
	public static boolean isPasswordMatch(String senha, String senhaEncode) {
		return new BCryptPasswordEncoder().matches(senha, senhaEncode);
	}
}
