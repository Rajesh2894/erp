<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript"
	src="js/solid_waste_management/WasteRateChart.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.wasteRate.heading"
						text="Waste Sale Rate Chart" /></strong>
			</h2>
			<apptags:helpDoc url="WasteRateChart.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand" /><i
					class="text-red-1">* </i> <spring:message
						code="solid.waste.mand.field" /> </span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="WasteRateChart.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="UpdateSaleDetailsForm" id="id_UpdateSaleDetailsForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<c:set value="${command.prefixLevel}" var="level" scope="page"></c:set>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->
				<div class="text-left clear padding-10">
					<div class="form-group">
						<c:choose>
							<c:when test="${command.saveMode eq 'V'}">
								<apptags:date fieldclass="fromDateClass"
									labelCode="swm.fromDate"
									datePath="wasteRateMasterDto.wFromDate" cssClass="fromDate "
									isDisabled="true" isMandatory="true">
								</apptags:date>
								<apptags:date fieldclass="toDateClass" labelCode="swm.toDate"
									datePath="wasteRateMasterDto.wToDate" isMandatory="true"
									isDisabled="true" cssClass="toDate">
								</apptags:date>

							</c:when>
							<c:otherwise>
								<apptags:date fieldclass="fromDateClass"
									labelCode="swm.fromDate"
									datePath="wasteRateMasterDto.wFromDate" cssClass="fromDate "
									isDisabled="true" isMandatory="true">
								</apptags:date>
								<apptags:date fieldclass="toDateClass" labelCode="swm.toDate"
									datePath="wasteRateMasterDto.wToDate" isMandatory="true" readonly="true"
									isDisabled="" cssClass="toDate">
								</apptags:date>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="swm.wasteRate.solid.details"
										text="Waste Rate Details" />
								</a>
							</h4>
						</div>
						<div class="panel-default">
							<div id="a2" class="panel-collapse collapse in">
								<div class=" clear padding-10"></div>
								<div class="panel-body">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="id_updateSaleDetailsEdit">
										<thead>
											<tr>

												<th scope="col" width="10%"><spring:message
														code="swm.wastetype" text="Waste Type" /></th>
												<th scope="col" width="10%"><spring:message
														code="swm.Expenditure.item.name" text="Item Name" /></th>
												<th scope="col" width="10%"><spring:message
														code="swm.Expenditure.rate" text="Rate" /></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td align="center" width="10%"><div
														class="input-group col-sm-6 ">
														<form:select path="wasteRateMasterDto.codWast1"
															cssClass="form-control mandColorClass" id="wasteType"
															onchange="" disabled="true" data-rule-required="true">
															<form:option value="">select</form:option>
															<c:forEach items="${command.getSecondLevelData('WTY',1)}"
																var="lookup">
																<form:option value="${lookup.lookUpId}"
																	code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
															</c:forEach>
														</form:select>
													</div></td>
												<td align="center" width="10%"><div
														class="input-group col-sm-6 ">
														<form:select path="wasteRateMasterDto.codWast"
															cssClass="form-control mandColorClass" id="item${d}"
															onchange="" disabled="true" data-rule-required="true">
															<form:option value="0">Select</form:option>
															<c:forEach items="${command.getSecondLevelData('WTY',level)}"
																var="lookup">
																<form:option value="${lookup.lookUpId}"
																	code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
															</c:forEach>
														</form:select>
													</div></td>

												<td align="center" width="10%">
													<div class="input-group col-sm-6 ">
														<form:input path="wasteRateMasterDto.wasteRate"
															cssClass="form-control  mandColorClass hasDecimal text-right"
															onblur="" id="rate${d}"
															disabled="${command.saveMode eq 'V' ? true : false }" />
														<span class="input-group-addon"><i
															class="fa fa-inr"></i>&nbsp;<spring:message code="swm.wasteRate.solid.perkg"
																text=" Per Kg" /></span>
													</div>
												</td>

											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="Proceed(this)" id="btnSave">
							<spring:message code="solid.waste.submit" text="Submit" />
						</button>
					</c:if>
					<apptags:backButton url="WasteRateChart.html"></apptags:backButton>

				</div>
			</form:form>
		</div>
	</div>
</div>
