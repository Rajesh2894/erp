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
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/adh/inspectionEntry.js"></script>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="adh.inspection.entry.title"
					text="Inspection Entry"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span> <spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="InspectionEntry.html" name="inspectionEntry"
				id="inspectionEntry" cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label"> <spring:message
							code="adh.label.advertisement.license.no"
							text="Advertisement License No"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="advertisementDto.licenseNo" id="licenseNo"
							class="chosen-select-no-results" data-rule-required="true"
							isMandatory="true" onchange="getAgencyName()">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.licenseNumberList }"
								var="licenseNumberList">
								<form:option value="${licenseNumberList}">${licenseNumberList }</form:option>
							</c:forEach>
						</form:select>
					</div>
					<apptags:input labelCode="adh.label.advertiser.name"
						path="advertisementDto.agencyName" isDisabled="true"></apptags:input>
				</div>

				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="ADZ" hasId="true"
						pathPrefix="advertisementDto.adhZone"
						hasLookupAlphaNumericSort="true" disabled="true"
						hasSubLookupAlphaNumericSort="true" isMandatory="false"
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" />

					<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
						pathPrefix="advertisementDetailDto.adhTypeId"
						hasLookupAlphaNumericSort="true" disabled="true"
						hasSubLookupAlphaNumericSort="true" isMandatory="false"
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" />
				</div>

				<div class="form-group">
					<apptags:date labelCode="adh.label.inspection.date"
						datePath="inspectionEntryDto.inesDate" fieldclass="datepicker"
						isMandatory="true"></apptags:date>
					<label class="col-sm-2 control-label"> <spring:message
							code="adh.label.inspector.name" text="Inspector Name"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="inspectionEntryDto.inesEmpId" id="inesEmpId"
							isMandatory="true" class="chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.inspectorName}" var="list">
								<form:option value="${list.empId }">${list.empname }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="adh.label.inspection.details.title"
						text="Inspection Details"></spring:message>
				</h4>
				<div class="overflow margin-top-10">
					<c:set var="d" value="0" scope="page"></c:set>
					<table class="table table-hover table-bordered table-striped"
						id="inspectionTableId">
						<thead>
							<tr>
								<th scope="col" width="5%"><spring:message code="adh.sr.no"
										text="Sr.No." /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="adh.label.trms.condition" text="Terms/Conditions" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="adh.status" text="Status" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="adh.label.observation" text="Observation" /></th>
								<th scope="col" width="5%"><a href="javascript:void(0);"
									class=" btn btn-blue-2 btn-sm addInspection"> <i
										class="fa fa-plus-circle"></i>
								</a></th>
							</tr>
						</thead>
						<tbody>
							<tr class="inspectionEntryTr">
								<td align="center" width="5%"><form:input path=""
										cssClass="form-control mandColorClass " id="sequence${d}"
										value="${d+1}" disabled="true" /></td>
								<td><form:select
										path="inspectionEntryDto.inspectionEntryDetDto[0].remarkId"
										id="remarkId${d}" isMandatory="true" class="form-control"
										data-rule-required="true">
										<form:option value="">
											<spring:message code="adh.select" text="Select"></spring:message>
										</form:option>
										<c:forEach items="${command.remark}" var="remark">
											<form:option value="${remark.artId }">${remark.artRemarks }</form:option>
										</c:forEach>
									</form:select></td>

								<td><c:set var="baseLookupCode" value="AIS" /> <form:select
										path="inspectionEntryDto.inspectionEntryDetDto[0].remarkStatusId"
										class="form-control" id="remarkStatusId${d}">
										<form:option value="">
											<spring:message code="adh.select" text="Select"></spring:message>
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>
								<td><form:textarea
										path="inspectionEntryDto.inspectionEntryDetDto[0].observation"
										readonly="false" class="form-control" id="observation${d}"
										isMandatory="true" /></td>
								<td align="center" width="3%"><a href="javascript:void(0);"
									class="btn btn-danger btn-sm delButton" title="Delete"
									onclick=""><i class="fa fa-minus"></i> </a></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="text-center">
					<button type="button" class="btn btn-success" data-toggle="tooltip"
						data-original-title="Save" onclick="save(this)">
						<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.save" text="Save"></spring:message>
					</button>
					<button type="button" class="btn btn-blue-2" data-toggle="tooltip"
						data-original-title="Generate Notice" onclick="savePublicNotice(this)">
						<i class="fa fa-pencil padding-right-5" aria-hidden="true"></i>
						<spring:message code="" text="Generate Notice"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" data-toggle="tooltip"
						data-original-title="Reset" onclick="resetInspEntry(this);">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back"
						onclick="window.location.href='InspectionEntry.html'">
						<i class="fa fa-chevron-circle-left padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.back" text="Back"></spring:message>
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>