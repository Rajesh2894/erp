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
<script type="text/javascript" src="js/sfac/milestoneEntryForm.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.milestone.entry.summary"
					text="Milestone Entry Summary" />
			</h2>
			<apptags:helpDoc url="MilestoneEntryForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="milestoneEntrySummaryForm" action="MilestoneEntryForm.html"
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
							path="milestoneMasterDto.iaName"
							
							id="iaName" class="form-control " disabled="true" />
								<form:hidden path="milestoneMasterDto.iaId" id="iaId" />
					
					</div>
					
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.milestone.name" text="Milestone Name" /></label>
					<div class="col-sm-4">
					<form:input
							path="milestoneMasterDto.milestoneId"
							
							id="milestoneId" class="form-control "  />
								
					
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
						onclick="window.location.href='MilestoneEntryForm.html'">
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
										code="sfac.srno" text="Sr No." />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.IA.name" text="IA Name" />		
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.milestone.id" text="Milestone ID" />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.milestone.update.payment.status" text="Update Payment Status" />		
								
								<th scope="col" width="15%" class="text-center"><spring:message
										code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${command.milestoneMasterDtos}" var="dto"
									varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.iaName}</td>
										<td class="text-center">${dto.milestoneId}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"	onclick="modifyCase(${dto.msId},'MilestoneEntryForm.html','EDIT','U')">
												<i class="fa fa-edit"></i>
											</button> 
											
										</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"	onclick="modifyCase(${dto.msId},'MilestoneEntryForm.html','EDIT','V')">
												<i class="fa fa-eye"></i>
											</button> 
											<%-- <c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'CBBO'}"> --%>
												<button type="button" class="btn btn-warning btn-sm" title="Edit"
													onclick="modifyCase(${dto.msId},'MilestoneEntryForm.html','EDIT','E')">
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
