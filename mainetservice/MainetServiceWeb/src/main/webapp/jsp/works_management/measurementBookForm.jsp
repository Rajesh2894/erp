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
	src="js/works_management/measurementBook.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	jQuery('#tab1').addClass('active');
</script>
<style>
textarea::placeholder {
	color: black !important;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="mb.title" text="Measurement Book" />
			</h2>
			<div class="additional-btn">
				  <apptags:helpDoc url="MeasurementBook.html"></apptags:helpDoc>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="MeasurementBook.html" class="form-horizontal"
				name="measurementBook" id="measurementBook">
				<!-- Start Validation include tag -->
				<form:hidden path="requestFormFlag" id="requestFormFlag" />
				<form:hidden path="cpdModeCatSor" id="cpdModeCatSor" />
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<form:hidden path="" id="StartDate"
					value="${command.workOrderDto.contractFromDate}" />
				<form:hidden path="" id="EndDate"
					value="${command.workOrderDto.contractToDate}" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<jsp:include page="/jsp/works_management/measurementBookTab.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
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
											id="workId" data-rule-required="true"
											onchange="OpenCreateMb(this);">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.workOrderDtoList}" var="lookUp">
												<form:option value="${lookUp.workId}" code="">${lookUp.workOrderNo}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label for="" class="col-sm-2 control-label"><spring:message
											code="work.order.contract.no" text="Contract No." /> </label>
									<div class="col-sm-4">
										<form:input path="workOrderDto.contractMastDTO.contNo"
											cssClass="form-control" readonly="true" id="contNo" />
									</div>
								</div>

								<div class="form-group">
									<apptags:textArea labelCode="project.master.projname"
										path="workOrderDto.projName" isReadonly="true"></apptags:textArea>

									<apptags:textArea labelCode="work.def.workname"
										path="workOrderDto.workName" isReadonly="true"></apptags:textArea>
								</div>

								<div class="form-group">
									<label for="" class="col-sm-2 control-label"><spring:message
											code="mb.WorkOrderAmount" text="Work Order Amount" /></label>
									<div class="col-sm-4">
										<form:input path="workOrderDto.contractValue"
											cssClass="form-control text-right" readonly="true" id="contractValue" />
									</div>

									<label for="" class="col-sm-2 control-label"><spring:message
											code="work.order.workOrder.date"
											text="Work Order Agreement Date " /> </label>
									<div class="col-sm-4">
										<form:input path="workOrderDto.startDate"
											cssClass="form-control dates" readonly="true" id="startDate" />
									</div>
								</div>
								<%-- <div class="form-group">
									<label for="" class="col-sm-2"><spring:message
											code="work.order.contract.from.date"
											text="Agreement From Date" /></label>
									<div class="col-sm-4">
										<form:input path="workOrderDto.contractFromDate"
											cssClass="form-control dates" readonly="true" id="StartDate" />
									</div>

									<label for="" class="col-sm-2"><spring:message
											code="work.order.contract.to.date" text="Agreement To Date" />
									</label>
									<div class="col-sm-4">
										<form:input path="workOrderDto.contractToDate"
											cssClass="form-control dates" readonly="true" id="EndDate" />
									</div>
								</div> --%>
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
								<div class="form-group">

									<label for="" class="col-sm-2 control-label required-control"><spring:message
											code="mb.ActualMeasurementTakenDate"
											text="Actual Measurement Taken Date" /> </label>
									<div class="col-sm-4">
									<div class="input-group">
										<form:input path="mbMasDto.workMbTakenDate"
											cssClass="form-control datepicker" id="workMbTakenDate" />
										<label class="input-group-addon" for="workMbTakenDate"><i
											class="fa fa-calendar"></i><span class="hide"> <spring:message
													code="" text="icon" /></span><input type="hidden" id="workMbTakenDate"></label>
									</div>
									</div>
								</div>

								<%-- <div class="form-group">
									<label for="" class="col-sm-2"><spring:message
											code="mb.LedgerNo" text="Ledger No." /></label>
									<div class="col-sm-4">
										<form:input path="mbMasDto.ledgerNo"
											Class="form-control hasNumber" id="" />
									</div>

									<label for="" class="col-sm-2"><spring:message
											code="mb.PageNo" text="Page No." /> </label>
									<div class="col-sm-4">
										<form:input path="mbMasDto.pageNo"
											Class="form-control hasNumber" id="" />
									</div>
								</div> --%>

								<div class="form-group">
									<label for="" class="col-sm-2 control-label"><spring:message
											code="work.management.description" text="Description" /></label>
									<div class="col-sm-4">
										<form:input path="mbMasDto.description"
											cssClass="form-control" id="description" />
									</div>
									<c:if test="${command.saveMode eq 'V'}">
										<label for="" class="col-sm-2"><spring:message
												code="work.order.employee.enginner.name"
												text="Engineer Name" /></label>
										<div class="col-sm-4">
											<form:textarea path="workOrderDto.workAssigneeName"
												cssClass="form-control" id="" readonly="true" />
										</div>
									</c:if>
									<c:if test="${command.saveMode ne 'V'}">
										<label class="col-sm-2  control-label required-control"><spring:message
												code="work.order.employee.enginner.name"
												text="Engineer Name" /></label>
										<div class="col-sm-4">
											<form:select path="mbMasDto.mbMultiSelect"
												class="form-control mandColorClass" multiple="multiple"
												id="empId" disabled="${command.requestFormFlag eq 'AP'}">
												<form:option value="">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<c:forEach items="${command.employeeList}"
													var="employeeList">
													<form:option value="${employeeList.empId}"
														label="${employeeList.empname} => ${employeeList.designation.dsgname}"></form:option>
												</c:forEach>
											</form:select>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default" id="selectedItems">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="mb.SelectWorkOrderItemsMb"
										text="Select Work Order Items for Measurement Book" /></a>
							</h4>
						</div>

						<c:if test="${command.saveMode ne 'V'}">
							<div class="col-sm-4">
								<a class="text-center" href="#"
									onclick="openMBEstimateForm(this);"
									style="text-decoration: underline; text-align: center;"><span>
										<%-- <c:out
											value="Add Estimate"></c:out> --%> <spring:message
											code="wms.AddEstimate" text="" />
								</span></a>
							</div>
						</c:if>


						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page" />
								<div class="table-responsive clear">
									<table class="table table-bordered table-striped"
										id="datatables">
										<c:choose>
											<c:when test="${cpdModeCatSor eq 'N'}">
												<thead>
													<tr>
														<th scope="col" width="10%" align="center"><spring:message
																code="work.management.select" /></th>
														<th scope="col" width="15%" align="center"><spring:message
																code="work.estimate.work.estimate.number"
																text="Work Estimate No." /></th>
														<th scope="col" width="15%" align="center"><spring:message
																code="mb.sorNonSor" text="SOR/Non-SOR" /></th>
														<th scope="col" width="15%" align="center"><spring:message
																code="sor.category" text="Chapter" /></th>
														<th scope="col" width="15%" align="center"><spring:message
																code="sor.subCategory" text="SubCategory" /></th>
														<th scope="col" width="10%" align="center"><spring:message
																code="material.master.itemcode" /></th>
														<th scope="col" width="30%" align="center"><spring:message
																code="work.management.description" text="Description" /></th>
														<th scope="col" width="10%" align="center"><spring:message
																code="work.management.unit" text="Unit" /></th>
														<th scope="col" width="10%" align="center"><spring:message
																code="mb.BillofQuantity" text="Bill of Quantity" /></th>
														<th scope="col" width="15%" align="center"><spring:message
																code="sor.baserate" text="Rate" /></th>
													</tr>
												</thead>
											</c:when>
											<c:otherwise>
												<thead>
													<tr>
														<th scope="col" width="10%" align="center"><spring:message
																code="work.management.select" text="Select" /></th>
														<th scope="col" width="15%" align="center"><spring:message
																code="work.estimate.work.estimate.number"
																text="Work Estimate No." /></th>
														<th scope="col" width="10%" align="center"><spring:message
																code="mb.sorNonSor" text="SOR/Non-SOR" /></th>
														<th scope="col" width="15%" align="center"><spring:message
																code="sor.category" text="Category" /></th>
														<th scope="col" width="10%" align="center"><spring:message
																code="wms.itemCode" text="Item Code" /></th>
														<th scope="col" width="20%" align="center"><spring:message
																code="work.management.description" text="Description" /></th>
														<th scope="col" width="10%" align="center"><spring:message
																code="work.management.unit" text="Unit" /></th>
														<th scope="col" width="10%" align="center"><spring:message
																code="mb.BillofQuantity" text="Bill of Quantity" /></th>
														<th scope="col" width="10%" align="center"><spring:message
																code="sor.baserate" text="Rate" /></th>
													</tr>
												</thead>
											</c:otherwise>
										</c:choose>

										<tbody>
											<c:forEach items="${command.estimateMasDtoList}" var="mstDto">
												<tr>
													<form:hidden path="estimateMasDtoList[${d}].workEstemateId" />
													<form:hidden path="estimateMasDtoList[${d}].estimateType" />
													<td align="center"><form:checkbox
															path="estimateMasDtoList[${d}].checkBox" id="check${d}" /></td>
													<td align="center">${mstDto.workeEstimateNo}</td>
													<td align="center">${mstDto.workMbFlag}</td>
													<td align="center"><form:select
															path="estimateMasDtoList[${d}].sordCategory"
															class="form-control" readonly="true" disabled="true">
															<form:option value="0">
																<spring:message code="holidaymaster.select" />
															</form:option>
															<c:forEach items="${command.getLevelData('WKC')}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>
													<c:if test="${cpdModeCatSor eq 'N'}">
														<td>${mstDto.sordSubCategory}</td>
													</c:if>

													<td align="center">${mstDto.sorDIteamNo}</td>
													<td><form:textarea path=""
															placeholder="${mstDto.sorDDescription}"
															class="form-control text-left"
															style="margin: 0px; height: 33px;" readonly="true"
															title="${mstDto.sorDDescription}" /></td>
													<td align="center">${mstDto.sorIteamUnitDesc}</td>
													<td align="right">${mstDto.workEstimQuantity}</td>
													<td align="right">${mstDto.sorBasicRate}</td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</tbody>
									</table>
								</div>


							</div>
						</div>
					</div>
					<div class="text-center clear padding-10">
						<c:if
							test="${command.saveMode eq 'E' || command.saveMode eq 'C' }">
							<button type="button" id="save"
								class="btn btn-success btn-submit"
								onclick="addSelectedItems(this);">
								<i class="fa fa-sign-out padding-right-5"></i>
								<spring:message code="work.management.SaveContinue"
									text="Save & Continue" />
							</button>
						</c:if>
						<c:choose>
							<c:when
								test="${command.requestFormFlag eq 'AP' || command.saveMode eq 'AP' }">
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" onclick="backToApprvForm();"
									id="backButton">
									<i class="fa fa-chevron-circle-left padding-right-5"></i>
									<spring:message code="works.management.back" text="" />
								</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel"
									onclick="window.location.href='MeasurementBook.html'"
									id="backButton">
									<i class="fa fa-chevron-circle-left padding-right-5"></i>
									<spring:message code="works.management.back" text="" />
								</button>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
