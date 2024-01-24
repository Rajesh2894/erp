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
</script>
<c:if test="${tbAcAdvanceEntry.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>
<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="advance.management.master.headline1" text="Advance Management" />
		</h2>
	</div>
	<div class="widget-content padding">
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcAdvanceEntry" name="frmMaster" method="POST"
			action="AdvanceEntry.html">
			<form:hidden path="" id="secondaryId" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="" id="cpdIdStatusFlagDup" />

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
							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.advancenumber" text="Advance Number" /></label>

							<div class="col-sm-4">
								<form:input cssClass="form-control  text-right"
									id="advanceNumber" path="advanceNumber"></form:input>
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.advancedate" text="Advance Date" /></label>

							<div class="col-sm-4">
								<form:input cssClass="form-control" id="advanceDate"
									path="advanceDate" placeholder='DD/MM/YYYY'></form:input>
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.advancetype" text="Advance Type" /></label>

							<div class="col-sm-4">
								<form:input type="text" path="advanceTypeDesc"
									class="form-control" id="advanceTypeDesc" />
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.advancehead" text="Account Head" /></label>

							<div class="col-sm-4">

								<c:set value="${tbAcAdvanceEntry.pacHeadId}" var="pacHeadId"></c:set>
								<c:forEach items="${advanceHead}" varStatus="status"
									var="pacItem">
									<c:if test="${pacHeadId eq pacItem.key}">
										<form:input type="text" value="${pacItem.value}" path=""
											class="form-control" id="pacHeadId" />
									</c:if>
								</c:forEach>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.vendoremployeename" text="Vendor /Employee Name" /></label>

							<div class="col-sm-4">

								<c:set value="${tbAcAdvanceEntry.vendorId}" var="vendorId"></c:set>
								<c:forEach items="${vendorList}" varStatus="status"
									var="vendorItem">
									<c:if test="${vendorId eq vendorItem.vmVendorid}">
										<form:input type="text" value="${vendorItem.vmVendorname}"
											path="" class="form-control" id="vendorId" />
									</c:if>
								</c:forEach>
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.particularsofadvance" text="Particulars Of Advance" /></label>

							<div class="col-sm-4">
								<form:textarea id="partOfAdvance" path="partOfAdvance"
									class="form-control" maxLength="500" tabindex="-1" />
							</div>



						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.advanceamount" text="Advance Amount" /></label>

							<div class="col-sm-4">
								<form:input cssClass="form-control  text-right"
									onkeypress="return hasAmount(event, this, 13, 2)"
									id="advanceAmount" path="advanceAmount"
									onkeyup="copyContent(this)"></form:input>
							</div>

							<label class="col-sm-2 control-label "><spring:message
									code="advance.management.master.balanceadvance" text="Balance Advance" /></label>

							<div class="col-sm-4">
								<form:input cssClass="form-control  text-right"
									onkeypress="return hasAmount(event, this, 13, 2)"
									id="balanceAmount" path="balanceAmount"
									onchange="minMaxBalanceAmount(this)"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="advance.management.master.requisitionnumber" text="" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control  text-right"
									onkeypress="return hasAmount(event, this, 13, 2)"
									id="paymentOrderNo" path="paymentOrderNo"></form:input>
							</div>

							<label class="col-sm-2 control-label"><spring:message
									code="advance.management.master.requisitiondate" text="" /></label>

							<div class="col-sm-4">
								<form:input cssClass="form-control" id="paymentOrderDate"
									path="paymentOrderDateDup" placeholder='DD/MM/YYYY'
									readonly="${viewMode ne 'true'}"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.paymentnumber" text="" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control  text-right"
									onkeypress="return hasAmount(event, this, 13, 2)"
									id="paymentNumber" path="paymentNumber"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="advance.management.master.paymentdate" text="" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control" id="paymentDate"
									path="paymentDateDup" placeholder='DD/MM/YYYY'
									readonly="${viewMode ne 'true'}"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label for="Department"
								class="col-sm-2 control-label required-control"><spring:message
									code="budget.reappropriation.master.departmenttype" text="Department" /></label>
							<div class="col-sm-4">
								<form:select path="deptId"
									class="form-control mandColorClass chosen-select-no-results"
									name="department" id="departmentId">
									<option value=""><spring:message
									code="account.common.select" /></option>
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
						value="Save"> </input>
					<input type="button" id="Reset" class="btn btn-warning createData"
						value="Reset"></input>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn"
						class="btn btn-success btn-submit" onclick="saveLeveledData(this)"
						value="Save"> </input>
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

