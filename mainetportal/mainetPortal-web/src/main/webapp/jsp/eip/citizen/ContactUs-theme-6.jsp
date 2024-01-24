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
<jsp:useBean id="marathiConvert" class="com.abm.mainet.common.util.Utility"></jsp:useBean>
<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="CitizenHome.html"><strong
			class="fa fa-home margin-right-5"></strong>
		<spring:message code="home" text="Home" /></a></li>
	<li class="breadcrumb-item active"><spring:message code="theme6.portal.contact.us" text="Contact Us" /></li>
</ol>

<div class="internal-page-content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="theme6.portal.contact.us" text="Contact Us" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="table-responsive">
				<table class="table table-bordered table-striped dataTableNormal">
					<thead>
						<tr>
						    <th><spring:message code="eip.admin.contactUs.dept.name" text="Department"/></th>
							<th><spring:message code="eip.admin.contactUs.name" text="Name of HOD/ Ward Officer"/></th>
							<th><spring:message code="Employee.designation.dsgid" text="Designation"/></th>
							<th><spring:message code="eip.admin.contactUs.phoneNo" text="Telephone No."/></th>
							<th><spring:message code="eip.admin.contactUs.emailAddress" text="Email Address"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${command.organisationContactList }" var="contact">
							<c:if test ="${contact.flag eq 'P' || contact.flag eq 'S'}">
								<tr>
									<c:if test="${ contact.designationEn ne 'P.S. To Minister'}">
									<td>
											<c:choose>
												<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
													${contact.departmentEn}
												</c:when>
												<c:otherwise>
													${contact.departmentReg} 
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
													<strong>${contact.contactNameEn}</strong>
												</c:when>
												<c:otherwise>
													<strong>${contact.contactNameReg}</strong>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
													${contact.designationEn}
												</c:when>
												<c:otherwise>
													${contact.designationReg}
												</c:otherwise>
											</c:choose>
										</td>
										<td class="ph-num">
											<c:choose>
												<c:when test="${userSession.languageId eq 1}">
													<a href="tel:${contact.telephoneNo1En}">${contact.telephoneNo1En}</a>
												</c:when>
												<c:otherwise>
													<a href="tel:${marathiConvert.convertToRegional('marathi',contact.telephoneNo1En)}">
														${marathiConvert.convertToRegional("marathi",contact.telephoneNo1En)}
													</a>
												</c:otherwise>
											</c:choose>
										</td>
										<td class="email-addr">
											<a href="mailto:${contact.email1}" title="${contact.email1}">
												${contact.email1}
											</a>
										</td>
									</c:if>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>
