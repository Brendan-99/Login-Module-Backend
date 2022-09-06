package org.loginmodule.springboot.web;

import javax.validation.Valid;

import org.loginmodule.springboot.domain.Users;
import org.loginmodule.springboot.service.MapValidationErrorService;
import org.loginmodule.springboot.service.UserService;
import org.loginmodule.springboot.utility.CustomToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/users")
@CrossOrigin
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/register")
	public ResponseEntity<?> createNewUser(@Valid@RequestBody Users users,BindingResult result){
		ResponseEntity<?> errormap =mapValidationErrorService.mapValidationError(result);
		if(errormap!=null) return errormap;
		String token=userService.registerUser(users);
		return new ResponseEntity<String>(token,HttpStatus.CREATED);
	}

	@PostMapping("/validate")
	public ResponseEntity<?> validateUser(@RequestBody Users users){
		System.out.println(users.getUsername()+users.getPassword());
		return new ResponseEntity<CustomToken>(userService.validateUser(users.getUsername(), users.getPassword()),HttpStatus.OK);
	}
}
