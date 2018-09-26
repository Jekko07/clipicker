package com.clipicker.me.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

	private Okta okta;

	public Okta getOkta() {
		return okta;
	}

	public void setOkta(Okta okta) {
		this.okta = okta;
	}
}
