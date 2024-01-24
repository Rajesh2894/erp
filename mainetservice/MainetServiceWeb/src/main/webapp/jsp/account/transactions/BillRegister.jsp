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
<script src="js/account/accountBillRegister.js"></script>

<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script>
$(function() {
	$("#frmdate").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	$("#tocdate").keyup(function(e){
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
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="accounts.registerof.bill.register" text="Bill Register" />
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

			<form:form action="" modelAttribute="aBillRegisterBean"
				class="form-horizontal" id="BillReceiptPrintId">
				<div class="form-group">

					<label class="control-label col-sm-2 required-control" for="trTenderDate"><spring:message
							code="account.fromDate" text="From Date" /></label>


					<div class="col-sm-4">


						<div class="input-group">
							<input class="form-control datepicker" id="frmdate" path=" "
								maxlength="10" > <label class="input-group-addon"
								for="trasaction-date-icon30"><i class="fa fa-calendar"></i></label>
						</div>
					</div>


					<label class="control-label col-sm-2 required-control" for="trTenderDate"><spring:message
							code="account.todate" text="To Date" /></label>
					
					<div class="col-sm-4">

						<div class="input-group">
							<input class="form-control datepicker" id="tocdate" path=" "
							maxlength="10" > <label class="input-group-addon"
								for="trasaction-date-icon30"><i class="fa fa-calendar"></i></label>
						</div>

					</div>
					
					</div>
					
					<div class="form-group">
					<label for="billTyp"
						class="col-sm-2 control-label required-control"><spring:message
							code="bill.type" /></label>
					<div class="col-sm-4">
						<form:select type="select" path=""
							class="form-control chosen-select-no-results mandColorClass"
							name="billTyp" id="billTyp">
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

					<div class="text-center">
						<input type="button" id="PrintButn" class="btn btn-blue-2"
							value="<spring:message code="account.financial.view.report" text="View Report" />" title="View Report"/>
							
							
							
						<button type="button" class="btn btn-warning resetSearch"
							onclick="window.location.href ='BillRegister.html'" title="Reset">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
			</form:form>
		</div>
	</div>
</div>
