<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="java.util.Date" %>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" var="myDate"/> 

<jsp:useBean id="stringUtility"
	class="com.abm.mainet.common.util.StringUtility" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="CitizenHome.html"><strong
			class="fa fa-home margin-right-5"></strong>
		<spring:message code="home" text="Home" /></a></li>
	<li class="breadcrumb-item active"><spring:message code="theme6.portal.external.links" text="External Links" /></li>
</ol>

<div class="internal-page-content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="theme6.portal.external.links" text="External Links" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="table-responsive">
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
							<fmt:formatDate value="${lookUp.issueDate}" pattern="yyyy-MM-dd HH:mm:ss" var="iDate"/> 
							<fmt:formatDate value="${lookUp.validityDate}" pattern="yyyy-MM-dd HH:mm:ss" var="vDate"/> 
							<c:if test="${lookUp.isUsefullLink eq 'T' &&  iDate le myDate &&  myDate le vDate}">
	
								<tr>
									<td width="80%">
										<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
												<c:out value="${lookUp.noticeSubEn}" default=""/>
										</c:if>
										<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
											<c:out value="${lookUp.noticeSubReg}" default=""/>
										</c:if>
										<c:if test="${lookUp.isHighlighted eq 'Y'}">
											<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
										</c:if>
									</td>
									<fmt:formatDate value="${lookUp.issueDate}" var="formattedDate" type="date" pattern="dd-MM-yyyy" />
									<td width="10%">${formattedDate}</td>
									<td width="10%">
										<c:if test="${not empty lookUp.profileImgPath}">								
											<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
											<c:forEach items="${links}" var="download" varStatus="count">
												<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />
                                 				<c:set var="path" value="${stringUtility.getStringBeforeChar('/',download)}" />
                                             <c:if test="${lookUp.linkType eq 'R' }">
	                                             <apptags:filedownload filename="${link}" 
	                                                filePath="${path}" showIcon="true" docImage="true"
	                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
	                                         </c:if>
											</c:forEach>
										</c:if>
										<c:set var="exlink" value="${lookUp.link}" />
										<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
											
											<a href="${exlink}" target="_blank" class="ext-link"><i class="fa fa-external-link"></i></a>
										</c:if>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>
