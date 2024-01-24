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
<script src="js/account/transaction/paymentEntry.js"
	type="text/javascript"></script>
<script>

$(function(){
	$("#paymentDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
	
	 $("#paymentDate").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
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
			<h2>
				<spring:message code="account.bill.payment.entry.form"
					text="Payment Entry Form" />
			</h2>
		<apptags:helpDoc url="PaymentEntry.html" helpDocRefURL="PaymentEntry.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding" id="frmdivId">

			<form:form action="PaymentEntry.html"
				modelAttribute="paymentEntryDto" class="form-horizontal"
				id="PaymentEntryFrm">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="advance.management.master.paymentdate" text="Payment Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="transactionDate" id="paymentDate"
								cssClass="form-control datepicker"  maxlength="10" />
							<label class="input-group-addon" for="paymentDate"><i
								class="fa fa-calendar"></i> </label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="advance.management.master.paymentamount"
							text="Payment Amount" /></label>
					<div class="col-sm-4">
						<form:input path="paymentAmount" cssClass="form-control" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.tenderentrydetails.VendorEntry" text="Vendor Name" /></label>
					<div class="col-sm-4">
						<form:select path="vendorId"
							class="form-control mandColorClass chosen-select-no-results"
							data-rule-required="true">
							<form:option value="0">
								<spring:message code="account.bill.payment.sel.vendorname"
									text="Select Vendor Name" />
							</form:option>
							<c:forEach items="${vendorList}" varStatus="status" var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="account.cheque.dishonour.account.head" text="Account Head" /></label>
					<div class="col-sm-4">
						<form:select path="sacHeadId"
							class="form-control mandColorClass chosen-select-no-results"
							data-rule-required="true">
							<form:option value="0">
								<spring:message
									code="account.budget.code.master.selectaccountheads"
									text="Select Account Head" />
							</form:option>
							<c:forEach items="${expenditureHeadMap}" varStatus="status"
								var="entry">
								<form:option value="${entry.key}" code="${entry.key}">${entry.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.bill.payment.entry.number" text="Payment Entry No" /></label>
					<div class="col-sm-4">
						<form:input path="paymentNo" cssClass="form-control" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="account.fund.bank.acc" text="Bank Account" /></label>
					<div class="col-sm-4">
						<form:select path="bankAcId"
							class="form-control mandColorClass chosen-select-no-results"
							data-rule-required="true">
							<form:option value="0">
								<spring:message code="account.bill.payment.entry.sel.bank.acc"
									text="Select Bank Account" />
							</form:option>
							<c:forEach items="${bankAccountMap}" varStatus="status"
								var="bankItem">
								<form:option value="${bankItem.key}">${bankItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success" id="searchBtn">
						<i class="fa fa-search"></i>&nbsp;
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						onclick="window.location.href='PaymentEntry.html'" id="backBtn">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-blue-2" id="createBtn">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.create" text="Create" />
					</button>
				</div>
				<table id="paymentEntryGrid"></table>
				<div id="paymentEntryGridPager"></div>
				<div class="text-center padding-bottom-10"></div>

			</form:form>
		</div>
	</div>
</div>