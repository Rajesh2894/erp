<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/raBillGeneration.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="wms.RunningAccountBillSummary"
					text="Running Account Bill Summary" />
			</h2>
			<div class="additional-btn">
				 <apptags:helpDoc url="raBillGeneration.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="raBillGeneration.html" class="form-horizontal"
				id="raBill" name="raBill">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path="" id="projId"
							class="form-control chosen-select-no-results"
							onchange="getWorkName(this);">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:if test="${userSession.getCurrent().getLanguageId() ne '1'}">
								<c:forEach items="${command.projectMasterList}"
									var="activeProjName">
									<form:option value="${activeProjName.projId }"
										code="${activeProjName.projId }">${activeProjName.projNameReg}</form:option>
								</c:forEach>
							</c:if>
							<c:if test="${userSession.getCurrent().getLanguageId() eq '1'}">
								<c:forEach items="${command.projectMasterList}"
									var="activeProjName">
									<form:option value="${activeProjName.projId }"
										code="${activeProjName.projId }">${activeProjName.projNameEng}</form:option>
								</c:forEach>
							</c:if>
						</form:select>
					</div>


					<label class="col-sm-2 control-label"><spring:message
							code="work.def.workname" /></label>
					<div class="col-sm-4">
						<form:select path="" id="workId"
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
						</form:select>
					</div>
				</div>


			
				<div class="text-center padding-bottom-10">
					<button class="btn btn-blue-2 search" onclick="searchRaBill();" title='<spring:message code="works.management.search" text="Search" />'
						type="button">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning  reset" type="reset" title='<spring:message code="works.management.reset" text="Reset" />'>
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button class="btn btn-primary " id="addRaBillForm" title='<spring:message code="wms.CreateRABill" text="Add" />'
						onclick="openRaBillAddForm('raBillGeneration.html','addRaBill');" type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="wms.CreateRABill" text="Add" />
					</button>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="datatables">
						<thead>
							<tr>

								<th scope="col" width="18%" align="center"><spring:message
										code="project.master.projname" text="Project Name" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="milestone.works.name" text="Works Name" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="wms.RABillNo" text="RA Bill No." /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="wms.RADate" text="RA Bill Date" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="wms.RABillAmount" text="RA Bill Amount" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.status" text="Status" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="estate.grid.column.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${raBillList}" var="mstDto">
								<tr>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>