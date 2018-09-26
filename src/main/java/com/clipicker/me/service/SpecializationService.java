package com.clipicker.me.service;

import com.clipicker.me.domain.Specialization;
import java.util.List;
import java.util.Map;

public interface SpecializationService {
    List<Specialization> list(Map<String, Object> filter);

    Specialization loadSpecialization(Long id);

    void insert(Specialization specialization);

    void update(Specialization specialization);

    void delete(Long id);
}
