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

<script type="text/javascript" src="js/dms/metadataApproval.js"></script>
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
				<strong><spring:message code="" text="Metadata Approval" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>

			<!-- Start Form -->
			<form:form action="MetadataApproval.html" method="POST"
				commandName="command" class="form-horizontal form" name=""
				id="frmMetadata">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "> <spring:message
							code="dms.department" text="Department"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="deptId" id="deptId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true" disabled="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.departmentList}" var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
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

				<div class="widget-header">
					<h2>
						<strong><spring:message code="" text="User Action" /></strong>
					</h2>
				</div>
				<br>

				<div class="form-group">
					<apptags:radio radioLabel="Approve,Reject"
						radioValue="APPROVED,REJECTED" isMandatory="true"
						labelCode="dmsDto.statusApproval" path="dmsDto.statusApproval"
						defaultCheckedValue="APPROVED">
					</apptags:radio>
					<apptags:textArea labelCode="dmsDto.remark" isMandatory="true"
						path="dmsDto.remark" cssClass="hasNumClass form-control"
						maxlegnth="100" />
				</div>
				<div class="text-center">
					<button type="button" value=""
						class="btn btn-green-3" title="Submit"
						onclick="saveDeploymentStaffReqApprovalData(this)">
						<spring:message code="bt.save" />
						<i class="fa padding-left-4" aria-hidden="true"></i>
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>