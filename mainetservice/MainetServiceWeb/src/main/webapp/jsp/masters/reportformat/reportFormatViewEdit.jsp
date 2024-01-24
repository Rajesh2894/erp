
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/reportMaster/jquery.tablesorter.js"></script>
<script type="text/javascript" src="js/reportMaster/jquery.tablesorter.pager.js"></script>
<link rel="stylesheet" type="text/css" href="js/reportMaster/jquery.tablesorter.pager.css">
<script type="text/javascript" src="js/reportMaster/reportMaster.js"></script>
<script src="js/tableHeadFixer.js"></script>



<script type='text/javascript'>

	$(document).ready(function(){
		
	});
	
</script>


<div  id="childDiv">
	<c:url value="${saveAction}" var="url_form_submit" />
	
	<form:form method="post" action="${url_form_submit}" name="" id="reportFormatChild" class="form" modelAttribute="reportFormat">
	
	 <div class="error-div hide" id="error-div-child">
	 <div class="closeme">
	 <img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>
	 </div>
	 <ul>
	 <form:errors path="*"/>
	 </ul>
	 </div>
	
	<div class="form-elements about" >
	<h2>
	<spring:message code="" text="Report Format Detail" />
	</h2> 
	</div>
	
	<!-- Hidden Data -->
	
	<form:hidden path="deptId"  id="deptIdPopUp"  />
	<form:hidden path="crfId"  id="crfId"  />
	<form:hidden path="orgid"  id="orgid"  />
	<form:hidden path="crfStatus"  id="crfStatus"  />
	<form:hidden path="userId"  id="userId"  />
	<form:hidden path="langId"  id="langId"  />
	<div class="form-elements">
			<label for="" class="col-sm-2 control-label"> <spring:message code="" text="Report Name" /></label>
			
			<div class="element hasNameClass">
			<form:input id="repName" path="repName" class="form-control hasNameClass mandClassColor" maxLength="200" />
			</div>
	</div>

	<div class="form-elements">
			<label for="" class="col-sm-2 control-label"> <spring:message code="" text="Report Size" /></label>
			
			<div class="element hasNameClass">
			<form:select path="repTypeid" id="repTypeid" cssClass="mandClassColor">
    		<form:option value="0" label="-- Select --"></form:option>
			<c:forEach items="${reportSizeList}" var="objArray">
			<form:option value="${objArray.lookUpId}" label="${objArray.lookUpCode}"></form:option>
			</c:forEach>
			</form:select>
			</div>
	</div>
	
	<div class="form-elements">
			<label for="" class="col-sm-2 control-label"> <spring:message code="" text="Report Server Name" /></label>
			
			<div class="element hasNameClass">
			
			<form:select path="rsrCpdId" id="repTypeid" cssClass="mandClassColor">
    		<form:option value="0" label="-- Select --"></form:option>
			<c:forEach items="${reportServerList}" var="objArray">
			<form:option value="${objArray.lookUpId}" label="${objArray.lookUpCode}"></form:option>
			</c:forEach>
			</form:select>
			
			</div>
	</div>

	<div class="btn_fld padding_10 clear">
		<input type="button" class="css_btn btn-primary btn-lg btn-block" value="<spring:message code="" text=" Save"/>" onclick="saveReportFormatData(this)" />
	</div>

	
	</form:form>
</div>





 

