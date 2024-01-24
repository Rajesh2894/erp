<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
   $(document).ready(function(){
       var textsize = getCookie("accessibilitytextsize");
       var accessibility = getCookie("accessibility");
       var text="";
       var contrast="";
       switch (textsize) {
       case "LGT":
       	text='Largest';
           break;
       case "LGR":
       	text='Larger';
           break;
       case "MDM":
       	text='Medium';
            break;
       case "SLR":
       	text='Smaller';
            break;
       case "SLT":  
       	text='Smallest';
            break;
       default:
       	text='';
   	}
   	if(text != "")
       $("input[name=textsize][value=" + text + "]").prop('checked', true);
       switch (accessibility) {
       case "Y":
       	contrast='HC';
           break;
       case "M":
       	contrast='SC';
           break;
       default:
       	contrast='';
   	}
       $("input[name=contrastscheme][value=" + contrast + "]").prop('checked', true);
   });
   
   
   
   	function setCookie(cname, cvalue, exdays) {
   		var d = new Date();
   		d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
   		var expires = "expires=" + d.toGMTString();
   		document.cookie = cname + "=" + cvalue + "; " + expires;
   	}
   	function getCookie(cname) {
   		var name = cname + "=";
   		var ca = document.cookie.split(';');
   		for (var i = 0; i < ca.length; i++) {
   			var c = ca[i];
   			while (c.charAt(0) == ' ') {
   				c = c.substring(1);
   			}
   			if (c.indexOf(name) == 0) {
   				return c.substring(name.length, c.length);
   			}
   		}
   		return "";
   	}
   	function setcontrast(arg) {
   
   		if (arg == "O") {
   			
   
   			var d = new Date();
   			d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   			var expires = "expires=" + d.toGMTString();
   			document.cookie = "accessibilityCol" + "=" + 'O' + "; " + expires;
   			var user = getCookie("accessibility");
   		
   
   		}
   		if (arg == "B") {
   			
   			var d = new Date();
   			d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   			var expires = "expires=" + d.toGMTString();
   			document.cookie = "accessibilityCol" + "=" + 'B' + "; " + expires;
   
   		}
   
   		if (arg == "G") {
   			
   			var d = new Date();
   			d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   			var expires = "expires=" + d.toGMTString();
   			document.cookie = "accessibilityCol" + "=" + 'G' + "; " + expires;
   
   		}
   		location.reload(window.location.href);
   		
   	}
   	function setcontrast2() {
   		var rates = document.getElementsByName('contrastscheme');
   		var ratevalue;
   		for (var i = 0; i < rates.length; i++) {
   			if (rates[i].checked) {
   				ratevalue = rates[i].value;
   				
   				if (ratevalue == "HC") {
   					localStorage.setItem('accessibilitylocal','Y'); 
   
   					var d = new Date();
   					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   					var expires = "expires=" + d.toGMTString();
   					document.cookie = "accessibility" + "=" + 'Y' + "; "
   							+ expires;
   					var user = getCookie("accessibility");
   			
   
   				}
   				if (ratevalue == "SC") {
   					  localStorage.setItem('accessibilitylocal','N'); 
   					var d = new Date();
   					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   					var expires = "expires=" + d.toGMTString();
   					document.cookie = "accessibility" + "=" + 'M' + "; "
   							+ expires;
   
   				}
   				
   			}
   		}
   
   		var rates2 = document.getElementsByName('textsize');
   		var ratevalue;
   		for (var i = 0; i < rates2.length; i++) {
   			if (rates2[i].checked) {
   				ratevalue = rates2[i].value;
   				if (ratevalue == "Largest") {
   
   					var d = new Date();
   					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   					var expires = "expires=" + d.toGMTString();
   					document.cookie = "accessibilitytextsize" + "=" + 'LGT'
   							+ "; " + expires;
   					var user = getCookie("accessibility");
   
   				}
   				if (ratevalue == "Larger") {
   					var d = new Date();
   					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   					var expires = "expires=" + d.toGMTString();
   					document.cookie = "accessibilitytextsize" + "=" + 'LGR'
   							+ "; " + expires;
   
   				}
   
   				if (ratevalue == "Medium") {
   
   					var d = new Date();
   					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   					var expires = "expires=" + d.toGMTString();
   					document.cookie = "accessibilitytextsize" + "=" + 'MDM'
   							+ "; " + expires;
   					var user = getCookie("accessibility");
   					/*  alert("user"+user); */
   
   				}
   				if (ratevalue === "Smaller") {
   					var d = new Date();
   					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   					var expires = "expires=" + d.toGMTString();
   					document.cookie = "accessibilitytextsize" + "=" + 'SLR'
   							+ "; " + expires;
   
   				}
   
   				if (ratevalue === "Smallest") {
   					var d = new Date();
   					d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
   					var expires = "expires=" + d.toGMTString();
   					document.cookie = "accessibilitytextsize" + "=" + 'SLT'
   							+ "; " + expires;
   
   				}
   			}
   		}
   	}
   	
   	function setTextContrast2()
   	{
   		var textsize="Medium";
   		var contrast="SC";
   		$("input[name=textsize][value="+textsize+"]").prop('checked', true);
   		$("input[name=contrastscheme][value="+contrast+"]").prop('checked', true);
   		setcontrast2();
   	}
</script>
<div class="content-page">
   <ol class="breadcrumb">
      <c:if test="${empty userSession.employee.emploginname}" var="user">
         <li><a href="Home.html"><i class="fa fa-home"></i><spring:message code="menu.home" text="Home"/></a></li>
      </c:if>
      <c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname eq'NOUSER' }" var="user">
         <li><a href="CitizenHome.html"><i class="fa fa-home"></i><spring:message code="menu.home" text="Home"/></a></li>
      </c:if>
      <c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }" var="user">
         <li><a href="CitizenHome.html"><i class="fa fa-home"></i><spring:message code="menu.home" text="Home"/></a></li>
      </c:if>
      <li>
         <spring:message code="Accessibility" text="Accessibility" />
      </li>
   </ol>
   <br/>
   <div id="content" class="content">
      <div class="widget">
         <div class="widget-header">
            <h2 id="CitizenService">
               <spring:message code="Accessibility" text="Accessibility" />
            </h2>
         </div>
         <div class="widget-content">
            <div class="content tab-boxed">
               <div class="accesform">
                  <div>
                     <p class="bg-info text-center padding-10">
                        <spring:message code="accessibility.note1" text="Accessibility options enables you to increase or decrease the
                           font size and/or change color scheme of this website according
                           to your preferences." />
                        <br>
                        <spring:message code="accessibility.note2" text="All of us must have come across
                           situations where we need the services of talented people but
                           can't manage to get one, because of certain constraints." />
                     </p>
                     <form:form method="post" action="Accessibility.html" id="frmchangetextsize1" cssClass="form-horizontal" modelAttribute="command">
                        <fieldset id="textsizeoptions">
                           <legend>
                              <spring:message code="text.size" text="Change Text Size" />
                           </legend>
                           <div class="form-group">
                              <div class="col-xs-8 col-xs-offset-2">
                                 <label for="Largest" class="radio">
                                    <form:radiobutton id="Largest" path="textsize" value="Largest"/>
                                    <spring:message code="largest" text=" Largest" />
                                 </label>
                                 <label for="Larger" class="radio">
                                    <form:radiobutton id="Larger" path="textsize" value="Larger"/>
                                    <spring:message code="larger" text="Larger" />
                                 </label>
                                 <label for="Medium" class="radio">
                                    <form:radiobutton id="Medium" path="textsize" value="Medium"/>
                                    <spring:message code="medium" text="Medium" />
                                 </label>
                                 <label for="Smaller" class="radio">
                                    <form:radiobutton id="Smaller" path="textsize" value="Smaller"/>
                                    <spring:message code="smaller" text="Smaller" />
                                 </label>
                                 <label for="Smallest" class="radio">
                                    <form:radiobutton id="Smallest" path="textsize" value="Smallest"/>
                                    <spring:message code="smallest" text="Smallest" />
                                 </label>
                              </div>
                           </div>
                        </fieldset>
                        <fieldset id="contrastoptions">
                           <legend>
                              <spring:message code="contrast.scheme" text="Contrast Schemes" />
                           </legend>
                           <div class="form-group">
                              <div class="col-xs-8 col-xs-offset-2">
                                 <label for="High" class="radio">
                                    <form:radiobutton id="High" path="contrastscheme" value="HC"/>
                                    <spring:message code="high.contrast" text="High Contrast" />
                                 </label>
                                 <label for="Standard" class="radio">
                                    <form:radiobutton id="Standard" path="contrastscheme" value="SC"/>
                                    <spring:message code="standard.contrast" text="Standard Contrast" />
                                 </label>
                              </div>
                           </div>
                        </fieldset>
                        <div class="text-center padding-bottom-20">
                           <c:if
                              test="${ !empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }"
                              var="user">
                              <input type="submit" name="applyoptions" value="<spring:message code="apply" text="Apply"/>" class="btn btn-success" onclick="setcontrast2()"  style="width:150px">										
                              <input type="submit" class="btn btn-info"  style="width:150px" value="<spring:message code="reset.default" text="Reset Default" />"  onclick="setTextContrast2()">
                              <a href="CitizenHome.html" class="btn btn-primary"  style="width:150px">
                                 <spring:message code="bckBtn" text="Back" />
                              </a>
                           </c:if>
                           <c:if
                              test="${empty userSession.employee.emploginname or userSession.employee.emploginname eq'NOUSER' }"
                              var="user">
                              <input type="submit" name="applyoptions" value="<spring:message code="apply" text="Apply"/>"
                              class="btn btn-success" onclick="setcontrast2()"  style="width:150px">										
                              <input type="submit" class="btn btn-info" value="<spring:message code="reset.default" text="Reset Default" />"  onclick="setTextContrast2()"  style="width:150px">
                              <a href="CitizenHome.html" class="btn btn-primary" style="width:150px">
                                 <spring:message code="bckBtn" text="Back"/>
                              </a>
                           </c:if>
                        </div>
                     </form:form>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>
<hr/>