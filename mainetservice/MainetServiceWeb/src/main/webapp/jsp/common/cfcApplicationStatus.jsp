<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/common/cfcApplicationStatus.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
			</a>
		</div>
		<div class="widget-content padding">
			<form:form action="CFCApplicationStatus.html" method="post"
				class="form-horizontal" name="nursingHomeRegSummary"
				id="cfcApplicationStatusId">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="cfc.application.status"
						text="CFC Application Status" />
				</h4>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="NHP.application.no" text="Application No." /></label>
					<div class="col-sm-4">
						<form:input path="applicationStatusDto.appNo" id="appNo"
							class="form-control" maxlength="100" />
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.first.name" text="First Name" /></label>
					<div class="col-sm-4">
						<form:input path="applicationStatusDto.fName" id="firstName"
							class="form-control" maxlength="100" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="NHP.middle.name" text="Middle Name" /></label>
					<div class="col-sm-4">
						<form:input path="applicationStatusDto.mName" id="middleName"
							class="form-control" maxlength="100" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.last.name" text="Last Name" /></label>
					<div class="col-sm-4">
						<form:input path="applicationStatusDto.lName" id="lastName"
							class="form-control" maxlength="100" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="cfc.application.date" text="Application Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="applicationStatusDto.appDate" id="date"
								class="form-control datepicker" readonly="true" onchange="" />
							<label class="input-group-addon"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden"></label>
						</div>
					</div>

				</div>


				<div class="form-group">

					<label for="text-1" class="col-sm-2 control-label"><spring:message
							code="NHP.dept" text="Department" /> </label>
					<div class="col-sm-4">

						<form:select path="applicationStatusDto.deptId"
							class="form-control chosen-select-no-results" label="Select"
							id="deptId" onchange="getServiceByDeptId()">
							<form:option value="" selected="true">
								<spring:message code='work.management.select' />
							</form:option>
							<%-- <c:forEach items="${command.departmentList}" var="dept">
						     <form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpDeptdesc}</form:option>
						    </c:forEach> --%>	
						    <c:choose>
								<c:when test="${command.langId eq 1}">
									<c:forEach items="${command.departmentList}" var="dept">
						        		<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpDeptdesc}</form:option>
						        	</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.departmentList}" var="dept">
							        	<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpNameMar}</form:option>
						         	</c:forEach>
								</c:otherwise>
							</c:choose>
											
							<%-- <c:choose>
								<c:when test="${command.langId eq 1}">
									<c:forEach items="${command.departmentList}" var="dept">
						     		<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpDeptdesc}</form:option>
						     	</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.departmentList}" var="dept">
								<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
								</c:otherwise>
							</c:choose> --%>

						</form:select>
					</div>

					<label for="text-1" class="col-sm-2 control-label"><spring:message
							code="NHP.service.name" text="Service Name" /></label>
					<div class="col-sm-4">

						<form:select path="applicationStatusDto.serviceId"
							class="form-control chosen-select-no-results" label="Select"
							id="serviceId">
							<form:option value="" selected="true">
								<spring:message code='work.management.select' />
							</form:option>
												
							
							<c:choose>
								<c:when test="${userSession.getLanguageId() eq 1}">
									<c:forEach items="${command.serviceMstList}" var="service">
							    	<form:option value="${service.smServiceId}">${service.smServiceName}</form:option>
							        </c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.serviceMstList}" var="service">
							    	<form:option value="${service.smServiceId}">${service.smServiceNameMar}</form:option>
							        </c:forEach>
								</c:otherwise>
							</c:choose>

						</form:select>
					</div>
				</div>
				 <input type ="hidden" id="langId" value="${command.langId}"/>

				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchForm(this,'CFCApplicationStatus.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="SFT.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="ResetForm()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="NHP.reset" text="Reset"></spring:message>
					</button>
					
					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="NHP.back" text="Back"></spring:message>
					</button>

				</div>


				<div class="table-responsive" id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="cfcStatusTable" class="configurationTable">

							<thead>
								<tr>
									<th width="10%"><spring:message code="NHP.application.no"
											text="Application No" /></th>
									<th width="15%"><spring:message code="CFC.applicant.name"
											text="Applicant Name" /></th>
									<th width="15%"><spring:message code="cfc.application.date"
											text="Application Date" /></th>
									<th width="15%"><spring:message code="NHP.dept" text="Department" /></th>
									<th width="15%"><spring:message code="NHP.service.name"
											text="Service Name" /></th>
									<th width="10%"><spring:message code="SFT.status" text="Status" /></th>
									<th width="10%"><spring:message code="CFC.app.dueDate" text="Due Date" /></th>
									<th width="10%"><spring:message code="NHP.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.applicationStatusDTOs}" var="data"
									varStatus="index">
									<tr>

										<td class="text-center">${data.applicationId}</td>
										<td class="text-center">${data.empName}</td>
										<td class="text-center">${data.formattedDate}</td>
										<td class="text-center">${data.deptName}</td>
										<c:if test="${userSession.getCurrent().getLanguageId() eq '1'}">
									       <td class="text-center">${data.serviceName }</td>
										</c:if>
										<c:if test="${userSession.getCurrent().getLanguageId() ne '1'}">
									       <td class="text-center">${data.serviceNameReg }</td>
										</c:if>
										<td class="text-center">${data.status}</td>
										<td class="text-center">${data.dueDate}</td>
										<td class="text-center"><button type="button" class="btn btn-primary btn-sm text-center"
											title="<spring:message code="" text="Action History"></spring:message>"
											onclick="getActionForWorkFlow(${data.applicationId},'V');">
											<i class="fa fa-history"></i>
										</button></td>
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

