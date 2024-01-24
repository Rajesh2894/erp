<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="date" class="java.util.Date" />
<script src="js/property/billingMethodChange.js" type="text/javascript"></script>
<style>
.widget {
	padding: 40px;
}

.widget-content.padding {
	padding: 20px;
}

.margin-top-60 {
	margin-top: 60px;
}

.padding-left-20p {
	padding-left: 20%;
}

.border-bottom-black {
	border-bottom: 3px solid #000000
}

.cert-outer-div {
	border: 3px solid #000000;
}

.clear {
	clear: both;
}
</style>
<div class="content">
	<form:form action="BillingMethodChange.html"
		class="form-horizontal form" name="frmBillMethodWorkOrder"
		id="frmBillMethodWorkOrder">
		<div class="widget" id="receipt">
			<div class="widget-header">
				<h2>
					<strong><spring:message code=""
							text=" Work order generation" /></strong>
				</h2>
			</div>
			<div class="widget-content padding">



				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="cert-outer-div">
					<div class="border-bottom-black margin-0">
						<div class="overflow-hidden padding-vertical-10">
							<div class="col-xs-2">
								<img width="80" src="${userSession.orgLogoPath}">
							</div>
							<div class="col-xs-8  text-center">
								<h3 class="margin-bottom-0">
									<span class="text-bold text-center">
										${userSession.organisation.ONlsOrgnameMar}</span>
								</h3>
							</div>
							<div class="col-xs-2"></div>
							<div class="clear"></div>
						</div>
					</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-12 col-sm-12 padding-top-5">
							<p align="center" style="font-weight: bold;">
								<spring:message code="property.ack.applicationNo"
									text="Application No." />${command.workflowActionDto.applicationId}
							</p>
						</div>
					</div>

					<div class="clearfix padding-top-20"></div>
					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2 padding-top-5">
							<span class="text-bold"><spring:message
									code="property.ack.applicantName" text="Applicant Name" /></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20">${command.applicantName}</span>
						</div>
					</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2 padding-top-5">
							<span class="text-bold"><spring:message
									code="property.ack.serviceName" text="Service Name" /></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							: <span class="margin-left-10"></span><span>${command.serviceName}</span>
						</div>
					</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2 padding-top-5">
							<span class="text-bold"><spring:message
									code="property.ack.deptName" text="Department Name" /></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20"><spring:message
									code="property.propertyTaxDept" text="Property tax" /></span>
						</div>
					</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2 padding-top-5">
							<span class="text-bold"><spring:message
									code="property.ack.applicationDate" text="Application Date" /></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20"><fmt:formatDate
									value="${date}" pattern="dd/MM/yyyy" /></span>

						</div>
						<div class="col-xs-2 col-sm-2 text-right padding-top-5">
							<span class="text-bold"><spring:message
									code="property.ack.applicationTime" text="Application time" /></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20"> ${command.currentTime}</span>
						</div>
					</div>

					<div class="clearfix"></div>
					<div class="padding-5 clear">&nbsp;</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-12 col-sm-12 padding-top-5">
							<p align="center" style="font-weight: bold;">
								<spring:message code="property.ack.comp.generate"
									text="computer generated" />
							</p>
						</div>
					</div>
				</div>
			</div>

		</div>
		<div class="text-center padding-bottom-10">
			<button type="button" class="btn btn-blue-2" id="serchBtn"
				onclick="saveWorkOrder(this)">
				<spring:message code="bt.save" />
			</button>

			<button onclick="printContent('receipt')"
				class="btn btn-primary hidden-print">
				<i class="icon-print-2"></i>
				<spring:message code="" text="Print"></spring:message>
			</button>

			<button type="button" class="btn btn-danger" id="back"
				onclick="window.location.href='AdminHome.html'">
				<spring:message code="bt.backBtn" text="Back"></spring:message>
			</button>
		</div>
	</form:form>
</div>


<script>
	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>