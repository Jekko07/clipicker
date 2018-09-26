package com.clipicker.me.domain;

import java.util.List;

public class ClinicServiceOffer {

	private List<Long> specIdList;
	private List<Long> symptomIdList;
	private Long clinicId;

	public ClinicServiceOffer(Long clinicId) {
		this.clinicId = clinicId;
	}

	public ClinicServiceOffer(List<Long> specIdList, List<Long> symptomIdList, Long clinicId) {
		this.specIdList = specIdList;
		this.symptomIdList = symptomIdList;
		this.clinicId = clinicId;
	}

	public List<Long> getSpecIdList() {
		return specIdList;
	}

	public void setSpecIdList(List<Long> specIdList) {
		this.specIdList = specIdList;
	}

	public List<Long> getSymptomIdList() {
		return symptomIdList;
	}

	public void setSymptomIdList(List<Long> symptomIdList) {
		this.symptomIdList = symptomIdList;
	}

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}
}
