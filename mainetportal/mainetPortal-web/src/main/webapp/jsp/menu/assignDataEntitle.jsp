<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<script src="js/menu/assignDataEntitle.js"></script>
<div class="form-div form-horizontal">

	<form:form action="" name="activeNewNode" id="dataEntitleForm"
		class="form" method="POST">


		<div class="form-group clear" >
			<label class="col-sm-2 control-label required-control"
				id="selectLAbelActive"><spring:message
					code="menu.roleCode.label" /></label>
			<div class="col-sm-4">
				<form:select path="" id="roleSelected"
					cssClass="form-control mandColorClass"
					class="chosen-select-no-results form-control">
					<form:option value="0">
						<spring:message code="" text="select" />
					</form:option>
					<c:forEach var="groupmst" items="${command.getGroupMaster()}">
						<form:option value="${groupmst.gmId}"> ${groupmst.grCode}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>

        <div id="rolesDiv"></div>
        <spring:message code="menu.entitle.back" var="backBtn" />
       <div class="text-center"> <input type="button"
		id="addDataBackButtonNo" class="btn btn-danger" value="${backBtn}"
		onclick="back();">
		</div>
   </form:form>
</div>
