package com.appiancorp.ps.plugins.systemutilities;

import java.util.ArrayList;
import java.util.List;

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
import com.appiancorp.type.system.LabelValue;

@CustomContentFunctionsCategory
public class GetContentByUUID {

	private static final Logger LOG = Logger.getLogger(GetContentByUUID.class);

	@Function
	public LabelValue getContentByUUID(ServiceContext sc, ContentService cs,
			ProcessDesignService pds, @Parameter String objectUUID) {
		String objectInformation = new String("Content Object [");
		String deleteObjectName;
		try {
			Long id = cs.getIdByUuid(objectUUID);
			Content content;
			if (id == null) {
				deleteObjectName = pds.internalizeExpression("=#\"" + objectUUID + "\"");
				String label = deleteObjectName.contains("#") ? "No object with this UUID has been found"
						: deleteObjectName.replace('=', ' ');
				String type = label.equals("No object with this UUID has been found")? "": "This rule has been deleted";
				
				LabelValue lv = new LabelValue();
				
				// Set Label as Name
				lv.setLabel(label);

				// Set Values as UUID, ID, Description, Parent Name, Parent ID, Type
				List<Object> value = new ArrayList<Object>();
				value.add(" ");
				value.add(" ");
				value.add(" ");
				value.add(" ");
				value.add(" ");
				value.add(type);
				lv.setValue(value);

				return lv;
			}
			content = cs.getVersion(id, ContentConstants.VERSION_CURRENT);

			LabelValue lv = new LabelValue();

			// Set Label as Name
			lv.setLabel(content.getName());

			// Set Values as UUID, ID, Description, Parent Name, Parent ID, Type
			List<Object> value = new ArrayList<Object>();
			value.add(content.getUuid());
			value.add(content.getId());
			value.add(content.getDescription());
			value.add(content.getParentName());
			value.add(content.getParent());
			value.add(ContentUtils.getContentObjectType(content.getType()));
			lv.setValue(value);
			
			return lv;

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

		return null;
	}
}