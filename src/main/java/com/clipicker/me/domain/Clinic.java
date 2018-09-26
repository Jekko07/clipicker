package com.clipicker.me.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Clinic {

	private Long id;
	private String email;
	private String username;
	private String name;
	private Integer status;
	private Date createdTime;
	private Date updatedTime;
	private Date lastLoginTime;
	private Integer isOnline;
	private Integer isAdmin;
	private String address;
	private Double lat;
	private Double lng;
	private String contactNumber;
	private String busStartTime;
	private String busEndTime;

	public Clinic() {
	}

	public Clinic(String email, String username, String name, String address, Double lat, Double lng, String contactNumber, String busStartTime, String busEndTime) {
		this.email = email;
		this.username = username;
		this.name = name;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
		this.contactNumber = contactNumber;
		this.busStartTime = busStartTime;
		this.busEndTime = busEndTime;
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

	public String getBusStartTime() {
		return busStartTime;
	}

	public void setBusStartTime(String busStartTime) {
		this.busStartTime = busStartTime;
	}

	public String getBusEndTime() {
		return busEndTime;
	}

	public void setBusEndTime(String busEndTime) {
		this.busEndTime = busEndTime;
	}
}
