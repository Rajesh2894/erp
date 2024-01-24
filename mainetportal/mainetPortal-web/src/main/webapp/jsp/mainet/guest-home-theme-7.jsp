<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />

<!-- <link href="https://fonts.googleapis.com/css?family=Alegreya+Sans" rel="stylesheet"> -->
<link rel="stylesheet" href="assets/libs/bootstrap/css/bootstrap.min.css">
<link href="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.min.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js" ></script>
<script src="assets/js/jquery.marquee.min.js"></script>
<!-- <link href="assets/css/style-blue-theme-7.css" rel="stylesheet" type="text/css"/> -->

<script>
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
	
	/* ----- JS for Photo Gallery Carousel starts ----- */
	/* $(document).ready(function(){
		$('#photo-carousel .owl-carousel').owlCarousel({
			autoPlay: 3000, //Set AutoPlay to 3 seconds
	        pagination: false,
	        items : 1,
	        margin:5,
	        navigation : true,
	        navigationText: [
        	   "<i class='fa fa-chevron-left'></i>",
        	   "<i class='fa fa-chevron-right'></i>"
        	],
	        itemsDesktop : [1199,1],
	        itemsDesktopSmall : [980,1],
	        itemsTablet: [768,2],
	        itemsTabletSmall: false,
	        itemsMobile : [479,1]
		});
	}); */
	/* ----- JS for Photo Gallery Carousel ends ----- */
	
</script>
<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">
	<script>
		$(function() {
			$('#myCarousel').css('margin-top', '0px');
		});
	</script>
</c:if>

${command.setThemeMap()}
${command.getAboutUs()}



<div id="main">
<!-- Slider Start -->
	<c:if test="${command.themeMap['SLIDER_IMG'] ne 'I'}">
		<div class="container-fluid slider-main" id="container-fluid1" style="padding-left:0px;padding-right:0px;">			
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="sld">		
			<div class="row">
				<div id="myCarousel" class="carousel slide carousel-fade"
					data-ride="carousel" style="background: #000">
					<c:set var="maxfilecount"
						value="${userSession.getSlidingImgLookUps().size()}" />						
					<%--  Indicators --%>
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

					<%--  Wrapper for slides --%>
					<div class="carousel-inner">

						<c:if test="${userSession.getSlidingImgLookUps().size() gt 0}">
							
							<c:forEach items="${userSession.slidingImgLookUps}" var="slide"
								varStatus="status">

								<c:set var="data" value="${fn:split(slide,'*')}" />
								<c:set var="image" value="${data[0]}" />
								<c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
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
			<div class="clearfix"></div>
		</div>
	</c:if>
<%--  Slider End --%>
<div class="clear"></div>
<%--  ======= Civic Massege =======--%>
<c:if test="${command.themeMap['CIVIC-MESSAGE'] ne 'I'}">
      <section id="civic-msg" class="bg-blue pt-1 pb-0">
        <div class="container">
          <!-- <div class="col-sm-6 col-lg-2 col-md-2 col-xs-6 bg-red-color">Civic Massege<span><img src="assets/img/civic-icon.png"></span> </div> -->
            <div class="row civic-msg-cont">
              <div class="col-xl-2 col-lg-3 col-md-4 col-sm-4 col-12 civic-msg-title"><spring:message code="theme7.portal.civic.msg" text="Quick Services" /></div>
              <div class="col-xl-10 col-lg-9 col-md-8 col-sm-8 col-12 civic-msg-marquee"><marquee><c:forEach items="${command.getAllhtml('CIVIC-MESSAGE')}" var="civicMsg"> ${civicMsg} </c:forEach></marquee></div>
            </div>
        </div>
      </section>
   </c:if>
      <%-- ======= End Civic Massege ======= --%> 
     
      
<c:if test="${not empty command.themeMap['CITIZEN_SERVICES'] && command.themeMap['CITIZEN_SERVICES'] ne 'I'}">
		<c:set var="citizenService"
			value="${command.getAllDepartmentAndServices()}" />
		<c:if test="${citizenService.size()>0}">
<div id="counts" class="counts bg-blue pt-1 pb-2">
      <div class="container" data-aos="fade-up">
	<div class="row">
		<div class="col-sm-12">
			<div class="exservices-heading"><spring:message code="theme7.muncipal.services" text="Municipal Services & Taxes" /></div>
        <div id="exservices" class="owl-carousel services-carousel">
        <c:forEach items="${command.getAllDepartmentAndServices()}"
								var="dept" varStatus="index">
						
					 		<div class="services-wrap active">
					 			<div class="first-show">
					 				<div class="service-icon"><i class="fa fa-list_${dept.departmentCode} fa-icon-image fa-2x"></i></div>
					            	<h3>${dept.departmentName}</h3>
					 			</div>
					            <div class="sec-show">
					            	<ul>
												<c:forEach items="${dept.childDTO}" var="serv"
													varStatus="index">
													<li><a href="javascript:void(0);"
														onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
															<i class="fa fa-chevron-right"></i> ${serv.serviceName}
													</a></li>

												</c:forEach>
												
											</ul>
					            </div>
					          </div>         
			</c:forEach>
        	
							<div class="services-wrap active">
					 			<div class="first-show">
					 				<div class="service-icon"><i class="fa fa-wrench fa-icon-image fa-2x"></i></div>
					            	<h3><p><spring:message code="theme7.portal.quick.services" text="Quick Services" /></p></h3>
					 			</div>
					 			<div class="sec-show">
					            	<ul>
					            		<li><a href="#" onclick="quickPayment('billpay');" class="extlink internal"><i class="fa fa-chevron-right"></i><spring:message code="theme7.portal.bill.payment" text="Bill Payment" /></a></li>
					            		<li><a href="#" onclick="quickPayment('appstatus');" class="extlink internal"><i class="fa fa-chevron-right"></i> <spring:message code="theme7.portal.application.status" text="Application Status" /></a></li>
					            		<li><a href="#" onclick="quickPayment('complaintstatus');" class="extlink internal"><i class="fa fa-chevron-right"></i> <spring:message code="theme7.portal.complaint.status" text="Complaint Status" /></a></li>
					            	</ul>
					            </div>
					 		</div>
        </div>
        </div>
	</div>
      </div>
    </div>
</c:if>
</c:if>
    <%-- ======= End of E-services =======  --%>  

 <%-- ======= Commetiee Member Section ======= --%> 
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
    <section id="members" class="services">
      <div class="container" data-aos="fade-up">

        <!-- <div class="section-title">
          <h2>Services</h2>
          <p>Check our Services</p>
        </div> -->

        <div class="row element-animate">
        <c:if test="${not empty sessionScope.mayorProfile}">
          <div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 d-flex align-items-stretch" data-aos="zoom-in" data-aos-delay="100">
            <div class="members-wrap">
              <div class="member-photo"><img src="${sessionScope.mayorprofileImage}" alt="${sessionScope.mayorName}" title="${sessionScope.mayorName}" class="img-responsive" ></div>
              <div class="member-details">
                <h3>${sessionScope.mayorName}</h3>
                <p>${sessionScope.mayorProfile}</p>
                <p><spring:message code="theme7.portal.utter.pradesh" text="Uttar Pradesh" /></p>
                <div class="bottom-border"></div>
               <a href="SectionInformation.html?editForm&rowId=167&page="><div class="view-msg-btn"><spring:message code="theme7.portal.view.message" text="View Message" /></div></a>
              </div>
            </div>
          </div>
		</c:if>
		<c:if test="${not empty sessionScope.deputyMayorProfile}">
          <div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 d-flex align-items-stretch" data-aos="zoom-in" data-aos-delay="200">
            <div class="members-wrap">
                 <div class="members-wrap">
                    <div class="member-photo"><img src="${sessionScope.deputyMayorProfileImage}" alt="${sessionScope.deputyMayorName}" title="${sessionScope.deputyMayorName}" class="img-responsive"></div>
                    <div class="member-details">
                      <h3>${sessionScope.deputyMayorName}</h3>
                      <p>${sessionScope.deputyMayorProfile}</p>
                      <p><spring:message code="theme7.portal.utter.pradesh" text="Uttar Pradesh" /></p>
                      <div class="bottom-border"></div>
                     <a href="SectionInformation.html?editForm&rowId=168&page="><div class="view-msg-btn"><spring:message code="theme7.portal.view.message" text="View Message" /></div></a>
                    </div>
                  </div>
            </div>
          </div>
		</c:if>
		<c:if test="${not empty sessionScope.commissionerProfile}">
          <div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 d-flex align-items-stretch" data-aos="zoom-in" data-aos-delay="300">
            <div class="members-wrap">
                 <div class="members-wrap">
                    <div class="member-photo"><img src="${sessionScope.commissionerProfileImage}"
													alt="${sessionScope.commissionerName}"
													title="${sessionScope.commissionerName}"
													class="img-responsive"></div>
                    <div class="member-details">
                       <h3>${sessionScope.commissionerName}</h3>
                      <p>${sessionScope.commissionerProfile}</p>
                      <p><spring:message code="theme7.portal.nagarnigam.prayagraj" text="Prayagraj Nagar Nigam" /></p>
                      <div class="bottom-border"></div>
                      <a href="SectionInformation.html?editForm&rowId=169&page="><div class="view-msg-btn"><spring:message code="theme7.portal.view.message" text="View Message" /></div></a>
                    </div>
                  </div>
            </div>
          </div>
         </c:if>
				<c:if test="${not empty sessionScope.deputycommissionerProfile}">
          <div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 d-flex align-items-stretch" data-aos="zoom-in" data-aos-delay="300">
            <div class="members-wrap">
                 <div class="members-wrap">
                    <div class="member-photo"><img src="${sessionScope.deputycommissionerProfileImage}"
										alt="${sessionScope.deputyCommissionerName}"
										title="${sessionScope.deputyCommissionerName}"
										class="img-responsive"></div>
                    <div class="member-details">
                      <h3>${sessionScope.deputyCommissionerName}</h3>
                      <p>${sessionScope.deputycommissionerProfile}</p>
                      <p><spring:message code="theme7.portal.nagarnigam.prayagraj" text="Prayagraj Nagar Nigam" /></p>
                      <div class="bottom-border"></div>
                      <a href="SectionInformation.html?editForm&rowId=170&page="><div class="view-msg-btn"><spring:message code="theme7.portal.view.message" text="View Message" /></div></a>
                    </div>
                  </div>
            </div>
          </div>
          </c:if> 
        </div>
        
      </div>
    </section>
    <%--  ======= End of Commetiee Member======= --%>
	<%-- ======= About Section ======= --%>

    <section id="about" class="about bg-grey element-animate">
      <div class="container" data-aos="fade-up">

        <!-- <div class="section-title">
          <h2>About</h2>
          <p>About Us</p>
        </div> -->

        <div class="row">
        
          <div class="col-xs-12 col-sm-7 col-md-7 col-lg-7 about-left">
          <div class="content-heading">
          	<spring:message code="theme4.portal.welcome.message" text="Welcome in Prayagraj Nagar Nigam" />
          </div>
           <div class="content-text">
              <p>${command.aboutUsDescFirstPara}</p>

              	<p>${command.aboutUsDescSecondPara}</p>
            </div>
             <div class="content-imgslider">
                <c:forEach items="${command.getAllhtml('About Prayagraj Slider')}" var="aboutPrayagrajSlider"> ${aboutPrayagrajSlider} </c:forEach>
              </div>
            <%-- <div class="cta"><a class="cta-btn" href="CitizenAboutUs.html" title="View More"><spring:message code="portal.link.viewmore" text="VIEW MORE" /></a></div> --%>
          <%-- <c:if test="${not empty command.themeMap['SCHEMES'] && command.themeMap['SCHEMES'] ne 'I'}">
			<c:if test="${not empty command.aboutUsDescFirstPara }">
            <div class="content-heading"><span></span><h1>
						<spring:message code="theme4.portal.welcome.message" text="Welcome in Prayagraj Nagar Nigam" />
					</h1></div>
            <div class="content-text">
              ${command.aboutUsDescFirstPara}<br />

              ${command.aboutUsDescSecondPara}
            </div>
            <div class="cta"><a class="cta-btn" href="#">Read More...</a></div>
            </c:if>
		</c:if> --%>
             
          </div>
          
          <div class="col-xs-12 col-sm-5 col-md-4 col-lg-4 pt-4 pt-lg-0 col-md-offset-1" id="tender-list">
            <div class="content-heading"><spring:message code="theme4.portal.announcements" text="Tender Notice" /></div>
            <c:if test="${command.themeMap['NEWS'] ne 'I'}">
             <div class="scrolllistbox">
              <div class="tender-list element-animate">
                <!-- <marquee direction="up"> -->
                <c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">			  
									  <p>
										  <c:set var="links" value="${fn:split(lookUp.attach,',')}" />
										  <c:forEach items="${links}" var="download" varStatus="count">
										  <c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
										  <c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
										  <c:set var="link"	value="${stringUtility.getStringAfterChars(download)}" />
										  <c:choose>
										  <c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
										  <c:choose>
										  <c:when test="${isDMS}">
										  <a href="javascript:void(0);" 
										     onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
										  </c:when>
										  <c:otherwise>
											  <c:choose>
											      <c:when test="${not empty download}">			  
													  <apptags:filedownload filename="EIP"
														filePath="${download}"									
														actionUrl="CitizenHome.html?Download"
														eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
														<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
												  </c:when>
												  <c:otherwise>
												  	   ${lookUp.announceDescEng}
												  	   <c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
												  </c:otherwise>
											  </c:choose>
											  <c:if test="${not empty lookUp.link}">
												  <a href="${lookUp.link}" target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a>
											  </c:if>
										  </c:otherwise>
										  </c:choose>
										  <br/><span class="date"><i class="fa fa-calendar" aria-hidden="true"></i>
										  <fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
										  </c:when>
										  <c:otherwise>
										  <c:choose>
									      <c:when test="${isDMS}">			  
										  <a href="javascript:void(0);" 
										     onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
										  </c:when>
										  <c:otherwise>
										  	  <c:choose>
											      <c:when test="${not empty download}">			  
													  <apptags:filedownload filename="EIP"
														filePath="${download}"
														actionUrl="CitizenHome.html?Download"
														eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
														<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
												  </c:when>
												  <c:otherwise>
													 ${lookUp.announceDescReg}
												  	   <c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
												  </c:otherwise>
											  </c:choose>
											  <c:if test="${not empty lookUp.link}">
												  <a href="${lookUp.link}" target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a>
											  </c:if>
										  </c:otherwise>
										  </c:choose>
										  <br/><span class="date"><i class="fa fa-calendar" aria-hidden="true"></i>
										  <fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
										  </c:otherwise>
										  </c:choose>
										  </c:forEach>
									  </p> 
									 </c:forEach>
              <!-- </marquee> -->
              </div>
              </div>
              <div class="cta"><a class="cta-btn" href="CitizenHome.html?newsAndEvent" title="View More"><spring:message code="portal.link.viewmore" text="VIEW MORE" /></a></div>
            <!-- <a href="#" class="btn-learn-more">Learn More</a> -->
            </c:if>
            
          </div>
          <div class="col-xs-12 col-sm-5 col-md-4 col-lg-4 pt-4 pt-lg-0 col-md-offset-1" id="schemes-list">
          		<div class="content-heading"><spring:message code="theme4.portal.schemes" text="Schemes" /></div>
          			<c:forEach items="${command.getAllhtml('Scheme')}" var="scheme"> ${scheme} </c:forEach>
          		
          </div>
        </div>
		
	
		<div style="clear="both;"></div>
      </div>
    </section><!-- End About Section -->
	<%-- ======= Gallery Section ======= --%>
      <section id="gallery" class="why-us element-animate">
      		<div class="container" data-aos="fade-up">
      			<div class="row">
      				<div class="col-sm-12 col-md-4 col-12 col-lg-4">
      					<div class="card" id="important-links">
      						<div class="content-heading"><spring:message code="theme7.portal.total.important.links" text="Important Links" /></div>
      						<div class="scrolllistbox">
      						<div class="emergency-list">
				                <ul>
				                <li>
				                <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
				                  	<i class="fa fa-angle-right"></i> <a href="" onclick="changeULBbylink(1)" class="external" target="_blank"><spring:message code="theme7.portal.pscl" text="Prayagraj Smart City" /></a>
				                  	</div>
				                  </li>
				                  	<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3"></div>
				                  <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
											<fmt:formatDate value="${lookUp.issueDate}" pattern="yyyy-MM-dd HH:mm:ss" var="iDate"/> 
											<fmt:formatDate value="${lookUp.validityDate}" pattern="yyyy-MM-dd HH:mm:ss" var="vDate"/> 
											 <c:if test="${lookUp.isUsefullLink ne 'Y'}">
												<li>
													<%-- <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
													<span class="serialno-new">${serial_count+1}</span>
													<c:set var="serial_count" scope="page"
														value="${serial_count+1}" />
													</div> --%>
													<c:set var="serial_count" scope="page" value="${serial_count+1}" />
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
																		<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
																			<i class="fa fa-angle-right"></i>
																			<c:if test ="${not empty lookUp.link && lookUp.linkType ne 'R'}"> 
																				<a href="${lookUp.link}" target="_blank" class="">${lookUp.noticeSubEn}</a>
																			 </c:if> 
																			 
																			<c:if test="${lookUp.isHighlighted eq 'Y'}">
																				<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
																			</c:if>
																		</div>
																		<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																				<c:set var="lnk1" value="./cache/${downloadL}" /> 
																				<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
				                                          								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
				                                                                
				                                                                   <c:if test="${lookUp.linkType eq 'R' }">
											                                             <apptags:filedownload filename="${link}" 
											                                                filePath="${path}" showIcon="true" docImage="true"
											                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																				   </c:if>
																			</c:forEach>
																			
																		</div>
																	</c:when>
																	<c:otherwise>
																		
																		<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																				<c:set var="lnk1" value="./cache/${downloadL}" /> 
																				<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
				                                          								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
				                                                                   <c:if test="${lookUp.linkType eq 'R' }">
											                                             <apptags:filedownload filename="${link}"
											                                                filePath="${path}" showIcon="true" docImage="true" 
											                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																				   </c:if>
																			</c:forEach>
																			<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
																				<i class="fa fa-angle-right"></i><a href="${exlink}" target="_blank" class="">${lookUp.noticeSubReg}</a>	
																			</c:if>
																			<c:if test="${lookUp.isHighlighted eq 'Y'}">
																			<span><img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new"></span>
																			</c:if>
																		</div>
																		<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
																			
																		</div>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
														<input type="hidden" name="downloadLink"
															value="${lookUp.profileImgPath}">
				
												</li>
											</c:if>
										</c:forEach>
				                </ul>
				              </div>	
				             </div>					
      					</div>
      				</div>
      				
      				<div class="col-sm-12 col-md-8 col-12 col-lg-8">
      					<div class="card" id="image-gallery">
      						<div class="image-galley-imgslider">
      								<h2><spring:message code="theme7.portal.photo.gallery" text="Photo Gallery" /></h2>
                					<c:forEach items="${command.getAllhtml('Front Page gallery')}" var="frontPageGallery"> ${frontPageGallery} </c:forEach>
				              </div>	
      					</div>
      				</div>
      			</div>
      		</div>
      </section>
    <%--   End Gallery Section --%>
     <%-- ======= News and Update Section ======= --%>
    <section id="gallery" class="about bg-grey element-animate">
      <div class="container" data-aos="fade-up">
      		<div class="row">
      			<div class="col-xs-12 col-md-4 col-lg-4">
      				<div class="card" id="important-links">
      					<div class="content-heading"><spring:message code="theme7.portal.balance.sheet" text="Audited Balance Sheet" /></div>
      					<div class="scrolllistbox">
      						<div class="map-list">
				               <c:forEach items="${command.getAllhtml('Balance Sheet')}" var="balanceSheet"> ${balanceSheet} </c:forEach>
				              </div>	
				             </div>	
      				</div>
      			</div>
      			<div class="col-xs-12 col-md-4 col-lg-4">
      				<div class="card" id="important-links">
						<div class="content-heading"><spring:message code="theme7.portal.budget" text="Budget" /></div>
						<div class="scrolllistbox">
      						<div class="map-list">
				               <c:forEach items="${command.getAllhtml('Budget')}" var="budget"> ${budget} </c:forEach>
				              </div>	
				             </div>	
					</div>
      			</div>
      			<div class="col-xs-12 col-md-4 col-lg-4">
      				<div class="card" id="important-links">
      					<div class="content-heading"><spring:message code="theme7.portal.audit.report" text="Inter Audit Report" /></div>
      					<div class="scrolllistbox">
      						<div class="map-list">
				               <c:forEach items="${command.getAllhtml('Audit Report')}" var="auditReport"> ${auditReport} </c:forEach>
				              </div>	
				             </div>	
      				</div>
      			</div>
      		</div>
	  </div>
    </section>
    
    <section id="gallery" class="why-us element-animate">
      		<div class="container" data-aos="fade-up">
      			<div class="row">
      			<div class="col-sm-12 col-md-8 col-12 col-lg-8">
      					<div class="card" id="image-gallery">
      						<div class="image-galley-imgslider">
      								<h2><spring:message code="theme7.portal.video.gallery" text="Video Gallery" /></h2>
                					<div class="video" style="height:400px;">
                    					<iframe width="100%" height="400" src="https://www.youtube.com/embed/QgA_wLMGoQs" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe> 
                                     </div>
				              </div>	
      					</div>
      				</div>
      				<div class="col-sm-12 col-md-4 col-12 col-lg-4">
      					<div class="card" id="important-links">
      						<div class="content-heading"><spring:message code="theme7.portal.map" text="MAP" /></div>
      						<div class="scrolllistbox">
      						<div class="map-list">
				               <c:forEach items="${command.getAllhtml('Map')}" var="map"> ${map} </c:forEach>
				              </div>	
				             </div>					
      					</div>
      				</div>
      				
      				
      			</div>
      		</div>
      </section>
 </div>
<script src="js/eip/citizen/guest-home.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
      $('#p-image-container').desoSlide({
        thumbs: $('#p-slider li > a'),
        auto: {
            start: true
        },
        controls: {
            show:  true,
            keys:  true
        },
        first: 0,
        interval: 6000
    });
    })
  </script>
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
$(document).ready(function() {
    $("div.bhoechie-tab-menu>div.list-group>a").click(function(e) {
        e.preventDefault();
        $(this).siblings('a.active').removeClass("active");
        $(this).addClass("active");
        var index = $(this).index();
        $("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active");
        $("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
    });
    
	  $(".header-inner").addClass('hide');
	
});

	
</script>
<c:if
	test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
	<script>
/* $(document).ready(function() {
		var space=$('.minister-details').offset().top;
		if(space>=200){
		 $('.org-select').addClass('org-select-slider').css('float','right');
			/* $('.minister-div').removeClass('col-lg-9').addClass('col-lg-12');
			$('.minister-div').removeClass('col-md-8').addClass('col-md-12'); */
			
		/* }else{
			$('.org-select').addClass('org-select-minister');
			/* $('.minister-div').removeClass('col-lg-12').addClass('col-lg-9');
			$('.minister-div').removeClass('col-md-12').addClass('col-md-8'); */
	//	} 
//}); 

</script>
</c:if>

<c:if test="${(not empty command.themeMap['SLIDER_IMG']) && (command.themeMap['SLIDER_IMG'] ne 'A')}">  
 <script>
$(function(){
	$('.about-bg').css('margin-top','65px');	
});
</script>
</c:if>
 
		