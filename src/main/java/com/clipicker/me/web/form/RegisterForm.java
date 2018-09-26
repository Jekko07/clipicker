package com.clipicker.me.web.form;

import javax.validation.constraints.NotNull;

public class RegisterForm {

	@NotNull(message = "First Name must be filled up.")
	private String firstName;
	@NotNull(message = "Last Name must be filled up.")
	private String lastName;
	@NotNull(message = "Email must be filled up.")
	private String email;
	@NotNull(message = "Captcha code must be filled up.")
	private String captchaCode;


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
}
