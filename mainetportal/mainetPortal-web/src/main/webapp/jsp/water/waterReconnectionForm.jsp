
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/water/reconnection.js"></script>
<script>
	$(document).ready(function() {
		$('#frmWaterReconnectionForm').validate({
			onkeyup : function(element) {
				this.element(element);
				console.log('onkeyup fired');
			},
			onfocusout : function(element) {
				this.element(element);
				console.log('onfocusout fired');
			}
		});

		$("#povertyLineId").change(function(e) {

			if ($("#povertyLineId").val() == 'Y') {
				$("#bpldiv").show();
				$("#bplNo").data('rule-required', true);
			} else {
				$("#bpldiv").hide();
				$("#bplNo").data('rule-required', false);
			}
		});
		if ($('#povertyLineId').val() == 'N') {
			$("#bpldiv").hide();
		}
		if ($('#povertyLineId').val() == 'Y') {
			$("#bpldiv").show();
			$("#bplNo").data('rule-required', true);
		} else {
			$("#bpldiv").hide();
			$("#bplNo").data('rule-required', false);
		}
		$("#searchConnection").click(function() {
			var data = 'connectionNo=' + $("#connectionNo").val();
			var url = 'WaterReconnectionForm.html?getReconnectionDetails';
			var returnData = __doAjaxRequest(url, 'post', data, false, 'json');

			var errorList = [];
			if (returnData.connectionNo == null) {
				errorList.push('Connection No. is not valid');
				displayErrorsOnPage(errorList);
			} else {
				$('.error-div').hide();
				$("#connectionNo").attr("readonly", true);

			}
			if (returnData.alreadyApplied) {
				errorList.push('Your Application is in progress');
				$("#connectionNo").attr("readonly", false);
				displayErrorsOnPage(errorList);
			} else {
				$("#connectionNo").val(returnData.connectionNo);
				$("#consumerName").val(returnData.consumerName);
				$("#conCategory").val(returnData.tarrifCategory);
				$("#conPremiseType").val(returnData.premiseType);
				$("#conSize").val(returnData.connectionSize);
				$("#discMethod").val(returnData.discMethod);
				$("#discDate").val(returnData.discDate);
				$("#discType").val(returnData.disconnectionType);
				$("#remarks").val(returnData.discRemarks);
			//	$("#searchConnection").attr('disabled', true);
			}

		});

		/* $("#NotRegister").click(function() {
			$(".toshow").show();
		});

		$("#ULBRegister").click(function() {
			$(".toshow").hide();
		}); */

		if ($("#chargeNCheckListId").val() == 'Y') {

			$("#confirmToProceedId").hide();
			$('select').attr("disabled", true);
			$("#searchConnection").attr('disabled', true);
			$("#consumerName").attr('readOnly', false);
			$("#conCategory").attr('readOnly', false);
			$("#connectionNo").attr('readOnly', false);
			$("#conPremiseType").attr('readOnly', false);
			$("#conSize").attr('readOnly', false);
			$("#discMethod").attr('readOnly', false);
			$("#discDate").attr('readOnly', false);
			$("#discType").attr('readOnly', false);
			$("#remarks").attr('readOnly', false);
			$('input[type=text]').attr('disabled', true);
			$("#NotRegister").attr('disabled', true);
			$("#ULBRegister").attr('disabled', true);
			$("#plumberLicNo").attr('disabled', true);
			$("#oflPaymentMode").attr('disabled', false);
			$("#bankAccId").attr('disabled', false);
			$("#reset").attr('disabled', true);
		}
	});
</script>
<style>
label[for="dwzid3"]{
	padding-top:14px !important;
}
label[for="dwzid3"]+div{
	padding-top:14px;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.reconnection.header"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form:form action="WaterReconnectionForm.html"
				class="form-horizontal form" name="frmWaterReconnectionForm"
				id="frmWaterReconnectionForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<%-- <jsp:include page="/jsp/mainet/applicantDetails.jsp"></jsp:include> --%>
					<%-- <apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail> --%>
					<apptags:waterReconnection wardZone="WWZ"></apptags:waterReconnection>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#child-level1" tabindex="-1"><spring:message
										code="water.reconnection.header"></spring:message></a>
							</h4>
						</div>
						<div id="child-level1" class="panel-collapse">
							<div class="panel-body">





								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.ConnectionNo"></spring:message></label>
									<c:set var="validationMessage" value="${command.getAppSession().getMessage('common.empty.validation.message') }"></c:set>
									<div class="col-sm-4">
										<form:input path="reconnRequestDTO.connectionNo" type="text"
											class="form-control disablefield" id="connectionNo"
											data-rule-required="true" data-msg-required="${validationMessage}"></form:input>
									</div>
									<div class="col-sm-6">
										<button type="button" value="Search" class="btn btn-success"
											id="searchConnection">
											<i class="fa fa-search"> </i>
											<spring:message code="bt.search" />
										</button>
										<button type="button" value="Reset" class="btn btn-warning"
											id="reset" onclick="resetReconnection()">
											<spring:message code="water.btn.reset" text="Reset" />
										</button>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.consumerName"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.consumerName" id="consumerName"
											readonly="true" data-rule-required="true" data-msg-required="${validationMessage}"></form:input>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.connectiondetails.tariffcategory"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.tarrifCategory" id="conCategory"
											readonly="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<%-- <label class="col-sm-2 control-label"><spring:message
											code="water.connectiondetails.premisetype"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.premiseType" id="conPremiseType"
											readonly="true"></form:input>
									</div> --%>
									<label class="col-sm-2 control-label"><spring:message
											code="water.ConnectionSize"></spring:message></label>
									<div class="col-sm-4">
										<%-- <form:input type="text" class="form-control disablefield"
											path="connectionSize" id="conSize" readonly="true"></form:input> --%>
										<form:select path="connectionSize"
											class="form-control" id="conSize" disabled="true">
											<c:set var="baseLookupCode" value="CSZ" />
											<form:option value="">
												<spring:message code='master.selectDropDwn' text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookup">
												<form:option value="${lookup.lookUpId}"
													code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.reconnection.disconnectionMethod"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.discMethod" id="discMethod"
											readonly="true"></form:input>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.reconnection.disconnectionDate"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.discDate" id="discDate"
											readonly="true"></form:input>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.reconnection.disconnectionType"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.disconnectionType" id="discType"
											readonly="true"></form:input>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.remark"></spring:message></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control disablefield"
											path="reconnRequestDTO.discRemarks" id="remarks"
											readonly="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.plumber.details"></spring:message></label>
									 <div class="radio col-sm-4">
										 <label> <form:radiobutton
												path="reconnRequestDTO.plumber" value="Y" id="ULBRegister"
												checked="true" /> <spring:message code="water.plumber.ulb" text="ULB's Licensed Plumber"></spring:message>
										<%-- </label> <label> <form:radiobutton
												path="reconnRequestDTO.plumber" value="N" id="NotRegister" />
											<spring:message code="water.plumber.notreg"></spring:message>
										</label>  --%>
									</div> 
									<label class="col-sm-2 control-label" for="plumberName"><spring:message
											code="water.plumber.name" text="Plumber Name" /></label>
									<%-- <div class="col-sm-4">

											<form:input name="" type="text" class="form-control"
												path="reqDTO.plumberName" id="plumberName"></form:input>
										</div> --%>
									<div id="ulbPlumber">
										<div class="col-sm-4">
											<form:select path="reconnRequestDTO.plumberId"
												class="form-control" id="plumber">
												<form:option value="">
													<spring:message code="master.selectDropDwn" text="Select" />
												</form:option>
												<c:forEach items="${command.plumberList}" var="lookUp">
													<form:option value="${lookUp.plumberId}">${lookUp.plumberFullName}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div id="licensePlumber">
										<div class="col-sm-4">
											<form:select path="reconnRequestDTO.plumberId"
												class="form-control" id="licPlumber">
												<form:option value="">
													<spring:message code="master.selectDropDwn" text="Select" />
												</form:option>
												<c:forEach items="${command.plumberList}" var="lookUp">
													<form:option value="${lookUp.plumberId}">${lookUp.plumberFullName}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>

								</div>

							</div>
						</div>
					</div>


					<div class="padding-top-10 text-center">
						<button type="button" class="btn btn-success"
							id="confirmToProceedId"
							onclick="getChecklistAndChargesWaterReconnection(this)">
							<spring:message code="water.btn.proceed" />
						</button>
					</div>
					<form:hidden path="" id="chargeNCheckListId"
						value="${command.checkListNCharges}" />
					<c:if test="${command.checkListNCharges eq 'Y'}">
						<c:if test="${not empty command.checkList}">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse" href="#child-level"><spring:message
												code="water.documentattchmnt" /><small class="text-blue-2"><spring:message
													code="water.uploadfile.validtn" /></small></a>
									</h4>
								</div>
								<div id="child-level" class="panel-collapse">
									<div class="panel-body">


										<fieldset class="fieldRound">
											<div class="overflow">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<tr>
																<th><label class="tbold"><spring:message
																			code="label.checklist.srno" text="Sr No" /></label></th>
																<th><label class="tbold"><spring:message
																			code="label.checklist.docname" text="Document Name" /></label></th>
																<th><label class="tbold"><spring:message
																			code="label.checklist.status" text="Status" /></label></th>
																<th><label class="tbold"><spring:message
																			code="label.checklist.upload" text="Upload" /></label></th>
															</tr>

															<c:forEach items="${command.checkList}" var="lookUp"
																varStatus="lk">
																<tr>
																	<td><label>${lookUp.documentSerialNo}</label></td>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<td><label>${lookUp.doc_DESC_ENGL}</label></td>
																		</c:when>
																		<c:otherwise>
																			<td><label>${lookUp.doc_DESC_Mar}</label></td>
																		</c:otherwise>
																	</c:choose>

																	<c:choose>
																		<c:when test="${lookUp.checkkMANDATORY eq 'Y'}">
																			<td><label><spring:message
																						code="label.checklist.status.mandatory" /></label></td>
																		</c:when>
																		<c:otherwise>
																			<td><label><spring:message
																						code="label.checklist.status.optional" /></label></td>
																		</c:otherwise>
																	</c:choose>

																	<td>
																		<div id="docs_${lk}" class="">
																			<apptags:formField fieldType="7" labelCode=""
																				hasId="true" fieldPath="checkList[${lk.index}]"
																				isMandatory="false" showFileNameHTMLId="true"
																				fileSize="BND_COMMOM_MAX_SIZE"
																				maxFileCount="CHECK_LIST_MAX_COUNT"
																				validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
																				currentCount="${lk.index}" />
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</c:if>

						<c:if test="${command.isFree eq 'N'}">


							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
							<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"><spring:message
										code="water.field.name.amounttopay" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
										value="${command.offlineDTO.amountToShow}" maxlength="12"
										readonly="readonly"></input> <a
										class="fancybox fancybox.ajax text-small text-info"
										href="WaterReconnectionForm.html?showChargeDetails"><spring:message
											code="water.lable.name.chargedetail" /> <i
										class="fa fa-question-circle "></i></a>
								</div>
							</div>
						</c:if>
						<div class="text-center padding-bottom-20" id="divSubmit">
							<button type="button" class="btn btn-success"
								onclick="saveWaterReconnectionForm(this)" id="submit">
								<spring:message code="water.btn.submit" />
							</button>
							<button type="Reset" class="btn btn-warning" id="resetform">
								<spring:message code="water.btn.reset" />
							</button>
							<input type="button" class="btn btn-danger"
								onclick="window.location.href='CitizenHome.html'"
								value="<spring:message code="water.btn.cancel"/>" />
						</div>
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
</div>

