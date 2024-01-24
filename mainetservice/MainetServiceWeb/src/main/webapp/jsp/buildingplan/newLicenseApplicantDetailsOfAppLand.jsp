<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/buildingplan/newTCPLicenseForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>	

<div class="pagediv">
	<div class="content animated top">
		<div class="widget">
			<div class="widget-content padding">
				<form:form id="applicantLandDetForm"
					action="NewTCPLicenseForm.html" method="post"
					class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<c:set var="search" value="\\" />
					<c:set var="replace" value="\\\\" />
					<div class="form-group">
						<c:if test="${not empty command.DGPSCheckList}">
							<c:set var="dg" value="800" scope="page" />
							<c:forEach items="${command.DGPSCheckList}"
								var="dgLookUp" varStatus="lk">
								<label class="col-sm-2 control-label required-control" for=""><spring:message
										code="" text="${dgLookUp.doc_DESC_ENGL}" /></label>
								
								<c:choose>
										<c:when test="${command.saveMode ne 'V' }">
											<div id="DGPSDocs_${lk}" class="col-sm-4">
									<apptags:formField fieldType="7" labelCode="" hasId="true"
										fieldPath="DGPSCheckList[${lk.index}]"
										isMandatory="false" showFileNameHTMLId="true"
										fileSize="TCPHR_MAX_FILE_SIZE"
										maxFileCount="CHECK_LIST_MAX_COUNT"
										validnFunction="EXCEL_UPLOAD_VALIDATION_EXTENSION_KML"
										currentCount="${dg}" folderName="DGPSCheckList${dg}" />
										
										<span><i style="font-size: 10px; font-weight: bold;"
											class="text-red-1"><spring:message code="file.upload.msg.kml"
											text="(UploadFile upto 100MB and Only .KML)" /></i></span>
								</div>
										</c:when>
										<c:otherwise>
											<c:if test="${not empty command.DGPSDocumentList[0]}">
												<c:set var="filePath"
													value="${command.DGPSDocumentList[0].attPath}" />
												<c:set var="path"
													value="${fn:replace(filePath,search,replace)}" />
												<c:if test="${not empty path}">
													<button type="button" class="button-input btn btn-blue-2"
														name="button" value="VIEW"
														onclick="downloadFileInTag('${path}${replace}${command.DGPSDocumentList[0].attFname}','NewTCPLicenseForm.html?Download','','')">
														<spring:message code="" text="VIEW" />
													</button>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
								<c:set var="dg" value="${dg + 1}" scope="page" />
							</c:forEach>
						</c:if>
					</div>
					
					<h4>
						<spring:message
								code="" text="Bifurcation Of Component of type of Licence" />
					</h4>
					
					<div class="form-group">
						<label class="col-sm-2 control-label " ><spring:message
								code="" text="Total Applied Area (in acres)" /></label>
						<div class="col-sm-4 ">
							<input name="" type="text"
								class="form-control alphaNumeric preventSpace required-control"
								path="" id="licFee"
								maxlength="20"
								value="${command.licenseApplicationMasterDTO.ciTotArea}"
								disabled="true" />
							
						</div>
					</div>
					
					
					<spring:eval
							expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${command.licenseApplicationMasterDTO.appPLicPurposeId},${userSession.getCurrent().getOrganisation().getOrgid()})"
							var="purposeType" />
					
					<div class="form-group">
						<label class="col-sm-2 control-label sub-title" ><spring:message
								code="" text="Purpose type" /></label>
						<div class="col-sm-4 ">
							<input name="" type="text"
								class="form-control alphaNumeric preventSpace required-control"
								path="" id="purpsoeDesc"
								maxlength="20"
								value="${purposeType.lookUpDesc}"
								disabled="true" />
							
						</div>
					</div>
					
					
					
						
						
					<table
						class="table table-bordered  table-condensed margin-bottom-10"
						id="itemDetails">
						<c:set var="d" value="0" scope="page" />
						<thead>

							<tr>
								<th width=""><spring:message code="" text="Component" /><i
									class="text-red-1">*</i></th>
								<th width=""><spring:message code="" text="Sub Component" /><i
									class="text-red-1">*</i></th>
								<th width=""><spring:message code="" text="FAR" /><i
									class="text-red-1">*</i></th>
								<th width=""><spring:message code="" text="Area(in acres)" /><i
									class="text-red-1">*</i></th>
								<c:if test="${command.saveMode ne 'V' }">
								<th width=""><spring:message code="" text="Action" /></th>
								</c:if>

							</tr>
						</thead>
						<c:choose>
							<c:when
								test="${empty command.licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList}">
								<tbody>


									<tr class="itemDetailClass">



										<form:hidden
											path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].purposeId"
											id="purposeId${d}" />

										<td><form:select
												path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].applicationPurpose2"
												cssClass="form-control mandColorClass" id="subpurpose1${d}"
												onchange="getSubpurpose2(${d})">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.suPurposeList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td class="subPurpose2${d}"><form:select
												path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].applicationPurpose3"
												cssClass="form-control mandColorClass" id="subpurpose2${d}">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.suPurpose2List}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select> <input type="hidden" id="selSubPurpose2${d}"
											value="${command.licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[d].applicationPurpose3}" />
										</td>

										<td><c:set var="baseLookupCode" value="FAR" /> <form:select
												path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].far"
												cssClass="form-control mandColorClass" id="far${d}">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input
												path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].area"
												type="text" disabled=""
												class="form-control unit required-control  preventSpace hasDecimal"
												maxLength="100" id="area${d}" onkeypress="return hasAmount(event, this, 10, 5)" /></td>

										<td class="text-center"><a title="Add" id="addBtn"
											class="btn btn-blue-2 btn-sm addItemCF" onclick=""> <i
												class="fa fa-plus"></i>
										</a> <a href="javascript:void(0);"
											class="btn btn-danger btn-sm delButton" onclick=""> <i
												class="fa fa-minus"></i>
										</a></td>


									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />

								</tbody>

							</c:when>
							<c:otherwise>
								<tbody>
									<c:forEach var="dataList"
										items="${command.licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList}"
										varStatus="status">
										<tr class="itemDetailClass">
										<form:hidden
											path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].purposeId"
											id="purposeId${d}" />
										<td><form:select
												path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].applicationPurpose2"
												cssClass="form-control mandColorClass" id="subpurpose1${d}"
												onchange="getSubpurpose2(${d})"
												disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.suPurposeList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td class="subPurpose2${d}"><form:select
												path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].applicationPurpose3"
												cssClass="form-control mandColorClass" id="subpurpose2${d}"
												disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.suPurpose2List}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select> <input type="hidden" id="selSubPurpose2${d}"
											value="${command.licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[d].applicationPurpose3}" />
										</td>

										<td><c:set var="baseLookupCode" value="FAR" /> <form:select
												path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].far"
												cssClass="form-control mandColorClass" id="far${d}"
												disabled="${command.saveMode eq 'V' ? 'true' : 'false'}">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input
												path="licenseApplicationMasterDTO.licenseApplicationPurposeTypeDetDTOList[${d}].area"
												type="text" disabled="${command.saveMode eq 'V' ? 'true' : 'false'}"
												class="form-control unit required-control hasDecimal preventSpace"
												maxLength="100" id="area${d}"  onkeypress="return hasAmount(event, this, 10, 5)" /></td>
										<c:if test="${command.saveMode ne 'V' }">
										<td class="text-center"><a title="Add" id="addBtn"
											class="btn btn-blue-2 btn-sm addItemCF" onclick=""> <i
												class="fa fa-plus"></i>
										</a> <a href="javascript:void(0);"
											class="btn btn-danger btn-sm delButton" onclick=""> <i
												class="fa fa-minus"></i>
										</a></td>
										</c:if>


										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</c:otherwise>
						</c:choose>
					</table>

					<%-- <h4>
						<spring:message
								code="" text="Bifurcation Of Component of type of Licence" />
					</h4>
					
					<div class="form-group">
					
						<label class="col-sm-2 control-label" for=""><spring:message
								code="" text="Total Applied Area (in acres)" /></label>
						<div class="col-sm-4 ">
							<form:input name="" type="text"
								class="form-control"
								path="" id="totalAppliedArea"
								maxlength="20"
								disabled="true" />
						</div>
						
						<label class="col-sm-2 control-label" for=""><spring:message
								code="" text="Purpose Name" /></label>
						<div class="col-sm-4 ">
							<form:input name="" type="text"
								class="form-control"
								path="" id="purposeName"
								maxlength="100"
								disabled="true" />
						</div>
					</div> --%>
					
					<%-- <div class="form-group">
						<label class="col-sm-2 control-label" for=""><spring:message
								code="" text="Area(in acres)" /></label>
						<div class="col-sm-4 ">
							<form:input name="" type=""
								class="form-control hasDecimal"
								path="" id="area"
								maxlength="5" />
						</div>
						<div class="col-sm-12">
								<small class="text-blue-2"> <spring:message
										code=""
										text="(Max Percentage: 100%, Min Percentage: 80%)" />
								</small>
						</div>

						<label class="col-sm-2 control-label required-control" for="name"><spring:message
								code="" text="FAR" /></label>

						<div class="col-sm-4">
							<select id="far" name="district" data-rule-required="true"
								class="form-control mandColorClass">
								<option value="">--Select--</option>
								<option code="SO" value="1">1.75</option>
								<option code="SO" value="2">1.5</option>
							</select>
						</div>

					</div> --%>
					
					<div>
						<c:if test="${not empty command.detailsOfLandCheckList}">									
								<h4>
									<spring:message code="" text="Upload Documents" />
									<small class="text-blue-2"><spring:message
													code="" text="Only .pdf and jpeg, jpg, png files allowed"/></small>
								</h4>

								<div id="detailsOfLandcheckListDiv">
									<div class="panel-body">

										<div class="overflow margin-top-10 margin-bottom-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
													<c:set var="e" value="200" scope="page" />
													<c:set var="c" value="0" scope="page" />
														<tr>
															<th><spring:message code="water.serialNo"
																	text="Sr No" /></th>
															<th><spring:message code="water.docName"
																	text="Document Name" /></th>
															<%-- <th><spring:message code="water.status"
																	text="Status" /></th> --%>
															<th width="500"><spring:message
																	code="water.uploadText" text="Upload" />
																	<span><i style="font-size: 10px; font-weight: bold;"
																class="text-red-1"><spring:message code="file.upload.msg"
																text="(UploadFile upto 100MB and Only .pdf and jpeg,jpg)" /></i></span></th>
														</tr>

														<c:forEach items="${command.detailsOfLandCheckList}" var="lookUp"
															varStatus="lk">

															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}<c:if test="${lookUp.checkkMANDATORY eq 'Y'}"><span class="mand">*</span></c:if></td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}<c:if test="${lookUp.checkkMANDATORY eq 'Y'}"><span class="mand">*</span></c:if></td>
																	</c:otherwise>

																</c:choose>															
																<%-- <c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td><spring:message code="water.doc.mand" /></td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td><spring:message code="water.doc.opt" /></td>
																</c:if> --%>
																<td>
																	
																	<c:choose>
																	<c:when test="${command.saveMode ne 'V' }">
																		<div id="detailsOfLandDocs_${lk}">
																			<apptags:formField fieldType="7" labelCode=""
																				hasId="true"
																				fieldPath="detailsOfLandCheckList[${lk.index}]"
																				isMandatory="false" showFileNameHTMLId="true"
																				fileSize="TCPHR_MAX_FILE_SIZE"
																				maxFileCount="CHECK_LIST_MAX_COUNT"
																				validnFunction="CHECK_LIST_VALIDATION_EXTENSION_MRM"
																				currentCount="${e}"
																				folderName="detailsOfLandCheckList${e}" />
																		</div>
																	</c:when>
																	<c:otherwise>
																		<c:if
																			test="${not empty command.appLandDocumentList[c]}">
																			<c:set var="filePath"
																				value="${command.appLandDocumentList[c].attPath}" />
																			<c:set var="path"
																				value="${fn:replace(filePath,search,replace)}" />
																			<c:if test="${not empty path}">
																				<button type="button"
																					class="button-input btn btn-blue-2" name="button"
																					value="VIEW"
																					onclick="downloadFileInTag('${path}${replace}${command.appLandDocumentList[c].attFname}','NewTCPLicenseForm.html?Download','','')">
																					<spring:message code="" text="VIEW" />
																				</button>
																			</c:if>
																		</c:if>
																	</c:otherwise>
																</c:choose>
															</td>
															</tr>
															<c:set var="e" value="${e + 1}" scope="page" />
															<c:set var="c" value="${c + 1}" scope="page" />
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
						</c:if>
					</div>


					<div class="text-center">
						<c:choose>
							<c:when test="${command.saveMode eq 'V'}">
								<button type="button" class="button-input btn btn-success"
									name="button" value="Save"
									onclick="saveApplicationDetaisofLand(this)" id="">
									<spring:message code="" text="Next" />
								</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="button-input btn btn-success"
									name="button" value="Save"
									onclick="saveApplicationDetaisofLand(this)" id="">
									<spring:message code="" text="Save & Next" />
								</button>
							</c:otherwise>
						</c:choose>
						
						<button type="button" class="btn btn-danger"
							onclick="showTab('#landSchedule')" name="button"
							value="Back">
							<spring:message code="" text="Back" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>