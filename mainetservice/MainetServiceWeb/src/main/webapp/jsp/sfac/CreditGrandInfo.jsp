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
			<form:form id="fpoPMCreditGrantInfo"
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
									data-parent="#accordion_single_collapse" href="#CreditGrantDiv">
									<spring:message code="sfac.fpo.pm.creditGrant"
										text="Credit Grant Detail" />
								</a>
							</h4>
						</div>
						<div id="CreditGrantDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="CreditGrantTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th><spring:message code="sfac.fpo.pm.docgf"
													text="Date of CGF availed" /></th>

											<th width="11%"><spring:message code="sfac.fpo.pm.cgfAvailed"
													text="CGF Availed" /></th>
											<th><spring:message code="sfac.fpo.pm.amtCGC"
													text="Amount of Credit Guarantee Coverage" /></th>
											<th><spring:message code="sfac.fpo.pm.actCGF"
													text="Activities for which CGF Utilised " /></th>
											<th><spring:message code="sfac.fpo.pm.totalCGF"
													text="Total Guarantee Coverage under CGF (INR)" /></th>


											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.creditGrantDetailDto)>0 }">
												<c:forEach var="dto"
													items="${command.dto.creditGrantDetailDto}"
													varStatus="status">
													<tr class="appendableCreditGrandDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>



														<td>
															<div class="input-group">
																<form:input
																	path="dto.creditGrantDetailDto[${d}].dateOfCGF"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="dateOfCGF${d}" placeholder="dd/mm/yyyy" readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>


														</td>
													<td><c:set var="baseLookupCode" value="YNC" /> <form:select
																path="dto.creditGrantDetailDto[${d}].cgfAvailed" disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control chosen-select-no-results" id="cgfAvailed${d}">
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
																path="dto.creditGrantDetailDto[${d}].amountOfCGF" disabled="${command.viewMode eq 'V' ? true : false }"
																id="amountOfCGF${d}" class="form-control" onkeypress="return hasAmount(event, this, 10, 2)"/></td>



														<td><form:input
																path="dto.creditGrantDetailDto[${d}].actCGF" disabled="${command.viewMode eq 'V' ? true : false }"
																id="actCGF${d}" class="form-control alphaNumeric"  maxlength="200"/></td>

														<td><form:input
																path="dto.creditGrantDetailDto[${d}].totalCovrageCGF" disabled="${command.viewMode eq 'V' ? true : false }"
																id="totalCovrageCGF${d}" class="form-control" onkeypress="return hasAmount(event, this, 10, 2)" /></td>



														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addCreditGrandButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addCreditGrandButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteCreditGrandDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteCreditGrandDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableCreditGrandDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" /></td>

													<td>
														<div class="input-group">
															<form:input
																path="dto.creditGrantDetailDto[${d}].dateOfCGF"
																type="text"
																class="form-control datepicker mandColorClass"
																id="dateOfCGF${d}" placeholder="dd/mm/yyyy" readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
															<span class="input-group-addon"><i
																class="fa fa-calendar"></i></span>
														</div>


													</td>
													<td><c:set var="baseLookupCode" value="YNC" /> <form:select
																path="dto.creditGrantDetailDto[${d}].cgfAvailed" disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control chosen-select-no-results" id="cgfAvailed${d}">
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
															path="dto.creditGrantDetailDto[${d}].amountOfCGF" disabled="${command.viewMode eq 'V' ? true : false }"
															id="amountOfCGF${d}" class="form-control " onkeypress="return hasAmount(event, this, 10, 2)" /></td>


													<td><form:input
															path="dto.creditGrantDetailDto[${d}].actCGF" disabled="${command.viewMode eq 'V' ? true : false }"
															id="actCGF${d}" class="form-control alphaNumeric"  maxlength="200"/></td>

													<td><form:input
															path="dto.creditGrantDetailDto[${d}].totalCovrageCGF" disabled="${command.viewMode eq 'V' ? true : false }"
															id="totalCovrageCGF${d}" class="form-control" onkeypress="return hasAmount(event, this, 10, 2)" /></td>




												<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addCreditGrandButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addCreditGrandButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteCreditGrandDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteCreditGrandDetails(this);"> <i
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
						onclick="saveCreditGrandForm(this);">
					<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
					<button type="button" class="btn btn-danger"
					onclick="navigateTab('mgmt-tab','mgmtInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>