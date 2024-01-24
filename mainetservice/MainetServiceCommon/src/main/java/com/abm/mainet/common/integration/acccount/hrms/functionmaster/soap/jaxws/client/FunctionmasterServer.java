package com.abm.mainet.common.integration.acccount.hrms.functionmaster.soap.jaxws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.14
 * 2018-05-16T12:59:19.589+05:30
 * Generated source version: 3.1.14
 * 
 */
@WebService(targetNamespace = "urn:orangescape", name = "functionmasterServer")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface FunctionmasterServer {

    @WebMethod(operationName = "Update", action = "Update")
    @WebResult(name = "functionmaster", targetNamespace = "urn:orangescape", partName = "parameters")
    public Functionmaster update(
        @WebParam(partName = "parameters", name = "functionmaster", targetNamespace = "urn:orangescape")
        Functionmaster parameters
    );

    @WebMethod(operationName = "Submit", action = "Submit")
    public void submit(
        @WebParam(partName = "parameters", mode = WebParam.Mode.INOUT, name = "functionmaster", targetNamespace = "urn:orangescape")
        javax.xml.ws.Holder<Functionmaster> parameters
    );
}
