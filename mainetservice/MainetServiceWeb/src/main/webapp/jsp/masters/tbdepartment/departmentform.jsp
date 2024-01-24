<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="js/masters/tbdepartment/departmentform.js"></script>
<script type="text/javascript">
	jQuery('.hasCharacter').keyup(function() {
		if (key == 37 || key == 39 || key == 8 || key == 46) {
			return;
		} else {
			this.value = this.value.replace(/[^a-z A-Z]/g, '');
		}
	});
	jQuery('.hasCharacterInUperCase').keyup(function() {
		if (key == 37 || key == 39 || key == 8 || key == 46) {
			return;
		} else {
			this.value = this.value.replace(/[^a-z A-Z]/g, '').toUpperCase();
			;
		}
	});
	$("input").on("keypress", function(e) {
		if (e.which === 32 && !this.value.length)
			e.preventDefault();
	});
</script>

<div id="heading_wrapper" class="">
	<div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
	<div class="widget">
		<c:url value="${saveAction}" var="url_form_submit" />
		<form:form method="post" action="${url_form_submit}"
			name="departmentForm" id="departmentForm" class="form-horizontal">
			<div class="error-div alert alert-danger alert-dismissible"
				style="display: none;" id="errorDivDeptMas">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<input type="hidden" value="${mode}" id="formModeId">
			<c:if test="${mode != 'create'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				<form:hidden path="dpDeptid" id="dpDeptid" />
				<form:hidden path="userId" />
				<form:hidden path="langId" />
				<form:hidden path="lmodDateStr" />
			</c:if>

			<div class="form-group">
				<label for="tbDepartment_dpDeptdesc"
					class="col-sm-2 control-label required-control"><spring:message
						code="tbDepartment.dpDeptdesc" /></label>
				<div class="col-sm-4">
					<c:choose>
						<c:when test="${transactionCount gt 0 }">
							<form:input id="tbDepartment_dpDeptdesc" path="dpDeptdesc"
								class="form-control mandClassColor hasCharacter" 
								maxLength="400" />
								<!-- #116488 -->
							<%-- <form:hidden path="dpDeptdesc" /> --%>
						</c:when>
						<c:otherwise>
							<form:input id="tbDepartment_dpDeptdesc" path="dpDeptdesc"
								class="form-control mandClassColor hasCharacter" maxLength="400" />
						</c:otherwise>
					</c:choose>
					<form:errors id="tbDepartment_dpDeptdesc_errors" path="dpDeptdesc"
						cssClass="label label-danger" />
				</div>

				<label for="tbDepartment_dpNameMar"
					class="col-sm-2 control-label required-control"><spring:message
						code="tbDepartment.dpNameMar" /></label>
				<div class="col-sm-4">
					<c:choose>
						<c:when test="${transactionCount gt 0 }">
							<form:input id="tbDepartment_dpNameMar" path="dpNameMar"
								class="form-control" />
								<!-- #116488 -->
							<%-- <form:hidden path="dpNameMar" /> --%>
						</c:when>
						<c:otherwise>
							<form:input id="tbDepartment_dpNameMar" path="dpNameMar"
								class="form-control" maxLength="400" />
						</c:otherwise>
					</c:choose>
					<form:errors id="tbDepartment_dpShortdesc_errors" path="dpNameMar"
						cssClass="label label-danger" />
				</div>
			</div>
			<div class="form-group">
				<label for="tbDepartment_dpDeptcode"
					class="col-sm-2 control-label required-control"><spring:message
						code="tbDepartment.dpDeptcode" /></label>
				<div class="col-sm-4">
					<c:choose>
						<c:when test="${mode == 'create'}">
							<form:input id="tbDepartment_dpDeptcode" path="dpDeptcode"
								class="form-control hasCharacter" maxLength="4" minLength="2"
								style="text-transform: uppercase;" />
						</c:when>
						<c:otherwise>
							<form:input id="tbDepartment_dpDeptcode" path="dpDeptcode"
								class="form-control hasCharacter" disabled="true" />
							<form:hidden path="dpDeptcode" />
						</c:otherwise>
					</c:choose>
					<form:errors id="tbDepartment_dpDeptcode_errors" path="dpDeptcode"
						cssClass="label label-danger" />
				</div>
				<label for="tbDepartment_dpPrefix" class="col-sm-2 control-label"><spring:message
						code="tbDepartment.dpPrefix" /></label>
				<div class="col-sm-4">
					<form:input id="tbDepartment_dpPrefix" path="dpPrefix"
						class="form-control hasCharacterInUperCase" maxLength="3"
						style="text-transform: uppercase;" />
					<form:errors id="tbDepartment_dpPrefix_errors" path="dpPrefix"
						cssClass="label label-danger" />
				</div>
			</div>
			<div class="form-group">
				<label for="tbDepartment_status" class="col-sm-2 control-label"><spring:message
						code="tbDepartment.status" /><span class="mand">*</span></label>
				<div class="col-sm-4">
					<label class="radio-inline padding-left-20"><form:radiobutton
							path="status" value="A" id="isActive1"
							onchange="checkForTransaction()" /> <spring:message
							code="master.active" text="Active" /></label> <label
						class="radio-inline"> <c:if test="${mode == 'create'}">
							<form:radiobutton path="status" value="I" id="isActive2"
								disabled="true" />
							<spring:message code="master.Inactive" text="Inactive" />
						</c:if> <c:if test="${mode != 'create'}">
							<form:radiobutton path="status" value="I" id="isActive2"
								onchange="checkForTransaction()" />
							<spring:message code="master.Inactive" text="Inactive" />
						</c:if> <form:errors id="tbDepartment_status_errors" path="status"
							cssClass="label label-danger" />
					</label>
				</div>

				<div id="actual-div">
					<label class="col-sm-2 control-label"><spring:message
							code="tbDepartment.actualdept" text="Is Actual Department" /></label>
					<div class="col-sm-4">
						<label for="tbDepartment_dpCheck"
							class="checkbox-inline padding-left-20"> <form:checkbox
								path="activeChkBox" id="dpCheckId" /> <form:hidden
								path="dpCheck" id="dpCheck" /> <form:errors
								id="tbDepartment_dpCheck_errors" path="dpCheck"
								cssClass="label label-danger" />
						</label>
					</div>
				</div>
			</div>
			<div class="text-center">
				<input type="button" class="btn btn-success btn-submit"
					value="<spring:message code="save" text="Save"/>"
					onclick="return validateData(this)"> <input type="reset"
					class="btn btn-warning"
					value="<spring:message code="reset.msg" text="Reset"/>"
					onclick="resetDept()"> <input type="button"
					class="btn btn-danger"
					value="<spring:message code="back.msg" text="Back"/>"
					onclick="window.location.href='Department.html'" />

			</div>
		</form:form>
	</div>
</div>