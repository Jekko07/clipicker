package com.clipicker.me.service.impl;

import com.clipicker.me.domain.Symptom;
import com.clipicker.me.dao.mapper.SymptomMapper;
import com.clipicker.me.service.SymptomService;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("symptomService")
public class SymptomServiceImpl implements SymptomService{

    @Autowired
    private SymptomMapper symptomMapper;

    @Override
    public List<Symptom> list(Map<String, Object> filter) {
        return symptomMapper.list(filter);
    }

    @Override
    public Symptom loadSymptom(Long id) {
        return symptomMapper.loadSymptom(id);
    }

    @Override
    @Transactional
    public void update(Symptom symptom) {
        symptomMapper.update(symptom);
    }

    @Override
    @Transactional
    public void insert(Symptom symptom) {
        symptomMapper.insert(symptom);
    }

    @Override
    public void delete(Long id) {
        symptomMapper.delete(id);
    }
}
