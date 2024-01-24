<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<style>
.popUp {
	width: 350px;
	position: absolute;
	z-index: 111;
	display: block;
	top: 275px;
	left: 400px;
	border: 5px solid #ddd;
	border-radius: 5px;
	display: none;
}

.popUp table th {
	padding: 3px !important;
	font-size: 12px;
	background: none !important;
}

.popUp table td {
	padding: 3px !important;
	font-size: 12px;
}
</style>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/accountBillEntry.js"
	type="text/javascript"></script>
<script>
$("#bmEntrydate").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: '0',
		onSelect : function(date){
				getExpenditureDetails2(date);
			}
	});
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: '0',
		
	});
	var mode = $("#editMode").val();
	if(mode =="edit"){
	
	$('.expenditureClass').each(function(i) {
		$('#viewExpDet'+i).prop("disabled", false);
	});
		
	}

</script>

<c:if test="${accountBillEntryBean.hasError eq 'false'}">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div id="content">
</c:if>
<c:url value="${mode}" var="form_mode" />

<c:if test="${mode eq 'update'}">
	<input type=hidden value="edit" id="editMode" />
</c:if>


<div class="form-div">
	<div class="widget" id="widget">
		<div class="widget-header">
			<c:if test="${accountBillEntryBean.authorizationMode eq Auth}">
				<h2>
					<spring:message code="account.bill.invoice" text="Invoice /" />
					<strong> <spring:message code="account.bill.billentry"
							text="Bill Entry" /></strong>
				</h2>
		<apptags:helpDoc url="AccountBillEntry.html" helpDocRefURL="AccountBillEntry.html"></apptags:helpDoc>		
			</c:if>
			<c:if test="${accountBillEntryBean.authorizationMode ne Auth}">
				<h2>
					<spring:message code="account.bill.invoice" text="Invoice /" />
					<strong> <spring:message code="account.bill.billauth"
							text="Bill Authorization" /></strong>
				</h2>
		<apptags:helpDoc url="AccountBillAuthorization.html" helpDocRefURL="AccountBillAuthorization.html"></apptags:helpDoc>		
			</c:if>
		</div>
		<div class="widget-content padding">
			<form:form action="AccountBillEntry.html" method="POST"
				class="form-horizontal" modelAttribute="accountBillEntryBean"
				id="frmAccountBillEntryId">
				
				<form:hidden path="successfulFlag" id="successfulFlag" />
				<form:hidden path="billTypeCode" id="billTypeCode"/>
				<form:hidden path="paymentId" id="paymentId"/>
				
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
				
					<label for="bmId" class="col-sm-2 control-label"><spring:message
							code="bill.no" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="billNo" class="form-control"
							name="bmBillno" enable-roles="true" role="1" id="bmBillno"
							disabled="${viewMode}" />
					</div>
					<label for="bmBilltypeCpdId" class="col-sm-2 control-label"><spring:message
							code="account.bill.entrydate" text="Bill Entry Date:" /></label>
					<div class="col-sm-4">
						<form:input path="billEntryDate" class="form-control"
							name="transactionDate" id="transactionDateId"
							disabled="${viewMode}" />
					</div>
				</div>
				<div class="form-group">
					<label for="bmBilltypeCpdId" class="col-sm-2 control-label"><spring:message
							code="bill.type" /></label>
					<div class="col-sm-4">

						<form:input path="billTypeDesc" class="form-control"
							name="billTypeDesc" id="billTypeDesc" disabled="${viewMode}" />
					</div>
					<label for="authorizationDateId"
								class="col-sm-2 control-label"><spring:message
									code="account.journal.voucher.auth.date"
									text="Authorization Date" /></label>
					<div class="col-sm-4">
							<form:input path="authorizationDate" id="authorizationDateId"
										cssClass="form-control" maxlength="10"  disabled="${viewMode}" />
					</div>
				</div>
				<div class="form-group">
					<label for="vmVendorname" class="col-sm-2 control-label"><spring:message
							code="accounts.vendormaster.vendorName" /></label>
					<div class="col-sm-4">
						<form:hidden path="vendorDesc" id="vmVendorDesc" />


						<form:input path="vendorDesc" value="" class="form-control"
							name="billTypeDesc" id="billTypeDesc" disabled="${viewMode}" />
					</div>
					<label for="bmInvoicevalue" class="col-sm-2 control-label"><spring:message
							code="bill.invoice.amount" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="invoiceValueStr"
							onkeypress="return hasAmount(event, this, 13, 2)"
							class="form-control text-right" name="bmInvoicevalue"
							id="bmInvoicevalue" disabled="${viewMode}" />
					</div>

				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div id="departmentPanel" class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#dept">Department
									Information</a>
							</h4>
						</div>
						<div id="dept" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="table-responsive" id="departmentInfoTableDiv">

									<table id="departmentInfoTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th scope="col" width="8%"><spring:message code=""
														text="Type" /></th>
												<th scope="col" width="15%"><spring:message code=""
														text="Number" /></th>
												<th scope="col" width="15%"><spring:message code=""
														text="Issue Date" /></th>
											</tr>
											<tr>
												<td><label for="bmInvoicenumber"><spring:message
															code="bill.invoice.no" /></label></td>
												<td><form:input type="text" path="invoiceNumber"
														class="form-control" name="bmInvoicenumber"
														id="bmInvoicenumber" disabled="${viewMode}" /></td>
												<td><div class="input-group">
														<form:input path="invoiceDate"
															class="datepicker form-control" name="bmInvoicedate"
															id="bmInvoicedate" disabled="${viewMode}" />
														<label class="input-group-addon" for="invoicedate"><i
															class="fa fa-calendar"></i> <input type="hidden"
															id="invoicedate"> </label>
													</div></td>
											</tr>
											<tr>
												<td><label for="bmWPOrderNumber"><spring:message
															code="bill.order.number" /></label></td>
												<td><form:input type="text"
														path="workOrPurchaseOrderNumber" class="form-control"
														name="bmWPOrderNumber" id="bmWPOrderNumber"
														disabled="${viewMode}" /></td>
												<td><div class="input-group">
														<form:input path="workOrPurchaseOrderDate"
															class="datepicker form-control" name="date-invoice"
															id="bmWPOrderDate" disabled="${viewMode}" />
														<label class="input-group-addon" for="WPOrderDate"><i
															class="fa fa-calendar"></i> <input type="hidden"
															id="WPOrderDate"> </label>
													</div></td>
											</tr>
											<tr>
												<td><label for="bmResolutionNumber"><spring:message
															code="bill.resolution.no" /></label></td>
												<td><form:input type="text" path="resolutionNumber"
														class="form-control" name="bmResolutionNumber"
														id="bmResolutionNumber" disabled="${viewMode}" /></td>
												<td><div class="input-group">
														<form:input path="resolutionDate"
															class="datepicker form-control" name="bmResolutionDate"
															id="bmResolutionDate" disabled="${viewMode}" />
														<label class="input-group-addon" for="resolutionDate"><i
															class="fa fa-calendar"></i> <input type="hidden"
															id="resolutionDate"> </label>
													</div></td>
											</tr>
										</thead>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<form:hidden path="departmentId" id="departmentId" />
				<c:if test="${!viewMode}">
				</c:if>
				<form:hidden id="transBcId" path="transHeadFlagBudgetCode" />
				<form:hidden id="transAcId" path="transHeadFlagAccountCode" />
				<h4>
					<spring:message code="bill.expenditure.details"
						text="Expenditure Details" />
				</h4>
				<div class="table-responsive" id="expenditureDetTable"
					style="overflow: visible;">

					<table id="expDetailTable"
						class="table table-bordered table-striped expDetailTableClass">
						<thead>
							<tr>
								<th scope="col" width="36%"><spring:message
										code="bill.budget.heads" /></th>
								<th scope="col" width="12%"><spring:message
										code="bill.amount" /></th>
								<th scope="col" width="12%"><spring:message
										code="bill.sanctioned.amount" /></th>
								<th scope="col" width="5%"><spring:message
										code="bill.budget" /></th>
								<c:if test="${!viewMode}">
									<th scope="col" width="10%"><spring:message
											code="bill.action" /></th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${viewData.expenditureDetailList}"
								var="expenditure" varStatus="index">
								<c:set value="${index.count}" var="count" />
								<tr class="expenditureClass" id="exptr0">
									<td><form:input path="" value="${expenditure.acHeadCode}"
											disabled="true" cssClass="form-control" /> <form:hidden
											path="" value="${expenditure.sacHeadId}"
											id="expenditureBudgetCode${count}" /></td>
									<td><form:input path=""
											value="${expenditure.actualAmount}" disabled="true"
											cssClass="form-control text-right" /></td>
									<td><form:input path=""
											value="${expenditure.billChargesAmount}" disabled="true"
											cssClass="form-control text-right" id="bchChargesAmt${count}" /></td>
									<td>
										<button type="button" class="btn btn-primary btn-sm viewExp"
											onclick="viewExpenditureDetails(${count})"
											id="viewExpDet${count}">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="popUp"></div>
				</div>
				<c:if test="${not empty viewData.deductionDetailList}">
					<h4>
						<spring:message code="account.bill.deduction.detail"
							text="Deduction Details" />
					</h4>
					<div class="table-responsive" id="deductionDetTable"
						style="overflow: visible;">
						<table id="dedDetailTable" class="table table-bordered table-striped dedDetailTableClass">
							<tbody>
								<tr>
								<th scope="col" style="width:33%;"><spring:message
										code="account.bill.deduction.head" text="Deduction Heads" /></th>
								<c:if test="${accountBillEntryBean.billTypeCode ne 'ESB'}">
								<th scope="col" width="33%"><spring:message
										code="" text="Expenditure Head" /></th>
								</c:if>
								<th scope="col" width="7%"><spring:message
										code="account.bill.rate" text="Rate(%)" /></th>
								<th scope="col" width="21%"><spring:message
										code="bill.amount" text="Amount" /></th>
							</tr>
								<c:forEach items="${viewData.deductionDetailList}"
									var="deduction" varStatus="status">
									<tr class="deductionClass" id="deduction0">
										<td><form:input path="" value="${deduction.acHeadCode}"
												cssClass="form-control" disabled="true" /></td>
										<c:if test="${accountBillEntryBean.billTypeCode ne 'ESB'}">
										<td><c:set value="${deduction.bchId}"
											var="dpDeptid"></c:set> <c:forEach items="${dedutionExpHeadMap}"
											varStatus="status" var="deptMap">
											<c:if test="${dpDeptid eq deptMap.key}">
										<form:input type="text" value="${deptMap.value}"
												path="" class="form-control" id="dpDeptid" disabled="true"/>
											</c:if>
										</c:forEach>
										</td>
										</c:if>
										<td><form:input path=""
												value="${deduction.deductionRate}" disabled="true"
												cssClass="form-control text-right" /></td>
										<td><form:input path=""
												value="${deduction.deductionAmount}"
												cssClass="form-control text-right" disabled="true" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
					&nbsp;  
					 <c:if test="${not empty viewData.disallowedRemark}">
					<div class="form-group">
						<label for="disallowedRemark" class="col-sm-2 control-label"><spring:message
								code="" text="Disallowed Remark" /></label>
						<div class="col-sm-10">
							<form:textarea path="disallowedRemark"
								value="${viewData.disallowedRemark}" class="form-control"
								disabled="true" />
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label for="bmNarration" class="col-sm-2 control-label"><spring:message
							code="bill.narration" /></label>
					<div class="col-sm-10">
						<form:textarea path="narration" value="${viewData.narration}"
							class="form-control" disabled="true" />
					</div>
				</div>
				<c:set value="${accountBillEntryBean.authorizationStatus}"
					var="checker"></c:set>
				<c:if
					test="${checker eq 'Rejected' && !(accountBillEntryBean.authorizationMode eq 'Auth') }">
					<div class="form-group">
						<label for="authorizerRemark" class="col-sm-2 control-label"><spring:message
								code="" text="Authorizer Remark" /></label>
						<div class="col-sm-10">
							<form:textarea id="authorizerRemark" path="checkerRemarks"
								disabled="true" class="form-control" />
						</div>
					</div>
				</c:if>
				<h4>
					<spring:message code="account.bill.summary" text="Bill Summary" />
				</h4>
				<div class="table-responsive" id="totalsTable">
					<table id="totalAmountsTable"
						class="table table-bordered table-striped totalTabClass">
						<thead>
							<tr>
								<th scope="col" width="30"><spring:message
										code="account.bill.total.amt" text="Total Bill Amount" /></th>
								<th scope="col" width="30"><spring:message
										code="account.bill.total.sanction.amt"
										text="Total Sanctioned Amount" /></th>
								<th scope="col" width="30"><spring:message
										code="account.bill.total.disallow.amt"
										text="Total Disallowed amount" /></th>
								<th scope="col" width="30"><spring:message
										code="account.bill.total.deduction" text="Total Deductions" /></th>
								<th scope="col" width="30"><spring:message
										code="account.bill.net.payable" text="Net Payable" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><form:input path="" value="${viewData.billTotalAmount}"
										disabled="true" class="form-control text-right" /></td>
								<td><form:input path=""
										value="${viewData.totalSanctionedAmount}" disabled="true"
										class="form-control text-right" /></td>
								<td><form:input path=""
										value="${viewData.totalDisallowedAmount}" disabled="true"
										class="form-control text-right" /></td>
								<td><form:input path="" value="${viewData.totalDeductions}"
										disabled="true" class="form-control text-right" /></td>
								<td><form:input path="" value="${viewData.netPayable}"
										disabled="true" class="form-control text-right" /></td>
							</tr>
						</tbody>
					</table>
				</div>
					&nbsp;
						<c:if test="${!(accountBillEntryBean.authorizationMode eq Auth) }">
					<div class="form-group" id="authMode">
						<label for="" class="col-sm-2 control-label"><spring:message
								code="account.bill.final.decision" text="Final Decision" /></label>
						<div class="col-sm-4">
							<label class="radio-inline"><form:radiobutton
									path="checkerAuthorization" name="checkerAuthorization"
									cssClass="set-radio" value="Y" disabled="true" /> <spring:message
									code="" text="Sanctioned" /></label> <label class="radio-inline"><form:radiobutton
									path="checkerAuthorization" name="checkerAuthorization"
									cssClass="set-radio" value="R" disabled="true" /> <spring:message
									code="" text="Rejected" /></label>
						</div>
					</div>

					<div class="form-group">
						<label for="checkerRemarks" class="col-sm-2 control-label"><spring:message
								code="account.bill.entry.authorizer" text="Authorizer" /></label>
						<div class="col-sm-4">
							<form:input id="authorizerEmployee" path="authorizerEmployee"
								disabled="true" class="form-control" />
						</div>
						<label for="checkerRemarks" class="col-sm-2 control-label"><spring:message
								code="account.bill.remarks" text="Remarks" /></label>
						<div class="col-sm-4">
							<form:textarea id="checkerRemarks" path="checkerRemarks"
								disabled="true" class="form-control" />
						</div>
					</div>
				</c:if>
				<form:hidden path="authorizationMode" id="authorizationMode" />
				<form:hidden id="notEnoughBudgetFlag" path="" />
				<div class="text-center padding-10">
					<button type="button" class="btn btn-danger"
							name="button-1496152380275" style=""
							onclick="viewRTGSBillPaymentDetails()" id="backBtn">
					  <spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
<c:if test="${accountBillEntryBean.hasError eq 'false'}">	
</c:if>