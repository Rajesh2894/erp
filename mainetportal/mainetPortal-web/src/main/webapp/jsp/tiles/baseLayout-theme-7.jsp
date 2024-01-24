<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page errorPage="/jsp/mainet/defaultExceptionView.jsp" %>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>
<!DOCTYPE html>
<c:choose>
	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
		<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" dir="ltr">
	</c:when>
	<c:otherwise>
		<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="hi" dir="ltr">
	</c:otherwise>
</c:choose>

<head>
<meta charset="UTF-8">
<c:if test="${empty pageName}">
<title><tiles:insertAttribute name="page-title"/></title>
</c:if>

<c:if test="${not empty pageName}">
<title><c:out value="${pageName}"/></title>
</c:if>

<link rel="shortcut icon" href="images/ASCL-images/ascl_favicon.ico">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

<!-- SEO Details -->
<c:set value="${userSession.getSEOMasterData()}" var="seo"/>
<c:if test="${metaKeywords eq null}">
<meta name="keywords" content="${seo.keyWord}">
</c:if>
<c:if test="${metaKeywords ne null}">
<meta name="keywords" content="${seo.keyWord},${metaKeywords}">
</c:if>
<meta name="description" content="${seo.description}">

<link href="assets/libs/bootstrap/css/bootstrap.min-theme-1.css" rel="stylesheet" />
<link href="assets/libs/bootstrap/css/bootstrap-theme.min-theme-1.css" rel="stylesheet" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="assets/libs/animate-css/animate.min.css" rel="stylesheet" />
<!-- <link href="assets/libs/pace/pace.css" rel="stylesheet" /> -->
<link href="assets/libs/chosen/chosen.css" rel="stylesheet" type="text/css">
<!-- <link href="assets/css/theme.css" rel="stylesheet" type="text/css" /> -->
<!-- <link href="assets/css/style-responsive.css" rel="stylesheet" /> -->
<link href="assets/libs/jqueryui/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<link href="assets/libs/lightbox/jquery.fancybox.css" rel="stylesheet" type="text/css" />
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css"/>
<link href="css/QandA/alertify.css" rel="stylesheet" type="text/css"/>
<link href="assets/libs/owl-carousel/owl.carousel.css" rel="stylesheet" type="text/css" />
<link href="assets/libs/jqueryui/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css"/>

<link href="assets/libs/jquery-datatables/css/responsive.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="assets/libs/jquery-gray/gray.min.css" rel="stylesheet" type="text/css"/>

<link href="assets/libs/smartmenus/sm-blue.css" rel="stylesheet" type="text/css"/>
<link href="assets/libs/smartmenus/sm-core-css.css" rel="stylesheet" type="text/css"/>
<link href="assets/libs/password-validation/css/password-strength.css" rel="stylesheet" type="text/css"/>
<link href="assets/libs/jquery-desoslide/css/jquery.desoslide.min.css" rel="stylesheet" type="text/css"/>
<style media="screen">
	html, body{font-size: 93% !important;}
</style>

<c:choose>
	<c:when test="${ cookie['accessibilitytextsize'].value =='LGT' }">
		<style media="screen">
			html, body{font-size:98% !important;}
			/* .navigation{height: 485px !important;} */
		</style>
		
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='LGR' }">
		<style media="screen">
			html, body{font-size: 97% !important;}
			.service-box .service-icon .front-content .fa-list_AS:before,.service-box .service-icon .front-content .fa-list_WT:before,.service-box .service-icon .front-content .fa-list_RL:before,.service-box .service-icon .front-content .fa-list_AS:before{font-size: 1.2em !important;}
			
		</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='MDM' }">
		<style media="screen">
			html, body{font-size: 96% !important;}			
		</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='SLR' }">
		<style media="screen">
			html, body{font-size: 95% !important;}
		</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='SLT' }">
		<style media="screen">
			html, body{font-size: 94% !important;}
		</style>
	</c:when>
</c:choose>
<script src="js/eip/citizen/baseLayout.js" ></script> 
<!-- <script src="assets/libs/jquery/jquery.min.js" ></script>  -->
<script src="assets/libs/jquery/jquery-3.7.1.min.js" ></script>
<script src="assets/libs/jquery/jquery.validate.min.js" ></script> 
<script src="js/mainet/validation/globalValidation.js" ></script>
<script src="assets/libs/jqueryui/jquery-ui.min.js" ></script>
<script src="assets/libs/superfish/superfish.min.js" ></script>
<script src="assets/libs/jquery-desoslide/js/jquery.desoslide.min.js"></script>
<script src="assets/libs/smartmenus/jquery.smartmenus.min.js" ></script>
<script src="assets/libs/password-validation/js/password-strength.js"></script>

<c:choose>
	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
		<script src="assets/libs/jqueryui/grid.locale-en.js" ></script>
	</c:when>
	<c:otherwise>
		<script src="assets/libs/jqueryui/grid.locale-hi.js" ></script>
	</c:otherwise>
</c:choose>



<!-- <script src="assets/libs/jqueryui/grid.locale-en.js" ></script> -->
<script src="assets/libs/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/libs/owl-carousel/owl.carousel.min.js"></script>
<script src="assets/libs/jqueryui/jquery-ui-timepicker-addon.js" ></script>
<!--<script  src="js/mainet/commonPhonetic.js"></script>

<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
<script  src="js/mainet/hindi.js"></script>
</c:if>-->
<%-- <c:choose>
	<c:when test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'UAD'}"> --%>
	<c:choose>
		<c:when test="${ cookie['accessibilityCol'].value =='B' and cookie['accessibility'].value =='Y'}">
			<link href="assets/css/style-accessibility-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='Y'}">
			<link href="assets/css/style-accessibility-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${cookie['accessibilityCol'].value =='G' and cookie['accessibility'].value =='Y'}">
			<link href="assets/css/style-accessibility-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${ request.getParameter('accessibility')=='Y' or cookie['accessibility'].value =='Y'}">
			<link href="assets/css/style-accessibility-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='M'}">
			<link href="assets/css/style-orange-theme-2.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${cookie['accessibilityCol'].value =='G' and cookie['accessibility'].value =='M'}">
			<link href="assets/css/style-green-theme-2.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${ cookie['accessibility'].value =='M' or empty cookie['accessibility'].value }">
			<link href="assets/css/style-blue-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/print-theme-4.css" media="print" rel="stylesheet" type="text/css"/>
		
				<script>
			$(document).ready(function($){
		 	var d = new Date();
		    d.setTime(d.getTime() + (1*24*60*60*1000));
			var expires = "expires=" + d.toGMTString();
			document.cookie = "accessibility"+"="+'M'+"; "+expires; 
			document.cookie="pageload"+"="+'1'+"; "+expires; 
			});
		</script>
		</c:when>
		</c:choose>
	<%-- </c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${ cookie['accessibilityCol'].value =='B' and cookie['accessibility'].value =='Y'}">
			<link href="assets/css/style-accessibility-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='Y'}">
			<link href="assets/css/style-accessibility-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${cookie['accessibilityCol'].value =='G' and cookie['accessibility'].value =='Y'}">
			<link href="assets/css/style-accessibility-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${ request.getParameter('accessibility')=='Y' or cookie['accessibility'].value =='Y'}">
			<link href="assets/css/style-accessibility-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='M'}">
			<link href="assets/css/style-orange-theme-2.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${cookie['accessibilityCol'].value =='G' and cookie['accessibility'].value =='M'}">
			<link href="assets/css/style-green-theme-2.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
		</c:when>
		<c:when test="${ cookie['accessibility'].value =='M' or empty cookie['accessibility'].value }">
			<link href="assets/css/style-blue-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/dashboard-theme-7.css" rel="stylesheet" type="text/css"  />
			<link href="assets/css/print-theme-4.css" media="print" rel="stylesheet" type="text/css"/>
		
				<script>
			$(document).ready(function($){
		 	var d = new Date();
		    d.setTime(d.getTime() + (1*24*60*60*1000));
			var expires = "expires=" + d.toGMTString();
			document.cookie = "accessibility"+"="+'M'+"; "+expires; 
			});
		</script>
		</c:when>
		</c:choose>
	</c:otherwise>
</c:choose> --%>
<script>
(function($){
  $(document).ready(function(){
	  $('.key-in .list-group').slimScroll({
		    color: '#000',
		    size: '10px',
		    height: '400px',
		    alwaysVisible: true,
				
		});  
    var example = $('#tabmenuhover').superfish();
  });
})(jQuery);




</script>
</head>

<noscript>	
	<p class="text-center">This page is trying to run JavaScript and your browser either does not support JavaScript or you may have turned-off JavaScript. If you have disabled JavaScript on your computer, please turn on JavaScript, to have proper access to this page.</p>
</noscript>
<form:form id="postMethodFormSuccess" method="POST" class="form"></form:form>
<body>
	<input type="hidden" id="chkFirstload" value="${ cookie['pageload'].value}" />
	<div id="text-resize">
	<a tabindex="-1" id="MainContent"></a>
	<%-- <c:choose>
		<c:when test="${userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'UAD'}">
			
		 	<tiles:insertAttribute name="smartcityapp-header"/>
			<div class="content-page"><tiles:insertAttribute name="app-body"/> </div> 
			<tiles:insertAttribute name="smartcityapp-footer"/>
		</c:when>	
		<c:otherwise>
			<tiles:insertAttribute name="app-header"/>
			<div class="content-page"><tiles:insertAttribute name="app-body"/> </div> 
			<tiles:insertAttribute name="app-footer"/>
		</c:otherwise>
	</c:choose> --%>
			<tiles:insertAttribute name="app-header"/>
				<div class="content-page"><tiles:insertAttribute name="app-body"/> </div> 
			<tiles:insertAttribute name="app-footer"/>
		
	<!-- Pop up Dilog  -->
	<div class="msg-dialog-box ok-msg" style="display: none;"></div>
	<div class="popup-dialog dialog" style="display: none;"></div>
	<div class="child-popup-dialog dialog" style="display: none;"></div>
	<div class="online-service-dialog dialog" style="display: none;"></div>
	<div class="error-dialog" style="display: none;"></div>
	</div>

<!-- the overlay modal element -->
<div class="md-overlay"></div>
<!-- End of eoverlay modal --> 
<script>
    var resizefunc = [];
</script> 

<script  src="assets/libs/bootstrap/js/bootstrap.min.js" ></script> 
<!-- <script  src="assets/libs/pace/pace.min.js"></script> Loader   -->
<script  src="assets/js/init.js"></script> <!-- Wedgets Control --> 
<script  src="assets/libs/lightbox/jquery.fancybox.min.js"></script> <!-- LightBox --> 
<script  src="assets/js/jquery.scrollbox.min.js"></script><!-- Scroll Announcement plugins --> 
<!-- <script  src="assets/js/menu.min.js"></script> Top Menu  -->
<script  src="assets/libs/chosen/chosen.jquery.min.js"></script> 
<script  src="js/mainet/framework-api.min.js"></script>
<c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname ne 'NOUSER' }">
<script  src="js/mainet/jquery.jqGrid.min.js"></script>
<script  src="js/mainet/framework.grid.min.js"></script>

</c:if>
<script  src="js/mainet/login.js"></script>
<script  src="js/mainet/script-library.js"></script>
<script src="js/eip/citizen/landingPage.js" ></script>
<c:choose>
	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
		<script  src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
	</c:when>
	<c:otherwise>
		<script  src="assets/libs/jquery-datatables/js/jquery.dataTables.hi.min.js"></script>
	</c:otherwise>
</c:choose>

<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.responsive.min.js"></script>

<script src="assets/libs/jquery-gray/jquery.gray.min.js"></script>

<!-- JS for onPageScroll effects -->
<script src="assets/libs/jquery-waypoints/jquery.waypoints.min.js"></script>
<!-- JS for onPageScroll effects ends -->

<script>
	//JS to get the element appear on scroll
	var contentWayPoint = function() {
		var i = 0;
		$('.element-animate').waypoint( function( direction ) {
			if( direction === 'down' && !$(this.element).hasClass('element-animated') ) {
				i++;
				$(this.element).addClass('item-animate');
				setTimeout(function(){
					$('body .element-animate.item-animate').each(function(k){
						var el = $(this);
						setTimeout( function () {
							var effect = el.data('animate-effect');
							if ( effect === 'fadeIn') {
								el.addClass('fadeIn element-animated');
							} else if ( effect === 'fadeInLeft') {
								el.addClass('fadeInLeft element-animated');
							} else if ( effect === 'fadeInRight') {
								el.addClass('fadeInRight element-animated');
							} else {
								el.addClass('fadeInUp element-animated');
							}
							el.removeClass('item-animate');
						},  k * 100);
					});
				}, 100);
			}
		} , { offset: '95%' } );
	};
	contentWayPoint();
	// JS to get the element appear on scroll ends
</script>


<c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname eq'NOUSER' }" var="user">
<input type="hidden" id="gotohome" name="gotohome" value="ULBHome.html?resetULB&orgId=${command.appSession.superUserOrganization.orgid}" />
<script>
$(function(){
	document.onkeyup = function(e) {
		var hv = $('#gotohome').val();
		 if ((e.altKey && e.which == 77) || (e.altKey && e.which == 109) ) {		//alt+M/m (Go to home page)
			  window.location.href=hv;
			  }
		};
	});

</script>
</c:if>

<!-- Add fancyBox main JS and CSS files --> 
<script>
$(document).ready(function() {
	$('.fancybox').fancybox();
});
$(window).on('load', function(){
	 $('.columns-multilevel').slimScroll({
	   	height: '400px',
		color: '#fff',
		alwaysVisible: true
	});
	});
</script>
<c:choose>
	<c:when test="${ cookie['accessibilitytextsize'].value =='LGT' }">
	<style>
	.service-content-new ul li{ font-size: 1em !important;}
	.service-box .service-icon .front-content .fa-list_AS:before,.service-box .service-icon .front-content .fa-list_WT:before,.service-box .service-icon .front-content .fa-list_RL:before,.service-box .service-icon .front-content .fa-list_AS:before{font-size: 1.2em !important;}
	.top-line div.last, .top-line div.last a{font-size: 0.9em;}
	.top-line p, .top-line a{font-size: 1em;}
	</style>
	</c:when>
	</c:choose>
<c:if test="${ cookie['accessibilitytextsize'].value =='LGT' ||  cookie['accessibilitytextsize'].value =='LGR'}">
<style>
	.drag1 .helpline span.list-number {font-size: 1.3em !important;font-weight: 600 !important;}
</style>
		
<script>
		if ( $(window).width() > 739) {      
			 	$('.col-lg-6 .dark-blue').slimScroll({
			    color: '#000',
			    size: '10px',
			    height: '390px',
			    alwaysVisible: true
				});
			}
		</script>

</c:if>

<c:if test="${cookie['accessibility'].value =='Y'}">
	<script>
	$(function(){
	 if ((navigator.userAgent.indexOf('MSIE') != -1) || (navigator.userAgent.match(/Trident\/7\./)) || (navigator.userAgent.indexOf('Edge') != -1))  { 
			  $('img').addClass('grayscale'); 
		}
	});
	</script>
</c:if>
<script>
setTimeout(function(){
 	$('#myCarousel .item').not(':first').removeClass('active'); 
 	},100) 
 	
 	
 	
 	
</script>
</body>
</html>