<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" href="assets/libs/chatBot/css/koopid.css">
<script type="application/javascript" src="assets/libs/chatBot/js/koopid-embed.min.js"></script>
<script type="text/javascript">kpde.server = "https://apac-in.app.koopid.ai/";</script>

<span>
	<img src="../images/DSCL-images/kedar-chatbot.jpg" alt="ChatBot" id="kpd_koopidtag"
	data-kpdembedded="true" 
	data-kpdproactive data-kpdproactivetimer="5"
	data-kpdguest="true" data-kpdtag="Welcome_579"
	data-kpdparams="&username=guest&autoconfig=true&
	splashscreen=true&hide=header-phone&hide=header-menu&
	hide=header-tags&hide=header-person"
	title="Chat with Sadaiv-Doon" class="klogo chat-Bot" />
</span>

<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}"></c:if>
<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">
   
   <div class="footer-logos">
      <div class="container">
	      <div id="carosel">
	         <div class="owl-carousel owl-theme">
	            <div class="item"><a href="http://digitalindia.gov.in/" target="_blank"><img src="assets/img/digital-india.png" class="img-responsive" alt="Digital India - Power to Empower"></a></div>
	            <div class="item"><a href="http://www.pmindia.gov.in/en/" target="_blank"><img src="assets/img/pmindia.png" class="img-responsive" alt="PM India"></a></div>
	            <div class="item"><a href="https://www.india.gov.in/" target="_blank"><img src="assets/img/india-gov.png" class="img-responsive" alt="India.gov.in"></a></div>
	            <div class="item"><a href="http://goidirectory.nic.in" target="_blank"><img src="assets/img/goe-directory.png" class="img-responsive" alt="Goe Directory"></a></div>
	            <div class="item"><a href="https://incredibleindia.org/" target="_blank"><img src="assets/img/incredible-india.png" class="img-responsive" alt="Incredible india"></a></div>
	            <div class="item"><a href="http://www.makeinindia.com/home" target="_blank"><img src="assets/img/makeinindia.png" class="img-responsive" alt="Make in India"></a></div>
	            <div class="item"><a href="https://www.mygov.in/" target="_blank"><img src="assets/img/mygov.png" class="img-responsive" alt="My Gov"></a></div>
	            <div class="item"><a href="https://pmnrf.gov.in/" target="_blank"><img src="assets/img/pmnrf.png" class="img-responsive" alt="Prime Minister's National Relief Fund"></a></div>
	            <div class="item"><a href="https://data.gov.in/" target="_blank"><img src="assets/img/data-gov.png" class="img-responsive" alt="open government data"></a></div>
	            <div class="item"><a href="https://services.india.gov.in/" target="_blank"><img src="assets/img/gevernment-portal.jpg" class="img-responsive" alt="service portal"></a></div>
	            <div class="item"><a href="https://www.uk.gov.in/" target="_blank"><img src="images/DSCL-images/Uttarakhand_logo.png" class="img-responsive" alt="Uttarakhand Government Portal"></a></div>
	         </div>
	      </div>
      </div>
   </div>
  <%--  <div class="navigation" style="display:block">
      <div class="container-fluid">
         <div class="rows row-eq-height">
            <div class="col-lg-12" style="width:100%">
               <div class="columsns-multilevel">
                  <ul class="section-nav">
                     <li class="parent">
                        <a href='javascript:void(0)'>
                           <span>
                              <spring:message code="top.home"></spring:message>
                           </span>
                        </a>
                        <ul>
                           <li>
                              <a href='CitizenHome.html'>
                                 <span>
                                    <spring:message code="top.home"></spring:message>
                                 </span>
                              </a>
                           </li>
                           <li>
                              <a href='CitizenAboutUs.html'>
                                 <span>
                                    <spring:message code="top.aboutus"></spring:message>
                                 </span>
                              </a>
                           </li>
                           <li>
                              <a href='CitizenContactUs.html'>
                                 <span>
                                    <spring:message code="eip.citizen.footer.contactUs" text="Contact Us" />
                                 </span>
                              </a>
                           </li>
                           <li>
                              <a href='CitizenFAQ.html?getFAQ'>
                              <span>
                                 <spring:message code="top.faq" text="Faqs"/>
                              </span>
                              </a>
                           </li>
                        </ul>
                     </li>
                     <c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
                     <c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
                     <li class="parent">
                        <a href="javascript:void(0);" class="blink" >
                        <spring:message code="lbl.webArchives" text="Web Archives"/>
                        </a>
                     </li>
                     <li class="blink">
                        <a href="DataArchival.html?archivedData" >
                        <spring:message code="lbl.archiveData" text="Archive Data"/>
                        </a>
                     </li>
                  </ul>
               </div>
            </div>
         </div>
      </div>
   </div> --%>
   
   
</c:if>
<%--  <footer class="clearfix">
   <div class="container">
   <!--Scroll To Top--> 
      <a class="tothetop" href="javascript:void(0);"><strong class="fa fa-angle-up"></strong><span><spring:message code="Top" text="Top"/></span></a> 
    <div class="pull-left">
       <ul>
         <li><img src="assets/img/css.jpg" class="holder_img" alt="Loading please wait" title="W3C CSS standards"></li>
         <li><img src="assets/img/html.jpg" class="holder_img" alt="Loading please wait" title="W3C XHTML standards"></li>
         <li><img src="assets/img/uc.jpg" class="holder_img" alt="Loading please wait" title="GIGW standards"></li>
         <li><img src="assets/img/w3c.png" class="holder_img" alt="Loading please wait" title="W3C WAI-AA WCAG standards"></li>
       </ul>
     </div>
     <div class="footer-links pull-right">
   		 	<a class="fancybox fancybox.iframe" href="./jsp/tiles/EIPguidelines.jsp"><spring:message code="eip.citizen.online.guidelines" text=""/></a> 
         <a onclick="openPopup('CitizenFeedBack.html?publishFeedBack')" href='javascript:void(0);'><span> <spring:message code="Feedback" text="Feedback"/></span></a>
         <a href="ScreenReader.html" title="Screen-Reader"><spring:message code="ScreenReader" text="Screen-Reader"/></a>  
         <a href="Privacypolicy.html" title="Privacy Policy"><spring:message code="PrivacyPolicy" text="Privacy Policy"/></a> 
         <a href="Termsconditions.html" title="Terms & Conditions"><spring:message code="TermsConditions" text="Terms & Conditions"/></a> 
         <a href="Refundcancellation.html" title="Refund / Cancellation"><spring:message code="RefundCancellation" text="Refund / Cancellation"/></a> 
        
          <a href="Copyright.html" title="Copyright"><spring:message code="eip.Copyright" text="Copyright"/></a>
   	        <c:set var="lastUpdated" value="${userSession.lastUpdated}"></c:set>
   	        <fmt:formatDate type = "date" value="${lastUpdated}"  dateStyle="SHORT" pattern="dd/MM/yyyy" var="formatedDt"/>
   	       <a href="javascript:void(0)" title="Last Updated on ${formatedDt}"><spring:message code="eip.update.dt" text="Last Updated on"/>&nbsp${formatedDt}</a> 
     </div>
    </div>
   </footer> --%>

<div class="footer-section">
	<footer class="clearfix">
		<div class="container">
			<div class="row">
				<!--Scroll To Top--> 
				<a class="tothetop" href="javascript:void(0);">
				   <i class="fa fa-angle-up" aria-hidden="true"></i>
				   <%-- <span class="pull-left">
				      <spring:message code="Top" text="Top"/>
				   </span> --%>
				</a>
				<div class="footer-section-one">
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
						<div class="wbst-logo-name">
							<a href="CitizenHome.html">
							      <img src="../images/DSCL-images/dscl-logo-white-text.png" class="img-responsive"
							      alt="dscl-logo-white-text"
							      <c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
							      <c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
							      ">
			                 </a>
			                 <%-- <c:if test="${userSession.languageId eq 1}">
		                        <span class="website-name">
		                        	<h1>${userSession.organisation.ONlsOrgname}</h1>
									<h3>${userSession.organisation.ONlsOrgnameMar}</h3>
								</span>
		                     </c:if>
		                     <c:if test="${userSession.languageId eq 2}">
		                        <span class="website-name">
									<h1>${userSession.organisation.ONlsOrgnameMar}</h1>
									<h3>${userSession.organisation.ONlsOrgname}</h3>
								</span>
		                     </c:if> --%>
		                 </div>
		                 <div class="fs-contact-us">
							<ul>
								<li>
									<span class="fscu-content fscu-addr"><spring:message code="theme5.portal.address" text="Saatvik Tower, 777, Kaulagarh Road, Rajendra Nagar, Dehradun, 248001"/></span>
								</li>
								<li>
									<span class="fscu-content fscu-tel">
										<a href="tel: <spring:message code="theme5.portal.phone.num" text="0135- 2750984"/>">
											<spring:message code="theme5.portal.phone.num" text="0135- 2750984"/>
										</a>
										<a href="tel: <spring:message code="theme5.portal.citizen.helpline.num" text="18001802525"/>">
											<spring:message code="theme5.portal.citizen.helpline.num" text="18001802525"/>
										</a>
									</span>
								</li>
								<li>
									<span class="fscu-content fscu-mail">
										<a href="mailto: <spring:message code="theme5.portal.email.address" text="smartcityddn@gmail.com"/>">
											<spring:message code="theme5.portal.email.address" text="smartcityddn@gmail.com"/>
										</a>
									</span>
								</li>
							</ul>
						</div>
						<%--  Start of code by ABM2144 for Social Media Section on 21-05-2019 --%>
			            <c:if test="${command.userSession.socialMediaMap.size() gt 0}">
		                  <ul class="footer-social-media">
		                     <c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
		                        <li><a href="${media.value}" target="new_${ count.count}">
		                           <i class="fa fa-${media.key}"> <span class="hidden">${media.key}</span></i>
		                           </a>
		                        </li>
		                     </c:forEach>
		                  </ul>
			            </c:if>
			            <%--  End of code by ABM2144 for Social Media Section on 21-05-2019 --%>
					</div>
					
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
						<div class="wbst-info-links">
							<h2 class="section-heading"><spring:message code="theme5.portal.useful.links" text="Useful Links"/></h2>
							<ul>
								<li>
									<a href="ScreenReader.html" title="Screen-Reader" >
					                  <spring:message code="ScreenReader" text="Screen-Reader"/>
					               </a>
								</li>
								<li>
									<a href="Websitepolicy.html" title="Website Policies" >
					                  <spring:message code="website.policies" text="Website Policies"/>
					               </a>
								</li>
								<li>
									<a href="sitemap.html" aria-label='<spring:message code="sitemap" text="Sitemap"/>' title="<spring:message code="sitemap" text="Sitemap"/>">
										<spring:message code="sitemap" text="Sitemap"/>
									</a>
								</li>
								<li>
									<a href="help.html" title="Help" >
					                  <spring:message code="help.footer" text="Help"/>
					               </a>
								</li>
								<li>
									<a href="webInfo.html" title="Web Information Manager" >
					                  <spring:message code="web.information" text="Web Information Manager"/>
					               </a>
								</li>
								<li>
									<a href="CitizenContactUs.html?id=2" title="Technical Support" >
					                  <spring:message code="theme5.portal.tech.support" text="Technical Support"/>
					               </a>
								</li>
							</ul>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
						<!-- VISITOR COUNT starts -->
						<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
							<c:if test="${ not empty totalRegisUser}">
							     <div class="counter-section">
							     	<h2 class="section-heading"><spring:message code="theme5.portal." text="User Analytics"/></h2>
									<ul>
										<li>
											<span class="counter-text"><spring:message code="TotalVisitors" text="Total Visitors" /></span>
											<span class="animate-number counter-numeral" data-value="<%=request.getSession().getAttribute("countuser")%>" id="countuser2" data-duration="3000">0</span>
										</li>
										<li>
											<c:set var="orgId" value="${userSession.organisation.orgid}" />
											<span class="counter-text"><spring:message code="TodayVisitors" text="Today Visitors" /></span>
											<span class="animate-number counter-numeral" data-value="<%=request.getSession().getAttribute("loggedInUser")%>" data-duration="3000" id="loggedInUser">0</span>
										</li>
										<li>
											<span class="counter-text"><spring:message code="theme5.portal.active.sessions" text="Active Sessions" /></span>
											<span class="animate-number counter-numeral" data-value="<%=request.getSession().getAttribute("activeuser")%>" data-duration="3000" id="activeuser">0</span>
										</li>
										<li>
											<span class="counter-text"><spring:message code="RegisterUsers" text="Register Users" /></span>
											<span class="animate-number counter-numeral" data-value="<%=request.getSession().getAttribute("totalRegisUser")%>" data-duration="3000" id="totalRegisUser">0</span>
										</li>
									</ul>
								
									<img
									                       src="<spring:message code="hitcounter.URL" arguments="${orgId}"/>"                        
									Alt="
									<spring:message code="left.TotalVisitors"/>
									" height="20"
									width="129" class="hide" />
								
									<%-- <div class="col-sm-3 col-md-3 col-lg-3 col-xs-6"
									   style="border-right: 1px #fff dotted;">
									   <ul>
									      <li>
									         <i class="fa fa-pie-chart text-primary"></i> 
									         <spring:message
									            code="TotalVisitors" text="Total Visitors" />
									         <span
									            class="animate-number badge pull-right"
									            data-value="<%=request.getSession().getAttribute("countuser")%>"
									            id="countuser2" data-duration="3000">0</span>
									      </li>
									   </ul>
									</div>
									<div class="col-sm-3 col-md-3 col-lg-3 col-xs-6"
									   style="border-right: 1px #fff dotted;">
									   <ul>
									      <li>
									         <c:set var="orgId"
									            value="${userSession.organisation.orgid}" />
									         <img
									            src="<spring:message code="hitcounter.URL" arguments="${orgId}"/>"                        
									         Alt="
									         <spring:message code="left.TotalVisitors"/>
									         " height="20"
									         width="129" class="hide" /> <i
									            class="fa fa-calendar-check-o text-danger"></i> 
									         <spring:message code="TodayVisitors" text="Today Visitors" />
									         <span
									            class="animate-number badge pull-right"
									            data-value="<%=request.getSession().getAttribute("loggedInUser")%>"
									            data-duration="3000" id="loggedInUser">0</span>
									      </li>
									   </ul>
									</div>
									<div class="col-sm-3 col-md-3 col-lg-3 col-xs-6"
									   style="border-right: 1px #fff dotted;">
									   <ul>
									      <li>
									         <i class="fa fa-user text-success"></i> 
									         <spring:message
									            code="ActiveUsers" text="Active Users" />
									         <span
									            class="animate-number badge pull-right"
									            data-value="<%=request.getSession().getAttribute("activeuser")%>"
									            data-duration="3000" id="activeuser">0</span>
									      </li>
									   </ul>
									</div>
									<div class="col-sm-3 col-md-3 col-lg-3 col-xs-6">
									   <ul>
									      <li class="facebook-color">
									         <i class="fa fa-users text-info"></i>
									         <spring:message code="RegisterUsers" text="Register Users" />
									         <span
									            class="animate-number badge pull-right"
									            data-value="<%=request.getSession().getAttribute("totalRegisUser")%>"
									            data-duration="3000" id="totalRegisUser">0</span>
									      </li>
									   </ul>
									</div> --%>
							</div>
							</c:if>
						</c:if>
						<!-- VISITOR COUNT ends -->
					</div>
				</div>
	      </div>
	   </div>
		<div class="footer-bottom">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="gov-cert-logos">
							<ul>
				               <li><img src="assets/img/css.jpg" class="holder_img" alt="W3C CSS standards" ></li>
				               <li><img src="assets/img/html.jpg" class="holder_img" alt="W3C XHTML standards"></li>
				               <li><img src="assets/img/uc.jpg" class="holder_img" alt="GIGW standards" ></li>
				               <li><img src="assets/img/w3c.png" class="holder_img" alt="W3C WAI-AA WCAG standards"></li>
				            </ul>
						</div>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="copy-right-section">
							<span>
								<a href="#" title="Content managed by Suda" >
				                  <spring:message code="suda.content.managed" text="Content managed by Suda"/>
				               </a>
							</span>
							<span>
								<a href="#" title="Website designed, developed by ABM" >
				                  <spring:message code="website.design" text="Website designed, developed by ABM"/>
				               </a>
							</span>
							<span>
								<a href="#" title="Copyright &copy; ABM 2018" target="_blank">
				                  <spring:message code="Copyright" text="Copyright &copy; ABM 2018"/>
				               </a>
							</span>
							<c:set var="lastUpdated" value="${userSession.lastUpdated}"></c:set>
				            <%-- <fmt:formatDate type = "date" value="${lastUpdated}"  dateStyle="SHORT" pattern="dd/MM/yyyy" var="formatedDt"/> --%>
				            <c:choose>
								<c:when test="${fn:contains(lastUpdated, ' 12:') || fn:contains(lastUpdated, ' 00:')}">	
									 <fmt:formatDate type = "both" value="${lastUpdated}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy hh:mm a"  var="formatedDt"/>
								</c:when>	
								<c:otherwise>
									 <fmt:formatDate type = "both" value="${lastUpdated}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy K:mm a"  var="formatedDt"/>
								</c:otherwise>
							</c:choose>
							<span>
								<a href="javascript:void(0)" title="
					               <spring:message code="last.update" text="Last Updated on ${formatedDt}"/>
					               " >
					               <spring:message code="last.update" text="Last Updated on ${formatedDt}"/>
					               &nbsp; ${formatedDt}</a>
							</span>
							<span>
								<a href="javascript:void(0)" title="<spring:message code="portal.version" text="Mainet - Version 2.0"/>">
									<spring:message code="portal.version" text="Mainet - Version 2.0"/>
								</a>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>

	</footer>
	
</div>
<script>
   $(function(){
	$(".navigation a[href='javascript:void(0);']").removeAttr('href');	
   	$(".section-nav li a").each(function(){
   		if($(this).html()=="Dashboard")	{
   			$(this).css('display','none');
   		}
   	});
   	$('.columsns-multilevel').slimScroll({
   	    color: '#313131',
   	    size: '4px',
   	    height: '285px',
   	    alwaysVisible: true
   	});
   });
</script>