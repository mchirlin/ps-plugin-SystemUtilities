package com.appiancorp.ps.plugins.systemutilities.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.type.Datatype;
import com.appiancorp.suiteapi.type.TypeService;
import com.appiancorp.suiteapi.type.exceptions.InvalidTypeException;
import com.appiancorp.type.system.LabelValue;

@CustomContentFunctionsCategory
public class GetDatatypeByID {

	private static final Logger LOG = Logger.getLogger(GetDatatypeByID.class);

	@Function
	public LabelValue getDatatypeByID(ServiceContext sc, TypeService ts, @Parameter(required=true) Long id) {

		try {
			Datatype datatype = ts.getType(id);

			LabelValue lv = new LabelValue();

			// Set Label as Name
			lv.setLabel(datatype.getName());

			// Set Values as ID, Description, Namespace, Version
			List<Object> value = new ArrayList<Object>();
			value.add(datatype.getId());
			value.add(datatype.getDescription());
			value.add(datatype.getNamespace());
			lv.setValue(value);

			return lv;
		} catch (InvalidTypeException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

		LabelValue lv = new LabelValue();
		
		// Set Label as Name
		lv.setLabel("No object with this ID has been found");

		// Set Values as ID, Description, Namespace
		List<Object> value = new ArrayList<Object>();
		value.add(" ");
		value.add(" ");
		value.add(" ");
		lv.setValue(value);
		
		return lv;
	}
}
