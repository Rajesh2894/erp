<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="js/account/miscDemandCollection.js"></script>
<script src="js/cfc/challan/offlinePay.js"></script>
<script src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="misc.demand.collection.heading" text="Misc Demand Collection" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="MiscDemandCollection.html" commandName="command" class="form-horizontal form" name="MiscDemandCollection" id="id_MiscDemandCollection">
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->

				<div class="form-group refStatus">
					<label class="col-sm-2 control-label " for=""><spring:message code="receivable.demand.entry.ccn.idn" text="IDN/CCN Number" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" id="refNumber" readonly="" disabled="" maxlength="12" />
					</div>
					<label class="col-sm-2 control-label " for=""><spring:message code="misc.bill.no" text="Supplementary Bill No." /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" id="billNo" readonly="" disabled="" maxlength="15" />
					</div>

				</div>
				<c:if test="${command.processUpdate ne 'Y' }">
					<div class="text-center padding-bottom-10" id="id_search">
						<button type="button" class="btn btn-success searchData" onclick="searchBillNo('MiscDemandCollection.html','searchConnection')">
							<i class="fa fa-search"></i>
							<spring:message code="master.search" text="Search" />
						</button>
						<button type="button" class="btn btn-info" onclick="window.location.href='BillPayment.html'">Back</button>

					</div>
				</c:if>

				<div class="panel-group accordion-toggle" id="accordion_single_collapse">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-target="#a1" data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#collapse1"> <spring:message code="receivable.demand.entry.consumer.details"
									text="Consumer Details"
								/>
							</a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group padding-top-20">
								<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.custName" text="Consumer Name" /></label>
								<div class="col-sm-4">

									<form:input path="receivableDemandEntryDto.customerDetails.fName" onchange="" readonly="" cssClass="form-control  mandColorClass required-control" autocomplete="off" id="custName"
										disabled="true"
									/>
								</div>
								<label class="col-sm-2 control-label required-control" for=""><spring:message code="receivable.demand.entry.custAdd" text="Consumer Address" /></label>
								<div class="col-sm-4">

									<form:textarea path="receivableDemandEntryDto.customerDetails.areaName" cssClass="form-control  required-control text-left" id="custAddress" readonly="true" disabled="" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label " for=""><spring:message code="receivable.demand.entry.loc" text="Location" /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.houseComplexName" onchange="" readonly="true" cssClass="form-control  mandColorClass " autocomplete="off" id="custLocation"
										disabled=""
									/>
								</div>
								<label class="col-sm-2 control-label " for=""><spring:message code="receivable.demand.entry.pincode" text="PinCode" /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.pincodeNo" onchange="" readonly="true" cssClass="form-control  mandColorClass " autocomplete="off" id="pinCode" disabled="" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label" for=""><spring:message code="receivable.demand.entry.mobNo" text="Mobile No." /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.mobileNo" cssClass="form-control text-left hasMobileNo" data-rule-number="10" data-rule-minLength="10" data-rule-maxLength="10"
										id="custMobile" readonly="true" disabled=""
									/>
								</div>
								<label class="col-sm-2 control-label" for=""><spring:message code="receivable.demand.entry.emailid" text="Email-ID" /></label>
								<div class="col-sm-4">
									<form:input path="receivableDemandEntryDto.customerDetails.email" cssClass="form-control hasemailclass text-left" id="custEmailId" readonly="true" disabled="" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<c:set var="d" value="0" scope="page" />

				<div class="table-responsive clear">
					<table class="table table-bordered table-striped" id="id_miscDemandTbl">
						<thead>
							<tr>
								<th colspan="${5}" class="text-center" rowspan="1"><spring:message code="" text="Supplementary Bill Details" /></th>
							</tr>
							<tr>
								<th scope="col" width="3%"><spring:message code="" text="Sr.No." /><input type="hidden" id="srNo"></th>
								<th scope="col" width="10%"><spring:message code="misc.bill.no" text="Supplementary Bill Number" /></th>
								<th scope="col" width="10%"><spring:message code="misc.bill.gen.date" text="Supplementary Bill Generation Date" /></th>
								<th scope="col" width="10%"><spring:message code="receivable.demand.entry.amnt" text="Amount" /></th>
								<th scope="col" width="10%"><spring:message code="water.select" text="Select" /></th>
							</tr>

						</thead>
						<tfoot>
							<tr>
								<th></th>
								<th colspan="1" class="text-center" rowspan="1"></th>
								<th align="left"><spring:message code="receivable.demand.entry.total.amnt" text="Total Amount" /></th>
								<th><form:input cssClass="form-control text-right mandColorClass required-control" disabled="true" path="" id="totalAmount"></form:input></th>
								<th></th>
							</tr>
						</tfoot>
						<tbody>
							<c:forEach var="billList" items="${command.receivableDemandEntryDtosList}" varStatus="bill">
								<tr>
									<td align="center" width="2%"><form:input path="" cssClass=" text-center form-control mandColorClass " id="sequence${d}" value="${d+1}" disabled="true" /></td>
									<td align="center">${billList.billNo}</td>
									<td align="center"><fmt:formatDate type="date" value="${billList.createdDate}" pattern="dd-MM-yyyy" /></td>
									<fmt:formatNumber var="billamnt" value="${billList.billAmount}" pattern="#"></fmt:formatNumber>
									<td align="right" id="billamnt${d}">${billamnt}</td>
									<td align="center"><form:checkbox path="receivableDemandEntryDtosList[${bill.index}].checkBillPay" id="checkBillPay${d}" value="true" cssClass="chkbox${bill.count}" onchange="sum1()" /></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>

				</div>

				<div class="form-group padding-top-20">
					<label class="col-sm-2 control-label" for=""><spring:message code="misc.demand.collection.total.receivable.amt" text="Total Receivable Amount" /></label>
					<div class="col-sm-4" align="left">

						<form:input cssClass="form-control text-right mandColorClass required-control" disabled="true" path="" id="receivableAmount"></form:input>


					</div>
					<label class="col-sm-2 control-label" for=""><spring:message code="misc.demand.collection.receipt.amt" text="Enter Receipt Amount" /></label>
					<div class="col-sm-4">
						<form:input path="receivableDemandEntryDto.receivedAmount" cssClass="form-control  text-right hasNumber" onchange="amountPay()" id="receivedAmount" readonly="true" />

					</div>
				</div>


				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Payment_mode"> <spring:message code="rnl.book.payment" text="Payment Mode"></spring:message>
							</a>
						</h4>
					</div>
					<div id="Payment_mode" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="offlinepayment" id="offlinepayment">

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message code="rti.offlinePaymentSelection" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="OFL" />
										<form:select path="offlineDTO.oflPaymentMode" cssClass="form-control" id="oflPaymentMode">
											<c:forEach items="${command.getLevelData(baseLookupCode)}" var="oflMode">
												<form:option code="${oflMode.lookUpCode}" value="${oflMode.lookUpId}">${oflMode.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>


							<div class="form-group">
								<label class="control-label col-sm-2"><spring:message code="rti.selectPaymrntMode" /><span class="mand">*</span></label>
								<div class="radio col-sm-8">
									<c:if test="${command.getLoggedInUserType() eq 'CFC' ||command.getLoggedInUserType() eq ''}">
										<label> <form:radiobutton path="offlineDTO.onlineOfflineCheck" value="P" id="payAtCounter" onclick="showDiv(this);" checked="checked" /> <spring:message code="rti.payAtCounter" />
										</label>
									</c:if>
								</div>

							</div>
							<div class="PPO" id="PPO">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message code="payment.freeMode" /></label>
									<apptags:lookupField items="${command.userSession.paymentMode}" path="offlineDTO.payModeIn" cssClass="form-control" changeHandler="enableDisableCollectionModes(this)"
										selectOptionLabelCode="rti.sel.paymentmode" hasId="true" isMandatory="true"
									>
									</apptags:lookupField>
								</div>
							</div>


							<div class="overflow CPAUC">
								<h4>
									<spring:message code="payment.header.name" />
								</h4>

								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message code="payment.accountNo" /></label>
									<div class="col-sm-4">
										<form:input path="offlineDTO.bmBankAccountId" class="form-control hasNumber" id="acNo" maxlength="16" />
									</div>


									<label class="col-sm-2 control-label required-control" id="selectType"><spring:message code="payment.checkOrDDNo" /></label>
									<div class="col-sm-4">
										<form:input path="offlineDTO.bmChqDDNo" class="form-control hasNumber" id="chqNo" maxlength="6" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control" id="selectDate"><spring:message code="payment.checkOrDDDate" /></label>
									<div class="col-sm-4">
										<apptags:dateField fieldclass="lessthancurrdate chqDate" datePath="offlineDTO.bmChqDDDate" cssClass="form-control" />
									</div>

									<label class="col-sm-2 control-label required-control"><spring:message code="" text="MICR Code" /></label>
									<div class="col-sm-4">
										<form:input path="offlineDTO.micrCode" class="form-control hasNumber" id="micrCode" maxlength="9" />
									</div>
								</div>

								<input type="hidden" value="<spring:message code="pay.option.poNo" />" id="PO" /> <input type="hidden" value="<spring:message code="pay.option.ddNo" />" id="DD" /> <input type="hidden"
									value="<spring:message code="pay.option.account" />" id="CQ"
								/> <input type="hidden" value="<spring:message code="pay.option.poNoDate" />" id="POD" /> <input type="hidden" value="<spring:message code="pay.option.ddNoDate" />" id="DDD" /> <input
									type="hidden" value="<spring:message code="pay.option.accountDate" />" id="CQD"
								/>
							</div>


						</div>
					</div>
				</div>
				<div class="text-center padding-top-20" id="divSubmit">
					<button type="button" class="btn btn-success btn-submit" id="submit" onclick="Proceed(this)">
						<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
						<spring:message code="master.save" text="Save" />
					</button>
					<c:if test="${command.processUpdate ne 'Y' }">
						<button type="reset" class="btn btn-warning " title="Reset" onclick="javascript:openRelatedForm('MiscDemandCollection.html');">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="water.btn.reset" text="Reset" />
						</button>
					</c:if>
					<c:if test="${command.processUpdate eq 'Y' }">
						<button type="button" class="btn btn-success btn-submit" id="back" onclick="window.location.href='AdminHome.html'">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
							<spring:message code="" text="Back" />
						</button>
					</c:if>
				</div>
			</form:form>
		</div>

	</div>
</div>