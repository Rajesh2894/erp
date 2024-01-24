<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/measurementBook.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.DirectMeasurementSheetDetails"
					text="Direct Measurement Sheet Details" />
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
				name="directMeasurementDetail" id="directMeasurementDetail">
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<form:hidden id="measurementId" path="measurementId" />
				<form:hidden id="uploadMode" path="uploadMode" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<h4 class="margin-bottom-10">
					<spring:message code="wms.DirectMeasurementSheetDetails"
						text="Direct Measurement Sheet Details" />
				</h4>
				<c:set var="d" value="0" scope="page" />
				<div class="materialMasterAdd" id="materialMasterAdd">
					<table class="table table-bordered table-striped"
						id="directMeasurementDetails">
						<c:choose>
							<c:when test="${cpdModeCatSor eq 'N'}">
								<thead>
									<tr>
										<th width="5%" colspan="2" align="right"><spring:message
												code="work.measurement.sheet.details.nos" text="Nos." /><span
											class="mand">*</span></th>
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
												code="work.measurement.sheet.details.subtotal"
												text="Sub Total" /><span class="mand">*</span></th>
										<th width="10%" rowspan="2"><spring:message
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
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="9" style="text-align: right"><spring:message
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
										<th width="5%" rowspan="2" align="right"><spring:message
												code="work.measurement.sheet.details.nos" text="Nos." /><span
											class="mand">*</span></th>
										<th width="10%" rowspan="2"><spring:message
												code="work.measurement.sheet.details.Length" text="Length" /><span
											class="mand">*</span></th>
										<th width="10%" rowspan="2"><spring:message
												code="work.measurement.sheet.details.width"
												text="Breadth/ Width" /><span class="mand">*</span></th>
										<th width="10%" rowspan="2"><spring:message
												code="work.measurement.sheet.details.height"
												text="Depth/ Height" /><span class="mand">*</span></th>
										<th width="10%" colspan="2"><spring:message
												code="work.measurement.sheet.details.subtotal"
												text="Sub Total" /><span class="mand">*</span></th>
										<th width="10%" rowspan="2"><spring:message
												code="wms.UploadImages" text="Upload Images" /></th>
									</tr>
									<tr>
										<%-- <th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th> --%>
										<th><spring:message code="mb.estimated" text="Estimate" /></th>
										<th><spring:message code="mb.actual" text="Actual" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="5" style="text-align: right"><spring:message
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
							items="${command.directEstimateList}" varStatus="status">
							<tbody>
								<tr class="appendableClass">
									<form:input type="hidden" path="lbhDtosList[${d}].sorRate"
										value="${command.estimateMasterDto.sorBasicRate}" />
									<form:input type="hidden" path="lbhDtosList[${d}].mbLbhId" />
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:input id="meEstNumber${d}"
												path="directEstimateList[${d}].meActualNos"
												class=" form-control" maxlength="5" readonly="true" /></td>
									</c:if>
									<td><form:input path="lbhDtosList[${d}].mbNosAct"
											class="form-control hasNumber text-right"
											id="meActNumber${d}" placeholder="0"
											onkeyup="calculateDirectMeasurementTotal();" /></td>
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:input id="meEstLength${d}"
												path="directEstimateList[${d}].meMentLength"
												class="form-control text-right" readonly="true" /></td>
									</c:if>
									<td><form:input path="lbhDtosList[${d}].mbLength"
											class="form-control text-right" id="meActLength${d}"
											placeholder="0.00"
											onkeyup="calculateDirectMeasurementTotal();"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'meActLength')"
											readonly="${measureDetList.meMentLength eq null ? true :false}" /></td>
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:input id="meEstBreadth${d}"
												path="directEstimateList[${d}].meMentBreadth"
												class="decimal form-control text-right" readonly="true" /></td>
									</c:if>
									<td><form:input path="lbhDtosList[${d}].mbBreadth"
											class="form-control text-right" id="meActBreadth${d}"
											placeholder="0.00"
											onkeyup="calculateDirectMeasurementTotal();"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'meActBreadth')"
											readonly="${measureDetList.meMentBreadth eq null ? true :false}" /></td>
									<c:if test="${cpdModeCatSor eq 'N'}">
										<td><form:input id="meEstHeight${d}"
												path="directEstimateList[${d}].meMentHeight"
												class="decimal form-control text-right" readonly="true" /></td>
									</c:if>
									<td><form:input path="lbhDtosList[${d}].mbHeight"
											class="form-control text-right" id="meActHeight${d}"
											placeholder="0.00"
											onkeyup="calculateDirectMeasurementTotal();"
											onkeypress="return hasAmount(event, this, 8, 3)"
											onchange="getAmountFormatInDynamic((this),'meActHeight')"
											readonly="${measureDetList.meMentHeight eq null ? true :false}" /></td>


									<td><form:input id="meEstToltal${d}"
											path="directEstimateList[${d}].meActValue"
											class=" form-control text-right" readonly="true" /></td>
									<td><form:input id="meActToltal${d}"
											path="lbhDtosList[${d}].mbTotal"
											class=" form-control text-right" maxlength="12"
											onkeypress="return hasAmount(event, this, 10, 4)"
											placeholder="0.00" onkeyup="calculateDirectMeasurementTotal();"
											onchange="getAmountFormatInDynamic((this),'meMentToltal')"
										/></td>
									<td class="text-center">
										<button type="button" class="btn btn-darkblue-2 btn-sm Upload"
											title="Upload Images" id="backButton${d}"
											onclick="uploadImages(${measureDetList.mbDetId},'U',this);">
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
							onclick="saveMbDirectMeasureDetails(this);" type="button">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="" />
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