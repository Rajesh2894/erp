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
<script type="text/javascript" src="js/sfac/allocationOfBlocksForm.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.allocationOfBlock.add.form"
					text="Allocation Of Blocks Form" />
			</h2>
			<apptags:helpDoc url="AllocationOfBlocks.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="allocationOfBlocksAddForm"
				action="AllocationOfBlocks.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					<form:hidden path="orgShortNm" id="orgShortNm" value="${userSession.getCurrent().getOrganisation().getOrgShortNm()}"/>
					<form:hidden path="saveMode" id="saveMode" value="${command.saveMode}"/>
					
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="Orgnization Type"> <spring:message
							code="sfac.organizationType" text="Orgnization Type" />
					</label>
					<%-- <c:set var="baseLookupCode" value="OTY" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}" path="blockAllocationDto.orgTypeId"
						cssClass="form-control chosen-select-no-results" changeHandler="showTable();"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="sfac.select" isMandatory="true" /> --%>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.orgTypeId" id="orgTypeId" onchange="getOrganizationName();" onload="getOrganizationName();"
							class="form-control chosen-select-no-results" disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }">
							<form:option value="0">
								<spring:message code="sfac.select" text="Select" />
							</form:option>
							<c:forEach items="${command.orgList}" var="org">
								<c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1 && org.orgShortNm eq 'IA'}">
										<form:option value="${org.orgid}" code="${org.orgShortNm}" selected="selected">${org.oNlsOrgname}</form:option>
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
							class="form-control chosen-select-no-results" disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }"
							id="organizationNameId">
							<form:option value="0">
								<spring:message code="sfac.select" text="Select" />
							</form:option>
							<c:forEach items="${command.commonMasterDtoList}" var="dto">
								<form:option value="${dto.id}">${dto.name}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="sfac.AllocationYear" text="Allocation Year" /></label>
					<div class="col-sm-4">
						<form:select path="blockAllocationDto.allocationYearId" onchange="changeMAxAndMinDate();"
							id="allocationYearId" disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }"
							cssClass="form-control chosen-select-no-results">
							<form:option value="0">
								<spring:message text="Select" code="sfac.select" />
							</form:option>
							<c:forEach items="${command.faYears}" var="lookUp">
								<form:option value="${lookUp.faYear}" code="${lookUp.faYearFromTo}">${lookUp.faYearFromTo}</form:option>
							</c:forEach>
						</form:select>
					</div>


					<%-- <label class="col-sm-2 control-label required-control target"><spring:message
							code="sfac.AllocationTarget" text="Allocation Target" /></label>
					<div class="col-sm-4 target">
						<form:input path="blockAllocationDto.allocationTarget"
							class="form-control hasNumber target" id="allocationTarget" />
					</div> --%>
				</div>

				<h4>
					<spring:message code="sfac.target.det" text="Target Details" />
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
								   <th><spring:message code="sfac.targetDate"
											text="Target Date" /></th>
									<c:if test="${command.saveMode ne 'V'}">
									<th width="10%"><spring:message code="sfac.action"
											text="Action" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${fn:length(command.blockAllocationDto.targetDetDto)>0 }">
										<c:forEach var="targetDto"
											items="${command.blockAllocationDto.targetDetDto}"
											varStatus="status">
											<tr class="appendableTargetClass">

												<td align="center"><form:hidden
														path="blockAllocationDto.targetDetDto[${d}].btId"
														id="blockId${d}" /> <form:input path=""
														cssClass="form-control mandColorClass" id="sequence${d}"
														value="${d+1}" disabled="true" /></td>


												<td><form:select
														path="blockAllocationDto.targetDetDto[${d}].allocationCategory" onchange="getAlcSubCatList(${d});"
														class="form-control" id="allocationCategoryTarget${d}" disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }">
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
														class="form-control" id="alcTargetSubCategory${d}" disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }">
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
														id="allocationTarget${d}" maxlength="200" disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }"/></td>

												<td align="center"><div class="input-group">
														<form:input
															path="blockAllocationDto.targetDetDto[${d}].targetDate"
															cssClass="form-control datepicker datepic text-center"
															maxlength="10" id="targetDate${d}" readonly="true"
															disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }" />
														<label class="input-group-addon"><i
															class="fa fa-calendar"></i><span class="hide"> <spring:message
																	code="" text="icon" /></span><input type="hidden"></label>
													</div></td>
												<c:if test="${command.saveMode ne 'V'}">
												<td class="text-center"><a title="Add" id="addBtn"
													class="btn btn-blue-2 btn-sm addTareget"><i
														class="fa fa-plus"></i></a> <a href="javascript:void(0);"
													class="btn btn-danger btn-sm delTarget" id="deleteBtn"><i
														class="fa fa-minus"></i></span> </a></td></c:if>
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="appendableTargetClass">
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass" id="sequence${d}"
													value="${d+1}" disabled="true" /></td>

											<td><form:select
													path="blockAllocationDto.targetDetDto[${d}].allocationCategory"
													onchange="getAlcSubCatList(${d});" class="form-control"
													id="allocationCategoryTarget${d}"
													disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }">
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
													class="form-control" id="alcTargetSubCategory${d}"
													disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }">
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
													id="allocationTarget${d}" maxlength="200" disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }"/></td>

											<td align="center"><div class="input-group">
													<form:input
														path="blockAllocationDto.targetDetDto[${d}].targetDate"
														cssClass="form-control datepicker datepic text-center"
														maxlength="10" id="targetDate${d}" readonly="true"
														disabled="${command.showEdit eq 'Y' || command.saveMode eq 'V' ? true : false }" />
													<label class="input-group-addon"><i
														class="fa fa-calendar"></i><span class="hide"> <spring:message
																code="" text="icon" /></span><input type="hidden"></label>
												</div></td>

											<c:if test="${command.saveMode ne 'V'}">
											<td class="text-center"><a title="Add" id="addBtn"
												class="btn btn-blue-2 btn-sm addTareget"><i
													class="fa fa-plus"></i></a> <a href="javascript:void(0);"
												class="btn btn-danger btn-sm delTarget" id="deleteBtn"><i
													class="fa fa-minus"></i></span> </a></td></c:if>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:otherwise>
								</c:choose>

							</tbody>
						</table>
					</div>
				</div>
                
				<h4>
					<spring:message code="sfac.BlockDetails" text="Block Details" />
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
										
									<th width="15%"><spring:message code="sfac.state" text="State" /></th>
									<th  width="15%"><spring:message code="sfac.district" text="District" /></th>
									<th  width="15%"><spring:message code="sfac.block" text="Block" /></th>
									<th  width="15%"><spring:message code="sfac.allocation.category"
											text="Allocation Category" /></th>
                                     <th><spring:message code="sfac.allocation.subcategory"
											text="Allocation SubCategory" /></th>
									<th  width="15%"><spring:message code="sfac.fpo.cbbo.name" text="CBBO Name" /></th>
                                    <c:if test="${command.saveMode ne 'V'}">
									<th width="10%"><spring:message code="sfac.action"
											text="Action" /></th></c:if>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${fn:length(command.blockAllocationDto.blockDetailDto)>0 }">
										<c:forEach var="dto"
											items="${command.blockAllocationDto.blockDetailDto}"
											varStatus="status">
											<tr class="appendableDetails">
												<td align="center"><form:hidden
														path="blockAllocationDto.blockDetailDto[${d}].bdId"
														id="bdId${d}" /> <form:input path=""
														cssClass="form-control mandColorClass" id="seqNo${d}"
														value="${d+1}" readonly="true"/></td>


											<td><form:select path="blockAllocationDto.blockDetailDto[${d}].stateId"
												class="form-control stateId" id="stateId${d}"
												onchange="getDistrictList(${d});" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
												<form:option value="0">
													<spring:message code="tbOrganisation.select" text="Select" />
												</form:option>
												<c:forEach items="${command.stateList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:select path="blockAllocationDto.blockDetailDto[${d}].distId"
												id="distId${d}" class="form-control mandColorClass distId"
												onchange="getBlockList(${d});" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${dto.districtList}" var="dist">
													<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select path="blockAllocationDto.blockDetailDto[${d}].blckId" 
												id="blckId${d}" class="form-control mandColorClass blckId" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${dto.blockList}" var="block">
													<form:option value="${block.lookUpId}">${block.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

												<td><form:select
														path="blockAllocationDto.blockDetailDto[${d}].allocationCategory" onchange="getAlcSubCatListDet(${d});"
														class="form-control" id="allocationCategoryDet${d}" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
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
														class="form-control" id="allocationSubCategoryDet${d}"
														disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
														<form:option value="0">
															<spring:message code="sfac.select" text="Select" />
														</form:option>
														<c:forEach items="${command.allocationSubCatgList}"
															var="lookUp">
															<form:option code="${lookUp.lookUpCode}"
																value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>

												<td><form:select
														path="blockAllocationDto.blockDetailDto[${d}].cbboId"
														id="cbboId${d}" cssClass="form-control" 
														disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA'? true : false }">
														<form:option value="0">
															<spring:message text="Select" code="sfac.select" />
														</form:option>
														<c:forEach items="${command.cbboMasterList}" var="dto">
															<form:option value="${dto.cbId}" code="${dto.cbboName}">${dto.cbboName}</form:option>
														</c:forEach>
													</form:select></td>
                                               <c:if test="${command.saveMode ne 'V'}">
												<td class="text-center"><a title="Add" id="addBtn"
													class="btn btn-blue-2 btn-sm addBlockDet"><i
														class="fa fa-plus"></i></a> <a href="javascript:void(0);"
													class="btn btn-danger btn-sm delBlockDet" id="deleteBtn"><i
														class="fa fa-minus"></i></span> </a></td></c:if>

											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="appendableDetails">
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass" id="seqNo${d}"
													value="${d+1}" readonly="true" /></td>

												<td><form:select path="blockAllocationDto.blockDetailDto[${d}].stateId"
												class="form-control stateId" id="stateId${d}"
												onchange="getDistrictList(${d});" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
												<form:option value="0">
													<spring:message code="tbOrganisation.select" text="Select" />
												</form:option>
												<c:forEach items="${command.stateList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><form:select path="blockAllocationDto.blockDetailDto[${d}].distId"
												id="distId${d}" class="form-control mandColorClass distId"
												onchange="getBlockList(${d});" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${dto.districtList}" var="dist">
													<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select path="blockAllocationDto.blockDetailDto[${d}].blckId"
												id="blckId${d}" class="form-control mandColorClass blckId" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
												<form:option value="0">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${dto.blockList}" var="block">
													<form:option value="${block.lookUpId}">${block.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>


											<td><form:select
													path="blockAllocationDto.blockDetailDto[${d}].allocationCategory"
													onchange="getAlcSubCatListDet(${d});" class="form-control"
													id="allocationCategoryDet${d}"
													disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
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
													class="form-control" id="allocationSubCategoryDet${d}"
													disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
													<form:option value="0">
														<spring:message code="sfac.select" text="Select" />
													</form:option>
													<c:forEach items="${command.allocationSubCatgList}"
														var="lookUp">
														<form:option code="${lookUp.lookUpCode}"
															value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>


											<td><form:select
													path="blockAllocationDto.blockDetailDto[${d}].cbboId" 
													id="cbboId${d}" cssClass="form-control" disabled="${command.saveMode eq 'V' || userSession.getCurrent().getOrganisation().getOrgShortNm() eq 'NPMA' ? true : false }">
													<form:option value="0">
														<spring:message text="Select" code="sfac.select" />
													</form:option>
													<c:forEach items="${command.cbboMasterList}" var="dto">
														<form:option value="${dto.cbId}" code="${dto.cbboName}">${dto.cbboName}</form:option>
													</c:forEach>
												</form:select></td>

                                            <c:if test="${command.saveMode ne 'V'}">
											<td class="text-center"><a title="Add" id="addBtn"
												class="btn btn-blue-2 btn-sm addBlockDet"><i
													class="fa fa-plus"></i></a> <a href="javascript:void(0);"
												class="btn btn-danger btn-sm delBlockDet" id="deleteBtn"><i
													class="fa fa-minus"></i></span> </a></td></c:if>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:otherwise>
								</c:choose>

							</tbody>
						</table>
					</div>
				</div>
				



				<div class="text-center padding-top-10">
			      <c:if test="${command.saveMode ne 'V'}">
					<button type="button" align="center" class="btn btn-green-3"
						data-toggle="tooltip" data-original-title="Submit"
						onclick="saveAllocationOfBlockForm(this);">
						<spring:message code="sfac.submit" text="Submit" />
					</button>
					<button type="button" class="btn btn-warning btn-yellow-2"
						title="Reset" onclick="ResetForm()">
						<spring:message code="sfac.button.reset" text="Reset" />
					</button>
					</c:if>
					
					<button type="button" align="center" class="btn btn-danger"
						data-toggle="tooltip" data-original-title="Back"
						onclick="window.location.href ='AllocationOfBlocks.html'">
						<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>


			</form:form>
		</div>
	</div>
</div>

