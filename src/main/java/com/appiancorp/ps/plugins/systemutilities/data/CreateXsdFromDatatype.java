package com.appiancorp.ps.plugins.systemutilities.data;

import java.io.StringWriter;

import com.appiancorp.ps.plugins.systemutilities.CustomContentFunctionsCategory;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.type.Type;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
@CustomContentFunctionsCategory
public class CreateXsdFromDatatype {

	private static final Logger LOG = Logger.getLogger(CreateXsdFromDatatype.class);
	private VelocityEngine ve;

	@Function
	public String createXsdFromDatatype(ServiceContext sc,
			@Parameter Datatype datatype) {
		try {
			ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

			Velocity.init();
			
			VelocityContext context = new VelocityContext();
			Template template = ve.getTemplate("templates/schema.htm");
			
			StringWriter sw = new StringWriter();
			
			context.put("datatype", datatype);
			template.merge(context, sw);
			
			return sw.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
