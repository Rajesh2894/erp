<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/sfac/changeofBlockForm.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Change of Block Form" />
			</h2>
			<apptags:helpDoc url="ChangeofBlockApproval.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="changeofBlockApprovalForm"
				action="ChangeofBlockApproval.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="Orgnization Type"> <spring:message
							code="sfac.organizationType" text="Orgnization Type" />
					</label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.orgTypeId" id="orgTypeId"
							class="form-control chosen-select-no-results" disabled="true">
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.orgList}" var="org">
								<c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
										<form:option value="${org.orgid}" code="${org.orgShortNm}">${org.oNlsOrgname}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${org.orgid}" code="${org.orgShortNm}">${org.oNlsOrgnameMar}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>

					<label for="text-1" class="col-sm-2 control-label required-control"><spring:message
							code="sfac.OrganizationName" text="Orgnization Name" /> </label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.organizationNameId"
							class="form-control chosen-select-no-results" disabled="true"
							id="organizationNameId">
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.commonMasterDtoList}" var="dto">
								<form:option value="${dto.id}">${dto.name}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="sfac.AllocationYear" text="Allocation Year" /></label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.allocationYearId"
							id="allocationYearId" disabled="true"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select'" />
							</form:option>
							<c:forEach items="${command.faYears}" var="lookUp">
								<form:option value="${lookUp.faYear}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="sfac.appId" text="Application Number" /></label>
					<div class="col-sm-4">
						<form:input path="blockAllocationDto.applicationId"
							id="applicationId" readonly="true"
							cssClass="form-control mandColorClass" />
					</div>
				</div>




				<h4>
					<spring:message code="" text="Target Details" />
				</h4>

				<div class="panel-body showFlag">
					<div class="table-responsive">
						<c:set var="d" value="0" scope="page"></c:set>
						<table id="targetDetails" summary="Block Details"
							class="table table-bordered table-striped">
							<c:set var="d" value="0" scope="page"></c:set>
							<thead>
								<tr>
									<th width="8%"><spring:message code="sfac.srno"
											text="Sr. No." /></th>
									<th><spring:message code="sfac.allocation.category"
											text="Allocation Category" /></th>
									<th><spring:message code="sfac.allocation.subcategory"
											text="Allocation SubCategory" /></th>
									<th><spring:message code="sfac.AllocationTarget"
											text="Allocation Target" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="targetDto"
									items="${command.blockAllocationDto.targetDetDto}"
									varStatus="status">
									<tr class="appendableTargetClass">

										<td align="center"><form:hidden
												path="blockAllocationDto.targetDetDto[${d}].btId"
												id="blockId${d}" /> <form:input path=""
												cssClass="form-control mandColorClass" id="sequence${d}"
												value="${d+1}" disabled="true" /></td>

										<td><c:set var="baseLookupCode" value="ALC" /> <form:select
												path="blockAllocationDto.targetDetDto[${d}].allocationCategory"
												class="form-control" id="allocationCategoryTarget${d}"
												disabled="true">
												<form:option value="0">
													<spring:message code="sfac.select" text="Select" />
												</form:option>
												<c:forEach items="${command.allocationCatgList}"
													var="lookUp">
													<form:option code="${lookUp.lookUpCode}"
														value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select
												path="blockAllocationDto.targetDetDto[${d}].allocationSubCategory"
												class="form-control" id="allocationSubCategoryTarget${d}"
												disabled="true">
												<form:option value="0">
													<spring:message code="sfac.select" text="Select" />
												</form:option>
												<c:forEach items="${command.allcSubCatgTargetList}"
													var="lookUp">
													<form:option code="${lookUp.lookUpCode}"
														value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input
												path="blockAllocationDto.targetDetDto[${d}].allocationTarget"
												cssClass="form-control mandColorClass required-control hasNumber"
												id="allocationTarget${d}" maxlength="200" disabled="true" /></td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>


				<h4>
					<spring:message code="sfac.allocated.BlockDetails"
						text="Existing Block Details" />
				</h4>
				<div class="panel-body showFlag">
					<div class="table-responsive">
						<c:set var="d" value="0" scope="page"></c:set>
						<table id="oldBlockDetails" summary="Block Details"
							class="table table-bordered table-striped">
							<c:set var="d" value="0" scope="page"></c:set>
							<thead>
								<tr>
									<th width="8%"><spring:message code="sfac.srno"
											text="Sr. No." /></th>
									<th width="15%"><spring:message code="sfac.state"
											text="State" /></th>
									<th width="15%"><spring:message code="sfac.district"
											text="District" /></th>
									<th width="15%"><spring:message code="sfac.block"
											text="Block" /></th>
									<th width="15%"><spring:message
											code="sfac.allocation.category" text="Allocation Category" /></th>
									<th width="15%"><spring:message code="sfac.allocation.subcategory"
											text="Allocation SubCategory" /></th>
									<th width="15%"><spring:message code="sfac.fpo.cbbo.name" text="CBBO Name" /></th>
									<th width="10%"><spring:message code="sfac.status" text="Status" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dto"
									items="${command.blockAllocationDto.blockDetailDto}"
									varStatus="status">
									<tr class="appendableDetails">
										<td align="center"><form:hidden
												path="blockAllocationDto.blockDetailDto[${d}].bdId"
												id="bdId${d}" /> <form:input path=""
												cssClass="form-control mandColorClass" id="sequence${d}"
												value="${d+1}" disabled="true" /></td>

										<td><form:select
												path="blockAllocationDto.blockDetailDto[${d}].stateId"
												class="form-control stateId" id="stateIds${d}"
												onchange="getDistrictList(${d});" disabled="true">
												<form:option value="0">
													<spring:message code="tbOrganisation.select" text="Select" />
												</form:option>
												<c:forEach items="${command.stateList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select
												path="blockAllocationDto.blockDetailDto[${d}].distId"
												id="distIds${d}" class="form-control mandColorClass distId"
												onchange="getBlockList(${d});" disabled="true">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.districtList}" var="dist">
													<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select
												path="blockAllocationDto.blockDetailDto[${d}].blckId"
												id="blckIds${d}" class="form-control mandColorClass blckId"
												disabled="true">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.blockList}" var="block">
													<form:option value="${block.lookUpId}">${block.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>


										<td><form:select
												path="blockAllocationDto.blockDetailDto[${d}].allocationCategory"
												onchange="getAlcSubCatList(${d});" class="form-control"
												id="allocationCategoryOld${d}" disabled="true">
												<form:option value="0">
													<spring:message code="sfac.select" text="Select" />
												</form:option>
												<c:forEach items="${command.allocationCatgList}"
													var="lookUp">
													<form:option code="${lookUp.lookUpCode}"
														value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select
												path="blockAllocationDto.blockDetailDto[${d}].allocationSubCategory"
												class="form-control" id="allocationSubCategoryOld${d}"
												disabled="true">
												<form:option value="0">
													<spring:message code="sfac.select" text="Select" />
												</form:option>
												<c:forEach items="${command.allocationSubCatgList}"
													var="lookUp">
													<form:option code="${lookUp.lookUpCode}"
														value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

									<%-- 	<td><form:select
												path="blockAllocationDto.blockDetailDto[${d}].cbboId"
												id="cbboId${d}" cssClass="form-control" disabled="true">
												<form:option value="0">
													<spring:message text="Select" code="sfac.select'" />
												</form:option>
												<c:forEach items="${command.cbboMasterList}" var="dto">
													<form:option value="${dto.cbboId}" code="${dto.cbboName}">${dto.cbboName}</form:option>
												</c:forEach>
											</form:select></td> --%>

										<td><form:input
												path="blockAllocationDto.blockDetailDto[${d}].cbboName"
												cssClass="form-control mandColorClass"
												disabled="true" id="cbboName${d}" /></td>

										<td class="text-center"><form:checkbox disabled="true"
												path="blockAllocationDto.blockDetailDto[${d}].status"
												id="status${d}" value="" checked="${dto.status eq 'I'? 'checked' : '' }"/></td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>


				<h4>
					<spring:message code="sfac.change.BlockDetails"
						text="New Block Details" />
				</h4>
				<div class="panel-body showFlag">
					<div class="table-responsive">
						<c:set var="d" value="0" scope="page"></c:set>
						<table id="blockDetails" summary="Block Details"
							class="table table-bordered table-striped">
							<c:set var="d" value="0" scope="page"></c:set>
							<thead>
								<tr>
									<th width="8%"><spring:message code="sfac.srno"
											text="Sr. No." /></th>
									<th width="13%"><spring:message code="sfac.state"
											text="State" /></th>
									<th width="13%"><spring:message code="sfac.district"
											text="District" /></th>
									<th width="13%"><spring:message code="sfac.changeOfBlock"
											text="Block" /></th>
									<th width="13%"><spring:message
											code="sfac.allocation.category" text="Allocation Category" /></th>
									<th><spring:message code="sfac.allocation.subcategory"
											text="Allocation SubCategory" /></th>
									<th width="13%"><spring:message code="sfac.fpo.cbbo.name"
											text="CBBO Name" /></th>
									<th width="12%"><spring:message code="sfac.reason"
											text="Reason" /></th>
								</tr>
							</thead>
							<tbody>

								<c:forEach var="dto"
									items="${command.newBlockAllocationDto.blockDetailDto}"
									varStatus="status">
									<tr class="appendableDetails">
										<td align="center"><form:hidden
												path="newBlockAllocationDto.blockDetailDto[${d}].bdId"
												id="bdId${d}" /> <form:input path=""
												cssClass="form-control mandColorClass" id="sequence${d}"
												value="${d+1}" disabled="true" /></td>


										<td><form:select
												path="newBlockAllocationDto.blockDetailDto[${d}].stateId"
												class="form-control stateId" id="stateId${d}"
												onchange="getDistrictList(${d});" disabled="true">
												<form:option value="0">
													<spring:message code="tbOrganisation.select" text="Select" />
												</form:option>
												<c:forEach items="${command.stateList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:select
												path="newBlockAllocationDto.blockDetailDto[${d}].distId"
												id="distId${d}" class="form-control mandColorClass distId"
												onchange="getBlockList(${d});" disabled="true">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.districtList}" var="dist">
													<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select
												path="newBlockAllocationDto.blockDetailDto[${d}].blckId"
												id="blckId${d}" class="form-control mandColorClass blckId"
												disabled="true">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.blockList}" var="block">
													<form:option value="${block.lookUpId}">${block.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select
												path="newBlockAllocationDto.blockDetailDto[${d}].allocationCategory"
												onchange="getAlcSubCatListDet(${d});" class="form-control"
												id="allocationCategoryDet${d}" disabled="true">
												<form:option value="0">
													<spring:message code="sfac.select" text="Select" />
												</form:option>
												<c:forEach items="${command.allocationCatgList}"
													var="lookUp">
													<form:option code="${lookUp.lookUpCode}"
														value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select
												path="newBlockAllocationDto.blockDetailDto[${d}].allocationSubCategory"
												class="form-control" id="allocationSubCategoryDet${d}"
												disabled="true">
												<form:option value="0">
													<spring:message code="sfac.select" text="Select" />
												</form:option>
												<c:forEach items="${command.allocationSubCatgList}"
													var="lookUp">
													<form:option code="${lookUp.lookUpCode}"
														value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input
												path="newBlockAllocationDto.blockDetailDto[${d}].cbboName"
												cssClass="form-control mandColorClass required-control"
												disabled="true" id="cbboName${d}" /></td>

										<td><form:input
												path="newBlockAllocationDto.blockDetailDto[${d}].reason"
												cssClass="form-control mandColorClass required-control"
												disabled="true" id="reason${d}" maxlength="200" /></td>

									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>

              <c:if test="${not empty command.attachDocsList}">
				<h4 class="panel-title table" id="">
					<a data-toggle="collapse" class=""
						data-parent="#accordion_single_collapse1" href="#a5"><spring:message
							text="Document Upload Details" /></a>
				</h4>

				<div class="panel-body">
						<fieldset class="fieldRound">
							<div class="overflow">
								<div class="table-responsive">
									<table class="table table-hover table-bordered table-striped">
										<tbody>
											<tr>
												<th><label class="tbold"><spring:message
															code="sfac.srno" text="Sr No" /></label></th>
												<th><label class="tbold"><spring:message
															code="document.name" text="Document Name" /></label></th>
												<th><label class="tbold"><spring:message
															code="doc.download" text="Download" /></label></th>
											</tr>

											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${e+1}</td>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="ChangeofBlockApproval.html?Download" /></td>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</fieldset>
				</div>
				</c:if>


				<div class="form-group">
					<apptags:radio radioLabel="sfac.approve,sfac.reject"
						radioValue="APPROVED,REJECTED" isMandatory="true"
						labelCode="sfac.decision" path="blockAllocationDto.status"
						defaultCheckedValue="APPROVED">
					</apptags:radio>
					<apptags:textArea labelCode="sfac.remark"
						path="blockAllocationDto.authRemark" isMandatory="true"
						cssClass="hasNumClass form-control" maxlegnth="100" />
				</div>


				<div class="text-center padding-top-10">
					<button type="button" align="center" class="btn btn-green-3"
						data-toggle="tooltip" data-original-title="Submit"
						onclick="saveAllocationOfBlockApprovalData(this);">
						<spring:message code="sfac.submit" text="Submit" />
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
				<!-- </div> -->
			</form:form>
		</div>

	</div>
</div>

