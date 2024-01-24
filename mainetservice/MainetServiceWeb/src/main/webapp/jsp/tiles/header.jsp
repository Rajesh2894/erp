<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String getURL = request.getServletPath().toString();
	System.out.println(getURL);
%>

<link rel="stylesheet" type="text/css" href="assets/libs/Jquery-Calculator/css/jquery.calculator.css" />
<link href="assets/libs/virtual-keyboard/css/keyboard.css" rel="stylesheet" type="text/css"/>
<script src="assets/libs/Jquery-Calculator/js/jquery.plugin.min.js"></script>
<script src="assets/libs/Jquery-Calculator/js/jquery.calculator.min.js"></script>
<script src="assets/libs/virtual-keyboard/js/keyboard.js"></script>

<script>
	
	function changeLanguage(url) {
		var array = document.URL.split('/');
		array = array[array.length - 1];
		url = url + '&url=' + array;
		window.location.href = url;
	}	
	
	function getPortalDeptLogin() {

		var responseUrl = __doAjaxRequest('Home.html?portalDeptLogin', 'get',
				'', false, 'json');
		window.location.href = responseUrl;
	}
	
	
	
</script>

<%-- <style>
/* .dropbtn {
  background-color: #4CAF50;
  color: white;
  padding: 16px;
  font-size: 16px;
  border: none;
} */

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  min-width: 35px;
  z-index: 1;
}
/*.theme{min-width: 37px !important;}*/
.dropdown-content>a {
  color: black;
  padding:5px 10px 5px 0px  !important;
  text-decoration: none;
  display: block;
  background:#343838;
}

.dropdown:hover .dropdown-content {display: block;}
div.is-calculator, span.is-calculator{    padding: 0px;
    margin: 0px;
    position: absolute;
  	left: -205px;
    top:-125px;
    width: auto;
    z-index: 1100;}
#drag{z-index: 55; position: absolute !important;left: 1310px;top: 175px;}
.calculator-popup{z-index: 55 !important;}
</style>
<c:if test="${!empty userSession.employee.emploginname}">
<style>
.header-top li a{
    font-size: 0.7em;
}
</style>
</c:if> --%>

<div class="topbar">
	<div class="topbar-left hidden" >
		<div class="logo">
			<a title="Organisation Logo" href="AdminHome.html">
				<img src="${userSession.orgLogoPath}" alt="Organisation Logo" class="not-logged-avatar">
			</a>
		</div>
		<button class="button-menu-mobile open-left " title="Menu Mobile"><strong class="fa fa-bars fa-lg"><span class="hide">Menu Mobile</span></strong></button>
	</div>
	<!-- Button mobile view to collapse sidebar menu -->
	<div class="navbar navbar-default">
		<c:if test="${!empty userSession.employee.emploginname}">
			<c:set var="display" value="style='font-size:0.89em'" scope="page"/>
		</c:if>
        <ul class="header-top" ${display}>
         <%-- <li class=""> <a onClick="getPortalDeptLogin();" title="<spring:message code="portal.dept.adminlogin" text="Return To Portal"/>"><strong class="fa fa-globe"> &nbsp;<spring:message code="portal.dept.adminlogin" text="Portal Dept Login"/></strong></a></li> --%>
        <li class=""><a href="${userSession.employee.designation.dsgshortname eq 'OPR' ? 'OperatorDashboardView.html' : 'AdminHome.html' }" title="Home"  data-placement="top"><strong class="fa fa-home fa-lg"> <span class="hide">Home</span></strong></a></li>
       <!--- Defect #113280 --> 
		<c:if test="${empty userSession.employee.emploginname}">
        <li class="hidden-xs"><div class="dropdown" id="dropdowngrp">
  			</span><a href="javascript:void(0);" class="dropbtn" data-placement="bottom" title="Select Theme">
			  <c:choose><c:when test="${cookie['accessibilityCol'].value =='G'}"><i class="fa fa-circle fa-lg green"></i></c:when>
			  <c:when test="${cookie['accessibilityCol'].value =='O'}"><i class="fa fa-circle fa-lg orange"></i></c:when>
			  <c:when test="${cookie['accessibilityCol'].value =='B'}"><i class="fa fa-circle fa-lg blue"></i></c:when>
			  <c:otherwise><i class="fa fa-circle fa-lg green"></i></c:otherwise></c:choose><span class="hide">Select Theme</span></a>
  <div class="dropdown-content theme">
  	<a href="javascript:void(0);" onclick="setcontrast('G')"  data-placement="bottom" title="Select Green Theme"> <i class="fa fa-circle fa-lg green"></i><span class="hide">Green</span></a>
    <a href="javascript:void(0);" onclick="setcontrast('B')"  data-placement="bottom" title="Select Blue Theme"> <i class="fa fa-circle fa-lg blue"></i><span class="hide">Blue</span></a>    
    <a href="javascript:void(0);" onclick="setcontrast('O')"  data-placement="bottom" title="Select Maroon Theme"> <i class="fa fa-circle fa-lg orange"></i><span class="hide">Orange</span></a>
  </div>
</div></li> 
  </c:if>    

        <!-- <li class=""><a href="AdminHome.html" title="Home"  data-placement="top"><strong class="fa fa-home fa-lg"> <span class="hide">Home</span></strong></a></li> -->
			<c:if test="${!empty userSession.employee.emploginname}" var="user">
			<!-- <li class="hidden-xs"><a href="Accessibility.html" data-placement="bottom" title="Accessibility Options"> <i class="fa fa-adjust fa-lg"></i><span class="hide">Accessibility</span></a></li> -->
			
			<%--  <li class="hidden-xs"><div class="dropdown" id="dropdowngrp" style="display: none;">
  			</span><a href="javascript:void(0);" class="dropbtn" data-placement="bottom" title="Select Theme">
			  <c:choose><c:when test="${cookie['accessibilityCol'].value =='G'}"><i class="fa fa-circle fa-lg green"></i></c:when>
			  <c:when test="${cookie['accessibilityCol'].value =='O'}"><i class="fa fa-circle fa-lg orange"></i></c:when>
			  <c:when test="${cookie['accessibilityCol'].value =='B'}"><i class="fa fa-circle fa-lg blue"></i></c:when>
			  <c:otherwise><i class="fa fa-circle fa-lg green"></i></c:otherwise></c:choose><span class="hide">Select Theme</span></a>
  <div class="dropdown-content theme">
  	<a href="javascript:void(0);" onclick="setcontrast('G')"  data-placement="bottom" title="Select Green Theme"> <i class="fa fa-circle fa-lg green"></i><span class="hide">Green</span></a>
    <a href="javascript:void(0);" onclick="setcontrast('B')"  data-placement="bottom" title="Select Blue Theme"> <i class="fa fa-circle fa-lg blue"></i><span class="hide">Blue</span></a>    
    <a href="javascript:void(0);" onclick="setcontrast('O')"  data-placement="bottom" title="Select Maroon Theme"> <i class="fa fa-circle fa-lg orange"></i><span class="hide">Orange</span></a>
  </div>
</div></li>  --%>

<li class="hidden-xs"><div class="dropdown">
  <a href="javascript:void(0);" id="decfont" class="dropbtn"  data-placement="bottom" title="Font Reduce">A-</a>
  <div class="dropdown-content">
    <a href="javascript:void(0);" id="norfont" data-placement="bottom" title="Font Normal">A &nbsp;</a>
  <a href="javascript:void(0);" id="incfont" data-placement="bottom" title="Font Increase">A+</a>
  </div>
</div></li></c:if>

			<!-- <li class="hidden-xs"><a href="javascript:void(0);" id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce">A -</a></li>
			<li class="hidden-xs"><a href="javascript:void(0);" id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal">A</a></li>
			<li class="hidden-xs"><a href="javascript:void(0);" id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase">A +</a></li> -->
			<%-- <li class="hidden-smx"><a href="javascript:void(0);" onclick="javascript:toggle_fullscreen()" data-placement="bottom" title="Full Screen"><strong class="fa fa-expand"><span class="hide">Full Screen</span></strong></a></li>
			<c:if test="${!empty userSession.employee.emploginname}">
			<li class="hidden-xs"><a href="sitemap.html" title="<spring:message code="sitemap" text="Sitemap"/>"><strong class="fa fa-sitemap"></strong><span class="hide"> <spring:message code="sitemap" text="Sitemap"/></span></a></li>
			</c:if>
			<li class="hidden-smx"><a href="javascript:void(0);" onclick="" id="hideshow" data-placement="bottom" title="Calculator"><strong class="fa fa-calculator"><span class="hide">Calculator</span></strong></a></li> --%>
			<li class="virtual-keyboard">
        		<a href="javascript:void(0);" class="virtualKeyboard vk-off" title="<spring:message code="service.virtual.keyboard" text="Virtual Keyboard"/>">
					<i class="fa fa-keyboard-o" aria-hidden="true"></i>
					<span class="hidden-xs hidden-sm"><spring:message code="service.virtual.keyboard" text="Virtual Keyboard"/></span>
				</a>
        	</li>
			<c:if test="${!empty userSession.employee.emploginname}">
			<li class="txt_li"><a href="javascript:void(0);" onclick=""  data-placement="bottom" title="${userSession.employee.empname}&nbsp;${userSession.employee.emplname}"><strong class="fa fa-user">&nbsp;</strong><span title="${userSession.employee.empname}&nbsp;${userSession.employee.emplname}"><c:if test="${!empty userSession.employee.empname}">&nbsp;&nbsp;${userSession.employee.empname}</c:if><c:if test="${!empty userSession.employee.empmname}">&nbsp;${userSession.employee.empmname}</c:if><c:if test="${!empty userSession.employee.emplname}">&nbsp;${userSession.employee.emplname}</c:if></span></a></li>
        	</c:if>
<!--         	<li class="hidden-xs"><a href="javascript:void(0);" onclick="" data-placement="bottom" title="&nbsp;"><strong class="fa fa-globe">&nbsp;<span title="&nbsp;">Hindi</span></strong></a></li>
 -->        	
 		<c:if test="${empty userSession.employee.emploginname}" var="user">
			
 			<c:if test="${userSession.languageId eq 1}">
            <li class=""> <a onClick="changeLanguage('?locale&lang=reg');" title="<spring:message code="header.reg" text="header.reg"/>"><strong class="fa fa-globe"> &nbsp;<spring:message code="header.reg" text="header.reg"/></strong></a></li>
          </c:if>
          <c:if test="${userSession.languageId eq 2}">
            <li class=""> <a onClick="changeLanguage('?locale&lang=en');" title="<spring:message code="header.eng" text="header.eng"/>"><strong class="fa fa-globe"> &nbsp;<spring:message code="header.eng" text="header.eng"/></strong></a></li>
           </c:if> 
           </c:if>
        	
        	
        	<li class="txt_li logout"><a href="LogOut.html" onclick=""  data-placement="bottom" title="Logout"><strong class="fa fa-power-off"></strong><span title="" >&nbsp;Logout</span></a></li>
        	<!-- Header More Settings starts -->
        	<li class="dropdown more-settings">
        		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-chevron-down" aria-hidden="true"></i></a>
        		<ul class="dropdown-menu">
				    <li>
				    	<a onClick="getPortalDeptLogin();" title="<spring:message code="portal.dept.adminlogin" text="Return To Portal"/>">
				    		<strong class="fa fa-globe"> &nbsp;<spring:message code="portal.dept.adminlogin" text="Portal Dept Login"/></strong>
				    	</a>
				    </li>
				    <c:if test="${!empty userSession.employee.emploginname}" var="user">
					    <li>
					    	<a href="Accessibility.html" data-placement="bottom" title="Accessibility Options">
					    		<i class="fa fa-adjust fa-lg"></i><span>Accessibility</span>
					    	</a>
					    </li>
				    </c:if>
				    <li class="hidden-smx">
				    	<a href="javascript:void(0);" onclick="javascript:toggle_fullscreen()" data-placement="bottom" title="<spring:message code="dropdown.fullscreen" text="Full Screen"/>">
				    		<strong class="fa fa-expand"><span><spring:message code="dropdown.fullscreen" text="Full Screen"/></strong></span></strong>
				    	</a>
				    </li>
					<c:if test="${!empty userSession.employee.emploginname}">
						<li class="hidden-xs">
							<a href="sitemap.html" title="<spring:message code="sitemap" text="Sitemap"/>">
								<strong class="fa fa-sitemap"></strong><span> <spring:message code="sitemap" text="Sitemap"/></span>
							</a>
						</li>
					</c:if>
					<li class="hidden-smx">
						<a href="javascript:void(0);" onclick="" id="hideshow" data-placement="bottom" title="<spring:message code="dropdown.calculator" text="Calculator"/>">
							<strong class="fa fa-calculator"><span><spring:message code="dropdown.calculator" text="Calculator"/></span></strong>
						</a>
					</li>
				  </ul>
        	</li>
        	<!-- Header More Settings ends -->
        </ul>
       
      
        
        
         <div id="drag"><span  id="basicCalculator" style="display:none;" ></span></div>
		<div class="container">
			<div class="navbar-collapse2">
				<ul class="nav navbar-nav">
				<c:if test="${!empty userSession.employee.emploginname}">
				<li class="hidden-sm hidden-md hidden-lg">	
				<a class="text-white" onclick="openMenu();"><i class="fa fa-bars" aria-hidden="true"></i></a></li></c:if>
				<li><img src="${userSession.orgLogoPath}" alt="Organisation Logo" class="not-logged"/></li>
				<li class="center-logo-text"><h1 <c:if test="${empty userSession.employee.emploginname}">style='font-size:1em!important'</c:if>>${userSession.ULBName.lookUpDesc}</h1></li>				
				 <!-- <form:form role="search" class="navbar-form" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
 				 <label for="search_input" class="hide">Search Input</label>
				 <input type="text" class="form-control col-sm-4" name="searchWord" tabindex="-1" id="search_input" autocomplete="off" required placeholder="Search here" />
				 <button type="button" id="searchButton" class="btn search-button" value=""><strong class="fa fa-search"><span class="hide">Search Here..</span></strong></button>
				 </form:form> -->				 
				</ul>
			</div>
		</div>	
		
		
	</div>
</div>
<div id="sdiv"></div><div class="sloading"><img src="css/images/loader.gif" alt="loading" /></div>
<script src="js/mainet/sessionExpired.js"></script>