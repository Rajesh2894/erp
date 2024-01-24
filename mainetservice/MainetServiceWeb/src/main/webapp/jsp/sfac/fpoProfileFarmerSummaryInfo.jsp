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
			<form:form id="fpoPMFarmerSummaryInfo"
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
									data-parent="#accordion_single_collapse" href="#FarmerSummaryInfoDiv">
									<spring:message code="sfac.fpo.pm.farmerSummary"
										text="Farmer Summary" />
								</a>
							</h4>
						</div>
						<div id="FarmerSummaryInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="FarmerSummaryInfoTable">
									<thead>
										<tr>
											<th ><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th width="13%"><spring:message code="sfac.fpo.pm.doe"
													text="Date Of Entry" /></th>

											<th><spring:message code="sfac.fpo.pm.nosfsh"
													text="No Of Small Farmers as shareholders" /></th>
											<th><spring:message code="sfac.fpo.pm.nomfsh"
													text="No Of Marginal farmers as shareholders" /></th>
											<th><spring:message code="sfac.fpo.pm.nolfsh"
													text="No of Landless shareholder farmers" /></th>
											<th><spring:message code="sfac.fpo.pm.notf"
													text="No Of Tenant Farmers" /></th>
													
											
											<th><spring:message code="sfac.fpo.pm.nowsh"
													text="No. of women shareholders" /></th>
											<th><spring:message code="sfac.fpo.pm.noscsh"
													text="No of Scheduled Caste (SC) shareholders" /></th>
											<th><spring:message code="sfac.fpo.pm.nostsh"
													text="No of Scheduled Caste (SC) shareholders" /></th>	
											<th><spring:message code="sfac.fpo.pm.totalSH"
													text="Total shareholders " /></th>			
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.farmerSummaryDto)>0 }">
												<c:forEach var="dto"
													items="${command.dto.farmerSummaryDto}"
													varStatus="status">
													<tr class="appendableBankDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>
														<td>
															
																<div class="input-group" widht="10%">
																	<form:input
																		path="dto.farmerSummaryDto[${d}].dateOfEntry"
																		type="text"
																		class="form-control datepicker mandColorClass"
																		id="dateOfEntry${d}" placeholder="dd/mm/yyyy"
																		readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																	<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span>
																</div>
														

														</td>

														
																
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFSmallFarmerSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFSmallFarmerSH${d}" class="form-control hasNumber" maxlength="3" /></td>
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFMarginalFarmerSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFMarginalFarmerSH${d}" class="form-control hasNumber "  maxlength="3"/></td>
																
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFLandlessSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFLandlessSH${d}" class="form-control hasNumber" maxlength="3" /></td>
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFTenantFarmer" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFTenantFarmer${d}" class="form-control hasNumber " maxlength="3"/></td>
																
															
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFWomenSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFWomenSH${d}" class="form-control hasNumber " maxlength="3"/></td>
																
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFSCSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFSCSH${d}" class="form-control hasNumber" maxlength="3"/></td>
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFSTSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFSTSH${d}" class="form-control hasNumber " maxlength="3"/></td>
																
																	<td><form:input
																path="dto.farmerSummaryDto[${d}].totalSharehold" disabled="${command.viewMode eq 'V' ? true : false }"
																id="totalSharehold${d}" class="form-control hasNumber" maxlength="3"/></td>
													

														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addFarmerSummaryButton" 
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addFarmerSummaryButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteFarmerSummaryDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteFarmerSummaryDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableBankDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" /></td>
														<td>
															
																<div class="input-group">
																	<form:input
																		path="dto.farmerSummaryDto[${d}].dateOfEntry"
																		type="text"
																		class="form-control datepicker mandColorClass"
																		id="dateOfEntry${d}" placeholder="dd/mm/yyyy"
																		readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																	<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span>
																</div>
														

														</td>

														
																
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFSmallFarmerSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFSmallFarmerSH${d}" class="form-control hasNumber" maxlength="3"/></td>
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFMarginalFarmerSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFMarginalFarmerSH${d}" class="form-control hasNumber " maxlength="3"/></td>
																
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFLandlessSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFLandlessSH${d}" class="form-control hasNumber" maxlength="3" /></td>
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFTenantFarmer" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFTenantFarmer${d}" class="form-control hasNumber " maxlength="3" /></td>
																
																
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFWomenSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFWomenSH${d}" class="form-control hasNumber " maxlength="3"/></td>
																
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFSCSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFSCSH${d}" class="form-control hasNumber" maxlength="3"/></td>
																<td><form:input
																path="dto.farmerSummaryDto[${d}].noOFSTSH" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFSTSH${d}" class="form-control hasNumber " maxlength="3" /></td>

																<td><form:input
																path="dto.farmerSummaryDto[${d}].totalSharehold" disabled="${command.viewMode eq 'V' ? true : false }"
																id="totalSharehold${d}" class="form-control hasNumber" maxlength="3"/></td>


														
														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addFarmerSummaryButton" 
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addFarmerSummaryButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteFarmerSummaryDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteFarmerSummaryDetails(this);"> <i
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
						onclick="saveFPOPMFarmerSummaryForm(this);">
					<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
					<button type="button" class="btn btn-danger" onclick="navigateTab('equity-tab','equityInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>