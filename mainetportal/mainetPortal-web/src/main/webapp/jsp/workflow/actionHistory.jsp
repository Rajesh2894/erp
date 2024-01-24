<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Application History" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form class="form-horizontal" name="Application History">
				<div id="ActionHistory" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-bordered table-condensed">
								<tr>
									<th><spring:message code="care.srno" text="Sr. No." /></th>
									<th><spring:message code="care.datetime"
											text="Date & Time" /></th>
									<th><spring:message code="care.actions.taskName"
											text="TaskName" /></th>
									<th width="18%"><spring:message code="care.Action"
											text="Action" /></th>
									<th width="12%"><spring:message code="care.department"
											text="Department" /></th>
									<th><spring:message code="care.employee.name"
											text="Employee Name" /></th>
									<th><spring:message code="care.email" text="Email" /></th>
									<th><spring:message code="care.designation"
											text="Designation" /></th>
									<th width="20%"><spring:message code="care.remarks"
											text="Remarks" /></th>
									<th><spring:message code="care.attachments" text="Attachments"/></th>
								</tr>
								<c:set var="rowCount" value="0" scope="page" />
								<c:forEach items="${actions}" var="action" varStatus="status">
								<!-- D#114247 -->
								<c:if test = "${!fn:contains(action.taskName, 'Hidden_Task')}">
									<tr>
										<td><c:set var="rowCount" value="${rowCount+1}"
												scope="page" /> <c:out value="${rowCount}"></c:out></td>
										<td><c:out value="${action.dateOfAction}"></c:out></td>
										<td><c:out value="${action.taskName}"></c:out></td>
										<td><c:out value="${action.decision}"></c:out></td>
										<%--D127199 --%>
										<td><c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId()== '1'}">${action.deptName}</c:when>
													<c:otherwise>${action.deptNameMar}</c:otherwise>
										</c:choose></td>
										<td><c:out value="${action.empName}"></c:out></td>
										<td><c:out value="${action.empEmail}"></c:out></td>
											<td><c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId()== '1'}">${action.empGroupDescEng}</c:when>
													<c:otherwise>${action.empGroupDescReg}</c:otherwise>
												</c:choose></td>

											<td><c:out value="${action.comments}"></c:out></td>
									  <td>
						                  <ul>
							                  <c:forEach items="${action.attachements}" var="lookUp" varStatus="status">
							                  <li><apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${lookUp.defaultVal}" actionUrl="CitizenHome.html?Download"></apptags:filedownload></li>
							                  </c:forEach>
						                  </ul>
					                  </td>
									</tr>
								</c:if>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<apptags:backButton url="CitizenHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>