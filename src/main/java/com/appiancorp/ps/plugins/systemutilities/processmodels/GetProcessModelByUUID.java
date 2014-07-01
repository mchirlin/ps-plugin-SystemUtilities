package com.appiancorp.ps.plugins.systemutilities.processmodels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.exceptions.InvalidFolderException;
import com.appiancorp.suiteapi.common.exceptions.InvalidProcessModelException;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessDesignService;
import com.appiancorp.suiteapi.process.ProcessModel;
import com.appiancorp.suiteapi.process.ProcessModelFolder;
import com.appiancorp.suiteapi.type.Type;
import com.appiancorp.type.system.LabelValue;

@CustomContentFunctionsCategory
public class GetProcessModelByUUID {

	private static final Logger LOG = Logger.getLogger(GetProcessModelByUUID.class);  

	@Function
	public LabelValue getProcessModelByUUID(ServiceContext sc, ProcessDesignService pd,
			ContentService cs, @Parameter String processModelUUIDOrId) {

		ProcessModel processModel;
		try {

			try {        
				processModel = pd.getProcessModel(Long.parseLong(processModelUUIDOrId));
				LOG.debug("Model ID passed");
			} catch (NumberFormatException e) {
				LOG.debug("Model UUID passed");
				processModel = pd.getProcessModelByUuid(processModelUUIDOrId);
			}
			
			ProcessModelFolder processFolder = pd.getFolder(processModel.getFolderId());
			
			LabelValue lv = new LabelValue();
			
			// Set Label as Name
			lv.setLabel(processModel.getName().get(sc.getLocale()));

			// Set Values as UUID, ID, Description, Folder, Public
			List<Object> value = new ArrayList<Object>();
			value.add(processModel.getUuid());
			value.add(processModel.getId());
			value.add(processModel.getDescription().get(sc.getLocale()));
			value.add(formatModelLocation(Arrays.toString(processFolder.getAncestorFolderNames()),
					processFolder.getName()));
			value.add(processModel.getIsPublic());
			lv.setValue(value);

			return lv;
			
		} catch (InvalidProcessModelException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();      
		} catch (PrivilegeException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (InvalidFolderException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

		return null;

	}

	private static String formatModelLocation(String modelLocation, String parentName) {
		if (modelLocation.equals("[]")) {
			return new String("[Process Models -> " + parentName + "]");
		}
		return new String("[Process Models -> " +
				modelLocation.substring(1, modelLocation.length() - 1).replace(",", " ->") + " -> " +
				parentName + "]");
	}

}
