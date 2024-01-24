package com.abm.mainet.dms.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisBaseException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.dms.controller.IDocumentService;
import com.abm.mainet.dms.controller.IFolderService;
import com.abm.mainet.dms.dto.DocumentDetails;
import com.abm.mainet.dms.dto.DocumentDetailsResponse;
import com.abm.mainet.dms.utility.DmsUtility;
import com.abm.mainet.dms.utility.SessionBuilder;

/**
 * @author Shyam.Ghodasra
 * 
 * @see IDMSService
 */
@Service
public class DocumentServiceImpl implements IDocumentService {

	private static final Logger LOGGER = Logger.getLogger(DocumentServiceImpl.class);


	@Autowired
	private SessionBuilder SessionBuilder;

	@Autowired
	private IFolderService FolderService;

	@Override
	public DocumentDetailsResponse getDocuments(String applicationId, String referenceId) throws Exception {
		LOGGER.info("applicationId:" + applicationId);
		LOGGER.info("referenceId:" + referenceId);
		DocumentDetailsResponse d = new DocumentDetailsResponse();
		Session session = SessionBuilder.getSession();
		Map<String, Object> res = new HashMap<>();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT * FROM " + MainetConstants.CMIS.CMIS_DOCUMENT + " AS d JOIN " + MainetConstants.ASPECT.ASPECT_REMOTE_REFERENCE_ID + " as p ON d."
					+ PropertyIds.OBJECT_ID + " = p." + PropertyIds.OBJECT_ID + " where ");

			if (referenceId != null && applicationId == null) {
				query.append("p." + MainetConstants.PROPERTIES.PROP_PROJECTREFERID + "= '" + referenceId + "'");
			} else if (applicationId != null && referenceId == null) {
				query.append("p." + MainetConstants.PROPERTIES.PROP_APPLICATIONID + "='" + applicationId + "'");
			} else if (referenceId != null && applicationId != null) {
				query.append("p." + MainetConstants.PROPERTIES.PROP_PROJECTREFERID + " = '" + referenceId + "' AND p." + MainetConstants.PROPERTIES.PROP_APPLICATIONID + "='"
						+ applicationId + "'");
			} else {
				d.setErrorMessage("gg");
				return d ;
			}
			LOGGER.info("query:" + query);
			ItemIterable<QueryResult> results = session.query(query.toString(), false);
			if (results.getTotalNumItems() == 0) {
				res.put("error", "zero results found");
				return d ;
			}
			Map<String, Object> docProp = new HashMap<>();
			List<Object> docList = new ArrayList<>();
			for (QueryResult queryResult : results) {
				String objectId = queryResult.getPropertyById(PropertyIds.OBJECT_ID).getFirstValue().toString();
				Document doc = (Document) session.getObject(session.createObjectId(objectId));
				docProp.put("docName", doc.getName());
				docProp.put("docId", doc.getId());
				docList.add(docProp.toString());
			}
			res.put("docDetails", docList);
		} catch (CmisBaseException e) {
			LOGGER.error(MainetConstants.EXCEPTION.EXCEPTION_OCCURED, e);
			res.put("error", "Invalid session:" + e);
			return d ;
		}
		return d ;
	}

	@Override
	public String createDocument(DocumentDetails documentListReq) {
		//for (Iterator<DocumentDetails> iterator = documentListReq.iterator(); iterator.hasNext();) {
		String id = null ;	
		DocumentDetails documentReq =documentListReq;// (DocumentDetails) iterator.next();
			if (documentReq.getDocumentId() == null) {
				Session session = SessionBuilder.getSession();
				Folder folder = getFolder(documentReq.getFolderPath(), session);
				JSONObject docProp = new JSONObject();

				Map<String, Object> properties = new HashMap<String, Object>();
				ContentStream contentStream = null;
				try {
					properties = new HashMap<String, Object>();					
					properties.put(PropertyIds.OBJECT_TYPE_ID, MainetConstants.CMIS.CMIS_DOCUMENT);
					properties.put(PropertyIds.NAME, documentReq.getFileName());
					//properties.put(PropertyIds.SECONDARY_OBJECT_TYPE_IDS,
							//Arrays.asList("P:" + MainetConstants.ASPECT.ASPECT_REMOTE_REFERENCE_ID));	
					
					//#106603 By Arun
					List<Object> aspects = new ArrayList<Object>();				
					aspects.add(MainetConstants.prefix.PREFIX + MainetConstants.ASPECT.ASPECT_REMOTE_REFERENCE_ID);
					if(documentListReq.getDocTypeDescription()!=null || documentListReq.getDepartmentName()!=null) {
						aspects.add(MainetConstants.prefix.PREFIX+MainetConstants.prefix.ASPECT+MainetConstants.prefix.PRIMARY_METADATA);
					}
					properties.put(PropertyIds.SECONDARY_OBJECT_TYPE_IDS, aspects);
					
					if(documentListReq.getDocTypeDescription()!=null) {						
						properties.put(MainetConstants.prefix.ASPECT+MainetConstants.prefix.DOC_TYPE,documentListReq.getDocTypeDescription());						
					}
					if(documentListReq.getDepartmentName()!=null) {
						properties.put(MainetConstants.prefix.ASPECT+MainetConstants.prefix.DEPT_NAME, documentListReq.getDepartmentName());
					}
					if(documentListReq.getDmsServiceMap()!=null && !documentListReq.getDmsServiceMap().isEmpty()) {
						aspects.add(MainetConstants.prefix.PREFIX+MainetConstants.prefix.ASPECT+documentListReq.getDepartmentCode());// EX :- "P:dc:webable"
						for (Map.Entry<String, String> entry : documentListReq.getDmsServiceMap().entrySet()) {
							if (StringUtils.isNotEmpty(entry.getValue())) {
								properties.put(MainetConstants.prefix.ASPECT+ entry.getKey(), entry.getValue());
							}
						}																			
					}
					//#106603 end 
					byte[] decodeBytes = Base64.getDecoder().decode(documentReq.getFileContent().getBytes());
					InputStream stream = new ByteArrayInputStream(decodeBytes);
					contentStream = new ContentStreamImpl(documentReq.getFileName(),
							BigInteger.valueOf(decodeBytes.length), documentReq.getContentType(), stream);
				} catch (IllegalArgumentException e) {
					LOGGER.error(MainetConstants.EXCEPTION.EXCEPTION_OCCURED, e);
					docProp.put("error", "IllegalArgumentException:" + e);
				}
				id = createDocument(folder, properties, contentStream);
				documentListReq.setDocumentId(id);
			} else {
				LOGGER.info("NEW VERSION");
				createNewVersion(documentReq);
			}
		
		//}
		return id;
	}

	private Folder getFolder(String folderPath, Session session) {
		Folder folder = null;
		try {
			folder = (Folder) session.getObjectByPath(folderPath);
		} catch (CmisObjectNotFoundException e) {
			try {
				FolderService.createFolder(folderPath);
				folder = (Folder) session.getObjectByPath(folderPath);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return folder;
	}

	private void createNewVersion(DocumentDetails documentReq) {
		Session session = SessionBuilder.getSession();
		CmisObject object = null;
		try {
			object = session.getObject(session.createObjectId(documentReq.getDocumentId()));
		} catch (CmisObjectNotFoundException e) {
			LOGGER.error(MainetConstants.EXCEPTION.EXCEPTION_OCCURED + " document id not exists", e);
		}
		Document document = (Document) object;
		ObjectId pwcId = document.checkOut();
		Document pwc = (Document) session.getObject(pwcId);
		byte[] decodeBytes = Base64.getDecoder().decode(documentReq.getFileContent().getBytes());
		InputStream stream = new ByteArrayInputStream(decodeBytes);
		ContentStream contentStream = new ContentStreamImpl(documentReq.getFileName(),
				BigInteger.valueOf(decodeBytes.length), documentReq.getContentType(), stream);
		pwc.checkIn(true, null, contentStream, "new version");
	}

	//#106603 By Arun
	public String createDocument(Folder folder, Map<String, Object> properties,
			ContentStream contentStream) {
		Map<String, Object> docProp = new HashMap<>();
		String sharedId = null;
		Document newDoc = null;		
		try {
			newDoc = folder.createDocument(properties, contentStream, VersioningState.MAJOR);
			newDoc.refresh();			
			List<Object> aspects = newDoc.getProperty(PropertyIds.SECONDARY_OBJECT_TYPE_IDS).getValues();
			if (aspects.contains("P:qshare:shared"))
				sharedId = newDoc.getProperty("qshare:sharedId").getValueAsString();
		} catch (CmisContentAlreadyExistsException c) {
			LOGGER.error("Document already exist", c);			
			docProp.put("msg", "DOC_ALREADY_EXIST");
			return MainetConstants.DmsErrorCodes.E101;
		} catch(IllegalArgumentException i) {			
			LOGGER.error("properties do not match", i);
			return MainetConstants.DmsErrorCodes.E102;
		}
		catch(Exception e) {			
			LOGGER.error("Exception occured", e);
			return MainetConstants.DmsErrorCodes.E103;
		}
		docProp.put("msg", "success");
		docProp.put("docId", newDoc.getId());
		docProp.put("shareId", sharedId);
		return newDoc.getId().split(";")[0];
	}

	@Override
	public String getDocumentByDocumentId(String documentId) {
		Session session = SessionBuilder.getSession();
		CmisObject object = null;
		try {
			object = session.getObject(session.createObjectId(documentId));
		} catch (CmisObjectNotFoundException e) {
			LOGGER.error(MainetConstants.EXCEPTION.EXCEPTION_OCCURED + " document id not exists", e);
		}
		Document document = (Document) object;		
		InputStream stream = document.getContentStream().getStream();
		byte[] stream2 = DmsUtility.convertStreamtoByte(stream);
		byte[] encoded = Base64.getEncoder().encode(stream2);
		return new String(encoded);
	}

	@Override
	public String getDocumentByPath(String path) {
		Session session = SessionBuilder.getSession();
		CmisObject object = null;
		object = session.getObjectByPath(session.getRootFolder().getPath() + path);
		Document doc = (Document) object;
		InputStream stream = doc.getContentStream().getStream();
		byte[] data = DmsUtility.convertStreamtoByte(stream);
		byte[] encoded = Base64.getEncoder().encode(data);
		return new String(encoded);

	}

	@Override
	public void deleteDocumentByDocumentId(String documentId) {
		Session session = SessionBuilder.getSession();
		Document doc = null;
		doc = (Document) session.getObject(documentId);
		doc.delete(true);
	}

	public void deleteDocumentByPah(String path) {
		Session session = SessionBuilder.getSession();
		Document doc = null;
		doc = (Document) session.getObjectByPath(session.getRootFolder().getPath() + path);
		doc.delete(true);
	}
	
}
