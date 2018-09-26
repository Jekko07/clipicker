package com.clipicker.me.service;

import com.clipicker.me.domain.Symptom;
import java.util.List;
import java.util.Map;

public interface SymptomService {

    List<Symptom> list(Map<String, Object> filter);

    Symptom loadSymptom(Long id);

    void insert(Symptom symptom);

    void update(Symptom symptom);

    void delete(Long id);
}
