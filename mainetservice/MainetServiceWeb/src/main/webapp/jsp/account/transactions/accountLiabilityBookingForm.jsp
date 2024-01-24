<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/accountLiabilityBooking.js"
	type="text/javascript"></script>
<script>
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true
	});
</script>

<form:form method="POST" action="AccountLiabilityBooking.html"
	class="form-horizontal" name="liabilityBooking" id="liabilityBooking"
	modelAttribute="liabilityBookingBean">
	<div class="error-div alert alert-danger alert-dismissible"
		id="errorDivId" style="display: none;">
		<button type="button" class="close" onclick="closeOutErrBox()"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<span id="errorId"></span>
	</div>
	<jsp:include page="/jsp/tiles/validationerror.jsp" />

	<div class="panel-group accordion-toggle"
		id="accordion_single_collapse">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" class=""
						data-parent="#accordion_single_collapse" href="#tender"><spring:message
							code="account.liability.booking.entry"
							text="Liability Booking Entry Details" /></a>
				</h4>
			</div>
			<div id="tender" class="panel-collapse collapse in">
				<div class="panel-body">

					<div class="form-group">
						<c:if test="${!disabled}">
							<form:hidden value="${!disabled}" id="liability" path="" />
							<label class="col-sm-2 control-label" for="lbLiabilityNo"><spring:message
									code="account.liability.booking.number" text="Liability No" /></label>
							<div class="col-sm-4">
								<form:hidden path="lbLiabilityId" />
								<form:input path="lbLiabilityNo" class="form-control"
									readonly="true"></form:input>
							</div>
						</c:if>
						<label
							class="col-sm-2 control-label required-cal  required-control"
							for="liability-date"><spring:message
								code="account.liability.booking.date" text="Liability Date" /></label>
						<div class="col-sm-4">
							<form:input path="lbEntryDate" id="lbEntryDate"
								class="datepicker cal form-control" disabled=""></form:input>


						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="remark-receipt"><spring:message
								code="account.liability.booking.tender.number" text="Tender No" /></label>
						<div class="col-sm-4">
							<form:input path="trTenderNo" class="form-control"
								disabled="true" />
							<form:hidden path="trTenderId"></form:hidden>
						</div>
						<label class="col-sm-2 control-label required-cal"
							for="tenderEntryDate"><spring:message
								code="account.liability.booking.tender.entrydate"
								text="Tender Entry Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input type="text" class="form-control" path="trEntryDate"
									disabled="true"></form:input>
								<label class="input-group-addon" for="trasaction-date-icon30"><i
									class="fa fa-calendar"></i><input type="hidden"
									id="trasaction-date-icon30"></label>
							</div>
						</div>
					</div>



					<div class="form-group">
						<label class="col-sm-2 control-label" for="department"><spring:message
								code="voucher.template.entry.master.department"
								text="Department" /></label>
						<div class="col-sm-4">
							<form:input path="dpDept" class="form-control" disabled="true" />
						</div>
						<label class="col-sm-2 control-label" for="tender-no"><spring:message
								code="account.liability.booking.tender.type" text="Tender Type" /></label>
						<div class="col-sm-4">
							<form:input path="trType" class="form-control" disabled="true" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="tender-amount"><spring:message
								code="account.liability.booking.tender.amount"
								text="Tender Amount" /></label>
						<div class="col-sm-4">
							<form:input path="trTenderAmount" class="form-control"
								disabled="true" />
						</div>
						<label class="col-sm-2 control-label" for="emd-amount"><spring:message
								code="account.liability.booking.emd.amount" text="EMD Amount" /></label>
						<div class="col-sm-4">
							<form:input path="trEmdAmt" class="form-control" disabled="true" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="vendor-code"><spring:message
								code="bill.vendor.code" text="Vendor Code" /></label>
						<div class="col-sm-4">
							<form:input path="vmVendorCode" class="form-control"
								disabled="true" />
						</div>
						<label class="col-sm-2 control-label" for="vendor-name"><spring:message
								code="account.tenderentrydetails.VendorEntry" text="Vendor Name" /></label>
						<div class="col-sm-4">
							<form:input path="vendorName" class="form-control"
								disabled="true" />
						</div>
					</div>



				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class="collapsed"
							data-parent="#accordion_single_collapse" href="#expenditure">Liability
							Details</a>
					</h4>
				</div>
				<div id="expenditure" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="table-responsive" id="liabilityDetTable">
							<table id="liabDetailTable" 
								class="table table-bordered table-striped appendableClass">
								<tbody>
									<tr>

										<th scope="col" width="14%"><spring:message
												code="account.liability.booking.primary.acc"
												text="Primary A/c" /></th>
										<th scope="col" width="14%"><spring:message
												code="account.liability.booking.secondary.acc"
												text="Secondary A/c" /></th>
										<th scope="col" width="14%"><spring:message
												code="account.common.fund" text="Fund" /></th>
										<th scope="col" width="14%"><spring:message
												code="account.common.function" text="Function" /></th>
										<th scope="col" width="14%"><spring:message
												code="account.common.field" text="Field" /></th>
										<c:if test="${!disabled}">
											<th scope="col" width="14%"><spring:message
													code="account.liability.booking.liabilityamt"
													text="Liability Amount" /></th>
											<th scope="col" width="14%"><spring:message
													code="account.budgetopenmaster.financialyear"
													text="Financial Year" /></th>
											<th><spring:message code="account.common.add.remove" /></th>
										</c:if>
									</tr>
									<c:forEach items="${tenderDetList}" var="taxHeadsLevel"
										varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>
										<form:hidden path="detList[${count}].lbLiabilityDetId"
											value='${liabilityBookingBean.detList[count].lbLiabilityDetId}' />
										<tr class="liabilityClass">
											<td><form:select id="pacHeadId${count}"
													path="detList[${count}].pacHeadId" class="form-control"
													disabled="true"
													onchange="setSecondaryCodeFinance(${count})">
													<form:option value="">
														<spring:message code="account.common.select" />
													</form:option>
													<c:forEach items="${pacMap}" varStatus="status"
														var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:hidden
													value='${liabilityBookingBean.detList[count].sacHeadId}'
													id="selectedSecondaryValue${count}" path="" /> <form:select
													id="sacHeadId${count}" path="detList[${count}].sacHeadId"
													class="form-control" disabled="true">
													<form:option value="">
														<spring:message code="account.common.select" />
													</form:option>
													<c:forEach items="${sacMap}" varStatus="status"
														var="sacItem">
														<form:option value="${sacItem.key}" code="${sacItem.key}">${sacItem.value}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:select id="fundId${count}"
													path="detList[${count}].fundId" class="form-control"
													disabled="true">
													<form:option value="">
														<spring:message code="account.common.select" />
													</form:option>
													<c:forEach items="${fundMap}" varStatus="status"
														var="fundItem">
														<form:option value="${fundItem.key}"
															code="${fundItem.key}">${fundItem.value}</form:option>
													</c:forEach>
												</form:select></td>

											<td><form:select id="functionId${count}"
													path="detList[${count}].functionId" class="form-control"
													disabled="true">
													<form:option value="">
														<spring:message code="account.common.select" />
													</form:option>
													<c:forEach items="${funcMap}" varStatus="status"
														var="functionItem">
														<form:option value="${functionItem.key}"
															code="${functionItem.key}">${functionItem.value}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:select id="fieldId${count}"
													path="detList[${count}].fieldId" class="form-control"
													disabled="true">
													<form:option value="">
														<spring:message code="account.common.select" />
													</form:option>
													<c:forEach items="${fieldMap}" varStatus="status"
														var="fieldItem">
														<form:option value="${fieldItem.key}"
															code="${fieldItem.key}">${fieldItem.value}</form:option>
													</c:forEach>
												</form:select></td>
											<c:if test="${!disabled}">
												<td><form:input id="liabilityAmount${count}"
														path="detList[${count}].liabilityAmount" readonly="true"
														class="form-control" /></td>
												<td><form:select id="faYearid${count}"
														path="detList[${count}].faYearid" class="form-control"
														disabled="true">
														<form:option value="">
															<spring:message code="account.common.select" />
														</form:option>
														<c:forEach items="${finYearMap}" varStatus="status"
															var="finItem">
															<form:option value="${finItem.key}" code="${finItem.key}">${finItem.value}</form:option>
														</c:forEach>
													</form:select></td>

												<td><a data-toggle="tooltip" data-placement="top"
													title="Add" class="btn btn-success btn-sm addButton"
													id="addButton${count}"><i class="fa fa-plus-circle"></i></a>
													<a data-toggle="tooltip" data-placement="top"
													title="Delete" class="btn btn-danger btn-sm delButton"
													id="delButton${count}"><i class="fa fa-trash-o"></i></a></td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div>
				</div>
			</div>

		</div>
		<div class="text-center">
			<c:if test="${disabled}">
				<button type="button" class="btn btn-success btn-submit"
					onclick="saveLiability(this)">
					<spring:message code="account.liability.booking.gen.liabilityno"
						text="Generate Liability No" />
				</button>
			</c:if>
			<c:if test="${!disabled}">
				<button type="button" id="saveButton" class="btn btn-success btn-submit"
					onclick="saveLiability(this)">
					<spring:message code="account.bankmaster.save" text="Save" />
				</button>
				<button type="button" id="editButton" class="btn btn-primary"
					onclick="enableDetFields()">
					<spring:message code="account.edit" text="Edit" />
				</button>
			</c:if>

			<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountLiabilityBooking.html'"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
		</div>

	</div>

</form:form>

