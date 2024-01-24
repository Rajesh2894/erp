<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<!-- <script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script type="text/javascript" src="js/common/fieldConfiguration.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>Field Configuration Master</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="fieldConfiguration.html" method="post"
				class="form-horizontal" name="fieldConfiguration"
				id="fieldConfiguration">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<c:choose>
					<c:when test="${command.flag eq 'V' }">
						<h4 class="margin-top-0">
							<spring:message code="" text="Page Info" />
						</h4>

						<div class="form-group">

							<label for="text-1" class="col-sm-2 control-label"><spring:message
									code="" text="Select page" /> </label>
							<div class="col-sm-4">

								<form:select path="resourceMasDto.pageId"
									class="form-control mandColorClass chosen-select-no-results"
									label="Select" id="resId" onchange="getPageInfo()">
									<form:option value="0">Select</form:option>
									<c:forEach items="${command.masDtos}" var="data">
										<form:option value="${data.resId}" path="resourceMasDto.resId">${data.pageId}</form:option>
									</c:forEach>

								</form:select>
							</div>
						</div>

						<div class="table-responsive margin-center-0 col-sm-6">

							<c:set var="d" value="0"></c:set>
							<table class="table table-striped table-condensed table-bordered"
								id="fieldTable">
								<thead>
									<tr>
										<th class="col-sm-2" style="text-align: left"><spring:message
												code="" text="Field Name"></spring:message></th>
										<th class="col-sm-2" style="text-align: left"><spring:message
												code="" text="Mandatory"></spring:message></th>
										<th class="col-sm-2" style="text-align: left"><spring:message
												code="" text="Visible"></spring:message></th>
									</tr>
								</thead>

								<tbody>
									<c:set var="d" value="0" />
									<c:forEach items="${command.resourceMasDto.fieldDetails}"
										var="data" varStatus="index">

										<tr>
											<td><form:input type="text" class="form-control"
													path="resourceMasDto.fieldDetails[${d}].fieldId"
													id="fieldId" readonly="true"></form:input></td>

											<td><form:select
													path="resourceMasDto.fieldDetails[${d}].isMandatory"
													class="form-control" id="isMandatory">
													<form:option value="0">Select</form:option>
													<form:option value="Y">Yes</form:option>
													<form:option value="N">No</form:option>
												</form:select></td>

											<td><form:select
													path="resourceMasDto.fieldDetails[${d}].isVisible"
													class="form-control" id="isVisible">
													<form:option value="0">Select</form:option>
													<form:option value="Y">Yes</form:option>
													<form:option value="N">No</form:option>
												</form:select></td>
										</tr>
										<c:set var="d" value="${d + 1}" />
									</c:forEach>
								</tbody>

							</table>

						</div>

						<div class="text-center clear padding-10">

							<button type="button" class="btn btn-danger"
								onclick="editPageFields('fieldConfiguration.html','searchResourceMasData')">
								<spring:message code="master.editSelected" />
							</button>
						</div>
					</c:when>
					<c:otherwise>
						<h4 class="margin-top-0">
							<spring:message code="" text="Field Info" />
						</h4>

						<div class="table-responsive margin-left-0 col-sm-6">

							<c:set var="d" value="0"></c:set>
							<table class="table table-striped table-condensed table-bordered"
								id="fieldTable">
								<thead>
									<tr>
										<th class="col-sm-2" style="text-align: left"><spring:message
												code="" text="Field Name"></spring:message></th>
										<th class="col-sm-2" style="text-align: left"><spring:message
												code="" text="Mandatory"></spring:message></th>
										<th class="col-sm-2" style="text-align: left"><spring:message
												code="" text="Visible"></spring:message></th>
									</tr>
								</thead>

								<tbody>
									<c:set var="d" value="0" />
									<c:forEach items="${command.resourceMasDto.fieldDetails}"
										var="data" varStatus="index">

										<tr>
											<td><form:input type="text" class="form-control"
													path="resourceMasDto.fieldDetails[${d}].fieldId"
													id="fieldId" readonly="true"></form:input></td>

											<td><form:select
													path="resourceMasDto.fieldDetails[${d}].isMandatory"
													class="form-control" id="isMandatory">
													<form:option value="0">Select</form:option>
													<form:option value="Y">Yes</form:option>
													<form:option value="N">No</form:option>
												</form:select></td>

											<td><form:select
													path="resourceMasDto.fieldDetails[${d}].isVisible"
													class="form-control" id="isVisible">
													<form:option value="0">Select</form:option>
													<form:option value="Y">Yes</form:option>
													<form:option value="N">No</form:option>
												</form:select></td>
										</tr>
										<c:set var="d" value="${d + 1}" />
									</c:forEach>
								</tbody>

							</table>

						</div>

						<div class="text-center clear padding-10">

							<button class="btn btn-success  submit"
								onclick="saveFieldConfiguration(this)" id="Submit" type="button"
								name="button" value="save">
								<i class="button-input"></i>
								<spring:message code="common.sequenceconfig.save" />
							</button>

							<button type="back" class="btn btn-danger" onclick="backForm()">
								<spring:message code="common.sequenceconfig.back" />
							</button>

						</div>
					</c:otherwise>

				</c:choose>
			</form:form>

		</div>
	</div>
</div>
