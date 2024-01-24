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
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">

	<div class="widget">
		<!-- Start Main Page Heading -->
		<div class="widget-header">
			<h2>
				<spring:message code="population.master.heading"
					text="Population Master" />
			</h2>
			<apptags:helpDoc url="PopulationMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="PopulationMaster.html" name="PopulationMaster"
				id="PopulationMasterForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="Year"><spring:message
							code="population.master.year" text="Census Year"></spring:message></label>
					<c:set var="baseLookupCode" value="CYR" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="populationMasterDTO.popYear"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

					<apptags:input labelCode="population.master.population"
						path="populationMasterDTO.popEst" isMandatory="true"
						maxlegnth="10" cssClass="form control mandcolour hasNumber">
					</apptags:input>
				</div>
				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
						 pathPrefix="populationMasterDTO.codDwzid"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control margin-bottom-10 "
						showAll="false" columnWidth="20%" />
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="population.master.status" text="Status" /> </label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton
								id="status1" path="populationMasterDTO.popActive" value="Y"
								checked="checked" /> <spring:message code="swm.Active"
								text="Active" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="status2" path="populationMasterDTO.popActive" value="N" />
							<spring:message code="swm.Inactive" text="Inactive" />
						</label>

					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="savePopulationMasterForm(this);">
						<spring:message code="solid.waste.submit" text="Submit"></spring:message>
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backPopulationMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>