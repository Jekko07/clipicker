package com.clipicker.me.web.form;

import java.util.Date;
import javax.validation.constraints.NotNull;

public class ClinicForm {

	private Long id;
	@NotNull(message = "required email")
	private String email;
	@NotNull(message = "required username")
	private String username;
	@NotNull(message = "required name")
	private String name;
	private Integer status;
	private Date createdTime;
	private Date updatedTime;
	private Date lastLoginTime;
	private Integer isOnline;
	private Integer isAdmin;
	@NotNull(message = "required address")
	private String address;
	@NotNull(message = "required lat")
	private Double lat;
	@NotNull(message = "required lng")
	private Double lng;
	@NotNull(message = "required contactNumber")
	private String contactNumber;
	@NotNull
	private Integer isOwn;
	@NotNull(message = "required business start time")
	private String bus_start_time;
	@NotNull(message = "required business end time")
	private String bus_end_time;

	public ClinicForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Integer getIsOwn() {
		return isOwn;
	}

	public void setIsOwn(Integer isOwn) {
		this.isOwn = isOwn;
	}

	public String getBus_start_time() {
		return bus_start_time;
	}

	public void setBus_start_time(String bus_start_time) {
		this.bus_start_time = bus_start_time;
	}

	public String getBus_end_time() {
		return bus_end_time;
	}

	public void setBus_end_time(String bus_end_time) {
		this.bus_end_time = bus_end_time;
	}
}
