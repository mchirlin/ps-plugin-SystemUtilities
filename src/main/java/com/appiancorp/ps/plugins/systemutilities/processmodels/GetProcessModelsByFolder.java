package com.appiancorp.ps.plugins.systemutilities.processmodels;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.Constants;
import com.appiancorp.suiteapi.common.paging.PagingInfo;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessDesignService;
import com.appiancorp.suiteapi.process.ProcessModel;
import com.appiancorp.type.system.LabelValue;

@CustomContentFunctionsCategory
public class GetProcessModelsByFolder {

	private static final Logger LOG = Logger.getLogger(GetProcessModelsByFolder.class);

	@Function
	public LabelValue[] getProcessModelsByFolder(ServiceContext sc,
			ProcessDesignService pds,
			@Parameter Long folderId,
			@Parameter PagingInfo pagingInfo) {
		
		LabelValue[] processModels = null;

		LOG.debug("Fetching a list of all of the process models in this folder: " + folderId);
		try {
			ProcessModel.Descriptor[] pmds = (ProcessModel.Descriptor[])pds.getProcessModelsForFolder(
					folderId,
					pagingInfo.getStartIndex(),
					pagingInfo.getBatchSize(),
					ProcessModel.Descriptor.SORT_PM_NAME,
					Constants.SORT_ORDER_DESCENDING
			).getResults();
			
			// Construct label value rule list
			processModels = new LabelValue[pmds.length];
			for (int i = 0; i < pmds.length; i++) {
				LabelValue lv = new LabelValue();
				lv.setLabel(pmds[i].getName().get(sc.getLocale()));
				List<Object> value = new ArrayList<Object>();
				value.add(pmds[i].getId());
				value.add(pmds[i].getDescription().get(sc.getLocale()));
				lv.setValue(value);
				processModels[i] = lv;
			}
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	    
		LOG.debug("Found number of process models: " + processModels.length);
	    return processModels;
	}
}