<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/directPaymentEntry.js"
	type="text/javascript"></script>
<script>

$(function() {
$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',
	changeMonth: true,
	changeYear: true,
	maxDate: '0',
	
});
});

</script>
<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Direct Payment Entry" />
			</h2>
		</div>
		<div class="widget-content padding" id="directPaymentDiv">
			<form:form action="" modelAttribute="paymentEntryDto"
				class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="form-group">
					<label for="paymentEntryDate" class="col-sm-2 control-label"><spring:message
							code="" text=" Payment Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="paymentEntryDate"
								cssClass="datepicker cal form-control" name="paymentEntryDate"
								id="paymentEntryDate" />
							<label class="input-group-addon" for="paymentEntryDate"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span>
								<input type="hidden" id="trasaction-date-icon30"> </label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Payment Amount" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control text-right"
							name="paymentAmount" id="paymentAmount" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Vendor Name" /></label>
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
							code="" text="Budget Head" /></label>
					<div class="col-sm-4">
						<form:select path="" class="form-control chosen-select-no-results"
							name="" id="budgetCodeId">
							<option value=""><spring:message code="account.select"
									text="Select" /></option>
							<c:forEach items="${expBudgetCodeMap}" varStatus="status"
								var="expItem">
								<form:option value="${expItem.key}" code="${expItem.key}">${expItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Payment Entry No" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" name="paymentNo"
							id="paymentNo" />
					</div>
					<label for="bankId" class="col-sm-2 control-label"><spring:message
							code="" text="Bank Account" /></label>
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
					<button type="button" id="search"
						value="<spring:message code="search.data"/>"
						class="btn btn-success searchData" onclick="searchDirectPayData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="reset" class="btn btn-warning"
						onclick="window.location.href = 'DirectPaymentEntry.html'">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<button type="button" value="Contra Entry"
						class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.create" text="Create" />
					</button>
				</div>
			</form:form>

			<table id="directPayGrid"></table>
			<div id="pagered"></div>

		</div>

	</div>
</div>

