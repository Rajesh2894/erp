<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/solid_waste_management/allLogBookReport/CdwasteCenterInputReport.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="CD Waste Center Input Report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="get" class="form-horizontal">
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
					<label class="control-label col-sm-2 required-control" for="Month"><spring:message
							code="" text="Month"></spring:message></label>
					<c:set var="baseLookupCode" value="MON" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleMasterDTO.monthNo"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />

					<label for="select-1479374267587"
						class="col-sm-2 control-label required-control"><spring:message
							code="swm.waste.wise.segregation.dumping.site" text="MRF Center" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="mrfId" data-rule-required="true">
							<form:option value="" selected="true">
								<spring:message code="solid.waste.select" text="Select" />
							</form:option>
							<c:forEach items="${command.mRFMasterDtoList}" var="lookUp">
								<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onClick="cdWasteCenterInputReport('CDWasteCenterInputReport.html')"
						data-original-title="Print">
						<spring:message code="solid.waste.print" text="Print"></spring:message>
					</button>
					<button type="Reset" class="btn btn-warning" onclick="resetArea()">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>