<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="content" id="content">
	<div class="animated slideInDown">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="history" text="History" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"></i></a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form class="form-horizontal" name="Application History">
					<h4 class="margin-top-0">
						<spring:message text="dashboard.heading.historyDetails"
							code="dashboard.heading.historyDetails" />
					</h4>
					<div id="ActionHistory" class="panel-collapse collapse in">
						<div class="table-responsive">
							<table class="table table-bordered table-condensed">
								<tr>
									<th><spring:message code="care.srno" text="Sr. No." /></th>
									<th><spring:message code="ack.labelDate"
											text="Date" /></th>
									<th width="18%"><spring:message code="care.action.Action"
											text="Action" /></th>
									<th><spring:message code="care.action.employee.name"
											text="Employee Name" /></th>
									<th><spring:message code="care.action.employee.email"
											text="Email" /></th>
									<th><spring:message code="care.action.designation"
											text="Designation" /></th>
									<th width="20%"><spring:message code="care.action.remarks"
											text="Remarks" /></th>
									<th><spring:message code="care.action.attachments"
											text="Attachments" /></th>
								</tr>
								<c:set var="rowCount" value="0" scope="page" />


								<c:forEach items="${actions}" var="action" varStatus="status">
									<tr>
										<td><c:set var="rowCount" value="${rowCount+1}"
												scope="page" /> <c:out value="${rowCount}"></c:out></td>
										<td><fmt:formatDate pattern="dd/MM/yyyy"
												value="${action.applicationDate}" /></td>
										<td><c:if test="${not empty action.actionname}">
												<c:out value="${action.actionname}"></c:out>
											</c:if> <c:if test="${empty action.actionname}">
												<spring:message code="rti.submited" text="Submited" />
											</c:if></td>
										<td><c:out value="${action.actionBy}"></c:out></td>
										<td><c:out value="${action.email}"></c:out></td>
										<td><c:out value="${action.actionByDesg}"></c:out></td>



										<td><c:if test="${action.remarks !='0'}">
												<c:out value="${action.remarks}"></c:out>
											</c:if></td>
										<td><c:forEach items="${action.docList}" var="lookUp">
												<apptags:filedownload dmsDocId="${lookUp.dmsDocId}"
													filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
													actionUrl="PioResponse.html?Download"></apptags:filedownload>

											</c:forEach></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>

					<div class="text-center clear padding-10">
						<%-- <c:choose>
							<c:when
								test="${userSession.employee.designation.dsgshortname eq 'OPR'}">
								<apptags:backButton url="OperatorDashboardView.html"></apptags:backButton>
							</c:when>
							<c:otherwise>
								<apptags:backButton url="AdminHome.html"></apptags:backButton>
							</c:otherwise>
						</c:choose> --%>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>