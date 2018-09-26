package com.clipicker.me.web.controller;

import com.clipicker.me.domain.Clinic;
import com.clipicker.me.domain.ClinicServiceOffer;
import com.clipicker.me.domain.Specialization;
import com.clipicker.me.domain.Symptom;
import com.clipicker.me.domain.User;
import com.clipicker.me.domain.okta.OktaRegistrationResponse;
import com.clipicker.me.exception.ValidateFailedException;
import com.clipicker.me.service.ClinicService;
import com.clipicker.me.service.OktaService;
import com.clipicker.me.service.SpecializationService;
import com.clipicker.me.service.SymptomService;
import com.clipicker.me.utils.CaptchaUtils;
import com.clipicker.me.utils.JSONUtil;
import com.clipicker.me.web.form.ClinicForm;
import com.clipicker.me.web.form.ClinicServiceForm;
import com.clipicker.me.web.form.RegisterForm;
import com.clipicker.me.web.model.AjaxResult;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinic")
public class ClinicController extends BaseController {

	@Autowired
	private ClinicService clinicService;
	@Autowired
	private SymptomService symptomService;
	@Autowired
	private SpecializationService specializationService;

	@Autowired
	private OktaService oktaService;

	@Autowired
	private SimpMessagingTemplate template;

	@PostMapping(value = "/register")
	@ResponseBody
	public AjaxResult register(@Valid @RequestBody RegisterForm form, HttpServletRequest request)
		throws IOException {
		String expectedCaptcha = (String) request.getSession().getAttribute(CaptchaUtils.CAPTCHA_KEY);
		if (expectedCaptcha != null && expectedCaptcha.equals(form.getCaptchaCode())) {
			OktaRegistrationResponse oktaResponse = oktaService.register(User.createOktaProfile(form.getFirstName(), form.getLastName(), form.getEmail(), form.getEmail()));
			return AjaxResult.success(oktaResponse);
		} else {
			throw new ValidateFailedException("Captcha not matched!");
		}
	}

	@GetMapping(value = "/loadClinic")
	@ResponseBody
	public AjaxResult loadClinic(@RequestParam String email) {
		Clinic clinic = clinicService.loadClinic(email);
		if (clinic == null) {
			throw new ValidateFailedException("Email not exist");
		} else {
			return AjaxResult.success(clinic);
		}
	}

	@PostMapping(value = "/saveOrUpdate")
	@ResponseBody
	public AjaxResult saveOrUpdate(@Valid @RequestBody ClinicForm form) {
		Clinic clinicFromDb = clinicService.loadClinic(form.getEmail());
		if (clinicFromDb == null) {
			Clinic clinic = new Clinic(form.getEmail(), form.getUsername(), form.getName(), form.getAddress(), form.getLat(), form.getLng(), form.getContactNumber(), form.getBus_start_time(), form.getBus_end_time());
			clinicService.insert(clinic);
			this.triggerNewRegisteredClinic(clinic);
			return AjaxResult.success("Registered clinic successfully!");
		} else {
			clinicService.update(new Clinic(clinicFromDb.getEmail(), form.getUsername(), form.getName(), form.getAddress(), form.getLat(), form.getLng(), form.getContactNumber(), form.getBus_start_time(), form.getBus_end_time()));
			return AjaxResult.success("Updated clinic successfully!");
		}
	}

	@PostMapping(value = "/services/save")
	@ResponseBody
	public AjaxResult saveClinicServices(Principal loginUser, @Valid @RequestBody ClinicServiceForm form) {
		Clinic clinicLoginUser = clinicService.loadClinic(loginUser.getName());
		if (clinicLoginUser == null) {
			throw new ValidateFailedException("Invalid clinic service post request.");
		}
		String clinicEmail = clinicLoginUser.getIsAdmin().intValue() == 1 ? form.getClinicEmail() : loginUser.getName();
		Clinic clinic = clinicService.loadClinic(clinicEmail);
		if (clinic == null) {
			throw new ValidateFailedException("Invalid clinic service post request.");
		}

		Map<String, Object> serviceFlag = this.prepareClinicServiceFlag(clinic, form);
		clinicService.saveService(serviceFlag, new ClinicServiceOffer(form.getSpecIdList(), form.getSymptomIdList(), clinic.getId()));
		return AjaxResult.success("Services successfully saved.");
	}

	private Map<String, Object> prepareClinicServiceFlag(Clinic clinic, ClinicServiceForm form) {
		Map<String, Object> serviceFlag = new HashMap<>();
		Map<String, Object> emptyFilter = new HashMap<>();
		List<Specialization> allSpecList = specializationService.list(emptyFilter);
		List<Symptom> allSymptomList = symptomService.list(emptyFilter);

		int isAllSpec = 1;
		int isAllSymptom = 1;
		serviceFlag.put("clinicId", clinic.getId());
		if (!form.getSpecIdList().isEmpty() && allSpecList.size() > form.getSpecIdList().size()) {
			isAllSpec = 0;
		}
		if (!form.getSymptomIdList().isEmpty() && allSymptomList.size() > form.getSymptomIdList().size()) {
			isAllSymptom = 0;
		}
		serviceFlag.put("isAllSpec", isAllSpec);
		serviceFlag.put("isAllSymptom", isAllSymptom);

		return serviceFlag;
	}


	@GetMapping("/services/load")
	@ResponseBody
	public AjaxResult loadClinicService(Principal loginUser, @RequestParam String clinicEmail) {
		Clinic clinicLoginUser = clinicService.loadClinic(loginUser.getName());
		if (clinicLoginUser == null) {
			throw new ValidateFailedException("Invalid clinic service post request.");
		}
		clinicEmail = clinicLoginUser.getIsAdmin().intValue() == 1 ? clinicEmail : loginUser.getName();
		Clinic clinic = clinicService.loadClinic(clinicEmail);
		if (clinic == null) {
			throw new ValidateFailedException("Invalid clinic service get request.");
		}
		ClinicServiceOffer clinicServiceOffer = clinicService.loadClinicService(clinic);

		return AjaxResult.success(clinicServiceOffer);
	}

	private void triggerNewRegisteredClinic(Clinic clinic) {
		this.template.convertAndSend("/adminNotify/listener/newRegisteredClinic", JSONUtil.toJson(clinic));
	}
}
