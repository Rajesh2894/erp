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
<script type="text/javascript" src="js/sfac/stateInfoMastForm.js"></script>
<style>
	@media (max-width: 480px) {
		table#stateInfo tbody tr td button {
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
				<spring:message code="sfac.state.info.summary.title"
					text="State Information Summary Form" />
			</h2>
			<apptags:helpDoc url="StateInformationMaster.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="StateInfoMastSummaryForm"
				action="StateInformationMaster.html" method="post"
				class="form-horizontal">
				<form:hidden path="" id="parentOrg" value="${userSession.getCurrent().getOrganisation().getDefaultStatus()}"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">

					<label for="State" class="col-sm-2 control-label required-control"><spring:message
							code="sfac.state" text="State">
						</spring:message></label>
					<div class="col-sm-4">
						<form:select path="stateInfoDto.state" class="form-control"
							id="state" onchange="getDistrictList();">
							<form:option value="0">
								<spring:message code="sfac.select" text="Select" />
							</form:option>
							<c:forEach items="${command.stateList}" var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label for="District"
						class="col-sm-2 control-label"><spring:message
							code="sfac.district" text="District">
						</spring:message></label>
					<div class="col-sm-4">
						<form:select path="stateInfoDto.district" id="district"
							class="form-control mandColorClass">
							<form:option value="0">
								<spring:message code='sfac.select' text="Select" />
							</form:option>
							<c:forEach items="${command.districtList}" var="dist">
								<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.button.search" text="Search"/>'
						onclick="searchForm(this,'StateInformationMaster.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						title='<spring:message code="sfac.button.reset" text="Reset" />'
						onclick="window.location.href ='StateInformationMaster.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
					<c:if test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'}">
					<button type="button" class="btn btn-blue-2"
						title='<spring:message code="sfac.button.add" text="Add" />'
						onclick="formForCreate(this);">
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="sfac.button.add" text="Add" />
					</button></c:if>
				</div>

				<h4>
					<spring:message code="sfac.state.info"
						text="State Information Detail" />
				</h4>

				<c:set var="d" value="0" scope="page"></c:set>
				<div class="table-responsive">
					<table class="table table-bordered table-striped " id="stateInfo">
						<thead>
							<tr>
								<th><spring:message code="sfac.srno" text="Sr No" /></th>
								<th><spring:message code="sfac.state" text="State" /></th>
								<th><spring:message code="sfac.district" text="District" /></th>
								<th><spring:message code="sfac.Area.Type" text="Area Type" /></th>
								<th><spring:message code="sfac.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>

			</form:form>

		</div>

	</div>
</div>
