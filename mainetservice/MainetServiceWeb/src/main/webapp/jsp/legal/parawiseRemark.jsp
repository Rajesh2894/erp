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
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/legal/parawiseRemark.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<!-- End JSP Necessary Tags -->


<style>
.widthAction{
	width:8% !important;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.caseEntry"
						text="Parawise Remark"/></strong>
			</h2>
			<apptags:helpDoc url="ParawiseRemark.html" />
		</div>
		<div class="widget-content padding">
		<!-- Start Form -->
			<form:form action="ParawiseRemark.html" class="form-horizontal form"
				name="ParawiseRemark" id="ParawiseRemark">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				
				<div class="form-group">

					<apptags:input labelCode="caseEntryDTO.cseSuitNo"
						path="caseEntryDTO.cseSuitNo"
						cssClass="alphaNumeric form-control "></apptags:input>

					<label class="control-label col-sm-2"> <spring:message
							code="caseEntryDTO.cseDeptid" /></label>
					<div class="col-sm-4">
						<form:select class="form-control chosen-select-no-results"
							path="caseEntryDTO.cseDeptid" id="cseDeptid">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="departmentList">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${departmentList.dpDeptid}">${departmentList.dpNameMar}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${departmentList.dpDeptid}">${departmentList.dpDeptdesc}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="CCT" hasId="true"
						showOnlyLabel="false" pathPrefix="caseEntryDTO.cseCatId"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control margin-bottom-10"
						showAll="false" columnWidth="20%" />



				</div>

				<div class="form-group">
					<apptags:date labelCode="caseEntryDTO.cseDate"
						datePath="caseEntryDTO.cseDate"
						cssClass="form-control" fieldclass="datepicker"></apptags:date>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2"> <spring:message
							code="caseEntryDTO.crtId" text="Court Name" /></label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select class="form-control chosen-select-no-results"
							path="caseEntryDTO.crtId" id="crtId">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
								<c:forEach items="${courtMasterDTOList}" var="courtNameList">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${courtNameList.id}">${courtNameList.crtNameReg}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${courtNameList.id}">${courtNameList.crtName}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2" for="Census"><spring:message
							code="caseEntryDTO.cseTypId" text="Case Type" /></label>
					<c:set var="baseLookupCode" value="TOC" />
					<!-- OZA -->
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.cseTypId"
						cssClass="form-control chosen-select-no-results"
						hasId="true" selectOptionLabelCode="selectdropdown" />


				</div>
				
				
				
				
				<div class="text-center clear padding-10">
					
					<button type="submit" class="btn btn-blue-2"
						onclick="searchParawiseRemark('ParawiseRemark.html','searchParawiseRemark');return false;"
						name="button-Add" style="" id="button-submit">
						<spring:message code="lgl.search" text="Search" />
						
					</button>
					
					<button type="Reset" class="btn btn-warning"
						onclick="window.location.href='ParawiseRemark.html'">
						<spring:message code="account.bankmaster.reset" text="Reset"></spring:message>
					</button>
					
					<apptags:backButton url="AdminHome.html" cssClass="btn btn-danger"></apptags:backButton>
					<%-- <button type="submit" class="button-input btn btn-success"
						onclick="openForm('ParawiseRemark.html','ADD');"
						name="button-Add" style="" id="button-submit">
						<spring:message code="lgl.add" text="Add" />
					</button> --%>
					
				</div>
				
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_parawiseRemarkTbl">
							<thead>
								<tr>
									<th><spring:message code="caseEntryDTO.cseName" text="Case Name" /></th>
									<th><spring:message code="caseEntryDTO.cseSuitNo" text="Suit Number"/></th>
									<th><spring:message code="caseEntryDTO.cseTypId" text="Case Type" /></th>
									<th><spring:message code="caseEntryDTO.cseCatId" text="Case Category" /></th>
									<th><spring:message code="caseEntryDTO.cseCaseStatusId" text="Case Status" /></th>
									<th><spring:message code="caseEntryDTO.advId" text="Advocate" /></th>
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
											<td align="center" bgcolor="skyblue">${item.cseName}</td>
											<td align="center" bgcolor="skyblue">${item.cseSuitNo}</td>
											<td align="center" bgcolor="skyblue"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseTypId)"
													var="lookup" />${lookup.lookUpDesc }</td bgcolor="skyblue">
											<td align="center" bgcolor="skyblue"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(item.cseCatId1)"
													var="lookup" />${lookup.lookUpDesc }</td>
											<td align="center" bgcolor="skyblue"><spring:eval
													expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(item.cseCaseStatusId)"
													var="lookup" />${lookup.lookUpDesc }</td>
											<td align="center" bgcolor="skyblue" bgcolor="skyblue">${item.advName}</td>
											<td class="text-center" bgcolor="skyblue">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="<spring:message code="legal.management.view" text="View CaseDetails"></spring:message>"
													onclick="modifyRemark(${item.cseId},'ParawiseRemark.html','EDIT','V')">
													<i class="fa fa-eye"></i>
												</button>
												<button type="button" class="btn btn-warning btn-sm"
													title="<spring:message code="legal.management.edit" text="Edit CaseDetails"></spring:message>"
													onclick="modifyRemark(${item.cseId},'ParawiseRemark.html','EDIT','E')">
													<i class="fa fa-pencil"></i>
												</button>
											</td>
										</tr>
									</c:if>
									</c:if>
									<c:if test="${orgid ne command.parentOrgid || (lookup.lookUpCode ne 'N' && orgid eq command.parentOrgid)}">
										<tr>
											<td align="center">${item.cseName}</td>
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
											<td align="center">${item.advName}</td>
											<td class="text-center">
												<button type="button" class="btn btn-blue-2 btn-sm"
													title="<spring:message code="legal.management.view" text="View CaseDetails"></spring:message>"
													onclick="modifyRemark(${item.cseId},'ParawiseRemark.html','EDIT','V')">
													<i class="fa fa-eye"></i>
												</button>
												<button type="button" class="btn btn-warning btn-sm"
													title="<spring:message code="legal.management.edit" text="Edit CaseDetails"></spring:message>"
													onclick="modifyRemark(${item.cseId},'ParawiseRemark.html','EDIT','E')">
													<i class="fa fa-pencil"></i>
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