<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/masters/contract/commonAdvanceEntry.js" />
<script src="js/mainet/file-upload.js" />

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget" id="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advance.requisition" text=""></spring:message>
				<apptags:helpDoc url="AdvanceRequisition.html"
					helpDocRefURL="AdvanceRequisition.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">
		<div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
			<form:form action="AdvanceRequisition.html" class="form-horizontal"
				name="advanceRequisitionAdd" id="advanceRequisitionAdd">
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="" value="${command.advanceRequisitionDto.headId}"
					id="hiddenHeadId" />
				<form:hidden path=""
					value="${command.advanceRequisitionDto.venderId}"
					id="hiddenVenderId" />
				<form:hidden path=""
					value="${command.advanceRequisitionDto.referenceNo}"
					id="hiddenReferenceNo" />
				<form:hidden path="" value="${command.sliStatus}" id="sliStatus" />
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="advance.requisition.advancedate" text="" /></label>

					<div class="col-sm-4">
						<form:input cssClass="form-control mandColorClass datepiker"
							id="entryDate" path="advanceRequisitionDto.entryDate"
							placeholder='DD/MM/YYYY' data-rule-required="true" maxLength="10"
							disabled="${command.saveMode ne 'A'}"></form:input>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="advance.requisition.dept" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="advanceRequisitionDto.deptId"
							cssClass="form-control" id="deptId"
							disabled="${command.saveMode ne 'A'}">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.departmentsList}" var="departments">
								<form:option value="${departments.dpDeptid }"
									code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="advance.requisition.advancetype" text="" /></label>

					<div class="col-sm-4">
						<form:select path="advanceRequisitionDto.advType"
							class="form-control mandColorClass" id="advanceType"
							onchange="getBudgetHeadOnAdvanceType();"
							disabled="${command.saveMode ne 'A'}">
							<form:option value="">
								<spring:message code="advance.requisition.select" text="" />
							</form:option>
							<c:forEach items="${command.advanceType}" varStatus="status"
								var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label "><spring:message
							code="advance.requisition.advancehead" text="" /></label>

					<div class="col-sm-4">
						<c:if test="${command.sliStatus eq 'L'}">

							<form:select path="advanceRequisitionDto.headId"
								class="form-control" id="pacHeadId" data-rule-required="true"
								disabled="${command.saveMode ne 'A'}">
								<form:option value="">
									<spring:message code="advance.requisition.select" text="" />
								</form:option>
								<c:forEach items="${advanceHead}" varStatus="status"
									var="levelChild">
									<form:option code="${levelChild.key}" value="${levelChild.key}">${levelChild.value}</form:option>
								</c:forEach>
							</form:select>
						</c:if>
						<c:if test="${command.sliStatus eq 'S'}">
							<form:input cssClass="form-control " id="headDesc"
								disabled="${command.saveMode ne 'A'}"
								path="advanceRequisitionDto.headDesc"></form:input>
						</c:if>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="advance.requisition.vendoremployeename" text="" /></label>

					<div class="col-sm-4">
						<form:select path="advanceRequisitionDto.venderId"
							class="form-control mandColorClass"
							onchange="getWorkOrderDetails();" id="vendorName"
							data-rule-required="true" disabled="${command.saveMode ne 'A'}">
							<form:option value="">
								<spring:message code="advance.requisition.select" text="" />
							</form:option>
							<c:forEach items="${command.vendorList}" var="vendorData">
								<form:option value="${vendorData.vmVendorid}">${vendorData.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="advance.requisition.referenceno"
							text="Advance Reference Number" /></label>

					<div class="col-sm-4">
						<form:select path="advanceRequisitionDto.referenceNo"
							class="form-control mandColorClass"
							onchange="getContrcatAmount();" id="referenceNo"
							data-rule-required="true" disabled="${command.saveMode ne 'A'}">
							<form:option value="">
								<spring:message code="advance.requisition.select" text="" />
							</form:option>
						</form:select>
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="advance.requisition.availableamt" text="Remaining Amount" /></label>

					<div class="col-sm-4">
						<form:input cssClass="form-control " id="remainingAmt" path=""
							readonly="true"></form:input>
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="advance.requisition.advanceamount" text="" /></label>

					<div class="col-sm-4">
						<form:input
							cssClass="form-control mandColorClass text-right amount"
							onkeypress="return hasAmount(event, this, 13, 2)"
							id="advanceAmount" path="advanceRequisitionDto.advAmount"
							onkeyup="copyContent(this)"
							onchange="getAmountFormatAdvance(this)" data-rule-required="true"
							disabled="${command.saveMode eq 'V'}"></form:input>
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="advance.requisition.contratamt" text="Contract Amount" /></label>

					<div class="col-sm-4">
						<form:input cssClass="form-control " id="totalAmount" path=""
							readonly="true"></form:input>
					</div>

				</div>

				<div class="form-group">
					<apptags:textArea
						labelCode="advance.requisition.advanceamount.particularsofadvance"
						isDisabled="${command.saveMode eq 'V'}"
						path="advanceRequisitionDto.particulars" maxlegnth="250"
						isMandatory="true"></apptags:textArea>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-parent="#accordion_single_collapse" href="#a1"
									data-toggle="collapse"> <spring:message
										code="advance.requisition.uploaddocuments" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">


							<c:if test="${command.wokflowMode || command.saveMode eq 'V'}">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="deleteCommonDoc">
										<tr>
											<th width="64%"><spring:message
													code="advance.requisition.description" /></th>
											<th width="30%"><spring:message
													code="advance.requisition.uploaddocument" text="" /></th>
											<th width="8%"><spring:message
													code="advance.requisition.action" text=""></spring:message></th>
										</tr>
										<c:set var="e" value="0" scope="page" />
										<c:forEach items="${command.attachDocsList}" var="lookUp">
											<tr>
												<td>${lookUp.dmsDocName}</td>
												<td><apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="WmsProjectMaster.html?Download" /></td>
												<td class="text-center"><a href='#'
													id="deleteCommonFile" onclick="return false;"
													class="btn btn-danger btn-sm"><i class="fa fa-trash"></i></a>
													<form:hidden path="" value="${lookUp.attId}" /></td>
											</tr>
										</c:forEach>
									</table>
								</div>
								<br>
							</c:if>


							<c:if test="${command.saveMode ne 'V'}">
								<div class="panel-body">
									<div id="uploadTagDiv">
										<div class="table-responsive">
											<c:set var="d" value="0" scope="page" />
											<table class="table table-bordered table-striped"
												id="attachDoc">
												<tr>
													<th><spring:message
															code="advance.requisition.description"
															text="Document Description" /></th>
													<th><spring:message
															code="advance.requisition.uploaddocuments" text="Upload" /></th>
												</tr>

												<tr class="appendableClass">
													<td><form:input path="attachments[0].doc_DESC_ENGL"
															class=" form-control" /></td>
													<td class="text-center"><apptags:formField
															fieldType="7"
															fieldPath="attachments[0].uploadedDocumentPath"
															currentCount="0" showFileNameHTMLId="true" folderName="0"
															fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
														</apptags:formField></td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</c:if>
						</div>
					</div>

					<!-- End Of Create Memo Section -->
					<c:if test="${command.wokflowMode}">
						<apptags:CheckerAction></apptags:CheckerAction>
					</c:if>
				</div>

				<div class="text-center padding-top-10">
					<c:if test="${command.saveMode ne 'V' && !command.wokflowMode}">
						<input type="button" id="saveBtn" class="btn btn-success btn-submit"
							onclick="saveRequisitionForm(this);"
							value="<spring:message code="advance.requisition.save" text="" />"></input>
					</c:if>
					<c:if test="${command.wokflowMode}">
						<input type="button" id="saveBtn" class="btn btn-success btn-submit"
							onclick="showConfirmBoxForApproval(this);"
							value="<spring:message code="advance.requisition.save" text="" />">
						</input>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value=""
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<spring:message code="advance.requisition.back" text="" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<input type="Reset" id="Reset" class="btn btn-warning"
							value="<spring:message code="advance.requisition.reset" text="" />">
						</input>
					</c:if>
					<c:if test="${!command.wokflowMode}">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backForm();" id="button-Cancel">
							<spring:message code="advance.requisition.back" text="" />
						</button>
					</c:if>
				</div>

			</form:form>
		</div>
	</div>
</div>

