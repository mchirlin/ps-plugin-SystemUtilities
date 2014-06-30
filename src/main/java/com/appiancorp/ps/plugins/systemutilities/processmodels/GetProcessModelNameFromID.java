package com.appiancorp.ps.plugins.systemutilities.processmodels;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessDesignService;
import com.appiancorp.suiteapi.process.ProcessModel;

@CustomContentFunctionsCategory
public class GetProcessModelNameFromID {

	private static final Logger LOG = Logger.getLogger(GetProcessModelNameFromID.class);

	@Function
	public String getProcessModelNameFromId(ServiceContext sc,
			ProcessDesignService pds,
			@Parameter Long id) {
		
		String name = null;

		LOG.debug("Getting name for UUID " + id + "*");
		try {
			ProcessModel pm = pds.getProcessModel(id);
			name = pm.getName().get(sc.getLocale());
		}		
		catch (Exception e) {
		  e.printStackTrace();
		}
	    
	    return name;
	}
}