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
	src="js/works_management/measurementBook.js"></script>
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
				<spring:message code="wms.MeasurementBookRateAnalysis" text="Measurement-Book Rate Analysis" />
			</h2>
			<div class="additional-btn">
			  <apptags:helpDoc url="MeasurementBook.html"></apptags:helpDoc>
			</div>
		</div>

		<div class="widget-content padding">
			<form:form action="MeasurementBook.html" class=""
				name="mbRateAnalysis" id="mbRateAnalysis">
					
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<c:set var="d" value="0" scope="page" />
				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="rateAnalysis">
						<thead>
							<tr>
								<th rowspan="2"><spring:message
										code="material.master.ratetype" text="Rate Type" /><span
									class="mand">*</span></th>
								<th rowspan="2"><spring:message
										code="material.master.itemcode" text="Item Code" /><span
									class="mand">*</span></th>
								<th rowspan="2"><spring:message
										code="material.master.matreialname" text="Rate Description" /><span
									class="mand">*</span></th>
								<th rowspan="2"><spring:message code="sor.baserate"
										text="Rate" /><span class="mand">*</span></th>
								<th rowspan="2"><spring:message code="work.management.unit"
										text="Unit" /><span class="mand">*</span></th>
								<th colspan="2"><spring:message
										code="work.estimate.quantity" text="Quantity" /><span
									class="mand">*</span></th>
								<th colspan="2"><spring:message
										code="work.estimate.total.amount" text="Total Amount" /><span
									class="mand">*</span></th>
							</tr>
							<tr>
								<th><spring:message code="mb.estimated" text="Estimated" /></th>
								<th><spring:message code="mb.actual" text="Actual" /></th>
								<th><spring:message code="mb.estimated" text="Estimated" /></th>
								<th><spring:message code="mb.actual" text="Actual" /></th>
							</tr>
						</thead>
						<%-- <tfoot>
							<tr>
								<th colspan="8" class="text-right"><spring:message
										code="work.estimate.Total" /></th>
								<th colspan=""><form:input path="" id="allTotal"
										cssClass="form-control text-right" readonly="true" /></th>

								<!-- <th colspan="1" class="text-right"></th> -->

							</tr>
						</tfoot> --%>
						<tbody>

							<c:forEach var="rateList" items="${command.addAllRatetypeEntity}"
								varStatus="status">

								<tr class="appendableClass">
									<form:input type="hidden"
										path="mbDetRateDtoList[${d}].workEstemateId"
										value="${rateList.workEstemateId}" />
									<form:input type="hidden"
										path="mbDetRateDtoList[${d}].workEstimateType"
										value="${rateList.estimateType}" />
									<form:input type="hidden" path="mbDetRateDtoList[${d}].mbId"
										value="${rateList.mbId}" />
									<form:input type="hidden" path="mbDetRateDtoList[${d}].rateId"
										value="${rateList.gRateMastId}" />
									<form:input type="hidden"
										path="mbDetRateDtoList[${d}].mbDetPId"
										value="${rateList.mbDetId}" />
									<form:input type="hidden"
										path="mbDetRateDtoList[${d}].sorRate"
										value="${command.estimateMasterDto.sorBasicRate}" />	
									<form:input type="hidden"
											path="mbDetRateDtoList[${d}].workFlag"
											value="${rateList.workEstimFlag}" />	

									<td align="center"><form:select
											path="addAllRatetypeEntity[${d}].workEstimFlag"
											class="form-control" id="rateTypeId${d}" disabled="true">
											<form:option value="">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.ratePrifixMap}" var="lookUp">
												<form:option value="${lookUp.key}" code="${lookUp.key}">${lookUp.value}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:input id="" path="rateList[${d}].maItemNo"
											class=" form-control text-right" readonly="true" /></td>
									<td><form:select
											path="addAllRatetypeEntity[${d}].gRateMastId"
											class="form-control" id="gRateMastId${d}"
											onkeypress="return hasAmount(event, this, 3, 2)"
											onchange="getRateUnitBySorId(${d});" disabled="true">
											<form:option value="">
												<spring:message code="holidaymaster.select" />
											</form:option>

											<c:forEach items="${command.rateList}" var="rateList">
												<form:option value="${rateList.maId}"
													code="${rateList.maItemUnit},${rateList.maRate}"
													label="${rateList.maDescription}"></form:option>
											</c:forEach>

										</form:select></td>



									<td><form:input id="sorBasicRate${d}" path=""
											class=" form-control text-right" readonly="true" /></td>

									<td><form:select path="" class="form-control"
											id="sorIteamUnit${d}" disabled="true">
											<form:option value="">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WUT')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:input
											path="addAllRatetypeEntity[${d}].workEstimQuantity"
											class="form-control text-right" id="workEstQuantity${d}"
											readonly="true" /></td>
									<td><form:input
											path="mbDetRateDtoList[${d}].workActualQty"
											class="form-control text-right" id="workActQuantity${d}"
											placeholder="00000.00" onkeyup="calculateRateAnalysis();"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'workActQuantity')"  />
									</td>

									<td><form:input
											path="addAllRatetypeEntity[${d}].workEstimAmount"
											class="form-control text-right" readonly="true"
											id="workEstAmount${d}" /></td>
									<td><form:input
											path="mbDetRateDtoList[${d}].workActualAmt"
											class="form-control text-right allamount" id="workActAmt${d}"
											placeholder="00000.00" onkeyup="calculateRateAnalysis();"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'workActAmt')" /></td>
								</tr>

								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>

						</tbody>
					</table>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
						<button class="btn btn-success " onclick="saveRateAnalysis(this);"
							type="button">
							<i class="button-input"></i>
							<spring:message code="mileStone.submit" text="Submit" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="measurementSheet();" id="backButton">
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



