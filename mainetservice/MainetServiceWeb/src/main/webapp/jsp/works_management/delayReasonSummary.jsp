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
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script type="text/javascript" src="js/works_management/delayReason.js"></script>




<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code='wms.delayReason.header.label.details.delay.reason' text='Details Of Delay Reason' /></h2>
		</div>
		<div class="widget-content padding">
			<form:form action="DelayReason.html" method="post"
				class="form-horizontal" name="delayReason" id="delayReason">

				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- ----------------- this div below is used to display the error message on the top of the page--------------------------->
           
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path="delayReasonDto.projId" id="projId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							onchange="getCreateWorkName(this);">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.projectMasterList}"
								var="activeProjName">
								<form:option value="${activeProjName.projId }"
									code="${activeProjName.workId }">${activeProjName.projNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
                   
					<label class="col-sm-2 control-label required-control"><spring:message
							code="work.def.workname" /></label>
					<div class="col-sm-4">
						<form:select path="delayReasonDto.workId" id="workId"
							cssClass="form-control chosen-select-no-results mandColorClass">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
								<c:forEach items="${command.workDefinitionDtoList}"
								var="activeProjName">
								<form:option value="${activeProjName.workId }">${activeProjName.workName}</form:option>
							</c:forEach>
						</form:select>
					</div>


				</div>





				<div class="form-group">
					<label class="col-sm-2 control-label required-control "><spring:message
							code="wms.delayReason.dateOccurance" text="Date of occurance" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="delayReasonDto.dateOccuranceDesc" id="dateOccu"
								class="form-control mandColorClass datepickerEndDate " readonly="true"
								onchange="" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>

						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="wms.delayReason.status" text="Status"/></label>
					<div class="col-sm-4">
						<form:select path="delayReasonDto.status"
							class="form-control mandColorClass chosen-select-no-results"
							id="status">
							<form:option value="" selected="true"><spring:message code='work.management.select' text='Select' /></form:option>
							<form:option value="A"><spring:message code='wms.delayReason.active' text='Active' /></form:option>
							<form:option value="I"><spring:message code='wms.delayReason.inactive' text='Inactive' /></form:option>
						</form:select>
					</div>


				</div>

				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title='<spring:message code="common.sequenceconfig.search" text="Search" />'
						onclick="searchForm('DelayReason.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="common.sequenceconfig.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title='<spring:message code="common.sequenceconfig.reset" text="Reset" />'
						onclick="ResetSearchForm(this)">
						<spring:message code="common.sequenceconfig.reset" text="Reset"></spring:message>
					</button>


					<button type="button" class="btn btn-blue-2" title='<spring:message code="common.sequenceconfig.add" text="Add" />'
						onclick="addSequenceMaster('DelayReason.html','addDetailsofDelayReason')">

						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="common.sequenceconfig.add" text="Add"></spring:message>
					</button>
				</div>

				<div class="table-responsive" id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="configurationId" class="configurationTable">
							<!-- class="table table-striped table-condensed table-bordered"
							id="sequenceTable" -->
							<thead>
								<tr>
									<th><spring:message code="project.master.projname"
											text="Project Name" /></th>
									<th><spring:message code="wms.delayReason.workName" text="Work Name" /></th>
									<th><spring:message code="wms.delayReason.dateOccurance" text="Date of occurance" /></th>
									<th><spring:message code="wms.delayReason.status"
											text="Status" /></th>
									<th><spring:message code="common.sequenceconfig.action"
											text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.delayReasonDtos}" var="data"
									varStatus="index">
									<tr>
										<form:hidden path="" id="delResId" value="${data.delResId}" />
										<form:hidden path="" id="projId" value="${data.projId}" />
										<td>${data.projName}</td>
										<td>${data.workName}</td>
										<td>${data.dateOccuranceDesc}</td>
										<td>${data.status }</td>

										<td class="text-center">

											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Work Delay Reason"
												onclick="viewDelayReason('DelayReason.html','viewDelayReason',${data.delResId},${data.projId})">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Work Delay Reason"
												onclick="editDelayReason('DelayReason.html','editDelayReason',${data.delResId},${data.projId})">
												<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
											</button>

										</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
