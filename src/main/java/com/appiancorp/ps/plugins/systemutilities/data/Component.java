package com.appiancorp.ps.plugins.systemutilities.database;

import java.io.Serializable;
import javax.xml.namespace.QName;

public class Component
implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String NAMESPACE = "urn:appian:ps:plugins:systemtools";
	public static final String LOCAL_PART = "SYSTEMTOOLS_Component";
	public static final QName QNAME = new QName("urn:appian:ps:plugins:systemtools", "SYSTEMTOOLS_Component");
	private Long id;
	private String name;
	private String type;
	private String uuid;
	private String username;
	private String company;

	public Component(Long id, String name, String type, String uuid, String username, String company) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.uuid = uuid;
		this.username = username;
		this.company = company;
	}

	public Component(String name, String type, String uuid) {
		this.name = name;
		this.type = type;
		this.uuid = uuid;
	}


	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	private void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return username;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	public String getCompany() {
		return company;
	}

	private void setCompany(String company) {
		this.company = company;
	}

}
