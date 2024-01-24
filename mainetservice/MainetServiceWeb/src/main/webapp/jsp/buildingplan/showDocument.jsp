<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/cfc/scrutiny.js"></script>

<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">


			<h4>
				<spring:message  text="Download Document" />
			</h4>
		</div>
		<div class="widget-content padding">
			<form:form action="SiteAffected.html" class="form-horizontal form"
				name="frmSiteAff" id="frmSiteAff">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
                <form:hidden path="flag" id="flag" />
				<input type="hidden" id="taskId"
					value="${userSession.scrutinyCommonParamMap.taskId}" />
				<input type="hidden" id="workflowId"
					value="${userSession.scrutinyCommonParamMap.workflowId}" />
				<input type="hidden" id="serviceId"
					value="${userSession.scrutinyCommonParamMap.SM_SERVICE_ID}" />
				<input type="hidden" id="appId"
					value="${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID}" />
					
					
				<div>
												<table
													class="table table-hover table-bordered table-striped">

													<tr>
														<th><label class="tbold"><spring:message
																	code="sr.no" text="Sr No" /></label></th>
														<th><label class="tbold"><spring:message
																	text="Field Name" /></label></th>
														<th><spring:message text="View" /></th>
													</tr>
													<tbody>
														<c:forEach items="${command.downloadDocs}"
															var="lookUp" varStatus="lk">
															<tr>
																<td><label>${lk.count}</label></td>
																<td><label>${lookUp.clmDescEngl}</label></td>
																<td align="center"><apptags:filedownload
																		filename="${lookUp.attFname}"
																		filePath="${lookUp.attPath}"
																		actionUrl="AdminHome.html?Download">
																	</apptags:filedownload></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												</div>
				
				<div class="text-center padding-top-10">
					
					<input type="button" class="btn btn-danger"
						onclick="backToApplicationForm(${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID},${userSession.scrutinyCommonParamMap.taskId},'ApplicationAuthorization.html',${userSession.scrutinyCommonParamMap.SM_SERVICE_ID},${userSession.scrutinyCommonParamMap.workflowId})"
						value="<spring:message code="back.button" />">
				</div>
			</form:form>
		</div>
	</div>
</div>

