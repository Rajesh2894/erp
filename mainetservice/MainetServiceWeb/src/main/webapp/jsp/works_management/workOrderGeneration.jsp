<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/works_management/workOrderGeneration.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.WorkOrderGeneration"
					text="Work Order Generation" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="PwWorkOrderGeneration.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="PwWorkOrderGeneration.html"
				cssClass="form-horizontal" name="WorkOrderGenerationForm"
				id="WorkOrderGenerationForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<form:hidden path="removeFileById" id="removeFileById" />
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="removeTermsCondById" id="removeTermsCondById" />
				<form:hidden path="workOrderDto.contractFromDate"
					id="contractFromDate" />
				<form:hidden path="workOrderDto.contractToDate" id="contractToDate" />
				<%-- <form:hidden path="contractNumber" id="contractNumber" /> --%>
				
				<div class="form-group">
				
				<label class="col-sm-2 control-label"><spring:message
							code="project.master.dept" /> </label>
					<div class="col-sm-4">
						<form:select path="workOrderDto.deptId" cssClass="form-control chosen-select-no-results mandColorClass" 
						   disabled="${command.saveMode ne 'A'}" id="deptId" onchange="getAgrementByDeptId();">
							<form:option value="0">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.departmentsList}" var="departments">
								<form:option value="${departments.dpDeptid }"
									code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<c:if test="${command.saveMode ne 'A'}">
						<apptags:input labelCode="work.order.workOrder.no"
							path="workOrderDto.workOrderNo"
							cssClass="form-control preventSpace" isDisabled="true"></apptags:input>
					</c:if>
					<label class="col-sm-2  control-label required-control"><spring:message
							code="work.order.workOrder.date" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<c:if
								test="${command.saveMode eq 'V' || command.saveMode eq 'E' }">
								<form:input path="workOrderDto.workOrderDate"
									cssClass="form-control datepickers1 " id="workOrderDate"
									maxlength="10" disabled="${command.saveMode eq 'V'}" />
								<label class="input-group-addon" for="workOrderDate"><i
									class="fa fa-calendar"></i><span class="hide"> <spring:message
											code="" text="icon" /></span><input type="hidden"
									id="workOrderDate"></label>
							</c:if>
							<c:if test="${command.saveMode eq 'A'}">
								<form:input path="workOrderDto.orderDate"
									cssClass="form-control datepickers1 " id="workOrderDate"
									maxlength="10" />
								<label class="input-group-addon" for="workOrderDate"><i
									class="fa fa-calendar"></i><span class="hide"> <spring:message
											code="" text="icon" /></span><input type="hidden"
									id="workOrderDate"></label>
							</c:if>
						</div>
					</div>

					<%-- <apptags:date fieldclass="datepicker"
						labelCode="work.order.workOrder.date"
						datePath="workOrderDto.orderDate"
						cssClass="datepicker text-center" readonly="true"></apptags:date> --%>
				</div>
				<div class="form-group">

					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="work.order.contract.no" text="Contract No." /> </label>
					<c:if test="${command.saveMode eq 'A'}">
						<div class="col-sm-4">
							<form:select path="workOrderDto.contractMastDTO.contId"
								cssClass="form-control chosen-select-no-results mandColorClass"
								id="contId" data-rule-required="true"
								onchange="getContractDetails();">
								<form:option value="0">
									<spring:message code='work.management.select' />
								</form:option>
								<c:forEach items="${command.contractDetailsDtosList}"
									var="contractList">
									<form:option value="${contractList.contId}"
										code="${contractList.contFromDate},${contractList.contToDate},${contractList.contp2Name},${contractList.contAmount},${contractList.contToPeriod},${contractList.tenderNo},${contractList.tenderDate},${contractList.contToPeriodUnit},${contractList.contDeptId}"
										label="${contractList.contNo}"></form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
					<c:if test="${command.saveMode eq 'V' || command.saveMode eq 'E'}">
						<div class="col-sm-4">
							<form:select path="workOrderDto.contractMastDTO.contId"
								cssClass="form-control chosen-select-no-results mandColorClass"
								id="contId" data-rule-required="true" disabled="true"
								onchange="getContractDetails();">
								<form:option value="0">
									<spring:message code='work.management.select' />
								</form:option>
								<c:forEach items="${command.contractDetailsDtosList}"
									var="contractList">
									<form:option value="${contractList.contId}"
										code="${contractList.contFromDate},${contractList.contToDate},${contractList.contp2Name},${contractList.contAmount},${contractList.contToPeriod},${contractList.tenderNo},${contractList.tenderDate},${contractList.contToPeriodUnit},${contractList.contDeptId}"
										label="${contractList.contNo}"></form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
					<label class="col-sm-2 control-label"><spring:message
							code="work.order.view.contract.details"
							text="View Contract Details" /></label>
					<div class="col-sm-2">
						<a href="#" onclick="viewContractDetails();"
							class="padding-top-5 link"><spring:message
								code='work.management.Clickhere' /></a>
						<spring:message code='wms.ToViewContractDetails' />
					</div>
				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="work.order.contract.from.date" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="workOrderDto.contractFromDate"
								cssClass="form-control" id="contractFromDate" readonly="true" />
							<label class="input-group-addon" for="contractFromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"
								id=contractFromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="work.order.contract.to.date" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="workOrderDto.contractToDate"
								cssClass="form-control " id="contractToDate" readonly="true" />
							<label class="input-group-addon" for="contractToDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"
								id="contractToDate"></label>
						</div>
					</div> 
				</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="tender.vendorname" text="Contractor Name" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" id="vendorName"
							readonly="true" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="work.def.workname" text="Work Name" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" id="workName"
							readonly="true" />
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="wms.ContractValue" text="Contract Value" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control text-right"
							id="contractValue" readonly="true" />
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="wms.Stipulatedcompletionperiod"
							text="Stipulated completion period" /></label>

					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path="" cssClass="form-control text-right"
								id="contToPeriod" readonly="true" />
							<!-- Defect #90797 -->
							<div class='input-group-field'>
								<form:select path="" class="form-control" id="contToPeriodUnit"
									disabled="true">
									<form:option value="">
										<spring:message code="holidaymaster.select" />
									</form:option>
									<c:forEach items="${command.getLevelData('UTS')}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</div>

						</div>
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="wms.Systemtendernumber" text="System tender number" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" id="tenderNo"
							readonly="true" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="wms.SystemtenderDate" text="System tender Date" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" id="tenderdate"
							readonly="true" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2  control-label required-control"><spring:message
							code="work.order.agrement.start.date"
							text="Date to Start the work" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<c:if
								test="${command.saveMode eq 'V' || command.saveMode eq 'E' }">
								<form:input path="workOrderDto.workOrderStartDate"
									cssClass="form-control datepickers mandColorClass"
									id="startToDate" maxlength="10"
									disabled="${command.saveMode eq 'V'}" />
								<label class="input-group-addon" for="startToDate"><i
									class="fa fa-calendar"></i><span class="hide"> <spring:message
											code="" text="icon" /></span><input type="hidden" id="startToDate"></label>
							</c:if>
							<c:if test="${command.saveMode eq 'A'}">
								<form:input path="workOrderDto.startDate"
									cssClass="form-control datepickers mandColorClass"
									id="startToDate" maxlength="10" />
								<label class="input-group-addon" for="startToDate"><i
									class="fa fa-calendar"></i><span class="hide"> <spring:message
											code="" text="icon" /></span><input type="hidden" id="startToDate"></label>
							</c:if>
						</div>
					</div>
					<label class="col-sm-2  control-label required-control"><spring:message
							code="work.assignee" text="Work Assignee" /></label>
					<div class="col-sm-4">
						<form:select path="workOrderDto.multiSelect"
							class="form-control mandColorClass" multiple="multiple"
							id="empId" disabled="${command.saveMode eq 'V'}">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.employeeList}" var="employeeList">
								<form:option value="${employeeList.empId}"
									label="${employeeList.empname} ${employeeList.emplname} => ${employeeList.designation.dsgname}"></form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<!-- <div class="tenderShow"></div> -->

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<!-- Start Of File Upload Section -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="wms.WorkOrderDocuments" text="Work Order Documents" />
									<%-- Defect #155962 --%>
									<small class="text-blue-2 text-bold">
										<spring:message code="work.file.upload.tooltip"
											text="(Upload File upto 5MB and Only pdf,doc,docx,jpeg,jpg,png,gif,bmp,xls,xlsx extension(s) file(s) are allowed.)" />
									</small>
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
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
															actionUrl="PwWorkOrderGeneration.html?Download" /></td>

													<c:if test="${command.saveMode ne 'V'}">
														<td class="text-center"><a href='#' id="deleteFile"
															onclick="return false;" class="btn btn-danger btn-sm" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
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

								<div id="workOrderAttachment">
									<div class="table-responsive">
										<c:set var="d" value="0" scope="page" />
										<c:if test="${command.saveMode ne 'V'}">
											<table class="table table-bordered table-striped"
												id="attachDoc">
												<tr>
													<th><spring:message
															code="work.estimate.document.description"
															text="Document Description" /></th>
													<th><spring:message code="work.estimate.upload"
															text="Upload Document" /></th>
													<th scope="col" width="8%"><a
														onclick='fileCountUpload(this);'
														class="btn btn-blue-2 btn-sm addButton" title="<spring:message code="works.management.add" text="Add"></spring:message>"><i
															class="fa fa-plus-circle"></i></a></th>
												</tr>

												<tr class="appendableClass">
													<td><form:input path="attachments[${d}].doc_DESC_ENGL"
															class=" form-control" /></td>

													<td class="text-center"><apptags:formField
															fieldType="7"
															fieldPath="attachments[${d}].uploadedDocumentPath"
															currentCount="${d}" showFileNameHTMLId="true"
															folderName="${d}" fileSize="WORK_COMMON_MAX_SIZE"
															isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="ALL_UPLOAD_VALID_EXTENSION">
														</apptags:formField></td>

													<td class="text-center"><a href='#' id="0_file_${d}"
														onclick="doFileDelete(this)"
														class='btn btn-danger btn-sm delButton' title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
															class="fa fa-trash"></i></a></td>
												</tr>

												<c:set var="d" value="${d + 1}" scope="page" />
											</table>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- End Of File Upload Section -->

					<!-- Start Of Terms & Condition Section -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="work.order.terms.and.conditions"
										text="Terms & Conditions" /></a>
							</h4>
						</div>

						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="workOrdertermsAndCondition">
										<thead>
											<tr>
												<th width="5%"><spring:message code="ser.no" text="" /><input
													type="hidden" id="srNo"></th>

												<th scope="col" width="85%" align="center"><spring:message
														code="work.order.terms.and.conditions"
														text="Terms & Conditions" /></th>

												<c:if test="${command.saveMode ne 'V'}">
													<c:if test="${command.termFlag ne 'Y'}">
														<th class="text-center" width="10%"><button
																type="button" onclick="return false;"
																class="btn btn-blue-2 btn-sm  addWorkOrdertermsAndCondition" title="<spring:message code="works.management.add" text="Add"></spring:message>">
																<i class="fa fa-plus-circle"></i>
															</button></th>
													</c:if>
													<c:if test="${command.termFlag eq 'Y'}">
														<th class="text-center" width="10%">
															<button onclick="addTermsDetails();" type="button"
																onclick="return false;" class="btn btn-blue-2 btn-sm" title="<spring:message code="works.management.add" text="Add"></spring:message>">
																<i class="fa fa-plus-circle"></i>
														</th>
													</c:if>
												</c:if>

											</tr>
										</thead>
										<c:choose>
											<c:when test="${command.saveMode eq 'V'}">
												<c:forEach var="workOrderTermsList"
													items="${command.workOrderDto.workOrderTermsDtoList}"
													varStatus="status">

													<tr class="workOrdertermsAppendableClass">

														<td><form:input path="" id="sNo${d}" value="${d + 1}"
																readonly="true" cssClass="form-control " /> <form:hidden
																path="workOrderDto.workOrderTermsDtoList[${d}].termsId"
																id="workOrdertermsId${d}" /></td>

														<td><form:textarea
																path="workOrderDto.workOrderTermsDtoList[${d}].termsDesc"
																cssClass="form-control control-label "
																id="workOrdertermsDesc${d}" disabled="true" /></td>


													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:when
												test="${fn:length(command.workOrderDto.workOrderTermsDtoList) > 0}">
												<c:forEach var="workOrderTermsList"
													items="${command.workOrderDto.workOrderTermsDtoList}"
													varStatus="status">
													<tr class="workOrdertermsAppendableClass">

														<td><form:input path="" id="sNo${d}" value="${d + 1}"
																readonly="true" cssClass="form-control " /> <form:hidden
																path="workOrderDto.workOrderTermsDtoList[${d}].termsId"
																id="workOrdertermsId${d}" /></td>
														<td><c:if test="${command.termFlag eq 'Y'}">
																<form:select
																	path="workOrderDto.workOrderTermsDtoList[${d}].termsDesc"
																	Class="form-control " id="workOrdertermsDesc${d}">
																	<form:option value="">Select</form:option>
																	<c:forEach items="${command.termsList}" var="lookUp">
																		<form:option value="${lookUp}">${lookUp} </form:option>
																	</c:forEach>
																</form:select>
															</c:if> <c:if test="${command.termFlag ne 'Y'}">
																<form:textarea
																	path="workOrderDto.workOrderTermsDtoList[${d}].termsDesc"
																	cssClass="form-control control-label "
																	id="workOrdertermsDesc${d}" />
															</c:if></td>

														<td class="text-center"><a href='#'
															onclick='return false;'
															class='btn btn-danger btn-sm deleteWorkOrderTermsDetails' title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
																class="fa fa-trash"></i></a></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tbody>
													<tr class="workOrdertermsAppendableClass">

														<td><form:input path="" id="sNo${d}" value="1"
																readonly="true" cssClass="form-control" /></td>

														<td><c:if test="${command.termFlag eq 'Y'}">
																<form:select
																	path="workOrderDto.workOrderTermsDtoList[${d}].termsDesc"
																	Class="form-control " id="workOrdertermsDesc${d}">
																	<form:option value="">Select</form:option>
																	<c:forEach items="${command.termsList}" var="lookUp">
																		<form:option value="${lookUp}">${lookUp} </form:option>
																	</c:forEach>
																</form:select>
															</c:if> <c:if test="${command.termFlag ne 'Y'}">
																<form:textarea
																	path="workOrderDto.workOrderTermsDtoList[${d}].termsDesc"
																	cssClass="form-control control-label "
																	id="workOrdertermsDesc${d}" />
															</c:if></td>

														<td class="text-center"><a href='#'
															onclick='return false;'
															class='btn btn-danger btn-sm deleteWorkOrderTermsDetails' title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
																class="fa fa-trash"></i></a></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</tbody>
											</c:otherwise>
										</c:choose>

									</table>
								</div>
							</div>
						</div>
					</div>
					<!-- End Of Terms & Condition Section -->
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							title='<spring:message code="works.management.save" text="Save" />'
							onclick="saveWorkOrder(this);">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<button class="btn btn-warning" onclick="resetFormLevel(this);"
							title='<spring:message code="works.management.reset" text="Reset" />'
							type="button">
							<i class="fa fa-undo padding-right-5"></i>
							<spring:message code="works.management.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						title='<spring:message code="works.management.back" text="Back" />'
						name="button-Cancel" id="button-Cancel" onclick="backWorkOrder();">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>