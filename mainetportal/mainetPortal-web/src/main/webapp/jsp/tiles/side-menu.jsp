<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 


<script>
$(document).ready(function(){
	$("ul.nav-menu-list-style li.dropdown-submenu > a").click(function() {
	  $(this).toggleClass("open");
	  
	});
	 
 $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });	
	/*Menu-toggle*/
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("active");
     });

	$('.dropdown-submenu > a').click(function () {	$(this).parent().children('ul.dropdown-menu').slideToggle(200);
});
 
	
 
	
});

</script>
<div id="sidebar-wrapper" class="hidden-xs">
<nav>
	<ul class="nav nav-list nav-menu-list-style" id="spy">
	<c:if test="${userSession.languageId eq 1}">${command.userSession.quickLinkEng}</c:if>
	<c:if test="${userSession.languageId eq 2}">${command.userSession.quickLinkReg}</c:if>
	</ul>
    </nav>
</div>