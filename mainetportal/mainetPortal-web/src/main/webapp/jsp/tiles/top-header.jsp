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

<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<script>
function googleTranslateElementInit() {
  new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');
}
</script>

<div class="page" id="pagecontainer"></div>
<c:set var="headerImages" value="${command.headerDetails}" />

<header id="header" data-plugin-options='{"stickyEnabled": true, "stickyEnableOnBoxed": true, "stickyEnableOnMobile": true, "stickyStartAt":0, "stickySetTop": "0px"}'>
  <div class="header-top">
    <div class="container clearfix">
      <div class="pull-left">
        <ul>
        <li class="hidden-xs hidden-sm"><a id="menu-toggle" href="#" class="glyphicon glyphicon-align-justify btn-menu toggle" > <spring:message code="Menu" text=""/> &nbsp; <i class="fa fa-bars"></i></a></li>
           	<li class="hidden-xs hidden-sm"><a href="Accessibility.html" title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options"><i class="fa fa-universal-access" aria-hidden="true"></i> <spring:message code="Accessibility" text="Accessibility Options"/></a></li>
          	<li class="hidden-sm hidden-xs"><a href="#MainContent" title="<spring:message code="Skip" text="Skip to Main Content"/>"><i class="fa fa-arrow-circle-o-down" aria-hidden="true"></i> <spring:message code="Skip" text="Skip to Main Content"/></a></li>
          	<c:set var="curentDomain" value="${userSession.organisation.orgid}.domain.url"/>
          	<spring:message var="curDomainName" code="${curentDomain}" text=""></spring:message>
          	<c:if test="${empty curDomainName }">
          	  	<li class="hidden-sm hidden-xs"><a href="ULBHome.html?resetULB&orgId=${command.appSession.superUserOrganization.orgid}" title="<spring:message code="main.dept" text="go To Main Dept"/>"><i class="fa fa-reply-all" aria-hidden="true"></i> <spring:message code="main.dept" text="go To Main Dept"/></a></li>
          	</c:if>
         </ul>
      </div>
      <div class="pull-right">
        <ul>
        <c:if test="${command.userSession.socialMediaMap.size() gt 0}">
      		<c:forEach var="media" items="${command.userSession.socialMediaMap}" >
        	<li><a href="${media.value}" target="new"><i class="fa fa-${media.key}"></i></a></li>
      		</c:forEach>
 			</c:if>
          <li class="hidden-xs"><a href="javascript:void(0);" class="orange" title="Orange Theme" onclick="setcontrast('O')"> <i class="fa fa-circle fa-lg orange" data-toggle="tooltip" data-placement="bottom" title="Select Orange Theme"></i><span class="hide">Orange</span> </a></li>
          <li class="hidden-xs"><a href="javascript:void(0);" title="Blue Theme" class="blue" onclick="setcontrast('B')"> <i class="fa fa-circle fa-lg blue" data-toggle="tooltip" data-placement="bottom" title="Select Blue Theme"></i><span class="hide">Blue</span> </a></li>
          <li class="hidden-xs"><a href="javascript:void(0);" title="Green Theme" class="green" onclick="setcontrast('G')"> <i class="fa fa-circle fa-lg green" data-toggle="tooltip" data-placement="bottom" title="Select Green Theme"></i><span class="hide">Green</span> </a></li>
          <li class="hidden-xs"><a href="javascript:void(0);" id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce">A -</a></li>
          <li class="hidden-xs"><a href="javascript:void(0);" id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal">A</a></li>
          <li class="hidden-xs"><a href="javascript:void(0);" id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase">A +</a></li>
          <li class="header-search hidden-xs hidden-md hidden-sm"> 
            <form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
            <div class="input-group">
            <label class="hide" for="search_input">SearchContent</label>
            <input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />"> <span class="input-group-btn"> <button class="btn btn-default" type="Submit" id="searchButton"><strong class="fa fa-search"></strong><span class="hide">searchButton</span></button></span> </div>
            </form:form>
            </li>
            <c:if test="${userSession.languageId eq 1}">
            <li><a onclick="changeLanguage('?locale&lang=reg');" href="javascript:void(0);" title="<spring:message code="header.reg" text="header.reg"/>"><i class="fa fa-globe"></i> <spring:message code="header.reg" text="header.reg" /></a></li>
          </c:if>
          <c:if test="${userSession.languageId eq 2}">
            <li><a onclick="changeLanguage('?locale&lang=en');" href="javascript:void(0);" title="<spring:message code="header.eng" text="header.eng"/>"><i class="fa fa-globe"></i> <spring:message code="header.eng" text="header.eng" /></a></li>
          </c:if>
          <li><div id="google_translate_element"></div></li>
        </ul>
      </div>
    </div>
    
    
    
    <div id="navigation" class="hidden-xs hidden-sm"> 
	<div class="container">
	<div class="row row-eq-height">
 	<div class="col-md-3 col-xs-12">
  	<div class="columns">
  	<h3><a href="CitizenHome.html"><spring:message code="top.home" text="Home"/></a></h3>
        <ul>
       	<li><a href='CitizenAboutUs.html'><span><spring:message code="top.aboutus" text="About Us"/></span></a></li>
        <li><a href='CitizenFAQ.html?getFAQ'><span><spring:message code="top.faq" text="Faqs"/></span></a></li>
        <li><a href='CitizenContactUs.html'><span><spring:message code="eip.citizen.footer.contactUs" text="Contact Us" /></span></a></li>
        <li><a href="sitemap.html" title="<spring:message code="sitemap" text="Sitemap"/>"><spring:message code="sitemap" text="Sitemap"/></a></li>
    </ul>
     <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">  
			   <h3><spring:message code="portal.head" text="Faqs"/></h3>
			   
     			
    			<form:form name="selectMunicipalForm" id="selectMunicipalForm" action="" class="list" method="get">
    			<div class="row">
    			
    			<div class="col-sm-10  col-xs-9">
    				<label class="hidden" for="selectedOrg">Select Department</label>
				<select name="orgId" id="selectedOrg" data-content="" class="form-control chosen-select-no-results">
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
     </c:if> 
  

     <h3><spring:message code="Login" text="Login"/></h3>
    <ul>
      <li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
      <li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="" text="Portal Admininstrator Login"/></span></a></li>
      <li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="" text="Department Employee Login"/></span></a></li>
    </ul>
    <h3><spring:message code="portal.dashboard" text="Dashboard"/></h3>
    <ul>
    <li><a href='DashBoard.html' title="<spring:message code="portal.dashboard" text="Dashboard"/>"><span><spring:message code="portal.dashboard" text="Dashboard"/></span></a></li>
    </ul>
 	</div> 
	</div>
  <div class="col-md-9 col-xs-12">
   	 <div class="columns-multilevel">
  		<ul class="section-nav">
		<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
		<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
		</ul>
  	</div> 
	</div>
   	</div>
   	</div>
   	</div>
  </div>

<%-- <c:if test="${headerFlag ne 'N' || headerFlag eq ''}"> --%>
  <div class="header-body hidden-lg hidden-md">
    <div class="container clearfix">
      <button class="btn header-btn-collapse-nav" data-toggle="collapse" data-target=".header-nav-main"> <strong class="fa fa-bars"></strong><span class="hide">Menu Collapse</span> </button>
      <div class="row">
        <div class="col-md-4 center-logo-text">
          <c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
            <c:set var="parts" value="${fn:split(logo, '*')}" />
            <c:if test="${parts[1] eq '1'}"> <a href="CitizenHome.html"><img src="${parts[0]}" alt="<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if><c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>"></a> </c:if>
          </c:forEach>
          <h1>
            <c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
            <c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
          </h1>
        </div>
        <div class="col-md-8 hidden-lg hidden-md">
          <div class="header-nav-main header-nav-main-effect-1 header-nav-main-sub-effect-1 collapse">
            <nav>
              <ul class="nav nav-pills" id="tabmenuhover">
                <li><a href='CitizenHome.html'><span><spring:message code="top.home" text="Home"/></span></a></li>
                <li class="dropdown hidden-lg" id='nav'><a class="dropdown-toggle" href="javascript:void(0);"><spring:message code="left.left.quicklinks" text="quicklinks"/></a>
						<ul class="dropdown-menu">
							<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
							<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
						</ul>
					</li>
                <li><a href='CitizenAboutUs.html'><span><spring:message code="top.aboutus" text="About Us"/></span></a></li>
                <li><a href='CitizenFAQ.html?getFAQ'><span><spring:message code="top.faq" text="Faqs"/></span></a></li>
                <li><a href='CitizenContactUs.html'><span><spring:message code="eip.citizen.footer.contactUs" text="Contact Us" /></span></a></li>
                
                <li class="dropdown"> <a class="dropdown-toggle" href="javascript:void(0);"> <spring:message code="Login" text="Login"/></a>
					<ul class="dropdown-menu">
                    <li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
                    <li><a href='javascript:void(0);' onclick="getAgencyLoginForm()"><span><spring:message code="AgencyLogin" text="Agency Login"/></span></a></li>
                    <li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="portal.admin" text="Portal Admin Login"/></span></a></li>
                    <%-- <li><a href='javascript:void(0);' onclick="activityLogin()"><span><spring:message code="portal.activity" text="Activity"/></span></a></li> --%>
                    <li><a href='javascript:void(0);'><span><spring:message code="portal.report" text="Departmental Report"/></span></a></li>
                    <li><a href='javascript:void(0);'><span><spring:message code="portal.dashboard" text="CM Dashboard"/></span></a></li>
                  </ul>
                </li>

              </ul>
            </nav>
          </div>
        </div>
      </div>
    </div>
  </div>
<%--   </c:if> --%>
</header>



<%-- <c:if test="${command.userSession.socialMediaMap.size() gt 0}">
  <div class="social">
    <ul>
      <c:forEach var="media" items="${command.userSession.socialMediaMap}" >
        <li><a href="${media.value}" target="new"><i class="fa fa-${media.key}"></i>  ${media.key}</a></li>
      </c:forEach>
    </ul>
  </div>
</c:if>--%>