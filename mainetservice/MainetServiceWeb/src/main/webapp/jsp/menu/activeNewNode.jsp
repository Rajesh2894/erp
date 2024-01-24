<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<script type="text/javascript" src="js/menu/entitlement.js"></script>
<script type="text/javascript" src="js/menu/jquery.dynatree.js"></script>

	<div class="form-div form-horizontal">
	<h4>Edit Existing Role Code</h4>
	<form:form action="entitlement.html?saveEditActiveNode" name="activeNewNode" id="activeNewNode" class="form" method="POST">
	<input type="hidden" name="menuIds" id="menuIds" />
	<div class="form-group clear"  id="activeExistTemp">
		<label class="col-sm-2 control-label required-control" id="selectLAbelActive"><spring:message code="menu.roleCode.label" /></label>
		<div class="col-sm-4">
			<form:select path="editedRoleId" id="activeRoleSelect" cssClass="form-control" class="chosen-select-no-results form-control">
				<form:option value="0"><spring:message code="" text="select"/></form:option>
					<c:forEach var="groupmst" items="${command.getGroupMaster()}">
					<c:choose>
						<c:when test="${command.isSuperAdmin eq 'Y'}">
							<form:option value="${groupmst.gmId}">${groupmst.grCode}</form:option>
						</c:when>
						<c:otherwise>
							<c:if test="${command.adminGmId ne groupmst.gmId}">
								<form:option value="${groupmst.gmId}">${groupmst.grCode}</form:option>
							</c:if>
						</c:otherwise>
					</c:choose>
					</c:forEach>
			</form:select>
		</div>
		<label class="col-sm-2 control-label"><spring:message code="master.department"/></label>
		<div class="col-sm-4">
		<select id="dpDeptId" class="form-control" name="dpDeptId" disabled="disabled">
									<option value="">Select</option>
									<c:forEach items="${command.departmentList}" var="deptListData">
										<option value="${deptListData.dpDeptid }" code="${deptListData.dpDeptcode }">${deptListData.dpDeptdesc }</option>
									</c:forEach>
								</select>
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-2 control-label"> <spring:message code="menu.role.description.label" /><spring:message code="menu.langType.english.label" /></label>
		<div class="col-sm-4">
			<input type="text" class="hasNotAllowSpecialLang form-control" name="roleDescriptionEng" id="roleDescriptionEng" />
		</div>
		
		<label class="col-sm-2 control-label"> <spring:message code="menu.role.description.label" /> <spring:message code="menu.langType.reg.label" /></label>
		<div class="col-sm-4">
			<input type="text" name="roleDescriptionReg" class="form-control" id="roleDescriptionReg" />
		</div>
	</div>
	
	<div class="form-group clear" id="table_template">
		<label class="col-sm-2 control-label required-control"><spring:message code="menu.structure.label" /></label>
		<div class="col-sm-10">
			<div id="tree" class="notes createStructure">
				<c:set var="node" value="${command.entitlements}" scope="request" />
				<jsp:include page="entitlementDynaTree.jsp"></jsp:include>
			</div>
		</div>
	</div>
	 
	
		
		
		<spring:message code="menu.entitle.back" var="backBtn" />
		<spring:message code="menu.entitle.save" var="saveRole" />
		
		<div class="text-center margin-top-10">
				<input type="button" id="entitleActiveSubmit" class="btn btn-success btn-submit"
					value='${saveRole}'>
				<input type="button" id="addDataBackButton" class="btn btn-danger" value="${backBtn}" onclick="back();">

		</div>
	</form:form>
	</div>
