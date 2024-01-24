<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%	response.setContentType("text/html; charset=utf-8");%>
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
	
	$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : $("#rmDatetemp").val(),
			maxDate : '-0d',
			changeYear : true
		});
		
	var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
	$("#transactionDateId").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		minDate: disableBeforeDate,
		maxDate: today,
		onSelect : function(date){
			resetReceiptModeDetails(date);
		}
		
	
	});
	$("#transactionDateId").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	
	$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : $("#tranRefDate1").val(),
			maxDate : '-0d',
			changeYear : true
		});
	$("#tranRefDate1").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	$("#rdchequedddatetemp").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
		$("#tranRefDate1").datepicker('setDate', new Date()); 
		$("#rdchequedddatetemp").datepicker('setDate', new Date()); 
		 
		var depositSlipType1=$("#receiptCategoryId option:selected").attr("code");
		if(depositSlipType1=="M"){
			$('#vm_VendorId').attr("disabled",true).trigger('chosen:updated');
			var transactionDateDup = $('#transactionDateDup').val();
			if(transactionDateDup != null && transactionDateDup != ""){
				$('#transactionDateId').val(transactionDateDup);
			}
		}else{
			$('#vm_VendorId').attr("disabled",false).trigger('chosen:updated');
			var transactionDateDup = $('#transactionDateDup').val();
			if(transactionDateDup != null && transactionDateDup != ""){
			$('#transactionDateId').val(transactionDateDup);
			}
		}
		
	
		
		
		
		
	});

	
	function showConfirmBoxReceipt(msg) {
		debugger
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = getLocalMessage("account.proceed.btn");
    	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+msg+'</h5>';
	    message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}

	function proceed() {
		debugger;
		var theForm = '#frmAccountRecieptReport';
		var requestData = __serializeForm(theForm);
		var flag = $('#url').val();
		requestData = requestData+"&flag="+flag;
		var url = "AccountReceiptEntry.html?reciptPrintForm"
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$(errMsgDiv).show('false');
		   $.fancybox.close();
			$('.content').html(returnData);
		

	}
	function showConfirmBoxReceiptAdvance(msg) {
		debugger;
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = 'Proceed';
    	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+msg+'</h5>';
	    message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceedAdv()"/></div>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}

	function proceedAdv() {
		debugger;
		var theForm = '#frmAccountRecieptReport';
		var requestData = __serializeForm(theForm);
		var url = "AccountReceiptEntry.html?reciptPrintForm"
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$(errMsgDiv).show('false');
		   $.fancybox.close();
			$('.content').html(returnData);
		
	}
	

	$('#tablePayModeHeading_cash').hide();
	$('#tablePayModeHeading_nftwebrtgs').hide();
	$('#tableRecModecbBankid').hide();
	$('#tranRefNumber').hide();
	$('#tranRefDate').hide();
	$('#dp_DeptId').hide();
	
CashModeValidationView();
function resetForm(){
	$('#vm_VendorId').val('').trigger('chosen:updated');
	$('#baAccountId').val('').trigger('chosen:updated');
	
	$('.accountClass').each(function(i) {
		$('#budgetCode'+i).val('').trigger('chosen:updated');
	});
	
	$('#bankId').val('').trigger('chosen:updated');
	$('#baAccountId').val('').trigger('chosen:updated');
	
	$("#rm_Receivedfrom").prop("readonly",false);
	
	resetDate();
}

</script>





<c:if test="${ShowBreadCumb ==false}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>

<c:if test="${tbServiceReceiptMas.hasError eq 'false'}">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div id="content">
</c:if>

<c:url value="${mode}" var="form_mode" />
<c:url value="${saveAction}" var="url_form_submit" />


<c:if test="${form_mode == 'create'}">
	<div class="mand-label clearfix">
		<span><spring:message code="account.common.mandmsg" /> <i
			class="text-red-1">*</i> <spring:message
				code="account.common.mandmsg1" /></span>
	</div>
</c:if>

<div class="form-div">
		<!-- Start info box -->
		<div class="widget" id="widget">
		<div class="widget-header">
				<h2>
					<strong><spring:message code="account.field.acc"
							text="Account" /></strong>
					<spring:message code="bank.reconciliation.receipts" text="Receipts" />
				</h2>
		<apptags:helpDoc url="AccountReceiptEntry.html" helpDocRefURL="AccountReceiptEntry.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding">
		
	<form:form class="form-horizontal" modelAttribute="tbServiceReceiptMas" id="tbServiceReceiptMas"
	cssClass="form-horizontal" method="POST" action="${url_form_submit}">
	<div class="error-div alert alert-danger alert-dismissible"
		id="errorDivId" style="display: none;">
		<button type="button" class="close" onclick="closeOutErrBox()"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<span id="errorId"></span>
	</div>



	<jsp:include page="/jsp/tiles/validationerror.jsp" />

	<form:hidden path="templateExistsFlag" id="" />
	<form:hidden path="advanceFlag" id="advanceFlag" />
	<form:hidden path="balanceAmount" id="balanceAmount" />
	<!-- RAHUL -->
<%-- 	<form:hidden path="totalAmount" id="totalReceiptAmount" /> --%>
	<form:hidden path="prAdvEntryId" id="prAdvEntryId" />
	<form:hidden path="transactionDateDup" id="transactionDateDup" />
	<form:hidden path="" value="${Advance}" id="Advance" />
	<form:hidden path = "flag" id="url"/>
	<form:hidden path="totalAmount" id="totalReceiptAmount" />
	<form:hidden path="" id="sudaEnv" value="${SudaEnv}" />
	

	<c:if test="${form_mode == 'view'}">

		<SCRIPT>
					$(document).ready(function() {
						$('.error-div').hide();
						$('#editOriew').find('*').attr('disabled', 'disabled');
						$('#editOriew').find('*').addClass("disablefield");
						$('#manual_ReceiptNo').attr('disabled', 'disabled');
						$('#rmDatetemp').attr('disabled', 'disabled');
						$('#rm_Receivedfrom').attr('disabled', 'disabled');
						$('#mobile_Number').attr('disabled', 'disabled');
						$('#email_Id').attr('disabled', 'disabled');
						$('#vm_VendorId').attr('disabled', 'disabled');
						$('#VmVendorIdDesc').attr('disabled', 'disabled');
						$('#rdamount').attr('disabled', 'disabled');
						$('#cpdFeemode').attr('disabled', 'disabled');
						$('#bankId').attr('disabled', 'disabled');
						$('#tranRefNumber').attr('disabled', 'disabled');
						$('#rdchequedddatetemp').attr('disabled', 'disabled');
						$('#rmNarration').attr('disabled', 'disabled');
						$('#baAccountId').attr('disabled', 'disabled');
						$('#tranRefNumber1').attr('disabled', 'disabled');
						$('#tranRefDate1').attr('disabled', 'disabled');
						$('#isdeleted').attr('disabled', 'disabled');
						$('#isdeleted').addClass("disablefield");
					});
					$('label.control-label').each(function(){
						$(this).removeClass('required-control');
						
					});
					
					CashModeValidationView();
				</SCRIPT>
	</c:if>


	<h4>
		<spring:message code="accounts.receipt.receipt_details"></spring:message>
	</h4>
	<div id="receipt-details" class="panel-collapse collapse in">
		<form:hidden path="rmAmount" />
		<div class="panel-body">
			<c:choose>
				<c:when test="${form_mode eq 'view'}">
					<div class="form-group">
						<label class="control-label col-sm-2"><spring:message
								code="accounts.receipt.receipt_no"></spring:message></label>
						<div class="col-sm-4">
							<form:input id="rmRcptno" path="rmRcptno"
								class="form-control hasNumber" maxLength="12" readonly="true" />
						</div>
						<%-- <label class="control-label  col-sm-2 required-control"><spring:message
								code="accounts.receipt.receipt_date"></spring:message></label>
						<div class="col-sm-4">
							<form:input id="rmDatetemp" path="rmDate" class="form-control"
								readonly="true" />
						</div> --%>
					</div>
                  <div class="form-group">
				     <label class="control-label  col-sm-2 required-control"><spring:message
								code="accounts.receipt.receipt_date"></spring:message></label>
						<div class="col-sm-4">
							<form:input id="rmDatetemp" path="rmDate" class="form-control"
								readonly="true" />
						</div>
					<label class="control-label col-sm-2 required-control"><spring:message
									code="account.receipt.category" text="Receipt Category" /></label>
					<div class="col-sm-4">
						<form:select path="recCategoryTypeId"
								class="form-control mandColorClass" id="receiptCategoryId"
								disabled="true" onchange="resetReceiptCategoryForm(this)"
								data-rule-required="true">
						<form:option value="">
						<spring:message
							code="" text="Select" />
						</form:option>
							<c:forEach items="${recieptVouType}" varStatus="status"
									var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>	
                   </div>

				</c:when>
			</c:choose>
			<c:if test="${form_mode eq 'create'}">
						
				<div class="form-group">
					<label for="transactionDateId"
						class="col-sm-2 control-label required-control"><spring:message
							code="accounts.receipt.receipt_date" text="Receipt Date" /></label>
					<div class="col-sm-4">

						<div class="input-group">
						
						<c:set var="now" value="<%=new java.util.Date()%>" />
								<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
								<form:input path="transactionDate" id="transactionDateId" cssClass="mandColorClass form-control" 
									value="${date}" maxLength="10" ></form:input>
									
							<label class="input-group-addon mandColorClass"
								for="transactionDateId"><i class="fa fa-calendar"></i> </label>
						</div>
					</div>
					
					<label class="control-label col-sm-2 required-control"><spring:message
									code="account.receipt.category" text="Receipt Category" /></label>
					<div class="col-sm-4">
						<form:select path="recCategoryTypeId"
								class="form-control mandColorClass" id="receiptCategoryId"
								disabled="${viewMode}" onchange="resetReceiptCategoryForm(this)"
								data-rule-required="true">
						<form:option value="">
						<spring:message
							code="" text="Select" />
						</form:option>
							<c:forEach items="${recieptVouType}" varStatus="status"
									var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>	
							
				</div>
			</c:if>
			<div class="form-group">
			
				<div id="vendorReceivedFrom">
				<label class="control-label  col-sm-2 required-control"> <spring:message
						code="accounts.receipt.received_from"></spring:message></label>
				<div class="col-sm-4">

					<c:if test="${form_mode eq 'create'}">
						<form:select id="vm_VendorId" path="vmVendorId"
							class="form-control mandColorClass chosen-select-no-results"
							onchange="setVendorName(this)">
							<form:option value="">
								<spring:message code="accounts.receipt.select_vendor"></spring:message>
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
				<c:if test="${form_mode eq 'create'}">
								<c:choose>
									<c:when test="${SudaEnv eq true}">
										<label class="control-label  col-sm-2 "> <spring:message
												code="accounts.receipt.name"></spring:message>
										</label>
									</c:when>
									<c:otherwise>
										<label class="control-label  col-sm-2  required-control ">
											<spring:message code="accounts.receipt.name"></spring:message>
										</label>
									</c:otherwise>
								</c:choose>
								<div class="col-sm-4">
						<form:input id="rm_Receivedfrom" onchange="enableMobileAndEmail()"
							path="rmReceivedfrom" class="form-control" maxLength="200" />
					</div>
				</c:if>
				<c:if test="${form_mode ne 'create'}">
					<label class="control-label  col-sm-2  required-control ">
						<spring:message code="accounts.receipt.name"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="rm_Receivedfrom" path="rmReceivedfrom"
							class="form-control" maxLength="200" disabled="true" />
					</div>
				</c:if>
			</div>

			<div class="form-group">
				<c:if test="${form_mode eq 'create'}">
					<label class="control-label  col-sm-2 "> <spring:message
							code="accounts.receipt.mobile_no"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="mobile_Number" path="mobileNumber"
							class="form-control hasNumber" maxLength="10" readonly="" />
					</div>
				</c:if>
				<c:if test="${form_mode ne 'create'}">
					<label class="control-label  col-sm-2 "> <spring:message
							code="accounts.receipt.mobile_no"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="mobile_Number" path="mobileNumber"
							class="form-control hasNumber" maxLength="10" disabled="true" />
					</div>
				</c:if>
				<c:if test="${form_mode eq 'create'}">
					<label class="control-label  col-sm-2 "> <spring:message
							code="accounts.receipt.email_id"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="email_Id" path="emailId"
							class="form-control text-lowercase" maxLength="200" readonly="" />
					</div>
				</c:if>
				<c:if test="${form_mode ne 'create'}">
					<label class="control-label  col-sm-2 "> <spring:message
							code="accounts.receipt.email_id"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="email_Id" path="emailId"
							class="form-control text-lowercase" maxLength="200"
							disabled="true" />
					</div>
				</c:if>
			</div>

			<form:select id="dp_DeptId" path="dpDeptId" class="form-control"
				disabled="true">
				<c:forEach items="${departmentlist}" var="deptData">
					<form:option value="${deptData.dpDeptid}">${deptData.dpDeptdesc}
									
									    <form:hidden path="dpDeptId" value="${deptData.dpDeptid}" />
					</form:option>
				</c:forEach>
			</form:select>


			<div class="form-group">
				<c:if test="${form_mode eq 'create'}">
					<label class="control-label  col-sm-2"> <spring:message
							code="accounts.receipt.manual_receipt_no"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="manual_ReceiptNo" path="manualReceiptNo"
							class="form-control" maxLength="50" />
					</div>
				</c:if>

				<c:if test="${form_mode ne 'create'}">
					<label class="control-label  col-sm-2"> <spring:message
							code="accounts.receipt.manual_receipt_no"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="manual_ReceiptNo" path="manualReceiptNo"
							class="form-control" maxLength="50" disabled="true" />
					</div>
				</c:if>
				<c:if test="${form_mode eq 'create'}">
					<label class="control-label  col-sm-2 required-control"> <spring:message
							code="accounts.receipt.narration"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:textarea id="rmNarration" path="rmNarration"
							class="form-control " maxLength="200" />
					</div>
				</c:if>
				<c:if test="${form_mode ne 'create'}">
					<label class="control-label  col-sm-2 required-control"> <spring:message
							code="accounts.receipt.narration"></spring:message>
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
		<spring:message code="accounts.receipt.receipt_collection_details"></spring:message>
	</h4>


	<div id="receipt-collection-details" class="panel-collapse collapse in"
		style="overflow: visible;">
		<div class="panel-body">
			<div class="" id="taxHeadsTable">
				<table class="table table-bordered table-striped appendableClass "
					id="receiptAccountHeadsTable">
					<tbody>
						<tr>
							<th width="70%"><spring:message
									code="receipt.reversal.receipthead" text="Receipt Head" /> <c:choose>
									<c:when test="${form_mode eq 'create'}">
										<span class="mand float-right">*</span>

									</c:when>
								</c:choose></th>
							<th width="20%"><spring:message
									code="accounts.receipt.receipt_amount" /> <c:choose>
									<c:when test="${form_mode eq 'create'}">
										<span class="mand float-right">*</span>

									</c:when>
								</c:choose> <i class="fa fa-inr"></i></th>
							<c:choose>
								<c:when test="${form_mode eq 'create'}">
									<c:if test="${tbServiceReceiptMas.advanceFlag ne 'Y'}">
										<th width="10%"><spring:message
												code="account.common.add.remove" /></th>
									</c:if>
								</c:when>
							</c:choose>
						</tr>
						<c:choose>
							<c:when test="${form_mode eq 'create'}">
								<c:forEach items="${tbServiceReceiptMas.receiptFeeDetail}"
									var="details" varStatus="sts">
									<tr id="tr${sts.index}" class="accountClass">
										<td><form:select id="budgetCode${sts.index}"
												path="receiptFeeDetail[${sts.index}].sacHeadId"
												class="form-control mandColorClass chosen-select-no-results"
												disabled="${viewMode}" onchange="validateReceiptHead()">
												<form:option value="">
													<spring:message code="account.common.select" />
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

										<c:if test="${tbServiceReceiptMas.advanceFlag ne 'Y'}">
											<td><c:if test="${!viewMode}">
													<button data-toggle="tooltip" data-placement="top"
														title="Add" class="btn btn-success btn-sm addButton"
														id="addButton${count}">
														<i class="fa fa-plus-circle"></i>
													</button>
													<button data-toggle="tooltip" data-placement="top"
														title="Delete" class="btn btn-danger btn-sm delButton"
														id="delButton${count}">
														<i class="fa fa-trash-o"></i>
													</button>
												</c:if></td>
										</c:if>
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
												var="famt" pattern="#,##,##,##,##0.00" maxIntegerDigits="15"
												maxFractionDigits="2" /> <form:input
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
		<spring:message code="accounts.receipt.collection_mode_details"></spring:message>
	</h4>
	<div id="collection-collection-details1"
		class="panel-collapse collapse in">
		<div class="panel-body">
			<table class="table table-bordered">

				<tr id="tablePayModeHeading_cheqDD">
					<th width="15%" scope="col"><spring:message
							code="accounts.receipt.payment_mode"></spring:message> <c:choose>
							<c:when test="${form_mode eq 'create'}">
								<span class="mand">*</span>
							</c:when>
						</c:choose></th>
					<c:if
						test="${tbServiceReceiptMas.receiptModeDetailList.cpdFeemodeCode ne 'C'}">
						<th width="40%" scope="col"><spring:message
								code="accounts.receipt.drawn_on"></spring:message> <c:if
								test="${form_mode eq 'create'}">
								<span class="mand">*</span>
							</c:if></th>
						<th width="15%" scope="col"><spring:message
								code="accounts.receipt.cheque_dd_no_pay_order"></spring:message>
							<span class="mand" id="DDno">*</span></th>
						<th width="15%" scope="col"><spring:message
								code="accounts.receipt.cheque_dd_date"></spring:message> <span
							class="mand"  > *</span></th>
					</c:if>
					<th width="15%" scope="col" class="text-center"><spring:message
							code="accounts.receipt.mode_amount"></spring:message> <i
						class="fa fa-inr"></i></th>
				</tr>


				<tr id="tablePayModeHeading_cash">
					<th width="15%" scope="col"><spring:message
							code="accounts.receipt.payment_mode"></spring:message> <c:choose>
							<c:when test="${form_mode eq 'create'}">
								<span class="mand">*</span>
							</c:when>
						</c:choose></th>
					<th width="15%" scope="col" class="text-center"><spring:message
							code="accounts.receipt.mode_amount"></spring:message> <i
						class="fa fa-inr"></i></th>
				</tr>
				<tr id="tablePayModeHeading_nftwebrtgs">
					<th width="15%" scope="col"><spring:message
							code="accounts.receipt.payment_mode"></spring:message> <c:if
							test="${form_mode eq 'create'}">
							<span class="mand">*</span>
						</c:if></th>

					<th width="40%" scope="col"><spring:message
							code="accounts.receipt.baAccountid"></spring:message> <c:if
							test="${form_mode eq 'create'}">
							<span class="mand">*</span>
						</c:if></th>

					<th width="15%" scope="col"><spring:message
							code="accounts.receipt.tranRefNumber1"></spring:message> <c:if
							test="${form_mode eq 'create'}">
							<span class="mand"  id="DDno1">*</span>
						</c:if></th>
					<th width="15%" scope="col"><spring:message
							code="accounts.receipt.tranRefDate1">
						</spring:message> <c:if test="${form_mode eq 'create'}">
							<span class="mand">*</span>
						</c:if></th>
					<th width="15%" scope="col" class="text-center"><spring:message
							code="accounts.receipt.mode_amount"></spring:message> <i
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

										<spring:message code="accounts.receipt.select_mode"></spring:message>
									</form:option>
									<c:forEach items="${paymentMode}" varStatus="status"
										var="levelChild">
										<c:if test="${userSession.getCurrent().getLanguageId() eq '1'}">
										<form:option value="${levelChild.lookUpId}"
											code="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
										</c:if>
										
										<c:if test="${userSession.getCurrent().getLanguageId() ne'1'}">
										<form:option value="${levelChild.lookUpId}"
											code="${levelChild.lookUpCode}">${levelChild.descLangSecond}</form:option>
										</c:if>
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
											<spring:message code="account.common.select" />
										</form:option>
										<c:choose>
										<c:when test="${SudaEnv eq true}">
										<c:forEach items="${customerBankList}" varStatus="status"
											var="cbBankid">
											<form:option value="${cbBankid.bankId}"
												code="${cbBankid.bankId}">${cbBankid.bank} - ${cbBankid.branch} </form:option>
										</c:forEach>
										</c:when>
										<c:otherwise>
										<c:forEach items="${customerBankList}" varStatus="status"
											var="cbBankid">
											<form:option value="${cbBankid.bankId}"
												code="${cbBankid.bankId}">${cbBankid.bank} - ${cbBankid.branch} - ${cbBankid.ifsc}</form:option>
										</c:forEach>
										</c:otherwise>
										</c:choose>
									</form:select>
								</c:when>
								<c:otherwise>
									<form:input id="cbBankidDesc"
										path="receiptModeDetailList.cbBankidDesc" class="form-control"
										disabled="true" />
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
										class="form-control hasNumber" maxLength="8" disabled="true" />
								</c:otherwise>
							</c:choose></td>
					</c:if>
					<c:if test="${form_mode eq 'create'}">
						<td id="rdchequedddatetempdata">
							<div class="input-group">
								<form:input id="rdchequedddatetemp"
									path="receiptModeDetailList.rdChequedddatetemp"
									class="form-control datepicker mandColorClass" onchange="validateChequeDate()" maxlength="10"/>
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
										<spring:message code="account.common.select" />
									</form:option>
									<c:forEach items="${bankaccountlist}" varStatus="status"
										var="baAccountId">
										<form:option value="${baAccountId.key}"
											code="${baAccountId.key}">${baAccountId.value}</form:option>
									</c:forEach>
								</form:select>
							</c:when>
							<c:otherwise>


								<form:input id="baAccountidDesc"
									path="receiptModeDetailList.baAccountidDesc" class="form-control"
									disabled="true" />

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
										class="form-control mandColorClass datepicker" maxlength="10" />
									<label class="input-group-addon" for="tranRefDate1"><i
										class="fa fa-calendar"></i> <input type="hidden"
										id="tranRefDateDt"> </label>
								</div>

							</c:when>
							<c:otherwise>
								<div class="input-group">
									<form:input id="tranRefDate1"
										path="receiptModeDetailList.tranRefDatetemp"
										class="form-control datepicker" readonly="true" maxLength="10"  />
									<label class="input-group-addon" for="tranRefDateDtv"><i
										class="fa fa-calendar"></i> <input type="hidden"
										id="tranRefDateDtv"> </label>
								</div>
							</c:otherwise>

						</c:choose></td>

					<c:choose>
<c:when test="${form_mode eq 'view'}"><td><fmt:setLocale value="en_IN" /> <fmt:formatNumber
							type="currency"
							value="${tbServiceReceiptMas.rmAmount}"
							pattern="#,##,##,##,##0.00" groupingUsed="false" var="famt"
							maxIntegerDigits="15" maxFractionDigits="2" /> <form:input
							id="rdamount" name="rdamount" path="receiptModeDetailList.rdAmount"
							value="${famt}" class="form-control text-right "
							disabled="${viewMode}" readonly="true"
							onkeypress="return hasAmount(event, this, 12, 2)" /></td></c:when>
				<c:otherwise><td><fmt:setLocale value="en_IN" /> <fmt:formatNumber
							type="currency"
							value="${tbServiceReceiptMas.receiptModeDetailList.rdAmount}"
							pattern="#,##,##,##,##0.00" groupingUsed="false" var="famt"
							maxIntegerDigits="15" maxFractionDigits="2" /> <form:input
							id="rdamount" name="rdamount" path="receiptModeDetailList.rdAmount"
							value="${famt}" class="form-control text-right "
							disabled="${viewMode}" readonly="true"
							onkeypress="return hasAmount(event, this, 12, 2)" /></td>
				</c:otherwise></c:choose>
				</tr>
			</table>
		</div>
	</div>

	<c:choose>
		<c:when test="${form_mode eq 'create'}">
			<div class="text-center" id="divSubmit">
				<button type="button" class="btn btn-success btn-submit"  id="submit"
					onclick="saveDataReceipt(this)">
					<spring:message code="accounts.receipt.save"></spring:message>
				</button>
				 <button type="Reset" class="btn btn-warning" id="createBtn">
					<spring:message code="account.bankmaster.reset" text="Reset"/>
				</button> 

				<c:if test="${tbServiceReceiptMas.advanceFlag eq 'Y'}">
					<spring:url var="cancelButtonURL" value="AdvanceEntry.html" />
					<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.back" text="Back" /></a>
				</c:if>
				<c:if test="${tbServiceReceiptMas.advanceFlag ne 'Y'}">
				<input type="button" class="btn btn-danger"
							onclick="back()"
							value="<spring:message code="accounts.stop.payment.back" text="Back"/>" id="cancelEdit" />
				</c:if>
			</div>
		</c:when>



		<c:otherwise>
			<div class="text-center" id="divSubmit">
				<c:if test="${form_mode ne 'view'}">
					<button type="button" class="btn btn-success btn-submit" id="submitEdit"
						onclick="saveDataReceipt(this)">
						<spring:message code="accounts.receipt.save"></spring:message>
					</button>
				</c:if>
				<c:if test="${PostFlag eq 'P'}">
					<input type="button" class="btn btn-danger"
						onclick="javascript:openRelatedForm('AccountVoucherPostingform.html');"
						value="<spring:message code="water.btn.cancel"/>" id="cancelEdit" />
				</c:if>
				<c:if test="${PostFlag eq 'R'}">
					<input type="button" class="btn btn-danger"
							onclick="back()"
							value="<spring:message code="accounts.stop.payment.back" text="Back"/>" id="cancelEdit" />

				</c:if>
			</div>
		</c:otherwise>
	</c:choose>
	<form:hidden path="" id="feeModeStatus" />
</form:form>

</div>
</div>
</div>
<!-- onclick="window.location.href='AccountReceiptEntry.html'" -->

<c:if test="${tbServiceReceiptMas.hasError eq 'false'}">
	</div>
</c:if>