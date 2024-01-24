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
import com.abm.mainet.common.integration.acccount.hrms.functionmaster.soap.jaxws.client.Functionmaster;
import com.abm.mainet.common.integration.acccount.hrms.functionmaster.soap.jaxws.client.FunctionmasterServer;
import com.abm.mainet.common.integration.acccount.hrms.functionmaster.soap.jaxws.client.FunctionmasterServer_Service;
import com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHead;
import com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHeadServer;

@Service
public class FunctionMasterSoapWSProvisionServiceImpl implements FunctionMasterSoapWSProvisionService {

	private static final Logger LOGGER = Logger.getLogger(FunctionMasterSoapWSProvisionServiceImpl.class);

	private static final QName SERVICE_NAME = new QName("urn:orangescape", "functionmasterServer");
	private static final String AUTHORIZATION = "Authorization";
	private static final String EMPLOYEE_SERVER_PREPARE_EXCEPTION = "Exception while preparing Bank Account Head Server with WSDL WRL, WSDL file location and Authentication : ";
	private static final String GRP_EMP_POSTING_EXCEPTION = "Exception while posting data to Property Tax :";

	@Value("${grp.posting.enable}")
	private String grpPostingFlag;

	@Value("${grp.server.url}")
	private String grpServerUrl;

	@Value("${grp.authentication.code}")
	private String grpPostingAuthCode;

	@Value("${soap.function.master.wsdl.url}")
	private String grpPostingEmpWsdlUrl;

	@Value("${GRP_FUNCTION_STORE}")
	private String departmentWiseEnableGrpStore;
	
	@Value("${GRP_FUNCTION_HRMS}")
	private String departmentWiseEnableGrpHrms;
	
	public FunctionMasterSoapWSProvisionServiceImpl() {
	}

	@Override
	public void createFunctionMaster(Functionmaster functionmaster) {

		/**
		 * it is used to push data to GRP environment if function master posting Mainet
		 * to GRP. all configuration data is configured in
		 * serviceConfiguration.properties file.
		 */
		LOGGER.info("start GRP Function Master if posting flag is 'Y'. configured value of posting flag is : "
				+ grpPostingFlag);
		if (grpPostingFlag.equals(MainetConstants.Common_Constant.YES)) {
			if (departmentWiseEnableGrpStore.equals(MainetConstants.Common_Constant.YES) || departmentWiseEnableGrpHrms.equals(MainetConstants.Common_Constant.YES)) {
				try {
					// System.out.println("Invoking submit...");
					javax.xml.ws.Holder<com.abm.mainet.common.integration.acccount.hrms.functionmaster.soap.jaxws.client.Functionmaster> _submit_parameters = new javax.xml.ws.Holder<com.abm.mainet.common.integration.acccount.hrms.functionmaster.soap.jaxws.client.Functionmaster>(
							functionmaster);
					FunctionmasterServer port = getFunctionMasterServer();
					port.submit(_submit_parameters);
					// System.out.println("submit._submit_parameters=" + _submit_parameters.value);
				} catch (Exception e) {
					throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
				}	
			}
		}
	}

	/**
	 * this service is used to update existing function master details on GRP
	 * Environment in function master posting is configured in
	 * serviceConfiguration.properties file.
	 * 
	 * @param Functionmaster.
	 */
	@Override
	public void updateFunctionMaster(Functionmaster functionmaster) {
		LOGGER.info("start GRP update Function Master posting flag is 'Y'. configured value of posting flag is : "
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
					FunctionmasterServer port = getFunctionMasterServer();
					com.abm.mainet.common.integration.acccount.hrms.functionmaster.soap.jaxws.client.Functionmaster _update__return = port
							.update(functionmaster);
					// System.out.println("update.result=" + _update__return);
				} catch (Exception e) {
					throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
				}
			}
		}
	}

	/**
	 * this common method is used to get function master server with
	 * authentication,WSDL URL and WSDL file location.
	 * 
	 * @return FunctionmasterServer.
	 */
	private FunctionmasterServer getFunctionMasterServer() {
		FunctionmasterServer functionmasterServer = null;
		try {
			FunctionmasterServer_Service functionMasterServerService = new FunctionmasterServer_Service(
					FunctionmasterServer_Service.WSDL_LOCATION, SERVICE_NAME);
			functionmasterServer = functionMasterServerService.getFunctionmasterServer();
			Map<String, Object> requestctx = ((BindingProvider) functionmasterServer).getRequestContext();

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
		return functionmasterServer;
	}

}
