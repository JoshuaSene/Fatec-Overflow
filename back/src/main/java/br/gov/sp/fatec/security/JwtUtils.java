package br.gov.sp.fatec.security;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
	
	private static final String KEY = "bac556d7a982bd8ab042de921e57a8d6";

	public static String generateToken(Authentication login)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Login newLogin = new Login();
		newLogin.setUsername(login.getName());
		
		if(!login.getAuthorities().isEmpty()) {
			newLogin.setAuthority(
				login.getAuthorities()
					.iterator()
						.next()
					.getAuthority()
			);
		}
		
		String userJson = mapper.writeValueAsString(newLogin);
		Date now = new Date();
		Long hour = 1000L * 60L * 60L;
		
		return Jwts.builder().claim("userDetails", userJson)
				.setIssuer("br.gov.sp.fatec")
				.setSubject(login.getName())
				.setExpiration(new Date(now.getTime() + hour))
				.signWith(SignatureAlgorithm.HS512, KEY)
				.compact();
	}
	
	public static Authentication parseToken(String token) 
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String credentialsJson = Jwts.parser()
				.setSigningKey(KEY)
				.parseClaimsJws(token)
				.getBody()
				.get("userDetails", String.class);
		Login login = mapper.readValue(credentialsJson, Login.class);
		
		UserDetails userDetails = User.builder()
				.username(login.getUsername())
				.password("passwd")
				.authorities(login.getAuthority())
				.build();
		return new UsernamePasswordAuthenticationToken(
				login.getUsername(), 
				login.getPassword(), 
				userDetails.getAuthorities() 
			);
	}
}
