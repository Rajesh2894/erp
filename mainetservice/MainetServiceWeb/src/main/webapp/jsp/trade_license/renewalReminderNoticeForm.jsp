<!-- Start JSP Necessary Tags -->
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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/trade_license/renewalReminderNotice.js"></script>

<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="renewal.reminder.notice.title"
					text="Renewal Notice Printing" />
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="trade.fiels.mandatory.message"
						text="Field with * is mandatory" /></span>
			</div>
			<form:form action="RenewalReminderNotice.html"
				name="RenewalReminderNotice" id="RenewalReminderNotice"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="license.category" text="License Category" /></label>
					<div class="col-sm-4">
						<form:select path="renewalDto.triCod1" id="triCodeList1"
							class="form-control mandClorClass chosen-select-no-results"
							data-rule-required="true" onchange="searchLicenseSubCatagory()">
							<form:option value="0">
								<spring:message code="trd.all" text="All"></spring:message>
							</form:option>
							<c:forEach items="${command.triCodList1}" var="lookup">
								<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>


					<form:hidden path="subCatId" value="${renewalDto.triCod2}"
						id="subCatId" />
					<label class="col-sm-2 control-label required-control"><spring:message
							code="trade.renewal.subCategory" text="License SubCategory" /></label>
					<div class="col-sm-4">
						<form:select path="renewalDto.triCod2" id="triCodeList2"
							class="form-control mandClorClass chosen-select-no-results">
							<form:option value="0">
								<spring:message code="trd.all" text="All"></spring:message>
							</form:option>
							<c:forEach items="${command.triCodList2}" var="lookUP">
								<form:option value="${lookUP.lookUpId}">${lookUP.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="form-group">

					<label class="col-sm-2 control-label required-control"
						for="FromDate"><spring:message code="trade.from.date"
							text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control mandColorClass datepicker"
								id="fromDate" readonly="true" path="renewalDto.treLicfromDate"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>

					<label class="col-sm-2 control-label required-control" for="ToDate"><spring:message
							code="trade.to.date" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control mandColorClass datepicker"
								id="toDate" readonly="true" path="renewalDto.treLictoDate"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success" title="Search"
						onclick="searchData()">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="trade.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='RenewalReminderNotice.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="trade.btn.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="trade.btn.back" text="Back"></spring:message>
					</button>
				</div>


				<c:if test="${command.dataDisplayFlag eq 'Y'}">
					<c:if test="${not empty command.trdMasDtoList}">
						<div class="clearfix padding-10"></div>
						<div class="table-responsive clear">
							<table class="table table-striped table-bordered" id="datatables">
								<thead>
									<tr>
										<th><spring:message code="Sr.No" text="Sr.No" /></th>
										<th><spring:message code="trade.lic.no"
												text="License No." /></th>
										<th><spring:message code="license.details.licenseType"
												text="License Type" /></th>
										<th><spring:message code="trd.business.owner.name"
												text="Business Owner Name" /></th>
										<th><spring:message code="license.category"
												text="License Category" /></th>
										<th><spring:message code="trade.renewal.subCategory"
												text="License Sub Category" /></th>
										<th><spring:message code="trade.license.from.date"
												text="License Start Date" /></th>
										<th><spring:message code="trade.license.end.date"
												text="License End Date" /></th>
										<th><spring:message code="trade.renewal.pending"
												text="Renewal Pending(In Month)" /></th>
										<th><spring:message code="trade.action" text="Action" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.trdMasDtoList}" var="mstDto"
										varStatus="status">
										<tr>
											<td class="text-center">${status.count}</td>
											<td>${mstDto.trdLicno}</td>

											<c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<td>${mstDto.licenseType}</td>
												</c:when>
												<c:otherwise>
													<td>${mstDto.licenceTypeReg}</td>
												</c:otherwise>
											</c:choose>
											<td>${mstDto.trdOwnerNm}</td>

											<c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<td>${mstDto.liceCategory}</td>
												</c:when>
												<c:otherwise>
													<td>${mstDto.licCatgoryReg}</td>
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<td>${mstDto.liceSubCategory}</td>
												</c:when>
												<c:otherwise>
													<td>${mstDto.licSubCatReg}</td>
												</c:otherwise>
											</c:choose>
											<td>${mstDto.licenseFromDate}</td>
											<td>${mstDto.licenseToDate}</td>
											<td align="center">${mstDto.renewalPendingDays}</td>
											<td align="center"><button
													style="height: 30px; width: 70px" type="button"
													class="btn btn-primary hidden-print"
													title="Print Agency License Letter"
													onclick="printRenewalreminderNotice(this,'${mstDto.trdLicno}')">
													<i class="fa fa-print"><spring:message
															code="trade.print" text="Print" /></i>
												</button></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
				</c:if>

			</form:form>
		</div>
	</div>
</div>
