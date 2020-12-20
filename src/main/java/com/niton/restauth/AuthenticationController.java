package com.niton.restauth;

import com.niton.jauth.AccountManager;
import com.niton.login.LoginResult;
import com.niton.restauth.jpa.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

	public  AccountManager<User, HttpServletRequest> handler;

	public AuthenticationController(@Autowired UserRepo repos) {
		handler = new AccountManager<>(new RestAuthenticationHandler(repos));
	}


	@GetMapping("login/{username}")
	public LoginResponse login(@RequestParam String password, @PathVariable String username, HttpServletRequest request){
		LoginResult res = handler.authenticate(username,password,request.getRemoteAddr());
		LoginResponse response = new LoginResponse(res, null);
		if(res.success)
			response.setId(handler.getID(username));
		return response;
	}

	@PostMapping("register")
	public String register(@RequestParam(required = false) String password,@RequestParam String username){
		handler.addAuthenticateable(new User(username), password);
		return handler.getID(username);
	}
	@GetMapping("{username}/private_content")
	@ResponseBody
	public ResponseEntity<String> authenticatedOperation(@PathVariable String username, HttpServletRequest request){
		User u = handler.getAuthentication(request);
		if(u == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		if(!u.getName().equals(username))
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(u.getPrivateText(),HttpStatus.ACCEPTED);
	}

}
