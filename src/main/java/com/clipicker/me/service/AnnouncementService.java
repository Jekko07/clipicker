package com.clipicker.me.service;

import com.clipicker.me.domain.Announcement;
import java.util.List;
import java.util.Map;

public interface AnnouncementService {

	List<Announcement> list(Map<String, Object> filter);

	Announcement load(Long id);

	void insert(Announcement announcement);

	void update(Announcement announcement);

	void delete(Long id);
}
