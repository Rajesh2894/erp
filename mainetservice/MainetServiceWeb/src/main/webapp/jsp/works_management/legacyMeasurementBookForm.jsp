<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/legacyMeasurementBook.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="" text="Legacy Measurement Book" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="LegacyMeasurementBook.html"></apptags:helpDoc>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="LegacyMeasurementBook.html"
				class="form-horizontal" name="LegacyMeasurementBook"
				id="LegacyMeasurementBook">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="length" id="length" />
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="wms.WorkOrderInformation" text="Work Order Information" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label for="" class="col-sm-2 control-label required-control"><spring:message
											code="work.order.workOrder.no" text="Work Order No." /></label>
									<div class="col-sm-4">
										<form:select path="workOrderDto.workId"
											cssClass="form-control chosen-select-no-results mandColorClass"
											id="" data-rule-required="true" disabled="true">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.workOrderDtoList}" var="lookUp">
												<form:option value="${lookUp.workId}" code="">${lookUp.workOrderNo}</form:option>
											</c:forEach>
										</form:select>
									</div>


									<label for="" class="col-sm-2"><spring:message
											code="work.order.contract.no" text="Contract No." /> </label>
									<div class="col-sm-4">
										<form:input path="workOrderDto.contractMastDTO.contNo"
											cssClass="form-control" readonly="true" id="" />
									</div>
								</div>

								<div class="form-group">
									<label for="" class="col-sm-2"><spring:message
											code="mb.WorkOrderAmount" text="Work Order Amount" /></label>
									<div class="col-sm-4">
										<form:input path="workOrderDto.contractValue"
											cssClass="form-control text-right" readonly="true" id="" />
									</div>

									<label for="" class="col-sm-2"><spring:message
											code="work.order.workOrder.date"
											text="Work Order Agreement Date " /> </label>
									<div class="col-sm-4">
										<form:input path="workOrderDto.startDate"
											cssClass="form-control dates" readonly="true" id="" />
									</div>
								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code="mb.MbInfo" text="Measurement Information" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div id="uploadTagDiv">
									<div class="table-responsive">
										<c:set var="d" value="0" scope="page" />
										<table class="table table-bordered table-striped"
											id="attachDoc">
											<tr>
												<th width="20%"><spring:message code=""
														text="Old Measurement Book No." /></th>
												<th width="20%"><spring:message
														code="mb.ActualMeasurementTakenDate"
														text="Actual Measurement Taken Date" /></th>
												<th width="20%"><spring:message code=""
														text="Measurement Book Amount" /></th>
												<th width="20%"><spring:message code="wms.UploadImages"
														text="Upload Images" /></th>
												<c:if test="${command.saveMode eq 'C'}">
													<th scope="col" width="8%"><a
														onclick='fileCountUpload(this);'
														class="btn btn-blue-2 btn-sm addCF"> <i
															class="fa fa-plus-circle"></i></a></th>
												</c:if>
											</tr>
											<tbody>
												<c:choose>
													<c:when test="${empty command.mbList}">
														<tr class="appendableClass">
															<td><form:input path="mbList[${d}].oldMbNo"
																	class="form-control mandColorClass text-center"
																	id="oldMbNo${d}" /></td>
															<td><form:input path="mbList[${d}].workMbTakenDate"
																	class="form-control datepicker mandColorClass text-center"
																	id="workMbTakenDate${d}" /></td>
															<td><form:input path="mbList[${d}].mbTotalAmt"
																	class=" form-control mandColorClass text-right numbersOnly"
																	onkeypress="return hasAmount(event, this, 10, 2)"
																	onchange="getAmountFormatInDynamic((this),'mbTotalAmt')"
																	id="mbTotalAmt${d}" /></td>
															<td class="text-center"><apptags:formField
																	fieldType="7"
																	fieldPath="attachments[${d}].uploadedDocumentPath"
																	currentCount="${d}" showFileNameHTMLId="true"
																	folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
																	isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="ALL_UPLOAD_VALID_EXTENSION">
																</apptags:formField></td>

															<td class="text-center"><a href='#'
																id="file_list_${d}"
																class='btn btn-danger btn-sm delButton'
																onclick="doFileDeletion($(this),${d});"><i
																	class="fa fa-trash"></i></a></td>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:when>
													<c:otherwise>
														<c:forEach var="count" items="${command.mbList}">
															<tr class="appendableClass">
																<td><form:input path="mbList[${d}].oldMbNo"
																		class="form-control mandColorClass text-center"
																		id="oldMbNo${d}"
																		readonly="${command.saveMode eq 'V' ? true :false}" /></td>
																<c:choose>
																	<c:when test="${command.saveMode eq 'E'}">
																		<td><form:input
																				path="mbList[${d}].workMbTakenDate"
																				class="form-control datepicker mandColorClass text-center"
																				id="workMbTakenDate${d}"
																				/></td>
																	</c:when>
																	<c:otherwise>
																		<td><form:input
																				path="mbList[${d}].workMbTakenDate"
																				class="form-control mandColorClass text-center"
																				id="workMbTakenDate${d}"
																				readonly="${command.saveMode eq 'V' ? true :false}" /></td>
																	</c:otherwise>
																</c:choose>
																<td><form:input path="mbList[${d}].mbTotalAmt"
																		class=" form-control mandColorClass text-right numbersOnly"
																		onkeypress="return hasAmount(event, this,10, 2)"
																		onchange="getAmountFormatInDynamic((this),'mbTotalAmt')"
																		id="mbTotalAmt${d}"
																		readonly="${command.saveMode eq 'V' ? true :false}" /></td>
																<td class="text-center"><c:if
																		test="${command.saveMode ne 'V'}">
																		<apptags:formField fieldType="7"
																			fieldPath="attachments[${d}].uploadedDocumentPath"
																			currentCount="${d}" showFileNameHTMLId="true"
																			folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
																			isMandatory="false"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="ALL_UPLOAD_VALID_EXTENSION">
																		</apptags:formField>
																	</c:if> <c:if
																		test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
																		<input type="hidden" name="deleteFileId"
																			value="${command.attachDocsList[0].attId}">
																		<input type="hidden" name="downloadLink"
																			value="${command.attachDocsList[0]}">
																		<apptags:filedownload
																			filename="${command.attachDocsList[0].attFname}"
																			filePath="${command.attachDocsList[0].attPath}"
																			actionUrl="LegacyMeasurementBook.html?Download"></apptags:filedownload>
																	</c:if></td>
																<c:if test="${command.saveMode eq 'C'}">
																	<td class="text-center"><a href='#'
																		id="file_list_${d}"
																		class='btn btn-danger btn-sm delButton'
																		onclick="doFileDeletion($(this),${d});"><i
																			class="fa fa-trash"></i></a></td>
																</c:if>
															</tr>
															<c:set var="d" value="${d + 1}" scope="page" />

														</c:forEach>
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>


				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveLegacyMeasurementBook(this);">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button class="btn btn-warning" type="button" id=""
							onclick="resetMilestoneForm();">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="Reset" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='LegacyMeasurementBook.html'"
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>
