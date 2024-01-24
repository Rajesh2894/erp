<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/buildingplan/newTCPLicenseForm.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="New License Summary" />
			</h2>
			<apptags:helpDoc url="NewTCPLicenseForm.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code=""
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="" text="is mandatory" /> </span>
			</div>

			<form:form action="NewTCPLicenseForm.html" name="NewTCPLicenseFormSummary" id="NewTCPLicenseFormSummaryId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>

				<div class="text-center padding-bottom-10 padding-top-20">
					<button type="submit" class="button-input btn btn-success" name="button-Add" id="button-submit"
						onclick="openNewLicenseForm('NewTCPLicenseForm.html','addNewLicenseForm', 'A');" >
						<i class="fa fa-plus-circle"></i><spring:message code="" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<table id="newLicenseTableId" summary="Store Inent Summary" class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th width="8%"><spring:message code="" text="Sr.No" /></th>
								<th><spring:message code="" text="Application No." /></th>
								<th><spring:message code="" text="Application Type" /></th>
								<th><spring:message code="" text="Purpose Name" /></th>
								<th width="10%"><spring:message code="" text="Status" /></th>
								<th width="10%"><spring:message code="" text="Action" /></th>
							</tr>
						</thead>
						<tbody>				
							<c:forEach items="${command.applicationMasDTOList}" var="data" varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
									<td class="text-center">${data.applicationNo}</td>
									<td align="center"><c:if test="${data.appPAppType ne null}">
										<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(data.appPAppType,${UserSession.organisation}).getLookUpDesc()"
											var="lookUpDesc" />${lookUpDesc}
									</c:if></td>
									<td align="center"><c:if test="${data.appPLicPurposeId ne null}">
										<spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${data.appPLicPurposeId},${UserSession.organisation.orgid}).getLookUpDesc()"
											var="lookUpDesc" />${lookUpDesc}
									</c:if></td>
									<td class="text-center">
										<c:choose>
											<c:when test="${'Y' eq data.draftFlag}">
												<spring:message code="" text="Draft" />
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${not empty data.workflowStatus}">
														${data.workflowStatus}
													</c:when>
													<c:otherwise>
														<spring:message code="" text="Complete" />
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="draftNewLicenseForm('NewTCPLicenseForm.html','addNewLicenseForm', 'V',  ${data.tcpLicMstrId})"
											title="<spring:message code="" text="View" />">
											<i class="fa fa-eye"></i>
										</button>
										<c:if test="${'Y' eq data.draftFlag}">
											<button type="button" class="btn btn-warning btn-sm"
												onClick="draftNewLicenseForm('NewTCPLicenseForm.html','addNewLicenseForm', 'E' , ${data.tcpLicMstrId})"
												title="<spring:message code="" text="Edit" />">
												<i class="fa fa-pencil"></i>
											</button>										
										</c:if>
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
