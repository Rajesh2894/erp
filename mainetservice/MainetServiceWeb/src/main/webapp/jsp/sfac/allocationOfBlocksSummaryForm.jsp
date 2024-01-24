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
<script type="text/javascript" src="js/sfac/allocationOfBlocksForm.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.allocationOfBlock.summary.form"
					text="Allocation Of Blocks Summary Form" />
			</h2>
			<apptags:helpDoc url="AllocationOfBlocks.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form method="post" id="allocationOfBlocksSummary"
				action="AllocationOfBlocks.html" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					<form:hidden path="orgShortNm" id="orgShortNm" />
					<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="Orgnization Type">
						<spring:message code="sfac.organizationType"	text="Orgnization Type" />	</label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.orgTypeId" id="orgTypeId"
							class="form-control chosen-select-no-results" >
							<form:option value="0">
								<spring:message code="sfac.select" text="Select" />
							</form:option>
							<c:forEach items="${command.orgList}" var="org">
							<c:if test="${org.orgShortNm eq 'IA'}">
								<c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1 && org.orgShortNm eq 'IA'}">
										<form:option value="${org.orgid}" code="${org.orgShortNm}" selected="selected">${org.oNlsOrgname}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${org.orgid}" code="${org.orgShortNm}">${org.oNlsOrgnameMar}</form:option>
									</c:otherwise>
								</c:choose>
								</c:if>
							</c:forEach>
						</form:select>
					</div>

					<label for="text-1" class="col-sm-2 control-label required-control"><spring:message
							code="sfac.OrganizationName" text="Orgnization Name" /> </label>
						<div class="col-sm-4"><form:select path="blockAllocationDto.organizationNameId" 
						          disabled="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA'?  true : false}"
								class="form-control chosen-select-no-results" id="organizationNameId">
								<form:option value="0">	<spring:message code="Select" text="Select" />
								</form:option>
								<c:forEach items="${command.commonMasterDtoList}"	var="dto">
								<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA' }">
									<form:option value="${dto.id}"  selected="selected">${dto.name}</form:option></c:if>
									<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() ne 'IA' }">
									<form:option value="${dto.id}" >${dto.name}</form:option></c:if>
								</c:forEach>
							</form:select>
							<%-- <form:input path="blockAllocationDto.orgName" id="organizationNameId" disabled="true"  cssClass="form-control"/> --%>
						</div>
					</div>




				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.AllocationYear" text="Allocation Year" /></label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.allocationYearId" id="allocationYearId"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.faYears}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.state" text="State" /></label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.sdb1"
							onchange="getDistrictData();" class="form-control chosen-select-no-results" id="sdb1">
							<form:option value="0">
								<spring:message code="sfac.select" text="Select" />
							</form:option>
							<c:forEach items="${command.stateList}" var="lookUp">
								<form:option code="${lookUp.lookUpCode}"
									value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.district" text="District" /></label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.sdb2"
							onchange="getBlockData();" class="form-control chosen-select-no-results" id="sdb2">
							<form:option value="0">
								<spring:message code="sfac.select" text="Select" />
							</form:option>
							<c:forEach items="${command.districtList}" var="lookUp">
								<form:option code="${lookUp.lookUpCode}"
									value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.block" text="Block" /></label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.sdb3" class="form-control chosen-select-no-results" id="sdb3">
							<form:option value="0">
								<spring:message code="sfac.select" text="Select" />
							</form:option>
							<c:forEach items="${command.blockList}" var="lookUp">
								<form:option code="${lookUp.lookUpCode}"
									value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
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

                  <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() == 'Y'}">
					<button type="button" class="btn btn-blue-2"" title="Add"
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add"></spring:message>
					</button></c:if>
					
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='AllocationOfBlocks.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset"></spring:message>
					</button>

					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'" title="Back">
						<spring:message code="sfac.button.back" text="Back"></spring:message>
					</button>

				</div>
				
		
				
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="blockDatatables">
						<thead>
							<tr>
							   <th scope="col" width="10%" align="center"><spring:message
										code="sfac.srno" text="Sr No." />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.OrganizationName" text="Organization Name" />
								<th scope="col" width="10%" align="center"><spring:message
										code="sfac.AllocationYear" text="Allocation Year" />
							<%-- 	<th scope="col" width="10%" align="center"><spring:message
										code="sfac.state" text="State" /> --%>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.totalNoOfBlock" text="Total Target Assigned" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="sfac.remainingforAllocation" text="Remaining Block Allocation Count" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${command.blockAllocationDtoList}" var="dto"
									varStatus="status">
									<tr>
										<td class="text-center">${status.count}</td>
										<td class="text-center">${dto.orgName}</td>
										<td class="text-center">${dto.alcYear}</td>
										<%-- <td>${dto.state}</td> --%>
										<td class="text-center">${dto.noofBlockAssigned}</td>
										<td class="text-center">${dto.pendingBlockCount}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View"
												onclick="modifyCase(${dto.blockId},'AllocationOfBlocks.html','EDIT','V')">
												<i class="fa fa-eye"></i>
											</button> 
											<c:if test="${command.showEdit eq 'Y' && userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA'}">
												<button type="button" class="btn btn-warning btn-sm"
													title="Edit"
													onclick="modifyCase(${dto.blockId},'AllocationOfBlocks.html','EDIT','E')">
													<i class="fa fa-pencil"></i>
											</button></c:if>
										</td>
									</tr>
								</c:forEach>
						</tbody>
					</table>
				</div> 
				
			<%-- 	<div class="viewBlockTable">
				<h4><spring:message code="" text="Details Of Statewise Block Allocation" /></h4>
				<div class="table-responsive">
						<table class="table table-bordered table-striped"
							id="ViewBlockDatatables">
							<thead>
								<tr>
									<th scope="col" width="10%" align="center"><spring:message
											code="" text="Sr No." /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="sfac.OrganizationName" text="Organization Name" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="sfac.AllocationYear" text="Allocation Year" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="sfac.state" text="State" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="sfac.district" text="Distrcit" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="sfac.block" text="Block" /></th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
					</div>
			 </div> --%>
			</form:form>
		</div>
	</div>
</div>

