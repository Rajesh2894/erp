<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetLeasingCompnay.js"></script>

<div class="widget-content padding">
	<form:form action="AssetRegistration.html" id="assetLeasing"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDivL"></div>
		<form:hidden path="modeType" id="modeType" />
		<input type="hidden" id="moduleDeptUrl"
			value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
								code="asset.leasing.leasingcomp" /></a>
					</h4>
				</div>
				<div class="panel-body">
					<div class="form-group">
						<apptags:input labelCode="asset.leasing.agreementno"
							cssClass="form-control hasNumber"
							isDisabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.astLeaseDTO.contractAgreementNo"
							isMandatory="false" maxlegnth="20"></apptags:input>

						<label class="col-sm-2 control-label" for=""><spring:message
								code="asset.leasing.agreementdate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker"
									id="leaseAgreementDate" disabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.astLeaseDTO.agreementDate" maxlength="10"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for=""><spring:message
								code="asset.leasing.noticedate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker" id="leaseNoticeDate"
									disabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.astLeaseDTO.noticeDate" maxlength="10"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

						<label class="col-sm-2 control-label" for=""><spring:message
								code="asset.leasing.leasestartdate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker"
									id="leaseStartingDate" disabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.astLeaseDTO.leaseStartDate" maxlength="10"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for=""><spring:message
								code="asset.leasing.leaseenddate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker" id="leaseEndingDate"
									disabled="${command.modeType eq 'V'}"
									path="astDetailsDTO.astLeaseDTO.leaseEndDate" maxlength="10"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>

						<label class="col-sm-2 control-label" for="leasetype"> <spring:message
								code="asset.leasing.leasetype" /></label>
						<c:set var="baseLookupCode" value="${userSession.moduleDeptCode == 'AST' ? 'ATN':'ITN'}" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="astDetailsDTO.astLeaseDTO.leaseType"
							disabled="${command.modeType eq 'V'}" cssClass="form-control"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="Select" isMandatory="false" />
					</div>

					<div class="form-group" id="assetIdenti">
						<apptags:input labelCode="asset.leasing.purchaseprize"
							cssClass="decimal text-right form-control"
							isDisabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.astLeaseDTO.purchasePrice"
							isMandatory="false"  maxlegnth="18"></apptags:input>


						<apptags:input labelCode="asset.leasing.installmentno"
							cssClass="form-control hasNumber"
							isDisabled="${command.modeType eq 'V'}"
							path="astDetailsDTO.astLeaseDTO.noOfInstallment"
							isMandatory="false"  maxlegnth="20"></apptags:input>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="paymentfreq"> <spring:message
								code="asset.leasing.paymentfreq" /></label>
						<c:set var="baseLookupCode" value="${userSession.moduleDeptCode == 'AST' ? 'PRF':'IRF'}" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="astDetailsDTO.astLeaseDTO.paymentFrequency"
							disabled="${command.modeType eq 'V'}" cssClass="form-control"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="Select" isMandatory="false" />
						<apptags:input labelCode="asset.leasing.advancepayment"
							cssClass="decimal text-right form-control"
							path="astDetailsDTO.astLeaseDTO.advancedPayment"
							isMandatory="false" isDisabled="${command.modeType eq 'V'}"  maxlegnth="18"></apptags:input>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${command.approvalProcess ne 'Y' }">
		<div class="text-center">
			<c:if test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
				<button type="button" class="button-input btn btn-success"
					name="button" value="Save" onclick="saveLeasComp(this);" id="save">
					<spring:message code="asset.leasing.save&continue" />
				</button>
			</c:if>
			<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
				<button type="Reset" class="btn btn-warning"
					onclick="resetLease()">
					<spring:message code="reset.msg" text="Reset" />
				</button>
			</c:if>
			<c:choose>
				<c:when test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D'}">
					<c:set var="backButtonAction" value="showTab('#astInsu')" />
				</c:when>
				<c:otherwise>
					<c:set var="backButtonAction" value="backToHomePage()" />
				</c:otherwise>
			</c:choose>
			<button type="button" class="btn btn-danger" name="button" id="Back"
				value="Back" onclick="${backButtonAction}">
				<spring:message code="asset.information.back" />
			</button>
			
		</div>
		</c:if>
	</form:form>
</div>
