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
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />

<!-- Start Content here -->

<div class="content animated top">
	<div class="widget">
		

		<div class="widget-content padding">
			<form:form id="fpoPMPreharveshInfo"
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
									data-parent="#accordion_single_collapse" href="#preInfoDiv">
									<spring:message code="sfac.fpo.pm.preHarvesh"
										text="Pre harvesh Infrastructure" />
								</a>
							</h4>
						</div>
						<div id="preInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="preInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
										

											<th><spring:message code="sfac.fpo.pm.storageType"
													text="Storage Type" /></th>
											<th><spring:message code="sfac.fpo.pm.StorageCapicity"
													text="Storage Capicity in Volume" /></th>
													
														<th><spring:message code="sfac.fpo.pm.phpEuqp"
													text="PHP Equipment's Description" /></th>
											
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.preHarveshInfraInfoDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.preHarveshInfraInfoDTOs}"
													varStatus="status">
													<tr class="appendablePreharveshDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.preHarveshInfraInfoDTOs[${d}].storageType" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageType${d}" class="form-control " /></td>
														<td><form:input
																path="dto.preHarveshInfraInfoDTOs[${d}].storageCapicity" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageCapicity${d}" class="form-control hasNumber" /></td>
																	<td><form:input
																path="dto.preHarveshInfraInfoDTOs[${d}].phpDescription" disabled="${command.viewMode eq 'V' ? true : false }"
																id="phpDescription${d}" class="form-control " /></td>
																
																


													<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
														class="btn btn-blue-2 btn-sm addPreharveshButton" 
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="addPreharveshButton(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deletePreharveshDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deletePreharveshDetails(this);"> <i
															class="fa fa-trash"></i>
													</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendablePreharveshDetails">
																											<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNopre${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.preHarveshInfraInfoDTOs[${d}].storageType" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageType${d}" class="form-control " /></td>
														<td><form:input
																path="dto.preHarveshInfraInfoDTOs[${d}].storageCapicity" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageCapicity${d}" class="form-control hasNumber" /></td>
																	<td><form:input
																path="dto.preHarveshInfraInfoDTOs[${d}].phpDescription" disabled="${command.viewMode eq 'V' ? true : false }"
																id="phpDescription${d}" class="form-control " /></td>


													<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
														class="btn btn-blue-2 btn-sm addPreharveshButton"
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="addPreharveshButton(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deletePreharveshDetails '
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deletePreharveshDetails(this);"> <i
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
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.submit" text="Submit" />'
						onclick="savePreharveshInfoForm(this);">
						<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					<button type="button" class="btn btn-warning"
						title='<spring:message code="sfac.button.reset" text="Reset"/>'
						onclick="window.location.href ='FPOProfileManagementForm.html'">
						<spring:message code="sfac.button.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>