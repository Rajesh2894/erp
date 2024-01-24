<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/adh/renewalRemainderNotice.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="renewal.remainder.notice.title" text="" />
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>

			<form:form action="RenewalRemainderNotice.html"
				name="RenewalRemainderNotice" id="RenewalRemainderNotice"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="renewal.remainder.notice.advertiser.category"
							text="Advertiser Type" /></label>
					<c:set var="baseLookupCode" value="ADC" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="advertiserType"
						changeHandler="getAdvertiserByAdvertiserType()"
						cssClass="form-control" hasChildLookup="false" hasId="true"
						showAll="false" selectOptionLabelCode="adh.select"
						isMandatory="true" showOnlyLabel="Advertiser Type" />


					<label class="col-sm-2 control-label required-control"><spring:message
							code="renewal.remainder.notice.advertiser.name"
							text="Advertiser Name" /></label>
					<div class="col-sm-4">
						<form:select path="advertiserDto.agencyId" id="agencyId"
							class="form-control mandColorClass chosen-select-no-results"
							disabled="false">
							<form:option value="0">
								<spring:message code='adh.select' />
							</form:option>
							<c:forEach items="${command.advertiserDtoList}" var="advertiser">
								<form:option value="${advertiser.agencyId}">${advertiser.agencyLicNo} - ${advertiser.agencyOwner}</form:option>
							</c:forEach>
						</form:select>
					</div>



				</div>
				<div class="text-center margin-bottom-10">
					<button type="button" class="btn btn-success" title="Search"
						onclick="searchAdvertiser()">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='RenewalRemainderNotice.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="adh.back" text="Back"></spring:message>
					</button>
				</div>


				<c:if test="${command.advertiserDisplayFlag eq 'Y'}">
					<c:if test="${not empty command.advertiserDto}">
						<table class="table table-bordered table-condensed table-striped">
							<tr>

								<th><spring:message
										code="advertiser.master.advertiser.number"
										text="Advertiser Number" /></th>
								<th><spring:message
										code="adh.new.advertisement.advertiser.name"
										text="Advertiser Name" /></th>
								<th><spring:message
										code="renewal.remainder.notice.due.date"
										text="Renewal Due Date" /></th>
								<th>Remarks</th>
								<th><spring:message code="adh.action" text="Action" /></th>
							</tr>
							<tr>
								<c:set value="${command.advertiserDto}" var="masterDto"></c:set>
								<td>${masterDto.agencyLicNo}</td>
								<td>${masterDto.agencyName}</td>
								<td>${masterDto.agencyLicenseToDate}</td>
								<td><form:input
										path="remainderNoticeDto.remarks"
										id="remarks"
										cssClass="required-control form-control" maxlength="200"
										disabled="${command.remainNoticeExistFlag eq 'Y' ? true : false }" /></td>
								<td align="center"><button
										style="height: 30px; width: 70px" type="button"
										class="btn btn-primary hidden-print"
										title="Print Agency License Letter"
										onclick="printRenewalremainderNotice(this)">
										<i class="fa fa-print"> <spring:message code="adh.print"
												text="Print" /></i>
									</button></td>
							</tr>
						</table>
					</c:if>
				</c:if>
			</form:form>
		</div>
	</div>
</div>
