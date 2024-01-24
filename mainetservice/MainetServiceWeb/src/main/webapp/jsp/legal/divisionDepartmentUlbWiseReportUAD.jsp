<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="js/legal/divisionDepartmentUlbWiseReportUAD.js"></script>
<script src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lgl.uad.form"
					text="Division Department UlbWise UAD Report " />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="legal.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="divisionDepartmentUad.html"
				cssClass="form-horizontal" id="divisionForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="legal.report.type" text="Report Type" /></label>

					<div class="radio col-sm-4 margin-top-5">
						<label> <form:radiobutton path="legalReport.csereportName"
								value="D" id="Detail" onclick="getReportType()" /> <spring:message
								code="lgl.division.Department.report"
								text="DivisionWiseDepartmentWiseReport" />
						</label> <label> <form:radiobutton
								path="legalReport.csereportName" value="S" id="Summary"
								onclick="getReportType()" /> <spring:message
								code="lgl.division.ulbwise.report"
								text="DivisionWiseUlbWiseReport" />
						</label>
					</div>

				</div>

				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveDivsionUabForm(this)">
						<spring:message code="legal.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform">
						<spring:message code="legal.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="legal.btn.back"></spring:message>
					</a>
				</div>



			</form:form>
		</div>
	</div>
</div>








