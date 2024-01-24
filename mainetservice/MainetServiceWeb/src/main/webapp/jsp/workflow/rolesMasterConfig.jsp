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
<script type="text/javascript" src="js/mainet/instafilta.min.js"></script>
<script type="text/javascript" src="js/mainet/ui/jquery-ui.js"></script>
<script type="text/javascript" src="js/workflow/rolesMasterConfig.js"></script>
<div class="error-div alert alert-danger alert-dismissible"
	id="errorDivId"></div>

<style>
#eventMapSelectedId, #eventMapId {
	overflow-x: scroll;
}
</style>

<form:form action="RolesMaster.html" method="POST"
	name="roleMasterFormName" id="roleMasterFormId"
	class="form-horizontal" modelAttribute="roleDesicionDto">
	<input type="hidden" id="mode" value="${mode}" />
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"> <spring:message
				code="workflow.form.field.dep" />
		</label>
		<div class="col-sm-4">
			<form:select path="deptId.dpDeptid" id="departmentId"
				class="form-control" disabled="true">
				<form:option value="0">
					<spring:message code="workflow.form.select.department" />
				</form:option>
				<c:forEach items="${deptList}" var="deptLst">
					<form:option value="${deptLst.dpDeptid }"
						code="${deptLst.dpDeptcode}">${deptLst.dpDeptdesc}</form:option>
				</c:forEach>
			</form:select>
			<form:hidden path="deptId.dpDeptid" />
		</div>
		<form:hidden path="organisation.orgid" />
		<label class="col-sm-2 control-label required-control"> <spring:message
				code="workflow.form.field.service" />
		</label>
		<div class="col-sm-4">
			<form:select path="smServiceId" id="serviceId" class="form-control"
				disabled="true">
				<form:option value="0">
					<spring:message code="workflow.form.select.service" />
				</form:option>
				<c:forEach items="${serviceList}" var="objArray">
					<form:option value="${objArray[0]}" label="${objArray[1]}">${objArray[1]}</form:option>
				</c:forEach>
			</form:select>
			<form:hidden path="smServiceId" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"> <spring:message
				code="" text="Roles" />
		</label>
		<div class="col-sm-4">
			<form:select path="roleId" id="roleId" class="form-control"
				disabled="true">
				<form:option value="0">
					<spring:message code="" text="select role" />
				</form:option>
				<c:forEach items="${rolesList}" var="objArray">
					<form:option value="${objArray[0]}" label="${objArray[1]}">${objArray[2]}</form:option>
				</c:forEach>
			</form:select>
			<form:hidden path="roleId" />
		</div>
	</div>

	<h4>
		<spring:message code=""
			text="Select Multiple Decisions" />
	</h4>
	<div class="form-group has-feedback" id="ex1">
		<div class="col-xs-6 padding-bottom-15">
			<input type="text" id="exf1"
				placeholder="<spring:message code="" text="Search Decision"/>"
				class="form-control"> <span
				class="glyphicon glyphicon-search form-control-feedback"></span>
		</div>
		<div class="additional-btn">
			<a href="#" data-toggle="tooltip"
				data-original-title="<spring:message code="workflow.multiple.events" text="Select multiple events by Ctrl+Select"/>">
				<i class="fa fa-question-circle fa-lg"></i>
			</a>
		</div>
		<div class="clearfix"></div>
		<div class="col-xs-6">
			<form:select path="decisionMapSelectedId" id="decisionMapSelectedId"
				class="form-control height-300">
				<c:forEach items="${decisionNotSelected}" var="objArray">
					<form:option value="${objArray.decisionId}" label="${objArray.decisionValue}"
						class="instafilta-target">${objArray.decisionDescFirst}</form:option>
				</c:forEach>
			</form:select>
		</div>
		<div class="col-xs-1">
			<button type="button" class="btn btn-blue-2 btn-block" id="moveright"
				onclick="move_list_items('decisionMapSelectedId','decisionMapId');">
				<i class="fa fa-angle-right"></i>
			</button>
			<button type="button" class="btn btn-blue-3 btn-block"
				id="moverightall"
				onclick="move_list_items_all('decisionMapSelectedId','decisionMapId');">
				<i class="fa fa-angle-double-right"></i>
			</button>
			<button type="button" class="btn btn-blue-2 btn-block" id="moveleft"
				onclick="move_list_items('decisionMapId','decisionMapSelectedId');">
				<i class="fa fa-angle-left"></i>
			</button>
			<button type="button" class="btn btn-blue-3 btn-block"
				id="moveleftall"
				onclick="move_list_items_all('decisionMapId','decisionMapSelectedId');">
				<i class="fa fa-angle-double-left"></i>
			</button>
		</div>
		<div class="col-xs-5">
			<form:select path="decisionMapId" id="decisionMapId" multiple="multiple"
				cssClass="form-control height-300">
				<c:forEach items="${decisionSelected}" var="objArray">
					<form:option value="${objArray.decisionId}" label="${objArray.decisionValue}"
						class="instafilta-target">${objArray.decisionDescFirst}</form:option>
				</c:forEach>
			</form:select>
		</div>
	</div>
	<div class="text-center">
		<c:if test="${mode != 'VIEW'}">
			<input type="button"
				value="<spring:message code="master.save" text="Save"/>"
				class="btn btn-success btn-submit" id="submitBtnId" />
		</c:if>
		<input type="button" class="btn btn-danger back-btn"
			value="<spring:message code="back.msg" text="Back"/>"
			onclick="window.location.href='RolesMaster.html'" />
	</div>
</form:form>