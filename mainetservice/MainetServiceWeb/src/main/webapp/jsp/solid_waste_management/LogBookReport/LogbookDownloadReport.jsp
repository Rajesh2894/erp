<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="js/solid_waste_management/allLogBookReport/LogBookFileDownload.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="LogBook FileDownload" />
			</h2>
			<apptags:helpDoc url="filedownload.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="filedownload.html" method="get"
				id="logBookFileDownload"
				class="form-horizontal ng-pristine ng-valid">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label for="select-1478760963433" class="col-sm-2 control-label"><spring:message
							code="" text="LogBook Report Type" /><span
						class="required-control"></span> </label>
					<c:set var="baseLookupCode" value="LBR" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="attachDocs.idfId" cssClass="form-control required-control"
						isMandatory="true" selectOptionLabelCode="selectdropdown"
						hasId="true" />
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchExceluploadedFile('filedownload.html','searchExcelFile');">
						<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search"></spring:message>
					</button>
					<button type="Reset" class="btn btn-warning"
						onclick="resetFileDownload()">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
				<c:if test="${not empty command.listAttachDocs}">
					<div class="table-responsive clear">
						<table summary="Dumping Ground Data"
							class="table table-bordered table-striped LogId">
							<thead>
								<tr>
									<th><spring:message code="population.master.srno"
											text="Sr.No."></spring:message></th>
									<th><spring:message code="" text="Date"></spring:message></th>
									<th><spring:message code="" text="File Name"></spring:message></th>
									<th><spring:message code="" text="Action"></spring:message></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.listAttachDocs}" var="data"
									varStatus="index">
									<tr>
										<td class="text-center" width="10%">${index.count}</td>
										<td width="39%" class="text-center"><fmt:formatDate
												pattern="dd/MM/yyyy" value="${data.attDate}" /></td>
										<td width="39%" class="text-center">${data.attFname}</td>

										<c:set var="path" value="${data.attPath}" />
										<c:set var="search" value="\\" />
										<c:set var="replace" value="\\\\" />
										<c:set var="filepath"
											value="${fn:replace(path,search,replace)}" />
										<td style="width: 40%" class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="downloadFile('${filepath}${replace}${data.attFname}','filedownload.html?Download')"
												data-original-title="View" title="Download">
												<strong class="fa fa-download"></strong><span class="hide"><spring:message
														code="" text="Download"></spring:message></span>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</form:form>
		</div>
	</div>
</div>