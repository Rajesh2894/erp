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

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.fpo.pm.title"
					text="FPO Profile Management Summary Form" />
			</h2>
			<apptags:helpDoc url="FPOProfileManagementForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="fpoProfileSummaryForm" action="FPOProfileManagementForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					
					<div class="form-group">
				
				
					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sfac.fpo.fpoName" text="FPO Name" /></label>
					<div class="col-sm-4">
						<form:input
							path="fpoMasterDto.fpoName"
							
							id="fpoName$" class="form-control " disabled="true" />
								<form:hidden path="fpoMasterDto.fpoId" id="fpoId" />
						<%-- <form:select path="dto.foFpoMasterDto.fpoId" id="fpoId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.masterDtoList}" var="dto">
								<form:option value="${dto.fpoId}">${dto.fpoName}</form:option>
							</c:forEach>
						</form:select> --%>
					</div>

					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sfac.fpo.cbbo.name" text="CBBO Name" /></label>
					<div class="col-sm-4">
					
					<form:input
							path="fpoMasterDto.cbboName"
							
							id="cbboName" class="form-control " disabled="true" />
								<form:hidden path="fpoMasterDto.cbboId" id="cbboId" />
						<%-- <form:select path="dto.cbboMasterDto.cbboId" id="cbboId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.cbboMasterList}" var="cbbo">
								<form:option value="${cbbo.cbboId}">${cbbo.cbboName}</form:option>
							</c:forEach>
						</form:select> --%>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.IA.name" text="IA Name" /></label>
					<div class="col-sm-4">
					<form:input
							path="fpoMasterDto.iaName"
							
							id="iaName" class="form-control " disabled="true" />
								<form:hidden path="fpoMasterDto.iaId" id="iaId" />
						<%-- <form:select path="dto.iaMasterDto.IAId" id="iaId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select'" />
							</form:option>
							<c:forEach items="${command.iaNameList}" var="dto">
								<form:option value="${dto.iaId}" code="${dto.IAName}">${dto.IAName}</form:option>
							</c:forEach>
						</form:select> --%>
					</div>
				
				</div>
					
					
					<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchForm(this)">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search"></spring:message>
					</button>

					<%-- <button type="button" class="btn btn-blue-2"" title="Search"
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add"></spring:message>
					</button> --%>
					
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='FPOProfileManagementForm.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset"></spring:message>
					</button>

					<button type="button" class="btn btn-danger" id="back" title="Back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="sfac.button.back" text="Back"></spring:message>
					</button>

				</div>


	
				<div class="table-responsive">
					<table class="table table-bordered table-striped"
						id="fpoMastertables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.srno" text="Sr No." />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.fpo.fpoName" text="FPO Name" />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.fpo.registrationYear" text="Registration Year" />
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.FPO.regNo" text="Registration No" /></th>
										<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.FPO.farmerCount" text="Farmer Count" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${command.fpoMasterDtoList}" var="dto"
									varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.fpoName}</td>
										<td class="text-center">${dto.fpoRegDate}</td>
										<td class="text-center">${dto.fpoRegNo}</td>
										<td class="text-center"></td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"	onclick="modifyCase(${dto.fpoId},'FPOProfileManagementForm.html','EDIT','V')">
												<i class="fa fa-eye"></i>
											</button> 
											<%-- <c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'CBBO'}"> --%>
												<button type="button" class="btn btn-warning btn-sm" title="Edit"
													onclick="modifyCase(${dto.fpoId},'FPOProfileManagementForm.html','EDIT','E')">
													<i class="fa fa-pencil"></i>
											</button><%-- </c:if> --%>
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
