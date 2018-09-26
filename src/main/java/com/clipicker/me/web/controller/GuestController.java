package com.clipicker.me.web.controller;

import com.clipicker.me.domain.Announcement;
import com.clipicker.me.domain.Clinic;
import com.clipicker.me.service.AnnouncementService;
import com.clipicker.me.service.ClinicService;
import com.clipicker.me.service.SpecializationService;
import com.clipicker.me.service.SymptomService;
import com.clipicker.me.web.model.AjaxResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	private ClinicService clinicService;
	@Autowired
	private AnnouncementService announcementService;
	@Autowired
	private SpecializationService specializationService;
	@Autowired
	private SymptomService symptomService;


	@Autowired
	private SimpMessagingTemplate template;

	@GetMapping("/welcome")
	public String authorizeCallback() {
		return "welcome guest user";
	}

	@GetMapping("/clinic/list")
	public AjaxResult listClinic(@RequestParam(required = false) List<Long> specIdList, @RequestParam(required = false) List<Long> symptomIdList) {
		Map<String, Object> filter = new HashMap<>();
		if (specIdList != null && !specIdList.isEmpty()) {
			filter.put("specIdList", specIdList);
		}
		if (symptomIdList != null && !symptomIdList.isEmpty()) {
			filter.put("symptomIdList", symptomIdList);
		}

		List<Clinic> clinics = clinicService.showNearbyClinic(filter);
		clinics.stream().forEach(clinic -> clinic.setEmail(null));		// hide the clinic email
		return AjaxResult.success(clinics);
	}

	@GetMapping("/announcement/list")
	public AjaxResult listAnnouncement() {
		List<Announcement> announcements = announcementService.list(new HashMap<>());
		return AjaxResult.success(announcements);
	}

	@GetMapping(value = "/announcement/load")
	@ResponseBody
	public AjaxResult loadAnnouncement(@RequestParam Long id) {
		return AjaxResult.success(announcementService.load(id));
	}

	@GetMapping(value = "/specialization/list")
	@ResponseBody
	public AjaxResult listSpecialization() {
		return AjaxResult.success(specializationService.list(new HashMap<>()));
	}

	@GetMapping(value = "/symptom/list")
	@ResponseBody
	public AjaxResult listSymptom() {
		return AjaxResult.success(symptomService.list(new HashMap<>()));
	}
}
