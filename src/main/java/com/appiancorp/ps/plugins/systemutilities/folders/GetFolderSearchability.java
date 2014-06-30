package com.appiancorp.ps.plugins.systemutilities.folders;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.exceptions.InvalidVersionException;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.content.Content;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.knowledge.FolderDataType;

@CustomContentFunctionsCategory
public class GetFolderSearchability {

	private static final Logger LOG = Logger.getLogger(GetFolderSearchability.class);

	@Function
	public Boolean getfoldersearchability(ServiceContext sc,
			ContentService cs,
			@Parameter @FolderDataType Long folderToCheck) {
		
		Content c;
		String binaryVisibility = null;
		Boolean isCurrentlySearchable = false;
		
		try {
	      LOG.debug("Fetching current version of folder ID "+folderToCheck);
		  c = cs.getVersion(folderToCheck, ContentConstants.VERSION_CURRENT);
		  
		  binaryVisibility = Integer.toBinaryString(c.getVisibility());
		  if(c.getVisibility() > 1) {
			isCurrentlySearchable = binaryVisibility.charAt(binaryVisibility.length()-2) == '0' ? false : true;
		  }
		  LOG.debug("Current visibility of folder is: "+c.getVisibility()+" ("+binaryVisibility+")");
		  LOG.debug("Current searchability of folder is: "
				     +(binaryVisibility.charAt(binaryVisibility.length()-2) == '0' ? "Disabled" : "Enabled"));
		}
		catch (InvalidContentException e) {
			e.printStackTrace();
		} catch (InvalidVersionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isCurrentlySearchable;
	}

}
