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
<script src="js/account/accountPrimayHeadCodeMaster.js"></script>
<script src="js/menu/jquery.dynatree.js"></script>
<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css">


<apptags:breadcrumb></apptags:breadcrumb>

<div id="heading_wrapper" class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Primary Account Code"></spring:message>
			</h2>
			<apptags:helpDoc url="PrimaryAccountCodeMaster.html" helpDocRefURL="PrimaryAccountCodeMaster.html"></apptags:helpDoc>	
		</div>
		<div class="error-div" style="display: none;" id="errorDivDeptMas"></div>
		<div class="widget-content padding">

			<form:form method="post" name=""
				modelAttribute="accountHeadPrimaryAccountCodeMasterBean"
				class="form-horizontal">
				<div class="text-center padding-bottom-10">
					<a class="btn btn-blue-2  createPrimaryCode" href=''><i
						class="fa fa-plus-circle"></i> <spring:message code="account.bankmaster.add"
							text=" Create Primary Code" /></a> <a
						class="btn btn-primary createPrimaryCode" href=''><spring:message
							code="account.edit" text=" Edit Primary Code" /></a>
				</div>






				<input type="hidden" value="${modeFlag}" id="modeFlagV" />
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="account.master.menu.structure.label" text="Menu Structure" /></label>
					<div class="col-sm-10">
						<div id="tree" class="notes createStructure">
							<c:set var="node" value="${node}" scope="request" />
							<jsp:include page="treeViewRepresentationPrimaryCode.jsp"></jsp:include>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script>
</script>
