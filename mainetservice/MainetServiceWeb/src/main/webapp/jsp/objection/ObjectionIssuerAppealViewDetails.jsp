<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/objection/objectionIssuerAppealDetails.js"></script>

<div class="widget">
	<div class="widget-header">
		<h2>
			<b><spring:message code="obj.objappealissuerdetails"></spring:message></b>
		</h2>
		<div class="additional-btn">
			<apptags:helpDoc url="objectionAddDetails.html"></apptags:helpDoc>
		</div>
	</div>
<div class="pagediv">
	<div class="widget-content padding">
		<form:hidden path="command.isValidationError" value="${command.isValidationError}" id="isValidationError" />
		<form:form method="POST" action="ObjectionDetails.html"
			class="form-horizontal" id="ObjIssuerDetailsForm"
			name="rtiObjIssuerDetailsForm">
			<div class="compalint-error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
			</div>

			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a1"> <spring:message
								code="obj.objappealissuerdetails" /></a>
					</h4>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="applicantTitle"><spring:message code="obj.title" /></label>
								<c:set var="baseLookupCode" value="TTL" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="objectionDetailsDto.title"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showOnlyLabel="rti.title" disabled="true" />

								<apptags:input labelCode="obj.firstName" cssClass="hasCharacter"
									path="objectionDetailsDto.fName" isMandatory="true" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="obj.middleName"
									cssClass="hasCharacter"
									path="objectionDetailsDto.mName" isDisabled="true"></apptags:input>
								<apptags:input labelCode="obj.lastName" cssClass="hasCharacter"
									path="objectionDetailsDto.lName" isMandatory="true" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.gender" /></label>
								<c:set var="baseLookupCode" value="GEN" />
								<apptags:lookupField items="${command.getLevelData('GEN')}"
									path="objectionDetailsDto.gender"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" disabled="true"/>

								<apptags:input labelCode="obj.mobile1" cssClass="hasMobileNo"
									path="objectionDetailsDto.mobileNo" isMandatory="true" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label" for="EmailID"><spring:message
										code="obj.emailId" text="Email ID" /></label>
								<div class="col-sm-4">
									<form:input  type="email"
										class="form-control mandColorClass" id="EmailID"
										path="objectionDetailsDto.eMail"
										data-rule-required="true" data-rule-email="true" disabled="true"></form:input>
								</div>
								<apptags:input labelCode="obj.Aadhar" cssClass="hasAadharNo"
									path="objectionDetailsDto.uid" isMandatory="true" isDisabled="true"></apptags:input>

							</div>
							<div class="form-group">
						<apptags:textArea isMandatory="true" labelCode="obj.address" path="objectionDetailsDto.address" 
						maxlegnth="500" cssClass="preventSpace" isDisabled="true"></apptags:textArea>

							</div>

						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a2"> <spring:message
								code="obj.objappealdetails" /></a>
					</h4>
					<div id="a2" class="panel-collapse collapse in">
						<div class="panel-body">

<%-- 							<div class="form-group">
								<apptags:select labelCode="obj.dept"
									items="${command.departments}"
									path="objectionDetailsDto.objectionDeptId"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showAll="false" isLookUpItem="true" changeHandler="getObjectionServiceByDept()"></apptags:select>

								<label class="col-sm-2 control-label required-control"
										for="objtype"><spring:message code="obj.objectiontype" /></label>								
								<apptags:lookupField
										items="${command.serviceList}"
										path="objectionDetailsDto.serviceId" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="obj.title" />
							</div> --%>
							
							<div class="form-group">							
								<apptags:input labelCode="obj.dept" path="objectionDetailsDto.deptName" isDisabled="true"></apptags:input>
								<apptags:input labelCode="obj.serviceName" path="objectionDetailsDto.serviceName" isDisabled="true"></apptags:input>
							</div>
						
							<div class="form-group">
							<c:set var="baseLookupCode" value="OBJ" />
								<apptags:select labelCode="obj.ObjOn"
									items="${command.getLevelData(baseLookupCode)}"
									path="objectionDetailsDto.objectionOn"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showAll="false" isLookUpItem="true" disabledOption="true"></apptags:select>
									
							</div>
				
					 <div class="form-group" >
		<label class="col-sm-10 text-red">Kindly upload Bill Copy in case of Bill</label>
	</div>			
<div class="form-group">
			<label class="col-sm-2 control-label required-control" id="refNo"
				for="dept"><spring:message code="obj.ReferenceNo" /></label>
			<div class="col-sm-4">
				<form:input  class="form-control mandColorClass" id="objectionReferenceNumber"
					path="objectionDetailsDto.objectionReferenceNumber"
					data-rule-required="true" disabled="true"></form:input>
			</div>
			
			<br><br>
			<div class="form-group">
			<label class="col-sm-10 text-red">&nbsp&nbsp&nbsp&nbsp<spring:message code="obj.note"/></label>
			</div>
			
	</div>			
		<div class="form-group">
			<label class="col-sm-2 control-label" 
				for="billNo">Bill No</label>
			<div class="col-sm-4">
				<form:input class="form-control mandColorClass hasNumber" id="billNo"
					path="objectionDetailsDto.billNo" disabled="true"
					></form:input>
			</div>
		<label class="col-sm-2 control-label" for="billDueDate"> <%-- <spring:message code="obj.HearingInspectionDateTime"/> --%> Bill Due Date</label>
							<div class="col-sm-4">
							<div class="input-group"> 
							<form:input path="objectionDetailsDto.billDueDate" class="form-control datepicker" id="inspectionDate" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off" disabled="true"/>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>	
							</div>
							</div>
	</div>

	
			<div class="form-group">
			<label class="col-sm-2 control-label"
				for="noticeNo">Notice No</label>
			<div class="col-sm-4">
				<form:input class="form-control mandColorClass" id="noticeNo"
					path="objectionDetailsDto.noticeNo" disabled="true"></form:input>
			</div>
	</div>
	
							<div id="objectionReferenceNumber">
							</div>
							<div class="form-group">
							<apptags:textArea isMandatory="true" labelCode="obj.appealobjdetails" path="objectionDetailsDto.objectionDetails" maxlegnth="45" cssClass="preventSpace" isDisabled="true"></apptags:textArea>
							</div>
							<div class="form-group">
									
								<apptags:input labelCode="obj.location" path="objectionDetailsDto.LocName" isDisabled="true"></apptags:input>
								
								
				<c:if test="${not empty command.documentList}">	
							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="water.serialNo" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="water.docName" text="Document Name" /></label></th>
												     <th><label class="tbold"><spring:message
																code="water.download"/></label></th>
												</tr>

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose>
														  <td>
														  <c:set var="links" value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
															<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}" actionUrl="SelfAssessmentForm.html?Download"></apptags:filedownload>
					                                    </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
					 </c:if>     
							</div>
						</div>
					</div>
				</div>
				<div class="padding-top-10 text-center">
					
					<button class="btn btn-danger" type="button"  onclick="backToMainPage(this)" id="back">
										<spring:message code="property.Back"/></button>
					
				</div>
			</div>
		</form:form>
	</div>
</div>
</div>