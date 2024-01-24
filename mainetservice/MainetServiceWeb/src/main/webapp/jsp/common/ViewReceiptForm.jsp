<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/common/viewReceipt.js"></script>

<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="" text="Receipts" />
				</h2>
				<apptags:helpDoc url="ReceiptForm.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="reciptForm" action="ReceiptForm.html" method="post"
					class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="error-div alert alert-danger alert-dismissible"
						id="errorDivId" style="display: none;">
						<button type="button" class="close" onclick="closeOutErrBox()"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span id="errorId"></span>
					</div>

					<h4>
						<spring:message code="receipt.details"></spring:message>
					</h4>
					<div id="receipt-details" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label class="control-label  col-sm-2"> <spring:message
										code="receipt.no"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input path="receiptMasBean.rmReceiptNo" id="rmRcptno"
										class="form-control" maxLength="12" disabled="true" />
								</div>
								<label class="control-label col-sm-2"> <spring:message
										code="receipt.date"></spring:message>
								</label>

								<div class="col-sm-4">
									<div class="input-group">
										<form:input name="" Class="datepicker form-control"
											path="receiptMasBean.rmDatetemp" id="rmDate" maxLength="10"
											disabled="true" />
										<label for="rmDatetemp" class="input-group-addon"> <i
											class="fa fa-calendar"></i><span class="hide"><spring:message
													code="account.additional.supplemental.auth.icon"
													text="icon" /></span><input type="hidden" id="rmDatetemp"></label>
									</div>
								</div>

							</div>
							<div class="form-group">

								<label class="control-label  col-sm-2 "> <spring:message
										code="receipt.mobileNo"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="mobileNumber"
										path="receiptMasBean.mobileNumber"
										class="form-control hasNumber" maxLength="10" disabled="true" />
								</div>
								<label class="control-label  col-sm-2 "> <spring:message
										code="receipt.emailId"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="emailId" path="receiptMasBean.emailId"
										class="form-control text-lowercase" maxLength="200"
										disabled="true" />
								</div>

							</div>
							<div class="form-group">
								<label class="control-label  col-sm-2  required-control ">
									<spring:message code="receipt.name"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="rmReceivedfrom"
										path="receiptMasBean.rmReceivedfrom" class="form-control"
										maxLength="200" disabled="true" />
								</div>

								<label class="control-label  col-sm-2 required-control">
									<spring:message code="receipt.narration"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:textarea id="rmNarration"
										path="receiptMasBean.rmNarration" class="form-control "
										maxLength="200" disabled="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label  col-sm-2"> <spring:message
										code="receipt.manual_receiptNo"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="manualReceiptNo"
										path="receiptMasBean.manualReceiptNo" class="form-control"
										maxLength="50" disabled="true" />
								</div>
							</div>

						</div>
					</div>


					<h4>
						<spring:message code="receipt.receipt_collection_details"></spring:message>
					</h4>


					<div id="receipt-collection-details"
						class="panel-collapse collapse in" style="overflow: visible;">
						<div class="panel-body">
							<table class="table table-bordered">
								<tr>
									<th width="15%" scope="col"><spring:message
											code="receipt.receipt_type" text="Type"></spring:message></th>
									<th width="15%" scope="col"><spring:message
											code="receipt.receipt_amount" text="Receipt Amount"></spring:message><i
										class="fa fa-inr"></i></th>
								</tr>
								<c:forEach items="${command.receiptMasBean.taxdetails}"
									var="chargeMap" varStatus="loop">
									<tr>
										<td class="text-left">${chargeMap.key}</td>
										<td class="text-right">${chargeMap.value}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>

					<h4>
						<spring:message code="receipt.collection_mode_details"></spring:message>
					</h4>
					<div id="collection-collection-details"
						class="panel-collapse collapse in">
						<div class="panel-body">
							<table class="table table-bordered">
								<tr>
									<th width="15%" scope="col"><spring:message
											code="receipt.payment_mode"></spring:message></th>
									<c:if
										test="${command.receiptMasBean.receiptModeDetailList.cpdFeemodeCode ne 'C'}">
										<th width="40%" scope="col"><spring:message
												code="receipt.drawn_on"></spring:message></th>
										<th width="15%" scope="col"><spring:message
												code="receipt.cheque_dd_no_pay_order"></spring:message>
											</th>
										<th width="15%" scope="col"><spring:message
												code="receipt.cheque_dd_date"></spring:message></th>
									</c:if>
									<th width="15%" scope="col" class="text-center"><spring:message
											code="receipt.mode_amount"></spring:message> <i
										class="fa fa-inr"></i></th>
								</tr>

								<tr>
									<td><form:input id="cpdFeemodeCode"
											path="receiptMasBean.receiptModeDetailList.cpdFeemodeCode"
											type="hidden" /> <form:input id="cpdFeemodeDesc"
											path="receiptMasBean.receiptModeDetailList.cpdFeemodeDesc"
											class="form-control" disabled="true" /></td>

									<c:if
										test="${command.receiptMasBean.receiptModeDetailList.cpdFeemodeCode ne 'C'}">
										<td id="tablePayModecbBankid"><form:input
												id="cbBankidDesc"
												path="receiptMasBean.receiptModeDetailList.cbBankidDesc"
												class="form-control" disabled="true" /></td>
												
										<td id="rdchequeddnodata"><form:input id="rdchequeddno"
												path="receiptMasBean.receiptModeDetailList.tranRefNumber"
												class="form-control hasNumber" maxLength="8" disabled="true" />
										</td>

										<td id="rdChequedddatetemp">
											<div class="input-group">
												<form:input id="rdChequedddatetemp"
													path="receiptMasBean.receiptModeDetailList.rdChequedddatetemp"
													class="form-control datepicker" disabled="true" />
												<label class="input-group-addon" for="rdChequedddatetemp"><i
													class="fa fa-calendar"></i> <input type="hidden"
													id="rdChequedddatetemp"> </label>
											</div>
										</td>
									</c:if>

									<td><fmt:setLocale value="en_IN" />
										<fmt:formatNumber type="currency"
											value="${command.receiptMasBean.receiptModeDetailList.rdAmount}"
											pattern="#,##,##,##,##0.00" groupingUsed="false" var="famt"
											maxIntegerDigits="15" maxFractionDigits="2" /> <form:input
											id="rdamount" name="rdamount"
											path="receiptMasBean.receiptModeDetailList.rdAmount"
											value="${famt}" class="form-control text-right"
											disabled="true" readonly="true"
											onkeypress="return hasAmount(event, this, 12, 2)" /></td>
								</tr>
							</table>
						</div>
					</div>
					<div class="text-center margin-bottom-10">
						<input type="button" class="btn btn-danger" onclick="back()"
							value="<spring:message code="receipt.back" text="Back"/>" id="cancelEdit" />
					</div>
				</form:form>

			</div>



		</div>
	</div>
</div>
