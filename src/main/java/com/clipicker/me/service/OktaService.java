package com.clipicker.me.service;

import com.clipicker.me.domain.User;
import com.clipicker.me.domain.okta.OktaRegistrationResponse;
import java.io.IOException;

public interface OktaService {

	OktaRegistrationResponse register(User user) throws IOException;
}
