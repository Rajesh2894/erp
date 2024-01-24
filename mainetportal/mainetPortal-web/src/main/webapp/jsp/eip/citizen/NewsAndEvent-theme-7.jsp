<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<link href="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
 
<script>
	$(document).ready(function() {
		$('.popular-box #news-carousel').owlCarousel({
			loop : true,
			margin : 10,
			animateOut : 'slideOutDown',
			animateIn : 'flipInX',
			 autoPlay:true,
			    autoPlayTimeout:4000,
			    autoplayHoverPause:true,

			items : 1,
			nav : true,
			responsive : {
				0 : {
					items : 1
				},
				600 : {
					items : 1
				},
				1000 : {
					items : 1
				}
			}
		})
	});
	
</script>

<ol class="breadcrumb">
      <li  class="breadcrumb-item"><a href="CitizenHome.html"><strong class="fa fa-home"></strong> Home </a></li>
      <li class="breadcrumb-item active"><spring:message code="theme4.portal.newsletter" text="Newsletter" /></li>
</ol>
<div class="container-fluid dashboard-page">
	<div class="col-sm-12" id="nischay">
		<div class="widget">
			
				<h2><spring:message code="theme7.portal.newsletter" text="Newsletter" /></h2>
			
			<div class="widget-content padding">
				<div class="row">					
					<%-- News Box starts --%>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="news-box">
							<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">
								<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
								<c:forEach items="${links}" var="download" varStatus="count">
									<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
									<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
									<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />
									<div class="all-news-events col-sm-4">
										<%-- <div class="col-sm-4">
											<img src="${lookUp.attachImage}" class="img-responsive" alt="${lookUp.announceDescEng}">
										</div> --%>
										<div class="col-sm-12">
											<div class="content-news">
											<div class="heading col-sm-6 col-md-8 col-xs-12">
													<div><c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() eq 1}">										
															${lookUp.announceDescEng}
														</c:when>
														<c:otherwise>
															${lookUp.announceDescReg}
														</c:otherwise>
													</c:choose></div>
													<span class="date"><fmt:formatDate type="date"
														value="${lookUp.newsDate}" dateStyle="SHORT" pattern="dd/MM/yyyy"/></span>
												</div>
											<div class="col-md-4 col-sm-6 col-xs-12 left-side">
												<c:if test="${not empty lookUp.attach}">
														<apptags:filedownload filename="EIP" filePath="${download}"
														actionUrl="CitizenHome.html?Download" showIcon="true" docImage="true">
														</apptags:filedownload>
														
														<%-- <apptags:filedownload filename="${download}"
		                                                filePath="${path}" showIcon="true" docImage="true"
		                                                actionUrl="CitizenHome.html?Download"></apptags:filedownload>
		                                                <jsp:useBean id="test" class="com.abm.mainet.common.util.Utility"/>
		                                      			  <c:set value="${test.fileSize(path, link)}" var="fileSize"></c:set> --%>
		                                        	 <%-- <strong style="display: block; text-align: center;">${link}<br>${fileSize}</strong>
		                                        	<h7 style="display: block; text-align: center;"><spring:message code="important.notice.updated.date" text="Last Updated Date" />&nbsp; ${updatedDate}</h7> --%> 
													
												</c:if>
												<c:if test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
													<a href="${lookUp.link }" target="_blank" class="extlink external"><i class="fa fa-download" aria-hidden="true"></i></a>
												</c:if>
											</div>
												
											</div>
										</div>
									</div>
								</c:forEach>
							</c:forEach>
						</div>
					</div>
					<%-- News Box ends --%>
					
				</div>
			</div>
		</div>
	</div>
</div>