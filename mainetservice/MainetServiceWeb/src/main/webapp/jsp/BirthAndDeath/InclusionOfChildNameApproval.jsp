<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/birthAndDeath/InclusionOfChildNameApproval.js"></script>



<div class="pageDivContent">
<apptags:breadcrumb></apptags:breadcrumb>
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="Inclusion Of Child Name and Birth Certificate" code="BirthRegDto.inclAndBrCert"/>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form id="frmInclusionOfChildNameApproval"
				action="InclusionOfChildNameApproval.html" method="POST" 
				class="form-horizontal" name="InclusionOfChildNameApprovalId">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div id="RegisDetail">
					<h4>
						<spring:message text="Registration Details" code="Issuaance.birth.certificate.detail"/>
					</h4>

					<div class="form-group">
						<apptags:input labelCode="BirthRegDto.brChildName" isReadonly="true"
							path="tbBirthregcorrDTO.brChildName"
							cssClass="hasNameClass form-control" >
						</apptags:input>
						<apptags:input labelCode="BirthRegDto.brChildNameMar" isReadonly="true"
							path="tbBirthregcorrDTO.brChildNameMar"
							cssClass="hasNameClass form-control" >
						</apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="BirthRegDto.brSex"
							path="tbBirthregcorrDTO.brSex" cssClass="hasNameClass form-control"
							maxlegnth="12" isReadonly="true">
						</apptags:input>
						<apptags:date fieldclass="datepicker" labelCode="BirthRegDto.brDob"
							datePath="tbBirthregcorrDTO.brDob" readonly="true">
						</apptags:date>
					</div>
					<div class="form-group">
						<apptags:input
							labelCode="BirthRegDto.parentDetailDTO.pdFathername"
							path="birthRegDto.parentDetailDTO.pdFathername"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
						<apptags:input
							labelCode="BirthRegDto.parentDetailDTO.pdFathernameMar"
							path="birthRegDto.parentDetailDTO.pdFathernameMar"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input
							labelCode="BirthRegDto.parentDetailDTO.pdMothername"
							path="birthRegDto.parentDetailDTO.pdMothername"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
						<apptags:input
							labelCode="BirthRegDto.parentDetailDTO.pdMothernameMar"
							path="birthRegDto.parentDetailDTO.pdMothernameMar"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="BirthRegDto.brBirthPlace"
							path="tbBirthregcorrDTO.brBirthPlace"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegDto.brBirthPlaceMar"
							path="tbBirthregcorrDTO.brBirthPlaceMar"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="BirthRegDto.brBirthAddr"
							path="tbBirthregcorrDTO.brBirthAddr"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegDto.brBirthAddrMar"
							path="tbBirthregcorrDTO.brBirthAddrMar"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.permanent.parent.addr"
							path="tbBirthregcorrDTO.brBirthAddr"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
						<apptags:input
							labelCode="ParentDetailDTO.permanent.parent.addrMar"
							path="tbBirthregcorrDTO.brBirthAddrMar"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="BirthRegDto.BrParAddr.childBirthEng"
							path="tbBirthregcorrDTO.pdParaddress"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
						<apptags:input labelCode="BirthRegDto.BrParAddr.childBirthReg"
							path="tbBirthregcorrDTO.pdParaddressMar"
							cssClass="hasNameClass form-control" isReadonly="true">
						</apptags:input>
					</div>
					<div class="form-group">
					<apptags:input
							labelCode="BirthRegDto.brRegNo"
							path="tbBirthregcorrDTO.brRegNo"
						   cssClass="alphaNumeric" isReadonly="true">
							</apptags:input>
					</div>

						<c:if test="${not empty command.fetchDocumentList}">

						<div class="panel-group accordion-toggle">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a3"><spring:message
										code="mrm.upload.attachement" text="Upload Attachment" /></a>
							</h4>
							<div class="panel-collapse collapse in" id="a3">

								<div class="panel-body">

									<div class="overflow margin-top-10 margin-bottom-10">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped">
												<tbody>
													<tr>
														<th><label class="tbold"><spring:message
																	code="label.checklist.srno" text="Sr No" /></label></th>
														<th><label class="tbold"><spring:message
																	code="bnd.documentName" text="Document Type" /></label></th>
														<th><label class="tbold"><spring:message
																	code="bnd.documentDesc" text="Document Description" /></label></th>
														<th><spring:message code="birth.view.document"
																text="" /></th>
													</tr>

													<c:forEach items="${command.fetchDocumentList}" var="lookUp"
														varStatus="lk">
														<tr>
															<td><label>${lookUp.clmSrNo}</label></td>
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<td><label>${lookUp.clmDescEngl}</label></td>
																</c:when>
																<c:otherwise>
																	<td><label>${lookUp.clmDesc}</label></td>
																</c:otherwise>
															</c:choose>
															<td><label>${lookUp.docDescription}</label></td>
															<td align="center"><apptags:filedownload
																	filename="${lookUp.attFname}"
																	filePath="${lookUp.attPath}"
																	actionUrl="BirthRegistrationCorrectionApproval.html?Download">
																</apptags:filedownload></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>

					</c:if>
					
					<div class="form-group">
					<div>
					   <c:choose>
					       <c:when test="${CheckFinalApp eq true}">
					          <apptags:radio radioLabel="nac.approve,nac.reject" radioValue="APPROVED,REJECTED" isMandatory="true"
							  	labelCode="nac.status" path="tbBirthregcorrDTO.birthRegstatus" defaultCheckedValue="APPROVED" >
							  </apptags:radio>
					          <br/>
					       </c:when>    
				       	   <c:otherwise>
					          <apptags:radio radioLabel="nac.approve" radioValue="APPROVED" isMandatory="true"
								labelCode="nac.status" path="tbBirthregcorrDTO.birthRegstatus" defaultCheckedValue="APPROVED" >
							  </apptags:radio>
				        	  <br/>
				           </c:otherwise>
				       </c:choose>
				       </div>
							 <%--  <apptags:textArea
								labelCode="Remark" isMandatory="true"
								path="tbBirthregcorrDTO.birthRegremark" cssClass="hasNumClass form-control"
								maxlegnth="100" />  --%>
							<%-- <apptags:input
							labelCode="TbDeathregDTO.form.remark"
							path="tbBirthregcorrDTO.birthRegremark" maxlegnth="100"
							cssClass="hasNumClass form-control" >
							</apptags:input> --%>
					</div>	
					
					<div class="text-center">
						<button type="button" value="<spring:message code="bt.save"/>"
							class="btn btn-green-3" title="Submit"
							onclick="saveInclusionOfChildNameApprovalData(this)">
							<spring:message code="TbDeathregDTO.form.savebutton"/>
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>	
					
					
					
				</div>
			</form:form>
		</div>
	</div>
</div>






