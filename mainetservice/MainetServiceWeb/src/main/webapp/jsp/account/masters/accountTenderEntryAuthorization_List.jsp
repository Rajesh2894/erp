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
<script src="js/account/accountTenderEntryAuthorization.js"
	type="text/javascript"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<script>
	$(document).ready(function(){
		 $('.hasMyNumber').keyup(function () {
			    this.value = this.value.replace(/[^0-9.]/g,'');
			    $(this).attr('maxlength','13');
		  });
	});
</script>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.pay.tds.work.order"
					text="Work Order Entry Authorization" />
			</h2>
		<apptags:helpDoc url="AccountTenderEntryAuthorization.html" helpDocRefURL="AccountTenderEntryAuthorization.html"></apptags:helpDoc>	
		</div>
		<div class="widget-content padding">
			<form:form action="" modelAttribute="accountTenderEntryBean"
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
					<form:hidden path="trTenderId" id="trTenderId" />

					<label class="control-label col-sm-2 " for="trTenderNo"><spring:message
							code="account.tenderentrydetails.tenderno" text="" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="" id="trTenderNo"
							class="form-control hasMyNumber" disabled="${viewMode}" />
					</div>

					<label class="control-label  col-sm-2 " for="vmVendorid"><spring:message
							code="account.tenderentrydetails.VendorEntry" text="Vendor Name" /></label>
					<div class="col-sm-4">
						<form:select path="" id="vmVendorid" class="form-control "
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="" text="Select Vendor Name" />
							</form:option>
							<c:forEach items="${vendorMap}" varStatus="status"
								var="vendorMap">
								<form:option value="${vendorMap.key }" code="${vendorMap.key }">${ vendorMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<form:hidden path="" id="trTenderId" />

					<label class="control-label col-sm-2" for=""><spring:message
							code="account.tenderentrydetails.tendertype" text="" /></label>
					<div class="col-sm-4">
						<form:select id="trTypeCpdId" path="" class="form-control"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="" text="Select Work Order Type" />
							</form:option>
							<c:forEach items="${tenderType}" varStatus="status"
								var="tenderTypeLookUp">
								<form:option value="${tenderTypeLookUp.lookUpId }"
									code="${tenderTypeLookUp.lookUpId }">${tenderTypeLookUp.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label  col-sm-2 " for="sacHeadId"><spring:message
							code="account.tenderentrydetails.budgethead"
							text="Budget Head" /></label>
					<div class="col-sm-4">
						<form:select id="sacHeadId" path=""
							cssClass="form-control chosen-select-no-results"
							disabled="${viewmode}" data-rule-required="true">
							<form:option value="">
								<spring:message code="" text="Select Account Head" />
							</form:option>
							<c:forEach items="${accountHeadsMap}" varStatus="status"
								var="pacItem">
								<form:option value="${pacItem.key }" code="${pacItem.key }">${pacItem.value} </form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<form:hidden path="trTenderId" id="trTenderId" />

					<label class="control-label col-sm-2 " for="trTenderAmount"><spring:message
							code="" text="Work Order Amount" /></label>
					<div class="col-sm-4">
						<form:input type="text" path="" id="trTenderAmount"
							class="form-control" disabled="${viewMode}"
							onkeypress="return hasAmount(event, this, 13, 2)" />
					</div>

					<label class="control-label  col-sm-2 " for="statusId"><spring:message
							code="account.deposit.status" text="Status" /></label>
					<div class="col-sm-4">
						<form:select path="" id="statusId" class="form-control "
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="" text="Select Status" />
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
						onclick="searchWorkOrderEntryData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL"
						value="AccountTenderEntryAuthorization.html" />
					<a class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>


