package com.clipicker.me.domain.okta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OktaProfile {

	@JsonProperty
	private String firstName;
	@JsonProperty
	private String lastName;
	@JsonProperty
	private String email;
	@JsonProperty
	private String login;

	public OktaProfile() {
	}

	public OktaProfile(String firstName, String lastName, String email, String login) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.login = login;
	}

	@Override
	public String toString() {
		return "OktaProfile [" +
			"firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", email='" + email + '\'' +
			", login='" + login + '\'' +
			']';
	}
}
