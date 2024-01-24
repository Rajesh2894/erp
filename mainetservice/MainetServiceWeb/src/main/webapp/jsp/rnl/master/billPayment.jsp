<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script>
	$(document).ready(function(e) {
		$('#submitBT').prop('disabled', true);
		//code update ISRAT-01-OCT
		var totalPayable = Number($("#totalPayable").val());
		/* $("#payAmount").keyup(function() {
			//$("#amountToPay").val($("#payAmount").val());
			let payAmount = Number($("#payAmount").val());
			if(totalPayable<payAmount){
				let payAmt = $('#payAmount').val();
				    //set the input's value
				    $('#payAmount').val(payAmt.substring(0,payAmt.length - 1));
				    //$('#submitBT').prop('disabled', true);
				return false;
			}else{
				$('#submitBT').prop('disabled', false);
				return true;
			}
		}); */
		
		$("#payAmount").keyup(function() {
			let payAmount = Number($("#payAmount").val());
			if(totalPayable == 0 || totalPayable == 'undefined' || payAmount<= 0 ){
				$('#submitBT').prop('disabled', true);
			}else{
				$('#submitBT').prop('disabled', false);
			}
		});
		
		$("#amountToPay").val($("#payAmount").val());

		
		 $("#btnsearch").click(function(){
			//T#37721
			debugger;
		 	 var contractNo = $("#contractNo").val();
		 	 let propertyContractNo = $("#propertyNo").val();
			 	if (contractNo != 0 || propertyNo != 0) {
			 		
			 		let requestData={
			 				"contNo":contractNo,
			 				"propertyContractNo":propertyContractNo
			 		}
			 		var ajaxResponse = __doAjaxRequest('EstateContractBillPayment.html?serachBillPayment', 'POST', requestData, false,'html');
					 if(ajaxResponse != false){
			    		 $('.content-page').html(ajaxResponse);
			    		 prepareTags();
			    	 }
			 	}else{
			 		let errorList=[];
			 		errorList.push(getLocalMessage("rnl.bill.pay.search.record"));
			 		displayErrorsOnPage(errorList);
			 	}	 
			 }); 
		 });

	function resetbillPayment(element) {
		$("#contractBillPayment").submit();
	}

	function saveData(element) {
		//check reciept amt and receivable amt equal or not
		let amt = Number($('#payAmount').val()); 
		if(amt <=0){
			showErrormsgboxTitle(getLocalMessage("receipt amt invalid"));
		}/* else if(Number($('#payAmount').val()) != Number($("#totalPayable").val())){
			showErrormsgboxTitle(getLocalMessage("receipt amt can't be more than receivable amt "));
			return false;
		} */
		$("#errorDiv").hide();
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(element, "Bill Payment done successfully!",
					'EstateContractBillPayment.html?PrintReport', 'saveform');
		} else {
			return saveOrUpdateForm(element, "Bill Payment done successfully!",
					'EstateContractBillPayment.html', 'saveform');
		}
	}
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.master.bill.coll" text="Bill Collection" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="EstateContractBillPayment.html" method="post"
				class="form-horizontal" name="contractBillPayment"
				id="contractBillPayment">
				<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" /> --%>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- D#88671 -->
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="Contract Number" text="Contract Number"></spring:message></label>
					<%-- <div class="col-sm-4">
						<form:input path="contractNo" cssClass="form-control"
							id="contractNo" data-rule-required="true" />
					</div> --%>
					<div class="col-sm-4">
						<form:select id="contractNo"
							cssClass="form-control chosen-select-no-results"
							class="form-control" path="contractNo">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.contractAgreementList}"
								var="contract">
								<form:option value="${contract.contNo}">${contract.contNo} - ${contract.contp2Name}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Property No" /></label>

					<div class="col-sm-4">
					<form:select id="propertyNo"
							cssClass="form-control chosen-select-no-results"
							class="form-control" path="propertyContractNo">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.propertyDetails}" var="objArray">
								<form:option value="${objArray[1]}" code="${objArray[0]}">${objArray[3]} - ${objArray[4]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-info" id="btnsearch">
						<i class="fa fa-search"></i>
						<spring:message code="rnl.master.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning"
						onclick="resetbillPayment(this)">
						<spring:message code="rnl.master.reset" text="Reset"></spring:message>
					</button>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#first"><spring:message
										code="rnl.master.contract" text="Contract Details" /></a>
							</h4>
						</div>
						<div id="first" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label class="control-label col-sm-2" for="ContractDate"><spring:message
											code="rnl.master.contract.date" text="Contract Date" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contDate"
											class="form-control" readonly="true"></form:input>

									</div>
									<label class="control-label col-sm-2" for="Department"><spring:message
											code="master.complaint.department" text="Department" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contDept"
											class="form-control" readonly="true"></form:input>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2" for="ContractDate"><spring:message
											code="rnl.master.represented.by" text="Represented By" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contp1Name"
											class="form-control" readonly="true"></form:input>
									</div>
									<label class="control-label col-sm-2" for="Department"><spring:message
											code="rnl.master.vender.name" text="Vendor Name" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contp2Name"
											class="form-control" readonly="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for="ContractDate"><spring:message
											code="rnl.master.contract.from.date"
											text="Contract From Date" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contFromDate"
											class="form-control" readonly="true"></form:input>
									</div>
									<label class="control-label col-sm-2" for="Department"><spring:message
											code="rnl.master.contract.to.date" text="Contract To Date" /></label>
									<div class="col-sm-4">
										<form:input path="contractAgreementSummaryDTO.contToDate"
											class="form-control" readonly="true"></form:input>
									</div>
								</div>


							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#second"><spring:message
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
											<th><spring:message code=""
													text="Agreement Amount" /></th>
											<th><spring:message code=""
													text="Balance Amount" /></th>
											<th><spring:message code=""
													text="Overdue Amount" /></th>
										</tr>
										<tr>
											<td><p class="text-center">${command.contractAgreementSummaryDTO.contAmount}</p></td>
											<td><p class="text-center">${command.contractAgreementSummaryDTO.balanceAmount}</p></td>
											<td><p class="text-center">${command.contractAgreementSummaryDTO.overdueAmount}</p></td>
										</tr>
									</table>
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
											code="rnl.master.receipt.amt" text="Enter Receipt Amount" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="payAmount"
													cssClass="form-control mandColorClass hasDecimal text-right"
													maxlength="10" id="payAmount"
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
					<div class="panel panel-default">
						<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
					</div>
					<div class="text-center padding-top-10">
						<button type="button" id="submitBT" class="btn btn-success btn-submit"
							onclick="saveData(this)">
							<spring:message code="rnl.master.submit" text="Submit" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='EstateContractBillPayment.html'">
							<spring:message code="rnl.master.back" text="Back" />
						</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
