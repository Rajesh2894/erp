<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/fpoProfileManagement.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />



<!-- Start Content here -->

<div class="content animated top">
	<div class="widget">


		<div class="widget-content padding">
			<form:form id="fpoPMPNSInfo" action="FPOProfileManagementForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="fpmId" id="fpmId" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>




				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">



					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#pdInfoDiv">
									<spring:message code="sfac.fpo.pm.productDetails"
										text="Product Details" />
								</a>
							</h4>
						</div>
						<div id="#pdInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="pdInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>


											<th><spring:message code="sfac.cob.commodity.name"
													text="Commodity Name" /></th>
											<th><spring:message code="sfac.fpo.pm.chsd.qtyItem"
													text="Quantity Of Item" /></th>
											<th width="13%"><spring:message code="sfac.fpo.pm.unit"
													text="Unit" /></th>
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>

										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.productionInfoDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.productionInfoDTOs}"
													varStatus="status">
													<tr class="appendablePDDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass " id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.productionInfoDTOs[${d}].commodityName"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="commodityNamePD${d}" class="form-control hasCharacter" maxlength="100"/></td>
														<td><form:input
																cssClass="mandColorClass hasNumber form-control"
																path="dto.productionInfoDTOs[${d}].itemQuantity" maxlength="4"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="itemQuantityPD${d}" /></td>

														<td><c:set var="baseLookupCode" value="UNT" /> <form:select
																path="dto.productionInfoDTOs[${d}].unit"
																class="form-control chosen-select-no-results"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="unit${d}">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>






														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addPDButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addPDButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deletePDDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deletePDDetails(this);"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendablePDDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" />
													<td><form:input
															path="dto.productionInfoDTOs[${d}].commodityName"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="commodityNamePD${d}" class="form-control hasCharacter" maxlength="100" /></td>
													<td><form:input
															cssClass="mandColorClass hasNumber form-control"
															path="dto.productionInfoDTOs[${d}].itemQuantity" maxlength="4"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="itemQuantityPD${d}" /></td>

													<td><c:set var="baseLookupCode" value="UNT" /> <form:select
															path="dto.productionInfoDTOs[${d}].unit"
															class="form-control chosen-select-no-results"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="unit${d}">
															<form:option value="0">
																<spring:message code="sfac.select" />
															</form:option>
															<c:forEach
																items="${command.getLevelData(baseLookupCode)}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>


													<td class="text-center"><c:if
															test="${command.viewMode ne 'V'}">
															<a class="btn btn-blue-2 btn-sm addPDButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addPDButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deletePDDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deletePDDetails(this);"> <i
																class="fa fa-trash"></i>
															</a>
														</c:if></td>

												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>


							</div>
						</div>
					</div>



					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#sdInfo"> <spring:message
										code="sfac.fpo.pm.saleDetails" text="Sale Details" />
								</a>
							</h4>
						</div>
						<div id="#sdInfo" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="sdInfoTable">
									<thead>
										<tr>
											<th width="6%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>

											<th><spring:message code="sfac.cob.commodity.name"
													text="Commodity Name" /></th>
											<th><spring:message code="sfac.fpo.pm.commodityQty"
													text="Commodity Quantity" /></th>
											<th width="8%"><spring:message code="sfac.fpo.pm.unit"
													text="Unit" /></th>
											<th><spring:message code="sfac.fpo.pm.CommodityRate"
													text="Commodity Rate" /></th>
											<th><spring:message code="sfac.fpo.pm.revGen"
													text="Revenue Generated" /></th>
											<th><spring:message code="sfac.fpo.pm.soldPrice"
													text="Sold Place (Mandi, Open market)" /></th>
											<th width="15%"><spring:message
													code="sfac.fpo.pm.mandiName" text="Mandi Name" /></th>
											<th width="15%"><spring:message
													code="sfac.fpo.pm.mandiAddress" text="Mandi Address" /></th>
											<th><spring:message code="sfac.fpo.pm.traderName"
													text="Name Of Trader" /></th>


											<th width="5%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(command.dto.salesInfoDTOs)>0 }">
												<c:forEach var="dto" items="${command.dto.salesInfoDTOs}"
													varStatus="status">
													<tr class="appendableSaleDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNoSale${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.salesInfoDTOs[${d}].commodityName"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="commodityNameSD${d}" class="form-control hasCharacter" maxlength="100" /></td>
														<td><form:input
																path="dto.salesInfoDTOs[${d}].commodityQuantity"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="commodityQuantity${d}"
																class="form-control hasNumber" maxlength="5"/></td>

														<td><c:set var="baseLookupCode" value="UNT" /> <form:select
																path="dto.salesInfoDTOs[${d}].unit"
																class="form-control chosen-select-no-results"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="unitSale${d}">
																<form:option value="0">
																	<spring:message code="sfac.select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:input
																path="dto.salesInfoDTOs[${d}].commodityRate"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="commodityRate${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
														<td><form:input
																path="dto.salesInfoDTOs[${d}].revenueGenerated"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="revenueGenerated${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
														<td><form:input
																path="dto.salesInfoDTOs[${d}].commoditySoldPrice"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="commoditySoldPrice${d}"
																class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
														<td><form:input
																path="dto.salesInfoDTOs[${d}].mandiName"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="mandiName${d}" class="form-control alphaNumeric" maxlength="200" /></td>
														<td><form:textarea
																path="dto.salesInfoDTOs[${d}].mandiAddress"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="mandiAddress${d}" class="form-control alphaNumeric"  /></td>
														<td><form:input
																path="dto.salesInfoDTOs[${d}].nameOfTrader"
																disabled="${command.viewMode eq 'V' ? true : false }"
																id="nameOfTrader${d}" class="form-control hasCharacter" maxlength="100"/></td>


														<td class="text-center"><c:if
																test="${command.viewMode ne 'V'}">
																<a class="btn btn-blue-2 btn-sm addSDButton"
																	title='<spring:message code="sfac.fpo.add" text="Add" />'
																	onclick="addSDButton(this);"> <i
																	class="fa fa-plus-circle"></i></a>
																<a class='btn btn-danger btn-sm deleteSDDetails '
																	title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																	onclick="deleteSDDetails(this);"> <i
																	class="fa fa-trash"></i>
																</a>
															</c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableSaleDetails">

													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNoSale${d}"
															value="${d+1}" disabled="true" />
													<td><form:input
															path="dto.salesInfoDTOs[${d}].commodityName"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="commodityNameSD${d}" class="form-control hasCharacter" maxlength="100" /></td>
													<td><form:input
															path="dto.salesInfoDTOs[${d}].commodityQuantity"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="commodityQuantity${d}" class="form-control hasNumber" maxlength="5"/></td>

													<td><c:set var="baseLookupCode" value="UNT" /> <form:select
															path="dto.salesInfoDTOs[${d}].unit"
															class="form-control chosen-select-no-results"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="unitSale${d}">
															<form:option value="0">
																<spring:message code="sfac.select" />
															</form:option>
															<c:forEach
																items="${command.getLevelData(baseLookupCode)}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>

													<td><form:input
															path="dto.salesInfoDTOs[${d}].commodityRate"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="commodityRate${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
													<td><form:input
															path="dto.salesInfoDTOs[${d}].revenueGenerated"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="revenueGenerated${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
													<td><form:input
															path="dto.salesInfoDTOs[${d}].commoditySoldPrice"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="commoditySoldPrice${d}"
															class="form-control " onkeypress="return hasAmount(event, this, 10, 2)"/></td>
													<td><form:input
															path="dto.salesInfoDTOs[${d}].mandiName"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="mandiName${d}" class="form-control alphaNumeric" maxlength="200"/></td>
													<td><form:textarea
															path="dto.salesInfoDTOs[${d}].mandiAddress"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="mandiAddress${d}" class="form-control alphaNumeric"  /></td>
													<td><form:input
															path="dto.salesInfoDTOs[${d}].nameOfTrader"
															disabled="${command.viewMode eq 'V' ? true : false }"
															id="nameOfTrader${d}" class="form-control hasCharacter" maxlength="100" /></td>


													<td class="text-center"><c:if
															test="${command.viewMode ne 'V'}">
															<a class="btn btn-blue-2 btn-sm addSDButton"
																title='<spring:message code="sfac.fpo.add" text="Add" />'
																onclick="addSDButton(this);"> <i
																class="fa fa-plus-circle"></i></a>
															<a class='btn btn-danger btn-sm deleteSDDetails '
																title='<spring:message code="sfac.fpo.delete" text="Delete" />'
																onclick="deleteSDDetails(this);"> <i
																class="fa fa-trash"></i>
															</a>
														</c:if></td>

												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>


							</div>
						</div>
					</div>



				</div>

				<div class="text-center padding-top-10">
					<c:if test="${command.viewMode ne 'V'}">
						<button type="button" class="btn btn-success"
							title='<spring:message code="sfac.savencontinue" text="Save & Continue" />'
							onclick="savePNSInfoForm(this);">
							<spring:message code="sfac.savencontinue" text="Save & Continue" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger"
					onclick="navigateTab('custom-tab','customInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>