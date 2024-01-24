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
	src="js/works_management/workOrderGeneration.js"></script>
<script type="text/javascript">
	function printdiv(printpage) {
		debugger;
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
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="receipt">
		<div class="widget-content padding">
			<form:form action="PwWorkOrderGeneration.html"
				class="form-horizontal" name="workOrderPrinting"
				id="workOrderPrinting">
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
							<spring:message code="work.order.generation" text="Work Order" />
						</h3>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class=" col-sm-2 col-xs-2">
						<p>
							<spring:message code="work.order.generation.printing.letter.no"
								text="Letter No.:" />
						</p>
					</div>
					<div class="col-sm-6 col-xs-5 text-left">
						<p>
							<b>${command.workOrderDto.workOrderNo}</b>
						</p>
					</div>
					<div class="col-sm-1 col-xs-2">
						<p>
							<spring:message code="work.order.generation.workOrder.date"
								text="Date:" />
						</p>
					</div>
					<div class="col-sm-3 col-xs-3 text-left ">
						<p>
							<b class="dateFormat">${command.workOrderDto.orderDateDesc}</b>
						</p>
					</div>
				</div>
				<div class="row margin-top-30 clear">
					<div class="col-sm-12">
						<p>To,</p>
						<p>
							<!-- VendorName -->
							<b> ${command.contractAgreementSummaryDTO.contp2Name}</b>
						</p>

						<p>
							<!-- Address -->
							<b> ${command.contractAgreementSummaryDTO.address}</b>
						</p>
						<p>
							<!-- email -->
							<b> ${command.contractAgreementSummaryDTO.emailId}</b>
						</p>

						<p class="padding-top-20">
							<spring:message code="work.order.generation.printing.subject"
								text="Subject:" />
							<b>${command.tenderWorkDto.workName}</b>
						</p>
						<p class="padding-top-20">
							<spring:message code="work.order.generation.printing.reference"
								text="Reference:" />
							<spring:message code="work.order.office.system" />
							<b>${command.tenderWorkDto.tenderNo}</b>
							<spring:message code="work.order.generation.printing.tender.date" />
							<b>${command.tenderWorkDto.tenderDateDesc}</b>
						</p>
						<p class="padding-top-20">
							<spring:message code="work.order.sir.madam" text="Sir/Madam," />
						</p>
						<p class="padding-top-20">
							<spring:message code="work.order.content3" />
							<b>${userSession.organisation.ONlsOrgname}</b>
							<spring:message code="work.order.content4" />
							<b>${command.contractAgreementSummaryDTO.contDate}</b>
							<spring:message code="work.order.content5" />
						</p>
						<p class="padding-top-20 margin-bottom-20">
							<spring:message code="work.order.content6" />
						</p>
						<div class="col-sm-6 col-xs-6">
							<ol>
								<li><spring:message code="work.order.agreementno" /></li>
								<li><spring:message code="work.order.probable.amt" /></li>
								<li><spring:message code="work.order.completion.period" /></li>
								<li><spring:message
										code="work.order.sanctionedrate.contract" /></li>
								<li><spring:message
										code="work.order.sanctionedamount.contract" /></li>
							</ol>
						</div>

						<div class="col-sm-6 col-xs-6">
							<ul>
								<li><b>${command.contractAgreementSummaryDTO.contNo}</b></li>
								<li><b>${command.tenderWorkDto.workEstimateAmt}</b></li>
								<li><b>${command.tenderWorkDto.vendorWorkPeriod}
										${command.tenderWorkDto.unitDesc}</b></li>
								<li><b>${command.tenderWorkDto.tenderValue} <c:if
											test="${command.tenderWorkDto.tenderTypeDesc eq 'Percentage'}">
											<spring:message code="work.order.percentage" />
										</c:if> ${command.tenderWorkDto.action}
										${command.scheduleOfRateMstDto.sorName}
								</b></li>
								<li><b>Rs.${command.contractAgreementSummaryDTO.contAmount}</b></li>
							</ul>
						</div>

					</div>
				</div>
				<div class="clear"></div>

				<div class="row">

					<div class="col-xs-12 col-sm-12 padding-top-20 margin-top-30">
						<p>
							<b>${userSession.organisation.ONlsOrgname}</b>
						</p>
						<p>
							<spring:message code="tender.loa.signatory"
								text="Authorized Signatory" />
						</p>
					</div>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="printdiv('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print padding-right-5"></i>
						<spring:message code="work.estimate.report.print" text="Print" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" onclick="backWorkOrder();"
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>