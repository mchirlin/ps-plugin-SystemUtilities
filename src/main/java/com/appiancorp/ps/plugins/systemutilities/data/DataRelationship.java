package com.appiancorp.ps.plugins.systemutilities.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlRootElement(namespace="http://plugins.ps.appiancorp.com/suite/types/", name="component")
@XmlType(namespace="http://plugins.ps.appiancorp.com/suite/types/", name=DataRelationship.LOCAL_PART, propOrder={})
public class DataRelationship implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAMESPACE = "urn:appiancorp:ps:plugins";
	public static final String LOCAL_PART = "UTIL_DataRelationship";
	public static final QName QNAME = new QName(NAMESPACE, LOCAL_PART);

	private String type;
	private String cascade;
	private Boolean optional;
	private Boolean indexed;
	
	private String joinTableName;
	
	private String joinColumnName;
	private String joinColumnNullable;
	private String joinColumnUnique;
	
	private String inverseJoinColumnName;
	private String inverseJoinColumnNullable;
	private String inverseJoinColumnUnique;
	
	@XmlElement
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement
	public String getCascade() {
		return cascade;
	}
	public void setCascade(String cascade) {
		this.cascade = cascade;
	}
	
	@XmlElement
	public Boolean isOptional() {
		return optional;
	}
	public void setOptional(Boolean optional) {
		this.optional = optional;
	}
	
	@XmlElement
	public Boolean isIndexed() {
		return indexed;
	}
	public void setIndexed(Boolean indexed) {
		this.indexed = indexed;
	}
	
	@XmlElement
	public String getJoinTableName() {
		return joinTableName;
	}
	public void setJoinTableName(String joinTableName) {
		this.joinTableName = joinTableName;
	}
	
	@XmlElement
	public String getJoinColumnName() {
		return joinColumnName;
	}
	public void setJoinColumnName(String joinColumnName) {
		this.joinColumnName = joinColumnName;
	}
	
	@XmlElement
	public String getJoinColumnNullable() {
		return joinColumnNullable;
	}
	public void setJoinColumnNullable(String joinColumnNullable) {
		this.joinColumnNullable = joinColumnNullable;
	}
	
	@XmlElement
	public String getJoinColumnUnique() {
		return joinColumnUnique;
	}
	public void setJoinColumnUnique(String joinColumnUnique) {
		this.joinColumnUnique = joinColumnUnique;
	}
	
	@XmlElement
	public String getInverseJoinColumnName() {
		return inverseJoinColumnName;
	}
	public void setInverseJoinColumnName(String inverseJoinColumnName) {
		this.inverseJoinColumnName = inverseJoinColumnName;
	}
	
	@XmlElement
	public String getInverseJoinColumnNullable() {
		return inverseJoinColumnNullable;
	}
	public void setInverseJoinColumnNullable(String inverseJoinColumnNullable) {
		this.inverseJoinColumnNullable = inverseJoinColumnNullable;
	}
	
	@XmlElement
	public String getInverseJoinColumnUnique() {
		return inverseJoinColumnUnique;
	}
	public void setInverseJoinColumnUnique(String inverseJoinColumnUnique) {
		this.inverseJoinColumnUnique = inverseJoinColumnUnique;
	}
}
