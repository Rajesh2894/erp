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
<script src="js/account/accountGrantMaster.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message
								code="Grant.Master.Summary" text="Grant Master Summary" /></h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="get" class="form-horizontal" onsubmit = "return validateSearch()">
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

				<!-- ---------------------------------------------------------------------------------------------------------------------- -->
				<h4><spring:message
								code="Grant.Information" text="Grant Information" /></h4>
				<form:hidden path="accountGrantMasterDto.categoryTypeId" id="categoryTypeId" />
				<form:hidden path="accountGrantMasterDto.sliDate" id = "sliDate"/>
				<div class="form-group">
					<label for="text-1" class="control-label col-sm-2 required-control">
						 <spring:message code="Grant.Type" text="Grant Type" /></label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton
								path="accountGrantMasterDto.grtType" value="R" id="rev"
								checked="true" /> <spring:message code="acc.Revenue" text="Revenue" />
						</label> <label class="radio-inline "> <form:radiobutton id="cap"
								path="accountGrantMasterDto.grtType" value="C" /> <spring:message
								code="acc.Capital" text="Capital" />
						</label>

					</div>
					<spring:message code="acc.Grant.Name" var="EnterGrantName" />
					<label for="text-1" class="col-sm-2 control-label ">
						 <spring:message
								code="grant.Name" text="Grant Name " /></label>
					<div class="col-sm-4">
						<form:input class="form-control" id="grtName"
							path="accountGrantMasterDto.grtName"
							placeholder="${EnterGrantName}" />
					</div>
				</div>
				<spring:message code="acc.EnterGrantNumber" var="EnterGrantNumber" />
				<div class="form-group">
					<label for="text-1" class="col-sm-2 control-label ">
						 <spring:message
								code="Grant.Number" text="Grant Number " /> </label>
					<div class="col-sm-4">
						<form:input class="form-control" id="grtNo"
							path="accountGrantMasterDto.grtNo" placeholder="${EnterGrantNumber}" />
					</div>
					<label for="text-1" class="col-sm-2 control-label "><spring:message
								code="account.common.fund" text="Fund " /></label>
					<div class="col-sm-4">
						<form:select
							class="form-control mandColorClass chosen-select-no-results"
							path="accountGrantMasterDto.fundId" id="fundId"
							placeholder="Enter fund">
							<form:option value="" selected="true">
								<spring:message code="account.common.select" text="Select" />
							</form:option>
							<c:forEach items="${command.fundList}" var="fundData">
								<form:option code="${fundData.fundCode}"
									value="${fundData.fundId}">${fundData.fundCompositecode} ${fundData.fundDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						onclick="searchForm('grantMaster.html','searchForm');">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i><spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="resetGrant()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i><spring:message
							code="account.bankmaster.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-success add"
						onclick="createGrantMaster('grantMaster.html','createForm');"
						title="Add">
						<strong class="fa fa-plus-circle"></strong> <spring:message code="account.bankmaster.add" text="Add" />
					</button>
				</div>
				<table class="table table-bordered margin-bottom-10 grantMaster">
					<thead>
						<tr>
							<th class="text-center"><spring:message
								code="Grant.Number" text="Grant Number " /></th>
							<th class="text-center"> <spring:message
								code="grant.Name" text="Grant Name " /></th>
							<th class="text-center"><spring:message
								code="Sanction.Amount" text="Sanction Amount" /></th>
							<th class="text-center"><spring:message
							code="account.deposit.BalanceAmmount" text="Balance Amount"></spring:message></th>
							<th class="text-center" width="14%"><spring:message code="Action.s" text="Action(s)" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${command.accountGrantMasterDtoList}" var="data"
							varStatus="index">
							<tr>
								<form:hidden path="accountGrantMasterDto.grtNature" id="grtNature" value="${data.grtNature}" />
								<form:hidden path="accountGrantMasterDto.grtNo" id="grantNo" value="${data.grtNo}" />
								<form:hidden path="accountGrantMasterDto.santionAmt" id="sanctionAmt" value="${data.santionAmt}" />
								<form:hidden path="accountGrantMasterDto.sanctionDate" id="sanctionDate" value="${data.sanctionDate}" />
								<td class="text-left">${data.grtNo}</td>
								<td class="text-left">${data.grtName}</td>
								<td class="text-right">${data.santionAmt}</td>
								<td class="text-right">${data.receivedAmt}</td>
								<td class="text-center" width="250px">
									<button type="button" class="btn btn-blue-2" title="View"
										onclick="viewGrantMaster('grantMaster.html','viewForm',${data.grntId});">
										<i class="fa fa-eye" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn btn-blue-2" title="Edit"
										onclick="editGrantMaster('grantMaster.html','editForm',${data.grntId});">
										<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn btn-danger btn-sm"
										title="Receipt"
										onclick="doOpenReceiptPageForGrant('${data.grntId}','${data.grtNature}','${data.grtNo}','${data.santionAmt}','${data.sanctionDate}','${data.grtDate}');">
										<i class="fa fa-file-text-o" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn btn-success btn-sm"
										title="reFund"
										onclick="doOpenRefundPageForGrantRefund(${data.grntId},'GFD', 'R');">
										<i class="fa fa-file-text text-white" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn btn-warning"
										title="Utilization"
										onclick="doOpenRefundPageForGrantUtilization(${data.grntId},'GRT','U');">
										<i class="fa fa-file-text text-white" aria-hidden="true"></i>
									</button>
									<!-- Defect #85033 -->
									<!-- <button type="button" class="btn btn-primary"
										title="Print Utilization Certificate">
										<i class="fa fa-print" aria-hidden="true"></i>
									</button> -->

								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form:form>
		</div>
	</div>
</div>