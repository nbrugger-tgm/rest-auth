package com.niton.restauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.niton.restauth.model.LoginResponse;
import com.niton.util.Logging;
import com.niton.util.config.Config;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {
	@Autowired
	private MockMvc                  mockMvc;

	@BeforeAll
	static void setup(){
		Config.init("config.cfg");
		Logging.init("debug", "logging");
	}

	@Test
	@Order(2)
	void loginBruteForceProtection() throws Exception {
		this.mockMvc.perform(get("/login/someUser?password=wrong")).andExpect(status().isOk());
		this.mockMvc.perform(get("/login/someUser?password=wrong")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("result", equalTo("ON_COOLDOWN")));
		for(int i = 0;i<200;i++)
		    this.mockMvc.perform(get("/login/someUser?password=wrong")).andExpect(status().isOk());
		this.mockMvc.perform(get("/login/someUser?password=wrong")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("result", equalTo("BLOCKED")));
		this.mockMvc.perform(get("/login/otherUser?password=otherPassword")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("result", equalTo("BLOCKED")));
	}

	@Test
	@Order(1)
	void authenticatedOperation() throws Exception {
		this.mockMvc.perform(post("/register?password=1234&username=foo")).andExpect(status().isCreated());
		MvcResult response = this.mockMvc.perform(get("/login/foo?password=1234"))
		                                 .andDo(print())
		                                 .andExpect(status().isOk())
		                                 .andExpect(jsonPath("result", equalTo("VERIFIED")))
		                                 .andReturn();
		LoginResponse result = parseResponse(response,LoginResponse.class);
		String id = result.getId();
		this.mockMvc.perform(get("/foo/private_content").header("X-SESSION", id)).andExpect(status().isOk());
	}
	private static final ObjectMapper MAPPER = new ObjectMapper()
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule(new JavaTimeModule());

	public static String requestBody(Object request) {
		try {
			return MAPPER.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
		try {
			String contentAsString = result.getResponse().getContentAsString();
			return MAPPER.readValue(contentAsString, responseClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}