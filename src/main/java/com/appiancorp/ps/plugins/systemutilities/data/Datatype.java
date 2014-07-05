package com.appiancorp.ps.plugins.systemutilities.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlRootElement(namespace="http://plugins.ps.appiancorp.com/suite/types/", name="component")
@XmlType(namespace="http://plugins.ps.appiancorp.com/suite/types/", name=Datatype.LOCAL_PART, propOrder={"name", "description", "namespace", "tableName", "annotations", "includes", "includeNamespaces", "elements"})
public class Datatype implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAMESPACE = "urn:appiancorp:ps:plugins";
	public static final String LOCAL_PART = "UTIL_Datatype";
	public static final QName QNAME = new QName(NAMESPACE, LOCAL_PART);

	private String name;
	private String description;
	private String namespace;
	private String tableName;

	private List<String> annotations = new ArrayList<String>();
	private List<String> includes = new ArrayList<String>();
	private List<String> includeNamespaces = new ArrayList<String>();
	private List<Element> elements = new ArrayList<Element>();

	@XmlElement
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}


	@XmlElement
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	@XmlElement
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@XmlElement
	public List<String> getAnnotations() {
		return annotations;
	}
	public void addAnnotation(String annotation) {
		annotations.add(annotation);
	}
	
	@XmlElement
	public List<String> getIncludes() {
		return includes;
	}
	public void addIncludes(String include) {
		includes.add(include);
	}
	
	@XmlElement
	public List<String> getIncludeNamespaces() {
		return includeNamespaces;
	}
	public void addIncludeNamespaces(String includeNamespace) {
		includeNamespaces.add(includeNamespace);
	}

	@XmlElement
	public List<Element> getElements() {
		return elements;
	}
	
	public void addElement(Element element) {
		elements.add(element);
	}
	
	public String getElementName(int index) {
		return elements.get(index).getFieldName();
	}
	
	public String getElementType(int index) {
		return elements.get(index).getFieldType();
	}
	
	public Long getElementMinOccurs(int index) {
		return elements.get(index).getMinOccurs();
	}
	
	public Boolean getElementHasMinOccurs(int index) {
		Boolean hasMinOccurs = elements.get(index).getMinOccurs() != null;
		return hasMinOccurs;
	}
	
	public String getElementMaxOccurs(int index) {
		return elements.get(index).getMaxOccurs();
	}
	
	public Boolean getElementHasMaxOccurs(int index) {
		Boolean hasMaxOccurs = !elements.get(index).getMaxOccurs().isEmpty();
		return hasMaxOccurs;
	}
	
	public Boolean getElementNillable(int index) {
		return elements.get(index).isNillable();
	}
	
	public List<String> getElementAnnotations(int index) {
		return elements.get(index).getAnnotations();
	}
}
