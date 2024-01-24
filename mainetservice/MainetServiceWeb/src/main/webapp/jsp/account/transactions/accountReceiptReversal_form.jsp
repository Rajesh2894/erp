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
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountReceipt.js"></script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>

<script>
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : $("#rdChequedddate").val(),
			maxDate : '-0d',
			changeYear : true
		});

	});

	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : $("#rmDatetemp").val(),
			maxDate : '-0d',
			changeYear : true
		});
	});

	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : $("#tranRefDate1").val(),
			maxDate : '-0d',
			changeYear : true
		});
	});

	function showConfirmBox() {
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = 'Proceed';

		message += '<div class="form-group"><p>Form Submitted Successfully</p>';
		message += '<p style=\'text-align:center;margin: 5px;\'>'
				+ '<br/><input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-success \'    '
				+ ' onclick="proceed()"/>' + '</p></div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}

	function proceed() {

		window.location.href = "javascript:openRelatedForm('AccountVoucherReversal.html');";
	}
	$('#tablePayModeHeading_cash').hide();
	$('#tablePayModeHeading_nftwebrtgs').hide();
	$('#tableRecModecbBankid').hide();
	$('#tranRefNumber').hide();
	$('#tranRefDate').hide();

	CashModeValidation();
</script>
<c:if test="${ShowBreadCumb ==false}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>
<!-- Start info box -->
<div class="widget" id="widget">
	<div class="mand-label clearfix">
		<span><spring:message code="account.common.mandmsg"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="account.common.mandmsg1" text="is mandatory" /> </span>
	</div>
	<div class="widget-content padding">

		<c:url value="${mode}" var="form_mode" />
		<c:url value="${saveAction}" var="url_form_submit" />

		<form:form class="form-horizontal"
			modelAttribute="tbServiceReceiptMas" cssClass="form-horizontal"
			method="POST" action="${url_form_submit}">
			<form:hidden path="successFlag" id="successFlag" />
			<form:hidden path="rmRcptid" />
			<form:hidden path="createdBy" />
			<form:hidden path="langId" />
			<form:hidden path="orgId" />
			<form:hidden path="rmDatetemp" />


			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />


			<c:if test="${form_mode == 'view'}">
				<SCRIPT>
					$(document).ready(function() {
						$('.error-div').hide();
						$('#editOriew').find('*').attr('disabled', 'disabled');
						$('#editOriew').find('*').addClass("disablefield");
						$('#manual_ReceiptNo').attr('disabled', 'disabled');
						$('#rmDate').attr('disabled', 'disabled');
						$('#rm_Receivedfrom').attr('disabled', 'disabled');
						$('#mobile_Number').attr('disabled', 'disabled');
						$('#email_Id').attr('disabled', 'disabled');
						$('#vm_VendorId').attr('disabled', 'disabled');
						$('#rdamount').attr('disabled', 'disabled');
						$('#cpdFeemode').attr('disabled', 'disabled');
						$('#bankId').attr('disabled', 'disabled');
						$('#rdchequeddno').attr('disabled', 'disabled');
						$('#rdchequedddatetemp').attr('disabled', 'disabled');
						$('#rmNarration').attr('disabled', 'disabled');
						$('#baAccountId').attr('disabled', 'disabled');
						$('#tranRefNumber1').attr('disabled', 'disabled');
						$('#tranRefDate1').attr('disabled', 'disabled');
						$('#receiptDelRemark').attr('disabled', 'disabled');
						$('#receiptDelDate').attr('disabled', 'disabled');
						$('#fieldId').attr('disabled', 'disabled');
						$('#isdeleted').attr('disabled', 'disabled');
						$('#isdeleted').addClass("disablefield");
					});
				</SCRIPT>
			</c:if>
			<c:if test="${form_mode == 'edit'}">
				<SCRIPT>
					$(document).ready(function() {
						$('.error-div').hide();
						$('#editOriew').find('*').attr('disabled', 'disabled');
						$('#editOriew').find('*').addClass("disablefield");
						$('#manual_ReceiptNo').attr('disabled', 'disabled');
						$('#rmDate').attr('disabled', 'disabled');
						$('#rm_Receivedfrom').attr('disabled', 'disabled');
						$('#mobile_Number').attr('disabled', 'disabled');
						$('#email_Id').attr('disabled', 'disabled');
						$('#vm_VendorId').attr('disabled', 'disabled');
						$('#rdamount').attr('disabled', 'disabled');
						$('#cpdFeemode').attr('disabled', 'disabled');
						$('#bankId').attr('disabled', 'disabled');
						$('#rdchequeddno').attr('disabled', 'disabled');
						$('#rdchequedddatetemp').attr('disabled', 'disabled');
						$('#rmNarration').attr('disabled', 'disabled');
						$('#baAccountId').attr('disabled', 'disabled');
						$('#tranRefNumber1').attr('disabled', 'disabled');
						$('#tranRefDate1').attr('disabled', 'disabled');
						$('#receiptDelDate').attr('disabled', 'disabled');
						$('#fieldId').attr('disabled', 'disabled');
						$('#isdeleted').attr('disabled', 'disabled');
						$('#isdeleted').addClass("disablefield");
					});
				</SCRIPT>
			</c:if>



			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">

				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#receipt-details">
								<spring:message code="accounts.receipt.receipt_details"></spring:message>
							</a>
						</h4>
					</div>
					<div id="receipt-details" class="panel-collapse collapse in">
						<form:hidden path="rmAmount" />
						<div class="panel-body">
							<div class="form-group">
								<label class="label-control col-sm-2 required-control">
									<spring:message code="accounts.receipt.receipt_no"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="rmRcptno" path="rmRcptno"
										class="form-control hasNumber" maxLength="12" readonly="true" />
								</div>
								<label class="label-control col-sm-2 "> <spring:message
										code="account.common.field" /></label>
								<div class="col-sm-4">
									<form:select id="fieldId" path="tbAcFieldMaster.fieldId"
										cssClass="form-control">
										<form:option value="">
											<spring:message code="account.common.select" />
										</form:option>
										<c:forEach items="${listOfTbAcFieldMasterItems}"
											varStatus="status" var="fieldItem">
											<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
										</c:forEach>
									</form:select>

								</div>
							</div>
							<div class="form-group">
								<label class="label-control col-sm-2"> <spring:message
										code="accounts.receipt.manual_receipt_no"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="manual_ReceiptNo" path="manualReceiptNo"
										class="form-control hasNumber" maxLength="12" />
								</div>
								<label class="label-control col-sm-2 required-control  ">
									<spring:message code="accounts.receipt.receipt_date"></spring:message>
								</label>
								<%
									java.text.DateFormat df = new java.text.SimpleDateFormat(
												"dd/MM/yyyy");
								%>
								<div class="col-sm-4">
									<form:input id="rmDate" path="rmDate"
										class="form-control form-control cal datepicker" />
								</div>
							</div>

							<div class="form-group">
								<label class="label-control col-sm-2"> <spring:message
										code="accounts.receipt.received_from"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:select id="vm_VendorId" path="vmVendorId"
										class="form-control">
										<form:option value="">
											<spring:message code="accounts.receipt.select_vendor"></spring:message>
										</form:option>
										<c:forEach items="${list}" var="vendorData">
											<form:option value="${vendorData.vmVendorid}">${vendorData.vmVendorname}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<label class="label-control col-sm-2  required-control ">
									<spring:message code="accounts.receipt.name"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="rm_Receivedfrom" path="rmReceivedfrom"
										class="form-control" maxLength="200" />
								</div>
							</div>

							<div class="form-group">
								<label class="label-control col-sm-2 "> <spring:message
										code="accounts.receipt.mobile_no"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="mobile_Number" path="mobileNumber"
										class="form-control hasNumber" maxLength="200" />
								</div>
								<label class="label-control col-sm-2 "> <spring:message
										code="accounts.receipt.email_id"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="email_Id" path="emailId" class="form-control"
										maxLength="200" />
								</div>
							</div>
							<div class="form-group">
								<label class="label-control col-sm-2 required-control">
									<spring:message code="accounts.receipt.narration"></spring:message>
								</label>
								<div class="col-sm-10">
									<form:textarea id="rmNarration" path="rmNarration"
										class="form-control hasSpecialChara" maxLength="200" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse"
								href="#receipt-collection-details"><spring:message
									code="accounts.receipt.receipt_collection_details"></spring:message>
							</a>
						</h4>
					</div>


					<div id="receipt-collection-details"
						class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="table-responsive" id="taxHeadsTable">
								<table
									class="table table-bordered table-striped appendableClass"
									id="receiptAccountHeadsTable">
									<tbody>

										<tr>

											<th width="70%"><spring:message
													code="budget.projected.revenue.entry.master.budgetcode"
													text="Budget Code" /> <span class="mand float-right"></span></th>

											<th width="100"><spring:message
													code="accounts.receipt.receipt_amount" /><span
												class="mand float-right">*</span> <i class="fa fa-inr"></i>

											</th>
											<c:choose>
												<c:when test="${form_mode eq 'create'}">
													<th><spring:message code="account.common.add.remove" /></th>
												</c:when>
											</c:choose>
										</tr>
										<c:choose>
											<c:when test="${form_mode eq 'create'}">
												<tr id="tr0" class="accountClass">


													<td><form:select id="budgetCode${0}"
															path="receiptFeeDetail[0].budgetCode.prBudgetCodeid"
															cssClass="form-control">
															<form:option value="">
																<spring:message code="account.common.select" />
															</form:option>
															<c:forEach items="${headCodeMap}" varStatus="status"
																var="budgetCode">
																<form:option value="${budgetCode.key}"
																	code="${budgetCode.key}">${budgetCode.value}</form:option>
															</c:forEach>
														</form:select></td>

													<td><form:input id="rfFeeamount${0}"
															name="rfFeeamount" path="receiptFeeDetail[0].rfFeeamount"
															class="form-control hasNumber" maxLength="15"
															onkeyup="totalReceiptamount(this)" /></td>
													<td><c:if test="${!viewMode}">
															<a data-toggle="tooltip" data-placement="top" title="Add"
																class="btn btn-success btn-sm addButton"
																id="addButton${count}"><i class="fa fa-plus-circle"></i></a>
															<a data-toggle="tooltip" data-placement="top"
																title="Delete" class="btn btn-danger btn-sm delButton"
																id="delButton${count}"><i class="fa fa-trash-o"></i></a>
														</c:if></td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${tbServiceReceiptMas.receiptFeeDetail}"
													var="details" varStatus="sts">
													<tr id="tr${status.count-1}" class="accountClass">

														<td><form:select id="budgetCode${sts.count-1}"
																path="receiptFeeDetail[${sts.count-1}].budgetCode.prBudgetCodeid"
																class="form-control mandColorClass chosen-select-no-results"
																disabled="true" onchange="">
																<form:option value="">
																	<spring:message code="account.common.select" />
																</form:option>
																<c:forEach items="${headCodeMap}" var="budgetCode">
																	<form:option value="${budgetCode.key}"
																		code="${budgetCode.key}">${budgetCode.value}</form:option>
																</c:forEach>
															</form:select></td>


														<td><form:input id="rfFeeamount${sts.count-1}"
																name="rfFeeamount"
																path="receiptFeeDetail[${sts.count-1}].rfFeeamount"
																class="form-control hasNumber" maxLength="15"
																onkeyup="totalReceiptamount(this)" disabled="true" /></td>

													</tr>
												</c:forEach>
											</c:otherwise>

										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>

				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse"
								href="#collection-collection-details1"><spring:message
									code="accounts.receipt.collection_mode_details"></spring:message>
							</a>
						</h4>
					</div>
					<div id="collection-collection-details1"
						class="panel-collapse collapse in">
						<div class="panel-body">
							<table class="table table-bordered">

								<tr id="tablePayModeHeading_cheqDD">
									<th width="15%" scope="col"><spring:message
											code="accounts.receipt.payment_mode"></spring:message> <span
										class="mand">*</span></th>
									<th width="15%" scope="col"><spring:message
											code="accounts.receipt.drawn_on"></spring:message></th>
									<th width="15%" scope="col"><spring:message
											code="accounts.receipt.cheque_dd_no_pay_order"></spring:message>
									</th>
									<th width="15%" scope="col"><spring:message
											code="accounts.receipt.cheque_dd_date"></spring:message></th>
									<th width="15%" scope="col" class="text-center"><spring:message
											code="accounts.receipt.mode_amount"></spring:message> <span
										class="mand">*</span> <i class="fa fa-inr"></i></th>
								</tr>


								<tr id="tablePayModeHeading_cash">
									<th width="15%" scope="col"><spring:message
											code="accounts.receipt.payment_mode"></spring:message> <span
										class="mand">*</span></th>


									<th width="15%" scope="col" class="text-center"><spring:message
											code="accounts.receipt.mode_amount"></spring:message> <span
										class="mand">*</span> <i class="fa fa-inr"></i></th>
								</tr>

								<tr id="tablePayModeHeading_nftwebrtgs">
									<th width="15%" scope="col"><spring:message
											code="accounts.receipt.payment_mode"></spring:message> <span
										class="mand">*</span></th>
									<th width="15%" scope="col"><spring:message
											code="accounts.receipt.baAccountid"></spring:message></th>
									<th width="15%" scope="col"><spring:message
											code="accounts.receipt.tranRefNumber1"></spring:message></th>
									<th width="15%" scope="col"><spring:message
											code="accounts.receipt.tranRefDate1">
										</spring:message></th>
									<th width="15%" scope="col" class="text-center"><spring:message
											code="accounts.receipt.mode_amount"></spring:message> <span
										class="mand">*</span> <i class="fa fa-inr"></i></th>
								</tr>
								<tr>
									<td><c:set var="baseLookupCode" value="PAY" /> <form:select
											path="receiptModeDetail.cpdFeemode" class="form-control"
											id="cpdFeemode" disabled="${view}"
											onchange="CashModeValidation()">
											<form:option value="">

												<spring:message code="accounts.receipt.select_mode"></spring:message>
											</form:option>
											<c:forEach items="${paymentMode}" varStatus="status"
												var="levelChild">
												<form:option value="${levelChild.lookUpId}"
													code="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
											</c:forEach>
										</form:select></td>


									<td id="tablePayModecbBankid"><form:select id="bankId"
											path="receiptModeDetail.cbBankid" cssClass="form-control">
											<form:option value="">
												<spring:message code="account.common.select" />
											</form:option>
											<c:forEach items="${customerBankList}" varStatus="status"
												var="cbBankid">
												<form:option value="${cbBankid.bankId}"
													code="${cbBankid.bankId}"> ${cbBankid.ifsc}-${cbBankid.bank}</form:option>
											</c:forEach>
										</form:select></td>



									<td id="rdchequeddnodata"><form:input id="rdchequeddno"
											path="receiptModeDetail.rdChequeddno" class="form-control"
											maxLength="16" /></td>

									<td id="rdchequedddatetempdata"><form:input
											id="rdchequedddatetemp"
											path="receiptModeDetail.rdChequedddatetemp"
											class="form-control cal datepicker " /></td>

									<td id="tableRecModecbBankid"><form:select
											id="baAccountId" path="receiptModeDetail.baAccountid"
											cssClass="form-control">
											<form:option value="">
												<spring:message code="account.common.select" />
											</form:option>
											<c:forEach items="${bankaccountlist}" varStatus="status"
												var="baAccountId">
												<form:option value="${baAccountId.baAccountId}"
													code="${baAccountId.baAccountId}">${baAccountId.baAccountNo}-${baAccountId.baAccountName}</form:option>
											</c:forEach>
										</form:select></td>




									<td id="tranRefNumber"><form:input id="tranRefNumber1"
											path="receiptModeDetail.tranRefNumber" class="form-control"
											maxLength="22" /></td>

									<td id="tranRefDate"><form:input id="tranRefDate1"
											path="receiptModeDetail.tranRefDatetemp"
											class="form-control cal datepicker " /></td>

									<td><form:input id="rdamount" name="rdamount"
											path="receiptModeDetail.rdAmount"
											class="hasMyNumber form-control" disabled="${viewMode}"
											readonly="true" /></td>
								</tr>
							</table>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="form-group">
						<div class="panel-body">

							<label class="label-control col-sm-2 required-control"> <spring:message
									code="accounts.receipt.receiptdelremark"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:textarea id="receiptDelRemark" path="receiptDelRemark"
									class="form-control" maxLength="500" />
							</div>

							<label class="label-control col-sm-2 required-control  ">
								<spring:message code="accounts.receipt.receiptdeldate"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="receiptDelDatetemp" path="receiptDelDatetemp"
									class="form-control form-control"
									value="<%=df.format(new java.util.Date())%>" readonly="true" />
							</div>
						</div>
					</div>
				</div>

			</div>

			<c:choose>
				<c:when test="${form_mode eq 'edit'}">
					<div class="text-center" id="divSubmit">
						<button type="button" class="btn btn-success btn-submit" id="submitEdit"
							onclick="editSaveDataReceipt(this)">
							<spring:message code="accounts.receipt.save"></spring:message>
						</button>
						<input type="button" class="btn btn-danger"
							onclick="javascript:openRelatedForm('AccountVoucherReversal.html');"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
					</div>

				</c:when>
				<c:otherwise>
					<div class="text-center" id="divSubmit">

						<input type="button" class="btn btn-danger"
							onclick="javascript:openRelatedForm('AccountVoucherReversal.html');"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
					</div>
				</c:otherwise>
			</c:choose>

		</form:form>

	</div>
</div>