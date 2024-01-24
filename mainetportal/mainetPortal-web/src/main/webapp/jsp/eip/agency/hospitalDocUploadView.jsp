
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


				<div class="regheader">
					<spring:message code="eip.agency.header.hospital" />
				</div>
				<div class="clear form">
						<div class="form-elements">							
							<div class="element">
									<label><spring:message code="eip.agency.type" /> :</label>
									 <form:input path="" value="${command.agencyType}" maxlength="100" readonly="true" cssClass="disablefield" /> 
							</div>
							
							<div class="element">
									<label><spring:message code="eip.agency.ownerOrCharity.name" /> :</label>
									<form:input path="" value="${command.entity.empname}"  maxlength="200" readonly="true" cssClass="disablefield" /> 
							</div>
						</div>
						<div class="form-elements">
							<div class="element">
									<label><spring:message code="eip.agency.hospital.name" /> :</label>
									<form:input path="" value="${command.agencyHospital.hiName}"  maxlength="200" readonly="true" cssClass="disablefield" /> 
								
							</div>							
							<div class="element">
									<label><spring:message code="eip.agency.hospital.reg.name" /> :</label>
									<form:input path="" value="${command.agencyHospital.hiNameMar}"  maxlength="500" readonly="true" id="hospitalName_hindi"  cssClass="disablefield"/> 
								    
							</div>
						</div>
						<div class="form-elements">
							<div class="element">
							
									<label><spring:message code="eip.agency.hospital.address.eng" /> :</label>
									<form:input path=""  value="${command.entity.empAddress}"  maxlength="500" readonly="true" cssClass="disablefield" /> 
								
							</div>							
							<div class="element">
									<label><spring:message code="eip.agency.hospital.address.hindi" /> :</label>
									<form:input path=""  value="${command.agencyHospital.hiAddrMar}"  maxlength="500" readonly="true"  id="hospitalAddress_Hondi"   cssClass="disablefield"/> 
								    
							</div>

						</div>
						
						<div class="form-elements">							
							<div class="element">
									<label><spring:message code="eip.agency.location" /> :</label>
									<form:input path=""  value="${command.entity.agencyLocation}" maxlength="500" tabindex="7" readonly="true" rows="1" cssClass="disablefield"/>
							</div>
							<div class="element">
									<label><spring:message code="eip.agency.panNo" /> :</label>
									<form:input path="" value="${command.entity.panCardNo} " maxlength="10" readonly="true" cssClass="disablefield" tabindex="8"/>
							</div>
						</div>
						<div class="form-elements">							
							
							<div class="element">
									<label><spring:message code="eip.agency.registrationNo" /> :</label>
									
									<form:input path="" value="${command.agencyHospital.agencyId}" disabled="true"  maxlength="10" readonly="true" cssClass="disablefield"/>
									
							</div>
							<div class="element">
									<label><spring:message code="eip.agency.contactNo" /> :</label>
									<form:input path="" value= "${command.entity.empmobno}" maxlength="10" readonly="true" cssClass="disablefield" tabindex="10"/>
							</div>
							
						</div>	
						<div class="form-elements">							
							<div class="element">
									<label><spring:message code="eip.agency.authorize.name" /> :</label>
									<form:input path="" value= "${command.entity.authorizedRName}" disabled="true" maxlength="100" readonly="true" cssClass="disablefield" />
							</div>
							<div class="element">
									<label><spring:message code="eip.agency.loginForDEO.name" /> :</label>
									<form:input path="" value= "${command.entity.empmobno}" maxlength="10" readonly="true"  cssClass="disablefield" /> 
							</div>
						</div>	
						<div class="form-elements">							
							
							<div class="element">
									<label for="type"> <spring:message
											code="eip.agency.hospital.type" text="Hospital Type " /> :
									</label>
									<form:input path="" value="${command.agencyHospital.agencyId}" readonly="true" cssClass="disablefield" />
								</div>
								<div class="element">
									<label><spring:message code="eip.agency.hospital.code" text="Hospital Code" /> :</label>
									<form:input path=""   value="${command.agencyHospital.hiCode}" cssClass="disablefield" readonly="true"/>
								</div>
								
						</div>
					</div>	
				