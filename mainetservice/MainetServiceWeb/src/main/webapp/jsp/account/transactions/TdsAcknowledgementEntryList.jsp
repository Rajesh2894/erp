<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/tdsAcknowledgementEntry.js"
	type="text/javascript"></script>
<script>
$(function() {
	$("#fromDateId").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	$("#toDateId").keyup(function(e){
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
				<spring:message code="" text="TDS Acknowledgement Entry" />
			</h2>
		<apptags:helpDoc url="TdsAcknowledgementEntry.html" helpDocRefURL="TdsAcknowledgementEntry.html"></apptags:helpDoc>	
		</div>
		<div class="widget-content padding" id="directPaymentDiv">
				<form:form method="POST" action="TdsAcknowledgementEntry.html"
			cssClass="form-horizontal" name="paymentEntry" id="paymentEntry"
			modelAttribute="paymentEntryDto">
			
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- TDS Type -->
				<div class="form-group">
					<label for="tdsTypeId"
						class="col-sm-2 control-label required-control">TDS Type:</label>
					<div class="col-sm-4">
						<form:select path="tdsTypeId"
							class="form-control chosen-select-no-results chosenReset"
							id="tdsTypeId" data-rule-required="true">
							<option value=""><spring:message code="account.select"
									text="Select" /></option>
							<c:forEach items="${tdsList}" varStatus="status" var="tds">
								<form:option value="${tds.lookUpId}">${tds.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label for="qtrId"
						class="col-sm-2 control-label required-control">Quarter:</label>
					<div class="col-sm-4">
						<form:select path="qtrId"
							class="form-control chosen-select-no-results chosenReset"
							id="qtrId" data-rule-required="true">
							<option value=""><spring:message code="account.select"
									text="Select" /></option>
							<c:forEach items="${qtrList}" varStatus="status" var="qtr">
								<form:option value="${qtr.lookUpId}">${qtr.descLangFirst}</form:option>
							</c:forEach>
					</form:select>
					</div>
				</div>
				<!--From Date-->
				<div class="form-group">
					<label for="fromDateId"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="fromDate" id="fromDateId"
								cssClass="mandColorClass form-control" data-rule-required="true" maxlength="10"></form:input>

							<label class="input-group-addon mandColorClass" for="fromDateId"><i
								class="fa fa-calendar"></i> </label>
						</div>
					</div>
					<!--To Date-->
					<label for="toDateId"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="toDate" id="toDateId"
								cssClass="mandColorClass form-control" data-rule-required="true" maxlength="10"></form:input>
							<label class="input-group-addon mandColorClass" for="toDateId"><i
								class="fa fa-calendar"></i> </label>
						</div>
					</div>

				</div>
				<!-- Operation Button -->
				<div class="text-center padding-bottom-10">
					<button type="button" id="search"
						value="<spring:message code="search.data"/>"
						class="btn btn-success searchData" onclick="getAckDetails(this)">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="reset" class="btn btn-warning"
						onclick="window.location.href = 'TdsAcknowledgementEntry.html'">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
				</div>
				
				
				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>