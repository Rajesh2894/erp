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
<script src="js/account/advanceEntry.js"></script>
<script>
$(document).ready(function() {
	$("#advanceDate").keyup(function(e){
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
				<spring:message code="advance.management.master.title" text=""></spring:message>
			</h2>
			<apptags:helpDoc url="AdvanceRequisition.html" helpDocRefURL="AdvanceRequisition.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbAcAdvanceEntry"
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
					<form:hidden path="prAdvEntryId" />
					<label class="col-sm-2 control-label "><spring:message
							code="advance.management.master.advancenumber" text="" /></label>

					<div class="col-sm-4">
						<form:input cssClass="form-control  text-right"
							onkeypress="return hasAmount(event, this, 13, 2)"
							id="advanceNumber" path="advanceNumber"></form:input>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="advance.management.master.advancedate" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="advanceDate" id="advanceDate"
								class="form-control  datepicker" value="" disabled="${viewMode}" maxLength="10" />
							<label class="input-group-addon" for="advanceDate"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span><input
								type="hidden" id="advanceDate"></label>
						</div>
					</div>

				</div>

				<div class="form-group">

					<form:hidden path="prAdvEntryId" />
					<label class="col-sm-2 control-label" for="name"><spring:message
							code="advance.management.master.name" text="" /></label>
					<div class="col-sm-4">
						<form:select id="name" path=""
							cssClass="form-control chosen-select-no-results"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="advance.management.master.select" text="" />
							</form:option>
							<c:forEach items="${vendorList}" var="vendorData">
								<form:option value="${vendorData.vmVendorid}">${vendorData.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="advance.management.master.advanceamount" text="" /></label>
					<div class="col-sm-4">
						<form:input cssClass="form-control  text-right"
							onkeypress="return hasAmount(event, this, 13, 2)"
							id="advanceAmount" path="advanceAmount"></form:input>
					</div>

				</div>

				<div class="form-group">

					<form:hidden path="prAdvEntryId" />
					<label class="col-sm-2 control-label"><spring:message
							code="advance.management.master.advancetype" text="" /></label>

					<div class="col-sm-4">
						<form:select path="advanceTypeId" class="form-control "
							id="advanceType" disabled="${viewMode}">
							<form:option value="">
								<spring:message code="advance.management.master.select" text="" />
							</form:option>
							<c:forEach items="${AdvanceType}" varStatus="status"
								var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="advance.management.master.advancestatus" text="" /></label>

					<div class="col-sm-4">
						<form:select path="cpdIdStatus" class="form-control "
							id="cpdIdStatus" disabled="${viewMode}">
							<form:option value="">
								<spring:message code="advance.management.master.select" text="" />
							</form:option>
							<c:forEach items="${activeDeActiveMap}" varStatus="status"
								var="levelChild">
								<c:if test="${levelChild.lookUpCode eq 'A'}">
									<form:option value="${levelChild.lookUpCode}"
										code="${levelChild.lookUpCode}">
										<spring:message code="accounts.open" text="Open" />
									</form:option>
								</c:if>
								<c:if test="${levelChild.lookUpCode eq 'I'}">
									<form:option value="${levelChild.lookUpCode}"
										code="${levelChild.lookUpCode}">
										<spring:message code="account.close" text="Close" />
									</form:option>
								</c:if>
							</c:forEach>

						</form:select>
					</div>

				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchAdvanceEntryData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL" value="AdvanceEntry.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
					<button type="button" class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
					</button>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>

