package com.appiancorp.ps.plugins.systemutilities.data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.ps.plugins.systemutilities.SearchUtils;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.Constants;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.type.Datatype;
import com.appiancorp.suiteapi.type.TypeService;
import com.appiancorp.type.system.LabelValue;

@CustomContentFunctionsCategory
public class SearchDataTypes {

	private static final Logger LOG = Logger.getLogger(SearchDataTypes.class);

	@Function
	public LabelValue[] searchDatatypes(ServiceContext sc,
			TypeService ts,
			@Parameter String partialName) {
		
		LabelValue[] types = null;

		LOG.debug("Fetching a list of all of the data types that fit this search: *" + partialName + "*");
		try {
			Datatype[] datatypes = (Datatype[]) ts.getTypesFilteredPaging(
					Datatype.FLAG_SYSTEM,
					TypeService.LISTS_FILTER_ALL,
					0,
					Constants.COUNT_ALL,
					Datatype.SORT_BY_NAME,
					Constants.SORT_ORDER_DESCENDING
			).getResults();
			
			// Construct label value data type list
			List<LabelValue> typeList = new ArrayList<LabelValue>();
			for (int i = 0; i < datatypes.length; i++) {
				if(SearchUtils.wildCardMatch(datatypes[i].getName(), partialName)) {
					LabelValue lv = new LabelValue();
					
					// Set Label as Name
					lv.setLabel(datatypes[i].getName());
					
					// Set Values as Type Id
					List<Object> value = new ArrayList<Object>();
					value.add(datatypes[i].getId());
					lv.setValue(value);
					typeList.add(lv);
				}
			}
			types = typeList.toArray(new LabelValue[typeList.size()]);
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	    
		LOG.debug("Found number of datatypes: " + types.length);
	    return types;
	}
}