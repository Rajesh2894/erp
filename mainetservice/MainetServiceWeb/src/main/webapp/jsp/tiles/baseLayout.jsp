<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.abm.mainet.common.exception.FrameworkException" %>
<%@ page errorPage="/jsp/mainet/defaultExceptionView.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>
<!DOCTYPE html>
<c:if test="${userSession.languageId eq 1}">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" dir="ltr">
</c:if>
<c:if test="${userSession.languageId eq 2}">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="hi" lang="hi" dir="ltr">
</c:if>
<head>
<meta charset="UTF-8">
<%-- <meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" /> --%>
<%-- <meta name="test" content="${_csrf}"/> --%>
<c:if test="${userSession.checkSudaUatEnv eq 'Y' || userSession.checkThaneProdEnv eq 'Y' }">
	<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
</c:if>
<sec:csrfMetaTags />
<c:choose>
	<c:when test="${userSession.languageId eq 1}"><title>${userSession.organisation.ONlsOrgname}</title></c:when>
	<c:otherwise><title>${userSession.organisation.ONlsOrgnameMar}</title></c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${userSession.orgLogoPath ne null && userSession.orgLogoPath ne ''}"><link rel="shortcut icon" href="${userSession.orgLogoPath}"></c:when>
	<c:otherwise><link rel="shortcut icon" href="assets/img/abm_logo.ico"></c:otherwise>
</c:choose>

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- Base Css Files for Global -->
<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="assets/libs/animate-css/animate.min.css" rel="stylesheet" />
<link href="assets/libs/pace/pace.css" rel="stylesheet" />
<!-- <link href="assets/css/style.css" rel="stylesheet" type="text/css" /> -->
<link href="assets/libs/lightbox/jquery.fancybox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/mainet/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/humanity/jquery.ui.theme.css" />
<%--<script src="js/mainet/ui/jquery-1.10.2.min.js"></script> 
<script src="js/mainet/ui/jquery-migrate-3.3.2.min.js"></script> --%>
<script src="js/mainet/ui/jquery.min.js"></script>
<%-- <script src="js/mainet/ui/jquery-1.11.1.validate.min.js"></script> --%>
<script src="js/mainet/ui/jquery.validate.min.js"></script>
<script src="js/mainet/validation/globalValidation.js"></script>
<script src="js/mainet/ui/jquery-ui.js"></script>
<script src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<link rel="stylesheet" media="all" type="text/css" href="js/mainet/ui/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<link rel="stylesheet" href="assets/libs/chosen/chosen.css">

<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>
<link href="assets/libs/virtual-keyboard/css/keyboard.css" rel="stylesheet" type="text/css"/>
<script src="assets/libs/virtual-keyboard/js/keyboard.js"></script>

<!-- TUI Calender Plugin Libraries CSS -->
<link rel="stylesheet" type="text/css" href="assets/libs/tui.calendar-master/css/tui-time-picker.css">
<link rel="stylesheet" type="text/css" href="assets/libs/tui.calendar-master/css/tui-date-picker.css">
<link rel="stylesheet" type="text/css" href="assets/libs/tui.calendar-master/dist/tui-calendar.min.css">
<link rel="stylesheet" type="text/css" href="assets/libs/tui.calendar-master/css/default.css">
<link rel="stylesheet" type="text/css" href="assets/libs/tui.calendar-master/css/icons.css">



<style type="text/css" media="screen">
	html, body{font-size: 100% !important;}
</style>
<c:choose>
	<c:when test="${ cookie['accessibilitytextsize'].value =='LGT' }">
		<style type="text/css" media="screen">
			html, body{font-size:106% !important;}
		</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='LGR' }">
		<style type="text/css" media="screen">
			html, body{font-size: 103% !important;}
		</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='MDM' }">
		<style type="text/css" media="screen">
			html, body{font-size: 100% !important;}
		</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='SLR' }">
		<style type="text/css" media="screen">
			html, body{font-size: 98% !important;}
		</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='SLT' }">
		<style type="text/css" media="screen">
			html, body{font-size: 96% !important;}
		</style>
	</c:when>
</c:choose>


<c:choose>
<c:when test="${ cookie['accessibilityCol'].value =='B' and cookie['accessibility'].value =='Y'}">
	<link href="assets/css/blue-contrast.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='Y'}">
	<link href="assets/css/orange-contrast.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${cookie['accessibilityCol'].value =='G' and cookie['accessibility'].value =='Y'}">
	<link href="assets/css/green-contrast.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${ request.getParameter('accessibility')=='Y' or cookie['accessibility'].value =='Y'}">
	<link href="assets/css/blue-contrast.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='M'}">
	<link href="assets/css/style-orange.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${cookie['accessibilityCol'].value =='B' and cookie['accessibility'].value =='M'}">
	<link href="assets/css/style.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${ cookie['accessibility'].value =='M' or empty cookie['accessibility'].value }">
	<link href="assets/css/style-green.css" rel="stylesheet" type="text/css"  id=""/>
	<script>
	 $(document).ready(function($) {
	  var d = new Date();
	  d.setTime(d.getTime() + (1*24
			  *60*60*1000));
	  var expires = "expires=" + d.toGMTString();
	  document.cookie = "accessibility"+"="+'M'+"; "+expires; 
	});
	</script>
</c:when>
</c:choose>
<link href="assets/css/style-responsive.css" rel="stylesheet" />
<script>
	function dispNonLogService(obj) {
		$(obj).attr("class", "active");
		var formData = "dispNonLogInMenu=true";
		$.ajax({
			type : 'POST',
			//dataType: 'application/octet-stream', 
			asynch : false,
			dataType : 'html',
			url : 'AdminHome.html?dispNonLogInMenu=true',
			success : function(data) {

				//alert(data);
				location.reload();

			},
			error : function(jqXHR, exception) {

				//alert("ERROR:: "+jqXHR);
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found [404].');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error [500].');
				} else if (exception === 'parsererror') {
					alert('Requested JSON parse failed.');
				} else if (exception === 'timeout') {
					alert('Time out error.');
				} else if (exception === 'abort') {
					alert('Ajax request aborted.');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}

			}

		});
	}
	function changeFont(_name) {
		document.body.style.fontFamily = _name;
	}
	function openDialogD2K(mntid) {
		showModalBox();
		var d2kUrl = '';
		$.ajax({
			type : 'POST',
			headers : {
				"SecurityToken" : token
			},
			//dataType: 'application/octet-stream', 
			asynch : false,
			dataType : 'json',
			url : 'AdminHome.html?cfcFormParams=' + mntid,
			success : function(data) {

				window.location.replace(data[0], "_blank");
			},
			error : function(jqXHR, exception) {

				//alert("ERROR:: "+jqXHR);
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found. [404]');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error [500].');
				} else if (exception === 'parsererror') {
					alert('Requested JSON parse failed.');
				} else if (exception === 'timeout') {
					alert('Time out error.');
				} else if (exception === 'abort') {
					alert('Ajax request aborted.');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}
			}
		});
	}
</script>


</head>
<form:form id="postMethodFormSuccess" method="POST" class="form"></form:form>
<body class="fixed-left">
	<div id="text-resize">
	<div id="wrapper" class="enlarged forced">
		<tiles:insertAttribute name="app-header" />
		<tiles:insertAttribute name="left-menu" ignore="true" />
		 
		<div class="content-page"><tiles:insertAttribute name="app-body" /></div>
		<tiles:insertAttribute name="app-footer" />
		<!-- Pop up Dilog  -->
		<div class="msg-dialog-box ok-msg" style="display: none;"></div>
		<div class="popup-dialog dialog" style="display: none;"></div>
		<div class="child-popup-dialog dialog" style="display: none;"></div>
		<div class="online-service-dialog dialog" style="display: none;"></div>
		<div class="error-dialog" style="display: none;"></div>
	</div>
 <!--Scroll To Top--> 
    <a class="tothetop" href="javascript:void(0);" tabindex="-1"><strong class="fa fa-angle-up"></strong> <span>Top</span></a> 
<!-- the overlay modal element -->
<div class="md-overlay"></div>
<!-- End of eoverlay modal --> 
<script>
    var resizefunc = [];
</script> 
<!-- jQuery Necessary JavaScript plugins) --> 
<script src="js/mainet/login.js"></script>
<script src="js/mainet/script-library.js"></script> 
<script src="assets/libs/bootstrap/js/bootstrap.min.js"></script> 
<script src="assets/libs/pace/pace.min.js"></script> <!--Loader-->  
<script src="assets/libs/jquery-detectmobile/detect.js"></script> <!-- Mobile Detect --> 
<script src="assets/js/init.js"></script> <!-- Wedgets Control --> 
<script src="assets/libs/lightbox/jquery.fancybox.js"></script> <!-- LightBox --> 
<!-- <script src="assets/js/jquery.scrollbox.js" type="text/javascript"></script> --><!-- Scroll Announcement plugins --> 
<script src="assets/libs/jquery-slimscroll/jquery.slimscroll.js"></script><!--Left Menu Scroll-->
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/mainet/framework.grid.js"></script>
<script src="js/mainet/framework-api.js"></script>
<script src="assets/libs/chosen/chosen.jquery.js" ></script>

<!-- TUI Calender Plugin Libraries   -->
<script src="assets/libs/tui.calendar-master/js/tui-code-snippet.min.js"></script>
<script src="assets/libs/tui.calendar-master/js/tui-time-picker.min.js"></script>
<script src="assets/libs/tui.calendar-master/js/tui-date-picker.min.js"></script>
<script src="assets/libs/tui.calendar-master/js/moment.min.js"></script>
<script src="assets/libs/tui.calendar-master/js/chance.min.js"></script>
<script src="assets/libs/tui.calendar-master/dist/tui-calendar.min.js"></script>
<script src="assets/libs/tui.calendar-master/js/data/calendars.js"></script> 
<!-- <script src="assets/libs/tui.calendar-master/js/data/schedules.js"></script>  -->

<!-- D#128616 DataTable-->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<c:choose>
	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
		<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
	</c:when>
	<c:otherwise>
		<script  src="assets/libs/jquery-datatables/js/jquery.dataTables.hi.min.js"></script>
	</c:otherwise>
</c:choose>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script>
var lang;
var langId=${userSession.languageId};
if( langId == 1){
	lang="en";
}else{
	lang="reg";
}
$.extend($.validator.messages, eval(lang));
</script>
</div>
</body>
</html>
