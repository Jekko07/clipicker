package com.clipicker.me.domain;

import com.clipicker.me.domain.okta.OktaProfile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	@JsonProperty
	private OktaProfile profile;

	public OktaProfile getProfile() {
		return profile;
	}

	public void setProfile(OktaProfile profile) {
		this.profile = profile;
	}

	public static User createOktaProfile(String firstName, String lastName, String email, String login) {
		User user = new User();
		user.setProfile(new OktaProfile(firstName, lastName, email, login));
		return user;
	}

	@Override
	public String toString() {
		return "User [" +
			"profile=" + profile +
			']';
	}
}
