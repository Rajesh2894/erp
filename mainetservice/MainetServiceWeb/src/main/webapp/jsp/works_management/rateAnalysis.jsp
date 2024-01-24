<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript"
	src="js/works_management/workEstimateMeasureDetails.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Rate Analysis" />
			</h2>
			<div class="additional-btn">
				  <apptags:helpDoc url="WorkEstimate.html"></apptags:helpDoc>

			</div>
		</div>

		<div class="widget-content padding">
			<form:form action="WorkEstimate.html" class="form-horizontal"
				name="rateAnalysis" id="rateAnalysis">
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<!-- Start Each Section -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="" text="Rate Analysis" /> </a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<form:hidden path="materialMapKey" id="materialMapKey" />
								<div class="form-group">
									<label class="label-control col-sm-2"><spring:message
											code="material.master.ssorname" text="Select SOR Name" /></label>
									<div class="col-sm-4">
										<form:select path="sorCommonId" id="sorName"
											onchange="getDateBySorName();" disabled="true"
											class="form-control">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.activeScheduleRateList}"
												var="activeSor">
												<form:option value="${activeSor.sorId }">${activeSor.sorName }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<apptags:date fieldclass="" datePath="fromDate"
										labelCode="material.master.startdate"
										cssClass="mandColorClass " readonly="true"></apptags:date>
									<apptags:date fieldclass="" datePath="toDate"
										labelCode="material.master.enddate" cssClass="mandColorClass "
										readonly="true"></apptags:date>
								</div>

							</div>

							<c:set var="d" value="0" scope="page" />
							<div class="rateAnalysisdiv" id="rateAnalysisdiv">
								<form:input path="removeMaterialById" type="hidden" />
								<table class="table table-bordered table-striped"
									id="rateAnalysisTab">
									<thead>
										<tr>
											<th width="5%"><spring:message
													code="ser.no" text="Sr. No." /><span
												class="mand">*</span></th>
											<th width="25%"><spring:message code=""
													text="Matrerial Name" /></th>
											<th width="10%"><spring:message
													code="sor.baserate" text="Rate" /></th>
											<th width="15%"><spring:message
													code="work.management.unit" text="Unit" /></th>
											<th width="10%"><spring:message
													code="work.estimate.quantity" text="Quantity" /></th>
											<th width="10%"><spring:message
													code="work.estimate.total" text="Total" /></th>
											<th width="10%"><spring:message code=""
													text="Rate Analysis" /></th>
											<c:if test="${command.saveMode ne 'V'}">
												<th scope="col" width="5%"><a onclick='return false;'
													class="btn btn-blue-2 btn-sm addButton"> <i
														class="fa fa-plus-circle"></i></a></th>
											</c:if>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<c:choose>
												<c:when test="${command.saveMode ne 'V'}">
													<th colspan="5" class="text-right"><spring:message
															code="work.estimate.Total" /></th>
													<th colspan="1"><form:input path="" id="rateTotal"
															cssClass="form-control text-right" readonly="true" /></th>
													<th colspan="2" class="text-right"></th>
												</c:when>
												<c:otherwise>
													<th colspan="5" class="text-right"><spring:message
															code="work.estimate.Total" /></th>
													<th colspan="1"><form:input path="" id="rateTotal"
															cssClass="form-control text-right" readonly="true" /></th>
													<th colspan="1" class="text-right"></th>
												</c:otherwise>
											</c:choose>

										</tr>
									</tfoot>
									<c:choose>
										<c:when test="${command.saveMode eq 'V'}">
											<c:forEach var="sorData"
												items="${command.rateAnalysisMaterialList}">
												<tbody class="appendableClass">
													<tr>
														<form:hidden
															path="rateAnalysisMaterialList[${d}].workEstemateId"
															id="workEstemateId${d}" />
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass "
																id="sequence${d}" value="${d+1}" disabled="true" /></td>
														<td><form:select
																path="rateAnalysisMaterialList[${d}].gRateMastId"
																class="form-control" id="gRateMastId${d}"
																onchange="getRateUnitBySorId(${d});" disabled="true">
																<form:option value="">
																	<spring:message code="holidaymaster.select" />
																</form:option>
																<c:forEach items="${command.materialMasterList}"
																	var="lookUp">
																	<form:option value="${lookUp.maId}"
																		code="${lookUp.maRate},${lookUp.unitName}">${lookUp.maDescription}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:input id="sorBasicRate${d}"
																path="rateAnalysisMaterialList[${d}].sorBasicRate"
																class=" form-control text-right" readonly="true" /></td>
														<td><form:input id="sorIteamUnit${d}"
																path="rateAnalysisMaterialList[${d}].sorIteamUnit"
																class=" form-control" readonly="true" /></td>
														<td><form:input id="workEstimQuantity${d}"
																path="rateAnalysisMaterialList[${d}].workEstimQuantity"
																onkeyup="calculateRateAnalysis();"
																onkeypress="return hasAmount(event, this, 3, 2)"
																onchange="getAmountFormatInDynamic((this),'workEstimQuantity')"
																class=" form-control text-right" readonly="true" /></td>
														<td><form:input id="workEstimAmount${d}"
																path="rateAnalysisMaterialList[${d}].workEstimAmount"
																class=" form-control text-right allamount"
																readonly="true"
																onkeypress="return hasAmount(event, this, 13, 2)"
																onchange="getAmountFormatInDynamic((this),'workEstimAmount')" /></td>
														<td class="text-center"><a href='#'
															onclick='showOtherRate(${d},this); false;'
															class='btn btn-primary btn-sm '><i
																class="fa fa-money"></i></a></td>
													</tr>
												</tbody>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</c:when>
										<c:when
											test="${fn:length(command.rateAnalysisMaterialList) > 0}">
											<c:forEach var="sorData"
												items="${command.rateAnalysisMaterialList}">
												<tbody class="appendableClass">
													<tr>
														<form:hidden
															path="rateAnalysisMaterialList[${d}].workEstemateId"
															id="workEstemateId${d}" />
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass "
																id="sequence${d}" value="${d+1}" disabled="true" /></td>
														<td><form:select
																path="rateAnalysisMaterialList[${d}].gRateMastId"
																class="form-control" id="gRateMastId${d}"
																onchange="getRateUnitBySorId(${d});">
																<form:option value="">
																	<spring:message code="holidaymaster.select" />
																</form:option>
																<c:forEach items="${command.materialMasterList}"
																	var="lookUp">
																	<form:option value="${lookUp.maId}"
																		code="${lookUp.maRate},${lookUp.unitName}">${lookUp.maDescription}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:input id="sorBasicRate${d}"
																path="rateAnalysisMaterialList[${d}].sorBasicRate"
																class=" form-control text-right" readonly="true" /></td>
														<td><form:input id="sorIteamUnit${d}"
																path="rateAnalysisMaterialList[${d}].sorIteamUnit"
																class=" form-control" readonly="true" /></td>
														<td><form:input id="workEstimQuantity${d}"
																path="rateAnalysisMaterialList[${d}].workEstimQuantity"
																onkeyup="calculateRateAnalysis();"
																onkeypress="return hasAmount(event, this, 3, 2)"
																onchange="getAmountFormatInDynamic((this),'workEstimQuantity')"
																class=" form-control text-right" /></td>
														<td><form:input id="workEstimAmount${d}"
																path="rateAnalysisMaterialList[${d}].workEstimAmount"
																class=" form-control text-right allamount"
																readonly="true"
																onkeypress="return hasAmount(event, this, 13, 2)"
																onchange="getAmountFormatInDynamic((this),'workEstimAmount')" /></td>
														<td class="text-center"><a href='#'
															onclick='showOtherRate(${d},this); false;'
															class='btn btn-primary btn-sm '><i
																class="fa fa-money"></i></a></td>
														<td class="text-center"><a href='#'
															onclick='return false;'
															class='btn btn-danger btn-sm rateAnalysisDelButton'><i
																class="fa fa-trash"></i></a></td>
													</tr>
												</tbody>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tbody class="appendableClass">
												<tr>
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass " id="sequence${d}"
															value="${d+1}" disabled="true" /></td>
													<td><form:select
															path="rateAnalysisMaterialList[${d}].gRateMastId"
															class="form-control" id="gRateMastId${d}"
															onchange="getRateUnitBySorId(${d});">
															<form:option value="">
																<spring:message code="holidaymaster.select" />
															</form:option>
															<c:forEach items="${command.materialMasterList}"
																var="lookUp">
																<form:option value="${lookUp.maId}"
																	code="${lookUp.maRate},${lookUp.unitName}">${lookUp.maDescription}</form:option>
															</c:forEach>
														</form:select></td>

													<td><form:input id="sorBasicRate${d}"
															path="rateAnalysisMaterialList[${d}].sorBasicRate"
															class=" form-control text-right" readonly="true" /></td>
													<td><form:input id="sorIteamUnit${d}"
															path="rateAnalysisMaterialList[${d}].sorIteamUnit"
															class=" form-control" readonly="true" /></td>
													<td><form:input id="workEstimQuantity${d}"
															path="rateAnalysisMaterialList[${d}].workEstimQuantity"
															onkeyup="calculateRateAnalysis();"
															onkeypress="return hasAmount(event, this, 3, 2)"
															onchange="getAmountFormatInDynamic((this),'workEstimQuantity')"
															class=" form-control text-right" /></td>
													<td><form:input id="workEstimAmount${d}"
															path="rateAnalysisMaterialList[${d}].workEstimAmount"
															class=" form-control text-right allamount"
															readonly="true"
															onkeypress="return hasAmount(event, this, 13, 2)"
															onchange="getAmountFormatInDynamic((this),'workEstimAmount')" /></td>
													<td class="text-center"><a href='#'
														onclick='showOtherRate(${d},this); false;'
														class='btn btn-primary btn-sm '><i class="fa fa-money"></i></a>
													</td>
													<td class="text-center"><a href='#'
														onclick='return false;'
														class='btn btn-danger btn-sm rateAnalysisDelButton'><i
															class="fa fa-trash"></i></a></td>
												</tr>
											</tbody>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:otherwise>
									</c:choose>

								</table>
							</div>

						</div>
					</div>
				</div>
				<!-- End Each Section -->

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button class="btn btn-success " onclick="saveRateAnalysis(this);"
							type="button">
							<i class="button-input"></i>
							<spring:message code="" text="Submit" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="measurementSheet();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
				<!-- End button -->
			</form:form>
			<!-- End Each Section -->
		</div>
		<!-- End Form -->
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End Widget  here -->
