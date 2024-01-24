<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountRecieptRegister.js"
	type="text/javascript"></script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script>
	$(function() {
		$("#toDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		
		$("#fromDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
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
		$("#fromDateId").keyup(function(e){
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
		<div id="content">
    <apptags:breadcrumb></apptags:breadcrumb>
<div class="content" >
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="challan.receipt.challanreport"
					text="Challan Report" />
			</h2>

		</div>
		<div class="widget-content padding">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<form:form action="" modelAttribute="acRecieptRegister"
				class="form-horizontal" id="ReceiptPrintId">
				<div class="form-group">
					
					<label for="select-1492067432449"
						class="col-sm-2 control-label required-control"><spring:message
							code="accounts.chequebookleaf.employee_name" text="Employee Name" />
					</label>
					<div class="col-sm-4">
						<form:select id="EmployeeListid" path="empId"
							cssClass="form-control chosen-select-no-results" disabled=""
							onchange="">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${employeeList}" varStatus="status"
								var="employeeMap">
								<form:option value="${employeeMap.key}"
									code="${employeeMap.key}">${employeeMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
					<div class="form-group">
							<label for="fromDateId" class="col-sm-2 control-label required-control"><spring:message code="day.book.report.fromdate" text="From Date" /></label>
							<div class="col-sm-4">
							<div class="input-group">
							<form:input path="fromDate" id="fromDateId"	cssClass="mandColorClass form-control" data-rule-required="true"  maxlength="10" />
							<label class="input-group-addon mandColorClass" for="fromDateId"><i class="fa fa-calendar"></i> </label>
							</div>
							</div>
							<label for="toDateId" class="col-sm-2 control-label required-control"><spring:message code="budget.reappropriation.authorization.todate" text="To Date" /></label>
							<div class="col-sm-4">
								<div class="input-group"><form:input path="toDate" id="toDateId" cssClass="mandColorClass form-control datepicker" data-rule-required="true"  maxlength="10"/>
									<label class="input-group-addon mandColorClass" for="toDateId"><i class="fa fa-calendar"></i> </label>
								</div>
							</div>

						</div>
				<div class="text-center">
					<input type="button" id="PrintButn" class="btn btn-blue-2"
						value="<spring:message code="account.investment.view" text="View Report" />" />
					
					<button type="button" class="btn btn-warning resetSearch"
						onclick="window.location.href = 'RecieptRegisterController.html'">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>
</div>
