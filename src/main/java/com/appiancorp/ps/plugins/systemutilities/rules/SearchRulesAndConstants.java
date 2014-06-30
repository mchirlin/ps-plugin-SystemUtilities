package com.appiancorp.ps.plugins.systemutilities.rules;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.Constants;
import com.appiancorp.suiteapi.content.Content;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentFilter;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.type.system.LabelValue;

@CustomContentFunctionsCategory
public class SearchRulesAndConstants {

	private static final Logger LOG = Logger.getLogger(SearchRulesAndConstants.class);

	@Function
	public LabelValue[] searchRulesAndConstants(ServiceContext sc,
			ContentService cs,
			@Parameter String partialName) {
		
		LabelValue[] rules = null;

		LOG.debug("Fetching a list of all of the rules that fit this search: *" + partialName + "*");
		try {
			Content[] content = (Content[]) cs.queryByRootPaging(
					cs.getIdByUuid(ContentConstants.UUID_RULES_ROOT_FOLDER),
					"name: " + partialName,
					new ContentFilter(ContentConstants.TYPE_RULE),
					0,
					20,
					ContentConstants.COLUMN_NAME,
					Constants.SORT_ORDER_DESCENDING
			).getResults();
			
			// Construct label value rule list
			rules = new LabelValue[content.length];
			for (int i = 0; i < content.length; i++) {
				LabelValue lv = new LabelValue();
				lv.setLabel(content[i].getName());
				List<Object> value = new ArrayList<Object>();
				value.add(content[i].getUuid());
				lv.setValue(value);
				rules[i] = lv;
			}
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	    
		LOG.debug("Found number of rules: " + rules.length);
	    return rules;
	}
}