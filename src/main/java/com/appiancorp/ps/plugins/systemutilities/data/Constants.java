package com.appiancorp.ps.plugins.systemutilities.data;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String ACP_LOG_FILES = "LogFiles";
	public static final String ACP_FOLDER_ID = "FolderId";
	public static final String ARV_NEW_DOCUMENTS = "NewDocuments";
	public static final String LOG_FILES_REQUIRED = "logfiles.required";
	public static final String LOG_FILE_INVALID_LOCATION = "logfile.invalidLocation";
	public static final String LOG_FILE_INVALID_FILE = "logfile.invalidFile";
	public static final String LOG_FILE_VALIDATION = "logfile.validation";
	public static final String FOLDER_ID_REQUIRED = "folderId.required";
	public static final String ERROR_CREATE_LOG_FILE = "error.create";
	public static final String ERROR_FILE_NOT_FOUND_EXCEPTION = "error.fileNotFound";
	public static final String ERROR_FILE_IO_EXCEPTION = "error.fileIOException";
	public static final Map<String, String> ORACLE_DATA_TYPES;
	public static final Map<String, String> MYSQL_DATA_TYPES;
	public static final String XSD_SCHEMA_OPEN = "<xsd:schema targetNamespace=\"%1\" xmlns:tns=\"%2\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";
	public static final String XSD_SCHEMA_CLOSE = "</xsd:schema>";
	public static final String XSD_COMPLEX_TYPE_OPEN = "<xsd:complexType name=\"%1\">";
	public static final String XSD_COMPLEX_TYPE_CLOSE = "</xsd:complexType>";
	public static final String XSD_SEQUENCE_OPEN = "<xsd:sequence>";
	public static final String XSD_SEQUENCE_CLOSE = "</xsd:sequence>";
	public static final String XSD_ELEMENT_OPEN = "<xsd:element name=\"%1\" type=\"%2\" nillable=\"%3\" minOccurs=\"%4\" maxOccurs=\"%5\">";
	public static final String XSD_ELEMENT_CLOSE = "</xsd:element>";
	public static final String XSD_ANNOTATION_OPEN = "<xsd:annotation>";
	public static final String XSD_ANNOTATION_CLOSE = "</xsd:annotation>";
	public static final String XSD_APPINFO_OPEN = "<xsd:appinfo source=\"appian.jpa\">";
	public static final String XSD_APPINFO_CLOSE = "</xsd:appinfo>";
	public static final String ANN_TABLE = "@Table(name=\"%1\")";
	public static final String ANN_SECONDARY_TABLE = "@SecondaryTable??";
	public static final String ANN_SECONDARY_TABLES = "@SecondaryTables??";
	public static final String ANN_ID = "@Id";
	public static final String ANN_GENERATED_VALUE = "@GeneratedValue(strategy=\"%1\", generator=\"%2\")";
	public static final String ANN_SEQUENCE_GENERATOR = "@SequenceGenerator(name=\"%1\" sequenceName = \"%2\")";
	public static final String ANN_COLUMN = "@Column(name=\"%1\" columnDefinition=\"%2\")";
	public static final String ANN_JOIN_COLUMN = "@JoinColumn(name=\"%1\" nullable=\"%2\" unique=\"%3\")";
	public static final String ANN_JOIN_COLUMNS = "@JoinColumns(name=\"%1\")";
	public static final String ANN_MANY_TO_ONE = "@ManyToOne(cascade=%1)";
	public static final String ANN_ONE_TO_ONE = "@OneToOne??";
	public static final String ANN_ONE_TO_MANY = "@OneToMany??";
	public static final String ANN_MANY_TO_MANY = "@ManyToMany??";
	public static final String ANN_ORDER_BY = "@OrderBy??";
	public static final String ANN_TABLE_GENERATOR = "@TableGenerator??";


	static  {
		ORACLE_DATA_TYPES = new HashMap<String, String>();
		ORACLE_DATA_TYPES.put("NUMBER(19,0)", "xsd:long");
		ORACLE_DATA_TYPES.put("NUMBER(10,0)", "xsd:int");
		ORACLE_DATA_TYPES.put("NUMBER(19,2)", "xsd:decimal");
		ORACLE_DATA_TYPES.put("NUMBER", "xsd:decimal");
		ORACLE_DATA_TYPES.put("NUMBER(1,0)", "xsd:boolean");
		ORACLE_DATA_TYPES.put("VARCHAR2", "xsd:string");
		ORACLE_DATA_TYPES.put("TIMESTAMP", "xsd:dateTime");
		ORACLE_DATA_TYPES.put("DATE", "xsd:date");
		ORACLE_DATA_TYPES.put("FLOAT", "xsd:double");

		MYSQL_DATA_TYPES = new HashMap<String, String>();
		MYSQL_DATA_TYPES.put("bigint", "xsd:long");
		MYSQL_DATA_TYPES.put("integer", "xsd:int");
		MYSQL_DATA_TYPES.put("int", "xsd:int");
		MYSQL_DATA_TYPES.put("decimal", "xsd:decimal");
		MYSQL_DATA_TYPES.put("tinyint", "xsd:boolean");
		MYSQL_DATA_TYPES.put("varchar", "xsd:string");
		MYSQL_DATA_TYPES.put("datetime", "xsd:dateTime");
		MYSQL_DATA_TYPES.put("date", "xsd:date");
		MYSQL_DATA_TYPES.put("float", "xsd:float");
	}
}
