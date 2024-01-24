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
   	  $("#mobile.nav .parent>a").on("click", function(e){
   	    if($(this).parent().has("ul")) {
   	      e.preventDefault();
   	    }
 	    
   	    if(!$(this).hasClass("open")) {
   	      // hide any open menus and remove all other classes
   	      /* $("#mobile.nav li ul").slideUp(350);
   	      $("#mobile.nav .parent>a").removeClass("open"); */
   	      
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
<div class="page" id="pagecontainer"></div>
<%-- <c:set var="headerImages" value="${command.headerDetails}" /> --%>
<header class="site-header">
   <nav class="navbar">
      <div class="top-line navbar-fixed-top">
        <!--  <div class="container-fluid"> -->
            <div class="col-xs-6 col-lg-1 col-md-1 col-sm-5 hidden-lg hidden-md ">
               <div class="logo"> <!-- hidden-sm -->
                  <a href="CitizenHome.html">
                     <c:forEach
                        items="${userSession.logoImagesList}" var="logo" varStatus="status">
                        <c:set var="parts" value="${fn:split(logo, '*')}" />
                        <c:if test="${parts[1] eq '1'}">
                           <img src="${parts[0]}" class="pull-left hidden-md hidden-lg img-responsive"
                           style="height: 35px;"
                           alt="Organisation Logo"
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
            
            <div class="col-lg-6 col-md-12 col-sm-12 col-xs-6 content-left hidden-sm">
               <div class="last">
					<span class="hidden-xs">
						<a href="CitizenHome.html?newsAndEvent">
							<spring:message code="theme3.portal.press.release" text="Press Release" />
						</a>
					</span>
					<%-- <span class="hidden-xs">
						<a href="https://thanecity.gov.in/news.php" target="_blank">
							<spring:message code="News" text="News" />
						</a>
					</span>
					<span class="hidden-xs">
						<a href="https://thanecity.gov.in/event.php" target="_blank">
							<spring:message code="theme3.portal.events" text="Events" />
						</a>
					</span>
					<span class="hidden-xs">
						<a href="https://thanecity.gov.in/services.php" target="_blank">
							<spring:message code="theme3.portal.services" text="Services" />
						</a>
					</span>
					<span class="hidden-xs">
						<a href="Content.html?links&page=Photo Gallery">
							<spring:message code="theme3.portal.gallery" text="Gallery" />
						</a>
					</span>
					<span class="hidden-xs">
						<a href="Content.html?links&page=Video Gallery">
							<spring:message code="theme3.portal.videos" text="Videos" />
						</a>
					</span> --%>
					<span class="hidden-xs">
						<a href="./SectionInformation.html?editForm&rowId=36">
							<spring:message code="theme3.portal.rti" text="Right To Information" />
						</a>
					</span>
					<%-- <span class="hidden-xs">
						<a href="https://thanecity.gov.in/downloads.php" target="_blank">
							<spring:message code="theme3.portal.downloads" text="Downloads" />
						</a>
					</span>
					<span class="hidden-xs">
						<a href="https://thanecity.gov.in/career.php" target="_blank">
							<spring:message code="theme3.portal.career" text="Career" />
						</a>
					</span>
					<span class="hidden-xs">
						<a href="https://mail.thanecity.gov.in/mail/" target="_blank">
							<spring:message code="theme3.portal.email" text="Email" />
						</a>
					</span> --%>
					<span class="hidden-xs">
						<a href="./SectionInformation.html?editForm&rowId=351" class="flash-text">
							<spring:message code="theme3.portal.corona.covid.19" text="Corona (Covid-19)"/>
							<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new"/>
						</a>
					</span>
				</div>
			</div>
            
            <div class="col-lg-6 col-md-12 col-sm-7 col-xs-6 content-right">
               <div class="last">
                  <span class="hidden-md hidden-lg">
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
                  <!-- <span class="a"><span class="hidden-xs"><a  title="Blue Theme" onclick="setcontrast('B')"> <i class="c" style="background: #1764af;" data-toggle="tooltip" data-placement="bottom" title="Select Blue Theme"></i><span class="hide">Blue</span> </a></span>
                  <span class="hidden-xs"><a  title="Orange Theme" onclick="setcontrast('O')"><i class="c" style="background:#b91616;" data-toggle="tooltip" data-placement="bottom" title="Select Orange Theme"></i><span class="hide">Orange</span> </a></span>
                  <span class="hidden-xs"><a  title="Green Theme" onclick="setcontrast('G')"> <i class="c"  style="background:#00aa55;" data-toggle="tooltip" data-placement="bottom" title="Select Green Theme"></i><span class="hide">Green</span> </a></span></span> -->
                  <span class="hidden-xs">
                     <a href="Accessibility.html" title="
                     <spring:message code="Accessibility" text="Accessibility Options"/>
                     " title="Accessibility Options"><!--  style="font-size: 17px;top: 1px;position: relative;" -->
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
                  <span class="header-search hidden-xs">
					<form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
      				<div class="input-group">
      					<label class="hidden" for="search_input">Search</label>
      					<input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
      					<span class="input-group-btn"><button class="btn btn-default" type="Submit" id="searchButton"><strong class="fa fa-search"></strong><span class="hide">searchButton</span></button></span></div>
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
                  <span class="beta">
                  	<img src="assets/img/tmc-beta-version.png" class="img-responsive" alt="Beta Version"/>
                  	<span class="hidden-xs hidden-sm">Beta Version</span>
                  </span>
                 <%--  <span>
					 <a href="https://thanecity.gov.in/index.php" target="_blank" class="box-shadow-none" title="">
						<i class="fa fa-arrow-circle-o-left" aria-hidden="true"></i>
						<span class="hidden-sm hidden-xs">
							<spring:message code="theme3.portal.prev.web" text="Go To Previous Website" />
						</span>
					 </a>
				  </span> --%>
                  
                  <span class="hidden-lg hidden-md"> <!-- hidden-sm -->
                  	<a id="mobile-button" href="javascript:void(0);" aria-label="Menu"><i class="fa fa-align-justify" aria-hidden="true"></i></a>
                  </span> 
                 <!--  <div id="google_translate_element" class="hidden-xs hidden-sm hidden-md" style="display:inline-block;"></div> -->
               </div>
            </div>
            <!--  Start of code by ABM2144 for Social Media Section on 21-05-2019 -->
            <%-- <c:if test="${command.userSession.socialMediaMap.size() gt 0}">
               <div class="sticky-container1">
                  <ul class="sticky">
                     <c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
                        <li><a href="${media.value}" target="new_${ count.count}">
                           <i class="fa fa-${media.key}"> <span class="hidden">${media.key}</span></i>
                           Like Us on ${media.key}</a>
                        </li>
                     </c:forEach>
                  </ul>
               </div>
            </c:if> --%>
            <!--  End of code by ABM2144 for Social Media Section on 21-05-2019 -->            
         <!-- </div> -->
         
         <!-- Mobile View tab links -->
         <div id="navbar" class="container" style="position:fixed;top:43px;width:100%;display:none;">
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
                     <%-- <li class="parent">
						<a href="javascript:void(0)" role="link" class="internal">
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
					 <li class="parent">
					 	<a class="dropdown-toggle" href="javascript:void(0);">
                           <spring:message code="theme3.portal.more" text="More"/>
                        </a>
                        <ul>
                        	<li class="blink">
								<a href="CitizenHome.html?newsAndEvent" class="internal">
									<span><spring:message code="theme3.portal.press.release" text="Press Release" /></span>
								</a>
							</li>
							<%-- <li class="blink">
								<a href="" class="internal">
									<span><spring:message code="News" text="News" /></span>
								</a>
							</li>
							<li class="blink">
								<a href="" class="internal">
									<span><spring:message code="theme3.portal.events" text="Events" /></span>
								</a>
							</li>
							<li class="blink">
								<a href="" class="internal">
									<span><spring:message code="theme3.portal.services" text="Services" /></span>
								</a>
							</li>
							<li class="blink">
								<a href="Content.html?links&page=Photo Gallery" class="internal">
									<span><spring:message code="theme3.portal.gallery" text="Gallery" /></span>
								</a>
							</li>
							<li class="blink">
								<a href="Content.html?links&page=Video Gallery" class="internal">
									<span><spring:message code="theme3.portal.videos" text="Videos" /></span>
								</a>
							</li> --%>
							<li class="blink">
								<a href="./SectionInformation.html?editForm&rowId=36" class="internal">
									<span><spring:message code="theme3.portal.rti" text="Right To Information" /></span>
								</a>
							</li>
							<%-- <li class="blink">
								<a href="https://thanecity.gov.in/downloads.php" target="_blank" class="internal">
									<span><spring:message code="theme3.portal.downloads" text="Downloads" /></span>
								</a>
							</li>
							<li class="blink">
								<a href="https://thanecity.gov.in/career.php" target="_blank" class="internal">
									<span><spring:message code="theme3.portal.career" text="Career" /></span>
								</a>
							</li>
							<li class="blink">
								<a href="https://mail.thanecity.gov.in/mail/" target="_blank" class="internal">
									<span><spring:message code="theme3.portal.email" text="Email" /></span>
								</a>
							</li> --%>
							<li>
								<a href="./SectionInformation.html?editForm&rowId=351" class="flash-text">
									<span><spring:message code="theme3.portal.corona.covid.19" text="Corona (Covid-19)"/>
									<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new"/></span>
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
		<div class="logo">
			<a href="CitizenHome.html"><c:forEach
					items="${userSession.logoImagesList}" var="logo" varStatus="status">
					<c:set var="parts" value="${fn:split(logo, '*')}" />
					<c:if test="${parts[1] eq '1'}">
						<img src="${parts[0]}" class="pull-left hidden-xs img-responsive"
							
							alt="<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if><c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> logo">
					</c:if>
				</c:forEach> <c:if test="${userSession.languageId eq 1}">
					<h1>${userSession.organisation.ONlsOrgname}</h1>
				</c:if> <c:if test="${userSession.languageId eq 2}">
					<h1>${userSession.organisation.ONlsOrgnameMar}</h1>
				</c:if> </a>
		</div>
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
		<nav class="menu-header-menu-container">
			<ul class="navbar-nav main-navigation">
				  <%-- <li><a href="#"><spring:message code="Services" text="Services"></spring:message></a>
					<ul>	
						<c:if test="${command.themeMap['CITIZEN_SERVICES'] ne 'I'}">
						<li class="smooth-scroll"><a href="#CitizenService"><spring:message code="eip.citizencervices" text="Citizen Services" /></a></li>
						</c:if>
						<li class="smooth-scroll"><a href="#depser"><spring:message code="depServices" text="Department Services" /></a></li>
					</ul>
				  </li>
				  <li class="smooth-scroll"><a href="#nidaan" class=""><spring:message code="quick.header.nidaan" text="Nidaan" /></a></li>
				  <c:if test="${command.themeMap['KEY_CONTACTS'] ne 'I'}">
					<li class="smooth-scroll"><a href="#keyContact"><spring:message code="quick.header.keycontacts" text="Key Contacts" /></a></li>
				  </c:if>
				  <c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">
					<li class="smooth-scroll"><a href="#PublicNotice"><spring:message code="quick.header.notices" text="Notices" /></a></li>
				  </c:if>	
				  <li><a href="#"><spring:message code="others" text="others"></spring:message></a>
					<ul>
						<c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
						<li class="smooth-scroll"><a href="#helpline"><spring:message code="quick.header.helpline.numbers" text="Helpline Numbers" /></a></li>
						</c:if>
						<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
						<li class="smooth-scroll"><a href="#photo-box"><spring:message code="quick.header.gallery" text="Gallery" /> </a></li>
						</c:if>
						<c:if test="${not empty command.aboutUsDescFirstPara }">
						<li class="smooth-scroll"><a href="#about11"><spring:message code="quick.header.about" text="About" /></a></li>
						</c:if>	
					</ul>
				  </li> --%>
				  
				<li>
					<a href="CitizenHome.html" title="<spring:message code="top.home" text="Home"/>">
						<i class="fa fa-home" aria-hidden="true"></i>
					</a>
					<%-- <a href="javascript:void(0)" role="link" class="internal">
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
						      <span>
						         <spring:message code="top.aboutus" text="About Us"></spring:message>
						      </span>
						   </a>
						</li>
						<li>
						   <a href='CitizenContactUs.html'>
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
					</ul> --%>
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
				
				<li><a href="#"><spring:message code="Login" text="Login"/></a>			
					<ul>
						<li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
						<li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span></a></li>
						<li><a href='<spring:message code="service.admin.home.url"/>'  target="_blank"><span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span></a></li>
					</ul>
				</li>
			</ul>
	
			<%-- <ul class="menu">
			<li>Services
			<ul>
			<c:if test="${command.themeMap['CITIZEN_SERVICES'] ne 'I'}">
			<li class="smooth-scroll"><a href="#CitizenService"><spring:message code="eip.citizencervices" text="Citizen Services" /></a></li>
			</c:if>
			<li class="smooth-scroll"><a href="#depser"><spring:message code="depServices" text="Department Services" /></a></li>
			</ul></li>
			<li class="smooth-scroll"><a href="#nidaan" class=""><spring:message code="quick.header.nidaan" text="Nidaan" /></a></li>
			<c:if test="${command.themeMap['KEY_CONTACTS'] ne 'I'}">
			<li class="smooth-scroll"><a href="#keyContact"><spring:message code="quick.header.keycontacts" text="Key Contacts" /></a></li>
			</c:if>
			<c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">
			<li class="smooth-scroll"><a href="#PublicNotice"><spring:message code="quick.header.notices" text="Notices" /></a></li>
			</c:if>	
			<li>Others
			<ul>
			<c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
			<li class="smooth-scroll"><a href="#helpline"><spring:message code="quick.header.helpline.numbers" text="Helpline Numbers" /></a></li>
			</c:if>
			<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
			<li class="smooth-scroll"><a href="#photo-box"><spring:message code="quick.header.gallery" text="Gallery" /> </a></li>
			</c:if>
			<c:if test="${not empty command.aboutUsDescFirstPara }">
			<li class="smooth-scroll"><a href="#about11"><spring:message code="quick.header.about" text="About" /></a></li>
			</c:if>	
			</ul></li>	 --%>
	
				<%-- <li class="smooth-scroll"><a href="#nidaan" class=""><spring:message code="quick.header.nidaan" text="Nidaan" /></a></li> --%>
				
				<%-- <c:if test="${command.themeMap['NEWS'] ne 'I'}">
					<li class="smooth-scroll"><a href="#news"><spring:message code="quick.header.news" text="News" /></a></li>
				</c:if> --%>
				
				
				<%-- <c:if
					test="${not empty command.themeMap['SCHEMES'] && command.themeMap['SCHEMES'] ne 'I'}">
					<li><a href="#schemes"><spring:message code="quick.header.schemes" text="Schemes" /></a></li>
				</c:if> --%>
				
						
					<%-- <li class="smooth-scroll"><a href="javascript:void(0);"><spring:message code="Login" text="Login"/></a>			
					<ul class="drpdwn">
			        <li><a href='javascript:void(0);' onclick="getCitizenLoginForm('N')"><span><spring:message code="CitizenLogin" text="Citizen Login"/></span></a></li>
			        <li><a href='javascript:void(0);' onclick="getAdminLoginForm()"><span><spring:message code="PortalAdmininstratorLogin" text="Portal Admininstrator Login"/></span></a></li>
			        <li><a href='<spring:message code="service.admin.home.url"/>'  target="_blank"><span><spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/></span></a></li>
			       </ul></li> --%>
					
				<%-- <c:if test="${command.themeMap['EXTERNAL_SERVICES'] ne 'I'}">
					<li><a href="#footer"><spring:message code="quick.header.external.services" text="External Services" /></a></li>
				</c:if> --%>
		<!-- 	</ul> -->
		</nav>
	</header>
<!-- Qucik Header Menu End -->
      
      
   </nav>
</header>
<header id="top-header" class="page-heading">
   <div class="container"></div>
</header>