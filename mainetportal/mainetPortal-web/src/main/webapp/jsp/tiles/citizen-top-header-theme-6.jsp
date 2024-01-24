
<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- <style>
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
</style> -->
<script  src="js/eip/citizen/citizen-top-header.js"></script> 
<script src="js/mainet/dashboard/moment.min.js"></script>
<script  src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
<script>
$(function(){$(".navigation a[href='javascript:void(0);']").removeAttr('href');})	
function googleTranslateElementInit() {
  new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');
  $("script").removeAttr("type").removeAttr("charset");
  function changeGoogleStyles() {
      if($('.goog-te-menu-frame').contents().find('.goog-te-menu2').length) {
          $('.goog-te-menu-frame').contents().find('.goog-te-menu2').css({
              'max-width':'100%',
              'overflow-x':'auto',
              'box-sizing':'border-box',
              'height':'auto'
          });
      } else {
          setTimeout(changeGoogleStyles, 50);
      }
  }
  changeGoogleStyles();
}

var headerHeight = $('header').innerHeight();
$(document).ready(function() {
	$('#google_translate_element').bind('DOMNodeInserted', function(event) {
		/* ----- To change the text in google language select starts ----- */
		//$('.goog-te-menu-value span:first').html('Change Language');
		/* ----- To change the text in google language select ends ----- */
		
		/* ----- To set the height of the google translate banner starts ----- */
		$('.goog-te-banner-frame').css({'top':headerHeight+5});
		/* ----- To set the height of the google translate banner ends ----- */
	});
});
</script>
<script type="text/javascript">
history.pushState(null, null, ' ');
window.addEventListener('popstate', function(event) {
  history.pushState(null, null, ' ');
}); 
</script>
<style>
.goog-te-gadget-simple .VIpgJd-ZVi9od-xl07Ob-lTBxed  span{
	color:#000 !important;
}
</style>
<input type="hidden" id="Selectsmfid" name="Selectsmfid" />
<script>
$(document).ready(function(){
	  $("#mobile.nav .parent>a").on("click", function(e){
	    if($(this).parent().has("ul")) {
	      e.preventDefault();
	    }
	    
	    if(!$(this).hasClass("open")) {
	      // hide any open menus and remove all other classes
	      /* $("#mobile.nav ul").slideUp(350);
	      $("#mobile.nav .parent>a").removeClass("open"); */
	      
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


<script>
var idleTime = 15;
$(document).ready(function () {
	debugger;
	

	 document.getElementById("timer").innerText = idleTime;
    // Increment the idle time counter every minute.
     var idleInterval = setInterval(timerIncrement,60000);  // 1 minute
   
   /*  $(this).mousemove(function (e) {
        idleTime = 15;
        document.getElementById("timer").innerText = idleTime;
    });
    $(this).keypress(function (e) {
        idleTime = 15;
        document.getElementById("timer").innerText = idleTime;
    }); */

});

function timerIncrement() {
	debugger;
	var lastloginString = $('#sessionTime').html();
	var lastLoginDate = new Date(lastloginString.replace('IST', ''));
	lastLoginDate = moment(lastLoginDate).format('DD/MM/YYYY HH:mm:ss');
	var loginExpireTime = moment().subtract(15, 'minutes').format('DD/MM/YYYY HH:mm:ss');
	
    idleTime = idleTime - 1;
    document.getElementById("timer").innerText = idleTime;
    console.log(idleTime)
    if(idleTime == 1){
        var beforeTimeout= getLocalMessage("session.beforetimeout.message");
        if (confirm(beforeTimeout) == true) {
        	console.log("idleTime"+idleTime);
            debugger
        	 if(loginExpireTime > lastLoginDate){
            	 
		        		 setTimeout(function(){ 
		      			   alert(getLocalMessage("session.timeout.message"));
		      			   window.location.href= 'LogOut.html';
		      		    }, 200);
		  		   	
		           }else{
		        	   idleTime =15;
		             }
		             		 
        	
          } else {
        	 if(loginExpireTime > lastLoginDate){
	        		 setTimeout(function(){ 
	      			   alert(getLocalMessage("session.timeout.message"));
	      			   window.location.href= 'LogOut.html';
	      		    }, 200);
      		   	
               }else{
            	   idleTime =1;
                 }
          }
        }
    if (idleTime == 0) {
    	document.getElementById("timer").innerText = idleTime;
		   setTimeout(function(){ 
			   alert(getLocalMessage("session.timeout.message"));
			   window.location.href= 'LogOut.html';
		    }, 200);
		   
    }
}
function userLogout(){
	var result = __doAjaxRequest('LogOut.html?logout', 'post', '', false, 'json');
	window.location.href = result;
}
</script>

<header id="citizen-top-header">
<div id="sessionTime" style="display:none;">${userSession.employee.lastLoggedIn }</div>
	<!-- Header-1 starts -->
	<div class="header-1">
		<div class="row website-header">
			<div class="header-options col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<!-- Social Icons starts -->
				<div class="header-skip-to-content">
					<ul>
						<li>
							<a href="#main">
								<i class="fa fa-arrow-circle-o-down" aria-hidden="true"></i>
								<span class="hidden-xs hidden-sm"><spring:message code="Skip" text="Skip to Main Content"/></span>
							</a>
						</li>
					</ul>
				</div>
				<div class="header-social-icons-section">
					<ul>
						<li class="virtual-keyboard hidden-xs hidden-sm">
							<a href="javascript:void(0);" class="virtualKeyboard vk-off">
								<i class="fa fa-keyboard-o" aria-hidden="true"></i>
								<span class="hidden-xs hidden-sm"><spring:message code="theme6.portal.virtual.keyboard" text="Virtual Keyboard"/></span>
							</a>
						</li>
						<%-- <li><a href="javascript:void(0);" class="blue-icon" title="Blue Theme" onclick="setcontrast('B')"></a></li>
						<li><a href="javascript:void(0);" class="yellow-icon" title="Green Theme" onclick="setcontrast('G')"></a></li>
						<li><a href="javascript:void(0);" class="green-icon" title="Dark Green Theme" onclick="setcontrast('DG')"></a></li> --%>
						<%-- <c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
							<c:if test="${media.key ne 'youtube' }">
								<li class="hidden-xs">
									<a href="${media.value}" target="new_${ count.count}" class="${media.key}">
										<i class="fa fa-${media.key}"></i>
									</a>
								</li>
							</c:if>
						</c:forEach> --%>
					</ul>
				</div>
				<!-- Social Icons ends -->
			</div>
			<div class="website-name-main col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="logo-shadow"></div>
				<div class="website-name-container">
					<a class="navbar-brand" href="CitizenHome.html">
						<c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
							<span class="website-logo">
								<c:set var="parts" value="${fn:split(logo, '*')}" />
								<c:if test="${parts[1] eq '1'}">
									<img src="${parts[0]}" class="img-responsive"
									alt="Organisation Logo"
									<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
									<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> Logo
									">
								</c:if>
							</span>
						</c:forEach>
						<span class="website-name wb-nm">
							<c:if test="${userSession.languageId eq 1}">
								${userSession.organisation.ONlsOrgname}
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								${userSession.organisation.ONlsOrgnameMar}
							</c:if>
						</span>
					</a>
				</div>
			</div>
			
			<!-- Website settings starts  -->
				<div class="website-settings col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<ul>
						<li><a href="javascript:void(0);" id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce"><spring:message code="code.a-" text="A-"/></a></li>
						<li><a href="javascript:void(0);" id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal"><spring:message code="code.a" text="A"/></a></li>
						<li><a href="javascript:void(0);" id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase"><spring:message code="code.a+" text="A+"/></a></li>
						<li>
							<a href="AccessibilityLanding.html" title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options">
								<i class="fa fa-adjust" aria-hidden="true">
									<span class="hidden">
										<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options">
									</span>
								</i>
							</a>
						</li>
						<li>
							<a href="sitemap.html" aria-label='<spring:message code="sitemap" text="Sitemap"/>' title="<spring:message code="sitemap" text="Sitemap"/>">
								<i class="fa fa-sitemap" data-toggle="tooltip" data-placement="bottom"></i>
							</a>
						</li>
						<li>
							<c:if test="${userSession.languageId eq 1}">
								<a onclick="changeLanguage('?locale&lang=reg');" href="javascript:void(0);" title="<spring:message code="header.reg" text="header.reg"/>" class="chng-lang">
									<spring:message code="header.reg" text="header.reg" />
								</a>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								<a onclick="changeLanguage('?locale&lang=en');" href="javascript:void(0);" title="<spring:message code="header.eng" text="header.eng"/>" class="chng-lang">
									<spring:message code="header.eng" text="header.eng" />
							   </a>
							</c:if>
						</li>
						<li class="hidden-xs hidden-sm hidden-md">
							<div id="google_translate_element"></div>
						</li>
						<%-- <li class="sml-scr-settings smlScrSettings visible-xs visible-sm">
							<a href="javascript:void(0);">
								<i class="fa fa-share-alt" aria-hidden="true"></i>
							</a>
							<ul>
								<c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
								<c:if test="${media.key ne 'youtube' }">
									<li>
										<a href="${media.value}" target="new_${ count.count}" class="${media.key}">
											<i class="fa fa-${media.key}"></i>
										</a>
									</li>
									</c:if>
								</c:forEach>
							</ul>
						</li>
						<li class="sml-scr-settings smlScrSettings visible-xs visible-sm">
							<a href="javascript:void(0);">
								<i class="fa fa-cog" aria-hidden="true"></i>
							</a>
							<ul>
								<li>
									<a href="javascript:void(0);" id="decfont" data-toggle="tooltip" data-placement="bottom" title="Font Reduce">A -</a>
									<a href="javascript:void(0);" id="norfont" data-toggle="tooltip" data-placement="bottom" title="Font Normal">A</a>
									<a href="javascript:void(0);" id="incfont" data-toggle="tooltip" data-placement="bottom" title="Font Increase">A +</a>
								</li>
								<li>
									<a href="AccessibilityLanding.html" title="<spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options">
										<i class="fa fa-adjust" aria-hidden="true">
											<span class="hidden"><spring:message code="Accessibility" text="Accessibility Options"/>" title="Accessibility Options"></span>
										</i>
									</a>
								</li>
								<li>
									<a href="sitemap.html" aria-label='<spring:message code="sitemap" text="Sitemap"/>' title="<spring:message code="sitemap" text="Sitemap"/>">
										<i class="fa fa-sitemap" data-toggle="tooltip" data-placement="bottom"></i>
									</a>
								</li>
							</ul>
						</li> --%>
					</ul>
				</div>
				<!-- Website settings ends  -->
			
		</div>
	</div>
	<!-- Header-1 ends -->
	
	<!-- Header-2 starts -->
	<div class="header-2">
		<nav class="navbar navbar-expand-sm">
			<div class="main-menu-container col-xs-8 col-sm-10 col-md-10 col-lg-10">
				<ul class="main-navigation-menu">
					<c:if test="${userSession.employee.emploginname ne 'NOUSER'}" var="user">
						<li class="btn-group">
							<a class="btn-link btn-xs dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
									<a href="" onclick="userLogout();"><i class="fa fa-power-off text-red"></i><spring:message code="sign.out" text="Sign Out"/> </a>
								</li>
							</ul>
						</li>
					</c:if>
					<li class="session-timer">
						<a href="javascript:void(0);">
							<span class="colon"><spring:message code="session.time" text="Session Time"/></span><span id="timer"></span><spring:message code="session.minutes" text="Minutes"/>   
						</a>
					</li>
				</ul>
			</div>
			<div class="content-search-main col-xs-4 col-sm-2 col-md-2 col-lg-2">
				<%-- <form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
					<input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
					<div class="content-search"></div>
				</form:form> --%>
				<form:form role="search" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
      				<div class="input-group">
      					<input type="text" class="form-control" name="searchWord" id="search_input" autocomplete="off" required	placeholder="<spring:message code="eip.searchhere" text="search" />">
      					<span class="input-group-btn"><button class="btn" type="Submit" id="searchButton"><strong class="fa fa-search"></strong></button></span>
      				</div>
      			</form:form>
			</div>
		</nav>
	</div>
	<!-- Header-2 ends -->
	
</header>

<form:form id="postMethodForm" method="POST" class="form"></form:form>
<div id="loaderdiv"></div><div class="sloading"><img src="css/images/loader.gif" alt="loading" /></div>