package com.clipicker.me.web.form;

import java.util.List;
import javax.validation.constraints.NotNull;

public class ClinicServiceForm {
	@NotNull(message = "specialization required.")
	private List<Long> specIdList;
	@NotNull(message = "symptom required.")
	private List<Long> symptomIdList;
	@NotNull(message = "clinic email required.")
	private String clinicEmail;

	public ClinicServiceForm() {
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

	public String getClinicEmail() {
		return clinicEmail;
	}

	public void setClinicEmail(String clinicEmail) {
		this.clinicEmail = clinicEmail;
	}
}
