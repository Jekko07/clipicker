package com.clipicker.me.web.controller;

import com.clipicker.me.domain.Announcement;
import com.clipicker.me.domain.ChangeAnnouncement;
import com.clipicker.me.domain.Clinic;
import com.clipicker.me.domain.Specialization;
import com.clipicker.me.domain.Symptom;
import com.clipicker.me.exception.ValidateFailedException;
import com.clipicker.me.service.AnnouncementService;
import com.clipicker.me.service.ClinicService;
import com.clipicker.me.service.SpecializationService;
import com.clipicker.me.service.SymptomService;
import com.clipicker.me.utils.JSONUtil;
import com.clipicker.me.web.form.AnnouncementForm;
import com.clipicker.me.web.form.ClinicActivateForm;
import com.clipicker.me.web.form.DeleteAnnouncementForm;
import com.clipicker.me.web.form.DeleteClinicForm;
import com.clipicker.me.web.form.DeleteSpecializationForm;
import com.clipicker.me.web.form.DeleteSymptomForm;
import com.clipicker.me.web.form.SpecializationForm;
import com.clipicker.me.web.form.SymptomForm;
import com.clipicker.me.web.model.AjaxResult;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {
	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private ClinicService clinicService;
	@Autowired
	private SymptomService symptomService;
	@Autowired
	private SpecializationService specializationService;
	@Autowired
	private AnnouncementService announcementService;

	@GetMapping("/clinic/list")
	@ResponseBody
	public AjaxResult list(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "status", required = false) Integer status) {
		Map<String, Object> filter = new HashMap<>();
		if (!StringUtils.isEmpty(name)) {
			filter.put("name", name);
		}
		if (status != null) {
			filter.put("status", status);
		}
		return AjaxResult.success(clinicService.list(filter));
	}
	@PostMapping("/clinic/delete")
	@ResponseBody
	public AjaxResult deleteClinic(@RequestBody DeleteClinicForm form) {
		if (clinicService.loadClinic(form.getEmail()) == null) {
			throw new ValidateFailedException("Cannot delete clinic, clinic not found!");
		}
		clinicService.delete(form.getEmail());
		return AjaxResult.success("Deleted clinic successfully!");
	}

	@GetMapping("/symptom/list")
	@ResponseBody
	public AjaxResult listSymptom(@RequestParam(value="name", required = false) String name){
		Map<String, Object> filter = new HashMap<>();
		if(!StringUtils.isEmpty(name)){
			filter.put("name", name);
		}
		return AjaxResult.success(symptomService.list(filter));
	}

	@PostMapping("/symptom/delete")
	@ResponseBody
	public AjaxResult deleteSymptom(@RequestBody DeleteSymptomForm form) {
		if(symptomService.loadSymptom(form.getId()) == null) {
			throw new ValidateFailedException("Cannot delete symptom, symptom not found!");
		}
		symptomService.delete(form.getId());
		return AjaxResult.success("Deleted symptom successfully");
	}

	// only is_admin = 1 -> can insert/update/delete the symptom
	@PostMapping(value = "/symptom/saveOrUpdate")
	@ResponseBody
	public AjaxResult saveOrUpdate(@Valid @RequestBody SymptomForm form) {
		Symptom symptomFromDb = symptomService.loadSymptom(form.getId());
		if (symptomFromDb == null && form.getId() == null) {
			symptomService.insert(new Symptom(form.getName(), form.getDescription()));
			return AjaxResult.success("Registered symptom(s) successfully!");
		} else {
			symptomService.update(new Symptom(form.getId(), form.getName(), form.getDescription()));
			return AjaxResult.success("Updated symptom(s) successfully!");
		}
	}

	@GetMapping(value = "/symptom/loadSymptom")
	@ResponseBody
	public AjaxResult loadSymptom(@RequestParam Long id) {
		Symptom symp = symptomService.loadSymptom(id);
		if (symp == null) {
			throw new ValidateFailedException("ID not exist");
		} else {
			return AjaxResult.success(symp);
		}
	}

	@GetMapping("/specialization/list")
	@ResponseBody
	public AjaxResult listSpecialization(@RequestParam(value = "name", required = false) String name) {
		Map<String, Object> filter = new HashMap<>();
		if (!StringUtils.isEmpty(name)) {
			filter.put("name", name);
		}
		return AjaxResult.success(specializationService.list(filter));
	}

	@PostMapping("/specialization/delete")
	@ResponseBody
	public AjaxResult deleteSpecialization(@RequestBody DeleteSpecializationForm form) {
		if(specializationService.loadSpecialization(form.getId()) == null) {
			throw new ValidateFailedException("Cannot delete specialization, specialization not found!");
		}
		specializationService.delete(form.getId());
		return AjaxResult.success("Deleted specialization successfully");
	}

	@PostMapping(value = "/specialization/saveOrUpdate")
	@ResponseBody
	public AjaxResult saveOrUpdate(@Valid @RequestBody SpecializationForm form) {
		Specialization specFromDb = specializationService.loadSpecialization(form.getId());
		if (specFromDb == null && form.getId() == null) {
			specializationService.insert(new Specialization(form.getName(), form.getDescription()));
			return AjaxResult.success("Registered specialization(s) successfully!");
		} else {
			specializationService.update(new Specialization(form.getId(), form.getName(), form.getDescription()));
			return AjaxResult.success("Updated specialization(s) successfully!");
		}
	}

	// transfer this as only admin can load specialization for edit
	@GetMapping(value = "/specialization/loadSpecialization")
	@ResponseBody
	public AjaxResult loadSpecialization(@RequestParam Long id) {
		Specialization spec = specializationService.loadSpecialization(id);
		if (spec == null) {
			throw new ValidateFailedException("ID not exist");
		} else {
			return AjaxResult.success(spec);
		}
	}

	@GetMapping(value = "/announcement/list")
	@ResponseBody
	public AjaxResult listAnnouncement(@RequestParam(value = "name", required = false) String name) {
		Map<String, Object> filter = new HashMap<>();
		if (!StringUtils.isEmpty(name)) {
			filter.put("name", name);
		}
		return AjaxResult.success(announcementService.list(filter));
	}

	@PostMapping(value = "/announcement/saveOrUpdate")
	@ResponseBody
	public AjaxResult saveOrUpdateAnnouncement(@RequestBody @Valid AnnouncementForm form) {
		Announcement announcementFromDB = announcementService.load(form.getId());
		if (announcementFromDB == null && form.getId() == null) {
			Announcement announcement = new Announcement(form.getName(), form.getDescription(), form.getLocation(), form.getStartTime(), form.getExpireTime());
			announcementService.insert(announcement);
			this.triggerCreateAnnouncement(announcement);
			return AjaxResult.success("Registered announcement(s) successfully!");
		} else {
			Announcement announcement = new Announcement(announcementFromDB.getId(), form.getName(), form.getDescription(), form.getDescription(), form.getStartTime(), form.getExpireTime());
			announcementService.update(announcement);
			this.triggerUpdateAnnouncement(announcementFromDB, announcement);
			return AjaxResult.success("Updated announcement(s) successfully!");
		}
	}

	@GetMapping(value = "/announcement/load")
	@ResponseBody
	public AjaxResult loadAnnouncement(@RequestParam Long id) {
		return AjaxResult.success(announcementService.load(id));
	}

	@PostMapping(value = "/announcement/delete")
	@ResponseBody
	public AjaxResult deleteAnnouncement(@RequestBody DeleteAnnouncementForm form) {
		Announcement announcement = announcementService.load(form.getId());
		if(announcement == null) {
			throw new ValidateFailedException("Cannot delete announcement, announcement not found!");
		}
		announcementService.delete(announcement.getId());
		this.triggerDeleteAnnouncement(announcement);
		return AjaxResult.success("Deleted announcement successfully");
	}

	@PostMapping(value = "/clinic/activate")
	@ResponseBody
	public AjaxResult confirmActivation(@RequestBody ClinicActivateForm form) {
		Clinic clinic = clinicService.loadClinic(form.getEmail());
		if (clinic == null) {
			throw new ValidateFailedException("Clinic not exist.");
		}
		clinicService.activate(clinic.getId());
		this.triggerClinicActivated(clinic);
		return AjaxResult.success("Clinic activated successfully.");
	}

	private void triggerCreateAnnouncement(Announcement announcement) {
		this.template.convertAndSend("/announcement/listener/createAnnouncement", JSONUtil.toJson(announcement));
	}

	private void triggerDeleteAnnouncement(Announcement announcement) {
		this.template.convertAndSend("/announcement/listener/deleteAnnouncement", JSONUtil.toJson(announcement));
	}

	private void triggerUpdateAnnouncement(Announcement originalAnnouncement, Announcement newAnnouncement) {
		this.template.convertAndSend("/announcement/listener/updateAnnouncement", JSONUtil.toJson(new ChangeAnnouncement(originalAnnouncement, newAnnouncement)));
	}

	private void triggerClinicActivated(Clinic clinic) {
		this.template.convertAndSend("/clinicNotify/listener/activated/" + clinic.getEmail(), JSONUtil.toJson(clinic));
	}
}
