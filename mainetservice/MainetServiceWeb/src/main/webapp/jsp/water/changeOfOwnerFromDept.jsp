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
<script type="text/javascript" src="js/water/changeOfOwner.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				$(function() {
					$('#emailId').bind('input', function() {
						$(this).val(function(_, val) {
							return val.replace(/\s+/g, '');
						});
					});
				});
				// to prevent white space
				$(function() {
					$('#conNum').bind('input', function() {
						$(this).val(function(_, val) {
							return val.replace(/\s+/g, '');
						});
					});
				});

				$('#onlinebutton').hide();
				$('#trmGroup1').attr('disabled', true);
				$('#trmGroup2').attr('disabled', true);
				$('#conSize').attr('disabled', true);
				$('#conName').attr('disabled', true);
				$('#oldAdharNo').attr('disabled', true);

				var povertyLineId = $('#povertyLineId').val();
				if (povertyLineId == '0' || povertyLineId == ''
						|| povertyLineId == 'N') {

					$('#bpldiv').hide();
				} else {
					$('#bpldiv').show();
				}

			});

	function saveData(element) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'Y') {
			return saveOrUpdateForm(
					element,
					"Your application for change Of ownership saved successfully!",
					'ChangeOfOwnership.html?redirectToPay', 'saveform');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'N') {
			return saveOrUpdateForm(
					element,
					"Your application for change Of ownership saved successfully!",
					'ChangeOfOwnership.html?PrintReport', 'saveform');
		}
	}
</script>

<c:if test="${command.hasValidationErrors()}">
	<script type="text/javascript">
		if ($('#conNum').val() != '') {
			$('#searchConnection').attr('disabled', true);
			$('#confirmToProceedId').attr('disabled', true);
		}
	</script>
</c:if>
<style>
	.zone-ward .form-group > label[for="dwzid3"] {
		clear: both;
	}
	.zone-ward .form-group > label[for="dwzid3"],
	.zone-ward .form-group > label[for="dwzid3"] + div {
		margin-top: 0.7rem;
	}
</style>

<div id="fomDivId">

	<apptags:breadcrumb></apptags:breadcrumb>


	<div class="content">
		<!-- Start info box -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="water.head.changeOwnerFrmDept" />
				</h2>
				<div class="additional-btn">
					<!-- <a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a> -->
					<apptags:helpDoc url="ChangeOfOwnership.html"></apptags:helpDoc>
				</div>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="water.fieldwith" /> <i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" />
					</span>
				</div>
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<ul>
						<li><label id="errorId"></label></li>
					</ul>


				</div>


				<!-- Change of owner Form start -->
				<form:form action="ChangeOfOwnership.html" method="POST"
					class="form-horizontal" id="ChangeOfOwnershipId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="" id="enableSubmit"
						value="${command.enableSubmit}" />
					<%-- 
				<div id="allicantDetails">
					<jsp:include page="/jsp/mainet/applicantDetails.jsp" />
				</div> --%>

					<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>


					<!-- Old Owner Details -->
					<h4 class="margin-top-0">
						<spring:message code="water.changeOwner.oldDetails" />
					</h4>
					<div id="oldOwner">

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="water.ConnectionNo"></spring:message></label>
							<div class="col-sm-4">
								<form:input path="changeOwnerResponse.connectionNumber"
									type="text" class="form-control disablefield" id="conNum"
									maxlength="20" readonly="false"></form:input>
							</div>
							<div class="col-sm-6">
								<button type="button" value="Search" class="btn btn-success"
									id="searchConnection">
									<i class="fa fa-search"></i>
									<spring:message code="water.search" />
								</button>

								<input type="button" class="btn btn-warning"
									id="resetConnection" value=<spring:message code="rstBtn"/> />
							</div>
						</div>
					</div>


					<div id="oldOwnerId">
						<jsp:include page="/jsp/water/oldOwnerDetailsFromDept.jsp" />
					</div>


					<!--New Owner Details  -->
					<h4>
						<spring:message code="water.changeOwner.newDetails" />
					</h4>
					<div id="newOwner">

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="water.transferMode" /></label>
							<c:set var="baseLookupCode" value="TFM" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="changeOwnerMaster.ownerTransferMode"
								cssClass="form-control" hasChildLookup="false" hasId="true"
								showAll="false"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" />
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="water.title" /></label>
							<c:set var="baseLookupCode" value="TTL" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="changeOwnerMaster.cooNotitle" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" />
							<label class="col-sm-2 control-label required-control"><spring:message
									code="water.owner.details.fname"></spring:message></label>
							<div class="col-sm-4">
								<form:input path="changeOwnerMaster.cooNoname" type="text"
									class="form-control hasSpecialChara" maxlength="100"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="water.owner.details.mname"></spring:message></label>
							<div class="col-sm-4">
								<form:input path="changeOwnerMaster.cooNomname" type="text"
									class="form-control hasSpecialChara" maxlength="100"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="water.owner.details.lname"></spring:message></label>
							<div class="col-sm-4">
								<form:input path="changeOwnerMaster.cooNolname" type="text"
									class="form-control hasSpecialChara" maxlength="100"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="water.owner.details.gender" /></label>
							<div class="col-sm-4">
								<c:set var="baseLookupCode" value="GEN" />
								<form:select path="changeOwnerMaster.gender"
									class="form-control" id="newGender">
									<form:option value="0">
										<spring:message code="water.select" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label"><spring:message
									code="water.aadhar" /></label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control hasNumber"
									path="changeOwnerMaster.ConUidNo" id="newAdharNo"
									maxlength="12"></form:input>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="water.remark" /></label>
							<div class="col-sm-10">
								<form:textarea type="text" class="form-control"
									path="changeOwnerMaster.cooRemark" id="remark" maxlength="200"></form:textarea>
							</div>
						</div>

					</div>

					<!-- Additional owner Detail section -->
					<h4>
						<spring:message code="water.additionalOwner" />
					</h4>
					<c:set var="d" value="0" scope="page" />
					<div class="table-responsive">
						<table class="table table-bordered table-striped"
							id="customFields">
							<thead>
								<tr>
									<th width="120"><spring:message code="water.title" /></th>
									<th><spring:message code="water.owner.details.fname" /></th>
									<th><spring:message code="water.owner.details.mname" /></th>
									<th><spring:message code="water.owner.details.lname" /></th>
									<th width="130"><spring:message
											code="water.owner.details.gender" /></th>
									<th><spring:message code="water.aadhar" /></th>
									<th><spring:message code="water.add/remove" /></th>
								</tr>
							</thead>
							<tbody>
							
							<c:choose>
									<c:when test="${fn:length(command.additionalOwners) > 0}">
									<c:forEach var="sorDetailsList"
							items="${command.additionalOwners}"
							varStatus="status">
								<tr class="appendableClass">

									<td><input type="hidden" id="srNoId_${d}" value="">
										<c:set var="baseLookupCode" value="TTL" /> <form:select
											path="additionalOwners[${d}].caoNewTitle"
											class="form-control" id="caoNewTitle_${d}">
											<form:option value="0">
												<spring:message code="water.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input path="additionalOwners[${d}].caoNewFName"
											class="form-control" id="caoNewFName_${d}" maxlength="100" /></td>
									<td><form:input path="additionalOwners[${d}].caoNewMName"
											class="form-control" id="caoNewMName_${d}" maxlength="100" /></td>
									<td><form:input path="additionalOwners[${d}].caoNewLName"
											class="form-control" id="caoNewLName_${d}" maxlength="100" /></td>
									<td><c:set var="baseLookupCode" value="GEN" /> <form:select
											path="additionalOwners[${d}].caoNewGender"
											class="form-control" id="caoNewGender_${d}">
											<form:option value="0">
												<spring:message code="water.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input path="additionalOwners[${d}].caoNewUID"
											class="form-control hasNumber" id="caoNewUID_${d}"
											maxlength="12" /></td>

									<td><a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="addCF btn btn-success btn-sm"
										data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="remCF btn btn-danger btn-sm"
										data-original-title="Delete"><i class="fa fa-trash"></i></a></td>

								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
									</c:when>
									<c:otherwise>
							<tr class="appendableClass">

									<td><input type="hidden" id="srNoId_${d}" value="">
										<c:set var="baseLookupCode" value="TTL" /> <form:select
											path="additionalOwners[${d}].caoNewTitle"
											class="form-control" id="caoNewTitle_${d}">
											<form:option value="0">
												<spring:message code="water.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input path="additionalOwners[${d}].caoNewFName"
											class="form-control" id="caoNewFName_${d}" maxlength="100" /></td>
									<td><form:input path="additionalOwners[${d}].caoNewMName"
											class="form-control" id="caoNewMName_${d}" maxlength="100" /></td>
									<td><form:input path="additionalOwners[${d}].caoNewLName"
											class="form-control" id="caoNewLName_${d}" maxlength="100" /></td>
									<td><c:set var="baseLookupCode" value="GEN" /> <form:select
											path="additionalOwners[${d}].caoNewGender"
											class="form-control" id="caoNewGender_${d}">
											<form:option value="0">
												<spring:message code="water.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input path="additionalOwners[${d}].caoNewUID"
											class="form-control hasNumber" id="caoNewUID_${d}"
											maxlength="12" /></td>

									<td><a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="addCF btn btn-success btn-sm"
										data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" data-toggle="tooltip"
										data-placement="top" class="remCF btn btn-danger btn-sm"
										data-original-title="Delete"><i class="fa fa-trash"></i></a></td>

								</tr>
							</c:otherwise>
							
							</c:choose>
								
							</tbody>
						</table>
					</div>
					<c:if test="${empty command.checkList  && command.enableSubmit eq false}">
						<div class="padding-top-10 text-center" id="confirm">
							<button type="button" class="btn btn-success btn-submit"
								id="confirmToProceedId"
								onclick="getChecklistAndChargesForChangeOfOwner(this)">
								<spring:message code="water.btn.proceed" />
							</button>
							<input type="button" class="btn btn-danger" id="backBtnId"
								onclick="window.location.href='AdminHome.html'"
								value=<spring:message code="bckBtn"/> />
						</div>
					</c:if>
					<!-- File Uploading Area if any document list coming in search list response after calling search web service -->

					<div id="checkListAndChargeId">


						<c:if test="${not empty command.checkList}">
							<h4>
								<spring:message code="water.documentattchmnt" />
								<small class="text-blue-2"><spring:message
										code="water.uploadfile.validtn" /></small>
							</h4>
							<div class="table-responsive">
								<table class="table table-hover table-bordered table-striped">
									<tr>
										<th><label class="tbold"><spring:message
													code="label.checklist.srno" /></label></th>
										<th><label class="tbold"><spring:message
													code="label.checklist.docname" /></label></th>
										<th><label class="tbold"><spring:message
													code="label.checklist.status" /></label></th>
										<th><label class="tbold"><spring:message
													code="label.checklist.upload" /></label></th>
									</tr>
									<tr>
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
													<div id="docs_${lk}">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="changeOwnerMaster.fileList[${lk.index}]"
															isMandatory="false" showFileNameHTMLId="true"
															fileSize="BND_COMMOM_MAX_SIZE"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
															currentCount="${lk.index}" />
													</div>
												</td>
											</tr>
										</c:forEach>
									</tr>

								</table>
							</div>

						</c:if>

						<form:hidden path="" value="${command.isFree}" id="FreeService" />
						<c:if test="${command.isFree ne null && command.isFree ne 'F'}">
							<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"><spring:message
										code="water.field.name.amounttopay" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
										value="${command.offlineDTO.amountToShow}" maxlength="12"></input>
									<!-- <a class="fancybox fancybox.ajax text-small text-info" href="javascript:void(0);" onclick="showChargeInfo()">Charge Details <i class="fa fa-question-circle "></i></a> -->
									<a class="fancybox fancybox.ajax text-small text-info"
										href="ChangeOfOwnership.html?showChargeDetails"><spring:message
											code="water.lable.name.chargedetail" /> <i
										class="fa fa-question-circle "></i></a>
								</div>
							</div>
							<%-- <jsp:include page="/jsp/payment/onlineOfflinePay.jsp" /> --%>
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						</c:if>

						<c:if test="${command.enableSubmit eq true}">
							<div class="text-center padding-top-10">
								<input type="button" class="btn btn-success"
									onclick="saveChangeOfOwnerShip(ChangeOfOwnershipId);"
									value=<spring:message code="saveBtn"/> /> <input type="button"
									class="btn btn-default"
									onclick="window.location.href='ChangeOfOwnership.html'"
									value=<spring:message code="rstBtn"/> /> <input type="button"
									class="btn btn-danger"
									onclick="window.location.href='AdminHome.html'"
									value=<spring:message code="bckBtn"/> />
							</div>
						</c:if>
					</div>
				</form:form>
			</div>
		</div>
		<!-- End of info box -->
	</div>
</div>
