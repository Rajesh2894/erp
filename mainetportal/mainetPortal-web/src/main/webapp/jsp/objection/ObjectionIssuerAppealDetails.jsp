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
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/objection/objectionIssuerAppealDetails.js"></script>
<div class="widget">
	<div class="widget-header">
		<h2>
			<b><spring:message code="obj.objappealissuerdetails"></spring:message></b>
		</h2>
		<div class="additional-btn">
			<apptags:helpDoc url="objectionAddDetails.html"></apptags:helpDoc>
		</div>
	</div>

	<div class="widget-content padding">
		<form:hidden path="command.isValidationError" value="${command.isValidationError}" id="isValidationError" />
		<form:form method="POST" action="ObjectionDetails.html"
			class="form-horizontal" id="ObjIssuerDetailsForm"
			name="rtiObjIssuerDetailsForm">
			<div class="compalint-error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
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
									for="title"><spring:message code="obj.title" /></label>
								<c:set var="baseLookupCode" value="TTL" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="objectionDetailsDto.title"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showOnlyLabel="rti.title" />

								<apptags:input labelCode="obj.firstName" cssClass="hasCharacter"
									path="objectionDetailsDto.fName" isMandatory="true"></apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="obj.middleName"
									cssClass="hasCharacter"
									path="objectionDetailsDto.mName"></apptags:input>
								<apptags:input labelCode="obj.lastName" cssClass="hasCharacter"
									path="objectionDetailsDto.lName" isMandatory="true"></apptags:input>
							</div>

							<div class="form-group">
								<label for="gender" class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.gender" /></label>
								<c:set var="baseLookupCode" value="GEN" />
								<apptags:lookupField items="${command.getLevelData('GEN')}"
									path="objectionDetailsDto.gender"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" />

								<apptags:input labelCode="obj.mobile1" cssClass="hasMobileNo"
									path="objectionDetailsDto.mobileNo" isMandatory="true"></apptags:input>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label" for="EmailID"><spring:message
										code="obj.emailId" text="Email ID" /></label>
								<div class="col-sm-4">
									<form:input  type="email"
										class="form-control mandColorClass" id="EmailID"
										path="objectionDetailsDto.eMail"
										data-rule-required="true" data-rule-email="true"></form:input>
								</div>
								<apptags:input labelCode="obj.Aadhar" cssClass="hasAadharNo"
									path="objectionDetailsDto.uid" isMandatory="true"></apptags:input>

							</div>
							<div class="form-group">
						<apptags:textArea isMandatory="true" labelCode="property.propertyaddress" path="objectionDetailsDto.address" 
						maxlegnth="500" cssClass="preventSpace"></apptags:textArea>

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
								<apptags:select labelCode="obj.dept"
									items="${command.departments}"
									path="objectionDetailsDto.objectionDeptId"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showAll="false" isLookUpItem="true" changeHandler="getObjectionServiceByDept()"></apptags:select>
							
						
							<label class="col-sm-2 control-label required-control" for="serviceId"><spring:message
								code="obj.serviceName"/></label>
								<div class="col-sm-4">
					
								<form:select path="objectionDetailsDto.serviceId"
											 class="form-control mandColorClass" id="serviceId" data-rule-required="true">
											<%--  <c:set var="serviceIdVal" value="${command.objectionDetailsDto.serviceId}" />
												<form:option value="0">
													select Service
												</form:option>
												<c:forEach items="${command.serviceList}"
													var="lookUp">
													<form:option  value="${lookUp.lookUpId}" selected="${lookUp.lookUpId == serviceIdVal ? 'selected' : ''}">${lookUp.lookUpDesc}</form:option>		
													<form:option value="${lookUp.lookUpId}" 
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach> --%>
												<c:forEach items="${command.getServiceList()}"
													var="lookUp">
													<form:option  value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>	
													
												</c:forEach>
									</form:select> 
								
								</div>
							
								</div>
							
							<div class="form-group">
							<c:set var="baseLookupCode" value="OBJ" />
								<apptags:select labelCode="obj.ObjOn"
									items="${command.getLevelData(baseLookupCode)}"
									path="objectionDetailsDto.objectionOn"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showAll="false" isLookUpItem="true"></apptags:select>
									
							</div>
				
					 <div class="form-group" >
		<i class="col-sm-10 text-red">Kindly upload Bill Copy in case of Bill</i>
	</div>			
<div class="form-group">
			<label class="col-sm-2 control-label required-control" id="refNo"
				for="objectionReferenceNumber">Property No/RTI No/Trade License No</label>
			<div class="col-sm-4">
				<form:input  class="form-control mandColorClass" id="objectionReferenceNumber"
					path="objectionDetailsDto.objectionReferenceNumber"
					data-rule-required="true"></form:input>
			</div>
	</div>			
		<div class="form-group">
			<label class="col-sm-2 control-label" 
				for="billNo">Bill No</label>
			<div class="col-sm-4">
				<form:input class="form-control mandColorClass hasNumber" id="billNo"
					path="objectionDetailsDto.billNo"
					></form:input>
			</div>
		<label class="col-sm-2 control-label" for="inspectionDate"> <%-- <spring:message code="obj.InspectionDateTime"/> --%> Bill Due Date</label>
							<div class="col-sm-4">
							<div class="input-group"> 
							<form:input path="objectionDetailsDto.billDueDate" class="form-control datepicker" id="inspectionDate" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off"/>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>	
							</div>
							</div>
	</div>

	
			<div class="form-group">
			<label class="col-sm-2 control-label"
				for="noticeNo">Notice No</label>
			<div class="col-sm-4">
				<form:input class="form-control mandColorClass" id="noticeNo"
					path="objectionDetailsDto.noticeNo"></form:input>
			</div>
	</div>
	
							<div id="objectionReferenceNumber">
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="ObjDetails"><spring:message
										code="obj.appealobjdetails" /></label>
								<div class="col-sm-10">
									<!-- <textarea type="textarea" required
										class="form-control mandColorClass" name="textarea"
										id="objappeal" data-rule-required="true"></textarea> -->
									<form:textarea name="" cols="" rows="" class="form-control mandColorClass" id="ObjDetails" path="objectionDetailsDto.objectionDetails"></form:textarea>
								</div>
							</div>
							<div class="form-group">

								<label for="locId" class="col-sm-2 control-label required-control"><spring:message
										code="obj.location" /></label>
								<%-- <apptags:lookupField items="${command.locations}" path="objectionDetailsDto.locId" cssClass="form-control"
												hasChildLookup="false" hasId="true" showAll="false" selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" showOnlyLabel="applicantinfo.label.title" /> --%>
												<div class="col-sm-4">
												<form:select path="objectionDetailsDto.locId"
											 class="form-control mandColorClass" id="locId">
												<form:option value="0"><spring:message code="obj.selLocation" text="Select Location"/></form:option>
												<c:forEach items="${command.getLocations()}"
													var="lookUp">
													<form:option  value="${lookUp.lookUpId}" label="${lookUp.lookUpDesc}"></form:option>		
												</c:forEach>
												
									</form:select>
									</div>
												
								<%-- <div class="col-sm-4">
									<form:input name="deptlocation" type="location"
										class="form-control mandColorClass hasCharacter"
										path="objectionDetailsDto.codIdOperLevel1"
										data-rule-required="true"></form:input>
								</div> --%>
								<label for="file" class="col-sm-2 control-label"><spring:message
										code="obj.fileUpload"></spring:message> </label>
								<div class="col-sm-4">
												<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath=""
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																			currentCount="0"/>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%-- <div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success" id="save"
						onclick="saveObjectionDetailForm(this)">
						<spring:message code="rti.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform"
						onclick="resetRtiObjDetail(this)">
						<spring:message code="rti.reset" />
					</button>
				</div> --%>
				<div id="rtiAppealPayment">
							<c:if test="${command.offlineDTO.amountToShow > '0'}">
									
								<apptags:payment></apptags:payment>
										
								<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"> <spring:message code="rti.amtpay" /></label>
								<div class="col-sm-4">
								<input type="text" class="form-control" value="${command.offlineDTO.amountToShow}" maxlength="12" readonly="readonly" />
								 <a class="fancybox fancybox.ajax text-small text-info" href="ObjectionDetails?showChargeDetails">
								<spring:message code="rti.amtpay" /> <i class="fa fa-question-circle "></i></a>
								</div>
								</div>
							</c:if>	
				</div>
				
				<div class="padding-top-10 text-center">
				
					<!-- <button type="button" class="btn btn-success" id="save"

					<button type="button" class="btn btn-success" id="save" -->

					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveObjectionDetailForm(this);">
						<spring:message code="rti.submit" />
					</button>
					
					<button type="button" class="btn btn-success" id="proceedbutton" style="display:none;"
						onclick="getCharges(this);">
						<spring:message code="unit.proceed" />
					</button>
					
					<button type="Reset" class="btn btn-warning" id="resetform"
						onclick="resetRtiObjDetail(this)">
						<spring:message code="rti.reset" />
					</button>
					
				</div>
			</div>
		</form:form>
	</div>
</div>