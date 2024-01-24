<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<style>
.sitemap ul.tree input[type="submit"] {
    background: none;
    border: 0;
}

</style>

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

function openInformation(data) {
	alert();
    var result = __doAjaxRequest("CitizenContactUs.html?showPage", 'GET', data, false);
    /* myWindow.document.write(result); */
    myWindow.document.write(result);
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

 <ol class="breadcrumb">
      <li class="breadcrumb-item"><a href="CitizenHome.html"><i class="fa fa-home"></i>  Home</a></li>
      <li class="breadcrumb-item active"><spring:message code="eip.sitemap"/></li>
</ol>
     
    
    
    
<div class="container-fluid dashboard-page">
  <div class="col-sm-12" id="nischay">
<h3><spring:message code="sitemap"/></h3>
         <div class="widget-content padding dashboard-page form-horizontal">
<div class="panel-group accordion-toggle" id="accordion_single_collapse">
            <div class="panel panel-default">
              <div class="panel-heading">
              <h4 class="panel-title"><spring:message code="sitemap"/></h4>
              </div>
             
              <div class="panel-collapse collapse in">
              <div class="panel-body">
	<div class="listpage_content sitemap">
        <div class="col-md-4">
        <div class="categTree"> <a title="Home" href="CitizenHome.html"><spring:message code="menu.home"/></a>
        <ul class="tree">
        <li><a href="CitizenAboutUs.html"><spring:message code="top.aboutus"/></a></li>
        <li><a href="CitizenFAQ.html?getFAQ"><spring:message code="top.faq"/></a></li>
        <li><a href="CitizenContactUs.html"><spring:message code="eip.citizen.footer.contactUs"/></a></li>
         <li><a href="CitizenFeedBack.html"><spring:message code="eip.citizen.footer.feedback" /></a></li>
        </ul>
        </div>
        </div>
        <div class="col-md-4">
        <div class="categTree"> <a title="Home" href="CitizenHome.html"><spring:message code="eip.serviceType"/></a>
        <ul class="tree">
       <li><a href="javascript:void(0);" onclick="getAdminLoginForm()"><spring:message code="header.administrative.user" text="Administrative Login"></spring:message></a></li>
      <li><a href="javascript:void(0);" onclick="getAdminLoginForm()"><spring:message code="header.iprd.user" text="IPRD Login"></spring:message></a></li>     
      <li> <a href="javascript:void(0);" onclick="getAdminLoginForm()"><spring:message code="header.departments.user"></spring:message></a></li>
        </ul>
        </div>
       </div> 
     <div class="col-md-4">
       <div id="categTreeID" class="categTree"> <a title="Quick Links" href="CitizenHome.html"><spring:message code="left.left.quicklinks" text="Quick links"/></a>
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
	       	
	        </ul>
        </div> 
        </div>
        
        
        </div>
	
	
		</div>
		</div>
		</div>
		</div>
		
        
     </div>
    </div>
    </div>
