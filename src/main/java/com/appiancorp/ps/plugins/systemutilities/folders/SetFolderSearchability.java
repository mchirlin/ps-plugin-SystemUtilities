package com.appiancorp.ps.plugins.systemutilities.folders;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.exceptions.InvalidUserException;
import com.appiancorp.suiteapi.common.exceptions.InvalidVersionException;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.content.Content;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentFilter;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.DuplicateUuidException;
import com.appiancorp.suiteapi.content.exceptions.IllegalRecursionException;
import com.appiancorp.suiteapi.content.exceptions.InsufficientNameUniquenessException;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;
import com.appiancorp.suiteapi.content.exceptions.InvalidTypeMaskException;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.knowledge.FolderDataType;

@CustomContentFunctionsCategory
public class SetFolderSearchability {

	private static final Logger LOG = Logger.getLogger(SetFolderSearchability.class);

	@Function
	public Boolean setfoldersearchability(ServiceContext sc,
			ContentService cs,
			@Parameter @FolderDataType Long folderToModify,
			@Parameter Boolean makeFolderSearchable,
			@Parameter Boolean recursiveUpdate) {
		
		return updateFoldersAndDocuments(folderToModify, cs, makeFolderSearchable, recursiveUpdate);
	}
	
	private Boolean updateFoldersAndDocuments(Long rootFolder, ContentService cs, Boolean makeFolderSearchable, Boolean recursive) {
	  Boolean didValueChange = false;
	  Long[] folderIds = null;
	  Long[] documentIds = null;
	  
	  Content c;
		
      Integer intVisibility = null;
	  String binaryVisibility = null;
		
	  StringBuilder builderString;
	  Integer[] fields = new Integer[] { ContentConstants.COLUMN_VISIBILITY };

      try {
    	if(recursive) {
		  folderIds = cs.getChildrenIds(rootFolder, new ContentFilter(ContentConstants.TYPE_FOLDER), new Integer(ContentConstants.GC_MOD_POPULATE_TYPES_OF_CHILDREN));
		  if(folderIds.length > 0) {
	        for(int i=0; i<folderIds.length; i++) {
	    	    updateFoldersAndDocuments(folderIds[i], cs, makeFolderSearchable, true);
	        }
		  }
    	}
    	
		LOG.debug("Fetching all immediate child documents from folder ID "+rootFolder+" for modification");
		documentIds = cs.getChildrenIds(rootFolder, new ContentFilter(ContentConstants.TYPE_DOCUMENT), new Integer(ContentConstants.GC_MOD_POPULATE_TYPES_OF_CHILDREN));
		LOG.debug("Updating searchability for "+documentIds.length+" documents in folder ID "+rootFolder);
		    
		for(int i=0; i<documentIds.length;i++) {
		  c = cs.getVersion(documentIds[i], ContentConstants.VERSION_CURRENT);
		  intVisibility = c.getVisibility();
		  binaryVisibility = Integer.toBinaryString(intVisibility);
			  
	      builderString = new StringBuilder(binaryVisibility);
		  if(intVisibility>1) {
			builderString.setCharAt(binaryVisibility.length()-2, (makeFolderSearchable ? '1' : '0'));
		  }
		  else {
			builderString.insert(0,(makeFolderSearchable ? '1' : '0'));
		  }
	      c.setVisibility(Integer.parseInt(builderString.toString(), 2));
	      cs.updateFields(c, fields, ContentConstants.UNIQUE_NONE);
		}
		
		LOG.debug("Fetching current version of folder ID "+rootFolder);
		c = cs.getVersion(rootFolder, ContentConstants.VERSION_CURRENT);
		  
		intVisibility = c.getVisibility();
		binaryVisibility = Integer.toBinaryString(intVisibility);
		LOG.debug("Current visibility of folder is: "+intVisibility+" ("+binaryVisibility+")");
		LOG.debug("Current searchability of folder is: "+(binaryVisibility.length() > 1 && binaryVisibility.charAt(binaryVisibility.length()-2) == '1' ? "Enabled" : "Disabled"));
		didValueChange = !(makeFolderSearchable && binaryVisibility.charAt(binaryVisibility.length()-2) == '1' ? true : false); 
			  
		builderString = new StringBuilder(binaryVisibility);
		if(intVisibility>1) {
		  builderString.setCharAt(binaryVisibility.length()-2, (makeFolderSearchable ? '1' : '0'));
		}
		else {
		  builderString.insert(0,(makeFolderSearchable ? '1' : '0'));
		}
			
		c.setVisibility(Integer.parseInt(builderString.toString(), 2));
		cs.updateFields(c, fields, ContentConstants.UNIQUE_NONE);
		
		intVisibility = c.getVisibility();
		binaryVisibility = Integer.toBinaryString(intVisibility);
		LOG.debug("Value of visibility for content item has been updated. New visibility of folder is: "+intVisibility+" ("+binaryVisibility+")");
		LOG.debug("Current searchability of folder is: " +(binaryVisibility.charAt(binaryVisibility.length()-2) == '0' ? "Disabled" : "Enabled"));
	  } catch (InvalidContentException e) {
		e.printStackTrace();
	  } catch (InvalidTypeMaskException e) {
		e.printStackTrace();
	  } catch (InvalidVersionException e) {
		e.printStackTrace();
  	  } catch (PrivilegeException e) {
		e.printStackTrace();
	  } catch (InvalidUserException e) {
		e.printStackTrace();
	  } catch (IllegalRecursionException e) {
		e.printStackTrace();
	  } catch (DuplicateUuidException e) {
		e.printStackTrace();
	  } catch (InsufficientNameUniquenessException e) {
		e.printStackTrace();
	  }
	  
	  return didValueChange;
	}
}
