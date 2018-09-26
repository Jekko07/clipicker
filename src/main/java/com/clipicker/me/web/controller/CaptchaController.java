package com.clipicker.me.web.controller;

import static com.clipicker.me.utils.CaptchaUtils.CAPTCHA_KEY;

import com.clipicker.me.utils.CaptchaUtils;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

	@GetMapping(value = {"/generate", "/regenerate"})
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String captchaText = CaptchaUtils.generateText();
		request.getSession(true).setAttribute(CAPTCHA_KEY, captchaText);
		response.setHeader("Cache-Control", "private,no-cache,no-store");
		response.setContentType("image/png");
		response.getOutputStream().write(CaptchaUtils.generateImage(captchaText));
	}
}
