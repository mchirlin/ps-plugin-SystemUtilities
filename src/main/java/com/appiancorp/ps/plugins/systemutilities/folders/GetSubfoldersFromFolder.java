package com.appiancorp.ps.plugins.systemutilities.folders;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentFilter;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;
import com.appiancorp.suiteapi.content.exceptions.InvalidTypeMaskException;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.knowledge.FolderDataType;

@CustomContentFunctionsCategory
public class GetSubfoldersFromFolder {

	private static final Logger LOG = Logger.getLogger(GetSubfoldersFromFolder.class);

	@Function
	@FolderDataType
	public Long[] getsubfoldersfromfolder(ServiceContext sc,
			ContentService cs, 
			@Parameter @FolderDataType Long rootFolder,
			@Parameter Boolean recursiveSearch) {
		
		Long[] folderIds=null;

		LOG.debug("Fetching a list of all subfolders for folder ID "+rootFolder);
		try {
		  if(recursiveSearch) {
	        folderIds = cs.getAllChildrenIds(rootFolder, new ContentFilter(ContentConstants.TYPE_FOLDER), new Integer(ContentConstants.GC_MOD_POPULATE_TYPES_OF_CHILDREN));	
		  }
		  else {
		    folderIds = cs.getChildrenIds(rootFolder, new ContentFilter(ContentConstants.TYPE_FOLDER), new Integer(ContentConstants.GC_MOD_POPULATE_TYPES_OF_CHILDREN));
		  }
	    }
		catch (InvalidContentException e) {
		  e.printStackTrace();
		}
	    catch (InvalidTypeMaskException e) {
	      e.printStackTrace();
	    }
	    
	    LOG.debug("Found number of folders: " + folderIds.length);
	    return folderIds;
	}
}
