<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="js/mainet/file-upload.js"></script>
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


<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/accountBillEntry.js"
	type="text/javascript"></script>
<script>

	var depositFlag = $("#depositFlag").val();
	var advanceFlag = $("#advanceFlag").val();
	var grantFlag = $("#grantFlag").val();
	
	if(depositFlag != 'Y' && advanceFlag != 'Y' && grantFlag != 'Y'){
		$("#bmEntrydate").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: '0',
		onSelect : function(date){
				getExpenditureDetails2(date);
			}
		});
	}
	$("#bmEntrydate").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
    });
    
    $("#dueDate").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true
		});
    
	if(depositFlag == 'Y' || advanceFlag == 'Y' || grantFlag == 'Y'){
		var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
		var disableBeforeDate = new Date(response[0], response[1], response[2]);
		var sliYear = response[0];
		var date = new Date();
		var data = $('#depositDate').val();	
		var curArr;
		var year;
	if(data == null || data == undefined || data == ""){
		curArr = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
	}else{
		var arr = data.split('/');
			var day=arr[0];
			var month=arr[1];
		        year=arr[2];
			curArr = month+"/"+day+"/"+year;
	}
	
		if(sliYear==year)
			{
		var date = new Date();
		var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
		$("#bmEntrydate").datepicker({
		    dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			minDate : disableBeforeDate,
			maxDate : today
			
			
		});
		}
		else
			{
		var today = new Date(curArr);
		var date = new Date();
		var todays = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
		$("#bmEntrydate").datepicker({
		    dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			minDate: today,
			maxDate: todays
			
			
		}); 
			}
		
		
	//	$("#bmEntrydate").val(null);
		
	}

	

	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: '0',
		
	});
	var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
	$("#transactionDateId").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		//minDate : disableBeforeDate,
		maxDate : today
		
		
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
	$("#authorizationDateId").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		minDate : disableBeforeDate,
		maxDate : today,
		 onSelect : function(date){
				validateAuthorizationDate(date);
			} 
		
	});
	$("#authorizationDateId").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     } 
	    });
	var mode = $("#editMode").val();
	if(mode =="edit"){
	
	$('.expenditureClass').each(function(i) {
		$('#viewExpDet'+i).prop("disabled", false);
	});
		
	}
	
	var disallowedAmt = $('#disallowedAmtTot').val();
	if (disallowedAmt == undefined || disallowedAmt == '0.00' || disallowedAmt =='0') {
		$('#disallowedRemarkDivUpd').hide();
	}
	
</script>


<c:if test="${accountBillEntryBean.hasError eq 'false'}">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div id="content">
</c:if>
<c:url value="${mode}" var="form_mode" />

<c:if test="${mode eq 'update'}">
	<input type=hidden value="edit" id="editMode" />
</c:if>


<div class="form-div">
	<div class="widget" id="widget">
		<div class="widget-header">
			<c:if test="${accountBillEntryBean.authorizationMode eq Auth}">
				<h2>
					<spring:message code="account.bill.invoice" text="Invoice /" />
					<strong><spring:message code="account.bill.billentry"
							text="Bill Entry" /></strong>
				</h2>
				<apptags:helpDoc url="AccountBillEntry.html"
					helpDocRefURL="AccountBillEntry.html"></apptags:helpDoc>
			</c:if>
			<c:if test="${accountBillEntryBean.authorizationMode ne Auth}">
				<h2>
					<spring:message code="account.bill.invoice" text="Invoice /" />
					<strong><spring:message code="account.bill.billauth"
							text="Bill Authorization" /></strong>
				</h2>
				<apptags:helpDoc url="AccountBillAuthorization.html"
					helpDocRefURL="AccountBillAuthorization.html"></apptags:helpDoc>
			</c:if>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>
			<form:form action="AccountBillEntry.html" method="POST"
				class="form-horizontal" modelAttribute="accountBillEntryBean"
				id="frmAccountBillEntryId">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div id="errorId"></div>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="successfulFlag" id="successfulFlag" />
				<form:hidden path="expenditureExistsFlag" id="expenditureExistsFlag" />
				<form:hidden path="" value="${modeCheck}" id="modeCheckId" />
				<form:hidden path="advanceFlag" id="advanceFlag" />
				<form:hidden path="balAmount" id="balAmount" />
				<form:hidden path="prAdvEntryId" id="prAdvEntryId" />
				<form:hidden path="" value="${Advance}" id="Advance" />
				<form:hidden path="depositFlag" id="depositFlag" />
				<form:hidden path="grantFlag" id="grantFlag" />
				<form:hidden path="grantId" id="grantId" />
				<form:hidden path="grantPayableAmount" id="grantPayableAmount" />
				<form:hidden path="depId" id="depId" />
				<form:hidden path="trTenderId" id="trTenderId" />
				<form:hidden path="workOrderFlag" id="workOrderFlag" />
				<form:hidden path="billIntRefId" id="billIntRefId" />
				<form:hidden path="" value="${budgetDefParamStatus}"
					id="budgetDefParamStatusFlag" />
				<form:hidden path="deletedExpIds" id="deletedExpId" />
				<form:hidden path="deletedDedIds" id="deletedDedId" />
				<form:hidden path="actualTaskId" id="actualTaskId" />
				<form:hidden path="createdBy" id="createdBy" />
				<form:hidden path="dupCreatedDate" id="dupCreatedDate" />
				<form:hidden path="languageId" id="languageId" />
				<form:hidden path="lgIpMacAddress" id="lgIpMacAddress" />
				<form:hidden path="removeFileById" id="removeFileById" />
				<form:hidden path="loanPayableAmount" id="loanPayableAmount" />
				<form:hidden path="loanId" id="loanId" />
				<form:hidden path="loanFlag" id="loanFlag" />
				<form:hidden path="loanRepaymentId" id="loanRepaymentId" />
				<%-- <form:hidden path="billNo" id="billNo" /> --%>
				<form:hidden path="" value="${mode}" id="modeType" />
				<form:hidden path="" value="${depositDate}" id="depositDate" />
				<form:hidden path="" value="${defectLiablityDate}" id="defectLaiblityDate" />
				
				<form:hidden path="" value="${displayBillNo}" id="displayBillNo" />


				<c:if test="${modeCheck ne 'create'}">
					<div class="form-group">
						<form:hidden path="id" />
						<form:hidden path="billTypeId" />
						<label for="bmId" class="col-sm-2 control-label"><spring:message
								code="bill.no" /></label>
						<div class="col-sm-4">
							<form:input type="text" path="billNo" class="form-control"
								name="bmBillno" enable-roles="true" role="1" id="bmBillno"
								readonly="true" />
						</div>
						<label for="bmEntrydate"
							class="col-sm-2 control-label required-control"><spring:message
								code="bill.date"
								text="Bill Date" /></label>
						<c:if test="${accountBillEntryBean.authorizationMode eq 'Auth'}">
							<div class="col-sm-4">
								<div class="input-group">
									<%-- <form:hidden path="transactionDate" id="bmEntrydate" /> --%>
									<form:input path="transactionDate" id="bmEntrydate"
										cssClass="form-control mandColorClass" readonly="true"
										maxlength="10" />
									<label class="input-group-addon" for="bmEntrydate"><i
										class="fa fa-calendar"></i> </label>
								</div>
							</div>
						</c:if>
						<c:if test="${accountBillEntryBean.authorizationMode ne 'Auth'}">
							<div class="col-sm-4">
								<div class="input-group">
								<c:set var="now" value="<%=new java.util.Date()%>" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
									<form:input path="transactionDate" id="bmEntrydate"
										cssClass="form-control mandColorClass" disabled="flase" value="${date}"
										maxlength="10" />
									<label class="input-group-addon" for="bmEntrydate"><i
										class="fa fa-calendar"></i> </label>
								</div>
							</div>
						</c:if>
					</div>
				</c:if>


				<div class="form-group">

					<c:choose>
						<c:when test="${modeCheck eq 'Auth' }">
							<label for="authorizationDateId"
								class="col-sm-2 control-label required-control"><spring:message
									code="account.journal.voucher.auth.date"
									text="Authorization Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="authorizationDate" id="authorizationDateId"
										cssClass="mandColorClass form-control" maxlength="10"
										onchange="validateAuthorizationDates();" />
									<label class="input-group-addon mandColorClass"
										for="authorizationDateId"><i class="fa fa-calendar"></i>
									</label>
								</div>
							</div>
						</c:when>
						<c:when test="${modeCheck eq 'create'}">
							<label for="transactionDateId"
								class="col-sm-2 control-label required-control"><spring:message
									code="bill.date" text="Bill Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">

									<c:set var="now" value="<%=new java.util.Date()%>" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
									<form:input path="transactionDate" id="transactionDateId"
										cssClass="mandColorClass form-control" value="${date}"
										maxlength="10"></form:input>

									<label class="input-group-addon mandColorClass"
										for="transactionDateId"><i class="fa fa-calendar"></i>
									</label>
								</div>
							</div>
						</c:when>
						<c:when test="${modeCheck eq 'E'}">
							<label for="transactionDateId"
								class="col-sm-2 control-label required-control"><spring:message
									code="bill.date" text="Bill Date" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="transactionDate" id="transactionDateId"
										cssClass="form-control" disabled="true" />
									<label class="input-group-addon" for="transactionDateId"><i
										class="fa fa-calendar"></i> </label>
								</div>
							</div>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
					<label for="bmBilltypeCpdId"
						class="col-sm-2 control-label required-control"><spring:message
							code="bill.type" /></label>
					<div class="col-sm-4">
						<form:select type="select" path="billTypeId"
							class="form-control chosen-select-no-results mandColorClass"
							name="bmBilltypeCpdId" id="bmBilltypeCpdId"
							onchange="checkTemplate()" disabled="${viewMode}">
							<option value=""><spring:message
									code="budget.reappropriation.master.select" text="Select" /></option>
							<c:forEach items="${billTypeList}" varStatus="status"
								var="billType">
								<form:option code="${billType.lookUpCode}"
									value="${billType.lookUpId}">${billType.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				
				
				<div class="form-group">
					<c:if test="${accountBillEntryBean.depositFlag ne 'Y'}">
						<label for="bmInvoicevalue"
							class="col-sm-2 control-label required-control"><spring:message
								code="bill.invoice.amount" /></label>
						<div class="col-sm-4">
							<form:input type="text" path="invoiceValue"
								onkeypress="return hasAmount(event, this, 13, 2)"
								onchange="getAmountFormatInStatic('bmInvoicevalue')"
								 oninput ="setTaxableAmount()"
								class="form-control mandColorClass text-right amount"
								name="bmInvoicevalue" id="bmInvoicevalue" readonly="${viewMode}"/>
						</div>
						
						<label for="bmTaxableValue"
							class="col-sm-2 control-label"><spring:message
								code="account.taxable.amount" text="Taxable Amount" /></label>
						<div class="col-sm-4">
							<form:input type="text" path="bmTaxableValue"
								onkeypress="return hasAmount(event, this, 13, 2)"
								onchange="getDeductionAmtAutomatically()"
								class="form-control mandColorClass text-right amount"
								name="bmTaxableValue" id="bmTaxableValue"  readonly=""/>
				</div>
					</c:if>

					<c:if test="${accountBillEntryBean.depositFlag eq 'Y'}">
						<label for="bmInvoicevalue"
							class="col-sm-2 control-label required-control"><spring:message
								code="bill.invoice.amount" /></label>
						<div class="col-sm-4">
							<form:input type="text" path="invoiceValue"
								onkeypress="return hasAmount(event, this, 13, 2)"
								onchange ="setTaxableAmount()"
								class="form-control hasNumber mandColorClass text-right amount"
								name="bmInvoicevalue" id="bmInvoicevalue" readonly="true" />
						</div>
						<label for="bmTaxableValue"
							class="col-sm-2 control-label"><spring:message
								code="" text="Taxable Amount" /></label>
						<div class="col-sm-4">
							<form:input type="text" path="bmTaxableValue"
								onkeypress="return hasAmount(event, this, 13, 2)"
								class="form-control hasNumber mandColorClass text-right amount"
								name="bmTaxableValue" id="bmTaxableValue" readonly="true" />
					
					
				</div>
					</c:if>
					<%-- <label for="bmTaxableValue"
							class="col-sm-2 control-label"><spring:message
								code="" text="Taxable Amount" /></label>
						<div class="col-sm-4">
							<form:input type="text" path="bmTaxableValue"
								onkeypress="return hasAmount(event, this, 13, 2)"
								class="form-control mandColorClass text-right amount"
								name="bmTaxableValue" id="bmTaxableValue"  />
					
					
				</div> --%>
				</div>
				
				 
				<div class="form-group">
					<label for="Department"
						class="col-sm-2 control-label required-control"><spring:message
							code="account.deposit.Department" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="departmentId"
							class="form-control mandColorClass chosen-select-no-results"
							name="department" id="departmentId" disabled="${viewMode}">
							<option value=""><spring:message
									code="account.common.select" /></option>
							<c:forEach items="${departmentList}" varStatus="status"
								var="depart">
								<form:option value="${depart.lookUpId}"
									code="${depart.lookUpCode}">${depart.defaultVal}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label for="vmVendorname"
						class="col-sm-2 control-label required-control"><spring:message
							code="accounts.vendormaster.vendorName" /></label>
					<div class="col-sm-4">
						<form:hidden path="vendorDesc" id="vmVendorDesc" />
						<form:select path="vendorId"
							class="form-control mandColorClass chosen-select-no-results"
							name="vmVendorname" id="vmVendorname" onchange="getVendorCode()"
							disabled="${viewMode}">
							<option value=""><spring:message
									code="account.common.select" /></option>
							<c:forEach items="${vendorList}" varStatus="status" var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<%-- <label for="Field" class="col-sm-2 control-label required-control"><spring:message
							code="" text="Field" /></label>
					<div class="col-sm-4">
						<form:select path="fieldId"
							class="form-control mandColorClass chosen-select-no-results"
							name="field" id="fieldId" disabled="${viewMode}">
							<option value=""><spring:message
									code="account.common.select" /></option>
							<c:forEach items="${listOfTbAcFieldMasterItems}"
								varStatus="status" var="fieldItem">
								<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
				</div>
				<div class="form-group">
				<label for="Field" class="col-sm-2 control-label required-control"><spring:message
							code="account.common.field" text="Field" /></label>
					<div class="col-sm-4">
						<form:select path="fieldId" onchange="loadBudgetExpenditureFieldData(this)"
							class="form-control mandColorClass chosen-select-no-results"
							name="field" id="fieldId" disabled="${viewMode}">
							<option value=""><spring:message
									code="account.common.select" /></option>
							<c:forEach items="${listOfTbAcFieldMasterItems}"
								varStatus="status" var="fieldItem">
								<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<!-- D#79898 -->
					<c:if test="${empty accountBillEntryBean.grantFlag}">
					 <label for="fundId"
						class="col-sm-2 control-label"><spring:message
							code="account.fund.name" text ="Fund Name"/></label>
						<div class="col-sm-4">
							<form:select path="fundId"
								class="form-control mandColorClass chosen-select-no-results"
								name="fundId" id="fundId"  
								disabled="${viewMode}">
								<form:option value="" selected="true">
									<spring:message code="" text="Select" />
								</form:option>
								<c:forEach items="${fundList}" var="fundData">
									<form:option code="${fundData.fundCode}"
										value="${fundData.fundId}">${fundData.fundCompositecode} ${fundData.fundDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
				</div>

				<div class="form-group">
					<label for="Due Date" class="col-sm-2 control-label"><spring:message
							code="bill.due.date" text="Due Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="dueDate" id="dueDate" cssClass="form-control" maxLength="10"/>
							<label class="input-group-addon" for="dueDate"><i
								class="fa fa-calendar"></i> </label>
						</div>
					</div>
				</div>

				<c:if test="${accountBillEntryBean.grantFlag eq 'Y'}">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.grant.no" text="Grant No" /></label>
						<div class="col-sm-4">
							<form:input cssClass="form-control control-label" type="text"
								path="grantNo" readonly="true" />
						</div>
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.fund.name" text="Fund Name" /></label>
						<div class="col-sm-4">
							<form:input cssClass="form-control" type="text" path="grantFund"
								readonly="true" />
						</div>
					</div>
				</c:if>



				<h4>
					<spring:message code="account.bill.dept.info"
						text="Department Information" />
				</h4>
				<div class="table-responsive" id="departmentInfoTableDiv">

					<table id="departmentInfoTable"
						class="table table-bordered table-striped">
						<thead>
							<tr>
								<th scope="col" width="8%"><spring:message code="account.type"
										text="Type" /></th>
								<th scope="col" width="15%"><spring:message code="advance.management.number"
										text="Number" /></th>
								<th scope="col" width="15%"><spring:message code="account.issue.date"
										text="Issue Date" /></th>
							</tr>
							<tr>
								<td><label for="bmInvoicenumber" class="required-control"><spring:message
											code="bill.invoice.no" /></label></td>
								<td><form:input type="text" path="invoiceNumber"
										class="form-control" name="bmInvoicenumber"
										id="bmInvoicenumber" disabled="${viewMode}" /></td>
								<td><div class="input-group">
										<form:input path="invoiceDate" class="datepicker form-control"
											name="bmInvoicedate" id="bmInvoicedate" readonly="true"
											disabled="${viewMode}" />
										<label class="input-group-addon" for="bmInvoicedate"><i
											class="fa fa-calendar"></i> <input type="hidden"
											id="invoicedate"> </label>
									</div></td>
							</tr>
							<tr>
								<td><label for="bmWPOrderNumber" id="bmWPOrderNumberL" ><spring:message
											code="account.wo.po.proposalNo" text="WO/PO/Proposal No." /></label></td>
								<td><form:input type="text"
										path="workOrPurchaseOrderNumber" class="form-control"
										name="bmWPOrderNumber" id="bmWPOrderNumber"
										disabled="${viewMode}" /></td>
								<td><div class="input-group">
										<form:input path="workOrPurchaseOrderDate"
											class="datepicker form-control" name="date-invoice"
											id="bmWPOrderDate" readonly="true" disabled="${viewMode}" />
										<label class="input-group-addon" for="bmWPOrderDate"><i
											class="fa fa-calendar"></i> <input type="hidden"
											id="WPOrderDate"> </label>
									</div></td>
							</tr>
							<tr>
								<td><label for="bmResolutionNumber"><spring:message
											code="bill.resolution.no" /></label></td>
								<td><form:input type="text" path="resolutionNumber"
										class="form-control" name="bmResolutionNumber"
										id="bmResolutionNumber" disabled="${viewMode}" /></td>
								<td><div class="input-group">
										<form:input path="resolutionDate"
											class="datepicker form-control" name="bmResolutionDate"
											id="bmResolutionDate" readonly="true" disabled="${viewMode}" />
										<label class="input-group-addon" for="bmResolutionDate"><i
											class="fa fa-calendar"></i> <input type="hidden"
											id="resolutionDate"> </label>
									</div></td>
							</tr>
						</thead>
					</table>
				</div>
				<form:hidden path="departmentId" id="departmentId" />
				<form:hidden path="templateExistFlag" id="templateExistFlag" />
				<c:if test="${!viewMode}">
				</c:if>
				<form:hidden id="transBcId" path="transHeadFlagBudgetCode" />
				<form:hidden id="transAcId" path="transHeadFlagAccountCode" />

				<h4 id="">
					<spring:message code="bill.expenditure.details"
						text="Expenditure Details" />
				</h4>
				<div class="table-responsive" id="expenditureDetTable"
					style="overflow: visible;">


					<table id="expDetailTable"
						class="table table-bordered table-striped expDetailTableClass">
						<tbody>

							<tr>
								<th scope="col" width="55%"><spring:message
										code="bill.expenditure.head" text="Expenditure Head" /><span
									class="mand">*</span></th>

								<th scope="col" width="15%"><spring:message
										code="bill.amount" /> <span class="mand">*</span></th>
								<th scope="col" width="15%"><spring:message
										code="bill.sanctioned.amount" /><span class="mand">*</span></th>
								<th scope="col" width="5%"><spring:message
										code="bill.budget" /></th>
								<c:if
									test="${!viewMode && accountBillEntryBean.depositFlag ne 'Y'}">
									<th scope="col" width="10%"><spring:message
											code="bill.action" /></th>
								</c:if>
								<c:if test="${accountBillEntryBean.depositFlag eq 'Y'}">
								</c:if>
							</tr>

							<c:choose>
								<c:when test="${mode eq 'create'}">
									<c:forEach items="${projectedExpenditureList}" var="prExpList"
										varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>

										<tr class="expenditureClass" id="exptr0">
											<form:hidden id="expId${count}"
												path="expenditureDetailList[${count}].id"
												value='${accountBillEntryBean.expenditureDetailList[count].id}' />
											<form:hidden id="expenditureId${count}"
												path="expenditureDetailList[${count}].expenditureId" />
											<form:hidden id="prExpId${count}"
												path="expenditureDetailList[${count}].projectedExpenditureId"
												class="prExpId" />
											<form:hidden id="newBalance${count}"
												path="expenditureDetailList[${count}].newBalanceAmount"
												class="newBalance" />

											<c:if test="${accountBillEntryBean.depositFlag ne 'Y'}">
												<td><form:hidden id="pacHeadIdHidden${count}"
														path="expenditureDetailList[${count}].pacSacHeadId" /> <form:select
														id="expenditureBudgetCode${count}"
														path="expenditureDetailList[${count}].budgetCodeId"
														class="form-control chosen-select-no-results"
														onchange="validateExpAccountHead()" disabled="${viewMode}">
														<form:option value="">
															<spring:message code="account.common.select" />
														</form:option>
														<c:forEach items="${pacMap}" varStatus="status"
															var="pacItem">
															<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
														</c:forEach>
													</form:select></td>
											</c:if>

											<c:if test="${accountBillEntryBean.depositFlag eq 'Y'}">
												<form:hidden id="expenditureBudgetCode${count}"
													path="expenditureDetailList[${count}].budgetCodeId" />
												<td><form:hidden id="pacHeadIdHidden${count}"
														path="expenditureDetailList[${count}].pacSacHeadId" /> <form:select
														id="expenditureBudgetCode${count}"
														path="expenditureDetailList[${count}].budgetCodeId"
														class="form-control chosen-select-no-results"
														disabled="true">
														<form:option value="">
															<spring:message code="account.common.select" />
														</form:option>
														<c:forEach items="${pacMap}" varStatus="status"
															var="pacItem">
															<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
														</c:forEach>
													</form:select></td>
											</c:if>

											<td><form:input id="actAmt${count}"
													path="expenditureDetailList[${count}].actualAmount"
													onchange="getAmountFormatInDynamic((this),'actAmt')"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onblur="enableViewBudget(${count})" disabled="${viewMode}"
													cssClass="form-control mandColorClass text-right amount" />
											</td>
											<td><form:input id="bchChargesAmt${count}"
													path="expenditureDetailList[${count}].billChargesAmount"
													onchange="getAmountFormatInDynamic((this),'bchChargesAmt')"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onblur="calculateDisallowedAmt(${count})"
													disabled="${viewMode}"
													class="form-control mandColorClass text-right amount" /></td>

											<form:hidden id="disallowedAmt${count}"
												path="expenditureDetailList[${count}].disallowedAmount" />
											<td class="text-center">
												<button type="button" class="btn btn-primary btn-sm viewExp"
													disabled="disabled"
													onclick="viewExpenditureDetails(${count})"
													id="viewExpDet${count}">
													<i class="fa fa-eye" aria-hidden="true"></i>
												</button>

											</td>
											<c:if
												test="${!viewMode && accountBillEntryBean.depositFlag ne 'Y'}">
												<td class="text-center">
													<button data-placement="top" title="Add"
														class="btn btn-success btn-sm addbtn"
														id="addButton${count}">
														<i class="fa fa-plus-circle"></i>
													</button>
													<button data-placement="top" title="Delete"
														class="btn btn-danger btn-sm delButton"
														id="delButton${count}">
														<i class="fa fa-trash-o"></i>
													</button>
												</td>
											</c:if>

											<form:hidden id="budgetFlag${count}" path="" />
											<form:hidden id="notEnoughBudgetFlag${count}" path="" />

										</tr>
									</c:forEach>
								</c:when>
								<c:when test="${mode eq 'update'}">
									<c:forEach items="${editData.expenditureDetailList}"
										var="expenditure" varStatus="status">
										<c:set value="${status.index}" var="count" />
										<tr class="expenditureClass" id="exptr0">
											<form:hidden id="expId${count}"
												path="expenditureDetailList[${count}].id"
												value='${accountBillEntryBean.expenditureDetailList[count].id}' />
											<form:hidden id="expenditureId${count}"
												path="expenditureDetailList[${count}].expenditureId" />
											<form:hidden id="prExpId${count}"
												path="expenditureDetailList[${count}].projectedExpenditureId"
												class="prExpId" />
											<form:hidden id="newBalance${count}"
												path="expenditureDetailList[${count}].newBalanceAmount"
												class="newBalance" />
											<td><form:hidden id="pacHeadIdHidden${count}"
													path="expenditureDetailList[${count}].pacSacHeadId" /> <form:select
													id="expenditureBudgetCode${count}"
													path="expenditureDetailList[${count}].budgetCodeId"
													class="form-control chosen-select-no-results"
													disabled="${viewMode}">
													<form:option value="">
														<spring:message code="account.common.select" />
													</form:option>
													<c:forEach items="${expenditureHeadMap}" varStatus="status"
														var="pacItem">
														<c:choose>
															<c:when test="${expenditure.sacHeadId eq pacItem.key}">
																<form:option value="${pacItem.key}"
																	code="${pacItem.key}" selected="selected">${pacItem.value}</form:option>
															</c:when>
															<c:otherwise>
																<form:option value="${pacItem.key}"
																	code="${pacItem.key}">${pacItem.value}</form:option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</form:select></td>
											<td><form:input id="actAmt${count}"
													path="expenditureDetailList[${count}].actualAmount"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onblur="enableViewBudget(${count})" disabled="${viewMode}"
													cssClass="form-control mandColorClass text-right amount" />
											</td>
											<td><form:input id="bchChargesAmt${count}"
													path="expenditureDetailList[${count}].billChargesAmount"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onblur="calculateDisallowedAmt(${count})"
													disabled="${viewMode}"
													class="form-control mandColorClass text-right amount" /></td>
											<form:hidden id="disallowedAmt${count}"
												path="expenditureDetailList[${count}].disallowedAmount" />
											<td class="text-center">
												<button type="button" class="btn btn-primary btn-sm viewExp"
													disabled="disabled"
													onclick="viewExpenditureDetails(${count})"
													id="viewExpDet${count}">
													<i class="fa fa-eye" aria-hidden="true"></i>
												</button>
											</td>
											<c:if test="${!viewMode}">
												<td class="text-center">
													<button data-placement="top" title="Add"
														class="btn btn-success btn-sm addbtn"
														id="addButton${count}">
														<i class="fa fa-plus-circle"></i>
													</button>
													<button data-placement="top" title="Delete"
														class="btn btn-danger btn-sm delButton"
														id="delButton${count}">
														<i class="fa fa-trash-o"></i>
													</button>
												</td>
											</c:if>
											<form:hidden id="budgetFlag${count}" path="" />
											<form:hidden id="notEnoughBudgetFlag${count}" path="" />
										</tr>
									</c:forEach>
								</c:when>
							</c:choose>
						</tbody>
					</table>
					<div class="popUp"></div>
				</div>

				<h4 id="">
					<spring:message code="account.bill.deduction.detail"
						text="Deduction Details" />
				</h4>


				<div class="table-responsive" id="deductionDetTable"
					style="overflow: visible;">
					<table id="dedDetailTable"
						class="table table-bordered table-striped dedDetailTableClass">
						<tbody>
							<tr>
								<th scope="col" style="width: 33%;"><spring:message
										code="account.bill.deduction.head" text="Deduction Heads" /></th>
								<%-- <c:if test="${mode eq 'update'}"> --%>
								<th scope="col" width="33%" style="" id="dedExpenHeadField"><spring:message
										code="" text="Expenditure Account Head" /></th>
								<%-- </c:if> --%>

								<th scope="col" width="21%"><spring:message
										code="bill.amount" text="Amount" /></th>
								<c:if test="${!viewMode}">
									<th scope="col" width="12%"><spring:message
											code="bill.action" text="Action" /></th>
								</c:if>
							</tr>
							<c:choose>
								<c:when test="${mode eq 'create'}">
									<c:forEach items="${deductionDetailList}" var="dedDetList"
										varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>
										<form:hidden path="deductionDetailList[${count}].id"
											value='${accountBillEntryBean.deductionDetailList[count].id}' />
										<tr class="deductionClass" id="deduction0">
											<td><form:select id="dedPacHeadId${count}"
													path="deductionDetailList[${count}].budgetCodeId"
													class="form-control chosen-select-no-results"
													disabled="${viewMode}"
													onchange="validateDedAccountHead(${count})">
													<form:option value="">
														<spring:message code="account.common.select" text="select" />
													</form:option>
													<c:forEach items="${dedPacMap}" varStatus="status"
														var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
													</c:forEach>
												</form:select></td>


											<td style="" id="dedExpenHeadFielddisplay"><form:select
													id="bchId${count}"
													path="deductionDetailList[${count}].bchId"
													class="form-control" disabled="${viewMode}"
													onchange="validateDedExpenditureAccountHead(${count})">
													<c:forEach items="${dedutionExpHeadMap}" varStatus="status"
														var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input id="deductionAmt${count}"
													path="deductionDetailList[${count}].deductionAmount"
													readonly="false" class="form-control text-right amount"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="getAmountFormatInDynamic((this),'deductionAmt')"
													onblur="calculateDeductionAmt(${count})" /></td>
											<c:if test="${!viewMode}">
												<td class="text-center"><a data-placement="top"
													title="Add" class="btn btn-success btn-sm addDedButton"
													id="addDedButton${count}"><i class="fa fa-plus-circle"></i></a>
													<a data-placement="top" title="Delete"
													class="btn btn-danger btn-sm delDedButton"
													id="delDedButton${count}"><i class="fa fa-trash-o"></i></a>

												</td>
											</c:if>
										</tr>
									</c:forEach>
								</c:when>
								<c:when test="${mode eq 'update'}">
									<c:forEach items="${editData.deductionDetailList}"
										var="deduction" varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>
										<form:hidden path="deductionDetailList[${count}].id"
											value='${accountBillEntryBean.deductionDetailList[count].id}' />
										<tr class="deductionClass" id="deduction0">
											<td><form:hidden path="deductionDetailList[${count}].id"
													id="deleteDedId${count}" /> <form:select
													id="dedPacHeadId${count}"
													path="deductionDetailList[${count}].budgetCodeId"
													class="form-control chosen-select-no-results"
													disabled="${viewMode}"
													onchange="validateDedAccountHead(${count})">
													<form:option value="">
														<spring:message code="account.common.select" />
													</form:option>
													<c:forEach items="${deductionHeadMap}" varStatus="status"
														var="pacItem">
														<c:choose>
															<c:when test="${deduction.sacHeadId eq pacItem.key}">
																<form:option value="${pacItem.key}"
																	code="${pacItem.key}" selected="selected">${pacItem.value}</form:option>
															</c:when>
															<c:otherwise>
																<form:option value="${pacItem.key}"
																	code="${pacItem.key}">${pacItem.value}</form:option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</form:select></td>

											<%-- <td><form:select id="bchId${count}"
													path="deductionDetailList[${count}].bchId"
													class="form-control" disabled="${viewMode}"
													onchange="validateDedExpenditureAccountHead(${count})">
													<c:forEach items="${dedutionExpHeadMap}" varStatus="status"
														var="pacItem">
														<form:option value="${pacItem.key}" code="${pacItem.key}">${pacItem.value}</form:option>
													</c:forEach>
												</form:select></td> --%>
											<td><form:input id="deductionAmt${count}"
													path="deductionDetailList[${count}].deductionAmount"
													readonly="false" class="form-control text-right amount"
													onkeypress="return hasAmount(event, this, 13, 2)"
													onchange="getAmountFormatInDynamic((this),'deductionAmt')"
													onblur="calculateDeductionAmt(${count})" /></td>
											<c:if test="${!viewMode}">
												<td><a data-placement="top" title="Add"
													class="btn btn-success btn-sm addDedButton"
													id="addDedButton${count}"><i class="fa fa-plus-circle"></i></a>
													<a data-placement="top" title="Delete"
													class="btn btn-danger btn-sm delDedButton"
													id="delDedButton${count}"><i class="fa fa-trash-o"></i></a>

												</td>
											</c:if>
										</tr>
									</c:forEach>
								</c:when>

							</c:choose>
						</tbody>
					</table>
				</div>
						
						
			&nbsp;
			<c:if test="${mode eq 'create'}">
					<div id="disallowedRemarkDiv">
						<div class="form-group">
							<label for="disallowedRemark"
								class="col-sm-2 control-label required-control"><spring:message
									code="" text="Disallowed Remark" /></label>
							<div class="col-sm-10">
								<form:textarea id="disallowedRemark" path="disallowedRemark"
									disabled="true" class="form-control" />
								<form:hidden id="disallowedRemarkHidden" path="disallowedRemark"
									class="form-control" />
							</div>
						</div>
					</div>
				</c:if>

				<c:if test="${(mode eq 'update')}">
					<div id="disallowedRemarkDivUpd">
						<div class="form-group">
							<label for="disallowedRemark"
								class="col-sm-2 control-label required-control"><spring:message
									code="" text="Disallowed Remark" /></label>
							<div class="col-sm-10">
								<form:textarea id="disallowedRemarkUpd" path="disallowedRemark"
									disabled="true" class="form-control" />
								<form:hidden id="disallowedRemarkHiddenUpd"
									path="disallowedRemark" class="form-control" />
							</div>
						</div>
					</div>
				</c:if>
				<div class="form-group">


					<label for="bmNarration"
						class="col-sm-2 control-label required-control"><spring:message
							code="bill.narration" /></label>
					<div class="col-sm-10">
						<form:textarea path="narration" class="form-control"
							name="bmNarration" id="bmNarration" maxLength="1400"
							disabled="${viewMode}" style="font-size:10pt;"/>
					</div>
				</div>

				<div class="form-group" id="reload">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="bill.upload.docs" text="Invoice Upload" /></label>

					<div class="col-sm-3 text-left">
						<form:hidden path="attachments[0].documentName" />
						<apptags:formField fieldType="7"
							fieldPath="attachments[0].uploadedDocumentPath" currentCount="0"
							showFileNameHTMLId="true" folderName="0"
							fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM">
						</apptags:formField>
						<small class="text-blue-2"><spring:message code="account.uploadInvoice.msg" text="(Upload Invoice upto 1 MB and only PDF, JPG, JPEG,PNG)" /></small>
					</div>

					<c:if test="${mode eq 'update'}">
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="scheme.document.name" text="" /></th>
										<th><spring:message code="scheme.view.document" text="" /></th>
										<th><spring:message code="scheme.action" text=""></spring:message>
										</th>
									</tr>
									<c:forEach items="${accountBillEntryBean.attachDocsList}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}"
													filePath="${lookUp.attPath}"
													actionUrl="AccountBillEntry.html?Download" /></td>
											<td class="text-center"><a href='#' id="deleteFile"
												onclick="return false;" class="btn btn-danger btn-sm"><i
													class="fa fa-trash"></i></a> <form:hidden path=""
													value="${lookUp.attId}" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</c:if>
				</div>

				<c:set value="${accountBillEntryBean.authorizationStatus}"
					var="checker"></c:set>
				<c:if
					test="${checker eq 'Rejected' && !(accountBillEntryBean.authorizationMode eq 'Auth') }">
					<div class="form-group">
						<label for="authorizerRemark" class="col-sm-2 control-label"><spring:message
								code="" text="Authorizer Remark" /></label>
						<div class="col-sm-10">
							<form:textarea id="authorizerRemark" path="checkerRemarks"
								disabled="true" class="form-control" />
						</div>
					</div>
				</c:if>
				<h4 id="">
					<spring:message code="account.bill.summary" text="Bill Summary" />
				</h4>
				<div class="table-responsive" id="totalsTable">
					<table id="totalAmountsTable"
						class="table table-bordered table-striped totalTabClass">
						<thead>
							<tr>
								<th scope="col" width="30"><spring:message
										code="account.bill.total.amt" text="Invoice Amount" /></th>
								<th scope="col" width="30"><spring:message
										code="account.bill.total.sanction.amt"
										text=" Sanctioned Amount" /></th>
								<th scope="col" width="30"><spring:message
										code="account.bill.total.disallow.amt"
										text="Disallowed amount" /></th>
								<th scope="col" width="30"><spring:message
										code="account.bill.total.deduction" text=" Deducted Amount" /></th>
								<th scope="col" width="30"><spring:message
										code="account.bill.net.payable" text="Net Payable" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><form:hidden id="amountTotHidden" path="" /> <form:input
										id="amountTot" path="billTotalAmount" readonly="true"
										class="form-control text-right"
										onkeypress="return hasAmount(event, this, 13, 2)" /></td>
								<td>
									<%-- <form:hidden id="sanctionedTotHidden"
										path="totalSanctionedAmount" /> --%> <form:input
										id="sanctionedTot" path="totalSanctionedAmount"
										readonly="true" class="form-control text-right"
										onkeypress="return hasAmount(event, this, 13, 2)" />
								</td>
								<td><form:input id="disallowedAmtTot"
										path="totalDisallowedAmount" readonly="true"
										class="form-control text-right"
										onkeypress="return hasAmount(event, this, 13, 2)" /></td>
								<td><form:input id="deductionsTot" path="totalDeductions"
										readonly="true" class="form-control text-right"
										onkeypress="return hasAmount(event, this, 13, 2)" /></td>
								<c:if test="${mode eq 'create'}">
									<td>
										<%-- <form:hidden id="netPayableHidden" path="netPayable" /> --%>
										<form:input id="bmTotAmt" path="netPayable" readonly="true"
											class="form-control text-right"
											onkeypress="return hasAmount(event, this, 13, 2)" />
									</td>
								</c:if>
								<c:if test="${mode eq 'update'}">
									<td>
										<%-- <form:hidden id="netPayableHidden" path="netPayable"
											value="${accountBillEntryBean.billTotalAmount - (accountBillEntryBean.totalDisallowedAmount+accountBillEntryBean.totalDeductions)}" /> --%>
										<form:input id="bmTotAmt" path="netPayable"
											value="${accountBillEntryBean.billTotalAmount - (accountBillEntryBean.totalDisallowedAmount+accountBillEntryBean.totalDeductions)}"
											readonly="true" class="form-control text-right"
											onkeypress="return hasAmount(event, this, 13, 2)" />
									</td>
								</c:if>
							</tr>
						</tbody>

					</table>
				</div>
					&nbsp;
						<c:if test="${!(accountBillEntryBean.authorizationMode eq Auth) }">
					<div class="form-group" id="authMode">
						<label for="" class="col-sm-2 control-label required-control"><spring:message
								code="account.bill.final.decision" text="Final Decision" /></label>
						<div class="col-sm-4">
							<label class="radio-inline"><form:radiobutton
									checked="checked" path="checkerAuthorization"
									name="checkerAuthorization" cssClass="set-radio" value="Y" />
								<spring:message code="" text="Sanctioned" /></label> <label
								class="radio-inline"><form:radiobutton
									path="checkerAuthorization" name="checkerAuthorization"
									cssClass="set-radio" value="R" /> <spring:message code=""
									text="Rejected" /></label>
						</div>
					</div>
					<div class="form-group">
						<label for="checkerRemarks"
							class="col-sm-2 control-label required-control"><spring:message
								code="account.bill.remarks" text="Remarks" /></label>
						<div class="col-sm-4">
							<form:textarea id="checkerRemarks" path="checkerRemarks"
								disabled="" class="form-control" />
						</div>
					</div>

				</c:if>
				<form:hidden path="authorizationMode" id="authorizationMode" />

				<div class="text-center padding-10">
					<c:choose>
						<c:when test="${accountBillEntryBean.authorizationMode eq Auth }">

							<button type="button" class="btn btn-success btn-submit"
								onclick="saveBillEntry(this)">
								<spring:message code="account.bankmaster.save" text="Save" />
							</button>
							<c:if test="${mode eq 'create'}">
								<input type="button" id="Resetdf" onclick="ResetForm()"
								class="btn btn-warning createData" value='<spring:message
										code="account.bankmaster.reset" text="Reset" />'></input>
							</c:if>
							<c:if test="${accountBillEntryBean.advanceFlag eq 'Y'}">
								<spring:url var="cancelButtonURL" value="AdvanceEntry.html" />
								<a class="btn btn-danger" href="${cancelButtonURL}"><spring:message
										code="account.bankmaster.back" text="Back" /></a>
							</c:if>
							<c:if
								test="${(accountBillEntryBean.depositFlag eq 'Y') && (accountBillEntryBean.workOrderFlag eq 'N')}">
								<spring:url var="cancelButtonURL" value="AccountDeposit.html" />
								<a role="button" class="btn btn-danger"
									href="${cancelButtonURL}"><spring:message
										code="account.bankmaster.back" text="Back" /></a>
							</c:if>
							<c:if
								test="${(accountBillEntryBean.depositFlag eq 'Y') && (accountBillEntryBean.workOrderFlag ne 'N') }">
								<spring:url var="cancelButtonURL"
									value="AccountTenderEntry.html" />
								<a role="button" class="btn btn-danger"
									href="${cancelButtonURL}"><spring:message
										code="account.bankmaster.back" text="Back" /></a>
							</c:if>
							<c:if
								test="${(accountBillEntryBean.advanceFlag ne 'Y') && (accountBillEntryBean.depositFlag ne 'Y') && (accountBillEntryBean.grantFlag ne 'Y') && (accountBillEntryBean.loanFlag ne 'Y')}">
								<input type="button" class="btn btn-danger"
									onclick="window.location.href='AccountBillEntry.html'"
									value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
							</c:if>

							<c:if test="${(accountBillEntryBean.grantFlag eq 'Y')}">
								<button type="button" class="btn btn-danger"
									data-toggle="tooltip" data-original-title="Back"
									onclick="backGrantMasterForm();">
									<i class="fa fa-chevron-circle-left padding-right-5"
										aria-hidden="true"></i><spring:message
										code="account.bankmaster.back" text="Back" />
								</button>
							</c:if>
							<c:if test="${(accountBillEntryBean.loanFlag eq 'Y')}">
								<button type="button" class="btn btn-danger"
									
									onclick="backLoanMaster();">
									<i class="fa fa-chevron-circle-left padding-right-5"
										aria-hidden="true"></i><spring:message
										code="account.bankmaster.back" text="Back" />
								</button>

							</c:if>
						</c:when>
						<c:when
							test="${!(accountBillEntryBean.authorizationMode eq Auth) }">
							<button type="button" class="btn btn-success btn-submit"
								onclick="saveBillEntry(this)">
								<spring:message code="budget.allocation.master.conform"
									text="Confirm" />
							</button>
							<input type="button" class="btn btn-danger"
								onclick="window.location.href='AccountBillAuthorization.html'"
								value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
						</c:when>
					</c:choose>

				</div>
				<form:hidden path="isMakerChecker" id="isMakerChecker" />
				<form:hidden path="checkerAuthorization" id="checkerAuthorization" />
			</form:form>
		</div>
	</div>
</div>
<c:if test="${accountBillEntryBean.hasError eq 'false'}">
	</div>
</c:if>
<script>
$(document).on('keydown', '#dedDetailTable tr td', function (event)
 {
	  if(event.keyCode == 13){ 
		  //e.preventDefault();//for enter key 13
	    var currentDiv = event.target;
	    $(currentDiv).parents("td").next("td").find(".btn-success").click();
	    return false; // <== here
	  }
	});

</script>