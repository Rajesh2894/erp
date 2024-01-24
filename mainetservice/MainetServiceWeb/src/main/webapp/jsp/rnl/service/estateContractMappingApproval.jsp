<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/rnl/service/estateContractMappingApproval.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.estate.cont.map.approval"
					text="Estate Contract Mapping Approval"></spring:message>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="rnl.book.field" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message
						code="master.estate.field.mandatory.message" text="is mandatory"></spring:message>
				</span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form method="post" action="EstateContractMappingApproval.html"
				class="form-horizontal" name="mappingForm" id="mappingForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered" id="datatables">
						<thead>
							<tr>
								<th class="text-center"><spring:message code="rnl.master.contract.no"
										text="Contract No."></spring:message></th>
								<th class="text-center"><spring:message code="rnl.master.contract.date"
										text="Contract Date"></spring:message></th>
								<th class="text-center"><spring:message code="master.complaint.department"
										text="Department"></spring:message></th>
								<th class="text-center"><spring:message code="rnl.master.represented.by"
										text="Represented By"></spring:message></th>
								<th class="text-center"><spring:message code="rnl.master.vender.name"
										text="Vendor Name"></spring:message></th>
								<th class="text-center"><spring:message code="rnl.master.contract.from.date"
										text="Contract From Date"></spring:message></th>
								<th class="text-center"><spring:message code="rnl.master.contract.to.date"
										text="Contract To Date"></spring:message></th>
								<th class="text-center" width="120"><spring:message code="rnl.view.contract"
										text="View Contract"></spring:message></th>
							</tr>
						</thead>
						<tbody id="propertyListId">

							<c:forEach items="${command.contractList}" var="data"
								varStatus="count">
								<tr>
									<td class="text-center">${data.contractNo}</td>
									<td class="text-center">${data.contDate}</td>
									<td class="text-center">${data.deptName}</td>
									<td class="text-center">${data.representedBy}</td>
									<td class="text-center">${data.vendorName}</td>
									<td class="text-center">${data.fromDate}</td>
									<td class="text-center">${data.toDate}</td>
									<td class="text-center"><a href="javascript:void(0);"
										class="btn btn-blue-2 btn-sm"
										onClick="showContract(${data.contId},'V', '${command.showForm}')"><strong
											class="fa fa-eye"></strong><span class="hide">View</span></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#estate"> <spring:message
										code="rnl.master.prop.map" text="Property Mapping"></spring:message></a>
							</h4>
						</div>
						<div id="estate" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

									<label class="control-label col-sm-2 required-control"
										for="estateId"><spring:message
											code='estate.label.name' /></label>
									<div class="col-sm-4">
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<form:input path="estateContMappingDTO.nameEng"
													id="estateId" type="text" class="form-control text-center"
													disabled="true"></form:input>
											</c:when>
											<c:otherwise>
												<form:input path="estateContMappingDTO.nameReg"
													id="estateId" type="text" class="form-control text-center"
													disabled="true"></form:input>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="margin-top-10" id="propTableId">
									<c:set var="d" value="0" scope="page" />
									<table class="table table-bordered table-striped"
										id="customFields2">
										<thead>
											<tr id="ads">
												<th class="text-center" width="200"><spring:message
														code="rl.property.label.name" text="Property Name"></spring:message></th>
												<th class="text-center"><spring:message code="rnl.master.property"
														text="Property No."></spring:message></th>
												<th class="text-center"><spring:message code="rl.property.label.Usage"
														text="Usage"></spring:message></th>
												<th class="text-center"><spring:message code="rnl.master.unit" text="Unit"></spring:message></th>
												<th class="text-center"><spring:message code="rl.property.label.Floor"
														text="Floor"></spring:message></th>
												<th class="text-center"><spring:message code="rl.property.label.totalArea"
														text="Total Area (Sq.Ft)"></spring:message></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><form:input path="estateContMappingDTO.propName"
														id="propName" type="text" class="form-control text-center"
														readonly="true"></form:input></td>
												<td><form:input
														path="estateContMappingDTO.code"
														id="assesmentPropId" type="text" class="form-control text-center"
														readonly="true"></form:input></td>
												<td>
													<form:input path="estateContMappingDTO.usageDesc"
														id="usage" type="text" class="form-control text-center"
														readonly="true"></form:input>
												</td>
												<td><form:input path="estateContMappingDTO.unitNo"
														id="unit" type="text" class="form-control text-center" readonly="true"></form:input></td>
												<td><form:input path="estateContMappingDTO.floorDesc"
														id="floor" type="text" class="form-control text-center"
														readonly="true"></form:input></td>
												<td><form:input path="estateContMappingDTO.totalArea"
														id="totalArea" type="text" class="form-control text-center"
														readonly="true"></form:input></td>
											</tr>
										</tbody>

									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${command.modeType ne 'V'}">
					<div class="panel-group accordion-toggle" id="applicationFormId">
						<div class="panel panel-default">
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<div class="widget-content padding">
											<apptags:CheckerAction  hideForward="true" hideSendback="true"></apptags:CheckerAction>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${command.modeType eq 'V'}">
					<c:if test="${not empty command.attachDocsList }">
					<div class="form-group">
						<label for="text-1"
							class="col-sm-2 control-label "> <spring:message
								code="EChallan.uploadFile" text="Upload File" />
						</label>
						<div class="col-sm-4">
							
								<c:forEach items="${command.attachDocsList}" var="lookUp">
									<div>
										<apptags:filedownload filename="${lookUp.attFname}"
											filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}"
											actionUrl="EstateContractMapping.html?Download" />
									</div>
								</c:forEach>
							
						</div>
					</div>
					</c:if>
				</c:if>
				<div class="form-group text-center margin-top-25">
					<c:if test="${command.modeType ne 'V'}">
						<button type="button" id="save" class="btn btn-success btn-submit"
							onclick="proceedSave(this)">
							<spring:message code="rnl.save.button" text="Save" />
						</button>
					</c:if>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
