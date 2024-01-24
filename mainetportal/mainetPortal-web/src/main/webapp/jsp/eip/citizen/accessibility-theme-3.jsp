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

<ol class="breadcrumb">
   <c:if test="${empty userSession.employee.emploginname}" var="user">
      <li><a href="Home.html"><i class="fa fa-home"></i> Home </a></li>
   </c:if>
   <c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname eq'NOUSER' }" var="user">
      <li><a href="CitizenHome.html"><i class="fa fa-home"></i> Home </a></li>
   </c:if>
   <c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }" var="user">
      <li><a href="CitizenHome.html"><i class="fa fa-home"></i> Home </a></li>
   </c:if>
   <li>
      <spring:message code="Accessibility" text="Accessibility" />
   </li>
</ol>
<div id="content" class="content">
   <div class="widget">
      <div class="widget-header">
         <h2 id="CitizenService"><spring:message code="Accessibility" text="Accessibility" /></h2>
      </div>
      <div class="widget-content padding">
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
                     <div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
                      <ul class="nav nav-pills nav-stacked accessibility-tabs" role="tablist" data-tabs="tabs">
					<li class="active">
						<a href="#t1" data-toggle="tab">
							<div class="text-resize"></div>
							<spring:message code="text.size" text="Change Text Size" />
						</a>
					</li>
					<li>
						<a href="#t2" data-toggle="tab">
							<div class="contrast-icon"><i class="fa fa-adjust" aria-hidden="true"></i></div>
							<spring:message code="contrast.scheme" text="Contrast Schemes" />
						</a>
					</li>
				</ul>
			</div>
			<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active" id="t1">
						<fieldset id="textsizeoptions">
                           <div class="form-group">
                              <div class="radio-buttons">
                                 <form:radiobutton id="Largest" path="textsize" value="Largest"/>
                                 <label for="Largest" class="radio">
                                    <spring:message code="largest" text=" Largest" />
                                 </label>
                                 
                                 <form:radiobutton id="Larger" path="textsize" value="Larger"/>
                                 <label for="Larger" class="radio">
                                    <spring:message code="larger" text="Larger" />
                                 </label>
                                 
                                 <form:radiobutton id="Medium" path="textsize" value="Medium"/>
                                 <label for="Medium" class="radio">
                                    <spring:message code="medium" text="Medium" />
                                 </label>
                                 
                                 <form:radiobutton id="Smaller" path="textsize" value="Smaller"/>
                                 <label for="Smaller" class="radio">
                                    <spring:message code="smaller" text="Smaller" />
                                 </label>
                                 
                                 <form:radiobutton id="Smallest" path="textsize" value="Smallest"/>
                                 <label for="Smallest" class="radio">
                                    <spring:message code="smallest" text="Smallest" />
                                 </label>
                              </div>
                           </div>
                        </fieldset>
                        
					</div>
					<div role="tabpanel" class="tab-pane fade" id="t2">
						<fieldset id="contrastoptions">
                           <div class="form-group">
                              <div class="radio-buttons">
                                 <form:radiobutton id="High" path="contrastscheme" value="HC"/>
                                 <label for="High" class="radio">
                                    <spring:message code="high.contrast" text="High Contrast" />
                                 </label>
                                 
                                 <form:radiobutton id="Standard" path="contrastscheme" value="SC"/>
                                 <label for="Standard" class="radio">
                                    <spring:message code="standard.contrast" text="Standard Contrast" />
                                 </label>
                              </div>
                           </div>
                        </fieldset>
                        
					</div>
				</div>
			</div>
                     
                     <div class="text-center buttons-main col-xs-12 col-sm-2 col-md-2 col-lg-2">
                        <c:if
                           test="${ !empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }"
                           var="user">
                           <input type="submit" name="applyoptions" value="<spring:message code="apply" text="Apply"/>" class="btn" onclick="setcontrast2()">										
                           <input type="submit" class="btn" value="<spring:message code="reset.default" text="Reset Default" />"  onclick="setTextContrast2()">
                           <a href="CitizenHome.html" class="btn">
                              <spring:message code="bckBtn" text="Back" />
                           </a>
                        </c:if>
                        <c:if
                           test="${empty userSession.employee.emploginname or userSession.employee.emploginname eq'NOUSER' }"
                           var="user">
                           <input type="submit" name="applyoptions" value="<spring:message code="apply" text="Apply"/>"
                           class="btn" onclick="setcontrast2()">										
                           <input type="submit" class="btn" value="<spring:message code="reset.default" text="Reset Default" />"  onclick="setTextContrast2()">
                           <a href="CitizenHome.html" class="btn">
                              <spring:message code="bckBtn" text="Back"/>
                           </a>
                        </c:if>
                     </div>
                     <div class="clear"></div>
                     
                  </form:form>
               </div>
            </div>
            
      </div>
   </div>
</div>