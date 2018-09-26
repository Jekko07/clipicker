package com.clipicker.me.web.form;

import javax.validation.constraints.NotNull;

public class DeleteClinicForm {

	@NotNull(message = "required email")
	private String email;

	public DeleteClinicForm() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
