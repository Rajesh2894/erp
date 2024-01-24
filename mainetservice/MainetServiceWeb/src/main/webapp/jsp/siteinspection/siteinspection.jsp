<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" type="text/css" href="css/mainet/themes/jquery-ui-timepicker-addon.css" />


<script type="text/javascript" src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript">
$(function(){
	$('#visDate').datetimepicker({
		dateFormat: 'dd/mm/yy',
		timeFormat: "hh:mm tt",
		minDate: 0
	});
});
</script>

<script src="js/siteinspection/siteinspection.js"></script>
<script src="js/cfc/scrutiny.js"></script>


<ol class="breadcrumb">
	<li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
	<li class="active"><spring:message code="siteinspection.siteinspection"/></li>
</ol>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
			<spring:message code="water.site.inspection"/>	
			</h2>
		</div>

		<div class="widget-content padding">
		
		<div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div>
		<c:url value="${saveAction}" var="url_form_submit" />
		<c:url value="${mode}" var="form_mode" />
		
<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
	<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 	<span id="errorId"></span>
</div>

		<form:form method="post" action="${url_form_submit}" name="siteInspection"
			id="siteInspection" class="form-horizontal">
			

			 <div class="form-group">
			 <label class="col-sm-2 control-label required-control"><spring:message code="siteinspection.DepartmentName"/> :</label>
			<div class="col-sm-4">
              	<form:select id="deptId" path="" cssClass="form-control mandColorClass"
              	 onchange="getDeptEmployee(this,'getDeptEmpl')">
						<form:option value="" >--<spring:message code="siteinspection.select"/>--</form:option>
						<c:forEach items="${departmentList}" var="departmentList">
							<form:option value="${departmentList.dpDeptid}">${departmentList.dpDeptdesc}</form:option>						
						</c:forEach>
				</form:select>
			
			</div>
			
			<label class="col-sm-2 control-label required-control"><spring:message code="siteinspection.EmployeeName"/>:</label>
			<div class="col-sm-4">
			<form:select path="visEmpid" id="visEmpid"
							cssClass="form-control mandColorClass">
							<form:option value="0"><spring:message code="siteinspection.select"/></form:option>
						</form:select>
			</div>
				
			</div>
			
			<div class="form-group">
			 <label class="col-sm-2 control-label required-control"><spring:message code="siteinspection.DateAndTime"/>:</label>
			<div class="col-sm-4">
			
			  <form:input path="dateTimeStr" cssClass="form-control mandColorClass cal" id="visDate"/>
			 
				
								</div> 
			</div>
			
			 
			 <form:hidden id="appId" path="visApplicationId" value="${applId}"/>	 
			 <form:hidden id="labelId" path="labelId" value="${labelId}"/>	 
			 <form:hidden id="visServiceId" path="visServiceId" value="${serviceId}"/>	 
			 <form:hidden id="level" path="level" value="${level}"/>	 
			 <form:hidden id="labelVal" path="labelVal" value="${labelVal}"/>	 

			
		 <div class="text-center padding-10">
			<button type="button" class="btn btn-success btn-submit" onclick="submitSiteInspectionForm(this);" ><spring:message code="siteinspection.submit"/></button>

					

						 <button type="Reset" class="btn btn-warning"><spring:message code="siteinspection.reset"/></button>
		 
		 <input type="button"
						onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${applId}','${labelId}','${serviceId}')"
						class="btn btn-danger" value="<spring:message code="bt.backBtn"/>">
		 
			
		</div>

		</form:form>
		</div>
	</div>
</div>
