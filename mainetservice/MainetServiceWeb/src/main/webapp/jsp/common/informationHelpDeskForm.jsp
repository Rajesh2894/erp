<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
	<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/common/informationHelpDeskForm.js"></script>
<style>
.ser-div {
font-family: "Roboto", sans-serif;
font-size: 14px;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="title" text="Service Information" />
			</h2>
			<apptags:helpDoc url="InformationHelpDesk.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="InformationHelpDesk" action="InformationHelpDesk.html" method="post"
				class="form-horizontal">
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
					<label for="text-1" class="col-sm-2 control-label required-control"><spring:message
							code="dept.name" text="Department" /> </label>
					<div class="col-sm-4">
						<form:select path="informationDeskDto.deptId" class="form-control chosen-select-no-results"
							label="Select" id="deptId" onchange="getServiceList()">
							<form:option value="">
								<spring:message code="cfc.report.select" text="Select" />
							</form:option>
							<c:choose>
								<c:when test="${command.langId eq 1}">
									<c:forEach items="${command.departmentList}" var="dept">
										<form:option value="${dept.dpDeptid}"
											code="${dept.dpDeptcode}">${dept.dpDeptdesc}</form:option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.departmentList}" var="dept">
										<form:option value="${dept.dpDeptid}"
											code="${dept.dpDeptcode}">${dept.dpNameMar}</form:option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label required-control"><spring:message
							code="service.name" text=" Service Name" /></label>
					<div class="col-sm-4">
						<form:select path="informationDeskDto.serviceId" class="form-control mandColorClass chosen-select-no-results"
							id="serviceId" onchange="getCateoryList()">
							<form:option value="">
								<spring:message code="cfc.report.select" text="Select" />
							</form:option>
							<c:choose>
								<c:when test="${command.langId eq 1}">
									<c:forEach items="${command.tbServicesMsts}"
										var="tbServicesMst">
										<form:option value="${tbServicesMst.smServiceId}">${tbServicesMst.smServiceName}</form:option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.tbServicesMsts}"
										var="tbServicesMst">
										<form:option value="${tbServicesMst.smServiceId}" >${tbServicesMst.smServiceNameMar}</form:option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>

                  </div>
                  
                  <div class="form-group" id="chekFlag" style="display: none;">
               
                  <label class="col-sm-2 control-label required-control"><spring:message
					code="info.help.desk.category"  text="Category" /></label>
					<div class="col-sm-4">
						<form:select path="informationDeskDto.categoryId" class="form-control mandColorClass chosen-select-no-results"
							id="categoryId">
							<form:option value="">
								<spring:message code="cfc.report.select" text="Select" />
							</form:option>
							<c:choose>
								<c:when test="${command.langId eq 1}">
									<c:forEach items="${command.categoryList}"
										var="lookUp">
									<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.categoryList}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.descLangSecond}</form:option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>
					
				</div>
				
				
				
				<div class="form-group" id="chekFlagProp" style="display: none;">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="info.help.desk.category.TransferType"  text="Transfer Type"/></label>
					<div class="col-sm-4">
						<form:select path="informationDeskDto.categoryId" class="form-control mandColorClass chosen-select-no-results"
							id="categoryIdProp" >
							<form:option value="">
								<spring:message code="cfc.report.select" text="Select" />
							</form:option>
							<c:choose>
								<c:when test="${command.langId eq 1}">
									<c:forEach items="${command.categoryList}"
										var="lookUp">
									<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.categoryList}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.descLangSecond}</form:option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>
                  </div>
              

					<div class="text-center margin-bottom-20">

					<button type="button" class="btn btn-success" title="Search"
						onclick="getServiceInfo()">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="receipt.Search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="resetSearchForm()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="receipt.Reset" text="Reset"></spring:message>
					</button>
				
					<button type="button" class="btn btn-danger" onclick="back()">
						<spring:message code="receipt.back" text="Back"></spring:message>
					</button>

				</div>

				<div class="ser-div">
					<c:if test="${command.mode eq 'Y'}">
						<h4>
							<spring:message code="service.title"
								text="Service Information - Installment For Fees"></spring:message>
						</h4>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="service.duration" text="Service Duration" /></label>
							<div class="col-sm-4">
								<form:input path="informationDeskDto.smServiceDuration"
									id="smServiceDuration" disabled="true" />
							</div>

							<label class="col-sm-2 control-label"><spring:message
									code="service.duration.unit" text="Duration Unit" /></label>
							<div class="col-sm-4">
								<form:input path="informationDeskDto.smDurationUnit"
									id="smDurationUnit" disabled="true" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="free.service" text="Free Service" /></label>
							<div class="col-sm-4">
								<form:input path="informationDeskDto.chargeApplicable"
									id="chargeApplicable" disabled="true" />
							</div>
						</div>

						<c:if
							test="${command.informationDeskDto.chargeApplicable eq 'No'}">
							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="app.time.charges" text="Application Time Charges" /></label>
								<div class="col-sm-4">
									<form:input path="informationDeskDto.smAppliChargeFlag"
										id="smAppliChargeFlag" disabled="true" />
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="scrutiny.time.charges" text="At Scrutiny Time Charges" /></label>
								<div class="col-sm-4">
									<form:input path="informationDeskDto.smScrutinyChargeFlag"
										id="smScrutinyChargeFlag" disabled="true" />
								</div>
							</div>
						</c:if>
					</c:if>
				</div>

				<c:if test="${command.checkListApplicable eq 'N'}">
				  <div class="text-red-1 margin-bottom-20">
					<p><strong><i><spring:message code="checklist.note"
									text="Checklist Not Applicable" /></i></strong>
					</p>
					</div>
				</c:if>
				<br>

				<c:if test="${command.checkListApplicable eq 'Y'}">
					<h4>
						<spring:message code="document.detail"
							text="Document Upload Details" />
					</h4>
					<div id="receipt-collection-details"
						class="panel-collapse collapse in" style="overflow: visible;">
						<div class="panel-body">
							<table class="table table-bordered table-striped">
								<tbody>
									<tr>
										<th><spring:message code="Sr.No" text="Sr.No" /></th>
										<th><spring:message code="document.group"
												text="Document Group" /></th>
										<th><spring:message code="document.status"
												text="Document Status" /></th>
									</tr>
									<c:forEach items="${command.informationDeskDto.checkList}"
										var="lookUp" varStatus="lk">
										<tr>
											<td class="text-center">${lookUp.documentSerialNo}</td>
											<c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
													<td class="text-center"><label>${lookUp.doc_DESC_ENGL}</label></td>
												</c:when>
												<c:otherwise>
													<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
													<td class="text-center"><label>${lookUp.doc_DESC_Mar}</label></td>
												</c:otherwise>
											</c:choose>
											<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
												<td class="text-center"><spring:message code="doc.mand" /></td>
											</c:if>
											<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
												<td class="text-center"><spring:message code="doc.opt" /></td>
											</c:if>
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
