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
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/report/wasteWiseSegregation.js"></script>

<script>
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="swm.waste.wise.segregation.head"
					text="Waste Wise Segregation Report" />
			</h2>
			<apptags:helpDoc url="WasteWiseSegregation.html"></apptags:helpDoc>
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
					<div id="errorId"></div>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div id="a0" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label for="select-1479374267587"
										class="col-sm-2 control-label required-control"><spring:message
											code="swm.waste.wise.segregation.dumping.site"
											text="MRF Center" /></label>
									<div class="col-sm-4">
										<form:select path="wastageSegregationDto.deId"
											cssClass="form-control chosen-select-no-results mandColorClass"
											id="nrDisName" data-rule-required="true">
											<form:option value="" selected="true">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<form:option value="0">All</form:option>
											<c:forEach items="${command.mRFMasterDtoList}" var="lookUp">
												<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:lookupFieldSet baseLookupCode="WTY" hasId="true"
										pathPrefix="wastageSegregationDet.codWast" isMandatory="true"
										hasLookupAlphaNumericSort="true" isNotInForm="false"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control margin-bottom-10"
										showAll="false" columnWidth="20%" />

								</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										labelCode="vehicle.fuelling.fromDate"
										datePath="wastageSegregationDto.fromDate" isMandatory="true"
										cssClass="custDate mandColorClass">
									</apptags:date>
									<apptags:date fieldclass="datepicker" labelCode="vehicle.fuelling.toDate"
										datePath="wastageSegregationDto.toDate" isMandatory="true"
										cssClass="custDate mandColorClass">
									</apptags:date>
								</div>
							</div>
							<div class="text-center padding-top-10">
								<button type="button" class="btn btn-success btn-submit" title='<spring:message code="solid.waste.print" text="Print" />'
									onClick="wasteWiseSegregationPrint('WasteWiseSegregation.html','report')"
									data-original-title="View">
									<spring:message code="solid.waste.print" text="Print"></spring:message>
								</button>
								<button type="Reset" class="btn btn-warning" title='<spring:message code="solid.waste.reset" text="Reset" />'
									onclick="resetTrip()">
									<spring:message code="solid.waste.reset" text="Reset" />
								</button>
								<apptags:backButton url="AdminHome.html"></apptags:backButton>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>