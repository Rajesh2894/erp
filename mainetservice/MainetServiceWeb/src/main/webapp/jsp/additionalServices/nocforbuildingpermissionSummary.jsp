<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<%-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> --%>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/additionalServices/nocforbuildingpermission.js"></script>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="NOC For Building Permission" code="NOCBuildingPermission.nocforbuildingpermissions"/>
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form id="birthRegDraftId"
					action="NOCForBuildingPermission.html" method="POST"
					class="form-horizontal" name="NOCForBuildingPermission">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<%-- <h4>
						<spring:message text="NOC For Building Permission"  code="NOCBuildingPermission.nocforbuildingpermissions" />
					</h4> --%>

					<div class="form-group">
						<apptags:date fieldclass="datepicker"
							labelCode="NOCBuildingPermission.fromDate"
							datePath="nocBuildingPermissionDto.fromDate" isMandatory=""
							cssClass="custDate mandColorClass date">
						</apptags:date>

						<apptags:date fieldclass="datepicker"
							labelCode="NOCBuildingPermission.toDate"
							datePath="nocBuildingPermissionDto.toDate" isMandatory=""
							cssClass="custDate mandColorClass date">
						</apptags:date>
					</div>

					<div class="form-group">

						<apptags:input labelCode="NOCBuildingPermission.applicationNo"
							path="nocBuildingPermissionDto.apmApplicationId"
							isMandatory="false" cssClass="hasNumber form-control"
							maxlegnth="20">
						</apptags:input>

					</div>

					<div class="text-center">
						<button type="button" class="btn btn-blue-3" title="Search"
							onclick="searchData(this)">
							<spring:message code="NOCBuildingPermission.search" text="Search" />
							<!-- <i class="fa padding-left-4" aria-hidden="true"></i> -->
						</button>

						<button type="button" class="btn btn-warning resetSearch"
							onclick="window.location.href = 'NOCForBuildingPermission.html'">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>

						<button type="button" id="add" class="btn btn-green-3"
							onclick="openForm('NOCForBuildingPermission.html','add')"
							title="Add">
							<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
							<spring:message code="NOCBuildingPermission.add" text="Add" />
						</button>
					</div>

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="nocbuildpermissionSummaryDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="NOCBuildingPermission.srno" text="Sr.No" /></th>
									<th width="10%" align="center"><spring:message
											code="NOCBuildingPermission.applicationDate" text="Date" /></th>
									<th width="20%" align="center"><spring:message
											code="NOCBuildingPermission.applicationNo"
											text="Application No." /></th>
									<th width="20%" align="center"><spring:message
											code="NOCBuildingPermission.applicationName"
											text="Applicant Name" /></th>
									<th width="15%" align="center"><spring:message
											code="NOCBuildingPermission.wfStatus" text="Status" /></th>
									<th width="10%" align="center"><spring:message
											code="TbDeathregDTO.form.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${data}" var="birth" varStatus="item">

									<tr>
										<td class="text-center">${item.count}</td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
												value="${birth.date }" /></td>

										<c:choose>
											<c:when test="${empty birth.apmApplicationId}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${birth.apmApplicationId}</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty birth.fName}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${birth.fName} ${birth.mName}
													${birth.lName}</td>
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="${empty birth.wfStatus}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${birth.wfStatus}</td>
											</c:otherwise>
										</c:choose>

										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View CourtDetails"
												onclick="modify('${birth.bpId}','NOCForBuildingPermission.html','edit/View','V')">
												<i class="fa fa-eye"></i>
											</button> <c:forEach items="${command.attachDocsList}" var="lookUp">
												<c:if
													test="${birth.status eq 'A' && (birth.apmApplicationId eq lookUp.idfId)}">
													<button type="button" 
														title="Download">

														<apptags:filedownload filename="${lookUp.attFname}" dmsDocId="${lookUp.dmsDocId}"
															filePath="${lookUp.attPath}"
															actionUrl="NOCForBuildingPermission.html?Download" />

														<!-- <i class="fa fa-print" aria-hidden="true"></i> -->
													</button>
												</c:if>

											</c:forEach>

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
</div>

