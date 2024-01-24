<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/masters/trasactions/ManualReceipt/CommonManualReceiptEntry.js"></script>
	
	<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">

	<div class="widget">
	
	
	<div class="widget-header">
			<h2>
				<strong>Manual Receipt Entry</strong>
			</h2>	
			
		</div>
		<div class="widget-content padding">
		
		<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /></span>
			</div>
			
			<form:form action="CommonManualReceiptEntry.html"
				class="form-horizontal form" name="CommonManualReceiptEntry"
				id="CommonManualReceiptEntry">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="accordion-toggle ">
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="license.validity.dept.name" text="Department Name" /></label>

					<div class="col-sm-4">

						<form:select path="depShortCode" id="departmentId"
							class="chosen-select-no-results" data-rule-required="true"
							onchange="redirectToDepartmentWiseBillPayment1()"
							>
							<form:option value="0">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.deparatmentList}" var="department">
								<form:option value="${department.dpDeptcode}">${department.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
