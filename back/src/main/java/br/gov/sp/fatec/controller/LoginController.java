package br.gov.sp.fatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.sp.fatec.security.JwtUtils;
import br.gov.sp.fatec.security.Login;

@RestController
@RequestMapping(value = "login")
@CrossOrigin
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping()
	public ResponseEntity<Object> authenticate(@RequestBody Login login) throws JsonProcessingException {
		Authentication auth = new UsernamePasswordAuthenticationToken(
			login.getUsername(), 
			login.getPassword()
		);
		
		try {
			auth = authManager.authenticate(auth);
			
			login.setPassword(null);
			login.setAuthority(
					auth.getAuthorities()
					.iterator()
					.next()
					.getAuthority()
					);
			
			login.setToken(JwtUtils.generateToken(auth));
			return ResponseEntity.ok(login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha incorretos");
	}
}
