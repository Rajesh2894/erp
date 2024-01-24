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
			<form:form id="fpoPMTrainingInfo"
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
									data-parent="#accordion_single_collapse" href="#TrainingInfoDiv">
									<spring:message code="sfac.fpo.pm.trainingDet"
										text="Training Details" />
								</a>
							</h4>
						</div>
						<div id="TrainingInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="TrainingInfoTable">
									<thead>
										<tr>
											<th><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th width="13%" width="12%"><spring:message code="sfac.fpo.pm.dot"
													text="Date Of training " /></th>
											<th width="13%" width="12%"><spring:message code="sfac.fpo.pm.trainingName"
													text="Training Name" /></th>
											<th><spring:message code="sfac.fpo.pm.notfceo"
													text="Number of Training conducted for CEO" /></th>
										<%-- 	<th><spring:message code="sfac.fpo.pm.nobodit"
													text="Number of BoDs Involved In Training" /></th> --%>
											<th><spring:message code="sfac.fpo.pm.notcbod"
													text="Number of Training conducted for BODs" /></th>
											<th><spring:message code="sfac.fpo.pm.notfa"
													text="No of Training conducted for Accountants" /></th>
													<th><spring:message code="sfac.fpo.pm.notcff"
													text="No of Training Completed for FPO Farmers" /></th>
											<th><spring:message code="sfac.fpo.pm.noshit"
													text="No of Shareholder farmers involved in training" /></th>
												
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.fpoProfileTrainingDetailDto)>0 }">
												<c:forEach var="dto"
													items="${command.dto.fpoProfileTrainingDetailDto}"
													varStatus="status">
													<tr class="appendableTrainingInfoDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" /></td>
														

													<td>

															<div class="input-group">
																<form:input
																	path="dto.fpoProfileTrainingDetailDto[${d}].dateOfTraining"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="dateOfTraining${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>


														</td>
														<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].trainingName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="trainingName${d}" class="form-control alphaNumeric" maxlength="100"/></td>
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFCBO" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFCBO${d}" class="form-control hasNumber" maxlength="3"/></td>
																
																<%-- <td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFBODSInvolved" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFBODSInvolved${d}" class="form-control hasNumber " maxlength="3"/></td> --%>
																
																
																
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFBODSConduct" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFBODSConduct${d}" class="form-control hasNumber" maxlength="3"/></td>
																
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFAccountantTraningConduct" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFAccountantTraningConduct${d}" class="form-control hasNumber" maxlength="3"/></td>
																
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFTrainingCompleteFPO" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFTrainingCompleteFPO${d}" class="form-control hasNumber" maxlength="3"/></td>
																
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFSHFTraining" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFSHFTraining${d}" class="form-control hasNumber" maxlength="3"/></td>
																
																
																
															
													

														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addTrainingInfoButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addTrainingInfoButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteTrainingInfoDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteTrainingInfoDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableTrainingInfoDetails">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sNo${d}"
															value="${d+1}" disabled="true" /></td>
														
															<td>

															<div class="input-group">
																<form:input
																	path="dto.fpoProfileTrainingDetailDto[${d}].dateOfTraining"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="dateOfTraining${d}" placeholder="dd/mm/yyyy"
																	readonly="true"  disabled="${command.viewMode eq 'V' ? true : false }"/>
																<span class="input-group-addon"><i
																	class="fa fa-calendar"></i></span>
															</div>


														</td>
														<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].trainingName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="trainingName${d}" class="form-control alphaNumeric" maxlength="100"/></td>
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFCBO" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFCBO${d}" class="form-control hasNumber" maxlength="3"/></td>
																
																<%-- <td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFBODSInvolved" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFBODSInvolved${d}" class="form-control hasNumber" maxlength="3"/></td> --%>
																
																
																
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFBODSConduct" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFBODSConduct${d}" class="form-control hasNumber " maxlength="3"/></td>
																
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFAccountantTraningConduct" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFAccountantTraningConduct${d}" class="form-control hasNumber" maxlength="3"/></td>
																
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFTrainingCompleteFPO" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFTrainingCompleteFPO${d}" class="form-control hasNumber" maxlength="3"/></td>
																
																<td><form:input
																path="dto.fpoProfileTrainingDetailDto[${d}].noOFSHFTraining" disabled="${command.viewMode eq 'V' ? true : false }"
																id="noOFSHFTraining${d}" class="form-control hasNumber" maxlength="3"/></td>
																
																

													<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addTrainingInfoButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addTrainingInfoButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteTrainingInfoDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteTrainingInfoDetails(this);"> <i
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
						title='<spring:message code="sfac.button.save" text="Save" />'
						onclick="saveFPOPMTraningInfoForm(this);">
						<spring:message code="sfac.button.save" text="Save" />
					</button>
					</c:if>
					<button type="button" class="btn btn-danger"
						onclick="navigateTab('dpr-tab','dprInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>