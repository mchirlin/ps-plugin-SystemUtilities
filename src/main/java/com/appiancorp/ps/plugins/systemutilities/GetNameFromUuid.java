package com.appiancorp.ps.plugins.systemutilities;

import org.apache.log4j.Logger;

import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.content.Content;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;

@CustomContentFunctionsCategory
public class GetNameFromUuid {

	private static final Logger LOG = Logger.getLogger(GetNameFromUuid.class);

	@Function
	public String getNameFromUuid(ServiceContext sc,
			ContentService cs,
			@Parameter String uuid) {
		
		String name = null;

		LOG.debug("Getting name for UUID " + uuid + "*");
		try {
			Content c = cs.getVersion(uuid, ContentConstants.VERSION_CURRENT);
			name = c.getName();
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	    
	    return name;
	}
}