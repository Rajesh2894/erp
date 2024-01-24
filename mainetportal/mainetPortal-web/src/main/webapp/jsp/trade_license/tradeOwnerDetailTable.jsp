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
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
<!-- <script
	src="js/trade_license/tradeLicenseApplicationForm.js"></script> -->
	
<style>
.imgupload input{width:200px;}
</style>

<div class="table-responsive">
	<c:set var="d" value="0" scope="page"></c:set>
	<table class="table table-striped table-bordered" id="ownerDetail">
		<thead>
			<tr>
				<th width="8%"><spring:message code="owner.details.title" /></th>
				<th width="15%"><spring:message code="owner.details.name"></spring:message><span
					class="mand">*</span></th>
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
				<c:if test="${command.openMode ne 'D' }">
					<th width="8%"><spring:message code="owner.details.photo"></spring:message><small
							class="text-blue-2"><spring:message
									code="trade.uploadfileupto" text=" " /></th>
				</c:if>
				<!-- <th width="8%"><a title="Add" class="btn btn-blue-2 btn-sm"
					onclick='fileCountUpload(this);'><i class="fa fa-plus"></i></a></th> -->

				<!-- <th colspan="2"><a href="javascript:void(0);" title="Add"
					class="addCF btn btn-success btn-sm" id="addUnitRow"><i
						class="fa fa-plus-circle"></i></a></th> -->
						
				<th colspan="2"><a href="javascript:void(0);" title="Add"
					class="addCF btn btn-success btn-sm" id="addUnitRow"><i
						class="fa fa-plus-circle"></i></a></th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="taxData"
				items="${command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO}"
				varStatus="status">
				<tr class="appendableClass">

					<td>
						<%-- <div>
									<c:set var="baseLookupCode" value="TTL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
										cssClass="form-control mandColorClass" hasChildLookup="false"
										hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										hasTableForm="true" isMandatory="true" />
										
										
								</div> --%> <c:set var="baseLookupCode" value="TTL" /> <form:select
							path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
							onchange="" class="form-control mandColorClass" id="troTitle${d}"
							data-rule-required="">
							<form:option value="">
								<spring:message code="trade.sel.optn.gender" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>

					</td>


					<td><form:input
							path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troName"
							type="text"
							class="form-control unit required-control hasCharacter"
							id="troName${d}" data-rule-required="true" /></td>
					<td><form:input
							path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMname"
							type="text"
							class="form-control unit required-control hasCharacter"
							id="troMname${d}" /></td>
					<td><c:set var="baseLookupCode" value="GEN" /> <form:select
							path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troGen"
							onchange="" class="form-control mandColorClass" id="troGen${d}"
							data-rule-required="">
							<form:option value="">
								<spring:message code="trade.sel.optn.gender" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpCode}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select></td>
					<%-- <td><form:input
							path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
							type="text" class="form-control unit required-control"
							id="troAddress${d}" data-rule-required="true" /></td> --%>

					<td><form:textarea class="form-control mandColorClass"
							path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
							type="text" id="troAddress${d}" data-rule-required="true"></form:textarea></td>
					<td><form:input
							path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMobileno"
							type="text"
							class="form-control unit required-control hasMobileNo"
							id="troMobileno${d}" data-rule-required="true" /></td>
					<td><form:input
							path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troEmailid"
							type="text"
							class="form-control unit required-control hasemailclass"
							id="troEmailid${d}" data-rule-email="true" maxlegnth="50" /></td>
					<td><form:input
							path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAdhno"
							type="text"
							class="form-control unit required-control hasAadharNo"
							data-rule-maxlength="12" data-rule-minlength="12"
							id="troAdhno${d}" maxlegnth="12" /></td>
					<c:if
						test="${command.openMode ne 'D' && command.tradeMasterDetailDTO.apmApplicationId eq null }">
						<td class="text-center imgupload"><apptags:formField fieldType="7"
								fieldPath="command.tradeMasterDetailDTO.attachments[${d}].uploadedDocumentPath"
								currentCount="${100 + d}" showFileNameHTMLId="true"
								folderName="${d}" fileSize="TRADE_COMMON_MAX_SIZE"
								isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION">
							</apptags:formField></td>
					</c:if>
					<%-- <c:if
						test="${command.tradeMasterDetailDTO.apmApplicationId ne null}">
						<td class="text-center"><apptags:filedownload
								filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
								actionUrl="TradeApplicationForm.html?Download"></apptags:filedownload>
						</td>
					</c:if> --%>

					<td class="text-center"><a href="javascript:void(0);"
						class="btn btn-danger btn-sm delButton" id="0_file_${d}"
						onclick="doFileDeletion($(this),${d});"><i class="fa fa-minus"></i></a></td>


				</tr>
				<c:set var="d" value="${d + 1}" scope="page" />
			</c:forEach>
		</tbody>
	</table>
</div>
