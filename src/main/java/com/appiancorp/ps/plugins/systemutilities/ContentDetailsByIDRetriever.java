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

@CustomContentFunctionsCategory
public class ContentDetailsByIDRetriever {

  private static final Logger LOG = Logger.getLogger(ContentDetailsByIDRetriever.class);

  @Function
  public String getContentObjectDetailsByID(ServiceContext sc, ContentService cs, @Parameter(required=true) Long id) {

    try {
      Content content = cs.getVersion(id, ContentConstants.VERSION_CURRENT);
      return "Content Object [Name: " + content.getDisplayName() + ", UUID: " + content.getUuid() +
        ", Parent: " + content.getParentName() + ", Parent Id: " + content.getParent() + ", " + getContentObjectType(content.getType()) +
        "]";
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

    return "No object with this ID has been found";
  }

  private String getContentObjectType(int type) {
    switch (type) {

    case ContentConstants.TYPE_COMMUNITY:
      return "Type: Community";

    case ContentConstants.TYPE_COMMUNITY_KC:
      return "Type: Knowledge Center";

    case ContentConstants.TYPE_PERSONAL_KC:
      return "Type: Personal Knowledge Center";

    case ContentConstants.TYPE_FOLDER:
      return "Type: Folder";

    case ContentConstants.TYPE_RULE:
      return "Type: Rule or Constant";

    case ContentConstants.TYPE_DOCUMENT:
      return "Type: Document";

    case ContentConstants.TYPE_APPLICATION:
      return "Type: Application or Data Store";
    }
    return "";

  }

}
