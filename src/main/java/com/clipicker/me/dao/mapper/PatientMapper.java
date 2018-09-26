package com.clipicker.me.dao.mapper;

import com.clipicker.me.domain.Clinic;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PatientMapper {

	List<Clinic> showNearbyClinics(Map<String, Object> filter);
}
