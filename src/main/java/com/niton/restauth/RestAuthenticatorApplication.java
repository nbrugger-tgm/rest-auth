package com.niton.restauth;

import com.niton.util.Logging;
import com.niton.util.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestAuthenticatorApplication {

	public static void main(String[] args) {
		Config.init("config.cfg");
		Logging.init("WebService","logging");
		SpringApplication.run(RestAuthenticatorApplication.class, args);
	}

}
