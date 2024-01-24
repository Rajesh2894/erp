<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/trade_license/dataSuiteEntry.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<style>
	@media screen and (max-width:800px){
		.margin-right-10{
			margin-right: 0px !important;
		}
	}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="license.master.data" text=" License Master Data " />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="dataEntrySuites.html" class="form-horizontal"
				id="dataEntrySuite" name="dataEntrySuite">
              <form:hidden path="printable" id="printable"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">
					<label id="licenseType" class="col-sm-2 control-label"
						for="licenseType"><spring:message
							code="license.details.licenseType" /></label>
					<c:set var="baseLookupCode" value="LIT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="tradeMasterDetailDTO.trdLictype" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select" isMandatory=""
						showOnlyLabel="applicantinfo.label.title" />


				</div>
				<div class="form-group">

					<apptags:input labelCode="license.details.OldLicenseNo" cssClass=""
						isReadonly="" isMandatory=""
						path="tradeMasterDetailDTO.trdOldlicno" maxlegnth="50"></apptags:input>



					<apptags:input labelCode="license.details.newLicenseNo" cssClass=""
						isReadonly="" isMandatory="" path="tradeMasterDetailDTO.trdLicno"
						maxlegnth="50"></apptags:input>

				</div>
				<div class="form-group">

					<apptags:input labelCode="owner.details.name" cssClass=""
						isReadonly="" isMandatory=""
						path="tradeMasterDetailDTO.primaryOwnerName" maxlegnth="50"></apptags:input>



					<apptags:input labelCode="hawkerLicense.details.businessName" cssClass=""
						isReadonly="" isMandatory="" path="tradeMasterDetailDTO.trdBusnm"
						maxlegnth="50"></apptags:input>

				</div>
				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="MWZ" hasId="true"
						showOnlyLabel="false" pathPrefix="tradeMasterDetailDTO.trdWard"
						isMandatory="" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control" showAll="false"
						columnWidth="20%" />
				</div>
				<div class="text-center padding-bottom-10">
					<button class="btn btn-success  search"
						onclick="searchDataEntry();" type="button">
						<i class="button-input"></i>
						<spring:message code="trade.search" text="" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						onclick="window.location.href='dataEntrySuite.html'">
						<i class="button-input"></i>
						<spring:message code="trade.reset" />
					</button>

					<button class="btn btn-blue-2"
						onclick="openDataEntrySuiteForm('C');" type="button">
						<i class="button-input"></i>
						<spring:message code="license.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<table class="table table-bordered table-striped" id="datatables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="license.details.licenseType" text="" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="license.details.OldLicenseNo" text="" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="license.details.newLicenseNo" text="" /></th>
								<th scope="col" width="14%" align="center"><spring:message
										code="license.details.licenseFromPeriod" text="" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="license.details.licenseToPeriod" text="" /></th>
								<th scope="col" width="12%" align="center"><spring:message
										code="license.details.businessName" text="" /></th>
								<th scope="col" width="16%" align="center"><spring:message
										code="license.details.businessAddress" text="" /></th>
								<th scope="col" width="16%" class="text-center"><spring:message
										code="estate.grid.column.action" /></th>
							</tr>
						</thead>
						<%-- <tbody>
							<c:forEach items="${command.masList}" var="mstDto">
								<tr>
									<td>${mstDto.licenseType}</td>
									<td>${mstDto.trdOldlicno}</td>
									<td>${mstDto.trdLicno}</td>
									<td>${mstDto.licenseFromDate}</td>
									<td>${mstDto.licenseToDate}</td>
									<td>${mstDto.trdBusnm}</td>
									<td>${mstDto.trdBusadd}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											title="View"
											onclick="getActionForDefination(${mstDto.trdId},'V');">
											<i class="fa fa-eye"></i>
										</button>
										<button type="button" class="btn btn-warning btn-sm"
											title="Edit"
											onclick="getActionForDefination(${mstDto.trdId},'E');">
											<i class="fa fa-pencil"></i>
										</button>
								</tr>
							</c:forEach>
						</tbody> --%>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>