<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="js/eip/citizen/top-header.js"></script>
<script src="js/eip/admin/adminLoginForm.js"></script>
<script src="js/eip/citizen/citizenLoginForm.js"></script>
<script src="js/eip/agency/agencyLoginForm.js"></script>
<script src="js/eip/agency/agencyRegistrationForm.js"></script>
<script src="js/eip/agency/agencyForgotPasswordProcess.js"></script>
<script src="js/eip/agency/agencyResetPasswordProcess.js"></script>
<script src="js/eip/citizen/citizenRegistrationForm.js"></script>
<script src="js/eip/citizen/citizenForgotPasswordProcess.js"></script>
<script src="js/eip/admin/adminForgotPasswordProcess.js"></script>
<script src="js/eip/citizen/citizenResetPasswordProcess.js"></script>
<script src="js/eip/citizen/adminResetPasswordProcess.js"></script>
<script src="js/eip/admin/adminRegistrationForm.js"></script>
<script src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>


<script>
$(document).ready(function(){
	  $("#mobile.nav li a").on("click", function(e){
	    if($(this).parent().has("ul")) {
	      e.preventDefault();
	    }
	    
	    if(!$(this).hasClass("open")) {
	      // hide any open menus and remove all other classes
	      $("#mobile.nav li ul").slideUp(350);
	      $("#mobile.nav li a").removeClass("open");
	      
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




<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<script>
 function googleTranslateElementInit() {
  new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');
}
</script>
 <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">  

<script>
	$(document).ready(function(){
		if($("div").hasClass('invisibeHead')){
	   $("#top-header").addClass('hide');
	 }
});
	
</script>

 </c:if>
			
        	
      		


<div class="page" id="pagecontainer"></div>
<c:set var="headerImages" value="${command.headerDetails}" />


<header class="site-header">
		<nav class="navbar navbar-default">
				<div class="top-line navbar-fixed-top">
						<div class="container-fluid">
 						<div class="col-md-2 col-sm-2 col-xs-6">
 						<p class="hidden-xs"><span><a id="menu-toggle" href="javascript:void(0);" title="Menu" ><i class="fa fa-align-justify" aria-hidden="true"></i> <spring:message code="Menu" text="MENU"/></a></span> </p>
						</div>
						<div class="col-md-10 col-sm-10 col-xs-6 text-right">
						<div class="last">
						<span><a href="CitizenHome.html" title="<spring:message code="top.home" text="Home"/>"><i class="fa fa-home" aria-hidden="true"></i><spring:message code="top.home" text="Home"/></a></span>										
						<span class="hidden-xs hidden-sm"><a href="#MainContent" title="<spring:message code="Skip" text="Skip to Main Content"/>"><i class="fa fa-arrow-circle-o-down" aria-hidden="true"></i> <spring:message code="Skip" text="Home"/></a></span>										

			<c:set var="curentDomain" value="${userSession.organisation.orgid}.domain.url"/>
          	<spring:message var="curDomainName" code="${curentDomain}" text=""></spring:message>
          	<c:if test="${empty curDomainName }">
          	<span class="hidden-sm hidden-xs"><a href="ULBHome.html?resetULB&orgId=${command.appSession.superUserOrganization.orgid}" title="<spring:message code="main.dept" text="Go to Home Page"/>"><i class="fa fa-reply-all" aria-hidden="true"></i> <spring:message code="main.dept" text="Go to Home Page"/></a></span>
          	</c:if>
          	<span class="hidden-xs hidden-sm"><a href="NewsLetterSubscription.html" title="<spring:message code="" text="Subscribe Newsletter"/>"><i class="fa fa-envelope" aria-hidden="true"></i> <spring:message code="" text="Subscribe Newsletter"/></a></span>


			<c:if test="${command.userSession.socialMediaMap.size() gt 0}">
 				<c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
					<span class="hidden-xs social"><a href="${media.value}" target="new_${ count.count}" title="${media.key}">
					<i class="fa fa-${media.key}"> <span class="hidden">${media.key}</span></i></a></span>
				</c:forEach>
			</c:if>
          <span class="hidden-xs"><a href="#" title="Blue Theme" onclick="setcontrast('B')"> <i class="fa fa-circle fa-lg" style="color:#1E90FF;" data-toggle="tooltip" data-placement="bottom" title="Select Blue Theme"></i><span class="hide">Blue</span> </a></span>
		  <span class="hidden-xs"><a href="#" title="Orange Theme" onclick="setcontrast('O')"><i class="fa fa-circle fa-lg" style="color:#FFA500;" data-toggle="tooltip" data-placement="bottom" title="Select Orange Theme"></i><span class="hide">Orange</span> </a></span>
          <span class="hidden-xs"><a href="#" title="Green Theme" onclick="setcontrast('G')"> <i class="fa fa-circle fa-lg"  style="color:#00AA55;" data-toggle="tooltip" data-placement="bottom" title="Select Green Theme"></i><span class="hide">Green</span> </a></span>
          <span class="hidden-xs"><a href="Accessibility.html" title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options"><i class="fa fa-adjust fa-lg" aria-hidden="true"><span class="hidden"><spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options"></span></i> </a></span>
          <span class="hidden-xs hidden-sm"><a href="#" id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce">A -</a></span>
          <span class="hidden-xs hidden-sm"><a href="#" id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal">A</a></span>
          <span class="hidden-xs hidden-sm"><a href="#" id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase">A +</a></span>

		<span class="header-search hidden-xs">
		<form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
      	<div class="input-group">
      	<label class="hidden" for="search_input">Search</label>
      <input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
      <span class="input-group-btn"><button class="btn btn-default" type="Submit" id="searchButton"><strong class="fa fa-search"></strong><span class="hide">searchButton</span></button></span></div>
      </form:form></span> 


            
<c:if test="${userSession.languageId eq 1}">
            <span><a onclick="changeLanguage('?locale&lang=reg');" href="javascript:void(0);" title="<spring:message code="header.reg" text="header.reg"/>"><i class="fa fa-globe"></i> <spring:message code="header.reg" text="header.reg" /></a></span>
          </c:if>
          <c:if test="${userSession.languageId eq 2}">
            <span><a onclick="changeLanguage('?locale&lang=en');" href="javascript:void(0);" title="<spring:message code="header.eng" text="header.eng"/>"><i class="fa fa-globe"></i> <spring:message code="header.eng" text="header.eng" /></a></span>
          </c:if> 
<span id="google_translate_element" class="hidden-xs hidden-sm hidden-md" style="display:inline-block;"></span>
</div></div>


<div id="navigation" class="hidden-xs">
      <div class="container">
        <div class="row row-eq-height">
          <div class="col-md-3 col-xs-12">
            <div class="columns">
              <h3><a href="CitizenHome.html"><spring:message code="top.home" text="Home"/></a></h3>
        		<ul>
       			<li><a href='CitizenAboutUs.html' title="<spring:message code="top.aboutus" text="About Us"/>"><span><spring:message code="top.aboutus" text="About Us"/></span></a></li>
        		<li><a href='CitizenFAQ.html?getFAQ' title="<spring:message code="top.faq" text="Faqs"/>"><span><spring:message code="top.faq" text="Faqs"/></span></a></li>
        		<li><a href='CitizenContactUs.html' title="<spring:message code="eip.citizen.footer.contactUs" text="Contact Us" />"><span><spring:message code="eip.citizen.footer.contactUs" text="Contact Us" /></span></a></li>
        		<li><a href="sitemap.html" title="<spring:message code="sitemap" text="Sitemap"/>"><spring:message code="sitemap" text="Sitemap"/></a></li>
    			</ul>
    			 <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">  
			   <h3><spring:message code="portal.head" text="Faqs"/></h3>
			   
    			<div class="input-group">
    			
    			<form:form name="selectMunicipalForm" id="selectMunicipalForm" action="" class="list" method="get">
    			<div class="row">
    			
    			<div class="col-sm-10 col-xs-9">
    				<label class="hidden" for="selectedOrg">Select Department</label>
				<select name="orgId" id="selectedOrg" data-content="" class="form-control">
				<option value="-1"><spring:message code="Select" text="Select" /></option>
				<c:forEach items="${command.userSession.organisationsList}" var="orglist">
				<optgroup label="${orglist.key}">
					<c:forEach items="${orglist.value}" var="org">
						<c:if test="${userSession.languageId eq 1}"><option value="${org.orgid}">${org.ONlsOrgname}</option></c:if>
						<c:if test="${userSession.languageId eq 2}"><option value="${org.orgid}">${org.ONlsOrgnameMar}</option></c:if>
					</c:forEach>
				</optgroup>

				</c:forEach>
			</select>
			</div>
			
			
	 <div class="col-sm-2 col-xs-2 "><div class="row"><span class="input-group-btn"><button type="button" class="btn btn-default" id="submitMunci"><spring:message text="Go!"/></button></span></div></div>
    </div>
    </form:form>
    </div> 
    </c:if> 
      
	<h3><spring:message code="Login" text="Login"/></h3>
    	<ul>
        <li><a href='#' onclick="getAdminLoginForm()"><span><spring:message code="header.administrative.user" text="Administrative User"/></span></a></li>
        <li><a href='#' onclick="getAdminLoginForm()"><span><spring:message code="header.iprd.user" text="IPRD User"/></span></a></li>
        <li><a href='#' onclick="getAdminLoginForm()"><span><spring:message code="header.departments.user" text="Departments Users"/></span></a></li>
    	</ul>
		<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
			  <h3><spring:message code="portal.dashboard" text="Dashboard"/></h3>
              <ul>
              <li><a href='DashBoard.html' title="<spring:message code="portal.dashboard" text="Dashboard"/>"><span><spring:message code="portal.dashboard" text="Dashboard"/></span></a></li>
              </ul>
 		</c:if>  
			  
            </div>
          </div>
          <div class="col-md-9 col-xs-12">
              <div class="columns-multilevel">
              <ul class="section-nav">
			<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
			<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
			<li class="parent"><a href="javascript:void(0);" class="blink" rel="1577343694" title="Web Archives" >Archival</a></li>
			<li class="input-link" class="blink"><a href="DataArchival.html?archivedData" title="Web Archives">Archival Data</a></li>
			</ul>
            </div>
          </div>
          <a href="javascript:void(0)" title="Close" class="close_icon"><i class="fa fa-times-circle" aria-hidden="true"></i><span>Close</span></a> </div>
      </div>
    </div>



</div>
</div>
	<div class="header-inner">
<%-- 		<c:if test="${userSession.employee.emploginname ne 'NOUSER' }"> --%>
	<div class="container-fluid">
 	<div class="col-md-9 col-sm-9 col-lg-9 col-xs-12"> 
	<div class="brand wow fadeInLeft animated animated" data-wow-duration="1.5s" data-wow-offset="10" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;">
	<a href="CitizenHome.html"><c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
    <c:set var="parts" value="${fn:split(logo, '*')}" />
    <c:if test="${parts[1] eq '1'}"> <img src="${parts[0]}" class="pull-left" alt="<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if><c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>">  </c:if>
    </c:forEach>	
    
     <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">  
    	<c:if test="${userSession.languageId eq 1}"><h1>${userSession.organisation.ONlsOrgname}</h1></c:if>
    	<c:if test="${userSession.languageId eq 2}"><h1>${userSession.organisation.ONlsOrgnameMar}</h1></c:if>
    </c:if>
    
     <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">  
     <h1 class="brand-main"> 
     <c:if test="${userSession.languageId eq 1}"><c:set var="orgname" value="${userSession.organisation.ONlsOrgname}" /></c:if>
     <c:if test="${userSession.languageId eq 2}"><c:set var="orgname" value="${userSession.organisation.ONlsOrgnameMar}" /></c:if>
	  <c:forEach items="${fn:split(orgname, ' ')}" var="text">
	    ${text} <br>
	    </c:forEach>
	 </h1>
	 </c:if>
    </a>
	</div>
	
	</div>
	<div class="col-md-3 col-sm-3 col-lg-3 hidden-xs"><div class="brand wow fadeInRight animated animated pull-right" data-wow-duration="1.5s" data-wow-offset="10" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;"><img src="assets/img/national_emblem.png" class="pull-right" alt="National Emblem"></div></div>
 	</div>
<%--  	</c:if> --%>
						
						<div class="container">
						
						
						
		 
    
    <nav class="navbar navbar-default hidden-lg hidden-md hidden-sm">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">  
     	  <a class="navbar-brand" href="#">
     	  <c:if test="${userSession.languageId eq 1}"><c:set var="orgname" value="${userSession.organisation.ONlsOrgname}" /></c:if>
          <c:if test="${userSession.languageId eq 2}"><c:set var="orgname" value="${userSession.organisation.ONlsOrgnameMar}" /></c:if>
	      <c:forEach items="${fn:split(orgname, ' ')}" var="text">${text}</c:forEach></a>
	      </c:if>
         </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul id="mobile" class="nav navbar-nav">
           <li class="parent"><a class="dropdown-toggle" href="javascript:void(0);"> <spring:message code="Login" text="Login"/></a>
				<ul>
   				<li><a href='#' onclick="getAdminLoginForm()"><span><spring:message code="header.administrative.user" text="Administrative User"/></span></a></li>
        		<li><a href='#' onclick="getAdminLoginForm()"><span><spring:message code="header.iprd.user" text="IPRD User"/></span></a></li>
        		<li><a href='#' onclick="getAdminLoginForm()"><span><spring:message code="header.departments.user" text="Departments Users"/></span></a></li>
                </ul>
                </li>
 			<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
			<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
           </ul>
         </div>
      </div>
    </nav>
    
 </div>
</div>
</nav>
</header>


<header id="top-header" class="page-heading"><div class="container"></div></header>
 