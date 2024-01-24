<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/masters/tbdepartment/departmentlist.js">	</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content"> 
<div class="widget">
   <div class="widget-header">
     <h2><spring:message code="department.master" text="Department Master"/></h2>
     <apptags:helpDoc url="Department.html" helpDocRefURL="Department.html"></apptags:helpDoc>
   </div>
   <div class="widget-content padding"  id="deptDetDiv" >	
   <form action="">

	<input type="hidden" value="${userSession.organisation.defaultStatus}" id="orgDefStatus"/>	
			<div class="error-div alert alert-danger alert-dismissible" style="display:none;" id="errorDivDeptMas"></div>
		<div class="text-center padding-top-10">
				
				<c:if test="${userSession.organisation.defaultStatus eq 'Y'}">
					<button type="button" id="createData" class="btn btn-success btn-submit"><spring:message code='master.addButton' text="Add"/></button>
				</c:if>
				
			</div>

		<div class="clear padding-top-10">
			<table id="grid" class="table"></table>
			<div id="pagered"></div> 
		</div>
	</form>
</div>
</div>
</div>