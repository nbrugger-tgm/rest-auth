package com.niton.restauth;

import com.niton.login.LoginResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
	private LoginResult result;
	private String id;
}
