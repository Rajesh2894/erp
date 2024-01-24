
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/account/transaction/accountBillEntry.js"
	type="text/javascript"></script>
<script>
$(function() {
	var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
$("#fromDate").datepicker({
    dateFormat: 'dd/mm/yy',
	changeMonth: true,
	changeYear: true,
	minDate : disableBeforeDate,
	maxDate: today
});
$("#fromDate").datepicker('setDate', new Date()); 
$("#toDate").datepicker({
    dateFormat: 'dd/mm/yy',
	changeMonth: true,
	changeYear: true,
	minDate : disableBeforeDate,
	maxDate: today
});
$("#toDate").datepicker('setDate', new Date()); 

$("#fromDate").keyup(function(e){
    if (e.keyCode != 8){    
        if ($(this).val().length == 2){
            $(this).val($(this).val() + "/");
        }else if ($(this).val().length == 5){
            $(this).val($(this).val() + "/");
        }
     }
    });
$("#toDate").keyup(function(e){
    if (e.keyCode != 8){    
        if ($(this).val().length == 2){
            $(this).val($(this).val() + "/");
        }else if ($(this).val().length == 5){
            $(this).val($(this).val() + "/");
        }
     }
    });
});


</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<c:if test="${accountBillEntryBean.authorizationMode eq Auth}">
				<h2>
					<spring:message code="account.bill.invoice" text="Invoice /" />
					<strong> <spring:message code="account.bill.billentry"
							text="Bill Entry" /></strong>
				</h2>
			<apptags:helpDoc url="AccountBillEntry.html" helpDocRefURL="AccountBillEntry.html"></apptags:helpDoc>		
			</c:if>
			<c:if test="${accountBillEntryBean.authorizationMode ne Auth}">
				<h2>
					<spring:message code="account.bill.invoice" text="Invoice /" />
					<strong> <spring:message code="account.bill.billauth"
							text="Bill Authorization" /></strong>
				</h2>
			<apptags:helpDoc url="AccountBillAuthorization.html" helpDocRefURL="AccountBillAuthorization.html"></apptags:helpDoc>		
			</c:if>
			
		</div>
		<div class="widget-content padding" id="billEntryDiv">

			<form:form method="POST" action="AccountBillEntry.html"
				cssClass="form-horizontal" name="billEntry" id="billEntry"
				modelAttribute="accountBillEntryBean">

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				<div class="form-group">

					<label for="fromDate" class="col-sm-2 control-label"><spring:message
							code="day.book.report.fromdate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="fromDate" cssClass="form-control"
								 name="fromDate" id="fromDate" maxlength="10" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="trasaction-date-icon30"> </label>
						</div>
					</div>
					<label for="toDate" class="col-sm-2 control-label"><spring:message
							code="day.book.report.todate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="toDate" cssClass=" form-control"
								 name="toDate" id="toDate"  maxlength="10"/>
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="trasaction-date-icon30"> </label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="bmBilltypeCpdId" class="col-sm-2 control-label"><spring:message
							code="bill.type" /></label>
					<div class="col-sm-4">
						<form:select type="select" path="billTypeId"
							class="form-control chosen-select-no-results"
							name="bmBilltypeCpdId" id="bmBilltypeCpdId"
							disabled="${viewMode}">
							<option value=""><spring:message
									code="budget.reappropriation.master.select" text="Select" /></option>
							<c:forEach items="${billTypeList}" varStatus="status"
								var="billType">
								<form:option value="${billType.lookUpId}">${billType.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<form:hidden id="transBcId" path="transHeadFlagBudgetCode" />
					<label class="col-sm-2 control-label"><spring:message
							code="bill.no" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" name="bmBillno"
							id="bmBillno" />

					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="accounts.vendormaster.vendorName" /></label>
					<div class="col-sm-4">
						<form:select path="vendorName"
							class="form-control chosen-select-no-results" name="vmVendorname"
							id="vmVendorname">
							<option value="" selected="true"><spring:message
									code="budget.reappropriation.master.select" text="Select" /></option>
							<c:forEach items="${vendorList}" varStatus="status" var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="account.deposit.Department" text="Department" /></label>
					<div class="col-sm-4">
						<form:select id="departmentId" path="departmentId"
							class="form-control mandColorClass chosen-select-no-results"
							disabled="false">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${departmentList}" var="deptData">
								<form:option value="${deptData.lookUpId}"
									code="${deptData.lookUpCode}">${deptData.defaultVal}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" id="search"
						value="<spring:message code="search.data"/>"
						class="btn btn-success searchData" onclick="searchBillData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<c:if test="${accountBillEntryBean.authorizationMode ne Auth}">
						<button type="button" class="btn btn-warning resetSearch"
							onclick="window.location.href = 'AccountBillAuthorization.html'">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
					</c:if>
					<c:if test="${accountBillEntryBean.authorizationMode eq Auth}">
						<button type="button" class="btn btn-warning resetSearch"
							href= "AccountBillEntry.html">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
						<button type="button" value="Create Data"
							class="btn btn-blue-2 createData">
							<i class="fa fa-plus-circle"></i>
							<spring:message code="account.bankmaster.add" text="Add" />
						</button>
					</c:if>
					<form:hidden path="authorizationMode" id="authorizationMode" />
					<form:hidden path="authorizationStatus" id="authorizationStatus" />

				</div>
				<form:hidden path="isMakerChecker" id="isMakerChecker" />
				<form:hidden path="isServiceActive" id="isServiceActive" />
			</form:form>



			<table id="billEntryGrid"></table>
			<div id="pagered"></div>


		</div>
	</div>

</div>
