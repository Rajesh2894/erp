<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility"
	class="com.abm.mainet.common.util.StringUtility" />

<!-- <link href="https://fonts.googleapis.com/css?family=Alegreya+Sans" rel="stylesheet"> -->
<link rel="stylesheet"
	href="assets/libs/bootstrap/css/bootstrap.min.css">
<!-- <link href="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.css"
	rel="stylesheet" type="text/css" />
<script
	src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script> -->
<link href="assets/libs/tmc-plugins/mCustomScrollbar/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/tmc-plugins/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	
<script src="assets/js/jquery.marquee.min.js"></script>
<link href="assets/css/style-blue-theme-3.css" rel="stylesheet"
	type="text/css" />

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

$(document).ready(function(){
	//Owl Caraousel-----------------------------------------------

	$( "#photo-carosel .owl-next").html('<img alt="Image Next" src="https://d1ycj7j4cqq4r8.cloudfront.net/bbb447994b253bea1bb81b002e3413b2.svg" />');
		$( "#photo-carosel .owl-prev").html('<img alt="Image Prev" src="https://d1ycj7j4cqq4r8.cloudfront.net/20bd4ea61b53e89f4d8c6531d59f19ab.svg" />');

	$('#photo-carosel .owl-carousel').owlCarousel({
		autoPlay: 3000, //Set AutoPlay to 3 seconds
		items : 4,
		margin: 20,
		stagePadding: 50,
		nav: true,
		navText:["<div class='nav-btn prev-slide'></div>","<div class='nav-btn next-slide'></div>"]
		/* responsive: {
			0: {
				items: 1
			},
			600: {
				items: 3
			},
			1000: {
				items: 4
			}
		} */
	});
	
   $(".press-release").click(function(){
	   
	    var imageId = $(this).data('id');
	    var imageList=imageId.split(",");
	    $('div#press-modal-body-div > div').remove();	
	    $('div#press-modal-body-div > img').remove();
		for(i=0;i<imageList.length;i++){
			if(i==0){
				$("<div>", {
				    'class': "item active press-release-item"
				}).appendTo('.press-modal-body');					
			}
			else{
				$("<div>", {
				    'class': "item press-release-item"
				}).appendTo('.press-modal-body');
			}

			var img = $('<img alt="Image" class="img-responsive" />').attr({
	            'id': 'myImage'+i,
	            'src': './cache/'+imageList[i]
	        }).appendTo('.press-release-item');
	    }
	    
     $('#pressreleasediv').modal('show');
   });
});


	$(function() {
		var curHeight = $('.about1').height();
		var curHeight2 = $('.about2').height();
		if (curHeight >= 117 || curHeight2 >= 117) {
			$('.read-more').css('display', 'block');
		} else
			$('.read-more').css('display', 'none');
	});
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

<div class="invisibeHead"></div>
<div id="main">
	<!-- Scrolling Messages -->
	<div class="scroll-messages">
		<!-- CIVIC MESSAGE Marquee -->
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="civic-message">
				<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12">
					<h3>
						<spring:message code="theme3.portal.civic.msg"
							text="Civic Message :" />
					</h3>
				</div>
				<div class="marquee col-lg-10 col-md-9 col-sm-8 col-xs-12">
					<c:forEach items="${command.getAllhtml('CIVIC MESSAGE')}"
						var="civicMsg"> ${civicMsg} </c:forEach>
				</div>
			</div>
		</div>
		<!-- CIVIC MESSAGE Marquee ends -->
		<div class="clear"></div>

		<hr class="margin-top-5 margin-bottom-5" />
		<!-- RDMC ALERT Marquee -->
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="rdmc-alert">
				<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12">
					<h3>
						<spring:message code="theme3.portal.rdmc.alert"
							text="RDMC Alert :" />
					</h3>
				</div>
				<div class="marquee col-lg-10 col-md-9 col-sm-8 col-xs-12">
					<c:forEach items="${command.getAllhtml('RMDC ALERT')}"
						var="rmdcMsg"> ${rmdcMsg} </c:forEach>
				</div>
			</div>
		</div>
		<!-- RDMC ALERT Marquee ends -->
		<div class="clear"></div>
	</div>
	<!-- Scrolling Messages ends -->
	<div class="clear"></div>
	
	<!-- Covid-19 Dashboard Link starts  -->
	<div class="covid-dashboard-main col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<!-- <a href="./SectionInformation.html?editForm&rowId=376" class="covid-19-dashboard"> -->
		<a href="https://covidthane.org/covid-19.html" target="_blank" class="covid-19-dashboard">
			<spring:message code="theme3.portal.covid.new.dashboard" text="Covid 19 Dashboard" />
			<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new"/>
		</a>
	</div>
	<!-- Covid-19 Dashboard Link ends  -->

	<!-- Column 1 starts -->
	<div class="column-1 col-xs-12 col-sm-12 col-md-3 col-lg-3">
	
		<!--MINISTER START  -->
		<c:if test="${command.themeMap['COMMITTEE_MEMBERS'] ne 'I'}">
			<div class="about-bg container-fluid minister-details" id="container-fluid">
				<div class="minister-org">
	
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
					<div class="minister-div">
						<!-- <br /> -->
						<div>
							<nav>
								<div class="minister-ul">
									<div class="panel">
										<%-- <div class="panel-heading">
											<i class="fa fa-users" aria-hidden="true"></i>
											<spring:message code="theme3.portal.our.honours" text="Our Honours" />
										</div> --%>
										<div class="panel-body">
											<ul>
												<c:if test="${not empty sessionScope.mayorProfile}">
													<li>
														<div class="content-img">
															<img src="${sessionScope.mayorprofileImage}"
																alt="${sessionScope.mayorName}"
																title="${sessionScope.mayorName}" class="img-responsive">
														</div>
														<div class="minister-info">
															<h3>${sessionScope.mayorName}</h3>
															<p class="designation">${sessionScope.mayorProfile}</p>
														</div>
													</li>
												</c:if>
												<c:if test="${not empty sessionScope.deputyMayorProfile}">
													<li>
														<div class="content-img">
															<img src="${sessionScope.deputyMayorProfileImage}"
																alt="${sessionScope.deputyMayorName}"
																title="${sessionScope.deputyMayorName}"
																class="img-responsive">
														</div>
														<div class="minister-info">
															<h3>${sessionScope.deputyMayorName}</h3>
															<p class="designation">${sessionScope.deputyMayorProfile}</p>
														</div>
													</li>
												</c:if>
												<c:if test="${not empty sessionScope.commissionerProfile}">
													<li>
														<div class="content-img">
															<img src="${sessionScope.commissionerProfileImage}"
																alt="${sessionScope.commissionerName}"
																title="${sessionScope.commissionerName}"
																class="img-responsive">
														</div>
														<div class="minister-info">
															<h3>${sessionScope.commissionerName}</h3>
															<p class="designation">${sessionScope.commissionerProfile}</p>
														</div>
													</li>
												</c:if>
												
												<c:if test="${empty sessionScope.mayorProfile}">
													<li>
														<div class="content-img">
															<img src="assets/img/Hon'ble-Minister.jpg"
																alt="Hon'ble-Minister" title="Hon'ble-Minister"
																class="img-responsive">
														</div>
														<br />
													</li>
												</c:if>
												<c:if test="${empty sessionScope.deputyMayorProfile}">
													<li>
														<div class="content-img">
															<img src="assets/img/Hon'ble-Minister.jpg"
																alt="Hon'ble-Minister" title="Hon'ble-Minister"
																class="img-responsive">
														</div>
														<br />
													</li>
												</c:if>
												<c:if test="${empty sessionScope.commissionerProfile}">
													<li>
														<div class="content-img">
															<img src="assets/img/Hon'ble-Minister.jpg"
																alt="Hon'ble-Minister" title="Hon'ble-Minister"
																class="img-responsive">
														</div>
														<br />
													</li>
												</c:if>
												
											
											</ul>
										</div>
									</div>
	
									<%-- <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
										<c:if test="${not empty sessionScope.deputycommissionerProfile}">
	
											<div class=" col-xs-6 col-lg-3 col-md-3 col-sm-3">
	
												<div class="content-img">
													<img src="${sessionScope.deputycommissionerProfileImage}"
														alt="${sessionScope.deputyCommissionerName}"
														title="${sessionScope.deputyCommissionerName}"
														class="img-responsive">
													<p class="designation">${sessionScope.deputycommissionerProfile}</p>
												</div>
												<div class="minister-info">
													<h3>${sessionScope.deputyCommissionerName}</h3>
													
													<p class="minister-phone">
														<i class="fa fa-volume-control-phone" aria-hidden="true"></i>
														${sessionScope.deputyMayorSummaryEng}
													</p>
												</div>
											</div>
										</c:if>
									</c:if> --%>
	
									<%-- <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
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
									</c:if> --%>
									
								</div>
							</nav>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<!--MINISTER END  -->
		
		<!-- Key Contacts starts -->
		<div class="key-contacts-main">
			<c:if test="${command.themeMap['KEY_CONTACTS'] ne 'I'}">
			<i id="keyContact"></i>
			<div class="panel">
				<div class="panel-heading">
					<spring:message code="KeyContacts" text="Key Contacts" />
				</div>
				<div class="panel-body">
					<c:forEach items="${command.organisationContactList }"
						var="contact">
						
							<div class="kCbox col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<address>
									<c:choose>
										<c:when
											test="${userSession.getCurrent().getLanguageId() eq 1}">
											<strong>${contact.contactNameEn}</strong>
											<br>
											<span>${contact.designationEn}</span>
											<br>
										</c:when>
										<c:otherwise>
											<strong>${contact.contactNameReg}</strong>
											<br>
											<span>${contact.designationReg}</span>
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
						
					</c:forEach>
				</div>
			</div>
			</c:if>
		</div>
		<!-- Key Contacts ends -->
		
		<!-- Helpline starts -->
		<c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
		<i id="helpline"></i>
		<div class="helpline-main-container">
			<div class="panel">
				<div class="panel-heading">
					<spring:message code="theme3.portal.helpline" text="Helpline" />
				</div>
				<div class="panel-body">
					<div class="helpline-contacts">
						<ul>
							<li><spring:message code="theme3.portal.grievances.complaints" text="Grievances / Complaints" /></li>
							<li><spring:message code="theme3.portal.grievances.complaints.num" text="+91-22-25331590 / +91-22-25331211" /></li>
						</ul>
					</div>
					<div class="helpline-contacts">
						<ul>
							<li><spring:message code="theme3.portal.rdmc.no" text="Regional Disaster Management Cell" /></li>
							<li><spring:message code="theme3.portal.rdmc.num" text="1800-222-108" /></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		</c:if>
		<!-- Helpline ends -->
		
		<!-- Social Media starts -->
		<c:if test="${command.userSession.socialMediaMap.size() gt 0}">
		<div class="social-media-main">
			<div class="panel">
				<div class="panel-heading">
					<spring:message code="theme3.portal.social.media" text="Social Media" />
				</div>
				<div class="panel-body">
					<ul>
						<c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
							<li>
								<a href="${media.value}" target="new_${ count.count}">
									<i class="fa fa-${media.key}"> <span class="hidden">${media.key}</span></i>
									<%-- Like Us on ${media.key} --%>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		</c:if>
		<!-- Social Media ends -->
		
	</div>
	<!-- Column 1 ends -->
	
	<!-- Column 2 starts -->
	<div class="column-2 col-xs-12 col-sm-12 col-md-6 col-lg-6">
	
		<!-- Slider Start -->
		<c:if test="${command.themeMap['SLIDER_IMG'] ne 'I'}">
			<div id="container-fluid1" class="slider-minister-profiles-main">
				<div id="sld">
					
				
					<!-- <div class="row"> -->
						<div id="myCarousel" class="carousel slide carousel-fade"
							data-ride="carousel">
							<c:set var="maxfilecount"
								value="${userSession.getSlidingImgLookUps().size()}" />
							<!-- Indicators -->
							<ol class="carousel-indicators">
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
					<!-- </div> -->
					
					<!-- Corona Covid Link starts -->
					<div class="corona-main">
						<a href="./SectionInformation.html?editForm&rowId=351" class="flash-text1">
							<spring:message code="theme3.portal.corona.covid.19" text="Corona (Covid-19)"/>
							<img src="./assets/img/flashing-new.png" class="flash-new"/>
						</a>
					</div>
					<!-- Corona Covid Link ends -->
					
				</div>
				
				<%-- <div class="col-xs-12 col-sm-12 col-md-5 col-lg-5 covid-dashboard">
					<a href="https://essentials.thanecity.gov.in/covid-19.html" target="_blank">
						<img src="assets/img/tmc-covid-map.jpg" alt="TMC Covid-19 Dashboard" class="covid-dashboard-img">
					</a>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 covid-dashboard-links">
					<a href="https://essentials.thanecity.gov.in/covid19ThaneCurrentStatus.html" target="_blank">
						<spring:message code="theme3.portal.covid.dashboard.link1" text="COVID 19 Thane Current Status" />
					</a>
					<a href="https://essentials.thanecity.gov.in/growthRateOfPositiveCases.html" target="_blank">
						<spring:message code="theme3.portal.covid.dashboard.link2" text="Growth rate of Positive Cases" />
					</a>
					<a href="https://essentials.thanecity.gov.in/dateWiseTrendlineOfPositiveCases.html" target="_blank">
						<spring:message code="theme3.portal.covid.dashboard.link3" text="Date Wise Trendline of Positive Cases" />
					</a>
					<a href="https://essentials.thanecity.gov.in/positiveCasesbyAgeGroup.html" target="_blank">
						<spring:message code="theme3.portal.covid.dashboard.link4" text="Positive Cases by Age Group" />
					</a>
					<a href="https://essentials.thanecity.gov.in/containmentPlan.html" target="_blank">
						<spring:message code="theme3.portal.covid.dashboard.link5" text="Containment Plan & Containment Zone" />
					</a>
					<a href="https://essentials.thanecity.gov.in/communityLevelInitiativeByTMC.html" target="_blank">
						<spring:message code="theme3.portal.covid.dashboard.link6" text="Community Level Initiatives by TMC" />
					</a>
				</div> --%>
	
				<!-- <div class="clearfix"></div> -->
				
			</div>
		</c:if>
		<!-- Slider End -->
		
		<!-- Six sections starts  -->
		<div class="six-sections-main">
			<div class="drag1" id="PublicNotice">
				<div class="clear">
					<!-- <div class="row"> -->
		
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
		
						<!-- New Links -->
						<c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">
		
							<!-- <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> -->
								<div class="widget">
									<div class="widget-header">
										<h2>
											<i class="fa fa-external-link" aria-hidden="true"></i>
											<spring:message code="theme3.portal.new.links" text="New Links" />
											<a href="CitizenHome.html?schemes" class="pull-right"
												title="View More"><spring:message
													code="portal.link.viewmore" text="VIEW MORE" /></a>
										</h2>
									</div>
									<!-- <div class="widget-content announcement height-350 scrolllistbox magazine-section public-notice"> -->
									<div class="widget-content announcement height-350 magazine-section public-notice overflow-y-auto
									">
										<c:if test="${command.usefull eq true}">
											<c:set var="serial_count" scope="page" value="0" />
											<c:forEach items="${command.publicNotices}" var="lookUp"
												varStatus="lk">
		
												<c:if
													test="${lookUp.isUsefullLink eq 'Y' && serial_count < 7}">
													<div class="public-notice">
														
														<c:set var="serial_count" scope="page" value="${serial_count+1}" />
														<div class="col-md-12 col-sm-12 col-lg-12 col-xs-12">
														
															<c:set var="link"
																value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
															<c:choose>
																<c:when test="${isDMS}">
																	<c:choose>
																		<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubEn}</a>
																		</c:when>
																		<c:otherwise>
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubReg}</a>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" /> 
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lnk1}"  
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubEn}"></apptags:filedownload> --%>
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubEn}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubEn}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																				
																			</c:forEach>
																			<c:if test="${not empty exlink}">
																				<a href="${exlink}" target="_blank" class="">&nbsp;<i class="fa fa-external-link" style="font-size:24px;color:red"></i>&nbsp;</a>
																			</c:if>
																		</c:when>
																		<c:otherwise>
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			<c:set var="lnk1" value="./cache/${downloadL}" /> 
																			
																			<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubReg}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubReg}
																					</c:if>
																			</c:if>
																			<c:if test="${count.index ne 0 }">
																			<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																			</c:if>
																			
																			
																			
																			<%-- <apptags:filedownload filename="EIP" 
																				filePath="${lnk1}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload> --%>
																			</c:forEach>
																			<c:if test="${not empty exlink}">
																				<a href="${exlink}" target="_blank" class="">&nbsp;<i class="fa fa-external-link" style="font-size:24px;color:red"></i>&nbsp;</a>
																			</c:if>
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
		
															<input type="hidden" name="downloadLink"
																value="${lookUp.profileImgPath}">
		
														</div>
													</div>
												</c:if>
											</c:forEach>
										</c:if>
									</div>
								</div>
							<!-- </div> -->
						</c:if>
						<!-- New Links ends -->
		
						<!-- Important Links -->
						<c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">
		
							<!-- <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> -->
								<div class="widget">
									<div class="widget-header">
										<h2>
											<i class="fa fa-link" aria-hidden="true"></i>
											<spring:message code="theme3.portal.imp.links"
												text="Important Links" />
											<a href="CitizenHome.html?usefullLink" class="pull-right"
												title="View More"><spring:message
													code="portal.link.viewmore" text="VIEW MORE" /></a>
										</h2>
									</div>
									<!-- <div class="widget-content announcement height-350 scrolllistbox magazine-section public-notice"> -->
									<div class="widget-content announcement height-350 magazine-section public-notice overflow-y-auto
									">
									
										<c:if test="${command.highlighted eq true}">
											<c:set var="serial_count" scope="page" value="0" />
											<c:forEach items="${command.publicNotices}" var="lookUp"
												varStatus="lk">
		
												<c:if
													test="${lookUp.isHighlighted eq 'Y' && serial_count < 7}">
													<div class="public-notice">
														<%-- <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
															<span class="serialno-new">${serial_count+1}</span>
															<c:set var="serial_count" scope="page"
																value="${serial_count+1}" />
														</div> --%>
														<c:set var="serial_count" scope="page" value="${serial_count+1}" />
														<div class="col-md-12 col-sm-12 col-lg-12 col-xs-12">
															<c:set var="link"
																value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
															<c:choose>
																<c:when test="${isDMS}">
																	<c:choose>
																		<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubEn}</a>
																		</c:when>
																		<c:otherwise>
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubReg}</a>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																				<c:set var="lnk1" value="./cache/${downloadL}" /> 
																				
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubEn}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubEn}
																					</c:if>
																					
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																			<c:if test="${not empty exlink}">
																				<a href="${exlink}" target="_blank" class="">&nbsp;<i class="fa fa-external-link" style="font-size:24px;color:red"></i>&nbsp;</a>
																			</c:if>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubEn}"></apptags:filedownload> --%> 
																		</c:when>
																		<c:otherwise>
																		
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																				<c:set var="lnk1" value="./cache/${downloadL}" /> 
																				
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubReg}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubReg}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																			<c:if test="${not empty exlink}">
																				<a href="${exlink}" target="_blank" class="">&nbsp;<i class="fa fa-external-link" style="font-size:24px;color:red"></i>&nbsp;</a>
																			</c:if>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload> --%>
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
															<input type="hidden" name="downloadLink"
																value="${lookUp.profileImgPath}">
		
														</div>
													</div>
												</c:if>
											</c:forEach>
										</c:if>
		
		
									</div>
								</div>
							<!-- </div> -->
						</c:if>
						<!-- Important Links -->
		
						<!-- Press Release -->
						<c:if test="${command.themeMap['NEWS'] ne 'I'}">
							<!-- <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 press-release-main"> -->
							<div class="press-release-main">
								<div class="widget">
									<div class="widget-header">
										<h2>
											<i class="fa fa-newspaper-o"></i>
											<spring:message code="theme3.portal.press.release"
												text="Press Release" />
											<a href="CitizenHome.html?newsAndEvent" class="pull-right"
												title="View More"><spring:message
													code="portal.link.viewmore" text="VIEW MORE" /></a>
										</h2>
									</div>
									
									<div class="widget-content announcement height-350 magazine-section public-notice overflow-y-auto">
									
										<c:if test="${command.highlightedAnnouncement eq true}">
											<c:set var="serial_count" scope="page" value="0" />
											<c:forEach items="${command.eipAnnouncement}" var="lookUp"
												varStatus="status">
		
												<c:if test="${lookUp.isHighlighted eq 'Y' && serial_count < 7}">
													<div class="public-notice">
														
														<c:set var="serial_count" scope="page" value="${serial_count+1}" />
														<div class="col-md-12 col-sm-11 col-lg-12 col-xs-12">
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	
																	<div class="col-lg-8 col-md-7 col-sm-12 col-xs-12 padding-left-0">
																		<c:choose>
																			<c:when test="${isDMS}">
																				<a href="javascript:void(0);"
																					onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
																			</c:when>
																			<c:otherwise>
										 										<c:choose>
																				<c:when
																					test="${not empty lookUp.attach}">
																					<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
																					<c:forEach items="${links}" var="download" varStatus="count">
																					<c:if test="${count.index eq 0 }">
																					<a href="./cache/${download}" target="_blank" class="">${lookUp.announceDescEng}</a>
																					</c:if>
																					<c:if test="${count.index ne 0 }">
																					<a href="./cache/${download}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					</c:forEach>																				
																				</c:when>
																				<c:otherwise>
																					${lookUp.announceDescEng}
																				</c:otherwise>
																				</c:choose>																		
																			</c:otherwise>
																		</c:choose>
																	</div>
																	
																	<span
																		class="date col-lg-3 col-md-4 col-sm-12 col-xs-12 padding-right-0"><i
																		class="fa fa-calendar" aria-hidden="true"></i> <fmt:formatDate
																			value="${lookUp.newsDate}" pattern="dd-MM-yyyy" /></span>
																<div class="col-lg-1 col-md-1 col-sm-12 col-xs-12 modal-btn-container">
																	<c:if test="${not empty lookUp.attachImage}">
																	<a class="press-release" data-toggle="modal" data-id="${lookUp.attachImage}"  ><i class="fa fa-picture-o " ></i></a>
																	</c:if>
																	</div>
																</c:when>
																<c:otherwise>
																	
																	<div
																		class="col-lg-8 col-md-7 col-sm-12 col-xs-12 padding-left-0">
																		<c:choose>
																			<c:when test="${isDMS}">
																				<a href="javascript:void(0);"
																					onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
																			</c:when>
																			<c:otherwise>
																			<c:choose>
																				<c:when
																					test="${not empty lookUp.attach}">
																					<c:set var='attachment' value= './cache/${lookUp.attach}' />
																					<a href="${attachment}" target="_blank" class="">${lookUp.announceDescEng}</a>										
																				</c:when>
																				<c:otherwise>
																					${lookUp.announceDescReg}
																				</c:otherwise>
																				</c:choose>	
																			</c:otherwise>
																		</c:choose>
																	</div>
																	<span
																		class="date col-lg-3 col-md-4 col-sm-12 col-xs-12 padding-right-0"><i
																		class="fa fa-calendar" aria-hidden="true"></i> <fmt:formatDate
																			value="${lookUp.newsDate}" pattern="dd-MM-yyyy" /></span>
																			<div class="col-lg-1 col-md-1 col-sm-12 col-xs-12 modal-btn-container">
																	<c:if test="${not empty lookUp.attachImage}">
																	<a class="press-release" data-toggle="modal" data-id="${lookUp.attachImage}"  ><i class="fa fa-picture-o " ></i></a>
																	</c:if>
																	</div>
																</c:otherwise>
															</c:choose>
															
														</div>
													</div>
												</c:if>
											</c:forEach>
										</c:if>
									</div>
								</div>
							</div>
						</c:if>
						<!-- Press Release ends -->
		
						<!-- Modal for Press Release Events starts -->
						<div id="pressreleasediv" class="modal fade press-modal press-release-modal-class" role="dialog">
							<div class="modal-dialog modal-lg">
								<!-- Modal content-->
								<div class="modal-content">
									<button type="button" class="close" data-dismiss="modal">
										<i class="fa fa-times" aria-hidden="true"></i>
									</button>
									<div class="modal-body ">
										<div id="myCarousel-1" class="carousel slide carousel-fade" data-ride="carousel">
											<!-- Wrapper for slides -->
											<div class="carousel-inner press-modal-body" id="press-modal-body-div">
		
											</div>
					
											<!-- Left and right controls -->
											<a class="left carousel-control hidden-xs" href="#myCarousel-1"	data-slide="prev">
												<i class="fa fa-angle-left" aria-hidden="true"></i>
												<span class="sr-only">Previous</span>
											</a>
											<a class="right carousel-control hidden-xs" href="#myCarousel-1" data-slide="next">
												<i class="fa fa-angle-right" aria-hidden="true"></i>
												<span class="sr-only">Next</span>
											</a>
										</div>
										
									</div>
								</div>
		
							</div>
						</div>
						<!-- Modal for Press Release Events ends -->
		
					<!-- </div> -->
				</div>
			</div>
		
			<!-- Media Gallery starts -->
			<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
			<div class="photo-boxx" id="photo-box">
	
				<!-- <div class="row"> -->
				<div class="panel">
					<div class="panel-heading">
						<spring:message code="portal.Media.Gallery" text="Media Gallery" />
					</div>
					<div class="panel-body">
						<div class="photo-video-main">
							<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
								<div class="gallery-box">
									<%-- <a href="Content.html?links&page=Photo Gallery"
										class="col-xs-6 col-sm-6 col-md-6 col-lg-6"
										title="<spring:message code="PhotoGallery" text="Photo Gallery"/>"><img
										src="assets/img/photos1.png" class="img-responsive"
										alt="Photo Gallery"></a> <a
										href="Content.html?links&page=Photo Gallery"
										class="view-more col-xs-6 col-sm-6 col-md-6 col-lg-6"
										title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message
											code="portal.link.viewmore" text="View More" />&nbsp;<i
										class="fa fa-arrow-right" aria-hidden="true"></i></a> --%>
										
										<%-- <a href="./SectionInformation.html?editForm&rowId=24"
										class="col-xs-6 col-sm-6 col-md-6 col-lg-6"
										title="<spring:message code="PhotoGallery" text="Photo Gallery"/>">
											<img src="assets/img/photos1.png" class="img-responsive" alt="Photo Gallery">
										</a> --%>
										<a href="./SectionInformation.html?editForm&rowId=24" class="view-more"
										title="<spring:message code="portal.link.viewmore" text="View More"/>">
											<div class="photo-thumbnail">
												<div class="thumbnail-text">
													<h3><spring:message code="PhotoGallery" text="Photo Gallery" /></h3>
													<i class="fa fa-arrow-circle-right" aria-hidden="true"></i>
												</div>
												<div class="icon-img">
													<i class="fa fa-picture-o" aria-hidden="true"></i>
												</div>
											</div>
										</a>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
								<div class="gallery-box">
									<%-- <a href="Content.html?links&page=Video Gallery"
										class="col-xs-6 col-sm-6 col-md-6 col-lg-6"
										title="<spring:message code="VideoGallery" text="Video Gallery"/>"><img
										src="assets/img/videos1.png" class="img-responsive"
										alt="Video Gallery">
									</a> --%>
									<a href="Content.html?links&page=Video Gallery" class="view-more"
									title="<spring:message code="portal.link.viewmore" text="View More"/>">
										<div class="video-thumbnail">
											<div class="thumbnail-text">
												<h3><spring:message code="VideoGallery" text="Video Gallery" /></h3>
												<i class="fa fa-arrow-circle-right" aria-hidden="true"></i>
											</div>
											<div class="icon-img">
												<i class="fa fa-youtube-play" aria-hidden="true"></i>
											</div>
										</div>
									</a>
								</div>
							</div>
							
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div id="photo-carosel" class="photo-carousel-main">
				                  <div class="owl-carousel owl-theme">
				                     <div class="item"><a href="./assets/img/img1.jpg" target="_blank"><img src="./assets/img/img1.jpg" class="img-responsive" alt="img"></a></div>
				                     <div class="item"><a href="./assets/img/img2.jpg" target="_blank"><img src="./assets/img/img2.jpg" class="img-responsive" alt="img"></a></div>
				                     <div class="item"><a href="./assets/img/img3.jpg" target="_blank"><img src="./assets/img/img3.jpg" class="img-responsive" alt="img"></a></div>
				                     <div class="item"><a href="./assets/img/img4.jpg" target="_blank"><img src="./assets/img/img4.jpg" class="img-responsive" alt="img"></a></div>
				                     <div class="item"><a href="./assets/img/img5.jpeg" target="_blank"><img src="./assets/img/img5.jpeg" class="img-responsive" alt="img"></a></div>
				                     <div class="item"><a href="./assets/img/img6.jpg" target="_blank"><img src="./assets/img/img6.jpg" class="img-responsive" alt="img"></a></div>
				                     <div class="item"><a href="./assets/img/img7.jpg" target="_blank"><img src="./assets/img/img7.jpg" class="img-responsive" alt="img"></a></div>
				                     <div class="item"><a href="./assets/img/img8.jpg" target="_blank"><img src="./assets/img/img8.jpg" class="img-responsive" alt="img"></a></div>
				                  </div>
				               </div>
							</div>
								
						</div>
					</div>
				
				
				</div>
				<%-- <div class="depser">
					<h2 class="text-center " data-animation-effect="fadeInDown"
						data-effect-delay="600">
						<spring:message code="portal.Media.Gallery" text="Media Gallery" />
					</h2>
				</div>

				<div class="photo-video-main">
					<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
						<div class="gallery-box">
							<a href="Content.html?links&page=Photo Gallery"
								class="col-xs-6 col-sm-6 col-md-6 col-lg-6"
								title="<spring:message code="PhotoGallery" text="Photo Gallery"/>"><img
								src="assets/img/photos1.png" class="img-responsive"
								alt="Photo Gallery"></a> <a
								href="Content.html?links&page=Photo Gallery"
								class="view-more col-xs-6 col-sm-6 col-md-6 col-lg-6"
								title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message
									code="portal.link.viewmore" text="View More" />&nbsp;<i
								class="fa fa-arrow-right" aria-hidden="true"></i></a>
								
								<a href="./SectionInformation.html?editForm&rowId=24"
								class="col-xs-6 col-sm-6 col-md-6 col-lg-6"
								title="<spring:message code="PhotoGallery" text="Photo Gallery"/>">
									<img src="assets/img/photos1.png" class="img-responsive" alt="Photo Gallery">
								</a>
								<a href="./SectionInformation.html?editForm&rowId=24" class="view-more"
								title="<spring:message code="portal.link.viewmore" text="View More"/>">
									<div class="photo-thumbnail">
										<div class="thumbnail-text">
											<h3><spring:message code="PhotoGallery" text="Photo Gallery" /></h3>
											<i class="fa fa-arrow-circle-right" aria-hidden="true"></i>
										</div>
										<div class="icon-img">
											<i class="fa fa-picture-o" aria-hidden="true"></i>
										</div>
									</div>
								</a>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
						<div class="gallery-box">
							<a href="Content.html?links&page=Video Gallery"
								class="col-xs-6 col-sm-6 col-md-6 col-lg-6"
								title="<spring:message code="VideoGallery" text="Video Gallery"/>"><img
								src="assets/img/videos1.png" class="img-responsive"
								alt="Video Gallery">
							</a>
							<a href="Content.html?links&page=Video Gallery" class="view-more"
							title="<spring:message code="portal.link.viewmore" text="View More"/>">
								<div class="video-thumbnail">
									<div class="thumbnail-text">
										<h3><spring:message code="VideoGallery" text="Video Gallery" /></h3>
										<i class="fa fa-arrow-circle-right" aria-hidden="true"></i>
									</div>
									<div class="icon-img">
										<i class="fa fa-youtube-play" aria-hidden="true"></i>
									</div>
								</div>
							</a>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div id="photo-carosel" class="photo-carousel-main">
		                  <div class="owl-carousel owl-theme">
		                     <div class="item"><a href="./assets/img/img1.jpg" target="_blank"><img src="./assets/img/img1.jpg" class="img-responsive" alt="img"></a></div>
		                     <div class="item"><a href="./assets/img/img2.jpg" target="_blank"><img src="./assets/img/img2.jpg" class="img-responsive" alt="img"></a></div>
		                     <div class="item"><a href="./assets/img/img3.jpg" target="_blank"><img src="./assets/img/img3.jpg" class="img-responsive" alt="img"></a></div>
		                     <div class="item"><a href="./assets/img/img4.jpg" target="_blank"><img src="./assets/img/img4.jpg" class="img-responsive" alt="img"></a></div>
		                     <div class="item"><a href="./assets/img/img5.jpeg" target="_blank"><img src="./assets/img/img5.jpeg" class="img-responsive" alt="img"></a></div>
		                     <div class="item"><a href="./assets/img/img6.jpg" target="_blank"><img src="./assets/img/img6.jpg" class="img-responsive" alt="img"></a></div>
		                     <div class="item"><a href="./assets/img/img7.jpg" target="_blank"><img src="./assets/img/img7.jpg" class="img-responsive" alt="img"></a></div>
		                     <div class="item"><a href="./assets/img/img8.jpg" target="_blank"><img src="./assets/img/img8.jpg" class="img-responsive" alt="img"></a></div>
		                  </div>
		               </div>
					</div>
						
				</div> --%>
				<!-- </div> -->
			</div>
			</c:if>
			<!-- Media Gallery ends -->
		
		</div>
		<!-- Six sections ends  -->
		
	</div>
	<!-- Column 2 ends -->
	
	<!-- Column 3 starts -->
	<div class="column-3 col-xs-12 col-sm-12 col-md-3 col-lg-3">
		<div class="e-serv-main">
			<div class="panel">
				<div class="panel-heading">
					<spring:message code="theme3.portal.e.services" text="E-Services" />
				</div>
				<div class="panel-body">
					<!-- E-Services Links  -->
					<c:forEach items="${command.getAllhtml('ESERVICES')}" var="eService"> ${eService} </c:forEach>
					<!-- E-Services Links Ends  -->
				</div>
			</div>
		</div>
		
		<div class="gtapp-main">
			<div class="panel">
				<div class="panel-heading">
					<spring:message code="theme3.portal.get.mobile.app" text="Get Mobile App" />
				</div>
				<div class="panel-body">
					<!-- Get Mobile App Section -->
					<c:forEach items="${command.getAllhtml('GETMOBILEAPP')}" var="getMobileApp"> ${getMobileApp} </c:forEach>
					<!-- Get Mobile App Section ends -->
				</div>
			</div>
		</div>	
	</div>
	<!-- Column 3 ends -->
	
	<div class="clear"></div>

	<!-- New Helpline Number -->
	<%-- <c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
		<i id="helpline"></i>
		<div class="helpline-main">
			<div class="helpline-text">
				<spring:message code="theme3.portal.helpline" text="Helpline" />
			</div>
			<div class="helpline-inner-container">
				<div class="helpline help-line">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="helpline-list col-xs-12">
							<span class="list-number  col-xs-12"> <spring:message
									code="theme3.portal.grievances.complaints"
									text="Grievances / Complaints" />
							</span> <span class="list-text col-xs-12"> <spring:message
									code="theme3.portal.grievances.complaints.num"
									text="+91-22-25331590 / +91-22-25331211" />
							</span>
						</div>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="helpline-list col-xs-12">
							<span class="list-number  col-xs-12"> <spring:message
									code="theme3.portal.rdmc.no"
									text="Regional Disaster Management Cell" />
							</span> <span class="list-text col-xs-12"> <spring:message
									code="theme3.portal.rdmc.num" text="1800-222-108" />
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if> --%>
	<!-- New Helpline Number ends -->

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
								<c:forEach items="${command.eipAnnouncement}" var="lookUp"
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
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</c:if>
	
	
	<div class="clear"></div>

	${command.setContactList()}
	
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
	
	
		<!-- modal for care registration on phone start -->
	<%-- <div class="modal press-release-modal-class" id="press-release-modal-div" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<spring:message code="eip.tel" text="Registration On Phone" />
					</h4>
				</div>
				<div class="modal-body press-modal-body">
<!-- 				<input type="text" id="imageField" value="" />
				<img  id="press-release-img" alt="mayank" /> -->
					<p>
					hello 
					</p>
				</div>

			</div>
		</div>
	</div> --%>
	<!-- modal for care registration on phone end -->



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



	<!--  News and Events  -->
	<c:if test="${command.themeMap['SLIDER_IMG'] eq 'I'}">
		<c:if test="${command.themeMap['NEWS'] ne 'I'}">

		</c:if>
	</c:if>
	<!--  News and Events  -->


	<!--Public Notice START  -->

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>

	</div>
	
	

	
	
	
	
	
	
	<div class="clear"></div>

	<!-- Down Arrow Start -->
	<%-- <div class="mbr-arrow hidden-sm-down" aria-hidden="true">
		<a> <i class="fa fa-angle-down" aria-hidden="true"></i> <span><spring:message
					code="theme3.portal.scroll" text="Scroll" /></span>
		</a>
	</div> --%>
	<!-- Down Arrow End -->

	

</div>
<script src="js/eip/citizen/guest-home.js"></script>

<script>
	$(document)
			.ready(
					function() {

						var show_per_page = 5;
						/* var number_of_items = $('#list').children('li').size(); */
						var number_of_items = $('#list').children('li').length;
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
								'display', 'block');

					});

	function go_to_page(page_num) {

		var show_per_page = parseInt($('#show_per_page').val(), 0);
		start_from = page_num * show_per_page;
		end_on = start_from + show_per_page;
		$('#list').children().css('display', 'none').slice(start_from, end_on)
				.css('display', 'block');

		$('ul li a.active').removeClass('active');
		$('.pagination li a.page[longdesc=' + page_num + ']')
				.addClass('active');
		$('#current_page').val(page_num);
	}

	$(function() {
		$('ul.scrolllistbox-new').marquee();
	});
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
					} else {
						$('.org-select').addClass('org-select-minister');
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
