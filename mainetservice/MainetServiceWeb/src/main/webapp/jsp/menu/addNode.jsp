<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="js/menu/addNode.js"></script>
<script type="text/javascript" src="js/menu/jquery.dynatree.js"></script>
<script type="text/javascript">
	jQuery('.hasHtmlLink').keyup(function (evt) {
		var theEvent = evt || window.event;
		var key = theEvent.keyCode || theEvent.which;
		if(key == 37 || key == 39 || key == 8 || key == 46) {
		return;
		}
		/* }else {
			if(key == 16 || key == 51){
				$('#smfaction').attr("maxlength",1);
			}else{
				this.value = this.value.replace(/[^a-z A-Z0-9.= + ? \/]/g,'');
			}
		} */
	});
</script>
<form:form method="post" action="" name="frmAddForm" id="frmAddForm" class="form-horizontal">
	<div class="alert alert-danger alert-dismissible error-div" id="errorDivEntitle"></div>
	<jsp:include page="/jsp/tiles/validationerror.jsp" />
    <c:if test="${command.addOrEdit eq 'A'}">
    <h4>Add Node In Template</h4>
	    <div class="form-group" id="table_template">
		    <label class="col-sm-2 control-label"><spring:message code="menu.create.root.node" /></label>
		    <div class="col-sm-10">
				<input class="margin-left-0 margin-top-10" type="checkbox" id="rootNode" name="rootNode"> 				
			</div>
		</div>
	</c:if>
	<c:if test="${command.addOrEdit eq 'E'}">
	<h4>Edit Node In Template</h4>
	</c:if>
<div class="form-group">
	<label class="col-sm-2 control-label required-control"><spring:message code="menu.structure.label" /></label>
	<div class="col-sm-10">
	
		<div class="notes mandColorClass" id="tree3">
		    <c:set var="addnNodeVar" value="${command.entitlements}" scope="request" />
			<jsp:include page="addNodeDynaTree.jsp"></jsp:include>
		</div>
	</div>
</div>

	<div class="text-center">
		<input type="button" id="noDataBackButton" class="btn btn-danger" value="Back" onclick="back();">
	</div>
	
	<div id="showMasterForm">
		<div id="content">
			
			<div class="form-group padding-top-10">
				<label class="control-label col-sm-2 required-control">
					<spring:message code="menu.node.name" />
					<spring:message code="menu.langType.english.label" />
				</label>
				<div class="col-sm-4">
					<apptags:inputField fieldPath="entitle.smfname" hasId="true"
						maxlegnth="100" cssClass="form-control hasNameClass hasNotAllowSpecialLang"
						isDisabled="" isMandatory="true"/>
				</div>
				<label class="control-label col-sm-2 required-control">
					<spring:message code="menu.node.name" />
					<spring:message code="menu.langType.reg.label" />
				</label>
				<div class="col-sm-4">
					<apptags:inputField fieldPath="entitle.smfname_mar" hasId="true" maxlegnth="1000" cssClass="form-control hasNameClass" isMandatory="true" isDisabled="" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2 required-control">
					<spring:message code="menu.node.description" />
				</label>
				<div class="col-sm-4">
					<apptags:inputField fieldPath="entitle.smfdescription" maxlegnth="100" cssClass="form-control hasNotAllowSpecialLang" hasId="true" isMandatory="true" isDisabled="" />
				</div>
			<label class="control-label col-sm-2 required-control">
				<spring:message code="menu.node.action" />
			</label>
			<div class="col-sm-4">
				<apptags:inputField fieldPath="entitle.smfaction" maxlegnth="1000" cssClass="form-control hasHtmlLink" hasId="true" isDisabled="" isMandatory="true"/>
			</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-2">
					<spring:message code="menu.node.Menu_Param_1"/>
				</label>
				<div class="col-sm-4">
				<apptags:inputField fieldPath="entitle.smParam1" hasId="true"
						maxlegnth="150" cssClass="form-control" />
				</div>
				<label class="control-label col-sm-2">
					<spring:message code="menu.node.Menu_Param_2"/>
				</label>
				<div class="col-sm-4">
					<apptags:inputField fieldPath="entitle.smParam2" hasId="true" maxlegnth="150" cssClass="form-control"/>
				</div>
			</div>
			
			<form:hidden path="entitle.hiddenEtId" id="etId" />
			<form:hidden path="entitle.smfflag" id="flag" />
			<form:hidden path="addOrEdit" id="addOredit"/>
			<spring:message code="menu.entitle.save" var="saveNode" />
			<spring:message code="menu.entitle.back" var="backBtn" />
			
			<div id="addDataDiv">
				<input type="hidden" name="menuIds" id="menuIds" />
				<div class="text-center">
					<input type="button" id="addNodeButton" class="btn btn-success btn-submit" value="${saveNode}"> 
					<input type="button" id="addDataBackButton" class="btn btn-danger" value="${backBtn}" onclick="back();">
				</div>
			</div>

		</div>
	</div>

</form:form>
