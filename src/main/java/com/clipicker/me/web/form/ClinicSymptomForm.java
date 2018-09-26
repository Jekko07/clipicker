package com.clipicker.me.web.form;


import java.util.List;

public class ClinicSymptomForm {
    private Long clinic_id;
    private List<Long> sympIdList;

    public ClinicSymptomForm(Long clinic_id, List<Long> sympIdList) {
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
