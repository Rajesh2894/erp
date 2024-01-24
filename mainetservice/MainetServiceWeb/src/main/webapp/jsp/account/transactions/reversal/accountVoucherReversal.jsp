<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h4 id="">
	<spring:message code="account.voucher.reversal.title"
		text="Account Voucher Reversal" />
</h4>
<form:form action="">
	<div class="table-responsive" id="billDetailTableDiv"
		style="overflow: visible;">
		<table id="billDetailTable"
			class="table table-bordered table-striped billDetailTableClass">
			<thead>
				<tr>
					<th scope="col" width="10%"><spring:message
							code="account.cheque.cash.transaction" text="Transaction No." /></th>
					<th scope="col" width="16%"><spring:message
							code="accounts.Secondaryhead.Date" text="Date" /></th>
					<th scope="col" width="16%"><spring:message code="bill.amount"
							text="Amount" /></th>
					<th scope="col" width="16%"><spring:message
							code="accounts.receipt.narration" text="Narration" /></th>
					<th scope="col" width="5%"><spring:message text="View" /></th>
					<th scope="col" width="6%"><label
						class="checkbox-inline padding-top-0" for="selectAllBills"><input
							type="checkbox" name="All" id="selectAllBills"><b><spring:message
									text="All" /></b></label></th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${reversalItems}" var="reversalItem"
					varStatus="status">
					<c:set value="${status.index}" var="count" />
					<tr class="reversalItemCss">
						<td><form:input id="" path="" disabled="true"
								value="${reversalItem.transactionNo}"
								cssClass="form-control text-right" /></td>
						<td><form:input id="" path="" disabled="true"
								value="${reversalItem.transactionDate}"
								cssClass="form-control text-right" /></td>
						<td><form:input id="" path="" disabled="true"
								value="${reversalItem.transactionAmount}"
								cssClass="form-control text-right" /></td>
						<td><form:input id="" path="" onkeypress="" disabled="true"
								value="${reversalItem.narration}"
								cssClass="form-control text-right" /></td>
						<td>
							<button type="button" class="btn btn-primary btn-sm viewBill"
								onclick="viewDetails()" id="">
								<i class="fa fa-eye" aria-hidden="true"></i>
							</button>
						</td>

						<td align="center" scope="col"><form:checkbox path=""
								value="" name="selectBill" class="selectBill" onchange=""
								disabled="" id="" /></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="account.voucher.reversal.approval.order.no"
				text="Approval Order No" /><span class="mand">*</span></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control mandColorClass" />
		</div>
		<label class="col-sm-2 control-label"><spring:message
				code="account.voucher.reversal.approval.auth"
				text="Approval Authority" /><span class="mand">*</span></label>
		<div class="col-sm-4">
			<form:select path="transactionType"
				class="form-control mandColorClass">
				<form:option value="0">
					<spring:message code="budget.reappropriation.master.select"
						text="Select" />
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
			<form:textarea path=""
				class="form-control mandColorClass required-control" />
		</div>
	</div>

</form:form>

<div class="text-center padding-bottom-10">
	<button type="button" class="btn btn-success btn-submit" id="saveBtn">
		<spring:message code="account.bankmaster.save" text="Save" />
	</button>
	<button type="button" class="btn btn-danger"
		onclick="window.location.href='AdminHome.html'" id="backBtn">
		<spring:message code="account.bankmaster.back" text="Back" />
	</button>
</div>

