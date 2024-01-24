<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<!-- <script type="text/javascript" src="js/mainet/file-upload.js"></script> -->
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript" src="js/dms/docManagement.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/dms/dmsFileUpload.js"></script>
<style>
table.table tbody tr td {
	font-size: 14px !important;
}
</style>
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Metadata Details" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>

			<!-- Start Form -->
			<form:form action="DocManagement.html" method="POST"
				commandName="command" class="form-horizontal form" name=""
				id="frmMetadata">
            <form:hidden path="dmsId" id="dmsId" />  
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "> <spring:message
							code="dms.department" text="Department"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="deptId" id="deptId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.departmentList}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>

					</div>
					<c:choose>
						<c:when test="${command.deptCode eq 'ADH'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="ADZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control" 
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'BND'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="BZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control" 
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'CFC'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="CWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'HSM'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="SZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'LTD'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="LZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'PYD'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="PZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'AS'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'WMS'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="ZWB" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'RL'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="LZB" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'RTI'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="RWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'SWM'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'ML'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="MWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'WT'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward" 
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control"
									showData="true" />
							</div>
						</c:when>

						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</div>



				<c:set var="i" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="frmMetadataTable">
							<thead>
								<tr>
									<th width="10%"><spring:message code="dms.srNo"
											text="Sr. No." /></th>
									<th><spring:message code="dms.metadata"
											text="Metadata Description" /></th>
									<th><spring:message code="dms.value" text="Value" /></th>
								</tr>
							</thead>
							<c:forEach items="${command.metadataList}" var="lookUp"
								varStatus="loop">
								<tbody>
									<td class="text-center">${loop.count}</td>
									<td class="text-center">${lookUp.lookUpDesc}</td>
									<td class="text-center"><form:hidden
											path="lookUpList[${i}].lookUpId" value="${lookUp.lookUpId}" />
										<form:input path="lookUpList[${i}].otherField"
											class="form-control" /></td>
									<c:set var="i" value="${i + 1}" scope="page" />
								</tbody>
							</c:forEach>
						</table>
					</div>
				</div>
				
				
				<div class="form-group">
					<c:set var="DocAction" value="D,R"
						scope="session" />
					<c:set var="DocLabel" value="Delete,Retention"
						scope="session" />
					<label class="col-sm-2 control-label"><spring:message
							code="dms.doc.docAction" text="Document Action" /> <span class="mand">*</span></label>
					<div class="col-sm-4">
						<label class="radio-inline margin-top-5"> <form:radiobutton
								path="docAction" class="documentAction"
								id="delete" value="D" /> <spring:message code="dms.doc.delete"
								text="Delete" /></label> 
						<label class="radio-inline margin-top-5"> <form:radiobutton
								path="docAction" class="documentAction"
								id="retention" value="R" /> <spring:message code="dms.doc.retention"
								text="Retention" /></label>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="dms.docManagement.actType" text="Action Type" /> <span class="mand">*</span></label>
					<div class="col-sm-4">
						<label class="radio-inline margin-top-5"> <form:radiobutton
								path="docActType" class="actionType"
								id="byDay" value="BD" /> <spring:message code="dms.docManagement.actType.byDay"
								text="By No of Days" /></label> 
						<label class="radio-inline margin-top-5"> <form:radiobutton
								path="docActType" class="actionType"
								id="byDate" value="BDT" /> <spring:message code="dms.docManagement.actType.byDate"
								text="By Date" /></label>
					</div>
					
				</div>
				
				<div class="form-group" >
					<div id="docActDt">
					<apptags:date fieldclass="datepicker"
										labelCode="dms.docManagement.actType.date"
										isDisabled="false"
										isMandatory="true"
										datePath="docActionDate"></apptags:date>
					</div>
					<label class="col-sm-2 control-label required-control"
						for="noOfDays" id="deleteDayLbl"><spring:message
							code="dms.doc.deleteDayLbl" text="No of Deletion Days" /></label>
					<label class="col-sm-2 control-label required-control"
						for="noOfDays" id="retentionDayLbl"><spring:message
							code="dms.doc.retentionDayLbl" text="No of Retention Days" /></label>
					<div class="col-sm-4" id="noOfDays">
						<form:input name="noOfDays" type="text"
							class="form-control hasNumber" 
							path="noOfDays" maxlength="10"></form:input>
					</div>
				
					<div id="retrvlDays">
					<label class="col-sm-2 control-label required-control"
						for="noOfDays"><spring:message
							code="dms.doc.RetrivalDays" text="Number of days document can be retrieved after retention" /></label>
					<div class="col-sm-4">
						<form:input name="retrvlDays" type="text"
							class="form-control hasNumber" id="docRtrvlDays"
							path="docRtrvlDays" maxlength="10"></form:input>
					</div>
					</div>
				</div>
				
				<div class="text-center clear padding-10">
					<input type="button" value="<spring:message code="bt.save"/>"
						onclick="saveForm(this)" class="btn btn-success" id="Save">
					<button type="button" id="reset"
						onclick="window.location.href='DocManagement.html'"
						class="btn btn-warning" title="Reset">
						<spring:message code="bt.clear" text="Reset" />
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>

				</div>

			</form:form>
		</div>
	</div>
</div>