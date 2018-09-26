package com.clipicker.me.dao.mapper;


import com.clipicker.me.domain.Symptom;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface SymptomMapper {

    List<Symptom> list(Map<String, Object> filter);

    Symptom loadSymptom(Long id);

    void insert(Symptom symptom);

    void update(Symptom symptom);

    void delete(Long id);
}
