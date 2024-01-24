<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/works_management/measurementBook.js"></script>
<style>
textarea::placeholder {
	color: black !important;
}
</style>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.measurement.sheet.details"
					text="Measurement Sheet Details" />
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
				name="measurementDetail" id="measurementDetail">
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<form:hidden id="measurementId" path="measurementId" />
				<form:hidden id="uploadMode" path="uploadMode" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<h4 class="">
					<spring:message code="work.measurement.sheet.details.itemdetails"
						text="SOR Item Details" />
				</h4>
				<table class="table table-bordered table-striped">
					<thead>
						<tr>
							<th width="5%" align="center"><spring:message
									code="work.measurement.sheet.details.itemcode" text="" /></th>
							<th width="5%" align="center"><spring:message
									code="work.management.unit" text="" /></th>
							<th width="90%" align="center"><spring:message
									code="work.management.description" text="" /></th>
						</tr>
						<tr>
							<td align="center">${command.estimateMasterDto.sorDIteamNo}</td>
							<td align="center">${command.estimateMasterDto.sorIteamUnitDesc}</td>
							<td>${command.estimateMasterDto.sorDDescription}</td>
						</tr>
				</table>

				<h4 class="margin-bottom-10">
					<spring:message code="work.measurement.sheet.details"
						text="Measurement Sheet Details" />
				</h4>

				<c:set var="d" value="0" scope="page" />
				<div class="materialMasterAdd" id="materialMasterAdd">
					<table class="table table-bordered table-striped"
						id="measurementDetails">
						<c:choose>
							<c:when test="${cpdModeCatSor eq 'N'}">
								<thead>
									<tr>
										<th rowspan="2" width="10%"><spring:message
												code="work.measurement.sheet.details.particulars"
												text="Particulars" /><span class="mand">*</span></th>
										<th width="8%" colspan="2" align="right"><spring:message
												code="work.measurement.sheet.details.nos" text="Nos." /><span
											class="mand">*</span></th>
										<th width="10%" rowspan="2"><spring:message
												code="work.measurement.sheet.details.Type" text="Type" /><span
											class="mand">*</span></th>
										<th width="8%" colspan="2"><spring:message
												code="work.measurement.sheet.details.formula" text="Formula" /><span
											class="mand">*</span></th>
										<th width="1%" rowspan="2"><spring:message
												code="wms.AddDeduct" text="Add/Ded" /><span class="mand">*</span></th>
										<th width="10%" colspan="2"><spring:message
												code="work.measurement.sheet.details.Length" text="Length" /><span
											class="mand">*</span></th>
										<th width="10%" colspan="2"><spring:message
												code="work.measurement.sheet.details.width"
												text="Breadth/ Width" /><span class="mand">*</span></th>
										<th width="10%" colspan="2"><spring:message
												code="work.measurement.sheet.details.height"
												text="Depth/ Height" /><span class="mand">*</span></th>
										<th width="10%" colspan="2"><spring:message
												code="work.estimate.quantity" text="Quantity" /><span
											class="mand">*</span></th>
										<th width="12%" colspan="2"><spring:message
												code="work.estimate.report.total.quantity"
												text="Total Quantity" /><span class="mand">*</span></th>
										<th width="5%" rowspan="2"><spring:message
												code="wms.UploadImages" text="Upload Images" /></th>
									</tr>

									<tr>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<%-- <th><spring:message code="" text="Image" /></th> --%>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="16" style="text-align: right"><spring:message
												code="mb.Total" text="Total:" /></th>
										<th><form:input id="subTotal"
												class=" form-control text-right" path="" readonly="true"
												onkeypress="return hasAmount(event, this, 10, 2)" /></th>
										<th></th>
									</tr>
								</tfoot>

							</c:when>
							<c:otherwise>
								<thead>
									<tr>
										<th rowspan="2" width="20%"><spring:message
												code="work.measurement.sheet.details.particulars"
												text="Particulars" /><span class="mand">*</span></th>
										<th width="6%" rowspan="2" align="right"><spring:message
												code="work.measurement.sheet.details.nos" text="Nos." /><span
											class="mand">*</span></th>
										<th width="9%" rowspan="2"><spring:message
												code="work.measurement.sheet.details.Type" text="Type" /><span
											class="mand">*</span></th>
										<th width="8%" rowspan="2"><spring:message
												code="work.measurement.sheet.details.formula" text="Formula" /><span
											class="mand">*</span></th>
										<th width="5%" rowspan="2"><spring:message
												code="wms.AddDeduct" text="Add/Ded" /><span class="mand">*</span></th>
										<th width="7%" rowspan="2"><spring:message
												code="work.measurement.sheet.details.Length" text="Length" /><span
											class="mand">*</span></th>
										<th width="7%" rowspan="2"><spring:message
												code="work.measurement.sheet.details.width"
												text="Breadth/ Width" /><span class="mand">*</span></th>
										<th width="7%" rowspan="2"><spring:message
												code="work.measurement.sheet.details.height"
												text="Depth/ Height" /><span class="mand">*</span></th>
										<th width="12%" colspan="2"><spring:message
												code="work.estimate.quantity" text="Quantity" /><span
											class="mand">*</span></th>
										<th width="5%" rowspan="2"><spring:message
												code="work.estimate.report.total.quantity"
												text="Total Quantity" /><span class="mand">*</span></th>
										<th width="5%" rowspan="2"><spring:message
												code="wms.UploadImages" text="Upload Images" /></th>
									</tr>

									<tr>
										<%-- <th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th> --%>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<%-- <th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th> --%>

										<%-- <th><spring:message code="" text="Image" /></th> --%>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="10" style="text-align: right"><spring:message
												code="mb.Total" text="Total:" /></th>
										<th><form:input id="subTotal"
												class=" form-control text-right" path="" readonly="true"
												onkeypress="return hasAmount(event, this, 10, 2)" /></th>
										<th></th>
									</tr>
								</tfoot>

							</c:otherwise>
						</c:choose>
						<c:forEach var="measureDetList"
							items="${command.measureDetailsList}" varStatus="status">
							<tbody>
								<tr class="appendableClass">
									<form:input type="hidden" path="lbhDtosList[${d}].sorRate"
										value="${command.estimateMasterDto.sorBasicRate}" />
									<form:input type="hidden"
										path="lbhDtosList[${d}].estimateMeasureDetId"
										value="${measureDetList.meMentId}" />
									<form:input type="hidden" path="lbhDtosList[${d}].mbLbhId" />
									<form:input type="hidden"
										path="lbhDtosList[${d}].mbParticulare"
										value="${measureDetList.meMentParticulare}" />
									<form:input type="hidden" path="lbhDtosList[${d}].mbValueType"
										value="${measureDetList.meMentValType}" />
									<form:input type="hidden" path="lbhDtosList[${d}].mbType"
										value="${measureDetList.meMentType}" />

									<td><form:textarea id="" path=""
											placeholder="${measureDetList.meMentParticulare}"
											style="margin: 0px; height: 33px;"
											class="form-control text-left" readonly="true"
											title="${measureDetList.meMentParticulare}" /></td>
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:input id="meEstNumber${d}"
												path="measureDetailsList[${d}].meMentNumber"
												class=" form-control" maxlength="5" onkeyup=""
												readonly="true" title="${measureDetList.meMentNumber}" /></td>
									</c:if>
									<td><form:input path="lbhDtosList[${d}].mbNosAct"
											class="form-control hasNumber text-center"
											id="meActNumber${d}" placeholder="0"
											onblur="calculateTotal();" /></td>
									<td><form:select
											path="measureDetailsList[${d}].meMentValType"
											class="form-control" id="meMentValType${d}" onchange=""
											disabled="true">
											<form:option value="">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<form:option value="C">
												<spring:message
													code="work.measurement.sheet.details.calculated" />
											</form:option>
											<form:option value="D">
												<spring:message code="work.measurement.sheet.details.direct" />
											</form:option>
											<form:option value="F">
												<spring:message
													code="work.measurement.sheet.details.formula" />
											</form:option>
										</form:select></td>
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:textarea id="meEstFormula${d}"
												path="measureDetailsList[${d}].meMentFormula"
												class="form-control text-center"
												style="margin: 0px; height: 33px;" readonly="true" /></td>
									</c:if>


									<td><form:textarea id="meActFormula${d}"
											path="lbhDtosList[${d}].mbFormula" onblur="calculateTotal();"
											style="margin: 0px; height: 33px;"
											class="form-control text-right"
											readonly="${measureDetList.meMentValType eq 'F' ? false : true }" /></td>

									<td><form:select
											path="measureDetailsList[${d}].meMentType"
											class="form-control text-center" id="meMentType${d}"
											onchange="" disabled="true">
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
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:input id="meEstLength${d}"
												path="measureDetailsList[${d}].meMentLength"
												class="form-control text-right" readonly="true"
												title="${measureDetList.meMentLength}" /></td>
									</c:if>
									<td><form:input path="lbhDtosList[${d}].mbLength"
											class="form-control text-right" id="meActLength${d}"
											placeholder="0.0000" onblur="calculateTotal();"
											onkeypress="return hasAmount(event, this, 5, 4)"
											onchange="getAmountFormatInDynamic((this),'meActLength')"
											readonly="${measureDetList.meMentValType eq 'F' || measureDetList.meMentValType eq 'D' ? true : false }" /></td>
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:input id="meEstBreadth${d}"
												path="measureDetailsList[${d}].meMentBreadth"
												class="decimal form-control text-right"
												value="${measureDetList.meMentBreadth}" readonly="true"
												title="${measureDetList.meMentBreadth}" /></td>
									</c:if>
									<td><form:input path="lbhDtosList[${d}].mbBreadth"
											class="form-control text-right" id="meActBreadth${d}"
											placeholder="0.0000" onblur="calculateTotal();"
											onkeypress="return hasAmount(event, this, 5, 4)"
											onchange="getAmountFormatInDynamic((this),'meActBreadth')"
											readonly="${measureDetList.meMentValType eq 'F' || measureDetList.meMentValType eq 'D' ? true : false }" /></td>
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:input id="meEstHeight${d}"
												path="measureDetailsList[${d}].meMentHeight"
												class="decimal form-control text-right" readonly="true"
												title="${measureDetList.meMentHeight}" /></td>
									</c:if>
									<td><form:input path="lbhDtosList[${d}].mbHeight"
											class="form-control text-right" id="meActHeight${d}"
											placeholder="0.0000" onblur="calculateTotal();"
											onkeypress="return hasAmount(event, this, 8, 4)"
											onchange="getAmountFormatInDynamic((this),'meActHeight')"
											readonly="${measureDetList.meMentValType eq 'F' || measureDetList.meMentValType eq 'D' ? true : false }" /></td>

									<td><form:input id="meEstValue${d}"
											path="measureDetailsList[${d}].meValue"
											class=" form-control text-right" readonly="true"
											title="${measureDetList.meValue}" /></td>
									<td><form:input id="meActValue${d}"
											path="lbhDtosList[${d}].mbValue" onblur="calculateTotal();"
											placeholder="0.0000" class=" form-control text-right"
											onkeypress="return hasAmount(event, this, 10, 4)"
											onchange="getAmountFormatInDynamic((this),'meActValue')"
											readonly="${measureDetList.meMentValType eq 'D' ? false : true }" /></td>
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:input id="meEstToltal${d}"
												path="measureDetailsList[${d}].meMentToltal"
												class=" form-control text-right" readonly="true"
												title="${measureDetList.meMentToltal}" /></td>
									</c:if>
									<td><form:input id="meActToltal${d}"
											path="lbhDtosList[${d}].mbTotal"
											class=" form-control text-right" maxlength="12"
											onkeypress="return hasAmount(event, this, 10, 4)"
											placeholder="0.0000"
											onchange="getAmountFormatInDynamic((this),'meActToltal')"
											readonly="true" /></td>

									<td class="text-center">
										<button type="button" class="btn btn-darkblue-2 btn-sm Upload"
											title="Upload Images"
											onclick="uploadImages(${measureDetList.meMentId},'S',this);">
											<i class="fa fa-upload"></i>
										</button>

									</td>
								</tr>
							</tbody>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>

					</table>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
						<button class="btn btn-success save"
							onclick="saveMbMeasureDetails(this);" type="button">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="work.management.SaveContinue" text="" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="measurementSheet();" id="backButton">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>