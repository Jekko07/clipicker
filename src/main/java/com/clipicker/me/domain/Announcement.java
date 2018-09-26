package com.clipicker.me.domain;

import java.util.Date;

public class Announcement {

	private Long id;
	private String name;
	private String description;
	private String location;
	private Date startTime;
	private Date expireTime;

	public Announcement() {
	}

	public Announcement(String name, String description, String location, Date startTime,
		Date expireTime) {
		this.name = name;
		this.description = description;
		this.location = location;
		this.startTime = startTime;
		this.expireTime = expireTime;
	}

	public Announcement(Long id, String name, String description, String location,
		Date startTime, Date expireTime) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.startTime = startTime;
		this.expireTime = expireTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
}
