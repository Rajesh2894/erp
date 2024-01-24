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
<script  src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
<script>
$(function(){$(".navigation a[href='javascript:void(0);']").removeAttr('href');})	
function googleTranslateElementInit() {
  new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');
  $("script").removeAttr("type").removeAttr("charset");
}
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
				<%-- <div class="col-md-2 col-sm-3 col-xs-3 hidden-xs">
		       	<p><span><a id="menu-toggle" class="toggle" ><i class="fa fa-align-justify" aria-hidden="true"></i> <spring:message code="Menu" text="MENU"/></a></span></p>        	
		      	</div>
		      	<div class="col-md-2 col-sm-3 col-xs-3 hidden-lg hidden-md hidden-sm">
		      		<button type="button" class="navbar-toggle collapsed " data-toggle="collapse" data-target="#navbarlogin" aria-expanded="false" aria-controls="navbar">
		           <span class="sr-only">Toggle navigation</span>
		           <span class="icon-bar"></span>
		           <span class="icon-bar"></span>
		           <span class="icon-bar"></span>
		         </button></div> --%>
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 content-right text-right">
					<div class="last">
						<%-- <span class="hidden-xs"><a href="#MainContent" title="<spring:message code="Skip" text="Skip to Main Content"/>"><i class="fa fa-arrow-circle-o-down" aria-hidden="true"></i> <spring:message code="Skip" text="Skip to Main Content"/></a></span>
						 --%>
						<span class="hidden-xs">
							<a href="CitizenHome.html" title="Go back to <spring:message code="top.home" text="Home" />">
								<i class="fa fa-home" aria-hidden="true"></i> <%-- <spring:message code="top.home" text="Home" /> --%>
							</a>
						</span>	
					    <!-- <span class="hidden-xs"><a  title="Blue Theme" onclick="setcontrast('B')"> <i class="c" style="background: #1764af;" data-toggle="tooltip" data-placement="bottom" title="Select Blue Theme"></i><span class="hide">Blue</span> </a></span>
					    <span class="hidden-xs"><a  title="Orange Theme" onclick="setcontrast('O')"><i class="c" style="background:#b91616;" data-toggle="tooltip" data-placement="bottom" title="Select Orange Theme"></i><span class="hide">Orange</span> </a></span>
				        <span class="hidden-xs"><a  title="Green Theme" onclick="setcontrast('G')"> <i class="c"  style="background:#00aa55;" data-toggle="tooltip" data-placement="bottom" title="Select Green Theme"></i><span class="hide">Green</span> </a></span> -->
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
						
						<%-- <span><a href="sitemap.html" title="<spring:message code="sitemap" text="Sitemap"/>"><strong class="fa fa-sitemap"></strong> <spring:message code="sitemap" text="Sitemap"/></a></span> --%>
						<!-- <span id="google_translate_element" class="hidden-xs hidden-sm hidden-md" style="display:inline-block;"></span> -->
						<span>
							<c:if test="${userSession.employee.emploginname ne 'NOUSER'}" var="user">
								<div class="btn-group">
									<button class="btn-link btn-xs dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									<!-- <span class="glyphicon glyphicon-user"></span> -->
				                    <strong><spring:message code="eip.citizen.home.welcome" text="Welcome"/>, ${userSession.employee.empname}&nbsp;${userSession.employee.empLName}</strong>
				                    <span class="glyphicon glyphicon-chevron-down"></span>
										
									</button>
									
									<ul class="dropdown-menu">
										<li><a href="CitizenHome.html?EditUserProfile" id="editUserProfile"><i class="fa fa-pencil text-orange"></i><spring:message code="edit.profile" text=" Edit Profile"/></a>
										</li>
										<li><a href="LogOut.html"><i class="fa fa-power-off text-red"></i><spring:message code="sign.out" text="Sign Out"/> </a></li>
									</ul>
								</div>
							</c:if>
						</span>
			 		</div>
				</div>
				
			<%-- <div class="navigation" class="hidden-xs">
      <div class="container">
        <div class="row row-eq-height">
          <div class="col-md-2">
            <div class="columns">
  	<a href="CitizenHome.html"><spring:message code="top.home" text="Home"/></a>
        <ul>
			<li><a href='CitizenHome.html'><span><spring:message code="top.home"></spring:message></span></a></li>
			<li><a href='CitizenAboutUs.html' title="ULB"><span><spring:message code="top.aboutus"></spring:message></span></a></li>
			<li><a href='CitizenFAQ.html?getFAQ'><span><spring:message code="top.faq"></spring:message></span></a></li>
			<li><a href='CitizenContactUs.html' title="ULB2"><span><spring:message code="eip.citizen.footer.contactUs" /></span></a></li>
    	</ul>
    	
 		<h3 class="dept-user">${userSession.employee.empname}&nbsp;${userSession.employee.empLName}</h3>
 		<c:if test="${userSession.employee.emploginname ne 'NOUSER'}" var="user">
 		<ul>
 			<li><a href="CitizenHome.html?EditUserProfile" id="editUserProfile"><i class="fa fa-pencil"></i><spring:message code="edit.profile" text=" Edit Profile"/></a></li>
			<li><a href="LogOut.html"><i class="fa fa-power-off text-red"></i><spring:message code="sign.out" text="Sign Out"/> </a></li>
			</ul>
 		</c:if>
     	
    	
    	
    	
 
 	</div> 
	</div>
 <div class="col-md-10">
   	 <div class="columns-multilevel">
  		<ul class="section-nav">
						<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
							<li class="parent" id="${masters.entitle.smfid}"><a class="" href="${masters.entitle.smfaction}" title="${masters.entitle.smfname}">${masters.entitle.smfname}</a>
								<ul class="blink">
									<c:forEach items="${menuRoleEntitlement.childList}" var="data">
										<c:if test="${masters.entitle.smfid eq  data.parentId}">
											<c:set var="action0" value="${data.entitle.smfaction}" />
											<li id="${data.entitle.smfid}"><a   <c:choose><c:when test="${fn:containsIgnoreCase(action0 , '.html')}"> onclick="openRelatedForm('${action0}','this');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}');"</c:when><c:otherwise>href="${action0}"</c:otherwise></c:choose>>${data.entitle.smfname}
											</a>
												<ul class="">
													<c:forEach items="${menuRoleEntitlement.childList}" var="data1">
														<c:if test="${data.entitle.smfid eq  data1.parentId}">
															<c:set var="action1" value="${data1.entitle.smfaction}" />
															<li id="${data1.entitle.smfid}"><a  <c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}"> onclick="openRelatedForm('${action1}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}');"</c:when><c:otherwise>href="${action1}"</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
																<ul class="">
																	<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
																		<c:if test="${data1.entitle.smfid eq  data2.parentId}">
																			<c:set var="action2" value="${data2.entitle.smfaction}" />
																			<li id="${data2.entitle.smfid}"><a  <c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}"> onclick="openRelatedForm('${action2}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}');"</c:when><c:otherwise>href="${action2}"</c:otherwise></c:choose>>${data2.entitle.smfname}</a>
																				<ul>
																					<c:forEach items="${menuRoleEntitlement.childList}" var="data3">
																						<c:if test="${data2.entitle.smfid eq  data3.parentId}">
																							<c:set var="action3" value="${data3.entitle.smfaction}" />
																							<li id="${data3.entitle.smfid}"><a  
																								<c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}"> onclick="openRelatedForm('${action3}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}+${data3.entitle.smfname}');"</c:when><c:otherwise>href="${action3}"</c:otherwise></c:choose>>${data3.entitle.smfname}</a></li>
																						</c:if>
																					</c:forEach>
																				</ul></li>
																		</c:if>
																	</c:forEach>
																</ul></li>
														</c:if>
													</c:forEach>
												</ul></li>
										</c:if>
									</c:forEach>
								</ul></li>
						</c:forEach>
						</ul>
  	</div> 
	</div>
 	<a href="javascript:void(0)" title="Close Menu" class="close_icon"><i class="fa fa-times-circle" aria-hidden="true"></i><span><spring:message code="bt.close" text="Close"/></span></a>
  	</div>
   	</div>
   	</div> --%>
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
				
				
				
 <div class="container" style="position:fixed;top: 50px;width:100%;" >
		
    <nav class="navbar navbar-default hidden-lg hidden-md hidden-sm">
      <div class="container hidden-lg hidden-md hidden-sm">
			<div id="navbarlogin" class="navbar-collapse collapse">
          	<ul id="mobile" class="nav navbar-nav">
				<%--<div id="nav-scrool-1">
						
					<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
							<li class="dropdown parent" id="${masters.entitle.smfid}"><a class="dropdown-toggle" href="${masters.entitle.smfaction}" title="${masters.entitle.smfname}" onclick="dodajAktywne(this)">${masters.entitle.smfname}</a>
								<ul class="">
									<c:forEach items="${menuRoleEntitlement.childList}" var="data">
										<c:if test="${masters.entitle.smfid eq  data.parentId}">
											<c:set var="action0" value="${data.entitle.smfaction}" />
											<li id="${data.entitle.smfid}"><a   <c:choose><c:when test="${fn:containsIgnoreCase(action0 , '.html')}"> onclick="openRelatedForm('${action0}','this');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}');"</c:when><c:otherwise>href="${action0}"</c:otherwise></c:choose>>${data.entitle.smfname}
											</a>
												<ul class="">
													<c:forEach items="${menuRoleEntitlement.childList}" var="data1">
														<c:if test="${data.entitle.smfid eq  data1.parentId}">
															<c:set var="action1" value="${data1.entitle.smfaction}" />
															<li id="${data1.entitle.smfid}"><a  <c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}">  onclick="openRelatedForm('${action1}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}');"</c:when><c:otherwise>href="${action1}"</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
																<ul class="">
																	<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
																		<c:if test="${data1.entitle.smfid eq  data2.parentId}">
																			<c:set var="action2" value="${data2.entitle.smfaction}" />
																			<li id="${data2.entitle.smfid}"><a  <c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}"> onclick="openRelatedForm('${action2}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}');"</c:when><c:otherwise>href="${action2}"</c:otherwise></c:choose>>${data2.entitle.smfname}</a>
																				<ul>
																					<c:forEach items="${menuRoleEntitlement.childList}" var="data3">
																						<c:if test="${data2.entitle.smfid eq  data3.parentId}">
																							<c:set var="action3" value="${data3.entitle.smfaction}" />
																							<li id="${data3.entitle.smfid}"><a  
																								<c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}"> onclick="openRelatedForm('${action3}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}+${data3.entitle.smfname}');"</c:when><c:otherwise>href="${action3}"</c:otherwise></c:choose>>${data3.entitle.smfname}</a></li>
																						</c:if>
																					</c:forEach>
																				</ul></li>
																		</c:if>
																	</c:forEach>
																</ul></li>
														</c:if>
													</c:forEach>
												</ul></li> 
										</c:if>
									</c:forEach>
								</ul></li>
						</c:forEach>--%>
								
								
					  <%--<c:if test="${userSession.employee.emploginname ne 'NOUSER'}" var="user">
 		
 						<li><a href="CitizenHome.html?EditUserProfile" id="meditUserProfile"><i class="fa fa-pencil"></i> Edit Profile</a></li>
						<li><a href="LogOut.html"><i class="fa fa-power-off text-red"></i> Sign Out</a></li>
			
 					</c:if> 
 				</div>--%>
		  	</ul>
			</div>
		</div>
	</nav>
 </div>
</div>

	</nav>
</header>

<header id="top-header" class="page-heading"><div class="container"></div></header>







<form:form id="postMethodForm" method="POST" class="form"></form:form>