<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
  <script type="text/javascript" src="js/asset/assetRegisterUpload.js"></script>
  <script type="text/javascript" src="js/mainet/file-upload.js"></script>
  <apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
			<h2>
				<spring:message code="asset.header.label.asset.register.upload"  text="Asset Register Upload"/>
			</h2>
			<apptags:helpDoc url="AssetRegisterUpload.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
		<c:set var="assetFlag"
			value="${userSession.moduleDeptCode == 'AST' ? true : false}" />
 <form:form id="AssetRegisterUpload" name="AssetRegisterUpload"
					class="form-horizontal" action="AssetRegisterUpload.html" method="post">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
			<c:choose>
				<c:when test="${assetFlag}">
					<input type="hidden" id="atype" value="AST" />
				</c:when>
				<c:otherwise>

					<input type="hidden" id="atype" value="IAST" />
				</c:otherwise>
			</c:choose>
			<form:hidden path="successFlag" id="successFlag" />
<!------------------------- this is for download tamplate and upload excel file start--------------------------->
					<div class="form-group">
						<label class="col-sm-3 control-label" for="ExportDocument"><spring:message
								code="excel.template" text="Excel Template" /></label>
						<div class="col-sm-3 text-left">
							<button type="button" class="btn btn-success save"
								name="button-Cancel" value="import"
								onclick="downloadTamplate();" id="import">
								<spring:message code="excel.template" text="Excel Template" />
							</button>
						</div>
						<label class="col-sm-3 control-label" for="ExcelFileUpload"><spring:message
								code="excel.file.upload" text="Excel File Upload" /></label>
						<div class="col-sm-3 text-left">
							<apptags:formField fieldPath="uploadFileName"
								showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
								currentCount="0" fieldType="7">
							</apptags:formField>
							<small class="text-blue-2" >
								<spring:message code="upload.excel.label" text="(Upload Excel upto 5MB)" /></small>
						</div>
						
						<form:hidden path="uploadFileName" id="filePath" />
					</div>
<!------------------------- this is for download tamplate and upload excel file start--------------------------->
<!------------------------- when your file is uploaded at that time it will show the values  start--------------------------->	
					<c:if test="${fn:length(command.astUploadDto)>0}">
					<c:set var="count" value="0" scope="page"></c:set>
				<div class="tableDiv">
					<table class="table table-bordered table-striped " id="sortbl">
						<thead>
							<tr>
								<th scope="col" width="10%"><spring:message
										code="" text="Asset Name" /><span class="mand">*</span></th>
								<th scope="col" width="8%"><spring:message
										code="" text="Serial No" /></th>
								<th scope="col" width="10%"><spring:message
										code="" text="Asset description" /><span class="mand">*</span></th>
								<th scope="col" width="10%"><spring:message code=""
										text="No. of units" /></th>
								<th scope="col" width="10%"><spring:message code=""
										text="GIS ID" /></th>
								<th scope="col" width="10%"><spring:message
										code="" text="Latitude" /></th>
								<th scope="col" width="8%"><spring:message
										code="" text="Longitude" /></th>
								<th scope="col" width="7%"><spring:message
										code="" text="department to which allocated" /></th>
								<th scope="col" width="9%"><spring:message
										code="" text="purpose of use/utilization of asset" /></th>
								<th scope="col" width="8%"><spring:message
										code="" text="asset class (movable, immovable)" /></th>
								<th scope="col" width="8%"><spring:message
										code="" text="Model identifier" /></th>
								
							</tr>
						</thead>
						<tbody>
								<c:if test="${fn:length(command.astUploadDto)>0}">
									<c:forEach var="sorData" items="${command.astUploadDto}"
										varStatus="status">
										<tr class="sorClass appendableClass">
											<td><form:input
													path="astUploadDto[${count}].assetName"
													cssClass="form-control" id="assetName${count}" disabled="true" 
													 /></td>
													 <td><form:input
													path="astUploadDto[${count}].serialNo"
													cssClass="form-control" id="serialNo${count}" disabled="true" 
													 /></td>
													 <td><form:input
													path="astUploadDto[${count}].discription"
													cssClass="form-control" id="discription${count}" disabled="true" 
													 /></td>
													 <td><form:input
													path="astUploadDto[${count}].noOfUnit"
													cssClass="form-control" id="noOfUnit${count}" disabled="true" 
													 /></td>
													 <td><form:input
													path="astUploadDto[${count}].gisId"
													cssClass="form-control" id="gisId${count}" disabled="true" 
													 /></td><td><form:input
													path="astUploadDto[${count}].latitude"
													cssClass="form-control" id="latitude${count}" disabled="true" 
													 /></td>
													 <td><form:input
													path="astUploadDto[${count}].longitude"
													cssClass="form-control" id="longitude${count}" disabled="true" 
													 /></td>
													 <td><form:input
													path="astUploadDto[${count}].department"
													cssClass="form-control" id="department${count}" disabled="true" 
													 /></td>
													 <td><form:input
													path="astUploadDto[${count}].purpose"
													cssClass="form-control" id="purpose${count}" disabled="true" 
													 /></td>
													
													  <td><form:input
													path="astUploadDto[${count}].assetClass1"
													cssClass="form-control" id="assetClass1${count}" disabled="true" 
													 /></td>
													  <td><form:input
													path="astUploadDto[${count}].modelIdent"
													cssClass="form-control" id="modelIdent${count}" disabled="true" 
													 /></td>
										
											<c:set var="count" value="${count + 1}" scope="page" />
										</tr>
									</c:forEach>
									</c:if>
						</tbody>
					</table>
				</div>
				
				</c:if>
				<!--changes for It Asset Table Data starts  -->
			<c:if test="${fn:length(command.itAstUploadDto)>0}">
				<c:set var="count" value="0" scope="page"></c:set>
				<div class="tableDiv">
					<table class="table table-bordered table-striped " id="sortbl">
						<thead>
							<tr>
								<%-- <th scope="col" width="10%"><spring:message
										code="" text="Asset Name" /><span class="mand">*</span></th> --%>
								<th scope="col" width="8%"><spring:message code=""
										text="Registered Serial No" /></th>
								<th scope="col" width="8%"><spring:message code=""
										text="Serial No" /><span class="mand">*</span></th>
								<th scope="col" width="8%"><spring:message code=""
										text="Hard Ware Name" /><span class="mand">*</span></th>
								<th scope="col" width="8%"><spring:message code=""
										text="PO No" /><span class="mand">*</span></th>


							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(command.itAstUploadDto)>0}">
								<c:forEach var="sorData" items="${command.itAstUploadDto}"
									varStatus="status">
									<tr class="sorClass appendableClass">

										<td><form:input path="itAstUploadDto[${count}].serialNo"
												cssClass="form-control" id="serialNo${count}"
												disabled="true" /></td>
										<td><form:input
												path="itAstUploadDto[${count}].assetModelIdentifier"
												cssClass="form-control" id="assetModelIdentifier${count}"
												disabled="true" /></td>
										<td><form:input
												path="itAstUploadDto[${count}].assetClass2"
												cssClass="form-control" id="assetClass2${count}"
												disabled="true" /></td>
										<td><form:input
												path="itAstUploadDto[${count}].purchaseOrderNo"
												cssClass="form-control" id="purchaseOrderNo${count}"
												disabled="true" /></td>


										<c:set var="count" value="${count + 1}" scope="page" />
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>

			</c:if>
			<!--changes for It Asset Table Data ends  -->
<!------------------------- when your file is uploaded at that time it will show the values  end--------------------------->				
<!------------------------- this is for error if present in your upload file it will show  start --------------------------->			
						<c:if
					test="${ fn:length(command.errDetails)>0 }">
					<h4 class="margin-bottom-10">Error Log</h4>
					<div id="errorTable">
						<c:set var="index" value="0" scope="page" />
						<table class="table table-bordered table-striped"
							id="errorTableRateType">
							<thead>
								<tr>
									<th width="20%"><spring:message
											code="excel.upload.filename" text="File Name" /></th>
									<th width="25%"><spring:message
											code="excel.upload.errordescription" text="Error Description" /></th>
									<th width="55%"><spring:message
											code="excel.upload.errordata" text="Error Data" /></th>
								</tr>
							</thead>
							<c:forEach var="error" items="${command.errDetails}"
								varStatus="status">
								<tr>
									<td><form:input id="fileName${index}"
											path="errDetails[${index}].fileName" class=" form-control"
											readonly="true" /></td>
									<td><form:input id="maDescription${index}"
											path="errDetails[${index}].errDescription" class=" form-control"
											readonly="true" /></td>
									<td><form:input id="maDescription${index}"
											path="errDetails[${index}].errData" class=" form-control"
											readonly="true" /></td>
								</tr>
								<c:set var="index" value="${index + 1}" scope="page" />
							</c:forEach>
						</table>
					</div>
				</c:if>	
<!--------------------------- this is for error if present in your upload file it will show  end -------------------------------->
			<div class="text-center padding-top-20">
			<button type="button" class="btn btn-success save" title='<spring:message code="ast.upload.saveexcel"/>'
								name="button-save" value="saveExcel"
								onclick="uploadExcelFile();" id="button-save">
								<spring:message code="ast.upload.saveexcel"/>
							</button>
			        <button type="button" class="btn btn-warning resetSearch" onclick="window.location.href = 'AssetRegisterUpload.html'" id=reset title='<spring:message code="reset.msg" />'><spring:message code="reset.msg" /></button>
					<%-- <apptags:backButton url="AssetSearch.html"></apptags:backButton> --%>
					<button type="button" class="btn btn-danger" name="button" id="Back" title='<spring:message code="asset.information.back" />'
						value="Back" onclick="window.location.href = '${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}'">
						<spring:message code="asset.information.back" />
					</button>
						</div>
			</form:form>
			</div>
			</div>
			</div>
		