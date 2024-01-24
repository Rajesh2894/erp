<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="" src="js/land_estate/landEstate.js"></script>
<script>
	$(document).ready(function() {

		$('.lessthancurrdate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
			yearRange : "-100:-0"
		});

		prepareChallanDateTag();
		$('#onlinebutton').prop('disabled', 'disabled');
		$('#onlinebutton').hide();
	});


	function clearData(obj) {
		$('#applicationId').val('');
		$('#loiNo').val('');
		$('#searchDto.loiDate').val('');
	}

	function prepareChallanDateTag() {
		var dateFields = $('.lessthancurrdate');
		dateFields.each(function() {
			var fieldValue = $(this).val();
			if (fieldValue.length > 10) {
				$(this).val(fieldValue.substr(0, 10));
			}
		});
	}
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="master.loi" text="LOI" />
				<strong><spring:message code="master.loi.payment"
						text="Payment" /></strong>
			</h2>
			<apptags:helpDoc url="LoiPayment.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="contract.breadcrumb.fieldwith"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="common.master.mandatory" text="is mandatory" /> </span>
			</div>
			<!-- Start Form -->
			<form:form action="LOIPayable.html" cssClass="form-horizontal"
				id="landAcqId">
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<%-- <form:hidden path="saveMode" id="saveMode" /> --%>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="land.acq.form.details" text="Land Details" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:input labelCode="land.acq.pay.to" isMandatory="true"
										isReadonly="true" path="acquisitionDto.payTo"></apptags:input>

									<apptags:input labelCode="land.acq.area" isReadonly="true"
										path="acquisitionDto.lnArea" isMandatory="true"></apptags:input>

								</div>

								<div class="form-group">
									<apptags:input labelCode="land.acq.mobNo" isReadonly="true"
										path="acquisitionDto.lnMobNo" cssClass="alphaNumeric"
										maxlegnth="10"></apptags:input>

									<apptags:input labelCode="land.acq.address" isReadonly="true"
										path="acquisitionDto.lnAddress" cssClass="alphaNumeric"
										maxlegnth="500" isMandatory="true"></apptags:input>
								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.location" /> <span><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.locId" id="locId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="true">
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.locationList}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="land.acq.acqType" text="Acquisition Type" /></label>
									<c:set var="baseLookupCode" value="AQM" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="acquisitionDto.acqModeId" cssClass="form-control"
										hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Acquisition Mode"
										disabled="true" />
								</div>


								<div class="form-group">
									<apptags:input labelCode="land.acq.surveyNo" isReadonly="true"
										path="acquisitionDto.lnServno" cssClass="alphaNumeric"
										maxlegnth="10" isMandatory="true"></apptags:input>


									<apptags:input labelCode="land.acq.currentUsage"
										isReadonly="true" path="acquisitionDto.currentUsage"
										cssClass="alphaNumeric" maxlegnth="100" isMandatory="true"></apptags:input>

								</div>
								<div class="form-group">
									<apptags:input labelCode="land.acq.currentMarktValue"
										isReadonly="true" path="acquisitionDto.currentMarktValue"
										cssClass="hasNumber1" maxlegnth="100" isMandatory="false"></apptags:input>

									<apptags:input labelCode="land.acq.purpose" isMandatory="true"
										isReadonly="true" path="acquisitionDto.acqPurpose"
										cssClass="hasCharacter" maxlegnth="100"></apptags:input>

								</div>

								<div class="form-group">
									<apptags:input labelCode="land.acq.desc" isReadonly="true"
										path="acquisitionDto.lnDesc" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true"></apptags:input>


									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.oth" /> <span><i class="text-red-1">*</i></span>
									</label>

									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.lnOth" id="lnOth"
											cssClass="form-control chosen-select-no-results"
											isMandatory="true" class="form-control mandColorClass"
											data-rule-required="true" disabled="true">
											<form:option value="0">select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>

								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.Title" text="Tittle Document available" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.lnTitle"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="true">
											<form:option value="0">select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>

									<apptags:input labelCode="land.acq.remark" isReadonly="true"
										path="acquisitionDto.lnRemark" cssClass="alphaNumeric "
										maxlegnth="100"></apptags:input>

								</div>

								<%-- <div class="form-group">
									<apptags:date fieldclass="datepicker" labelCode="land.acq.date"
										datePath="acquisitionDto.acqDate" readonly="true"
										isDisabled="true"></apptags:date>

								</div> --%>

								<!-- Document code set -->
								<div class="form-group">
									<c:set var="count" value="${count + 1}" scope="page" />
									<label for="" class="col-sm-2 control-label"> <spring:message
											code="council.member.documents" text="Documents" /></label>
									<c:set var="count" value="0" scope="page"></c:set>
									<div class="col-sm-4">
										<c:if
											test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
											<input type="hidden" name="deleteFileId"
												value="${command.attachDocsList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.attachDocsList[0]}">
											<apptags:filedownload
												filename="${command.attachDocsList[0].attFname}"
												filePath="${command.attachDocsList[0].attPath}"
												actionUrl="LandAcquisition.html?Download"></apptags:filedownload>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="" text="Vendors" />
					</label>
					<div class="col-sm-4">
						<form:select path="acquisitionDto.vendorId"
							cssClass="form-control chosen-select-no-results"
							disabled="true" id="vendorId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${vendorList}" var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<apptags:input labelCode="land.val.amt"  isDisabled="true"
										path="acquisitionDto.acqCost" isMandatory="true"></apptags:input>
				</div>
				<div class="form-group padding-top-10">
					<label class="col-sm-2 control-label "><spring:message
							code="Final Decision" text="Final Decision" /></label>
					<div class="col-sm-4">
						<form:select path="wokflowDecision" cssClass="form-control"
							id="wokflowDecision">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<form:option value="APPROVED">
								<spring:message code="workflow.action.decision.approved"
									text="APPROVED" />
							</form:option>
							<form:option value="REJECTED">
								<spring:message code="workflow.action.decision.rejected"
									text="REJECTED" />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success" title="Submit"
						onclick="submitLOIPayable(this)">
						<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
						<spring:message code="bt.save" text="Submit" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
			<!-- End Form -->

		</div>
	</div>
</div>
