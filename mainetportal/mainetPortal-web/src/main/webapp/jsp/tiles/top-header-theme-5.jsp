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
   }
</script>
<script>
   $(document).ready(function(){
   	if($("div").hasClass('invisibeHead')){
      $("#top-header").addClass('hide');
    }
   });
   $(document).scroll(function(){
	   if ($(this).scrollTop() == 0){
		   $('#login-div').removeClass('login-link');
		   	$('#login-div').removeClass('highlight');
		}else{
			$('#login-div').addClass('login-link');
			$('#login-div').addClass('highlight');
		}
	   	
	   });   
</script>
<%-- Defect #158732 --%>
<div id="sdiv"></div><div class="sloading"><img src="css/images/loader.gif" alt="loading" /></div>
<div class="page" id="pagecontainer"></div>
<div class="top-header">
	<div class="container">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-4">
			
		</div>
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-8">
	            	<div class="content-right">
		               <div class="last wbst-settings">
		                  <span class="header-search hidden-xs">
							<form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
		      				<div class="input-group">
		      					<label class="hidden" for="search_input">Search</label>
		      					<input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
		      					<span class="input-group-btn"><button class="btn btn-default" type="Submit" id="searchButton"><strong class="fa fa-search"></strong><span class="hide">searchButton</span></button></span></div>
		      				</form:form>
		      			  </span>
		      			  <span class="visible-xs">
		                     <a href="CitizenHome.html" title="
		                     <spring:message code="top.home" text="Home"/>
		                     "><i class="fa fa-home" aria-hidden="true"></i>
		                     </a>
		                  </span>
		                  <span class="hidden-xs hidden-sm"><a  id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase" class="box-shadow-none">A +</a></span>
		                  <span class="hidden-xs hidden-sm"><a  id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal" class="box-shadow-none">A</a></span>
		                  <span class="hidden-xs hidden-sm"><a  id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce" class="box-shadow-none">A -</a></span>
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
		                  <span class="virtual-keyboard hidden-xs hidden-sm">
		                  	<a href="javascript:void(0);" class="virtualKeyboard vk-off">
								<i class="fa fa-keyboard-o" aria-hidden="true"></i>
								<span class="hidden-xs hidden-sm"><spring:message code="theme5.portal.virtual.keyboard" text="Virtual Keyboard"/></span>
							</a>
		                  </span>
		                  <%-- <span class="hidden-lg hidden-md mobile-hamburger"> hidden-sm
		                  	<a id="mobile-button" href="javascript:void(0);" aria-label="Menu"><i class="fa fa-align-justify" aria-hidden="true"></i></a>
		                  </span>  --%>
		                 <%--  <div id="google_translate_element" class="hidden-xs hidden-sm hidden-md" style="display:inline-block;"></div> --%>
		               </div>
	               </div>
	            </div> 
	</div>
</div>
<%-- <c:set var="headerImages" value="${command.headerDetails}" /> --%>
<header class="site-header">
   <nav class="navbar">
      <div class="top-line navbar-fixed-top">
         <div class="container">
            <div class="row">
	           <%--  <div class="col-xs-12 col-sm-5 col-md-4 col-lg-4">
	               <div class="logo"> <!-- hidden-sm -->
	                  <a href="CitizenHome.html">
	                     <c:forEach
	                        items="${userSession.logoImagesList}" var="logo" varStatus="status">
	                        <c:set var="parts" value="${fn:split(logo, '*')}" />
	                        <c:if test="${parts[1] eq '1'}">
	                           <img src="${parts[0]}" class="pull-left img-responsive"
	                           alt="
	                           <c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
	                           <c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
	                           ">
	                        </c:if>
	                     </c:forEach>
	                     <c:if test="${userSession.languageId eq 1}">
	                        <h1>${userSession.organisation.ONlsOrgname}</h1>
	                        <span class="website-name visible-xs">
	                        	<h1>${userSession.organisation.ONlsOrgname}</h1>
								<h3>${userSession.organisation.ONlsOrgnameMar}</h3>
							</span>
	                     </c:if>
	                     <c:if test="${userSession.languageId eq 2}">
	                        <h1>${userSession.organisation.ONlsOrgnameMar}</h1>
	                        <span class="website-name visible-xs">
								<h1>${userSession.organisation.ONlsOrgnameMar}</h1>
								<h3>${userSession.organisation.ONlsOrgname}</h3>
							</span>
	                     </c:if>
	                  </a>
	               </div>
	            </div> --%>
	            
	            <%-- --%>
	            
	            <!-- Qucik Header Menu Start -->
	            <div class="col-xs-10 col-sm-11 col-md-5 col-lg-6">
	               <div class="logo"> <!-- hidden-sm -->
	                  <a href="CitizenHome.html">
	                     <c:forEach
	                        items="${userSession.logoImagesList}" var="logo" varStatus="status">
	                        <c:set var="parts" value="${fn:split(logo, '*')}" />
	                        <c:if test="${parts[1] eq '1'}">
	                           <img src="${parts[0]}" class="pull-left img-responsive"
	                           alt="Organisation Logo"
	                           <c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
	                           <c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
	                           ">
	                        </c:if>
	                     </c:forEach>
                     </a>
                     <a href="javascript:void(0);">
                     	<img src="images/DSCL-images/G20_U20-03.jpg" alt="G20" />
                     </a>
                     <a href="CitizenHome.html">
	                     <c:if test="${userSession.languageId eq 1}">
	                        <%-- <h1>${userSession.organisation.ONlsOrgname}</h1> --%>
	                        <span class="website-name">
	                        	<h1>${userSession.organisation.ONlsOrgname}</h1>
								<h3>${userSession.organisation.ONlsOrgnameMar}</h3>
							</span>
	                     </c:if>
	                     <c:if test="${userSession.languageId eq 2}">
	                        <%-- <h1>${userSession.organisation.ONlsOrgnameMar}</h1> --%>
	                        <span class="website-name">
								<h1>${userSession.organisation.ONlsOrgnameMar}</h1>
								<h3>${userSession.organisation.ONlsOrgname}</h3>
							</span>
	                     </c:if>
	                  </a>
	               </div>
	            </div>
	            <div class="col-md-7 col-lg-6 hidden-xs hidden-sm">
	            	<%--  Start of code by ABM2144 for Social Media Section on 21-05-2019 --%>
		            <c:if test="${command.userSession.socialMediaMap.size() gt 0}">
	                  <ul class="header-social-media">
	                     <c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
	                        <li><a href="${media.value}" target="new_${ count.count}">
	                           <i class="fa fa-${media.key}"> <span class="hidden">${media.key}</span></i>
	                           <%-- <span>Like Us on ${media.key}</span> --%>
	                           <%-- <c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
										<span class="margin-right-5"><spring:message code="theme5.portal.social.media.label" text="Like Us On" /></span><span>${media.key}</span>
									</c:when>
									<c:otherwise>
										<span class="margin-right-5">${media.key}</span><span><spring:message code="theme5.portal.social.media.label.reg" text="पर हमें लाइक कीजिए" /></span>
									</c:otherwise>
								</c:choose> --%>
	                           </a>
	                        </li>
	                     </c:forEach>
	                  </ul>
		            </c:if>
		            <%--  End of code by ABM2144 for Social Media Section on 21-05-2019 --%>
		            <div class="addr-info">
		            	<div class="addr-col-1">
		            		<span class="addr-info-content addr">
	            				<spring:message code="theme5.portal.address" text="Saatvik Tower, 777, Kaulagarh Road, Rajendra Nagar, Dehradun, 248001"/>
	            			</span>
		            	</div>
		            	<div class="addr-col-2">
		            		<span class="addr-info-content tel">
			            		<a href="tel: <spring:message code="theme5.portal.phone.num" text="0135- 2750984"/>">
									<spring:message code="theme5.portal.phone.num" text="0135- 2750984"/>
								</a>
								<a href="tel: <spring:message code="theme5.portal.citizen.helpline.num" text="18001802525"/>">
									<spring:message code="theme5.portal.citizen.helpline.num" text="18001802525"/>
								</a>
							</span>
							<span class="addr-info-content mail">
								<a href="mailto: <spring:message code="theme5.portal.email.address" text="smartcityddn@gmail.com"/>">
									<spring:message code="theme5.portal.email.address" text="smartcityddn@gmail.com"/>
								</a>
							</span>
		            	</div>
		            </div>
	            </div>
	            
	            <div class="col-xs-2 col-sm-1 hidden-lg hidden-md mob-icon">
	            	<span class="mobile-hamburger"> <!-- hidden-sm -->
		                  	<a id="mobile-button" href="javascript:void(0);" aria-label="Menu"><i class="fa fa-align-justify" aria-hidden="true"></i></a>
		                  </span>
	            </div>
				<!-- Qucik Header Menu End -->
            </div>          
         </div>
         <div class="header-3">
         	<div class="container">
				<nav class="menu-header-menu-container wbst-menu">
					<%-- <div class="navbar-header">
						<a class="navbar-brand" href="#">
							<c:forEach
		                        items="${userSession.logoImagesList}" var="logo" varStatus="status">
		                        <c:set var="parts" value="${fn:split(logo, '*')}" />
		                        <c:if test="${parts[1] eq '1'}">
		                           <img src="${parts[0]}" class="pull-left hidden-md img-responsive"
		                           alt="
		                           <c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
		                           <c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>
		                           ">
		                        </c:if>
		                    </c:forEach>
						</a>
					</div> --%>
					<ul class="nav navbar-nav main-navigation">
						<li class="parent">
							<a href="CitizenHome.html" role="link" class="internal">
								<spring:message code="top.home" text="Home"></spring:message>
							</a>
							<ul>
								<li>
								   <%-- <a href='CitizenAboutUs.html'> --%>
								   <%-- Defect #158543 --%>
								   <a href='SectionInformation.html?editForm&rowId=76&page='>
								      <span>
								         <spring:message code="top.aboutus" text="About Us"></spring:message>
								      </span>
								   </a>
								</li>
								<li>
								   <a href='CitizenContactUs.html?id=1'>
								      <span>
								         <spring:message code="eip.citizen.footer.contactUs" text="Contact Us" />
								      </span>
								   </a>
								</li>
								<li>
								   <a href='CitizenFAQ.html?getFAQ'>
								   <span>
								      <spring:message code="top.faq" text="Faqs"/>
								   </span>
								   </a>
								</li>
								<%-- <li>
								   <a href='AdminOpinionPollOptionResponseForm.html'>
								      <span>
								         <spring:message code="admin.opinionpoll" text="Survay Poll"></spring:message>
								      </span>
								   </a>
								</li>	 --%>					
							</ul>
						</li>
						<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
						<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
						<li class="parent">
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
						</li>
						<li class="parent" id="login-div"><a href="#"><spring:message code="Login" text="Login"/></a>			
							<ul>
								<li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
								<li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span></a></li>
								<li><a href='<spring:message code="service.admin.home.url"/>'  target="_blank"><span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span></a></li>
							</ul>
						</li>
					</ul>
				</nav>
         	</div>
         </div>
         
         <!-- Mobile View tab links -->
         <div id="navbar" class="container" style="display:none;">
            <nav class="navbar navbar-default hidden-lg hidden-md"> <!-- hidden-sm -->
               <div class="container-fluid nav-scroll">
                  <ul id="mobile" class="nav navbar-nav">                    
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
                      
                     <li class="parent">
                        <a class="dropdown-toggle" href="javascript:void(0);">
                           <spring:message code="Login" text="Login"/>
                        </a>
                        <ul>
                           <li>
                              <a href='javascript:void(0);' onclick="getCitizenLoginForm('N')">
                                 <span>
                                    <spring:message code="CitizenLogin" text="Citizen Login"/>
                                 </span>
                              </a>
                           </li>
                           <li>
                              <a href='javascript:void(0);' onclick="getAdminLoginForm()">
                                 <span>
                                    <spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/>
                                 </span>
                              </a>
                           </li>
                           <li>
                              <a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
                                 <span>
                                    <spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/>
                                 </span>
                              </a>
                           </li>
                        </ul>
                     </li>
                     <li class="parent">
						<a href="javascript:void(0)" role="link" class="internal">
							<spring:message code="top.home" text="Home"></spring:message>
						</a>
						<ul>
							<%-- <li>
		                        <a href='CitizenHome.html'>
		                           <span>
		                              <spring:message code="top.home" text="Home"></spring:message>
							      </span>
							   </a>
							</li> --%>
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
					</li>
					<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
					<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
					<li class="parent">
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
					</li>
                  </ul>
               </div>
            </nav>
         </div>
         <!-- Mobile View tab links ends -->
      </div>

   </nav>
</header>
<%-- <header id="top-header" class="page-heading">
   <div class="container"></div>
</header> --%>