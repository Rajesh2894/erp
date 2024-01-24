<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/account/stopPayment.js"></script>
<script src="js/mainet/script-library.js"></script>
<!-- <script type="text/javascript" src="/js/adh/hoardingMaster.js"></script> -->
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="accounts.stop.payment.title"
					text="Stop Payment"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="StopPayment.html" cssClass="form-horizontal" id="StopPayment">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<form:hidden path="stopPaymemtReqDto.checqueStatusCode" id="checqueStatusCode"/>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<apptags:input labelCode="accounts.stop.payment.payment.number" path="paymentEntryDto.paymentNo"></apptags:input>
					<apptags:input labelCode="accounts.stop.payment.cheque.number" path="paymentEntryDto.instrumentNumber"></apptags:input>
				</div>
				<div class="form-group">
					<apptags:date labelCode="accounts.stop.payment.date.label" datePath="paymentEntryDto.paymentDate"
						fieldclass="datepicker" isMandatory="true"></apptags:date>
				</div>
				<div class="text-center margin-bottom-10" id="searchButtons">
					<button type="button" class="btn btn-success" id="" title="Search"
						onclick="searchPaymentDetails()">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="accounts.stop.payment.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" data-toggle="tooltip"
						data-original-title="Reset" onclick="window.location.href='StopPayment.html'">
						<i class="fa fa-undo padding-right-5" area-hidden="true"></i>
						<spring:message code="accounts.stop.payment.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back" onclick="window.location.href='AdminHome.html'">
						<i class="fa fa-arrow-left padding-right-5" area-hidden="true"></i>
						<spring:message code="accounts.stop.payment.back" text="Back"></spring:message>
					</button>
				</div>
				
				<div id="showPaymentDetail">
				<h4 class="margin-top-0">
					<a data-toggle="collapse" class="collapsed" href="#a1"> <spring:message
							code="accounts.payment.details.title" text="Payment Details" />
					</a>
				</h4>
				
				<div class="table-responsive">
				<div class="table-responsive margin-top-10">
				<table class="table table-striped table-condensed table-bordered" id="stopPayment">
					<thead>
						<tr>
							<th><spring:message code="accounts.stop.payment.payment.number" text="Payment No"></spring:message></th>
							<th><spring:message code="accounts.stop.payment.date.label" text="Payment Date"></spring:message></th>
							<th><spring:message code="accounts.stop.payment.vendor.name" text="Vendor Name"></spring:message></th>
							<th><spring:message code="accounts.stop.payment.bank.account" text="Bank Account"></spring:message></th>
							<th><spring:message code="accounts.stop.payment.cheque.number" text="Cheque Number"></spring:message></th>
							<th><spring:message code="accounts.stop.payment.payment.amount" text="Payment Amount"></spring:message></th>
						</tr>
					</thead>
				
				<tbody>
					<tr>
						<td><form:input path="stopPaymemtReqDto.paymentEntryDto.paymentNo" id="paymentNumber" cssClass="form-control" disabled="true"></form:input></td>	
						<td><form:input path="stopPaymemtReqDto.paymentEntryDto.paymentEntryDate" id="paymentdate" cssClass="form-control" disabled="true"></form:input></td>
						<td><form:input path="stopPaymemtReqDto.tbAcVendormaster.vmVendorname" id="vmVendorid" cssClass="form-control" disabled="true"></form:input></td>
						<td><form:input path="stopPaymemtReqDto.bankAccountMasterDto.baAccountNo" id="baAccountNo" cssClass="form-control" disabled="true"></form:input></td>
						<td><form:input path="stopPaymemtReqDto.paymentEntryDto.instrumentNumber" id="instrumentNo" cssClass="form-control" disabled="true"></form:input></td>
						<td><form:input path="stopPaymemtReqDto.paymentEntryDto.paymentAmount" id="paymentAmount" 
						cssClass="form-control mandColorClass hasDecimal text-right" maxlength="10"  
													onkeypress="return hasAmount(event, this, 8, 2)" disabled="true"></form:input></td>
					</tr>
				</tbody>
				</table>
				</div>
				<h4 class="margin-top-0">
					<a data-toggle="collapse" class="collapsed" href="#a1"> <spring:message
							code="accounts.stop.payment.details.title" text="Stop Payment Details" />
					</a>
				</h4>
			</div>
				<div class="form-group">
					<apptags:date labelCode="accounts.stop.payment.date"
						datePath="chequebookleafDetDto.stoppayDate" fieldclass="datepicker" isMandatory="true"></apptags:date>
					<apptags:textArea labelCode="accounts.stop.payment.reason" path="chequebookleafDetDto.stopPayRemark"
						isMandatory="true"></apptags:textArea>
				</div>

				<div class="text-center">
					<button type="button" class="btn btn-success" data-toggle="tooltip"
						data-original-title="Save" onclick="save(this)">
						<i class="fa fa-floppy-o padding-right-5" area-hidden="true"></i>
						<spring:message code="accounts.stop.payment.save" text="Save"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" data-toggle="tooltip"
						data-original-title="Reset" onclick="window.location.href='StopPayment.html'">
						<i class="fa fa-undo padding-right-5" area-hidden="true"></i>
						<spring:message code="accounts.stop.payment.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back" onclick="window.location.href='AdminHome.html'">
						<i class="fa fa-arrow-left padding-right-5" area-hidden="true"></i>
						<spring:message code="accounts.stop.payment.back" text="Back"></spring:message>
					</button>
				</div>
				</div>
			</form:form>
		</div>
	</div>
</div>