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
<script src="js/account/reappropriationOfBudgetAuthorization.js"></script>
<script>
$(function() {
	
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
			<h2>
				<spring:message code="budget.reappropriation.authorization.title"
					text=""></spring:message>
			</h2>
		<apptags:helpDoc url="ReappropriationOfBudgetAuthorization.html" helpDocRefURL="ReappropriationOfBudgetAuthorization.html"></apptags:helpDoc>	
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbAcBudgetReappOfAuthorization"
				class="form-horizontal">


				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>


				<div class="form-group">


					<label class="col-sm-2 control-label required-control"><spring:message
							code="budget.reappropriation.authorization.fromdate" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="fromDate" id="fromDate"
								class="form-control mandColorClass datepicker" value=""
								maxlength="10" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span><input
								type="hidden" id="fromDate"></label>
						</div>
					</div>



					<label class="col-sm-2 control-label required-control"><spring:message
							code="budget.reappropriation.authorization.todate" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="toDate" id="toDate"
								class=" form-control mandColorClass datepicker" value=""
								maxlength="10"/>
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span><input
								type="hidden" id="toDate"></label>
						</div>
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "> <spring:message
							code="budget.reappropriation.master.budgettype" text=""></spring:message>
					</label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="RE" />
						<form:select path="cpdBugtypeId" class="form-control"
							id="cpdBugtypeId" disabled="${viewMode}">
							<form:option value="">
								<spring:message
									code="budget.reappropriation.master.selectbudgettype" text="" />
							</form:option>
							<c:forEach items="${levelMap}" varStatus="status"
								var="levelChild">
								<form:option value="${levelChild.lookUpId}" code="">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>



					<label class="col-sm-2 control-label"><spring:message
							code="budget.reappropriation.authorization.status" text="" /> </label>
					<div class="col-sm-4">

						<form:select path="status" class="form-control" id="status"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message
									code="budget.reappropriation.authorization.selectstatus"
									text="" />
							</form:option>
							<c:forEach items="${activeDeActiveMap}" varStatus="status"
								var="levelChild">
								<c:if test="${levelChild.lookUpCode eq 'A'}">
									<form:option value="${levelChild.lookUpId}"
										code="${levelChild.lookUpCode}">
										<spring:message code="account.pay.tds.disapproved"
											text="Unapproved" />
									</form:option>
								</c:if>
								<c:if test="${levelChild.lookUpCode eq 'I'}">
									<form:option value="${levelChild.lookUpId}"
										code="${levelChild.lookUpCode}">
										<spring:message code="account.pay.tds.approved"
											text="Approved" />
									</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchReappropriationOfBudgetData()" id="search">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL"
						value="ReappropriationOfBudgetAuthorization.html" />
					<a role="button" class="btn btn-warning resetSearch" href="${cancelButtonURL}" id="disAnchor"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>
				<%-- <form:hidden path="isMakerChecker" id="isMakerChecker" /> --%>
				<form:hidden path="isServiceActive" id="isServiceActive" />
			</form:form>
		</div>
	</div>
</div>

