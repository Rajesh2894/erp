
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/cfc/scrutiny.js"></script>
<script type="text/javascript">
function saveLicenseDetails(obj) {
	var errorList=[];
	if (errorList == ''){
	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest('LandDetails.html?saveLicenseDetails',
			'POST', requestData, false);
	showConfirmBoxFoLoiPayment(returnData);
	}else{
		displayErrorsOnPage(errorList);
	}
}

function showConfirmBoxFoLoiPayment(sucessMsg) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("bt.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ sucessMsg.command.message + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="showScrutinyLabel()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function showScrutinyLabel() {
	$.fancybox.close();
	backToApplicationForm($(
	'#appId').val(),$('#taskId').val(),'ApplicationAuthorization.html',$('#serviceId').val(),$('#workflowId').val());
}

</script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lincense.landDetails" text="Land Details" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<form:form action="LandDetails.html" class="form-horizontal form"
				name="frmSiteAff" id="frmSiteAff">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
                <form:hidden path="fieldFlag" id="fieldFlag" />
                <form:hidden path="scrnFlag" id="scrnFlag" />
				<input type="hidden" id="taskId"
					value="${userSession.scrutinyCommonParamMap.taskId}" />
				<input type="hidden" id="workflowId"
					value="${userSession.scrutinyCommonParamMap.workflowId}" />
				<input type="hidden" id="serviceId"
					value="${userSession.scrutinyCommonParamMap.SM_SERVICE_ID}" />
				<input type="hidden" id="appId"
					value="${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID}" />
				<input type="hidden" id="landDetailsMap"
					value="${command.landDetailsMap.size()}" />
				<!-- ------------------------------------------------------- -->
				
				<div class="overflow margin-top-10">
											<div class="table-responsive">
											<c:set var="d" value="0" scope="page"></c:set>
												<table id="acqDetTable"								
													class="table table-striped table-bordered table-wrapper table-responsive">
													<thead>
				
														<tr>
															<th class="text-center" width="20%"><spring:message code="" text="District" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Development Plan" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Zone" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Sector" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Tehsil " /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Revenue Estate" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Hadbast No." /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Rectangle No." /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Khasra No." /></th>
															<th class="text-center" ><spring:message code="" text="Min" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of Land Owner" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Type of land" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Change in information" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Rectangle No./Mustil(Changed)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Khasra Number(Changed)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of the Land Owner as per
																Mutation/Jamabandi" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Whether Khasra been developed in
																collaboration" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of the developer company" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Date of registering
																collaboration agreement" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Whether collaboration agreement
																irrevocable (Yes/No)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of authorized signatory on
																behalf of land owner(s)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Name of authorized signatory on
																behalf of developer" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Registering Authority" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Collaboration Document" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="SPA/GPA/Board Resolution on
																behalf of land owner" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="SPA/GPA/Board Resolution on
																behalf of developer" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Collaborator Agreement Document" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Consolidation Type" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Kanal" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Marla" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Sarsai" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Bigha" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Biswa" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Biswansi" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Acquisition Status" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Consolidated Area(In Acres)" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Non-Consolidated Area(In Acres)" /></th> 
															<c:if test="${command.scrnFlag eq 'F' }">
															<th class="text-center" width="10%"><spring:message code="" text="Decision" /></th>
															<th class="text-center" width="10%"><spring:message code="" text="Remark" /></th> 
															</c:if>
														</tr>
													</thead>
															<tbody>
																<c:forEach var="dataList"
																	items="${command.licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList}"
																	varStatus="status">
																	<tr class="acqAppendClass">
				
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].district"
																				class="form-control hasNameClass valid"
																				id="district${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devPlan"
																				class="form-control hasNameClass valid" id="devPlan${d}"
																				readonly="true" /></td>
																		<td class="text-center form-cell" width="40%"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].zone"
																				class="form-control hasNameClass valid" id="zone${d}"
																				readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sector"
																				class="form-control hasNameClass valid" id="sector${d}"
																				readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].thesil"
																				class="form-control hasNameClass valid" id="thesil${d}"
																				readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].revEstate"
																				class="form-control hasNameClass valid"
																				id="revEstate${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].hadbastNo"
																				class="form-control hasNameClass valid"
																				id="hadbastNo${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].rectangleNo"
																				class="form-control hasNameClass valid"
																				id="rectangleNo${d}" readonly="true" /></td disabled="true"class="text-center">
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasraNo"
																				class="form-control hasNameClass valid"
																				id="khasraNo${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].min"
																				class="form-control hasNameClass valid"
																				id="minTable${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
															              path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerName"
															               class="form-control hasNameClass valid" id="landOwnerName${d}"
															               readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
															              path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landType"
															              class="form-control hasNameClass valid" id="landType${d}"
															              readonly="true" /></td>
															            <td class="text-center form-cell"><form:input
															               path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].chInfo"
															               class="form-control hasNameClass valid" id="chInfo${d}"
															                readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].mustilCh"
																				class="form-control hasNameClass valid"
																				id="mustilCh${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].khasaraCh"
																				class="form-control hasNameClass valid"
																				id="khasaraCh${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].landOwnerMUJAM"
																				class="form-control hasNameClass valid"
																				id="landOwnerMUJAM${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devColab"
																				class="form-control hasNameClass valid"
																				id="devColab${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].devCompName"
																				class="form-control hasNameClass valid"
																				id="devCompName${d}" readonly="true"
																				 /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDate"
																				class="form-control hasNameClass valid"
																				id="collabAgrDate${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrRev"
																				class="form-control hasNameClass valid"
																				id="collabAgrRev${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignLO"
																				class="form-control hasNameClass valid"
																				id="authSignLO${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].authSignDev"
																				class="form-control hasNameClass valid"
																				id="authSignDev${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].regAuth"
																				class="form-control hasNameClass valid" id="regAuth${d}"
																				readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabDec"
																				class="form-control hasNameClass valid"
																				id="collabDec${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brLO"
																				class="form-control hasNameClass valid" id="brLO${d}"
																				readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].brDev"
																				class="form-control hasNameClass valid" id="brDev${d}"
																				readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].collabAgrDoc"
																				class="form-control hasNameClass valid"
																				id="collabAgrDoc${d}" readonly="true" /></td>
																		<td class="text-center form-cell"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolType"
																				class="form-control hasNameClass valid"
																				id="consolType${d}" readonly="true" /></td> 
																		<td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].kanal"
																				class="form-control hasNameClass valid" id="kanal${d}"
																				readonly="true" /></td>
																		<td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].marla"
																				class="form-control hasNameClass valid" id="marla${d}"
																				readonly="true" /></td>
																		<td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].sarsai"
																				class="form-control hasNameClass valid" id="sarsai${d}"
																				readonly="true" /></td>
																		<td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].bigha"
																				class="form-control hasNameClass valid" id="bigha${d}"
																				readonly="true" /></td>
																		<td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswa"
																				class="form-control hasNameClass valid" id="biswa${d}"
																				readonly="true" /></td>
																		<td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].biswansi"
																				class="form-control hasNameClass valid"
																				id="biswansi${d}" readonly="true" /></td>
																		<td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].acqStat"
																				class="form-control hasNameClass valid" id="acqStat${d}"
																				readonly="true" /></td>
																		<td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].consolTotArea"
																				class="form-control hasNameClass valid consolTotArea"
																				id="consolTotArea${d}" onchange="calculateTotalArea()"
																				readonly="true" /></td>
																		<td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].nonConsolTotArea"
																				class="form-control hasNameClass valid nonConsolTotArea"
																				id="nonConsolTotArea${d}"
																				onchange="calculateTotalArea()" readonly="true" /></td>
																		<c:if test="${command.scrnFlag eq 'F' }">
											                           <td><form:select
												                    	path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].decision"
													                    id="decision${d}" cssClass="form-control">

													                   <c:set var="baseLookupCode" value="DEC" />
													                     <form:option value="">
														                   <spring:message code="siteinspection.vldn.answer" />
													                     </form:option>
													                    <c:forEach items="${command.getLevelData(baseLookupCode)}"
													                       	var="lookUp">
														                   <form:option value="${lookUp.lookUpDesc}"
														                	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													                     </c:forEach>
												                      </form:select></td>

											                             <td class="text-center"><form:input
																				path="licenseApplicationMasterDTO.licenseApplicationLandAcquisitionDetDTOList[${d}].resolutionComments"
																				class="form-control"
																				id="resolutionComments${d}" /></td>	
																		</c:if>
																	</tr>
																	<c:set var="d" value="${d + 1}" scope="page" />
																</c:forEach>
															</tbody>
														<%-- </c:otherwise>
													</c:choose> --%>
												</table>
											</div>
										</div>
										
				
				
				
				<!-- ------------------------------------------------------------- -->
				<br>
				<c:if test="${command.scrnFlag ne 'F' }">
				<div class="form-group">
						<apptags:input labelCode="Remark"
							path="landRmark" isMandatory="true"
							cssClass="hasNameClass form-control" maxlegnth="500">
						</apptags:input>	
							
					</div>
				
					
				<h4>
				<spring:message code="professional.uploaded.document" text="Uploaded Document" />
			</h4>
				<div class="container">
					<table class="table table-bordered table-striped" id="tbl1">
						<tr>
							<th width="20%" align="center"><spring:message
									code="sr.no" /></th>
							<th width="50%" align="center"><spring:message
									code="field.name" text="Field Name" /></th>
							<th width="30%" align="center"><spring:message
									code="file.upload" text="File Upload" /></th>
						</tr>
						<tr id="sUpload1">
							<td class="text-center">1</td>
							<td><spring:message
									 text="Shajra Plan (.pdf)" /></td>
							<td><apptags:formField fieldType="7" labelCode=""
									hasId="true" folderName="0" maxFileCount="CHECK_LIST_MAX_COUNT"
									fieldPath=""
									fileSize="COMMOM_MAX_SIZE"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
									isMandatory="false" showFileNameHTMLId="true" currentCount="0" />
							</td>
						</tr>
						
					</table>
				</div>
				</c:if>
				
				<div class="text-center padding-top-10">
				<c:if test="${command.scrnFlag eq 'F' }">
				<button type="button" class="btn btn-success btn-submit"
						onclick="saveLicenseDetails(this)">
						<spring:message code="professional.submit" />
					</button>
				</c:if>
				<c:if test="${command.scrnFlag ne 'F' }">
				
					<button type="button" class="btn btn-success btn-submit"
						onclick="saveDocument(this)">
						<spring:message code="professional.submit" />
					</button>
					</c:if>
					<input type="button" class="btn btn-danger"
						onclick="backToApplicationForm(${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID},${userSession.scrutinyCommonParamMap.taskId},'ApplicationAuthorization.html',${userSession.scrutinyCommonParamMap.SM_SERVICE_ID},${userSession.scrutinyCommonParamMap.workflowId})"
						value="<spring:message code="back.button" />">
				</div>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

