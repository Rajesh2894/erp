
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/masters/servicemaster/serviceruledefHome.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>


<div class="content" id="content-div">
      <div class="widget">
        <div class="widget-header">
		<h2><spring:message code='service.master' text="Service Rule Definition"/></h2>
		<apptags:helpDoc url="ServiceMaster.html" helpDocRefURL="ServiceMaster.html"></apptags:helpDoc>
        </div>
        <div class="widget-content padding">
	
	<form:form id="serviceruleDefForm" name="serviceruleDefForm" class="form-horizontal" action="#" method="post">

		<div class="warning-div alert alert-danger alert-dismissible error-div" id="serviceRuleDefMas" style="display:none;">
				<button type="button" class="close" aria-label="Close" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button>
				
				<ul><li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors path="*"/></li></ul>
				<script>
					$(".warning-div ul").each(function () {
					    var lines = $(this).html().split("<br>");
					    $(this).html('<li>' + lines.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;") + '</li>');						
					});
		  			$('html,body').animate({ scrollTop: 0 }, 'slow');
		  		</script>
		</div>

	<div class="form-group">
			<label class="col-sm-2 control-label"><spring:message code='master.department'/><span class="mand">*</span></label> 
			<div class="col-sm-4">
			<input type="hidden" id="langId" value="${languageId}"/>
			<input type="hidden" value="${activeId}" id="activeId"/>
			<select class="form-control chosen-select-no-results required-control" id="deptId" onchange="refreshServiceData()">
				<option value=""><spring:message code='master.serviceMas.department' text="Select Department"/></option>
				<c:choose>
					<c:when test="${languageId eq 1}">
						<c:forEach items="${deptList}" var="deptData">
							<option value="${deptData.dpDeptid }" code="${deptData.dpDeptcode }">${deptData.dpDeptdesc }</option>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach items="${deptList}" var="deptData">
							<option value="${deptData.dpDeptid }" code="${deptData.dpDeptcode }">${deptData.dpNameMar }</option>
						</c:forEach>
					</c:otherwise>
				</c:choose>	
			</select></div>

			<label class="col-sm-2 control-label"><spring:message code='master.serviceName'/><span class="mand">*</span></label>
			<div class="col-sm-4">
			<select class="form-control chosen-select-no-results required-control" id="serviceId" onchange="hideDiv()">
				<option value=""><spring:message code='master.serviceMas.service' text="Select Service"/></option>
			</select></div>
		</div>


	<div class="text-center padding-top-10 clear">
		<input type="hidden" value="${isDefaultOrg}" id="isDefaultOrg"/>		
		
				<button type="button" id="search" class="btn btn-blue-2"  onclick="searchServiceMst();">&nbsp;<spring:message code='master.search'/></button> 
				<button type="reset" id="reset" class="btn btn-warning" onclick="resetHomePage()"> <spring:message code="reset.msg"/></button>
				<c:if test="${isDefaultOrg eq 'Y'}">
						<button type="button" id="createData" class="btn btn-success btn-submit" onclick="openAddForm()">&nbsp;<spring:message code='add.msg'/></button>
				</c:if>
		</div>
	<div class="clear padding-top-10" id="showGrid">
		<table id="grid"></table>
		<div id="pagered">
		</div>
	</div>
		
</form:form>

</div>
</div>
</div>