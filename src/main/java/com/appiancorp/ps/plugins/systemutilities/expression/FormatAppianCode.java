package com.appiancorp.ps.plugins.systemutilities.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessDesignService;

@CustomContentFunctionsCategory
public class FormatAppianCode {

	private static final Logger LOG = Logger.getLogger(FormatAppianCode.class);

	@Function
	public String formatAppianCode(ServiceContext sc,
			ContentService cs,
			ProcessDesignService pds,
			@Parameter String input) {

		LOG.debug("Formatting Appian Code: *" + input + "*");
		try {
			String indented = input
					.replaceAll("\\s+", "")
					.replace(",", ",\n")
					.replace(")", "\n)")
					.replace("}", "\n}")
					.replace("(", "(\n")
					.replace("{", "{\n");

			Pattern p = Pattern.compile(".*");
			Matcher m = p.matcher(indented);
			StringBuffer sb = new StringBuffer();
			int count = 0;
			while(m.find()) {
				if(!m.group().isEmpty()) {
					// Calculate how many tabs to add
					Integer openParenths = StringUtils.countMatches(indented.substring(0, m.start()), "(");
					Integer closedParenths = StringUtils.countMatches(indented.substring(0, m.start()), ")");
					Integer openBrackets = StringUtils.countMatches(indented.substring(0, m.start()), "{");
					Integer closedBrackets = StringUtils.countMatches(indented.substring(0, m.start()), "}");
					Integer numTabs = openParenths + openBrackets - closedParenths - closedBrackets;
					if (m.group().contains(")") || m.group().contains("}")) numTabs--;
					
					String tabs = "";
					for(int i = 0 ; i <  numTabs; i++) {
						tabs += "  ";
					}
					
					// Add space after colons
					String group = m.group().replace(":", ": ");
					
					m.appendReplacement(sb, tabs + group);
				}
			}
			m.appendTail(sb);

			return sb.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}