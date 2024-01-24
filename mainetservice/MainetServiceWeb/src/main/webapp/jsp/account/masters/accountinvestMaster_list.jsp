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
<script src="js/account/accountInvesmantMaster.js"
	type="text/javascript"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="Investment.Summary.Form" text="Investment Summary Form" /></h2>
		</div>
		<div class="widget-content padding">
			<form:form action="investmentMaster.html" name="investmentMaster"
				id="investmentMasterId" class="form-horizontal" onsubmit="return validateSearch()">
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
			
				<div class="mand-label clearfix">
					<span><spring:message code="account.common.mandmsg"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="account.common.mandmsg1" text="is mandatory" /> </span>
				</div>
				
				<h4><spring:message code="Investment.Information" text="Investment Information" /></h4>
				<form:hidden path="accountInvestmentMasterDto.categoryTypeId" id="categoryTypeId" />
			    <form:hidden path="accountInvestmentMasterDto.sliDate" id = "sliDate"/>
			  
				<div class="form-group">
					<label for="text-1" class="col-sm-2 control-label ">
						<spring:message code="accounts.bankfortds.ptbbankname" text="Bank Name" /><span class="mand"></span>
					</label>
						
					<div class="col-sm-4">
					
						<form:select label="select"
							class="form-control chosen-select-no-results" id="bankId"
							path="accountInvestmentMasterDto.bankId">
							
							<form:option value="" selected = "true"><spring:message code="accounts.bankfortds.select.bank.name" text="Select Bank Name" /></form:option>
							<c:forEach items="${command.bankMap}" varStatus="status"
								var="bankMapList">
								<form:option value="${bankMapList.key}" code="${bankMapList.key}">${bankMapList.value}</form:option>
							</c:forEach>
						</form:select> 
				 
						
					</div>
				 <spring:message code="Enter.Investment.Amount"  var="enterinvestmentamount"/>
					<apptags:input labelCode="Investment.Amount"
					cssClass="hasDecimal"
					
						path="accountInvestmentMasterDto.invstAmount"  
						placeholder="${enterinvestmentamount}"
					
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
				</div>
				<div class="form-group">
			<spring:message code="enter.investment.number"  var="enterinvestmentnumber"/>
					<apptags:input labelCode="investment.Number"
						path="accountInvestmentMasterDto.invstNo" 
						placeholder="${enterinvestmentnumber}"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
					<label for="text-1" class="control-label col-sm-2"><spring:message code="account.common.fund" text=" Fund" /></label>
					<div class="col-sm-4">
						<form:select
							class="form-control mandColorClass chosen-select-no-results"
							path="accountInvestmentMasterDto.fundId" id="fundId"
							placeholder="Enter fund">
							<form:option value="" selected="true">
								<spring:message code="account.budgetopenmaster.selectfundcode" text="Select Fund" />
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
						onclick="searchForm('investmentMaster.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i><spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="resetlistForm()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i><spring:message
							code="account.bankmaster.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-success" title="Add"
						onclick="createInvestMentMaster('investmentMaster.html','createForm')">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i><spring:message code="account.bankmaster.add" text="Add" />
					</button>
				</div>
				<table class="table table-bordered margin-bottom-10 myTable">
					<thead>
						<tr>
							<th class="text-center"><spring:message code="investment.Number" text="Investment Number" /></th>
							<th class="text-center"><spring:message code="Investment.Date" text="Investment Date" /></th>
							<th class="text-center"><spring:message code="Investment.Amount" text="Investment Amount" /></th>
							<th class="text-center"><spring:message code="accounts.bankfortds.ptbbankname" text="Bank Name" /></th>
							<th class="text-center"><spring:message code="accounts.master.status" text="Status" /></th>
							<th class="text-center"><spring:message
														code="bill.action" text="Action" /><spring:message
														code="acc.s" text="(s)" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${command.acInvstDtoList}" var="data"
							varStatus="index">
							<tr>
								<form:hidden path="" id="invstNo" value="${data.invstNo}" />
								<form:hidden path="" id="status" value="${data.status}" />
								<form:hidden path="" id="invstAmount" value="${data.invstAmount}" />	
								
								<td class="text-center">${data.invstNo}</td>
								<td class="text-center"><fmt:formatDate
										pattern="dd/MM/yyyy" value="${data.invstDate}" /></td>
								<td class="text-right">${data.invstAmount}</td>
								<td class="text-center">${data.bankName}</td><!-- bankName -->
								
								<c:choose>
								<c:when test = "${data.status eq 'A'}">
										<td class="text-center">Open</td>
								</c:when>
								<c:otherwise>
										<td class="text-center">Closed</td>
								</c:otherwise>
								</c:choose>

								
								
								
								<form:hidden path="" id="status" value="${data.status}" />
								<td class="text-center">
									<button type="button" class="btn btn-blue-2" title="View"
										onclick="viewInvestMentMaster('investmentMaster.html','viewInvestmentMaster',${data.invstId})">
										<i class="fa fa-eye" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn btn-blue-2" title="Edit"
										onclick="editInvestMentMaster('investmentMaster.html','editForm',${data.invstId},'${data.status}')">
										<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
									</button>
									<button type="button" class="btn btn-danger btn-sm"
										title="Receipt"
										onclick="doOpenInvestPageForLoan('AccountReceiptEntry.html','createformFromInvest',${data.invstId},'${data.invstNo}','${data.status}','${data.invstAmount}','${data.invstDate}',${data.fundId},'${data.inFdrNo}','${data.bankName}');">
										<i class="fa fa-file-text-o" aria-hidden="true"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form:form>
		</div>
	</div>
</div>