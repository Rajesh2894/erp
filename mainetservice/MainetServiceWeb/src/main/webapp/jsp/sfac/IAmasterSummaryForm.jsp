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
<script type="text/javascript" src="js/sfac/IAmasterForm.js"></script>
<style>
	@media (max-width: 480px) {
		table#IADetailsTable tbody tr td button {
			margin: 0 !important;
		}
	}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.Ia.form.title"
					text="Implementing Agency On-Boarding" />
			</h2>
			<apptags:helpDoc url="IAMasterForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="IAMasterSummaryForm" action="IAMasterForm.html"
				method="post" class="form-horizontal">
				<form:hidden path="" id="parentOrg" value="${userSession.getCurrent().getOrganisation().getDefaultStatus()}"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.IA.name" text="IA Name" /></label>
					<div class="col-sm-4">
						<form:select path="iaMasterDto.IAName" id="IAName"
							cssClass="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.iaMasterDtoList}" var="dto">
								<form:option value="${dto.IAId}">${dto.IAName}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.iaOnboarding.year" text="IA Onboarding Year" /></label>
					<div class="col-sm-4">
						<form:select path="iaMasterDto.alcYear" id="allocationYear"
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
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.button.search" text="Search"/>'
						onclick="searchForm(this,'IAMasterForm.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						title='<spring:message code="sfac.button.reset" text="Reset" />'
						onclick="window.location.href ='IAMasterForm.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
					<button type="button" class="btn btn-blue-2"
						 " title='<spring:message code="sfac.button.add" text="Add" />'
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add" />
					</button>
				</div>

				<h4>
					<spring:message code="sfac.IA.Details" text="IA Details" />
				</h4>

				<c:set var="d" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<table class="table table-bordered table-striped crop-details-table"
						id="IADetailsTable">
						<thead>
							<tr>
								<th><spring:message code="sfac.srno" text="Sr No" /></th>
								<th><spring:message code="sfac.IA.name" text="IA Name" /></th>
								<th><spring:message code="sfac.iaOnboarding.year" text="IA Onboarding Year" /></th>
								<th><spring:message code="sfac.status" text="Status" /></th>
								<th><spring:message code="sfac.action"	text="Action" /></th>
							</tr>
						</thead>
						<tbody>
		<%-- 					<c:forEach items="${command.iaDetailDtoList}" var="dto"
								varStatus="loop">
								<tr>
									<td align="center">${loop.count}</td>
									<td align="center">${dto.ianame}</td>
									<td align="center">${dto.allcYear}</td>
									<td align="center">
										<button type="button" class="btn btn-blue-2 btn-sm margin-right-10"
											title="View" onclick="getActionForDefination(${dto.iaid},'V')">
											<i class="fa fa-eye"></i>
										</button> <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() == 'Y'}">
											<button type="button" class="btn btn-warning btn-sm btn-sm"
												title="Edit" onclick="getActionForDefination(${dto.iaid},'E')">
												<i class="fa fa-pencil"></i>
											</button>
										</c:if>
									</td>
									</tr>
							</c:forEach> --%>
						</tbody>
					</table>
				</div>

			</form:form>
		</div>

	</div>
</div>