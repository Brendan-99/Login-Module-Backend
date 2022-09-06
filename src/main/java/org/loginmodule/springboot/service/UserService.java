package org.loginmodule.springboot.service;

import org.loginmodule.springboot.domain.Users;
import org.loginmodule.springboot.utility.CustomToken;

public interface UserService {
	public String registerUser(Users user);
	public CustomToken validateUser(String username,String password);
}
