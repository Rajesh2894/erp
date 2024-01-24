
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <script>

 function openCitizenGuideLines(data) {
    var myWindow = window.open("", "MsgWindow3", "scrollbars=1,width=800,height=600");
    var result = __doAjaxRequest("CitizenContactUs.html?showPage", 'GET', data, false);
    myWindow.document.write(result);
}

 function openRelatedForm(url){
	 var token = '';
		var optionsAsString = '';
		var formid = $(".form").attr('id');

		$
				.ajax({
					url : "Autherization.html?getRandomKey",
					type : "POST",
					async : false,
					success : function(response) {
						token = response;

						optionsAsString = "<input  type='hidden' value="+token+" name='SecurityTokens'>";
						$("#" + formid).append(
								optionsAsString);
					},
					error : function(xhr, ajaxOptions,
							thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
	 alert("url  "+url);
	 $("#postMethodForm").prop('action','');
	
	 $("#postMethodForm").prop('action',url);
	 $("#postMethodForm").submit();
	 
}

<%
	response.setContentType("text/html; charset=utf-8");
%>

 <script>

 function openCitizenGuideLines(data) {
    var myWindow = window.open("", "MsgWindow3", "scrollbars=1,width=800,height=600");
    var result = __doAjaxRequest("CitizenContactUs.html?showPage", 'GET', data, false);
    myWindow.document.write(result);
}

 function openRelatedForm(url){
	 var token = '';
		var optionsAsString = '';
		var formid = $(".form").attr('id');

		$
				.ajax({
					url : "Autherization.html?getRandomKey",
					type : "POST",
					async : false,
					success : function(response) {
						token = response;

						optionsAsString = "<input  type='hidden' value="+token+" name='SecurityTokens'>";
						$("#" + formid).append(
								optionsAsString);
					},
					error : function(xhr, ajaxOptions,
							thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
	 alert("url  "+url);
	 $("#postMethodForm").prop('action','');
	
	 $("#postMethodForm").prop('action',url);
	 $("#postMethodForm").submit();
	 
}


	

	
</script>




<div id="left_menu">
<div class="left side-menu">
	<div class="sidebar-inner slimscrollleft">

		<!--- Divider -->
		<div class="clearfix"></div>
		<!--- Divider -->
		<div id="sidebar-menu">
			<ul>
	  
	   
	    <li><a href="javascript:openRelatedForm('entitlement.html')"><i class="icon-info-circled-alt"></i>Entitlement</a></li>
		<li><a href="javascript:void(0);"  onclick="openCitizenGuideLines('name=guideLineForCitizen')"><i class="icon-info-circled-alt"></i>
<span><spring:message code="left.online.guidelines"></spring:message></span></a></li>
		<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
			<li class="folder" id="${masters.entitle.etId}"><a
				href="${masters.entitle.action}"><i class="fa fa-angle-double-right"></i><span>${masters.entitle.nameEng}</span> <span class="pull-right"><i class="fa fa-angle-down"></i></span></a>
				<ul>
					<c:forEach items="${menuRoleEntitlement.childList}" var="data">
						<c:if test="${masters.entitle.etId eq  data.parentId}">
							<li class="folder" id="${data.entitle.etId}"><a
								href="${data.entitle.action}"><i class="fa fa-angle-double-right"></i><span>${data.entitle.nameEng}</span> <span class="pull-right"><i class="fa fa-angle-down"></i></span></a>
								<ul>
									<c:forEach items="${menuRoleEntitlement.childList}" var="data1">
										<c:if test="${data.entitle.etId eq  data1.parentId}">
											<!-- <script>$(document).ready(function(){alert("${fn:containsIgnoreCase('${data1.entitle.action}', '.html')}");});</script>  -->
											<c:set var="action1" value="${data1.entitle.action}" />
											<li class="folder" id="${data1.entitle.etId}"><a href=
												<c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}">"javascript:openRelatedForm('${action1}');"</c:when><c:otherwise>${action1}</c:otherwise></c:choose>><i class="fa fa-angle-double-right"></i><span>${data1.entitle.nameEng}</span> <span class="pull-right"><i class="fa fa-angle-down"></i></span></a>
												<ul>
													<c:forEach items="${menuRoleEntitlement.childList}"
														var="data2">
														<c:if test="${data1.entitle.etId eq  data2.parentId}">
															<c:set var="action2" value="${data2.entitle.action}" />
															<li class="folder" id="${data2.entitle.etId}"><a href=
																<c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}">"javascript:openRelatedForm('${action2}');"</c:when><c:otherwise>${action2}</c:otherwise></c:choose>><i class="fa fa-angle-double-right"></i><span>${data2.entitle.nameEng}</span> <span class="pull-right"><i class="fa fa-angle-down"></i></span></a>
																<ul>
																	<c:forEach items="${menuRoleEntitlement.childList}"
																		var="data3">
																		<c:if test="${data2.entitle.etId eq  data3.parentId}">
																			<c:set var="action3" value="${data3.entitle.action}" />
																			<li class="folder" id="${data3.entitle.etId}"><a
																				<c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}">"javascript:openRelatedForm('${action3}');"</c:when><c:otherwise>${action3}</c:otherwise></c:choose>><i class="fa fa-angle-double-right"></i><span>${data3.entitle.nameEng}</span> <span class="pull-right"><i class="fa fa-angle-down"></i></span></a></li>
																		</c:if>
																	</c:forEach></ul>
																</li>
														</c:if>
													</c:forEach></ul>
												</li>
										</c:if>
									</c:forEach></ul>
								</li>
						</c:if>
					</c:forEach></ul>
				</li>
		</c:forEach>


	</ul> 
			<div class="clearfix"></div>
		</div>
	</div>
</div>
</div>


<form:form id="postMethodForm" method="POST" class="form">	
</form:form>



 

 