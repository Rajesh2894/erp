<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/account/advanceEntry.js" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<style>
.tooltip-inner {
	text-align: left;
}
</style>
<script>
	$(document).ready(function() {
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});

	$("#advanceDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#paymentOrderDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	$("#paymentDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
</script>
<c:if test="${tbAcAdvanceEntry.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>
<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="advance.management.master.title" text="" />
		</h2>
		<apptags:helpDoc url="AdvanceEntry.html"
			helpDocRefURL="AdvanceEntry.html"></apptags:helpDoc>
	</div>
	<div class="widget-content padding">
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcAdvanceEntry" name="frmMaster" method="POST"
			action="AdvanceEntry.html">
			<form:hidden path="" id="secondaryId" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="" id="cpdIdStatusFlagDup" />
			<form:hidden path="liveModeDate" id="liveModeDate" />
			<form:hidden path="" value="${MODE_DATA}" id="formMode_Id" />
			<form:hidden path="successFlag" id="successFlag" />

			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" text="Field with"/> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" text="is mandatory"/></span>
			</div>
			<form:hidden path="hasError" />
			<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="errorDivScrutiny">
				<button type="button" class="close" aria-label="Close"
					onclick="closeErrBox()">
					<span aria-hidden="true">&times;</span>
				</button>
				<ul>
					<li><form:errors path="*" /></li>
				</ul>
			</div>

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<div class="error-div alert alert-danger alert-dismissible "
				id="errorDivIdI" style="display: none;">
				<span id="errorIdI"></span>
			</div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />

			<c:set var="count" value="0" scope="page" />

			<ul id="ulId">
				<li>
					<fieldset id="divId" class="clear">

						<div class="form-group">
							<c:if test="${MODE_DATA == 'EDIT'}">
								<label class="col-sm-2 control-label "><spring:message
										code="advance.management.master.advancenumber" text="Advance Number" /></label>

								<div class="col-sm-4">
									<form:input cssClass="form-control  text-right"
										id="advanceNumber" path="advanceNumber"
										readonly="${viewMode ne 'true'}"></form:input>
								</div>
							</c:if>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.advancedate" text="Advance Date" /></label>

							<div class="col-sm-4">
							<div class="input-group">
							<spring:message code="account.master.date" text="DD/MM/YYYY" var="enterprop" />
								<form:input cssClass="form-control mandColorClass datepiker"
									id="advanceDate" path="advanceDate" placeholder='${enterprop}'
									onchange="changeValidLiveAdvanceDate(this)"
									data-rule-required="true" maxLength="10" autofocus="true"></form:input>
									<label class="input-group-addon" for="advanceDate"><i
									class="fa fa-calendar"></i></label>
								</div>	
							</div>

						</div>


						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.advancetype" text="Advance Type" /></label>

							<div class="col-sm-4">
								<form:select path="advanceTypeId"
									class="form-control mandColorClass" id="advanceType"
									disabled="${viewMode}"
									onchange="getBudgetHeadOnAdvanceType(this)"
									data-rule-required="true">
									<form:option value="">
										<spring:message code="advance.management.master.select"
											text="Select" />
									</form:option>
									<c:forEach items="${AdvanceType}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.advancehead" text="Advance Head" /></label>

							<div class="col-sm-4">
								<form:select path="pacHeadId"
									class="form-control mandColorClass chosen-select-no-results"
									id="pacHeadId" disabled="${viewMode}" data-rule-required="true">
									<form:option value="">
										<spring:message code="advance.management.master.select"
											text="Select" />
									</form:option>
									<c:forEach items="${advanceHead}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.key}"
											value="${levelChild.key}">${levelChild.value}</form:option>
									</c:forEach>
								</form:select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.vendoremployeename" text="Vendor /Employee Name " /></label>

							<div class="col-sm-4">
								<c:set var="baseLookupCode" value="REX" />
								<form:select path="vendorId"
									class="form-control mandColorClass chosen-select-no-results"
									id="vendorName" disabled="${viewMode}"
									data-rule-required="true">
									<form:option value="">
										<spring:message code="advance.management.master.select"
											text="Select" />
									</form:option>
									<c:forEach items="${vendorList}" var="vendorData">
										<form:option value="${vendorData.vmVendorid}">${vendorData.vmVendorname}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.particularsofadvance"
									text="Particulars Of Advance" /></label>

							<div class="col-sm-4">
								<form:textarea id="partOfAdvance" path="partOfAdvance"
									class="form-control mandColorClass" maxLength="500"
									data-rule-required="true" />
							</div>
						</div>
						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.advanceamount" text="Advance Amount " /></label>

							<div class="col-sm-4">
								<form:input
									cssClass="form-control mandColorClass text-right amount"
									onkeypress="return hasAmount(event, this, 13, 2)"
									id="advanceAmount" path="advanceAmount"
									onkeyup="copyContent(this)"
									onchange="getAmountFormatAdvance(this)"
									data-rule-required="true"></form:input>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.balanceadvance" text="Balance Advance" /></label>

							<div class="col-sm-4">
								<form:input
									cssClass="form-control mandColorClass text-right amount"
									onkeypress="return hasAmount(event, this, 13, 2)"
									id="balanceAmount" path="balanceAmount"
									onchange="minMaxBalanceAmount(this)" data-rule-required="true"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.requisitionnumber" text="Payment Order Number" /></label>
							<div class="col-sm-4">
								<form:input
									cssClass="form-control mandColorClass text-right amount"
									onkeypress="return hasAmount(event, this, 13, 2)"
									id="paymentOrderNo" path="paymentOrderNo"></form:input>

							</div>
							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.requisitiondate" text="Payment Order Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
								<spring:message code="account.master.date" text="DD/MM/YYYY" var="enterprop" />
								<form:input cssClass="form-control mandColorClass datepiker"
									id="paymentOrderDate" path="paymentOrderDateDup"
									placeholder='${enterprop}'
									onchange="changeValidLiveRequisitionDate(this)" maxLength="10"></form:input>
									<label class="input-group-addon" for="paymentOrderDate"><i
									class="fa fa-calendar"></i></label>
								</div>	
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.paymentnumber" text="Payment Number" /></label>
							<div class="col-sm-4">
								<form:input
									cssClass="form-control mandColorClass text-right amount"
									onkeypress="return hasAmount(event, this, 13, 2)"
									id="paymentNumber" path="paymentNumber" data-rule-required="true"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.paymentdate" text="" /></label>
							<div class="col-sm-4">
							   <!-- datepiker  removed this cssClass against defect No 79497-->
							   <div class="input-group">
							   <spring:message code="account.master.date" text="DD/MM/YYYY" var="enterprop" />
								<form:input cssClass="form-control mandColorClass"
									id="paymentDate" path="paymentDateDup" placeholder='${enterprop}'
									onchange="changeValidLivePaymentDate(this)"
									data-rule-required="true" maxlength="10"></form:input>
									<label class="input-group-addon" for="paymentDate"><i
									class="fa fa-calendar"></i></label>
								</div>	
							</div>

						</div>

						<div class="form-group">
							<label for="Department"
								class="col-sm-2 control-label required-control"><spring:message
									code="account.budget.code.master.departmenttype" text="Department" /></label>
							<div class="col-sm-4">
								<form:select path="deptId"
									class="form-control mandColorClass chosen-select-no-results"
									name="department" id="departmentId" data-rule-required="true">
									<option value=""><spring:message
									code="account.common.select" text="Select"/></option>
									<c:forEach items="${departmentList}" varStatus="status"
										var="depart">
										<form:option value="${depart.lookUpId}"
											code="${depart.lookUpCode}">${depart.defaultVal}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<form:hidden path="prAdvEntryId" />




					</fieldset>
				</li>
			</ul>

			<INPUT type="hidden" id="count" value="0" />
			<INPUT type="hidden" id="countFinalCode" value="0" />

			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'create'}">
					<input type="button" id="saveBtn"
						class="btn btn-success btn-submit" onclick="saveLeveledData(this)"
						value="<spring:message
						code="accounts.receipt.save" text="Save" />"> </input>
					<input type="button" id="Reset" class="btn btn-warning createData"
						value="<spring:message
						code="account.bankmaster.reset" text="Reset" />"></input>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn"
						class="btn btn-success btn-submit" onclick="saveLeveledData(this)"
						value="<spring:message
						code="accounts.receipt.save" text="Save" />"> </input>
				</c:if>
				<spring:url var="cancelButtonURL" value="AdvanceEntry.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" text="Back" /></a>
			</div>

		</form:form>
	</div>
</div>
<c:if test="${tbAcAdvanceEntry.hasError =='true'}">
	</div>
</c:if>

<script>
	chosen();
</script>

