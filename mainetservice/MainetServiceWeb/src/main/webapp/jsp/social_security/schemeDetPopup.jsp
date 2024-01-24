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
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"></i></a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form class="form-horizontal" name="Application History">
					<h4 align="center" class="margin-top-0">
						<spring:message
							text="Details of the scheme previously Benefited by applicant"
							code="soc.heading.historyDetails" />
					</h4>
					<div id="ActionHistory" class="panel-collapse collapse in">
						<div class="table-responsive">
							<table class="table table-bordered table-condensed">
								<tr>
									<th><spring:message code="soc.srno" text="Sr. No." /></th>

									<th width="18%"><spring:message code="soc.benSchemeId"
											text="Benefited Scheme No" /></th>
									<th><spring:message code="soc.benSchemename"
											text="Benefited Scheme Name" /></th>
									<th><spring:message code="soc.year" text="Year" /></th>
									<th><spring:message code="soc.amt" text="Benefited Amount" /></th>

								</tr>
								<c:set var="rowCount" value="0" scope="page" />


								<c:forEach items="${command.applicantDtoList}" var="action"
									varStatus="status">

									<tr>
										<td><c:set var="rowCount" value="${rowCount+1}"
												scope="page" /> <c:out value="${rowCount}"></c:out></td>

										<td>${action.selectSchemeName}</td>
										<td>${action.servDesc}</td>
										<td><fmt:formatDate pattern="dd/MM/yyyy"
												value="${action.validtoDate}" /></td>
										<td>${action.benfitedAmt}</td>

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