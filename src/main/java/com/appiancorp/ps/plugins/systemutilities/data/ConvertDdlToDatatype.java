package com.appiancorp.ps.plugins.systemutilities.data;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.knowledge.Document;
import com.appiancorp.suiteapi.knowledge.DocumentDataType;
import com.appiancorp.suiteapi.process.framework.Order;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

@PaletteInfo(paletteCategory="Appian Smart Services", palette="System Tools")
@Order({"ddl","ddlFile","databaseType"})
@CustomContentFunctionsCategory
public class ConvertDdlToDatatype  {

	private static final Logger LOG = Logger.getLogger(ConvertDdlToDatatype.class);

	@Function
	public Datatype convertddltodatatype(ContentService cs, @Parameter String databaseType, @Parameter(required=false) String ddl, @Parameter(required=false) @DocumentDataType Long ddlFile) {
		if (ddl.isEmpty() && ddlFile == null || !ddl.isEmpty() && ddlFile != null) {
			return null;
		}
		try {
			Datatype datatype = new Datatype();
			
			/* If ddl is blank, grab the ddl file */
			if (ddl.isEmpty()) {
				Document d = cs.download(ddlFile, ContentConstants.VERSION_CURRENT, Boolean.valueOf(false))[0];
				File file = new File(d.getInternalFilename());
				FileInputStream inputStream = new FileInputStream(file);
				ddl = IOUtils.toString(inputStream, "UTF-8");
			}
			String tableRegEx = "";
			String fieldRegEx = "";
			String primaryRegEx = "";
			String notNullRegEx = "";

			/* Set the regular expressions according to the database type */
			if (databaseType.equals("oracle")) {
				tableRegEx = "CREATE TABLE \"\\w+\".\"(\\w+)\"";
				fieldRegEx = "\"(\\w+)\" ((\\w+)\\s?(\\((\\d+),(\\d+)\\)|\\((\\d+) (\\w+)\\)|\\((\\d+)\\))?)(\\s\\w+)?(\\s\\d+)?([\\w ]+)?";
				primaryRegEx = "ALTER TABLE \"\\w+\".\"\\w+\" ADD CONSTRAINT \"\\w+\" PRIMARY KEY \\(\"(\\w+)\"\\)";
				notNullRegEx = "ALTER TABLE \"\\w+\".\"\\w+\" MODIFY \\(\"(\\w+)\" NOT NULL ENABLE\\)";
			} else if (databaseType.equals("mysql")) {
				tableRegEx = "CREATE TABLE `(\\w+)`";
				fieldRegEx = "`(\\w+)` ((\\w+)(\\(\\d+\\))?) ([\\w ]+)?";
				primaryRegEx = "PRIMARY KEY \\(`(.*)`\\)";
				notNullRegEx = "`(\\w+)` .*? NOT NULL";
			}

			/* Match Table Name */
			Pattern p = Pattern.compile(tableRegEx);
			Matcher m = p.matcher(ddl);
			while (m.find()) {
				datatype.setTableName(m.group(1));
			}

			/* Match Fields */
			p = Pattern.compile(fieldRegEx);
			m = p.matcher(ddl);
			Map<String, String[]> xsdMap = new LinkedHashMap<String, String[]>();
			List<String> failedLineList = new ArrayList<String>();
			List<String> addedLineList = new ArrayList<String>();
			while (m.find())  {
				String logOutput = "";
				for (int i = 0; i < m.groupCount(); i++) {
					logOutput = (new StringBuilder(String.valueOf(logOutput))).append(m.group(i)).append(" ").toString();
				}

				LOG.debug(logOutput);
				/* If found a new field */
				if (databaseType.equals("oracle") && (Constants.ORACLE_DATA_TYPES.keySet().contains(m.group(2)) || Constants.ORACLE_DATA_TYPES.keySet().contains(m.group(3)))) {
					String fieldName = m.group(1).trim();
					String fieldType = calculateXsdType(databaseType, m.group(2).trim(), m.group(3).trim(), databaseType);
					String fieldDesc = m.group(2).trim();
					xsdMap.put(fieldName, new String[] {fieldType, fieldDesc});
					addedLineList.add(m.group(0));
				} else	if (databaseType.equals("mysql") && (Constants.MYSQL_DATA_TYPES.keySet().contains(m.group(2)) || Constants.MYSQL_DATA_TYPES.keySet().contains(m.group(3)))) {
					String fieldName = m.group(1).trim();
					String fieldType = calculateXsdType(databaseType, m.group(2).trim(), m.group(3).trim(), databaseType);
					String fieldDesc = m.group(2).trim();
					xsdMap.put(fieldName, new String[] {fieldType, fieldDesc});
					addedLineList.add(m.group(0));
				} else {
					failedLineList.add(m.group(0));
				}
			}
			//failedLines = new String[failedLineList.size()];
			//failedLines = (String[])failedLineList.toArray(new String[0]);

			/* Match Primary Key */
			p = Pattern.compile(primaryRegEx);
			m = p.matcher(ddl);
			String primaryField = "";
			String primaryFields[] = new String[0];
			while (m.find())  {
				primaryFields = m.group(1).trim().split("`");
				primaryField = primaryFields[0];
			}

			/* Match Not Null Field */
			p = Pattern.compile(notNullRegEx);
			m = p.matcher(ddl);
			List<String> notNullFields = new ArrayList<String>();
			while (m.find())  {
				notNullFields.add(m.group(1).trim());
			}			

			/* Create Datatype Elements */
			Element e;
			for (Iterator<String> iterator = xsdMap.keySet().iterator(); iterator.hasNext(); datatype.addElement(e)) {
				String field = (String)iterator.next();

				Long minOccurs;
				if (notNullFields.contains(field)) {
					minOccurs = new Long(1);
				} else {
					minOccurs = new Long(0);
				}
				//String maxOccurs = null;

				Boolean primaryKey;
				if (primaryField.equals(field)) {
					primaryKey = true;
				} else { 
					primaryKey = false;
				}

				e = new Element();
				e.setFieldName(toCamelCase(field));
				e.setColumnName(field);
				e.setFieldType(((String[])xsdMap.get(field))[0]);
				e.setNillable(true);
				e.setMinOccurs(minOccurs);
				//e.setMaxOccurs(maxOccurs);
				e.setColumnDefinition(((String[])xsdMap.get(field))[1]);
				e.setPrimaryKey(primaryKey);
			}
			return datatype;
		}
		catch (Exception e) {
			return null;
		}
	}

	private String calculateXsdType(String database, String databaseTypeFull, String databaseTypeShort, String databaseType) {
		String xsdType = "";
		if (database.equals("oracle")) {
			if (databaseTypeFull.contains("NUMBER")) {
				xsdType = (String)Constants.ORACLE_DATA_TYPES.get(databaseTypeFull);
			} else {
				xsdType = (String)Constants.ORACLE_DATA_TYPES.get(databaseTypeShort);
			}
		} else if (databaseType.equals("mysql")) {
			xsdType = (String)Constants.MYSQL_DATA_TYPES.get(databaseTypeShort);
		}
		return xsdType;
	}

	private String toCamelCase(String s) {
		String parts[] = s.split("_");
		String camelCaseString = "";
		for (int i = 0; i < parts.length; i++) {
			if (i == 0) {
				camelCaseString = (new StringBuilder(String.valueOf(camelCaseString))).append(parts[i].toLowerCase()).toString();
			} else {
				camelCaseString = (new StringBuilder(String.valueOf(camelCaseString))).append(toProperCase(parts[i])).toString();
			}
		}

		return camelCaseString;
	}

	static String toProperCase(String s) {
		return (new StringBuilder(String.valueOf(s.substring(0, 1).toUpperCase()))).append(s.substring(1).toLowerCase()).toString();
	}
}
