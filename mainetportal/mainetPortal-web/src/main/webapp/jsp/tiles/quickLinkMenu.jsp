<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script src="js/jquery.slimscroll.js"></script>
<script>
$(function(){
	$('#nav').slimScroll({height : '483px',});
});
</script>

<script>

function showConfirm(form){

	var childDivName	=	'.child-popup-dialog';
	var message=' ';
	var msg1 = getLocalMessage("com.link.red1");
	var msg2 = getLocalMessage("com.link.red2");
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
function openEIPGuideLines(data) {
    var myWindow = window.open("", "MsgWindow1", "scrollbars=1,width=800,height=600");
    var result = __doAjaxRequest("CitizenContactUs.html?showPage", 'GET', data, false);
    myWindow.document.write(result);
}

</script>


<!--Lfet Menu-->
  <div class="left_menu">
    <h3><i class="fa fa-link"></i><spring:message code="left.left.quicklinks" text="quicklinks"> </spring:message></h3>
    <ul class="nav" id="nav">
     
       <li><a href="javascript:void(0);"  onclick="openEIPGuideLines('name=guideLineForQuick')"><spring:message code="left.online.guidelines"></spring:message></a></li>
           <c:if test="${userSession.languageId eq 1}">
               ${command.userSession.quickLinkEng}
           </c:if>
           <c:if test="${userSession.languageId eq 2}">
               ${command.userSession.quickLinkReg}
           </c:if>
      </ul>
  </div>
  
  <!--Lfet Menu--> 

 