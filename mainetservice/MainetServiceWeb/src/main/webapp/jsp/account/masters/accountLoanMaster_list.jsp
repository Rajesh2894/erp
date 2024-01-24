<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>

<script src="js/mainet/validation.js"></script>
<script src="js/account/accountLoanMaster.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
	<script>$("select").chosen();</script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2> <spring:message code="Loan.Master.Summary" text="Loan Master Summary" /></h2>
		</div>
		<div class="widget-content padding">

			<form:form action="loanmaster.html" method="get" class="form-horizontal" onsubmit="return validateSearch()">
				<h4> <spring:message code="Loan.Information" text="Loan Information" /></h4>
				<form:hidden path="accountLoanMasterDto.categoryTypeId"
					id="categoryTypeId" />
					<form:hidden path="accountLoanMasterDto.sliDate" id = "sliDate"/>
					
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- ----------------- this div below is used to display the error message on the top of the page--------------------------->
				<div
					class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
						<span id="errorId"></span>
				</div>
				<!------------------------------------------------------------  -->
				<!--  Loan Information form -->
				<!------------------------------------------------------------  -->


				<div class="form-group">
					<label for="text-1" class="col-sm-2 control-label "> <spring:message code="rChallan.deptName" text="Department Name" />
						</label>

					<div class="col-sm-4">
						<form:select path="accountLoanMasterDto.lnDeptname"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select" id="deptId">

							<form:option value="" selected="true"> <spring:message code="Select.Department.Name" text="Select Department Name" /></form:option>
							<c:forEach items="${command.depList}" var="dept">
								<form:option value="${dept.key}" code="${dept.key}">${dept.value}</form:option>
							</c:forEach>
						</form:select>

					</div>
					<!-- is the loan description being mapped ?? -->
					<label for="text-1" class="col-sm-2 control-label "> <spring:message code="Loan.Purpose" text="Loan Purpose" />
						</label>
					<div class ="col-sm-4" >
					<!-- 	<form:textarea rows="1" cols="50"
							class="form-control padding-left-10"
							placeholder="Enter Loan Description" id="loanDesc"
							path="accountLoanMasterDto.lnPurpose" /> -->
					
					<form:select path="accountLoanMasterDto.lnPurpose"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select" id="lnPurpose">

							<form:option value="" selected="true"> <spring:message code="Select.Loan.Purpose" text="Select Loan Purpose" />
						</form:option>
							<c:forEach items="${command.accountLoanMasterDtoList}" var="data">
								<form:option value="${data.lnPurpose}">${data.lnPurpose}</form:option>
							</c:forEach>
						</form:select>
					
					</div>
				</div>
				
				<div class="form-group">

					<label for="loanCode" class="col-sm-2 control-label"><spring:message
							code="Loan.Code" text="Loan Code" /></label>
					<div class="col-sm-4">
						<%-- <form:input  class="form-control padding-left-10"
							placeholder="Enter Loan Code" id="loanCode" path=""/> --%>
							
						<form:select id="loanCode" path=""
							class="form-control mandColorClass chosen-select-no-results">
							
							<form:option value="" selected="true">
								<spring:message code="acc.select" text="Select" />
							</form:option>
							
					 <c:forEach items="${command.id}" var="data">
								<form:option value="${data}">${data}</form:option>
					</c:forEach>  
						</form:select>
						
							
					</div>
				
				</div>

				<!------------------------------------------------------------  -->
				<!--  Loan Information form ends -->
				<!------------------------------------------------------------  -->




				<!-- Button controls  -->

				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						onclick="searchForm('loanmaster.html','searchForm');">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i> <spring:message code="accounts.stop.payment.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="resetlistForm()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i> <spring:message code="accounts.stop.payment.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addLoanMaster('loanmaster.html','craeteForm');"
						title="Add">
						<strong class="fa fa-plus-circle"></strong> <spring:message code="accounts.master.add" text="Add" /> 
					</button>
				</div>




				<table class="table table-bordered margin-bottom-10 loanMaster">
					<thead>
						<tr>
							<th class="text-center"> <spring:message code="Loan.Number" text="Loan Number" /></th>
							<th class="text-center"> <spring:message code="Sanction.Date" text="Sanction Date" /></th>
							<th class="text-center"> <spring:message code="Purpose" text="Purpose" /></th>
							<th class="text-center"> <spring:message code="Loan.Amount" text="Loan Amount" /></th>
							<th class="text-center"> <spring:message code="budget.reappropriation.master.balanceamount" text="Balance Amount" /></th>
							<th class="text-center"> <spring:message code="Action.s" text="Action(s)" /></th>
						</tr>
					</thead>
					<tbody>
					
						<c:forEach items="${command.accountLoanMasterDtoList}" var="data"
							varStatus="index">
							<tr>
								<form:hidden path="" id="loanremarks" value="${data.lnNo}" />
								<form:hidden path="" id="loanPurpose" value="${data.lnPurpose}" />
								<form:hidden path="" id="santionamount" 
									value="${data.santionAmount}" />
								<td class="text-center">${data.lnNo}</td>
								<td class="text-center"><fmt:formatDate 
										pattern="dd/MM/yyyy" value="${data.sanctionDate}" /></td>
								<td>${data.lnPurpose}</td>
								<td class="text-right">${data.santionAmount}</td>
								<td class="text-right">${data.santionAmount}</td>
										<form:hidden path = "" id ="sanctionDate"  value="${data.sanctionDate}" />
								<!-- To display the balance amount uncomment the below code -->
								<td class="text-center">
									<button type="button" class="btn btn-blue-2" title="View"
										onclick="viewLoanMaster('loanmaster.html','viewForm',${data.loanId});">
										<i class="fa fa-eye" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn btn-blue-2" title="Edit"
										onclick="editLoanMaster('loanmaster.html','editForm',${data.loanId});">
										<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn btn-danger btn-sm"
										title="Receipt"
										onclick="doOpenReceiptPageForLoan('AccountReceiptEntry.html','createformFromLoan',${data.loanId},'${data.lnNo}',
										'${data.lnPurpose}',${data.santionAmount},'${data.sanctionDate}');">
										<i class="fa fa-file-text-o" aria-hidden="true"></i>
									</button>
									
									 <!-- commented against #132555 -->
									<%-- <button type="button" class="btn btn-success btn-sm"
										title="Bill"
										onclick="doOpenBillPageForLoan('AccountBillEntry.html','refund',${command.accountLoanMasterDto.categoryTypeId},${data.loanId});">
										<i class="fa fa-file-text-o" aria-hidden="true"></i>
									</button> --%>


								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form:form>
		</div>
	</div>
</div>
