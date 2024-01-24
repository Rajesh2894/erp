<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<ol class="breadcrumb">
      <li  class="breadcrumb-item"><a href="CitizenHome.html"><strong class="fa fa-home"></strong> Home </a></li>
      <li class="breadcrumb-item active"><spring:message code="portal.Scheme" text="Schemes"/></li>
</ol>
<div class="widget-content padding">

<div class="container-fluid dashboard-page">
	<div class="col-sm-12" id="nischay">
		<h3><spring:message code="portal.Scheme" text="Schemes"/></h3>
		<div class="widget">
			<div class="widget-content padding">



			
			<div class="scheme-view">
				<table class="table table-bordered dataTableClass">
					<thead>
						<tr>
							<th><spring:message code="rti.srno" text="SR NO"/></th>
							<th><spring:message code="portal.Scheme" text="SCHEMES"/></th>
						</tr>
					</thead>
					<tbody>
					<c:set var="schemeCount" value="0" scope="page"/>
						<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
											<c:if test="${lookUp.isHighlighted ne 'Y' && lookUp.isUsefullLink ne 'Y'}">
											<c:set var="schemeCount" value="${schemeCount + 1}" scope="page"/>
												<tr>
												<td style="width:10%">${schemeCount}</td>
													<c:choose>
															<c:when test="${not empty lookUp.profileImgPath }">
															
															
															<c:set var="link" value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />	
																		<td>
																			<c:if test="${lookUp.imagePath ne ' ' }">
																					<c:set var="search" value="\\" />
																					<c:set var="replace" value="\\\\" />
																					<c:set var="path" value="${fn:replace(link,search,replace)}" />
																					<c:if test="${empty lookUp.detailEn}"><a href="javascript:void(0);" onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')" ><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.imagePath }"></a></c:if>	
																					<c:if test="${not empty lookUp.detailEn}"><div class="banner-img"><a href="javascript:void(0);" onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')" ><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.detailEn }"></a> </div></c:if>	
																			</c:if>
																			<c:choose>
																				<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<c:if test="${lookUp.imagePath eq' ' }"><p class="padding-18"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailEn}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></p></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq' ' }"><p class="padding-18"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailReg}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></p></c:if>
																				</c:otherwise>
																			</c:choose>
																			
																		</td>
																
															</c:when>
															<c:otherwise>
															
																
																			<td>
																			<c:if test="${lookUp.imagePath ne ' '}">
																				<c:if test="${not empty lookUp.detailEn}"><div class="banner-img"><a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new"><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.detailEn }"></a></div></c:if>
																				<c:if test="${empty lookUp.detailEn}"><a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new"><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.imagePath }"></a></c:if>
																			</c:if>
													
																			
																			<c:choose>
																				<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<c:if test="${lookUp.imagePath eq ' '}"><p class="padding-18"></c:if>	<a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new">${lookUp.detailEn}</a><c:if test="${lookUp.imagePath eq ' ' }"></p></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq ' '}"><p class="padding-18"></c:if>	<a class="title" title="${lookUp.detailReg}" href="${lookUp.link}" target="new">${lookUp.detailReg}</a><c:if test="${lookUp.imagePath eq ' ' }"></p></c:if>
																				</c:otherwise>
																			</c:choose>
																			</td>
																			
																</c:otherwise>
													</c:choose> 
													</tr>
													</c:if>
									</c:forEach>
					</tbody>
				</table>
					
				
			</div>
		
	</div>
	</div>
	</div>
	</div>
</div>	