<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetDocumentDetails.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!--D#76780  -->
<style>
input.astDatt {
	width: 200px;
	padding: 0 20px;
}

input.astDatt, input.astDatt::-webkit-input-placeholder {
	font-size: 10px;
	line-height: 2;
}
</style>
<c:set var="assetFlag"	value="${userSession.moduleDeptCode == 'AST' ? true : false}" />

			<c:if test="${userSession.moduleDeptCode != 'AST' && userSession.moduleDeptCode != 'IAST'}">
				<c:set var="assetFlag"	value="${command.astDetailsDTO.assetInformationDTO.deptCode == 'AST' ? true : false}" />
			</c:if> 
 <c:if test="${assetFlag}">               
    <div class="widget-content padding" id="astDocId">
     </c:if>

	<form:form action="AssetRegistration.html" id="assetDocumentId"
		method="post" class="form-horizontal">
		
		<c:if test = "${userSession.moduleDeptCode == 'AST' }">
		<form:hidden path="modeType" id="modeType" />
		</c:if>
		<form:hidden path="deleteByAtdId" id="deleteByAtdId" />
		
		<input type="hidden" id="moduleDeptUrl"
			value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
			
			<!-- when it is of IT Asset we are not using Location Tab because of that department value is not comming so passing dummy value for avoing that validation -->
			<c:if test="${!assetFlag}">
				<form:hidden path="astDetailsDTO.assetClassificationDTO.department" id="deptITAssetId" value ="-1" />
			</c:if>
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		 <c:if test="${userSession.moduleDeptCode == 'AST'}">  
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDivDoc"></div>
			</c:if>
			<spring:message code="Enter.document.description" var="docdiscrip" />
		<c:if test="${!assetFlag}">
	             <div class="panel-heading">
					<h4 class="panel-title">
						
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#docUpload"><spring:message
										code="asset.lenear.documentdetails" /></a>
							

					</h4>
				</div>
				</c:if>
				
			<div id="docUpload" class="panel-collapse collapse in">
			<div class="panel-body">
			<c:if test="${(command.modeType ne 'V' || command.modeType eq 'E') }">
			<!---------------------------------------------document detail upload start ---------------------------------------------------->

			<div id="uploadTagDiv">
				<div class="table-responsive">
					<c:set var="index" value="0" scope="page" />
					<table class="table table-bordered table-striped" id="attachDoc">
						<tr>
							<%-- <th><spring:message code="" text="Sr No." /></th>  --%>
							<th><spring:message code="asset.document.description"
									text="Document Description" /></th>
							<th><spring:message code="work.estimate.upload"
									text="Upload Document" /></th>
							<th scope="col" width="8%"><a
								onclick='fileCountUpload(this);'
								class="btn btn-blue-2 btn-sm addButton"><i
									class="fa fa-plus-circle"></i></a></th>
						</tr>
						<tr class="appendableClass">
							<%-- <td id="sequnce">${d}</td> --%>
							<td><form:input placeholder="${docdiscrip}"
									path="astDetailsDTO.attachments[${index}].doc_DESC_ENGL"
									disabled="${command.modeType eq 'V'}" class=" form-control astDatt"
									maxlength="100" /></td>
							<td class="text-center"><apptags:formField fieldType="7"
									isDisabled="${command.modeType eq 'V'}"
									fieldPath="astDetailsDTO.attachments[${index}].uploadedDocumentPath"
									currentCount="${index}" showFileNameHTMLId="true"
									folderName="${index}" fileSize="WORK_COMMON_MAX_SIZE"
									isMandatory="false" maxFileCount="WORKS_MANAGEMENT_MAXSIZE"
									validnFunction="ALL_UPLOAD_VALID_EXTENSION">
								</apptags:formField>
								<small class="text-blue-2"><spring:message code="work.file.upload.tooltip" text="(Upload File upto 5MB and Only pdf,doc,docx,jpeg,jpg,png,gif,bmp,xls,xlsx extension(s) file(s) are allowed.)" /></small></td>
							<td class="text-center"><a href='#' id="0_file_${index}"
								onclick="doFileDelete(this)"
								class='btn btn-danger btn-sm delButton'><i
									class="fa fa-trash"></i></a></td>
						</tr>
						<c:set var="index" value="${index + 1}" scope="page" />
					</table>
				</div>
			</div>
		</c:if>
		
			
		<c:choose>
			<c:when
				test="${(!assetFlag &&  (command.modeType eq 'V' || command.modeType eq 'E') && fn:length(command.astDetailsDTO.attachDocsList)>0 ) || (assetFlag && (command.modeType eq 'V' || command.modeType eq 'E') )}">
				<!---------------------------------------------document detail upload end ---------------------------------------------------------->
				<!----------------------------------- document detail view start---------------------------------------------------------------- -->

					<c:if test="${fn:length(command.astDetailsDTO.attachDocsList)>0}">
						<div class="table-responsive">
							<table class="table table-bordered table-striped" id="deleteDoc">
								<tr>
									<th width="" align="center"><spring:message code="ser.no"
											text="" /><input type="hidden" id="srNo"></th>
									<th scope="col" width="64%" align="center"><spring:message
											code="work.estimate.document.description"
											text="Document Description" /></th>
									<th scope="col" width="30%" align="center"><spring:message
											code="scheme.view.document" /></th>
									<c:if test="${command.modeType ne 'V'}">
										<th scope="col" width="8%"><spring:message
												code="scheme.action" text=""></spring:message></th>
									</c:if>
								</tr>
								<c:set var="index" value="0" scope="page" />
								<c:forEach items="${command.astDetailsDTO.attachDocsList}"
									var="lookUp">
									<tr>
										<td>${index+1}</td>
										<td>${lookUp.dmsDocName}</td>
										<td><apptags:filedownload filename="${lookUp.attFname}"
												filePath="${lookUp.attPath}"
												actionUrl="AssetRegistration.html?Download"
												dmsDocId="${lookUp.dmsDocId}" /><br>
										<p class="text-small">
												Uploaded Date:
												<fmt:formatDate value="${lookUp.attDate}"
													pattern="dd-MM-yyyy" />
											</p></td>

										<c:if test="${command.modeType ne 'V'}">
											<td class="text-center"><a href='#' id="deleteFile"
												onclick="return false;" class="btn btn-danger btn-sm"><i
													class="fa fa-trash"></i></a> <form:hidden path=""
													value="${lookUp.attId}" /></td>
										</c:if>
									</tr>
									<c:set var="index" value="${index + 1}" scope="page" />
								</c:forEach>
							</table>
						</div>
						<br>
					</c:if>
			</c:when>
			<c:otherwise>
					<div class="table-responsive">
						<table class="table table-bordered table-striped" id="deleteDoc">
							<tr>
								<th width="" align="center"><spring:message code="ser.no"
										text="" /><input type="hidden" id="srNo"></th>
								<th scope="col" width="64%" align="center"><spring:message
										code="work.estimate.document.description"
										text="Document Description" /></th>
								<th scope="col" width="30%" align="center"><spring:message
										code="scheme.view.document" /></th>
							</tr>
							<c:set var="index" value="0" scope="page" />
							<tr>
								<td colspan="3"><spring:message
										code="asset.documentdetails.noresultfound" /></td>
								<!-- <td>No Results Found</td>
									<td>No Results Found</td> -->
							</tr>
						</table>
					</div>
					<br>
			</c:otherwise>
		</c:choose>
		<!------------------------ document detail view end------------------------------------------------------------------  -->
		</div>
		</div>
		<c:if test="${command.approvalProcess ne 'Y' && assetFlag }">
			<div class="text-center margin-top-10">
				<c:choose>
					<c:when
						test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D' }">
						<c:set var="backButtonAction"
							value="showPreviousTab('${userSession.moduleDeptCode == 'AST' ? '#astLine':'#astCod'}','#astCod')" />
					</c:when>
					<c:otherwise>
						<c:set var="backButtonAction" value="backToHomePage()" />
					</c:otherwise>
				</c:choose>
				
<c:if
					test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
					<button type="button" class="button-input btn btn-success"
						name="button" value="Save" onclick="saveAssetDocDetails(this);"
						id="save">
						<spring:message code="asset.documentdetails.save&continue" />
					</button>
				</c:if>
				<button type="button" class="btn btn-danger" name="button" id="Back"
					value="Back" onclick="${backButtonAction}">
					<spring:message code="asset.information.back" />
				</button>

			</div>
		</c:if>
	</form:form>
	 <c:if test="${assetFlag}">               
   </div>
     </c:if>
<!-- End of info box -->
