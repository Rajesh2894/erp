<!-- Start JSP Necessary Tags -->
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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/illegalNoticeGeneration.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code=""
					text="Illegal To Legal Connection Notice Generation Summary" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="IllegalConnectionNoticeGeneration.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="IllegalConnectionNoticeGeneration.html"
				class="form-horizontal" id="IllegalConnectionNoticeGeneration"
				name="IllegalConnectionNoticeGeneration">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
						showOnlyLabel="false" pathPrefix="searchDTO.codDwzid"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control changeParameterClass" disabled="" />
				</div>
				<div class="text-center padding-bottom-10">
					<button class="btn btn-blue-2  search" onclick="searchNotice();"
						type="button">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="" />
					</button>
					<button class="btn btn-warning  reset"
						onclick="window.location.href='IllegalConnectionNoticeGeneration.html'"
						type="reset">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" />
					</button>
					<button class="btn btn-primary" onclick="generateNoticeForm('C');"
						type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="" text="Generate Notice" />
					</button>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="datatables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="" text="Notice No." /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="" text="Owner Name" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="" text="Address" /></th>
								<th scope="col" width="18%" align="center"><spring:message
										code="" text="Mobile No." /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="estate.grid.column.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.noticeList}" var="mstDto">
								<tr>
									<td>${mstDto.csIllegalNoticeNo}</td>
									<td>${mstDto.csOname}</td>
									<td>${mstDto.csOadd}</td>
									<td>${mstDto.csOcontactno}</td>
									<td class="text-center">
										<button type="button"
											class="btn btn-warning btn-sm margin-right-10 margin-left-10"
											title="Edit Notice"
											onclick="getActionForDefination(${mstDto.csIdn},'E');">
											<i class="fa fa-pencil"></i>
										</button>
										<button type="button"
											class="btn btn-blue-2 btn-sm margin-right-10 margin-left-10"
											title="View Notice"
											onclick="getActionForDefination(${mstDto.csIdn},'V');">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button"
											class="btn btn-warning btn-sm margin-right-10 margin-left-10"
											onclick="getActionForDefination(${mstDto.csIdn},'P')"
											title="Print Illegal Notice">
											<i class="fa fa-print"></i>
										</button>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>