<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/eip/citizen/citizen-top-header.js"></script> 
<script src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
<script>
function googleTranslateElementInit() {
  new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');
}
</script>

<input type="hidden" id="Selectsmfid" name="" />


 

<header class="site-header">
<nav class="navbar navbar-default">
	<div class="top-line navbar-fixed-top">
			<div class="container-fluid">
			<div class="col-md-2 col-sm-3 col-xs-3 hidden-xs hidden-sm">
        	<p><span><a id="menu-toggle" href="#" class="toggle" ><i class="fa fa-align-justify" aria-hidden="true"></i> <spring:message code="Menu" text="MENU"/></a></span></p>
       		
       		
        		
       		
       		
       		</div>
			<div class="col-md-10 col-sm-12 col-xs-12 text-right">
			<div class="last">
          	<span class="hidden-xs"><a href="#MainContent" title="<spring:message code="Skip" text="Skip to Main Content"/>"><i class="fa fa-arrow-circle-o-down" aria-hidden="true"></i> <spring:message code="Skip" text="Skip to Main Content"/></a></span>

 						
		  <span class="hidden-xs"><a href="#" title="Blue Theme" onclick="setcontrast('B')"> <i class="c" style="background: #1764af;" data-toggle="tooltip" data-placement="bottom" title="Select Blue Theme"></i><span class="hide">Blue</span> </a></span>
		  <span class="hidden-xs"><a href="#" title="Orange Theme" onclick="setcontrast('O')"><i class="c" style="background:#ffa500;" data-toggle="tooltip" data-placement="bottom" title="Select Orange Theme"></i><span class="hide">Orange</span> </a></span>
          <span class="hidden-xs"><a href="#" title="Green Theme" onclick="setcontrast('G')"> <i class="c"  style="background:#00aa55;" data-toggle="tooltip" data-placement="bottom" title="Select Green Theme"></i><span class="hide">Green</span> </a></span>
          <span class="hidden-xs"><a href="Accessibility.html" style="font-size: 17px;top: 1px;position: relative;"title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options"><i class="fa fa-adjust fa-lg" aria-hidden="true"><span class="hidden"><spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options"></span></i> </a></span>          
          <span class="hidden-xs hidden-sm"><a href="#" id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce">A -</a></span>
          <span class="hidden-xs hidden-sm"><a href="#" id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal">A</a></span>
          <span class="hidden-xs hidden-sm"><a href="#" id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase">A +</a></span>
          &nbsp;<span class="hidden-xs"><a href="sitemap.html" title="<spring:message code="sitemap" text="Sitemap"/>"> <i class="fa fa-sitemap" data-toggle="tooltip" data-placement="bottom"></i></a></span>
		  <span class="header-search hidden-xs">
		  <form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
		  <div class="input-group">
		  <label class="hide" for="search_input">Search Content</label> 
		  <input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required placeholder="<spring:message code="eip.searchhere" text="search"/>">
		  <span class="input-group-btn"><button class="btn btn-default" type="Submit" id="searchButton">
		  <strong class="fa fa-search"></strong><span class="hide">searchButton</span></button></span>
		 </div>
		 </form:form>
		 </span>
		<span><a href="sitemap.html" title="<spring:message code="sitemap" text="Sitemap"/>"><strong class="fa fa-sitemap"></strong> <spring:message code="sitemap" text="Sitemap"/></a></span>
		<span id="google_translate_element" class="hidden-xs hidden-sm hidden-md" style="display:inline-block;"></span>
 		</div>
		</div>
		
	<div id="navigation" class="hidden-xs hidden-sm">
      <div class="container">
        <div class="row row-eq-height">
          <div class="col-md-3">
            <div class="columns">
  	<h3><a href="CitizenHome.html"><spring:message code="top.home" text="Home"/></a></h3>
        <ul>
			<li><a href='CitizenHome.html'><span><spring:message code="top.home"></spring:message></span></a></li>
			<li><a href='CitizenAboutUs.html' title="ULB"><span><spring:message code="top.aboutus"></spring:message></span></a></li>
			<li><a href='CitizenFAQ.html?getFAQ'><span><spring:message code="top.faq"></spring:message></span></a></li>
			<li><a href='CitizenContactUs.html' title="ULB2"><span><spring:message code="eip.citizen.footer.contactUs" /></span></a></li>
    	</ul>
    	
 		<h3>${userSession.employee.empname}&nbsp;${userSession.employee.empLName}</h3>
 		<c:if test="${userSession.employee.emploginname ne 'NOUSER'}" var="user">
 		<ul>
 			<li><a href="CitizenHome.html?EditUserProfile" id="editUserProfile"><i class="fa fa-pencil"></i> Edit Profile</a></li>
			<li><a href="LogOut.html"><i class="fa fa-power-off text-red"></i> Sign Out</a></li>
			</ul>
 		</c:if>
     	
    	
    	
    	
 
 	</div> 
	</div>
 <div class="col-md-9">
   	 <div class="columns-multilevel">
  		<ul class="section-nav">
						<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
							<li class="parent" id="${masters.entitle.smfid}"><a class="" href="${masters.entitle.smfaction}" title="${masters.entitle.smfname}">${masters.entitle.smfname}</a>
								<ul class="blink">
									<c:forEach items="${menuRoleEntitlement.childList}" var="data">
										<c:if test="${masters.entitle.smfid eq  data.parentId}">
											<c:set var="action0" value="${data.entitle.smfaction}" />
											<li id="${data.entitle.smfid}"><a   <c:choose><c:when test="${fn:containsIgnoreCase(action0 , '.html')}">href="#" onclick="openRelatedForm('${action0}','this');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}');"</c:when><c:otherwise>href="${action0}"</c:otherwise></c:choose>>${data.entitle.smfname}
											</a>
												<ul class="">
													<c:forEach items="${menuRoleEntitlement.childList}" var="data1">
														<c:if test="${data.entitle.smfid eq  data1.parentId}">
															<c:set var="action1" value="${data1.entitle.smfaction}" />
															<li id="${data1.entitle.smfid}"><a  <c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}"> href="#" onclick="openRelatedForm('${action1}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}');"</c:when><c:otherwise>href="${action1}"</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
																<ul class="">
																	<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
																		<c:if test="${data1.entitle.smfid eq  data2.parentId}">
																			<c:set var="action2" value="${data2.entitle.smfaction}" />
																			<li id="${data2.entitle.smfid}"><a  <c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}">href="#" onclick="openRelatedForm('${action2}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}');"</c:when><c:otherwise>href="${action2}"</c:otherwise></c:choose>>${data2.entitle.smfname}</a>
																				<ul>
																					<c:forEach items="${menuRoleEntitlement.childList}" var="data3">
																						<c:if test="${data2.entitle.smfid eq  data3.parentId}">
																							<c:set var="action3" value="${data3.entitle.smfaction}" />
																							<li id="${data3.entitle.smfid}"><a  
																								<c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}">href="#" onclick="openRelatedForm('${action3}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}+${data3.entitle.smfname}');"</c:when><c:otherwise>href="${action3}"</c:otherwise></c:choose>>${data3.entitle.smfname}</a></li>
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
 	<a href="javascript:void(0)" title="Close" class="close_icon"><i class="fa fa-times-circle" aria-hidden="true"></i><span><spring:message code="bt.close" text="Close"/></span></a>
  	</div>
   	</div>
   	</div>
	</div>
	</div>

	<div class="header-inner">
	<div class="container-fluid">
 	<div class="col-md-9 col-sm-9 col-lg-9 col-xs-12"> 
	<div class="brand wow fadeInLeft animated animated" data-wow-duration="1.5s" data-wow-offset="10" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;">

 					<c:forEach items="${userSession.logoImagesList}" var="logo"
					varStatus="status">
					<c:set var="parts" value="${fn:split(logo, '*')}" />
					<c:if test="${parts[1] eq '1'}">
						<a href="CitizenHome.html"><img src="./${parts[0]}" width="70" alt="Loading please wait"></a>
					</c:if>
				</c:forEach>			
				<a href="CitizenHome.html"><h1>
					<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
					<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
 				</h1></a>
		</div>
		</div>	
		<div class="col-md-3 col-sm-3 col-lg-3 hidden-xs"><div class="brand wow fadeInRight animated animated pull-right" data-wow-duration="1.5s" data-wow-offset="10" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;"><img src="assets/img/national_emblem.png" class="pull-right" alt="national_emblem"></div></div>
 	</div>	
				
				
				
 			<div class="col-md-6 hidden-lg hidden-md">
				<div class="header-nav-main header-nav-main-effect-1 header-nav-main-sub-effect-1 collapse">
				<nav>
					<ul class="nav nav-pills" id="tabmenuhover">
					<li class="dropdown"><a class="dropdown-toggle" href="javascript:void(0);"> <spring:message code="Services" text="Services"/> </a>
						<ul class="dropdown-menu">
						<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
							<li class="dropdown parent" id="${masters.entitle.smfid}"><a class="dropdown-toggle" href="${masters.entitle.smfaction}" title="${masters.entitle.smfname}" onclick="dodajAktywne(this)">${masters.entitle.smfname}</a>
								<ul class="dropdown-menu">
									<c:forEach items="${menuRoleEntitlement.childList}" var="data">
										<c:if test="${masters.entitle.smfid eq  data.parentId}">
											<c:set var="action0" value="${data.entitle.smfaction}" />
											<li id="${data.entitle.smfid}"><a   <c:choose><c:when test="${fn:containsIgnoreCase(action0 , '.html')}">href="#" onclick="openRelatedForm('${action0}','this');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}');"</c:when><c:otherwise>href="${action0}"</c:otherwise></c:choose>>${data.entitle.smfname}
											</a>
												<ul class="dropdown-menu">
													<c:forEach items="${menuRoleEntitlement.childList}" var="data1">
														<c:if test="${data.entitle.smfid eq  data1.parentId}">
															<c:set var="action1" value="${data1.entitle.smfaction}" />
															<li id="${data1.entitle.smfid}"><a  <c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}"> href="#" onclick="openRelatedForm('${action1}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}');"</c:when><c:otherwise>href="${action1}"</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
																<ul class="dropdown-menu">
																	<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
																		<c:if test="${data1.entitle.smfid eq  data2.parentId}">
																			<c:set var="action2" value="${data2.entitle.smfaction}" />
																			<li id="${data2.entitle.smfid}"><a  <c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}">href="#" onclick="openRelatedForm('${action2}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}');"</c:when><c:otherwise>href="${action2}"</c:otherwise></c:choose>>${data2.entitle.smfname}</a>
																				<ul>
																					<c:forEach items="${menuRoleEntitlement.childList}" var="data3">
																						<c:if test="${data2.entitle.smfid eq  data3.parentId}">
																							<c:set var="action3" value="${data3.entitle.smfaction}" />
																							<li id="${data3.entitle.smfid}"><a  
																								<c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}">href="#" onclick="openRelatedForm('${action3}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}+${data3.entitle.smfname}');"</c:when><c:otherwise>href="${action3}"</c:otherwise></c:choose>>${data3.entitle.smfname}</a></li>
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
						</li>
					</ul>
				</nav>
			</div>
			</div>
			</div>		
		</div>		
	</div>
	</nav>
</header>

<header id="top-header" class="page-heading"><div class="container"></div></header>







<form:form id="postMethodForm" method="POST" class="form"></form:form>