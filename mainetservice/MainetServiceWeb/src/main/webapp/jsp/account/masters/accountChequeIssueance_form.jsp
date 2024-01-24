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
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/account/accountChequeIssueance.js"></script>

<script>
	$(document).ready(function(){
		/* var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		} */
		var table = $('.main1').DataTable({
			"oLanguage": { "sSearch": "" } ,
			"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
		    "iDisplayLength" : 5, 
		    "bInfo" : true,
		    "lengthChange": true,
		    "bPaginate": true,
		    "bFilter": true,
		    "ordering":  false,
		    "order": [[ 1, "desc" ]]
		    });
		
		
	});
	
</script>

<c:if test="${tbBankReconciliation.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>

<script>
 $('#sacHeadId${count}').val($('#secondaryId').val());
</script>
<div class="">
	<form:form class="form-horizontal"
		modelAttribute="tbBankReconciliation" method="POST"
		action="AccountChequeIssue.html">

		<form:hidden path="index" id="index" />
		<form:hidden path="" value="${keyTest}" id="keyTest" />
		<form:hidden path="" value="${categoryType}" id="categoryType" />
		<form:hidden path="" value="${categoryTypeDesc}" id="categoryTypeDesc" />

		<div class="mand-label clearfix">
			<span><spring:message code="account.common.mandmsg" /> <i
				class="text-red-1">*</i> <spring:message
					code="account.common.mandmsg1" /></span>
		</div>
		<form:hidden path="hasError" />
		<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
		<form:hidden path="" id="hiddenFinYear"></form:hidden>
		<form:hidden path="" id="cpdBugtypeIdHidden" />
		<div class="warning-div alert alert-danger alert-dismissible hide"
			id="clientSideErrorDivScrutiny"></div>
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<button type="button" class="close" onclick="closeOutErrBox()"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span id="errorId"></span>
		</div>

		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<c:set var="count" value="0" scope="page" />
		<ul id="ulId">
			<li>

				<fieldset id="divId" class="clear"></fieldset> <input type="hidden"
				value="${viewMode}" id="test" />

				<div class="panel-group accordion-toggle" id="ledgerDetailIds">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type="h4" class="panel-title table" id="">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2">Cheque
									Issuance Details : ${categoryTypeDesc} : ${bankTypeDesc} </a>
							</h4>
						</div>
						<div class="table-responsive clear">
							<table class="table table-bordered table-striped main1"
								id="bankrec">
								<thead>
									<tr class="bankRecTable">
										<th>Payment Date</th>
										<th>Payment No.</th>
										<th>Payment Mode</th>
										<!-- <th>Transaction Type</th> -->
										<th>Instrument No.</th>
										<th>Instrument Date</th>
										<th>Amount</th>
										<th>Issuance Date <span class="mand">*</span></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach
										items="${tbBankReconciliation.bankReconciliationDTO}"
										var="funMasterLevel" varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>
										<tr class="chequeTr">
											<form:hidden
												path="bankReconciliationDTO[${count}].transactionMode"
												id="transactionMode${count}" />
											<form:hidden path="bankReconciliationDTO[${count}].serchType"
												id="serchType${count}" />
											<form:hidden
												path="bankReconciliationDTO[${count}].depositSlipId"
												id="depositSlipId${count}" />
											<form:hidden path="bankReconciliationDTO[${count}].id"
												id="id${count}" />
											<form:hidden
												path="bankReconciliationDTO[${count}].previousDate"
												id="previousDate${count}" />
											<form:hidden
												path="bankReconciliationDTO[${count}].chequebookDetid"
												id="chequebookDetid${count}" />

											<td><form:input
													path="bankReconciliationDTO[${count}].transactionDate"
													id="transactionDate${count}" class=" form-control "
													readonly="true" name="text-01" /></td>
											<td><form:input
													path="bankReconciliationDTO[${count}].transactionNo"
													id="transactionNo${count}" class=" form-control "
													readonly="true" name="text-01" /></td>
											<td><form:input
													path="bankReconciliationDTO[${count}].transMode"
													id="transMode${count}" class=" form-control "
													readonly="true" name="text-01" /></td>
											<td><form:input
													path="bankReconciliationDTO[${count}].chequeddno"
													id="transactionNo${count}" class=" form-control "
													readonly="true" name="text-01" /></td>

											<td><form:input
													path="bankReconciliationDTO[${count}].chequedddate"
													id="transactionNo${count}" class=" form-control "
													readonly="true" name="text-01" /></td>

											<td class="text-right"><form:input type="text"
													cssClass="form-control text-right"
													path="bankReconciliationDTO[${count}].amount"
													id="amount${count}" readonly="true" /></td>

											<td><form:input
													path="bankReconciliationDTO[${count}].date"
													id="date${count}"
													cssClass="form-control mandColorClass datepicker"
													onchange="changeValidIssueaneDate(${count})" maxlength="10" /></td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<c:if test="${tbBankReconciliation.balanceAsUlb ne '0.00' }">
										<tr>
											<th colspan="2" style="text-align: left">Balance As Per
												ULB Book</th>
											<th colspan="2"><form:input class="form-control"
													path="balanceAsUlb" id="balanceUlbId" disabled="true"
													style="text-align:right;" /></th>
											<th colspan="2" style="text-align: left">Bank Balance As
												Per Bank Statement</th>
											<th colspan="2"><form:input class="form-control"
													path="bankBalanceAsperStatement" id="statementId"
													disabled="true" style="text-align:right" /></th>
										</tr>
									</c:if>
								</tfoot>
							</table>
						</div>
					</div>
				</div>

			</li>
		</ul>
		<input type="hidden" id="count" value="0" />
		<div class="text-center padding-top-10">

			<input type="button" id="saveBtn" class="btn btn-success btn-submit"
				onclick="saveLeveledData(this)" value="Save"> </input>

			<spring:url var="cancelButtonURL" value="AccountChequeIssue.html" />
			<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
					code="account.bankmaster.back" text="Back" /></a>
		</div>
	</form:form>
</div>




