<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/solid_waste_management/populationMaster.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/PublicToiletMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="public.toilet.master.view"
					text="Public Toilet Master View" />
			</h2>
		
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory"></spring:message></span>
			</div>
			<form:form action="PublicToiletMaster.html" name="PublicToiletMaster"
				id="PublicToiletMasterForm" class="form-horizontal">
				<div class="form-group">
					<apptags:input labelCode="public.toilet.master.name"
						path="sanitationMasterDTO.sanName" isMandatory="true"
						cssClass="hasNameClass" isDisabled="true"></apptags:input>
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="public.toilet.master.type" text="Toilet Type" /></label>
					<apptags:lookupField items="${command.getLevelData('TOT')}"
						path="sanitationMasterDTO.sanType"
						cssClass="form-control mandColorClass chosen-select-no-results"
						selectOptionLabelCode="solid.waste.select" hasId="true"
						isMandatory="true" disabled="true" />
				</div>
				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
						pathPrefix="sanitationMasterDTO.codWard"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control chosen-select-no-results"
						showAll="false" columnWidth="20%" disabled="true" />
				</div>
				<div class="form-group">
					<apptags:input labelCode="public.toilet.master.seatCount"
						path="sanitationMasterDTO.sanSeatCnt" isMandatory="true"
						cssClass="hasNumber" isDisabled="true"></apptags:input>
					<apptags:input labelCode="public.toilet.master.gisno"
						path="sanitationMasterDTO.sanGisno" cssClass="hasNumber"
						isDisabled="true"></apptags:input>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="Location"><spring:message
							code="route.master.location" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="sanitationMasterDTO.sanLocId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="Location" data-rule-required="true" disabled="true">
							<form:option value="0">
								<spring:message code="solid.waste.select" text="Select" />
							</form:option>
							<c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="control-label col-sm-2" for="AssetCode"><spring:message
							code="public.toilet.master.assetno" text="Asset Code" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" path="sanitationMasterDTO.assetId"
								class="form-control " id="AssetCode" disabled="true"></form:input>
							<label class="input-group-addon"><i class="fa fa-globe"></i><span
								class="hide"><spring:message
										code="vehicle.master.vehicle.asset.code" text="AssetCode" /></span></label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="employee.verification.status" text="Status" /> </label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton
								id="status1" path="sanitationMasterDTO.sanActive" value="Y"
								disabled="true" checked="checked" /> <spring:message
								code="swm.Active" text="Active" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="status2" path="sanitationMasterDTO.sanActive" value="N"
								disabled="true" /> <spring:message code="swm.Inactive"
								text="Inactive" />
						</label>
					</div>
				</div>
				<div class="text-center padding-top-10">

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backPublicToiletMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>