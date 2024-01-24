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
   	//$("#selectedOrg1").chosen();
   	  $("#mobile.nav .parent>a").on("click", function(e){
   	    if($(this).parent().has("ul")) {
   	      e.preventDefault();
   	    }
 	    
   	    if(!$(this).hasClass("open")) {
   	      // hide any open menus and remove all other classes
   	      $("#mobile.nav li ul").slideUp(350);
   	      $("#mobile.nav .parent>a").removeClass("open");
   	      
   	      // open our new menu and add the open class
   	      $(this).next("ul").slideDown(350);
   	      $(this).addClass("open");
   	    }
   	    
   	    else if($(this).hasClass("open")) {
   	      $(this).removeClass("open");
   	      $(this).next("ul").slideUp(350);
   	    }
   	  });   	  
   	});
   
</script>
<script>
   function googleTranslateElementInit() {
     new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');
     $(".skiptranslate").removeAttr("frameborder").attr("style","border:0px");
     $("script").removeAttr("type").removeAttr("charset");
   }
</script>
<script>
   $(document).ready(function(){
   	if($("div").hasClass('invisibeHead')){
      $("#top-header").addClass('hide');
    }
   });   
</script>

<script>
	$(function() {
	  $('#main-menu').smartmenus();
	});
</script>
<div class="page" id="pagecontainer"></div>
<%-- <c:set var="headerImages" value="${command.headerDetails}" /> --%>
<header>
	<!-- Header-1 starts -->
	<div class="header-1">
		<div class="header-options">
			<!-- Website Settings starts -->
			<div class="website-settings">
				<ul>
					<li><a id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce">A -</a></li>
					<li><a id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal">A</a></li>
					<li><a id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase">A +</a></li>
					<li>
						<a href="Accessibility.html" title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options">
							<i class="fa fa-adjust" aria-hidden="true">
								<span class="hidden">
									<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options">
								</span>
							</i>
						</a>
					</li>
					<li>
						<a href="sitemap.html" aria-label='<spring:message code="sitemap" text="Sitemap"/>' title="<spring:message code="sitemap" text="Sitemap"/>">
							<i class="fa fa-sitemap" data-toggle="tooltip" data-placement="bottom"></i>
						</a>
					</li>
					<li>
						<c:if test="${userSession.languageId eq 1}">
							<a onclick="changeLanguage('?locale&lang=reg');" href="javascript:void(0);" title="<spring:message code="header.reg" text="header.reg"/>">
								<spring:message code="header.reg" text="header.reg" />
							</a>
						</c:if>
						<c:if test="${userSession.languageId eq 2}">
							<a onclick="changeLanguage('?locale&lang=en');" href="javascript:void(0);" title="<spring:message code="header.eng" text="header.eng"/>">
								<spring:message code="header.eng" text="header.eng" />
						   </a>
						</c:if>
					</li>
					<li>
						<c:set var="curentDomain" value="${userSession.organisation.orgid}.domain.url"/>
						<spring:message var="curDomainName" code="${curentDomain}" text=""></spring:message>
						<c:if test="${empty curDomainName}">
							<a href="ULBHome.html?resetULB&orgId=${command.appSession.superUserOrganization.orgid}" title="<spring:message code="theme6.portal.main.dept" text="Go To Parent"/>">
								<i class="fa fa-reply-all" aria-hidden="true"></i> 
								<span class="hidden-xs">
									<spring:message code="theme6.portal.main.dept" text="Go To Parent"/>
								</span>
							</a>
						</c:if>
					</li>
					<li class="ws-icon ws-btn"><i class="fa fa-cogs"></i></li>
				</ul>
			</div>
			<!-- Website Settings ends -->
		</div>
		<div class="row website-header">
			<!-- Website Menu Bar starts -->
			<nav class="navbar navbar-expand-sm">
				<div class="website-name-main col-xs-6 col-sm-6 col-md-2 col-lg-2">
					<a class="navbar-brand" href="CitizenHome.html">
						<c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
							<span class="website-logo">
								<c:set var="parts" value="${fn:split(logo, '*')}" />
								<c:if test="${parts[1] eq '1'}">
									<img src="${parts[0]}" class="img-responsive"
									alt="Organisation Logo"
									<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
									<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> Logo
									">
								</c:if>
							</span>
						</c:forEach>
						<%-- <span class="website-name wb-nm">
							<c:if test="${userSession.languageId eq 1}">
								${userSession.organisation.ONlsOrgname}
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								${userSession.organisation.ONlsOrgnameMar}
							</c:if>
						</span> --%>
					</a>
				</div>
				<div class="col-sm-8 col-md-8 col-lg-8 hidden-xs">
					<ul class="main-navigation-menu sm sm-blue" id="main-menu">
						<li>
							<a href="CitizenHome.html" title="<spring:message code="top.home" text="Home"/>">
								<spring:message code="top.home" text="Home"/>
							</a>
						</li>
						<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
						<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
						<li><a href="#"><spring:message code="Login" text="Login"/></a>			
							<ul>
								<li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
								<li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span></a></li>
								<li><a href='<spring:message code="service.admin.home.url"/>'  target="_blank"><span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span></a></li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="header-gov-logos col-xs-6 col-sm-6 col-md-2 col-lg-2">
					<span class="hgl">
						<a href="" target="_blank">
							<c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
								<c:set var="parts" value="${fn:split(logo, '*')}" />
								<c:if test="${parts[1] eq '2'}">
									<img src="${parts[0]}" class="hidden-xs img-responsive"
										alt="<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
										<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> logo">
								</c:if>	
							</c:forEach>
						</a>
					</span>
				</div>
				<div class="visible-xs mob-hamburger">
					<a id="mobile-button" href="javascript:void(0);" aria-label="Menu"><i class="fa fa-align-justify" aria-hidden="true"></i></a>
				</div>
				
			</nav>
		</div>
	</div>
	<!-- Header-1 ends -->
	
	<!-- Mobile View starts -->
	<div id="navbar" style="position:fixed;width:100%;display:none;">
		<nav class="navbar navbar-default hidden-lg hidden-md hidden-sm">
			<div class="container-fluid nav-scroll">
				<ul id="mobile" class="nav navbar-nav">                    
					<li class="parent">
						<a class="dropdown-toggle" href="javascript:void(0);">
							<spring:message code="Login" text="Login"/>
						</a>
						<ul>
							<li>
								<a href='javascript:void(0);' onclick="getCitizenLoginForm('N')">
									<span><spring:message code="CitizenLogin" text="Citizen Login"/></span>
								</a>
							</li>
							<li>
								<a href='javascript:void(0);' onclick="getAdminLoginForm()">
									<span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span>
								</a>
							</li>
							<li>
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span>
								</a>
							</li>
						</ul>
					</li>
					<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
					<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
				</ul>
			</div>
		</nav>
	</div>
	<!-- Mobile View ends -->
	
</header>