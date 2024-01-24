
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
<script src="js/water/water-disconnection.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<style>
label[for="dwzid3"]{
	padding-top:14px !important;
}
label[for="dwzid3"]+div{
	padding-top:14px;
}
</style>
<c:if test="${command.hasValidationErrors()}">
	<script >
		if ($('#csIdn').val() !='' ) {
			$("#csIdn").val("").attr("readonly", false);
		}
	
	</script>
</c:if>
<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.breadcrumb.disconnect"
					text="Temporary permanent closing of water connection" />
			</h2>
			<apptags:helpDoc url="WaterDisconnectionForm.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /><i
					class="text-red-1">*</i>
				<spring:message code="water.ismandtry" /> </span>
			</div>
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">

				<form:form action="WaterDisconnectionForm.html"
					class="form-horizontal" name="frmwaterDisconnectionForm"
					id="frmwaterDisconnectionForm" method="post">


					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<%-- <apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail> --%>
					<apptags:waterReconnection wardZone="WWZ"></apptags:waterReconnection>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#Disconnection_Details" tabindex="-1"> <spring:message
										code="water.disconnect.detail" text="Disconnection Details" /></a>
							</h4>
						</div>
						<div id="Disconnection_Details" class="panel-collapse in collapse">
							<div class="panel-body">
								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.ConnectionNo" text="Connection No." /></label>
									<c:set var="validationMessage" value="${command.getAppSession().getMessage('common.empty.validation.message') }"></c:set>
									<div class="col-sm-4">
										<form:input path="connectionNo" id="csIdn"
											class="form-control" data-rule-required="true" data-msg-required="${validationMessage}"></form:input>
									</div>

									<div class="col-sm-6">
										<button class="btn btn-success" onclick="search(this);"
											title='<spring:message code="bt.search" text="Search" />'
											type="button">
											<i class="fa fa-search"></i>
											<spring:message code="bt.search" text="Search" />
										</button>
										<%-- Defect #153420 --%>
										<button type="button" class="btn btn-warning" id="resetDiscDetails"
											title='<spring:message code="rstBtn" text="Reset" />&nbsp;<spring:message code="water.disconnect.detail" text="Disconnection Details" />'>
											<spring:message code="rstBtn" text="Reset" />&nbsp;<spring:message code="water.disconnect.detail" text="Disconnection Details" />
										</button>
									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.consumerName" text="Consumer Name" /></label>
									<div class="col-sm-4">
										<form:input path="consumerName" cssClass="form-control"
											id="consumerName" readonly="true" />
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.areaName" text="Area Name" /></label>
									<div class="col-sm-4">
										<form:input path="areaName" cssClass="form-control"
											id="consumerAreaName" readonly="true" />

									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.disconnect.type" text="Disconnection Type" /></label>
									<c:set var="baseLookupCode" value="DIC" />
									<apptags:lookupField
										items="${command.getServiceLevelData(baseLookupCode)}"
										path="disconnectionEntity.discType" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="water.select" isMandatory="true" />

									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.disconnect.reason" text="Disconnection Reason" /></label>
									<div class="col-sm-4">
										<form:textarea path="disconnectionEntity.discReason"
											id="discReason" name="textarea" class="form-control"
											data-rule-required="true" maxlength="500" data-msg-required="${validationMessage}"></form:textarea>
									</div>
								</div>
								<fieldset id="tempDisconnection">
									<legend>
										<spring:message code="water.disconnect.temp"
											text="Temporary Disconnection" />
									</legend>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.disconnect.from" text="From Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="disconnectionEntity.disconnectFromDate"
													cssClass="form-control disDate" id="fromDate" data-msg-required="${validationMessage}" />
												<label for="fromDate" class="input-group-addon"><i
													class="fa fa-calendar"></i></label>
											</div>
										</div>
										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.disconnect.to" text="To Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="disconnectionEntity.disconnectToDate"
													cssClass="form-control distDate" id="toDate" data-msg-required="${validationMessage}" />
												<label for="toDate" class="input-group-addon"><i
													class="fa fa-calendar"></i></label>
											</div>
										</div>
									</div>
								</fieldset>

							<div class="form-group">
									<label class="control-label col-sm-2"><spring:message
											code="water.plumber.details" text="Plumber Details" /><span
										class="mand">*</span></label>
									<div class="radio col-sm-4">
										<label><form:radiobutton path="ulbPlumber" value="Y"
												onclick="notUlbPlumber()" id="ULBRegister" checked="true" />
											<spring:message code="water.plumber.ulb"
												text="ULB's Licensed Plumber" /></label>
									</div>
								<%--  <div class="radio col-sm-2">
										<label><form:radiobutton path="ulbPlumber" value="N"
												onclick="notUlbPlumber()" id="No" /> <spring:message
												code="water.plumber.notreg" text="Not ULB Registered" /></label>
									</div> --%>
									 
									<label class="col-sm-2 control-label"><spring:message
											code="water.plumber.name" text="Plumber Name" /></label>
									<%-- <div class="col-sm-4">
										<form:input path="plumberLicence" cssClass="form-control"
											id="plumId" />
									</div> --%>
									<div id="ulbPlumber">
										<div class="col-sm-4">
											<form:select path="disconnectionEntity.plumId"
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
								</div>
							</div>
						</div>
					</div>



					<c:if test="${command.enableSubmit eq false}">
						<div class="padding-top-10 text-center">
							<button type="button" class="btn btn-success"
								title='<spring:message code="water.btn.proceed" />'
								id="confirmToProceedId" onclick="getChecklistAndCharges(this)">
								<spring:message code="water.btn.proceed" />
							</button>
							<%-- Defect #153420 --%>
							<button type="button" class="btn btn-warning" id="resetBtn" title='<spring:message code="rstBtn" text="Reset" />'>
								<spring:message code="rstBtn" text="Reset" />
							</button>			
						</div>

					</c:if>
					<c:if test="${not empty command.checkList}">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse"
										href="#Document_Attachment"> <spring:message
											code="water.documentattchmnt" /><small class="text-blue-2"><spring:message
												code="water.uploadfile.validtn" /></small>
									</a>
								</h4>
							</div>
							<div id="Document_Attachment" class="panel-collapse in collapse">
								<div class="panel-body">
									<div class="table-responsive margin-bottom-10">
										<table class="table table-hover table-striped table-bordered">
											<tbody>
												<tr>
													<th><spring:message code="water.serialNo"
															text="Serial No" /></th>
													<th><spring:message code="water.docName"
															text="Document Name" /></th>
													<th><spring:message code="water.status"
															text="Document Status" /></th>
													<th width="400"><spring:message
															code="water.uploadText" text="Upload" /></th>
												</tr>

												<c:forEach items="${command.checkList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td>${lookUp.documentSerialNo}</td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																<td>${lookUp.doc_DESC_ENGL}</td>
															</c:when>
															<c:otherwise>
																<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																<td>${lookUp.doc_DESC_Mar}</td>
															</c:otherwise>
														</c:choose>

														<c:choose>
															<c:when test="${lookUp.checkkMANDATORY eq 'Y'}">
																<td><spring:message
																		code="label.checklist.status.mandatory" /></td>
															</c:when>
															<c:otherwise>
																<td><spring:message
																		code="label.checklist.status.optional" /></td>
															</c:otherwise>
														</c:choose>

														<td>
															<div id="docs_${lk}">
																<apptags:formField fieldType="7" labelCode=""
																	hasId="true"
																	fieldPath="disconnectionEntity.fileList[${lk.index}]"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="BND_COMMOM_MAX_SIZE"
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
																	checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																	checkListDesc="${docName}" currentCount="${lk.index}" />
															</div>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${command.payable eq true}">
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="water.field.name.amounttopay" /></label>
							<div class="col-sm-4">
								<form:input class="form-control" path=""
									value="${command.offlineDTO.amountToShow}" maxlength="12"
									readonly="true"></form:input>
								<a class="fancybox fancybox.ajax text-small text-info"
									href="WaterDisconnectionForm.html?showChargeDetails"><spring:message
										code="water.lable.name.chargedetail" /> <i
									class="fa fa-question-circle "></i></a>
							</div>
						</div>
						<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
					</c:if>
					<c:if test="${command.enableSubmit eq true}">
						<div class="text-center">
							<button type="button" class="btn btn-success"
								title='<spring:message code="water.btn.submit" />'
								onclick="saveFormDisconnection(this)" id="submitAndPayButtonId">
								<spring:message code="water.btn.submit" />
							</button>
							<%-- Defect #153420 --%>
							<button type="button" class="btn btn-warning" id="resetBtn" title='<spring:message code="rstBtn" text="Reset" />'>
								<spring:message code="rstBtn" text="Reset" />
							</button>
							<input type="button" class="btn btn-danger"
								title='<spring:message code="water.btn.cancel" />'
								onclick="window.location.href='CitizenHome.html'"
								value="<spring:message code="water.btn.cancel"/>" />
						</div>
					</c:if>


				</form:form>
			</div>
		</div>
	</div>
</div>


<script>
    $(document).ready(function() {
        $('.fancybox').fancybox();
    });
</script>


