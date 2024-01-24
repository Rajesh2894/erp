<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/account/chequeOrCashDeposite.js"></script>

<script>
$(document).ready(function() {

	var response = findSLIDateDate();
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
	 $("#fromDate").datepicker({
		    dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,	
			minDate: disableBeforeDate,
			maxDate: today
		});
	 
	 $("#fromDate").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    });
	 $("#fromDate").datepicker('setDate', new Date());  
	  $("#toDate").datepicker({
		    dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,	
			minDate: disableBeforeDate,
			maxDate: today
		});
	  $("#toDate").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    }); 
	 $("#toDate").datepicker('setDate', new Date()); 
});
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.cheque.dishonour.depositslip"
					text="Deposit Slip" />
			</h2>
		<apptags:helpDoc url="AccountDepositSlip.html" helpDocRefURL="AccountDepositSlip.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding">
			<form:form method="post" action="" class="form-horizontal"
				modelAttribute="accountChequeOrCashDepositeBean">

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 "><spring:message
							code="account.fromDate" text="From Date" /></label>
					<div class="col-sm-4">
						 <div class="input-group">
							<form:input path="" cssClass=" form-control" id="fromDate" maxLength="10" />
							<label for="fromDate" class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span><input
								type="hidden" id="fromDate"></label>
						</div> 
					</div>


					<label class="control-label col-sm-2"><spring:message
							code="account.todate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="toDate" cssClass="form-control"  maxLength="10" />
							<label for="toDate" class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span><input
								type="hidden" id="toDate"></label>
						</div>
					</div>

				</div>


				<div class="form-group">
					<label for="radio-group-1477553805187"
						class="col-sm-2 control-label"><spring:message
							code="account.cheque.cash.payment.mode" text="Payment Mode" /> </label>


					<div class="col-sm-4">
						<form:select class="form-control" id="depositType" onchange=""
							path="depositeType">
							<option value=""><spring:message code="account.select"
									text="Select" /></option>
							<c:forEach items="${permanentPayList}" var="deptData">
								<option value="${deptData.lookUpCode}"
									code="${deptData.lookUpId}">${deptData.lookUpDesc}</option>
							</c:forEach>
						</form:select>

					</div>

					<label class="control-label col-sm-2"><spring:message
							code="deposite.slip.number" text="Slip Number" /> </label>
					<div class="col-sm-4">

						<form:input path="" cssClass="form-control" id="slipNumber" />


					</div>


				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="account.bank.accounts" text="Bank Accounts :" /></label>

					<div class="col-sm-4">
						<form:select id="account" path=""
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="account.cheque.cash.acc.select"
									text="Select Account" />
							</form:option>
							<c:forEach items="${banklist}" var="accountData">
								<form:option value="${accountData.baAccountId}"
									code="${accountData.fundId}">${accountData.bankName} - ${accountData.baAccountNo} - ${accountData.baAccountName}</form:option>
							</c:forEach>
						</form:select>
					</div>


				</div>




				<div class="text-center padding-bottom-10">

					<a href="javascript:void(0);"
						onclick="searchSavedReceiptDetails(this)" class="btn btn-success"
						id="btnsearch"><i class="fa fa-search"></i>&nbsp;<spring:message
							code="account.bankmaster.search" text="Search" /></a>
					<button type="reset" class="btn btn-warning"
						onclick="resetGridForm()">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<a href="javascript:void(0);" onclick="showForm(this)"
						class="btn btn-blue-2" id="btnsearch"><i
						class="fa fa-plus-circle"></i> <spring:message code="account.bankmaster.add"
							text="Add" /> </a>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>

			</form:form>
		</div>
	</div>
</div>
