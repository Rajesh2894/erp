<!-- Start JSP Necessary Tags -->
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
	src="js/works_management/workEstimateSummary.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>Add Rate Type</h2>
			<div class="additional-btn">
				  <apptags:helpDoc url="WorkEstimate.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>

			<form:form action="WorkEstimate.html" class="form-horizontal"
				id="addlabourForm" name="addlabourForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="removeLabourById" id="removeLabourById" />
				<form:hidden path="rateAnalysisWorkId" id="rateAnalysisWorkId" />
				<form:hidden path="rateAnalysisSorId" id="rateAnalysisSorId" />
				<form:hidden path="rateAnalysisSorDId" id="rateAnalysisSorDId" />
				<h4 class="">
					<spring:message code="" text="Material Description" />
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
							<td align="center">${command.rateTypeAllMaster.maItemNo}</td>
							<td align="center">${command.rateTypeAllMaster.unitName}</td>
							<td>${command.rateTypeAllMaster.maDescription}</td>
						</tr>
				</table>

				<div class="panel-body">
					<div class="form-group">
						<label class="label-control col-sm-2"><spring:message
								code="material.master.ssorname" text="Select SOR Name" /></label>
						<div class="col-sm-4">
							<form:select path="sorCommonId" id="sorName"
								onchange="getDateBySorName() ;" disabled="true"
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
							labelCode="material.master.startdate" cssClass="mandColorClass "
							readonly="true"></apptags:date>
						<apptags:date fieldclass="" datePath="toDate"
							labelCode="material.master.enddate" cssClass="mandColorClass "
							readonly="true"></apptags:date>
					</div>
				</div>


				<c:set var="d" value="0" scope="page" />
				<div class="rateAnalysisMasterAdd" id="rateAnalysisMasterAdd">
					<table class="table table-bordered table-striped"
						id="rateAnalysisMasterTab">
						<thead>
							<tr>
								<th width="10%"><spring:message code="" text="Rate Type" /><span
									class="mand">*</span></th>
								<th width="10%"><spring:message code="" text="Select From" /><span
									class="mand">*</span></th>
								<th width="20%"><spring:message code="" text="From Master" /><span
									class="mand">*</span></th>
								<th width="10%"><spring:message code="" text="Rate" /><span
									class="mand">*</span></th>
								<th width="10%"><spring:message code="" text="Unit" /><span
									class="mand">*</span></th>
								<th width="10%"><spring:message code="" text="Quantity" /><span
									class="mand">*</span></th>
								<th width="10%"><spring:message code="" text="Total Amount" /><span
									class="mand">*</span></th>
								<c:if test="${command.saveMode ne 'V'}">
									<th scope="col" width="10%"><a onclick='return false;'
										class="btn btn-blue-2 btn-sm addButton"> <i
											class="fa fa-plus-circle"></i></a></th>
								</c:if>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th colspan="6" class="text-right"><spring:message
										code="work.estimate.Total" /></th>
								<th colspan="1"><form:input path="" id="allTotal"
										cssClass="form-control text-right" readonly="true" /></th>
								<c:if test="${command.saveMode ne 'V'}">
									<th colspan="1" class="text-right"></th>
								</c:if>
							</tr>
						</tfoot>
						<c:choose>
							<c:when test="${command.saveMode eq 'V'}">
								<c:forEach var="sorData" items="${command.addAllRatetypeEntity}"
									varStatus="status">

									<tr class="appendableClass">

										<td align="center"><form:hidden
												path="addAllRatetypeEntity[${d}].workEstemateId"
												id="workEstemateId${d}" /> <form:select
												path="addAllRatetypeEntity[${d}].workEstimFlag"
												class="form-control" id="rateTypeId${d}"
												onchange="rateTypeData(${d});" disabled="true">
												<form:option value="">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<c:forEach items="${command.ratePrifixMap}" var="lookUp">
													<form:option value="${lookUp.key}" code="${lookUp.key}">${lookUp.value}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:select path="" class="form-control"
												id="selectType${d}" onchange="rateTypeData(${d});"
												disabled="true">
												<form:option value="">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<form:option value="M">
													<spring:message code="" text="From Master" />
												</form:option>
												<form:option value="D">
													<spring:message
														code="work.measurement.sheet.details.direct" />
												</form:option>
											</form:select></td>

										<td><form:select
												path="addAllRatetypeEntity[${d}].gRateMastId"
												class="form-control" id="gRateMastId${d}"
												onkeypress="return hasAmount(event, this, 3, 2)"
												onchange="getRateUnitBySorId(${d});" disabled="true">
												<form:option value="">
													<spring:message code="holidaymaster.select" />
												</form:option>

												<c:forEach
													items="${command.addAllRatetypeEntity[d].rateList}"
													var="rateList">
													<form:option value="${rateList.maId}"
														code="${rateList.maItemUnit},${rateList.maRate}"
														label="${rateList.maDescription}"></form:option>
												</c:forEach>

											</form:select></td>

										<td><form:input id="rate${d}" path=""
												class=" form-control text-right" placeholder="00.00"
												readonly="true" /></td>

										<td><form:select path="" class="form-control"
												id="maItemUnit${d}" disabled="true">
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
												class="form-control text-right" id="workEstimQuantity${d}"
												onkeyup="calculateTotal();" placeholder="000.00"
												disabled="true"
												onkeypress="return hasAmount(event, this, 3, 2)"
												onchange="getAmountFormatInDynamic((this),'workEstimQuantity')" /></td>

										<td><form:input
												path="addAllRatetypeEntity[${d}].workEstimAmount"
												class="form-control text-right allamount" disabled="true"
												id="workEstimAmount${d}" placeholder="0000.00"
												onkeypress="return hasAmount(event, this, 13, 2)"
												onchange="getAmountFormatInDynamic((this),'workEstimAmount')" /></td>

									</tr>

									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:when test="${fn:length(command.addAllRatetypeEntity) > 0}">
								<c:forEach var="sorData" items="${command.addAllRatetypeEntity}"
									varStatus="status">

									<tr class="appendableClass">

										<td align="center"><form:hidden
												path="addAllRatetypeEntity[${d}].workEstemateId"
												id="workEstemateId${d}" /> <form:select
												path="addAllRatetypeEntity[${d}].workEstimFlag"
												class="form-control" id="rateTypeId${d}"
												onchange="rateTypeData(${d});">
												<form:option value="">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<c:forEach items="${command.ratePrifixMap}" var="lookUp">
													<form:option value="${lookUp.key}" code="${lookUp.key}">${lookUp.value}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:select path="" class="form-control"
												id="selectType${d}" onchange="rateTypeData(${d});">
												<form:option value="">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<form:option value="M">
													<spring:message code="" text="From Master" />
												</form:option>
												<form:option value="D">
													<spring:message
														code="work.measurement.sheet.details.direct" />
												</form:option>
											</form:select></td>

										<td><form:select
												path="addAllRatetypeEntity[${d}].gRateMastId"
												class="form-control" id="gRateMastId${d}"
												onchange="getRateUnitBySorId(${d});">
												<form:option value="">
													<spring:message code="holidaymaster.select" />
												</form:option>

												<c:forEach
													items="${command.addAllRatetypeEntity[d].rateList}"
													var="rateList">
													<form:option value="${rateList.maId}"
														code="${rateList.maItemUnit},${rateList.maRate}"
														label="${rateList.maDescription}"></form:option>
												</c:forEach>

											</form:select></td>

										<td><form:input id="rate${d}" path=""
												class=" form-control text-right" readonly="true"
												placeholder="00.00"
												onkeypress="return hasAmount(event, this, 3, 2)" /></td>

										<td><form:select path="" class="form-control"
												id="maItemUnit${d}" disabled="true">
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
												class="form-control text-right" id="workEstimQuantity${d}"
												onkeyup="calculateTotal();" placeholder="000.00"
												onkeypress="return hasAmount(event, this, 3, 2)"
												onchange="getAmountFormatInDynamic((this),'workEstimQuantity')" /></td>

										<td><form:input
												path="addAllRatetypeEntity[${d}].workEstimAmount"
												class="form-control text-right allamount"
												id="workEstimAmount${d}" onkeyup="calculateTotal();"
												onkeypress="return hasAmount(event, this, 13, 2)"
												onchange="getAmountFormatInDynamic((this),'workEstimAmount')"
												readonly="true" placeholder="0000.00" /></td>

										<td class="text-center"><a href='#'
											onclick='return false;'
											class='btn btn-danger btn-sm delButton'><i
												class="fa fa-trash"></i></a></td>
									</tr>

									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="appendableClass">

									<td align="center"><form:select
											path="addAllRatetypeEntity[${d}].workEstimFlag"
											class="form-control" id="rateTypeId${d}"
											onchange="rateTypeData(${d});">
											<form:option value="">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<c:forEach items="${command.ratePrifixMap}" var="lookUp">
												<form:option value="${lookUp.key}" code="${lookUp.key}">${lookUp.value}</form:option>
											</c:forEach>
										</form:select></td>
									<td><form:select path="" class="form-control"
											id="selectType${d}" onchange="rateTypeData(${d});">
											<form:option value="">
												<spring:message code="holidaymaster.select" />
											</form:option>
											<form:option value="M">
												<spring:message code="" text="From Master" />
											</form:option>
											<form:option value="D">
												<spring:message code="work.measurement.sheet.details.direct" />
											</form:option>
										</form:select></td>

									<td><form:select
											path="addAllRatetypeEntity[${d}].gRateMastId"
											class="form-control" id="gRateMastId${d}"
											onchange="getRateUnitBySorId(${d});">
											<form:option value="">
												<spring:message code="holidaymaster.select" />
											</form:option>
										</form:select></td>

									<td><form:input id="rate${d}" path=""
											class=" form-control text-right" readonly="true"
											onkeypress="return hasAmount(event, this, 3, 2)"
											placeholder="00.00" /></td>

									<td><form:select path="" class="form-control"
											id="maItemUnit${d}" disabled="true">
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
											class="form-control text-right" id="workEstimQuantity${d}"
											onkeyup="calculateTotal();" placeholder="000.00"
											onkeypress="return hasAmount(event, this, 3, 2)"
											onchange="getAmountFormatInDynamic((this),'workEstimQuantity')" /></td>
									<td><form:input
											path="addAllRatetypeEntity[${d}].workEstimAmount"
											class="form-control text-right allamount"
											id="workEstimAmount${d}" onkeyup="calculateTotal();"
											onkeypress="return hasAmount(event, this, 13, 2)"
											onchange="getAmountFormatInDynamic((this),'workEstimAmount')"
											readonly="true" placeholder="0000.00" /></td>

									<td class="text-center"><a href='#'
										onclick='return false;'
										class='btn btn-danger btn-sm delButton'><i
											class="fa fa-trash"></i></a></td>
								</tr>

								<c:set var="d" value="${d + 1}" scope="page" />
							</c:otherwise>
						</c:choose>

					</table>

				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="saveRateTypeForm(this);">
							<spring:message code="" text="Submit" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="proceedForMaterial();" id="button-Cancel">
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>