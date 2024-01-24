<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/billingMethodChange.js"></script>

<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>
<div class="widget">
	<div class="widget-header">
		<h2>
			<strong><spring:message code="property.flatWiseDistribution"
					text="Flat wise Taxes Wise Distribution" /></strong>
		</h2>
	</div>

	<div class="widget-content padding">

		<!-- Start Form -->
		<form:form action="BillingMethodAuthorization.html"
			class="form-horizontal form" name="BillingArrearsAuthorization"
			id="BillingArrearsAuthorization">

			<jsp:include page="/jsp/tiles/validationerror.jsp" />

			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv"></div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message
						code="propertydetails.PropertyNo." text="Property No." /></label>
				<div class="col-sm-4">
					<div class="input-group">
						<form:input path="provisionalAssesmentMstDto.assNo"
							class="form-control" id="assNo" readonly="true" />

					</div>
				</div>
				<label class="col-sm-2 control-label"><spring:message
						code="property.FlatNo" text="Flat No" /></label>
				<div class="col-sm-4">
					<div class="input-group">
						<form:input path="provisionalAssesmentMstDto.flatNo"
							class="form-control" id="flatNo" readonly="true" />

					</div>
				</div>
			</div>

			<c:choose>
				<c:when test="${command.getProvBillPresent() eq 'Y'}">
					<div id="billWiseDetail">
						<div class="widget-header">
							<h2>
								<strong><spring:message code="property.taxentry"
										text="Tax entry" /></strong>
							</h2>
						</div>
						<div>
							<c:choose>
								<c:when test="${not empty command.getBillMasList()}">
									<table id="taxdetailTable"
										class="table table-striped table-bordered appendableClass taxDetails">
										<tbody>
											<tr>
												<th width="3%"><spring:message
														code="unitdetails.unitno" text="Sr No." /></th>
												<th width="17%"><spring:message
														code="dataEntry.finYear" /></th>
												<th width="20%"><spring:message
														code="taxdetails.taxdescription" /></th>
												<th width="20%"><spring:message
														code="taxdetails.balAmount" text="Balance Amount" /></th>
											</tr>
											<c:forEach var="billMas" items="${command.getBillMasList()}"
												varStatus="masStatus">
												<c:forEach var="billDet" items="${billMas.getTbWtBillDet()}"
													varStatus="detStatus">
													<tr class="firstUnitRow ${masStatus.count%2==0? "trfirst": "trSecond"}" >
														<td>${d+1}</td>
														<td><form:select
																path="billMasList[${masStatus.count-1}].bmYear"
																id="year${d}"
																class="form-control disabled text-center mandColorClass displayYearList"
																data-rule-required="true" disabled="true">
																<form:option value="0" label="Select Year"></form:option>
																<c:forEach items="${command.financialYearMapForTax}"
																	var="yearMap">
																	<form:option value="${yearMap.key}"
																		label="${yearMap.value}"></form:option>
																</c:forEach>
															</form:select></td>
														<td width="150"><form:select id="taxDesc"
																path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].taxId"
																class="form-control mandColorClass"
																data-rule-prefixValidation="true"
																onchange="getPaymentMadeDetails()" disabled="true">
																<form:option value="0">
																	<spring:message code="property.sel.optn" text="Select" />
																</form:option>
																<c:forEach items="${command.taxMasterMap}"
																	var="taxMasterMap">
																	<form:option value="${taxMasterMap.key}">${taxMasterMap.value}</form:option>
																</c:forEach>
															</form:select></td>
														<td width=""><form:input
																path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].bdCurBalTaxamt"
																type="text" disabled="true"
																class="form-control has2Decimal mandColorClass text-right"
																id="bdCurBalTaxamt${d}" /></td>
													</tr>
													<c:set var="d" value="${d+1}" />
												</c:forEach>
											</c:forEach>
										</tbody>
									</table>
								</c:when>
							</c:choose>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<c:set var="d" value="0" scope="page" />
					<c:if test="${not empty command.getBillMasList()}">
						<div id="billWiseDetail">
							<div class="widget-header">
								<h2>
									<strong><spring:message code="property.taxentry"
											text="Tax entry" /></strong>
								</h2>
							</div>
							<div>
								<c:choose>
									<c:when test="${not empty command.getBillMasList()}">
										<table id="taxdetailTable"
											class="table table-striped table-bordered appendableClass taxDetails">
											<tbody>
												<tr>
													<th width="3%"><spring:message
															code="unitdetails.unitno" text="Sr No." /></th>
													<th width="17%"><spring:message
															code="dataEntry.finYear" /></th>
													<th width="20%"><spring:message
															code="taxdetails.taxdescription" /></th>
													<th width="20%"><spring:message
															code="taxdetails.balAmount" text="Balance Amount" /></th>
													<th width="20%"><spring:message
															code="taxdetails.balAmountToTransfer"
															text="Amount to be transfered" /></th>
													<th width="20%"><spring:message
															code="taxdetails.arrears" /></th>
												</tr>
												<c:forEach var="billMas" items="${command.getBillMasList()}"
													varStatus="masStatus">
													<c:forEach var="billDet"
														items="${billMas.getTbWtBillDet()}" varStatus="detStatus">

														<tr class="firstUnitRow ${masStatus.count%2==0? "trfirst": "trSecond"}" >
															<td>${d+1}</td>
															<td><form:select
																	path="billMasList[${masStatus.count-1}].bmYear"
																	id="year${d}"
																	class="form-control disabled text-center mandColorClass displayYearList"
																	data-rule-required="true" disabled="true">
																	<form:option value="0" label="Select Year"></form:option>
																	<c:forEach items="${command.financialYearMapForTax}"
																		var="yearMap">
																		<form:option value="${yearMap.key}"
																			label="${yearMap.value}"></form:option>
																	</c:forEach>
																</form:select></td>
															<td width="150"><form:select id="taxDesc"
																	path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].taxId"
																	class="form-control mandColorClass"
																	data-rule-prefixValidation="true"
																	onchange="getPaymentMadeDetails()" disabled="true">
																	<form:option value="0">
																		<spring:message code="property.sel.optn" text="Select" />
																	</form:option>
																	<c:forEach items="${command.taxMasterMap}"
																		var="taxMasterMap">
																		<form:option value="${taxMasterMap.key}">${taxMasterMap.value}</form:option>
																	</c:forEach>
																</form:select></td>
															<td width=""><form:input
																	path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].bdCurBalTaxamt"
																	type="text" disabled="true"
																	class="form-control has2Decimal mandColorClass text-right"
																	id="bdCurBalTaxamt${d}" /></td>
															<td width=""><form:input
																	path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].bdBalAmtToTransfer"
																	type="text" disabled="true"
																	class="form-control has2Decimal mandColorClass text-right"
																	id="bdBalAmtToTransfer${d}" /></td>
															<td width=""><form:input
																	path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].bdCsmp"
																	type="text"
																	class="form-control has2Decimal mandColorClass text-right"
																	id="arrear${d}" /></td>
														</tr>														
														<c:set var="d" value="${d+1}" />
													</c:forEach>
													<tr>
														<td></td>
														<td></td>
														<td><spring:message code="taxdetails.total" text="Total" /></td>
														<td class="text-right">${billMas.totalBalanceAmount}</td>
														<td class="text-right">${billMas.totalAmtToTransfer}</td>
														<td></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
								</c:choose>
							</div>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>

			<!-- Start button -->
			<div class="text-center padding-10">

				<button type="button" class="btn btn-success btn-submit"
					onclick="confirmToNext(this)" id="nextView">
					<spring:message code="bt.ok" text="OK" />
				</button>

				<button class="btn btn-blue-2" type="button"
					onclick="backToFirstPage(this)" id="back">
					<spring:message code="bt.back" text="Cancel" />
				</button>

			</div>
			<!--  End button -->
		</form:form>

	</div>
</div>

