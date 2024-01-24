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
<script type="text/javascript" src="js/additionalServices/seizedItemChallanEntrySummary.js"></script>
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
			
			<div class="widget">
				<div class="mand-label clearfix">
					<span><spring:message code="material.management.mand"
							text="Field with" /> <i class="text-red-1">*</i> <spring:message
							code="material.management.mand.field" text="is mandatory" /> </span>
				</div>
				<div class="widget-content padding">
					<form:form action="SeizedItemChallanEntry.html" commandName="command" method="POST" class="form-horizontal" name="seizedItemDetails"
					 id="seizedItemDetails" >
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span id="errorId"></span>
						</div>
						
						<h4>
							<spring:message code="EChallan.offendersInfo" text="Offenders Information" />
						</h4>
						<form:input type="hidden" path="removeItemIds" id="removeItemIds" />
						<!-- for seized items -->
						<div class="form-group forSeizedItems">
							<label for="text-1"
								class="col-sm-2 control-label "> <spring:message
									code="EChallan.refNo" text="Reference Number" />
							</label>
							<div class="col-sm-4">
						
								<form:input type="text" class="form-control hasNumber" maxlength="10" minlength="10"
									id="refNo" path="challanMasterDto.referenceNo"
									placeholder="Enter the Refernce Number" 
									disabled="true"/>
							
							</div>
							<label for="text-1"
								class="col-sm-2 control-label forSeizedItems"> <spring:message
									code="EChallan.emailId" text="Email-Id" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control hasemailclass forSeizedItems" id="emailId"
									path="challanMasterDto.offenderEmail"
									placeholder="Enter the Email-Id" 
									disabled="true"/>
							</div>
						</div>
						
						<div class="form-group">
					
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.fromArea" text="From Area" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control" id="challanFromArea"
									path="challanMasterDto.fromArea" placeholder="Enter from Area"
									disabled="true" />
							</div>
							
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.toArea" text="To Area" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control" id="challanToArea"
									path="challanMasterDto.toArea" placeholder="Enter to Area"
									disabled="true" />
							</div>
						</div>
						<div class="form-group">
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.offenderName" text="Offender Name" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control hasCharacter"
									id="offenderName" path="challanMasterDto.offenderName" 
									placeholder="Enter the Offender Name" 
									disabled="true"/>
							</div>
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.mobNo" text="Mobile Number" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control hasNumber" maxlength="10" minlength="10"
									id="offenderMobNo" path="challanMasterDto.offenderMobNo"
									placeholder="Enter the Mobile No"
									disabled="true" />
							</div>
						</div>
						<div class="form-group">
							
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.violationPurpose" text="Violation Purpose" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control" id="violationPurpose"
									path="challanMasterDto.challanDesc"
									placeholder="Enter the Purpose" 
									disabled="true"/>
							</div>
						</div>

						<div class="form-group">
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.uploadFile" text="Upload File" />
							</label>
							<div class="col-sm-4">
								<%-- <form:input class="form-control" type="file" id="formFile" path="challanMasterDto.evidenceImg"
								disabled="${command.saveMode eq 'V' ? true : false }" /> --%>
								
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
								<form:input type="text" class="form-control hasCharacter" id="officerAvailableOnsite" 
								path="challanMasterDto.officerOnsite" placeholder="Enter Officer On-Site" 
								disabled="true"/>
							</div>
						</div>
						
						<h4 id="itemTabletitle">
							<spring:message code="EChallan.seizedItemDetails" text="Seized Item Details" />
						</h4>
						<div class="form-group">
							<label for="text-1" class="col-sm-2 control-label "> <spring:message
									code="EChallan.raidNumber" text="Raid Number" />
							</label>
							<div class="col-sm-4 margin-top-5">
								<form:input type="text" class="form-control" name="raidNo"
									id="raidNo" path="challanMasterDto.raidNo" value=""
									disabled="true" />
							</div>
							
							<label for="text-1"
								class="col-sm-2 control-label"> <spring:message
									code="EChallan.raidDate" text="Raid Date" />
							</label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input type="text"
										class="form-control  mandColorClass hideElement"
										id="raidDate" path="challanMasterDto.datechallan"
										disabled="true" />
									<label class="input-group-addon" for="challanDate"><i
										class="fa fa-calendar"></i></label>
								</div>
							</div>
						</div>

						<!-- Challan Details Table Start -->
						<h4 id="itemTabletitle">
							<spring:message code="EChallan.seizedItemList" text="Seized Item List" />
						</h4>
						
						<div class="table-responsive">
						<table class="table table-bordered table-striped itemTable" id="itemTable">
							<thead>
								<tr>
									<th class="text-center required-control"><spring:message
											code="EChallan.itemName" text="Item Name" /></th>
									<th class="text-center"><spring:message
											code="EChallan.itemDesc" text="Item Description" /></th>
									<th class="text-center required-control"><spring:message
											code="EChallan.quantity" text="Item Quantity" /></th>
									<th class="text-center required-control"><spring:message
											code="EChallan.storageLocation" text="Storage Location" /></th>
									<%-- <th class="text-center"><spring:message
											code="EChallan.amount" text="Amount" /></th> --%>
									<c:if test="${command.saveMode eq 'E'}">
									<th class="text-center"><spring:message
											code="EChallan.action" text="Action" /></th>
									</c:if>
								</tr>
							</thead>
							<tbody>
			 				<form:hidden path="challanMasterDto.challanId" id="challanId" />
			 				<c:set var="e" value="0" scope="page" />
									<c:forEach var="det" items="${command.challanMasterDto.echallanItemDetDto}" varStatus="status">
								<c:if test="${command.challanMasterDto.echallanItemDetDto ne 'Y'}">
								<tr id="firstItemRow" class="firstItemRow">
									<%-- <form:hidden path="challanMasterDto.echallanItemDetDto[${e}].itemId" id="itemId${e}" /> --%>
									<td width="20%">
									<form:select
										path="challanMasterDto.echallanItemDetDto[${e}].itemName"
										cssClass="form-control mandColorClass required-control"
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
										class="form-control" name="itemDesc${e}"
										id="itemDesc${e}" path="challanMasterDto.echallanItemDetDto[${e}].itemDesc" 
										placeholder="${enterItemDesc}" disabled="${command.saveMode eq 'V' ? true : false }" />
									</td>
									
									<td width="20%">
									<spring:message code="EChallan.enter.itemQuantity" text="Enter the Quantity" var="enterItemQuantity" />
									<form:input type="number"
										class="form-control required-control" name="itemQuantity${e}"
										id="itemQuantity${e}" path="challanMasterDto.echallanItemDetDto[${e}].itemQuantity" 
										placeholder="${enterItemQuantity}" 
										disabled="${command.saveMode eq 'V' ? true : false }"/></td>
										
									<td width="20%">
									<spring:message code="EChallan.enter.storeLocation" text="Enter the Storage Location" var="enterStoreLocation" />
									<form:input type="text"
										class="form-control required-control" name="storeId${e}"
										id="storeId${e}" path="challanMasterDto.echallanItemDetDto[${e}].storeId" 
										placeholder="${enterStoreLocation}" 
										disabled="${command.saveMode eq 'V' ? true : false }"/></td>
										
									<%-- <td width="20%"><form:input type="text"
										class="form-control" name="challanAmt${e}"
										id="challanAmt${e}" path="" 
										disabled="${command.saveMode eq 'V' ? true : false }"/></td> --%>
										
									<c:if test="${command.saveMode eq 'E'}">
									<td class="text-center" width="10%">
										<a  data-placement="top" title="<spring:message code="EChallan.add" text="Add" />"
											id="addrow0" class="addButton btn btn-success btn-sm"
											data-original-title="Add Row" onclick="addRow();">
											<i class="fa fa-plus-circle" aria-hidden="true"></i></a>
											
										<%-- <a  data-placement="top" title="<spring:message code="EChallan.delete" text="Delete" />"
											class="experienceDeleteRow btn btn-danger btn-sm" id="deleteItemDetRow"
											 data-original-title="Remove Row" href="javascript:void(0);"
											onclick="removeRow(0);">
											<i class="fa fa-trash-o" aria-hidden="true"></i>
										</a> --%>
										<a href="javascript:void(0);"
										   class="deleteItemDetRow btn btn-danger btn-sm delete"
										   id="deleteItemDetRow"><i class="fa fa-minus-circle"></i></a>
									</td>
									</c:if>			
								</tr>
								</c:if>
								<c:set var="e" value="${e + 1}"  />
								</c:forEach>
							</tbody>
						</table>
						</div>
						<!-- Challan Details Table Ends -->

						<c:if test="${command.saveMode eq 'E'}">
							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="EChallan.remark" /></label>
								<div class="col-sm-10">
									<form:textarea class="form-control"
										path="challanMasterDto.remark"></form:textarea>
								</div>
							</div>
						</c:if>

						<div class="text-center margin-top-25">
							<c:if test="${command.saveMode eq 'E'}">
								<button type="button" class="btn btn-success"
									title="<spring:message code="EChallan.save" text="Save" />"
									onclick="proceedSave(this);">
									<!-- <i class="fa fa-search padding-right-5" aria-hidden="true"></i> -->
									<spring:message code="EChallan.save" text="Save" />
								</button>
							</c:if>

							<button type="button" class="btn btn-danger"
								title="<spring:message code="EChallan.back" text="Back" />"
								onclick="window.location.href='SeizedItemChallanEntry.html'">
								<i class="fa fa-arrow-left padding-right-5" aria-hidden="true"></i>
								<spring:message code="EChallan.back" text="Back" />
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>

	</div>