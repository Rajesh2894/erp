<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/workEstimateMeasureDetails.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.measurement.sheet.details"
					text="Measurement Sheet Details" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>
			<form:form action="WorkEstimateMeasureDetails.html"
				class="form-horizontal" name="WorkEstimateMeasureDetails"
				id="WorkEstimateMeasureDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="removeChildIds" id="removeChildIds" />
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
							<td align="center">${command.sorDetailsDto.sorDIteamNo}</td>
							<td align="center">${command.sorDetailsDto.sorIteamUnitDesc}</td>
							<td>${command.sorDetailsDto.sorDDescription}</td>
						</tr>
				</table>

				<h4 class="margin-bottom-10">
					<spring:message code="work.measurement.sheet.details"
						text="Measurement Sheet Details" />
				</h4>

				<c:set var="d" value="0" scope="page" />
				<div class="materialMasterAdd" id="materialMasterAdd">
					<table class="table table-bordered table-striped"
						id="WorkEstimateMeasureDetailsTab">
						<thead>
							<tr>
								<th width="3%"><spring:message
										code="ser.no" text="Sr. No." /><span
									class="mand">*</span></th>
								<th width="25%"><spring:message
										code="work.measurement.sheet.details.particulars"
										text="Particulars of Item" /><span class="mand">*</span></th>
								<th width="3%"><spring:message
										code="work.measurement.sheet.details.nos" text="Nos." /><span
									class="mand">*</span></th>
								<th width="9%"><spring:message
										code="work.measurement.sheet.details.Type" text="Type" /><span
									class="mand">*</span></th>
								<th width="8%"><spring:message
										code="work.measurement.sheet.details.formula" text="Formula" /><span
									class="mand">*</span></th>
								<th width="6%"><spring:message
										code="work.measurement.sheet.details.additiondeduction"
										text="Addition/Deduction" /><span class="mand">*</span></th>
								<th width="8%"><spring:message
										code="work.measurement.sheet.details.Length" text="Length" /><span
									class=""></span></th>
								<th width="8%"><spring:message
										code="work.measurement.sheet.details.width"
										text="Breadth/Width" /><span class=""></span></th>
								<th width="8%"><spring:message
										code="work.measurement.sheet.details.height"
										text="Depth/Height" /><span class=""></span></th>
								<%-- <th width="8%"><spring:message
										code="work.measurement.sheet.details.value" text="Value" /><span
									class="mand">*</span></th> --%>
								<th width="8%"><spring:message
										code="work.estimate.quantity" text="Quantity" /><span
									class="mand">*</span></th>
								<th width="10%"><spring:message
										code="work.measurement.sheet.details.subtotal"
										text="Sub Total" /><span class="mand">*</span></th>
								<c:if test="${command.saveMode ne 'V'}">
									<th scope="col" width="5%"><a onclick='return false;'
										class="btn btn-blue-2 btn-sm addButton"> <i
											class="fa fa-plus-circle"></i></a></th>
								</c:if>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th colspan="10" style="text-align: right">Total:</th>
								<th><form:input id="subTotal"
										class=" form-control text-right" path="" readonly="true"
										onkeypress="return hasAmount(event, this, 10, 2)" /></th>
								<th></th>
							</tr>
						</tfoot>
						<c:choose>
							<c:when test="${command.saveMode eq 'V'}">
								<c:forEach var="sorData" items="${command.measureDetailsList}"
									varStatus="status">
									<tr class="appendableClass">

										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass " id="sequence${d}"
												value="${d+1}" readonly="true" /> <form:input type="hidden"
												path="measureDetailsList[${d}].meMentId" id="meMentId${d}" />
										</td>
										<td><form:input id="meMentParticulare${d}"
												path="measureDetailsList[${d}].meMentParticulare"
												class=" form-control hasNumber" maxlength="500"
												readonly="true" /></td>

										<td><form:input id="meMentNumber${d}"
												path="measureDetailsList[${d}].meMentNumber"
												class=" form-control" maxlength="5"
												onkeyup="calculateTotal();" readonly="true" /></td>

										<td><form:select
												path="measureDetailsList[${d}].meMentValType"
												class="form-control" id="meMentValType${d}"
												onchange="calculateTotal();" disabled="true">
												<form:option value="">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<form:option value="C">
													<spring:message
														code="work.measurement.sheet.details.calculated" />
												</form:option>
												<form:option value="D">
													<spring:message
														code="work.measurement.sheet.details.direct" />
												</form:option>
												<form:option value="F">
													<spring:message
														code="work.measurement.sheet.details.formula" />
												</form:option>
											</form:select></td>

										<td><form:input
												path="measureDetailsList[${d}].meMentFormula"
												class="form-control" disabled="true" id="meMentFormula${d}"
												onblur="calculateTotal();" /></td>

										<td><form:select
												path="measureDetailsList[${d}].meMentType"
												class="form-control" id="meMentType${d}"
												onchange="calculateTotal();" disabled="true">
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

										<td><form:input id="meMentLength${d}"
												path="measureDetailsList[${d}].meMentLength"
												class=" decimal form-control text-right" disabled="true"
												onblur="calculateTotal();" placeholder="00.0000"
												onkeypress="return hasAmount(event, this, 6, 4"
												onchange="getAmountFormatInDynamic((this),'meMentLength')" /></td>
										<td><form:input id="meMentBreadth${d}"
												path="measureDetailsList[${d}].meMentBreadth"
												class="decimal form-control text-right" disabled="true"
												onblur="calculateTotal();" placeholder="00 .0000"
												onkeypress="return hasAmount(event, this, 6, 4)"
												onchange="getAmountFormatInDynamic((this),'meMentBreadth')" /></td>
										<td><form:input id="meMentHeight${d}"
												path="measureDetailsList[${d}].meMentHeight"
												class=" form-control text-right" disabled="true"
												onblur="calculateTotal();" placeholder="000.0000"
												onkeypress="return hasAmount(event, this, 8, 4)"
												onchange="getAmountFormatInDynamic((this),'meMentHeight')" /></td>

										<td><form:input id="meValue${d}"
												path="measureDetailsList[${d}].meValue"
												class=" form-control text-right" readonly="true"
												onblur="calculateTotal();" placeholder="00.0000"
												onkeypress="return hasAmount(event, this, 10, 4)"
												onchange="getAmountFormatInDynamic((this),'meValue')" /></td>

										<td><form:input id="meMentToltal${d}"
												path="measureDetailsList[${d}].meMentToltal"
												class=" form-control text-right" maxlength="14"
												readonly="true" placeholder="0000.0000"
												onkeypress="return hasAmount(event, this, 12, 4)"
												onchange="getAmountFormatInDynamic((this),'meMentToltal')" /></td>
									</tr>

									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</c:when>
							<c:when test="${fn:length(command.measureDetailsList) > 0}">
								<c:forEach var="sorData" items="${command.measureDetailsList}"
									varStatus="status">
									<tr class="appendableClass">

										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass " id="sequence${d}"
												value="${d+1}" disabled="true" /> <form:input type="hidden"
												path="measureDetailsList[${d}].meMentId" id="meMentId${d}" />
										</td>
										<td><form:input id="meMentParticulare${d}"
												path="measureDetailsList[${d}].meMentParticulare"
												class=" form-control" maxlength="500" /></td>

										<td><form:input id="meMentNumber${d}"
												path="measureDetailsList[${d}].meMentNumber"
												class=" form-control hasNumber" maxlength="5"
												onblur="calculateTotal();" /></td>

										<td><form:select
												path="measureDetailsList[${d}].meMentValType"
												class="form-control" id="meMentValType${d}"
												onblur="calculateTotal();">
												<form:option value="">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<form:option value="C">
													<spring:message
														code="work.measurement.sheet.details.calculated" />
												</form:option>
												<form:option value="D">
													<spring:message
														code="work.measurement.sheet.details.direct" />
												</form:option>
												<form:option value="F">
													<spring:message
														code="work.measurement.sheet.details.formula" />
												</form:option>
											</form:select></td>

										<td><form:input
												path="measureDetailsList[${d}].meMentFormula"
												class="form-control" id="meMentFormula${d}"
												onblur="calculateTotal();" /></td>

										<td><form:select
												path="measureDetailsList[${d}].meMentType"
												class="form-control" id="meMentType${d}"
												onchange="calculateTotal();">
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

										<td><form:input id="meMentLength${d}"
												path="measureDetailsList[${d}].meMentLength"
												class=" decimal form-control text-right"
												onblur="calculateTotal();" placeholder="00.0000"
												onkeypress="return hasAmount(event, this, 6, 4)"
												onchange="getAmountFormatInDynamic((this),'meMentLength')" /></td>
										<td><form:input id="meMentBreadth${d}"
												path="measureDetailsList[${d}].meMentBreadth"
												class="decimal form-control text-right"
												onblur="calculateTotal();" placeholder="00.0000"
												onkeypress="return hasAmount(event, this, 6, 4)"
												onchange="getAmountFormatInDynamic((this),'meMentBreadth')" /></td>
										<td><form:input id="meMentHeight${d}"
												path="measureDetailsList[${d}].meMentHeight"
												class=" form-control text-right" onblur="calculateTotal();"
												placeholder="0000.0000"
												onkeypress="return hasAmount(event, this, 8, 4)"
												onchange="getAmountFormatInDynamic((this),'meMentHeight')" /></td>

										<td><form:input id="meValue${d}"
												path="measureDetailsList[${d}].meValue"
												class=" form-control text-right" readonly="true"
												onblur="calculateTotal();" placeholder="00.0000"
												onkeypress="return hasAmount(event, this, 10, 4)"
												onchange="getAmountFormatInDynamic((this),'meValue')" /></td>

										<td><form:input id="meMentToltal${d}"
												path="measureDetailsList[${d}].meMentToltal"
												class=" form-control text-right" readonly="true"
												onblur="return hasAmount(event, this, 12, 4)"
												onchange="getAmountFormatInDynamic((this),'meMentToltal')"
												placeholder="0000.00" /></td>

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

									<td align="center"><form:input path=""
											cssClass="form-control mandColorClass " id="sequence${d}"
											value="${d+1}" disabled="true" /></td>
									<td><form:input id="meMentParticulare${d}"
											path="measureDetailsList[${d}].meMentParticulare"
											class=" form-control" maxlength="500" /></td>

									<td><form:input id="meMentNumber${d}"
											path="measureDetailsList[${d}].meMentNumber"
											class=" form-control hasNumber" maxlength="5"
											onblur="calculateTotal();" /></td>

									<td><form:select
											path="measureDetailsList[${d}].meMentValType"
											class="form-control" id="meMentValType${d}"
											onchange="calculateTotal();">
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

									<td><form:input
											path="measureDetailsList[${d}].meMentFormula"
											class="form-control" id="meMentFormula${d}"
											onblur="calculateTotal();" /></td>

									<td><form:select
											path="measureDetailsList[${d}].meMentType"
											class="form-control" id="meMentType${d}"
											onchange="calculateTotal();">
											<%-- <form:option value="">
												<spring:message code="holidaymaster.select" />
											</form:option> --%>
											<form:option value="A">
												<spring:message
													code="work.measurement.sheet.details.addition" />
											</form:option>
											<form:option value="D">
												<spring:message
													code="work.measurement.sheet.details.deduction" />
											</form:option>
										</form:select></td>

									<td><form:input id="meMentLength${d}"
											path="measureDetailsList[${d}].meMentLength"
											class=" decimal form-control text-right"
											onblur="calculateTotal();" placeholder="00.0000"
											onkeypress="return hasAmount(event, this, 6, 4)"
											onchange="getAmountFormatInDynamic((this),'meMentLength')" /></td>
									<td><form:input id="meMentBreadth${d}"
											path="measureDetailsList[${d}].meMentBreadth"
											class="decimal form-control text-right"
											onblur="calculateTotal();" placeholder="00.0000"
											onkeypress="return hasAmount(event, this, 6, 4)"
											onchange="getAmountFormatInDynamic((this),'meMentBreadth')" /></td>
									<td><form:input id="meMentHeight${d}"
											path="measureDetailsList[${d}].meMentHeight"
											class=" form-control text-right" onblur="calculateTotal();"
											placeholder="0000.0000"
											onkeypress="return hasAmount(event, this, 8, 4)"
											onchange="getAmountFormatInDynamic((this),'meMentHeight')" /></td>

									<td><form:input id="meValue${d}"
											path="measureDetailsList[${d}].meValue"
											class=" form-control text-right" readonly="true"
											onblur="calculateTotal();" placeholder="00.0000"
											onkeypress="return hasAmount(event, this, 10, 4)"
											onchange="getAmountFormatInDynamic((this),'meValue')" /></td>

									<td><form:input id="meMentToltal${d}"
											path="measureDetailsList[${d}].meMentToltal"
											class=" form-control text-right" readonly="true"
											onkeypress="return hasAmount(event, this, 12, 4)"
											onchange="getAmountFormatInDynamic((this),'meMentToltal')"
											placeholder="0000.00" /></td>

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
						<button class="btn btn-success save"
							onclick="saveWorkEstimateMeasureDetails(this);" type="button">
							<i class="fa fa-sign-out padding-right-5"></i>
							<spring:message code="works.management.save" text="" />
						</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="measurementSheet();" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>