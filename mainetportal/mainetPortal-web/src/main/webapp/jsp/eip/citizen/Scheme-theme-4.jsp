<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<ol class="breadcrumb">
      <li><a href="CitizenHome.html"><spring:message code="home" text="Home"/></a></li>
      <li class="active"><spring:message code="theme4.portal.budget" text="Budget" /></li>
</ol>

<div class="content animated">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="theme4.portal.budget" text="Budget" /></h2>
		</div>
		<div class="widget-content padding">
			<div class="scheme-view">
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
								<c:if test="${lookUp.isUsefullLink eq 'Y'}">
									<tr>
										<td style="width: 80%">
											<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:out value="${lookUp.noticeSubEn}" default=""/>
											</c:if>
											<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
												<c:out value="${lookUp.noticeSubReg}" default=""/>
											</c:if>
										</td>
										<fmt:formatDate value="${lookUp.issueDate}" var="formattedDate" type="date" pattern="dd-MM-yyyy" />
										<td style="width: 10%">${formattedDate}</td>
										<td style="width: 10%">
											<c:if test="${not empty lookUp.profileImgPath}">								
												<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
												<c:forEach items="${links}" var="download" varStatus="count">
													<a href="./cache/${download}" target="_blank" class="">&nbsp;<i class="fa fa-download"></i>&nbsp;</a>
												</c:forEach>
											</c:if>
											<c:set var="exlink" value="${lookUp.link}" />
											<c:if test="${not empty exlink}">
												
												<a href="${exlink}" target="_blank" class="">&nbsp;<i class="fa fa-external-link" style="font-size:24px;color:red"></i>&nbsp;</a>
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
</div>