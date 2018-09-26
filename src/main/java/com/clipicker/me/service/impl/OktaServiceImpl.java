package com.clipicker.me.service.impl;

import com.clipicker.me.config.ApplicationConfig;
import com.clipicker.me.domain.User;
import com.clipicker.me.domain.okta.OktaRegistrationResponse;
import com.clipicker.me.exception.ValidateFailedException;
import com.clipicker.me.httpclient.HttpClientRequest;
import com.clipicker.me.service.OktaService;
import com.clipicker.me.utils.JSONUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("oktaService")
public class OktaServiceImpl implements OktaService {
	public static final String PREFIX_AUTHORIZATION_KEY = "SSWS ";
	public static final String APPLICATION_JSON = "application/json";

	@Autowired
	private ApplicationConfig config;
	public static final Logger LOGGER = LoggerFactory.getLogger(OktaServiceImpl.class);

	@Override
	public OktaRegistrationResponse register(User user) throws IOException {
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", PREFIX_AUTHORIZATION_KEY + config.getOkta().getApiToken());
		headers.put("Accept", APPLICATION_JSON);
		headers.put("Content-Type", APPLICATION_JSON);

		String jsonOktaUser = JSONUtil.toJson(user);
		String rawResult = HttpClientRequest.postJson(config.getOkta().getRegisterUrl(), jsonOktaUser, headers);
		LOGGER.info("Register new okta user: {},  response: {}", user, rawResult);
		OktaRegistrationResponse oktaResponse = JSONUtil.toObject(rawResult, OktaRegistrationResponse.class);
		if (oktaResponse == null || oktaResponse.getStatus() == null) {
			throw new ValidateFailedException("Email already registered!");
		}

		return oktaResponse;
	}


}
