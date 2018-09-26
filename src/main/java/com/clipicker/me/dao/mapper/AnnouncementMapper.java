package com.clipicker.me.dao.mapper;

import com.clipicker.me.domain.Announcement;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AnnouncementMapper {
	List<Announcement> list(Map<String, Object> filter);

	Announcement load(Long id);

	void insert(Announcement announcement);

	void update(Announcement announcement);

	void delete(Long id);
}
