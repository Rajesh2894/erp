<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

		<apptags:breadcrumb></apptags:breadcrumb>
<c:if test="${not command.hasValidationErrors()}">
</c:if>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>

<div class="content" >
	<div class="widget">
	     <div class="widget-header">  <h2><strong><spring:message code="eip.admin.helpDocs.breadcrumb" /></strong></h2></div>
	     <apptags:helpDoc url="CommonHelpDocs.html"></apptags:helpDoc>
			<div class="widget-content padding ">
	<form:form  id="myForm" class="form-horizontal" >
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message code="eip.form.name" text="Form Name" /></label>
							<div class="col-sm-4">
							<form:select path="entity.moduleName"  cssClass=" form-control mandClassColor" id="deptCode">
							<form:option value="0" > <spring:message code="eip.select.form.name" text="Select Form Name" /></form:option> 
							<c:forEach items="${command.nodes}" var="look">
							  	<form:option value="${look.key}" label="${look.value}"/>
							</c:forEach>
						</form:select>
						</div>
					</div>
					<div class="text-center padding-10">
							<a href="javascript:void(0);"  onclick="findAll(this)"class="btn btn-blue-3"><i class="fa fa-search"></i> <spring:message code="eip.admin.auth.search" /></a>
							<a href="javascript:void(0);"  onclick="openForm('HelpDoc.html')"class="btn btn-success"><i class="fa fa-plus-circle"></i><spring:message code="addBtn" text="Add"/></a>
							<spring:url var="cancelButtonURL" value="CommonHelpDocs.html" />
								<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message code="rstBtn" text="Reset"/></a>
		 			</div>   
					
				</form:form>
				<div class="table-responsive" id="quickLinkGrid">
					<apptags:jQgrid id="helpDocs"
						url="CommonHelpDocs.html?List_Of_Services" mtype="POST"
						gridid="gridCommonHelpDocs" 
						deleteURL="CommonHelpDocs.html"
						editurl="HelpDoc.html"
						colHeader="eip.admin.helpDocs.gridTitle.uploadFileNameEng,eip.admin.helpDocs.gridTitle.uploadFileNameReg"
						colModel="[
								{name : 'fileNameEng',index : 'fileNameEng', editable : true,sortable : false,search : false, align : 'center' },
								{name : 'fileNameReg',index : 'fileNameReg', editable : true,sortable : false,search : false, align : 'center' }
					             ]"
						height="200" caption="eip.admin.helpDocs.gridTitle.uploadDocs" isChildGrid="false"
						hasViewDet="false" 
						hasEdit="true" 
						hasDelete="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
				</div> 
				
			</div>
</div>
</div>