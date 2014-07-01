package com.appiancorp.ps.plugins.systemutilities;

import com.appiancorp.suiteapi.content.ContentConstants;

public class ContentUtils {
	protected static String getContentObjectType(int type) {
		switch (type) {

		case ContentConstants.TYPE_COMMUNITY:
			return "Community";

		case ContentConstants.TYPE_COMMUNITY_KC:
			return "Knowledge Center";

		case ContentConstants.TYPE_PERSONAL_KC:
			return "Personal Knowledge Center";

		case ContentConstants.TYPE_FOLDER:
			return "Folder";

		case ContentConstants.TYPE_RULE:
			return "Rule or Constant";

		case ContentConstants.TYPE_DOCUMENT:
			return "Document";

		case ContentConstants.TYPE_APPLICATION:
			return "Application or Data Store";
		}
		return "";

	}
}
