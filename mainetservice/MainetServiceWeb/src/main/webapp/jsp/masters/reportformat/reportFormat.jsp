
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/reportMaster/reportMaster.js"></script>
<div id="mainContent">
	<ul class="breadcrumbs">
		<li><spring:message code="" text="Athentication Master " /></li>
		<li><spring:message code="" text="Master" /></li>
		<li class="active"><spring:message code=""  text="Reprot Format Size"/></li>
	</ul>
	
	
	<h1><spring:message code="" text="Report Format Details" /></h1>
	
	<div id="content" class="about">	
	
	<form:form action="${action_url_form_submit}" method="GET" name="" class="form" modelAttribute="reportFormat">
 
 <form:hidden path="successFlag" id="successFlag"/>
 
	 <div class="error-div padding_top_20 hide" >
	 <div class="closeme">
	 <img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>
	 </div>
	 <ul>
	 <form:errors path="*"  />
	 </ul>
	 </div>


		<div class="table clear">
			<div class="col-155 padding_top_20">
				<label><spring:message code="" text="Department Name" /> :</label>
			</div>
			<div class="col-5 margin_right_25">
				<form:select path="deptId" id="deptId"
					class="input2 mandClassColor">
					<form:option value="0">
						<spring:message code="" text="-- select --" />
					</form:option>
					<c:forEach items="${deptlist}" var="deptlist">
						<form:option value="${deptlist.dpDeptid}"
							label="${deptlist.dpDeptdesc}"></form:option>
					</c:forEach>
				</form:select>
			</div>
			
			<div class="element">
			<input name="" type="button" value="Add" class="css_btn"   onclick="addNewRecord(this)" />
			</div>
			
		</div>

		<div class="form-elements clear margin_top_20">
			<table id="reportFormatGrid" ></table>
			<div id="pagered"></div>
		</div>

	</form:form>	
	</div>
</div>