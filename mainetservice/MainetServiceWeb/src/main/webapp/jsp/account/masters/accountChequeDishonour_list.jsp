<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/accountChequeDishonour.js"></script>

<script>
$( document ).ready(function() {
	 $("#viewDishonourDetails").hide();
	 
});

$(function() {
$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',
	changeMonth: true,
	changeYear: true,
	maxDate: '0'
});

$(".datepicker").keyup(function(e){
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

	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.chequeDish.cheDishonour" text="Cheque Dishonour"></spring:message>
			</h2>
		<apptags:helpDoc url="AccountChequeDishonour.html" helpDocRefURL="AccountChequeDishonour.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbAccountChequeDishonour"
				class="form-horizontal" id="tbChequeDishonour">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<form:hidden path="dishonourIds" id="dishonourIds" />

				<div class="form-group margin-top-10">
					<label class="control-label col-sm-2"> <spring:message
							code="account.search.type" text="Search Type"></spring:message><span class="mand">*</span></label>
					<div class="col-sm-10">	

						<label for="payInSlipNo" class="radio-inline"><form:radiobutton
								name="serchType" path="serchType" value="P" id="payInSlipNo"
								 /> <spring:message code="account.pay.in.slip" text="Pay-In Slip"></spring:message> </label> 
						<label for="chequeDDNo" class="radio-inline"><form:radiobutton
								name="serchType" path="serchType" value="D" id="chequeDDNo"
								 /><spring:message code="account.cheque.dd" text="Cheque /DD"></spring:message></label>

					</div>
				</div>

				<div class="form-group">

					<label class="control-label  col-sm-2 required-control"> <spring:message
							code="bank.master.accountNo" text="Bank"></spring:message>
					</label>
					<div class="col-sm-4" >
						<form:select id="bankAccount" path="bankAccount"
							class="form-control chosen-select-no-results mandColorClass">
							<form:option value="">
								<spring:message code="account.common.select" text="Select" />
							</form:option>
							<c:forEach items="${bankList}" varStatus="status"
								var="bankAccountMap">
								<form:option value="${bankAccountMap.key}"
									code="${bankAccountMap.key}">${bankAccountMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="account.number" text="Number" /></label>
					<div class="col-sm-4">
						<form:input path="number"
							cssClass="form-control mandColorClass" name=""
							id="number" />
					</div>

				</div>

				<div class="form-group">

					<label for="toDate" class="col-sm-2 control-label"><spring:message
							code="account.transaction.date" text="Transaction Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="date" cssClass="datepicker form-control"
								name="" id="date" maxlength="10" />
							<label class="input-group-addon" for="date"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="trasaction-date-icon30"> </label>
						</div>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="accounts.receipt.transaction.amount" text="Transaction Amount" /></label>
					<div class="col-sm-4">
						<form:input path="amount" cssClass="form-control hasMyNumber"
							name="" id="amount" onchange="getAmountFormatInStatic('amount')" />
					</div>

				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchAccountChequeDishonourData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL"
						value="AccountChequeDishonour.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>

			</form:form>
		</div>
	</div>
</div>

