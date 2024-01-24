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
import com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHead;
import com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHeadServer;
import com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHeadServer_Service;

@Service
public class AccountHeadProvisionServiceImpl implements AccountHeadProvisionService {

	private static final Logger LOGGER = Logger.getLogger(AccountHeadProvisionServiceImpl.class);

	private static final QName SERVICE_NAME = new QName("urn:orangescape", "Account_HeadServer");
	private static final String AUTHORIZATION = "Authorization";
	private static final String EMPLOYEE_SERVER_PREPARE_EXCEPTION = "Exception while preparing Account Head Server with WSDL WRL, WSDL file location and Authentication : ";
	private static final String GRP_EMP_POSTING_EXCEPTION = "Exception while posting data to Property Tax :";
	
	@Value("${grp.posting.enable}")
	private String grpPostingFlag;

	@Value("${grp.server.url}")
	private String grpServerUrl;

	@Value("${grp.authentication.code}")
	private String grpPostingAuthCode;

	@Value("${soap.account.head.wsdl.url}")
	private String grpPostingEmpWsdlUrl;

	@Value("${GRP_FUNCTION_STORE}")
	private String departmentWiseEnableGrpStore;
	
	@Value("${GRP_FUNCTION_HRMS}")
	private String departmentWiseEnableGrpHrms;
	
	public AccountHeadProvisionServiceImpl() {
	}

	@Override
	public void createAccountHead(AccountHead accountHead) {

		/**
		 * it is used to push data to GRP environment if account Head posting Mainet to
		 * GRP. all configuration data is configured in serviceConfiguration.properties
		 * file.
		 */
		LOGGER.info("start GRP Account Head if posting flag is 'Y'. configured value of posting flag is : "
				+ grpPostingFlag);
		if (grpPostingFlag.equals(MainetConstants.Common_Constant.YES)) {
			if (departmentWiseEnableGrpStore.equals(MainetConstants.Common_Constant.YES) || departmentWiseEnableGrpHrms.equals(MainetConstants.Common_Constant.YES)) {
				try {
					// System.out.println("Invoking submit...");
					javax.xml.ws.Holder<com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHead> _submit_parameters = new javax.xml.ws.Holder<com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHead>(
							accountHead);
					AccountHeadServer port = getAccountHeadServer();
					port.submit(_submit_parameters);

					// System.out.println("submit._submit_parameters=" + _submit_parameters.value);

				} catch (Exception e) {
					throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
				}	
			}
		}
	}

	/**
	 * this service is used to update existing account head details on GRP
	 * Environment in account head posting is configured in
	 * serviceConfiguration.properties file.
	 * 
	 * @param AccountHead.
	 */
	@Override
	public void updateAccountHead(AccountHead accountHead) {
		LOGGER.info("start GRP update Account Head if posting flag is 'Y'. configured value of posting flag is : "
				+ grpPostingFlag);

		if (grpPostingFlag.equals(MainetConstants.Common_Constant.YES)) {
			if (departmentWiseEnableGrpStore.equals(MainetConstants.Common_Constant.YES) || departmentWiseEnableGrpHrms.equals(MainetConstants.Common_Constant.YES)) {
				try {
					// System.out.println("Invoking update...");
					// com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHead
					// _update_parameters = new
					// com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHead();
					// get employee server with authentication, WSDL URL and WSDL file location for
					// update employee data.
					AccountHeadServer port = getAccountHeadServer();
					com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHead _update__return = port
							.update(accountHead);
					// System.out.println("update.result=" + _update__return);
				} catch (Exception e) {
					throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
				}
			}
		}
	}

	/**
	 * this common method is used to get Account Head server with
	 * authentication,WSDL URL and WSDL file location.
	 * 
	 * @return AccountHeadServer.
	 */
	private AccountHeadServer getAccountHeadServer() {
		AccountHeadServer accountHeadServer = null;
		try {
			AccountHeadServer_Service accountHeadServerService = new AccountHeadServer_Service(
					AccountHeadServer_Service.WSDL_LOCATION, SERVICE_NAME);
			accountHeadServer = accountHeadServerService.getAccountHeadServer();
			Map<String, Object> requestctx = ((BindingProvider) accountHeadServer).getRequestContext();

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
		return accountHeadServer;
	}

}
