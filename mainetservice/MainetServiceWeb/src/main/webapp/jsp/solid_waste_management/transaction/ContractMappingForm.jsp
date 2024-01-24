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
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/ContractMapping.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="contract.mapping.form.heading"
					text="Contract Mapping" />
			</h2>			
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with" /><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="ContractMapping.html" name="ContractMapping"
				id="ContractMappingForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div id="receipt">
					<div class="form-group">
						<c:choose>
							<c:when test="${command.saveMode eq 'V'}">
								<apptags:input labelCode="contract.mapping.contract.no"
									path="contractMappingDTO.contractNo" isMandatory="false"
									cssClass="" isDisabled="true"></apptags:input>
							</c:when>
							<c:otherwise>
								<label class="control-label col-sm-2 required-control"
									for="ContractNo"><spring:message
										code="contract.mapping.contract.no" text="Contract No." /></label>
								<div class="col-sm-4">
									<form:select id="ContractNo"
										onchange="SearchContractNo(this.value);"
										path="vendorContractMappingDTO.contId" class=" form-control">
										<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
										<c:forEach items="${command.contractMappingDTOList}"
											var="lookUp">
											<form:option value="${lookUp.contId}" code="${lookUp.contId}">${lookUp.contractNo}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="padding-10 clear">&nbsp;</div>
					<div class="overflow-scroll">
						<div id="export-excel">
							<c:if test="${command.contractMappingDTO.contId ne null}">
								<div class="table-responsive margin-bottom-10">
									<table class="table table-bordered table-stripedform">
										<thead>
											<tr>
												<th><spring:message code="contract.mapping.contract.no"
														text="Contract No." /></th>
												<th><spring:message code="contract.mapping.department"
														text="Department" /></th>
												<th><spring:message
														code="contract.mapping.represented.by"
														text="Represented By" /></th>
												<th><spring:message code="contract.mapping.vendor.name"
														text="Vendor Name" /></th>
												<th><spring:message
														code="contract.mapping.contract.from.date"
														text="Contract From Date" /></th>
												<th><spring:message
														code="contract.mapping.contract.to.date"
														text="Contract To Date" /></th>
												<th width="120"><spring:message
														code="contract.mapping.view.mapping" text="View Contract" /></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>${command.contractMappingDTO.contractNo}</td>
												<td>${command.contractMappingDTO.deptName}</td>
												<td>${command.contractMappingDTO.representedBy}</td>
												<td>${command.contractMappingDTO.vendorName}</td>
												<td>${command.contractMappingDTO.fromDate}</td>
												<td>${command.contractMappingDTO.toDate}</td>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2 btn-sm"
														onclick="getViewContractMappingForm('ContractAgreement.html','${command.saveMode}',${command.contractMappingDTO.contId});"
														data-toggle="tooltip">
														<strong class="fa fa-eye"></strong>
													</button>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</c:if>
							<div class="panel-group accordion-toggle"
								id="accordion_single_collapse">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-target="#UnitDetail" class="collapsed"
												data-toggle="collapse"
												data-parent="#accordion_single_collapse" href="#UnitDetail">
												<spring:message code="contract.mapping.task.mapping"
													text="Task Mapping" />
											</a>
										</h4>
									</div>

									<div class="panel-default">
										<div class="panel-collapse collapse in" id="UnitDetail">
											<div class=" clear padding-10">
												<c:set var="d" value="0" scope="page" />
												<table id="unitDetailTable"
													class="table table-striped table-bordered appendableClass unitDetails">
													<thead>
														<tr>
															<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
																pathPrefix="VendorContractMappingList[0].codWard"
																isMandatory="true" hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true"
																cssClass="form-control required-control" showAll="false"
																hasTableForm="true" showData="false"/>

															<th width="150" class=""><spring:message
																	code="contract.mapping.task" text="Task" /></th>

															<th class=""><spring:message
																	code="contract.mapping.waste.type" text="Waste Type" /></th>
															<th><spring:message
																	code="contract.mapping.garbage.volume"
																	text="Garbage Volume" /></th>
															<th><spring:message code="contract.mapping.route.no"
																	text="Beat No." /></th>
															<th><spring:message
																	code="contract.mapping.Employee.Count"
																	text="Employee Count." /></th>
															<th><spring:message
																	code="contract.mapping.Vehicle.Count"
																	text="Vehicle Count." /></th>
															<c:if test="${command.saveMode ne 'V'}">		
																<th class="text-center" width="8%"><spring:message code="solid.waste.action" text="Action" /></th>
															</c:if>
														</tr>
													</thead>
													<tbody>
														<c:choose>
															<c:when test="${command.saveMode eq 'V'}">
																<c:forEach items="${command.vendorContractMappingList}"
																	var="data" varStatus="index">
																	<tr class="firstUnitRow">
																		<apptags:lookupFieldSet baseLookupCode="SWZ"
																			hasId="true" 
																			pathPrefix="VendorContractMappingList[${index.index}].codWard"
																			isMandatory="true" hasLookupAlphaNumericSort="true"
																			hasSubLookupAlphaNumericSort="true"
																			cssClass="form-control required-control "
																			showAll="false" hasTableForm="true" showData="true"
																			disabled="true" columnWidth="8%" />

																		<td width="310"><c:set var="baseLookupCode"
																				value="TSK" /> <apptags:lookupField
																				items="${command.getLevelData(baseLookupCode)}"
																				selectOptionLabelCode="selectdropdown"
																				path="VendorContractMappingList[${index.index}].mapTaskId"
																				cssClass="form-control" isMandatory="true"
																				hasId="true" disabled="true" hasTableForm="true" /></td>


																		<td width="310"><c:set var="baseLookupCode"
																				value="WTY" /> <apptags:lookupField
																				items="${command.getSortedLevelData(baseLookupCode,1)}"
																				selectOptionLabelCode="selectdropdown"
																				path="VendorContractMappingList[${index.index}].mapWastetype"
																				cssClass="form-control" isMandatory="true"
																				hasId="true" hasTableForm="true" disabled="true" /></td>
																		<td width="300">
																			<div class="input-group col-sm-12 ">
																				<form:input
																					path="VendorContractMappingList[${index.index}].mapGarbage"
																					class="form-control mandColorClass" id="garbageId"
																					disabled="true" />
																				<div class='input-group-field'>
																					<form:select
																						path="VendorContractMappingList[${index.index}].mapGarbageUnit"
																						class="form-control mandColorClass" label="Select"
																						id="garbageUnit" disabled="true">
																						<form:option value="">
																							<spring:message code='master.selectDropDwn' />
																						</form:option>
																						<c:forEach items="${command.getLevelData('UOM')}"
																							var="lookup">
																							<c:if test="${lookup.otherField eq 'WEIGHT'}">
																								<form:option value="${lookup.lookUpId}"
																									code="${lookup.lookUpCode}">${lookup.lookUpCode}</form:option>
																							</c:if>
																						</c:forEach>
																					</form:select>
																				</div>
																			</div>
																		</td>
																		<td width="100px"><form:select
																				path="VendorContractMappingList[0].beatId"
																				cssClass="form-control  mandColorClass" id="beatId" disabled="true">
																				<form:option value="">
																					<spring:message code='master.selectDropDwn' />
																				</form:option>
																				<c:forEach items="${command.routeList}"
																					var="routeDto">
																					<form:option value="${routeDto.beatId}" code="">${routeDto.beatNo}</form:option>
																				</c:forEach>
																			</form:select></td>

																		<td><form:input
																				path="VendorContractMappingList[0].empCount"
																				class="form-control mandColorClass hasNumber" id="empCount" disabled="true"/></td>
																		<td><form:input
																				path="VendorContractMappingList[0].vehicleCount"
																				class="form-control mandColorClass hasNumber"
																				id="vehicleCount" disabled="true" /></td>																	
																	</tr>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<tr class="firstUnitRow">
																	<apptags:lookupFieldSet baseLookupCode="SWZ"
																		hasId="true"
																		pathPrefix="VendorContractMappingList[0].codWard"
																		isMandatory="true" hasLookupAlphaNumericSort="true"
																		hasSubLookupAlphaNumericSort="true"
																		cssClass="form-control required-control "
																		showAll="false" hasTableForm="true" showData="true"
																		columnWidth="8%" />

																	<td width="200"><c:set var="baseLookupCode"
																			value="TSK" /> <apptags:lookupField
																			items="${command.getLevelData(baseLookupCode)}"
																			selectOptionLabelCode="selectdropdown"
																			path="VendorContractMappingList[0].mapTaskId"
																			cssClass="form-control" isMandatory="true"
																			hasId="true" hasTableForm="true" /></td>

																	<td width="210"><c:set var="baseLookupCode"
																			value="WTY" /> <apptags:lookupField
																			items="${command.getSortedLevelData(baseLookupCode,1)}"
																			selectOptionLabelCode="selectdropdown"
																			path="VendorContractMappingList[0].mapWastetype"
																			cssClass="form-control" isMandatory="true"
																			hasId="true" hasTableForm="true" /></td>
																	<td width="250">
																		<div class="input-group col-sm-12 ">
																			<form:input
																				path="VendorContractMappingList[0].mapGarbage"
																				class="form-control mandColorClass" id="garbageId" />
																			<div class='input-group-field'>
																				<form:select
																					path="VendorContractMappingList[0].mapGarbageUnit"
																					class="form-control mandColorClass" label="Select"
																					id="garbageUnit">
																					<form:option value="">
																						<spring:message code='master.selectDropDwn' />
																					</form:option>
																					<c:forEach items="${command.getLevelData('UOM')}"
																						var="lookup">
																						<c:if test="${lookup.otherField eq 'WEIGHT'}">
																							<form:option value="${lookup.lookUpId}"
																								code="${lookup.lookUpCode}">${lookup.lookUpCode}</form:option>
																						</c:if>
																					</c:forEach>
																				</form:select>
																			</div>
																		</div>
																	</td>
																	<td width="100px"><form:select
																			path="VendorContractMappingList[0].beatId"
																			cssClass="form-control  mandColorClass" id="beatId">
																			<form:option value="">
																				<spring:message code='master.selectDropDwn' />
																			</form:option>
																			<c:forEach items="${command.routeList}"
																				var="routeDto">
																				<form:option value="${routeDto.beatId}" code="">${routeDto.beatNo}</form:option>
																			</c:forEach>
																		</form:select></td>

																	<td><form:input
																			path="VendorContractMappingList[0].empCount"
																			class="form-control mandColorClass hasNumber" id="empCount" /></td>
																	<td><form:input
																			path="VendorContractMappingList[0].vehicleCount"
																			class="form-control mandColorClass hasNumber" id="vehicleCount" /></td>
																	<td class="text-center" width="8%">
																	<a	href="javascript:void(0);" data-toggle="tooltip"
																		data-original-title="Add"
																		class="addCF btn btn-success btn-sm unit"
																		id="addUnitRow"><i class="fa fa-plus-circle"></i></a>
																	<a href="#"
																		class="btn btn-danger btn-sm" title="Delete"
																		onclick="deleteEntry($(this),'removedIds');"> <strong
																			class="fa fa-trash"></strong>
																	</a></td>
																</tr>
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
					</div>
				</div>
				<div class="text-center padding-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveContractMapping(this);">
							<spring:message code="solid.waste.submit" text="Submit"></spring:message>
						</button>
						<button type="Reset" class="btn btn-warning"
							onclick="addContractMappingForm('ContractMapping.html','AddContractMapping');">
							<spring:message code="solid.waste.reset" text="Reset"></spring:message>
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backcontractMapping();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
