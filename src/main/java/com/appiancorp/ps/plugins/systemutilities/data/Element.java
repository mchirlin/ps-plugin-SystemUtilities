package com.appiancorp.ps.plugins.systemutilities.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlRootElement(namespace="http://plugins.ps.appiancorp.com/suite/types/", name="component")
@XmlType(namespace="http://plugins.ps.appiancorp.com/suite/types/", name=Element.LOCAL_PART, propOrder={"fieldName", "fieldType", "cdtId", "nillable", "multiple", "minOccurs", "maxOccurs", "columnName", "columnDefinition", "primaryKey", "dataRelationship", "annotations"})
class Element implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAMESPACE = "urn:appiancorp:ps:plugins";
	public static final String LOCAL_PART = "UTIL_Element";
	public static final QName QNAME = new QName(NAMESPACE, LOCAL_PART);
	
	private String fieldName;
	private String fieldType;
	private Long cdtId;
	private Boolean nillable;
	private Boolean multiple;
	private Long minOccurs;
	private String maxOccurs;
	private String columnName;
	private String columnDefinition;
	private Boolean primaryKey;
	
	private DataRelationship dataRelationship;
	
	private List<String> annotations = new ArrayList<String>();

	@XmlElement
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@XmlElement
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	@XmlElement
	public Long getCdtId() {
		return cdtId;
	}
	public void setCdtId(Long cdtId) {
		this.cdtId = cdtId;
	}
	
	@XmlElement
	public Boolean isNillable() {
		return nillable;
	}
	public void setNillable(Boolean nillable) {
		this.nillable = nillable;
	}
	
	@XmlElement
	public Boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}
	
	@XmlElement
	public Long getMinOccurs() {
		return minOccurs;
	}
	public void setMinOccurs(Long minOccurs) {
		this.minOccurs = minOccurs;
	}
	
	@XmlElement
	public String getMaxOccurs() {
		return maxOccurs;
	}
	public void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}
	
	@XmlElement
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	@XmlElement
	public String getColumnDefinition() {
		return columnDefinition;
	}
	public void setColumnDefinition(String columnDefinition) {
		this.columnDefinition = columnDefinition;
	}
	
	@XmlElement
	public DataRelationship getDataRelationship() {
		return dataRelationship;
	}
	public void setDataRelationship(DataRelationship dataRelationship) {
		this.dataRelationship = dataRelationship;
	}
	
	@XmlElement
	public Boolean isPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	@XmlElement
	public List<String> getAnnotations() {
		return annotations;
	}
	public void addAnnotation(String annotation) {
		annotations.add(annotation);
	}
}