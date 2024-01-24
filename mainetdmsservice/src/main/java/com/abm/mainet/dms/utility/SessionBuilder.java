package com.abm.mainet.dms.utility;

import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.config.ApplicationProperties;
import com.abm.mainet.constant.MainetConstants;

@SuppressWarnings("deprecation")
@Component
public class SessionBuilder {

	private final static Logger LOGGER = Logger.getLogger(SessionBuilder.class);

	@Autowired
	private ApplicationProperties applicationProperties;

	static Repository repository;
	public static Session session = null;
	public static SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
	static Map<String, String> parameters = new HashMap<String, String>();
	private HttpClient httpClient;

	private Map<String, String> getSessionParameterMap() {
		parameters.put(SessionParameter.USER, applicationProperties.getUserid());
		parameters.put(SessionParameter.PASSWORD, applicationProperties.getUserPassword());
		parameters.put(SessionParameter.BROWSER_URL, applicationProperties.getAlfrescoBrowserURL());
		parameters.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());
		parameters.put(SessionParameter.AUTH_HTTP_BASIC, "true");
		parameters.put(SessionParameter.COOKIES, "true");
		return parameters;
	}

	public Session getSession() {
		getSessionParameterMap();
		repository = sessionFactory.getRepositories(parameters).get(0);
		session = repository.createSession();
		return session;

	}

	public String getAlfTicket() {
		httpClient = new DefaultHttpClient();
		String URL = applicationProperties.getAlfrescoWsAlfTicketURL() + MainetConstants.OPERATOR.QUE_MARK
				+ MainetConstants.URLParameterName.USERNAME + MainetConstants.OPERATOR.EQUAT_TO
				+ applicationProperties.getUserid() + MainetConstants.OPERATOR.AMPERSENT
				+ MainetConstants.URLParameterName.PASSWORD + MainetConstants.OPERATOR.EQUAT_TO
				+ applicationProperties.getUserPassword() + MainetConstants.OPERATOR.AMPERSENT
				+ MainetConstants.Responce.JSON;
		LOGGER.info(URL);
		HttpGet getRequest = new HttpGet(URL);
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(getRequest);
			LOGGER.info("httpResponse:" + httpResponse);
			httpClient.getConnectionManager().shutdown();
			String responce = IOUtils.toString(httpResponse.getEntity().getContent());
			JSONObject obj = new JSONObject(responce);
			String ticket = obj.getJSONObject("data").getString("ticket");
			LOGGER.info(ticket);
			return ticket;
		} catch (Exception e) {
			return null;
		}

	}

}
