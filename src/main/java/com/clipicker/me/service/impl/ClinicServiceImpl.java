package com.clipicker.me.service.impl;

import com.clipicker.me.dao.mapper.ClinicMapper;
import com.clipicker.me.dao.mapper.PatientMapper;
import com.clipicker.me.domain.Clinic;
import com.clipicker.me.domain.ClinicServiceOffer;
import com.clipicker.me.service.ClinicService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("clinicService")
public class ClinicServiceImpl implements ClinicService {

	@Autowired
	private ClinicMapper clinicMapper;

	@Autowired
	private PatientMapper patientMapper;

	@Override
	public List<Clinic> list(Map<String, Object> filter) {
		return clinicMapper.list(filter);
	}

	@Override
	public Clinic loadClinic(String email) {
		return clinicMapper.loadClinic(email);
	}

	@Override
	@Transactional
	public void update(Clinic clinic) {
		clinicMapper.update(clinic);
	}

	@Override
	@Transactional
	public void insert(Clinic clinic) {
		clinicMapper.insert(clinic);
	}

	@Override
	public void delete(String email) {
		clinicMapper.delete(email);
	}

	@Override
	@Transactional
	public void saveService(Map<String, Object> clinicServiceFlag, ClinicServiceOffer serviceOffer) {
		clinicMapper.updateClinicServiceFlag(clinicServiceFlag);
		clinicMapper.deleteSpecService(serviceOffer.getClinicId());
		clinicMapper.deleteSymptomService(serviceOffer.getClinicId());

		if (!serviceOffer.getSpecIdList().isEmpty()) {
			clinicMapper.insertSpecService(serviceOffer);
		}
		if (!serviceOffer.getSymptomIdList().isEmpty()) {
			clinicMapper.insertSymptomService(serviceOffer);
		}
	}

	@Override
	public ClinicServiceOffer loadClinicService(Clinic clinic) {
		ClinicServiceOffer clinicServiceOffer = new ClinicServiceOffer(clinic.getId());
		clinicServiceOffer.setSpecIdList(clinicMapper.loadSpecService(clinic.getId()));
		clinicServiceOffer.setSymptomIdList(clinicMapper.loadSymptomService(clinic.getId()));

		return clinicServiceOffer;
	}

	@Override
	public List<Clinic> showNearbyClinic(Map<String, Object> filter) {
		return patientMapper.showNearbyClinics(filter);
	}

	@Override
	public void activate(Long id) {
		clinicMapper.activate(id);
	}
}
