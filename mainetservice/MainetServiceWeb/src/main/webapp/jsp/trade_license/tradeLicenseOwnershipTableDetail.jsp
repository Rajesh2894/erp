<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript" src="js/trade_license/tradeOwner.js"></script>


<!-- <div id="ownerType"> -->
<c:choose>

	<c:when test="${command.ownershipPrefix eq 'O'}">

		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-striped table-bordered" id="ownerDetail">
			<thead>
				<tr>
					<th width="8%"><spring:message code="owner.details.title"></spring:message></th>
					<th width="15%"><spring:message code="owner.details.name"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message
							code="owner.details.father/husband.name"></spring:message><span
						class="mand">*</span></th>
					<th width="8%"><spring:message code="owner.details.gender"></spring:message><span
						class="mand">*</span></th>
					<th width="15%"><spring:message code="owner.details.address"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message code="owner.details.mobileNo"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message code="owner.details.emailid"></spring:message></th>
					<th width="10%"><spring:message code="owner.details.adharno"></spring:message></th>
					<%-- <c:if test="${command.openMode ne 'D' }"> --%>
					<c:if test="${command.sudaEnv eq 'Y'}">
						<th width="8%"><spring:message code="owner.details.photo"></spring:message><h6 class="text-blue-2"><spring:message
															code="trade.suda.uploadfileupto" text="size"/></h6></th>
					</c:if>
					<c:if test="${command.sudaEnv ne 'Y'}">
					<th width="8%"><spring:message code="owner.details.photo"></spring:message><h6 class="text-blue-2"><spring:message
															code="trade.uploadfileupto" text="size"/></h6></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when
						test="${fn:length(command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO)>0 }">
						<c:forEach var="taxData"
							items="${command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO}"
							varStatus="status">
							<tr class="appendableClass">
								<td><c:set var="baseLookupCode" value="TTL" /> <form:select
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
										onchange="" class="form-control mandColorClass"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troTitle${d}" data-rule-required="true">
										<form:option value="">
											<spring:message code="trade.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troName"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasSpecialChara preventSpace" maxLength="100"
										id="troName${d}" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMname"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasSpecialChara preventSpace" maxLength="50"
										id="troMname${d}" /></td>
								<td>

									<div>

										<c:set var="baseLookupCode" value="GEN" />
										<form:select
											path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troGen"
											onchange="" class="form-control mandColorClass"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="troGen${d}" data-rule-required="true">
											<form:option value="">
												<spring:message code="trade.sel.optn.gender" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpCode}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>


								</td>
								<td>
									<%-- <form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
									type="text" disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control"
									id="troAddress${d}" data-rule-required="true" />  --%> <form:textarea
										class="form-control preventSpace" maxLength="200"
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troAddress${d}" data-rule-required="true"></form:textarea>


								</td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMobileno"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasMobileNo preventSpace"
										id="troMobileno${d}" data-rule-required="true" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troEmailid"
										type="text"
										class="form-control unit required-control hasemailclass preventSpace"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troEmailid${d}" data-rule-email="true" maxLength="50" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAdhno"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasAadharNo preventSpace"
										data-rule-maxlength="12" data-rule-minlength="12"
										id="troAdhno${d}" maxlegnth="12" /></td>
								<c:if test="${command.sudaEnv eq 'Y'}">
									<c:if
										test="${command.openMode ne 'D' && command.tradeMasterDetailDTO.apmApplicationId eq null}">
										<td class="text-center"><apptags:formField fieldType="7"
												fieldPath="command.tradeMasterDetailDTO.attachments[${d}]"
												currentCount="${100 + index}" showFileNameHTMLId="true"
												folderName="${d}" fileSize="MIN_FILE_SIZE"
												isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
											</apptags:formField></td>
									</c:if>
								</c:if>
								<c:if test="${command.sudaEnv ne 'Y'}">
									<c:if
										test="${command.openMode ne 'D' && command.tradeMasterDetailDTO.apmApplicationId eq null}">
										<td class="text-center"><apptags:formField fieldType="7"
												fieldPath="command.tradeMasterDetailDTO.attachments[${d}]"
												currentCount="${100 + index}" showFileNameHTMLId="true"
												folderName="${d}" fileSize="TRADE_COMMON_MAX_SIZE"
												isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
											</apptags:formField></td>
									</c:if>
								</c:if>

								<%-- <c:if
									test="${command.openMode ne 'D' && command.tradeMasterDetailDTO.apmApplicationId ne null}">
									<td class="text-center"><apptags:filedownload
											filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
											actionUrl="TradeApplicationForm.html?Download"></apptags:filedownload>
									</td>
								</c:if> --%>
								
								
								 <c:if test="${command.downloadMode eq 'M'}">
								<td><div id="uploadPreview">
							<c:forEach items="${command.fileNames}" var="entry">
								<c:if test="${entry.key eq d}">

									<ul>
										<c:forEach items="${entry.value}" var="val">
											<li id=""><%-- <img src="${val}" width="100" height="100"
												class="img-thumbnail"> --%><a href="${val}" download><i
													class="fa fa-download"></i></a></li>
										</c:forEach>
									</ul>

								</c:if>
							</c:forEach>
						</div></td></c:if>
						
						<c:if test="${command.fileMode eq 'G'}">
								<td><div id="preview">
							
								<c:forEach items="${taxData.viewImg}" var="lookUp">
							<apptags:filedownload filename="${lookUp.attFname}"
								dmsDocId="${lookUp.dmsDocId}"
								filePath="${lookUp.attPath}"
								actionUrl="AdminHome.html?Download"></apptags:filedownload>
							<form:hidden path="command" value="${lookUp.attId}" />
						</c:forEach>
							
						</div></td></c:if>

							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="appendableClass">
							<td><c:set var="baseLookupCode" value="TTL" /> <form:select
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
									onchange="" class="form-control mandColorClass"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troTitle${d}" data-rule-required="true">
									<form:option value="">
										<spring:message code="trade.sel.optn.gender" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>

							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troName"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasSpecialChara preventSpace" maxLength="100"
									id="troName${d}" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMname"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasSpecialChara preventSpace" maxLength="50"
									id="troMname${d}" /></td>
							<td>

								<div>

									<c:set var="baseLookupCode" value="GEN" />
									<form:select
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troGen"
										onchange="" class="form-control mandColorClass"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troGen${d}" data-rule-required="true">
										<form:option value="">
											<spring:message code="trade.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpCode}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>


							</td>
							<td>
								<%-- <form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control" id="troAddress${d}"
										data-rule-required="true" /> --%> <form:textarea
									class="form-control preventSpace" maxLength="200"
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troAddress${d}" data-rule-required="true"></form:textarea>

							</td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMobileno"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasMobileNo preventSpace"
									id="troMobileno${d}" data-rule-required="true" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troEmailid"
									type="text"
									class="form-control unit required-control hasemailclass preventSpace"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troEmailid${d}" data-rule-email="true" maxLength="50" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAdhno"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasAadharNo preventSpace"
									data-rule-maxlength="12" data-rule-minlength="12"
									id="troAdhno${d}" maxlegnth="12" /></td>
							<c:if test="${command.sudaEnv eq 'Y'}">
								<c:if
									test="${command.openMode ne 'D' && command.tradeMasterDetailDTO.apmApplicationId eq null}">
									<td class="text-center"><apptags:formField fieldType="7"
											fieldPath="command.tradeMasterDetailDTO.attachments[${d}].uploadedDocumentPath"
											currentCount="${100 + d}" showFileNameHTMLId="true"
											folderName="${d}" fileSize="MIN_FILE_SIZE"
											isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
										</apptags:formField></td>
								</c:if>
							</c:if>
							<c:if test="${command.sudaEnv ne 'Y'}">
								<c:if
									test="${command.openMode ne 'D' && command.tradeMasterDetailDTO.apmApplicationId eq null}">
									<td class="text-center"><apptags:formField fieldType="7"
											fieldPath="command.tradeMasterDetailDTO.attachments[${d}].uploadedDocumentPath"
											currentCount="${100 + d}" showFileNameHTMLId="true"
											folderName="${d}" fileSize="TRADE_COMMON_MAX_SIZE"
											isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
										</apptags:formField></td>
								</c:if>
							</c:if>
							<%-- <c:if
								test="${command.openMode ne 'D' && command.tradeMasterDetailDTO.apmApplicationId ne null}">
								<td class="text-center"><apptags:filedownload
										filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
										actionUrl="TradeApplicationForm.html?Download"></apptags:filedownload>
								</td>
							</c:if> --%>
							
							 <c:if test="${command.downloadMode eq 'M'}">
							<td><div id="uploadPreview">
							<c:forEach items="${command.fileNames}" var="entry">
								<c:if test="${entry.key eq d}">

									<ul>
										<c:forEach items="${entry.value}" var="val">
											<li id=""><%-- <img src="${val}" width="100" height="100"
												class="img-thumbnail"> --%><a href="${val}" download><i
													class="fa fa-download"></i></a></li>
										</c:forEach>
									</ul>

								</c:if>
							</c:forEach>
						</div></td></c:if>
						
						<c:if test="${command.fileMode eq 'G'}">
								<td><div id="preview">
							
								<c:forEach items="${taxData.viewImg}" var="lookUp">
							<apptags:filedownload filename="${lookUp.attFname}"
								dmsDocId="${lookUp.dmsDocId}"
								filePath="${lookUp.attPath}"
								actionUrl="AdminHome.html?Download"></apptags:filedownload>
							<form:hidden path="command" value="${lookUp.attId}" />
						</c:forEach>
							
						</div></td></c:if>

						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>

		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-striped table-bordered" id="ownerDetail">
			<thead>
				<tr>
					<th width="8%"><spring:message code="owner.details.title" /></th>
					<c:choose>
					<c:when test="${command.ownershipPrefix eq 'R'}">
					<th width="15%"><spring:message code="" text="Tenant Name"></spring:message><span
						class="mand">*</span></th>
					</c:when>
					<c:otherwise>
					
					<th width="15%"><spring:message code="owner.details.name"></spring:message><span
						class="mand">*</span></th>
					</c:otherwise>
					</c:choose>
					<th width="10%"><spring:message
							code="owner.details.father/husband.name"></spring:message><span
						class="mand">*</span></th>
					<th width="8%"><spring:message code="owner.details.gender"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message code="owner.details.address"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message code="owner.details.mobileNo"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message code="owner.details.emailid"></spring:message></th>
					<th width="10%"><spring:message code="owner.details.adharno"></spring:message></th>
					<%-- <c:if test="${command.openMode ne 'D' }"> --%>
					<c:choose>
					<c:when test="${command.ownershipPrefix eq 'R'}">
					<c:if test="${command.sudaEnv ne 'Y'}">
						<th width="8%"><spring:message code="" text="Tenant's Photo"></spring:message><small class="text-blue-2"><spring:message
															code="trade.uploadfileupto" text=" "/></small></th>
					</c:if>
					<c:if test="${command.sudaEnv eq 'Y'}">
					<th width="8%"><spring:message code="" text="Tenant's Photo"></spring:message><small class="text-blue-2"><spring:message
															code="trade.suda.uploadfileupto" text=" "/></small></th>
					</c:if>
				    </c:when>	
				    <c:otherwise>	
				    	<c:if test="${command.sudaEnv ne 'Y'}">
				    <th width="8%"><spring:message code="owner.details.photo"></spring:message><small class="text-blue-2"><spring:message
															code="trade.uploadfileupto" text=" "/></small></th>
						</c:if>
						<c:if test="${command.sudaEnv eq 'Y'}">
						<th width="8%"><spring:message code="owner.details.photo"></spring:message><small class="text-blue-2"><spring:message
															code="trade.suda.uploadfileupto" text=" "/></small></th>
						</c:if>
				    </c:otherwise>
					</c:choose>
					<%-- </c:if> --%>
					<!-- <th width="8%"><a title="Add" class="btn btn-blue-2 btn-sm addCF"
							><i class="fa fa-plus"></i></a></th> -->

					<%-- <c:if test="${command.viewMode ne 'V'}"> --%>
					<c:if test="${command.openMode ne 'D' }">
					<th colspan="2"><a href="javascript:void(0);" title="Add"
						class=" addCF btn btn-success btn-sm" id="addUnitRow"
						onclick='fileCountUpload(this);'> <i
							class="fa fa-plus-circle"></i></a></th></c:if>
							
					<%-- </c:if> --%>


				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when
						test="${fn:length(command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO)>0 }">
						<c:forEach var="taxData"
							items="${command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO}"
							varStatus="status">
							<tr class="appendableClass">

								<td><c:set var="baseLookupCode" value="TTL" /> <form:select
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
										onchange="" class="form-control mandColorClass"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troTitle${d}" data-rule-required="">
										<form:option value="">
											<spring:message code="trade.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>


								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troName"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasSpecialChara preventSpace" maxLength="100"
										id="troName${d}" data-rule-required="true" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMname"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasSpecialChara preventSpace" maxLength="50"
										id="troMname${d}" /></td>
								<td><c:set var="baseLookupCode" value="GEN" /> <form:select
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troGen"
										onchange="" class="form-control mandColorClass"
										id="troGen${d}"
										disabled="${command.viewMode eq 'V' ? true : false }"
										data-rule-required="true">
										<form:option value="">
											<spring:message code="trade.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpCode}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>
								<td>
									<%-- <form:input
											path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
											type="text"
											disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control unit required-control"
											id="troAddress${d}" data-rule-required="true" /> --%> <form:textarea
										class="form-control preventSpace" maxLength="200"
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troAddress${d}" data-rule-required="true"></form:textarea>

								</td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMobileno"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasMobileNo preventSpace"
										id="troMobileno${d}" data-rule-required="true" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troEmailid"
										type="text"
										class="form-control unit required-control hasemailclass preventSpace"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troEmailid${d}" data-rule-email="true" maxLength="50" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAdhno"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasAadharNo preventSpace"
										data-rule-maxlength="12" data-rule-minlength="12"
										id="troAdhno${d}" maxlegnth="12" /></td>
										
								<c:if test="${command.sudaEnv eq 'Y'}">
									<c:if
										test="${command.openMode ne 'D'  && command.tradeMasterDetailDTO.apmApplicationId eq null}">
										<td class="text-center"><apptags:formField fieldType="7"
												fieldPath="command.tradeMasterDetailDTO.attachments[${d}].uploadedDocumentPath"
												currentCount="${100 + d}" showFileNameHTMLId="true"
												folderName="${d}" fileSize="MIN_FILE_SIZE"
												isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
											</apptags:formField></td>
									</c:if>
								</c:if>
								<c:if test="${command.sudaEnv ne 'Y'}">
									<c:if
										test="${command.openMode ne 'D'  && command.tradeMasterDetailDTO.apmApplicationId eq null}">
										<td class="text-center"><apptags:formField fieldType="7"
												fieldPath="command.tradeMasterDetailDTO.attachments[${d}].uploadedDocumentPath"
												currentCount="${100 + d}" showFileNameHTMLId="true"
												folderName="${d}" fileSize="TRADE_COMMON_MAX_SIZE"
												isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
												validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
											</apptags:formField></td>
									</c:if>
								</c:if>
								<%-- <c:if
									test="${command.openMode ne 'D' && command.tradeMasterDetailDTO.apmApplicationId ne null}">
									<td class="text-center"><apptags:filedownload
											filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
											actionUrl="TradeApplicationForm.html?Download"></apptags:filedownload>
									</td>
								</c:if> --%>
								
								 <c:if test="${command.downloadMode eq 'M'}">
								<td><div id="uploadPreview">
							<c:forEach items="${command.fileNames}" var="entry">
								<c:if test="${entry.key eq d}">

									<ul>
										<c:forEach items="${entry.value}" var="val">
											<li id=""><%-- <img src="${val}" width="100" height="100"
												class="img-thumbnail"> --%><a href="${val}" download><i
													class="fa fa-download"></i></a></li>
										</c:forEach>
									</ul>

								</c:if>
							</c:forEach>
						</div></td></c:if>
						
						<c:if test="${command.fileMode eq 'G'}">
								<td><div id="preview">
							
								<c:forEach items="${taxData.viewImg}" var="lookUp">
							<apptags:filedownload filename="${lookUp.attFname}"
								dmsDocId="${lookUp.dmsDocId}"
								filePath="${lookUp.attPath}"
								actionUrl="AdminHome.html?Download"></apptags:filedownload>
							<form:hidden path="command" value="${lookUp.attId}" />
						</c:forEach>
							
						</div></td></c:if>
								
								<c:if test="${command.openMode ne 'D' }">
								<td class="text-center"><a href="javascript:void(0);"
									class="btn btn-danger btn-sm delButton" onclick="doFileDeletion($(this),${100+d});"><i
										class="fa fa-minus" id="0_file_${d}"></i></a></td></c:if>


							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
					</c:when>
					<c:otherwise>

						<tr class="appendableClass">

							<td><c:set var="baseLookupCode" value="TTL" /> <form:select
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
									onchange="" class="form-control mandColorClass"
									id="troTitle${d}" data-rule-required="">
									<form:option value="">
										<spring:message code="trade.sel.optn.gender" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>


							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troName"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasSpecialChara preventSpace" maxLength="100"
									id="troName${d}" data-rule-required="true" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMname"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasSpecialChara preventSpace" maxLength="50"
									id="troMname${d}" /></td>
							<td><c:set var="baseLookupCode" value="GEN" /> <form:select
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troGen"
									disabled="${command.viewMode eq 'V' ? true : false }"
									onchange="" class="form-control mandColorClass" id="troGen${d}"
									data-rule-required="true">
									<form:option value="">
										<spring:message code="trade.sel.optn.gender" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpCode}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>
							<td>
								<%-- <form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control" id="troAddress${d}"
										data-rule-required="true" /> --%> <form:textarea
									class="form-control preventSpace" maxLength="200"
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troAddress${d}" data-rule-required="true"></form:textarea>


							</td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMobileno"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasMobileNo preventSpace"
									id="troMobileno${d}" data-rule-required="true" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troEmailid"
									type="text"
									class="form-control unit required-control hasemailclass preventSpace"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troEmailid${d}" data-rule-email="true" maxLength="50" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAdhno"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasAadharNo preventSpace"
									data-rule-maxlength="12" data-rule-minlength="12"
									id="troAdhno${d}" maxlegnth="12" /></td>
							<c:if test="${command.sudaEnv eq 'Y'}">
							<c:if
								test="${command.openMode ne 'D'  && command.tradeMasterDetailDTO.apmApplicationId eq null}">
								<td class="text-center"><apptags:formField fieldType="7"
										fieldPath="command.tradeMasterDetailDTO.attachments[${d}].uploadedDocumentPath" currentCount="${100 + d}" 
										showFileNameHTMLId="true" folderName="${d}"
										fileSize="MIN_FILE_SIZE" isMandatory="false"
										maxFileCount="CHECK_LIST_MAX_COUNT"
										validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
									</apptags:formField></td>
							</c:if>
							</c:if>
							<c:if test="${command.sudaEnv ne 'Y'}">
								<c:if
									test="${command.openMode ne 'D'  && command.tradeMasterDetailDTO.apmApplicationId eq null}">
									<td class="text-center"><apptags:formField fieldType="7"
											fieldPath="command.tradeMasterDetailDTO.attachments[${d}].uploadedDocumentPath"
											currentCount="${100 + d}" showFileNameHTMLId="true"
											folderName="${d}" fileSize="TRADE_COMMON_MAX_SIZE"
											isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
											validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
										</apptags:formField></td>
								</c:if>
							</c:if>

							<c:if test="${command.downloadMode eq 'M'}">
							<td><div id="uploadPreview">
							<c:forEach items="${command.fileNames}" var="entry">
								<c:if test="${entry.key eq d}">

									<ul>
										<c:forEach items="${entry.value}" var="val">
											<li id=""><%-- <img src="${val}" width="100" height="100"
												class="img-thumbnail"> --%><a href="${val}" download><i
													class="fa fa-download"></i></a></li>
										</c:forEach>
									</ul>

								</c:if>
							</c:forEach>
						</div></td></c:if>
						
						<c:if test="${command.fileMode eq 'G'}">
								<td><div id="preview">
							
								<c:forEach items="${taxData.viewImg}" var="lookUp">
							<apptags:filedownload filename="${lookUp.attFname}"
								dmsDocId="${lookUp.dmsDocId}"
								filePath="${lookUp.attPath}"
								actionUrl="AdminHome.html?Download"></apptags:filedownload>
							<form:hidden path="command" value="${lookUp.attId}" />
						</c:forEach>
							
						</div></td></c:if>
							
							<%-- <c:if
								test="${command.openMode ne 'D' && command.tradeMasterDetailDTO.apmApplicationId ne null}">
								<td class="text-center"><apptags:filedownload
										filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
										actionUrl="TradeApplicationForm.html?Download"></apptags:filedownload>
								</td>
							</c:if> --%>

							<c:if test="${command.openMode ne 'D' }">
							<td class="text-center"><a href="javascript:void(0);"
								class="btn btn-danger btn-sm delButton" onclick="doFileDeletion($(this),${d});"><i
									class="fa fa-minus" id="0_file_${d}"></i></a></td></c:if>
									

						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:otherwise>
				</c:choose>

			</tbody>
		</table>
	</c:otherwise>
</c:choose>
<!-- </div> -->


