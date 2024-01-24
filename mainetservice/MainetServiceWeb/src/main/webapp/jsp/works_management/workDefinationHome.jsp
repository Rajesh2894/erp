<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/wmsWorkDefination.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.def.summary"
					text="Work Definition Summary" />
			</h2>
			<div class="additional-btn">
			    <apptags:helpDoc url="WmsWorkDefinationMaster.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="WmsWorkDefinationMaster.html"
				class="form-horizontal" name="" id="WmsWorkDefinationMaster">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="cpdMode" id="cpdMode" />
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="work.def.workCode" text="Work Code" /></label>
					<div class="col-sm-4">
						<form:select path="wmsDto.workcode"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="workcode">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.defDtoList}" var="def">
								<form:option value="${def.workcode}">${def.workcode}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label for="" class="col-sm-2 control-label "><spring:message
							code="work.def.workname" text="Work Name" /> </label>
					<div class="col-sm-4">
						<form:select path="wmsDto.workName"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="workName">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.defDtoList}" var="def">
								<form:option value="${def.workName}">${def.workName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<!-- Remove As per SUDA UAT -->

				<%-- <div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="work.def.startDate"
						datePath="wmsDto.workStartDate" cssClass="datepicker"></apptags:date>
					<apptags:date fieldclass="datepicker" labelCode="work.def.endDate"
						datePath="wmsDto.workEndDate" cssClass="datepicker"></apptags:date>
				</div> --%>

				<div class="form-group">
					<label for="" class="col-sm-2 control-label "><spring:message
							code="project.master.projname" text="Project Name" /> </label>
					<div class="col-sm-4">
						<form:select path="wmsDto.projId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="projId">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.projectMasterList}" var="list">
								<form:option value="${list.projId}" code="${list.projCode}">${list.projNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label for="" class="col-sm-2 control-label "><spring:message
							code="work.def.workType" text="Type of Work" /> </label>
					<c:set var="WRTlookUp" value="WRT" />
					<apptags:lookupField items="${command.getLevelData(WRTlookUp)}"
						path="wmsDto.workType"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="work.management.select"
						isMandatory="true" />
				</div>

				<!-- Remove As per SUDA UAT -->
				<%-- <div class="form-group">
					<label for="" class="col-sm-2 control-label"><spring:message
							code="work.def.projPhase" text="Project Phase" /> </label>
					<c:set var="PPHlookUp" value="PPH" />
					<apptags:lookupField items="${command.getLevelData(PPHlookUp)}"
						path="wmsDto.workProjPhase"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select" />
				</div> --%>


				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button class="btn btn-blue-2 search" type="button" title='<spring:message code="works.management.search" text="Search" />'
						id="searchWorkDef">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning" title='<spring:message code="works.management.reset" text="Reset" />'
						onclick="window.location.href='WmsWorkDefinationMaster.html'"
						type="button">
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button class="btn btn-primary add" id="addWorkDefination" title='<spring:message code="works.management.add" text="Add" />'
						type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="works.management.add" text="Add" />
					</button>

				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<th width="10%" align="center"><spring:message
										code="work.def.workCode" text="Work Code" /></th>
								<th width="20%" align="center"><spring:message
										code="work.def.workname" text="Work Name" /></th>
								<th width="10%" align="center"><spring:message
										code="work.def.workType" text="Type of Work" /></th>
								<th width="30%" align="center"><spring:message
										code="project.master.projname" text="Project Name" /></th>


								<!-- Remove As Per SUDA UAT -->

								<%-- <th width="10%" align="center"><spring:message
										code="work.def.startDate" text="" /></th>
								<th width="10%" align="center"><spring:message
										code="work.def.endDate" text="" /></th> --%>

								<%-- <th width="10%" align="center"><spring:message
										code="work.def.projPhase" text="Project Phase" /></th> --%>
								<th width="10%" align="center"><spring:message
										code="works.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.defDtoList}" var="DefDto">
								<tr>


									<td align="center">${DefDto.workcode}</td>
									<td align="center">${DefDto.workName}</td>
									<td align="center">${DefDto.workTypeDesc}</td>
									<td align="center">${DefDto.projName}</td>
									<!-- Remove As Per SUDA UAT -->

									<%-- <td>${DefDto.startDateDesc}</td>
									<td>${DefDto.endDateDesc}</td> --%>


									<%-- <td>${DefDto.workProjPhaseDesc}</td> --%>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="viewWorkDef(${DefDto.workId})"
											title="<spring:message code="works.management.view"></spring:message>">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-success btn-sm"
											onClick="editWorkDef(${DefDto.workId})"
											title="<spring:message code="works.management.edit"></spring:message>">
											<i class="fa fa-pencil-square-o"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</form:form>
		</div>
	</div>
</div>
