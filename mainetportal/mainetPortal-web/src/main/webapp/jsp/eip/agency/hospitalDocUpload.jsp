
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
google.load("elements", "1", {
	packages : "transliteration"
});
 $( document ).ready(function() {
	var options = {
		sourceLanguage : google.elements.transliteration.LanguageCode.ENGLISH,
		destinationLanguage : [ google.elements.transliteration.LanguageCode.HINDI ],
		shortcutKey : 'ctrl+g',
		transliterationEnabled : true
	};
	
	var control = new google.elements.transliteration.TransliterationControl(
			options);
	
	control.makeTransliteratable([ 'hospitalName_hindi','hospitalAddress_Hondi']);
	


 });
</script>

				<div class="regheader">
					<spring:message code="eip.agency.header.hospital" />
				</div>
					<div class="form-elements clear">
						<div class="form-elements">							
							<div class="element">
									<label><spring:message code="eip.agency.type" /> :</label>
									<form:input path="" value="${command.agencyType}" maxlength="100" readonly="true" cssClass="disablefield" tabindex="1"/>
							</div>
							
							<div class="element">
									<label><spring:message code="eip.agency.ownerOrCharity.name" /> :</label>
									<form:input path="" value="${userSession.getEmployee().getEmpname() }" maxlength="200" readonly="true" cssClass="disablefield" tabindex="2"/> 
							</div>
						</div>
						<div class="form-elements">
							<div class="element">
									<label><spring:message code="eip.agency.hospital.name" /> :</label>
									<form:input path="" value="${userSession.getEmployee().getAgencyName()}" maxlength="200" readonly="true" cssClass="disablefield" tabindex="3"/> 
								
							</div>							
							<div class="element">
									<label><spring:message code="eip.agency.hospital.reg.name" /> :</label>
									<form:input path="entity.hospitalNameInHindi"  maxlength="500" id="hospitalName_hindi" tabindex="4"  cssClass="mandClassColor"/> 
								    <span class="mand">*</span>
							</div>

						</div>
						<div class="form-elements">
							<div class="element">
									<label><spring:message code="eip.agency.hospital.address.eng" /> :</label>
									<form:input path="entity.empAddress1" value="${userSession.getEmployee().getEmpAddress()}" maxlength="500" readonly="true" cssClass="disablefield" tabindex="5"/> 
								
							</div>							
							<div class="element">
									<label><spring:message code="eip.agency.hospital.address.hindi" /> :</label>
									<form:input path="entity.hospitalAddressInHindi"  maxlength="500"  id="hospitalAddress_Hondi" tabindex="6"  cssClass="mandClassColor"/> 
								    <span class="mand">*</span>
							</div>

						</div>
						
						<div class="form-elements">							
							<div class="element">
									<label><spring:message code="eip.agency.location" /> :</label>
									
								  <c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">
									<form:input disabled="true" path="entity.agencyLocation" value="${userSession.getEmployee().getAgencyLocation()}" maxlength="500" tabindex="7" rows="1"  cssClass="mandClassColor"/>
									</c:if> 
									
									<c:if test="${(empty command.cfcAttachmentsAfterReject) and ((empty command.entity.isUploaded) or (command.entity.isUploaded eq 'N'))}">
									<form:input path="entity.agencyLocation"  value="${userSession.getEmployee().getAgencyLocation()}" maxlength="500" tabindex="7" rows="1" cssClass="mandClassColor"/>
									</c:if>
									
									 <c:if test="${(empty command.cfcAttachmentsAfterReject) and (command.entity.isUploaded eq 'Y')}">
									<form:input path="entity.agencyLocation" value="${userSession.getEmployee().getAgencyLocation()}" disabled="true"  maxlength="500" tabindex="7" rows="1" cssClass="mandClassColor"/>
									</c:if> 
									<span class="mand">*</span>
							</div>
							<div class="element">
									<label><spring:message code="eip.agency.panNo" /> :</label>
									<form:input path="" value="${userSession.getEmployee().getPanCardNo()} " maxlength="10" readonly="true" cssClass="disablefield" tabindex="8"/>
							</div>
						</div>
						<div class="form-elements">							
							
							<div class="element">
									<label><spring:message code="eip.agency.registrationNo" /> :</label>
									<c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">
									<form:input path="entity.agencyRegNo" disabled="true"  maxlength="10" cssClass="mandClassColor hasNumber" tabindex="9"/>
									</c:if>
									<c:if test="${(empty command.cfcAttachmentsAfterReject) and ((empty command.entity.isUploaded) or (command.entity.isUploaded eq 'N'))}">
									<form:input path="entity.agencyRegNo" disabled="" maxlength="10" cssClass="mandClassColor hasNumber" tabindex="9"/>
									
									</c:if>
									
									<c:if test="${(empty command.cfcAttachmentsAfterReject) and (command.entity.isUploaded eq 'Y')}">
									<form:input path="entity.agencyRegNo" disabled="true" value="${userSession.getEmployee().getAgencyRegNo()}" maxlength="10" cssClass="mandClassColor hasNumber" tabindex="9"/>
									</c:if>
									<span class="mand">*</span>
							</div>
							<div class="element">
									<label><spring:message code="eip.agency.contactNo" /> :</label>
									<form:input path="" value="${userSession.getEmployee().getEmpmobno()}" maxlength="10" readonly="true" cssClass="disablefield" tabindex="10"/>
							</div>
							
						</div>	
						<div class="form-elements">							
							<div class="element">
									<label><spring:message code="eip.agency.authorize.name" /> :</label>
									
									
									<c:if test="${(not empty command.cfcAttachmentsAfterReject)  and (fn:length(command.cfcAttachmentsAfterReject) > 0 )}">
									<form:input path="" value="" disabled="true" maxlength="100" cssClass="mandClassColor" tabindex="11"/>
									</c:if>
									<c:if test="${(empty command.cfcAttachmentsAfterReject) and ((empty command.entity.isUploaded) or (command.entity.isUploaded eq 'N'))}">
									<form:input path=""  maxlength="100" cssClass="mandClassColor" tabindex="11"/>
									</c:if>
									<c:if test="${(empty command.cfcAttachmentsAfterReject) and (command.entity.isUploaded eq 'Y')}">
									<form:input path="" value="" disabled="true" maxlength="100" cssClass="mandClassColor" tabindex="11"/>
									</c:if>
									<span class="mand">*</span>
							</div>
							<div class="element">
									<label><spring:message code="eip.agency.loginForDEO.name" /> :</label>
									<form:input path="" value="${userSession.getEmployee().getEmploginname()}" maxlength="10" readonly="true" cssClass="disablefield" tabindex="12"/> 
							</div>
						</div>	
						<div class="form-elements">							
							
							<div class="element">
									<label for="type"> <spring:message
											code="eip.agency.hospital.type" text="Hospital Type " /> :
									</label>
									<c:set var="baseLookupCode" value="HTY" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="entity.hospitalType" cssClass="subsize"
										selectOptionLabelCode="--Select Your Type--" hasId="true"
										tabIndex="13" isMandatory="true" />
									<span class="mand">*</span>
								</div>
								<div class="element">
									<label><spring:message code="eip.agency.hospital.code" text="Hospital Code" /> :</label>
									<form:input path="entity.hospitalCode"  maxlength="3" cssClass="hasCharacterInUperCase" tabindex="14"/>
								</div>
								
						</div>	
					</div>
					
			
					