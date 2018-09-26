package com.clipicker.me.domain.okta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OktaRegistrationResponse {

	@JsonProperty
	private String id;
	@JsonProperty
	private String status;
	@JsonProperty
	private OktaProfile profile;

	public OktaRegistrationResponse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OktaProfile getProfile() {
		return profile;
	}

	public void setProfile(OktaProfile profile) {
		this.profile = profile;
	}

}
