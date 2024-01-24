
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/water-disconnection.js"></script>
<style>
.form-group .radio.multiple-radio-btns{
	margin-top: 0.3rem;
}
.zone-ward .form-group > label[for="dwzid3"] {
	clear: both;
}
.zone-ward .form-group > label[for="dwzid3"],
.zone-ward .form-group > label[for="dwzid3"] + div {
	margin-top: 0.7rem;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.breadcrumb.disconnect"
					text="Temporary permanent closing of water connection" />
			</h2>
			<apptags:helpDoc url="WaterDisconnectionForm.html"></apptags:helpDoc>
			<!-- <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div> -->
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" /></span>
			</div>
			<form:form action="WaterDisconnectionForm.html"
				class="form-horizontal" name="frmwaterDisconnectionForm"
				id="frmwaterDisconnectionForm" method="post">


				<!--New Connection-->

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<%--   <jsp:include page="/jsp/water/WaterConnectionApplicantDetails.jsp" /> --%>
				<div class="zone-ward">
					<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="water.disconnect.detail"
						text="Disconnection Details" />
				</h4>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.ConnectionNo" text="Connection No." /></label>
					<div class="col-sm-4">
						<form:input path="connectionNo" id="csIdn" class="form-control"></form:input>
					</div>

					<div class="col-sm-6">
						<%-- <button class="btn btn-success" onclick="_openPopUpForm('WaterDisconnectionForm.html','searchConnectionDetails');" type="button"><i class="fa fa-search"></i> <spring:message code="bt.search" text="Search"/></button> --%>
						<button class="btn btn-success" onclick="search(this);"
							title='<spring:message code="bt.search" text="Search" />'
							type="button">
							<i class="fa fa-search"></i>
							<spring:message code="bt.search" text="Search" />
						</button>
						<%-- Defect #153420 --%>
						<button type="button" class="btn btn-warning" id="resetDiscDetails"
							title='<spring:message code="rstBtn" text="Reset" /> <spring:message code="water.disconnect.detail" text="Disconnection Details" />'>
							<spring:message code="rstBtn" text="Reset" /> <spring:message code="water.disconnect.detail" text="Disconnection Details" />
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
							id="discReason" name="textarea" class="form-control"></form:textarea>
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
									cssClass="form-control Moredatepicker" id="fromdate" />
								<label for="fromdate" class="input-group-addon"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>
						<label class="col-sm-2 control-label required-control"><spring:message
								code="water.disconnect.to" text="To Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="disconnectionEntity.disconnectToDate"
									cssClass="form-control Mostdatepicker" id="todate" />
								<label for="todate" class="input-group-addon"><i
									class="fa fa-calendar"></i></label>
							</div>
						</div>
					</div>
				</fieldset>

				<%-- <div class="form-group">
					<label class="control-label col-sm-2 required-control"><spring:message
							code="water.plumber.details" text="Plumber Details" /> </label>
					<div class="radio col-sm-2">
						<label><form:radiobutton path="ulbPlumber" value="Y"
								onclick="notUlbPlumber()" id="Yes" />
							<spring:message code="water.plumber.reg" text="ULB Registered" /></label>
					</div>
					<div class="radio col-sm-2">
						<label><form:radiobutton path="ulbPlumber" value="N"
								onclick="notUlbPlumber()" id="No" />
							<spring:message code="water.plumber.notreg"
								text="Not ULB Registered" /></label>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="water.plumber.licno" text="Plumber License No." /></label>
					<div class="col-sm-4">
						<form:input path="plumberLicence" cssClass="form-control"
							id="plumId" />
					</div>
				</div> --%>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="water.plumber.details" /></label>
					<div class="radio col-sm-4 multiple-radio-btns">
						<label> <form:radiobutton path="ulbPlumber" value="U"
								id="ULBRegister" checked="true" /> <spring:message
								code="water.plumber.reg" />
						</label> <label> <form:radiobutton path="ulbPlumber" value="L"
								id="NotRegister" /> <spring:message code="water.plumber.notreg" />
						</label>
					</div>
					<label class="col-sm-2 control-label required-control"
						for="plumberName"><spring:message
							code="water.plumber.name" /></label>

					<div id="ulbPlumber">
						<div class="col-sm-4">
							<form:select path="disconnectionEntity.plumId"
								class="form-control chosen-select-no-results" id="plumber">
								<form:option value="">
									<spring:message code="water.dataentry.select" />
								</form:option>
								<c:forEach items="${command.plumberList}" var="lookUp">
									<form:option value="${lookUp.plumId}">${lookUp.plumFname} ${lookUp.plumMname} ${lookUp.plumLname}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div id="licensePlumber">
						<div class="col-sm-4">
							<form:select path="disconnectionEntity.plumId"
								class="form-control" id="licPlumber">
								<form:option value="">
									<spring:message code="water.dataentry.select" />
								</form:option>
								<c:forEach items="${command.plumberList}" var="lookUp">
									<form:option value="${lookUp.plumId}">${lookUp.plumFname} ${lookUp.plumMname} ${lookUp.plumLname}</form:option>
								</c:forEach>
							</form:select>
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
				<div class="accordion-toggle">
					<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#child-level">
								<spring:message code="water.documentattchmnt" />
								<small class="text-blue-2 margin-left-10">
									<spring:message code="water.uploadfile.validtn" text="(UploadFile up to 5MB and only .pdf or .doc)"/>
								</small>
							</a>
						</h4>
					</div>
					<div id="child-level" class="panel-collapse">
					<div class="panel-body">
					<div class="table-responsive margin-bottom-10">
						<table class="table table-hover table-striped table-bordered">
							<tbody>
								<tr>
									<th><label class="tbold"><spring:message
												code="marriage.serialNo" text="Serial No" /></label></th>
									<th><label class="tbold"><spring:message
												code="marriage.docName" text="Document Name" /></label></th>
									<th><label class="tbold"><spring:message
												code="marriage.status" text="Document Status" /></label></th>
									<th><label class="tbold"><spring:message
												code="marriage.uploadText" text="Upload" /></label></th>
								</tr>

								<c:forEach items="${command.checkList}" var="lookUp"
									varStatus="lk">
									<tr>
										<td><label>${lookUp.documentSerialNo}</label></td>
										<c:choose>
											<c:when
												test="${userSession.getCurrent().getLanguageId() eq 1}">
												<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
												<td><label>${lookUp.doc_DESC_ENGL}</label></td>
											</c:when>
											<c:otherwise>
												<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
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
											<div id="docs_${lk}">
												<apptags:formField fieldType="7" labelCode="" hasId="true"
													fieldPath="disconnectionEntity.fileList[${lk.index}]"
													isMandatory="false" showFileNameHTMLId="true"
													fileSize="BND_COMMOM_MAX_SIZE"
													maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
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
							<!-- <a class="fancybox fancybox.ajax text-small text-info" href="javascript:void(0);" onclick="showChargeInfo()">Charge Details <i class="fa fa-question-circle "></i></a> -->
							<a class="fancybox fancybox.ajax text-small text-info"
								href="WaterDisconnectionForm.html?showChargeDetails"><spring:message
									code="water.lable.name.chargedetail" /> <i
								class="fa fa-question-circle "></i></a>
						</div>
					</div>
			
					<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
				</c:if>
				<c:if test="${command.enableSubmit eq true}">
					<div class="text-center padding-bottom-20">

						<button type="button" class="btn btn-success btn-submit" 
							title='<spring:message code="water.btn.submit" />'
							onclick="saveFormDisconnection(frmwaterDisconnectionForm)" id="submitAndPayButtonId">
							<spring:message code="water.btn.submit" />
						</button>
						<%-- Defect #153420 --%>
						<button type="button" class="btn btn-warning" id="resetBtn" title='<spring:message code="rstBtn" text="Reset" />'>
							<spring:message code="rstBtn" text="Reset" />
						</button>
						<button type="button" class="btn btn-danger"
							title='<spring:message code="water.btn.cancel" />'
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="water.btn.cancel" />
						</button>

					</div>
				</c:if>
				<!--New Connection-->

			</form:form>
		</div>
	</div>
</div>
