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
<script type="text/javascript" src="js/sfac/fundReleaseRequestForm.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.fund.release.req.summary"
					text="Fund Release Request Summary" />
			</h2>
			<apptags:helpDoc url="FundReleaseRequestForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="FundReleaseRequestSummaryForm" action="FundReleaseRequestForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>



				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.IA.name" text="IA Name" /></label>
					<div class="col-sm-4">
					<form:input
							path="dto.iaName"
							
							id="iaName" class="form-control " disabled="true" />
								<form:hidden path="dto.iaId" id="iaId" />
						
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.fund.release.req.fileRef" text="File Reference No." /></label>
					<div class="col-sm-4">
						<form:input path="dto.fileReferenceNumber" id="fileReferenceNumber"
							class="form-control alphaNumeric" maxlength="50"/>
					</div>

				</div>
				
				<div  class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.cob.financial.year" text="Financial Year" /></label>
					<div class="col-sm-4">
						<form:select path="dto.financialYear" id="financialYear"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.faYears}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				
				</div>


				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchForm(this)">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-blue-2"" title="Add"
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add"></spring:message>
					</button>
					
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='FundReleaseRequestForm.html'">
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
								<th scope="col" width="8%" align="center"><spring:message
										code="sfac.srno" text="S No." />
								<th scope="col" width="8%" align="center"><spring:message
										code="sfac.fund.release.req.fileRef" text="File Reference No." />		
								<th scope="col" width="8%" align="center"><spring:message
										code="sfac.fpo.mc.appNo" text="Application No." />
								<th scope="col" width="20%" align="center"><spring:message
										code="sfac.fund.release.req.newDemand" text="New Demand" />
								<th scope="col" width="30%" class="text-center"><spring:message
										code="sfac.remark" text="Remark" /></th>	
								
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.status" text="Status" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${command.fundReleaseRequestMasterDtos}"
								var="dto" varStatus="status">
								<tr>
									<td class="text-center">${status.count}</td>
									<td class="text-center">${dto.fileReferenceNumber}</td>
									<td class="text-center">${dto.applicationNumber}</td>
									<td class="text-center">${dto.newDemandTotal}</td>
									<td class="text-center">${dto.authRemark}</td>
									<c:choose>
									<c:when test="${dto.status eq 'APPROVED'}">
									<td class="text-center">${dto.totalCostApproved}</td>
									</c:when>
									<c:otherwise>
									<td class="text-center"></td>
									</c:otherwise>
									</c:choose>
									
									<td class="text-center">${dto.status}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View"
											onclick="modifyCase(${dto.frrId},'FundReleaseRequestForm.html','EDIT','V')">
											<i class="fa fa-eye"></i>
										</button>  <c:if test="${dto.status eq 'REJECTED'} "> 
										<button type="button" class="btn btn-warning btn-sm"
											title="Edit"
											onclick="modifyCase(${dto.frrId},'FundReleaseRequestForm.html','EDIT','E')">
											<i class="fa fa-pencil"></i>
										</button>
										</c:if>
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
