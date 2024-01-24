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
<script src="js/account/reversal.js"></script>
<script>

	var ptbId = '';
	$(function() {
	$('#reversalTable').hide();
		
	});

	function returnViewUrl(cellValue, options, rowdata, action) {

		return "<a href='#'  return false; class='viewReceiptClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
	}
	
	function returnEditUrl(cellValue, options, rowdata, action) {
		rmRcptid = rowdata.rmRcptid;
	 	return "<a href='#'  return false; class='editReceiptClass'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
	}
	
	function addLink(cellvalue, options, rowdata) {
	    return "<a class='btn btn-blue-3 btn-sm viewReceiptClass' title='View'value='"+rowdata.fundId+"' id='"+rowdata.fundId+"' ><i class='fa fa-building-o'></i></a> " +
	    		"<a class='btn btn-warning btn-sm editReceiptClass' title='Edit'value='"+rowdata.fundId+"' id='"+rowdata.fundId+"' ><i class='fa fa-pencil'></i></a> ";
	 }
	
	$(function() {
		$("#transDate").keyup(function(e){
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
				<spring:message code="accounts.receipt.transaction.reversal.form"
					text="Transaction Reversal Form" />
			</h2>
			<apptags:helpDoc url="AccountVoucherReversal.html" helpDocRefURL="AccountVoucherReversal.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding" id="fr	mdivId">

			<form:form action="AccountVoucherReversal.html"
				modelAttribute="reversalDTO" class="form-horizontal"
				id="frmReversalSearch">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="form-group">
					
					<label class="col-sm-2 control-label required-control"><spring:message
									code="budget.projected.expenditure.master.department" text="" /></label>
					<div class="col-sm-4">
						<form:select path="dpDeptid"
							class="form-control mandColorClass chosen-select-no-results"
							id="dpDeptid" disabled="${viewMode}">
							<form:option value="">
							<spring:message code="" text="Select Department" />
							</form:option>
							<c:forEach items="${deptMap}" varStatus="status" var="deptMap">
								<form:option value="${deptMap.key}" code="${deptMap.key}">${deptMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label"><spring:message
							code="accounts.receipt.transaction.type" text="Transaction Type" /><span
						class="mand">*</span></label>
					<div class="col-sm-4">
						<form:hidden path="" id="transactionTypeIdHidden" />
						<form:select path="transactionTypeId"
							class="form-control mandColorClass" data-rule-required="true">
							<form:option value="0"><spring:message
							code="account.common.select" text="Select" /></form:option>
							<c:forEach items="${transactionTypeLookUps}" var="lookUp">
								<c:choose>
									<c:when test="${lookUp.lookUpId eq transactionTypeId}">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}" selected="selected">${lookUp.descLangFirst}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
					
				</div>
				<div class="form-group">
				
					<label class="col-sm-2 control-label"><spring:message
							code="account.cheque.cash.transaction" text="Transaction No." /><span
						class="mand">*</span></label>
					<div class="col-sm-4">
						<form:input path="transactionNo" id="transactionNo" class="form-control mandColorClass" />
					</div>
					
					<label class="col-sm-2 control-label"><spring:message
							code="account.budgetreappropriationmaster.transactiondate"
							text="Transaction Date" /><span
						class="mand">*</span></label>

					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="transactionDate" id="transDate"
								cssClass="form-control mandColorClass datepicker" maxlength="10"/>
							<label class="input-group-addon" for="transDate"><i
								class="fa fa-calendar" ></i> </label>
						</div>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" id="search" class="btn btn-blue-2">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search"/>
					</button>
					<spring:url var="cancelButtonURL"
						value="AccountVoucherReversal.html" />
					<a role="button" class="btn btn-warning"
						onclick="window.location.href='AccountVoucherReversal.html'"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
				</div>
				<form:hidden path="checkedIds" id="checkedId" />
				<table id="voucherReversalGrid"></table>
				<div id="voucherReversalPager"></div>
				<div class="text-center padding-bottom-10"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.voucher.reversal.approval.order.no"
							text="Approval Order No" /><span class="mand">*</span></label>
					<div class="col-sm-4">
						<form:input path="approvalOrderNo"
							class="form-control mandColorClass" maxlength="20" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="account.voucher.reversal.approval.auth"
							text="Approval Authority" /><span class="mand">*</span></label>
					<div class="col-sm-4">
						<form:select path="approvedBy" class="form-control mandColorClass chosen-select-no-results">
							<form:option value="0">
								<spring:message code="account.common.select" text="Select" />
							</form:option>
							<c:forEach items="${approvedBy}" var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.voucher.reversal.reason" text="Reversal Reason" /><span
						class="mand">*</span></label>
					<div class="col-sm-10">
						<form:textarea path="narration"
							class="form-control mandColorClass required-control" />
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<form:hidden path="" value="${actionURL}" id="actionURL" />
					<button type="button" class="btn btn-success btn-submit" id="saveBtn">
						<spring:message code="accounts.receipt.reversal.reverse"
							text="Reverse" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AdminHome.html'" id="backBtn">
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>


			</form:form>
		</div>
	</div>
</div>