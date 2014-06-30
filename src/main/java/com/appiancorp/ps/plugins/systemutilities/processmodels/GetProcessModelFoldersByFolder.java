package com.appiancorp.ps.plugins.systemutilities.processmodels;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.Constants;
import com.appiancorp.suiteapi.common.paging.PagingInfo;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessDesignService;
import com.appiancorp.suiteapi.process.ProcessModelFolder;
import com.appiancorp.type.system.LabelValue;

@CustomContentFunctionsCategory
public class GetProcessModelFoldersByFolder {

	private static final Logger LOG = Logger.getLogger(GetProcessModelFoldersByFolder.class);

	@Function
	public LabelValue[] getProcessModelFoldersByFolder(ServiceContext sc,
			ProcessDesignService pds,
			@Parameter Long folderId,
			@Parameter PagingInfo pagingInfo) {
		
		LabelValue[] processModelFolders = null;

		LOG.debug("Fetching a list of all of the process model folders in this folder: *" + folderId + "*");
		try {
			ProcessModelFolder[] pmfs = (ProcessModelFolder[])pds.getChildFolders(
					folderId,
					pagingInfo.getStartIndex(),
					pagingInfo.getBatchSize(),
					ContentConstants.COLUMN_NAME,
					Constants.SORT_ORDER_DESCENDING
			).getResults();
			
			// Construct label value rule list
			processModelFolders = new LabelValue[pmfs.length];
			for (int i = 0; i < pmfs.length; i++) {
				LabelValue lv = new LabelValue();
				lv.setLabel(pmfs[i].getName());
				List<Object> value = new ArrayList<Object>();
				value.add("Folder");
				value.add(pmfs[i].getId());
				value.add(pmfs[i].getNumberOfProcessModels());
				lv.setValue(value);
				processModelFolders[i] = lv;
			}
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	    
		LOG.debug("Found number of processModelFolders: " + processModelFolders.length);
	    return processModelFolders;
	}
}