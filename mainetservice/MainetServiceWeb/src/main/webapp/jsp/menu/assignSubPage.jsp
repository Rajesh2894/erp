<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType(
			"text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/menu/dataEntitle.js"></script>
<link href="css/menu/nestable-style.css" rel="stylesheet" type="text/css">
<script src="js/menu/jquery.nestable.js"></script> 
<script>
$(document).ready(function(){
	   $("#nestable").nestable({
	      maxDepth: 10,
	      collapsedClass:'dd-collapsed',
	   }).nestable('collapseAll');//Add this line
	});
</script>

<div class="cf nestable-lists">
  <div class="dd" id="nestable">
    <ol class="dd-list">
	<c:forEach var="masters" items="${command.parentRoleList}">
		<li class="dd-item"  id="${masters.entitle.smfid}">
		<div class="dd-handle">${masters.entitle.smfname}</div>
			<ol class="dd-list">
				<c:forEach items="${command.childRoleList}" var="data">
					<c:if test="${masters.entitle.smfid eq  data.parentId}">
						<c:set var="action0" value="${data.entitle.smfaction}" />
						<li class="dd-item" id="${data.entitle.smfid}">
						<div class="dd-handle">${data.entitle.smfname}
							<c:if test="${data.lastNode eq true}">
								<div class="pull-right">
								<label class="margin-left-40"><input type="checkbox"
									id="A/${data.roleEtId}" class='checkbox1'><spring:message code="menu.trans.add" /></label>
								<label class="margin-left-40"><input type="checkbox"
									id="E/${data.roleEtId}" class='checkbox1'><spring:message code="menu.trans.update" /></label>
								<label class="margin-left-40"><input type="checkbox"
									id="D/${data.roleEtId}" class='checkbox1'><spring:message code="menu.trans.delete" /></label>
								
								</div>
							</c:if></div>
                            <ol class="dd-list">
								<c:forEach items="${command.childRoleList}" var="data1">
									<c:if test="${data.entitle.smfid eq  data1.parentId}">
										<c:set var="action1" value="${data1.entitle.smfaction}" />
										<li class="dd-item" id="${data1.entitle.smfid}">
											<div class="dd-handle">${data1.entitle.smfname}
											<c:if test="${data1.lastNode eq true}">
												<div class="pull-right">
												<label class="margin-left-40"><input type="checkbox"
													id="A/${data1.roleEtId}" class='checkbox1'><spring:message code="menu.trans.add" /></label>
												<label class="margin-left-40"><input type="checkbox"
													id="E/${data1.roleEtId}" class='checkbox1'><spring:message code="menu.trans.update" /></label>
												<label class="margin-left-40"><input type="checkbox"
													id="D/${data1.roleEtId}" class='checkbox1'><spring:message code="menu.trans.delete" /></label>
												
													</div>
											</c:if></div>
											 <ol class="dd-list">
												<c:forEach items="${command.childRoleList}" var="data2">

													<c:if test="${data1.entitle.smfid eq  data2.parentId}">
														<c:set var="action2" value="${data2.entitle.smfaction}" />
														<li class="dd-item" id="${data2.entitle.smfid}">
														<div class="dd-handle">${data2.entitle.smfname}
															<c:if test="${data2.lastNode eq true}">
															<div class="pull-right">	
															    <label class="margin-left-40"><input
																	type="checkbox" id="A/${data2.roleEtId}"
																	class='checkbox1'><spring:message code="menu.trans.add" /></label>
																<label class="margin-left-40"><input
																	type="checkbox" id="E/${data2.roleEtId}"
																	class='checkbox1'><spring:message code="menu.trans.update" /></label>
																<label class="margin-left-40"><input
																	type="checkbox" id="D/${data2.roleEtId}"
																	class='checkbox1'><spring:message code="menu.trans.delete" /></label>
																</div>
															</c:if></div>
															 <ol class="dd-list">
																<c:forEach items="${command.childRoleList}" var="data3">
																	<c:if test="${data2.entitle.smfid eq  data3.parentId}">
																		<c:set var="action3"
																			value="${data3.entitle.smfaction}" />
																		<li class="dd-item" id="${data3.entitle.smfid}">
																		<div class="dd-handle">${data3.entitle.smfname}
																			<c:if test="${data3.lastNode eq true}">
																				<div class="pull-right"><label class="margin-left-40"><input
																					type="checkbox" id="A/${data3.roleEtId}"
																					class='checkbox1'><spring:message code="menu.trans.add" /></label>
																				<label class="margin-left-40"><input
																					type="checkbox" id="E/${data3.roleEtId}"
																					class='checkbox1'><spring:message code="menu.trans.update" /></label>
																				<label class="margin-left-40"><input
																					type="checkbox" id="D/${data3.roleEtId}"
																					class='checkbox1'><spring:message code="menu.trans.delete" /></label>
																				</div>
																			</c:if> </div>
																		</li>
																	</c:if>
																</c:forEach>
															</ol>
														</li>
													</c:if>
												</c:forEach>
											</ol>
										</li>
									</c:if>
								</c:forEach>
							</ol>
						</li>
					</c:if>
				</c:forEach>
			</ol>
		</li>
	</c:forEach>

</ol>
</div>
</div>

<spring:message code="menu.entitle.back" var="backBtn" />
<spring:message code="menu.entitle.save" var="saveRole" />

<div class="text-center margin-top-10">
	<input type="button" id="dataEntitleSubmit" class="btn btn-success btn-submit"
		value='${saveRole}'>
		<apptags:backButton url="entitlement.html" /> 

</div>
