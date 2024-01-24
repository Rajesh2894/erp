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
<script type="text/javascript" src="js/sfac/CBBOMasterForm.js"></script>

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
				<spring:message code="sfac.cbbo.summary.form.title"
					text="Cluster Based Business Organization On-Boarding" />
			</h2>
			<apptags:helpDoc url="CBBOMasterForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="cbboMasterSummaryForm" action="CBBOMasterForm.html"
				method="post" class="form-horizontal">
				<form:hidden path="" id="parentOrg" value="${userSession.getCurrent().getOrganisation().getOrgShortNm()}"/>
				<form:hidden path="cbId" id="cbId"/>
                <form:hidden path="iId" id="iId"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					
					<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA'}">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.CBBO.name" text="CBBO Name" /></label>
					<div class="col-sm-4">
						<form:select path="masterDto.cbboName" id="cbboName"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.cbboMasterList}" var="dto">
								<form:option value="${dto.cbId}">${dto.cbboName}</form:option>
							</c:forEach>
						</form:select>
					</div></c:if>
						<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'}">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.CBBO.name" text="CBBO Name" /></label>
					<div class="col-sm-4">
						<form:select path="masterDto.cbboName" id="cbboName"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.cbboMasterList}" var="dto">
								<form:option value="${dto.cbId}">${dto.cbboName}</form:option>
							</c:forEach>
						</form:select>
					</div></c:if>
					
					<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'CBBO'}">
						<apptags:input labelCode="sfac.CBBO.name" isReadonly="true"
										cssClass="mandColorClass hasNameClass" path="masterDto.cbboName"
										isMandatory="true" maxlegnth="100"></apptags:input>
					</c:if>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.EmpanelmentOfCbbo" text="Empanelment of Cbbo" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="masterDto.alcYearToCBBO" type="text"
								class="form-control datepicker mandColorClass alcYearToCBBO"
								id="alcYearToCBBO" placeholder="dd/mm/yyyy" readonly="true" />
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'CBBO'}">
						<label class="col-sm-2 control-label"><spring:message
								code="sfac.IA.name" text="IA Name" /></label>
						<div class="col-sm-4">
							<form:select path="masterDto.iaId" id="iaId"
								cssClass="form-control chosen-select-no-results">
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
						test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'}">
						<label class="col-sm-2 control-label"><spring:message
								code="sfac.IA.name" text="IA Name" /></label>
						<div class="col-sm-4">
							<form:select path="masterDto.iaId" id="iaId"
								cssClass="form-control chosen-select-no-results">
								<form:option value="0">
									<spring:message text="Select" code="sfac.select'" />
								</form:option>
								<c:forEach items="${command.iaMasterDtoList}" var="dto">
									<form:option value="${dto.IAId}" code="${dto.IAName}">${dto.IAName}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
					<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA'}">
						<apptags:input labelCode="sfac.IA.name" isReadonly="true"
							cssClass="mandColorClass hasNameClass" path="masterDto.IAName"
							isMandatory="true" maxlegnth="100"></apptags:input>
					</c:if>

				</div>

				<div class="text-center clear margin-top-20 margin-bottom-20">
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.button.search" text="Search"/>'
						onclick="searchForm(this,'CBBOMasterForm.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						title='<spring:message code="sfac.button.reset" text="Reset" />'
						onclick="window.location.href ='CBBOMasterForm.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
					<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'IA' }"> 
					<button type="button" class="btn btn-blue-2"
						 title='<spring:message code="sfac.button.add" text="Add" />'
						onclick="formForCreate();">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add" />
					</button></c:if>
				</div>

				<h4>
					<spring:message code="sfac.cbbo.Details" text="CBBO Details" />
				</h4>

				<c:set var="d" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<table class="table table-bordered table-striped crop-details-table"
						id="CBBODetailsTable">
						<thead>
							<tr>
								<th><spring:message code="sfac.srno" text="Sr No" /></th>
								<th><spring:message code="sfac.CBBO.IAName" text="IA Name" /></th>
								<th><spring:message code="sfac.CBBO.name" text="CBBO Name" /></th>
								<th><spring:message code="sfac.CBBO.UniqueId"
										text="CBBO Unique Id" /></th>
								<th><spring:message code="sfac.approval.status" text="Status" /></th>
								<th><spring:message code="sfac.remark" text="Remark" /></th>
								<th><spring:message code="sfac.status" text="Status" /></th>
								<th width="20%"><spring:message code="sfac.action"
										text="Action" /></th>
							</tr>
						</thead>
						<tbody>
	
							<c:forEach items="${command.cbboMastDtoList}" var="dto"
								varStatus="loop">
								<tr>
								    <td class="text-center">${loop.count}</td>
									<td align="center">${dto.IAName}</td>
									<td align="center">${dto.cbboName}</td>
									<td align="center">${dto.cbboUniqueId}</td>
									<td align="center">${dto.statusDesc}</td>
									<td align="center">${dto.remark}</td>
									<td align="center">${dto.summaryStatus}</td>
									<td align="center">
										<button type="button"
											class="btn btn-blue-2 btn-sm margin-right-10" title="View"
											onclick="getActionForDefination(${dto.cbboId},'V')">
											<i class="fa fa-eye"></i>
										</button> <c:if
											test="${userSession.getCurrent().getOrganisation().getOrgShortNm()=='IA' && dto.appStatus != 'A'}">
											<button type="button" class="btn btn-warning btn-sm btn-sm"
												title="Edit"
												onclick="getActionForDefination(${dto.cbboId},'E')">
												<i class="fa fa-pencil"></i>
											</button>
										</c:if>
										<%-- <c:if test="${dto.approved eq 'Y'}">
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