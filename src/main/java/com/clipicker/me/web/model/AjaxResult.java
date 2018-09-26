package com.clipicker.me.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;

@JsonInclude(Include.NON_NULL)
public class AjaxResult {
	private String message;
	private Object data;
	private boolean success;
	private static final Logger LOGGER = LoggerFactory.getLogger(AjaxResult.class);

	public AjaxResult(boolean success) {
		this.success = success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public boolean isSuccess() {
		return success;
	}

	public static final AjaxResult success() {
		AjaxResult result = new AjaxResult(true);
		result.success = true;
		return result;
	}

	public static final AjaxResult success(Object data) {
		AjaxResult result = new AjaxResult(true);
		result.setData(data);
		return result;
	}

	public static final AjaxResult success(String message) {
		AjaxResult result = new AjaxResult(true);
		result.setMessage(message);
		return result;
	}

	public static final AjaxResult failed(String message) {
		AjaxResult result = new AjaxResult(false);
		result.setMessage(message);
		return result;
	}

	public static final AjaxResult failed(Object data, String message) {
		AjaxResult result = new AjaxResult(false);
		result.setMessage(message);
		result.setData(data);
		return result;
	}

	public static AjaxResult failed(List<ObjectError> allErrors) {
		AjaxResult result = new AjaxResult(false);
		result.message = "";
		for (ObjectError objectError : allErrors) {
			LOGGER.info(objectError.getDefaultMessage());
			String error = replaceError(objectError.getDefaultMessage());
			if (!result.message.contains(error)) {
				result.message += error + "</br>";
			}
		}
		return result;
	}

	private static String replaceError(String errorMsg) {
		if (errorMsg != null && errorMsg.contains("Failed to convert property")) {
			return "You have inputed incorrect data";
		}
		return errorMsg;
	}
}