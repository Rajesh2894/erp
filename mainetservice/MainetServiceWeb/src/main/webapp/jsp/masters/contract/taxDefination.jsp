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
<script src="js/masters/contract/taxDefination.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
#raTaxDetailsTab{
  width: 100%;
  white-space: nowrap;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.TaxDeductionTitle" text="Tax Deduction" />
				<apptags:helpDoc url="TaxDefination.html"></apptags:helpDoc>
			</h2>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="TaxDefination.html" class="form-horizontal"
				name="taxDefination" id="taxDefination">
				<!-- Start Validation include tag -->
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<form:input type="hidden" path="removeTaxesId" id="removeTaxesId" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a4" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a4"> <spring:message
										code="wms.TaxDeductionTitle" text="Tax Deduction" /></a>
							</h4>
						</div>

						<div id="a4" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="e" value="0" scope="page" />
								<!-- <div class="table-responsive clear"> -->
								<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="raTaxDetailsTab">
									<thead>
										<tr>
											<th width="2%"><spring:message code="ser.no"
													text="Sr. No." /><span class="mand">*</span></th>
											<th width="10%"><spring:message code="wms.TaxNames"
													text="Tax Names" /><span class="mand">*</span></th>
											<th width="12%"><spring:message code="wms.VendorSubType"
													text="Vendor SubType" /></th>
											<th width="5%"><spring:message code="wms.Threshold"
													text="Threshold" /></th>
											<th width="14%"><spring:message
													code="work.management.unit" text="Unit" /></th>
											<th width="10%"><spring:message code="wms.PanCard"
													text="PanCard" /></th>
											<th width="10%"><spring:message code="" text="Fixed" /><span
												class="mand">*</span></th>
											<th width="10%"><spring:message code="wms.AddDeduction"
													text="Add/Deduct" /><span class="mand">*</span></th>
											<th width="5%"><spring:message
													code="wms.AmountOrPercentage" text="Amount/Percent" /><span
												class="mand">*</span></th>
											<th width="5%"><spring:message
													code="wms.PercentageValue" text="Percentage Value" /><span
												class="mand">*</span></th>
											<th width="10%"><spring:message
													code="work.estimate.Total" text="Amount" /><span
												class="mand">*</span></th>
											<c:if test="${command.saveMode ne 'V'}">
												<th scope="col" width="8%"></th>
											</c:if>
										</tr>
									</thead>
									<c:choose>
										<c:when test="${fn:length(command.taxDefinationList) > 0}">
											<c:forEach var="taxData" items="${command.taxDefinationList}"
												varStatus="status">
												<tbody>
												<tr class="appendableClass">

													<td align="center"><form:input path=""
															cssClass="form-control" id="sequence${e}" value="${e+1}"
															readonly="true" /> <form:input type="hidden"
															path="taxDefinationList[${e}].taxDefId" id="" /></td>
													<td align="center"><form:select
															path="taxDefinationList[${e}].taxId" disabled="${command.saveMode eq 'V' ? true : false }"
															cssClass="form-control mandColorClass chosen-select-no-results"
															id="taxId${e}" onchange="enableVendorSubtype(this);">
															<form:option value="">
																<spring:message code='master.selectDropDwn' />
															</form:option>
															<c:forEach items="${command.taxList}" var="lookUp">
																<c:if
																	test="${lookUp.lookUpId == command.parenetTaxId  }">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.otherField}">${lookUp.descLangFirst}</form:option>
																</c:if>
															</c:forEach>
														</form:select></td>
													<td align="center"><form:select
															path="taxDefinationList[${e}].cpdVendorSubType" disabled="${command.saveMode eq 'V' ? true : false }"
															cssClass="form-control mandColorClass chosen-select-no-results"
															id="cpdVendorSubType${e}">
															<form:option value="">
																<spring:message code='master.selectDropDwn' />
															</form:option>
															<c:forEach items="${command.getLevelData('VST')}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>

															</c:forEach>
														</form:select></td>
													<td><form:input
															path="taxDefinationList[${e}].taxThreshold"
															class="form-control mandColorClass text-right"
															id="threshold${e}"
															onkeypress="return hasAmount(event, this, 5, 2)"
															onchange="getAmountFormatInDynamic((this),'threshold')" /></td>
													<td align="center"><form:select
															path="taxDefinationList[${e}].taxUnit" disabled="${command.saveMode eq 'V' ? true : false }"
															cssClass="form-control mandColorClass chosen-select-no-results"
															id="unit${e}">
															<form:option value="">
																<spring:message code="holidaymaster.select" />
															</form:option>
															<c:forEach items="${command.getLevelData('WUT')}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>

															</c:forEach>
														</form:select></td>
													<td><form:select
															path="taxDefinationList[${e}].taxPanApp" disabled="${command.saveMode eq 'V' ? true : false }"
															class="form-control chosen-select-no-results"
															id="panCard${e}">
															<form:option value="">
																<spring:message code='master.selectDropDwn' />
															</form:option>
															<form:option value="Y">
																<spring:message
																	code="work.vigilance.inspection.required.yes"
																	text="Yes" />
															</form:option>
															<form:option value="N">
																<spring:message
																	code="work.vigilance.inspection.required.no" text="No" />
															</form:option>
														</form:select></td>
													<td><form:select
															path="taxDefinationList[${e}].taxFixed" disabled="${command.saveMode eq 'V' ? true : false }"
															class="form-control chosen-select-no-results"
															id="taxFixed${e}">
															<form:option value="">
																<spring:message code='master.selectDropDwn' />
															</form:option>
															<form:option selected="selected" value="Y">
																<spring:message
																	code="work.vigilance.inspection.required.yes"
																	text="Yes" />
															</form:option>
															<form:option value="N">
																<spring:message
																	code="work.vigilance.inspection.required.no" text="No" />
															</form:option>
														</form:select></td>
													<td><form:select
															path="taxDefinationList[${e}].raTaxType" disabled="${command.saveMode eq 'V' ? true : false }"
															class="form-control chosen-select-no-results"
															id="taxType${e}">
															<form:option value="">
																<spring:message code='master.selectDropDwn' />
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
															path="taxDefinationList[${e}].raTaxFact" disabled="${command.saveMode eq 'V' ? true : false }"
															cssClass="form-control mandColorClass chosen-select-no-results"
															id="taxFact${e}" data-rule-required="true"
															onchange="getTaxPerCent(this);">
															<form:option value="">
																<spring:message code='master.selectDropDwn' />
															</form:option>
															<c:forEach items="${command.valueTypeList}" var="payType">
																<form:option value="${payType.lookUpId }"
																	code="${payType.lookUpCode}">${payType.descLangFirst}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:input
															path="taxDefinationList[${e}].raTaxPercent"
															class="form-control text-right"
															readonly="${taxData.raTaxPercent eq null ? true : false}"
															id="taxPercent${e}" onblur="calculateAmt(this);"
															onkeypress="return hasAmount(event, this, 5, 2)"
															onchange="getAmountFormatInDynamic((this),'taxPercent')" /></td>
													<td><form:input
															path="taxDefinationList[${e}].raTaxValue"
															class="form-control mandColorClass text-right"
															readonly="${taxData.raTaxValue eq null ? true : false}"
															id="taxValue${e}"
															onkeypress="return hasAmount(event, this, 5, 2)"
															onchange="getAmountFormatInDynamic((this),'taxValue')" /></td>
													
													<c:if test="${command.saveMode ne 'V'}">
													<td align="center"><a href="javascript:void(0);"
														onclick='addTaxDetails();'
														 class="btn btn-blue-2 btn-sm addButton" ><i
															class="fa fa-plus-circle"></i></a> <a
														href="#" data-toggle="tooltip" data-placement="top"
														class="btn btn-danger btn-sm delButton"
														onclick="deleteTaxEntry($(this),'removeTaxesId');"><i
															class="fa fa-minus"></i></a>
													</td>
													</c:if>
													
												</tr>
												</tbody>

												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</c:when>
										<c:otherwise>

											<tbody>
											<tr class="appendableClass">

												<td align="center"><form:input path=""
														cssClass="form-control" id="sequence${e}" value="${e+1}"
														readonly="true" /> <form:input type="hidden"
														path="taxDefinationList[${e}].taxDefId" /></td>
												<td align="center"><form:select
														path="taxDefinationList[${e}].taxId"
														cssClass="form-control mandColorClass chosen-select-no-results"
														id="taxId${e}" onchange="enableVendorSubtype(this);">
														<form:option value="">
															<spring:message code="holidaymaster.select" />
														</form:option>
														<c:forEach items="${command.taxList}" var="lookUp">
															<c:if
																test="${!fn:contains(lookUp.descLangFirst, 'Withheld')}">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.otherField}">${lookUp.descLangFirst}</form:option>
															</c:if>
														</c:forEach>
													</form:select></td>
												<td align="center"><form:select
														path="taxDefinationList[${e}].cpdVendorSubType"
														cssClass="form-control mandColorClass chosen-select-no-results"
														id="cpdVendorSubType${e}">
														<form:option value="">
															<spring:message code="holidaymaster.select" />
														</form:option>
														<c:forEach items="${command.getLevelData('VST')}"
															var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>

														</c:forEach>
													</form:select></td>
												<td><form:input
														path="taxDefinationList[${e}].taxThreshold"
														class="form-control mandColorClass text-right"
														id="threshold${e}"
														onkeypress="return hasAmount(event, this, 5, 2)"
														onchange="getAmountFormatInDynamic((this),'threshold')" /></td>
												<td align="center"><form:select
														path="taxDefinationList[${e}].taxUnit"
														cssClass="form-control mandColorClass chosen-select-no-results"
														id="unit${e}">
														<form:option value="">
															<spring:message code="holidaymaster.select" />
														</form:option>
														<c:forEach items="${command.getLevelData('WUT')}"
															var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>

														</c:forEach>
													</form:select></td>
												<td><form:select
														path="taxDefinationList[${e}].taxPanApp"
														class="form-control chosen-select-no-results"
														id="panCard${e}">
														<form:option value="">
															<spring:message code="holidaymaster.select" />
														</form:option>
														<form:option value="Y">
															<spring:message
																code="work.vigilance.inspection.required.yes" text="Yes" />
														</form:option>
														<form:option value="N">
															<spring:message
																code="work.vigilance.inspection.required.no" text="No" />
														</form:option>
													</form:select></td>
												<td><form:select
														path="taxDefinationList[${e}].taxFixed"
														class="form-control chosen-select-no-results"
														id="taxFixed${e}">
														<form:option value="">
															<spring:message code='master.selectDropDwn' />
														</form:option>
														<form:option selected="selected" value="Y">
															<spring:message
																code="work.vigilance.inspection.required.yes" text="Yes" />
														</form:option>
														<form:option value="N">
															<spring:message
																code="work.vigilance.inspection.required.no" text="No" />
														</form:option>
													</form:select></td>
												<td><form:select
														path="taxDefinationList[${e}].raTaxType"
														class="form-control chosen-select-no-results"
														id="taxType${e}">
														<form:option value="">
															<spring:message code="holidaymaster.select" />
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
														path="taxDefinationList[${e}].raTaxFact"
														cssClass="form-control mandColorClass chosen-select-no-results"
														id="taxFact${e}" data-rule-required="true"
														onchange="getTaxPerCent(this);">
														<form:option value="">
															<spring:message code='master.selectDropDwn' />
														</form:option>
														<c:forEach items="${command.valueTypeList}" var="payType">
															<form:option value="${payType.lookUpId }"
																code="${payType.lookUpCode}">${payType.descLangFirst}</form:option>
														</c:forEach>
													</form:select></td>
												<td><form:input
														path="taxDefinationList[${e}].raTaxPercent"
														class="form-control text-right" disabled=""
														id="taxPercent${e}" onblur="calculateAmt(this);"
														onkeypress="return hasAmount(event, this, 5, 2)"
														onchange="getAmountFormatInDynamic((this),'taxPercent')" /></td>
												<td><form:input
														path="taxDefinationList[${e}].raTaxValue"
														class="form-control mandColorClass text-right"
														id="taxValue${e}"
														onkeypress="return hasAmount(event, this, 5, 2)"
														onchange="getAmountFormatInDynamic((this),'taxValue')" /></td>
												
												<c:if test="${command.saveMode ne 'V'}">
													<td align="center"><a href="javascript:void(0);"
														 onclick='addTaxDetails();'
														 class="btn btn-blue-2 btn-sm addButton" ><i
														 class="fa fa-plus-circle"></i></a> <a
														 href="#" data-toggle="tooltip" data-placement="top"
														 class="btn btn-danger btn-sm delButton"
														 onclick="deleteTaxEntry($(this),'removeTaxesId');"><i
															class="fa fa-minus"></i></a>
													</td>
												</c:if>
												
											</tr>
											</tbody>
											<c:set var="e" value="${e + 1}" scope="page" />
										</c:otherwise>
									</c:choose>
								</table>
								</div>
								<!-- </div> -->
							</div>
						</div>
					</div>
					<div class="text-center clear padding-10">
						<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
							<button type="button" id="save"
								class="btn btn-success btn-submit" onclick="saveData(this)">
								<i class="fa fa-sign-out padding-right-5"></i>
								<spring:message code="works.management.save" text="Save" />
							</button>
						</c:if>
						<c:if test="${command.saveMode eq 'C'}">
							<button type="button" class="btn btn-warning  reset"
								name="button-Cancel" value="Cancel"
								onclick="window.location.href='TaxDefination.html'"
								id="backButton">
								<spring:message code="reset.msg" text="Reset" />
							</button>
						</c:if>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="window.location.href='TaxDefination.html'"
							id="backButton">
							<spring:message code="back.msg" text="" />
						</button>
					</div>

				</div>
			</form:form>
		</div>
	</div>
</div>