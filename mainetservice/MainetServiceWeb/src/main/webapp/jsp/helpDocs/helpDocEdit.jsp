
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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/helpDocs/helpDocs.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<c:if test="${not command.hasValidationErrors()}">
</c:if>
<%
    request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
%>

<script>
	function saveForm(element) {

		return saveOrUpdateForm(element, getLocalMessage("eip.admin.helpDocs.sucessmsg"),
				'CommonHelpDocs.html', 'saveform');
	}
</script>
<!-- <div class="content">
                 	<div class="widget">
		           	<div class="widget-header"> -->
<%-- <h2> <strong><spring:message code="eip.admin.helpDocs.header" /></strong></h2>
                   	</div> --%>
<div class="widget-content padding">
	<form:form action="HelpDoc.html" name="frmHelpEdit" id="frmHelpEdit"
		method="post" class="form">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<input type="hidden" value="E" name="modeOfType">
		<div class="form-group clearfix">
			<label class="col-sm-2 control-label"><spring:message code="eip.admin.helpDocs.formname"
					text="Form Name" /> :</label>
			<div class="col-sm-4">
				<%-- <span><apptags:inputField fieldPath="entity.moduleName" isDisabled="true" cssClass="form-control"/></span> --%>
				<form:select path="entity.moduleName"
					cssClass=" form-control mandClassColor" id="deptCode"
					disabled="true">
					<form:option value="0">
						<spring:message code="eip.admin.helpDocs.selformname" text="Select Form Name" />
					</form:option>
					<c:forEach items="${command.nodes}" var="look">
						<form:option value="${look.value}" label="${look.key}" />
					</c:forEach>
				</form:select>
				<form:hidden path="entity.moduleName" />
			</div>
		</div>
		<div class="text-center padding-10 clearfix">
			<input type="button" class="btn btn-success btn-submit"
				onclick="return saveForm(this);"
				value="<spring:message code="eip.admin.helpDocs.save"/>" />
			<apptags:backButton url="CommonHelpDocs.html"
				cssClass="btn btn-primary " />
			<apptags:resetButton cssClass="btn btn-default" />
		</div>

	</form:form>

	<c:if test="${not empty command.filesDetails}">
		<table class="table table-bordered">
			<tr>
				<th><spring:message code="eip.admin.helpDocs.lang" text="Language" /></th>
				<th width="60%"><spring:message code="eip.admin.helpDocs.document" text="Document" /></th>
			</tr>

			<c:forEach var="singleDoc" items="${command.filesDetails}"
				varStatus="count">

				<c:set var="links" value="${fn:split(singleDoc.lookUpDesc,',')}" />
				<div class="document">
					<c:forEach items="${links}" var="download" varStatus="status">
						<c:set var="link" value="${singleDoc.lookUpCode}" />
						<tr>
							<c:if test="${count.count==1}">
								<td><spring:message code="eip.admin.helpDocs.curruntEng"
					text="Current Help Document For English" /></td>
								<td><apptags:filedownload filename="${link}"
										filePath="${download}" actionUrl="HelpDoc.html?Download"></apptags:filedownload>

									<form:form action="HelpDoc.html" name="frmHelpEditDoc"
										id="frmHelpEditDoc" method="post" class="form">

										<apptags:formField fieldType="7"
											fieldPath="entity.cfcAttachments[0].attPath" labelCode=""
											hasId="true" isMandatory="true" fileSize="COMMOM_MAX_SIZE"
											currentCount="0" showFileNameHTMLId="true" folderName="0"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC" />
									</form:form></td>
							</c:if>
							<c:if test="${count.count ==2}">
								<td><spring:message code="eip.admin.helpDocs.curruntReg"
					text="Current Help Document For Regional" /></td>
								<td><apptags:filedownload filename="${link}"
										filePath="${download}" actionUrl="HelpDoc.html?Download"></apptags:filedownload>
									<form:form action="HelpDoc.html" name="frmHelpEditDoc"
										id="frmHelpEditDoc" method="post" class="form">

										<apptags:formField fieldType="7"
											fieldPath="entity.cfcAttachments[1].attPath" labelCode=""
											hasId="true" isMandatory="true" fileSize="COMMOM_MAX_SIZE"
											currentCount="1" showFileNameHTMLId="true" folderName="1"
											maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC" />

									</form:form></td>
							</c:if>
						</tr>
					</c:forEach>
				</div>
			</c:forEach>
		</table>
	</c:if>
</div>
