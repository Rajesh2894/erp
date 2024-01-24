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
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/works_management/bidMaster.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2>
				<spring:message code="works.technical.details" text="Technical Details" />
			</h2>
			<apptags:helpDoc url="BIDMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="BIDMaster.html" class="form-horizontal"
				id="tenderInitiation">
				<form:hidden path="serviceFlag" id="serviceFlag" />
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="saveMode" id="saveMode" />

				<div class="form-group">

					<label for="text-1" class="col-sm-2 control-label"><spring:message
							code="works.bidderName" text="Bidder Name" /> </label>
					<div class="col-sm-4">
						<form:input cssClass="form-control " readonly="true"
							path="bidMasterDto.bidIdDesc" onkeypress="" id=""/>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 type="h4" class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a3"> <spring:message
									code="works.technical.details" text="Technical Details" />
							</a>
						</h4>
					</div>
					<div id="a3" class="panel-collapse collapse in">
						<div class="panel-body padding-top-0">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-striped table-condensed table-bordered"
								id="technicalevaluation">

								<thead>
									<tr>
										<th width="8%"><spring:message code="ser.no" text="Sr. No." />
											<i class="text-red-1"></i></th>
										<th width="15%"><spring:message code="works.evcriteria"
												text="Evaluation Criteria" /> <i class="text-red-1">*</i></th>
										<th width="15%"><spring:message code="works.criteria.qualified"
												text="Criteria Qualified" /> <i class="text-red-1">*</i></th>
										<th width="17%"><spring:message code="works.accepted.rejected"
												text="Accepted/Rejected" /> <i class="text-red-1">*</i></th>
										<c:if
											test="${fn:length(command.bidMasterDto.technicalBIDDetailDtos) eq 0 || command.saveMode eq 'E'}">
											<th width="20%"><spring:message code="works.management.action" text="Action" /></th>
										</c:if>

									</tr>
								</thead>
								<tbody>

									<c:choose>
										<c:when
											test="${fn:length(command.bidMasterDto.technicalBIDDetailDtos)>0}">
										
											<c:forEach
												items="${command.bidMasterDto.technicalBIDDetailDtos}"
												varStatus="status" var="dto">
												<tr class="techClass">
													<td><form:input path="" id="sNo${d}" value="${d + 1}"
															readonly="true" cssClass="form-control " /></td>
													<td><form:input
															path="bidMasterDto.technicalBIDDetailDtos[${d}].evaluation"
															type="text" class="form-control " maxLength="100"
															 id="evaluationc${d}"  disabled="${command.saveMode eq 'V' ||command.saveMode ne 'E'}"
															/></td>
													<td><form:select
															path="bidMasterDto.technicalBIDDetailDtos[${d}].criteria"
															 class="form-control" id="criteria${d}"
															disabled="${command.saveMode eq 'V' ||command.saveMode ne 'E'}">
															<form:option value="">
																<spring:message code="master.selectDropDwn" />
															</form:option>
															<form:option value="Y">Yes</form:option>
															<form:option value="N">No</form:option>
														</form:select></td>
													<td><form:select
															path="bidMasterDto.technicalBIDDetailDtos[${d}].acceptreject"
															 class="form-control"
															id="acceptreject${d}"
															disabled="${command.saveMode eq 'V' ||command.saveMode ne 'E'}">
															<form:option value="">
																<spring:message code="master.selectDropDwn" />
															</form:option>
															<form:option value="A">Accept</form:option>
															<form:option value="R">Reject</form:option>
														</form:select></td>
														
												<c:if test="${command.saveMode eq 'E'}">
												<td class="text-center"><a href="javascript:void(0);" title="<spring:message
												code="works.management.add" text="Add" />"
													class="btn btn-success addtable btn-sm"
													onclick="addtechnicalData();"> <i
														class="fa fa-plus-circle" id=""></i></a> <a
													href="javascript:void(0);" title="<spring:message
												code="works.management.delete"  text="Delete" />"
													class="btn btn-danger btn-sm delButton"
													onclick="deletetechnical($(this),'removedIds');"><i
														class="fa fa-minus" id=""></i></a></td>
														</c:if>
														
														

													<!-- <td class="text-center"><a href="javascript:void(0);"
														class="btn btn-success addtable btn-sm"
														onclick="addtechnicalData();"> <i
															class="fa fa-plus-circle" id=""></i></a> <a
														href="javascript:void(0);"
														class="btn btn-danger btn-sm delButton"
														onclick="deletetechnical($(this),'removedIds');"><i
															class="fa fa-minus" id=""></i></a></td> -->

												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</c:when>
										<c:otherwise>

											<tr class="techClass">
												<td><form:input path="" id="sNo${d}" value="${d + 1}"
														readonly="true" cssClass="form-control " /></td>
												<td><form:input
														path="bidMasterDto.technicalBIDDetailDtos[${d}].evaluation"
														type="text" class="form-control " maxLength="100"
														id="evaluationc${d}" 
														isDisabled="${command.saveMode eq 'V' ||command.saveMode ne 'E'}"/></td>
												<td><form:select
														path="bidMasterDto.technicalBIDDetailDtos[${d}].criteria"
														class="form-control" id="criteria${d}"
														isDisabled="${command.saveMode eq 'V' ? true : false }">
														<form:option value="">
															<spring:message code="master.selectDropDwn" />
														</form:option>
														<form:option value="Y">Yes</form:option>
														<form:option value="N">No</form:option>
													</form:select></td>
												<td><form:select
														path="bidMasterDto.technicalBIDDetailDtos[${d}].acceptreject"
														class="form-control" id="acceptreject${d}"
														isDisabled="${command.saveMode eq 'V' ? true : false }">
														<form:option value="">
															<spring:message code="master.selectDropDwn" />
														</form:option>
														<form:option value="A">Accept</form:option>
														<form:option value="R">Reject</form:option>
													</form:select></td>
												<c:if test="${command.saveMode ne 'V'}">
												<td class="text-center"><a href="javascript:void(0);" title="<spring:message
												code="works.management.add" text="Add" />"
													class="btn btn-success addtable btn-sm"
													onclick="addtechnicalData();"> <i
														class="fa fa-plus-circle" id=""></i></a> <a
													href="javascript:void(0);" title="<spring:message
												code="works.management.delete"  text="Delete" />"
													class="btn btn-danger btn-sm delButton"
													onclick="deletetechnical($(this),'removedIds');"><i
														class="fa fa-minus" id=""></i></a></td>
														</c:if>

											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />

										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center margin-bottom-10">
					<c:if test="${command.saveMode ne 'V'}">
					<button type="button" class="btn btn-success" 
						onclick="saveTechForm(this)"
						title="<spring:message code="works.management.save" text="Save"></spring:message>">
						<i class=" padding-right-5" aria-hidden="true"></i>
						<spring:message code="works.management.save" text="Save"></spring:message>
					</button>
					</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style="" onclick="resetForm()"
						id="button-Cancel"
						title="<spring:message code="works.management.back" text="Back"></spring:message>"
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
