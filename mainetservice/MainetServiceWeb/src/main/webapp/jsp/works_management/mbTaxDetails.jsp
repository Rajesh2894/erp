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
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	jQuery('#tab4').addClass('active');
</script>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="wms.MeasurementBookTaxDetails"
					text="Measurement-Book Tax Details" />
			</h2>
			<div class="additional-btn">
			  <apptags:helpDoc url="MeasurementBook.html"></apptags:helpDoc>
			</div>
		</div>

		<div class="widget-content padding">
			<form:form action="MeasurementBook.html" class="" name="mbTaxDetails"
				id="mbTaxDetails">
				<form:input type="hidden" path="removeMbTaxDetIds"
					id="removeMbTaxDetIds" />
				<form:hidden path="" id="saveMode" value="${command.saveMode}" />
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<jsp:include page="/jsp/works_management/measurementBookTab.jsp" />
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.order.workOrder.no" text="work Order number" /></label>
					<div class="col-sm-4">
						<form:input path="workOrderDto.workOrderNo"
							cssClass="form-control" id="" readonly="true"
							data-rule-required="true" />
					</div>
				</div>

				<c:set var="d" value="0" scope="page" />
				<div class="table-responsive clear">
					<table class="table table-bordered table-striped"
						id="mbTaxDetailsTab">
						<thead>
							<tr>
								<th width="5%"><spring:message
										code="ser.no" text="Sr. No." /><span
									class="mand">*</span></th>
								<th width="25%"><spring:message code="wms.TaxCategory"
										text="Tax Category" /><span class="mand">*</span></th>
								<th width="8%"><spring:message
										code="work.measurement.sheet.details.additiondeduction"
										text="Addition/Deduction" /><span class="mand">*</span></th>
								<th width="5%"><spring:message code="wms.AmountPercentage"
										text="Amount/Percentage" /><span class="mand">*</span></th>
								<th width="10%"><spring:message
										code="work.measurement.sheet.details.value" text="Value" /><span
									class="mand">*</span></th>

								<c:if
									test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
									<th scope="col" width="8%"><a onclick='addMbTaxDetails();'
										class="btn btn-blue-2 btn-sm addButton"> <i
											class="fa fa-plus-circle"></i></a></th>
								</c:if>
							</tr>
						</thead>

						<c:choose>
							<c:when
								test="${command.saveMode eq 'V' || command.saveMode eq 'AP'}">
								<c:forEach var="taxData" items="${command.mbTaxDetailsDto}"
									varStatus="status">
									<tr class="appendableClass">

										<td align="center"><form:input path=""
												cssClass="form-control" id="sequence${d}" value="${d+1}"
												readonly="true" /> <form:input type="hidden"
												path="mbTaxDetailsDto[${d}].mbTaxId" id="mbTaxId${d}" /></td>
										<td align="center"><form:select
												path="mbTaxDetailsDto[${d}].taxId"
												cssClass="form-control chosen-select-no-results mandColorClass"
												id="taxId${d}">
												<form:option value="">Select</form:option>
												<c:forEach items="${command.taxList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select path="mbTaxDetailsDto[${d}].mbTaxType"
												class="form-control chosen-select-no-results"
												id="mbTaxType${d}" disabled="true">
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

										<td><form:select path="mbTaxDetailsDto[${d}].mbTaxFact"
												cssClass="form-control mandColorClass chosen-select-no-results"
												id="mbTaxFact${d}" data-rule-required="true">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.valueTypeList}" var="payType">
													<form:option value="${payType.lookUpId }"
														code="${payType.lookUpCode}">${payType.descLangFirst}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input path="mbTaxDetailsDto[${d}].mbTaxValue"
												class="form-control text-right" disabled="true"
												id="mbTaxValue${d}"
												onkeypress="return hasAmount(event, this, 5, 2)"
												onchange="getAmountFormatInDynamic((this),'mbTaxValue')" /></td>
									</tr>

									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:when test="${fn:length(command.mbTaxDetailsDto) > 0}">
								<c:forEach var="taxData" items="${command.mbTaxDetailsDto}"
									varStatus="status">
									<tr class="appendableClass">

										<td align="center"><form:input path=""
												cssClass="form-control" id="sequence${d}" value="${d+1}"
												readonly="true" /> <form:input type="hidden"
												path="mbTaxDetailsDto[${d}].mbTaxId" id="mbTaxId${d}" /></td>
										<td align="center"><form:select
												path="mbTaxDetailsDto[${d}].taxId"
												cssClass="form-control mandColorClass" id="taxId${d}">
												<form:option value="">Select</form:option>
												<c:forEach items="${command.taxList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select path="mbTaxDetailsDto[${d}].mbTaxType"
												class="form-control" id="mbTaxType${d}">
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

										<td><form:select path="mbTaxDetailsDto[${d}].mbTaxFact"
												cssClass="form-control mandColorClass" id="mbTaxFact${d}"
												data-rule-required="true">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${command.valueTypeList}" var="payType">
													<form:option value="${payType.lookUpId }"
														code="${payType.lookUpCode}">${payType.descLangFirst}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input path="mbTaxDetailsDto[${d}].mbTaxValue"
												class="form-control mandColorClass text-right"
												id="mbTaxValue${d}"
												onkeypress="return hasAmount(event, this, 5, 2)"
												onchange="getAmountFormatInDynamic((this),'mbTaxValue')" /></td>
										<td align="center"><a href='#' data-toggle="tooltip"
											data-placement="top" class="btn btn-danger btn-sm delButton"
											onclick="deleteTableRow('mbTaxDetailsTab',$(this),'removeMbTaxDetIds');"><i
												class="fa fa-minus"></i></a></td>
									</tr>

									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="appendableClass">

									<td align="center"><form:input path=""
											cssClass="form-control" id="sequence${d}" value="${d+1}"
											readonly="true" /> <form:input type="hidden"
											path="mbTaxDetailsDto[${d}].mbTaxId" id="mbTaxId${d}" /></td>
									<td align="center"><form:select
											path="mbTaxDetailsDto[${d}].taxId"
											cssClass="form-control mandColorClass" id="taxId${d}">
											<form:option value="">Select</form:option>
											<c:forEach items="${command.taxList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:select path="mbTaxDetailsDto[${d}].mbTaxType"
											class="form-control" id="mbTaxType${d}">
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

									<td><form:select path="mbTaxDetailsDto[${d}].mbTaxFact"
											cssClass="form-control mandColorClass" id="mbTaxFact${d}"
											data-rule-required="true">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.valueTypeList}" var="payType">
												<form:option value="${payType.lookUpId }"
													code="${payType.lookUpCode}">${payType.descLangFirst}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:input path="mbTaxDetailsDto[${d}].mbTaxValue"
											class="form-control mandColorClass text-right"
											id="mbTaxValue${d}"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'mbTaxValue')" /></td>

									<td align="center"><a href='#' data-toggle="tooltip"
										data-placement="top" class="btn btn-danger btn-sm delButton"
										onclick="deleteTableRow('mbTaxDetailsTab',$(this),'removeMbTaxDetIds');"><i
											class="fa fa-minus"></i></a></td>
								</tr>

								<c:set var="d" value="${d + 1}" scope="page" />
							</c:otherwise>
						</c:choose>
					</table>
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
						<button class="btn btn-success " onclick="saveMbTaxDetails(this);"
							type="button">
							<i class="button-input"></i>
							<spring:message code="mileStone.submit" text="Submit" />
						</button>
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="measurementSheet();" id="backButton">
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



