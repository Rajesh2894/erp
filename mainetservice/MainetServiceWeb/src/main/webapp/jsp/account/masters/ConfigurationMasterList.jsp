<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/configurationMaster.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.configuration.header" />
				<strong><spring:message
						code="account.configuration.header1" /> </strong>
			</h2>
		<apptags:helpDoc url="ConfigurationMaster.html" helpDocRefURL="ConfigurationMaster.html"></apptags:helpDoc>		
		</div>

		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbAcCodingstructureMas"
				class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"
						for="comCpdId"><spring:message code="account.configuration.compname"
							text="Component Name" /></label>
					<div class="col-sm-4">
						<form:select id="comCpdId" path="comCpdId"
							cssClass="form-control mandColorClass chosen-select-no-results"
							disabled="${viewMode}">
							<form:option value="">
								<spring:message code="account.select.componentName" text="Select Component Name" />
							</form:option>
							<c:forEach items="${tempList}" varStatus="status"
								var="levelChild">
								<form:option code="${levelChild.lookUpCode}"
									value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchConfigurationMasterData()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<spring:url var="cancelButtonURL" value="ConfigurationMaster.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
					<c:if test="${defaultOrgIdStatus == 'Y'}">
						<button type="button" class="btn btn-blue-2 createData">
							<i class="fa fa-plus-circle"></i>
							<spring:message code="account.bankmaster.add" text="Add" />
						</button>
					</c:if>
					<c:if test="${nonDefaultOrgIdStatus == 'Y'}">
						<button type="button" class="btn btn-blue-2 createData">
							<i class="fa fa-plus-circle"></i>
							<spring:message code="account.bankmaster.add" text="Add" />
						</button>
					</c:if>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>
