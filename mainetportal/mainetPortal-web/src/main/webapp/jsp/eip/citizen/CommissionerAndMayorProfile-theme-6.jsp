<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="java.util.Date" %>

<c:set var="langFlag" value ="${userSession.getCurrent().getLanguageId() eq 1}"/>
		<c:set var ="isCommAndNotMar" value ="${command.mayorOrCommisnrFlag eq 'DM' }"/>
		<c:set var = "commOrMayrList" value =""/>
		<c:choose>
		<c:when test = "${isCommAndNotMar }">
		<c:set var = "commOrMayrList" value ="${command.commissinorProfile }"/>
		</c:when>
		<c:otherwise>
		<c:set var = "commOrMayrList" value ="${command.mayorProfile }"/>
		</c:otherwise>
		</c:choose>
<jsp:useBean id="stringUtility"
	class="com.abm.mainet.common.util.StringUtility" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="CitizenHome.html"><strong
			class="fa fa-home margin-right-5"></strong>
		<spring:message code="home" text="Home" /></a></li>
	<li class="breadcrumb-item active"><c:choose>
			<c:when test="${isCommAndNotMar }">
				<spring:message code="theme6.portal.commissioner.profile"
					text="Commissioner Profile" />
			</c:when>
			<c:otherwise>
				<spring:message code="theme6.portal.mayor.profile"
					text="Mayor Profile" />
			</c:otherwise>
		</c:choose>
	</li>
</ol>

<div class="internal-page-content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<c:choose>
					<c:when test="${isCommAndNotMar }">
						<spring:message code="theme6.portal.commissioner.profile"
							text="Commissioner Profile" />
					</c:when>
					<c:otherwise>
						<spring:message code="theme6.portal.mayor.profile"
							text="Mayor Profile" />
					</c:otherwise>
				</c:choose>
			</h2>
		</div>
		
		<div class="widget-content padding">
			<div class="table-responsive">
				<table class="table table-bordered table-striped">
				<c:set var="emailvar" value="0" scope="page"/>
							<c:set var="contvar" value="0" scope="page"/>
							<c:set var="doj" value="0" scope="page"/>
					<thead>
						<tr>
						    <th><spring:message code="Com.Title" text="Tittle" /></th>
							<th><spring:message code="mayorForm.Name" text="Name" /></th>
							<th><spring:message code="mayorForm.Designation" text="Designation" /></th>
							<th><spring:message code="Com.Profile" text="Profile" /></th>
							
							<c:forEach items="${commOrMayrList}" var="lookUp" varStatus="lk">
								<c:if test="${not empty lookUp.emailId && emailvar==0}">
									<th><spring:message code="eip.admin.auth.email"
											text="Email" /></th>
									<c:set var="emailvar" value="${emailvar+1}" />
								</c:if>

								<c:if test="${not empty lookUp.summaryEng && contvar==0 }">
									<th><spring:message code="mayorForm.msg" text="Contact No" /></th>
									<c:set var="contvar" value="${contvar+1}" />
								</c:if>

								<c:if test="${not empty lookUp.dtOfJoin && doj ==0 }">
									<th><spring:message code="admin.commissioner.doj"
											text="Date Of Joining" /></th>
									<c:set var="doj" value="${doj+1}" />

								</c:if>
							</c:forEach>
							<th><spring:message code="mayorForm.ProfileImage" text="Profile Image" /></th>
							<%-- <th><spring:message code="admin.commissioner.doj" text="Date Of Joining" /></th> --%>
								
						</tr>
					</thead>
					
					<tbody>
					<c:set var = "space" value = " " />
						<c:forEach items="${commOrMayrList}" var="lookUp" varStatus="lk">
							<tr>
								<td>
								    <c:choose>
										<c:when test ="${langFlag }">
		                                    ${lookUp.linkTitleEn }
		                                </c:when>
										<c:otherwise>
		                                      ${lookUp.linkTitleReg }
		                                 </c:otherwise>
									</c:choose>
							  </td>
							  <td>
								<c:choose>
										<c:when test ="${langFlag }">
		                                    ${lookUp.pNameEn }
		                                </c:when>
										<c:otherwise>
		                                      ${lookUp.pNameReg }
		                                 </c:otherwise>
								</c:choose>
							  </td>
							  <td>
								<c:choose>
										<c:when test ="${langFlag }">
		                                 ${lookUp.designationEn}  
		                                </c:when>
										<c:otherwise>
		                                ${lookUp.designationReg} 
		                                 </c:otherwise>
								</c:choose>
							  </td>
							  <td>
								    <c:choose>
										<c:when test ="${langFlag }">
		                                    ${lookUp.profileEn }
		                                </c:when>
										<c:otherwise>
		                                      ${lookUp.profileReg }
		                                 </c:otherwise>
									</c:choose>
							  </td>
							  
							  <c:if test="${not empty lookUp.emailId }">
							  <td> ${lookUp.emailId }</td>
							  </c:if>
							  
							  <c:if test="${not empty lookUp.summaryEng}">
							  <td>${lookUp.summaryEng }</td>
							  </c:if>
							   		  
							   <c:if test="${not empty lookUp.dtOfJoin }">
								  <td>
									<%-- ${fn:substring(lookUp.dtOfJoin, 0,11) } --%>
									<fmt:parseDate pattern="yyyy-MM-dd" value="${fn:substring(lookUp.dtOfJoin, 0,11) }" var="parsedDate" /> 
									<fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
									</td>
								</c:if>

								<td>
									<div class="member-profile-image">
										<img src="${lookUp.imagePath}" alt="${lookUp.imageName}"
											title="${lookUp.imageName}" class="img-responsive">
									</div>
								</td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>
