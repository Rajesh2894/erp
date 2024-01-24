<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
.content-page {
    position: relative;
    top: 45px;
}
.content-page .content{
margin-bottom: 20px;
}
.navbar-toggle{
padding:0px;
}
.brand img{
	height:45px;
}
@media (max-width: 767px) {
.brand img{
	height:30px;
}
}
@media (min-width: 767px) {
.content-page {
    position: relative;
    top: 0px;
}
}
</style>
<script  src="js/eip/citizen/citizen-top-header.js"></script> 
<!-- <script  src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script> -->
<script>
$(function(){$(".navigation a[href='javascript:void(0);']").removeAttr('href');})	
/* function googleTranslateElementInit() {
  new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');
  $("script").removeAttr("type").removeAttr("charset");
} */
</script>

<input type="hidden" id="Selectsmfid" name="Selectsmfid" />
<script>
$(document).ready(function(){
	  $("#mobile.nav .parent>a").on("click", function(e){
	    if($(this).parent().has("ul")) {
	      e.preventDefault();
	    }
	    
	    if(!$(this).hasClass("open")) {
	      // hide any open menus and remove all other classes
	      $("#mobile.nav ul").slideUp(350);
	      $("#mobile.nav .parent>a").removeClass("open");
	      
	      // open our new menu and add the open class
	      $(this).next("ul").slideDown(350);
	      $(this).addClass("open");
	    }
	    
	    else if($(this).hasClass("open")) {
	      $(this).removeClass("open");
	      $(this).next("ul").slideUp(350);
	    }
	   /*  $("#mobile.nav .parent .blink a").removeClass('open'); */
	  
	  });
	});

</script>

 

<header class="site-header site-header-inner">
<nav class="navbar navbar-default">
	<div class="top-line navbar-fixed-top">
		<div class="container">
			<div class="row">
				
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 content-right text-right">
					<div class="last">
						<%-- <span class="hidden-xs"><a href="#MainContent" title="<spring:message code="Skip" text="Skip to Main Content"/>"><i class="fa fa-arrow-circle-o-down" aria-hidden="true"></i> <spring:message code="Skip" text="Skip to Main Content"/></a></span>
						 --%>
						<span class="hidden-xs">
							<a href="CitizenHome.html" title="Go back to <spring:message code="top.home" text="Home" />">
								<i class="fa fa-home" aria-hidden="true"></i> <%-- <spring:message code="top.home" text="Home" /> --%>
							</a>
						</span>	
					   
				        <span class="hidden-xs">
					       	<a href="AccessibilityLanding.html" title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options">
					        	<i class="fa fa-adjust" aria-hidden="true">
					        		<span class="hidden"><spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options"></span>
					        	</i>
					       	</a>
				        </span>          
				        <span class="hidden-xs hidden-sm"><a  id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce">A -</a></span>
				        <span class="hidden-xs hidden-sm"><a  id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal">A</a></span>
				        <span class="hidden-xs hidden-sm"><a  id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase">A +</a></span>
				        &nbsp;
				        <span class="hidden-xs">
				           <a href="sitemap.html" aria-label='<spring:message code="sitemap" text="Sitemap"/>' title="
				           <spring:message code="sitemap" text="Sitemap"/>
				           "> <i class="fa fa-sitemap" data-toggle="tooltip" data-placement="bottom"></i></a>
				        </span>
						<span class="header-search hidden-xs">
						  <form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
							  <div class="input-group">
								  <label class="hide" for="search_input">Search Content</label> 
								  <input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required placeholder="<spring:message code="eip.searchhere" text="search"/>">
								  <span class="input-group-btn">
								  	<button class="btn btn-default" type="Submit" id="searchButton">
									  	<strong class="fa fa-search"></strong>
									  	<span class="hide">searchButton</span>
								  	</button>
								  </span>
							 </div>
						 </form:form>
						</span>
						<c:if test="${userSession.languageId eq 1}">
				          <span>
				             <a onclick="changeLanguage('?locale&lang=reg');" href="javascript:void(0);" title="
				             <spring:message code="header.reg" text="header.reg"/>
				             "><i class="fa fa-globe"></i> 
				             <spring:message code="header.reg" text="header.reg" />
				             </a>
				          </span>
				        </c:if>
				        <c:if test="${userSession.languageId eq 2}">
				           <span>
				              <a onclick="changeLanguage('?locale&lang=en');" href="javascript:void(0);" title="
				              <spring:message code="header.eng" text="header.eng"/>
				              "><i class="fa fa-globe"></i> 
				              <spring:message code="header.eng" text="header.eng" />
				              </a>
				           </span>
				        </c:if>
						
						
			 		</div>
				</div>
				
			
		</div>
	</div>
	
	</div>
	
	<div class="header-inner">
		<div class="container">
			<div class="row">
			 	<div class="col-md-9 col-sm-9 col-lg-9 col-xs-12"> 
					<div class="brand wow fadeInLeft animated animated" data-wow-duration="1.5s" data-wow-offset="10" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;">
				
	 					<a href="CitizenHome.html">
	 					<c:forEach items="${userSession.logoImagesList}" var="logo"
						varStatus="status">
						<c:set var="parts" value="${fn:split(logo, '*')}" />
						<c:if test="${parts[1] eq '1'}">
							<img src="./${parts[0]}" class="logo-img" alt="Organisation Logo"
							<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
						    <c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> 
						    ">
						</c:if>
						</c:forEach>
						<span class="website-name">
							<h3>${userSession.organisation.ONlsOrgnameMar}</h3>
							<h1>${userSession.organisation.ONlsOrgname}</h1>
						</span>			
						<%-- <h1>
							<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
							<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
		 				</h1> --%>
		 				</a>
					</div>
				</div>	
				<div class="col-md-3 col-sm-3 col-lg-3 hidden-xs">
					<div class="brand wow fadeInRight animated animated pull-right" data-wow-duration="1.5s" data-wow-offset="10" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;">
						<img src="assets/img/national_emblem.png" class="pull-right nat-emblem" alt="National emblem">
					</div>
				</div>
			</div>
	 	</div>	
				
				
<div class="border-red"></div>
		<div class="header-3-after-login">
			<div class="container">
				<div class="row">
					<nav class="menu-header-menu-container">
						<ul class="main-navigation sm sm-blue" id="main-menu">
							<c:if test="${userSession.employee.emploginname ne 'NOUSER'}" var="user">
								<li class="btn-group">
									<a class="parent" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<%-- <span class="glyphicon glyphicon-user"></span> --%>
										<span><spring:message code="eip.citizen.home.welcome" text="Welcome"/>, ${userSession.employee.empname}&nbsp;${userSession.employee.empLName}</span>
										<span class="glyphicon glyphicon-chevron-down"></span>	
									</a>
									<ul class="dropdown-menu">
										<li>
											<a href="CitizenHome.html?EditUserProfile" id="editUserProfile">
												<i class="fa fa-pencil text-orange"></i><spring:message code="edit.profile" text=" Edit Profile"/>
											</a>
										</li>
										<li>
											<a href="LogOut.html"><i class="fa fa-power-off text-red"></i><spring:message code="sign.out" text="Sign Out"/> </a>
										</li>
									</ul>
								</li>
							</c:if>

							
						</ul>
					</nav>
				</div>
			</div>
		</div>
	</header>

<header id="top-header" class="page-heading"><div class="container"></div></header>







<form:form id="postMethodForm" method="POST" class="form"></form:form>