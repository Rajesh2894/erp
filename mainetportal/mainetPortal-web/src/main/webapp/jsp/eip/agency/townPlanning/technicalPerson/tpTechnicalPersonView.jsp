<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
			<div class="regheader">
				<spring:message code="eip.techPersonDet"/>
			</div>
				<div class="form-elements clear form">
					<div class="table clear">
							<div class="col-155">
								<label><spring:message code="tp.nameOfApplicant" /> :</label>
							</div>
							<div class="col-50">
								<c:set var="baseLookupCode" value="TTL" />
								<form:input path="" value="${command.licenseMaster.titleString}"  cssClass=" hasCharacter input2 disablefield" disabled="true"/>
								
							</div>
							<div class="col-130">
								<form:input path="licenseMaster.licFname" cssClass=" hasCharacter input2 disablefield" disabled="true"/>
								
							</div>
							<div class="col-165">
								<form:input path="licenseMaster.licMname"   cssClass="input2 disablefield" disabled="true"/>
							</div>
							<div class="col-180">
								<form:input path="licenseMaster.licLname"  cssClass=" hasCharacter input2 disablefield" disabled="true"/>
								</div>
					</div>
					<div class="form-elements clear">							
							<div class="element">
									<label><spring:message code="eip.citizen.reg.dateOfBirth" /> :</label>
									<form:input path="entity.empdob" maxlength="10" readonly="true" cssClass="datepicker cal_disable" disabled="true"/>
							</div>
							<c:set var="baseLookupCode" value="TRY" />
							<div class="element">
								<label><spring:message code="eip.nationality"/> :</label>
								<apptags:lookupField items="${command.getLevelData(baseLookupCode,1)}"
									path="licenseMaster.licNationality" selectOptionLabelCode="tp.SelectNationality" isMandatory="true"  hasId="true" showOnlyLabel="true"/>								
							</div>
					</div>
					<div class="form-elements clear">	
							<div class="element">
									<label><spring:message code="eip.qualification"/> :</label>
									<form:input path="licenseMaster.licQualification" maxlength="50" cssClass="disablefield hasCharacterWithPeriod" disabled="true" /> 
							</div>
							<div class="element">
									<label><spring:message code="eip.experience"/> :</label>
									<form:input path="licenseMaster.licYearofexp" maxlength="4"  cssClass="has3Decimal disablefield" disabled="true"/>
							</div>
					</div>	
					
					<c:set value="${command.getCpdValue()}" var="cpdValue"/>	
					<div class="form-elements clear">							
						<div class="element">
								<label><spring:message code="eip.regnNo"/>
									<c:if test="${cpdValue eq  'A'}">
									<spring:message code="eip.coa"/>
									</c:if>
									<c:if test="${cpdValue eq  'D'}">
									<spring:message code="eip.itpi"/>
									</c:if>
									<c:if test="${cpdValue eq  'G' || cpdValue eq  'F' }">
									<spring:message code="eip.ie"/>
									</c:if>
								:</label>
								<form:input path="licenseMaster.licCntArcregno"  cssClass="disablefield" disabled="true"/>
						</div>
						<div class="element">
								<label><spring:message code="eip.regnNoULB"/> :</label>
								<form:input path=""  cssClass="disablefield" disabled="true"/> 
						</div>
					</div>

					<div class="form-elements clear">							
						<div class="element">
								<label><spring:message code="eip.admin.contactUs.address" /> :</label>
								<form:textarea path="licenseMaster.licApplicantAddr" maxlength="500"  rows="1"  cssClass="disablefield" disabled="true"/>
						</div>
						<div class="element">
								<label><spring:message code="eip.payment.mobileNo" /> :</label>
								<form:input path="licenseMaster.licMobileno"  maxlength="10" readonly="true" cssClass="disablefield" disabled="true"/>
						</div>
					</div>
					<div class="form-elements">
						<div class="element">
							<label><spring:message code="tp.TelNo"/> :</label>
							<form:input path="licenseMaster.licPhoneno"  cssClass="disablefield" disabled="true"/>
						</div>
						<div class="element">
							<label><spring:message code="eip.admin.auth.email"/> :</label>
							<form:input path="licenseMaster.licEmail"  cssClass="disablefield" disabled="true"/>
						</div>
					</div>
				</div>
					