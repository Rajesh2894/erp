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
									<th><spring:message code="dashboard.actions.srno"
											text="dashboard.actions.srno" /></th>
									<th><spring:message code="dashboard.actions.datetime"
											text="dashboard.actions.datetime" /></th>
									<th><spring:message code="dashboard.actions.taskName"
											text="dashboard.actions.taskName" /></th>
									<th width="18%"><spring:message
											code="dashboard.actions.action"
											text="dashboard.actions.action" /></th>
									<th><spring:message code="dashboard.actions.actor.name"
											text="dashboard.actions.actor.name" /></th>
									<th><spring:message code="dashboard.actions.actor.email"
											text="dashboard.actions.actor.email" /></th>
									<th><spring:message
											code="dashboard.actions.actor.designation"
											text="dashboard.actions.actor.designation" /></th>
									<th width="20%"><spring:message
											code="dashboard.actions.remarks"
											text="dashboard.actions.remarks" /></th>
									<%--  <th><spring:message code="care.attachments" text="Attachments"/></th> --%>
								</tr>
								<c:set var="rowCount" value="0" scope="page" />
								<c:forEach items="${actions}" var="action" varStatus="status">
									<tr>
										<td><c:set var="rowCount" value="${rowCount+1}"
												scope="page" />
											<c:out value="${rowCount}"></c:out></td>
										<td><fmt:formatDate pattern="dd/MM/yyyy hh:mm a"
												value="${action.dateOfAction}" /></td>
										<td><c:out value="${action.taskName}"></c:out></td>
										<c:set var="statusString" value="${action.decision}" />
										<td><spring:message
												code="workflow.action.decision.${fn:toLowerCase(statusString)}"
												text="${action.decision}" />
										<td><c:out value="${action.empName}"></c:out></td>
										<td><c:out value="${action.empEmail}"></c:out></td>
										<td><c:out value="${action.empGroupDescEng}"></c:out></td>
										<td><c:out value="${action.comments}"></c:out></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>