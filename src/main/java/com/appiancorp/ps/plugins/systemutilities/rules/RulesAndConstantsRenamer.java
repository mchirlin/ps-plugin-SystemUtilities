package com.appiancorp.ps.plugins.systemutilities.rules;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.common.exceptions.InvalidUserException;
import com.appiancorp.suiteapi.common.exceptions.InvalidVersionException;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.content.Content;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.DuplicateUuidException;
import com.appiancorp.suiteapi.content.exceptions.IllegalRecursionException;
import com.appiancorp.suiteapi.content.exceptions.InsufficientNameUniquenessException;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;

@PaletteInfo(paletteCategory = "Appian Smart Services", palette = "Rule Management") 
public class RulesAndConstantsRenamer extends AppianSmartService {

	private static final Logger LOG = Logger.getLogger(RulesAndConstantsRenamer.class);
	@SuppressWarnings("unused")
	private final SmartServiceContext smartServiceCtx;
	private final ContentService cs;
	private String constantUuid;
	private String newName;

	@Override
	public void run() throws SmartServiceException {
		LOG.setLevel(Level.DEBUG);
		LOG.debug("The following constant will be searched and then renamed if found: " + constantUuid);
		Content[] content;
		try {

			LOG.debug("Retrieving constant " + constantUuid);
			Content currentVersion = cs.getVersion(constantUuid, ContentConstants.VERSION_CURRENT);
			LOG.debug("About to rename constant [UUID: " + currentVersion.getUuid() + ", Name: " + currentVersion.getName() +
					", Display Name: " + currentVersion.getDisplayName() + "] to New Name: " + newName);

			if (currentVersion.getType().equals(ContentConstants.TYPE_RULE)) {
				content = (Content[]) cs.getAllVersions(cs.getIdByUuid(constantUuid));
				for(Content c : content) {
					c.setName(newName);
				}
				cs.updateFields(content, new Integer[]{ContentConstants.COLUMN_NAME}, ContentConstants.UNIQUE_FOR_ALL);
				currentVersion = (Content) cs.getVersion(constantUuid, ContentConstants.VERSION_CURRENT);
				LOG.debug("Object " + currentVersion.getUuid() + " has been renamed to: [Display name: " + currentVersion.getDisplayName() + ", name: " + currentVersion.getName() + "]");
			}
		} catch (InvalidContentException e) {
			LOG.error(e);
			throw createException(e, "error.invalidContentException");
		} catch (InvalidVersionException e) {
			LOG.error(e);
			throw createException(e, "error.invalidVersionException");
		} catch (PrivilegeException e) {
			LOG.error(e);
			throw createException(e, "error.privilegeException");
		} catch (InvalidUserException e) {
			LOG.error(e);
			throw createException(e, "error.invalidUserException");
		} catch (IllegalRecursionException e) {
			LOG.error(e);
			throw createException(e, "error.illegalRecursionException");
		} catch (DuplicateUuidException e) {
			LOG.error(e);
			throw createException(e, "error.duplicateUuidException");      
		} catch (InsufficientNameUniquenessException e) {
			LOG.error(e);
			throw createException(e, "error.insufficientNameUniquenessException");
		} finally {     
			LOG.setLevel(Level.ERROR);
		}

	}

	public RulesAndConstantsRenamer(SmartServiceContext smartServiceCtx, ContentService cs) {
		super();
		this.smartServiceCtx = smartServiceCtx;
		this.cs = cs;
	}

	@Input(required = Required.ALWAYS)
	@Name("constantUuid")
	public void setConstantUuid(String val) {
		this.constantUuid = val;
	}

	@Input(required = Required.ALWAYS)
	@Name("newName")
	public void setNewName(String val) {
		this.newName = val;
	}

	private SmartServiceException createException(Throwable t, String key, Object... args) {
		return new SmartServiceException.Builder(getClass(), t).userMessage(key, args).build();
	}

}
