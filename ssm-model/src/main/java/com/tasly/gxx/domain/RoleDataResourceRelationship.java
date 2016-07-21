package com.tasly.gxx.domain;

public class RoleDataResourceRelationship {

	private long id;
	private long roleId;
	private long dataResourceId;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getDataResourceId() {
		return dataResourceId;
	}
	public void setDataResourceId(long dataResourceId) {
		this.dataResourceId = dataResourceId;
	}
	

}
