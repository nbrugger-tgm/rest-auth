package com.niton.restauth;

import com.niton.jauth.AuthenticationHandler;
import com.niton.restauth.jpa.UserRepo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RestAuthenticationHandler implements AuthenticationHandler<User, HttpServletRequest> {

	private UserRepo bookRepository;

	public RestAuthenticationHandler(UserRepo bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void sendInitPassword(String password,User u) {
		System.out.println("Init password : "+password  +" for "+u.getName());
	}

	@Override
	public void persistAuthenticateable(User user, byte[] hash) {
		user.setHash(hash);
		bookRepository.save(user);
	}

	@Override
	public void deleteAuthenticateable(String id) {
		bookRepository.deleteById(id);
	}

	@Override
	public String getContextID(HttpServletRequest context) {
		return context.getRemoteAddr();
	}

	@Override
	public String getContextAuthInfo(HttpServletRequest context) {
		return context.getHeader("X-SESSION");
	}


	@Override
	public User getAuthenticateable(String u) {
		return bookRepository.getOne(u);
	}

	@Override
	public void onSessionIpPermaBan(String ip) {
		System.out.println("Ban IP : "+ip);
	}

	@Override
	public void onRapidSessionCheck(String ip, Integer integer) {
		System.out.println(ip+ " does rapid checks "+integer+" on");
	}

	@Override
	public void sendResetTokenMail(String mail, String toString) {
		System.out.println("Send token mail to "+mail+" with token "+toString);
	}

	@Override
	public boolean existsAuthenticatableById(String user) {
		return bookRepository.existsById(user);
	}

	@Override
	public byte[] getHash(String user) {
		return bookRepository.getOne(user).getHash();
	}

	@Override
	public void setHash(String key, byte[] hash) {
		User u = bookRepository.getOne(key);
		u.setHash(hash);
		bookRepository.saveAndFlush(u);
	}
}
