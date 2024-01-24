


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="js/fire_management/vehLogBookReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="VehicleLogBookReportDTO.logBookDetails"
						text="Vehicle Log Book"></spring:message></strong>
				<apptags:helpDoc url="VehLogBookReport.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="VehLogBookReport.html" id="frmVehLogBook"
				method="POST" commandName="command" class="form-horizontal form">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				<div class="form-group">

					<apptags:date fieldclass="datepicker"
						labelCode="VehicleLogBookReportDTO.fromDate" isMandatory="true"
						datePath="vehicleLogBookReportDTO.fromDate"
						cssClass="custDate mandColorClass date">
					</apptags:date>
					<apptags:date fieldclass="datepicker"
						labelCode="VehicleLogBookReportDTO.toDate" isMandatory="true"
						datePath="vehicleLogBookReportDTO.toDate"
						cssClass="custDate mandColorClass date">
					</apptags:date>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="vehicle"><spring:message
							code="VehicleLogBookReportDTO.veNo" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select id="veNo" path="vehicleLogBookReportDTO.veNo"
							cssClass="form-control" data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${ListDriver}" var="vehicle">
								<%-- <form:option value="${vehicle.veNo}">${vehicle.veNo}</form:option> --%>
								<form:option value="${vehicle}">${vehicle}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center margin-top-10">
					<input type="button" onClick="searchForm(this);"
						value="<spring:message code="bt.search"/>" class="btn btn-success"
						id="Search">
					<button type="Reset" class="btn btn-warning"
						onclick="window.location.href='VehLogBookReport.html'">
						<spring:message code="lgl.reset" text="Reset"></spring:message>
					</button>
					<input type="button"
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value='<spring:message code="bt.backBtn" text="Back" />'>
				</div>
			</form:form>
		</div>
	</div>
</div>
