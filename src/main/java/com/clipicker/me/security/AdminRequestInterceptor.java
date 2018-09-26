package com.clipicker.me.security;

import com.clipicker.me.exception.ValidateFailedException;
import com.clipicker.me.service.ClinicService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AdminRequestInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ClinicService clinicService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) throws Exception {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if (clinicService.loadClinic(email).getIsAdmin().intValue() != 1) {
			throw new ValidateFailedException("Unauthorized to access admin api.");
		}

		return super.preHandle(request, response, handler);
	}
}
