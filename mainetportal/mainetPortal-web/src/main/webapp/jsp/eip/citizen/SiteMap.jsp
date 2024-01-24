<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%--
   <style>
   .sitemap ul.tree input[type="submit"] {
       background: none;
       border: 0;
   }
   </style>
    --%>
<meta charset="utf-8">
<script>
   function showConfirm(form){
   
   	var childDivName	=	'.child-popup-dialog';
   	var message=' ';
   	var msg1 = getLocalMessage("tp.red1");
   	var msg2 = getLocalMessage("tp.red2");
   	var btnMsg=getLocalMessage("bt.ok");
   	
   	    message	+=	'<div class="sucess">';
           message	+=	'<h3>'+msg1+' </br>'+msg2+'</h3>';
           message	+=  '<div class="btn_fld">';    		
   		message	+=	'<input type="button" class="css_btn" value=' +btnMsg+ ' onclick=OpenNewTab("'+form+'"); />' ;
   		message	+=	'</div>';
   	    message	+=	'</div>';
   
   	
   	$(childDivName).html(message);
       showModalBox(childDivName);
   }
   
   function OpenNewTab(url)
   {
     var win=window.open(url,'_blank');
     win.focus();
     closeFancyOnLinkClick('.child-popup-dialog');
    
   }
   
</script>
<script>
   $(document).ready(function($){
   	$('#categTreeID > ul.tree > li > ul').switchClass("dropdown-menu","tree");
   	$('#categTreeID > ul.tree > li.blink').css({'display' : 'block'});
   	$('.input-link').addClass( "tree " ).removeClass( "input-link" )
   	
       $(document).find('#mega-menu-1').css({'display' : 'block'});
   });
</script>
<ol class="breadcrumb"  id="CitizenService">
   <li class="breadcrumb-item">
      <a href="CitizenHome.html">
         <i class="fa fa-home"></i> 
         <spring:message code="home"/>
      </a>
   </li>
   <li class="breadcrumb-item active">
      <spring:message code="eip.sitemap"/>
   </li>
</ol>
<div class="container-fluid dashboard-page">
   <div class="col-sm-12" id="nischay">
      <h2>
         <spring:message code="eip.sitemap"/>
      </h2>
      <div class="widget-content padding dashboard-page">
         <div class="listpage_content sitemap">
            <div class="col-md-4">
               <div class="categTree">
                  <a title="Home" href="CitizenHome.html">
                     <spring:message code="menu.home"/>
                  </a>
                  <ul class="tree">
                     <li>
                        <a href="CitizenAboutUs.html">
                           <spring:message code="top.aboutus"/>
                        </a>
                     </li>                     
                     <li>
                        <a href="CitizenFAQ.html?getFAQ">
                           <spring:message code="top.faq"/>
                        </a>
                     </li>
                     <li>
                        <a href="CitizenContactUs.html">
                           <spring:message code="eip.citizen.footer.contactUs"/>
                        </a>
                     </li>
                     <li>
                        <a href="#" onclick="openPopup('CitizenFeedBack.html?publishFeedBack')">
                           <spring:message code="eip.citizen.footer.feedback" />
                        </a>
                     </li>                     
                     <li>
                        <a href="ScreenReader.html">
                           <spring:message code="ScreenReader" text="Screen-Reader"/>
                        </a>
                     </li>
                     <li>
                        <a href="Websitepolicy.html">
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
                  </ul>
               </div>
            </div>
            <div class="col-md-4">
               <div class="categTree">
                  <a title="Home" href="CitizenHome.html">
                     <spring:message code="eip.serviceType"/>
                  </a>
                  <ul class="tree">                     
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
               </div>
            </div>
            <div class="col-md-4">
               <div id="categTreeID" class="categTree">
                  <a title="QuickLinks" href="CitizenHome.html">
                     <spring:message
                        code="left.left.quicklinks" text="quicklinks" />
                  </a>
                  <ul class="tree">
                     <%
                        if (session.getAttribute("quickPayFlag") != null
                        		&& session.getAttribute("quickPayFlag").equals("KDMC")) {
                        %>
                     <li onclick="getCitizenLoginForm('Y')"><a
                        href="javascript:void(0);">Quick Pay</a></li>
                     <%
                        }
                        %>
                     <c:if test="${userSession.languageId eq 1}">
                        ${command.userSession.quickLinkEng}
                     </c:if>
                     <c:if test="${userSession.languageId eq 2}">
                        ${command.userSession.quickLinkReg}
                     </c:if>
                     <li class="parent">
                        <a href="javascript:void(0);"
                        class="blink"
                        title="
                        <spring:message code="lbl.webArchives" text="Web Archives"/>
                        "
                        >
                        <spring:message code="lbl.webArchives"
                           text="Web Archives" />
                        </a>
                        <ul class="tree">
                           <li class="blink">
                              <a href="DataArchival.html?archivedData"
                              title="
                              <spring:message code="lbl.archiveData" text="Archive Data"/>
                              ">
                              <spring:message
                                 code="lbl.archiveData" text="Archive Data" />
                              </a>
                           </li>
                        </ul>
                     </li>
                  </ul>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>