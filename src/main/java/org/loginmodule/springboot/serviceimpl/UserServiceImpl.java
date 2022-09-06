package org.loginmodule.springboot.serviceimpl;

import org.loginmodule.springboot.configuration.JwtTokenProvider;
import org.loginmodule.springboot.domain.Users;
import org.loginmodule.springboot.exception.EmailException;
import org.loginmodule.springboot.repository.UserRepository;
import org.loginmodule.springboot.service.UserService;
import org.loginmodule.springboot.utility.CustomToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	public String registerUser(Users users) {
		try {

			users.setEmail(users.getEmail().toUpperCase());
			users.setPassword(passwordEncoder.encode(users.getPassword()));
			Users user = userRepository.save(users);
			String token = jwtTokenProvider.createToken(user.getUsername());
			return token;
		}
		catch(Exception e) {
			System.out.println(e);
			throw new EmailException("Email "+users.getEmail().toUpperCase()+" already exists");
			
		}	
	}

	@Override
	public CustomToken validateUser(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			String token = jwtTokenProvider.createToken(username);
			CustomToken toke= new CustomToken();
			toke.setToken(token);
			return toke;
		}catch(Exception e){
			System.out.println(e);
			throw new EmailException("validation failed");
		}
		
	}
}
