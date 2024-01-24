<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<script>
	function printdiv(printpage) {
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget invoice" id="loa">
		<div class="widget-content padding">
			<form:form action="TenderInitiation.html" class="form-horizontal"
				name="TenderInitiation" id="TenderInitiation"
				modelAttribute="command">
				<div class="row">
					<div class="col-xs-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
					<div class="col-xs-6 text-center">
						<h3 class="margin-bottom-0">
							<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
						</h3>
						<p>
							<spring:message code=""
								text="${userSession.organisation.orgAddress}" />

						</p>
					</div>
					<div class="col-xs-3 text-right">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
				</div>
				<div class="row margin-top-10">
					<div class="col-xs-12">
						<h3 class="text-bold text-center">
							<spring:message code="tender.loa.latter"
								text="Letter Of Acceptance(LOA)" />
						</h3>
					</div>
				</div>

				<div class="row margin-top-10 clear">
					<div class=" col-sm-2 col-xs-2">
						<p>
							<spring:message code="tender.loa.no" text="LOA No.:" />
						</p>
					</div>
					<div class="col-sm-6 col-xs-6 text-left">
						<b><p>${command.loaTenderDetails.tndLOANo}</p></b>
					</div>
					<div class="col-sm-1 col-xs-1">
						<p>
							<spring:message code="tender.loa.date" text="Date:" />
						</p>
					</div>
					<div class="col-sm-3 col-xs-3 text-left">
						<b><p>${command.loaTenderDetails.tndLoaDateFormat}</p></b>
					</div>
				</div>

				<div class="row margin-top-30 clear">
					<div class="col-sm-12">
						<p>To,</p>
						<p>
							<b>${command.loaTenderDetails.vendorName}</b>
						</p>
						<p>
							<b>${command.loaTenderDetails.vendorAddress}</b>
						</p>
						<p class="padding-top-20 ">
							<spring:message code="tender.loa.subject" text="Subject:" />
							<b>${command.loaTenderDetails.workName}</b>
						</p>

						<p class="padding-top-10">
							<spring:message code="tender.content" text="" />
						</p>

						<div class="padding-top-10 col-sm-6 col-xs-6">
							<ol>

								<li><spring:message code="work.order.tender.no"
										text="Subject:" /></li>
								<li><spring:message code="tender.tenderdate"
										text="Subject:" /></li>
								<li><spring:message code="tender.tenderestimateamt"
										text="Subject:" /></li>
								<li><spring:message code="tender.tenderemdamount"
										text="Subject:" /></li>
							</ol>
						</div>
						<div class="padding-top-10 col-sm-6 col-xs-6">
							<ul>
								<li><strong> ${command.loaTenderDetails.tenderNo}</strong></li>
								<li><strong>
										${command.loaTenderDetails.tndDateFormat}</strong></li>
								<li><strong>
										${command.loaTenderDetails.workEstimateAmt}</strong></li>
								<li><strong>${command.loaTenderDetails.tenderSecAmt}</strong></li>

							</ul>
						</div>
						<p class="padding-left-10 clear"></p>

						<p class="padding-top-20">
							<b></b> </b>
							<spring:message code="tender.content1" text="" />
						</p>
						<p>
							<spring:message code="tender.content2" text="" />
						</p>
						<b>${command.loaTenderDetails.tenderStampFee}</b>
						<spring:message code="tender.content3" text="" />
						<b>${command.loaTenderDetails.tenderNoOfDayAggremnt}</b>
						<spring:message code="tender.content4" text="" />
						<p></p>
						<%-- <p><spring:message code="tender.loa.note" text="Note:" /></p> --%>
						<div class="row margin-top-30 clear">
							<p class="margin-bottom-10">
								<spring:message code="tender.copyto" text="Subject:" />
							</p>
							<ol style="margin-left: 30px">
								<li><spring:message code="tender.copyto1" text="Subject:" /></li>
								<li><spring:message code="tender.copyto2" text="Subject:" /></li>
								<li><spring:message code="tender.copyto3" text="Subject:" /></li>
							</ol>
						</div>
					</div>
					<div class="clear"></div>
					<div class="row">

						<div class="col-xs-3 col-xs-push-9 text-center margin-top-50">
							<p>${userSession.organisation.ONlsOrgname}</p>
							<p>
								<spring:message code="tender.loa.signatory"
									text="Authorized Signatory" />
							</p>
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="printdiv('loa');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print padding-right-5"></i>
							<spring:message code="work.estimate.report.print" text="Print" />
						</button>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='TenderInitiation.html'"
							id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="Back" />
						</button>
					</div>
			</form:form>
		</div>
	</div>
</div>
<!-- End of Content -->