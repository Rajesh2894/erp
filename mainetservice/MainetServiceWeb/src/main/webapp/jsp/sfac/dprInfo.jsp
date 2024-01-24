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
			<form:form id="fpoPMDPRInfo"
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
									data-parent="#accordion_single_collapse" href="#dprInfoDiv">
									<spring:message code="sfac.fpo.pm.dpr"
										text="DPR Details" />
								</a>
							</h4>
						</div>
						<div id="dprInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="dprInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
										

											<th><spring:message code="sfac.fpo.pm.dprRecDt"
													text="DPR Receive Date" /></th>
											<th><spring:message code="sfac.fpo.pm.dprRev"
													text="DPR Reviewer" /></th>
													
														<th><spring:message code="sfac.fpo.pm.dprScore"
													text="DPR Score" /></th>
													
														<th><spring:message code="sfac.fpo.pm.dprSubDt"
													text="DPR Submit Date" /></th>
														
											
										<%-- 	<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th> --%>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.dprInfoDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.dprInfoDTOs}"
													varStatus="status">
													<tr class="appendableDPRDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td>

															<div class="input-group">
																<form:input
																	path="dto.dprInfoDTOs[${d}].dprRecDt"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="dprRecDt${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="true" />
																</div>


														</td>		
														<td><form:input
																path="dto.dprInfoDTOs[${d}].dprReviewer" disabled="true"
																id="dprReviewer${d}" class="form-control " /></td>
														<td><form:input
																path="dto.dprInfoDTOs[${d}].dprScore" disabled="true"
																id="dprScore${d}" class="form-control " /></td>
																	
																
																


														<td>

															<div class="input-group">
																<form:input
																	path="dto.dprInfoDTOs[${d}].dprRevSubmDt"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="dprRevSubmDt${d}" placeholder="dd/mm/yyyy"
																	readonly="true" disabled="true" />
																</div>


														</td>
														<%-- <td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addDPRButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addDPRButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteDPRDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteDPRDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td> --%>


													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableDPRDetails">
																											<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td>

																<div class="input-group">
																<form:input
																	path="dto.dprInfoDTOs[${d}].dprRecDt"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="dprRecDt${d}" placeholder="dd/mm/yyyy"
																	readonly="true"  disabled="true" />
																<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span>
																		</div>


														</td>		
														<td><form:input
																path="dto.dprInfoDTOs[${d}].dprReviewer"  disabled="true" 
																id="dprReviewer${d}" class="form-control " /></td>
														<td><form:input
																path="dto.dprInfoDTOs[${d}].dprScore"  disabled="true" 
																id="dprScore${d}" class="form-control " /></td>
																	
																
																


														<td>

															<div class="input-group">
																<form:input
																	path="dto.dprInfoDTOs[${d}].dprRevSubmDt"
																	type="text"
																	class="form-control datepicker mandColorClass"
																	id="dprRevSubmDt${d}" placeholder="dd/mm/yyyy"
																	readonly="true"  disabled="true" />
																<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span>
															</div>

														</td>

														<%-- <td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addDPRButton"
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addDPRButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteDPRDetails '
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteDPRDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td> --%>

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
						onclick="saveDPRInfoForm(this);">
					<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
				<button type="button" class="btn btn-danger"
				onclick="navigateTab('abs-tab','absInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>