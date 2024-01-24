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
<script type="text/javascript" src="js/water/duplicatePlumberLicense.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message
					code="water.plumberLicense.headerPlumLicenseDuplicate"
					text="Duplicate Plumber License" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><strong
					class="fa fa-question-circle fa-lg"></strong><span class="hide"><spring:message
							code="water.Help" text="Help" /></span></a>
			</div>


		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /><i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv" style="display: none;"></div>

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>

			<form:form action="DuplicatePlumberLicense.html"
				class="form-horizontal form" name=""
				id="frmPlumberLicenseDuplicateApplication">

				<form:hidden path="" id="plumIdFlag" value="${command.plumDto.flag}" />
				<div class="form-group">
					<label class="col-sm-2 control-label">License Number</label>
					<div class="col-sm-4">
						<form:input path="plumDto.plumberLicenceNo" id="plumberLicenceNo"
							type="text" class="form-control" />
					</div>
				</div>
				<div id="searchButt">
					<div class="form-group">
						<div class="text-center padding-bottom-10">
							<button type="button" id="search" class="btn btn-info"
								onclick="viewPlumberDetail(this)">
								<i class="fa fa-search"></i> View Details
							</button>
						</div>

					</div>
				</div>
			</form:form>
		</div>

		<div id="selectHideShow1">
			<form:form action="DuplicatePlumberLicense.html" method="post"
				class="form-horizontal" name="licGenerarion" id="licGeneration">
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div id="appli">
						<apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail>
					</div>
					<%-- 		<div class="form-group">
						<apptags:input labelCode="water.plumber.applicationNum"
							path="plumDto.applicationId" isReadonly="true"></apptags:input>

						<apptags:input labelCode="water.plumber.application.apply.date"
							path="plumDto.plumAppDate" isReadonly="true"></apptags:input>

					</div> --%>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#child-level1">License
									Information</a>
							</h4>
						</div>
						<div id="child-level1" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="table-responsive" id="licenseId">
									<table class="table table-bordered table-striped"
										id="licenseDetTableId">
										<tr>
											<th>License Number<%-- <spring:message code="water.plum.licno" /> --%></th>
											<th>Valid From<%-- <spring:message code="water.plum.validFrom" /> --%></th>
											<th>Valid To<%-- <spring:message code="water.plum.validTo" /> --%></th>
										</tr>
										<tr class="licenseInfo">
											<td><form:input path="plumDto.plumberLicenceNo" name=""
													class="form-control" id="licNo" value="" readonly="true"
													data-rule-required="true" /></td>
											<td><form:input path="plumDto.plumLicFromDate" name=""
													class="form-control" id="validFrom" value=""
													readonly="true" data-rule-required="true" /></td>
											<td><form:input path="plumDto.plumLicToDate" name=""
													class="form-control" id="validTo" value="" readonly="true"
													data-rule-required="true" /></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>


					<div class="padding-top-10 text-center hideConfirmBtn">
						<button type="button" class="btn btn-success"
							id="confirmToProceedPlId"
							onclick="getChecklistAndChargesDuplicatePlumber(this);">
							<spring:message code="" text="Confirm To Proceed" />
						</button>
					</div>

					<%-- 					<div class="padding-top-10 text-center hideConfirmBtn">
						<button type="button" class="btn btn-success"
							id="confirmToProceedPlId"
							onclick="saveDuplicatePlumberLicense(this);">
							<spring:message code="water.apply.duplicate.plumber" />
						</button>
					</div> --%>
					<form:hidden id="chargesId" path=""
						value="${command.checkListNCharges}" />
					<c:if test="${command.checkListNCharges eq 'Y'}">
						<c:if test="${(not empty command.checkList)}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class="collapsed"
											data-parent="#accordion_single_collapse" href="#child-level3"><spring:message
												code="water.documentattchmnt" /><small class="text-blue-2"><spring:message
													code="water.uploadfile.validtn" /></small></a>
									</h4>
								</div>
								<div id="child-level3" class="panel-collapse">
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
																	<c:set var="isCheckListMandatory" value="N"></c:set>
																	<c:choose>
																		<c:when test="${lookUp.checkkMANDATORY eq 'Y'}">
																			<c:set var="isCheckListMandatory" value="Y"></c:set>
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
																				hasId="true"
																				fieldPath="plumDto.fileCheckList[${lk.index}]"
																				isMandatory="false" showFileNameHTMLId="true"
																				fileSize="BND_COMMOM_MAX_SIZE"
																				maxFileCount="CHECK_LIST_MAX_COUNT"
																				validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
																				currentCount="${lk.index}"
																				checkListMandatoryDoc="${isCheckListMandatory}"
																				folderName="${lk.index}" />
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
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
							<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"> <spring:message
										code="water.field.name.amounttopay" />
								</label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
										value="${command.offlineDTO.amountToShow}" maxlength="12"
										readonly="true" /> <a
										class="fancybox fancybox.ajax text-small text-info"
										href="DuplicatePlumberLicense.html?showChargeDetails"> <spring:message
											code="water.field.name.amounttopay" /> <i
										class="fa fa-question-circle "></i>
									</a>
								</div>
							</div>
						</c:if>
						<div class="text-center padding-bottom-20" id="divSubmit">
							<button type="button" class="btn btn-success"
								onclick="saveDuplicatePlumberLicense(this)" id="submit">
								<spring:message code="water.btn.submit" />
							</button>
							<input type="button" class="btn btn-danger"
								onclick="window.location.href='AdminHome.html'" value="Cancel" />
						</div>
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
</div>
