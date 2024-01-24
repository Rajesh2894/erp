<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/account/generalbank/bankMaster.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<style>
.tooltip-inner {
	text-align: left;
}
</style>

<c:if test="${tbBankMaster.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
</c:if>
<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="bank.master.title" />
		</h2>
	</div>
	<div class="widget-content padding">
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbBankMaster" name="frmMaster" method="POST"
			action="GeneralBankMaster.html">
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="bankId" />

			<form:hidden path="hasError" />
			<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="errorDivScrutiny">
				<button type="button" class="close" aria-label="Close"
					onclick="closeErrBox()">
					<span aria-hidden="true">&times;</span>
				</button>
				<ul>
					<li><form:errors path="*" /></li>
				</ul>
			</div>

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivIdI" style="display: none;">
				<span id="errorIdI"></span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<c:set var="count" value="0" scope="page" />
			<ul id="ulId">
				<li>
					<fieldset id="divId" class="clear">

						<div class="form-group">
							<form:hidden path="bankId" />
							<label class="control-label col-sm-2" for="bank"><spring:message
									code="account.bankmaster.bankname" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass " id="bank"
									path="bank" data-rule-maxLength="200" data-rule-required="true"></form:input>
							</div>
							<label class="control-label col-sm-2" for="branch"><spring:message
									code="account.bankmaster.branchname" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass " id="branch"
									path="branch" data-rule-maxLength="200"
									data-rule-required="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="ifsc"><spring:message
									code="account.bankmaster.ifsccode" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass " id="ifsc"
									path="ifsc" data-rule-maxLength="200" data-rule-required="true"></form:input>
							</div>
							<label class="control-label  col-sm-2" for="micr"><spring:message
									code="account.bankmaster.micrcode" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass" id="micr"
									path="micr" data-rule-maxLength="200" data-rule-required="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="city"><spring:message
									code="account.bankmaster.city" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass " id="city"
									path="city" data-rule-maxLength="200" data-rule-required="true"></form:input>
							</div>
							<label class="control-label col-sm-2" for="district"><spring:message
									code="account.bankmaster.district" text="District" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass "
									id="district" path="district" data-rule-maxLength="200"
									data-rule-required="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2" for="state"><spring:message
									code="account.bankmaster.state" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass " id="state"
									path="state" data-rule-maxLength="200"
									data-rule-required="true"></form:input>
							</div>

							<label class="control-label col-sm-2" for="address"><spring:message
									code="account.bankmaster.branchaddress" /></label>
							<div class="col-sm-4">
								<form:textarea id="address" path="address"
									class="form-control mandColorClass" data-rule-maxLength="200"
									data-rule-required="true" />
							</div>
						</div>

						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code='master.status' /></label>
							<div class="col-sm-4">
								<form:select path="bankStatus"
									cssClass="form-control mandColorClass" id="bankStatus">
									<form:option value="">
										<spring:message code='account.common.select' />
									</form:option>
									<c:forEach items="${acnPrefixList}" var="acnPrefixData">
										<form:option value="${acnPrefixData.lookUpCode }"
											code="${acnPrefixData.lookUpCode }">${acnPrefixData.descLangFirst }</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="control-label col-sm-2" for="contact"><spring:message
									code="account.bankmaster.contact.details" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control " id="contact" path="contact"
									data-rule-maxLength="200"></form:input>
							</div>
						</div>
					</fieldset>
				</li>
			</ul>

			<INPUT type="hidden" id="count" value="0" />
			<INPUT type="hidden" id="countFinalCode" value="0" />

			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'create'}">
					<button type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="Save">
						<i class="fa fa-floppy-o" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.save" />
					</button>
					<button type="button" id="Reset" class="btn btn-warning createData"
						value="Reset">
						<spring:message code="account.bankmaster.reset" />
					</button>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<button type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="Save">
						<i class="fa fa-floppy-o" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.save" />
					</button>
				</c:if>
				<spring:url var="cancelButtonURL" value="GeneralBankMaster.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="account.bankmaster.back" /></a>
			</div>
		</form:form>
	</div>
</div>
<c:if test="${tbBankMaster.hasError =='true'}">
	</div>
</c:if>

