<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/transaction/tdsPaymentEntry.js"
	type="text/javascript"></script>
<script>
	$(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0',

		});
		$(".datepicker").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content form-div" id="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="tds.payment.entry" text="TDS PAYMENT ENTRY" /></h2>
			<apptags:helpDoc url="TdsPaymentEntry.html" helpDocRefURL="TdsPaymentEntry.html"></apptags:helpDoc>	
		</div>
		<div class="widget-content padding" id="frmdivId">

			<form:form action="TdsPaymentEntry.html"
				modelAttribute="tdsPaymentEntryDto" class="form-horizontal"
				id="tdsPaymentEntrylist">

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="successfulFlag" id="successfulFlag" />
				<div class="form-group">
					<label for="paymentEntryDate" class="col-sm-2 control-label"><spring:message
							code="advance.management.master.paymentdate" text=" Payment Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="paymentEntryDate"
								cssClass="datepicker cal form-control" name="paymentEntryDate"
								id="paymentEntryDate" maxlength="10" />
							<label class="input-group-addon" for="paymentEntryDate"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span>
								<input type="hidden" id="trasaction-date-icon30"> </label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="advance.management.master.paymentamount" text="Payment Amount" /></label>
					<div class="col-sm-4">
						<form:input path="total" cssClass="form-control text-right"
							name="paymentAmount" id="paymentAmount" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="accounts.deduction.register.vendorname" text="Vendor Name" /></label>
					<div class="col-sm-4">
						<form:select path="vendorName"
							class="form-control chosen-select-no-results" name="vmVendorname"
							id="vmVendorname">
							<option value=""><spring:message code="account.select"
									text="Select" /></option>
							<c:forEach items="${vendorList}" varStatus="status" var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="account.budgetopenmaster.accountheads" text="Account Head" /></label>
					<div class="col-sm-4">
						<form:select path="" class="form-control chosen-select-no-results"
							name="" id="budgetCodeId">
							<option value=""><spring:message code="account.select" text="Select" /></option>
							<c:forEach items="${expBudgetCodeMap}" varStatus="status"
								var="expItem">
								<form:option value="${expItem.key}" code="${expItem.key}">${expItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="accounts.payment.entry.no" text="Payment Entry No" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" name="paymentNo"
							id="paymentNo" />
					</div>
					<label for="bankId" class="col-sm-2 control-label"><spring:message
							code="account.fund.bank.acc" text="Bank Account" /></label>
					<div class="col-sm-4">
						<form:select id="baAccountid" path=""
							cssClass="form-control chosen-select-no-results" disabled=""
							onchange="">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${bankAccountMap}" varStatus="status"
								var="bankItem">
								<form:option value="${bankItem.key}">${bankItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success" id="searchBtn"
						onclick="searchTdsPayData()">
						<i class="fa fa-search"></i>&nbsp;
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						onclick="window.location.href='TdsPaymentEntry.html'" id="backBtn">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<button type="button" value="Contra Entry"
						class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
					</button>
				</div>
			</form:form>
			<table id="tdspaymentEntryGrid"></table>
			<div id="pagered"></div>

		</div>
	</div>
</div>