<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>

<!-- <link href="https://fonts.googleapis.com/css?family=Alegreya+Sans" rel="stylesheet"> -->
<link rel="stylesheet" href="assets/libs/bootstrap/css/bootstrap.min.css">
<link href="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.min.css" rel="stylesheet" type="text/css" />
<script	src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<link href="assets/css/style-blue-theme-2.css" rel="stylesheet" type="text/css" />
<style>
.displayimagef,.displayimage{float: left;height:90px;overflow:hidden}
.displayimagef img{height: 90px;width: 100%;}
.displayimage img{height: 90px;width: 100px;margin-right: 10px;}
#collapseTwo .scrolllistbox1 .displayimage img{height: 50px;width: 80px;margin-right: 10px;}
#collapseTwo .scrolllistbox1 .displayimage {height:50px;overflow:hidden}
</style>
<script src="assets/libs/totemTicker/jquery.totemticker.min.js"></script>

<script>
	/* $(function() {
		var curHeight = $('.about1').height();
		var curHeight2 = $('.about2').height();
		if (curHeight >= 117 || curHeight2 >= 117) {
			$('.read-more').css('display', 'block');
		} else
			$('.read-more').css('display', 'none');
	}); */
	function showPropertyTaxCalculator(obj) {
		var data = {};
		var response = __doAjaxRequest(
				'PropertyTaxCalculator.html?viewApplication', 'POST', data,
				false);
		$(formDivName).html(response);
	}
	
	$(function() {
		$('.drag1 .col-lg-12').find('.col-lg-6').removeClass('col-lg-6')
				.addClass('col-lg-4');
		$('.drag1 .col-lg-12 .dark-blue').removeClass('height-390').addClass(
				'height-200');
		$('.drag1 .col-lg-12 .list-number').removeClass('col-sm-12');
		$('.drag1 .col-lg-12 .dark-blue .helpline').removeClass('help-line')
				.addClass('help-line1');
	});
</script>
<c:if
	test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">
	<script>
		$(function() {
			$('#myCarousel').css('margin-top', '0px');
		});
	</script>
</c:if>

${command.setThemeMap()} ${command.getAboutUs()}

<!-- Qucik Header Menu Start -->
<header class="header hidden-xs fixed">
	<div class="logo">
		<a href="CitizenHome.html"><c:forEach
				items="${userSession.logoImagesList}" var="logo" varStatus="status">
				<c:set var="parts" value="${fn:split(logo, '*')}" />
				<c:if test="${parts[1] eq '1'}">
					<img src="${parts[0]}" class="pull-left hidden-xs img-responsive"
						style="height: 45px;"
						alt="<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if><c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> logo">
				</c:if>
			</c:forEach> <c:if test="${userSession.languageId eq 1}">
				<h1>${userSession.organisation.ONlsOrgname}</h1>
			</c:if> <c:if test="${userSession.languageId eq 2}">
				<h1>${userSession.organisation.ONlsOrgnameMar}</h1>
			</c:if> </a>		 	
	</div>
	<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">
	<c:choose>
		<c:when test="${userSession.getOrganisation().orgid eq '6' || userSession.getOrganisation().orgid eq '7'
		 || userSession.getOrganisation().orgid eq '8' || userSession.getOrganisation().orgid eq '9'
		 || userSession.getOrganisation().orgid eq '14' || userSession.getOrganisation().orgid eq '15'
		 || userSession.getOrganisation().orgid eq '16' || userSession.getOrganisation().orgid eq '17'
		 || userSession.getOrganisation().orgid eq '18' || userSession.getOrganisation().orgid eq '19'
		 || userSession.getOrganisation().orgid eq '20' || userSession.getOrganisation().orgid eq '21'
		 || userSession.getOrganisation().orgid eq '22'}">
		<div class="corona"><a class="covid" href="SectionInformation.html?editForm&rowId=&page=<spring:message code="quick.constant.covid19" text="Corona (Covid-19)" />"><spring:message code="quick.header.covid19" text="Corona (Covid-19)" /></a>&nbsp;&nbsp;&nbsp;<i class="fa fa-circle text-danger flicker"></i></div>
		</c:when>
	</c:choose>
	</c:if>
	<%-- 	<div class="mini-menu">
		<div class="login-btn">
		<a href="javascript:void(0);"><spring:message code="Login" text="Login"/></a>			
				<ul class="drpdwn">
		        <li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
		        <li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span></a></li>
		        <li><a href='<spring:message code="service.admin.home.url"/>'  target="_blank"><span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span></a></li>
		       </ul>
		</div>
	</div>	 --%>
	<span class="imenu"><i class="flaticon-menu"></i></span>
	<div class="main-navigation-div">
	<nav class="menu-header-menu-container">
		<ul class="main-navigation">
			<li><a href="#"><spring:message code="Services"
						text="Services"></spring:message></a>
				<ul>
					<c:if test="${command.themeMap['CITIZEN_SERVICES'] ne 'I'}">
						<li class="smooth-scroll"><a href="#CitizenService"><spring:message
									code="eip.citizencervices" text="Citizen Services" /></a></li>
					</c:if>
					<li class="smooth-scroll"><a href="#depser"><spring:message
								code="depServices" text="Department Services" /></a></li>
				</ul></li>
			<%-- <li class="smooth-scroll"><a href="#nidaan" class=""><spring:message code="quick.header.nidaan" text="Nidaan" /></a></li> --%>
			<li class="smooth-scroll"><a href="SectionInformation.html?editForm&rowId=&page=<spring:message code="quick.constant.letters" text="Letters" />"><spring:message code="quick.header.letters" text="Letters" /></a></li>
			<li class="smooth-scroll"><a href="SectionInformation.html?editForm&rowId=&page=<spring:message code="quick.constant.tenders" text="Tenders EOI" />"><spring:message code="quick.header.tenders" text="Tenders" /></a>
			<%-- <ul>
				<li class="smooth-scroll"><a href="SectionInformation.html?editForm&rowId=&page=Tenders EOI"><spring:message
				code="quick.header.ntenders" text="SUDA / UAD" /></a></li>				
				<li class="smooth-scroll"><a href="SectionInformation.html?editForm&rowId=&page=Tenders EOI"><spring:message
				code="quick.header.atenders" text="ULB" /></a></li>				
			</ul>--%> 
			</li>
			<c:if test="${command.themeMap['KEY_CONTACTS'] ne 'I'}">
				<li class="smooth-scroll"><a href="#keyContact"><spring:message
							code="quick.header.keycontacts" text="Key Contacts" /></a></li>
			</c:if>
			<c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">
				<li class="smooth-scroll"><a href="#PublicNotice"><spring:message
							code="quick.header.notices" text="Notices" /></a></li>
			</c:if>
			<li><a href="#"><spring:message code="others" text="others"></spring:message></a>
				<ul>
					<c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
						<li class="smooth-scroll"><a href="#helpline"><spring:message
									code="quick.header.helpline.numbers" text="Helpline Numbers" /></a></li>
					</c:if>
					<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
						<li class="smooth-scroll"><a href="#photo-box"><spring:message
									code="quick.header.gallery" text="Gallery" /> </a></li>
					</c:if>
					<c:if test="${not empty command.aboutUsDescFirstPara }">
						<li class="smooth-scroll"><a href="#about11"><spring:message
									code="quick.header.about" text="About" /></a></li>
					</c:if>
				</ul></li>
			<li><a href="#"><spring:message code="Login" text="Login" /></a>
				<ul>
					<li><a href='javascript:void(0);'
						onclick="getCitizenLoginForm('N')"><span><spring:message
									code="CitizenLogin" text="Citizen Login" /></span></a></li>
					<li><a href='javascript:void(0);'
						onclick="getAdminLoginForm()"><span><spring:message
									code="PortalAdmininstratorLogin"
									text="Portal Admininstrator Login" /></span></a></li>
					<li><a class="dept-login" href='<spring:message code="service.admin.home.url"/>'
						target="_blank"><span><spring:message
									code="DepartmentEmployeeLogin" text="Department Employee Login" /></span></a></li>
				</ul></li>
		</ul>





		<%-- <ul class="menu">
		<li>Services
		<ul>
		<c:if test="${command.themeMap['CITIZEN_SERVICES'] ne 'I'}">
		<li class="smooth-scroll"><a href="#CitizenService"><spring:message code="eip.citizencervices" text="Citizen Services" /></a></li>
		</c:if>
		<li class="smooth-scroll"><a href="#depser"><spring:message code="depServices" text="Department Services" /></a></li>
		</ul></li>
		<li class="smooth-scroll"><a href="#nidaan" class=""><spring:message code="quick.header.nidaan" text="Nidaan" /></a></li>
		<c:if test="${command.themeMap['KEY_CONTACTS'] ne 'I'}">
		<li class="smooth-scroll"><a href="#keyContact"><spring:message code="quick.header.keycontacts" text="Key Contacts" /></a></li>
		</c:if>
		<c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">
		<li class="smooth-scroll"><a href="#PublicNotice"><spring:message code="quick.header.notices" text="Notices" /></a></li>
		</c:if>	
		<li>Others
		<ul>
		<c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
		<li class="smooth-scroll"><a href="#helpline"><spring:message code="quick.header.helpline.numbers" text="Helpline Numbers" /></a></li>
		</c:if>
		<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
		<li class="smooth-scroll"><a href="#photo-box"><spring:message code="quick.header.gallery" text="Gallery" /> </a></li>
		</c:if>
		<c:if test="${not empty command.aboutUsDescFirstPara }">
		<li class="smooth-scroll"><a href="#about11"><spring:message code="quick.header.about" text="About" /></a></li>
		</c:if>	
		</ul></li>	 --%>




		<%-- <li class="smooth-scroll"><a href="#nidaan" class=""><spring:message code="quick.header.nidaan" text="Nidaan" /></a></li> --%>

		<%-- <c:if test="${command.themeMap['NEWS'] ne 'I'}">
				<li class="smooth-scroll"><a href="#news"><spring:message code="quick.header.news" text="News" /></a></li>
			</c:if> --%>


		<%-- <c:if
				test="${not empty command.themeMap['SCHEMES'] && command.themeMap['SCHEMES'] ne 'I'}">
				<li><a href="#schemes"><spring:message code="quick.header.schemes" text="Schemes" /></a></li>
			</c:if> --%>


		<%-- <li class="smooth-scroll"><a href="javascript:void(0);"><spring:message code="Login" text="Login"/></a>			
				<ul class="drpdwn">
		        <li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
		        <li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span></a></li>
		        <li><a href='<spring:message code="service.admin.home.url"/>'  target="_blank"><span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span></a></li>
		       </ul></li> --%>

		<%-- <c:if test="${command.themeMap['EXTERNAL_SERVICES'] ne 'I'}">
				<li><a href="#footer"><spring:message code="quick.header.external.services" text="External Services" /></a></li>
			</c:if> --%>
		<!-- 	</ul> -->
	</nav>
	</div>
</header>
<!-- Qucik Header Menu End -->

<div class="invisibeHead"></div>
<div id="main">
	<!-- Slider Start -->
	<c:if test="${command.themeMap['SLIDER_IMG'] ne 'I'}">
		<div class="container-fluid" id="container-fluid1"
			style="padding-left: 0px; padding-right: 0px;">
			<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12" id="sld">
				<div class="row">
					<div id="myCarousel" class="carousel slide carousel-fade"
						data-ride="carousel">
						<c:set var="maxfilecount"
							value="${userSession.getSlidingImgLookUps().size()}" />
						<!-- Indicators -->
						<ol class="carousel-indicators hidden-xs">
							<c:if test="${maxfilecount gt 0 }">
								<c:forEach begin="0" end="${maxfilecount-1}" varStatus="index">
									<c:choose>
										<c:when test="${index.index eq 0}">
											<li data-target="#myCarousel" data-slide-to="${index.index}"
												class="active"></li>
										</c:when>
										<c:otherwise>
											<li data-target="#myCarousel" data-slide-to="${index.index}"></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>

						</ol>

						<!-- Wrapper for slides -->
						<div class="carousel-inner">

							<c:if test="${userSession.getSlidingImgLookUps().size() gt 0}">

								<c:forEach items="${userSession.slidingImgLookUps}" var="slide"
									varStatus="status">

									<c:set var="data" value="${fn:split(slide,'*')}" />
									<c:set var="image" value="${data[0]}" />
									<c:choose>
										<c:when
											test="${userSession.getCurrent().getLanguageId() eq 1}">
											<c:set var="caption" value="${data[1]}" />
										</c:when>
										<c:otherwise>
											<c:set var="caption" value="${data[2]}" />
										</c:otherwise>
									</c:choose>
									<c:if test="${status.index eq 0 }">
										<div class="item active">
											<img src="./${image}" alt="${caption}" />
											<c:if test="${not empty caption}">
												<div class="container hidden-xs">
													<div class="carousel-caption animate fadeInLeft">
														<h1>${caption}</h1>
													</div>
												</div>
											</c:if>
										</div>
									</c:if>
									<c:if test="${status.index ne 0 }">
										<div class="item active">
											<img src="./${image}" alt="${caption}" />
											<c:if test="${not empty caption}">
												<div class="container hidden-xs">
													<div class="carousel-caption animate fadeInLeft">
														<h1>${caption}</h1>
													</div>
												</div>
											</c:if>
										</div>
									</c:if>
								</c:forEach>
							</c:if>

						</div>

						<!-- Left and right controls -->
						<a class="left carousel-control hidden-xs" href="#myCarousel"
							data-slide="prev"><span
							class="glyphicon glyphicon-chevron-left"></span><span
							class="sr-only">Previous</span></a> <a
							class="right carousel-control hidden-xs" href="#myCarousel"
							data-slide="next"><span
							class="glyphicon glyphicon-chevron-right"></span><span
							class="sr-only">Next</span></a>
					</div>
				</div>
			</div>


			<c:if test="${command.themeMap['NEWS'] ne 'I'}">
				<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
					<div class="panel-group sidemenu" id="accordion" role="tablist"
						aria-multiselectable="true">
						<div class="panel panel-primary">
							<div class="panel-heading" role="tab" id="newsupdates">
								<h4 class="panel-title">
									<a role="button" data-parent="#accordion" href="#collapseTwo"
										aria-expanded="true" aria-controls="collapseTwo"> <spring:message
											code="portal.Latest.News" text="Latest News" />
									</a>
								</h4>
							</div>
							<div id="collapseTwo" class="panel-collapse" role="tabpanel"
								aria-labelledby="newsupdates">
								<ul class="list-group scrolllistbox1" style="height: 410px">
									<%-- <c:forEach items="${command.eipAnnouncement}" var="lookUp"
										varStatus="status">
										<li class="list-group-item"><c:set var="links"
												value="${fn:split(lookUp.attach,',')}" /> <c:forEach
												items="${links}" var="download" varStatus="count">
												<c:set var="idappender"
													value="<%=java.util.UUID.randomUUID()%>" />
												<c:set var="idappender"
													value="${fn:replace(idappender,'-','')}" />
												<c:set var="link"
													value="${stringUtility.getStringAfterChars(download)}" />
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<c:choose>
															<c:when test="${isDMS}">
																<a href="javascript:void(0);"
																	onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
															</c:when>
															<c:otherwise>
																<apptags:filedownload filename="EIP"
																	filePath="${download}"
																	actionUrl="CitizenHome.html?Download"
																	eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
																<input type="hidden" value="${lookUp.isHighlighted}"
																	class="Highlighted">
															</c:otherwise>
														</c:choose>
														<br />
														<span class="date"><i class="fa fa-calendar"
															aria-hidden="true"></i> <fmt:formatDate
																value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${isDMS}">
																<a href="javascript:void(0);"
																	onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
															</c:when>
															<c:otherwise>
																<apptags:filedownload filename="EIP"
																	filePath="${download}"
																	actionUrl="CitizenHome.html?Download"
																	eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
																<input type="hidden" value="${lookUp.isHighlighted}"
																	class="Highlighted">
															</c:otherwise>
														</c:choose>
														<br />
														<span class="date"><i class="fa fa-calendar"
															aria-hidden="true"></i> <fmt:formatDate
																value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											</li>
									</c:forEach> --%>
									<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">
									<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
									<%-- <c:set var="images" value="${fn:split(lookUp.attachImage,',')}" /> --%>									
									<li class="list-group-item">
									
											<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
											<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
											<c:set var="link" value="${stringUtility.getStringAfterChars(download)}" />
											<c:choose>
												<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:choose>
														<c:when test="${isDMS}">
															<a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
														</c:when>
														<c:otherwise>
															<c:choose>
															 <c:when test="${lookUp.linkType eq 'L'}">			                     
															 <a href="${lookUp.link}" target="">
															 <%-- <c:if test="${not empty images}"><div class="displayimage">															 
															 <c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescEng}">
														 	 </c:forEach>
														 	</div></c:if> --%>
															 ${lookUp.announceDescEng}
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </a>
															 </c:when>
															 <c:when test="${lookUp.linkType eq 'E'}">			                     
															 <a href="${lookUp.link}" target="_blank">
															 <%-- <c:if test="${not empty images}"><div class="displayimage">
															 <c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescEng}">
														 	 </c:forEach>
														 	 </div></c:if> --%>
															 ${lookUp.announceDescEng}
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </a>
															 </c:when>
															 <c:otherwise>
															 															 
															 <c:forEach items="${links}" var="download" varStatus="count">
															 <c:set var="lnk1" value="./cache/${download}" />
														 		<c:if test="${count.index eq 0 }">
														 		<a href="${lnk1}" target="_blank">
														 		<%-- <c:if test="${not empty images}"><div class="displayimage">
															 <c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescEng}">
														 	 </c:forEach>
														 	 </div></c:if> --%> ${lookUp.announceDescEng}</a>
														 		</c:if>
																<c:if test="${lnk1 ne './cache/'}">
																	<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>																		
																</c:if>
																
															 <%-- <apptags:filedownload filename="EIP"
																filePath="${download}"
																actionUrl="CitizenHome.html?Download"
																eipFileName="${lookUp.announceDescEng}"></apptags:filedownload> --%>
															 </c:forEach>
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </c:otherwise>
															</c:choose>		
														</c:otherwise>
													</c:choose>
													<br />
													<span class="date"><i class="fa fa-calendar"
														aria-hidden="true"></i> <fmt:formatDate
															value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${isDMS}">
															<a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
														</c:when>
															<c:otherwise>
															<c:choose>
															 <c:when test="${lookUp.linkType eq 'L'}">			                     
															 <a href="${lookUp.link}" target="">
															 <%-- <c:if test="${not empty images}"><div class="displayimage">
															<c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescReg}">
														 	</c:forEach>
														 	</div></c:if> --%>
															 ${lookUp.announceDescReg}
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </a>
															 </c:when>
															 <c:when test="${lookUp.linkType eq 'E'}">			                     
															 <a href="${lookUp.link}" target="_blank">
															 <%-- <c:if test="${not empty images}"><div class="displayimage">
															<c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescReg}">
														 	</c:forEach>
														 	</div></c:if> --%>
															 ${lookUp.announceDescReg}
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </a>
															 </c:when>
															 <c:otherwise>
															 <c:forEach items="${links}" var="download" varStatus="count">															 
															 <c:set var="lnk1" value="./cache/${download}" />
														 		<c:if test="${count.index eq 0 }">
														 		<a href="${lnk1}" target="_blank">
														 		<%-- <c:if test="${not empty images}"><div class="displayimage">
																<c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescReg}">
														 		</c:forEach>
														 		</div></c:if> --%>
														 		${lookUp.announceDescReg} <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted"></a>
														 		</c:if>
																<c:if test="${lnk1 ne './cache/'}">
																	<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>																		
																</c:if>
																
															 <%-- <apptags:filedownload filename="EIP"
																filePath="${download}"
																actionUrl="CitizenHome.html?Download"
																eipFileName="${lookUp.announceDescReg}"></apptags:filedownload> --%>
															 </c:forEach>
																														  
															 </c:otherwise>
															</c:choose> 						
														</c:otherwise>
													</c:choose>
													<br />
													<span class="date"><i class="fa fa-calendar" aria-hidden="true"></i> <fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
												</c:otherwise>
											</c:choose>										
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<div class="clearfix"></div>
		</div>
	</c:if>
	<!-- Slider End -->

	<c:if test="${command.themeMap['SLIDER_IMG'] eq 'I'}">
		<c:if test="${command.themeMap['NEWS'] ne 'I'}">
			<div class="col-lg-12 col-md-12 col-sm-12" style="margin-top: 283px"
				id="lnsh">
				<div class="panel-group sidemenu" id="accordion" role="tablist"
					aria-multiselectable="true">
					<div class="panel panel-primary">
						<div class="panel-heading" role="tab" id="newsupdates">
							<h4 class="panel-title">
								<a role="button" data-parent="#accordion" href="#collapseTwo"
									aria-expanded="true" aria-controls="collapseTwo"> <spring:message
										code="portal.Latest.News" text="Latest News" />
								</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse" role="tabpanel"
							aria-labelledby="newsupdates">
							<ul class="list-group scrolllistbox" style="height: 410px">
								<%-- <c:forEach items="${command.eipAnnouncement}" var="lookUp"
									varStatus="status">
									<li class="list-group-item"><c:set var="links"
											value="${fn:split(lookUp.attach,',')}" /> <c:forEach
											items="${links}" var="download" varStatus="count">
											<c:set var="idappender"
												value="<%=java.util.UUID.randomUUID()%>" />
											<c:set var="idappender"
												value="${fn:replace(idappender,'-','')}" />
											<c:set var="link"
												value="${stringUtility.getStringAfterChars(download)}" />
											<c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:choose>
														<c:when test="${isDMS}">
															<a href="javascript:void(0);"
																onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
														</c:when>
														<c:otherwise>
															<apptags:filedownload filename="EIP"
																filePath="${download}"
																actionUrl="CitizenHome.html?Download"
																eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
														</c:otherwise>
													</c:choose>
													<br />
													<span class="date"><i class="fa fa-calendar"
														aria-hidden="true"></i> <fmt:formatDate
															value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${isDMS}">
															<a href="javascript:void(0);"
																onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
														</c:when>
														<c:otherwise>
															<apptags:filedownload filename="EIP"
																filePath="${download}"
																actionUrl="CitizenHome.html?Download"
																eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
														</c:otherwise>
													</c:choose>
													<br />
													<span class="date"><i class="fa fa-calendar"
														aria-hidden="true"></i> <fmt:formatDate
															value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
												</c:otherwise>
											</c:choose>
										</c:forEach></li>
								</c:forEach> --%>
								<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">
									<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
									<%-- <c:set var="images" value="${fn:split(lookUp.attachImage,',')}" /> --%>
									<li class="list-group-item">
									 <c:forEach items="${links}" var="download" varStatus="count">
											<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
											<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
											<c:set var="link" value="${stringUtility.getStringAfterChars(download)}" />
											<c:choose>
												<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:choose>
														<c:when test="${isDMS}">
															<a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
														</c:when>
														<c:otherwise>
															<c:choose>
															 <c:when test="${lookUp.linkType eq 'L'}">			                     
															 <a href="${lookUp.link}" target="">
															 <%-- <c:if test="${not empty images}"><div class="displayimage">
															<c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
														 	</c:forEach>
														 	</div></c:if> --%>
															 ${lookUp.announceDescEng}
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </a>
															 </c:when>
															 <c:when test="${lookUp.linkType eq 'E'}">			                     
															 <a href="${lookUp.link}" target="_blank">
															 <%-- <c:if test="${not empty images}"><div class="displayimage">
															<c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
														 	</c:forEach>
														 	</div></c:if> --%>
															 ${lookUp.announceDescEng}
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </a>
															 </c:when>
															 <c:otherwise>
															 <c:forEach items="${links}" var="download" varStatus="count">
															 <c:set var="lnk1" value="./cache/${download}" />
														 		<c:if test="${count.index eq 0 }">
														 		<a href="${lnk1}" target="_blank">
														 		<%-- <c:if test="${not empty images}"><div class="displayimage">
															 	<c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescEng}">
														 	 	</c:forEach>
														 	 	</div></c:if> --%>${lookUp.announceDescEng}</a>
														 		</c:if>
																<c:if test="${lnk1 ne './cache/'}">
																	<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>																		
																</c:if>
																
															 <%-- <apptags:filedownload filename="EIP"
																filePath="${download}"
																actionUrl="CitizenHome.html?Download"
																eipFileName="${lookUp.announceDescEng}"></apptags:filedownload> --%>
															 </c:forEach>
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </c:otherwise>
															</c:choose> 						
														</c:otherwise>
													</c:choose>
													<br />
													<span class="date"><i class="fa fa-calendar"
														aria-hidden="true"></i> <fmt:formatDate
															value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${isDMS}">
															<a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
														</c:when>
															<c:otherwise>
															<c:choose>
															 <c:when test="${lookUp.linkType eq 'L'}">			                     
															 <a href="${lookUp.link}" target="">
															 <%-- <c:if test="${not empty images}"><div class="displayimage">
															<c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
														 	</c:forEach>
														 	</div></c:if> --%>
															 ${lookUp.announceDescReg}
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </a>
															 </c:when>
															 <c:when test="${lookUp.linkType eq 'E'}">			                     
															 <a href="${lookUp.link}" target="_blank">
															 <%-- <c:if test="${not empty images}"><div class="displayimage">
															<c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
														 	</c:forEach>
														 	</div></c:if> --%>
															 ${lookUp.announceDescReg}
															 <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </a>
															 </c:when>
															 <c:otherwise>
															 <c:forEach items="${links}" var="download" varStatus="count">															 
															 <c:set var="lnk1" value="./cache/${download}" />
														 		<c:if test="${count.index eq 0 }">
														 		<a href="${lnk1}" target="_blank">
														 		<%-- <c:if test="${not empty images}"><div class="displayimage">
															 	<c:forEach items="${images}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescReg}">
														 	 	</c:forEach>
														 	 	</div></c:if> --%>
														 		${lookUp.announceDescReg} <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted"></a>
														 		</c:if>
																<c:if test="${lnk1 ne './cache/'}">
																	<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>																		
																</c:if>																
															 <%-- <apptags:filedownload filename="EIP"
																filePath="${download}"
																actionUrl="CitizenHome.html?Download"
																eipFileName="${lookUp.announceDescReg}"></apptags:filedownload> --%>
															 </c:forEach>
															<input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted">
															 </c:otherwise>
															</c:choose> 						
														</c:otherwise>
													</c:choose>
													<br />
													<span class="date"><i class="fa fa-calendar" aria-hidden="true"></i> <fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										</li>
									</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</c:if>


	${command.setContactList()}
	<!-- select Organization Box in case of slider/Minister not active start -->
	<c:if test="${command.themeMap['COMMITTEE_MEMBERS'] ne 'A'}">
		<!--SELECT ORGNAIZATION START -->
		<c:if
			test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
			<div
				class="about-bg container-fluid clear  minister-details padding-bottom_20 margin-top-90">
				<div class="minister-org ">

					<div
						class="col-sm-12 col-md-4 col-lg-3 col-xs-12 col-md-offset-8 col-lg-offset-9  org-select hidden-xs">
						<div class="content-tab">
							<div class="form-group">
								<h3>
									<spring:message code="ULB.head" text="Select ULB" />
								</h3>
								<form:form name="selectMunicipalForm" id="selectMunicipalForm"
									action="" class="list" method="get">
									<div class="row">
										<label class="hidden" for="selectedOrg">Select
											Department</label> <select name="orgId" id="selectedOrg"
											data-content="" class="form-control chosen-select-no-results">
											<option value="-1" selected style="display: none;"><spring:message code="Select" text="Select" /></option>
											<c:forEach items="${command.userSession.organisationsList}"
												var="orglist">
												<optgroup label="${orglist.key}">
													<c:forEach items="${orglist.value}" var="org">
														<c:if test="${userSession.languageId eq 1}">
															<option value="${org.orgid}">${org.ONlsOrgname}</option>
														</c:if>
														<c:if test="${userSession.languageId eq 2}">
															<option value="${org.orgid}">${org.ONlsOrgnameMar}</option>
														</c:if>
													</c:forEach>
												</optgroup>
											</c:forEach>
										</select>
										<button type="button" id="submitMunci" class="btn btn-success">
											<spring:message code="change" text="Change" />
										</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>

				</div>
			</div>
		</c:if>
	</c:if>
	<!-- select Organization Box in case of slider/Minister not active end -->
	<!--SELECT ORGNAIZATION END -->
	<!--MINISTER START  -->
	<c:if test="${command.themeMap['COMMITTEE_MEMBERS'] ne 'I'}">
		<div
			class="about-bg container-fluid clear padding-top-20 minister-details"
			id="container-fluid">
			<div class="minister-org">
				<!--SELECT ORGNAIZATION START -->
				<c:if
					test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
					<div
						class="col-sm-12 col-md-3 col-lg-3 col-xs-12 org-select hidden-xs">
						<div class="content-tab">
							<div class="form-group">
								<h3>
									<spring:message code="ULB.head" text="Select ULB" />
								</h3>
								<form:form name="selectMunicipalForm" id="selectMunicipalForm"
									action="" class="list" method="get">
									<div class="row">
										<label class="hidden" for="selectedOrg">Select
											Department</label> <select name="orgId" id="selectedOrg"
											data-content="" class="form-control chosen-select-no-results">
											<option value="-1" selected style="display: none;"><spring:message code="Select" text="Select" /></option>
											<c:forEach items="${command.userSession.organisationsList}"
												var="orglist">
												<optgroup label="${orglist.key}">
													<c:forEach items="${orglist.value}" var="org">
														<c:if test="${userSession.languageId eq 1}">
															<option value="${org.orgid}">${org.ONlsOrgname}</option>
														</c:if>
														<c:if test="${userSession.languageId eq 2}">
															<option value="${org.orgid}">${org.ONlsOrgnameMar}</option>
														</c:if>
													</c:forEach>
												</optgroup>

											</c:forEach>
										</select>
										<button type="button" id="submitMunci" class="btn btn-success">
											<spring:message code="change" text="Change" />
										</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</c:if>

				<!--SELECT ORGNAIZATION END -->
				<c:set var="count" value="0" scope="page" />
				<c:if test="${not empty sessionScope.mayorProfile}">
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:if>
				<c:if test="${not empty sessionScope.deputyMayorProfile}">
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:if>
				<c:if test="${not empty sessionScope.commissionerProfile}">
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:if>
				<c:if test="${not empty sessionScope.deputycommissionerProfile}">
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:if>
				<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 minister-div">
					<br />
					<div>
						<nav>
							<div class="minister-ul">


								<c:if test="${not empty sessionScope.mayorProfile}">

									<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">

										<div class="content-img">
											<img src="${sessionScope.mayorprofileImage}"
												alt="${sessionScope.mayorName}"
												title="${sessionScope.mayorName}" class="img-responsive">
										</div>
										<div class="minister-info">
											<h3>${sessionScope.mayorName}</h3>
											<p class="designation">${sessionScope.mayorProfile}</p>
											<c:if test="${not empty sessionScope.SummaryEng}">
											<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i><a href="tel:${sessionScope.SummaryEng}"> ${sessionScope.SummaryEng}</a></p>
											</c:if>
										</div>
										<br />
									</div>
								</c:if>
								<c:if test="${not empty sessionScope.deputyMayorProfile}">

									<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">

										<div class="content-img">
											<img src="${sessionScope.deputyMayorProfileImage}"
												alt="${sessionScope.deputyMayorName}"
												title="${sessionScope.deputyMayorName}"
												class="img-responsive">
										</div>
										<div class="minister-info">
											<h3>${sessionScope.deputyMayorName}</h3>
											<p class="designation">${sessionScope.deputyMayorProfile}</p>
											<c:if test="${not empty sessionScope.deputyMayorSummaryEng}">
											<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i><a href="tel:${sessionScope.SummaryEng}"> ${sessionScope.deputyMayorSummaryEng}</a></p>
											</c:if>
										</div>
										<br />
									</div>
								</c:if>

								<c:if test="${not empty sessionScope.commissionerProfile}">

									<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">

										<div class="content-img">
											<img src="${sessionScope.commissionerProfileImage}"
												alt="${sessionScope.commissionerName}"
												title="${sessionScope.commissionerName}"
												class="img-responsive">
										</div>
										<div class="minister-info">
											<h3>${sessionScope.commissionerName}</h3>
											<p class="designation">${sessionScope.commissionerProfile}</p>
											<c:if test="${not empty sessionScope.commissionerSummaryEng}">
											<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i><a href="tel:${sessionScope.SummaryEng}"> ${sessionScope.commissionerSummaryEng}</a></p>
											</c:if>
										</div>
										<br />
									</div>
								</c:if>

								<c:if
									test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
									<c:if
										test="${not empty sessionScope.deputycommissionerProfile}">

										<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">


											<div class="content-img">
												<img src="${sessionScope.deputycommissionerProfileImage}"
													alt="${sessionScope.deputyCommissionerName}"
													title="${sessionScope.deputyCommissionerName}"
													class="img-responsive">
											</div>
											<div class="minister-info">
												<h3>${sessionScope.deputyCommissionerName}</h3>
												<p class="designation">${sessionScope.deputycommissionerProfile}</p>
												<c:if test="${not empty sessionScope.deputyMayorSummaryEng}">
												<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i><a href="tel:${sessionScope.SummaryEng}"> ${sessionScope.deputyMayorSummaryEng}</a></p>
												</c:if>
											</div>
											<br />
										</div>
									</c:if>
								</c:if>

								<c:if test="${empty sessionScope.mayorProfile}">
									<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">
										<div class="content-img">
											<img src="assets/img/Hon'ble-Minister.jpg"
												alt="Hon'ble-Minister" title="Hon'ble-Minister"
												class="img-responsive">
										</div>
										<br />
									</div>
								</c:if>
								<c:if test="${empty sessionScope.deputyMayorProfile}">
									<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">
										<div class="content-img">
											<img src="assets/img/Hon'ble-Minister.jpg"
												alt="Hon'ble-Minister" title="Hon'ble-Minister"
												class="img-responsive">
										</div>
										<br />
									</div>
								</c:if>
								<c:if test="${empty sessionScope.commissionerProfile}">
									<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">
										<div class="content-img">
											<img src="assets/img/Hon'ble-Minister.jpg"
												alt="Hon'ble-Minister" title="Hon'ble-Minister"
												class="img-responsive">
										</div>
										<br />
									</div>
								</c:if>
								<c:if
									test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
									<c:if test="${empty sessionScope.deputycommissionerProfile}">
										<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">
											<div class="content-img">
												<img src="assets/img/Hon'ble-Minister.jpg"
													alt="Hon'ble-Minister" title="Hon'ble-Minister"
													class="img-responsive">
											</div>
											<br />
										</div>
									</c:if>
								</c:if>
								<c:if
									test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
									<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">

										<a href="javascript:void(0);"
														onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
											<h3 class="cm-title">
												<spring:message code="care.new.water.connection"
													text="New Water Connection" />
											</h3>
										</a>
										<a href="grievance.html?complaintRegistrationStatus"
											title="Click Here.." class="cm-office"
											data-tooltip="Click Here..">
											<h3 class="cm-title">
												<spring:message code="care.ward.heading"
													text="Mukhyamantri Ward Kaaryalaya " />
											</h3>
										</a>
										<a href="SectionInformation.html?editForm&rowId=1334&page="
											title="Click Here.." class="cm-office"
											data-tooltip="Click Here..">
											<h3 class="cm-title">
												<spring:message code="portal.pension.module.header"
													text="Pension Module" />
											</h3>
										</a>
									</div>
									
								</c:if>
							</div>
						</nav>
					</div>
				</div>
			</div>
		</div>
	</c:if>


	<!--MINISTER END  -->
	<div class="clear"></div>


	<!-- Citizen Services Start -->
	<c:if
		test="${not empty command.themeMap['CITIZEN_SERVICES'] && command.themeMap['CITIZEN_SERVICES'] ne 'I'}">
		<c:set var="citizenService"
			value="${command.getAllDepartmentAndServices()}" />
		<c:if test="${citizenService.size()>0}">
			<hr />
			<div class="service-div citser" id="CitizenService">
				<div class="container-fluid">
					<div class="row">
						<h2>
							<spring:message code="eip.citizencervices"
								text="Citizen Services" />
						</h2>
						<div>
							<c:forEach items="${command.getAllDepartmentAndServices()}"
								var="dept" varStatus="index">
								<div
									class="col-lg-3 col-md-3 col-sm-4 col-xs-12 Services-tab  item">
									<div class="folded-corner service_tab_AS">
										<div class="text">
											<h3>
												<i
													class="fa fa-list_${dept.departmentCode} fa-icon-image fa-2x"></i>${dept.departmentName}</h3>
											<div class="clearfix"></div>
										</div>
										<div class="text-contents">
											<ul>
												<c:forEach items="${dept.childDTO}" var="serv"
													varStatus="index">
													<li><a href="javascript:void(0);"
														onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
															<i class="fa fa-chevron-right"></i> ${serv.serviceName}
													</a></li>

												</c:forEach>
												<%-- Code to display non service link in property tax service --%>
												<c:if test="${dept.departmentCode eq 'AS'}">
													<li><a href="<spring:message code="property.cs_prop_vps_link" text="" />" class="extlink internal"><i class="fa fa-chevron-right"></i> <spring:message code="property.cs_prop_vps" text="" /></a></li>
												</c:if>
												<%-- Code to display non service link in property tax service --%>
												<%-- Code to display non service link in water sewerage service --%>
												<c:if test="${dept.departmentCode eq 'WT'}">
													<li><a href="<spring:message code="watersewerage.cs_prop_vps_link" text="" />" class="extlink internal"><i class="fa fa-chevron-right"></i> <spring:message code="watersewerage.cs_prop_vps" text="" /></a></li>
												</c:if>
												<c:if test="${dept.departmentCode eq 'ML'}">
													<li><a href="<spring:message code="trade.licence_search_vps_link" text="LicenseSearchDetails.html" />" class="extlink internal"><i class="fa fa-chevron-right"></i> <spring:message code="trade.licence_search_vps" text="Trade License Search" /></a></li>
												</c:if>
												<%-- Code to display non service link in water sewerage service --%>
											</ul>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>

					</div>
				</div>
			</div>
		</c:if>
	</c:if>
	<!-- Citizen Services End -->


<!-- Down Arrow Start -->
	<div class="mbr-arrow hidden-sm-down">
		<a> <i class="fa fa-arrow-down" aria-hidden="true"></i>
		<span class="hidden"><spring:message code="theme3.portal.scroll" text="Scroll"/></span>
		</a>
	</div>
<!-- Down Arrow End -->

	<!-- 
	Departmental based Services
	Code added by ABM2144 on 20-5-2019
 -->
	<c:if
		test="${not empty command.themeMap['EXTERNAL_SERVICES'] && command.themeMap['EXTERNAL_SERVICES'] ne 'I'}">
		<hr />
		<div class="container-fluid depser" id="depser">
			<div class="col-lg-12">
				<h2 class="text-center " data-animation-effect="fadeInDown"
					data-effect-delay="600">
					<spring:message code="depServices" text="Department Services" />
				</h2>
			</div>
			<ul>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-home icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices1" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-tint icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices2" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-desktop icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices3" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-cogs icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices4" text="Services" /></span></a></li>
				<%-- 		<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12">
		<span class="pull-left fa fa-tachometer icon"></span>
		<a href='<spring:message code="dashboard.url"/>'  target="_blank"><span class="d-middle"><spring:message code="depServices5" text="Services" /></span></a>
		</li>	 --%>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-inr icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices6" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-building-o icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices7" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-universal-access icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices8" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-gavel icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices9" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-money icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices10" text="Services" /></span></a></li>

				<%-- start Added by ABM2144 for SUDA all 18 Modules task dated 19.07.2019 --%>

				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-archive icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices11" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-recycle icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices12" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-globe icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices13" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-user-secret icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices14" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-road icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices15" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-bullhorn icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices16" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-send icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices17" text="Services" /></span></a></li>
				<li class=" col-lg-3 col-md-3 col-sm-6 col-xs-12"><span
					class="pull-left fa fa-shield icon"></span> <a
					href='<spring:message code="service.admin.home.url"/>'
					target="_blank"><span class="d-middle"><spring:message
								code="depServices18" text="Services" /></span></a></li>

				<%-- ended Added by ABM2144 for SUDA all 18 Modules task dated 19.07.2019 --%>

			</ul>
		</div>
	</c:if>
	<!--
	Departmental based Services
	End Code by ABM2144
-->


	<c:if
		test="${not empty command.themeMap['IMP_LINKS'] && command.themeMap['IMP_LINKS'] ne 'I'}">
		<hr />
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<div id="exservices" class="text-center">
						<a class="dept-login" href="<spring:message code="dashboard.url" text="CitizenHome.html"/>" target="_blank"
							data-target="internal">
							<div class="dcard">
								<div class="image">
									<i class="fa fa-dashboard fa-3x" aria-hidden="true"></i>
								</div>
								<div class="txt">
									<spring:message code="portal.dashboard" text="Dashboard" />
								</div>
							</div>
						</a> <a href="#" onclick="showPropertyTaxCalculator(this)"
							data-target="internal">
							<div class="dcard">
								<div class="image">
									<i class="fa fa-building fa-3x" aria-hidden="true"></i>
								</div>
								<div class="txt">
									<spring:message code="PropertyTax" text="Property Tax" />
									<br>
									<spring:message code="Calculator" text="Calculator" />
								</div>
							</div>
						</a>
						<div class="dcard mkcmp">
							<div class="image">
								<i class="fa fa-comments fa-3x" aria-hidden="true"></i>
							</div>
							<div class="txt">
								<spring:message code="MakeaComplaint" text="Make a Complaint" />
							</div>
						</div>
						<div class="dcard ckstat">
							<div class="image">
								<i class="fa fa-check fa-3x" aria-hidden="true"></i>
							</div>
							<div class="txt">
								<spring:message code="CheckStatus" text="Check Status" />
							</div>
						</div>
						<div class="dcard mkpay">
							<div class="image">
								<i class="fa fa-credit-card fa-3x" aria-hidden="true"></i>
							</div>
							<div class="txt">
								<spring:message code="MakePayment" text="Make Payment" />
							</div>
						</div>
						<a
							href="https://play.google.com/store/apps/details?id=com.abm.mainetproduct"
							target="_blank" data-target="internal">
							<div class="dcard">
								<div class="image">
									<i class="fa fa-mobile fa-3x" aria-hidden="true"></i>
								</div>
								<div class="txt">
									<spring:message code="GetMobileApp" text="Get MobileApp" />
								</div>
							</div>
						</a>
					</div>
				</div>
			</div>
			<div class="nidaan-contents" style="display: none">
				<!--  Make complaint -->
				<div id="mkcmp" style="display: none">
					<div class="col-sm-6 col-md-8 col-lg-8 col-xs-12">
						<div class="content-tab">
							<h3>
								<spring:message code="eip.nidan1100" text="Nidaan 1100" />
							</h3>
							<p>
								<spring:message code="eip.nidan.desc" text="" />
								&nbsp;
							</p>
							<p class="yellow">
								<spring:message code="eip.comp.time" text="" />
							</p>
						</div>
					</div>
					<div class="col-sm-6 col-md-4 col-lg-4 col-xs-12">
						<div class="hidden-lg hidden-md hidden-sm"
							style="margin-top: 20px;"></div>
						<div class="content-tab">
							<h3>
								<spring:message code="eip.reg.care"
									text="Grievance Registration" />
							</h3>
							<ul class="banner-new">
								<li><a href="javascript:void(0);" data-toggle="modal"
									data-target="#phonemodal"><i class="fa fa-phone"></i>&nbsp;
										<span><spring:message code="eip.tel"
												text="Registration On Phone" /></span></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<!--  Make complaint -->
				<!-- Check Status -->
				<div id="ckstat" style="display: none">
					<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="content-tab">
							<h3>
								<spring:message code="CheckStatus" text="Check Status" />
							</h3>
							<p>
								<spring:message code="eip.checkstatus.desc" text="" />
							</p>
							<ul class="banner-new col-sm-6">
								<li><a href="CitizenHome.html?applicationStatus"><i
										class="fa fa-file"></i>&nbsp; <span><spring:message
												code="eip.app.status" text="Applicant Status" /></span></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<!-- Check Status -->
				<!-- Make payment -->
				<div id="mkpay" style="display: none">
					<div class="col-sm-7 col-md-7 col-lg-7 col-xs-12">
						<div class="content-tab">
							<h3>
								<spring:message code="MakePayment" text="Make Payment" />
							</h3>
							<p>
								<spring:message code="eip.online.process" text="" />
							</p>
							<ol class="payment">
								<li><spring:message code="eip.ref.no" text="" /></li>
								<li><spring:message code="eip.auth.det" text="" /></li>
								<li><spring:message code="eip.confirm.pay" text="" /></li>
								<li><spring:message code="eip.onl.tran.conf" text="" /></li>
							</ol>
						</div>
					</div>
					<div class="col-sm-5 col-md-5 col-lg-5 col-xs-12">
						<div class="hidden-lg hidden-md hidden-sm"
							style="margin-top: 20px;"></div>
						<div class="content-tab">
							<form class="form">
								<div class="form-group">
									<label for="billtype"><spring:message code="BillType"
											text="Bill Type" /></label> <select class="form-control"
										name="bill-type" id="billtype">
										<option> <spring:message code="selectdropdown" text="Select" /></option>
										<option value="PropertyBillPayment.html"><spring:message code="PropertyBill" text="Property Bill" /></option>
										<option value="WaterBillPayment.html"><spring:message code="WaterBill" text="Water Bill" /></option>
									</select>
								</div>
								<div class="form-group">
									<spring:message code="eg" var="placeholder4" />
									<label for="billNumber"> <spring:message
											code="ConsumerNumber" text="Consumer Number" /></label> <input
										type="text" class="form-control" name="referral"
										id="billNumber" placeholder="${placeholder4}">
								</div>
								<div class="text-center">
									<br />
									<button type="button" onclick="searchBillPay()"
										class="btn btn-success" style="width: 150px">
										<spring:message code="eip.page.process" text="Proceed" />
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<!-- Make payment -->
			</div>
		</div>
	</c:if>

	<!--  Complaint Start Here   -->
	<%-- <hr/>
	<div class="container-fluid complaint" id="nidaan">
		<div class="row">
			<div
				class="col-lg-12 col-md-12 col-sm-12 col-xs-12 bhoechie-tab-container">
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 bhoechie-tab-menu">
					<div class="list-group">
						<a href="#" class="list-group-item active"> <i
							class="fa fa-pencil-square-o fa-3x" aria-hidden="true"></i>&nbsp;
						<spring:message code="MakeaComplaint" text="Make a Complaint" />
						</a> <a href="#" class="list-group-item"> <i
							class="fa fa-check-square-o fa-3x" aria-hidden="true"></i>&nbsp;
						<spring:message code="CheckStatus" text="Check Status" />
						</a> <a href="#" class="list-group-item"> <i
							class="fa fa-credit-card fa-3x" aria-hidden="true"></i>&nbsp;
						<spring:message code="MakePayment" text="Make Payment" />
						</a>
					</div>
				</div>
				<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12 bhoechie-tab">
					<!-- flight section -->
					<div class="bhoechie-tab-content active tab-pane">
						<div class="col-sm-7 col-md-7 col-lg-7 col-xs-12">
							<div class="content-tab">
								<h3>
									<spring:message code="eip.nidan1100" text="Nidaan 1100" />
								</h3>
								<p>
									<spring:message code="eip.nidan.desc" text="" />
									&nbsp
									<a href='<spring:message code="care.dashboard.report.url"></spring:message>RP_CARE.rptdesign'
								target="_blank" class="dashboard-blink"><spring:message
									code="eip.care.dashboard" text="DASHBOARD" /></a>
								</p>
								<p class="yellow">
									<spring:message code="eip.comp.time" text="" />
								</p>
							</div>
						</div>

						<div class="col-sm-5 col-md-5 col-lg-5 col-xs-12">
							<div class="content-tab">
								<h3>
									<spring:message code="eip.reg.care"
										text="Grievance Registration" />
								</h3>
								<ul class="banner-new">
									<li><a href="javascript:void(0);" data-toggle="modal"
										data-target="#phonemodal"><img src="assets/img/phone.png"
											alt="Registration On Phone" class="hidden-xs hidden-sm">
											<span><spring:message code="eip.tel"
													text="Registration On Phone" /></span></a></li>


													
							<li><a href="grievance.html"><img
									src="assets/img/internet.png" alt="Registration On Internet" class="hidden-xs hidden-sm"> <span><spring:message
											code="eip.ie" text="Registration On Internet" /></span></a></li>
								</ul>

							</div>
						</div>
					</div>
					<!-- train section -->
					<div class="bhoechie-tab-content tab-pane">
						<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
							<div class="content-tab">
								<h3>
									<spring:message code="CheckStatus" text="Check Status" />
								</h3>
								<p>
									<spring:message code="eip.checkstatus.desc" text="" />
								</p>
								<ul class="banner-new">
									<li class="col-sm-6"><a
								href="grievance.html?grievanceStatus"><img
									src="assets/img/check_status.png" alt="Complaint Status" class="hidden-xs"> <span><spring:message
											code="eip.comp.status" text="Complaint Status" /></span></a></li>
									<li class="col-sm-6"><a
										href="CitizenHome.html?applicationStatus"><img
											src="assets/img/check_status.png" alt="Applicant Status"
											class="hidden-xs"> <span><spring:message
													code="eip.app.status" text="Applicant Status" /></span></a></li>
								</ul>
							</div>
						</div>
					</div>

					<!-- hotel search -->
					<div class="bhoechie-tab-content tab-pane">
						<div class="col-sm-7 col-md-7 col-lg-7 col-xs-12">
							<div class="content-tab">
								<h3>
									<spring:message code="MakePayment" text="Make Payment" />
								</h3>
								<p>
									<spring:message code="eip.online.process" text="" />
								</p>

								<ol class="payment">

									<li><spring:message code="eip.ref.no" text="" /></li>
									<li><spring:message code="eip.auth.det" text="" /></li>
									<li><spring:message code="eip.confirm.pay" text="" /></li>
									<li><spring:message code="eip.onl.tran.conf" text="" /></li>
								</ol>

							</div>
						</div>

						<div class="col-sm-5 col-md-5 col-lg-5 col-xs-12">

							<div class="content-tab">

								<form class="form">
									<div class="form-group">
										<label for="bill-type"><spring:message code="BillType"
												text="Bill Type" /></label> <select class="form-control"
											name="bill-type" id="billtype">
											<option> <spring:message code="selectdropdown" text="Select" /></option>
											<option value="PropertyBillPayment.html"><spring:message code="PropertyBill" text="Property Bill" /></option>
											<option value="WaterBillPayment.html"><spring:message code="WaterBill" text="Water Bill" /></option>
										</select>
									</div>
									<div class="form-group">
										<spring:message code="eg" var="placeholder4" />
										<label for="referral"> <spring:message
												code="ConsumerNumber" text="Consumer Number" /></label> <input
											type="text" class="form-control" name="referral"
											id="billNumber" placeholder="${placeholder4}">
									</div>
									<!-- <div class="form-group">
								<label for="amount"><spring:message code="AmountRs" text="Amount in Rs." /></label> <input type="text"
									class="form-control" name="amount" id="amount"
									placeholder="Amount in Rupees">
							</div> -->									
									<div class="text-center"><br/><button type="button" onclick="searchBillPay()"
										class="btn btn-success" style="width:150px">
										<spring:message code="eip.page.process" text="Proceed" />
									</button></div>
								</form>


							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> --%>
	<!--  Complaint End Here   -->

	<!-- Dashboard/Property Tax/Mobile App Start
	<section class="dash">
		<div class="">
			<div class="row mbr-justify-content-center">
				<div class="col-lg-4 col-md-4 col-sm-4 dash-bg">
					<a href="<spring:message code="dashboard.url"/>" target="_blank">
						<div class="wrap">
							<div class="ico-wrap">
								<img src="assets/img/dashboard-icon1.png" height="67" width="67">
							</div>
							<div class="text-wrap vcenter">
								<h2
									class="mbr-fonts-style mbr-bold mbr-section-title3 display-5">
									<spring:message code="portal.dashboard" text="Dashboard" />
								</h2><br/>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 tax-bg">
					<a href="#" onclick="showPropertyTaxCalculator(this)">
						<div class="wrap">
							<div class="ico-wrap">
								<img src="assets/img/property-tax-icon1.png" height="67"
									width="67">
							</div>
							<div class="text-wrap vcenter">
								<h2
									class="mbr-fonts-style mbr-bold mbr-section-title3 display-5">
									<spring:message code="PropertyTaxCalculator"
										text="Property Tax Calculator" />
								</h2><br>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 mob-bg">
					<a href="#">
						<div class="wrap">
							<div class="ico-wrap">
								<img src="assets/img/android-67.png" height="67" />
							</div>
							<div class="text-wrap vcenter">
								<h2
									class="mbr-fonts-style mbr-bold mbr-section-title3 display-5">
									<spring:message code="GetMobileApp" text="Get Mobile App" />
								</h2><br/>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-6 mbr-col-md-10"></div>
			</div>
		</div>

	</section>
	
	Dashboard/Property Tax/Mobile App End -->

	<!-- modal for care registration on phone start -->
	<div class="modal fade" id="phonemodal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<spring:message code="eip.tel" text="Registration On Phone" />
					</h4>
				</div>
				<div class="modal-body">
					<p>
						<spring:message code="eip.grievance.msg"
							text="Citizens can Dial 1100 to Register the Grievance. ." />
					</p>
				</div>

			</div>
		</div>
	</div>
	<!-- modal for care registration on phone end -->



	<!-- 	<div class="common-bg">
		<div class="container-fluid" id="keyContact">
			<div class="row"> -->
	<c:if
		test="${empty command.themeMap['NEWS'] || command.themeMap['NEWS'] eq 'A'}">
		<c:set var="class3" value="col-lg-6 col-md-6 col-sm-6" />
	</c:if>


	<c:if test="${command.themeMap['SLIDER_IMG'] eq 'A'}">
		<c:set var="class3" value="col-lg-12" />
	</c:if>
	<c:if test="${command.themeMap['NEWS'] eq 'I'}">
		<c:set var="class3" value="col-lg-12" />
	</c:if>
	<c:if
		test="${empty command.themeMap['KEY_CONTACTS'] || command.themeMap['KEY_CONTACTS'] eq 'A'}">
		<c:set var="class" value="col-lg-6 col-md-6 col-sm-6" />
	</c:if>


	<c:if test="${command.themeMap['KEY_CONTACTS'] eq 'I'}">
		<c:set var="class" value="col-lg-12" />
	</c:if>
	<%-- <c:if test="${command.themeMap['KEY_CONTACTS'] ne 'I'}">
<!-- Key Contact Strat -->
					<div class="${class3}">

						<div class="key-contact contact">
							<div class="row">
								<div class="col-md-3">
									<div class="contact-info">
										<img src="assets/img/mail-contact.png" alt="image" width="64"
											height="64" />
										<h2>
											<spring:message code="KeyContacts" text="Key Contacts" />
										</h2>
										<h4><spring:message code="KeyContacts.tagline" text="Get in touch with us !" /></h4>
									</div>
								</div>
								<div class="col-md-9">
									<div id="list-main">
										<ul id="list">
											<c:forEach items="${command.organisationContactList }"
												var="contact">
												<c:if test="${ contact.designationEn ne 'P.S. To Minister'}">
													<li style="display: block;"><c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<h3>${contact.contactNameEn}
																	<small> ${contact.designationEn}</small>
																</h3>
															</c:when>
															<c:otherwise>
																<h3>${contact.contactNameReg}
																	<small> ${contact.designationReg}</small>
																</h3>
															</c:otherwise>
														</c:choose>
														<table width="100%" class="table-respns">
															<thead>
																<tr>
																	<th class="hidden-lg  hidden-md hidden-sm">Phone</th>
																	<th class="hidden-lg  hidden-md hidden-sm">Mobile</th>
																	<th class="hidden-lg  hidden-md hidden-sm">Email</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td class="hidden-xs" width="8%" align="center"><i
																		class="fa fa-phone" aria-hidden="true"><span
																			class="hide">Phone</span></i></td>
																	<td width="25%"><span class="minister-phone">
																			${contact.telephoneNo1En}</span></td>
																	<td class="hidden-xs" width="8%" align="center"><i
																		class="fa fa-mobile" aria-hidden="true"><span
																			class="hide">Mobile</span></i></td>
																	<td width="20%"><span class="minister-phone">${contact.telephoneNo2En}</span></td>
																	<td class="hidden-xs" width="8%" align="center"><i
																		class="fa fa-envelope" aria-hidden="true"><span
																			class="hide">Email</span></i></td>
																	<td width="36%"><span class="minister-phone"><a
																			href="mailto:${contact.email1}"
																			title="${contact.email1}">${contact.email1}</a></span></td>
																</tr>
															</tbody>
														</table></li>
												</c:if>
											</c:forEach>
										</ul>
										<c:forEach items="${command.organisationContactList }" var="contact">
										<c:if test="${ contact.designationEn ne 'P.S. To Minister'}">
										<div class="kCbox col-lg-4 col-md-6 col-sm-3 col-xs-6">
										<address>
										<c:choose>
										<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
												<strong>${contact.contactNameEn}</strong><br>
												${contact.designationEn}<br>
										</c:when>
										<c:otherwise>
												<strong>${contact.contactNameReg}</strong><br>
												${contact.designationReg}<br>	
										</c:otherwise>	
										</c:choose>	
												<abbr title="Phone"></abbr><i class="fa fa-phone" aria-hidden="true"></i> ${contact.telephoneNo1En}<br>
												<abbr title="Phone"></abbr><i class="fa fa-mobile" aria-hidden="true"></i> ${contact.telephoneNo2En}<br>
											  <a href="mailto:${contact.email1}" title="${contact.email1}">
											  <i class="fa fa-envelope" aria-hidden="true"></i> ${contact.email1}</a>
											</address>
											</div>
										</c:if>
									</c:forEach>		
										</div>
								</div>
							</div>
						</div>
					</div>
				</c:if> --%>
	<!--Key Contacts -->


	<!--  News and Events  -->
	<c:if test="${command.themeMap['SLIDER_IMG'] eq 'I'}">
		<c:if test="${command.themeMap['NEWS'] ne 'I'}">
			<%-- <div class="${class}" id="news">
						<div class=" news-department">
							<div class="container-fluid clear">
								<div class="row">
									<div class="col-md-3 col-lg-3">
										<div class="contact-info">
											<img src="assets/img/latest-news.png" alt="image"
												style="height: 64px; width: 64px;">
											<h2 data-animation-effect="fadeInDown"
												data-effect-delay="600">
												<spring:message code="portal.Latest.News" text="Latest News" />
											</h2>
											<h4><spring:message code="portal.News.tagline" text="Top Stories !!!" /></h4>
										</div>
									</div>
									<div class="col-md-9 col-lg-9 white" style="height: 600px;">
										<ul
											class="news-holder-departments clearfix announcement scrolllistbox">

											<c:forEach items="${command.eipAnnouncement}" var="lookUp"
												varStatus="status">

												<li class="wow fadeInLeft animated animated"
													data-wow-offset="10" data-wow-duration="1.5s"
													style="visibility: visible; -webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;">

													<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
													<c:forEach items="${links}" var="download"
														varStatus="count">
														<c:set var="idappender"
															value="<%=java.util.UUID.randomUUID()%>" />
														<c:set var="idappender"
															value="${fn:replace(idappender,'-','')}" />
														<c:set var="link"
															value="${stringUtility.getStringAfterChars(download)}" />

														<div class="news-description">

															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<div class="col-md-5 col-sm-5 col-lg-5 col-xs-5">
																		<!-- <div class="view overlay hm-white-slight z-depth-1-half"> -->

																		<c:if test="${ lookUp.attachImage ne ' '}">
																			<img src="${lookUp.attachImage }" class="img-fluid"
																				alt="Minor sample post image">
																		</c:if>
																		<c:if test="${ lookUp.attachImage eq ' '}">
																			<img src="\images\news.svg" class="img-fluid"
																				alt="Minor sample post image">
																		</c:if>
																		<!-- </div> -->
																	</div>

																	<div class="col-md-7 col-sm-7 col-lg-7 col-xs-7 ">

																		<c:choose>
																			<c:when test="${isDMS}">
																				<h3>
																					<a href="javascript:void(0);"
																						onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
																				</h3>
																			</c:when>
																			<c:otherwise>

																				<h3>
																					<apptags:filedownload filename="EIP"
																						filePath="${download}"
																						actionUrl="CitizenHome.html?Download"
																						eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
																				</h3>

																			</c:otherwise>
																		</c:choose>
																		<span class="date"><i class="fa fa-calendar"
																			aria-hidden="true"></i> <fmt:formatDate
																				value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>

																	</div>
																</c:when>
																<c:otherwise>
																	<div class="col-md-5 col-sm-5 col-lg-5 col-xs-5">
																		<!--  <div class="view overlay hm-white-slight z-depth-1-half"> -->
																		<c:if test="${ lookUp.attachImage ne ' '}">
																			<img src="${lookUp.attachImage }" class="img-fluid"
																				alt="Minor sample post image">
																		</c:if>
																		<c:if test="${ lookUp.attachImage eq ' '}">
																			<img src="\images\news.svg" class="img-fluid"
																				alt="Minor sample post image">
																		</c:if>
																		<!-- </div> -->
																	</div>
																	<div class="col-md-7 col-sm-7 col-lg-7 col-xs-7 ">

																		<c:choose>
																			<c:when test="${isDMS}">
																				<h3>
																					<a href="javascript:void(0);"
																						onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
																				</h3>
																			</c:when>
																			<c:otherwise>


																				<h3>
																					<apptags:filedownload filename="EIP"
																						filePath="${download}"
																						actionUrl="CitizenHome.html?Download"
																						eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
																				</h3>

																			</c:otherwise>
																		</c:choose>
																		<span class="date"><i class="fa fa-calendar"
																			aria-hidden="true"></i> <fmt:formatDate
																				value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
																	</div>
																</c:otherwise>
															</c:choose>

														</div>

													</c:forEach>

												</li>

											</c:forEach>



										</ul>

									</div>
								</div>
							</div>
						</div>
					</div> --%>
		</c:if>
	</c:if>
	<!--  News and Events  -->








	<!--Public Notice START  -->

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>

	</div>

	<hr />
	<div class=" drag1" id="PublicNotice">
		<div class="clear">
			<div class="row">

				<c:if
					test="${empty command.themeMap['PUBLIC_NOTICE'] || command.themeMap['PUBLIC_NOTICE'] eq 'A'}">
					<c:set var="class2" value="col-lg-6 col-md-6 col-sm-6" />
				</c:if>

				<c:if test="${command.themeMap['PUBLIC_NOTICE'] eq 'I'}">
					<c:set var="class2" value="col-lg-12" />
				</c:if>
				<c:if
					test="${empty command.themeMap['HELPLINE_NO'] || command.themeMap['HELPLINE_NO'] eq 'A'}">
					<c:set var="class1" value="col-lg-6 col-md-6 col-sm-6" />
				</c:if>

				<c:if test="${command.themeMap['HELPLINE_NO'] eq 'I'}">
					<c:set var="class1" value="col-lg-12" />
				</c:if>

				<c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">

					<div class="${class1}">
						<div class="widget">
							<div class="widget-header">
								<h2>
									<i class="fa fa-newspaper-o"></i>
									<spring:message code="PublicNotice" text="Public Notice" />
								</h2>
							</div>
							<div
								class="widget-content announcement height-350 scrolllistbox magazine-section public-notice">
								<c:if test="${command.highlighted eq true}">
									<c:set var="serial_count" scope="page" value="0" />
									<c:forEach items="${command.publicNotices}" var="lookUp"
										varStatus="lk">

										<c:if test="${lookUp.isHighlighted eq 'Y'}">
											<div class="public-notice">
												<div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
													<span class="serialno-new">${serial_count+1}</span>
													<c:set var="serial_count" scope="page"
														value="${serial_count+1}" />
												</div>

												<div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">
													<c:set var="link" value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
													<c:choose>
														<c:when test="${isDMS}">
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<a href="javascript:void(0);"
																		onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailEn}</a>
																</c:when>
																<c:otherwise>
																	<a href="javascript:void(0);"
																		onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailReg}</a>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${not empty lookUp.profileImgPath}">
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />																			 
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.detailEn}"></apptags:filedownload> --%>
																				
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">
																						${lookUp.detailEn}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.detailEn}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																		</c:when>
																		<c:otherwise>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.detailReg}"></apptags:filedownload> --%>
																				
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.detailReg}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.detailReg}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<c:if test="${lookUp.imagePath ne ' '}">																	
																	<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																		<c:set var="lnk1" value="./cache/${downloadL}" />																	
																		<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<c:if test="${not empty lookUp.detailEn}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
																			 	</c:forEach>
																			 	</div>${lookUp.detailEn}
																			 </a>
																			</c:if>
																			<c:if test="${empty lookUp.detailEn}">
																			<div class="displayimage">
																			<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 	<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
																			 </c:forEach>
																			</div>
																			</c:if>
																		</c:if>
																		<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																			<c:if test="${not empty lookUp.detailReg}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailReg}">
																				</c:forEach>
																				</div>${lookUp.detailReg}
																			</a>
																			</c:if>
																			<c:if test="${empty lookUp.detailReg}">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailReg}">
																				</c:forEach>
																			</div>
																			</c:if>
																		</c:if>
																	</c:if>
																</c:otherwise>
															</c:choose>															
														</c:otherwise>
													</c:choose>
													<p>
														<fmt:formatDate type="date" value="${lookUp.issueDate}"
															pattern="dd/MM/yyyy" var="issueDate" />
														${issueDate}
													</p>
													<input type="hidden" name="downloadLink"
														value="${lookUp.profileImgPath}">

													<%-- 	<c:choose>
													<c:when test="${isDMS}">
														<a href="jadocIdvascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.docName}</a>
													</c:when>
													<c:otherwise>
														<apptags:filedownload filename="EIP"
															filePath="${lookUp.profileImgPath}"
															actionUrl="CitizenPublicNotices.html?Download"
															eipFileName="${stringUtility.getStringAfterChars(lookUp.profileImgPath)}"></apptags:filedownload>
													</c:otherwise>
												</c:choose> --%>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</c:if>

							</div>
						</div>
					</div>
				</c:if>

				<!-- Helpline Number Start -->
				<%-- <c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}"> --%>
				<c:if test="${command.themeMap['KEY_CONTACTS'] ne 'I'}">
					<i id="keyContact"></i>
					<div class="${class2} help-no">
						<div class="widget">
							<div class="widget-header">
								<h2>
									<i class="fa fa-envelope" aria-hidden="true"></i>
									<%-- <spring:message code="eip.helplineNo" text="Helpline Numbers" /> --%>
									<spring:message code="KeyContacts" text="Key Contacts" />
								</h2>
							</div>
							<div class="widget-content  magazine-section scrolllistbox"
								style="height: 390px;">

								<c:forEach items="${command.organisationContactList }"
									var="contact">
									<c:if test="${ contact.designationEn ne 'P.S. To Minister'}">
										<div class="kCbox col-lg-6 col-md-12 col-sm-12 col-xs-12">
											<address>
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<strong>${contact.contactNameEn}</strong>
														<br>
														<span style="color: black">${contact.designationEn}</span>
														<br>
													</c:when>
													<c:otherwise>
														<strong>${contact.contactNameReg}</strong>
														<br>
														<span style="color: black">${contact.designationReg}</span>
														<br>
													</c:otherwise>
												</c:choose>
												<abbr title="Phone"></abbr><i class="fa fa-phone"
													aria-hidden="true"></i> ${contact.telephoneNo1En}<br>
												<abbr title="Phone"></abbr><i class="fa fa-mobile"
													aria-hidden="true"></i> ${contact.telephoneNo2En}<br>
												<a href="mailto:${contact.email1}" title="${contact.email1}">
													<i class="fa fa-envelope" aria-hidden="true"></i>
													${contact.email1}
												</a>
											</address>
										</div>
									</c:if>
								</c:forEach>

								<%-- <div class="dark-blue height-390">
										<div class="helpline help-line">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
												<div class="helpline-list col-xs-12">
													<span class="list-number  col-sm-12"><spring:message
															code="eip.citizen.center" text="Citizen's Call center" /></span>
													<span class="list-text"><spring:message
															code="" text="155300" /></span>

												</div>
											</div>
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
												<div class="helpline-list col-xs-12">
													<span class="list-number  col-sm-12"><spring:message
															code="eip.child.helpline" text="Child Helpline" /></span> <span
														class="list-text "><spring:message code="1098"
															text="1098" /></span>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
												<div class="helpline-list col-xs-12">
													<span class="list-number  col-sm-12"><spring:message
															code="eip.women.helpline" text="Women Helpline" /></span> <span
														class="list-text "><spring:message code=""
															text="1091" /></span>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
												<div class="helpline-list col-xs-12">
													<span class="list-number  col-sm-12"><spring:message
															code="eip.airport" text="Airport" /></span> <span
														class="list-text "><spring:message code=""
															text="2418201" /></span>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
												<div class="helpline-list col-xs-12">
													<span class="list-number  col-sm-12"><spring:message
															code="eip.railway" text="Railway" /></span> <span
														class="list-text "><spring:message code=""
															text="139/2528131" /></span>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
												<div class="helpline-list col-xs-12">
													<span class="list-number  col-sm-12"><spring:message
															code="eip.ambulance" text="Ambulance" /> </span> <span
														class="list-text "><spring:message code=""
															text="102, 108" /></span>
												</div>
											</div>

										</div>
									</div> --%>
							</div>
						</div>
					</div>
				</c:if>
				<!-- Key Contact End -->
			</div>
		</div>
	</div>

	<c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
		<i id="helpline"></i>
		<hr />
		<div class="col-md-12 col-lg-12 help-no depser text-center">
			<h2>
				<spring:message code="eip.helplineNo" text="Helpline Numbers" />
			</h2>
		</div>
		<div class="drag1">
			<div class="dark-blue height-3900">
				<div class="helpline help-line">
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
						<div class="helpline-list col-xs-12">
							<span class="list-number  col-sm-12"><spring:message
									code="eip.citizen.center" text="Citizen's Call center" /></span> <span
								class="list-text"><spring:message code="" text="155300" /></span>

						</div>
					</div>
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
						<div class="helpline-list col-xs-12">
							<span class="list-number  col-sm-12"><spring:message
									code="eip.child.helpline" text="Child Helpline" /></span> <span
								class="list-text "><spring:message code="1098"
									text="1098" /></span>
						</div>
					</div>
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
						<div class="helpline-list col-xs-12">
							<span class="list-number  col-sm-12"><spring:message
									code="eip.women.helpline" text="Women Helpline" /></span> <span
								class="list-text "><spring:message code="" text="1091" /></span>
						</div>
					</div>
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
						<div class="helpline-list col-xs-12">
							<span class="list-number  col-sm-12"><spring:message
									code="eip.airport" text="Airport" /></span> <span class="list-text "><spring:message
									code="" text="2418201" /></span>
						</div>
					</div>
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
						<div class="helpline-list col-xs-12">
							<span class="list-number  col-sm-12"><spring:message
									code="eip.railway" text="Railway" /></span> <span class="list-text "><spring:message
									code="" text="139/2528131" /></span>
						</div>
					</div>
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
						<div class="helpline-list col-xs-12">
							<span class="list-number  col-sm-12"><spring:message
									code="eip.ambulance" text="Ambulance" /> </span> <span
								class="list-text "><spring:message code=""
									text="102, 108" /></span>
						</div>
					</div>

				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</c:if>



	<!-- SCHEME AND BANNER LINK START -->
	<%-- <c:if
		test="${not empty command.themeMap['SCHEMES'] && command.themeMap['SCHEMES'] ne 'I'}">

		<div class="container-fluid facts" id="schemes">
			<c:if test="${command.scheme eq true}">

				<div class="row">
					<div class="col-md-12 col-lg-12">
						<h2 class="text-center" data-animation-effect="fadeInDown"
							data-effect-delay="600">
							<spring:message code="portal.Scheme" text="Schemes" />
						</h2>
					</div>
					<div class="container-fluid">
						<div class="clear" id="schemes-new">
							<div class="owl-carousel owl-theme">
								<c:set var="schemeCount" value="0" scope="page" />
								<c:forEach items="${command.publicNotices}" var="lookUp"
									varStatus="lk">
									<c:if
										test="${lookUp.isHighlighted ne 'Y' && lookUp.isUsefullLink ne 'Y'}">
										<c:set var="schemeCount" value="${schemeCount + 1}"
											scope="page" />

										<c:choose>
											<c:when test="${not empty lookUp.profileImgPath }">
												<c:set var="link"
													value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
												<div class="column">
													<div class="post-module">
														<c:if test="${lookUp.imagePath ne ' ' }">
															<c:set var="search" value="\\" />
															<c:set var="replace" value="\\\\" />
															<c:set var="path"
																value="${fn:replace(link,search,replace)}" />
															<c:if test="${empty lookUp.detailEn}">
																<div class="thumbnail">
																	<a href="javascript:void(0);"
																		onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')"
																		><img src="${lookUp.imagePath }"
																		class="img-responsive" alt="${lookUp.imagePath }"></a>
																</div>
															</c:if>
															<c:if test="${not empty lookUp.detailEn}">
																<div class="thumbnail">
																	<a href="javascript:void(0);"
																		onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')"
																		><img src="${lookUp.imagePath }"
																		class="img-responsive" alt="${lookUp.detailEn }"></a>
																</div>
															</c:if>
														</c:if>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<div class="post-content">
																	<h1 class="title">
																		<apptags:filedownload filename="EIP"
																			filePath="${lookUp.profileImgPath}"
																			actionUrl="CitizenPublicNotices.html?Download"
																			eipFileName="${lookUp.detailEn}"></apptags:filedownload>
																	</h1>
																	<h2 class="sub_title">
																		<apptags:filedownload filename="EIP"
																			filePath="${lookUp.profileImgPath}"
																			actionUrl="CitizenPublicNotices.html?Download"
																			eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload>
																	</h2>

																</div>
															</c:when>
															<c:otherwise>
																<div class="post-content">
																	<h1 class="title">
																		<apptags:filedownload filename="EIP"
																			filePath="${lookUp.profileImgPath}"
																			actionUrl="CitizenPublicNotices.html?Download"
																			eipFileName="${lookUp.detailReg}"></apptags:filedownload>
																	</h1>
																	<h2 class="sub_title">
																		<apptags:filedownload filename="EIP"
																			filePath="${lookUp.profileImgPath}"
																			actionUrl="CitizenPublicNotices.html?Download"
																			eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload>

																	</h2>
																</div>
															</c:otherwise>
														</c:choose>
													</div>
												</div>

											</c:when>
											<c:otherwise>

												<div class="column">
													<div class="post-module">
														<c:if test="${lookUp.imagePath ne ' '}">
															<c:if test="${not empty lookUp.detailEn}">
																<div class="thumbnail">
																	<a class="title" title="${lookUp.detailEn}"
																		href="${lookUp.link}" target="new"><img
																		src="${lookUp.imagePath }" class="img-responsive"
																		alt="${lookUp.detailEn }"></a>
																</div>
															</c:if>
															<c:if test="${empty lookUp.detailEn}">
																<div class="thumbnail">
																	<a class="title" title="${lookUp.detailEn}"
																		href="${lookUp.link}" target="new"><img
																		src="${lookUp.imagePath }" class="img-responsive"
																		alt="${lookUp.imagePath }"></a>
																</div>
															</c:if>
														</c:if>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<div class="post-content">
																	<h1 class="title">
																		<a class="" title="${lookUp.detailEn}"
																			href="${lookUp.link}" target="new">${lookUp.detailEn}</a>
																	</h1>
																	<h2 class="sub_title">
																		<a class="" title="${lookUp.noticeSubReg}"
																			href="${lookUp.link}" target="new">${lookUp.noticeSubReg}</a>
																	</h2>
																</div>
															</c:when>
															<c:otherwise>
																<div class="post-content">
																	<h1 class="title">
																		<a class="" title="${lookUp.detailReg}"
																			href="${lookUp.link}" target="new">${lookUp.detailReg}</a>
																	</h1>
																	<h2 class="sub_title">
																		<a class="" title="${lookUp.noticeSubReg}"
																			href="${lookUp.link}" target="new">${lookUp.noticeSubReg}</a>
																	</h2>
																</div>

															</c:otherwise>
														</c:choose>
													</div>
												</div>

											</c:otherwise>
										</c:choose>

									</c:if>
								</c:forEach>

							</div>
							<c:if test="${schemeCount gt 8}">
								<div class="news-bttn-area text-center">
									<a href="CitizenHome.html?schemes" class="bttn"
										title="View More"><spring:message
											code="portal.link.viewmore" text="VIEW MORE" /></a>
								</div>
							</c:if>
						</div>
					</div>
				</div>


			</c:if>
		</div>
	</c:if>  --%>

	<!-- SCHEME AND BANNER LINK END -->

	<%-- <c:if
		test="${not empty command.themeMap['IMP_LINKS'] && command.themeMap['IMP_LINKS'] ne 'I'}">
		<div class="container-fluid important-bg" id="importantLink">
			<div class="container">
				<div class="col-md-12 col-lg-12">
					<h2 class="text-center" data-animation-effect="fadeInDown"
						data-effect-delay="600">
						<spring:message code="ImportantLinks" text="Important Links" />
					</h2>
				</div>
				<div class="col-sm-12 col-lg-12 col-lg-12 col-xs-12 text-center">
					<ul>



						<c:forEach items="${command.publicNotices}" var="lookUp"
							varStatus="lk">

							<c:if test="${lookUp.isUsefullLink eq 'Y'}">
								<c:choose>
									<c:when test="${not empty lookUp.profileImgPath }">


										<c:set var="link"
											value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
										<li><c:if test="${lookUp.imagePath ne ' ' }">
												<c:set var="search" value="\\" />
												<c:set var="replace" value="\\\\" />
												<c:set var="path" value="${fn:replace(link,search,replace)}" />
												<c:if test="${empty lookUp.detailEn}">
													<a href="javascript:void(0);"
														onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')"
														><img src="${lookUp.imagePath }"
														class="img-responsive" alt="${lookUp.imagePath }"></a>
												</c:if>
												<c:if test="${not empty lookUp.detailEn}">
													<div class="banner-img">
														<a href="javascript:void(0);"
															onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')"
															><img src="${lookUp.imagePath }"
															class="img-responsive" alt="${lookUp.detailEn }"></a>
													</div>
												</c:if>
											</c:if> <c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:if test="${lookUp.imagePath eq' ' }">
														<p class="padding-18">
													</c:if>
													<apptags:filedownload filename="EIP" filePath="${lookUp.profileImgPath}"
														actionUrl="CitizenPublicNotices.html?Download"
														eipFileName="${lookUp.detailEn}"></apptags:filedownload>
													<c:if test="${lookUp.imagePath eq ' '}">
														</p>
													</c:if>
												</c:when>
												<c:otherwise>
													<c:if test="${lookUp.imagePath eq' ' }">
														<p class="padding-18">
													</c:if>
													<apptags:filedownload filename="EIP" filePath="${lookUp.profileImgPath}"
														actionUrl="CitizenPublicNotices.html?Download"
														eipFileName="${lookUp.detailReg}"></apptags:filedownload>
													<c:if test="${lookUp.imagePath eq ' '}">
														</p>
													</c:if>
												</c:otherwise>
											</c:choose></li>

									</c:when>
									<c:otherwise>


										<li><c:if test="${lookUp.imagePath ne ' '}">
												<c:if test="${not empty lookUp.detailEn}">
													<div class="banner-img">
														<a class="title" title="${lookUp.detailEn}"
															href="${lookUp.link}" target="_blank"><img
															src="${lookUp.imagePath }" class="img-responsive"
															alt="${lookUp.detailEn}"></a>
													</div>
												</c:if>
												<c:if test="${empty lookUp.detailEn}">
													<a class="title" title="${lookUp.detailEn}"
														href="${lookUp.link}" target="_blank"><img
														src="${lookUp.imagePath }" class="img-responsive"
														alt="${lookUp.imagePath }"></a>
												</c:if>
											</c:if> <c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:if test="${lookUp.imagePath eq ' '}">
														<p class="padding-18">
													</c:if>
													<a class="title" title="${lookUp.detailEn}"
														href="${lookUp.link}" target="_blank">${lookUp.detailEn}</a>
													<c:if test="${lookUp.imagePath eq ' ' }">
														</p>
													</c:if>
												</c:when>
												<c:otherwise>
													<c:if test="${lookUp.imagePath eq ' '}">
														<p class="padding-18">
													</c:if>
													<a class="title" title="${lookUp.detailReg}"
														href="${lookUp.link}" target="_blank">${lookUp.detailReg}</a>
													<c:if test="${lookUp.imagePath eq ' ' }">
														</p>
													</c:if>
												</c:otherwise>
											</c:choose></li>

									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>


					</ul>
				</div>
			</div>
		</div>
	</c:if> --%>
	<!--  IMPORTANT LINK-->



	<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
		<hr />
		<div class="container-fluid photo-boxx" id="photo-box">

			<div class="row">

				<div class="col-md-12 col-lg-12 depser">
					<h2 class="text-center " data-animation-effect="fadeInDown"
						data-effect-delay="600">
						<spring:message code="portal.Media.Gallery" text="Media Gallery" />
					</h2>
				</div>

				<div class="col-md-12 col-lg-12">
					<div class="col-sm-4">
						<h3>
							<spring:message code="PhotoGallery" text="Photo Gallery" />
						</h3>
						<div class="gallery-box">
							<a href="Content.html?links&page=Photo Gallery"
								title="<spring:message code="PhotoGallery" text="Photo Gallery"/>"><img
								src="assets/img/photos1.png" class="img-responsive"
								alt="Photo Gallery"></a> <a
								href="Content.html?links&page=Photo Gallery" class="view-more"
								title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message
									code="portal.link.viewmore" text="View More" />&nbsp;<i
								class="fa fa-arrow-right" aria-hidden="true"></i></a>

						</div>

					</div>
					<div class="col-sm-4">
						<h3>
							<spring:message code="VideoGallery" text="Video Gallery" />
						</h3>
						<div class="gallery-box">
							<a href="Content.html?links&page=Video Gallery"
								title="<spring:message code="VideoGallery" text="Video Gallery"/>"><img
								src="assets/img/videos1.png" class="img-responsive"
								alt="Video Gallery"></a> <a
								href="Content.html?links&page=Video Gallery" class="view-more"
								title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message
									code="portal.link.viewmore" text="View More" />&nbsp;<i
								class="fa fa-arrow-right" aria-hidden="true"></i> </a>


						</div>
					</div>
					<%--   <div class="col-sm-3">
    <h3><spring:message code="portal.dashboard" text="Dashboard"/></h3>

   <div class="gallery-box"><a href="<spring:message code="dashboard.url"/>" title="Publications"><img src="assets/img/desktop.png" class="text-center img-responsive" alt="Dashboard"></a>
     <a href="<spring:message code="dashboard.url"/>" class="view-more" title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message code="portal.link.viewmore" text="View More"/> <i class="fa fa-arrow-right" aria-hidden="true"></i> </a>

   </div></div> --%>
					<div class="col-sm-4">
						<h3>
							<spring:message code="Feedback" text="Feedback" />
						</h3>
						<div class="gallery-box">
							<a onclick="openPopup('CitizenFeedBack.html?publishFeedBack')"
								href='#' title="Feedback"><img
								src="assets/img/feedback1.png"
								class="text-center img-responsive" alt="Feedback"></a> <a
								onclick="openPopup('CitizenFeedBack.html?publishFeedBack')"
								href='#' class="view-more"
								title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message
									code="portal.link.viewmore" text="View More" />&nbsp;<i
								class="fa fa-arrow-right" aria-hidden="true"></i> </a>

						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>

	<!--<c:if test="${command.themeMap['EXTERNAL_SERVICES'] ne 'I'}">
		<div id="footer">
			<div class="container-fluid">

				<div class="row">

					<div class="col-md-12 col-xs-12">
						<h2>
							<spring:message code="external.services" text="External Services" />
						</h2>
						<div class="row">
							<div class="container-fluid ">
								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.certificates" text="Certificates" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=1&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.birth.certificate" text="Birth Certificate" /></a></li>
										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=2&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.death.certificate" text="Death Certificate" /></a></li>
										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=7&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.residence.certificate"
													text="Residence Certificate" /></a></li>
										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=3&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.marriage.registration"
													text="Marriage Registration" /> </a></li>
										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=7&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.caste.certificate" text="Caste Certificate" /> </a></li>
									</ul>

								</div>
								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.bills" text="Bills" />
									</h3>

									<ul class="nav nav-list narrow">


										<li><a
											href="https://portal2.bsnl.in/myportal/cfa.do?gclid=EAIaIQobChMIn_qSxqHF2QIVwQ0rCh3zPgMcEAAYASAAEgLzVfD_BwE"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.pay.bill" text="Pay Telephone Bill Online" /></a></li>
										<li><a
											href="http://www.cspdcl.co.in/cseb/(S(jwufbn0pgajoqjirwogy22xc))/billPayment.aspx"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.electricity.bill" text="Electricity Bill Payment" />
										</a></li>
										<li><a
											href="http://www.nagarnigamprojects.in/tax/admin/old-tax-pay/create"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.online.property"
													text="Online Property Tax Payment" /> </a></li>

									</ul>
								</div>
								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.election" text="Election" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a href=" http://election.cg.nic.in/elesrch/"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.search.name"
													text="Search Your Name In Voters List" /> </a></li>
										<li><a
											href="http://election.cg.nic.in/voterlist/Default.aspx"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.voter.list" text="Voters List" /> </a></li>
										<li><a
											href="http://election.cg.nic.in/voterlist/Default.aspx"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.list.polling" text="List of Polling Stations" />
										</a></li>
									</ul>

								</div>
								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.magisterial" text="Magisterial" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a
											href="http://districtcourt-raipur.cg.nic.in/dcraipur/"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.district.court" text="District Court Raipur" /> </a></li>
										<li><a href=" http://highcourt.cg.gov.in/" target="_new"><i
												class="fa fa-angle-double-right"></i> <spring:message
													code="ex.high.court" text="High Court of Chhattisgarh" />
										</a></li>
									</ul>

								</div>

								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.revenue" text="Revenue" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a href="https://revenue.cg.nic.in/revcase/"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.ecourt" text="Ecourt " /> </a></li>
										<li><a href="http://cg.nic.in/bhunaksha/" target="_new"><i
												class="fa fa-angle-double-right"></i> <spring:message
													code="ex.bhunaksha" text="Bhu-Naksha" /></a></li>
										<li><a href="https://bhuiyan.cg.nic.in/" target="_new"><i
												class="fa fa-angle-double-right"></i> <spring:message
													code="ex.bhuiyan" text="Bhuiyan" /> </a></li>
										<li><a
											href="http://cg.nic.in/raipur/misal/UserSearch.aspx"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.missal" text="Missal/Chakbandi" /></a></li>
									</ul>

								</div>

								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.social.security"
											text="Social Security" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a href=" http://sw.cg.gov.in/" target="_new"><i
												class="fa fa-angle-double-right"></i> <spring:message
													code="ex.social.welfare" text="Social Welfare" /> </a></li>

									</ul>

								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>-->


	<!--AUBOUT US START  -->
	<c:if
		test="${not empty command.themeMap['SCHEMES'] && command.themeMap['SCHEMES'] ne 'I'}">
		<c:if test="${not empty command.aboutUsDescFirstPara }">
			<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 about11"
				id="about11">
				<div class="about-content">
					<h1>
						<spring:message code="eip.citizen.aboutUs.title" />
					</h1>
					<p class="about about1">${command.aboutUsDescFirstPara}</p>

					<h1>
						<spring:message code="eip.citizen.aboutUs.vision" text="Vision" />
					</h1>
					<p class="about about2">${command.aboutUsDescSecondPara}</p>
					<a href="CitizenAboutUs.html" class="read-more pull-right"><spring:message
							code="ReadMore" text="read more ...." /> </a>

				</div>
			</div>
		</c:if>
	</c:if>
	<!--AUBOUT US END  -->
	<div class="clear"></div>
	<section class="subscribe-area pt-15">
		<div class="container">
			<div class="row">

				<div class="col-md-4">
					<div class="subscribe-text mb-15">
						<span><spring:message code="eip.newsletter.msg"
								text="JOIN OUR NEWSLETTER" /></span>
						<h2>
							<spring:message code="eip.newsletter"
								text="Suscribe for Newsletter" />
						</h2>
					</div>
				</div>
				<div class="col-md-8">
					<div class="subscribe-wrapper subscribe2-wrapper mb-15">
						<div class="error-div alert alert-danger alert-dismissable"
							role="alert" style="display: none"></div>
						<div class="message alert" role="alert" style="display: none"></div>
						<div class="subscribe-form">
							<form class="form">
								<spring:message code="newsletter.placeholder" var="placeholder3" />
								<input type="email" name="email" id="subscribeemail"
									autocomplete="off" aria-label="${placeholder3}"
									placeholder="${placeholder3}">


								<button onclick="subscribeNewsLetter(this)" type="button">
									<spring:message code="portal.subscribe" text="Subscribe" />
									<i class="fa fa-long-arrow-right"></i>
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>

		</div>
	</section>
</div>
<script src="js/eip/citizen/guest-home.js"></script>

<script>
	/*var number_of_pages=0;
	var page=0;*/
	$(document)
			.ready(
					function() {

						/*var show_per_page = 5;
						var number_of_items = $('#list').children('li').size();
						var number_of_pages = Math.ceil(number_of_items
								/ show_per_page);

						$('#list-main')
								.append(
										'<ul class="pagination"></ul><input id=current_page type=hidden><input id=show_per_page type=hidden>');
						$('#current_page').val(0);
						$('#show_per_page').val(show_per_page);

						var navigation_html = '';
						var current_link = 0;
						while (number_of_pages > current_link) {
							navigation_html += '<li><a class="page" onclick="go_to_page('
									+ current_link
									+ ')" longdesc="'
									+ current_link
									+ '">'
									+ (current_link + 1)
									+ '</a></li>';
							current_link++;
						}
						navigation_html += '';

						$('.pagination').html(navigation_html);
						$('.pagination li a.page:first').addClass('active');

						$('#list').children().css('display', 'none');
						$('#list').children().slice(0, show_per_page).css(
								'display', 'block');*/

						/*var show_per_page = 2;	    
						var number_of_items = $('.public-notice').size();
					    number_of_pages = Math.ceil(number_of_items/ show_per_page);;
						
					    $('.announcement').append('<ul class="pagination"></ul><input id=current_page type=hidden><input id=show_per_page type=hidden>');
					    $('#current_page').val(0);
					    $('#show_per_page').val(show_per_page);

					    var navigation_html = '';
					    var current_link = 0;					    
					    navigation_html += '<li><a class="page" onclick="go_to_page()" longdesc="' + current_link + '"><i class="fa fa-arrow-down" aria-hidden="true"></i></a></li><li><a class="page" onclick="go_back_page()" longdesc="' + current_link + '"><i class="fa fa-arrow-up" aria-hidden="true"></i></a></li>';
					    navigation_html += '';
					    
					    $('.pagination').html(navigation_html);
					    $('.pagination li a.page:first').addClass('active');

					    $('.public-notice').css('display', 'none');
					    $('.public-notice').slice(0, (show_per_page+1)).css('display', 'block');
					    $('.announcement').removeAttr("style");*/
					});

	/*function go_to_page() {
		if((number_of_pages-1) > page){
			page_num = (page+1);page++	
			var show_per_page = parseInt($('#show_per_page').val(), 0);
			start_from = page_num * show_per_page;
			end_on = start_from + show_per_page;
			$('.public-notice').css('display', 'none').slice(start_from, end_on).css('display', 'block');
			$('.pagination li a.page.active').removeClass('active');
			$('.pagination li a.page[longdesc=' + page_num + ']').addClass('active');
			$('#current_page').val(page_num);
			$('.announcement').removeAttr("style");
		}	
	}
	function go_back_page() {
		if(page > 0){
			page_num = (page-1);page--;
			var show_per_page = parseInt($('#show_per_page').val(), 0);
			start_from = page_num * show_per_page;
			end_on = start_from + show_per_page;
			$('.public-notice').css('display', 'none').slice(start_from, end_on).css('display', 'block');
			$('.pagination li a.page.active').removeClass('active');
			$('.pagination li a.page[longdesc=' + page_num + ']').addClass('active');
			$('#current_page').val(page_num);
			$('.announcement').removeAttr("style");
		}
	}*/
</script>
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
							$("div.bhoechie-tab>div.bhoechie-tab-content").eq(
									index).addClass("active");
						});

				$(".header-inner").addClass('hide');

			});
</script>
<c:if
	test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
	<script>
		$(document).ready(
				function() {
					var space = $('.minister-details').offset().top;
					if (space >= 200) {
						$('.org-select').addClass('org-select-slider').css(
								'float', 'right');
						/* $('.minister-div').removeClass('col-lg-9').addClass('col-lg-12');
						$('.minister-div').removeClass('col-md-8').addClass('col-md-12'); */

					} else {
						$('.org-select').addClass('org-select-minister');
						/* $('.minister-div').removeClass('col-lg-12').addClass('col-lg-9');
						$('.minister-div').removeClass('col-md-12').addClass('col-md-8'); */
					}
				});
	</script>
</c:if>

<c:if
	test="${(not empty command.themeMap['SLIDER_IMG']) && (command.themeMap['SLIDER_IMG'] ne 'A')}">
	<script>
		$(function() {
			$('.about-bg').css('margin-top', '65px');
		});
	</script>
</c:if>
<script>
	$(function() {
		$(".Highlighted")
				.each(
						function(index) {

							if ($(this).val() == "true") {
								var a = $(this).closest('li').find('a').last()
										.addClass('new');
								$(a)
										.append(
												"&nbsp;<img src='assets/img/new-blink.gif' alt='new'>");
							}
						});
		
						
	});
</script>
