<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="js/legal/caseEntry.js"></script>
<script src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->

<style>
.widthAction{
	width:10% !important;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.caseEntry"
						text="Case Entry"/></strong>
			</h2>
			<apptags:helpDoc url="CaseEntry.html" />
		</div>
		
		<div class="widget-content padding">
		<!-- Start Form -->
			<form:form action="CaseEntry.html" class="form-horizontal form" name="frmCaseEntry" id="frmCaseEntry">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="envFlag" id="envFlag"/>
				<div class="form-group">

					<apptags:input labelCode="caseEntryDTO.cseSuitNo"
						path="caseEntryDTO.cseSuitNo"
						cssClass="alphaNumeric form-control "></apptags:input>
				
					<apptags:input labelCode="caseEntryDTO.caseNo"
						path="caseEntryDTO.caseNo"
						cssClass="alphaNumeric form-control "></apptags:input>
			
			</div>
		
		<div class="form-group">
				<apptags:lookupFieldSet baseLookupCode="CCT" hasId="true"
						showOnlyLabel="false" pathPrefix="caseEntryDTO.cseCatId"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control margin-bottom-10"
						showAll="false" columnWidth="20%" 
						disabled="${command.saveMode eq 'V' ? true : false }"/> 
				
					
			
		</div>
		
		<div class="form-group">
			<apptags:date labelCode="caseEntryDTO.cseDate"
						datePath="caseEntryDTO.cseDate" 
						cssClass="form-control" fieldclass="datepicker"
						></apptags:date>	
						
			<label class="control-label col-sm-2"> <spring:message	code="caseEntryDTO.cseDeptid" /></label>
					<div class="col-sm-4">
								<form:select class=" mandColorClass form-control chosen-select-no-results"
									path="caseEntryDTO.cseDeptid" id="cseDeptid">
									<form:option value="">
										<spring:message code="lgl.select" text="Select" />
									</form:option>
									<c:forEach items="${command.departments}" var="department">
										<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${department.dpDeptid}">${department.dpNameMar}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
									</c:otherwise>
								</c:choose>
									</c:forEach>
								</form:select>
					</div>
		</div>
		
		<div class="form-group">
			<label class="control-label col-sm-2"> <spring:message	code="caseEntryDTO.crtId" text="Court Name"/></label>
			<div class="col-sm-4">
				<!-- chosen-select-no-results -->
				<form:select class=" mandColorClass form-control chosen-select-no-results" path="caseEntryDTO.crtId" id="crtId">
					<form:option value="">
						<spring:message code="lgl.select" text="Select" />
					</form:option>
					<c:forEach items="${command.courtMasterDTOList}" var="courtMasterDTO">
						<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtNameReg}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtName}</form:option>
									</c:otherwise>
								</c:choose>
					</c:forEach>
				</form:select>
			</div>
		
			<label class="control-label col-sm-2" for="Census"><spring:message
						code="caseEntryDTO.cseTypId" text="Case Type" /></label>
			<c:set var="baseLookupCode" value="TOC" />  <!-- OZA -->
			<apptags:lookupField
				items="${command.getLevelData(baseLookupCode)}"
				path="caseEntryDTO.cseTypId"
				cssClass="mandColorClass form-control chosen-select-no-results" isMandatory="true"
				hasId="true" selectOptionLabelCode="selectdropdown"
				disabled="${command.saveMode eq 'V' ? true : false }" />
		
				
		</div>
				<div class="text-center clear padding-10">
					
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchCase();return false;">
						<i class="fa fa-search"></i>
						<spring:message code="lgl.search" text="Search"></spring:message>
					</button>
					
					<button type="Reset" class="btn btn-warning"
						onclick="resetCaseEntryForm();">
						<spring:message code="lgl.reset" text="Reset"></spring:message>
					</button>
					
					<button type="submit" class="button-input btn btn-success"
						onclick="openAddForm('CaseEntry.html','ADD');"
						name="button-Add" style="" id="button-submit">
						<spring:message code="lgl.add" text="Add" />
					</button>
					
					<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
				</div>
				<!-- End button -->
				
				<!-- case number, suit number, case type, case category, case status and advocate name. -->
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_caseEntryTbl">
							
							<thead>
								<tr>
									<th><spring:message code="lgl.Srno" text="Sr. No." /></th>
									<th><spring:message code="caseEntryDTO.caseNo" text="Case Ref Number" /></th>
									<th><spring:message code="caseEntryDTO.cseName" text="Case Name" /></th>
									<th><spring:message code="caseEntryDTO.crtId" text="Court Name" /></th>
									<th><spring:message code="caseEntryDTO.cseSuitNo" text="Case Number" /></th>
									<th><spring:message code="caseEntryDTO.cseTypId" text="Case Type" /></th>
									<th><spring:message code="caseEntryDTO.cseCatId" text="Case Category" /></th>
									<th><spring:message code="caseEntryDTO.cseCaseStatusId" text="Case Status" /></th>
									<th class="widthAction"><spring:message code="lgl.action" text="Action" /></th>
	
								</tr>
							</thead>
							<tbody>						
								<c:forEach items="${command.caseEntryDTOList}" var="item"
									varStatus="loop">
									<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
									<c:set value="${item.orgid}" var="orgid" scope="page"></c:set>
									<c:set value="${item.cseCaseStatusId}" var="cseCaseStatusId" scope="page"></c:set>
										<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(${item.cseCaseStatusId})"
											var="lookup" />
										<c:if test="${command.parentOrgid eq orgid  && lookup.lookUpCode eq 'N'}">
										<tr>
											<td align="center" bgcolor="skyblue">${loop.count}</td>
											<td align="center" bgcolor="skyblue">${item.caseNo}</td>
											<td align="left" bgcolor="skyblue">${item.cseName}</td>
											<td align="left" bgcolor="skyblue">${item.courtName}</td>
											<td align="center" bgcolor="skyblue">${item.cseSuitNo}</td>
											<td align="center" bgcolor="skyblue"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseTypId)"
													var="lookup" />${lookup.lookUpDesc }</td>
											<td align="center" bgcolor="skyblue"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(item.cseCatId1)"
													var="lookup" />${lookup.lookUpDesc }</td>
											<td align="center" bgcolor="skyblue"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseCaseStatusId)"
													var="lookup" />${lookup.lookUpDesc }</td>

											<td class="text-center" bgcolor="skyblue">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="View CaseDetails"
													onclick="modifyCase(${item.cseId},'CaseEntry.html','EDIT','V')">
													<i class="fa fa-eye"></i>
												</button> <c:if test="${lookup.lookUpCode ne  'C'}">
													<button type="button" class="btn btn-warning btn-sm"
														title="Edit CaseDetails"
														onclick="modifyCase(${item.cseId},'CaseEntry.html','EDIT','E')">
														<i class="fa fa-pencil"></i>
													</button>
												</c:if>
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="print CaseDetails"
													onclick="printCase(${item.cseId},'CaseEntry.html','PRINT','P')">

													<i class="fa fa-print"></i>
												</button>
											</td>
										</tr>
									</c:if>
									</c:if>
									
									<c:if test="${command.parentOrgid ne orgid || (lookup.lookUpCode ne 'N' && command.parentOrgid  eq orgid)}">
										<tr>
											<td align="center">${loop.count}</td>
											<td align="center">${item.caseNo}</td>
											<td align="left">${item.cseName}</td>
											<td align="left">${item.courtName}</td>
											<td align="center">${item.cseSuitNo}</td>
											<td align="center"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseTypId)"
													var="lookup" />${lookup.lookUpDesc }</td>
											<td align="center"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(item.cseCatId1)"
													var="lookup" />${lookup.lookUpDesc }</td>
											<td align="center"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseCaseStatusId)"
													var="lookup" />${lookup.lookUpDesc }</td>

											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="<spring:message	code="lgl.case.view.details" text="View CaseDetails"/>"
													onclick="modifyCase(${item.cseId},'CaseEntry.html','EDIT','V')">
													<i class="fa fa-eye"></i>
												</button> <c:if test="${lookup.lookUpCode ne  'C'}">
													<button type="button" class="btn btn-warning btn-sm"
														title="<spring:message	code="lgl.case.edit" text="Edit CaseDetails"/>"
														onclick="modifyCase(${item.cseId},'CaseEntry.html','EDIT','E')">
														<i class="fa fa-pencil"></i>
													</button>
												</c:if>
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="<spring:message	code="lgl.case.print" text="print CaseDetails" />"
													onclick="printCase(${item.cseId},'CaseEntry.html','PRINT','P')">

													<i class="fa fa-print"></i>
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

