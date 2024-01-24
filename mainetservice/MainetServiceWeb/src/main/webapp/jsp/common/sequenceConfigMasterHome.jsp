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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/common/sequenceConfigMaster.js"></script>



<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="common.sequenceconfig.header"
					text="Sequence Configuration Master" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="common.sequenceconfig.fieldwith"
						text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
						code="common.sequenceconfig.mandatory" text="is mandatory"></spring:message></span>
			</div>
			<form:form action="SequenceConfigrationMaster.html"
				name="sequenceConfigMaster" id="sequenceConfigMaster"
				class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">

					<label for="text-1" class="col-sm-2 control-label"><spring:message
							code="common.sequenceconfig.deptname" text="Department Name" />
					</label>
					<div class="col-sm-4">
						<!-- This form tag binds the incoming data from the user to the dto using the path attribute -->
						<form:select path="configMasterDTO.deptId"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select" id="deptId">
							<form:option value="" selected="true">Select Department Name</form:option>
							<c:forEach items="${command.departmentList}" var="dept">
								<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpDeptdesc}</form:option>
							</c:forEach>

						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="common.sequenceconfig.Name" text="Sequence Name" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCodeSQN" value="SQN" />
						<form:select path="configMasterDTO.seqName"
							class="form-control mandColorClass chosen-select-no-results"
							id="seqName">
							<form:option value="0" selected="true">Select</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCodeSQN)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpCode}</form:option>
							</c:forEach>
						</form:select>

					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="common.sequenceconfig.seqstatus" /></label>
					<div class="col-sm-4">
						<form:select path="configMasterDTO.seqStatus"
							class="form-control mandColorClass chosen-select-no-results"
							id="status">
							<form:option value="0" selected="true">Select</form:option>
							<form:option value="A">Active</form:option>
							<form:option value="I">Inactive</form:option>
						</form:select>

					</div>

					<c:set var="baseLookupCodeSEC" value="SEC" />
					<label class="col-sm-2 control-label"><spring:message
							code="common.sequenceconfig.category" text="Category" /></label>

					<apptags:lookupField path="configMasterDTO.catId" disabled="false"
						items="${command.getLevelData(baseLookupCodeSEC)}"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="Select Category" isMandatory="true" />

				</div>


				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchForm('SequenceConfigrationMaster.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="common.sequenceconfig.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="ResetSearchForm(this)">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="common.sequenceconfig.reset" text="Reset"></spring:message>
					</button>


					<button type="button" class="btn btn-blue-2" title="Add"
						onclick="addSequenceMaster('SequenceConfigrationMaster.html','addSequenceMaster')">
						<!-- addAdvertiserMaster('AdvertiserMaster.html','addAdvertiserMaster')  -->
						<i class="fa fa-plus padding-right-5" aria-hidden="true"></i>
						<spring:message code="common.sequenceconfig.add" text="Add"></spring:message>
					</button>
				</div>


				<div class="table-responsive" id="export-excel">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="configurationId" class="configurationTable">
							<!-- class="table table-striped table-condensed table-bordered"
							id="sequenceTable" -->
							<thead>
								<tr>
									<th><spring:message code="common.sequenceconfig.name"
											text="Sequence Name" /></th>
									<th><spring:message code="common.sequenceconfig.deptname"
											text="Department Name" /></th>
									<th><spring:message
											code="common.sequenceconfig.sequencetype"
											text="Sequence Type" /></th>
									<th><spring:message code="common.sequenceconfig.category"
											text="Category" /></th>
									<th><spring:message code="common.sequenceconfig.status"
											text="Status" /></th>
									<th><spring:message code="common.sequenceconfig.action"
											text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.configMasterDTOs}" var="data"
									varStatus="index">
									<tr>
										<form:hidden path="" id="seqConfigId"
											value="${data.seqConfigId}" />
										<td>${data.seqtbName}</td>
										<td>${data.deptName}</td>
										<td>${data.seqTypeName}</td>
										<td>${data.catName }</td>
										<td>${data.seqStatus}</td>
										<td class="text-center">

											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Sequence Master"
												onclick="viewSequenceMaster('SequenceConfigrationMaster.html','viewSequenceMaster',${data.seqConfigId})">

												<!-- editadvertiserMaster(${masterList.agencyId},'V') -->
												<i class="fa fa-eye"></i>
											</button> <c:if test="${data.seqStatus eq 'Active'}">
												<button type="button" class="btn btn-warning btn-sm"
													title="Edit Sequence Master"
													onclick="editSequenceMaster('SequenceConfigrationMaster.html','editSequenceMaster',${data.seqConfigId})">
													<!-- editadvertiserMaster(${masterList.agencyId},'E') -->
													<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
												</button>
											</c:if>
										</td>

									</tr>
								</c:forEach>
							</tbody>


						</table>
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>

