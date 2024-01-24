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
	src="js/solid_waste_management/report/DayMonthwiseReport.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="swm.day.wise.month.report.head"
					text="Day Month Wise Dumping Report" />
			</h2>
			<apptags:helpDoc url="DayMonthWiseDumping.html"></apptags:helpDoc>
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
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div id="a0" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label for="select-1479365507176"
										class="col-sm-2 control-label required-control"><spring:message
											code="swm.day.wise.month.report.disposal.site"
											text="MRF Center" /></label>
									<div class="col-sm-4">
										<form:select required="" path="mRFMasterDto.mrfPlantName"
											cssClass="form-control  chosen-select-no-results mandColorClass"  id="dname"
											aria-required="true">
											<form:option value="" selected="true">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<form:option value="0">
												<spring:message code="solid.waste.All" text="All" />
											</form:option>
											<c:forEach items="${command.mRFMasterDtoList}" var="lookUp">
												<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker" labelCode="swm.fromDate"
										datePath="mRFMasterDto.fromDate" isMandatory="true"
										cssClass="custDate mandColorClass">
									</apptags:date>
									<apptags:date fieldclass="datepicker" labelCode="swm.toDate"
										datePath="mRFMasterDto.toDate" isMandatory="true"
										cssClass="custDate mandColorClass">
									</apptags:date>
								</div>
							</div>
							<div class="text-center padding-top-10">
								<button type="button" class="btn btn-success btn-submit"
									onClick="DayMonthWiseReportPrint('DayMonthWiseDumping.html','report')"
									data-original-title="Print">
									<spring:message code="solid.waste.print" text="Print"></spring:message>
								</button>
								<button type="Reset" class="btn btn-warning"
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