package com.appiancorp.ps.plugins.systemutilities.rules;

import java.util.Date;
import java.sql.Timestamp;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.common.exceptions.InvalidUserException;
import com.appiancorp.suiteapi.common.exceptions.InvalidVersionException;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.common.exceptions.StorageLimitException;
import com.appiancorp.suiteapi.content.Content;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.DuplicateUuidException;
import com.appiancorp.suiteapi.content.exceptions.InsufficientNameUniquenessException;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;
import com.appiancorp.suiteapi.knowledge.FolderDataType;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;
import com.appiancorp.suiteapi.rules.Constant;
import com.appiancorp.suiteapi.rules.FreeformRule;
import com.appiancorp.suiteapi.rules.RulesFolder;

@PaletteInfo(paletteCategory = "Appian Smart Services", palette = "Rule Management") 
public class RulesAndConstantsCopier extends AppianSmartService {

	private static final Logger LOG = Logger.getLogger(RulesAndConstantsCopier.class);
	@SuppressWarnings("unused")
	private final SmartServiceContext sc;
	private final ContentService cs;
	private String constantUuid;
	private Long folderId;
	private String newName;

	@Override
	public void run() throws SmartServiceException {
		LOG.setLevel(Level.DEBUG);
		LOG.debug("The following constant will be searched and then renamed if found: " + constantUuid);
		Content[] content;
		try {

			LOG.debug("Copying content " + constantUuid);
			Content currentVersion = cs.getVersion(constantUuid, ContentConstants.VERSION_CURRENT);
			
			Date d = new Date();
			RulesFolder f = (RulesFolder)cs.getVersion(folderId, ContentConstants.VERSION_CURRENT);
			
			if(currentVersion.getSubtype().equals(ContentConstants.SUBTYPE_RULE_CONSTANT)) {
				Constant c = new Constant();
				c.setName(newName);
				c.setType(((Constant) currentVersion).getType());
				//c.setSubtype(((Constant) currentVersion).getSubtype());
				c.setTypedValue(((Constant) currentVersion).getTypedValue());
				c.setCreatedTimestamp(new Timestamp(d.getTime()));
				c.setCreator(sc.getUsername());
				c.setAttributes(((Constant) currentVersion).getAttributes());
				c.setParent(f.getId());
				c.setParentName(f.getName());
				c.setParentType(f.getType());
				Long newId = cs.create(c, folderId.intValue());
				
				LOG.debug("Constant:" + newId + " has been copied from Constant: " + currentVersion.getId());
			} else if (currentVersion.getSubtype().equals(ContentConstants.SUBTYPE_RULE_FREEFORM)) {
				FreeformRule r = new FreeformRule();
				r.setName(newName);
				r.setDefinition(((FreeformRule) currentVersion).getDefinition());
				r.setType(((FreeformRule) currentVersion).getType());
				//r.setType(((FreeformRule) currentVersion).getSubtype());
				r.setCreatedTimestamp(new Timestamp(d.getTime()));
				r.setCreator(sc.getUsername());
				r.setAttributes(((FreeformRule) currentVersion).getAttributes());
				r.setParams(((FreeformRule) currentVersion).getParams());
				r.setParent(f.getId());
				r.setParentName(f.getName());
				r.setParentType(f.getType());
				Long newId = cs.create(r, folderId.intValue());
				
				LOG.debug("Expression Rule:" + newId + " has been copied from Expression Rule: " + currentVersion.getId());
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
		} catch (DuplicateUuidException e) {
			LOG.error(e);
			throw createException(e, "error.duplicateUuidException");      
		} catch (InsufficientNameUniquenessException e) {
			LOG.error(e);
			throw createException(e, "error.insufficientNameUniquenessException");
		} catch (StorageLimitException e) {
			LOG.error(e);
			throw createException(e, "error.storageLimitException");
		} finally {     
			LOG.setLevel(Level.ERROR);
		}

	}

	public RulesAndConstantsCopier(SmartServiceContext sc, ContentService cs) {
		super();
		this.sc = sc;
		this.cs = cs;
	}

	@Input(required = Required.ALWAYS)
	@Name("constantUuid")
	public void setConstantUuid(String val) {
		this.constantUuid = val;
	}
	
	@Input(required = Required.ALWAYS)
	@Name("folderId")
	@FolderDataType
	public void setFolderId(Long val) {
		this.folderId = val;
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
