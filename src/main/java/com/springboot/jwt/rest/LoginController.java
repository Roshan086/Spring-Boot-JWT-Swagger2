package com.springboot.jwt.rest;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.jwt.configuration.Jwtutil;
import com.springboot.jwt.models.AuthenticationRequest;
import com.springboot.jwt.models.AuthenticationResponse;
import com.springboot.jwt.services.UserAuthenticationProvide;

@RestController
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserAuthenticationProvide userAuthenticationProvide;
	@Autowired
	private Jwtutil jwtTokenUtil;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST )
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new Exception("User Name or password is incorrect",e);
		}
		
		final UserDetails userDetails=userAuthenticationProvide.loadUserByUsername(authenticationRequest.getUserName());
		
		final String jwt=jwtTokenUtil.generateToken(userDetails);
		System.out.println("jwt=="+jwt);
		return new ResponseEntity<>(jwt,HttpStatus.OK);
	}
}
