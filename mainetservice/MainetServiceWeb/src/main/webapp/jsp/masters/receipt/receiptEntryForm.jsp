<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<script src="js/masters/receipt/receipt.js" type="text/javascript"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content form-div" id="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="collection.receipt.entry"
					text="Collection Receipts"></spring:message>
			</h2>
		       <div class="additional-btn">
				<apptags:helpDoc url="DepositReceiptEntry.html"></apptags:helpDoc>
			</div>
			
		</div>
		<div class="widget-content padding">
		<div class="mand-label clearfix">
				<span><spring:message code="common.sequenceconfig.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.sequenceconfig.mandatory" text="is mandatory" /> 
				</span>
			</div>
			<form:form action="DepositReceiptEntry.html" class="form-horizontal"
				modelAttribute="tbServiceReceiptMas" id="tbServiceReceiptMas"
				method="POST">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="" value="${form_mode}" id="form_mode" />
				<div id="receipt-details" class="panel-collapse collapse in">
					<form:hidden path="rmAmount" />
					<div class="panel-body">
						<c:choose>
							<c:when test="${form_mode eq 'view'}">
								<div class="form-group">
									<label class="control-label col-sm-2"><spring:message
											code="collection.receipt.number"></spring:message></label>
									<div class="col-sm-4">
										<form:input id="rmRcptno" path="rmReceiptNo"
											class="form-control" maxLength="12" readonly="true" />
									</div>
									<label class="control-label  col-sm-2 required-control"><spring:message
											code="collection.receipt.date"></spring:message></label>
									<div class="col-sm-4">
										<form:input id="rmDatetemp" path="rmDate" class="form-control"
											readonly="true" />
									</div>


								</div>

							</c:when>
						</c:choose>
						<c:if test="${form_mode eq 'create'}">
							<div class="form-group">
								<label for="transactionDateId"
									class="col-sm-2 control-label required-control"><spring:message
										code="collection.receipt.date" text="Receipt Date" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<c:set var="now" value="<%=new java.util.Date()%>" />
										<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
										<form:input path="transactionDate" id="transactionDateId"
											cssClass="mandColorClass form-control" value="${date}"
											maxLength="10"></form:input>
										<label class="input-group-addon mandColorClass"
											for="transactionDateId"><i class="fa fa-calendar"></i>
										</label>
									</div>
								</div>
								<label class="control-label  col-sm-2 required-control">
									<spring:message code="collection.receipt.department"
										text="Department"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:hidden path="dpDeptId" />
									<form:input path="deptName" readonly="true"
										cssClass=" form-control"></form:input>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<label class="control-label col-sm-2 required-control"><spring:message
									code="collection.receipt.category" text="Receipt Category" /></label>
							<div class="col-sm-4">
								<form:select path="recCategoryTypeId"
									class="form-control mandColorClass chosen-select-no-results"
									id="receiptCategoryId"
									disabled="${form_mode ne 'create'? true:false}"
									data-rule-required="true"
									onchange="getReceiptCategoryType(this);">
									<form:option value="">
										<spring:message code="collection.receipt.select" text="Select" />
									</form:option>
									<c:forEach items="${recieptVouType}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>
							<!-- </div> -->
							<label class="control-label  col-sm-2 required-control">
								<spring:message code="collection.receipt.receivedfrom"></spring:message>
							</label>
							<div class="col-sm-4">
								<c:if test="${form_mode eq 'create'}">
									<form:select id="vm_VendorId" path="vmVendorId"
										class="form-control mandColorClass chosen-select-no-results"
										onchange="setVendorName(this)">
										<form:option value="">
											<spring:message code="collection.receipt.select"></spring:message>
										</form:option>
										<c:forEach items="${list}" var="vendorData">
											<form:option value="${vendorData.vmVendorid}">${vendorData.vmVendorcode} - ${vendorData.vmVendorname}</form:option>
										</c:forEach>
									</form:select>
								</c:if>
								<c:if test="${form_mode ne 'create'}">
									<form:input id="VmVendorIdDesc" path="VmVendorIdDesc"
										class="form-control" maxLength="200" disabled="true" />
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label  col-sm-2 required-control">
								<spring:message code="receipt.reference.no" text=""></spring:message>
							</label>
							<div class="col-sm-4">
								<c:if test="${form_mode eq 'create'}">
									<form:input id="apmApplicationId" path="additionalRefNo"
										class="form-control hasNumber" maxLength="200" />
								</c:if>
								<c:if test="${form_mode ne 'create'}">
									<form:input id="apmApplicationId" path="additionalRefNo"
										class="form-control hasNumber" maxLength="200" readonly="true" />
								</c:if>
							</div>
							<c:if test="${form_mode eq 'create'}">
								<label class="control-label  col-sm-2  required-control ">
									<spring:message code="collection.receipt.payeename"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="rm_Receivedfrom"
										onchange="enableMobileAndEmail()" path="rmReceivedfrom"
										class="form-control hasCharacter" maxLength="200" />
								</div>
							</c:if>
							<c:if test="${form_mode ne 'create'}">
								<label class="control-label  col-sm-2  required-control ">
									<spring:message code="collection.receipt.payeename"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="rm_Receivedfrom" path="rmReceivedfrom"
										class="form-control hasCharacter" maxLength="200" disabled="true" />
								</div>
							</c:if>
						</div>
						<div class="form-group">
							<c:if test="${form_mode eq 'create'}">
								<label class="control-label  col-sm-2 "> <spring:message
										code="collection.receipt.mobileno"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="mobile_Number" path="mobileNumber"
										class="form-control hasNumber" maxLength="10" readonly="" />
								</div>
							</c:if>
							<c:if test="${form_mode ne 'create'}">
								<label class="control-label  col-sm-2 "> <spring:message
										code="collection.receipt.mobileno"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="mobile_Number" path="mobileNumber"
										class="form-control hasNumber" maxLength="10" disabled="true" />
								</div>
							</c:if>
							<c:if test="${form_mode eq 'create'}">
								<label class="control-label  col-sm-2 "> <spring:message
										code="collection.receipt.email"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="email_Id" path="emailId"
										class="form-control text-lowercase" maxLength="200"
										readonly="" />
								</div>
							</c:if>
							<c:if test="${form_mode ne 'create'}">
								<label class="control-label  col-sm-2 "> <spring:message
										code="collection.receipt.email"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="email_Id" path="emailId"
										class="form-control text-lowercase" maxLength="200"
										disabled="true" />
								</div>
							</c:if>
						</div>
						<div class="form-group">
							<c:if test="${form_mode eq 'create'}">
								<label class="control-label  col-sm-2"> <spring:message
										code="collection.receipt.manualreceiptno"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="manual_ReceiptNo" path="manualReceiptNo"
										class="form-control" maxLength="50" />
								</div>
							</c:if>
							<c:if test="${form_mode ne 'create'}">
								<label class="control-label  col-sm-2"> <spring:message
										code="collection.receipt.manualreceiptno"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:input id="manual_ReceiptNo" path="manualReceiptNo"
										class="form-control" maxLength="50" disabled="true" />
								</div>
							</c:if>
							<c:if test="${form_mode eq 'create'}">
								<label class="control-label  col-sm-2 required-control">
									<spring:message code="collection.receipt.narration"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:textarea id="rmNarration" path="rmNarration"
										class="form-control " maxLength="200" />
								</div>
							</c:if>
							<c:if test="${form_mode ne 'create'}">
								<label class="control-label  col-sm-2 required-control">
									<spring:message code="collection.receipt.narration"></spring:message>
								</label>
								<div class="col-sm-4">
									<form:textarea id="rmNarration" path="rmNarration"
										class="form-control " maxLength="200" disabled="true" />
								</div>
							</c:if>
						</div>
					</div>
				</div>
				<h4>
					<spring:message code="collection.receipt.receiptcollectiondetails"></spring:message>
				</h4>
				<div id="receipt-collection-details"
					class="panel-collapse collapse in" style="overflow: visible;">
					<div class="panel-body">
						<div class="" id="taxHeadsTable">
							<table
								class="table table-bordered table-striped appendableClass "
								id="receiptAccountHeadsTable">
								<tr>
									<th width="70%"><spring:message
											code="collection.receipt.receipthead" text="Receipt Head" />
										<c:choose>
											<c:when test="${form_mode eq 'create'}">
												<span class="mand float-right">*</span>

											</c:when>
										</c:choose></th>
									<th width="20%"><spring:message
											code="collection.receipt.amount" /> <c:choose>
											<c:when test="${form_mode eq 'create'}">
												<span class="mand float-right">*</span>

											</c:when>
										</c:choose> <i class="fa fa-inr"></i></th>

									<th scope="col" width="12%"><spring:message
											code="bill.action" text="Action" /></th>

								</tr>
								<tbody>
									<c:choose>
										<c:when test="${form_mode eq 'create'}">
											<c:forEach items="${tbServiceReceiptMas.receiptFeeDetail}"
												var="details" varStatus="sts">
												<tr id="tr${sts.index}" class="accountClass">
													<td><form:select id="budgetCode${sts.index}"
															path="receiptFeeDetail[${sts.index}].sacHeadId"
															class="form-control mandColorClass chosen-select-no-results"
															disabled="${viewMode}" onchange="validateDedAccountHead(${sts.index})">
															<form:option value="">
																<spring:message code="collection.receipt.select" />
															</form:option>
															<c:forEach items="${headCodeMap}" varStatus="status"
																var="budgetCode">

																<form:option value="${budgetCode.key}"
																	code="${budgetCode.key}">${budgetCode.value}
															</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:input id="rfFeeamount${sts.index}"
															name="rfFeeamount"
															path="receiptFeeDetail[${sts.index}].rfFeeamount"
															class="form-control mandColorClass text-right"
															onkeyup="totalReceiptamount()"
															onchange="getAmountFormatInDynamic((this),'rfFeeamount')"
															onkeypress="return hasAmount(event, this, 12, 2)" /></td>

													<td class="text-center"><a data-placement="top"
														title="Add" class="btn btn-success btn-sm addDedButton"
														id="addDedButton${sts.index}"><i class="fa fa-plus-circle"></i></a>
														<a data-placement="top" title="Delete"
														class="btn btn-danger btn-sm delDedButton"
														id="delDedButton${sts.index}"><i class="fa fa-trash-o"></i></a>

													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach items="${tbServiceReceiptMas.receiptFeeDetail}"
												var="details" varStatus="sts">
												<tr id="tr${status.count-1}" class="accountClass">
													<td><form:input id="budgetCode${sts.count-1}"
															name="receiptFeeDetail[${sts.count-1}].sacHeadId"
															path="receiptFeeDetail[${sts.count-1}].acHeadCode"
															class="form-control " disabled="true"
															cssClass="form-control" /></td>
													<td><fmt:formatNumber type="number"
															value="${details.rfFeeamount}" groupingUsed="false"
															var="famt" pattern="#,##,##,##,##0.00"
															maxIntegerDigits="15" maxFractionDigits="2" /> <form:input
															id="rfFeeamount${sts.count-1}" name="rfFeeamount" path=""
															value="${famt}" class="form-control hasAmount"
															maxLength="15" onkeyup="totalReceiptamount(this)"
															disabled="true" cssClass="form-control text-right"
															onkeypress="return hasAmount(event, this, 12, 2)" /></td>
													
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<h4>
					<spring:message code="collection.receipt.modedetails"></spring:message>
				</h4>
				<div id="collection-collection-details1"
					class="panel-collapse collapse in">
					<div class="panel-body">
						<table class="table table-bordered">
							<tr id="tablePayModeHeading_cheqDD">
								<th width="15%" scope="col"><spring:message
										code="collection.receipt.Mode"></spring:message> <c:choose>
										<c:when test="${form_mode eq 'create'}">
											<span class="mand">*</span>
										</c:when>
									</c:choose></th>
								<c:if
									test="${tbServiceReceiptMas.receiptModeDetailList.cpdFeemodeCode ne 'C'}">
									<th width="30%" scope="col"><spring:message
											code="collection.receipt.drawn_on"></spring:message> <c:if
											test="${form_mode eq 'create'}">
											<span class="mand">*</span>
										</c:if></th>

									<th width="15%" scope="col"><spring:message code="collection.receipt.baAccountNo"
											text="Account Number"></spring:message><c:if
											test="${form_mode eq 'create'}">
										</c:if></th>
									<th width="15%" scope="col"><spring:message
											code="collection.receipt.cheque_dd_no_pay_order"></spring:message>
										<span class="mand">*</span></th>
									<th width="15%" scope="col"><spring:message
											code="collection.receipt.cheque_dd_date"></spring:message> <span
										class="mand">*</span></th>
								</c:if>
								<th width="15%" scope="col" class="text-center"><spring:message
										code="collection.receipt.mode_amount"></spring:message> <i
									class="fa fa-inr"></i></th>
							</tr>
							<tr id="tablePayModeHeading_cash">
								<th width="15%" scope="col"><spring:message
										code="collection.receipt.Mode"></spring:message> <c:choose>
										<c:when test="${form_mode eq 'create'}">
											<span class="mand">*</span>
										</c:when>
									</c:choose></th>
								<th width="15%" scope="col" class="text-center"><spring:message
										code="collection.receipt.mode_amount"></spring:message> <i
									class="fa fa-inr"></i></th>
							</tr>
							<tr id="tablePayModeHeading_nftwebrtgs">
								<th width="15%" scope="col"><spring:message
										code="collection.receipt.Mode"></spring:message> <c:if
										test="${form_mode eq 'create'}">
										<span class="mand">*</span>
									</c:if></th>

								<th width="40%" scope="col"><spring:message
										code="collection.receipt.baAccountid"></spring:message> <c:if
										test="${form_mode eq 'create'}">
										<span class="mand">*</span>
									</c:if></th>

								<th width="15%" scope="col"><spring:message
										code="collection.receipt.tranRefNumber1"></spring:message> <c:if
										test="${form_mode eq 'create'}">
										<span class="mand">*</span>
									</c:if></th>
								<th width="15%" scope="col"><spring:message
										code="collection.receipt.tranRefDate1">
									</spring:message> <c:if test="${form_mode eq 'create'}">
										<span class="mand">*</span>
									</c:if></th>
								<th width="15%" scope="col" class="text-center"><spring:message
										code="collection.receipt.mode_amount"></spring:message> <i
									class="fa fa-inr"></i></th>
							</tr>
							<tr>
								<td><c:choose>
										<c:when test="${form_mode eq 'create'}">
											<c:set var="baseLookupCode" value="PAY" />
											<form:select path="receiptModeDetailList.cpdFeemode"
												class="form-control mandColorClass  required-control"
												id="cpdFeemode" disabled="${view}"
												onchange="CashModeValidation()">
												<form:option value="">
													<spring:message code="collection.receipt.select_mode"></spring:message>
												</form:option>
												<c:forEach items="${paymentMode}" varStatus="status"
													var="levelChild">
													<form:option value="${levelChild.lookUpId}"
														code="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</c:when>
										<c:otherwise>
											<form:input id="cpdFeemodeCode"
												path="receiptModeDetailList.cpdFeemodeCode" type="hidden" />

											<form:input id="cpdFeemodeDesc"
												path="receiptModeDetailList.cpdFeemodeDesc" class="form-control"
												disabled="true" />
										</c:otherwise>
									</c:choose></td>
								<c:if
									test="${tbServiceReceiptMas.receiptModeDetailList.cpdFeemodeCode ne 'C'}">
									<td id="tablePayModecbBankid"><c:choose>
											<c:when test="${form_mode eq 'create'}">
												<form:select id="bankId" path="receiptModeDetailList.cbBankid"
													class="form-control chosen-select-no-results">
													<form:option value="">
														<spring:message code="collection.receipt.select" />
													</form:option>
													<c:forEach items="${customerBankList}" varStatus="status"
														var="cbBankid">
														<form:option value="${cbBankid.bankId}"
															code="${cbBankid.bankId}">${cbBankid.bank} - ${cbBankid.branch} - ${cbBankid.ifsc}</form:option>
													</c:forEach>
												</form:select>
											</c:when>
											<c:otherwise>
												<form:select id="bankId" path="receiptModeDetailList.cbBankid"
													class="form-control chosen-select-no-results"
													disabled="true">
													<form:option value="">
														<spring:message code="collection.receipt.select" />
													</form:option>
													<c:forEach items="${customerBankList}" varStatus="status"
														var="cbBankid">
														<form:option value="${cbBankid.bankId}"
															code="${cbBankid.bankId}">${cbBankid.bank} - ${cbBankid.branch} - ${cbBankid.ifsc}</form:option>
													</c:forEach>
												</form:select>
											</c:otherwise>
										</c:choose></td>

									<td id="bankAccountid"><c:choose>
											<c:when test="${form_mode eq 'create'}">
												<form:input id="accountNumber"
													path="receiptModeDetailList.accountNumber"
													class="form-control hasNumber mandColorClass"
													maxLength="16" />
											</c:when>
											<c:otherwise>
												<form:input id="accountNumber"
													path="receiptModeDetailList.accountNumber"
													class="form-control hasNumber" maxLength="16" />
											</c:otherwise>
										</c:choose></td>
									<td id="rdchequeddnodata"><c:choose>
											<c:when test="${form_mode eq 'create'}">
												<form:input id="rdchequeddno"
													path="receiptModeDetailList.rdChequeddno"
													class="form-control mandColorClass hasNumber" maxLength="8" />
											</c:when>
											<c:otherwise>
												<form:input id="rdchequeddno"
													path="receiptModeDetailList.tranRefNumber"
													class="form-control hasNumber" maxLength="8"
													disabled="true" />
											</c:otherwise>
										</c:choose></td>
								</c:if>
								<c:if test="${form_mode eq 'create'}">
									<td id="rdchequedddatetempdata">
										<div class="input-group">
											<form:input id="rdchequedddatetemp"
												path="receiptModeDetailList.rdChequedddatetemp"
												class="form-control datepicker mandColorClass"
												onchange="validateChequeDate()" maxlength="10" />
											<label class="input-group-addon" for="rdchequedddate"><i
												class="fa fa-calendar"></i> <input type="hidden"
												id="rdchequedddate"> </label>
										</div>
									</td>
								</c:if>
								<c:if
									test="${tbServiceReceiptMas.receiptModeDetailList.cpdFeemodeCode ne 'C'}">
									<c:if test="${form_mode ne 'create'}">
										<td id="rdChequedddatetemp">
											<div class="input-group">
												<form:input id="rdChequedddatetemp"
													path="receiptModeDetailList.rdChequedddatetemp"
													class="form-control datepicker" disabled="true" />
												<label class="input-group-addon" for="rdChequedddatetemp"><i
													class="fa fa-calendar"></i> <input type="hidden"
													id="rdChequedddatetemp"> </label>
											</div>
										</td>
									</c:if>
								</c:if>
								<td id="tableRecModecbBankid"><c:choose>
										<c:when test="${form_mode eq 'create'}">
											<form:select id="baAccountId"
												path="receiptModeDetailList.baAccountid"
												class="form-control chosen-select-no-results">
												<form:option value="">
													<spring:message code="collection.receipt.select" />
												</form:option>
												<c:forEach items="${bankAccountMap}" varStatus="status"
													var="baAccountId">
													<form:option value="${baAccountId.key}"
														code="${baAccountId.key}">${baAccountId.value}</form:option>
												</c:forEach>
											</form:select>
										</c:when>
										<c:otherwise>

											<form:select id="baAccountId"
												path="receiptModeDetailList.baAccountid"
												class="form-control chosen-select-no-results"
												disabled="true">
												<form:option value="">
													<spring:message code="collection.receipt.select" />
												</form:option>
												<c:forEach items="${bankAccountMap}" varStatus="status"
													var="baAccountId">
													<form:option value="${baAccountId.key}"
														code="${baAccountId.key}">${baAccountId.value}</form:option>
												</c:forEach>
											</form:select>

										</c:otherwise>
									</c:choose></td>

								<td id="tranRefNumber"><c:choose>
										<c:when test="${form_mode eq 'create'}">
											<form:input id="tranRefNumber1"
												path="receiptModeDetailList.tranRefNumber"
												class="form-control mandColorClass" maxLength="44" />

										</c:when>
										<c:otherwise>
											<form:input id="tranRefNumber1"
												path="receiptModeDetailList.tranRefNumber" class="form-control"
												maxLength="44" />
										</c:otherwise>
									</c:choose></td>

								<td id="tranRefDate"><c:choose>
										<c:when test="${form_mode eq 'create'}">
											<div class="input-group">
												<form:input id="tranRefDate1"
													path="receiptModeDetailList.tranRefDatetemp"
													class="form-control mandColorClass datepicker"
													maxlength="10" />
												<label class="input-group-addon" for="tranRefDate1"><i
													class="fa fa-calendar"></i> <input type="hidden"
													id="tranRefDateDt"> </label>
											</div>

										</c:when>
										<c:otherwise>
											<div class="input-group">
												<form:input id="tranRefDate1"
													path="receiptModeDetailList.tranRefDatetemp"
													class="form-control datepicker" readonly="true"
													maxLength="10" />
												<label class="input-group-addon" for="tranRefDateDtv"><i
													class="fa fa-calendar"></i> <input type="hidden"
													id="tranRefDateDtv"> </label>
											</div>
										</c:otherwise>

									</c:choose></td>

								<td><fmt:setLocale value="en_IN" /> <fmt:formatNumber
										type="currency"
										value="${tbServiceReceiptMas.receiptModeDetailList.rdAmount}"
										pattern="#,##,##,##,##0.00" groupingUsed="false" var="famt"
										maxIntegerDigits="15" maxFractionDigits="2" /> <form:input
										id="rdamount" name="rdamount"
										path="receiptModeDetailList.rdAmount" value="${famt}"
										class="form-control text-right " disabled="${viewMode}"
										readonly="true"
										onkeypress="return hasAmount(event, this, 12, 2)" /></td>
							</tr>
						</table>
					</div>
				</div>

				<c:choose>
					<c:when test="${form_mode eq 'create'}">
						<div class="text-center" id="divSubmit">
							<button type="button" class="btn btn-submit" id="submit"
								onclick="saveDataReceipt(this)">
								<i class="fa fa-sign-out padding-right-5"></i>
								<spring:message code="collection.receipt.save"></spring:message>
							</button>

							<button type="Reset" class="btn btn-warning"
								onclick="createData();">
								<spring:message code="collection.receipt.reset" text="Reset" />
							</button>
							<apptags:backButton url="DepositReceiptEntry.html" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="text-center" id="divSubmit">
							<c:if test="${form_mode ne 'view'}">
								<button type="button" class="btn btn-success" id="submitEdit"
									onclick="saveDataReceipt(this)">
									<spring:message code="collection.receipt.save"></spring:message>
								</button>
							</c:if>
							<apptags:backButton url="DepositReceiptEntry.html" />
						</div>
					</c:otherwise>
				</c:choose>
				<form:hidden path="" id="feeModeStatus" />
			</form:form>
		</div>
	</div>
</div>