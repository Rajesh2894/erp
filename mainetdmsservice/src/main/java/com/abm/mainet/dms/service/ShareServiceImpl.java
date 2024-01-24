package com.abm.mainet.dms.service;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.config.ApplicationProperties;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.dms.controller.IShareService;
import com.abm.mainet.dms.utility.SessionBuilder;

/**
 * @author Shyam.Ghodasra
 * 
 * @see IDMSService
 */
@SuppressWarnings("deprecation")
@Service
public class ShareServiceImpl implements IShareService {

	private static final Logger LOGGER = Logger.getLogger(ShareServiceImpl.class);

	@Autowired
	private SessionBuilder SessionBuilder;

	@Autowired
	private ApplicationProperties applicationProperties;

	private HttpClient httpClient;

	@Override
	public String getShareId(String documentId) throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		String ticket = SessionBuilder.getAlfTicket();
		String URL = createQuickShareURL(documentId, ticket);
		HttpPost postRequest = new HttpPost(URL);
		HttpResponse httpResponse = httpClient.execute(postRequest);
		httpClient.getConnectionManager().shutdown();
		return IOUtils.toString(httpResponse.getEntity().getContent());
	}

	@Override
	public void deleteShareId(String documentId) throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		String ticket = SessionBuilder.getAlfTicket();
		String shareURL = createQuickShareURL(documentId, ticket);
		HttpPost postRequest = new HttpPost(shareURL);
		HttpResponse httpResponse = httpClient.execute(postRequest);
		String responce = IOUtils.toString(httpResponse.getEntity().getContent());
		JSONObject obj = new JSONObject(responce);
		String sharedId = obj.getString("sharedId");
		String unShareURL = createQuickUnshareURL(sharedId,ticket);
		LOGGER.info(unShareURL);
		HttpDelete deleteRequest = new HttpDelete(unShareURL);
		httpResponse = httpClient.execute(deleteRequest);
		httpClient.getConnectionManager().shutdown();
		LOGGER.info(IOUtils.toString(httpResponse.getEntity().getContent()));
	}

	private String createQuickShareURL(String documentId, String ticket) {
		String URL = applicationProperties.getAlfrescoWsQuickShareURL() + MainetConstants.STRING.WORKSPACE
				+ MainetConstants.OPERATOR.FORWARD_SLACE + MainetConstants.STRING.SPACESSTORE
				+ MainetConstants.OPERATOR.FORWARD_SLACE + documentId + MainetConstants.OPERATOR.QUE_MARK
				+ MainetConstants.STRING.ALF_TICKET + MainetConstants.OPERATOR.EQUAT_TO + ticket;
		return URL;
	}
	private String createQuickUnshareURL(String sharedId, String ticket) {
		String URL = applicationProperties.getAlfrescoWsQuickUnshareURL()+ sharedId + MainetConstants.OPERATOR.QUE_MARK
				+ MainetConstants.STRING.ALF_TICKET + MainetConstants.OPERATOR.EQUAT_TO + ticket;
		return URL;
	}

}
