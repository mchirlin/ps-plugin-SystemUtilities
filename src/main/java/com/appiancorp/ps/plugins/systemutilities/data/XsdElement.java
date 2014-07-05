package com.appiancorp.ps.plugins.systemutilities.data;

import java.io.Serializable;
import javax.xml.namespace.QName;

public class XsdElement
implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String LOCAL_PART = "XsdElement";
	public static final QName QNAME = new QName("http://coe.appiancorp.com/suite/types/", "XsdElement");
	private String columnName;
	private String fieldName;
	private String fieldType;
	private String nillable;
	private String minOccurs;
	private String maxOccurs;
	private String columnDefinition;
	private String primaryKey;

	public XsdElement(String columnName, String fieldName, String fieldType, String nillable, String minOccurs, String maxOccurs, String columnDefinition, 
			String primaryKey) {
		this.columnName = columnName;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.nillable = nillable;
		this.minOccurs = minOccurs;
		this.maxOccurs = maxOccurs;
		this.columnDefinition = columnDefinition;
		this.primaryKey = primaryKey;
	}

	public String getColumnName() {
		return columnName;
	}

	private void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldName() {
		return fieldName;
	}

	private void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	private void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getNillable() {
		return nillable;
	}

	private void setNillable(String nillable) {
		this.nillable = nillable;
	}

	public String getMinOccurs() {
		return minOccurs;
	}

	private void setMinOccurs(String minOccurs) {
		this.minOccurs = minOccurs;
	}

	public String getMaxOccurs() {
		return maxOccurs;
	}

	private void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	public String getColumnDefinition() {
		return columnDefinition;
	}

	private void setColumnDefinition(String columnDefinition) {
		this.columnDefinition = columnDefinition;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	private void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

}
