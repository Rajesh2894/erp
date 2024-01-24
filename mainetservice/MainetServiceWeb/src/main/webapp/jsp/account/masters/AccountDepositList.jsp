<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/AccountDeposit.js"></script>

<script>
$(document).ready(function() {
	
$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,	
		maxDate: '-0d',
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
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.deposite.title" text="Deposit" />
			</h2>
		<apptags:helpDoc url="AccountDeposit.html" helpDocRefURL="AccountDeposit.html"></apptags:helpDoc>	
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="accountDepositBean"
				class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">
					<form:hidden path="depId" id="depId" />

					<label class="control-label col-sm-2 " for="depNo"><spring:message
							code="account.deposit.depNo" text="Deposit Number" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="" id="depNo"
							class="form-control hasMyNumber" disabled="${viewMode}" />
					</div>

					<label class="control-label col-sm-2" for="cpdDepositType"><spring:message
							code="account.deposit.deposit.type" text="Deposit Type" /></label>
					<div class="col-sm-4">
						<form:select id="cpdDepositType" path="" disabled="${viewMode}"
							class="form-control mandColorClass chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="account.select.deposit.type" text="Select Deposit Type" />
							</form:option>
							<c:forEach items="${depositType}" varStatus="status"
								var="depositTypeLookUp">
								<form:option value="${depositTypeLookUp.lookUpId }"
									code="${depositTypeLookUp.lookUpId }">${depositTypeLookUp.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<form:hidden path="" id="depId" />

					<label class="control-label col-sm-2 " for="date"><spring:message
							code="account.deposit.dates" text="Deposit Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="date" class="form-control datepicker"
								value="" disabled="${viewMode}"  maxlength="10"/>
							<label class="input-group-addon" for="date"><i
								class="fa fa-calendar"></i><span class="hide">icon</span><input
								type="hidden" id="fromDate"></label>
						</div>
					</div>

					<label class="control-label  col-sm-2 " for="vmVendorid"><spring:message
							code="account.deposite.party.name" text="Name Of The Party" /></label>
					<div class="col-sm-4">
						<form:select path="" id="vmVendorid"
							class="form-control chosen-select-no-results"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="account.select.name.of.party" text="Select Name Of The Party" />
							</form:option>
							<c:forEach items="${vendorMap}" varStatus="status"
								var="vendorMap">
								<form:option value="${vendorMap.key }" code="${vendorMap.key }">${ vendorMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<form:hidden path="" id="depId" />

					<label class="control-label col-sm-2 " for="depAmount"><spring:message
							code="account.deposit.DepositAmount" text="Deposit Amount" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="" id="depAmount"
							onkeypress="return hasAmount(event, this, 13, 2)"
							onchange="getAmountFormatInStatic('depAmount')"
							class="form-control" disabled="${viewMode}" />
					</div>

					<label class="control-label  col-sm-2 " for="sacHeadId"><spring:message
							code="account.cheque.dishonour.account.head" text="Account Head" /></label>
					<div class="col-sm-4">
						<form:select id="sacHeadId" path=""
							cssClass="form-control chosen-select-no-results"
							disabled="${viewmode}" data-rule-required="true">
							<form:option value="">
								<spring:message code="account.select.head" text="Select Account Head" />
							</form:option>
							<c:forEach items="${accountHeadsMap}" varStatus="status"
								var="pacItem">
								<form:option value="${pacItem.key }" code="${pacItem.key }">${pacItem.value} </form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchDepositEntryData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL" value="AccountDeposit.html" />
					<a  class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
					<button type="button" class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
					</button>
					<button type="button" class="btn btn-primary" onclick="exportTemplate();">
						<spring:message code="account.deposit.export.import" text="Export/Import" />
					</button>
				</div>


				<table id="depositGrid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>