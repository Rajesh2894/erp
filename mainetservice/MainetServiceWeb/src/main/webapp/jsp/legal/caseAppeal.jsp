<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/legal/caseEntry.js"></script>
<script src="js/mainet/validation.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.caseEntry"
						text="Case Entry" /></strong>
			</h2>
			<apptags:helpDoc url="CaseEntry.html" />
		</div>

		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="CaseEntry.html" class="form-horizontal form"
				name="frmCaseEntry" id="frmCaseEntry">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_caseEntryTbl">

							<thead>
								<tr>
									<th><spring:message code="lgl.Srno" text="Sr. No." /></th>
									<th><spring:message text="Registration Date" /></th>
									<th><spring:message text="Registration Number" /></th>
									<th><spring:message text="Reference Number" /></th>
									<th><spring:message code="caseEntryDTO.cseTypId"
											text="Case Type" /></th>
									<th><spring:message code="caseEntryDTO.cseCatId"
											text="Case Category" /></th>
									<th><spring:message code="caseEntryDTO.crtId"
											text="Court Name" /></th>
									<th><spring:message code="caseEntryDTO.cseCaseStatusId"
											text="Case Status" /></th>
											<th class="widthAction"><spring:message code="lgl.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.caseEntryDTOList}" var="item"
									varStatus="loop">
									<c:if
										test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
										<c:set value="${item.orgid}" var="orgid" scope="page"></c:set>
										<c:set value="${item.cseCaseStatusId}" var="cseCaseStatusId"
											scope="page"></c:set>
										<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(${item.cseCaseStatusId})"
											var="lookup" />
										<c:if
											test="${command.parentOrgid eq orgid  && lookup.lookUpCode eq 'N'}">
											<tr>
												<td align="center" bgcolor="skyblue">${loop.count}</td>
												<td align="center" bgcolor="skyblue">${item.createdDate}</td>
												<td align="center" bgcolor="skyblue">${item.cseSuitNo}</td>
												<td align="left" bgcolor="skyblue">${item.cseRefsuitNo}</td>
												<td align="center" bgcolor="skyblue"><spring:eval
														expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseTypId)"
														var="lookup" />${lookup.lookUpDesc }</td>
												<td align="center" bgcolor="skyblue"><spring:eval
														expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(item.cseCatId1)"
														var="lookup" />${lookup.lookUpDesc }</td>
												<td align="left" bgcolor="skyblue">${item.courtName}</td>
												<td align="center" bgcolor="skyblue"><spring:eval
														expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseCaseStatusId)"
														var="lookup" />${lookup.lookUpDesc }</td>
														
														<td class="text-center" bgcolor="skyblue">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View CaseDetails"
													onclick="modifyCase(${item.cseId},'CaseEntry.html','EDIT','V')">
													<i class="fa fa-eye"></i>
											</td>
											</tr>
										</c:if>
									</c:if>

									<c:if
										test="${command.parentOrgid ne orgid || (lookup.lookUpCode ne 'N' && command.parentOrgid  eq orgid)}">
										<tr>
											<td align="center">${loop.count}</td>
											<td align="center">${item.createdDate}</td>
											<td align="center">${item.cseSuitNo}</td>
											<td align="left">${item.cseRefsuitNo}</td>
											<td align="center"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseTypId)"
													var="lookup" />${lookup.lookUpDesc }</td>
											<td align="center"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(item.cseCatId1)"
													var="lookup" />${lookup.lookUpDesc }</td>
											<td align="left">${item.courtName}</td>
											<td align="center"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseCaseStatusId)"
													var="lookup" />${lookup.lookUpDesc }</td>
													<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View CaseDetails"
													onclick="modifyCase(${item.cseId},'CaseEntry.html','EDIT','V')">
													<i class="fa fa-eye"></i>
												</button> 
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>
