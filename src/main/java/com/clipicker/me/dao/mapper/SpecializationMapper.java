package com.clipicker.me.dao.mapper;

import com.clipicker.me.domain.Specialization;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SpecializationMapper {

    List<Specialization> list(Map<String, Object> filter);

    Specialization loadSpecialization(Long id);

    void insert(Specialization specialization);

    void update(Specialization specialization);

    void delete(Long id);

}
