<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">

$(document).ready(function() {
	$('#departmentViewForm input').prop('disabled', true);
	$('#departmentViewForm select').prop('disabled', true);
	$('#departmentViewForm checkbox').prop('disabled', true);
	$('#departmentViewForm radio').prop('disabled', true);
	$('#backBtn').prop('disabled', false);

});

</script>

<div id="heading_wrapper" class="">
	<div class="widget">
		<div class="widget-content padding">
		<form:form method="post" name="departmentForm" action="#"
			id="departmentViewForm" class="form-horizontal">
			
			<div class="form-group">
				<label for="tbDepartment_dpDeptdesc" class="col-sm-2 control-label"><spring:message code="tbDepartment.dpDeptdesc"/></label>
				<div class="col-sm-4">
					<form:input id="tbDepartment_dpDeptdesc" path="dpDeptdesc" class="form-control mandClassColor" maxLength="400"  />
				</div>
				
				<label for="tbDepartment_dpNameMar" class="col-sm-2 control-label"><spring:message code="tbDepartment.dpNameMar"/></label>
				<div class="col-sm-4">
					<form:input id="tbDepartment_dpNameMar" path="dpNameMar" class="form-control" maxLength="400"  />
				</div>
			</div>
			<div class="form-group">
				<label for="tbDepartment_dpDeptcode" class="col-sm-2 control-label"><spring:message code="tbDepartment.dpDeptcode"/></label>
				<div class="col-sm-4">
					<form:input id="tbDepartment_dpDeptcode" path="dpDeptcode" class="form-control" maxLength="3"  />
				</div>
				<label for="tbDepartment_dpPrefix" class="col-sm-2 control-label"><spring:message code="tbDepartment.dpPrefix"/></label>
				<div class="col-sm-4">
					<form:input id="tbDepartment_dpPrefix" path="dpPrefix" class="form-control" maxLength="3"  />
				</div>
			</div>
			<div class="form-group">
				<label for="tbDepartment_status" class="col-sm-2 control-label"><spring:message code="tbDepartment.status"/></label>
				<div class="col-sm-4">
					<label class="radio-inline padding-left-20">
						<form:radiobutton path="status" value="A" id="isActive"/><spring:message code="common.master.active" text="Active"/>
					</label>
					<label class="radio-inline">
						<form:radiobutton path="status" value="I" id="isActive"/><spring:message code="common.master.inactive" text="Inactive"/>
					</label>	
				</div>
				<div class="col-sm-4">
				<label for="tbDepartment_dpCheck" class="col-sm-6 checkbox-inline">
						<form:checkbox path="dpCheck" value="Y" id="dpCheckId"/>

						<spring:message code="tbDepartment.actualdept"/>
					</label>

				</div>
					
				</div>
	
			<div class="text-center">
				<input type="button" class="btn btn-danger" value="Back" onclick="window.location.href='Department.html'" id="backBtn"/>
			
			</div>			
		</form:form>		
	</div>
</div>	
</div>