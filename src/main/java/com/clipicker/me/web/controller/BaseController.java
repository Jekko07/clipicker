package com.clipicker.me.web.controller;

import com.clipicker.me.exception.ValidateFailedException;
import com.clipicker.me.web.model.AjaxResult;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController {

	@ExceptionHandler(ValidateFailedException.class)
	public @ResponseBody
	Object validateFailed(ValidateFailedException e, HttpServletRequest request) {
		return AjaxResult.failed(e.getMessage());
	}
}
