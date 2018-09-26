package com.clipicker.me.service.impl;

import com.clipicker.me.dao.mapper.AnnouncementMapper;
import com.clipicker.me.domain.Announcement;
import com.clipicker.me.service.AnnouncementService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("announcementService")
public class AnnouncementServiceImpl implements AnnouncementService {

	@Autowired
	private AnnouncementMapper mapper;

	@Override
	public List<Announcement> list(Map<String, Object> filter) {
		return mapper.list(filter);
	}

	@Override
	public Announcement load(Long id) {
		return mapper.load(id);
	}

	@Override
	@Transactional
	public void insert(Announcement announcement) {
		mapper.insert(announcement);
	}

	@Override
	@Transactional
	public void update(Announcement announcement) {
		mapper.update(announcement);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		mapper.delete(id);
	}
}
