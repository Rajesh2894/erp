package com.abm.mainet.common.integration.acccount.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.hrms.bankaccount.soap.jaxws.client.BankAccount;
import com.abm.mainet.common.integration.acccount.hrms.bankaccount.soap.jaxws.client.BankAccountServer;
import com.abm.mainet.common.integration.acccount.hrms.bankmaster.soap.jaxws.client.WSBankMaster;
import com.abm.mainet.common.integration.acccount.hrms.bankmaster.soap.jaxws.client.WSBankMasterServer;
import com.abm.mainet.common.integration.acccount.hrms.bankmaster.soap.jaxws.client.WSBankMasterServer_Service;

@Service
public class BankMasterSoapWSProvisionServiceImpl implements BankMasterSoapWSProvisionService {

	private static final Logger LOGGER = Logger.getLogger(BankMasterSoapWSProvisionServiceImpl.class);

	private static final QName SERVICE_NAME = new QName("urn:orangescape", "WS_BankMasterServer");
	private static final String AUTHORIZATION = "Authorization";
	private static final String EMPLOYEE_SERVER_PREPARE_EXCEPTION = "Exception while preparing Bank Master Head Server with WSDL WRL, WSDL file location and Authentication : ";
	private static final String GRP_EMP_POSTING_EXCEPTION = "Exception while posting data to Property Tax :";

	@Value("${grp.posting.enable}")
	private String grpPostingFlag;

	@Value("${grp.server.url}")
	private String grpServerUrl;

	@Value("${grp.authentication.code}")
	private String grpPostingAuthCode;

	@Value("${soap.bank.master.wsdl.url}")
	private String grpPostingEmpWsdlUrl;

	public BankMasterSoapWSProvisionServiceImpl() {
	}

	@Override
	public void createBankMasterHead(WSBankMaster wSBankMaster) {

		/**
		 * it is used to push data to GRP environment if bank master Head posting Mainet
		 * to GRP. all configuration data is configured in
		 * serviceConfiguration.properties file.
		 */
		LOGGER.info("start GRP Bank Master Head if posting flag is 'Y'. configured value of posting flag is : "
				+ grpPostingFlag);
		if (grpPostingFlag.equals(MainetConstants.Common_Constant.YES)) {
			try {
				// System.out.println("Invoking submit...");
				javax.xml.ws.Holder<com.abm.mainet.common.integration.acccount.hrms.bankmaster.soap.jaxws.client.WSBankMaster> _submit_parameters = new javax.xml.ws.Holder<com.abm.mainet.common.integration.acccount.hrms.bankmaster.soap.jaxws.client.WSBankMaster>(
						wSBankMaster);
				WSBankMasterServer port = getWSBankMasterServer();
				port.submit(_submit_parameters);

				// System.out.println("submit._submit_parameters=" + _submit_parameters.value);
			} catch (Exception e) {
				throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
			}
		}
	}

	@Override
	public void updateBankMasterHead(WSBankMaster wSBankMaster) {
		LOGGER.info("start GRP update Bank Master Head if posting flag is 'Y'. configured value of posting flag is : "
				+ grpPostingFlag);

		if (grpPostingFlag.equals(MainetConstants.Common_Constant.YES)) {
			try {
				// System.out.println("Invoking update...");
				WSBankMasterServer port = getWSBankMasterServer();
				com.abm.mainet.common.integration.acccount.hrms.bankmaster.soap.jaxws.client.WSBankMaster _update__return = port
						.update(wSBankMaster);

				// System.out.println("update.result=" + _update__return);
			} catch (Exception e) {
				throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
			}
		}
	}

	/**
	 * this common method is used to get bank master server with authentication,WSDL
	 * URL and WSDL file location.
	 * 
	 * @return WSBankMasterServer.
	 */
	private WSBankMasterServer getWSBankMasterServer() {
		WSBankMasterServer wSBankMasterServer = null;
		try {
			WSBankMasterServer_Service bankMasterHeadServerService = new WSBankMasterServer_Service(
					WSBankMasterServer_Service.WSDL_LOCATION, SERVICE_NAME);
			wSBankMasterServer = bankMasterHeadServerService.getWSBankMasterServer();
			Map<String, Object> requestctx = ((BindingProvider) wSBankMasterServer).getRequestContext();

			// this WSDL URL is configured in serviceConfiguration.properties file.
			requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, grpServerUrl + grpPostingEmpWsdlUrl);

			Map<String, List<String>> headers = new HashMap<>();

			// this Authentication code is configured in serviceConfiguration.properties
			// file.
			headers.put(AUTHORIZATION, Collections.singletonList(grpPostingAuthCode));
			requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		} catch (Exception ex) {
			throw new FrameworkException(EMPLOYEE_SERVER_PREPARE_EXCEPTION + ex);
		}
		return wSBankMasterServer;
	}

}
