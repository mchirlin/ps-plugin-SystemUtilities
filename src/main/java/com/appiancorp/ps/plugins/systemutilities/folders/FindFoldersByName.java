package com.appiancorp.ps.plugins.systemutilities.folders;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.exceptions.ExpressionException;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentFilter;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.InvalidTypeMaskException;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.knowledge.FolderDataType;
import com.appiancorp.suiteapi.knowledge.KnowledgeCenterDataType;

@CustomContentFunctionsCategory
public class FindFoldersByName {

	private static final Logger LOG = Logger.getLogger(FindFoldersByName.class);

	@Function
	@FolderDataType
	public Long[] findfoldersbyname(ServiceContext sc,
			ContentService cs,
			@Parameter Boolean searchAllContent,
			@Parameter String folderNameToSearchFor,
			@Parameter(required = false) @FolderDataType Long rootFolder,
			@Parameter(required = false) @KnowledgeCenterDataType Long rootKC) {
		
		ContentFilter cf = new ContentFilter(ContentConstants.TYPE_FOLDER, ContentConstants.SUBTYPE_FOLDER_KNOWLEDGE);
		Long[] searchResults = null;
		Long objectToSearchWithin = null;
		
		try {
		  if(searchAllContent) {
   		    objectToSearchWithin = cs.getIdByUuid(com.appiancorp.suiteapi.content.ContentConstants.UUID_COMMUNITY_ROOT);
		    LOG.debug("Searching for folders named `"+folderNameToSearchFor+"' within all content");
		  }
		  else if((rootKC == null || rootKC == 0) && (rootFolder == null || rootFolder == 0)) {
		    throw new ExpressionException("No root folder has been specified. If searchAllContent is set to false, either rootKC or rootFolder must be given a value.");
		  }
		  else {
		    if(rootFolder == null || rootFolder == 0) {
		      objectToSearchWithin = rootKC;
		      LOG.debug("Searching for folders named `"+folderNameToSearchFor+"' within specified KC ID "+objectToSearchWithin);
		    }
		    else {
			  objectToSearchWithin = rootFolder;
			  LOG.debug("Searching for folders named `"+folderNameToSearchFor+"' within specified folder ID "+objectToSearchWithin);
		    }
		  }
		  searchResults = cs.queryIdsByRoot(objectToSearchWithin, "name:"+folderNameToSearchFor, cf);
		  LOG.debug("Found number of folders matching search query: "+(searchResults == null ? 0 : searchResults.length));
		} catch (InvalidTypeMaskException e) {
		  LOG.error("InvalidTypeMaskException",e);
		} catch (ExpressionException e) {
		  LOG.error("ExpressionException",e);
		}
		return searchResults;
	}

}
