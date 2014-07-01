package com.appiancorp.ps.plugins.systemutilities.expression;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessDesignService;

@CustomContentFunctionsCategory
public class InternalizeExpression {

	private static final Logger LOG = Logger.getLogger(InternalizeExpression.class);

	@Function
	public String internalizeExpression(ServiceContext sc,
			ContentService cs,
			ProcessDesignService pds,
			@Parameter String expression) {
		
		LOG.debug("Internalizing expression: *" + expression + "*");
		try {
			return pds.internalizeExpression(expression);
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	    
	    return null;
	}
}