<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}"></c:if>
<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">
   <div class="footer-logos">
      <div class="clear">
         <!-- <div class="row"> -->
            <div class="col-md-12 col-lg-12 white">
               <div id="carosel">
                  <div class="owl-carousel owl-theme">
                     <div class="item"><a href="http://digitalindia.gov.in/" target="_blank"><img src="assets/img/digital-india.png" class="img-responsive" alt="Digital India - Power to Empower"></a></div>
                     <div class="item"><a href="http://www.pmindia.gov.in/en/" target="_blank"><img src="assets/img/pmindia.png" class="img-responsive" alt="PM India"></a></div>
                     <div class="item"><a href="https://www.india.gov.in/" target="_blank"><img src="assets/img/india-gov.png" class="img-responsive" alt="India.gov.in"></a></div>
                     <div class="item"><a href="http://goidirectory.nic.in" target="_blank"><img src="assets/img/goe-directory.png" class="img-responsive" alt="Goe Directory"></a></div>
                     <div class="item"><a href="https://incredibleindia.org/" target="_blank"><img src="assets/img/incredible-india.png" class="img-responsive" alt="Incredible india"></a></div>
                     <div class="item"><a href="http://www.makeinindia.com/home" target="_blank"><img src="assets/img/makeinindia.png" class="img-responsive" alt="Make in India"></a></div>
                     <div class="item"><a href="https://www.mygov.in/" target="_blank"><img src="assets/img/mygov.png" class="img-responsive" alt="My Gov"></a></div>
                     <div class="item"><a href="https://pmnrf.gov.in/" target="_blank"><img src="assets/img/pmnrf.png" class="img-responsive" alt="Prime Minister's National Relief Fund"></a></div>
                     <div class="item"><a href="https://data.gov.in/" target="_blank"><img src="assets/img/data-gov.png" class="img-responsive" alt="open government data"></a></div>
                     <div class="item"><a href="https://services.india.gov.in/" target="_blank"><img src="assets/img/gevernment-portal.jpg" class="img-responsive" alt="service portal"></a></div>
                  </div>
               </div>
            </div>
         <!-- </div> -->
      </div>
   </div>
  <%--  <div class="navigation" style="display:block">
      <div class="container-fluid">
         <div class="rows row-eq-height">
            <div class="col-lg-12" style="width:100%">
               <div class="columsns-multilevel">
                  <ul class="section-nav">
                     <li class="parent">
                        <a href='javascript:void(0)'>
                           <span>
                              <spring:message code="top.home"></spring:message>
                           </span>
                        </a>
                        <ul>
                           <li>
                              <a href='CitizenHome.html'>
                                 <span>
                                    <spring:message code="top.home"></spring:message>
                                 </span>
                              </a>
                           </li>
                           <li>
                              <a href='CitizenAboutUs.html'>
                                 <span>
                                    <spring:message code="top.aboutus"></spring:message>
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
                        </ul>
                     </li>
                     <c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
                     <c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
                     <li class="parent">
                        <a href="javascript:void(0);" class="blink" >
                        <spring:message code="lbl.webArchives" text="Web Archives"/>
                        </a>
                     </li>
                     <li class="blink">
                        <a href="DataArchival.html?archivedData" >
                        <spring:message code="lbl.archiveData" text="Archive Data"/>
                        </a>
                     </li>
                  </ul>
               </div>
            </div>
         </div>
      </div>
   </div> --%>
   
   <!-- VISITOR COUNT-->
   <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
      <c:if test="${ not empty totalRegisUser}">
         <div class="white container-fluid visitors-counter"> 
            <div class="col-sm-3 col-md-3 col-lg-3 col-xs-6">
               <ul>
                  <li>
                     <div class="visitor-count">
                      <span
                         class="animate-number badge"
                         data-value="<%=request.getSession().getAttribute("countuser")%>"
                         id="countuser2" data-duration="3000">0</span>
			</div>
                     <div class="visitor-text">
                     	<i class="fa fa-pie-chart text-primary"></i> 
                     	<spring:message code="TotalVisitors" text="Total Visitors" />
                     </div>
                     
                  </li>
               </ul>
            </div>
            <div class="col-sm-3 col-md-3 col-lg-3 col-xs-6">
               <ul>
                  <li>
                  	<div class="visitor-count">
                   	<span
                         class="animate-number badge"
                         data-value="<%=request.getSession().getAttribute("loggedInUser")%>"
                         data-duration="3000" id="loggedInUser">0</span>
                     </div>
                     <div class="visitor-text">
                      <c:set var="orgId"
                         value="${userSession.organisation.orgid}" />
                      <img
                         src="<spring:message code="hitcounter.URL" arguments="${orgId}"/>"                        
                      Alt="
                      <spring:message code="left.TotalVisitors"/>
                      " height="20"
                      width="129" class="hide" /> <i
                         class="fa fa-calendar-check-o text-danger"></i> 
                      <spring:message code="TodayVisitors" text="Today Visitors" />
			</div>
                  </li>
               </ul>
            </div>
            <div class="col-sm-3 col-md-3 col-lg-3 col-xs-6">
               <ul>
                  <li>
                     <div class="visitor-count">
                      <span
                         class="animate-number badge"
                         data-value="<%=request.getSession().getAttribute("activeuser")%>"
                         data-duration="3000" id="activeuser">0</span>
                     </div>
                     <div class="visitor-text">
                     	<i class="fa fa-user text-success"></i> 
                     	<spring:message code="ActiveUsers" text="Active Users" />
                     </div>
                  </li>
               </ul>
            </div>
            <div class="col-sm-3 col-md-3 col-lg-3 col-xs-6">
               <ul>
                  <li class="facebook-color">
                     <div class="visitor-count">
                      <span
                         class="animate-number badge"
                         data-value="<%=request.getSession().getAttribute("totalRegisUser")%>"
                         data-duration="3000" id="totalRegisUser">0</span>
                     </div>
                     <div class="visitor-text">
                     	<i class="fa fa-users text-info"></i>
                     	<spring:message code="RegisterUsers" text="Register Users" />
                     </div>
                  </li>
               </ul>
            </div>
         </div>
      </c:if>
   </c:if>
</c:if>
<div class="clear"></div>
<%--  <footer class="clearfix">
   <div class="container">
   <!--Scroll To Top--> 
      <a class="tothetop" href="javascript:void(0);"><strong class="fa fa-angle-up"></strong><span><spring:message code="Top" text="Top"/></span></a> 
    <div class="pull-left">
       <ul>
         <li><img src="assets/img/css.jpg" class="holder_img" alt="Loading please wait" title="W3C CSS standards"></li>
         <li><img src="assets/img/html.jpg" class="holder_img" alt="Loading please wait" title="W3C XHTML standards"></li>
         <li><img src="assets/img/uc.jpg" class="holder_img" alt="Loading please wait" title="GIGW standards"></li>
         <li><img src="assets/img/w3c.png" class="holder_img" alt="Loading please wait" title="W3C WAI-AA WCAG standards"></li>
       </ul>
     </div>
     <div class="footer-links pull-right">
   		 	<a class="fancybox fancybox.iframe" href="./jsp/tiles/EIPguidelines.jsp"><spring:message code="eip.citizen.online.guidelines" text=""/></a> 
         <a onclick="openPopup('CitizenFeedBack.html?publishFeedBack')" href='javascript:void(0);'><span> <spring:message code="Feedback" text="Feedback"/></span></a>
         <a href="ScreenReader.html" title="Screen-Reader"><spring:message code="ScreenReader" text="Screen-Reader"/></a>  
         <a href="Privacypolicy.html" title="Privacy Policy"><spring:message code="PrivacyPolicy" text="Privacy Policy"/></a> 
         <a href="Termsconditions.html" title="Terms & Conditions"><spring:message code="TermsConditions" text="Terms & Conditions"/></a> 
         <a href="Refundcancellation.html" title="Refund / Cancellation"><spring:message code="RefundCancellation" text="Refund / Cancellation"/></a> 
        
          <a href="Copyright.html" title="Copyright"><spring:message code="eip.Copyright" text="Copyright"/></a>
   	        <c:set var="lastUpdated" value="${userSession.lastUpdated}"></c:set>
   	        <fmt:formatDate type = "date" value="${lastUpdated}"  dateStyle="SHORT" pattern="dd/MM/yyyy" var="formatedDt"/>
   	       <a href="javascript:void(0)" title="Last Updated on ${formatedDt}"><spring:message code="eip.update.dt" text="Last Updated on"/>&nbsp${formatedDt}</a> 
     </div>
    </div>
   </footer> --%>

<div class="footer-blue">
	<footer class="clearfix">
      <!--Scroll To Top--> 
      <a class="tothetop" href="javascript:void(0);">
         <!-- <strong class="fa fa-angle-up"></strong>  -->
         <i class="fa fa-angle-double-up" aria-hidden="true"></i>
         <span>
            <spring:message code="Top" text="Top"/>
         </span>
      </a>
      <div class="col-sm-4 col-md-6 website-standards">
         <div class="pull-left ">
            <ul>
               <li><img src="assets/img/css.jpg" class="holder_img" alt="W3C CSS standards" ></li>
               <li><img src="assets/img/html.jpg" class="holder_img" alt="W3C XHTML standards"></li>
               <li><img src="assets/img/uc.jpg" class="holder_img" alt="GIGW standards" ></li>
               <li><img src="assets/img/w3c.png" class="holder_img" alt="W3C WAI-AA WCAG standards"></li>
            </ul>
         </div>
      </div>
      <div class="footer-links col-sm-8  col-md-6 text-center">
         <ul>
            <li>
               <i class="fa fa-info-circle" aria-hidden="true"></i>
               <a href="#" title="Content managed by Suda" >
                  <spring:message code="suda.content.managed" text="Content managed by Suda"/>
               </a>
            </li>
            <li>
               <i class="fa fa-info-circle" aria-hidden="true"></i>
               <a href="http://abmindia.com/" title="Website designed, developed by ABM" >
                  <spring:message code="website.design" text="Website designed, developed by ABM"/>
               </a>
            </li>
            <li>
            	<a id="desktop-version">
            		<i class="fa fa-desktop margin-right-5" aria-hidden="true"></i>
            		<spring:message code="theme3.portal.desktop.version" text="Desktop Version"/>
            	</a>
				<a id="mobile-version">
					<i class="fa fa-mobile margin-right-5" aria-hidden="true"></i>
					<spring:message code="theme3.portal.mobile.version" text="Mobile"/>
					<i class="fa fa-tablet margin-right-5" aria-hidden="true"></i>
					<spring:message code="theme3.portal.tab.version" text="Tab Version"/>
				</a>
            </li>
         </ul>
      </div>
	</footer>
	<div class="footer-link1">
      <div class="footer-link1 col-sm-12 text-center">
         <ul>
            <li>
               <a href="ScreenReader.html" title="Screen-Reader" >
                  <spring:message code="ScreenReader" text="Screen-Reader"/>
               </a>
            </li>
            <li>
               <a href="Websitepolicy.html" title="Website Policies" >
                  <spring:message code="website.policies" text="Website Policies"/>
               </a>
            </li>
            <li>
               <a href="help.html" title="Help" >
                  <spring:message code="help.footer" text="Help"/>
               </a>
            <li>
               <a href="webInfo.html" title="Web Information Manager" >
                  <spring:message code="web.information" text="Web Information Manager"/>
               </a>
            </li>
            <li>
               <a href="http://abmindia.com/" title="Copyright &copy; ABM 2018" target="_blank">
                  <spring:message code="Copyright" text="Copyright &copy; ABM 2018"/>
               </a>
            </li>
            <c:set var="lastUpdated" value="${userSession.lastUpdated}"></c:set>
          <%--   <fmt:formatDate type = "date" value="${lastUpdated}"  dateStyle="SHORT"  pattern="dd/MM/yyyy" var="formatedDt"/> --%>
           <fmt:formatDate type = "both" value="${lastUpdated}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy  HH:mm a"  var="formatedDt"/>
            <li>
               <a href="javascript:void(0)" title="
               <spring:message code="last.update" text="Last Updated on ${formatedDt}"/>
               " >
               <spring:message code="last.update" text="Last Updated on ${formatedDt}"/>
               &nbsp; ${formatedDt}</a> 
            </li>
         </ul>
      </div>
      <div class="clear"></div>
	</div>
</div>
<script>
   $(function(){
	$(".navigation a[href='javascript:void(0);']").removeAttr('href');	
   	$(".section-nav li a").each(function(){
   		if($(this).html()=="Dashboard")	{
   			$(this).css('display','none');
   		}
   	});
   	$('.columsns-multilevel').slimScroll({
   	    color: '#313131',
   	    size: '4px',
   	    height: '285px',
   	    alwaysVisible: true
   	});
   });
</script>