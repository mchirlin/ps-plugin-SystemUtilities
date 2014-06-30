package com.appiancorp.ps.plugins.systemutilities.processmodels;

import java.util.Arrays;

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

@CustomContentFunctionsCategory
public class ProcessModelByUUIDDetailsRetriever {

  private static final Logger LOG = Logger.getLogger(ProcessModelByUUIDDetailsRetriever.class);  
 
  @Function
  public String getProcessModelDetailsByUUID(ServiceContext sc, ProcessDesignService pd,
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
      
      String pmName = processModel.getName().toString().replace("{", "[").replace("}", "]");

      ProcessModelFolder processFolder = pd.getFolder(processModel.getFolderId());

      return new String("Type: Process Model" +
        ", Name: " +
        pmName +
        ", Id:" +
        processModel.getId() +
        ", UUID:" +
        processModel.getUuid() +
        ", Parent: " +
        processFolder.getName() +
        ", Parent Id: " +
        processFolder.getId() +
        ", Location: " +
        formatModelLocation(Arrays.toString(processFolder.getAncestorFolderNames()),
          processFolder.getName()) + ", IsPublic: " + processModel.getIsPublic());

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

    return "";

  }

  private static String formatModelLocation(String modelLocation, String parentName) {
    if (modelLocation.equals("[]")) {
      return new String("[Process Models -> " + parentName + " ]");
    }
    return new String("[Process Models -> " +
      modelLocation.substring(1, modelLocation.length() - 1).replace(",", " ->") + " -> " +
      parentName + "]");
  }

}
