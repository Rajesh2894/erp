<!-- Start JSP Necessary Tags -->
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/contractTimeVariation.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="wms.contractVariation"
					text="Contract Time Variation" />
			</h2>
			<div class="additional-btn">
				 <apptags:helpDoc url="ContractTimeVariation.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="ContractTimeVariation.html"
				class="form-horizontal" id="contractTimeVariation"
				name="contractTimeVariation">
				<form:hidden path="removeVariationFileById"
					id="removeVariationFileById" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.order.contract.no" text="Contract No." /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="contId" data-rule-required="true"
							onchange="getContractDetail(this)">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.contractSummaryDTOList}" var="lookUp">
								<form:option value="${lookUp.contId}" code="">${lookUp.contNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2"><spring:message
							code="mb.ContractorName" text="Contract Vendor Name" /></label>
					<div class="col-sm-4">
						<form:input path="" id="contp2Name"
							class="form-control mandColorClass " value="" readonly="true" />
					</div>


				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="mb.contract.StartDate" text="Contract From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id="contFromDate" class="form-control"
								value="" readonly="true" />
							<label class="input-group-addon" for=""><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="mb.contract.EndDate" text="Contract To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input
								path="contractMastDTO.contractDetailList[0].contToDate"
								id="contToDate" class="form-control " value="" readonly="true" />
							<label class="input-group-addon" for=""><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span></label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="wms.contractVariation" text="Contract Time Variation" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input type='text'
								path="contractMastDTO.contractDetailList[0].contTimeExtPer"
								class='form-control hasNumber' id="contTimeExtPer" />
							<div class='input-group-field'>
								<c:set var="baseLookupCode" value="UTS" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="contractMastDTO.contractDetailList[0].contTimeExtUnit"
									cssClass="form-control required-control chosen-select-no-results"
									isMandatory="true" selectOptionLabelCode="selectdropdown"
									hasId="true" />

							</div>
						</div>
					</div>
				</div>


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#uploadDoc"><spring:message
										code="work.estimate.upload" text="Upload Documents" /></a>
							</h4>
						</div>
						<div id="uploadDoc" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="panel-body">
									<c:if test="${fn:length(command.attachDocsList)>0}">
										<div class="table-responsive">
											<table class="table table-bordered table-striped"
												id="deleteDoc">
												<tr>
													<th width="" align="center"><spring:message
															code="ser.no" text="" /><input type="hidden" id="srNo"></th>
													<th scope="col" width="64%" align="center"><spring:message
															code="work.estimate.document.description"
															text="Document Description" /></th>
													<th scope="col" width="30%" align="center"><spring:message
															code="scheme.view.document" /></th>
													<c:if test="${command.saveMode ne 'V'}">
														<th scope="col" width="8%"><spring:message
																code="works.management.action" text=""></spring:message></th>
													</c:if>
												</tr>
												<c:set var="e" value="0" scope="page" />
												<c:forEach items="${command.attachDocsList}" var="lookUp">
													<tr>
														<td>${e+1}</td>
														<td>${lookUp.dmsDocName}</td>
														<td><apptags:filedownload
																filename="${lookUp.attFname}"
																filePath="${lookUp.attPath}"
																actionUrl="ContractTimeVariation.html?Download" /></td>
														<c:if test="${command.saveMode ne 'V'}">
															<td class="text-center"><a href='#' id="deleteFile"
																onclick="return false;" class="btn btn-danger btn-sm"><i
																	class="fa fa-trash"></i></a> <form:hidden path=""
																	value="${lookUp.attId}" /></td>
														</c:if>
													</tr>
													<c:set var="e" value="${e + 1}" scope="page" />
												</c:forEach>
											</table>
										</div>
										<br>
									</c:if>

									<div id="uploadTagDiv">
										<div class="table-responsive">
											<c:set var="d" value="0" scope="page" />
											<table class="table table-bordered table-striped"
												id="attachDoc">
												<tr>
													<th><spring:message
															code="work.estimate.document.description"
															text="Document Description" /></th>
													<th><spring:message code="work.estimate.upload"
															text="Upload Document" /></th>
													<th scope="col" width="8%"><a
														onclick='fileCountUpload(this);' title="<spring:message code="works.management.add" text="Add"></spring:message>"
														class="btn btn-blue-2 btn-sm addButton"><i
															class="fa fa-plus-circle"></i></a></th>
												</tr>

												<tr class="appendable">
													<td><form:input path="attachments[${d}].doc_DESC_ENGL"
															class=" form-control" /></td>
													<td class="text-center"><apptags:formField
															fieldType="7"
															fieldPath="attachments[${d}].uploadedDocumentPath"
															currentCount="${d}" showFileNameHTMLId="true"
															folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
															isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
															checkListMStatus="true">
														</apptags:formField><small class="text-blue-2" style="font-size: unset;"> <spring:message code="wms.doc.validatation"
						                          	     text="(Upload Document File upto 5 MB)" /></small></td>
													<td class="text-center"><a href='#' id="0_file_${d}"
														onclick="" class='btn btn-danger btn-sm delButton' title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
															class="fa fa-trash"></i></a></td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</table>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button class="btn btn-success " type="button"
						onclick="updateContractPeriod();" title="<spring:message code="works.management.save" text="Save"></spring:message>">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="works.management.save" text="Save" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						onclick="window.location.href='ContractTimeVariation.html'" title="<spring:message code="works.management.reset" text="Reset"></spring:message>">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" title="<spring:message code="works.management.back" text="Back"></spring:message>"
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>