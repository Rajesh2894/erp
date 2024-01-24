<%@ page pageEncoding="UTF-8"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="js/eip/citizen/top-header.js"></script>
<script  src="js/eip/admin/adminLoginForm.js"></script>
<script  src="js/eip/citizen/citizenLoginForm.js"></script>
<script  src="js/eip/agency/agencyLoginForm.js"></script>
<script  src="js/eip/agency/agencyRegistrationForm.js"></script>
<script  src="js/eip/agency/agencyForgotPasswordProcess.js"></script>
<script  src="js/eip/agency/agencyResetPasswordProcess.js"></script>
<script  src="js/eip/citizen/citizenRegistrationForm.js"></script>
<script  src="js/eip/citizen/citizenForgotPasswordProcess.js"></script>
<script  src="js/eip/admin/adminForgotPasswordProcess.js"></script>
<script  src="js/eip/citizen/citizenResetPasswordProcess.js"></script>
<script  src="js/eip/citizen/adminResetPasswordProcess.js"></script>
<script  src="js/eip/admin/adminRegistrationForm.js"></script>
<script  src="js/mainet/element.js"></script>
<script> 
$(document).ready(function(){
	
	$('*[title]').removeAttr('title');	
   	$("#selectedOrg1").chosen();
   	$("#mobile-button").on("click", function(e){
		if($("#navbars").css("display")=="block"){
			$("#navbars").slideToggle();
			$("#mobile.nav li ul").slideUp(350);
			$("#mobile.nav .parent>a").removeClass("open");
		}else{
			$("#navbars").slideToggle();
			$("#mobile").slideDown(350);
		}
	})
	
	$("#mobile.nav .parent>a").on("click", function(e){
		if($(this).parent().has("ul")) {
		 e.preventDefault();
		 if($(this).hasClass("open")) {
			 $(this).removeClass("open");
		     $(this).next("ul").slideUp(350);
		 }else{				 
		     if($(this).parent().parent().attr("id")=="mobile"){
		    	 $("#mobile.nav li ul").slideUp(350);
				 $("#mobile.nav .parent>a").removeClass("open");
		       	 $(this).next("ul").slideDown(350);
			     $(this).addClass("open");			     
		     }else{
		    	 $(this).parent().parent().find("li ul").slideUp(350);
		    	 $(this).parent().prev().find("a").removeClass("open");
		    	 $(this).parent().parent().prev().addClass("open");
		    	 $(this).next("ul").slideDown(350);
			     $(this).addClass("open");
			     $(this).parent().parent().slideDown(350);
		     }				 			 
		 }     
	   }
	});
	
});

</script>
<script>
   function googleTranslateElementInit() {
     new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');
     $(".skiptranslate").removeAttr("frameborder").attr("style","border:0px");
     $("script").removeAttr("type").removeAttr("charset");
     function changeGoogleStyles() {
         if($('.goog-te-menu-frame').contents().find('.goog-te-menu2').length) {
             $('.goog-te-menu-frame').contents().find('.goog-te-menu2').css({
                 'max-width':'100%',
                 'overflow-x':'auto',
                 'box-sizing':'border-box',
                 'height':'auto'
             });
         } else {
             setTimeout(changeGoogleStyles, 50);
         }
     }
     changeGoogleStyles();
   }
   
   var headerHeight = $('header').innerHeight();
   $(document).ready(function() {
	$('#google_translate_element').bind('DOMNodeInserted', function(event) {
		/* ----- To change the text in google language select starts ----- */
		//$('.goog-te-menu-value span:first').html('Change Language');
		/* ----- To change the text in google language select ends ----- */
		
		/* ----- To set the height of the google translate banner starts ----- */
		$('.goog-te-banner-frame').css({'top':headerHeight+5});
		/* ----- To set the height of the google translate banner ends ----- */
	});
   });
   
</script>
<script>
   $(document).ready(function(){
   	if($("div").hasClass('invisibeHead')){
      $("#top-header").addClass('hide');
    }
   });   
  /*  function downloadCounter(){
		var countVal= parseInt($('#MarathiCount').html());
		countVal=countVal+1;
		var response = __doAjaxRequest('HitCounterCookies.html?updateMyMarathiCount','GET','','');
		if(response == true)
		{
			$('#MarathiCount').html(countVal);
			window.open('https://play.google.com/store/apps/details?id=co.mymarathi.app', '_blank');
		}
			
	} */
</script>
<style>

</style>
<div id="sdiv"></div><div class="sloading"><img src="css/images/loader.gif" alt="loading" /></div>
<div class="page" id="pagecontainer"></div>
<%-- <c:set var="headerImages" value="${command.headerDetails}" /> --%>
<header id="mainHeader">
	<!-- Header-1 starts -->
	<div class="header-1">
		<div class="row website-header">
			<div class="header-options col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<!-- Social Icons starts -->
				<div class="header-skip-to-content">
					<ul>
						<li>
							<a href="#main">
								<i class="fa fa-arrow-circle-o-down" aria-hidden="true"></i>
								<span class="hidden-xs hidden-sm"><spring:message code="Skip" text="Skip to Main Content"/></span>
							</a>
						</li>
					</ul>
				</div>
				<div class="header-social-icons-section">
					<ul>
						<li class="virtual-keyboard hidden-xs hidden-sm">
							<a href="javascript:void(0);" class="virtualKeyboard vk-off">
								<i class="fa fa-keyboard-o" aria-hidden="true"></i>
								<span class="hidden-xs hidden-sm"><spring:message code="theme6.portal.virtual.keyboard" text="Virtual Keyboard"/></span>
							</a>
						</li>
						<%-- <li class="hidden-xs"><a href="javascript:void(0);" class="blue-icon" title="Blue Theme" onclick="setcontrast('B')"></a></li>
						<li class="hidden-xs"><a href="javascript:void(0);" class="yellow-icon" title="Green Theme" onclick="setcontrast('G')"></a></li>
						<li class="hidden-xs"><a href="javascript:void(0);" class="green-icon" title="Dark Green Theme" onclick="setcontrast('DG')"></a></li> --%>
						<c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
						<c:if test="${media.key ne 'youtube' }">
							<li>
								<a href="${media.value}" target="new_${ count.count}" class="${media.key}" aria-label="${media.key}">
									<i class="fa fa-${media.key}">
										<span class="hidden">
											${media.key}
										</span>
									</i>
								</a>
							</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<!-- Social Icons ends -->
			</div>
			<div class="website-name-main col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="logo-shadow"></div>
				<div class="website-name-container">
					
					<a class="navbar-brand" href="CitizenHome.html">
						<%-- <c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status"> --%>
							<span class="website-logo">
								<img src="./images/KDMC-images/kdmc_footer_logo.png" class="img-responsive" alt="Kalyan Dombivli Municipal Corporation Logo">
								<c:set var="parts" value="${fn:split(logo, '*')}" />
								<c:if test="${parts[1] eq '1'}">
									<%-- <img src="${parts[0]}" class="img-responsive"
									alt="
									<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
									<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> Logo
									"> --%>
								</c:if>
							</span>
						<%-- </c:forEach> --%>
						<span class="website-name wb-nm">
							<c:if test="${userSession.languageId eq 1}">
								${userSession.organisation.ONlsOrgname}
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								${userSession.organisation.ONlsOrgnameMar}
							</c:if>
						</span>
					</a>
				</div>
			</div>
			
			<!-- Website settings starts  -->
			<div class="website-settings col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<ul>
					<li class="visible-xs visible-sm">
						<a href="CitizenHome.html" title="<spring:message code="top.home" text="Home"/>">
							<i class="fa fa-home" aria-hidden="true">
								<span class="hidden">
									<spring:message code="top.home" text="Home"/>
								</span>
							</i>
						</a>
					</li>
					<li><a href="javascript:void(0);" id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce"><spring:message code="code.a-" text="A-"/></a></li>
					<li><a href="javascript:void(0);" id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal"><spring:message code="code.a" text="A"/></a></li>
					<li><a href="javascript:void(0);" id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase"><spring:message code="code.a+" text="A+"/></a></li>
					<li>
						<a href="Accessibility.html" title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options">
							<i class="fa fa-adjust" aria-hidden="true">
								<span class="hidden">
									<spring:message code="Accessibility" text="Accessibility Options"/>
								</span>
							</i>
						</a>
					</li>
					<li>
						<a href="sitemap.html" aria-label='<spring:message code="sitemap" text="Sitemap"/>' title="<spring:message code="sitemap" text="Sitemap"/>">
							<i class="fa fa-sitemap" data-toggle="tooltip" data-placement="bottom" tittle="SiteMap">
								<span class="hidden">
									<spring:message code="sitemap" text="Sitemap"/>
								</span>
							</i>
						</a>
					</li>
					<li>
						<c:if test="${userSession.languageId eq 1}">
							<a onclick="changeLanguage('?locale&lang=reg');" href="javascript:void(0);" title="<spring:message code="header.reg" text="header.reg"/>" class="chng-lang">
								<spring:message code="header.reg" text="header.reg" />
							</a>
						</c:if>
						<c:if test="${userSession.languageId eq 2}">
							<a onclick="changeLanguage('?locale&lang=en');" href="javascript:void(0);" title="<spring:message code="header.eng" text="header.eng"/>" class="chng-lang">
								<spring:message code="header.eng" text="header.eng" />
						   </a>
						</c:if>
					</li>
					<li class="hidden-xs hidden-sm hidden-md">
						<div id="google_translate_element"></div>
					</li>
					<%-- <li class="sml-scr-settings smlScrSettings visible-xs">
						<a href="javascript:void(0);">
							<i class="fa fa-share-alt" aria-hidden="true"></i>
						</a>
						<ul>
							<c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
							<c:if test="${media.key ne 'youtube' }">
								<li>
									<a href="${media.value}" target="new_${ count.count}" class="${media.key}">
										<i class="fa fa-${media.key}"></i>
									</a>
								</li>
								</c:if>
							</c:forEach>
						</ul>
					</li>
					<li class="sml-scr-settings smlScrSettings visible-xs visible-sm">
						<a href="javascript:void(0);">
							<i class="fa fa-cog" aria-hidden="true"></i>
						</a>
						<ul>
							<li class="visible-xs"><a href="javascript:void(0);" class="blue-icon" title="Blue Theme" onclick="setcontrast('B')"></a></li>
							<li class="visible-xs"><a href="javascript:void(0);" class="yellow-icon" title="Green Theme" onclick="setcontrast('G')"></a></li>
							<li class="visible-xs"><a href="javascript:void(0);" class="green-icon" title="Dark Green Theme" onclick="setcontrast('DG')"></a></li>
							<li>
								<a href="Accessibility.html" title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options">
									<i class="fa fa-adjust" aria-hidden="true">
										<span class="hidden"><spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options"></span>
									</i>
								</a>
							</li>
							<li>
								<a href="sitemap.html" aria-label='<spring:message code="sitemap" text="Sitemap"/>' title="<spring:message code="sitemap" text="Sitemap"/>">
									<i class="fa fa-sitemap" data-toggle="tooltip" data-placement="bottom"></i>
								</a>
							</li>
						</ul>
					</li> --%>
					<li class="visible-xs visible-sm">
						<a id="mobile-button" href="javascript:void(0);" aria-label="Menu"><i class="fa fa-align-justify" aria-hidden="true">
								<span class="hidden">
									<spring:message code="eip.mobile.navigation" text="Navigation for Mobile"/>
								</span>
						</i></a>
					</li>
				</ul>
			</div>
			<!-- Website settings ends  -->
			
			
		</div>
	</div>
	<!-- Header-1 ends -->
	
	<!-- Header-2 starts -->
	<div class="header-2">
		<nav class="navbar navbar-expand-sm">
			<div class="main-menu-container col-sm-12 col-md-12 col-lg-12">
				<ul class="main-navigation-menu">
					<li>
						<a href="CitizenHome.html" title="<spring:message code="top.home" text="Home"/>">
							<i class="fa fa-home" aria-hidden="true">
								<span class="hidden">
									<spring:message code="top.home" text="Home"/>
								</span>
							</i>
						</a>
					</li>
					<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
					<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
					<li class="blink">
						<a href='CitizenFeedBack.html' class="pinned">
							<spring:message code="theme6.portal.suggest.innovative.ideas" text="Suggest Innovative Ideas To Us"/>
						</a>
					</li>
					<li class="blink">
						<a href='CitizenFAQ.html?getFAQ'>
							<spring:message code="top.faq" text="Faqs"/>
						</a>
					</li>
					<li class="parent"><a href="#"><spring:message code="Login" text="Login"/></a>	
						<ul>
							<c:if test="${command.themeMap['CITIZEN_LOGIN'] ne 'I'}"> 
								<li class="blink"><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
							</c:if> 
							<li class="blink"><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span></a></li>
						 	<c:if test="${command.themeMap['DEPARTMENT_EMPLOYEE_LOGIN'] ne 'I'}">
								<li class="blink"><a href='<spring:message code="service.admin.home.url"/>'  target="_blank"><span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span></a></li>
						 	</c:if> 
						</ul>
					</li>
					<%-- <li class="blink"><a onclick="downloadCounter();"><spring:message code="my.marathi" text="My Marathi App"/> <span class="arrow-box animate-number" data-value="<%=request.getSession().getAttribute("MarathiCount")%>" data-duration="3000" id="MarathiCount">0</span></a> --%>
				</ul>
			</div>
			<%-- <form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm"> --%>
			
				<div class="content-search-main col-xs-2 col-sm-2 col-md-2 col-lg-2">
				 <form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
						<input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
						<div class="content-search" id="searchButton">
								<button class="btn btn-danger" type="Submit" id="searchButton" aria-label="Search"><strong class="fa fa-search"></strong></button>
						</div>
					</form:form>
					
	      				<%-- <div class="input-group">
	      					<input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
	      					<span class="input-group-btn"><button class="btn" type="Submit" id="searchButton" aria-label="Search"><strong class="fa fa-search"></strong></button></span>
	      				</div> --%>
	      			
				</div>
				
			<%-- </form:form>  --%>
		</nav>
	</div>
	<!-- Header-2 ends -->
	
	<!-- Mobile View starts -->
	<div id="navbar" style="display:none;">
		<nav class="navbar navbar-default hidden-lg hidden-md">
			<div class="container-fluid nav-scroll">
				<ul id="mobile" class="nav navbar-nav">                  
					<li class="parent">
						<a class="dropdown-toggle" href="javascript:void(0);">
							<spring:message code="Login" text="Login"/>
						</a>
						<ul>
							<c:if test="${command.themeMap['CITIZEN_LOGIN'] ne 'I'}">
								<li>
									<a href='javascript:void(0);' onclick="getCitizenLoginForm('N')">
										<span><spring:message code="CitizenLogin" text="Citizen Login"/></span>
									</a>
								</li>
							</c:if>
							<li>
								<a href='javascript:void(0);' onclick="getAdminLoginForm()">
									<span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span>
								</a>
							</li>
							<c:if test="${command.themeMap['DEPARTMENT_EMPLOYEE_LOGIN'] ne 'I'}">
								<li>
									<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
										<span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span>
									</a>
								</li>
							</c:if>
						</ul>
					</li>
					<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
					<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
					<li class="blink">
						<a href='CitizenFeedBack.html' class="pinned">
							<spring:message code="theme6.portal.suggest.innovative.ideas" text="Suggest Innovative Ideas To Us"/>
						</a>
					</li>
					<li class="blink">
						<a href='CitizenFAQ.html?getFAQ'>
							<spring:message code="top.faq" text="Faqs"/>
						</a>
					</li>
				</ul>
			</div>
		</nav>
	</div>
	<!-- Mobile View ends -->
	
</header>

<!-- Widget Links starts -->
<div class="widget-links hidden-xs hidden-sm">
	<ul>
	
		<%-- </c:if>
		<c:if test="${command.themeMap['FEEDBACK'] ne 'I'}"> --%>
			<li>
				<a href="CitizenContactUs.html" title="<spring:message code="eip.citizen.footer.contactUs" text="Contact Us"/>">
					<spring:message code="eip.citizen.footer.contactUs" text="Contact Us"/>
				</a>
			</li>
		<%-- </c:if> --%>
		<%-- <c:if test="${command.themeMap['OPINION_POLL'] ne 'I'}"> --%>
			<li>
				<a href="AdminOpinionPollOptionResponseForm.html" title="<spring:message code="theme6.portal.opinion.poll" text="Opinion Poll"/>">
					<spring:message code="theme6.portal.opinion.poll" text="Opinion Poll"/>
				</a>
			</li>
	
	</ul>
</div>
<!-- Widget Links ends -->
