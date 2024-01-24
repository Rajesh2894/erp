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
<header id="header" data-plugin-options='{"stickyEnabled": true, "stickyEnableOnBoxed": true, "stickyEnableOnMobile": true, "stickyStartAt": 30, "stickySetTop": "0px"}'>
	<div class="header-top">
		<div class="container clearfix">
			<div class="pull-left">
        <ul>
            <li class="hidden-xs hidden-sm"><a id="menu-toggle" href="#" class="glyphicon glyphicon-align-justify btn-menu toggle" > <spring:message code="Menu" text=""/> &nbsp; <i class="fa fa-bars"></i></a></li>
           	<li class="hidden-xs hidden-sm"><a href="AccessibilityLanding.html" title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options"><i class="fa fa-universal-access" aria-hidden="true"></i> <spring:message code="Accessibility" text="Accessibility Options"/></a></li>
          	<li class="hidden-sm hidden-xs"><a href="#MainContent" title="<spring:message code="Skip" text="Skip to Main Content"/>"><i class="fa fa-arrow-circle-o-down" aria-hidden="true"></i> <spring:message code="Skip" text="Skip to Main Content"/></a></li>
         </ul>
      </div>
			<div class="pull-right">
				<ul>
			<c:if test="${userSession.socialMediaMap.size() gt 0}">
      		<c:forEach var="media" items="${userSession.socialMediaMap}" >
        	<li><a href="${media.value}" target="new"><i class="fa fa-${media.key}"></i></a></li>
      		</c:forEach>
 			</c:if>
					<li>
						<div class="btn-group">
							<button class="btn-link btn-xs dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								${userSession.employee.empname}&nbsp;${userSession.employee.empLName}
							</button>
							<c:if test="${userSession.employee.emploginname ne 'NOUSER'}"
								var="user">
								<ul class="dropdown-menu">
									<li><a href="CitizenHome.html?EditUserProfile" id="editUserProfile"><i class="fa fa-pencil"></i> Edit Profile</a></li>
									<li><a href="LogOut.html"><i class="fa fa-power-off text-red"></i> Sign Out</a></li>
								</ul>
							</c:if>
						</div>
					</li>
					<li class="hidden-xs"><a href="javascript:void(0);" class="orange" title="Orange Theme" onclick="setcontrast('O')"> <i class="fa fa-circle fa-lg orange" data-toggle="tooltip" data-placement="bottom" title="Select Orange Theme"></i><span class="hide">Orange</span></a></li>
					<li class="hidden-xs"><a href="javascript:void(0);" title="Blue Theme" class="blue" onclick="setcontrast('B')"> <i class="fa fa-circle fa-lg blue" data-toggle="tooltip" data-placement="bottom" title="Select Blue Theme"></i><span class="hide">Blue</span></a></li>
					<li class="hidden-xs"><a href="javascript:void(0);" title="Green Theme" class="green" onclick="setcontrast('G')"> <i class="fa fa-circle fa-lg green" data-toggle="tooltip" data-placement="bottom" title="Select Green Theme"></i><span class="hide">Green</span></a></li>
					<li class="hidden-xs"><a href="javascript:void(0);" id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce">A-</a></li>
					<li class="hidden-xs"><a href="javascript:void(0);" id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal">A</a></li>
 					<li class="hidden-xs"><a href="javascript:void(0);" id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase">A +</a></li>
					<li class="header-search hidden-xs hidden-md hidden-sm"> 
								<form:form role="search" action="SearchContent.html"
									method="POST" id="EIPSearchContentForm">
									<div class="input-group">
										<label class="hide" for="search_input">Search Content</label> <input
											type="text" class="form-control" name="searchWord"
											id="search_input" autocomplete="off" required
											placeholder="<spring:message code="eip.searchhere" text="search"/>">
										<span class="input-group-btn">
											<button class="btn btn-default" type="Submit"
												id="searchButton">
												<strong class="fa fa-search"></strong><span class="hide">searchButton</span>
											</button>
										</span>
									</div>
								</form:form>
							 </li>
					<li><a href="sitemap.html" title="<spring:message code="sitemap" text="Sitemap"/>"><strong class="fa fa-sitemap"></strong> <spring:message code="sitemap" text="Sitemap"/></a></li>
					<li><div id="google_translate_element"></div></li>
				</ul>
			</div>
		</div>
		
		<div id="navigation" class="hidden-xs hidden-sm"> 
	<div class="container">
	<div class="row row-eq-height">
 	<div class="col-md-2">
  	<div class="columns">
  	<h3><a href="CitizenHome.html"><spring:message code="top.home" text="Home"/></h3>
        <ul>
			<li><a href='CitizenHome.html'><span><spring:message code="top.home"></spring:message></span></a></li>
			<li><a href='CitizenAboutUs.html' title="ULB"><span><spring:message code="top.aboutus"></spring:message></span></a></li>
			<li><a href='CitizenFAQ.html?getFAQ'><span><spring:message code="top.faq"></spring:message></span></a></li>
			<li><a href='CitizenContactUs.html' title="ULB2"><span><spring:message code="eip.citizen.footer.contactUs" /></span></a></li>
    	</ul>
 
 	</div> 
	</div>
  <div class="col-md-10">
  	<h3><spring:message code="left.quicklinks" text="Quick Links"/></h3>
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
	<div class="header-body" style="margin-top:0px !important;">
		<div class="container clearfix">
			<button type="submit" class="btn header-btn-collapse-nav" name="Menu Collapse" data-toggle="collapse" data-target=".header-nav-main"><strong class="fa fa-bars"><span class="hide">Menu Collapse</span></strong></button>
			<div class="row">
				<div class="col-md-6 center-logo-text">
					<c:forEach items="${userSession.logoImagesList}" var="logo"
					varStatus="status">
					<c:set var="parts" value="${fn:split(logo, '*')}" />
					<c:if test="${parts[1] eq '1'}">
						<a href="CitizenHome.html"><img src="./${parts[0]}" width="70" alt="Loading please wait"></a>
					</c:if>
				</c:forEach>			
				<h1>
					<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
					<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
				</h1>
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
</header>
<form:form id="postMethodForm" method="POST" class="form"></form:form>