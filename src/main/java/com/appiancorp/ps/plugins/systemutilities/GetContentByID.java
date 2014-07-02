package com.appiancorp.ps.plugins.systemutilities;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.appiancorp.type.system.LabelValue;

@CustomContentFunctionsCategory
public class GetContentByID {

	private static final Logger LOG = Logger.getLogger(GetContentByID.class);

	@Function
	public LabelValue getContentByID(ServiceContext sc, ContentService cs, @Parameter(required=true) Long id) {

		try {
			Content content = cs.getVersion(id, ContentConstants.VERSION_CURRENT);

			LabelValue lv = new LabelValue();

			// Set Label as Name
			lv.setLabel(content.getName());

			// Set Values as UUID, ID, Description, Parent Name, Parent ID, Type, (Doc) Internal Filename
			List<Object> value = new ArrayList<Object>();
			value.add(content.getUuid());
			value.add(content.getId());
			value.add(content.getDescription());
			value.add(content.getParentName());
			value.add(content.getParent());
			value.add(ContentUtils.getContentObjectType(content.getType()));
			if (content.getType().equals(ContentConstants.TYPE_DOCUMENT)) value.add(cs.getInternalFilename(id));
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

		LabelValue lv = new LabelValue();
		
		// Set Label as Name
		lv.setLabel("No object with this ID has been found");

		// Set Values as UUID, ID, Description, Parent Name, Parent ID, Type
		List<Object> value = new ArrayList<Object>();
		value.add(" ");
		value.add(" ");
		value.add(" ");
		value.add(" ");
		value.add(" ");
		value.add(" ");
		lv.setValue(value);
		
		return lv;
	}
}
