package com.clipicker.me.service;

import com.clipicker.me.domain.Clinic;
import com.clipicker.me.domain.ClinicServiceOffer;
import java.util.List;
import java.util.Map;

public interface ClinicService {

	List<Clinic> list(Map<String, Object> filter);

	Clinic loadClinic(String email);

	void insert(Clinic clinic);

	void update(Clinic clinic);

	void delete(String email);

	/*
	void specServiceSave (ClinicSpec clinicSpec);

	void symptomServiceSave (ClinicSymptom clinicSymptom);
	*/

	void saveService(Map<String, Object> clinicServiceFlag, ClinicServiceOffer serviceOffer);

	ClinicServiceOffer loadClinicService(Clinic clinic);

	List<Clinic> showNearbyClinic(Map<String, Object> filter);

	void activate(Long id);
}
