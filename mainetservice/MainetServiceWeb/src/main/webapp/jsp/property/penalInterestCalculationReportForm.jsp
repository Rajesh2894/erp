<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="js/property/penalInterestCalculationReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="property.Penal.Interest.calculation"
					text="Penal Interest calculation " />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="property.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="penalInterestReport.html"
				cssClass="form-horizontal" id="penalInterestFormReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="property.detail.property.wise" text="Property No." /> </label>
					<div class="col-sm-4">
						<form:input path="searchDto.proertyNo" id="proertyNo"
							class="form-control mandColorClass" maxlength="50"
							onChange="getFlatNo()"></form:input>
					</div>



					<label class="col-sm-2 control-label"><spring:message
							code="prop.report.flatNo" text="Flat No." /></label>
					<div class="col-sm-4">
						<form:select path="flatNo" id="flatNo"
							class="form-control mandColorClass chosen-select-no-results">
							<form:option value="">
								<spring:message code='adh.select' />
							</form:option>
							<c:forEach items="${command.flatNoList}" var="flatNoList">
								<form:option value="${flatNoList}">${flatNoList}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<%-- <div class="form-group" id="showFlatNo">
				
				
				<label class="col-sm-2 control-label"><spring:message
							code="prop.report.flatNo" text="Flat No." /></label>
					<div class="col-sm-4">
						<form:select path="flatNo" id="flatNo"
							class="form-control mandColorClass chosen-select-no-results">
							<form:option value="">
								<spring:message code='adh.select' />
							</form:option>
							<c:forEach items="${command.flatNoList}" var="flatNoList">
								<form:option value="${flatNoList}">${flatNoList}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
			</div> --%>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveReportForm(this)">
						<spring:message code="property.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform">
						<spring:message code="property.btn.reset" />
					</button>


					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="property.btn.back"></spring:message>
					</a>


				</div>

			</form:form>
		</div>
	</div>
</div>




