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
<script>
jQuery('.hasCharacter').keyup(function (evt) {	
	var theEvent = evt || window.event;
	var key = theEvent.keyCode || theEvent.which;
	if(key == 37 || key == 39 || key == 8 || key == 46) {
		return 0;
	 }else{
		 this.value = this.value.replace(/[^a-z A-Z &  ]/g,'');
	 }
});
	jQuery('.hasDigit').keyup(function (evt) { 
		var theEvent = evt || window.event;
		var key = theEvent.keyCode || theEvent.which;
		if(key == 37 || key == 39 || key == 8 || key == 46) {
			return;
		 }else{
			 this.value = this.value.replace(/[^0-9]/g,'');
		 }
	});
	
	jQuery('.isContact').keyup(function (evt) { 
		var theEvent = evt || window.event;
		var key = theEvent.keyCode || theEvent.which;
		if(key == 37 || key == 39 || key == 8 || key == 46) {
			return;
		 }else{
			 this.value = this.value.replace(/[^0-9 -]/g,'');
		 }
	});
	
	jQuery('.isBranch').keyup(function (evt) { 
		var theEvent = evt || window.event;
		var key = theEvent.keyCode || theEvent.which;
		if(key == 37 || key == 39 || key == 8 || key == 46) {
			return;
		 }else{
			 this.value = this.value.replace(/[^0-9 A-Z a-z]/g,'');
		 }
	}); 
</script>

<div class="widget" id="widget">
	<div class="widget-header">
		<div class="widget-header">
			<h2>
				<spring:message code="bank.master.title" />
			</h2>
		</div>
	</div>
	<div class="widget-content padding">
	<div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbBankMaster" name="frmMaster" method="POST"
			action="GeneralBankMaster.html">
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" id="indexdata" />
			<form:hidden path="bankId" />
			<input type="hidden" value="${MODE_DATA}" id="mode">
			<form:hidden path="hasError" />
			<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
			<input type="hidden" value="${envFlag}" id="envFlag" />
			<div class="warning-div error-div alert  alert-danger alert-dismissible"
				id="errorDivBank"></div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<c:set var="count" value="0" scope="page" />
			<ul id="ulId">
				<li>
					<fieldset id="divId" class="clear">

						<div class="form-group">

							<form:hidden path="bankId" />
							<form:hidden path="lgIpMac"/>
							<label class="control-label col-sm-2 required-control" for="bank"><spring:message
									code="account.bankmaster.bankname" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass hasCharacter"
									id="bank" path="bank" data-rule-maxLength="200"
									data-rule-required="true" onchange="clearBranchName(this)"
									style="text-transform: uppercase;"></form:input>
							</div>

							<label class="control-label col-sm-2 required-control"
								for="branch"><spring:message
									code="account.bankmaster.bankbranch"></spring:message></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass isBranch"
									id="branch" path="branch" data-rule-maxLength="200"
									data-rule-required="true"
									onchange="findduplicatecombinationBranchexit(this)"
									style="text-transform: uppercase;"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2 required-control" for="ifsc"><spring:message
									code="account.bankmaster.ifsccode" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass isIFSC"
									placeholder="Ex: XXXX0123456" id="ifsc" path="ifsc"
									maxLength="11" data-rule-required="true"
									onchange="findduplicatecombinationifscexit(this)"
									style="text-transform: uppercase;"></form:input>
							</div>

							<c:if test="${envFlag eq 'Y'}">	<label class="control-label col-sm-2 " for="micr"><spring:message
									code="account.bankmaster.micrcode"></spring:message></label></c:if>
							<c:if test="${envFlag eq 'N'}"><label class="control-label col-sm-2 required-control" for="micr"><spring:message
									code="account.bankmaster.micrcode"></spring:message></label></c:if>
							<div class="col-sm-4">
								<form:input cssClass="form-control  hasDigit"
									placeholder="Ex: 1234567890" id="micr" path="micr"
									maxLength="9" 
									onchange="checkDuplicateMICR(this);"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2 required-control" for="city"><spring:message
									code="account.bankmaster.city" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass hasCharacter"
									id="city" path="city" style="text-transform: uppercase;"></form:input>
							</div>

							<label class="control-label  col-sm-2 required-control"
								for="district"><spring:message code="account.bankmaster.district" text="District"></spring:message></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass hasCharacter"
									id="district" path="district" data-rule-maxLength="200"
									data-rule-required="true" style="text-transform: uppercase;"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label  col-sm-2 required-control"
								for="state"><spring:message
									code="account.bankmaster.state" /></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control mandColorClass hasCharacter"
									id="state" path="state" data-rule-maxLength="200"
									data-rule-required="true" style="text-transform: uppercase;"></form:input>
							</div>

							<label class="control-label col-sm-2 required-control"
								for="address"><spring:message
									code="account.bankmaster.branchaddress" /></label>
							<div class="col-sm-4">
								<form:textarea id="address" path="address"
									class="form-control mandColorClass" data-rule-maxLength="200"
									data-rule-required="true" style="text-transform: uppercase;" />
							</div>


						</div>

						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code='master.status' /></label>
							<div class="col-sm-4">
								<form:select path="bankStatus"
									cssClass="form-control mandColorClass" id="bankStatus">
									<form:option value="">
										<spring:message code='master.selectDropDwn' />
									</form:option>
									<c:forEach items="${acnPrefixList}" var="acnPrefixData">
										<form:option value="${acnPrefixData.lookUpCode }"
											code="${acnPrefixData.lookUpCode }">${acnPrefixData.descLangFirst }</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="control-label col-sm-2" for="contact"><spring:message
									code="account.bankmaster.contact.details"></spring:message></label>
							<div class="col-sm-4">
								<form:input cssClass="form-control isContact" id="contact"
									path="contact" maxLength="12"></form:input>
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
						<spring:message code="account.bankmaster.save" />
					</button>
				</c:if>
				<c:if test="${MODE_DATA == 'EDIT'}">
					<button type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value="Save">
						<spring:message code="account.bankmaster.save" />
					</button>
				</c:if>
				<button type="reset" id="Reset" class="btn btn-warning"
					value="Reset">
					<spring:message code="account.bankmaster.reset" />
				</button>
				<spring:url var="cancelButtonURL" value="" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}">
					<spring:message code="account.bankmaster.back" />
				</a>
			</div>
		</form:form>
	</div>
</div>




