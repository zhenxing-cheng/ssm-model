package com.tasly.gxx.domain;

import java.io.Serializable;

/**
 * @author Clark
 *
 */
public class Menu implements Serializable{

	private static final long serialVersionUID = -6072208045779215432L;
	private long id;
	private String name;
	private String url;
	private long parent_id;
	private int priority;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public long getParent_id() {
		return parent_id;
	}
	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
	
	
	
}
