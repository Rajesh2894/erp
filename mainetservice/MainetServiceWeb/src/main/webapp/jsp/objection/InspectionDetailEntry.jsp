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
<script type="text/javascript"
	src="js/objection/inspectionDetailEntry.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<b><spring:message code="obj.inspection.entry" /></b>
			</h2>

			<%-- <div class="additional-btn">
			<apptags:helpDoc url="objectionAddDetails.html"></apptags:helpDoc>
		</div> --%>
		</div>
		<div class="widget-content padding">
			<form:form action="InspectionDetailEntry.html"
				class="form-horizontal" name="InspectionDetailEntry"
				id="InspectionDetailEntry">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


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
										path="objectionDetailsDto.title" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="rti.title" disabled="true" />

									<apptags:input labelCode="obj.firstName"
										cssClass="hasCharacter" path="objectionDetailsDto.fName"
										isMandatory="true" isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="obj.middleName"
										cssClass="hasCharacter" path="objectionDetailsDto.mName"
										isDisabled="true"></apptags:input>
									<apptags:input labelCode="obj.lastName" cssClass="hasCharacter"
										path="objectionDetailsDto.lName" isMandatory="true"
										isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="applicantinfo.label.gender" /></label>
									<c:set var="baseLookupCode" value="GEN" />
									<apptags:lookupField items="${command.getLevelData('GEN')}"
										path="objectionDetailsDto.gender" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" disabled="true" />

									<apptags:input labelCode="obj.mobile1" cssClass="hasMobileNo"
										path="objectionDetailsDto.mobileNo" isMandatory="true"
										isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" for="EmailID"><spring:message
											code="obj.emailId" text="Email ID" /></label>
									<div class="col-sm-4">
										<form:input name="" type="email"
											class="form-control mandColorClass" id="EmailID"
											path="objectionDetailsDto.eMail" data-rule-required="true"
											data-rule-email="true" disabled="true"></form:input>
									</div>

									<apptags:input labelCode="obj.Aadhar"
										cssClass="form-control hasAadharNo"
										path="objectionDetailsDto.uid" 
										maxlegnth="12" isDisabled="true"></apptags:input>

								</div>
								<div class="form-group">
									<apptags:textArea isMandatory="true"
										labelCode="property.propertyaddress"
										path="objectionDetailsDto.address" maxlegnth="500"
										cssClass="preventSpace" isDisabled="true"></apptags:textArea>
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

								<div class="form-group">
									<apptags:input labelCode="obj.dept"
										path="objectionDetailsDto.deptName" isDisabled="true"></apptags:input>
									<apptags:input labelCode="obj.serviceName"
										path="objectionDetailsDto.serviceName" isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<c:set var="baseLookupCode" value="OBJ" />
									<apptags:select labelCode="obj.dept"
										items="${command.getLevelData(baseLookupCode)}"
										path="objectionDetailsDto.objectionOn"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showAll="false" isLookUpItem="true"
										disabledOption="true"></apptags:select>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										id="refNo" for="dept"><spring:message
											code="obj.pro.no.trade.no" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass"
											id="objectionReferenceNumber"
											path="objectionDetailsDto.objectionReferenceNumber"
											data-rule-required="true" disabled="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label "
										for="billNo"><spring:message code="obj.billNo" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass" id="billNo"
											path="objectionDetailsDto.billNo" data-rule-required="true"
											disabled="true"></form:input>

									</div>
									<apptags:input labelCode="obj.billDueDate"
										path="objectionDetailsDto.billDueDate" isDisabled="true"></apptags:input>


								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label "
										for="noticeNo"><spring:message code="obj.noticeNo" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass" id="noticeNo"
											path="objectionDetailsDto.noticeNo" data-rule-required="true"
											disabled="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="rtiDetails"><spring:message
											code="obj.appealobjdetails" /></label>
									<div class="col-sm-10">

										<form:textarea name="" cols="" rows=""
											class="form-control mandColorClass" id="ObjDetails"
											path="objectionDetailsDto.objectionDetails" disabled="true"></form:textarea>
									</div>
								</div>
							</div>

						</div>

					</div>
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"><spring:message
								code="obj.inspection.det" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="form-group padding-top-10">
							<label class="col-sm-2 control-label required-control"
								for="billNo"><spring:message code="obj.inspection.aut" /></label>
							<div class="col-sm-4">
								<form:select path="hearingInspectionDto.empId"
									class="form-control mandColorClass" id="empId">
									<form:option value="0">
										<spring:message code="obj.selcet.emp" />
									</form:option>
									<c:forEach items="${command.getEmpList()}" var="lookUp"
										varStatus="key">
										<form:option value="${lookUp.lookUpId}"
											label="${lookUp.descLangFirst}"></form:option>
									</c:forEach>
								</form:select>
							</div>

							<apptags:textArea labelCode="obj.InspectionRemark"
								path="hearingInspectionDto.remark" isMandatory="true"></apptags:textArea>
						</div>

						<!-- deptCode for RTI and inspection status-->
						<form:hidden path="objectionDetailsDto.deptCode" id="deptCode" />
						
						<form:hidden path="objectionDetailsDto.inspectionStatus" id="inspectionStatus" />
						<!-- deptCode for RTI and inspection status-->
						

						<div class="form-group">
							<div id="owner">
								<apptags:radio radioLabel="obj.owner,obj.representative"
									radioValue="O,R" labelCode="obj.Selection"
									path="hearingInspectionDto.availPerson"></apptags:radio>
							</div>
							<!-- </div> -->

							<div id="rtiapplicant">
								<!-- <div class="form-group padding-top-10 "> -->
								<apptags:radio radioLabel="obj.applicant,obj.representative"
									radioValue="A,E" labelCode="obj.Selection"
									path="hearingInspectionDto.availPerson" changeHandler="displayApplicantName(this)"></apptags:radio>
							</div>
						</div>
						<div class="form-group">
							<div id="appName">
								<apptags:input labelCode="obj.applicant.name"
									path="hearingInspectionDto.applicantName" isMandatory="true"
									cssClass="hasCharacter" maxlegnth="50"></apptags:input>
							</div>
						
							<div id="appRepName">
								<apptags:input labelCode="obj.applicant.rep.name"
									path="hearingInspectionDto.applicantRepName" isMandatory="true"
									cssClass="hasCharacter" maxlegnth="50"></apptags:input>
							</div>
						</div>


						<div class="form-group">
							<div id="name">
								<apptags:input labelCode="obj.NameOfPerson"
									path="hearingInspectionDto.name" isMandatory="true"></apptags:input>
									</div>
									</div>
								<div></div>
								<div class="form-group">
									<apptags:input labelCode="obj.mobile1" cssClass="hasMobileNo"
										path="hearingInspectionDto.mobno" isMandatory="true"></apptags:input>

									<label class="col-sm-2 control-label" for="EmailID"><spring:message
											code="obj.emailId" text="Email ID" /></label>
									<div class="col-sm-4">
										<form:input type="email" class="form-control mandColorClass"
											id="emailid" path="hearingInspectionDto.emailid"
											data-rule-required="true" data-rule-email="true"></form:input>
									</div>
								</div>
							</div>
							<div class="padding-top-10 text-center">
								<button type="button" class="btn btn-success btn-submit"
									id="save" onclick="submitEntryDetails(this)">
									<spring:message code="obj.submit" />
								</button>
								<input type="button" class="btn btn-warning"
									value="<spring:message
										code="reset.msg" text="Reset" />"
									onclick="resetForm(this)" id="Reset" />
							</div>
						</div>
			</form:form>
		</div>

	</div>
</div>