<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<script type="text/javascript"
	src="js/rnl/service/estateContractBillPayment.js"></script>
	
<style>
.widget-content.padding {
	overflow: unset;
}
#contractBillPayment > .form-group {
	margin: 0 -0.6rem;
}
#contractBillPayment .collapse {
	margin: 0;
}
</style>
	
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.master.bill.coll" text="Bill Collection"></spring:message>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help" tabindex="-1"><i
					class="fa fa-question-circle fa-lg" ></i></a>
			</div>
		</div>

		<div class="widget-content padding">
			<form:form action="EstateContractBillPayment.html" method="post"
				class="form-horizontal" name="contractBillPayment"
				id="contractBillPayment">
				<div class="error-div alert alert-danger alert-dismissible">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<!-- <div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div> -->
				</div>
				<spring:message code="rnl.enter.amt" text="Enter Amt" var="enterAmount" />

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="rnl.demand.reg.contractNo" text="Contract Number"></spring:message></label>
					<div class="col-sm-4">
						<form:select id="contractNo"
							cssClass="form-control chosen-select-no-results"
							class="form-control required-control" path="contractNo" onchange="getPropertyDetail(this)">
							<form:option value="0"><spring:message code="rnl.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.contractAgreementList}"
								var="contract">
								<form:option value="${contract.contNo}">${contract.contNo} - ${contract.contp2Name}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="rnl.property.number" text="Property Number" /></label>
					<div class="col-sm-4">
						<form:select path="propertyContractNo" id="propertyNo"
							class="form-control chosen-select-no-results" disabled="false">
							<option value="0"><spring:message code="rnl.master.select" text="Select" /></option>
							<c:forEach items="${command.propertyDetails}" var="objArray">
								<form:option value="${objArray[1]}" code="${objArray[0]}">${objArray[3]} - ${objArray[4]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-info" id="btnsearch" title='<spring:message code="rnl.master.search" text="Search"></spring:message>'>
						<i class="fa fa-search"></i>
						<spring:message code="rnl.master.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						title='<spring:message code="rnl.master.reset" text="Reset"></spring:message>'
						onclick="resetbillPayment(this)">
						<spring:message code="rnl.master.reset" text="Reset"></spring:message>
					</button>
				</div>
				
				<c:if test="${command.formFlag eq 'Y'}">
				
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#first" tabindex="-1"><spring:message
										code="rnl.master.contractHeader" text="Contract Details" /></a>
							</h4>
						</div>
						<div id="first" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label class="control-label col-sm-2" for="ContractDate"><spring:message
											code="rnl.master.contract.date" text="Contract Date" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contDate"
											class="form-control" readonly="true" tabindex="-1"></form:input>

									</div>
									<label class="control-label col-sm-2" for="Department"><spring:message
											code="master.complaint.department" text="Department" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contDept"
											class="form-control" readonly="true" tabindex="-1"></form:input>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2" for="ContractDate"><spring:message
											code="rnl.master.represented.by" text="Represented By" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contp1Name"
											class="form-control" readonly="true" tabindex="-1"></form:input>
									</div>
									<label class="control-label col-sm-2" for="Department"><spring:message
											code="rnl.master.vender.name" text="Vendor Name" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contp2Name"
											class="form-control" readonly="true" tabindex="-1"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for="ContractDate"><spring:message
											code="rnl.master.contract.from.date"
											text="Contract From Date" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contFromDate"
											class="form-control" readonly="true" tabindex="-1"></form:input>
									</div>
									<label class="control-label col-sm-2" for="Department"><spring:message
											code="rnl.master.contract.to.date" text="Contract To Date" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contToDate"
											class="form-control" readonly="true" tabindex="-1"></form:input>
									</div>
								</div>


							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#second" tabindex="-1"><spring:message
										code="rnl.master.receipt.amt.det"
										text="Receipt Amount Details" /></a>
							</h4>
						</div>
						<div id="second" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="table-responsive">
								<!-- Code Comment on 18-Oct ISRAT behalf of SAMADHAN SIR-->
									<%-- <table class="table table-bordered table-condensed">

										<tr>
											<th><spring:message code="rnl.master.tax.desc"
													text="Tax Description" /></th>
											<th><spring:message code="rnl.master.balance.interest"
													text="Balance Arrears with interest" /></th>
											<th><spring:message code="rnl.master.current.tax"
													text="Current  Tax" /></th>
											<th><p class="text-right">
													<spring:message code="rnl.master.current.bal.tax"
														text="Current Balance Tax" />
												</p></th>
											<th><p class="text-right">
													<spring:message code="rnl.master.total.bal"
														text="Total Balance" />
												</p></th>
										</tr>

										<tr>
											<td>${command.taxDesc}</td>
											<td><p class="text-right">${command.billMas.bmTotalArrears}</p></td>
											<td><p class="text-right">${command.bmTotalAmount}</p></td>
											<td><p class="text-right">${command.bmTotalBalAmount}</p></td>
											<td><p class="text-right">${command.billMas.bmTotalArrears + command.bmTotalAmount}</p></td>
											
										</tr>
									</table> --%>
									<table class="table table-bordered table-condensed">

										<tr>
											<th><spring:message code="rnl.master.agreement.amount"
													text="Agreement Amount" /></th>
											<th><spring:message code="rnl.master.balance.amount"
													text="Balance Amount" /></th>
											<th><spring:message code="rnl.master.overdue.amount"
													text="Overdue Amount" /></th>
											<th><spring:message code="rnl.master.payment.amount"
													text="Payment Amount" /></th>
										</tr>
										<tr>
											<td><p class="text-center">${command.contractAgreementSummaryDTO.contAmount}</p></td>
											<td><p class="text-center">${command.contractAgreementSummaryDTO.balanceAmount}</p></td>
											<td><p class="text-center">${command.contractAgreementSummaryDTO.overdueAmount}</p></td>
											<td>
												<form:input placeholder="${enterAmount}" id="inputAmountId"
												path="inputAmount" class=" form-control hasNumber "
												onchange="getTaxCharges(this)" maxlength="10" />
											</td>
										</tr>
									</table>
								
									<br>
									<c:if test="${not empty command.contractAgreementSummaryDTO.chargeList }">
										<table class="table table-bordered table-condensed">
	
											<tr>
												<th><spring:message code="rnl.master.tax.name" text="Tax Name" /></th>
												<th><spring:message code="rnl.master.tax.amount" text="Tax Amount" /></th>
											</tr>
											<tbody>
												<c:forEach items="${command.contractAgreementSummaryDTO.chargeList}" var="charge">
													<tr>
														<%-- <td class="text-center">${index.count}</td> --%>
														<td align="center">${charge.chargeDescEng}</td>
														<td align="center">${charge.chargeAmount}</td>
													</tr>
			
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									
								</div>


								<div class="form-group padding-top-10">
									<!-- Code Comment on 18-Oct ISRAT behalf of SAMADHAN SIR-->
									<%-- <label class="col-sm-2 control-label"><spring:message
											code="rnl.master.receivable" text="Total Receivable" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<input type="text" class="form-control" id="totalPayable"
												value="${command.billMas.bmTotalArrears + command.bmTotalAmount}"
												value="${command.billMas.bmTotalOutstanding}"
												readonly="readonly"> <label
												class="input-group-addon"><i class="fa fa-inr"></i></label>
										</div>
									</div> --%>
									<label class="col-sm-2 control-label"><spring:message
											code="rnl.master.receipt.amount" text="Receipt Amount" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="payAmount"
													cssClass="form-control mandColorClass hasDecimal text-right"
													maxlength="10" id="payAmount" readonly="true"
													onkeypress="return hasAmount(event, this, 8, 2)"
													onchange="getAmountFormatInDynamic((this),'payAmount')" />
												<label class="input-group-addon"><i
													class="fa fa-inr"></i></label>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div iclass="panel panel-default">
								<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
							</div>
					<%-- <div class="panel panel-default">
						<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
					</div> --%>
					<div class="text-center padding-top-10">
						<button type="button" id="submitBT" class="btn btn-success btn-submit"
							title='<spring:message code="rnl.master.submit" text="Submit" />'
							onclick="saveData(this)">
							<spring:message code="rnl.master.submit" text="Submit" />
						</button>
						<button type="button" class="btn btn-danger"
							title='<spring:message code="rnl.master.back" text="Back" />'
							onclick="window.location.href='EstateContractBillPayment.html'">
							<spring:message code="rnl.master.back" text="Back" />
						</button>
					</div>
				</div>
				</c:if>

			</form:form>
		</div>
	</div>
</div>



































