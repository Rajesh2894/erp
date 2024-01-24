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

<script>
	$(document)
			.ready(
					function() {
						function closeErrBox() {
							$('.error-div').hide();
						}

						// add multiple select / deselect functionality
						$("#selectallchequeOrDDD")
								.click(
										function() {
											debugger;
											var status = this.checked; // "select all" checked status
											$('.case3').each(function() { //iterate all listed checkbox items
												this.checked = status; //change ".checkbox" checked status
											});
											var totalAmount = 0;
											$('.chequeTr')
													.each(
															function(i) {
																var amount = parseFloat($(
																		"#orginalEstamt"
																				+ i)
																		.val())

																totalAmount = totalAmount
																		+ amount;
																$(
																		"#chequeTotalAmount")
																		.val(
																				totalAmount);
																$(
																		"#totalAmount")
																		.val(
																				totalAmount);
																getAmountFormatInStatic('totalAmount');

															});
											if ($("#selectallchequeOrDDD:checked").length == 0) {
												$("#chequeTotalAmount").val(
														"0.00");
												$("#totalAmount").val("0.00");

											}
										});

						$(".case3")
								.click(
										function() {
											var id = $(this).attr('id');
											debugger;
											var amount = parseFloat($(
													"#orginalEstamt" + id)
													.val());

											if ($("#chequeTotalAmount").val() != undefined
													&& $("#chequeTotalAmount")
															.val() != "") {
												var totalAmount = parseFloat($(
														"#chequeTotalAmount")
														.val());
											} else {
												var totalAmount = 0;
											}
											if ($('input[id="' + id
													+ '"]:checked').length > 0) {
												if (isNaN(amount)) {
													totalAmount = totalAmount;
												} else {
													totalAmount = totalAmount
															+ amount;
													$("#totalAmount").val(
															totalAmount);
													getAmountFormatInStatic('totalAmount');
												}
											} else {
												if (isNaN(amount)) {
													totalAmount = 0;
												} else {
													totalAmount = totalAmount
															- amount;
													$("#totalAmount").val(
															totalAmount);
													getAmountFormatInStatic('totalAmount');

												}
											}
											var result = parseFloat(totalAmount
													.toFixed(2));
											$("#chequeTotalAmount").val(result);
											$("#totalAmount").val(result);
											getAmountFormatInStatic('totalAmount');
											getAmountFormatInStatic('chequeTotalAmount');
											debugger;
											var alertms = getLocalMessage('tp.app.alMessg');

											if ($(".case3").length == 0) {
												alert(alertms);
											}
											$("#deptId").removeClass(
													"mandColorClass");
										});

					});

	$(function() {
		var response = __doAjaxRequest('AccountReceiptEntry.html?SLIDate',
				'GET', {}, false, 'json');
		var disableBeforeDate = new Date(response[0], response[1], response[2]);
		var date = new Date();
		var today = new Date(date.getFullYear(), date.getMonth(), date
				.getDate());

		$('#transactionDateId').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate : disableBeforeDate,
			maxDate : today,
			onSelect : function(dateText, inst) {
				$(this).focus();
			}
		});
		$("#transactionDateId").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});

		$("#fromDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate : disableBeforeDate,
			maxDate : today
		});
		$("#fromDate").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});

		$('#fromDate, #toDate, #transactionDateId').change(function() {
			//alert();
			var check = $(this).val();
			if (check == '') {
				$(this).parent().switchClass("has-success", "has-error");
			} else {
				$(this).parent().switchClass("has-error", "has-success");
			}
		});

		$("#toDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate : disableBeforeDate,
			maxDate : today
		});
		$("#toDate").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});

	});
</script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/tdsPaymentEntry.js"
	type="text/javascript"></script>

<div class="widget">
	<div class="widget-content padding" id="paymentEntryDiv">
	<div class="widget-header" id="tdsFirstWidget">
		<h2> <spring:message code="tds.payment.entry" text="TDS PAYMENT ENTRY" /></h2>
	</div>

	
		<form:form method="POST" action="TdsPaymentEntry.html"
			cssClass="form-horizontal" name="paymentEntry"
			id="tdsPaymentEntryFrm" modelAttribute="tdsPaymentEntryDto">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:hidden path="" value="${templateExistFlag}"
				id="templateExistFlag" />
			<form:hidden path="dupTransactionDate" id="dupTransactionDate" />
			<form:hidden path="dupVendorId" id="dupVendorId" />
			<form:hidden path="sacHeadId" id="sacHeadId" />
			<form:hidden path="successfulFlag" id="successfulFlag" />
			<input type="hidden"
				value="${fn:length(tdsPaymentEntryDto.vendorDetails)}" id="revCount" />

			<div class="form-group" style="margin-top: 10px;">
				<label for="transactionDateId"
					class="col-sm-2 control-label required-control"><spring:message
						code="advance.management.master.paymentdate" text="Payment Date" /></label>

				<div class="col-sm-4">
					<div class="input-group">
						<form:input path="transactionDate" id="transactionDateId"
							cssClass="mandColorClass form-control" data-rule-required="true"
							onchange="setInstrumentDate(this)" maxlength="10"></form:input>
						<label class="input-group-addon" for="transactionDateId"><i
							class="fa fa-calendar"></i><span class="hide"><spring:message
									code="account.additional.supplemental.auth.icon" text="icon" /></span></label>
					</div>
				</div>

				<!--tds  -->
				<label for="tdsTypeId"
					class="col-sm-2 control-label required-control"> <spring:message code="accounts.bankfortds.tds.type" text="TDS Type" /></label>
				<div class="col-sm-4">
					<form:select path="tdsTypeId"
						class="form-control chosen-select-no-results chosenReset"
						id="tdsTypeId" data-rule-required="true" onchange="getvendornameByTdsType()">
						<option value=""><spring:message code="account.select"
								text="Select" /></option>
						<c:forEach items="${tdsList}" varStatus="status" var="tds">
							<form:option value="${tds.lookUpId}">${tds.descLangFirst}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label required-control"> <spring:message
						code="account.tenderentrydetails.VendorEntry" text="Vendor Name" /></label>
				<div class="col-sm-4">
					<form:hidden path="vendorDesc" id="vendorDesc" />
					<form:select path="vendorId"
						class="form-control chosen-select-no-results chosenReset"
						name="vendorId" id="vendorId" data-rule-required="true">
						<option value=""><spring:message code="account.select"
								text="Select" /></option>
						<c:forEach items="${vendorList}" varStatus="status" var="vendor">
							<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<!-- commented below field against id #133285 -->
				<%-- <label for="bmBilltypeCpdId"
					class="col-sm-2 control-label required-control"><spring:message
						code="" text=" Payment Type" /></label>
				<div class="col-sm-4">
					<form:select type="select" path="billTypeId"
						class="form-control chosen-select-no-results chosenReset"
						name="billTypeId" id="bmBilltypeCpdId" disabled="${viewMode}"
						data-rule-required="true">
						<option value=""><spring:message code="account.select"
								text="Select" /></option>
						<c:forEach items="${billTypeList}" varStatus="status"
							var="billType">
							<form:option value="${billType.lookUpId}">${billType.descLangFirst}</form:option>
						</c:forEach>
					</form:select>

				</div> --%>
			</div>

			<div class="form-group">

				<label class="col-sm-2 control-label required-control"><spring:message
						code="budget.additionalsupplemental.authorization.fromdate"
						text="From Date" /></label>
				<div class="col-sm-4">
					<div class="input-group">
						<form:input path="fromDate" id="fromDate"
							class="form-control mandColorClass " value="" maxlength="10" />
						<label class="input-group-addon" for="fromDate"><i
							class="fa fa-calendar"></i><span class="hide"><spring:message
									code="account.additional.supplemental.auth.icon" text="icon" /></span><input
							type="hidden" id="fromDate"></label>
					</div>
				</div>

				<label class="col-sm-2 control-label required-control"><spring:message
						code="budget.additionalsupplemental.authorization.todate" text="To Date" /></label>
				<div class="col-sm-4">
					<div class="input-group">
						<form:input path="toDate" id="toDate"
							class=" form-control mandColorClass " value="" maxlength="10" />
						<label class="input-group-addon" for="toDate"><i
							class="fa fa-calendar"></i><span class="hide"><spring:message
									code="account.additional.supplemental.auth.icon" text="icon" /></span><input
							type="hidden" id="toDate"></label>
					</div>
				</div>
			</div>

			<div class="text-center padding-bottom-10">
				<button type="button" id="search"
					value="<spring:message code="search.data"/>"
					class="btn btn-success searchData" onclick="getVendorDetails(this)">
					<i class="fa fa-search"></i>
					<spring:message code="account.bankmaster.search" text="Search" />
				</button>
			</div>
			<div class="panel-group accordion-toggle" id="ledgerDetailIds">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 type="h4" class="panel-title table" id="">
							<a data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#a2"> <spring:message code="account.tds.details" text="TDS Details" />
								</a>
						</h4>
					</div>
					<div id="a2" class="panel-collapse collapse in">
						<div class="panel-body">
							<div id="table-responsive">
								<table id="main-table"
									class="table text-left table-bordered table-striped">
									<tbody>
										<tr>
											<th width="10%"> <spring:message code="opening.balance.report.srno" text="Sr. No." /></th>
											<th width="20%"> <spring:message code="accounts.vendormaster.vendorName" text="Vendor Name" /></th>
											<th width="25%"> <spring:message code="account.bill.no.validation" text="Bill No" /></th>
											<th width="15%"> <spring:message code="bill.date" text="Bill Date" /></th>
											<th width="20%"> <spring:message code="accounts.deduction.register.tds.amount" text="TDS Amount" /></th>
											<th width="10%"><label><input type="checkbox"
													class="case3" name="All" id="selectallchequeOrDDD"
													class="case3"
								 					onchange="getAmountFormatInStatic('chequeTotalAmount')" /><spring:message code="account.budgetopenmaster.selectall" text="Select All" /> </label></th>
										</tr>
										<c:forEach items="${tdsPaymentEntryDto.paymentDetailsDto}"
											var="funMasterLevel" varStatus="status">
											<c:set value="${status.index}" var="count"></c:set>
											<tr class="chequeTr">
												<form:hidden path="paymentDetailsDto[${count}].bdhId"
													id="bdhId${count}" />


												<form:hidden path="paymentDetailsDto[${count}].id"
													id="sacHeadId${count}" />
												<form:hidden path="paymentDetailsDto[${count}].bmId"
													id="bmId${count}" />
												<form:hidden path="paymentDetailsDto[${count}].billTypeId"
													id="billTypeId${count}" />

												<td><form:input path="" id="" class=" form-control "
														disabled="true" name="text-01" value="${count+1}" /></td>

												<td><form:input
														path="paymentDetailsDto[${count}].vendorName"
														id="vmVendorname${count}" class=" form-control "
														disabled="true" name="text-01" /></td>

												<td><form:input
														path="paymentDetailsDto[${count}].billNumber"
														id="billNumber${count}" class=" form-control "
														disabled="true" name="text-01" /></td>

												<td><form:input
														path="paymentDetailsDto[${count}].billDate"
														id="billDate${count}" class=" form-control "
														disabled="true" name="text-01"
														cssClass="form-control text-center" /></td>

												<td class="text-right"><form:input type="text"
														cssClass="form-control text-right" name="text-01"
														path="paymentDetailsDto[${count}].deductions"
														id="orginalEstamt${count}" readonly="true" /></td>

												<td class="text-center"><form:checkbox
														path="paymentDetailsDto[${count}].vendorDetCheckFlag"
														value="" id="${count}" class="case3"
														onchange="getAmountFormatInStatic('chequeTotalAmount')" />
												</td>
											</tr>
										</c:forEach>
										<tr>
											<th colspan="4" class="text-right"> <spring:message code="advance.management.master.paymentamount" text="Payment Amount" /></th>
											<td><form:input path="total" readonly="true"
													id="chequeTotalAmount" class="form-control text-right" /></td>
											<td></td>
										</tr>
									</tbody>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
			<jsp:include page="/jsp/account/transactions/tdsEntryForm.jsp" />
		</form:form>
	</div>
</div>
