<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/asset/searchAsset.js"></script>
<style>
table#searchAssetHome :is(thead tr th:nth-child(1), tbody tr td:nth-child(1)){
	display: none;
}
#searchAssetHome thead tr th{
	width: 0px !important;
}
</style>
<div id="searchAssetPage">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="asset.search.search" />
				</h2>
				<apptags:helpDoc url="AssetSearch.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">

					<form:form id="assetSearch" name="assetSearch"
						class="form-horizontal" action="AssetRegistration.html"
						method="post">

						<div class="compalint-error-div">
							<jsp:include page="/jsp/tiles/validationerror.jsp" />
							<div
								class="warning-div error-div alert alert-danger alert-dismissible"
								id="errorDiv"></div>
						</div>
						<input type="hidden" id="moduleDeptCode"  value="${userSession.moduleDeptCode == 'AST' ? 'AST':'IAST'}">
						<input type="hidden" id="moduleDeptUrl"  value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
						<form:input type="hidden" path="saveMode" value="${command.saveMode}" />
							<c:set var="assetFlag" 	value="${userSession.moduleDeptCode == 'AST' ? true : false}" />	
						<!--add new prefix  -->
						<c:choose>
							<c:when test="${assetFlag}">
								<input type="hidden" id="atype" value="AST" />
							</c:when>
							<c:otherwise>
								<input type="hidden" id="atype" value="IAST" />
							</c:otherwise>
						</c:choose>

						<c:choose>

							<c:when test="${assetFlag}">
<div class="form-group">
							<c:set var="baseLookupCodeACL" value="${userSession.moduleDeptCode == 'AST' ? 'ACL':'ICL'}" />
							<label class="col-sm-2 control-label " for="assetgroup">
								<spring:message code="asset.info.assetclass" />
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeACL)}"
								path="astSearchDTO.assetClass2" disabled="false"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								changeHandler="changeAssetTypeOrClass()"
								selectOptionLabelCode="Select" isMandatory="false" />

							<c:set var="baseLookupCodeASC" value="${userSession.moduleDeptCode == 'AST' ? 'ASC':'ISC'}" />
							<label class="col-sm-2 control-label" for="assetgroup"> <spring:message
									code="asset.info.assetClassification" /></label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeASC)}"
								path="astSearchDTO.assetClass1" disabled="false"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false" />
						</div>



						<!--add new prefix  -->

						<div class="form-group">
							<apptags:input labelCode="asset.search.assetno"
								path="astSearchDTO.astSerialNo" isMandatory="false"
								isDisabled="false"></apptags:input>

							<%-- <apptags:input labelCode="asset.search.identifier"
							path="astSearchDTO.astModelId" isMandatory="false"
							isDisabled="false"></apptags:input> --%>

							<label class="control-label col-sm-2" for=""> <spring:message
									code="asset.classification.department" />
							</label>
							<div class="col-sm-4">
								<form:select path="astSearchDTO.deptId" id="deptId"
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="asset.info.select" />
									</form:option>
									<c:forEach items="${command.departmentsList}" var="obj">
										<form:option value="${obj.dpDeptid}" code="${obj.dpDeptcode}">${obj.dpDeptdesc}</form:option>
									</c:forEach>
								</form:select>
							</div>

						</div>



						<div class="form-group">
							<%-- <apptags:input labelCode="asset.search.subidentifier"
							path="astSearchDTO.subId" isMandatory="false" isDisabled="false"></apptags:input> --%>

							<%-- <label class="col-sm-2 control-label" for="costcenter"> <spring:message
								code="asset.classification.costcenter" /></label>
						<div class="col-sm-4">
							<form:select path="astSearchDTO.costCenter" disabled="false"
								cssClass="form-control chosen-select-no-result" id="costCenterId">
								<form:option value="">
									<spring:message code="asset.info.select"/>
								</form:option>
								<c:forEach items="${command.acHeadCode}" var="costCenterList">
									<form:option value="${costCenterList.lookUpId }"
										code="${costCenterList.lookUpId}">${costCenterList.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div> --%>


							<label class="col-sm-2 control-label" for="assetgroup"> <spring:message
									code="asset.search.location" /></label>


							<div class="col-sm-4">
								<form:select path="astSearchDTO.locationId" id="locationId"
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="asset.info.select" />
									</form:option>
									<%-- <c:forEach items="${command.funcLocDTOList}" var="obj">
										<form:option value="${obj.funcLocationId}"
											code="${obj.funcLocationId}">${obj.funcLocationCode}</form:option>
									</c:forEach> --%>
									<c:choose>
										<c:when
											test="${userSession.getCurrent().getLanguageId() eq 1}">
											<c:forEach items="${command.locList}" var="locationList">
												<form:option value="${locationList.locId }" code="">${locationList.locNameEng }</form:option>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach items="${command.locList}" var="locationList">
												<form:option value="${locationList.locId }" code="">${locationList.locNameReg }</form:option>
											</c:forEach>
										</c:otherwise>
									</c:choose>


								</form:select>
							</div>
							<label class="col-sm-2 control-label" for=""> <spring:message
									code="asset.information.assetstatus" />
							</label>

							<c:set var="baseLookupCode" value="AST" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="astSearchDTO.assetStatusId" disabled="false"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false" />

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for=""> <spring:message
									code="asset.information.assetaquation" />
							</label>

							<c:set var="baseLookupCode" value="${userSession.moduleDeptCode == 'AST' ? 'AQM':'IQM'}" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="astSearchDTO.acquisitionMethodId" disabled="false"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false" />

							<label class="control-label col-sm-2" for=""> <spring:message
									code="asset.information.assetemployeeid" />
							</label>
							<div class="col-sm-4">
								<form:select id="employeeId" path="astSearchDTO.employeeId"
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code='asset.info.select' text="Select" />
									</form:option>
									<c:forEach items="${command.empList}" var="employeeList">
										<form:option value="${employeeList.empId }" code="">${employeeList.empname }</form:option>
									</c:forEach>
								</form:select>
							</div>

						</div>

						<div class="form-group">
							<apptags:input labelCode="asset.search.appNo"
								path="astSearchDTO.astAppNo" isMandatory="false"
								isDisabled="false"></apptags:input>

						</div>
							</c:when>
							<c:otherwise>
                       <div class="form-group">
							<c:set var="baseLookupCodeACL" value="${userSession.moduleDeptCode == 'AST' ? 'ACL':'ICL'}" />
							<label class="col-sm-2 control-label " for="assetgroup">
								<spring:message code="asset.information.hardwareName" />
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeACL)}"
								path="astSearchDTO.assetClass2" disabled="false"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								changeHandler="changeAssetTypeOrClass()"
								selectOptionLabelCode="Select" isMandatory="false" />
								<label class="col-sm-2 control-label" for=""> <spring:message
									code="asset.information.assetstatus" />
							</label>

							<c:set var="baseLookupCode" value="AST" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="astSearchDTO.assetStatusId" disabled="false"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false" />
								
						</div>
						<div class="form-group">
							<apptags:input labelCode="asset.search.assetno"
								path="astSearchDTO.astSerialNo" isMandatory="false"
								isDisabled="false"></apptags:input>
							<label class="col-sm-2 control-label" for=""> <spring:message
									code="asset.information.purchaseMethod" />
							</label>

							<c:set var="baseLookupCode" value="${userSession.moduleDeptCode == 'AST' ? 'AQM':'IQM'}" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="astSearchDTO.acquisitionMethodId" disabled="false"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false" />
						</div>
						<div class="form-group">
						   <apptags:input labelCode="asset.search.appNo"
								path="astSearchDTO.astAppNo" isMandatory="false"
								isDisabled="false"></apptags:input>
								</div>
							</c:otherwise>
						</c:choose>
						
						
						<!--T#85539  -->
						<div class="form-group" style="display: none" id="buildPropId">
							<apptags:input labelCode="asset.search.roadName" maxlegnth="49"
								path="astSearchDTO.roadName" isMandatory="false"
								isDisabled="false"></apptags:input>

							<apptags:input labelCode="asset.search.pincode"
								cssClass='hasPincode' path="astSearchDTO.pincode"
								isMandatory="false" maxlegnth="6"></apptags:input>

						</div>

						<div class="text-center clear padding-10">

							<button class="btn btn-blue-2  search" id="searchAST" title='<spring:message code="asset.search" />'
								type="button">
								<i class="button-input"></i>
								<spring:message code="asset.search" />
							</button>
							<button type="Reset" class="btn btn-warning" onclick="resetAST()" title='<spring:message code="reset.msg" text="Reset" />'>
								<spring:message code="reset.msg" text="Reset" />
							</button>
							<c:if test="${command.saveMode ne 'C'}">
								<button class="btn btn-success add" id="AddAssetRegisration" title='<spring:message code="asset.add" />'
									type="button">
									<i class="button-input"></i>
									<spring:message code="asset.add" />
								</button>
								<button type="button" class="btn btn-primary" title='<spring:message code="asset.search.importexport" />'
									id="importExportAsset" onclick="ExportImport()">
									<spring:message code="asset.search.importexport" />
								</button>
								<button type="button" class="btn btn-primary" id="barcodeId" title='<spring:message code="asset.search.barcode" />'
									onclick="BarcodePage(this)">
									<spring:message code="asset.search.barcode" />
								</button>
							</c:if>


						</div>
						<!-- End button -->

						<div class="table-responsive clear">
							<table class="table table-striped table-bordered"
								id="searchAssetHome">
								<thead>
								<tr>
										<th></th>
										<c:if test="${command.saveMode eq 'C'}">
											<th align="center"><spring:message code="sor.select"
													text="Select" /></th>
										</c:if>
										<th width="22%" align="center"><spring:message
												code="asset.registration.no" text="Registration No" /></th>
								<c:choose>

							<c:when test="${assetFlag}">
                                         <th align="center"><spring:message
												code="asset.search.assetno" text="" /></th>
										<th align="center"><spring:message
												code="asset.search.assetclass" text="" /></th>
										<th align="center"><spring:message
												code="asset.search.assetClassification" text="" /></th>
										<%-- <th align="center" width="15%"><spring:message
												code="asset.search.costcenter" text="" /></th> --%>
										<th align="center"><spring:message
												code="asset.search.location" text="" /></th>
										<th align="center"><spring:message
												code="asset.search.department" text="" /></th>
							</c:when>
							<c:otherwise>
										<th width="12%" align="center"><spring:message
												code="asset.serial.no" text="Serial No" /></th>
										<th width="20%" align="center"><spring:message
												code="asset.Register.Serial.No" text="Register Serial No" /></th>
                                        <th width="13%" align="center"><spring:message
												code="asset.search.assetno" text="" /></th>
										<th width="15%" align="center"><spring:message
												code="asset.information.hardwareName" text="" /></th>
									
							</c:otherwise>
						</c:choose>
									
										
										<th align="center" width="10%"><spring:message
												code="asset.information.assetstatus" text="" /></th>
										<th width="10%" align="center"><spring:message code="asset.insurance.action"
												text="Action" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${summaryDTOList}" var="summaryDTO"
										varStatus="count">
										<tr>
											<td>${summaryDTO.astId}</td>
											<c:if test="${command.saveMode eq 'C'}">
												<td class="text-center"><input type="checkbox"
													id="astSearchDTO.searchCheck${count.index}"
													value="${summaryDTO.astId}" class="selectedRow" /></td>
											</c:if>
											<%-- <td>${summaryDTO.serialNo}</td> --%>
											<td>${summaryDTO.astAppNo}</td>
											
											
											
											<c:choose>

							<c:when test="${assetFlag}">
                                        <td>${summaryDTO.astCode}</td>
											<td>${summaryDTO.assetClass2Desc}</td>
											<td>${summaryDTO.assetClass1Desc}</td>
											<%-- <td>${summaryDTO.costCenterDesc}</td> --%>
											<td>${summaryDTO.location}</td>
											<td>${summaryDTO.deptName}</td>
							</c:when>
							<c:otherwise>
                                       		<td>${summaryDTO.assetModelIdentifier}</td>
											<td>${summaryDTO.serialNo}</td> 
									        <td>${summaryDTO.astCode}</td>
											<td>${summaryDTO.assetClass2Desc}</td>
											
									
							</c:otherwise>
						</c:choose>
											
											<td>${summaryDTO.assetStatusDesc}</td>

											<c:set var="astClassification"
												value="${command.getNonHierarchicalLookUpObject(summaryDTO.assetClass1)}" />
											<c:set var="astClassificationLookup"
												value="${astClassification}" />
											<td class="text-center"><c:if
													test="${summaryDTO.appovalStatus eq 'D'}">
													<button type="button" class="btn btn-danger btn-sm btn-sm"
														onClick="draftAST(${summaryDTO.astId})"
														title="<spring:message code="asset.search.draftast" text="Draft"></spring:message>">
														<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
													</button>
												</c:if>
												<button type="button" class="btn btn-blue-2 btn-sm"
													onClick="viewAST(${summaryDTO.astId})"
													title="<spring:message code="asset.search.viewast"></spring:message>">
													<i class="fa fa-eye"></i>
												</button> <c:if test="${command.saveMode ne 'C'}">

													<button type="button" class="btn btn-success btn-sm"
														onClick="editAST(${summaryDTO.astId})"
														title="<spring:message code="asset.search.editast"/>"
														<c:if test="${empty summaryDTO.appovalStatus or summaryDTO.appovalStatus eq 'P' or summaryDTO.appovalStatus eq 'R' or summaryDTO.assetStatus eq 'DSP' or summaryDTO.appovalStatus eq 'D' }">disabled='disabled'</c:if>>
														<i class="fa fa-pencil"></i>
													</button>


													<c:if test="${userSession.moduleDeptCode == 'AST' }">
													<button type="button" class="btn btn-primary btn-sm"
														onClick="transferAST(${summaryDTO.astId})"
														title="<spring:message code="asset.search.transferast"/>"
														<c:if test="${empty summaryDTO.appovalStatus or summaryDTO.appovalStatus eq 'P' or summaryDTO.appovalStatus eq 'R' or summaryDTO.assetStatus eq 'DSP' or summaryDTO.appovalStatus eq 'D' or astClassificationLookup.lookUpCode eq 'IMO'}">disabled='disabled'</c:if>>
														<i class="fa fa-exchange"></i>
													</button>
													</c:if>
													
													<c:if test="${command.orgname ne 'TSCL' && userSession.moduleDeptCode == 'AST' }">
													<button type="button" class="btn btn-primary btn-sm"
														onClick="retireAST(${summaryDTO.astId})"
														title="<spring:message code="asset.search.retireast"/>"
														<c:if test="${empty summaryDTO.appovalStatus or summaryDTO.appovalStatus eq 'P'  or summaryDTO.appovalStatus eq 'R' or summaryDTO.assetStatus eq 'DSP' or summaryDTO.appovalStatus eq 'D'}">disabled='disabled'</c:if>>
														<i class="fa fa-sort-amount-asc"></i>
													</button>
													</c:if>
													
													<c:if test="${userSession.moduleDeptCode == 'AST' }">
													<button type="button" class="btn btn-primary btn-sm"
														onClick="revaluateAST(${summaryDTO.astId})"
														title="<spring:message code="asset.search.revaluationast"/>"
														<c:if test="${empty summaryDTO.appovalStatus or summaryDTO.appovalStatus eq 'P' or summaryDTO.appovalStatus eq 'R' or summaryDTO.assetStatus eq 'DSP' or summaryDTO.appovalStatus eq 'D'}">disabled='disabled'</c:if>>
														<i class="fa fa-recycle"></i>
													</button>
													</c:if>
													
													<c:if test="${command.orgname ne 'TSCL' && userSession.moduleDeptCode == 'AST' }">
													<button type="button" class="btn btn-primary btn-sm"
														onClick="depreciationAST(${summaryDTO.astId})"
														title="<spring:message code="asset.search.astdepreciationrpt"/>"
														<c:if test="${empty summaryDTO.appovalStatus or summaryDTO.appovalStatus eq 'P' or summaryDTO.appovalStatus eq 'R' or summaryDTO.assetStatus eq 'DSP' or summaryDTO.appovalStatus eq 'D'  or summaryDTO.depriChecked eq 'N'}"></c:if>>
														<i class="fa fa-area-chart"></i>
													</button>
													</c:if>
												</c:if></td>
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
</div>
