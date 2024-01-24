<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<br/>
<br/>
<div class="popup-form-div">
	<form:form method="post" action="AdminCategoryForm.html"
		name="frmAdminCategoryForm" id="frmAdminCategoryForm">

		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		
		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message code="admin.categoryList.TitleEn" /> :</label>
				<span><apptags:inputField fieldPath="entity.catTitleEng"></apptags:inputField><span class="mand" style="color: red">*</span></span>
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message code="admin.categoryList.TitleReg" /> :</label>
				<span><apptags:inputField fieldPath="entity.catTitleReg"></apptags:inputField><span class="mand" style="color: red">*</span></span>
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message code="admin.categoryList.CatDescEn" /> :</label>
				<span><form:textarea path="entity.catDescEn" cssStyle="width:150px ; height : 80px;" /><span class="mand" style="color: red">*</span></span>
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message code="admin.categoryList.CatDescReg" /> :</label>
				<span><form:textarea path="entity.catDescReg" cssStyle="width:150px ; height : 80px;" /><span class="mand" style="color: red">*</span></span>
			</div>
		</div>
		
		<div class="buttons btn-fld" align="center">
			<apptags:submitButton entityLabelCode="Admin Category" successUrl="AdminCategory.html" />
			<apptags:resetButton />
			<apptags:backButton url="AdminCategory.html" />
		</div>
		
	</form:form>
</div>