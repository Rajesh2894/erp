<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript" src="js/dms/viewMetadataDetails.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<style>
table.table tbody tr td {
	font-size: 14px !important;
	vertical-align: middle;
}
</style>
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code=""
						text="View Metadata Details" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>

			<!-- Start Form -->
			<form:form action="ViewMetadataDetails.html" method="POST"
				commandName="command" class="form-horizontal form" name=""
				id="frmMetadata">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="MTD" hasId="true"
						pathPrefix="metadatDto.level" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" isMandatory="false"
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="dms.enterValue" path="metadataValue"
						cssClass="" maxlegnth="250" />
					<%-- <apptags:input labelCode="dms.docRefNo" path="docRefNo"
						cssClass="mandColorClass"></apptags:input> --%>
				</div>

				<c:if
					test="${command.deptCode  ne null && not empty command.deptCode}">

					<c:choose>
						<c:when test="${command.deptCode eq 'ADH'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="ADZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'BND'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="BZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'CFC'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="CWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'HSM'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="SZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'LTD'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="LZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'PYD'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="PZW" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'AS'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'WMS'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="ZWB" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'RL'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="LZB" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'RTI'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="RWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'SWM'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'ML'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="MWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>
						<c:when test="${command.deptCode eq 'WT'}">
							<div class="form-group wardZone">
								<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="ward"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" cssClass="form-control  "
									showAll="false" showData="true" />
							</div>
						</c:when>

						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</c:if>

				<div class="text-center clear padding-10">

					<button type="button" id="search" class="btn btn-blue-2"
						onclick="SearchDetails()" title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="bt.search" text="Search" />
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='ViewMetadataDetails.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="bt.clear" text="Reset"></spring:message>
					</button>

					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back"
						onclick="window.location.href='AdminHome.html'">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="bt.backBtn" text="Back"></spring:message>
					</button>

				</div>

				<c:if test="${command.dmsDocsMetadataDto ne null}">
					<div class="table-responsive">
						<div class="table-responsive margin-top-10">
							<table class="table table-striped table-condensed table-bordered"
								id="frmMetadataDetailTbl">
								<thead>
									<tr>
										<th width="5%"><spring:message code="dms.srNo"
												text="Sr. No." /></th>
										<th width="85%"><spring:message code="dms.metadata"
												text="Metadata Description" /></th>
										<th width="10%"><spring:message code="dms.document"
												text="Document" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.dmsDocsMetadataDto}" var="dto"
										varStatus="loop">
										<tr>
											<td class="text-center">${loop.count}</td>
											<td>
												<table
													class="table table-striped table-condensed table-bordered">
													<c:forEach items="${dto.dmsDocsMetadataDetList}"
														var="detList">

														<tr>
															<td width="40%">${detList.mtKey}</td>
															<%-- <td width="60%">${detList.mtVal}</td> --%>
															<c:choose>
																<c:when
																	test="${detList.mtVal eq null || detList.mtVal eq 'null'}">
																	<td width="40%">-</td>
																</c:when>
																<c:otherwise>
																	<td width="40%">${detList.mtVal}</td>
																</c:otherwise>
															</c:choose>
														</tr>

													</c:forEach>
												</table>
											</td>
											<td class="text-center"><c:if
													test="${dto.dmsDocName ne null && not empty dto.dmsDocName}">
													<c:set var="docNameStr" value="${dto.dmsDocName}"></c:set>
													<c:set var="docIdStr" value="${dto.dmsDocId}"></c:set>
													<c:choose>
														<c:when test="${fn:contains(docNameStr, ',')}">
															<c:set var="docNameSplitStr"
																value="${fn:split(docNameStr, ',')}" />
															<c:set var="docIdSplitStr"
																value="${fn:split(docIdStr, ',')}" />
															<c:forEach items="${docNameSplitStr}" var="docName"
																varStatus="loop">
																<apptags:filedownload filename="${docName}"
																	dmsDocId="${docIdSplitStr[loop.count-1]}"
																	actionUrl="ViewMetadataDetails.html?Download"
																	filePath="" />
																<br />
															</c:forEach>
														</c:when>
														<c:otherwise>
															<apptags:filedownload filename="${docNameStr}"
																dmsDocId="${docIdStr}"
																actionUrl="ViewMetadataDetails.html?Download"
																filePath="" />
														</c:otherwise>
													</c:choose>
												</c:if></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>


			</form:form>
		</div>
	</div>
</div>