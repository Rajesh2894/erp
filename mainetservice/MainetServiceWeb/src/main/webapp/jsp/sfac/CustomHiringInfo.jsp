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

<style>
table.crop-details-table tbody tr td>input[type="checkbox"] {
	margin: 0.5rem 0 0 -0.5rem;
}

.stateDistBlock>label[for="sdb3"]+div {
	margin-top: 0.5rem;
}

.charCase {
	text-transform: uppercase;
}

#udyogAadharApplicable, #isWomenCentric {
	margin: 0.6rem 0 0 0;
}
</style>

<!-- Start Content here -->

<div class="content animated top">
	<div class="widget">
		

		<div class="widget-content padding">
			<form:form id="fpoPMCustomHiringInfo"
				action="FPOProfileManagementForm.html" method="post"
				class="form-horizontal">
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
									data-parent="#accordion_single_collapse" href="#customHiringCenterInfo">
									<spring:message code="sfac.fpo.pm.customHiringEqip"
										text="Equipment Details" />
								</a>
							</h4>
						</div>
						<div id="customHiringCenterInfo" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="customHiringCenterInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											

											<th><spring:message code="sfac.fpo.pm.eqip.eqipName"
													text="Equipment Name" /></th>
										<th><spring:message code="sfac.fpo.pm.eqip.noofeqip"
													text="No of Equipment" /></th>
													<th><spring:message code="sfac.fpo.pm.eqip.eqipPrice"
													text="Price Of Equipment" /></th>	
													
													<th><spring:message code="sfac.fpo.pm.eqip.eqipDesc"
													text="Equipment Description" /></th>
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.equipmentInfoDtos)>0 }">
												<c:forEach var="dto"
													items="${command.dto.equipmentInfoDtos}"
													varStatus="status">
													<tr class="appendableCenterDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.equipmentInfoDtos[${d}].equipmentName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="equipmentName${d}" class="form-control alphaNumeric" maxlength="200"/></td>
														<td><form:input
																path="dto.equipmentInfoDtos[${d}].noOfEquipment" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOfEquipment${d}" class="form-control hasNumber" maxlength="3" /></td>
																
																<td><form:input cssClass="mandColorClass form-control" disabled="${command.viewMode eq 'V' ? true : false }"
																path="dto.equipmentInfoDtos[${d}].priceOfEquipment" onkeypress="return hasAmount(event, this, 10, 2)"
																id="priceOfEquipment${d}" /></td>
																
																<td><form:input  cssClass="mandColorClass  form-control alphaNumeric" disabled="${command.viewMode eq 'V' ? true : false }"
																path="dto.equipmentInfoDtos[${d}].equipmentDesc" maxlength="1000"
																id="equipmentDesc${d}" /></td>	
																
																



														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addCHCenterButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addCHCenterButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteCHCenterDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteCHCenterDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableCenterDetails">
																<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
															<td><form:input
																path="dto.equipmentInfoDtos[${d}].equipmentName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="equipmentName${d}" class="form-control alphaNumeric" maxlength="200" /></td>
														<td><form:input
																path="dto.equipmentInfoDtos[${d}].noOfEquipment" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOfEquipment${d}" class="form-control hasNumber" maxlength="3" /></td>
																
																<td><form:input cssClass="mandColorClass hasAmount form-control" disabled="${command.viewMode eq 'V' ? true : false }"
																path="dto.equipmentInfoDtos[${d}].priceOfEquipment" onkeypress="return hasAmount(event, this, 10, 2)"
																id="priceOfEquipment${d}" /></td>
																
																<td><form:input  cssClass="mandColorClass  form-control alphaNumeric" disabled="${command.viewMode eq 'V' ? true : false }"
																path="dto.equipmentInfoDtos[${d}].equipmentDesc" maxlength="1000"
																id="equipmentDesc${d}" /></td>	


													<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
														class="btn btn-blue-2 btn-sm addCHCenterButton"
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="addCHCenterButton(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deleteCHCenterDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deleteCHCenterDetails(this);"> <i
															class="fa fa-trash"></i>
													</a></c:if></td>

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
									data-parent="#accordion_single_collapse" href="#customHiringServiceInfo">
									<spring:message code="sfac.fpo.pm.customHiringService"
										text="Service Details" />
								</a>
							</h4>
						</div>
						<div id="customHiringServiceInfo" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="customHiringServiceInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											

											<th><spring:message code="sfac.fpo.pm.chsd.itemName"
													text="Rented Item Name" /></th>
													<th><spring:message code="sfac.fpo.pm.chsd.rentedAmt"
													text="Rented Amount" /></th>
										<th><spring:message code="sfac.fpo.pm.chsd.qtyItem"
													text="Quantity Of Item" /></th>
														<th><spring:message code="sfac.fpo.pm.chsd.fromDate"
													text="Rented From Date" /></th>
														<th><spring:message code="sfac.fpo.pm.chsd.toDate"
													text="Rented To Date" /></th>
													
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.customHiringServiceInfoDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.customHiringServiceInfoDTOs}"
													varStatus="status">
													<tr class="appendableServiceDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNoS${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.customHiringServiceInfoDTOs[${d}].rentedItemName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="rentedItemName${d}" class="form-control alphaNumeric"  maxlength="200"/></td>
														<td><form:input
																path="dto.customHiringServiceInfoDTOs[${d}].rentedAmount" disabled="${command.viewMode eq 'V' ? true : false }"
																id="rentedAmount${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>			
														<td><form:input
																path="dto.customHiringServiceInfoDTOs[${d}].itemQuantity" disabled="${command.viewMode eq 'V' ? true : false }"
																id="itemQuantity${d}" class="form-control  hasNumber" maxlength="4" /></td>
																
																<td>
														
															<div class="input-group">
																<form:input
																	path="dto.customHiringServiceInfoDTOs[${d}].rentedFromDate"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="rentedFromDate${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>
													</td>
													<td>
														
															<div class="input-group">
																<form:input
																	path="dto.customHiringServiceInfoDTOs[${d}].rentedToDate"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="rentedToDate${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>
													</td>



														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addCHServiceButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addCHServiceButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteCHServiceDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteCHServiceDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableServiceDetails">
												<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo3${d}"
																value="${d+1}" disabled="true" />
																									<td><form:input
																path="dto.customHiringServiceInfoDTOs[${d}].rentedItemName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="rentedItemName${d}" class="form-control alphaNumeric" maxlength="200" /></td>
														<td><form:input
																path="dto.customHiringServiceInfoDTOs[${d}].rentedAmount" disabled="${command.viewMode eq 'V' ? true : false }"
																id="rentedAmount${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
														<td><form:input
																path="dto.customHiringServiceInfoDTOs[${d}].itemQuantity" disabled="${command.viewMode eq 'V' ? true : false }"
																id="itemQuantity${d}" class="form-control hasNumber" maxlength="4" /></td>
														<td>
															<div class="input-group">
																<form:input
																	path="dto.customHiringServiceInfoDTOs[${d}].rentedFromDate"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="rentedFromDate${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>
													</td>
													<td>
														
															<div class="input-group">
																<form:input
																	path="dto.customHiringServiceInfoDTOs[${d}].rentedToDate"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="rentedToDate${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>
													</td>


													<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
														class="btn btn-blue-2 btn-sm addCHServiceButton"
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="addCHServiceButton(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deleteCHServiceDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deleteCHServiceDetails(this);"> <i
															class="fa fa-trash"></i>
													</a></c:if></td>

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
						title='	<spring:message code="sfac.savencontinue" text="Save & Continue" />'
						onclick="saveCustomHiringInfoForm(this);">
						<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
					<button type="button" class="btn btn-danger"
					onclick="navigateTab('storage-tab','storageInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>