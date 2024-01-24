<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility"
	class="com.abm.mainet.common.util.StringUtility" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="CitizenHome.html"><strong
			class="fa fa-home margin-right-5"></strong>
		<spring:message code="home" text="Home" /></a></li>
	<li class="breadcrumb-item active"><spring:message code="theme5.portal.quick.links" text="Quick Links" /></li>
</ol>

<div class="content animated">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="theme5.portal.quick.links" text="Quick Links" /></h2>
		</div>
		<div class="widget-content padding">
			<table class="table table-bordered table-striped dataTableNormal">
				<thead>
					<tr>
						<th><spring:message code="theme3.portal.details.info" text="Details/Information" /></th>
						<th><spring:message code="theme3.portal.issue.date" text="Issue Date" /></th>
						<th><spring:message code="theme3.portal.download" text="Download" /></th>
						
					</tr>
				</thead>
				<tbody>
					<c:set var="schemeCount" value="0" scope="page" />
					<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
						<c:if test="${lookUp.isUsefullLink ne 'Y' && empty lookUp.isHighlighted && lookUp.isHighlighted ne 'Y'}">

							<tr>
								<td style="width: 80%">
									<c:choose>
										<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
											${lookUp.noticeSubEn}
										</c:when>
										<c:otherwise>
											${lookUp.noticeSubReg}
										</c:otherwise>
									</c:choose>
								</td>
								<fmt:formatDate value="${lookUp.issueDate}" var="formattedDate" type="date" pattern="dd-MM-yyyy" />
								<td style="width: 10%">${formattedDate}</td>
								<td style="width: 10%" class="text-center">
									<c:set var="exlink" value="${lookUp.link}" />
									<c:if test="${not empty exlink}">
										<a href="${exlink}" target="_blank" class="">&nbsp;<i class="fa fa-external-link" style="color:red"></i>&nbsp;</a>
									</c:if>
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			<div class="clear"></div>

		</div>
	</div>
</div>
