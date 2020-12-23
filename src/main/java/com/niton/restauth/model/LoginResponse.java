package com.niton.restauth.model;

import com.niton.login.LoginResult;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class LoginResponse {
	private LoginResult result;
	private String id;
}
