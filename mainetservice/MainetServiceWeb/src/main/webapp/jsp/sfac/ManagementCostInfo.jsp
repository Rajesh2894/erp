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
			<form:form id="fpoPMManagementCostInfo"
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
									data-parent="#accordion_single_collapse" href="#ManagementCostDiv">
									<spring:message code="sfac.fpo.pm.mgmtCost"
										text="Management Cost Details" />
								</a>
							</h4>
						</div>
						<div id="ManagementCostDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="ManagementCostTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th><spring:message code="sfac.fpo.pm.mcd"
													text="FPO Management Cost disbursed (INR)" /></th>

											<th><spring:message code="sfac.fpo.pm.DateofRelease"
													text="Date of Release" /></th>
											<th><spring:message code="sfac.fpo.pm.amtRelease"
													text="Amount Released" /></th>
											<th><spring:message code="sfac.fpo.pm.costDisbursed"
													text="CBBO Cost disbursed (INR)" /></th>
											<th><spring:message code="sfac.fpo.pm.DateofRelease"
													text="Date of Release" /></th>
													
											<th><spring:message code="sfac.fpo.pm.amtRelease"
													text="Amount Released" /></th>
												
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.managementCostDetailDto)>0 }">
												<c:forEach var="dto"
													items="${command.dto.managementCostDetailDto}"
													varStatus="status">
													<tr class="appendableMgmtCostDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>
														

														
														<td><form:input
																path="dto.managementCostDetailDto[${d}].managementCostDisbursed" disabled="${command.viewMode eq 'V' ? true : false }"
																id="managementCostDisbursed${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)"/></td>
																	<td>

															<div class="input-group">
																<form:input
																	path="dto.managementCostDetailDto[${d}].dateOfRelease"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="dateOfRelease${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>


														</td>
																<td><form:input
																path="dto.managementCostDetailDto[${d}].amountRelease" disabled="${command.viewMode eq 'V' ? true : false }"
																id="amountRelease${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																
																<td><form:input
																path="dto.managementCostDetailDto[${d}].cbboCostDisbursed" disabled="${command.viewMode eq 'V' ? true : false }"
																id="cbboCostDisbursed${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																
																<td>

															<div class="input-group">
																<form:input
																	path="dto.managementCostDetailDto[${d}].cbboDateOfRelease"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="cbboDateOfRelease${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="${command.viewMode eq 'V' ? true : false }" />
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>


														</td>
																
																<td><form:input
																path="dto.managementCostDetailDto[${d}].cbbodAmountRelease" disabled="${command.viewMode eq 'V' ? true : false }"
																id="cbbodAmountRelease${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																
															

													

													<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
														class="btn btn-blue-2 btn-sm addManagementCostButton"
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="addManagementCostButton(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deleteManagementCostDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deleteManagementCostDetails(this);"> <i
															class="fa fa-trash"></i>
													</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableMgmtCostDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" /></td>
														
														<td><form:input
																path="dto.managementCostDetailDto[${d}].managementCostDisbursed" disabled="${command.viewMode eq 'V' ? true : false }"
																id="managementCostDisbursed${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																	<td>

															<div class="input-group">
																<form:input
																	path="dto.managementCostDetailDto[${d}].dateOfRelease"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="dateOfRelease${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>


														</td>
																<td><form:input
																path="dto.managementCostDetailDto[${d}].amountRelease" disabled="${command.viewMode eq 'V' ? true : false }"
																id="amountRelease${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																
																<td><form:input
																path="dto.managementCostDetailDto[${d}].cbboCostDisbursed" disabled="${command.viewMode eq 'V' ? true : false }"
																id="cbboCostDisbursed${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																
																<td>

															<div class="input-group">
																<form:input
																	path="dto.managementCostDetailDto[${d}].cbboDateOfRelease"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="cbboDateOfRelease${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>


														</td>
																
																<td><form:input
																path="dto.managementCostDetailDto[${d}].cbbodAmountRelease" disabled="${command.viewMode eq 'V' ? true : false }"
																id="cbbodAmountRelease${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>
																
															


													<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
														class="btn btn-blue-2 btn-sm addManagementCostButton" 
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="addManagementCostButton(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deleteManagementCostDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deleteManagementCostDetails(this);"> <i
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
						title='<spring:message code="sfac.savencontinue" text="Save & Continue" />'
						onclick="saveManagementInfoForm(this);">
						<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
					<button type="button" class="btn btn-danger"
					onclick="navigateTab('farmerSummary-tab','farmerSummaryInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>