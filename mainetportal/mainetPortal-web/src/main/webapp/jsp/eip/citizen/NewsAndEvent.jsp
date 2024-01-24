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
      <li class="breadcrumb-item active"><spring:message code="news.event" text="News and Events"/></li>
</ol>
<!-- <div class="widget-content padding"> -->
<div class="container-fluid dashboard-page">
	<div class="col-sm-12" id="nischay">
		<h3><spring:message code="news.event" text="News and Events"/></h3>
		<div class="widget">
			<div class="widget-content padding">
				<div class="row row-eq-height">
							
				
					<div class="col-sm-8">
					<div class="news-box">
							<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">
											<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
											<c:forEach items="${links}" var="download" varStatus="count">
												<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
												<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
												<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />
							
															<div class="all-news-events">
																<div class="col-sm-4">
																	<img src="${lookUp.attachImage}" class="img-responsive" alt="${lookUp.announceDescEng}">
																</div>
																<div class="col-sm-8">
																	<div class="content-news">
																		<h4>
																			<apptags:filedownload filename="EIP" filePath="${download}"
																				actionUrl="CitizenHome.html?Download"
																				eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
																		</h4>
																		<span class="date"><fmt:formatDate type="date"
																				value="${lookUp.newsDate}" dateStyle="SHORT" pattern="dd/MM/yyyy"/></span>
																	</div>
																</div>
															</div>
							
												</c:forEach>
										</c:forEach>
					
						
					
					
					</div>
					</div>

					<div class="col-sm-4">
						<div class="popular-box">
							<h1 class="text-center">Popular News</h1>

							<div class="owl-carousel" id="news-carousel">
							
									<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">
											<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
											<c:forEach items="${links}" var="download" varStatus="count">
												<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
												<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
												<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />



								<div class="item">
									<div class="col-xs-12">
										<img src="${lookUp.attachImage}" class="img-responsive" alt="${lookUp.announceDescEng}">
										<div class="popular-box-content">
											<p><apptags:filedownload filename="EIP" filePath="${download}"
																				actionUrl="CitizenHome.html?Download"
																				eipFileName="${lookUp.announceDescEng}"></apptags:filedownload></p>
										</div>
									</div>
								</div>
								
								</c:forEach>
								</c:forEach>
								
								
								
								
							</div>
						</div>

					</div>




				</div>
			</div>
		</div>
	</div>
</div>
<!-- </div> -->