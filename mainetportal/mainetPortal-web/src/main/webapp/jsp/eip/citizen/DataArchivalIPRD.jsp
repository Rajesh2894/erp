<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility"
	class="com.abm.mainet.common.util.StringUtility" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<style>
div.bhoechie-tab-container {
	z-index: 10;
	background-color: #ffffff;
	padding: 0 !important;
}

div.bhoechie-tab-menu {
	padding-right: 0;
	padding-left: 0;
	padding-bottom: 0;
}

div.bhoechie-tab-menu div.list-group {
	margin-bottom: 0;
	background:
}

div.bhoechie-tab-menu div.list-group>a {
	margin-bottom: 0;
	background: #474747;
	color: #fff;
}

div.bhoechie-tab-menu div.list-group>a .glyphicon, div.bhoechie-tab-menu div.list-group>a .fa
	{
	color: #5A55A3;
}

div.bhoechie-tab-menu div.list-group>a:first-child {
	border-top-right-radius: 0;
	-moz-border-top-right-radius: 0;
}

div.bhoechie-tab-menu div.list-group>a:last-child {
	border-bottom-right-radius: 0;
	-moz-border-bottom-right-radius: 0;
}

div.bhoechie-tab-menu div.list-group>a.active {
	background-color: #5A55A3;
	background-image: #5A55A3;
	color: #ffffff;
}

div.bhoechie-tab-menu div.list-group>a.active:after {
	content: '';
	position: absolute;
	left: 100%;
	top: 50%;
	margin-top: -13px;
	border-left: 0;
	border-bottom: 13px solid transparent;
	border-top: 13px solid transparent;
	border-left: 10px solid #5A55A3;
}

div.bhoechie-tab-content {
	background-color: #ffffff;
	padding-left: 20px;
	padding-top: 10px;
}
div.bhoechie-tab-content h3{font-size: 2em !important;
    font-weight: 400;
    padding: 15px 0px;
    color: #a51e28 !important;}
/* div.bhoechie-tab div.bhoechie-tab-content:not (.active ){
	display: none !important;
} */

.bhoechie-tab-content {
  display: none;
}
.bhoechie-tab-content.active {
  display: block;
}


</style>

<script>
	$(document).ready(
			function() {
				$("div.bhoechie-tab-menu>div.list-group>a").click(
						function(e) {
							e.preventDefault();
							$(this).siblings('a.active').removeClass("active");
							$(this).addClass("active");
							var index = $(this).index();
							$("div.bhoechie-tab>div.bhoechie-tab-content")
									.removeClass("active");
							$("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
						});
			});
</script>

<div class="container-fluid dashboard-page">




	<div class="col-sm-12" id="nischay">

	<%--  <c:set var="lookUpList" value="${command.sectionInformation}"/>  --%>
			

		<div class="widget">

			<div class="widget-content padding">




				<div class="col-sm-12">

					<form action="" method="get" class="form-horizontal" novalidate>
						<div class="panel-group accordion-toggle"
							id="accordion_single_collapse">
							<div class="panel panel-default">
								<div class="panel-heading margin-bottom-20">
									<h4 class="panel-title">Data Archival</h4>
								</div>
								<div id="accordion1" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="container">
											<div class="row">
												<div
													class="col-lg-12 col-md-12 col-sm-12 col-xs-12 bhoechie-tab-container">
													<div
														class="col-lg-2 col-md-2 col-sm-2 col-xs-2 bhoechie-tab-menu">
														<div class="list-group ">
															<a href="#" class="list-group-item active text-center">
															Tenders</a> 
															<a href="#" class="list-group-item text-center">
															Recruitment</a> 
															<a href="#" class="list-group-item text-center">
															Schemes</a>
															<a href="#" class="list-group-item text-center">
															Highlightes</a>
															<!-- <a href="#"	class="list-group-item text-center">
															Important Link</a>
															<a href="#" class="list-group-item text-center">
															Public Notices</a> -->
														</div>
													</div>
													
													<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 bhoechie-tab">
													
		<!-- ----------------------------------------New Tenders------------------------------------------- -->		
											
														<div class="bhoechie-tab-content active">
												   		<c:set var="lookUpList" value="${command.getSectionInformation('Tenders')}" />
														<h3>Tenders</h3>
													
																<c:if test="${fn:length(lookUpList) == 0}">
																
																	<div class="text-center">
																	<span>No Archived Tender</span>
																	</div>
																</c:if>
																	<c:forEach items="${lookUpList}" var="lookUps"
																		varStatus="lk">

														
						<c:forEach items="${lookUps}" var="lookUp">
						<c:if test="${lookUp.lookUpType eq 8}">
							${lookUp.lookUpDesc}
						</c:if>
						</c:forEach>
					</c:forEach>
																<%-- <table class="table table-bordered">
																<tbody>
																<c:if test="${fn:length(lookUpList) == 0}">
																<tr>
																<td>
																	<div class="text-center">
																	<span>No Archived Tender</span>
																	</div>
																	</td>
																	</tr>
																</c:if>
																	<c:forEach items="${lookUpList}" var="lookUps"
																		varStatus="lk">
																		<c:if test="${lk.index eq 0}">
																			<tr>
																				<c:forEach items="${lookUps}" var="lookUp">
																					<c:if
																						test="${not empty lookUp.lookUpCode && lookUp.lookUpType ne MainetConstants.TEXT_AREA_HTML}">
																						<th>${lookUp.lookUpCode}</th>
																					</c:if>
																				</c:forEach>
																			</tr>
																		</c:if>
																		<tr>
																		
																		<c:forEach items="${lookUps}" var="lookUp">
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.TEXT_AREA_HTML}">
																				<td>${lookUp.lookUpDesc}</td>
																			</c:if>

																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD}">
																				<td>${lookUp.lookUpDesc}</td>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.TEXT_AREA}">
																				<td>${lookUp.lookUpDesc}</td>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.DATE_PICKER}">
																				<td><p class="arial">${lookUp.lookUpDesc}</p></td>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG}">
																				<c:choose>
																					<c:when test="${not empty lookUp.lookUpDesc}">
																						<td><c:set var="imgs"
																								value="${fn:split(lookUp.lookUpDesc, '|')}" />
																							<c:forEach items="${imgs}" var="img">
																								<a class="fancybox" href="${img}"
																									data-fancybox-group="images"> <img
																									src="${img}" alt="${img}" class="img-thumbnail"
																									width="300px"></a>
																							</c:forEach></td>
																					</c:when>
																					<c:otherwise>
																						<td><div align="center">
																								<spring:message code="eip.quicklinkNoImage" />
																							</div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.VIDEO}">
																				<c:choose>
																					<c:when test="${not empty lookUp.lookUpDesc}">
																						<td><c:set var="videos"
																								value="${fn:split(lookUp.lookUpDesc, '|')}" />
																							<c:forEach items="${videos}" var="video">
																								<video width="100%" controls>
																									<source src="${video}"
																										type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
																								</video>
																							</c:forEach></td>
																					</c:when>
																					<c:otherwise>
																						<td><div align="center">
																								<spring:message code="" text="Video Not found" />
																							</div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.ATTACHMENT_FIELD}">

																				<c:set var="links"
																					value="${fn:split(lookUp.lookUpDesc, ',')}" />
																				<td><c:forEach items="${links}" var="download"
																						varStatus="status">

																						<c:set var="link"
																							value="${stringUtility.getStringAfterChar('/',download)}" />
																						<c:set var="path"
																							value="${stringUtility.getStringBeforeChar('/',download)}" />
																						<c:if test="${not empty lookUp.lookUpDesc}">
																							
																							<apptags:filedownload filename="${link}"
																								filePath="${path}"
																								actionUrl="SectionInformation.html?Download"></apptags:filedownload>

																						</c:if>
																						<c:if test="${empty lookUp.lookUpDesc}">
																						</c:if>

																					</c:forEach></td>
																			</c:if>

																		</c:forEach>
																		
																		</tr>
																	</c:forEach>
																	</tbody>
																</table>  --%>

														</div>
														
<!-- ----------------------------------------New Tenders End------------------------------------------- -->		
<!-- ----------------------------------------Recruitment------------------------------------------- -->		
<div class="bhoechie-tab-content">
												   		<c:set var="lookUpList" value="${command.getSectionInformation('Recruitment')}" />
												   	
														<h3>Recruitment</h3>
																<table class="table table-bordered">
																<tbody>
																<c:if test="${fn:length(lookUpList) == 0}">
																<tr>
																<td>
																	<div class="text-center">
																	<span>No Archived Recruitment</span>
																	</div>
																	</td>
																	</tr>
																</c:if>
																	<c:forEach items="${lookUpList}" var="lookUps"
																		varStatus="lk">
																		<c:if test="${lk.index eq 0}">
																			<tr>
																				<c:forEach items="${lookUps}" var="lookUp">
																					<c:if
																						test="${not empty lookUp.lookUpCode && lookUp.lookUpType ne MainetConstants.TEXT_AREA_HTML}">
																						<th>${lookUp.lookUpCode}</th>
																					</c:if>
																				</c:forEach>
																			</tr>
																		</c:if>
																		<tr>
																		
																		<c:forEach items="${lookUps}" var="lookUp">
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.TEXT_AREA_HTML}">
																				<td>${lookUp.lookUpDesc}</td>
																			</c:if>

																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD}">
																				<td>${lookUp.lookUpDesc}</td>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.TEXT_AREA}">
																				<td>${lookUp.lookUpDesc}</td>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.DATE_PICKER}">
																				<td><p class="arial">${lookUp.lookUpDesc}</p></td>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG}">
																				<c:choose>
																					<c:when test="${not empty lookUp.lookUpDesc}">
																						<td><c:set var="imgs"
																								value="${fn:split(lookUp.lookUpDesc, '|')}" />
																							<c:forEach items="${imgs}" var="img">
																								<a class="fancybox" href="${img}"
																									data-fancybox-group="images"> <img
																									src="${img}" alt="${img}" class="img-thumbnail"
																									width="300px"></a>
																							</c:forEach></td>
																					</c:when>
																					<c:otherwise>
																						<td><div align="center">
																								<spring:message code="eip.quicklinkNoImage" />
																							</div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.VIDEO}">
																				<c:choose>
																					<c:when test="${not empty lookUp.lookUpDesc}">
																						<td><c:set var="videos"
																								value="${fn:split(lookUp.lookUpDesc, '|')}" />
																							<c:forEach items="${videos}" var="video">
																								<video alt="Videos" width="100%" controls>
																									<source src="${video}"
																										type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
																								</video>
																							</c:forEach></td>
																					</c:when>
																					<c:otherwise>
																						<td><div align="center">
																								<spring:message code="" text="Video Not found" />
																							</div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:if>
																			<c:if
																				test="${lookUp.lookUpType==MainetConstants.ATTACHMENT_FIELD}">

																				<c:set var="links"
																					value="${fn:split(lookUp.lookUpDesc, ',')}" />
																				<td><c:forEach items="${links}" var="download"
																						varStatus="status">

																						<c:set var="link"
																							value="${stringUtility.getStringAfterChar('/',download)}" />
																						<c:set var="path"
																							value="${stringUtility.getStringBeforeChar('/',download)}" />
																						<c:if test="${not empty lookUp.lookUpDesc}">
																							
																							<apptags:filedownload filename="${link}"
																								filePath="${path}"
																								actionUrl="SectionInformation.html?Download"></apptags:filedownload>

																						</c:if>
																						<c:if test="${empty lookUp.lookUpDesc}">
																						</c:if>

																					</c:forEach></td>
																			</c:if>

																		</c:forEach>
																		
																		</tr>
																	</c:forEach>
																	</tbody>
																</table> 

														</div>
		<!-- ----------------------------------------Recruitment End------------------------------------------- -->														
														
		<!-- --------------------------------------------Schemes----------------------------------------------- -->														
														
														
														<div class="bhoechie-tab-content">
														<h3>Schemes</h3>
																<table class="table table-bordered">
					<thead>
						<tr>
							<th><spring:message code="rti.srno" text="SR NO"/></th>
							<th><spring:message code="portal.Scheme" text="SCHEMES"/></th>
						</tr>
					</thead>
					<tbody>
					<c:set var="schemeCount" value="0" scope="page"/>
					<c:if test="${command.scheme ne true}">
					<tr>
																<td colspan="2">
																	<div class="text-center">
																	<span>No Archived Schemes</span>
																	</div>
																	</td>
																	</tr></c:if>
																	<c:if test="${command.scheme eq true}">
						<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
											<c:if test="${lookUp.isHighlighted ne 'Y' && lookUp.isUsefullLink ne 'Y'}">
											<c:set var="schemeCount" value="${schemeCount + 1}" scope="page"/>
												<tr>
												<td>${schemeCount}</td>
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
																					<c:if test="${lookUp.imagePath eq' ' }"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailEn}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq' ' }"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailReg}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></c:if>
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
									</c:if>
					</tbody>
				</table>
														</div>
														
<!-- --------------------------------------------Schemes End----------------------------------------------- -->														
														
<!-- --------------------------------------------Highlightes----------------------------------------------- -->														
														
														
														<div class="bhoechie-tab-content">
														<h3>Highlightes</h3>
																<table class="table table-bordered">
					<thead>
						<tr>
							<th><spring:message code="rti.srno" text="SR NO"/></th>
							<th><spring:message code="" text="HIGHLIGHTES"/></th>
						</tr>
					</thead>
					<tbody>
					<c:set var="Count" value="0" scope="page"/>
					<c:if test="${command.highlighted ne true}">
					<tr>
																<td colspan="2">
																	<div class="text-center">
																	<span>No Archived Highlightes</span>
																	</div>
																	</td>
																	</tr></c:if>
																	<c:if test="${command.highlighted eq true}">
						<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
											<c:if test="${lookUp.isHighlighted eq 'Y'}">
											<c:set var="count" value="${count + 1}" scope="page"/>
												<tr>
												<td>${count}</td>
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
																					<c:if test="${lookUp.imagePath eq' ' }"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailEn}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq' ' }"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailReg}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></c:if>
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
									</c:if>
					</tbody>
				</table>
														</div>
	<!-- --------------------------------------------Highlightes End----------------------------------------------- -->																
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	</div>


