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
			<form:form id="fpoPMPostharvestInfo"
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
									data-parent="#accordion_single_collapse" href="#postInfoDiv">
									<spring:message code="sfac.fpo.pm.postHarvest"
										text="Post harvest Infrastructure" />
								</a>
							</h4>
						</div>
						<div id="postInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="postInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
										

											<th><spring:message code="sfac.fpo.pm.storageType"
													text="Storage Type" /></th>
											<th><spring:message code="sfac.fpo.pm.StorageCapicity"
													text="Storage Capicity in Volume" /></th>
											<th><spring:message code="sfac.fpo.pm.processing"
													text="Processing" /></th>
													
											<th><spring:message code="sfac.fpo.pm.qa"
													text="Quality Analysis" /></th>				
													
														<th><spring:message code="sfac.fpo.pm.phpEuqp"
													text="PHP Equipment's Description" /></th>
											
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.postHarvestInfraInfoDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.postHarvestInfraInfoDTOs}"
													varStatus="status">
													<tr class="appendablePostharvestDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].storageType" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageTypePost${d}" class="form-control alphaNumeric"  maxlength="100"/></td>
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].storageCapicity" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageCapicityPost${d}" class="form-control hasNumber" maxlength="5" /></td>
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].processing" disabled="${command.viewMode eq 'V' ? true : false }"
																id="processing${d}" class="form-control alphaNumeric" maxlength="500" /></td>
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].qualityAnalysis" disabled="${command.viewMode eq 'V' ? true : false }"
																id="qualityAnalysis${d}" class="form-control alphaNumeric" maxlength="500"/></td>					
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].phpDescription" disabled="${command.viewMode eq 'V' ? true : false }"
																id="phpDescriptionPost${d}" class="form-control alphaNumeric" maxlength="500" /></td>
																
																
																


														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addPostharvestButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addPostharvestButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deletePostharvestDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deletePostharvestDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendablePostharvestDetails">
																											<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].storageType" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageTypePost${d}" class="form-control alphaNumeric" maxlength="100"/></td>
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].storageCapicity" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageCapicityPost${d}" class="form-control hasNumber " maxlength="5"/></td>
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].processing" disabled="${command.viewMode eq 'V' ? true : false }"
																id="processing${d}" class="form-control alphaNumeric" maxlength="500" /></td>
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].qualityAnalysis" disabled="${command.viewMode eq 'V' ? true : false }"
																id="qualityAnalysis${d}" class="form-control alphaNumeric" maxlength="500" /></td>	
														<td><form:input
																path="dto.postHarvestInfraInfoDTOs[${d}].phpDescription" disabled="${command.viewMode eq 'V' ? true : false }"
																id="phpDescriptionPost${d}" class="form-control alphaNumeric" maxlength="500"/></td>


														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addPostharvestButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addPostharvestButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deletePostharvestDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deletePostharvestDetails(this);"> <i
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
						onclick="savePostharvestInfoForm(this);">
					<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
				<button type="button" class="btn btn-danger"
				onclick="navigateTab('subsidies-tab','subsidiesInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>