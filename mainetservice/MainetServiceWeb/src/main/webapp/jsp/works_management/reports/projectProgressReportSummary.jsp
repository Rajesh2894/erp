<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/reports/projectProgressReport.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="project.progress"
					text="Project Progress Report" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="ProjectProgressReport.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="ProjectProgressReport.html"
				class="form-horizontal" id="projectProgressReport"
				name="projectProgressReport">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group padding-top-25">
					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sor.fromdate" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control datepicker"
								id="fromDate" readonly="true" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=fromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sor.todate" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control datepicker"
								id="toDate" readonly="true" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=toDate></label>
						</div>
					</div>
				</div>

				<div class="text-center padding-bottom-20 padding-top-20">
					<button class="btn btn-primary hidden-print " type="button"
						onclick="viewProjectProgressReport();">
						<i class="fa fa-print padding-right-5"></i>
						<spring:message code="work.estimate.report.print" text="Print" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						onclick="window.location.href='ProjectProgressReport.html'">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="" />
					</button>
					<%-- 					<button class="btn btn-blue-2 " onclick="">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button> --%>
				</div>

			</form:form>
		</div>
	</div>
</div>