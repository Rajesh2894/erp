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
	src="js/solid_waste_management/report/VehicleWiseCollection.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Vehicle Wise Survey Report" />
			</h2>
			<apptags:helpDoc url="VehicleWiseCollectionReport.html"></apptags:helpDoc>
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
					<label for="select-1479365507176"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="Vehicle Wise Survey" /></label>
					<div class="col-sm-4">
						<form:select required="" path=""
							cssClass="form-control  chosen-select-no-results mandColorClass"
							id="VehicleWise" aria-required="true">
							<form:option value="" selected="true">
								<spring:message code="solid.waste.select" text="Select" />
							</form:option>
							<form:option value="VLBFP">Vehicle log book front page</form:option>
							<form:option value="VLBMP">vehicle log book main page</form:option>
							<form:option value="VDP">vehicle deployment plan</form:option>
							<form:option value="SWP">sweeping</form:option>
						</form:select>
					</div>
			<%-- 		<label for="select-1479365507176"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="Beat No." /></label>
					<div class="col-sm-4">
						<form:select required="" path=""
							cssClass="form-control  chosen-select-no-results mandColorClass"
							id="beatId" aria-required="true">
							<form:option value="" selected="true">
								<spring:message code="solid.waste.select" text="Select" />
							</form:option>
							<c:forEach items="${command.beatMasterList}" var="lookup">
								<form:option value="${lookup.beatId}">${lookup.beatNo } &nbsp; ${ lookup.beatName}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
				</div>
				<div class="form-group">
						<apptags:date labelCode="Date" fieldclass="fromDateClass"
							datePath="" cssClass="fromDateClass fromDate" 
							isMandatory="true">
						</apptags:date>
					<%-- <label class="control-label col-sm-2 required-control" for="Year"><spring:message
							code="" text="Month"></spring:message></label>
					<c:set var="baseLookupCode" value="MON" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleScheduleDTO.monthNo"
						cssClass="form-control required-control" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" /> --%>
					<label class="col-sm-2 control-label " for="vehicleno"><spring:message
							code="swm.vehicleno" /></label>
					<div class="col-sm-4">
						<form:select path="" class="form-control  "
							label="Select" id="veId">
							<form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
							<c:forEach items="${command.vehicleMasterList}" var="lookup">
								<form:option value="${lookup.veId}" code="${lookup.veId}">${lookup.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>	
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onClick="VehicleWiseSurveyReportPrint('VehicleWiseCollectionReport.html')"
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
