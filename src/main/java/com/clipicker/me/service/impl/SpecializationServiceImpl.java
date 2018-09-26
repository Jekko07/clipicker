package com.clipicker.me.service.impl;

import com.clipicker.me.dao.mapper.SpecializationMapper;
import com.clipicker.me.domain.Specialization;
import com.clipicker.me.service.SpecializationService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("specializationService")
public class SpecializationServiceImpl implements SpecializationService{

    @Autowired
    private SpecializationMapper specializationMapper;

    @Override
    public List<Specialization> list(Map<String, Object> filter) {
        return specializationMapper.list(filter);
    }

    @Override
    public Specialization loadSpecialization(Long id) {
        return specializationMapper.loadSpecialization(id);
    }

    @Override
    @Transactional
    public void update(Specialization specialization) {
        specializationMapper.update(specialization);
    }

    @Override
    @Transactional
    public void insert(Specialization specialization) {
        specializationMapper.insert(specialization);
    }

    @Override
    public void delete(Long id) {
        specializationMapper.delete(id);
    }
}
