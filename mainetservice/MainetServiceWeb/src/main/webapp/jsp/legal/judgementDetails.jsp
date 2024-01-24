<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/legal/caseHearing.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="JudgementDetailDTO.jdDetails" text="Judgement Details"/>
			</h2>
			<!-- SET HELP DOC URL WHICH YOU SET IN YOU CONTROLLER INDEX METHOD -->
			<apptags:helpDoc url="JudgementDetails.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="JudgementDetails.html" name="frmHearingDetails" 	id="frmHearingDetails" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="text-center padding-bottom-10">
						<%-- <button type="button" class="btn btn-blue-2 search"
							onclick="searchPopulationMaster(this);">
							<i class="fa fa-search"></i>
							<spring:message code="solid.waste.search" text="Search"></spring:message>
						</button>
						<button type="button" class="btn btn-warning"
							onclick="resetPopulation();">
							<spring:message code="solid.waste.reset" text="Reset"></spring:message>
						</button> --%>
						<%-- <button type="button" class="btn btn-success add"
							onclick="openForm('JudgementDetails.html','ADD');">
							<strong class="fa fa-plus-circle"></strong>
							<spring:message code="bt.add" text="Add" />
						</button> --%>
					</div>
				
				
					<div class="table-responsive clear">
						<table summary="SUMMARY" class="table table-bordered table-striped pmId" id="hearingTable">
							<thead>
								<tr>
								
								
								<th><spring:message code="label.checklist.srno" text="Sr.No."/></th>
									<th><spring:message code="caseEntryDTO.cseName" text="Case Name" /></th>
									<th><spring:message code="caseEntryDTO.cseSuitNo" text="Case Number"/></th>
									<th><spring:message code="caseEntryDTO.cseTypId" text="Case Type" /><span class="mand">*</span></th>
									<th><spring:message code="caseEntryDTO.cseCatId" text="Case Category" /><span class="mand">*</span></th>
									<th><spring:message code="caseEntryDTO.cseCaseStatusId" text="Case Status" /><span class="mand">*</span></th>
									<th><spring:message code="lgl.action" text="Action"/></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${command.caseEntryDTOList}" var="data"
								varStatus="index">
								<c:set value="${data.orgid}" var="orgid" scope="page"></c:set>
								<c:if test="${orgid eq 1}">
									<tr>
										<td class="text-center" width="10%" bgcolor="skyblue">${index.count}</td>
										<td class="text-center" width="10%" bgcolor="skyblue">${data.cseName}</td>
										<td class="text-center" width="10%" bgcolor="skyblue">${data.cseSuitNo}</td>
										<td class="text-center" width="10%" bgcolor="skyblue"><spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.cseTypId)"
												var="lookup" />${lookup.lookUpDesc }</td>
										<td class="text-center" width="10%"><spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(data.cseCatId1)"
												var="lookup" />${lookup.lookUpDesc }</td>
										<td class="text-center" width="10%" bgcolor="skyblue"><spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.cseCaseStatusId)"
												var="lookup" />${lookup.lookUpDesc }</td>
										<td class="text-center" width="10%" bgcolor="skyblue">
											<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="getData(${data.cseId},'V','JudgementDetails.html?EDIT')"
												data-original-title="View" title="View">
												<strong class="fa fa-eye"></strong><span class="hide"><spring:message
														code="solid.waste.view" text="View"></spring:message></span>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												onClick="getData(${data.cseId},'E','JudgementDetails.html?EDIT')"
												data-original-title="Edit" title="Edit">
												<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
														code="solid.waste.edit" text="Edit"></spring:message></span>
											</button>
										</td>
									</tr>
								</c:if>
								<c:if test="${orgid ne 1}">
									<tr>
										<td class="text-center" width="10%">${index.count}</td>
										<td class="text-center" width="10%">${data.cseName}</td>
										<td class="text-center" width="10%">${data.cseSuitNo}</td>
										<td class="text-center" width="10%"><spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.cseTypId)"
												var="lookup" />${lookup.lookUpDesc }</td>
										<td class="text-center" width="10%"><spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(data.cseCatId1)"
												var="lookup" />${lookup.lookUpDesc }</td>
										<td class="text-center" width="10%"><spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.cseCaseStatusId)"
												var="lookup" />${lookup.lookUpDesc }</td>
										<td class="text-center" width="10%">
											<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="getData(${data.cseId},'V','JudgementDetails.html?EDIT')"
												data-original-title="View" title="View">
												<strong class="fa fa-eye"></strong><span class="hide"><spring:message
														code="solid.waste.view" text="View"></spring:message></span>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												onClick="getData(${data.cseId},'E','JudgementDetails.html?EDIT')"
												data-original-title="Edit" title="Edit">
												<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
														code="solid.waste.edit" text="Edit"></spring:message></span>
											</button>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
						</table>
				</div>		

			</form:form>
		</div>
	</div>
</div>