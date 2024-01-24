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
	src="js/works_management/raBillGeneration.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
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
				<spring:message code="wms.RunningAccountBill"
					text="Running Account Bill" />
			</h2>
			<div class="additional-btn">
				 <apptags:helpDoc url="raBillGeneration.html"></apptags:helpDoc>

			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<form:form action="raBillGeneration.html" class="form-horizontal"
				name="raBillGeneration" id="raBillGeneration">
				<!-- Start Validation include tag -->
				<form:hidden path="status" id="status" />
				<form:hidden path="" id="tenderTypeCode"
					value="${command.tndWorkDto.tenderTypeCode}" />
				<form:hidden path="" id="contractBgDate"
					value="${command.contractbgDate}" />	
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<form:input type="hidden" path="removeTaxDetIds"
					id="removeTaxDetIds" />
				<form:input type="hidden" path="removeTaxWithIds"
					id="removeTaxWithIds" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
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
										code="wms.ProjectInformation" text="Project Information" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="project.master.projname" /></label>
									<div class="col-sm-4">
										<form:select path="workDefDto.projId" id="projId"
											class="form-control chosen-select-no-results"
											onchange="getWorkName(this);">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:if
												test="${userSession.getCurrent().getLanguageId() ne '1'}">
												<c:forEach items="${command.projectMasterList}"
													var="activeProjName">
													<form:option value="${activeProjName.projId }"
														code="${activeProjName.projId }">${activeProjName.projNameReg}</form:option>
												</c:forEach>
											</c:if>
											<c:if
												test="${userSession.getCurrent().getLanguageId() eq '1'}">
												<c:forEach items="${command.projectMasterList}"
													var="activeProjName">
													<form:option value="${activeProjName.projId }"
														code="${activeProjName.projId }">${activeProjName.projNameEng}</form:option>
												</c:forEach>
											</c:if>
										</form:select>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="work.def.workname" /></label>
									<div class="col-sm-4">
										<form:select path="workDefDto.workId" id="workId"
											class="form-control chosen-select-no-results"
											onchange="openRaBillGeneration(this);">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.workDefList}"
												var="activeProjName">
												<form:option value="${activeProjName.workId }"
													code="${activeProjName.workId }">${activeProjName.workName}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-sm-2 control-label required-control"><spring:message
											code="work.order.workOrder.no" text="Work Order No." /></label>

									<div class="col-sm-4">
										<form:input path="orderDto.workOrderNo" id="workOrderNo"
											class="form-control mandColorClass dates" readonly="true" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="wms.PreviousRunningBillInformation"
										text="Previous RA Bill Details" /></a>
							</h4>
						</div>

						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive clear">
									<table class="table table-bordered table-striped" id="previous">
										<thead>
											<tr>
												<th scope="col" width="10%" align="center"><spring:message
														code="wms.RADate" text="RA Date" /></th>
												<th scope="col" width="15%" align="center"><spring:message
														code="wms.RABillNo" text="RA Bill No." /></th>
												<th scope="col" width="15%" align="center"><spring:message
														code="wms.RABillAmount" text="RA Bill Amount" /></th>
												<th scope="col" width="15%" align="center"><spring:message
														code="wms.TaxAmount" text="Deduction" /></th>
												<%-- <th scope="col" width="15%" align="center"><spring:message
														code="wms.TotalPaidAmount" text="Total Paid Amount" /></th> --%>
												<th scope="col" width="10%" align="center"><spring:message
														code="wms.Remark" text="Remark" /></th>
												<%-- <th scope="col" width="30%" align="center"><spring:message
														code="" text="Action" /></th> --%>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${command.raBillList}" var="mstDto">
												<tr>
													<td align="center">${mstDto.raDate}</td>
													<td align="right">${mstDto.raBillno}</td>
													<td align="right">${mstDto.raBillAmt}</td>
													<td align="right">${mstDto.raTaxAmt}</td>
													<%-- <td align="right">${mstDto.raPaidAmt}</td> --%>
													<td>${mstDto.raRemark}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>


					<div class="panel panel-default" id="selectedItems">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="wms.MBNotPaid" text="MB Not Paid" /></a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page" />
								<div class="table-responsive clear">
									<table class="table table-bordered table-striped" id="mbTable">

										<thead>
											<tr>
												<th scope="col" width="20%" align="center"><spring:message
														code="wms.SelectMeasurementBook"
														text="Select Measurement Book" /></th>
												<th scope="col" width="20%" align="center"><spring:message
														code="wms.MeasurementBookDate"
														text="MB Date" /></th>
												<th scope="col" width="20%" align="center"><spring:message
														code="wms.MeasurementBookNo" text="Measurement Book No." /></th>
												<%-- <th scope="col" width="20%" align="center"><spring:message
														code="mb.PageNo" text="Page No." /></th> --%>
												<th scope="col" width="20%" align="center"><spring:message
														code="work.estimate.Total" text="Amount" /></th>

											</tr>
										</thead>
										<tfoot>
											<tr>
												<th colspan="3" style="text-align: right"><spring:message
														code="mb.Total" text="Total:" /></th>
												<th><form:input id="mbTotal"
														class=" form-control text-right"
														path="billMasDto.raMbAmount" readonly="true"
														onkeypress="return hasAmount(event, this, 10, 2)" /></th>
											</tr>
										</tfoot>
										<tbody>
											<c:forEach items="${command.mbList}" var="mstDto">
												<tr class="appendableClass">
													<td align="center"><form:checkbox
															path="mbList[${d}].checkBox" id="check${d}"
															onclick="calculateMbTotal();" /></td>
													<td align="center">${mstDto.mbTakenDate}</td>
													<td align="center">${mstDto.workMbNo}</td>
													<%-- <td align="center">${mstDto.pageNo}</td> --%>
													<td align="right"><form:input path=""
															class="form-control hasNumber text-right"
															value="${mstDto.mbTotalAmt}" id="mbAmount${d}"
															readonly="true" /></td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<form:hidden path="" value="${command.tndWorkDto.action}"
						id="tndAction" />
						
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a4" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a4"> <spring:message
										code="wms.TenderWorkDetails" text="Tender Work Details" /></a>
							</h4>
						</div>
						<div id="a4" class="panel-collapse collapse in">
							<div class="panel-body">

								<table class="table table-bordered table-striped"
									id="tendWorkUpdate">
									<thead>
										<tr>
											<th width="10%"><spring:message code='work.def.workname'
													text="Work Name" /></th>
											<th width="10%"><spring:message
													code='tender.estimate.cost' text="Estimate Cost" /></th>
											<th width="10%"><spring:message code='tender.type'
													text="Tender Type" /></th>
											<th width="10%"><spring:message
													code='work.measurement.sheet.details.value' text="Value" /></th>
											<th width="10%"><spring:message code='milestone.amount'
													text="Amount" /></th>
										</tr>
									</thead>
									<tbody>

										<tr class="tenderWorkUpdate">

											<td><form:input path="" id="workName"
													cssClass="form-control"
													value="${command.tndWorkDto.workName}" readonly="true" /></td>
											<td><form:input path="" id="workEstimateAmt"
													cssClass="form-control text-right"
													value="${command.tndWorkDto.workEstimateAmt}" readonly="true" /></td>

											<td><form:input path="" id="tenderTypeDesc"
													cssClass="form-control text-center"
													value="${command.tndWorkDto.tenderTypeDesc}"
													readonly="true" /></td>

											<td><form:input path=""
													cssClass="form-control  text-right" id="tenderValue"
													value="${command.tndWorkDto.tenderValue}" readonly="true" /></td>
											<td><form:input path="" readonly="true"
													cssClass="form-control  text-right" id=""
													value="${command.tndWorkDto.tenderAmount}"  /></td>
												<form:hidden path="" value="${command.tndWorkDto.tenderAmount}"
														id="tenderAmount" />
											
										</tr>

									</tbody>
								</table>
							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a5" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a5"> <spring:message
										code="wms.WithHeldDeductionsDetails"
										text="Withheld Deductions Details" /></a>
							</h4>
						</div>

						<div id="a5" class="panel-collapse collapse in">
							<div class="panel-body">
							<c:set var="d" value="0" scope="page" />
								<table class="table table-bordered table-striped"
									id="withHeldTable">
									<thead>
										<tr>

											<th width="25%"><spring:message code="wms.TaxCategory"
													text="Tax Name" /></th>
													
											<th width="10%"><spring:message
													 text="Remark" /></th>

											<th width="10%"><spring:message
													code="work.estimate.Total" text="Amount" /></th>
											<c:if
												test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
												<th width="13%"><spring:message
														code="works.management.action" text="Action" /></th>
											</c:if>
										</tr>
									</thead>
									<tfoot>
										<tr>
										<th></th>
											<th colspan="1" style="text-align: right"><spring:message
													code="wms.SanctionedAmount" text="Sanctioned Amount :" /></th>
											<th><form:input path="" id="sanctionedAmt"
													class="form-control mandColorClass text-right"
													readonly="true" /></th>

										</tr>
									</tfoot>
									<tbody>
									
									<c:choose>
											<c:when test="${command.saveMode eq 'V' || command.saveMode eq 'E'}">
												<c:forEach var="taxData1"
													items="${command.billMasDto.raBillTaxDtoWith}"
													varStatus="status">

										<tr class="withHeld">
											<td align="center"><form:select
													path="billMasDto.raBillTaxDtoWith[${d}].taxId"
													cssClass="form-control mandColorClass"
													id="taxDid${d}" readonly="true">
													<form:option value="">
														<spring:message code='work.management.select' />
													</form:option>
													<c:forEach items="${command.listTaxDefinationDto}"
														var="listTaxDesc">
														<c:if
															test="${fn:contains(listTaxDesc.taxDesc, 'Withheld')}">

															<form:option selected="selected"
																value="${listTaxDesc.taxId}"
																code="${listTaxDesc.taxId},${listTaxDesc.raTaxType},${listTaxDesc.raTaxPercent},${listTaxDesc.raTaxValue},${listTaxDesc.raTaxFact}">${listTaxDesc.taxDesc}</form:option>
														</c:if>
													</c:forEach>
												</form:select>
												<form:input type="hidden"
																path="billMasDto.raBillTaxDtoWith[${d}].raTaxId" /></td></td></td>
												
												<td><form:input
													path="billMasDto.raBillTaxDtoWith[${d}].raRemark"
													class="form-control text-right" id="remark${d}"/></td>


											<td><form:input
													path="billMasDto.raBillTaxDtoWith[${d}].raTaxValue"
													class="form-control text-right" id="withHeldAmt${d}"
													onkeypress="return hasAmount(event, this, 5, 2)"
													onchange="getAmountFormatInDynamic((this),'withHeldAmt')"
													onblur="calculateSanctionedAmount();" /></td>
											<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">

															<td align="center"><a href="javascript:void(0);" data-toggle="tooltip"
																data-placement="top" onclick='addTaxDetailsWith(this);'
																class="btn btn-blue-2 btn-sm addButton" title="<spring:message code="works.management.add" text="Add"></spring:message>"> <i
																	class="fa fa-plus-circle"></i></a> <a href="javascript:void(0);"
																data-toggle="tooltip" data-placement="top"
																class="btn btn-danger btn-sm delButton"
																onclick="deleteTaxEntryWith($(this),'removeTaxWithIds');" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
																	class="fa fa-minus"></i></a></td>
														</c:if>
													</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
										</c:when>
										<c:otherwise>
										<tr class="withHeld">
											<td align="center"><form:select
													path="billMasDto.raBillTaxDtoWith[${d}].taxId"
													cssClass="form-control mandColorClass"
													id="taxDid${d}" readonly="true">
													<form:option value="">
														<spring:message code='work.management.select' />
													</form:option>
													<c:forEach items="${command.listTaxDefinationDto}"
														var="listTaxDesc">
														<c:if
															test="${fn:contains(listTaxDesc.taxDesc, 'Withheld')}">

															<form:option selected="selected"
																value="${listTaxDesc.taxId}"
																code="${listTaxDesc.taxId},${listTaxDesc.raTaxType},${listTaxDesc.raTaxPercent},${listTaxDesc.raTaxValue},${listTaxDesc.raTaxFact}">${listTaxDesc.taxDesc}</form:option>
														</c:if>
													</c:forEach>
												</form:select>
												<form:input type="hidden"
																path="billMasDto.raBillTaxDtoWith[${d}].raTaxId" /></td></td>
												
												<td><form:input
													path="billMasDto.raBillTaxDtoWith[${d}].raRemark"
													class="form-control text-right" id="remark${d}"/></td>


											<td><form:input
													path="billMasDto.raBillTaxDtoWith[${d}].raTaxValue"
													class="form-control text-right" id="withHeldAmt${d}"
													onkeypress="return hasAmount(event, this, 5, 2)"
													onchange="getAmountFormatInDynamic((this),'withHeldAmt')"
													onblur="calculateSanctionedAmount();" /></td>

											<td align="center"><c:if
													test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
													<a href="javascript:void(0);" data-toggle="tooltip" data-placement="top"
														onclick='addTaxDetailsWith(this);'
														class="btn btn-blue-2 btn-sm addButton" title="<spring:message code="works.management.add" text="Add"></spring:message>"> <i
														class="fa fa-plus-circle"></i></a>
												 <a href="javascript:void(0);" data-toggle="tooltip" data-placement="top"
												class="btn btn-danger btn-sm delButton"
												onclick="deleteTaxEntryWith($(this),'removeTaxWithIds');" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
													class="fa fa-minus"></i></a></c:if>
													</td>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
										</c:otherwise>
										</c:choose>
	
									</tbody>
								</table>

							</div>
						</div>
					</div>




					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a6" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a6"> <spring:message
										code="wms.RABillTaxDetails" text="TDS Details" /></a>
							</h4>
						</div>

						<div id="a6" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="e" value="0" scope="page" />
								<div class="table-responsive clear">
									<table class="table table-bordered table-striped"
										id="raTaxDetailsTab">
										<thead>
											<tr>
												<th width="5%"><spring:message code="ser.no"
														text="Sr. No." /></th>
												<th width="20%"><spring:message code="wms.TaxCategory"
														text="Tax Category" /></th>
												<th width="8%"><spring:message
														code="work.measurement.sheet.details.additiondeduction"
														text="Addition/Deduction" /></th>
												<th width="5%"><spring:message
														code="wms.AmountOrPercentage" text="Amount/Percentage" /></th>
												<th width="5%"><spring:message
														code="wms.PercentageValue" text="Percentage Value" /></th>
												<th width="10%"><spring:message
														code="work.estimate.Total" text="Amount" /></th>
												<c:if
													test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">

													<th width="13%"><spring:message
															code="works.management.action" text="Action" /></th>
												</c:if>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<th colspan="5" style="text-align: right"><spring:message
														code="mb.Total" text="Total:" /></th>
												<th><form:input id="taxTotal"
														class=" form-control text-right"
														path="billMasDto.raTaxAmt" readonly="true"
														onkeypress="return hasAmount(event, this, 10, 2)" /></th>
												<c:if
													test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
													<th></th>
												</c:if>

											</tr>
										</tfoot>
										<c:choose>
											<c:when test="${command.saveMode eq 'V'}">
												<c:forEach var="taxData"
													items="${command.billMasDto.raBillTaxDetails}"
													varStatus="status">
													<tr class="appendableClass">

														<td align="center"><form:input path=""
																cssClass="form-control" id="sequence${e}" value="${e+1}"
																readonly="true" /> <form:input type="hidden"
																path="billMasDto.raBillTaxDetails[${e}].raTaxId" /></td>
														<td align="center"><form:select
																path="billMasDto.raBillTaxDetails[${e}].taxId"
																cssClass="form-control chosen-select-no-results mandColorClass"
																id="taxId${e}">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.listTaxDefinationDto}"
																	var="listTaxDesc">
																	<c:if
																		test="${!fn:contains(listTaxDesc.taxDesc, 'Withheld')}">
																		<form:option value="${listTaxDesc.taxId}"
																			code="${listTaxDesc.taxId},${listTaxDesc.raTaxType},${listTaxDesc.raTaxPercent},${listTaxDesc.raTaxValue},${listTaxDesc.raTaxFact}">${listTaxDesc.taxDesc}</form:option>
																	</c:if>
																</c:forEach>
															</form:select></td>

														<td><form:select
																path="billMasDto.raBillTaxDetails[${e}].raTaxType"
																class="form-control chosen-select-no-results"
																id="taxType${e}" disabled="true">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<form:option value="A">
																	<spring:message
																		code="work.measurement.sheet.details.addition" />
																</form:option>
																<form:option value="D">
																	<spring:message
																		code="work.measurement.sheet.details.deduction" />
																</form:option>
															</form:select></td>

														<td><form:select
																path="billMasDto.raBillTaxDetails[${e}].raTaxFact"
																cssClass="form-control mandColorClass chosen-select-no-results"
																id="taxFact${e}" data-rule-required="true">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.valueTypeList}"
																	var="payType">
																	<form:option value="${payType.lookUpId }"
																		code="${payType.lookUpCode}">${payType.descLangFirst}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="billMasDto.raBillTaxDetails[${e}].raTaxPercent"
																class="form-control text-right" disabled="true"
																id="taxPercent${e}"
																onkeypress="return hasAmount(event, this, 5, 2)"
																onchange="getAmountFormatInDynamic((this),'taxPercent')" /></td>
														<td><form:input
																path="billMasDto.raBillTaxDetails[${e}].raTaxValue"
																class="form-control text-right" disabled="true"
																id="taxValue${e}"
																onkeypress="return hasAmount(event, this, 5, 2)"
																onchange="getAmountFormatInDynamic((this),'taxValue')" /></td>
													</tr>

													<c:set var="e" value="${e + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:when
												test="${fn:length(command.billMasDto.raBillTaxDetails) > 0}">
												<c:forEach var="taxData"
													items="${command.billMasDto.raBillTaxDetails}"
													varStatus="status">
													<tr class="appendableClass">

														<td align="center"><form:input path=""
																cssClass="form-control" id="sequence${e}" value="${e+1}"
																readonly="true" /> <form:input type="hidden"
																path="billMasDto.raBillTaxDetails[${e}].raTaxId" id="" /></td>
														<td align="center"><form:select
																path="billMasDto.raBillTaxDetails[${e}].taxId"
																cssClass="form-control mandColorClass" id="taxId${e}"
																onchange="getTaxDeatils();">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.listTaxDefinationDto}"
																	var="listTaxDesc">
																	<c:if
																		test="${!fn:contains(listTaxDesc.taxDesc, 'Withheld')}">
																		<form:option value="${listTaxDesc.taxId}"
																			code="${listTaxDesc.taxId},${listTaxDesc.raTaxType},${listTaxDesc.raTaxPercent},${listTaxDesc.raTaxValue},${listTaxDesc.raTaxFact},${listTaxDesc.taxPanApp},${listTaxDesc.lookUpOtherField},${listTaxDesc.taxThreshold},${listTaxDesc.taxFixed}">${listTaxDesc.taxDesc}</form:option>
																	</c:if>
																</c:forEach>
															</form:select></td>

														<td><form:select
																path="billMasDto.raBillTaxDetails[${e}].raTaxType"
																class="form-control"
																id="taxType${e}">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<form:option value="A">
																	<spring:message
																		code="work.measurement.sheet.details.addition" />
																</form:option>
																<form:option value="D">
																	<spring:message
																		code="work.measurement.sheet.details.deduction" />
																</form:option>
															</form:select></td>

														<td><form:select
																path="billMasDto.raBillTaxDetails[${e}].raTaxFact"
																class="form-control"
																id="taxFact${e}">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.valueTypeList}"
																	var="payType">
																	<form:option value="${payType.lookUpId }"
																		code="${payType.lookUpCode}">${payType.descLangFirst}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="billMasDto.raBillTaxDetails[${e}].raTaxPercent"
																class="form-control text-right" id="taxPercent${e}"
																onblur="calculateAmt(this);"
																onkeypress="return hasAmount(event, this, 5, 2)"
																onchange="getAmountFormatInDynamic((this),'taxPercent')" /></td>
														<td><form:input
																path="billMasDto.raBillTaxDetails[${e}].raTaxValue"
																class="form-control mandColorClass text-right"
																id="taxValue${e}" onblur="calculateTotalAmt();"
																onkeypress="return hasAmount(event, this, 5, 2)"
																onchange="getAmountFormatInDynamic((this),'taxValue')" /></td>
														<td align="center">
														<c:if
																test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
																<a href="javascript:void(0);" data-toggle="tooltip" data-placement="top"
																	onclick='addTaxDetails();'
																	class="btn btn-blue-2 btn-sm addButton" title="<spring:message code="works.management.add" text="Add"></spring:message>"> <i
																	class="fa fa-plus-circle"></i></a>
															</c:if> <a href="javascript:void(0);" data-toggle="tooltip"
															data-placement="top"
															class="btn btn-danger btn-sm delButton"
															onclick="deleteTaxEntry($(this),'removeTaxDetIds');" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
																class="fa fa-minus"></i></a></td>
													</tr>

													<c:set var="e" value="${e + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableClass">

													<td align="center"><form:input path=""
															cssClass="form-control" id="sequence${e}" value="${e+1}"
															readonly="true" /> <form:input type="hidden"
															path="billMasDto.raBillTaxDetails[${e}].raTaxId" /></td>
													<td align="center"><form:select
															path="billMasDto.raBillTaxDetails[${e}].taxId"
															cssClass="form-control mandColorClass" id="taxId${e}"
															onchange="getTaxDeatils();">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.listTaxDefinationDto}"
																var="listTaxDesc">
																<<c:if
																	test="${!fn:contains(listTaxDesc.taxDesc, 'Withheld')}">
																	<form:option value="${listTaxDesc.taxId}"
																		code="${listTaxDesc.taxId},${listTaxDesc.raTaxType},${listTaxDesc.raTaxPercent},${listTaxDesc.raTaxValue},${listTaxDesc.raTaxFact},${listTaxDesc.taxPanApp},${listTaxDesc.lookUpOtherField},${listTaxDesc.taxThreshold},${listTaxDesc.taxFixed}">${listTaxDesc.taxDesc}</form:option>
																</c:if>
															</c:forEach>
														</form:select></td>

													<td><form:select
															path="billMasDto.raBillTaxDetails[${e}].raTaxType"
															class="form-control" id="taxType${e}"
															onfocus="this.oldIndex=this.selectedIndex"
															onchange="this.selectedIndex=this.oldIndex">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<form:option value="A">
																<spring:message
																	code="work.measurement.sheet.details.addition" />
															</form:option>
															<form:option value="D">
																<spring:message
																	code="work.measurement.sheet.details.deduction" />
															</form:option>
														</form:select></td>

													<td><form:select
															path="billMasDto.raBillTaxDetails[${e}].raTaxFact"
															cssClass="form-control mandColorClass" id="taxFact${e}"
															data-rule-required="true"
															onfocus="this.oldIndex=this.selectedIndex"
															onchange="this.selectedIndex=this.oldIndex">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.valueTypeList}" var="payType">
																<form:option value="${payType.lookUpId }"
																	code="${payType.lookUpCode}">${payType.descLangFirst}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:input
															path="billMasDto.raBillTaxDetails[${e}].raTaxPercent"
															class="form-control text-right" id="taxPercent${e}"
															onblur="calculateAmt(this);"
															onkeypress="return hasAmount(event, this, 5, 2)"
															onchange="getAmountFormatInDynamic((this),'taxPercent')"
															readonly="true" /></td>
													<td><form:input
															path="billMasDto.raBillTaxDetails[${e}].raTaxValue"
															class="form-control mandColorClass text-right"
															id="taxValue${e}" onblur="calculateTotalAmt();"
															onkeypress="return hasAmount(event, this, 5, 2)"
															onchange="getAmountFormatInDynamic((this),'taxValue')" /></td>

													<td align="center"><c:if
															test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
															<a href="javascript:void(0);" data-toggle="tooltip" data-placement="top"
																onclick='addTaxDetails();'
																class="btn btn-blue-2 btn-sm addButton" title="<spring:message code="works.management.add" text="Add"></spring:message>"> <i
																class="fa fa-plus-circle"></i></a></c:if> 
														<a href="javascript:void(0);" data-toggle="tooltip"
														data-placement="top"
														class="btn btn-danger btn-sm delButton"
														onclick="deleteTaxEntry($(this),'removeTaxDetIds');" title="<spring:message code="works.management.delete" text="Delete"></spring:message>"><i
															class="fa fa-minus"></i></a></td>
												</tr>

												<c:set var="e" value="${e + 1}" scope="page" />
											</c:otherwise>
										</c:choose>
									</table>
								</div>
							</div>

							<div class="text-right text-blue-2 margin-top-15">
								<p>
									<strong><spring:message code="wms.TotalRABillAmount"
											text="Total RA Bill Amount" /> <form:input
											path="billMasDto.raBillAmt" class="form-control text-right"
											id="raBillAmt" readonly="true" /></strong>
								</p>
							</div>
						</div>
					</div>
					<div class="text-center clear padding-10">
						<c:if test="${command.saveMode ne 'V' }">
							<button type="button" id="save"
								class="btn btn-success btn-submit" onclick="saveData(this,'D')">
								<i class="fa fa-sign-out padding-right-5"></i>
								<spring:message code="wms.SaveAsDraft" text="Save As Draft" />
							</button>

							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel"
								onclick="window.location.href='raBillGeneration.html'"
								id="backButton">
								<i class="fa fa-chevron-circle-left padding-right-5"></i>
								<spring:message code="works.management.back" text="" />
							</button>

							<button type="button" id="save" class="btn btn-primary"
								onclick="saveData(this,'S')">
								<i class="fa fa-paper-plane padding-right-5" aria-hidden="true"></i>
								<spring:message code="wms.SaveSendForApproval"
									text="Save & Send For Approval" />
							</button>

						</c:if>

						<c:choose>
							<c:when
								test="${command.saveMode eq 'V' && command.approvalMode eq 'AP'}">
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" onclick="backToApprvForm();"
									id="backButton">
									<i class="fa fa-chevron-circle-left padding-right-5"></i>
									<spring:message code="works.management.back" text="" />
								</button>
							</c:when>
							<c:otherwise>
								<c:if test="${command.saveMode eq 'V' }">
									<button type="button" class="button-input btn btn-danger"
										name="button-Cancel" value="Cancel"
										onclick="window.location.href='raBillGeneration.html'"
										id="backButton">
										<i class="fa fa-chevron-circle-left padding-right-5"></i>
										<spring:message code="works.management.back" text="" />
									</button>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
