<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/trade_license/inspectionDetailEntry.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="inspection.notice" text="Notice" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>


		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="InspectionDetailForm.html" class="form-horizontal"
				id="notice" name="Notice">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="inscpection.license.no" text="License No" /></label>
					<div class="col-sm-4">
						<form:input path="inspectionDetailDto.licNo" id="licNo"
							class="form-control mandColorClass" disabled="true" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="" text="Inspection Number" /></label>
					<div class="col-sm-4">
						<form:input path="inspectionDetailDto.inspNo" id="inspNo"
							class="form-control mandColorClass" disabled="true" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="owner.details.mobileNo" text="Mobile No" /></label>
					<div class="col-sm-4">
						<form:input path="inspectionDetailDto.mobNo" id="mobNo"
							class="form-control unit required-control hasMobileNo preventSpace" />
					</div>


				</div>



				<div class="panel-group accordion-toggle padding-bottom-10"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<h4 class="panel-title table">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="" text="Notice Reason" /></a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="">
								<c:set var="d" value="0" scope="page"></c:set>
								<table id="noticeReasonTable" summary="Reason Data"
									class="table table-bordered table-striped">
									<thead>
										<tr>
											<th width="10"><spring:message code="lgl.srno"
													text="Sr. No." /></th>
											<th width="200"><spring:message code="trd.notice.reason"
													text="Reason" /></th>
											<th width="10"><spring:message code="trade.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${not empty command.noticeDetailDtoList}">
												<c:forEach items="${command.noticeDetailDtoList}" var="data"
													varStatus="index">
													<tr class="appendableClass">
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sequence${d}"
																value="${d+1}" disabled="true" /></td>

														<td><form:input
																path="noticeDetailDtoList[${d}].reason"
																cssClass="form-control mandColorClass required-control hasCharacter"
																id="reason${d}" /></td>

														<td align="center"><a href="#a3"
															data-toggle="tooltip" data-placement="top"
															class="btn btn-danger btn-sm"
															data-original-title="Delete"
															onclick="deleteEntry($(this),'removedIds');"> <strong
																class="fa fa-minus"></strong> <span class="hide"><spring:message
																		code="lgl.delete" text="Delete" /></span>
														</a></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableClass">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass" id="sequence${d}"
															value="${d+1}" disabled="true" /></td>


													<td><form:input
															path="noticeDetailDtoList[${d}].reason"
															cssClass="form-control mandColorClass required-control hasCharacter"
															id="reason${d}" /></td>


													<td align="center"><a href="#a2" data-toggle="tooltip"
														data-placement="top" class="btn btn-blue-2  btn-sm"
														data-original-title="Add" onclick="addEntryData();"><strong
															class="fa fa-plus"></strong><span class="hide"></span></a> <a
														href="#a3" data-toggle="tooltip" data-placement="top"
														class="btn btn-danger btn-sm" data-original-title="Delete"
														onclick="deleteEntry($(this),'removedIds');"><strong
															class="fa fa-minus"></strong> <span class="hide"><spring:message
																	code="lgl.delete" text="Delete" /></span> </a></td>
												</tr>
											</c:otherwise>
										</c:choose>
										<c:set var="d" value="${d+1}" scope="page" />
									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>


				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="submitNotice(this)">
						<spring:message code="obj.submit" text="Submit"></spring:message>
					</button>
					<apptags:backButton url="InspectionDetailForm.html"></apptags:backButton>
				</div>

			</form:form>
		</div>


	</div>
</div>
