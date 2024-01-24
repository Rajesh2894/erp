<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	function openCitizenGuideLines(data) {
		var myWindow = window.open("", "MsgWindow3",
				"scrollbars=1,width=800,height=600");
		var result = __doAjaxRequest("CitizenContactUs.html?showPage", 'GET',
				data, false);
		myWindow.document.write(result);
	}

	function openRelatedForm(url,elem,eventName,eventSMShortDesc) {	
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', url);
		$('#postMethodForm').append('<input type="hidden" id="eventName" name="eventName" value="" /> ');
		$('#postMethodForm').append('<input type="hidden" id="eventSMShortDesc" name="eventSMShortDesc" value="" /> ');
		$("#eventName").val(eventName);
		$("#eventSMShortDesc").val(eventSMShortDesc);
		showloader(true);
		$('#postMethodForm').submit();
		
		
	}
	
	
	function breadcrumb(data,id) {
		if(data!=null && data!=''){
			var array = data.split("+");
			/* Defect #94362 */
			var uniqueValues = [];				
			$.each(array, function(i, el){
			    if($.inArray(el, uniqueValues) === -1) 
			    	uniqueValues.push(el);
			});
			var breadcrumbdata='<li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>';
				$.each(uniqueValues, function(i) {
					breadcrumbdata=breadcrumbdata +'<li><a href="AdminHome.html#go'+ id +'">'+array[i]+'</a></li>';
				});
				localStorage['breadCrumbData'] = breadcrumbdata;
		}
	}
	
	
</script>
<script type="text/javascript">
   var golablid ;
    jQuery(document).ready(function($){
    	$("ul#nav").each(
    			  function() {
    			    var elem = $(this);
    			    if (elem.children().length == 0) {
    			      elem.remove();
    			    }
    			  }
    			);
    	
    	
    	
    	 $("#Selectsmfid").val(golablid) ;   
         var u = window.location.href;
                  var a= 0;
                  $("#sidebar-menu ul li a").each(function(){
                      var someVarName1 = localStorage.getItem("someVarName");
                          if($(this).closest('li').attr('id') == someVarName1)
                      	  { 
                      	 $(this).addClass("active");
                      	  }
                      		});  
       
                      $("#"+golablid).addClass('active');
                      var someVarName;        	   
                   $(".has_sub li").click(function() {
                 	$("#Selectsmfid").val($(this).attr('id'))
                 	someVarName = $(this).attr('id');
                    localStorage.setItem("someVarName", someVarName);
                 	golablid=$("#Selectsmfid").val($(this).attr('id'));
                 	
                 	
                 });  
                    
			  	 $("#sidebar-menu ul li").each(function(){
			  	
			  		 
			  		if($('ul#nav li').hasClass('folder')){
			  			/* $('ul#nav a').addClass(' subdrop active'); */
			  			/* $('ul#nav').addClass('has_sub subdrop active'); */
			  		 	$(this).parent().parent().addClass('has_sub active'); 
			  		 	$(this).parent().addClass('has_sub active');
			  		}
			  		
			  		 
			  	 });  
                   
    });
    
   
	   
    function dodajAktywne(elem) {
         var u = window.location.href;
         var a = document.getElementsByTagName('a');
        for (i = 0; i < a.length; i++) {
            a[i].classList.remove('active')
        }
        elem.classList.add('active');
        j= u ;
    }
 
</script>




			<input type="hidden" id="Selectsmfid" name="" /> 

<!--  New Left Menu -->
<!-- Left Sidebar Start -->
<div id="left_menu">
	<div class="left side-menu hidden" >
		<div class="sidebar-inner slimscrollleft">
			
 			<form:form role="search" class="navbar-form" action="SearchContent.html" method="POST" id="EIPSearchContentForm">
 			<label for="search_input" class="hide">Search Input</label>
			<input type="text" class="form-control" name="searchWord" tabindex="-1" id="search_input" autocomplete="off" required placeholder="Search here" />
			<button type="Submit" id="searchButton" class="btn search-button" value=""><strong class="fa fa-search"><span class="hide">Search Here..</span></strong></button>

		</form:form>			
			<!--- Profile -->
      <div class="clearfix"></div>
      <!--- Profile -->

			
			<%-- <div class="profile-info">
			<div class="col-xs-4">
			<a href="#" class="rounded-image profile-image" title="Profile">
				<img src="${userSession.loggedInEmpPhoto}" class="not-logged-avatar">		
				<!-- <img src="css/images/profile_pic.png" alt="Profile"> --></a>
			</div>
			 <div class="col-xs-8">
			 	<c:if test="${userSession.employee.emploginname eq 'NOUSER'}" var="user"><li><a href="Home.html" title="Home"><strong class="fa fa-reply-all"></strong>Change Your City</a></li></c:if>
					<div class="profile-text">
						<span title="${userSession.employee.empname}&nbsp;${userSession.employee.emplname}">${userSession.employee.empname}&nbsp;${userSession.employee.empmname}&nbsp;${userSession.employee.emplname}</span>
					</div>
						<c:if test="${userSession.employee.emploginname ne 'NOUSER'}" var="user">
						
						<div class="profile-buttons">
<a href="LogOut.html" title="Log Out" data-toggle="tooltip" data-placement="top" title="Sign Out"><strong class="fa fa-power-off text-red-1"><span class="hide">Sign Out</span></strong></a>
<a href="AdminHome.html" title="Home" data-toggle="tooltip" data-placement="top"><strong class="fa fa-home"> <span class="hide">Home</span></strong></a>
						</div>
						 </c:if>
						
						</div>
			</div> --%>
			
			 <!--- Divider -->
      <div class="clearfix"></div>
      <!--- Divider -->
      
			<<%-- div id="sidebar-menu">
			
			
				<ul id="nav" class="has_sub">
				<hr class="divider">
				
					<!-- <li><a href="javascript:void(0);"
						onclick="openCitizenGuideLines('name=guideLineForCitizen')"><i
							class="icon-info-circled-alt"></i> <span>GuideLine</span></a></li> -->
					
					<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
						<li class="has_sub" id="${masters.entitle.smfid}">
						<a  title="${masters.entitle.smfname}"   href="${masters.entitle.smfaction}" onclick="dodajAktywne(this)"><strong class="fa fa-angle-right"></strong> <span>${masters.entitle.smfname}</span> <span class="pull-right"><i class="fa fa-angle-down"></i></span></a>
	
							<ul>
								<c:forEach items="${menuRoleEntitlement.childList}" var="data">
								   <c:if test="${masters.entitle.smfid eq  data.parentId}">
										 <c:set var="action0" value="${data.entitle.smfaction}" />
										
										<li class="navigation" id="${data.entitle.smfid}">
										<a  title="${data.entitle.smfname}"  <c:choose> <c:when test="${fn:containsIgnoreCase(action0 , '.html')}">onclick="openRelatedForm('${action0}','this','${data.entitle.smfdescription}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}');" href="#"</c:when><c:otherwise> href="${action0}" </c:otherwise></c:choose> > ${data.entitle.smfname} </a> 
											<ul >
												<c:forEach items="${menuRoleEntitlement.childList}"	var="data1">
													
													<c:if test="${data.entitle.smfid eq  data1.parentId}">
														
														
														
												<c:set var="action1" value="${data1.entitle.smfaction}" />
												
												
												
												<li class="folder " id="${data1.entitle.smfid}"><a title="${data1.entitle.smfname}"  <c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}">onclick="openRelatedForm('${action1}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}');" href="#"</c:when><c:otherwise>href="${action1}"</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
															<ul>
																<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
																	<c:if test="${data1.entitle.smfid eq  data2.parentId}">
																	
																		<c:set var="action2" value="${data2.entitle.smfaction}" />
																		<li class="folder" id="${data2.entitle.smfid}"><a title="${data2.entitle.smfname}"  <c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}">onclick="openRelatedForm('${action2}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}');" href="#"</c:when><c:otherwise>href="${action2}"</c:otherwise></c:choose>>${data2.entitle.smfname}</a>
																			<ul>
																				<c:forEach items="${menuRoleEntitlement.childList}"	var="data3">
																					<c:if test="${data2.entitle.smfid eq  data3.parentId}">	
																							<c:set var="action3" value="${data3.entitle.smfaction}" />
																						<li class="folder" id="${data3.entitle.smfid}"><a title="${data3.entitle.smfname}" <c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}">onclick="openRelatedForm('${action3}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}+${data3.entitle.smfname}');" href="#"</c:when><c:otherwise>href="${action3}"</c:otherwise></c:choose>>${data3.entitle.smfname}</a></li>
																					</c:if>
																				</c:forEach>
																			</ul></li>
																	</c:if>
																</c:forEach>
															</ul></li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:if>
								</c:forEach>
							</ul>
							</li>
					</c:forEach>

                   
				</ul><br><br><br><br><br><br><br><br><br>
				
				<div class="clearfix"></div>
			</div> --%>
		</div>
	</div>
</div>
<!-- Left Sidebar End -->
<form:form id="postMethodForm" method="POST" class="form"></form:form>