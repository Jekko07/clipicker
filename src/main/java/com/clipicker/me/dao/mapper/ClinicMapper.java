package com.clipicker.me.dao.mapper;

import com.clipicker.me.domain.Clinic;
import com.clipicker.me.domain.ClinicServiceOffer;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ClinicMapper {

	List<Clinic> list(Map<String, Object> filter);

	Clinic loadClinic(String email);

	void insert(Clinic clinic);

	void update(Clinic clinic);

	void delete(String email);

	void insertSpecService(ClinicServiceOffer clinicServiceOffer);

	void insertSymptomService(ClinicServiceOffer clinicServiceOffer);

	void deleteSpecService(Long clinic_id);

	void deleteSymptomService(Long clinic_id);

	void updateClinicServiceFlag(Map<String, Object> serviceFlag);

	List<Long> loadSpecService(Long clinicId);

	List<Long> loadSymptomService(Long clinicId);

	void activate(Long id);
}
