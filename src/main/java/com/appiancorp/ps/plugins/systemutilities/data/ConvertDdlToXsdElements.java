// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   ConvertDdlToXsdElements.java

package com.appiancorp.ps.plugins.systemutilities.data;

import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.knowledge.Document;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.appiancorp.ps.plugins.systemtools2:
//            Constants, XsdElement

public class ConvertDdlToXsdElements extends AppianSmartService {

            private static final Logger LOG = Logger.getLogger(ConvertDdlToXsdElements.class);
            private ContentService cs;
            private String ddl;
            private Long ddlFile;
            private String databaseType;
            private String tableName;
            private XsdElement xsdElements[];
            private String failedLines[];

            public ConvertDdlToXsdElements(ContentService cs) {
/*  48*/        this.cs = cs;
            }

            public void validate(MessageContainer messages) {
/*  53*/        if (ddl == null && ddlFile == null || ddl != null && ddlFile != null) {
/*  54*/            messages.addError("ddl", "validation.ddlNull", new Object[0]);
                }
            }

            public void run() throws SmartServiceException {
/*  64*/        try {
/*  64*/            if (ddl == null) {
/*  65*/                Document d = cs.download(ddlFile, ContentConstants.VERSION_CURRENT, Boolean.valueOf(false))[0];
/*  66*/                File file = new File(d.getInternalFilename());
/*  69*/                FileInputStream inputStream = new FileInputStream(file);
/*  70*/                ddl = IOUtils.toString(inputStream, "UTF-8");
                    }
/*  74*/            String tableRegEx = "";
/*  75*/            String fieldRegEx = "";
/*  76*/            String primaryRegEx = "";
/*  77*/            String notNullRegEx = "";
/*  78*/            if (databaseType.equals("oracle")) {
/*  79*/                tableRegEx = "CREATE TABLE \"\\w+\".\"(\\w+)\"";
/*  80*/                fieldRegEx = "\"(\\w+)\" ((\\w+)\\s?(\\((\\d+),(\\d+)\\)|\\((\\d+) (\\w+)\\)|\\((\\d+)\\))?)(\\s\\w+)?(\\s\\d+)?([\\w ]+)?";
/*  81*/                primaryRegEx = "ALTER TABLE \"\\w+\".\"\\w+\" ADD CONSTRAINT \"\\w+\" PRIMARY KEY \\(\"(\\w+)\"\\)";
/*  82*/                notNullRegEx = "ALTER TABLE \"\\w+\".\"\\w+\" MODIFY \\(\"(\\w+)\" NOT NULL ENABLE\\)";
                    } else
/*  83*/            if (databaseType.equals("mysql")) {
/*  84*/                tableRegEx = "CREATE TABLE `(\\w+)`";
/*  85*/                fieldRegEx = "`(\\w+)` ((\\w+)(\\(\\d+\\))?) ([\\w ]+)?";
/*  86*/                primaryRegEx = "PRIMARY KEY \\(`(.*)`\\)";
/*  87*/                notNullRegEx = "`(\\w+)` .*? NOT NULL";
                    }
/*  91*/            Pattern p = Pattern.compile(tableRegEx);
/*  92*/            Matcher m = p.matcher(ddl);
/*  94*/            for (tableName = ""; m.find(); tableName = m.group(1).trim()) { }
/* 100*/            p = Pattern.compile(fieldRegEx);
/* 101*/            m = p.matcher(ddl);
/* 103*/            Map xsdMap = new LinkedHashMap();
/* 104*/            List failedLineList = new ArrayList();
/* 105*/            List addedLineList = new ArrayList();
/* 106*/            List xsdElementList = new ArrayList();
/* 108*/            while (m.find())  {
/* 108*/                String logOutput = "";
/* 109*/                for (int i = 0; i < m.groupCount(); i++) {
/* 110*/                    logOutput = (new StringBuilder(String.valueOf(logOutput))).append(m.group(i)).append(" ").toString();
                        }

/* 113*/                LOG.debug(logOutput);
/* 115*/                if (databaseType.equals("oracle") && (Constants.ORACLE_DATA_TYPES.keySet().contains(m.group(2)) || Constants.ORACLE_DATA_TYPES.keySet().contains(m.group(3)))) {
/* 116*/                    String fieldName = m.group(1).trim();
/* 117*/                    String fieldType = calculateXsdType(databaseType, m.group(2).trim(), m.group(3).trim());
/* 118*/                    String fieldDesc = m.group(2).trim();
/* 120*/                    xsdMap.put(fieldName, new String[] {
/* 120*/                        fieldType, fieldDesc
                            });
/* 122*/                    addedLineList.add(m.group(0));
                        } else
/* 123*/                if (databaseType.equals("mysql") && (Constants.MYSQL_DATA_TYPES.keySet().contains(m.group(2)) || Constants.MYSQL_DATA_TYPES.keySet().contains(m.group(3)))) {
/* 124*/                    String fieldName = m.group(1).trim();
/* 125*/                    String fieldType = calculateXsdType(databaseType, m.group(2).trim(), m.group(3).trim());
/* 126*/                    String fieldDesc = m.group(2).trim();
/* 128*/                    xsdMap.put(fieldName, new String[] {
/* 128*/                        fieldType, fieldDesc
                            });
/* 130*/                    addedLineList.add(m.group(0));
                        } else {
/* 132*/                    failedLineList.add(m.group(0));
                        }
                    }
/* 135*/            failedLines = new String[failedLineList.size()];
/* 136*/            failedLines = (String[])failedLineList.toArray(new String[0]);
/* 139*/            p = Pattern.compile(primaryRegEx);
/* 140*/            m = p.matcher(ddl);
/* 142*/            String primaryField = "";
/* 143*/            String primaryFields[] = new String[0];
/* 145*/            while (m.find())  {
/* 145*/                primaryFields = m.group(1).trim().split("`");
/* 146*/                primaryField = primaryFields[0];
                    }
/* 150*/            p = Pattern.compile(notNullRegEx);
/* 151*/            m = p.matcher(ddl);
/* 153*/            List notNullFields = new ArrayList();
/* 155*/            for (; m.find(); notNullFields.add(m.group(1).trim())) { }
                    XsdElement xsdElement;
/* 159*/            for (Iterator iterator = xsdMap.keySet().iterator(); iterator.hasNext(); xsdElementList.add(xsdElement)) {
/* 159*/                String field = (String)iterator.next();
/* 160*/                String columnName = field;
/* 161*/                String fieldName = toCamelCase(field);
/* 162*/                String fieldType = ((String[])xsdMap.get(field))[0];
/* 163*/                String nillable = "true";
/* 164*/                String minOccurs = "0";
/* 165*/                String maxOccurs = "1";
/* 166*/                String columnDefinition = ((String[])xsdMap.get(field))[1];
/* 167*/                String primaryKey = "false";
/* 169*/                if (notNullFields.contains(field)) {
/* 170*/                    minOccurs = "1";
                        }
/* 173*/                if (primaryField.equals(field)) {
/* 174*/                    primaryKey = "true";
                        }
/* 177*/                xsdElement = new XsdElement(columnName, fieldName, fieldType, nillable, minOccurs, maxOccurs, columnDefinition, primaryKey);
                    }

/* 180*/            xsdElements = new XsdElement[xsdElementList.size()];
/* 181*/            xsdElements = (XsdElement[])xsdElementList.toArray(new XsdElement[0]);
                }
/* 183*/        catch (Exception e) {
/* 184*/            throw createException(e, "error.generic", new Object[] {
/* 184*/                e.getMessage()
                    });
                }
            }

            private String calculateXsdType(String database, String databaseTypeFull, String databaseTypeShort) {
/* 189*/        String xsdType = "";
/* 190*/        if (database.equals("oracle")) {
/* 191*/            if (databaseTypeFull.contains("NUMBER")) {
/* 192*/                xsdType = (String)Constants.ORACLE_DATA_TYPES.get(databaseTypeFull);
                    } else {
/* 194*/                xsdType = (String)Constants.ORACLE_DATA_TYPES.get(databaseTypeShort);
                    }
                } else
/* 196*/        if (databaseType.equals("mysql")) {
/* 197*/            xsdType = (String)Constants.MYSQL_DATA_TYPES.get(databaseTypeShort);
                }
/* 200*/        return xsdType;
            }

            private String toCamelCase(String s) {
/* 204*/        String parts[] = s.split("_");
/* 205*/        String camelCaseString = "";
/* 206*/        for (int i = 0; i < parts.length; i++) {
/* 207*/            if (i == 0) {
/* 208*/                camelCaseString = (new StringBuilder(String.valueOf(camelCaseString))).append(parts[i].toLowerCase()).toString();
                    } else {
/* 210*/                camelCaseString = (new StringBuilder(String.valueOf(camelCaseString))).append(toProperCase(parts[i])).toString();
                    }
                }

/* 213*/        return camelCaseString;
            }

            static String toProperCase(String s) {
/* 217*/        return (new StringBuilder(String.valueOf(s.substring(0, 1).toUpperCase()))).append(s.substring(1).toLowerCase()).toString();
            }

            private transient SmartServiceException createException(Throwable t, String key, Object args[]) {
/* 221*/        return (new com.appiancorp.suiteapi.process.exceptions.SmartServiceException.Builder(getClass(), t)).userMessage(key, args).build();
            }

            public void setDdl(String ddl) {
/* 227*/        this.ddl = ddl;
            }

            public void setDdlFile(Long ddlFile) {
/* 234*/        this.ddlFile = ddlFile;
            }

            public void setDatabaseType(String databaseType) {
/* 240*/        this.databaseType = databaseType;
            }

            public String getTableName() {
/* 245*/        return tableName;
            }

            public XsdElement[] getXsdElements() {
/* 250*/        return xsdElements;
            }

            public String[] getFailedLines() {
/* 255*/        return failedLines;
            }

}
