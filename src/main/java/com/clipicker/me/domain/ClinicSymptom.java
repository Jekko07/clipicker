package com.clipicker.me.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinicSymptom {

    private Long clinic_id;
    private List<Long> sympIdList;

    public ClinicSymptom(List<Long> sympIdList, Long clinic_id) {
        this.clinic_id = clinic_id;
        this.sympIdList = sympIdList;
    }

    public List<Long> getSympIdList() {
        return sympIdList;
    }

    public void setSympIdList(List<Long> sympIdList) {
        this.sympIdList = sympIdList;
    }

    public Long getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(Long clinic_id) {
        this.clinic_id = clinic_id;
    }
}
