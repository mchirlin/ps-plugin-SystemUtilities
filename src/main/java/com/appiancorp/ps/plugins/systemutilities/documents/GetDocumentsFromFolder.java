package com.appiancorp.ps.plugins.systemutilities.documents;

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
import com.appiancorp.suiteapi.knowledge.DocumentDataType;
import com.appiancorp.suiteapi.knowledge.FolderDataType;

@CustomContentFunctionsCategory
public class GetDocumentsFromFolder {

	private static final Logger LOG = Logger.getLogger(GetDocumentsFromFolder.class);

	@Function
	@DocumentDataType
	public Long[] getdocumentsfromfolder(ServiceContext sc,
			ContentService cs,
			@Parameter @FolderDataType Long rootFolder,
			@Parameter Boolean recursiveSearch) {
		
		Long[] documentIds=null;;

		LOG.debug("Fetching a list of all documents for folder ID "+rootFolder);
		try {
		  if(recursiveSearch) {
			documentIds = cs.getAllChildrenIds(rootFolder, new ContentFilter(ContentConstants.TYPE_DOCUMENT), new Integer(ContentConstants.GC_MOD_POPULATE_TYPES_OF_CHILDREN));	  
		  }
		  else {
			documentIds = cs.getChildrenIds(rootFolder, new ContentFilter(ContentConstants.TYPE_DOCUMENT), new Integer(ContentConstants.GC_MOD_POPULATE_TYPES_OF_CHILDREN));  
		  }
		}
		catch (InvalidContentException e) {
		  e.printStackTrace();
		}
		catch (InvalidTypeMaskException e) {
		  e.printStackTrace();
		}
	    
		LOG.debug("Found number of documents: " + documentIds.length);
	    return documentIds;
	}
}
