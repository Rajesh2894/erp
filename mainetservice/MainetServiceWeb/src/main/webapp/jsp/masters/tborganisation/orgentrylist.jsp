<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="js/masters/tborganisation/orgentrylist.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="Organisation.master" text="Organisation Master"/></h2>
			<apptags:helpDoc url="Organisation.html" helpDocRefURL="Organisation.html"></apptags:helpDoc>
			
		</div>

		<div class="widget-content padding">
		        <div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
				<div class="error-div alert alert-danger alert-dismissible"
				style="display: block;" id="errorDivOrgMas"></div>
				<form class="form-horizontal">
				<div id="custBankDiv" class="clear padding-top-10">
				<div class="form-group" id="orgSearch">
				<label class="col-sm-2 control-label required-control"><spring:message code='Organisation.name'/></label>
				<div class="col-sm-4">
					<select id="orgId" class="form-control chosen-select-no-results">
						<option value=""><spring:message code="eip.admin.login.select.organisation" /></option>
						<c:forEach items="${list}" var="org">
							<c:if test="${userSession.languageId eq 1}"><option value="${org.orgid}">${org.ONlsOrgname}</option></c:if>
							<c:if test="${userSession.languageId eq 2}"><option value="${org.orgid}">${org.ONlsOrgnameMar}</option></c:if>
						</c:forEach>
					</select>
				</div>
 			</div> 

			<div class="text-center padding-top-10 clear">
				<button type="button" id="search" class="btn btn-blue-2" onclick="searchOrganisation()">
					<spring:message code='search.data' />
				</button>
				<button type="reset" id="reset" class="btn btn-warning" onclick="orgReset()">
					<spring:message code="reset.msg"/>
				</button>
				<button type="button" class="btn btn-success btn-submit" id="createData">
					<spring:message code='master.addButton' />
				</button>
			</div>

			<input type="hidden" id="default" value="${userSession.organisation.defaultStatus}"/>
			<div class="clear padding-top-10">
			<table id="grid" class="table"></table>
			<div id="pagered"></div>
			</div>
			</div>
			</form>
		</div>
	</div>
</div>