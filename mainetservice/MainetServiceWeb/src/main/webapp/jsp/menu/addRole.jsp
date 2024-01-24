<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/menu/jquery.cookie.js" type="text/javascript"></script>
<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/menu/jquery.dynatree.js"></script>
<script type="text/javascript" src="js/menu/entitlement.js"></script>

				<div class="form-div">
				
					<form:form method="post" action="entitlement.html?saveForm"
						name="frmmanageRoleForm" id="frmmanageRoleForm"
						class="form-horizontal">
					<h4>Add Role Code</h4>
			            <jsp:include page="/jsp/tiles/validationerror.jsp" />
						
						<c:if test="${command.successMsg eq true}">
							<div class="success-div alert alert-success alert-dismissible margin-bottom-20">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
								<ul>
									<li><spring:message code="menu.create.role.success" /></li>
								</ul>
							</div>
						</c:if>


						<div id="createTemp">
					
						<div class="form-group">
						
								
							<label class="col-sm-2 control-label required-control"> <spring:message
									code="menu.new.roleCode.label" />
							</label>
							<div class="col-sm-4">
								<input type="text" name="roleName"
									class="hasNotAllowSpecialLang form-control" id="roleName" />
								<%-- <p id="pull_exist">
									[<a href="javascript:void(0);" onclick="showTemplate('E');"><spring:message code="menu.exist.template.link" /></a>]
								</p> --%>
							</div>
							<label class="col-sm-2 control-label required-control">Department</label>
							<div class="col-sm-4">
								<select id="tempDpDeptId" class="chosen-select-no-results form-control" name="tempDpDeptId" >
									<option value="">Select</option>
									<c:forEach items="${command.departmentList}" var="deptListData">
										<c:choose>
											<c:when test="${deptListData.dpDeptid eq command.dpDeptId}">
												<option value="${deptListData.dpDeptid }" code="${deptListData.dpDeptcode }" selected="selected">${deptListData.dpDeptdesc }</option>
											</c:when>
											<c:otherwise>
												<option value="${deptListData.dpDeptid }" code="${deptListData.dpDeptcode }">${deptListData.dpDeptdesc }</option>
											</c:otherwise>
										</c:choose>										
									</c:forEach>
								</select>
							</div>
							<input type="hidden" name="dpDeptId" id="dpDeptId" >
						</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"> <spring:message
									code="menu.role.description.label" /> <spring:message
									code="menu.langType.english.label" />
							</label>
							<div class="col-sm-4">
								<input type="text" class="hasNotAllowSpecialLang form-control"
									name="groupDescE" id="groupDescE" />
							</div>
							<label class="col-sm-2 control-label"> <spring:message
									code="menu.role.description.label" /> <spring:message
									code="menu.langType.reg.label" />
							</label>
							<div class="col-sm-4">
								<input type="text" name="groupDescR" class="form-control"
									id="groupDescR" />
							</div>
						</div> 
						
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="menu.structure.label"/></label>
						<div class="col-sm-10">
							<div id="tree" class="notes createStructure">
								<c:set var="node" value="${command.entitlements}" scope="request" />
								<jsp:include page="entitlementDynaTree.jsp"></jsp:include>
							</div>
						</div>
						<div class="col-sm-10">
							<div class="existStructure notes"></div>
 						</div>
 						</div>
 						<spring:message code="menu.entitle.save" var="saveRole" />
						

						<input type="hidden" name="menuIds" id="menuIds" /> <input
							type="hidden" name="deActiveNodes" id="deActiveNodes" /> <input
							type="hidden" name="checkAction" id="checkAction" />

						<div class="text-center clearfix padding-top-10">
							<input type="button" id="entitleSubmit" class="btn btn-success btn-submit" value='${saveRole}'>

							<%-- <input type="button" id="addDataBackButton" class="btn btn-danger" 
							    value="${backBtn}" onclick="back();"> --%>
							<apptags:backButton url="entitlement.html" /> 

						</div>
                       </form:form>
				</div>
