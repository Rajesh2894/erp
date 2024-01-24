<!-- Start JSP Necessary Tags -->
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/social_security/applicationForm.js"></script>	
<!-- <script type="text/javascript"
	src="js/works_management/measurementBook.js"></script> -->
<style>
table.mbs-table tbody td {
	text-align: center;
}
</style>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="soc.application.form" text="Application Form" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="SchemeApplicationForm.html" method="POST"
				class="form-horizontal" id="schemeApplicationFormId"
				name="schemeApplicationFormId">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="social.sec.schemename" /></label>

					<div class="col-sm-4">

						<form:select name="applicationformdtoId"
							path="applicationformdto.selectSchemeName" id="schemenameId"
							class="form-control chosen-select-no-results" disabled="false">
							<option value="0"><spring:message text="Select"
									code="soc.select" /></option>
							<c:forEach items="${command.serviceList}" var="objArray">
								<c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
										<form:option value="${objArray[0]}" code="${objArray[2]}"
											label="${objArray[1]}"></form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${objArray[0]}" code="${objArray[2]}"
											label="${objArray[3]}"></form:option>
									</c:otherwise>
								</c:choose>

							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="social.sec.subschemename" text="Sub - Scheme" /></label>

					<div class="col-sm-4">
						<form:select path="applicationformdto.subSchemeName"
							id="subschemeId" class="form-control chosen-select-no-results"
							disabled="false">
							<form:option value="">
								<spring:message text="Select" code="soc.select" />
							</form:option>
							<c:forEach items="${command.subTypeList}" var="slookUp">
								<form:option value="${slookUp.lookUpId}"
									code="${slookUp.lookUpCode}">${slookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">

					<c:set var="baseLookupCode" value="CWZ" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="CWZ" hasId="true"
						pathPrefix="applicationformdto.swdward"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="false"
						isMandatory="false" />

					<apptags:input labelCode="social.sec.adharnumber"
						path="applicationformdto.aadharCard"
						cssClass="hasAadharNo text-left" isMandatory="false" maxlegnth="12"></apptags:input>
                </div>
                
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="work.estimate.status" /></label>
					<div class="col-sm-4">
						<form:select path="applicationformdto.status"
							disabled="${command.saveMode eq 'V'}"
							class="form-control" id="status">
							<c:set var="baseLookupCode" value="SOA" />
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpCode}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>

						</form:select>
					</div>

				</div>

				<!-- <div class="form-group"> -->

				


				<div class="text-center padding-bottom-10">
					<button class="btn btn-blue-2 searchMbDetails" type="button"
						title='<spring:message code="works.management.search" text="Search" />'
						onclick="searchData(this)">
						<i class="fa fa-search padding-right-5"></i>
						<spring:message code="works.management.search" text="Search" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						title='<spring:message code="works.management.reset" text="Reset" />'
						onclick="resetData()">
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button class="btn btn-primary" onclick="addForm()"
						title='<spring:message code="works.management.add" text="Add" />'
						type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="works.management.add" text="Add" />
					</button>
				</div>
				<div class="table-responsive">
					<table class="table table-bordered table-striped mbs-table"
						id="applicationSummaryDataTable">
						<thead>
							<tr>
								<th scope="col" width="5%" align="center"><spring:message
										code="social.srNo" text="Sr.No" />
								<th scope="col" width="22%" class="text-center"><spring:message
										code="social.sec.schemename" text="Scheme Name" /></th>
								<th scope="col" width="22%" class="text-center"><spring:message
										code="social.sec.subschemename" text="Sub-Scheme" /></th>
								<th scope="col" width="22%" class="text-center"><spring:message
										code="social.sec.nameapplicant" text="Name of Applicant" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="" text="Ward" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.status" text="Status" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="works.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.applicantDtoList}" var="mstDto" varStatus="item">
								<tr>
								    <td>${item.count}</td>
									<td>${mstDto.servDesc}</td>
									<td>${mstDto.objOfschme}</td>
									<td>${mstDto.nameofApplicant}</td>
									<td>${mstDto.swdward1}</td>
									<td>${mstDto.statusDesc}</td>
									<td class="text-center">
									<c:if test="${mstDto.status eq 'D'}">
										<button type="button" class="btn btn-warning"
											title="Edit"
											onclick="editData('${mstDto.applicationId}','E')">
											<i class="fa fa-pencil"></i>
										</button>
										</c:if>
										<button type="button" class="btn btn-blue-2"
											title="View"
											onclick="editData('${mstDto.applicationId}','V')">
											<i class="fa fa-eye"></i>
										</button>
										<c:if test="${mstDto.status ne 'D'}">
										 <button type="button" class="btn btn-primary btn-sm"
											title="<spring:message code="" text="Action History"></spring:message>"
											onclick="getActionForWorkFlow('${mstDto.applicationId}');">
											<i class="fa fa-history"></i>
										</button>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						
					</table>
				</div>
			</form:form>
			
		</div>
	</div>
</div>