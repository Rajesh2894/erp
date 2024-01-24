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
<script type="text/javascript" src="js/sfac/fpoMasterForm.js"></script>
<style>
.icon-details{
	width: 1.9rem;
}

</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.fpo.summary.form.title"
					text="FPO Master Summary Form" />
			</h2>
			<apptags:helpDoc url="FPOMasterForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="fpoMasterSummaryForm" action="FPOMasterForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					
					<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="" text="FPO Name" /></label>
					<div class="col-sm-4">
						<form:select path="dto.fpoId" id="fpoId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.fpoMasterDtoList}" var="dto">
								<form:option value="${dto.fpoId}">${dto.fpoName}</form:option>
							</c:forEach>
						</form:select>
					</div><label class="col-sm-2 control-label"><spring:message
							code="" text="FPO Registration No." /></label>
					<div class="col-sm-4">
						<form:select path="dto.fpoRegNo" id="fpoRegNo"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.fpoMasterDtoList}" var="dto">
								<form:option value="${dto.fpoRegNo}">${dto.fpoRegNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
					</div>

				<div class="form-group">
				<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() ne 'IA'}">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.IA.name" text="IA Name" /></label>
					<div class="col-sm-4">
						<form:select path="dto.iaId" id="iaId"
							cssClass="form-control chosen-select-no-results hasNameClass">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select'" />
							</form:option>
							<c:forEach items="${command.iaNameList}" var="dto">
								<form:option value="${dto.iaId}" code="${dto.IAName}">${dto.IAName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					</c:if>

					<c:if
						test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA'}">
						<apptags:input labelCode="sfac.IA.name" isReadonly="true"
							cssClass="mandColorClass hasNameClass" path="dto.iaName"
							isMandatory="true" maxlegnth="100"></apptags:input>
					</c:if>

					
				</div>



				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchForm(this)">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search"></spring:message>
					</button>
                   <c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'CBBO'}">
					<button type="button" class="btn btn-blue-2"" title="Search"
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add"></spring:message>
					</button>
					</c:if>
					
					<button type="button" class="btn btn-warning reset" title="Reset">
						<!-- onclick="window.location.href='FPOMasterForm.html'" -->
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset"></spring:message>
					</button>

					<button type="button" class="btn btn-danger" id="back"  title="Back"
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
										code="sfac.srno" text="Sr No." /></th>
							<th scope="col" width="12%" align="center">	<spring:message
							code="sfac.IA.name" text="IA Name" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="sfac.fpo.fpoName" text="FPO Name" /></th>
								<th scope="col" width="8%" align="center"><spring:message
										code="sfac.fpo.registrationYear" text="Registration Year" /></th>
								<th scope="col" width="8%" class="text-center"><spring:message
										code="sfac.fpo.cmpnyFpoRegNo" text="Company Registration No" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.approved.ia" text="Approved By IA" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.approved.cbbo" text="Approved By CBBO" /></th>
								<%-- <th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.approved.fpo" text="Approved By FPO" /></th> --%>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.remark" text="Remark" /></th>
								<th scope="col" width="8%" class="text-center"><spring:message
										code="sfac.status" text="Status" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${command.fpoMasterDtoList}" var="dto"
									varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.iaName}</td>
										<td class="text-center">${dto.fpoName}</td>
										<td class="text-center">${dto.fpoRegDate}</td>
										<td class="text-center">${dto.companyRegNo}</td>
										<td class="text-center">${dto.appByIaDesc}</td>
										<td class="text-center">${dto.appByCbboDesc}</td>
										<td class="text-center">${dto.remark}</td>
										<td class="text-center">${dto.summaryStatus}</td>
									
										<%-- <td class="text-center">${dto.appByFpoDesc}</td> --%>
										<%-- <td class="text-center">${dto.appDesc}</td> --%>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"	onclick="modifyCase(${dto.fpoId},'FPOMasterForm.html','EDIT','V')">
												<i class="fa fa-eye"></i>
											</button> 
											<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'CBBO' && (dto.appStatus  eq 'NA' || dto.appStatus  eq 'R') && (dto.activeInactiveStatus ne 'I')}">
												<button type="button" class="btn btn-warning btn-sm" title="Edit"
													onclick="modifyCase(${dto.fpoId},'FPOMasterForm.html','EDIT','E')">
													<i class="fa fa-pencil"></i>
											</button></c:if> 
										<%-- 	<c:if test="${dto.approved eq 'Y'}">
												<img src="assets/img/checked-mark.png" alt="checked mark" class="icon-details" title="Approved">
										  	</c:if> 
										
											<c:if test="${dto.approved ne 'Y'}">
												<img src="assets/img/cancel-mark.png" alt="cancel mark" class="icon-details" title="Not Approved">
											</c:if>  --%>
											
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
