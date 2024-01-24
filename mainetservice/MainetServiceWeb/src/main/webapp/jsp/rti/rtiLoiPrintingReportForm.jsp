
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/rti/rtiLoiPrintingForm.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rti.information.to.applicant"
					text="LOI PRINTING" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="rti.fiels.mandatory.message" text="Fields with * is Mandatory" /></span>
			</div>
			<form:form action="LoiPrintingReport.html" cssClass="form-horizontal"
				id="rtiLoiPrintingForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="rti.information.Loi.no" text="Loi Number" /> </label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results"
							class="form-control mandColorClass" id="loiNo"
							data-rule-required="true">
							<form:option value="">Select</form:option>
							<c:forEach items="${command.loidata}" var="LoiNum">
								<form:option value="${LoiNum.loiNo}">${LoiNum.loiNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveLoiForm(this)">
						<spring:message code="rti.btn.submit" text="Submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm" >
						<spring:message code="rti.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="rti.btn.back" text="Back"></spring:message>
					</a>
				</div>

			</form:form>
		</div>
	</div>
</div>
