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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/material_mgmt/service/purRequisition.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="purchase.requisition.heading"
					text="Purchase Requisition" />
			</h2>
			<apptags:helpDoc url="PurchaseRequisition.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="PurchaseRequisition.html"
				name="PurchaseRequisitionForm" id="PurchaseRequisitionId"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<h4>
					<spring:message code="store.master.details1"
						text="Purchase Requisition Details" />
				</h4>
				
				<form:hidden path="levelCheck" id="levelCheck" />
								
				<div class="form-group">
					<label class="control-label col-sm-2" for="Store Name"><spring:message
							code="store.master.name" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="purchaseRequistionDto.storeId"
							onchange="getStoreData()"
							cssClass="form-control   mandColorClass" id="storeNameId"
							data-rule-required="true" disabled="true">
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<apptags:input labelCode="purchase.requisition.reqDate"
						cssClass="fromDateClass fromDate" path="purchaseRequistionDto.prDate" isReadonly="true"
						isMandatory="true" isDisabled="true" />
				</div>
				<div class="form-group">
					<apptags:input labelCode="store.master.store.incharge"
						path="purchaseRequistionDto.departmentName" isMandatory="true"
						cssClass="form control mandcolour" isReadonly="true">
					</apptags:input>
					<apptags:input labelCode="store.master.location"
						path="purchaseRequistionDto.requestedName" isMandatory="true"
						cssClass="form control mandcolour hasNameClass" isReadonly="true">
					</apptags:input>
				</div>
			
				<h4>
					<spring:message code="material.item.master.itemDetails" text="Item Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="unitDetailTable"
								class="table table-striped table-bordered appendableClass unitDetail">
								<thead>
									<tr>
										<th><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="material.item.master.name" text="Item Name" /></th>
										<th><spring:message code="store.master.uom" text="Uom" /></th>
										<th><spring:message code="store.master.quantity" text="Quantity" /></th>
										<th><spring:message code="material.item.master.tax" text="Tax" /></th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${command.purchaseRequistionDto.purchaseRequistionDetDtoList}" var="data" varStatus="index">
									<tr class="firstUnitRow">
										<td align="center" width="10%"><form:input path="" cssClass="form-control mandColorClass " id="sequence${d}" value="${d+1}" disabled="true" />
										<td style="width: 20%">
											<div class="col-sm-4" class="text-center">
												<form:select
													path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].itemId"
													cssClass="form-control   mandColorClass" id="itemId${d}"
													data-rule-required="true" style="width:400%" onchange="getUomName()" disabled="true">
													<c:forEach items="${command.itemIdNameList}" var="item">
														<form:option value="${item[0]}">${item[1]}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</td>
										<td><form:input name="name"
												path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].uonName"
												id="uomId${d}" type="text" class="form-control" disabled="true"></form:input></td>
										<td><form:input name="name"
												path="purchaseRequistionDto.purchaseRequistionDetDtoList[${d}].quantity"
												id="quantity${d}" type="text" class="form-control" value="${data.quantity}" disabled="true"></form:input></td>
										<td><form:input name="name"
												path=""
												id="taxId${d}" type="text" class="form-control" disabled="true"></form:input></td>
									</tr>
									<c:set var="d" value="${d+1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<h4>
					<spring:message code="work.def.fin.info"
						text="Year-wise Financial Information" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table class="table table-bordered table-striped"
										id="financeDataDetails">
								<thead>
											<tr>
												<th width="18%"><spring:message
														code="work.def.fin.year" text="Financial Year" /></th>
												<th width="18%"><spring:message
														code="work.def.fin.code" text="Finance Code" /></th>
												<%-- <th width="18%"><spring:message
														code="work.def.percentage" text="Percentage" /></th> --%>
												<th width="18%"><spring:message code="work.def.docs.no"
														text="Approval No." /></th>
												<th width="18%"><spring:message code="purchase.requisition.estimated.amount"
														text="Estimated Amount" /></th>
												<th width="18%"><spring:message code="work.def.budget" /></th>
											</tr>
										</thead>
								  <tbody>
									<c:forEach items="${command.purchaseRequistionDto.yearDto}">
													<tr class="finacialInfoClass">
														<form:hidden path="purchaseRequistionDto.yearDto[${d}].yearId"
															id="yearId${d}" />
														<form:hidden path="purchaseRequistionDto.yearDto[${d}].finActiveFlag"
															id="finActiveFlag${d}" value="A" />
														<td><form:select
																path="purchaseRequistionDto.yearDto[${d}].faYearId"
																cssClass="form-control"
																onchange="resetFinanceCode(this,${d});"
																id="faYearId${d}" disabled="true">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.faYears}" var="lookUp">
																	<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
																</c:forEach>
															</form:select></td>
														<c:choose>
															<c:when test="${command.cpdMode eq 'L'}">
																<td><form:select
																		path="purchaseRequistionDto.yearDto[${d}].sacHeadId"
																		cssClass="form-control chosen-select-no-results"
																		onchange="checkForDuplicateHeadCode(this,${d});"
																		id="sacHeadId${d}"
																		disabled="true">
																		<form:option value="">
																			<spring:message code='work.management.select' />
																		</form:option>
																		<c:forEach items="${command.budgetList}" var="lookUp">
																			<form:option value="${lookUp.sacHeadId}">${lookUp.acHeadCode}</form:option>
																		</c:forEach>
																	</form:select></td>
															</c:when>
															<c:otherwise>
																<td><form:input
																		path="purchaseRequistionDto.yearDto[${d}].financeCodeDesc"
																		cssClass="form-control"
																		onchange="checkForDuplicateFinanceCode(this,${d});"
																		id="financeCodeDesc${d}"
																		onkeyup="inputPreventSpace(event.keyCode,this);" disabled="true"/></td>
															</c:otherwise>
														</c:choose>
														<%-- <td><form:input
																path="wmsDto.yearDtos[${d}].yearPercntWork"
																cssClass="form-control text-right"
																id="yearPercntWork${d}"
																onkeypress="return hasAmount(event, this, 3, 2)"
																onchange="getAmountFormatInDynamic((this),'yearPercntWork')" /></td> --%>
														<td><form:input
																path="purchaseRequistionDto.yearDto[${d}].yeDocRefNo"
																cssClass="form-control  " id="yeDocRefNo${d}"
																maxlength="50"
																onkeyup="inputPreventSpace(event.keyCode,this);" disabled="true"/></td>
														<td><form:input
																path="purchaseRequistionDto.yearDto[${d}].yeBugAmount"
																cssClass="form-control text-right " id="yeBugAmount${d}"
																onkeypress="return hasAmount(event, this, 8, 2)"
																onchange="getAmountFormatInDynamic((this),'yeBugAmount')"
																onkeyup="getTotalAmount()" disabled="true"/></td>
														<td class="text-center">
															<button type="button" class="btn btn-primary btn-sm"
																onclick="return viewExpenditureDetails(${d});"
																id="viewExpDet${d}">
																<i class="fa fa-eye" aria-hidden="true"></i>
															</button>

														</td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<c:choose>
						<c:when test="${command.isTenderViewEstimate eq 'Y'}">
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backTenderForm();" id="button-Cancel">
								<spring:message code="solid.waste.back" text="Back" />
							</button>
						</c:when>
						<c:when test="${command.levelCheck eq 1}">
							<apptags:backButton url="AdminHome.html"></apptags:backButton>
						</c:when>
						<c:otherwise>
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backPurReqForm('PurchaseRequisition.html');"
								id="button-Cancel">
								<spring:message code="material.management.back" text="Back" />
							</button>
						</c:otherwise>
					</c:choose>
				</div>
			</form:form>
		</div>
	</div>
</div>