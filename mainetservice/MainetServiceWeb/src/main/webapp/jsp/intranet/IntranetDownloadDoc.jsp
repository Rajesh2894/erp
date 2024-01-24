<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="intranet.downldDoc" text="Download Intranet Documents"></spring:message></strong>
				<apptags:helpDoc url="UploadIntranetDoc.html"></apptags:helpDoc>
			</h2>
		</div>

		<div class="widget-content padding">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<form:form action="DownloadIntranetDoc.html" id="frmDownloadIntranetDoc" method="POST" commandName="command" class="form-horizontal form">
				<div class="col-sm-12 text-left">
					<div class="table-responsive">
						<table class="table table-bordered table-striped"
							id="attachDocs">
							<tr>
								<th><spring:message code="intranet.srNo" text="Sr. No." /></th>
								<th><spring:message code="intranet.docCat" text="Document Category" /></th>
								<th><spring:message code="intranet.DocName" text="Document Name" /></th>
								<th><spring:message code="intranet.DocDesc" text="Document Description" /></th>
								<th><spring:message code="intranet.dept" text="Department" /></th>
								<th><spring:message code="scheme.view.document" text="" /></th>
							</tr>
							<c:forEach items="${command.fetchIntranetListMas}" var="lookUp" varStatus="item">
								<tr>
									<td align="center">${item.count}</td>
									<td align="center">${lookUp.docCatDesc}</td>
									<td align="center">${lookUp.docName}</td>
									<td align="center">${lookUp.docDesc}</td>
									<td align="center">${lookUp.deptDesc}</td>
									<td align="center">
										<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" actionUrl="DownloadIntranetDoc.html?Download"></apptags:filedownload> 	
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
					</div>
				</div>	
				<div class="text-center">
					<input type="button" onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger hidden-print" value="Back">
				</div>

			</form:form>
		</div>
	</div>
</div>

<!-- ashish test -->


