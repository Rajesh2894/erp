package com.abm.mainet.dms.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.dms.controller.IOtherService;
import com.abm.mainet.dms.utility.DmsUtility;
import com.abm.mainet.dms.utility.SessionBuilder;

/**
 * @author Shyam.Ghodasra
 * 
 * @see IDMSService
 */
@SuppressWarnings("deprecation")
@Service
public class OtherServiceImpl implements IOtherService {

	private static final Logger LOGGER = Logger.getLogger(ShareServiceImpl.class);

	@Autowired
	private SessionBuilder SessionBuilder;


	private HttpClient httpClient;

	@Override
	public List<String> getMultipleDocumentContentByIds(List<String> docIds) {
		Session session = SessionBuilder.getSession();
		CmisObject object = null;
		InputStream stream = null;
		List<String> documents = new ArrayList<>();
		for (int noOfDocuements = 0; noOfDocuements <= docIds.size() - 1; noOfDocuements++) {
			object = session.getObject(session.createObjectId(docIds.get(noOfDocuements)));
			Document document = (Document) object;
			stream = document.getContentStream().getStream();
			documents.add(noOfDocuements,
					docIds.get(0).toString() + "|" + DmsUtility.convertStreamtoByte(stream).toString());
		}
		return documents;
	}


	@Override
	public void increaseVersionNumber(String docId, int versionNumber, byte[] content) {
		Session session = SessionBuilder.getSession();
		CmisObject object = null;
		object = session.getObject(docId);
		String documentName = ((Document) object).getName();
		String mimeType = ((Document) object).getContentStreamMimeType();
		InputStream stream = new ByteArrayInputStream(content);
		for (int version = 0; version < versionNumber; version++) {
			ObjectId idpwc = ((Document) object).checkOut();
			Document cmisDocpwc = null;
			cmisDocpwc = (Document) session.getObject(idpwc);
			cmisDocpwc.checkIn(false, null, new ContentStreamImpl(documentName, mimeType, stream.toString()),
					"V:" + versionNumber);
		}
	}

	@Override
	public String getWfStatus(String workflowId) throws Exception {
		Session session = SessionBuilder.getSession();
		httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(
				session.getSessionParameters().get(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE_ENDPOINT) + "?refID="
						+ workflowId);
		HttpResponse httpResponse = httpClient.execute(getRequest);
		LOGGER.info("httpResponse:" + httpResponse);
		httpClient.getConnectionManager().shutdown();
		return IOUtils.toString(httpResponse.getEntity().getContent());
	}

}
