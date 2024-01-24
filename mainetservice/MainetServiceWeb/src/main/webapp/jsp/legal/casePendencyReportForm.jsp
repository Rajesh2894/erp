<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="js/legal/casePendencyReport.js"></script>
<script src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="legal.CasePendencyReport"
					text=" Case Pendency Report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="legal.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="CasePendencyReport.html"
				cssClass="form-horizontal" id="CasePendencyFormReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				
				<div class="form-group">
				<%-- 	<label class="col-sm-2 control-label required-control"><spring:message
							code="legal.casePendency.department " text="Department" /> </label>
					<div class="col-sm-4">
						<form:select path="legalReport.cseDeptId"
							cssClass="form-control chosen-select-no-results"
							class="form-control mandColorClass" id="cseDeptId"
							data-rule-required="true">
							<form:option value="0">Select</form:option>
							<c:forEach items="${command.departmentList}" var="department">
								<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
					
					
					<label class="col-sm-2 control-label required-control"><spring:message
							code="legal.casePendency.department" text="Department" /></label>

					<div class="col-sm-4">
						<form:select path="legalReport.cseDeptId"
							id="cseDeptId" class="form-control chosen-select-no-results"
							data-rule-required="true">
							<form:option value="0"><spring:message code="lgl.select"  text="Select"/></form:option>
							<c:forEach items="${command.departmentList}" var="department">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${department.dpDeptid}">${department.dpNameMar}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="legal.casePendency.court.type" text="Court Type" /></label>
					<c:set var="baseLookupCode" value="CTP" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReport.crtType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Court Type" />

				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="legal.detail.case.advocate" text="Advocate Name" /> </label>
					<%-- <div class="col-sm-4">
						<form:select path="" class="chosen-select-no-results"
							id="AdvName" data-rule-required="true">
							<form:option value="0">Select</form:option>
							<c:forEach items="${command.advocateName}" var="Csadvocate">
								<form:option value="${Csadvocate.advId}">${Csadvocate.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
					
					<div class="col-sm-4">
						<form:select path="legalReport.AdvName"
							id="AdvName" class="form-control chosen-select-no-results"
							data-rule-required="true">
							<form:option value="-1"><spring:message code="legal.case.all"  text="All"/></form:option>
							<form:option value="0"><spring:message code="lgl.select"  text="Select"/></form:option>
							<c:forEach items="${command.advocateName}" var="Csadvocate">
								<form:option value="${Csadvocate.advId}">${Csadvocate.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					

					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="legal.casePendency.case.status" text="Case Status" /></label>
					<c:set var="baseLookupCode" value="CSS" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReport.cseStatus" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Case Status" />
				</div>

				<div class="form-group">
					<c:set var="baseLookupCode" value="CCT" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="CCT" hasId="true"
						pathPrefix="legalReport.cseCategory"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="true" />
				</div>
				<div class="form-group">
						<apptags:date labelCode="legal.casePendency.active.from.date"
						datePath="legalReport.csefrmDate" isMandatory="true"
						cssClass="form-control required-control" fieldclass="datepicker"
						></apptags:date>	
					<%-- <label class="col-sm-2 control-label required-control"> <spring:message
							code="legal.casePendency.active.from.date"
							text="Active From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="legalReport.csefrmDate" id="csefrmDate"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="csefrmDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=csefrmDate></label>
						</div>
					</div> --%>
				<%-- 	<label class="col-sm-2 control-label required-control"> <spring:message
							code="legal.casePendency.active.to.date" text="Active To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="legalReport.csetoDate" id="csetoDate"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="csetoDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=csetoDate></label>
						</div>
					</div> --%>
					<apptags:date labelCode="legal.casePendency.active.to.date"
						datePath="legalReport.csetoDate" isMandatory="true"
						cssClass="form-control required-control" fieldclass="datepicker"
						></apptags:date>
					
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="legal.casePendency.case.type" text="Case Type" /></label>
					<c:set var="baseLookupCode" value="TOC" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="legalReport.cseType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Case Type" />
				</div>
				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveForm(this)">
						<spring:message code="legal.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm"
					 onclick="window.location.href='CasePendencyReport.html'">
					
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








