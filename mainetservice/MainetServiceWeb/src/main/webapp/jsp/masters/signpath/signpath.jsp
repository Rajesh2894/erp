
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/signpathmaster/signpathmaster.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
	
		$("#add_btn").hide();
		$(".error-div").hide();
		});
	
</script>


<div id="contentMain">
	<ul class="breadcrumbs">
		<li><spring:message code="" text="Athentication Master " /></li>
		<li><spring:message code="" text="Master" /></li>
		<li class="active"><spring:message code=""  text="Scanned Signature"/></li>
	</ul>
	
	
	<h1><spring:message code="" text="Scanned Signature" /></h1>
			
	<c:url value="${saveAction}" var="action_url_form_submit" />
	
<div id="content">	
	<form:form action="${action_url_form_submit}" method="GET" name="" class="form" modelAttribute="tbSignPathMas">
	
	 
	
	 <div class="error-div">
	 <div class="closeme">
	 <img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>
	 </div>
	 <ul>
	 <form:errors path="*" id="errorId"/>
	 </ul>
	 </div>
	
	
	<form:hidden path="signId" id="signId"/>
	
		 <div class="form-elements padding_top_10">	
				<div class="table clear">
				<div class="col-155 padding_top_5"><label><spring:message code=""  text="Department Name" /> :</label></div>			
				<div class="col-5 margin_right_25">
				<form:select path="deptId" id="deptId" class="input2 mandClassColor">
				<form:option value="0"><spring:message code="" text= "-- select --" /></form:option>
				<c:forEach items="${deptlist}" var="deptlist">
				<form:option value="${deptlist.dpDeptid}" label="${deptlist.dpDeptdesc}"></form:option>
				</c:forEach> 
				</form:select>
				</div>
				<button type="button" class="css_btn focustable" id="add_btn" onclick="returnwithoutwordWeise(this)"><spring:message code="contract.label.add" text="Add"/></button>
				</div>
										
				<div class="form-elements padding_top_10">				
				<table id="signpathGrid"></table>
				<div id="pagered"></div> 
				</div>	
				
				<div class="form-elements padding_top_10">				
				<div id="signpathaddnonhirarchical">
				
				</div>
				</div>
  
			</div>
		
	</form:form>
	</div>
	</div>

<script>
$(document).ready(function(){
	$('.focustable').click(function(){
		$('html,body').animate({ scrollTop: 400 }, 'slow');
	});
});
</script>
