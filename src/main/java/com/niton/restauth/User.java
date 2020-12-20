package com.niton.restauth;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@lombok.Setter
@lombok.Getter
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
public class User {
	@lombok.NonNull
	@Id
	private String name;
	private byte[] hash;
	private String privateText = "Something private";
}
