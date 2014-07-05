package com.appiancorp.ps.plugins.systemutilities.expression;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessDesignService;

@CustomContentFunctionsCategory
public class FormatJson {

	private static final Logger LOG = Logger.getLogger(FormatJson.class);

	@Function
	public String formatJson(ServiceContext sc,
			ContentService cs,
			ProcessDesignService pds,
			@Parameter String input) {
		
		LOG.debug("Formatting json: *" + input + "*");
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(input, Object.class);
			String indented = mapper.defaultPrettyPrintingWriter().writeValueAsString(json);
			return indented;
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	    
	    return null;
	}
}