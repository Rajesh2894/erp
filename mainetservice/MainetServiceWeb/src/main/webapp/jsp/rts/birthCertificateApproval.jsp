<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/rts/birthCertificateApproval.js"></script>

<script>
	$('[data-toggle="tooltip"]').tooltip({
		trigger : 'hover'
	})
</script>


<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="BirthCertificateDTO.form.name" text="Birth Certificate Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding" id="">
				<form:form id="frmBirthCertificateForm"
					action="BirthCertificateApproval.html" method="POST"
					class="form-horizontal" name="frmBirthCertificateForm">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>


					<h4>
						<spring:message code="BirthCertificateDTO.childbirthDetails" text="Child Information" />
					</h4>



					<div class="form-group">

                          <apptags:date fieldclass="datepicker" labelCode="BirthCertificateDTO.brDob"
							datePath="birthCertificateDto.brDob"  isDisabled="true" >
						</apptags:date>
										
                          
						<label class="control-label col-sm-2 required-control"
							for="Census"> <spring:message
								code="BirthCertificateDTO.brSex" text="Sex" />
						</label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="birthCertificateDto.brSex" cssClass="form-control"
							disabled="true" isMandatory="true" hasId="true"
							selectOptionLabelCode="selectdropdown" />

					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthCertificateDTO.brChildName"
							path="birthCertificateDto.brChildName" isMandatory=""
							isReadonly="true" cssClass="hasNameClass form-control"
							maxlegnth="400">
						</apptags:input>
						<apptags:input labelCode="BirthCertificateDTO.brChildNameMar"
							path="birthCertificateDto.brChildNameMar" isMandatory=""
							isReadonly="true" cssClass="hasNameClass form-control"
							maxlegnth="400">
						</apptags:input>
					</div>

					<div class="form-group">

						<apptags:input labelCode="BirthCertificateDTO.brBirthPlace"
							path="birthCertificateDto.brBirthPlace" isMandatory="true"
							isReadonly="true" cssClass="hasNameClass form-control"
							maxlegnth="200">
						</apptags:input>
						<apptags:input labelCode="BirthCertificateDTO.brBirthPlaceMar"
							path="birthCertificateDto.brBirthPlaceMar" isMandatory="true"
							isReadonly="true" cssClass="hasNameClass form-control"
							maxlegnth="200">
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthCertificateDTO.brBirthAddr"
							path="birthCertificateDto.brBirthAddr" isMandatory="true"
							isReadonly="true" cssClass="hasNumClass form-control"
							maxlegnth="800">
						</apptags:input>

						<apptags:input labelCode="BirthCertificateDTO.brBirthAddrMar"
							path="birthCertificateDto.brBirthAddrMar" isMandatory="true"
							isReadonly="true" cssClass="hasNumClass form-control"
							maxlegnth="800">
						</apptags:input>
					</div>


					<h4>
						<spring:message code="BirthCertificateDTO.parentDetails"
							text="Parent Information" />
					</h4>

					<div class="form-group">
						<apptags:input labelCode="BirthCertificateDTO.pdFathername"
							path="birthCertificateDto.pdFathername" isReadonly="true"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="BirthCertificateDTO.pdFathernameMar"
							path="birthCertificateDto.pdFathernameMar" isReadonly="true"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="BirthCertificateDTO.pdMothername"
							path="birthCertificateDto.pdMothername" isReadonly="true"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350">
						</apptags:input>
						<apptags:input labelCode="BirthCertificateDTO.pdMothernameMar"
							path="birthCertificateDto.pdMothernameMar" isReadonly="true"
							isMandatory="true" cssClass="hasNameClass form-control"
							maxlegnth="350">
						</apptags:input>
					</div>


					<div class="form-group">
						<apptags:input
							labelCode="BirthCertificateDTO.pdParaddress"
							path="birthCertificateDto.pdParaddress" isReadonly="true"
							isMandatory="true" cssClass="hasNumClass form-control"
							maxlegnth="350">
						</apptags:input>
						<apptags:input
							labelCode="BirthCertificateDTO.pdParaddressMar"
							path="birthCertificateDto.pdParaddressMar" isReadonly="true"
							isMandatory="true" cssClass="hasNumClass form-control"
							maxlegnth="350">
						</apptags:input>
					</div>
					<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title table" id="">
										<a data-toggle="collapse" class="" href="#deathRegCor-1">
											<spring:message code="TbDeathregDTO.attacheddoc"
												text="Attached Documents" />
										</a>
									</h4>
								</div>


								<div class="col-sm-12 text-left">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="attachDocs">
											<tr>
												<th><spring:message code="scheme.document.name" text="" /></th>
												<th><spring:message code="scheme.view.document" text="" /></th>
											</tr>
											<c:forEach items="${command.fetchDocumentList}" var="lookUp">
												<tr>
													<td align="center">${lookUp.attFname}</td>
													<td align="center"><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="BirthCertificateApproval.html?Download">
														</apptags:filedownload></td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</div>

					<%-- <h4>
						<spring:message code="BirthCertificateDTO.parentAddressDetails"
							text="Certificate print details" />
					</h4> --%>

					<%-- <div class="form-group">
										<apptags:input
											labelCode="No Of Copies"
											path="birthCertificateDto.noOfCopies" isReadonly="true"
											isMandatory="true" cssClass="hasNumber form-control" maxlegnth="350">
										</apptags:input>
										<apptags:input
											labelCode="Amount"
											path="birthCertificateDto.amount" isReadonly="true"
											isMandatory="true" cssClass="hasNumClass form-control" maxlegnth="350">
										</apptags:input>
									</div> --%>


					<%-- <div class="form-group">


						<apptags:radio radioLabel="Approve,Reject"
							radioValue="APPROVED,REJECTED" isMandatory="true"
							labelCode="Status" path="birthCertificateDto.birthRegstatus"
							defaultCheckedValue="APPROVED">
						</apptags:radio>

						<apptags:textArea labelCode="Remark" isMandatory="true" path="birthCertificateDto.birthRegremark"
							cssClass="hasNumClass form-control" maxlegnth="100" />
					</div> --%>
					
					<div class="form-group">
					   <c:choose>
					       <c:when test="${CheckFinalApp eq true}">
					          <apptags:radio radioLabel="Approve,Reject" radioValue="APPROVED,REJECTED" isMandatory="true"
							  	labelCode="Status" path="birthCertificateDto.birthRegstatus" defaultCheckedValue="APPROVED" >
							  </apptags:radio>
					          <br/>
					       </c:when>    
				       	   <c:otherwise>
					          <apptags:radio radioLabel="Approve" radioValue="APPROVED" isMandatory="true"
								labelCode="Status" path="birthCertificateDto.birthRegstatus" defaultCheckedValue="APPROVED" >
							  </apptags:radio>
				        	  <br/>
				           </c:otherwise>
				       </c:choose>
							  <apptags:textArea
								labelCode="BirthCertificateDTO.birthRegremark" isMandatory="true"
								path="birthCertificateDto.birthRegremark" cssClass="hasNumClass form-control"
								maxlegnth="100" /> 
					</div>	
					
					<c:if test="${CheckFinalApp eq true}">
					<div class="form-group" id="reload">
						<label class="col-sm-2 control-label" for="ExcelFileUpload"><spring:message
								code="intranet.fileUpld" text="File Upload" /><i
							class="text-red-1">*</i></label>
						<c:set var="count" value="0" scope="page"></c:set>
						<div class="col-sm-2 text-left">
							<apptags:formField fieldType="7"
								fieldPath="attachments[${count}].uploadedDocumentPath"
								currentCount="${count}" folderName="${count}"
								fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
								isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="ALL_UPLOAD_VALID_EXTENSION" cssClass="clear">
							</apptags:formField>

						</div>

					</div>
				</c:if>
					
					

					<div class="text-center padding-top-10">

						<button type="button" class="btn btn-green-3" title="Submit"
							onclick="saveBirthApprovalData(this)">
							Save<i class="fa padding-left-4" aria-hidden="true"></i>
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>
