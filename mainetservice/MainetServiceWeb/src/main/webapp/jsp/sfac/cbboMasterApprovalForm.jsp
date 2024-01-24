<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/CBBOMasterForm.js"></script>



<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="cbbo.approval.form.title"
					text="Cluster Based Business Organization On-Boarding Approval Form" />
			</h2>
			<apptags:helpDoc url="CBBOMasterApproval.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="cbboMasterApprovalForm"
				action="CBBOMasterApproval.html" method="post"
				class="form-horizontal">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<h4 class="panel-title">
					<spring:message code="sfac.cbbo.data.entry" text="Data Entry" />
				</h4>

				<div class="panel-body">
					<div class="form-group">

						<apptags:input labelCode="sfac.pan.no" isDisabled="true"
							cssClass="mandColorClass alphaNumeric charCase"
							path="masterDto.panNo" isMandatory="true" maxlegnth="10"
							placeholder="BLUCS4233S"></apptags:input>


						<apptags:input labelCode="sfac.CBBO.name" isDisabled="true"
							cssClass="mandColorClass" path="masterDto.cbboName"
							maxlegnth="200" isMandatory="true"></apptags:input>
					</div>


					<div class="form-group">
						<apptags:input labelCode="sfac.IA.name" isReadonly="true"
							cssClass="mandColorClass hasNameClass" path="masterDto.IAName"
							isMandatory="true" maxlegnth="100"></apptags:input>


						<label class="col-sm-2 control-label required-control" for="state">
							<spring:message code="sfac.state" text="State" />
						</label>
						<div class="col-sm-4">
							<form:input path="masterDto.state" type="text"
								class="form-control" readonly="true" />
						</div>

					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="sfac.EmpanelmentOfCbbo" text="Empanelment of Cbbo" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="masterDto.alYrCbbo" type="text"
									onchange="getAppoitmentYear();"
									class="form-control datepicker mandColorClass alcYearToCBBO"
									id="alcYearToCBBO" placeholder="dd/mm/yyyy" disabled="true"/>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

						<label class="col-sm-2 control-label"><spring:message
								code="sfac.appointment.year" text="CBBO Appointment Year" /></label>
						<div class="col-sm-4">
							<form:input path="masterDto.appYear" id="appYear" readonly="true"
								class="form-control mandColorClass" />
						</div>
					</div>



					<div class="form-group">

						<apptags:input labelCode="sfac.address" cssClass="mandColorClass"
							isDisabled="true" path="masterDto.address" maxlegnth="300"></apptags:input>

						<apptags:input labelCode="sfac.pincode"
							cssClass="mandColorClass hasPincode" isMandatory="true"
							isDisabled="true" path="masterDto.pinCode" maxlegnth="6"></apptags:input>
					</div>




					<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="regAct"> <spring:message
								code="sfac.fpo.typeOfPromotionAge"
								text="Type of Promotion Agency" />
						</label>
						<c:set var="baseLookupCode" value="PAT" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="masterDto.typeofPromAgen" disabled="true"
							cssClass="form-control chosen-select-no-results"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="sfac.select" isMandatory="true" />

						<apptags:input labelCode="sfac.cbbo.fpoAllocationTarge"
							cssClass="mandColorClass hasNumber" isMandatory="true"
							isDisabled="true" path="masterDto.fpoAllocationTarget"
							maxlegnth="3"></apptags:input>

					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="" text="Status" /></label>
						<div class="col-sm-4">
							<form:select path="masterDto.activeInactiveStatus"
								class="form-control chosen-select-no-results"
								id="activeInactiveStatus" disabled="true"
								data-rule-required="true">
								<form:option value="0"><spring:message code="sfac.select" text="Select" />
								</form:option>
								<form:option value="A"><spring:message code="sfac.active" text="Active" />
								</form:option>
								<form:option value="I"><spring:message code="sfac.inactive" text="InActive" />
								</form:option>
							</form:select>
						</div>
					</div>

				</div>


				<h4 class="panel-title">
					<spring:message code="sfac.contact.details" text="Contact Details" />
				</h4>

				<div class="panel-body">
					<c:set var="d" value="0" scope="page"></c:set>
					<div class="table-responsive">
						<table
							class="table table-bordered table-striped contact-details-table"
							id="contactDetails">
							<thead>
								<tr>
									<th width="5%"><spring:message code="sfac.srno"
											text="Sr. No." /></th>
									<th><spring:message code="sfac.designation"
											text="Designation" /></th>
									<th width="10%"><spring:message code="sfac.title"
											text="Title" /></th>
									<th><spring:message code="sfac.first.name"
											text="First Name" /></th>
									<th><spring:message code="sfac.middel.name"
											text="Middle Name" /></th>
									<th><spring:message code="sfac.last.name" text="Last Name" /></th>
									<th><spring:message code="sfac.contact.no"
											text="Contact No." /></th>
									<th><spring:message code="sfac.emailId" text="Email Id" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="detDto" items="${command.masterDto.cbboDetDto}"
									varStatus="status">
									<tr class="appendableContactDetails">
									<td class="text-center">${status.count}</td>
										<td class="text-center">${detDto.designation}</td>
										<td class="text-center">${detDto.title}</td>
										<td class="text-center">${detDto.fName}</td>
										<td class="text-center">${detDto.mName}</td>
										<td class="text-center">${detDto.lName}</td>
										<td class="text-center">${detDto.contactNo}</td>
										<td class="text-center">${detDto.emailId}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				
					<div class="form-group">
					<apptags:radio radioLabel="sfac.approve,sfac.reject"
						radioValue="APPROVED,REJECTED" isMandatory="true"
						labelCode="sfac.decision" path="masterDto.appStatus"
						defaultCheckedValue="APPROVED">
					</apptags:radio>
					<apptags:textArea labelCode="sfac.remark"
						path="masterDto.remark" isMandatory="true"
						cssClass="hasNumClass form-control" maxlegnth="100" />
				</div>

				<div class="text-center padding-top-10">
					<button type="button" align="center" class="btn btn-green-3"
						data-toggle="tooltip" data-original-title="Submit"
						onclick="saveApprovalData(this);">
						<spring:message code="sfac.submit" text="Submit" />
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>


			</form:form>
		</div>
	</div>
</div>
