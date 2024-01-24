<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/additionalServices/eChallanEntryForm.js"></script>
<script type="text/javascript" src="js/cfc/challan/offlinePay.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">
					<spring:message code="EChallan.help" text="Help" />
			</span>
			</a>
		</div>

		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="EChallan.challanEntry" text="Challan Entry" />
				</h2>
			</div>
			<div class="widget">
				<div class="mand-label clearfix">
					<span><spring:message code="material.management.mand"
							text="Field with" /> <i class="text-red-1">*</i> <spring:message
							code="material.management.mand.field" text="is mandatory" /> </span>
				</div>
				<div class="widget-content padding">
					<form:form action="EChallanEntry.html" commandName="command" method="POST" class="form-horizontal" name="echallanEntry"
					 id="echallanEntry" >
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span id="errorId"></span>
						</div>
						
						<div class="form-group">
							<label for="text-1" class="col-sm-2 control-label required-control" id="challanType"> 
							<spring:message code="EChallan.challanType" text="Challan Type" />
							</label>
							<div class="col-sm-4 margin-top-5">
								<label for="challantype" class="radio-inline"> 				
									 <form:radiobutton class="radio-group margin-right-5 challanType" onclick="payModefields();" 
									 path="challanMasterDto.challanType" id="challanType" value="OS" />
									<spring:message code="EChallan.onSpot" text="On Spot" />
								</label> 
								<label for="challantype" class="radio-inline"> 								
								<form:radiobutton class="radio-group challanType" onclick="payModefields();"
								    path="challanMasterDto.challanType" id="challanType" value="AS"  
								    disabled="${command.saveMode eq 'V' ? true : false }"/>
									<spring:message code="EChallan.againstSeizedItems" text="Against Seized Items" /> 
								</label>
							</div>
						</div>
						
						<h4>
							<spring:message code="EChallan.challanEntryForm"
								text="Challan Entry Form" />
						</h4>
						
						<div class="form-group">
							
							<label for="text-1"
								class="col-sm-2 control-label "> <spring:message
									code="EChallan.challanDate" text="Challan Date" />
							</label>
							<c:set var="now" value="<%=new java.util.Date()%>" />
							<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
							<div class="col-sm-4">
								<form:input path="challanMasterDto.challanDate" id="challanDate"
									cssClass="mandColorClass form-control datepicker"
									data-rule-required="true" value="${date}" disabled="true"
									maxlength="10"></form:input>
							</div>
						</div>
						
						<!-- for seized items -->
						<div class="form-group forSeizedItems">
							<label for="text-1"
								class="col-sm-2 control-label "> <spring:message
									code="EChallan.refNo" text="Reference Number" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.referenceNo" text="Enter the Refernce Number" var="enterReferenceNo" />
								<form:input type="text" class="form-control hasNumber" maxlength="10" minlength="10"
									id="refNo" path="challanMasterDto.referenceNo"
									placeholder="${enterReferenceNo}" 
									disabled="${command.saveMode eq 'V' ? true : false }"/>
							
							</div>
							<label for="text-1"
								class="col-sm-2 control-label forSeizedItems"> <spring:message
									code="EChallan.emailId" text="Email-Id" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.offenderEmail" text="Enter the Email-Id" var="enterOffenderEmail" />
								<form:input type="text" class="form-control hasemailclass forSeizedItems" id="emailId"
									path="challanMasterDto.offenderEmail" data-rule-email="true"
									placeholder="${enterOffenderEmail}" 
									disabled="${command.saveMode eq 'V' ? true : false }"/>
							</div>
						</div>
						
						<div class="form-group">
					
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.fromArea" text="From Area" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.fromArea" text="Enter from Area" var="enterFromArea" />
								<form:input type="text" class="form-control" id="challanFromArea" maxlength="50"
									path="challanMasterDto.fromArea" placeholder="${enterFromArea}"
									disabled="${command.saveMode eq 'V' ? true : false }"/>
							</div>
							
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.toArea" text="To Area" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.toArea" text="Enter to Area" var="enterToArea" />
								<form:input type="text" class="form-control" id="challanToArea" maxlength="50"
									path="challanMasterDto.toArea" placeholder="${enterToArea}"
									disabled="${command.saveMode eq 'V' ? true : false }" />
							</div>
						</div>
						<div class="form-group">
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.offenderName" text="Offender Name" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.offenderName" text="Enter the Offender Name" var="enterOffenderName" />
								<form:input type="text" class="form-control hasCharacter" maxlength="50"
									id="offenderName" path="challanMasterDto.offenderName" 
									placeholder="${enterOffenderName}" 
									disabled="${command.saveMode eq 'V' ? true : false }"/>
							</div>
							<label for="text-1"
								class="col-sm-2 control-label required-control"><spring:message
									code="EChallan.mobNo" text="Mobile Number" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.offenderMobNo" text="Enter the Mobile No" var="enterOffenderMobNo" />
								<form:input type="text" class="form-control hasMobileNo" maxlength="10" minlength="10"
									id="offenderMobNo" path="challanMasterDto.offenderMobNo"
									placeholder="${enterOffenderMobNo}"
									disabled="${command.saveMode eq 'V' ? true : false }" />
							</div>
						</div>
						<div class="form-group">
							
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.violationPurpose" text="Violation Purpose" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.challanDesc" text="Enter the Violation Purpose" var="enterChallanDesc" />
								<%-- <form:input type="text" class="form-control" id="violationPurpose"
									path="challanMasterDto.challanDesc"
									placeholder="${enterChallanDesc}" 
									disabled="${command.saveMode eq 'V' ? true : false }"/> --%>
								<form:textarea name="" cols="" rows="" class="form-control" id="violationPurpose"
										path="challanMasterDto.challanDesc" maxlength="100"
										disabled="${command.saveMode eq 'V' ? true : false }" />
							</div>
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.locality" text="Locality" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.locality" text="Enter the Locality" var="enterLocality" />
								<form:input type="text" class="form-control" id="locality" maxlength="50"
									path="challanMasterDto.locality"
									placeholder="${enterLocality}" 
									disabled="${command.saveMode eq 'V' ? true : false }"/>
							</div>
						</div>

						<div class="form-group">
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.uploadFile" text="Upload File" />
							</label>
							<%-- <div class="col-sm-4">
								<form:input class="form-control" type="file" id="formFile" path="challanMasterDto.evidenceImg"
								disabled="${command.saveMode eq 'V' ? true : false }" />
							</div> --%>
							<div class="col-sm-4">
							
								<c:if test="${command.saveMode ne 'V'}">
									<apptags:formField fieldType="7" fieldPath="" currentCount="0"
										showFileNameHTMLId="true" folderName="0"
										fileSize="ECHALLAN_MAX_SIZE" isMandatory="true"
										maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
										validnFunction="CHECK_LIST_VALIDATION_EXTENSION_ECHALLAN">
									</apptags:formField> -
			 					<small class="text-blue-2"> <spring:message
											text="(Upload file of png, jpeg, pdf, avi, mp4, mpeg, 3gp, wmv, flv, mkv, webm upto 50MB)" 
											code="EChallan.uploadformat" />

									</small>
								</c:if>

								<c:if test="${not empty command.documentList }">
									<c:forEach items="${command.documentList}" var="lookUp">
										<div>
										<apptags:filedownload filename="${lookUp.attFname}"
											filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}"
											actionUrl="EChallanEntry.html?Download" />
										</div>
									</c:forEach>
								</c:if>
							</div>
							
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.officerAvailableOnsite"
									text="Officer Available On-Site" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.officerOnsite" text="Enter Officer On-Site" var="enterOfficerOnsite" />
								<form:input type="text" class="form-control hasCharacter"
									id="officerAvailableOnsite" maxlength="50"
									path="challanMasterDto.officerOnsite"
									placeholder="${enterOfficerOnsite}"
									disabled="${command.saveMode eq 'V' ? true : false }" />
							</div>
						</div>

						<!-- Challan Details Table Start -->
						<c:if test="${command.saveMode ne 'V'}">
						<h4 id="itemTabletitle">
							<spring:message code="EChallan.seizedItemList" text="Seized Item List" />
						</h4>
						
						<div class="table-responsive">
						<table class="table table-bordered margin-bottom-10 itemTable" id="itemTable">
							<thead>
								<tr>
									<th class="text-center required-control"><spring:message
											code="EChallan.itemName" text="Item Name" /></th>
									<th class="text-center"><spring:message
											code="EChallan.itemDesc" text="Item Description" /></th>
									<th class="text-center required-control"><spring:message
											code="EChallan.quantity" text="Item Quantity" /></th>
									<th class="text-center required-control store"><spring:message
											code="EChallan.storageLocation" text="Storage Location" /></th>
									<th class="text-center required-control challanAmt"><spring:message
											code="EChallan.amount" text="Amount" /></th>		
									<th class="text-center"><spring:message
											code="EChallan.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:set var="e" value="0" scope="page" />
								<tr id="firstItemRow" class="firstRow">
									
									<td width="20%">
									<form:select
										path="challanItemDetailsDtoList[${e}].itemName"
										cssClass="form-control mandColorClass" data-rule-required="true" onchange="getOtherItemAmount();"
										id="itemName${e}" disabled="${command.saveMode eq 'V' ? true : false }"> 
										<c:set var="baseLookupCode" value="EIC" />
      									<form:option value="">
											<spring:message code='master.selectDropDwn' />
										</form:option>
      									<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpDesc}"
												  code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}
											</form:option>
										</c:forEach>
									</form:select>
									</td>
									
									<td width="20%">
									<spring:message code="EChallan.enter.itemDesc" text="Enter item description" var="enterItemDesc" />
									<form:textarea
										class="form-control" name="itemDesc${e}" maxlength="500"
										id="itemDesc${e}" path="challanItemDetailsDtoList[${e}].itemDesc" 
										placeholder="${enterItemDesc}" disabled="${command.saveMode eq 'V' ? true : false }" />
									</td>
									
									<td width="20%">
									<spring:message code="EChallan.enter.itemQuantity" text="Enter the Quantity" var="enterItemQuantity" />
									<form:input data-rule-required="true" maxlength="10"
										class="form-control required-control hasNumber" name="itemQuantity${e}"
										id="itemQuantity${e}" path="challanItemDetailsDtoList[${e}].itemQuantity" 
										placeholder="${enterItemQuantity}" disabled="${command.saveMode eq 'V' ? true : false }" onblur="getBrmsCharges(this)" />
									</td>
										
									<td width="20%" class="store">
									<spring:message code="EChallan.enter.storeLocation" text="Enter the Storage Location" var="enterStoreLocation" />
									<form:input type="text" data-rule-required="true"
										class="form-control required-control" name="storeId${e}"
										id="storeId${e}" path="challanItemDetailsDtoList[${e}].storeId" 
										placeholder="${enterStoreLocation}" disabled="${command.saveMode eq 'V' ? true : false }" />
									</td>
									
									<td width="20%" class="challanAmt">
									<spring:message code="EChallan.enter.amount" text="Enter the amount" var="enterAmount" />
									<form:input type="text" data-rule-required="true" maxlength="10"
										class="form-control required-control hasNumber" name="challanAmt${e}"
										id="challanAmt${e}" path="challanItemDetailsDtoList[${e}].itemAmount" 
										onblur="addTotalAmount(this);"
										placeholder="${enterAmount}" disabled="${command.saveMode eq 'V' ? true : false }"/>
									</td>
									
									<td class="text-center" width="10%">
										<a  data-placement="top" title="<spring:message code="EChallan.add" text="Add" />"
											id="addrow0" class="addButton btn btn-success btn-sm"
											data-original-title="Add Row" onclick="addRow();">
											<i class="fa fa-plus-circle" aria-hidden="true"></i></a>
											
										<a  data-placement="top" title="<spring:message code="EChallan.delete" text="Delete" />"
											class="experienceDeleteRow btn btn-danger btn-sm" id="removeRow0"
											 data-original-title="Remove Row"
											onclick="removeRow(0);">
											<i class="fa fa-trash-o" aria-hidden="true"></i>
										</a>
									</td>

								</tr>
							</tbody>
						</table>
						</div>
						</c:if>

							<!-- to fetch item table data  -->
							<c:if test="${command.saveMode eq 'V'}">
							<c:if test="${not empty  command.challanMasterDto.echallanItemDetDto}">
								<h4 class="panel-title table margin-top-10" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a2"> <spring:message
											code="EChallan.itemDetails" text="Item Details" />
									</a>
								</h4>
								<div id="a2" class="panel-collapse collapse in">
									<table id="CollectionDetails"
										class="table table-striped table-bordered margin-top-10">
										<thead>
										<tr>
											<th class="text-center"><spring:message code="EChallan.itemNo"
													text="Item Number" /></th>
											<th class="text-center"><spring:message code="EChallan.itemName"
													text="Item Name" /></th>
											<th class="text-center"><spring:message code="EChallan.quantity"
													text="Quantity" /></th>
											<th class="text-center"><spring:message code="EChallan.amount" 
											        text="Amount" /></th>
										</tr>
										</thead>
										<tbody>
											<c:forEach
												items="${command.challanMasterDto.echallanItemDetDto}"
												var="data" varStatus="loop">
												<tr>
													<td class="text-center">${data.itemNo}</td>
													<td class="text-center">${data.itemDesc}</td>
													<td class="text-center">${data.itemQuantity}</td>
													<td class="text-center">${data.itemAmount}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</c:if>
							</c:if>
						<div class=" form-group">
							<p class="text-red col-sm-6" for="text-1">
								<spring:message code="EChallan.note.noteDesc"
									text="Note: In case of Polythene Bag the item quantity should be entered in grams." />
							</p>
						</div>
						<br />

						<div class="form-group">
							<label for="text-1"
								class="col-sm-2 control-label challanAmt required-control"> <spring:message
									code="EChallan.totalAmount" text="Total Amount" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.totalAmt" text="Enter the total amount" var="enterTotalAmt" />
								<form:input type="text" class="form-control hasNumber challanAmt" id="challanTotalAmt"
									path="challanMasterDto.challanAmt" value=""
									placeholder="${enterTotalAmt}" maxlength="7" disabled="${command.saveMode eq 'V' ? true : false }" readonly="true"/>
							</div>
						</div>
						
						<c:if test="${command.saveMode ne 'V'}">
							<div class="panel panel-default" id="chnPay">
								<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
							</div>
						</c:if>

						<div class="text-center margin-top-25">
							<c:if test="${command.saveMode ne 'V'}">
								<button type="button" class="btn btn-success"
									onclick="proceedSave(this);" title="save">
									<!-- <i class="fa fa-search padding-right-5" aria-hidden="true"></i> -->
									<spring:message code="EChallan.save" text="Save" />
								</button>
							</c:if>

							<c:if test="${command.saveMode ne 'V'}">
								<button type="button" class="btn btn-warning"
									title="<spring:message code="EChallan.reset" text="Reset" />"
									onclick="resetForm();">
									<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
									<spring:message code="EChallan.reset" text="Reset" />
								</button>
								<%-- <apptags:resetButton cssClass="btn btn-warning" buttonLabelText="EChallan.reset" /> --%>
							</c:if>
				
							<button type="button" class="btn btn-danger"
								title="<spring:message code="EChallan.back" text="Back" />"
								onclick="backButton();">
								<i class="fa fa-arrow-left padding-right-5" aria-hidden="true"></i>
								<spring:message code="EChallan.back" text="Back" />
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>

	</div>