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
				<spring:message code="public.toilet.master.form"
					text="Public Toilet Master Form" />
			</h2>
			
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="PublicToiletMaster.html" name="PublicToiletMaster"
				id="PublicToiletMasterForm" class="form-horizontal">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">

					<apptags:input labelCode="public.toilet.master.name"
						path="sanitationMasterDTO.sanName" isMandatory="true"
						cssClass="hasNameClass" isReadonly="true"></apptags:input>
					<label class="col-sm-2 control-label required-control" for="Census">
						<spring:message code="public.toilet.master.type"
							text="Toilet Type" />
					</label>

					<apptags:lookupField items="${command.getLevelData('TOT')}"
						path="sanitationMasterDTO.sanType"
						cssClass="form-control required-control chosen-select-no-results"
						selectOptionLabelCode="solid.waste.select" hasId="true"
						isMandatory="true" />

				</div>
				<div class="form-group">

					<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
						pathPrefix="sanitationMasterDTO.codWard"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control margin-bottom-10"
						showAll="false" columnWidth="20%" />

				</div>

				<div class="form-group">

					<apptags:input labelCode="public.toilet.master.seatCount"
						path="sanitationMasterDTO.sanSeatCnt" isMandatory="true"
						cssClass="hasNumber"></apptags:input>

					<apptags:input labelCode="public.toilet.master.gisno"
						path="sanitationMasterDTO.sanGisno" cssClass="hasNumber"></apptags:input>

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control"
						for="Location"><spring:message
							code="route.master.location" text="Location" /></label>

					<div class="col-sm-4">
						<form:select path="sanitationMasterDTO.sanLocId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="Location" data-rule-required="true">
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
								class="form-control hasNumber" id="AssetCode" ></form:input>
							<label class="input-group-addon"><i class="fa fa-globe"></i><span
								class="hide"><spring:message
										code="vehicle.master.vehicle.asset.code" text="AssetCode" /></span></label>
						</div>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="submit" class="btn btn-success btn-submit"
						onclick="savePublicToiletMaster(this);">
						<spring:message code="solid.waste.submit" text="Submit" />
					</button>
					<button type="Reset" class="btn btn-warning" onclick="resetForm() ">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
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