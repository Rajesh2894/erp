<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script
	src="js/legal/detailCasePendencyReport.js"></script>
<script src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="legal.detail.case.Pendency.Report"
					text="Detail Case Pendency Report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="legal.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="DetailCaseReport.html" cssClass="form-horizontal"
				id="DetailCaseFormReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

			
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="legal.detail.case.Case.Suit.No" text="Case Number" /> </label>
					<div class="col-sm-4">
						<form:select path="caseEntryDto.cseSuitNo"
							cssClass="form-control chosen-select-no-results"
							class="form-control mandColorClass" id="suitNo"  showAll="true"
							data-rule-required="true"  >
							<form:option value="0"><spring:message code="lgl.select"  text="Select"/></form:option>
							<form:option value="-1"><spring:message code="legal.case.all"  text="All"/></form:option>
							<c:forEach items="${command.caseEntryListDto}" var="caseEntry">
								<form:option value="${caseEntry.cseSuitNo}">${caseEntry.cseSuitNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="legal.detail.case.Division.Wise" text="Division Wise" /></label>
					<c:set var="baseLookupCode" value="DVN" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReport.cseDivWise" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Division Wise" />

				</div>


				<div class="form-group">

					<%-- <label class="col-sm-2 control-label required-control"> <spring:message
							code="legal.detail.Case.court.type" text="Court Type" /></label>
					<div class="col-sm-4">
						<form:select path="courtMasterDTO.id"
							cssClass="form-control chosen-select-no-results"
							class=" form-control mandColorClass" id="crtId"
							data-rule-required="true">
							<form:option value="0">Select</form:option>
							<c:forEach items="${command.courtMasterDTOList}"
								var="courtMasterDTO">
								<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtName}</form:option>
							</c:forEach>
						</form:select>
					</div>
 --%>
 
 
 
 	                <label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="legal.detail.Case.court.type" text="Court Type" /></label>
					<c:set var="baseLookupCode" value="CTP" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReport.crtType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Court Type" />
 
 
 
 
					<label class="col-sm-2 control-label required-control"><spring:message
							code="legal.detail.case.advocate" text="Advocate Name" /> </label>
					<div class="col-sm-4">
						<form:select path="" class="form-control mandColorClass"
							cssClass="form-control chosen-select-no-results" id="AdvName"
							data-rule-required="true">
							<form:option value="0"><spring:message code="lgl.select"  text="Select"/></form:option>
							<form:option value="-1"><spring:message code="legal.case.all"  text="All"/></form:option>
							<c:forEach items="${command.advocateName}" var="Csadvocate">
								<form:option value="${Csadvocate.advId}">${Csadvocate.fullName}</form:option>
							</c:forEach>
							
						</form:select>
					</div>

				</div>



				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveForm(this)">
						<spring:message code="legal.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="" onclick="window.location.href='DetailCaseReport.html'">
						<spring:message code="legal.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="legal.btn.back"></spring:message>
					</a>
				</div>

			</form:form>
		</div>
	</div>
</div>