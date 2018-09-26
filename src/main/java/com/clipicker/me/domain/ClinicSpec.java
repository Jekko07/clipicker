package com.clipicker.me.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinicSpec {

    private List<Long> specIdList;
    private Long clinic_id;


    public ClinicSpec(List<Long> specIdList, Long clinic_id) {
        this.specIdList = specIdList;
        this.clinic_id = clinic_id;
    }

    public Long getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(Long clinic_id) {
        this.clinic_id = clinic_id;
    }

    public List<Long> getSpecIdList() {
        return specIdList;
    }

    public void setSpecIdList(List<Long> specIdList) {
        this.specIdList = specIdList;
    }
}
