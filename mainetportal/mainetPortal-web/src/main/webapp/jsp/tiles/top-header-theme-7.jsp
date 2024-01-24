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
	debugger;
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
	});
	
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

<div class="page" id="pagecontainer">
</div>
<%-- <c:set var="headerImages" value="${command.headerDetails}" /> --%>
<header class="site-header" id="mainHeader">
   <nav class="navbar navbar-default">
      <div class="top-line navbar-fixed-top">
         <div class="container">
         	<div class="row">
	            <div class="col-xs-6 col-lg-1 col-md-1 col-sm-5 hidden-lg hidden-md ">
	               <div class="logo"> <!-- hidden-sm -->
	                  <a href="CitizenHome.html">
	                     <c:forEach
	                        items="${userSession.logoImagesList}" var="logo" varStatus="status">
	                        <c:set var="parts" value="${fn:split(logo, '*')}" />
	                        <c:if test="${parts[1] eq '1'}">
	                           <img src="${parts[0]}" class="pull-left hidden-md hidden-lg img-responsive"
	                           alt="
	                           <c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
	                           <c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
	                           ">
	                        </c:if>
	                     </c:forEach>
						<%-- <span class="website-name">
							<h3>${userSession.organisation.ONlsOrgnameMar}</h3>
							<h1>${userSession.organisation.ONlsOrgname}</h1>
						</span> --%>
	                     <c:if test="${userSession.languageId eq 1}">
	                        <h1>${userSession.organisation.ONlsOrgname}</h1>
	                     </c:if>
	                     <c:if test="${userSession.languageId eq 2}">
	                        <h1>${userSession.organisation.ONlsOrgnameMar}</h1>
	                     </c:if> 
	                  </a>
	               </div>
	            </div>
	            
	            <div class="col-lg-12 col-md-12 col-sm-7 col-xs-6 content-right text-right">
	               <div class="last">
	               	<span class="header-search hidden-xs">
						<form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
	      				<div class="input-group">
	      					<label class="hidden" for="search_input">Search</label>
	      					<input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
	      					<span class="input-group-btn"><button class="btn btn-default" type="Submit" id="searchButton"><strong class="fa fa-search"></strong><span class="hide">searchButton</span></button></span></div>
	      				</form:form>
	      			   </span>  
	                  <span>
	                     <a href="CitizenHome.html" title="
	                     <spring:message code="top.home" text="Home"/>
	                     "><i class="fa fa-home" aria-hidden="true"></i>
	                     <%-- <span class="hidden-xs">
	                        <spring:message code="top.home" text="Home" />
	                     </span> --%>
	                     </a>
	                  </span>
	                  <%-- <span class="hidden-xs hidden-sm smooth-scroll">
	                     <a href="#CitizenService" title="
	                     <spring:message code="Skip" text="Skip to Main Content"/>
	                     "><i class="fa fa-arrow-circle-o-down" aria-hidden="true"></i> 
	                     <spring:message code="Skip" text="Home"/>
	                     </a>
	                  </span> --%>
	                  <%-- <c:set var="curentDomain" value="${userSession.organisation.orgid}.domain.url"/>
	                  <spring:message var="curDomainName" code="${curentDomain}" text=""></spring:message>
	                  <c:if test="${empty curDomainName }">
	                     <span class="">
	                        <a href="ULBHome.html?resetULB&orgId=${command.appSession.superUserOrganization.orgid}" title="
	                        <spring:message code="main.dept" text="Go to UAD Page"/>
	                        "><i class="fa fa-reply-all" aria-hidden="true"></i> 
	                        <span class="hidden-xs">
	                           <spring:message code="main.dept" text="Go to Home Page"/>
	                        </span>
	                        </a>
	                     </span>
	                  </c:if> --%>
	                  <%-- <span class="a"><span class="hidden-xs"><a  title="Blue Theme" onclick="setcontrast('B')"> <i class="c" style="background: #1764af;" data-toggle="tooltip" data-placement="bottom" title="Select Blue Theme"></i><span class="hide">Blue</span> </a></span>
	                  <span class="hidden-xs"><a  title="Orange Theme" onclick="setcontrast('O')"><i class="c" style="background:#b91616;" data-toggle="tooltip" data-placement="bottom" title="Select Orange Theme"></i><span class="hide">Orange</span> </a></span>
	                  <span class="hidden-xs"><a  title="Green Theme" onclick="setcontrast('G')"> <i class="c"  style="background:#00aa55;" data-toggle="tooltip" data-placement="bottom" title="Select Green Theme"></i><span class="hide">Green</span> </a></span></span> --%>
	                  <span class="hidden-xs">
	                     <a href="Accessibility.html" title="
	                     <spring:message code="Accessibility" text="Accessibility Options"/>
	                     " title="Accessibility Options" class="box-shadow-none"><!--  style="font-size: 17px;top: 1px;position: relative;" -->
	                     <i class="fa fa-adjust" aria-hidden="true">
	                        <span class="hidden">
	                           <spring:message code="Accessibility" text="Accessibility Options"/>
	                           " title="Accessibility Options">
	                        </span>
	                     </i>
	                     </a>
	                  </span>
	                  <span class="hidden-xs hidden-sm"><a  id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce" class="box-shadow-none">A -</a></span>
	                  <span class="hidden-xs hidden-sm"><a  id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal" class="box-shadow-none">A</a></span>
	                  <span class="hidden-xs hidden-sm"><a  id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase" class="box-shadow-none">A +</a></span>
	                  <!-- &nbsp; -->
	                  <span class="hidden-xs">
	                     <a href="sitemap.html" aria-label='<spring:message code="sitemap" text="Sitemap"/>' title="
	                     <spring:message code="sitemap" text="Sitemap"/>
	                     "> <i class="fa fa-sitemap" data-toggle="tooltip" data-placement="bottom"></i></a>
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
	                        <a onclick="changeLanguage('?locale&lang=en');" href="javascript:void(0);" class="box-shadow-none" title="
	                        <spring:message code="header.eng" text="header.eng"/>
	                        "><i class="fa fa-globe"></i> 
	                        <spring:message code="header.eng" text="header.eng" />
	                        </a>
	                     </span>
	                  </c:if>
	                  
	                  <span class="hidden-lg hidden-md"> <!-- hidden-sm -->
	                  	<a id="mobile-button" href="javascript:void(0);" aria-label="Menu"><i class="fa fa-align-justify" aria-hidden="true"></i></a>
	                  </span> 
	                 <%--  <div id="google_translate_element" class="hidden-xs hidden-sm hidden-md" style="display:inline-block;"></div> --%>
	                  
	               </div>
	            </div>
	            
	            <!--  Start of code by ABM2144 for Social Media Section on 21-05-2019 -->
	           <%--  <c:if test="${command.userSession.socialMediaMap.size() gt 0}">
	               <div class="sticky-container1">
	                  <ul class="sticky">
	                     <c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
	                        <li class="${media.key}">
	                        	<a href="${media.value}" target="new_${ count.count}">
									<i class="fa fa-${media.key}"> <span class="hidden">${media.key}</span></i>
									<span class="social-icon-lable">Like Us on ${media.key}</span>
	                           </a>
	                        </li>
	                     </c:forEach>
	                  </ul>
	               </div>
	            </c:if> --%>
	            <!--  End of code by ABM2144 for Social Media Section on 21-05-2019 -->
	            
		         <!-- Mobile View tab links -->
		         <div id="navbar" class="container">
		            <nav class="navbar navbar-default hidden-lg hidden-md"> <!-- hidden-sm -->
		               <div class="container-fluid nav-scroll">
		                  <ul id="mobile" class="nav navbar-nav">
		                  	<li class="parent">
		                        <a class="dropdown-toggle" href="javascript:void(0);">
		                           <spring:message code="Login" text="Login"/>
		                        </a>
		                        <ul>
		                           <li>
		                              <a href='javascript:void(0);' onclick="getCitizenLoginForm('N')">
		                              	<spring:message code="CitizenLogin" text="Citizen Login"/>
		                              </a>
		                           </li>
		                           <li>
		                              <a href='javascript:void(0);' onclick="getAdminLoginForm()">
		                                <spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/>
		                              </a>
		                           </li>
		                           <li>
		                              <a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
		                                <spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/>
		                              </a>
		                           </li>
		                        </ul>
		                     </li>                  
		                     <%-- <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
		                        <li>
		                           <form:form name="selectMunicipalForm1" id="selectMunicipalForm1" action="" class="list" method="get">
		                              <div class="container-fluid">
		                                 <div class=" col-xs-10">
		                                    <label class="hidden" for="selectedOrg1">Select Department</label>
		                                    <select name="orgId" id="selectedOrg1" data-content="" class="form-control ">
		                                       <option value="-1">
		                                          <spring:message code="" text="Select Muncipality" />
		                                       </option>
		                                       <c:forEach items="${command.userSession.organisationsList}" var="orglist">
		                                          <optgroup label="${orglist.key}">
		                                             <c:forEach items="${orglist.value}" var="org">
		                                                <c:if test="${userSession.languageId eq 1}">
		                                                   <option value="${org.orgid}">${org.ONlsOrgname}</option>
		                                                </c:if>
		                                                <c:if test="${userSession.languageId eq 2}">
		                                                   <option value="${org.orgid}">${org.ONlsOrgnameMar}</option>
		                                                </c:if>
		                                             </c:forEach>
		                                          </optgroup>
		                                       </c:forEach>
		                                    </select>
		                                 </div>
		                                 <div class=" col-xs-2 ">
		                                    <div class="row">
		                                       <span class="input-group-btn">
		                                          <button type="button" class="btn btn-default btn-sm" id="submitMunci1">
		                                             <spring:message code="go" text="Go!"/>
		                                          </button>
		                                       </span>
		                                    </div>
		                                 </div>
		                              </div>
		                           </form:form>
		                        </li>
		                     </c:if> --%>
		                      
		                     <%-- <li>
								<!-- <a href="javascript:void(0)" role="link" class="internal"> -->
								<a href="CitizenHome.html" role="link" class="internal">
									<spring:message code="top.home" text="Home"></spring:message>
								</a>
								<ul>							
									<li>
				                        <a href='CitizenHome.html'>
				                           <span>
				                              <spring:message code="top.home" text="Home"></spring:message>
									      </span>
									   </a>
									</li>
									<li>
									   <a href='CitizenAboutUs.html'>
									      <span><spring:message code="top.aboutus" text="About Us"></spring:message></span>
									   </a>
									</li>
									<li>
									   <a href='CitizenContactUs.html'>
									      <span><spring:message code="eip.citizen.footer.contactUs" text="Contact Us" /></span>
									   </a>
									</li>
									<li>
									   <a href='CitizenFAQ.html?getFAQ'>
									      <span><spring:message code="top.faq" text="Faqs"/></span>
									   </a>
									</li>
								</ul>
							</li> --%>
							<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
							<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
							<%-- <li class="parent">
								<a href="javascript:void(0);" class="blink" >
									<spring:message code="lbl.webArchives" text="Web Archives"/>
								</a>
								<ul>
									<li class="blink">
										<a href="DataArchival.html?archivedData" >
											<spring:message code="lbl.archiveData" text="Archive Data"/>
										</a>
									</li>
								</ul>
							</li> --%>
		                                          
		                  </ul>
		               </div>
		            </nav>
		         </div>
		         <!-- Mobile View tab links ends -->
	         </div>
         </div>
      </div>
      <%-- <div class="header-inner hidden-xs">
         		<c:if test="${userSession.employee.emploginname ne 'NOUSER' }">
         <div class="container-fluid">
            <div class="col-md-9 col-sm-8 col-lg-9 col-xs-9">
               <div class="brand wow fadeInLeft animated animated" data-wow-duration="1.5s" data-wow-offset="10" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;">
                  <a href="CitizenHome.html">
                     <c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
                        <c:set var="parts" value="${fn:split(logo, '*')}" />
                        <c:if test="${parts[1] eq '1'}">
                           <img src="${parts[0]}" class="pull-left hidden-xs img-responsive" style="height:50px;" alt="
                           <c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
                           <c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
                           ">  
                        </c:if>
                     </c:forEach>
                     <c:if test="${userSession.languageId eq 1}">
                        <h1>${userSession.organisation.ONlsOrgname}</h1>
                     </c:if>
                     <c:if test="${userSession.languageId eq 2}">
                        <h1>${userSession.organisation.ONlsOrgnameMar}</h1>
                     </c:if>
                  </a>
               </div>
            </div>
            <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
            	<div class="col-md-3 col-sm-4 col-lg-3 col-xs-3">
            		<nav class="menu-header-menu-container">
						<ul class="main-navigation">
						<li><a ><spring:message code="Login" text="Login"/></a>			
						<ul>
						<li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
						<li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span></a></li>
						<li><a href='<spring:message code="service.admin.home.url"/>'  target="_blank"><span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span></a></li>
						</ul>
						</li>
						</ul>
					</nav>
            	</div>
            </c:if>
            <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
               <div class="col-md-3 col-sm-3 col-lg-3 hidden-xs">
                  <div class="brand wow fadeInRight animated animated pull-right" data-wow-duration="1.5s" data-wow-offset="10" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;"><img src="assets/img/national_emblem.png" class="pull-right" alt="National Emblem" style="height:56px;"></div>
               </div>
            </c:if>
         </div>
      </div> --%>
      
	<!-- Qucik Header Menu Start -->
	<header class="header hidden-xs hidden-sm fixed">
		<div class="header-2">
			<div class="container">
				<div class="row">
					<div class="logo col-sm-6 col-md-7 col-lg-8">
						<a href="CitizenHome.html"><c:forEach
								items="${userSession.logoImagesList}" var="logo" varStatus="status">
								<c:set var="parts" value="${fn:split(logo, '*')}" />
								<c:if test="${parts[1] eq '1'}">
									<img src="${parts[0]}" class="pull-left hidden-xs img-responsive"
										
										alt="<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if><c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> logo">
								</c:if>
							</c:forEach>
							<span class="website-name">
								 <h1>${userSession.organisation.ONlsOrgname}</h1>
								 <h3>${userSession.organisation.ONlsOrgnameMar}</h3>
							</span>
							<%-- <c:if test="${userSession.languageId eq 1}">
								<h1>${userSession.organisation.ONlsOrgname}</h1>
							</c:if> <c:if test="${userSession.languageId eq 2}">
								<h1>${userSession.organisation.ONlsOrgnameMar}</h1>
							</c:if> --%>
						</a>
					</div>
					<div class="logo-right col-sm-6 col-md-5 col-lg-4">
						<a href="https://swachhbharat.mygov.in/" target="_blank">
							<c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
								<c:set var="parts" value="${fn:split(logo, '*')}" />
								<c:if test="${parts[1] eq '2'}">
									<img src="${parts[0]}" class="hidden-xs img-responsive"
										alt="<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
										<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> logo">
								</c:if>	
							</c:forEach>
						</a>
					</div>
					
					<%-- <div class="second-header-helpline">
			        	<span class="header-helpline"><spring:message code="theme4.portal.helpline.number" text="Helpline Number (+917500441344)"/></span>
			        </div> --%>
					<%-- <div class="mini-menu">
						<div class="login-btn">
						<a href="javascript:void(0);"><spring:message code="Login" text="Login"/></a>			
								<ul class="drpdwn">
						        <li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
						        <li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span></a></li>
						        <li><a href='<spring:message code="service.admin.home.url"/>'  target="_blank"><span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span></a></li>
						       </ul>
						</div>
					</div>	 --%>
					<span class="imenu"><i class="flaticon-menu"></i></span>
				</div>
			</div>
		</div>
		<div class="border-red"></div>
		<div class="header-3">
		<div class="container">
		<div class="row">
		<nav class="navbar navbar-expand-sm">
			<div class="main-menu-container col-sm-12 col-md-12 col-lg-12">
				<ul class="main-navigation-menu">
					<li>
						<a href="CitizenHome.html" title="<spring:message code="top.home" text="Home"/>">
							<i class="fa fa-home" aria-hidden="true"></i>
						</a>
					</li>
					<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
					<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
					
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
					<%-- <li class="blink">
						<a href='CitizenFeedBack.html' class="pinned">
							<spring:message code="theme6.portal.suggest.innovative.ideas" text="Suggest Innovative Ideas To Us"/>
						</a>
					</li> --%>
				</ul>
			</div>
			<div class="content-search-main col-xs-2 col-sm-2 col-md-2 col-lg-2">
				<%-- <form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
					<input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
					<div class="content-search" id="searchButton"></div>
				</form:form> --%>
				<%-- <form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
      				<div class="input-group">
      					<input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
      					<span class="input-group-btn"><button class="btn" type="Submit" id="searchButton"><strong class="fa fa-search"></strong></button></span>
      				</div>
      			</form:form> --%>
			</div>
		</nav>
		</div>
		</div>
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
	

<!-- Qucik Header Menu End -->
      
      
   </nav>
</header>
<header id="top-header" class="page-heading">
   <div class="container"></div>
</header>
<!-- Widget Links starts -->
<div class="widget-links hidden-xs hidden-sm">
	<ul>
	
		<%-- </c:if>
		<c:if test="${command.themeMap['FEEDBACK'] ne 'I'}"> --%>
			<li class="blink">
				<a href='CitizenFeedBack.html' class="pinned">
					<spring:message code="theme7.portal.suggest.innovative.ideas" text="Suggest Innovative Ideas"/>
				</a>
		</li>
	</ul>
</div>
<!-- Widget Links ends -->
