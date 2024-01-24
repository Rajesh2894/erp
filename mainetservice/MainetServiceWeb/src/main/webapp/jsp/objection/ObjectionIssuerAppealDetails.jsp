<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/objection/objectionIssuerAppealDetails.js"></script>
<div class="widget">
	<div class="widget-header">
		<h2>
			<b><spring:message code="obj.objappealissuerdetails"></spring:message></b>
		</h2>
		<div class="additional-btn">
			<apptags:helpDoc url="objectionAddDetails.html"></apptags:helpDoc>
		</div>
	</div>

	<div class="widget-content padding">
		<form:hidden path="command.isValidationError"
			value="${command.isValidationError}" id="isValidationError" />
		<form:form method="POST" action="ObjectionDetails.html"
			class="form-horizontal" id="ObjIssuerDetailsForm"
			name="rtiObjIssuerDetailsForm">
			<div class="compalint-error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
			</div>
			<form:hidden path="billingMethod" id="billingMethod" />

			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a1"> <spring:message
								code="obj.objappealissuerdetails" /></a>
					</h4>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group">
								<apptags:select labelCode="obj.dept"
									items="${command.departments}"
									path="objectionDetailsDto.objectionDeptId"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showAll="false" isLookUpItem="true"
									changeHandler="getObjectionServiceByDept()"
									cssClass="form-control chosen-select-no-results">
								</apptags:select>


								<label class="col-sm-2 control-label required-control"
									for="objectionDeptId"><spring:message
										code="obj.serviceName" /></label>
								<div class="col-sm-4">

									<form:select path="objectionDetailsDto.serviceId"
										class="form-control mandColorClass chosen-select-no-results" id="serviceId">
										<form:option value="0">
											<spring:message code="obj.sel.serive" text="Select Service" />
										</form:option>
										<c:forEach items="${command.getServiceList()}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>

							</div>
							<div class="form-group">
								<label class="col-sm-6 "></label> <label
									class="col-sm-4 text-red"><spring:message
										code="obj.note" /></label>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="objectionDeptId"><spring:message
										code="obj.ObjOrAppeal" /></label>
								<div class="col-sm-4">

									<form:select path="objectionDetailsDto.objectionOn"
										class="form-control mandColorClass  chosen-select-no-results"
										id="objectionOn">
										<form:option value="0">
											<spring:message code="applicantinfo.label.select"
												text="Select Objection" />
										</form:option>
										<c:forEach items="${command.objOrAppealList}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>

								</div>

								<label class="col-sm-2 control-label required-control"
									id="refNo" for="dept"><spring:message
										code="obj.ReferenceNo" /></label>
								<div class="col-sm-4">
									<form:input class="form-control mandColorClass"
										id="objectionReferenceNumber"
										path="objectionDetailsDto.objectionReferenceNumber"
										data-rule-required="true" maxlength="49"></form:input>
								</div>

							</div>

							<div class="form-group" id="showFlatNo">
								<label class="col-sm-2 control-label"><spring:message
										code="unitdetails.flatNo" text="Flat No" /></label>
								<div class="col-sm-4">
									<form:select path="objectionDetailsDto.flatNo"
										id="flatNo"
										class="form-control mandColorClass chosen-select-no-results">
										<form:option value="">
											<spring:message code='adh.select' text="Select"/>
										</form:option>
										<c:forEach items="${command.flatNoList}" var="flatNoList">
											<form:option value="${flatNoList}">${flatNoList}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="applicantTitle"><spring:message code="obj.title" /></label>
								<c:set var="baseLookupCode" value="TTL" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="objectionDetailsDto.title" cssClass="form-control"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showOnlyLabel="rti.title" />

								<apptags:input labelCode="obj.firstName" cssClass="hasCharacter"
									path="objectionDetailsDto.fName" isMandatory="true"></apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="obj.middleName"
									cssClass="hasCharacter" path="objectionDetailsDto.mName"></apptags:input>
								<apptags:input labelCode="obj.lastName" cssClass="hasCharacter"
									path="objectionDetailsDto.lName" isMandatory="true"></apptags:input>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.gender" /></label>
								<c:set var="baseLookupCode" value="GEN" />
								<apptags:lookupField items="${command.getLevelData('GEN')}"
									path="objectionDetailsDto.gender" cssClass="form-control"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" />

								<apptags:input labelCode="obj.mobile1" cssClass="hasMobileNo"
									path="objectionDetailsDto.mobileNo" isMandatory="true" maxlegnth="10"></apptags:input>
							</div>


							<div class="form-group">
								<label class="col-sm-2 control-label" for="EmailID"><spring:message
										code="obj.emailId" text="Email ID" /></label>
								<div class="col-sm-4">
									<form:input type="email" class="form-control mandColorClass"
										id="EmailID" path="objectionDetailsDto.eMail"
										data-rule-required="true" data-rule-email="true"></form:input>
								</div>
								<apptags:input labelCode="obj.Aadhar" cssClass="hasAadharNo"
									path="objectionDetailsDto.uid" isMandatory="true" maxlegnth="12"></apptags:input>

							</div>
							<div class="form-group">
								<apptags:textArea isMandatory="true" labelCode="obj.address"
									path="objectionDetailsDto.address" maxlegnth="500"
									cssClass="preventSpace"></apptags:textArea>
								<label class="col-sm-2 control-label required-control"
									id="deptLocationId" for="objectionDeptId"><spring:message
										code="obj.location" /></label>
								<div class="col-sm-4">

									<form:select path="objectionDetailsDto.locId"
										class="form-control mandColorClass" id="locId">
										<form:option value="0">
											<spring:message code="obj.selLocation" text="Select Location" />
										</form:option>
										<c:forEach items="${command.getLocations()}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												label="${lookUp.lookUpDesc}"></form:option>
										</c:forEach>
									</form:select>
								</div>

							</div>


						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<!-- #U73424 -->
					<%-- <h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a2"> <spring:message
								code="obj.objappealdetails" /></a>
					</h4> --%>
					<div id="a2" class="panel-collapse collapse in">
						<div class="panel-body">

							<%-- 							<div class="form-group">
								<apptags:select labelCode="obj.dept"
									items="${command.departments}"
									path="objectionDetailsDto.objectionDeptId"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showAll="false" isLookUpItem="true" changeHandler="getObjectionServiceByDept()"></apptags:select>

								<label class="col-sm-2 control-label required-control"
										for="objtype"><spring:message code="obj.objectiontype" /></label>								
								<apptags:lookupField
										items="${command.serviceList}"
										path="objectionDetailsDto.serviceId" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="obj.title" />
							</div> --%>
							<!-- Code Place change from down to Up -->






							<div class="form-group" id="billComponent">
								<label class="col-sm-2 control-label" for="billNo"><spring:message
										code="obj.billNo" text="Bill No" /></label>
								<div class="col-sm-4">
									<form:input class="form-control mandColorClass hasNumber"
										id="billNo" path="objectionDetailsDto.billNo" maxlength="15"></form:input>
								</div>
								<label class="col-sm-2 control-label" for="billDueDate"><spring:message
										code="obj.billDueDate" text="Bill Due Date" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="objectionDetailsDto.billDueDate"
											class="form-control" id="inspectionDate"
											data-rule-required="true" placeholder="DD/MM/YYYY"
											autocomplete="off" />
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
							</div>
							<div class="form-group" id="dispatch" style="display: none;">
								<label class="col-sm-2 control-label" for="dispatchDate"><spring:message
										code="obj.dispatch.date" text="Dispatch Date" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="objectionDetailsDto.dispachDate"
											class="form-control datepicker" id="dispachDate"
											data-rule-required="true" placeholder="DD/MM/YYYY"
											autocomplete="off" readonly="true" disabled="true" />
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>

								<label class="col-sm-2 control-label" for="dispatch no"><spring:message
										code="obj.dispatch.no" text="Dispatch No" /></label>
								<div class="col-sm-4">
									<form:input class="form-control mandColorClass" id="dispatchNo"
										path="objectionDetailsDto.dispatchNo" readonly="true"></form:input>
								</div>

							</div>



							<div class="form-group" id="noticeNoDiv">
								<label class="col-sm-2 control-label" for="noticeNo"><spring:message
										code="obj.noticeNo" text="Notice No" /></label>
								<div class="col-sm-4">
									<form:input class="form-control mandColorClass" id="noticeNo"
										path="objectionDetailsDto.noticeNo"></form:input>
								</div>
							</div>

							<div id="objectionReferenceNumber"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label reasonForAppeal"><spring:message
										code="obj.reasonForAppeal" text="Reason For Appeal" /></label>
								<div class="radio col-sm-4 margin-top-5 reasonForAppeal">
									<label> <form:radiobutton
											path="objectionDetailsDto.objectionReason" value="INR"
											checked="checked" class="reason" /> <spring:message
											code="obj.reasonINT" text="Information Not Received" /></label> <label>
										<form:radiobutton path="objectionDetailsDto.objectionReason"
											value="ABD" class="reason" /> <spring:message
											code="obj.reasonABD" text="Aggrieved By Decision" />
									</label>
								</div>
								<apptags:textArea isMandatory="true"
									labelCode="obj.appealobjdetails"
									path="objectionDetailsDto.objectionDetails" maxlegnth="149"
									cssClass="preventSpace"></apptags:textArea>

							</div>

							<div class="form-group">
								<label class="col-sm-10 text-red"><spring:message
										code="obj.upload.bill.copy"
										text="Kindly upload Bill Copy in case of Bill" /></label>
							</div>
							<div class="form-group">




								<label for="file" class="col-sm-2 control-label"><spring:message
										code="obj.fileUpload"></spring:message> </label>
								<div class="col-sm-4">
									<apptags:formField fieldType="7" labelCode="" hasId="true"
										fieldPath="" isMandatory="false" showFileNameHTMLId="true"
										fileSize="BND_COMMOM_MAX_SIZE"
										maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
										validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
										currentCount="0" />
								</div>
							</div>
						</div>
					</div>
				</div>




				<div id="rtiAppealPayment">
					<c:if test="${command.offlineDTO.amountToShow > '0'}">

						<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />

						<div class="form-group margin-top-10">
							<label class="col-sm-2 control-label"> <spring:message
									code="rti.amtpay" /></label>
							<div class="col-sm-4">
								<input type="text" class="form-control"
									value="${command.offlineDTO.amountToShow}" maxlength="12"
									readonly="readonly" /> <a
									class="fancybox fancybox.ajax text-small text-info"
									href="ObjectionDetails.html?showChargeDetails"> <spring:message
										code="rti.amtpay" /> <i class="fa fa-question-circle "></i></a>
							</div>
						</div>
					</c:if>
				</div>

				<div class="padding-top-10 text-center">

					<!-- <button type="button" class="btn btn-success" id="save"

					<button type="button" class="btn btn-success" id="save" -->

					<button type="button" class="btn btn-success btn-submit" id="serchBtn" 
						onclick="getFlatListByRefNo(this);">
						<spring:message code="rti.submit" />
					</button>
					
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveObjectionDetailForm(this);">
						<spring:message code="rti.submit" />
					</button>

					<button type="button" class="btn btn-success" id="proceedbutton"
						style="display: none;" onclick="getCharges(this);">
						<spring:message code="unit.proceed" />
					</button>

					<button type="Reset" class="btn btn-warning" id="resetform"
						onclick="resetRtiObjDetail(this)">
						<spring:message code="rti.reset" />
					</button>
					<%-- 	<button class="btn btn-danger" type="button"
						onclick="getDept" id="back">
						<spring:message code="property.Back" />
					</button> --%>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>

				</div>
			</div>
		</form:form>
	</div>
</div>
<script>
$(function() {
	$("#inspectionDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0'
	});
});
	//D#84026
	function populateObjAppealList() {
		//var objAppealList ='${command.objOrAppealList}';
		var objAppealList = [];
		<c:forEach var="appeal" items="${command.objOrAppealList}" varStatus="i">
		var appealId = '${appeal.lookUpId}';
		var appealCode = '${appeal.lookUpCode}';
		var appealDesc = '${appeal.lookUpDesc}';

		/* make list of object for set data  */
		objAppealList.push({
			appealId : appealId,
			appealCode : appealCode,
			appealDesc : appealDesc,
		});
		</c:forEach>

		let deptCode = $('#objectionDeptId option:selected').attr('code');
		if (deptCode == 'RTI') {
			//iterate and make list for RTI specific
			$('#objectionOn').html('');
			$('#objectionOn').append(
					$("<option></option>").attr("value", "").text(
							getLocalMessage('selectdropdown'))).trigger(
					'chosen:updated');
			$.each(objAppealList, function(index, value) {
				if (value.appealCode == 'FRTI' || value.appealCode == 'SRTI') {
					$('#objectionOn').append(
							$("<option></option>")
									.attr("value", value.appealId).attr("code",
											value.appealCode).text(
											value.appealDesc));
				}
			});
			$('#objectionOn').trigger('chosen:updated');
		} else {
			//append list for other than RTI module
			$('#objectionOn').html('');
			$('#objectionOn').append(
					$("<option></option>").attr("value", "").text(
							getLocalMessage('selectdropdown'))).trigger(
					'chosen:updated');
			$.each(objAppealList, function(index, value) {
				$('#objectionOn').append(
						$("<option></option>").attr("value", value.appealId)
								.attr("code", value.appealCode).text(
										value.appealDesc));
			});
			$('#objectionOn').trigger('chosen:updated');
		}
	}
</script>