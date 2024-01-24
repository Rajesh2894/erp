package com.abm.mainet.dms.service;

import java.util.HashMap;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.config.ApplicationProperties;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.dms.controller.IFolderService;
import com.abm.mainet.dms.utility.SessionBuilder;

/**
 * @author Shyam.Ghodasra
 * 
 * @see IDMSService
 */
@Service
public class FolderServiceImpl implements IFolderService {

	private static final Logger LOGGER = Logger.getLogger(FolderServiceImpl.class);

	@Autowired
	private SessionBuilder SessionBuilder;
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Override
	public String createFolder(String folderpath) throws Exception {
		LOGGER.info(folderpath);
		Session session = SessionBuilder.getSession();
		Folder folder = null;
		String[] items = null;
		int Intialvalueloopitem = 0;
		try {
			Folder siteFolder =null;	//#106603 By Arun
			if(StringUtils.isNotEmpty(applicationProperties.getSiteName()) && !applicationProperties.getSiteName().equals(MainetConstants.NOT_APPLICABLE)){
				try {
					siteFolder = (Folder) session.getObjectByPath(MainetConstants.OPERATOR.FORWARD_SLACE+applicationProperties.getSiteName());
				} catch (Exception e) {					
					return MainetConstants.DmsErrorCodes.E104;
				}
			}
			else {
				siteFolder=session.getRootFolder();
			}
			if (folderpath != null) {
				folderpath = folderpath.replace("\\", "/");
				items = folderpath.split("/");
				for (String loopitems : items) {
					if (Intialvalueloopitem == 0 && loopitems != null)
						folder = this.createInternalFolder(siteFolder, loopitems, session);
					else
						folder = this.createInternalFolder(folder, loopitems, session);
					Intialvalueloopitem++;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in folder creation", e);
			return "Error in folder creation:" + e.toString();
		}
		return folder.getPath();
	}

	public Folder createInternalFolder(Folder rootFolder, String newFolderName, Session session) {
		CmisObject object = null;
		String subFolderId = null;
		Folder subFolderaftersave = null;
		Folder subFolder = null;

		try {
			String path = rootFolder.getPath().equals("/") ? "/" + newFolderName
					: rootFolder.getPath() + "/" + newFolderName;

			object = session.getObjectByPath(path);
			if (object.getId() != null) {
				subFolderaftersave = (Folder) object;
				subFolder = subFolderaftersave;
				LOGGER.info("Folder already existed!");
			}
		} catch (CmisObjectNotFoundException onfe) {
			try {
				HashMap<String, String> props = new HashMap<String, String>();
				props.put(PropertyIds.OBJECT_TYPE_ID, MainetConstants.CMIS.CMIS_FOLDER);
				props.put(PropertyIds.NAME, newFolderName);
				subFolder = rootFolder.createFolder(props);
				subFolderId = subFolder.getId();
				LOGGER.info("New folder created: " + subFolderId);
			} catch (CmisContentAlreadyExistsException e) {
				LOGGER.error("Folder already exists: ", e);
			}
		}
		return subFolder;
	}

	@Override
	public String getFolder(String path) {
		Session session = SessionBuilder.getSession();
		CmisObject object = null;
		object = session.getObjectByPath(session.getRootFolder().getPath() + path);
		object.getId();
		return object.getId();
	}

	@Override
	public void deleteFolder(String folderId) {
		Session session = SessionBuilder.getSession();
		Folder folder = null;
		folder = (Folder) session.getObject(folderId);
		folder.deleteTree(true, UnfileObject.DELETE, true);
	}
}
