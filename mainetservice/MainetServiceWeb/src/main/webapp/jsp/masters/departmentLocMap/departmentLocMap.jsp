<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="/js/mainet/ui/jquery-ui.js"></script>
<script src="js/masters/deptlocmap/deptLocMap.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('checkboxIsCC').change(function() {
	}); 
});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div id="heading_wrapper" class="content">
<div class="widget">
<div class="widget-header">
<h2><spring:message code="department.location.master"/></h2>
<apptags:helpDoc url="TbDeptLocation.html" helpDocRefURL="TbDeptLocation.html"></apptags:helpDoc>
<!-- <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a></div> -->
</div>
<div class="widget-content padding">
	<form:form action="TbDeptLocation.html" method="POST" name="" id="form2" class="form-horizontal">
	<div class="mand-label clearfix"><span><spring:message code="account.common.mandmsg"/> <i class="text-red-1">*</i><spring:message code="account.common.mandmsg1"/></span></div>
	<div class="warning-div alert alert-danger alert-dismissible" id="errorDivScrutiny"></div>
			<div class="form-group">
 					<label for="tbDepartment_cpdIdDcg" class="col-sm-2 control-label required-control" for="locId"><spring:message code="department.location"/></label>
					<div class="col-sm-4">
					<form:select id="locId" path="locId" cssClass="form-control chosen-select-no-results" onchange="getDepartmentData(this)">
						<form:option value="0"><spring:message code="orgLocMas.valMSG.selectLocation" text="Select Location"/></form:option>
						<c:forEach items="${listOfTbLocation}" var="listOfLocationItems">
						  <c:if test="${userSession.languageId eq 1}">					
							<form:option value="${listOfLocationItems.locId}">${listOfLocationItems.locNameEng}</form:option>
						  </c:if>
						   <c:if test="${userSession.languageId eq 2}">					
							<form:option value="${listOfLocationItems.locId}">${listOfLocationItems.locNameReg}</form:option>
						  </c:if>						
						</c:forEach>
					</form:select>
				</div>
			<div class="col-sm-6">
  			<input type="button" id="createData" value="<spring:message code='add.msg'/>" class="btn btn-success btn-submit" onclick="addDepLoc()"/>
			</div>
			</div>
		</form:form>
		<div class="table">
		<table id="deptLocationGrid"></table>
		<div id="pagered"></div> 
		</div>
 		</div>
 	</div>
	</div>
