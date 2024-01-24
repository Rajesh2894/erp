
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/commanremark/commanremark.js">
	
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="common.master.remark.master" text="Remark master"/></h2>
			<apptags:helpDoc url="CommonRemarkMaster.html" helpDocRefURL="CommonRemarkMaster.html"></apptags:helpDoc>
        </div>
		<div class="widget-content padding">
		
		<div class="mand-label clearfix"><span><spring:message code="contract.breadcrumb.fieldwith" text="Field with"/><i class="text-red-1">*</i><spring:message code="common.master.mandatory" text=" is mandatory"/></span></div>
			<form:form id="serviceruleDefForm" modelAttribute="tbApprejMas"
				name="tbApprejMas" class="form-horizontal" method="post"
				commandName="tbApprejMas">
        <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
	     <button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span>&times;</span></button>
	 	<span id="errorId"></span>
     </div>
				<!-- Here i have to start the code -->
				<div class="table clear padding_top_10">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message code="common.master.remark.type" text="Remark Type"/></label>
						<div class="col-sm-4">
							<input type="hidden" id="langId" value="${languageId}" />
							<form:select cssClass="form-control" path="artType">
								<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
								<c:choose>
									<c:when test="${languageId eq 1}">
										<c:forEach items="${listRemark}" var="Remark">
											<option value="${Remark.lookUpId }"
												code="${Remark.lookUpCode }">${Remark.lookUpDesc }</option>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach items="${listRemark}" var="Remark">
											<option value="${Remark.lookUpId }"
												code="${Remark.lookUpCode }">${Remark.lookUpDesc }</option>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</form:select>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="contract.label.department" text="Department"/></label>
					<div class="col-sm-4">
						<input type="hidden" id="langId" value="${languageId}" /> <form:select
							class="form-control" id="deptId" path="deptId"
							onchange="refreshServiceData(this)">
							<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
							<c:choose>
								<c:when test="${languageId eq 1}">
									<c:forEach items="${deptList}" var="deptData">
										<option value="${deptData.dpDeptid }"
											code="${deptData.dpDeptcode }">${deptData.dpDeptdesc }</option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${deptList}" var="deptData">
										<form:option value="${deptData.dpDeptid }"
											code="${deptData.dpDeptcode }">${deptData.dpNameMar }</form:option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message code="common.master.service.name" text="Service Name"/></label>
					<div class="col-sm-4">
						<form:select class="form-control" id="serviceId" path="artServiceId">
							<form:option value=""><spring:message code="master.selectDropDwn"/></form:option>
						</form:select>
					</div>
				</div>
				<div id="divid">
					<div class="text-center padding-top-10 padding-bottom-10">
						<input class="btn btn-success btn-submit" onClick="searchServiceMst(this);" value="<spring:message code="master.search" text="Search"/>" type="button"> 
						<input class="btn btn-warning" value="<spring:message code="reset.msg" text="Reset"/>" type="Reset" onclick="resetHomePage()"> 
						<input type="button" value="<spring:message code="add.msg" text="Add"/>" class="btn btn-blue-2" onclick="openAddForm()">
					</div>


					<div>
						<table id="grid"></table>
						<div id="pagered"></div>
					</div>
				</div>
			</form:form>

		</div>
	</div>
</div>