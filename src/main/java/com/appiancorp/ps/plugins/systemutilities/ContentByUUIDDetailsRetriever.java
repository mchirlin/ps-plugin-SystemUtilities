package com.appiancorp.ps.plugins.systemutilities;

import org.apache.log4j.Logger;

import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.exceptions.InvalidVersionException;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.content.Content;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessDesignService;

@CustomContentFunctionsCategory
public class ContentByUUIDDetailsRetriever {

  private static final Logger LOG = Logger.getLogger(ContentByUUIDDetailsRetriever.class);

  @Function
  public String getContentDetailsByUUID(ServiceContext sc, ContentService cs,
    ProcessDesignService pds, @Parameter String objectUUID) {
    String objectInformation = new String("Content Object [");
    String deleteObjectName;
    try {
      Long id = cs.getIdByUuid(objectUUID);
      Content document;
      if (id == null) {
        deleteObjectName = pds.internalizeExpression("=#\"" + objectUUID + "\"");
        return deleteObjectName.contains("#") ? "No object with this UUID has been found"
          : "This UUID belongs to a deleted rule or constant : [" + deleteObjectName.replace('=', ' ') + " ]";
      }
      document = cs.getVersion(id, ContentConstants.VERSION_CURRENT);
      switch (document.getType()) {

      case ContentConstants.TYPE_COMMUNITY:
        objectInformation = new String("Type: Community");
        break;
      case ContentConstants.TYPE_COMMUNITY_KC:
        objectInformation = new String("Type: Knowledge Center");
        break;
      case ContentConstants.TYPE_PERSONAL_KC:
        objectInformation = new String("Type: Personal Knowledge Center");
        break;
      case ContentConstants.TYPE_FOLDER:
        objectInformation = new String("Type: Folder");
        break;
      case ContentConstants.TYPE_RULE:
        objectInformation = new String("Type: Rule or Constant");
        break;
      case ContentConstants.TYPE_DOCUMENT:
        objectInformation = new String("Type: Document");
        break;
      case ContentConstants.TYPE_APPLICATION:
        objectInformation = new String("Type:Application or Data Store");
        break;
      }
      objectInformation = "Content Object [Name: " + document.getDisplayName() + ", Id: " + document.getId() +
        ", Parent: " + document.getParentName() + ", Parent Id: " + document.getParent() + ", " + objectInformation +"]";

    } catch (InvalidContentException e) {
      LOG.error(e.getMessage());
      e.printStackTrace();

    } catch (InvalidVersionException e) {
      LOG.error(e.getMessage());
      e.printStackTrace();

    } catch (PrivilegeException e) {
      LOG.error(e.getMessage());
      e.printStackTrace();
    }

    return objectInformation;
  }
}