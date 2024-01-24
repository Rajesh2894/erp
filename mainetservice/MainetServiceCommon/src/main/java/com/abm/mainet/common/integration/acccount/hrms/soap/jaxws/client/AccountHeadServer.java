package com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.14
 * 2018-05-11T14:48:31.742+05:30
 * Generated source version: 3.1.14
 * 
 */
@WebService(targetNamespace = "urn:orangescape", name = "Account_HeadServer")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface AccountHeadServer {

    @WebMethod(operationName = "Update", action = "Update")
    @WebResult(name = "Account_Head", targetNamespace = "urn:orangescape", partName = "parameters")
    public AccountHead update(
        @WebParam(partName = "parameters", name = "Account_Head", targetNamespace = "urn:orangescape")
        AccountHead parameters
    );

    @WebMethod(operationName = "Submit", action = "Submit")
    public void submit(
        @WebParam(partName = "parameters", mode = WebParam.Mode.INOUT, name = "Account_Head", targetNamespace = "urn:orangescape")
        javax.xml.ws.Holder<AccountHead> parameters
    );
}
