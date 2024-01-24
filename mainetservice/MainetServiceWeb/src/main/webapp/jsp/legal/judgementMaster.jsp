<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script src="js/legal/judgementMaster.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lgl.case.judgement.details" text="Judgement Details" />
			</h2>
			<apptags:helpDoc url="JudgementMaster.html" />
		</div>
		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="JudgementMaster.html"
				class="form-horizontal form" name="judgementMaster"
				id="judgementMaster">
				<!-- Start Validation include tag -->
				<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" />--%>
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
				<!-- End Validation include tag -->

				<div class="form-group">
					<apptags:input labelCode="caseEntryDTO.cseSuitNo"
						path="caseEntryDTO.cseSuitNo" cssClass="form-control"></apptags:input>
						
					<label class="control-label col-sm-2"><spring:message
							code="lgl.dept" text="Department" /></label>
					<div class="col-sm-4 ">
						<form:select path = "caseEntryDTO.cseDeptid" id= "cseDeptid" 
							cssClass="form-control chosen-select-no-results"
							class="form-control" data-rule-required="true">
							<form:option value="">Select</form:option>
							<c:forEach items="${command.departmentsList}" var="lookUp">
								<form:option value="${lookUp.dpDeptid}">${lookUp.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<!-- date picker input set -->
				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="lgl.casedate"
						datePath="caseEntryDTO.cseDate"></apptags:date>
				</div>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="<spring:message code="lgl.search" text="Search" />"
						id="searchJudgementData">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="lgl.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='JudgementMaster.html'"
						class="btn btn-warning" title="<spring:message code="lgl.reset" text="Reset" />">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="lgl.reset" text="Reset" />
					</button>
					
					<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
				</div>
				<!-- End button -->

				<div class="table-responsive clear">
					<table id="judgementTable" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th width="2%"><spring:message code="lgl.srno" text="Sr. No" /></th>
								<th width="5%"><spring:message code="lgl.caseno" text="Case Number" /></th>
								<th width="10%"><spring:message code="lgl.dept" text="Department" /></th>
								<th width="15%"><spring:message code="lgl.casetype" text="Case type" /></th>
								<th width="10%"><spring:message code="caseEntryDTO.cseName" text="Case Name" /></th>
								<th width="10%"><spring:message code="lgl.casedate" text="Case Date" /></th>		
								<th width="5%"><spring:message code="lgl.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.caseEntryDtoList}" var="caseDto"
								varStatus="status">
								<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
								 <c:set value="${caseDto.orgid}" var="orgid" scope="page"></c:set>
									<c:set value="${caseDto.cseCaseStatusId}" var="cseCaseStatusId"
										scope="page"></c:set>
									<spring:eval
										expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(${caseDto.cseCaseStatusId})"
										var="look" />
									<c:if
										test="${orgid  eq 1 && look.lookUpDesc eq 'New'}">
										<tr>
											<td class="text-center" bgcolor="skyblue">${status.count}</td>
											<td class="text-center" bgcolor="skyblue">${caseDto.cseSuitNo}</td>
											<td class="text-center" bgcolor="skyblue">${caseDto.cseDeptName}</td>
											<td class="text-center" bgcolor="skyblue"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(caseDto.cseTypId)"
													var="lookup" />${lookup.lookUpDesc }</td>

											<td class="text-center" bgcolor="skyblue">${caseDto.cseName}</td>
											<td class="text-center" bgcolor="skyblue">${caseDto.cseDateDesc}</td>
											<td class="text-center" bgcolor="skyblue">
												<button type="button"
													class="btn btn-blue-2 btn-sm margin-right-5 "
													name="button-plus" id="button-plus"
													onclick="showGridOption(${caseDto.cseId},'${caseDto.crtId}','V')"
													title="
									      <spring:message code="lgl.view" text="view"></spring:message>">
													<i class="fa fa-eye" aria-hidden="true"></i>
												</button>

												<button type="button"
													class="btn btn-danger btn-sm btn-sm"
													name="button-123" id=""
													onclick="showGridOption(${caseDto.cseId},'${caseDto.crtId }','E')"
													title="<spring:message code="lgl.edit" text="edit"></spring:message>">
													<i class="fa fa-pencil-square-o" aria-hidden="true">@</i>
												</button>
											</td>
										</tr>
									</c:if>
								</c:if>
								
								<c:if test="${orgid ne 1 || (look.lookUpDesc ne 'New' && orgid eq 1)}">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${caseDto.cseSuitNo}</td>
										<td class="text-center">${caseDto.cseDeptName}</td>
										<td class="text-center"><spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(caseDto.cseTypId)"
												var="lookup" />${lookup.lookUpDesc }</td>

										<td class="text-center">${caseDto.cseName}</td>
										<td class="text-center">${caseDto.cseDateDesc}</td>
										<td class="text-center">
											<button type="button"
												class="btn btn-blue-2 btn-sm margin-right-5 "
												name="button-plus" id="button-plus"
												onclick="showGridOption(${caseDto.cseId},'${caseDto.crtId}','V')"
												title="
									      <spring:message code="lgl.view" text="view"></spring:message>">
												<i class="fa fa-eye" aria-hidden="true"></i>
											</button>

											<button type="button"
												class="btn btn-warning btn-sm btn-sm"
												name="button-123" id=""
												onclick="showGridOption(${caseDto.cseId},'${caseDto.crtId }','E')"
												title="<spring:message code="lgl.edit" text="edit"></spring:message>">
												<i class="fa fa-pencil" aria-hidden="true"></i>
											</button>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->



