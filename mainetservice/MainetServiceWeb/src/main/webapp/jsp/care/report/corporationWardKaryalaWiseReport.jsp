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
	src="js/care/corporationWardKaryalaWiseReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="care.corporation.statistics.report"
					text="Corporation Ward Karyalaya Wise Report" />

			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message code="" text="" /></span>
			</div>
			<form:form action="CareReportBirtForm.html"
				cssClass="form-horizontal" id="careCorporationReports">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<!-- Format-2 -->
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="" text="Report Type" /></label>
					<div class="radio col-sm-4 margin-top-5">

						<label> <form:radiobutton
								path="careReportRequest.reportName" value="D" id="detailCorp"
								onclick="getReportType(this)" /> <spring:message
								code="care.stats.detail" text="Detail" /></label> <label> <form:radiobutton
								path="careReportRequest.reportName" value="S" id="summaryCorp"
								onclick="getReportType(this)" /> <spring:message
								code="care.stats.summary" text="Summary" />
						</label>


					</div>

				</div>



				<div class="form-group">
					<label class="col-sm-2"><spring:message code="" text="" /></label>
					<div class="radio col-sm-4 margin-top-5">
						<div id="corporationType">

							<label> <form:radiobutton
									path="careReportRequest.reports" value="R" id="serviceCorp"
									onclick="getReportType(this)" /> <spring:message
									code="care.service.birt.report" text="Service Request" /></label> <label>
								<form:radiobutton path="careReportRequest.reports" value="A"
									id="complaintCorp" onclick="getReportType(this)" /> <spring:message
									code="care.complaintWise.birt.report" text="Complaint Wise" />
							</label>
						</div>
					</div>

				</div>




				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveCorporationForm(this)">
						<spring:message code="care.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform">
						<spring:message code="care.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="care.btn.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>






