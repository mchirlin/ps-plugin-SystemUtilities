package com.appiancorp.ps.plugins.systemutilities.processmodels;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.Constants;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentFilter;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;
import com.appiancorp.suiteapi.content.exceptions.InvalidTypeMaskException;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.knowledge.FolderDataType;
import com.appiancorp.suiteapi.process.ProcessDesignService;
import com.appiancorp.suiteapi.process.ProcessModelFolder;
import com.appiancorp.type.system.LabelValue;

@CustomContentFunctionsCategory
public class GetTopLevelProcessModelFolders {

	private static final Logger LOG = Logger.getLogger(GetTopLevelProcessModelFolders.class);

	@Function
	public LabelValue[] getTopLevelProcessModelFolders(ServiceContext sc,
			ContentService cs, ProcessDesignService pds) {

		LabelValue[] folders =null;

		LOG.debug("Fetching a list of all top level process model folders");
		try {
			ProcessModelFolder[] fs = (ProcessModelFolder[])pds.getTopLevelFolders(
					0, 
					Constants.COUNT_ALL,
					ProcessModelFolder.SORT_BY_NAME, 
					Constants.SORT_ORDER_DESCENDING
					).getResults();

			// Construct label value folders list
			folders = new LabelValue[fs.length];
			for (int i = 0; i < fs.length; i++) {
				LabelValue lv = new LabelValue();
				lv.setLabel(fs[i].getName());
				List<Object> value = new ArrayList<Object>();
				value.add("Folder");
				value.add(fs[i].getId());
				value.add(fs[i].getNumberOfProcessModels());
				lv.setValue(value);
				folders[i] = lv;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		LOG.debug("Found number of folders: " + folders.length);
		return folders;
	}
}
