<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/legal/caseHearing.js"></script>




<style>
.widthAction{
	width:5% !important;
}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="CaseHearingDTO.hrDate" text="Hearing Date"/>
			</h2>
			<!-- SET HELP DOC URL WHICH YOU SET IN YOU CONTROLLER INDEX METHOD -->
			<apptags:helpDoc url="HearingDate.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="HearingDate.html" name="frmHearingDate" 	id="frmHearingDate" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="text-center clear padding-10">
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
							onclick="openForm('HearingDate.html','ADD');">
							<strong class="fa fa-plus-circle"></strong>
							<spring:message code="bt.add" text="Add" />
						</button> --%>
						<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
					</div>
				
				
					<div class="table-responsive clear">
						<table summary="SUMMARY" class="table table-bordered table-striped pmId" id="hearingTable">
							<thead>
								<tr>
									<th><spring:message code="label.checklist.srno" text="Sr.No."/></th>
									<th><spring:message code="caseEntryDTO.cseName" text="Case Name" /></th>
									<th><spring:message code="caseEntryDTO.cseSuitNo" text="Case Number"/></th>
									<th><spring:message code="caseEntryDTO.cseTypId" text="Case Type" /></th>
									<th><spring:message code="caseEntryDTO.cseCatId" text="Case Category" /></th>
									<th><spring:message code="caseEntryDTO.cseCaseStatusId" text="Case Status" /></th>
									<th class="widthAction"><spring:message code="lgl.action" text="Action"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.caseEntryDTOList}" var="data" varStatus="index">
								<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
								 <c:set value="${data.orgid}" var="orgid" scope="page"></c:set>
								   <c:set value="${data.cseCaseStatusId}" var="cseCaseStatusId" scope="page"></c:set>
										<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(${data.cseCaseStatusId})"
											var="lookup" />
										<c:if test="${command.parentOrgid eq orgid  && lookup.lookUpCode eq 'N'}">
									<tr>
										<td class="text-center" width="10%">${index.count}</td>
										<td class="text-center" width="10%">${data.cseName}</td>
										<td class="text-center" width="10%">${data.cseSuitNo}</td>
										<td class="text-center" width="10%"><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.cseTypId)"
											var="lookup" />${lookup.lookUpDesc }</td>
										<td class="text-center" width="10%">
											<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(data.cseCatId1)"
											var="lookup" />${lookup.lookUpDesc } 
										 </td>
										<td class="text-center" width="10%"><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.cseCaseStatusId)"
											var="lookup" />${lookup.lookUpDesc } </td>
										<td class="text-center"> 
											
											<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getData(${data.cseId},'V','HearingDate.html?EDIT')"
											data-original-title="View" title="View">
											<strong class="fa fa-eye"></strong><span class="hide"><spring:message
													code="solid.waste.view" text="View"></spring:message></span>
											</button>
											<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(${data.cseCaseStatusId})"
											var="lookup" />
											<c:if test= "${lookup.lookUpCode ne 'L' && (lookup.lookUpCode ne 'W')}">
												<button type="button" class="btn btn-warning btn-sm"
													onClick="getData(${data.cseId},'E','HearingDate.html?EDIT')"
													data-original-title="Edit" title="Edit">
													<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
														code="solid.waste.edit" text="Edit"></spring:message></span>
												</button>
											</c:if>
											
										</td>
									</tr>
									</c:if>
									</c:if>									
									  <c:if test="${orgid ne command.parentOrgid || (lookup.lookUpCode ne 'N' && orgid eq command.parentOrgid)}">
									<tr>
										<td class="text-center" width="10%">${index.count}</td>
										<td class="text-center" width="10%">${data.cseName}</td>
										<td class="text-center" width="10%">${data.cseSuitNo}</td>
										<td class="text-center" width="10%"><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.cseTypId)"
											var="lookup" />${lookup.lookUpDesc }</td>
										<td class="text-center" width="10%">
											<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(data.cseCatId1)"
											var="lookup" />${lookup.lookUpDesc } 
										 </td>
										<td class="text-center" width="10%"><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.cseCaseStatusId)"
											var="lookup" />${lookup.lookUpDesc } </td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getData(${data.cseId},'V','HearingDate.html?EDIT')"
											data-original-title="View" title="<spring:message
													code="solid.waste.view" text="View"></spring:message>">
											<strong class="fa fa-eye"></strong><span class="hide"><spring:message
													code="solid.waste.view" text="View"></spring:message></span>
											</button>
											<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(${data.cseCaseStatusId})"
											var="lookup" />
											<c:if test= "${lookup.lookUpCode ne 'L' && (lookup.lookUpCode ne 'W')}">
											
												<button type="button" class="btn btn-warning btn-sm"
													onClick="getData(${data.cseId},'E','HearingDate.html?EDIT')"
													data-original-title="Edit" title="<spring:message
														code="solid.waste.edit" text="Edit"></spring:message>">
													<strong class="fa fa-pencil"></strong><span class="hide"><spring:message
														code="solid.waste.edit" text="Edit"></spring:message></span>
												</button>
											</c:if>
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