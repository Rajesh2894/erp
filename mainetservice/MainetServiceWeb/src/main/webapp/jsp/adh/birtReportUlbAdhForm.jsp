<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/adh/hoardingRegister.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="adh.Ulb.reports"
					text="ADH ULB Reports" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="adh.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="HoardingRegister.html" cssClass="form-horizontal"
				id="c">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="adh.report.type" text="Report Type" /></label>
                        <div class="col-sm-4">
						<form:select path="reportType"
							id="reportType"
							class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="adh.select" text="Select" /></form:option>
		<form:option value="A"><spring:message code="adh.reoprt.Advertisement.Register" text="Advertisement Register Report" /></form:option>
		<form:option value="B"><spring:message code="adh.report.AdvertisementPermit.Register" text="Advertisement Permit Register" /></form:option>
		<%-- <form:option value="C"><spring:message code="adh.report.Dcb.Register.Report" text="Dcb Register Report" /></form:option>
		<form:option value="D"><spring:message code="adh.report.Demand.Register" text="Demand Register Report" /></form:option>
		<form:option value="E"><spring:message code="adh.report.DemandNotice.RegisterReport=" text="Demand Notice Register Report" /></form:option> --%>
		<form:option value="F"><spring:message code="adh.report.Hoarding.Register" text="Hoarding Register Report" /></form:option>
		<form:option value="L"><spring:message code="adh.report.List.Of.Defaulters" text="List Of Defaulters Report" /></form:option>
		



						</form:select>
					</div>



			</div>
				

				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveUlbAdhForm(this)">
						<spring:message code="adh.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetUlbform">
						<spring:message code="adh.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="adh.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>






