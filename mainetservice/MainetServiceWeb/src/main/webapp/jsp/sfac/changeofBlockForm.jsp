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
				<spring:message code="sfac.cob.form.title" text="Change of Block Form" />
			</h2>
			<apptags:helpDoc url="ChangeofBlockForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="changeofBlockForm" action="ChangeofBlockForm.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<form:hidden path="showHideFlag" id="showHideFlag" />
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
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1 && org.orgShortNm eq 'IA'}">
										<form:option value="${org.orgid}" code="${org.orgShortNm}">${org.oNlsOrgname}</form:option>
									</c:when>
									<c:otherwise>
									<c:if test="${org.orgShortNm eq 'IA'}">
										<form:option value="${org.orgid}" code="${org.orgShortNm}">${org.oNlsOrgnameMar}</form:option></c:if>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>

					<label for="text-1" class="col-sm-2 control-label required-control"><spring:message
							code="sfac.OrganizationName" text="Orgnization Name" /> </label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.organizationNameId"
							class="form-control chosen-select-no-results"
							disabled="true"
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
				</div>
		
				<h4>
					<spring:message code="sfac.targetDetails" text="Target Details" />
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
									varStatus="targetStatus">
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
									<th width="15%"><spring:message code="sfac.state" text="State" /></th>
									<th  width="15%"><spring:message code="sfac.district" text="District" /></th>
									<th  width="15%"><spring:message code="sfac.block" text="Block" /></th>
									<th  width="15%"><spring:message code="sfac.allocation.category"
											text="Allocation Category" /></th>
									<th><spring:message code="sfac.allocation.subcategory"
											text="Allocation SubCategory" /></th>
									<th  width="15%"><spring:message code="sfac.fpo.cbbo.name" text="CBBO Name" /></th>
									<th width="10%"><spring:message code="sfac.status"
											text="Status" /></th>
								</tr>
							</thead>
							<tbody>
										<c:forEach var="dto"
											items="${command.blockAllocationDto.blockDetailDto}"
											varStatus="Status">
											<tr class="appendableDetails">
												<td align="center"><form:hidden
														path="blockAllocationDto.blockDetailDto[${d}].bdId"
														id="bdId${d}" /> <form:input path=""
														cssClass="form-control mandColorClass" id="sequence${d}"
														value="${d+1}" disabled="true" /></td>

										<td><form:select
												path="blockAllocationDto.blockDetailDto[${d}].stateId"
												class="form-control" id="stateIds${d}"
												onchange="getDistrictList(${d});" disabled="true">
												<form:option value="0">
													<spring:message code="tbOrganisation.select" text="Select" />
												</form:option>
												<c:forEach items="${command.stateList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
											
										<td><form:select path="blockAllocationDto.blockDetailDto[${d}].distId"
												id="distIds${d}" class="form-control mandColorClass"
												onchange="getBlockList(${d});" disabled="true">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${dto.districtList}" var="dist">
													<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select path="blockAllocationDto.blockDetailDto[${d}].blckId"
												id="blckIds${d}" class="form-control mandColorClass" disabled="true">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${dto.blockList}" var="block">
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

											<td><form:input
												path="blockAllocationDto.blockDetailDto[${d}].cbboName"
												cssClass="form-control mandColorClass required-control"
												disabled="true" id="cbboName${d}" /></td>

												<td class="text-center"><form:checkbox
														path="blockAllocationDto.blockDetailDto[${d}].status" id="status${d}" disabled="${command.saveMode eq 'V'}"
														value="" onchange="statusChange(${d})" checked="${dto.status eq 'I'? 'checked' : '' }"/></td>

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
									<th width="13%"><spring:message code="sfac.state" text="State" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
									<th  width="13%"><spring:message code="sfac.district" text="District" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
									<th  width="13%"><spring:message code="sfac.changeOfBlock" text="Block" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
									<th  width="13%"><spring:message code="sfac.allocation.category" text="Allocation Category" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
									<th><spring:message code="sfac.allocation.subcategory" text="Allocation SubCategory" /><span class="showMand"><i
														class="text-red-1">*</i></span></th>
									<th  width="13%"><spring:message code="sfac.fpo.cbbo.name" text="CBBO Name" /></th>
									<th width="12%"><spring:message code="sfac.reason" text="Reason" /><span class="mand"><i
														class="text-red-1">*</i></span></th>
									<%-- <th width="10%"><spring:message code="sfac.upload" text="Upload Document" /></th> --%>
									 <c:if test="${command.saveMode ne 'V'}">
									<th width="15%"><spring:message code="sfac.action"
											text="Action" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${fn:length(command.newBlockAllocationDto.blockDetailDto)>0 }">
										<c:forEach var="dto"
											items="${command.newBlockAllocationDto.blockDetailDto}"
											varStatus="varStatus">
											<tr class="appendableDetails">
												<td align="center"><form:hidden
														path="newBlockAllocationDto.blockDetailDto[${d}].bdId"
														id="bdId${d}" /> <form:input path=""
														cssClass="form-control mandColorClass" id="sNo${d}"
														value="${d+1}" disabled="true" /></td>


											<td><form:select path="newBlockAllocationDto.blockDetailDto[${d}].stateId"
												class="form-control" id="stateId${d}"
												onchange="getDistrictList(${d});"	disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
												<form:option value="0">
													<spring:message code="tbOrganisation.select" text="Select" />
												</form:option>
												<c:forEach items="${command.stateList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:select path="newBlockAllocationDto.blockDetailDto[${d}].distId"
												id="distId${d}" class="form-control mandColorClass"
												onchange="getBlockList(${d});" 	disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${dto.districtList}" var="dist">
													<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

												<td><form:select
														path="newBlockAllocationDto.blockDetailDto[${d}].blckId"
														id="blckId${d}" class="form-control mandColorClass"
															disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
														<form:option value="0">
															<spring:message code='master.selectDropDwn' />
														</form:option>
														<c:forEach items="${dto.blockList}" var="block">
															<form:option value="${block.lookUpId}">${block.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>

												<td><form:select
														path="newBlockAllocationDto.blockDetailDto[${d}].allocationCategory"
														onchange="getAlcSubCatListDet(${d});" class="form-control"
														id="allocationCategoryDet${d}" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
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
												disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
												<form:option value="0">
													<spring:message code="sfac.select" text="Select" />
												</form:option>
												<c:forEach items="${command.allocationSubCatgList}"
													var="lookUp">
													<form:option code="${lookUp.lookUpCode}"
														value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

												<td><%-- <form:select
														path="newBlockAllocationDto.blockDetailDto[${d}].cbboId"
														id="cbboId${d}" cssClass="form-control"
														disabled="true">
														<form:option value="0">
															<spring:message text="Select" code="sfac.select'" />
														</form:option>
														<c:forEach items="${command.cbboMasterList}" var="dto">
															<form:option value="${dto.cbboId}" code="${dto.cbboName}" selected="selected">${dto.cbboName}</form:option>
														</c:forEach>
													</form:select> --%> <form:input
														path="newBlockAllocationDto.blockDetailDto[${d}].cbboName"
														cssClass="form-control mandColorClass required-control"
														readonly="true" id="cbboId${d}" /></td>
													
													<td><form:input
														path="newBlockAllocationDto.blockDetailDto[${d}].reason"
														cssClass="form-control mandColorClass required-control" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }"
														id="reason${d}" maxlength="200" /></td>
														
											<%-- 	<c:if test="${command.saveMode ne 'V'}">
													<td class="text-center"><a title="Add" id="addBtn"
														class="btn btn-blue-2 btn-sm addReButton"><i
															class="fa fa-plus"></i></a> <a href="javascript:void(0);"
														class="btn btn-danger btn-sm delButton" id="deleteBtn"><i
															class="fa fa-minus"></i></span> </a></td>
												</c:if> --%>
	                                         <!-- document data attribute -->
													<%-- <td class="text-center"><c:if
															test="${command.saveMode ne 'V'}">
															<apptags:formField fieldType="7"
																fieldPath="attachments[${d}].uploadedDocumentPath"
																currentCount="${d}" showFileNameHTMLId="true"
																folderName="${d}" fileSize="CARE_COMMON_MAX_SIZE"
																isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
															</apptags:formField>
														</c:if> <c:if
															test="${command.attachDocsList[d] ne null  && not empty command.attachDocsList[d] }">
															<input type="hidden" name="deleteFileId"
																value="${command.attachDocsList[d].attId}">
															<input type="hidden" name="downloadLink"
																value="${command.attachDocsList[d]}">
															<apptags:filedownload
																filename="${command.attachDocsList[d].attFname}"
																filePath="${command.attachDocsList[d].attPath}"
																actionUrl="ChangeofBlockForm.html?Download"></apptags:filedownload>
														</c:if></td>

													<c:if
														test="${command.saveMode eq 'A' || command.saveMode eq 'E'}">
														<td class="text-center">
															<a onclick='fileCountUpload(this);'
																class="btn btn-blue-2 btn-sm addReButton"> <i
																class="fa fa-plus-circle"></i></a>
															<a href='#' id="0_file_${d}"
																class='btn btn-danger btn-sm delButton'
																onclick="doFileDeletion($(this),${d});"><i
																class="fa fa-trash"></i></a></td>
													</c:if> --%>
                                                 <c:if test="${command.saveMode ne 'V'}">
												<td class="text-center"><a title="Add" id="addBtn"
													class="btn btn-blue-2 btn-sm addItemCF"><i
														class="fa fa-plus"></i></a> <a href="javascript:void(0);"
													class="btn btn-danger btn-sm delButton" id="deleteBtn"><i
														class="fa fa-minus"></i></span> </a></td></c:if>

											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="appendableDetails">
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass" id="sNo${d}"
													value="${d+1}" disabled="true" /></td>

											<td><form:select path="newBlockAllocationDto.blockDetailDto[${d}].stateId"
												class="form-control" id="stateId${d}" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }"
												onchange="getDistrictList(${d});" >
												<form:option value="0">
													<spring:message code="tbOrganisation.select" text="Select" />
												</form:option>
												<c:forEach items="${command.stateList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:select path="newBlockAllocationDto.blockDetailDto[${d}].distId"
												id="distId${d}" class="form-control mandColorClass"
												onchange="getBlockList(${d});"  disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${dto.districtList}" var="dist">
													<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select path="newBlockAllocationDto.blockDetailDto[${d}].blckId"
												id="blckId${d}" class="form-control mandColorClass" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${dto.blockList}" var="block">
													<form:option value="${block.lookUpId}">${block.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

											<td><form:select
													path="newBlockAllocationDto.blockDetailDto[${d}].allocationCategory"
													onchange="getAlcSubCatListDet(${d});" class="form-control"
													id="allocationCategoryDet${d}" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
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
													class="form-control" id="allocationSubCategoryDet${d}" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
													<form:option value="0">
														<spring:message code="sfac.select" text="Select" />
													</form:option>
													<c:forEach items="${command.allocationSubCatgList}"
														var="lookUp">
														<form:option code="${lookUp.lookUpCode}"
															value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>


											<td><%-- <form:select
													path="newBlockAllocationDto.blockDetailDto[${d}].cbboId" 
													id="cbboId${d}" cssClass="form-control" disabled="true">
													<form:option value="0">
														<spring:message text="Select" code="sfac.select'" />
													</form:option>
													<c:forEach items="${command.cbboMasterList}" var="dto">
														<form:option value="${dto.cbboId}" code="${dto.cbboName}" >${dto.cbboName}</form:option>
													</c:forEach>
												</form:select> --%>
												<form:input
														path="newBlockAllocationDto.blockDetailDto[${d}].cbboName"
														cssClass="form-control mandColorClass required-control"
														readonly="true" id="cbboId${d}" />
												</td>

											<td><form:input
													path="newBlockAllocationDto.blockDetailDto[${d}].reason" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }"
													cssClass="form-control mandColorClass required-control"
													id="reason${d}" maxlength="200" /></td>
												<%-- <td class="text-center doc"><apptags:formField
														fieldType="7"
														fieldPath="attachments[${d}].uploadedDocumentPath"
														currentCount="${d}" showFileNameHTMLId="true"
														folderName="${d}" fileSize="CARE_COMMON_MAX_SIZE"
														isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CARE_VALIDATION_EXTENSION">
													</apptags:formField></td>

												<c:if
													test="${command.saveMode eq 'A' || command.saveMode eq 'E'}">
													<td class="text-center">
														Defect #157025
														<a onclick='fileCountUpload(this);'
															class="btn btn-blue-2 btn-sm addReButton"> <i
															class="fa fa-plus-circle"></i></a>
														<a href='#' id="0_file_${d}"
														class='btn btn-danger btn-sm delButton'
														onclick="doFileDeletion($(this),${d});"><i
															class="fa fa-trash"></i></a></td>

												</c:if> --%>
                                           <c:if test="${command.saveMode ne 'V'}">
											<td class="text-center"><a title="Add" id="addBtn"
												class="btn btn-blue-2 btn-sm addItemCF"><i
													class="fa fa-plus"></i></a> <a href="javascript:void(0);"
												class="btn btn-danger btn-sm delButton" id="deleteBtn"><i
													class="fa fa-minus"></i></span> </a></td></c:if>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:otherwise>
								</c:choose>

							</tbody>
						</table>
					</div>
				</div>

                <c:if test="${not empty command.attachDocsList}">
				<h4>
					<a data-toggle="collapse" class=""
						data-parent="#accordion_single_collapse1" href="#a5"><spring:message
						code="sfac.upload.doc"	text="Uploaded Document" /></a>
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
				
				
				
				

			


			


				
					<!-- FILE UPLOAD------------------------------------------------------------------------------------------------>
                   
			
					  <%-- <c:if test="${(fn:length(command.attachDocsList)>0 && (command.saveMode eq 'V')) || (command.saveMode eq 'E' || command.saveMode eq 'C')}"> --%>
							<h4>
								<a data-target="#a4" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a4"> <spring:message
										code="document.upload" text="Upload Document" /></a>
							</h4>
						
						<%-- </c:if> --%>
						<div id="a4" class="panel-collapse collapse in">
							<form:hidden path="removeCommonFileById"
								id="removeCommonFileById" />

							<div class="panel-body">

								<div id="doCommonFileAttachment">
									<div class="table-responsive">
										<c:set var="cd" value="0" scope="page" />
										<c:if test="${command.saveMode ne 'V'}">
											<table class="table table-bordered table-striped"
												id="attachCommonDoc">
												<tr>
													<th><spring:message
															code="document.documetnDesc"
															text="Document Description" /></th>
													<th><spring:message code="document.upload"
															text="Upload Document" /></th>
													<th scope="col" width="8%"><spring:message	code="lgl.action" text="Action" /></th>		
												</tr>

												<tr class="appendableUploadClass">

													<td><form:input
															path="commonFileAttachment[${cd}].doc_DESC_ENGL"
															maxlength="100" class=" form-control" /></td>
													<td class="text-center"><apptags:formField
															pageId="CaseEntry" labelCode="caseEntryDTO.formField"
															fieldType="7"
															fieldPath="commonFileAttachment[${cd}].doc_DESC_ENGL"
															currentCount="${cd}" showFileNameHTMLId="true"
															fileSize="CARE_COMMON_MAX_SIZE" isMandatory="false"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CARE_VALIDATION_EXTENSION"
															callbackOtherTask="callbackOtherTask()">
														</apptags:formField>
														<small class="text-blue-2"> <spring:message
															code="sfac.validator.fileUploadNote" text="(Only pdf,doc,docx,xls,xlsx is allowed upto 5 MB)" /></small>
													</td>
													<td class="text-center" width="8%">
													<a onclick='doCommonFileAttachment(this);'
														class="btn btn-blue-2 btn-sm addButton"><i
															class="fa fa-plus-circle"></i></a>
													<a href='#18'
														id="0_file_${cd}" onclick="doFileDelete(this)"
														class='btn btn-danger btn-sm delButton'><i
															class="fa fa-trash"></i></a></td>
												</tr>
												<c:set var="cd" value="${cd+1}" scope="page" />
											</table>
										</c:if>
									</div>
								</div>

							</div>
						</div>
					
              

				
					<div class="text-center padding-top-10">
					<c:if test="${command.saveMode ne 'V' }">
						<button type="button" align="center" class="btn btn-green-3"
							data-toggle="tooltip" data-original-title="Submit"
							onclick="saveAllocationOfBlockForm(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button></c:if>
						
							
					<button type="button" align="center" class="btn btn-danger"
						data-toggle="tooltip" data-original-title="Back"
						onclick="window.location.href ='ChangeofBlockForm.html'">
						<spring:message code="sfac.button.back" text="Back" />
					</button>
					</div>
			

			</form:form>
		</div>

	</div>
</div>

