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
<script type="text/javascript" src="js/additionalServices/echallanPayment.js"></script>
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
					<form:form action="EChallanPayment.html" commandName="command" method="POST" class="form-horizontal" name="echallanEntry"
					 id="echallanPaymentDetails" >
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span id="errorId"></span>
						</div>
						<form:hidden path="saveMode" id="saveMode" />
						<h4>
							<spring:message code="EChallan.offendersInfo" text="Offenders Information" />
						</h4>
						
						<!-- for seized items -->
						<div class="form-group forSeizedItems">
							<label for="text-1"
								class="col-sm-2 control-label "> <spring:message
									code="EChallan.refNo" text="Reference Number" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.refNo" text="Enter Reference No." var="enterRefNo" />
								<form:input type="text" class="form-control hasNumber" maxlength="10" minlength="10"
									id="refNo" path="challanMasterDto.referenceNo"
									disabled="true" placeholder="${enterRefNo}"/>
							
							</div>
							<label for="text-1"
								class="col-sm-2 control-label forSeizedItems"> <spring:message
									code="EChallan.emailId" text="Email-Id" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.emailId" text="Enter Email Id" var="enterEmailId" />
								<form:input type="text" class="form-control hasemailclass forSeizedItems" id="emailId"
									path="challanMasterDto.offenderEmail"
									disabled="true" placeholder="${enterEmailId}" />
							</div>
						</div>
						
						<div class="form-group">
					
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.fromArea" text="From Area" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control" id="challanFromArea"
									path="challanMasterDto.fromArea" placeholder="Enter from Area" disabled="true" />
							</div>
							
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.toArea" text="To Area" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control" id="challanToArea"
									path="challanMasterDto.toArea" placeholder="Enter to Area" disabled="true" />
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
									placeholder="Enter the Offender Name" disabled="true" />
							</div>
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.mobNo" text="Mobile Number" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control hasNumber" maxlength="10" minlength="10"
									id="offenderMobNo" path="challanMasterDto.offenderMobNo"
									placeholder="Enter the Mobile No" disabled="true" />
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
									placeholder="Enter the Purpose" disabled="true" />
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
									<th class="text-center"><spring:message
											code="EChallan.itemNo" text="Item No." /></th>
									<th class="text-center"><spring:message
											code="EChallan.itemName" text="Item Name" /></th>
									<th class="text-center"><spring:message
											code="EChallan.itemDesc" text="Item Description" /></th>
									<th class="text-center"><spring:message
											code="EChallan.quantity" text="Item Quantity" /></th>
									<th class="text-center"><spring:message
											code="EChallan.storageLocation" text="Storage Location" /></th>
									<th class="text-center"><spring:message
											code="EChallan.amount" text="Amount" /></th>
								</tr>
							</thead>
							<tbody>
							
			 				<c:set var="e" value="0" scope="page" />
			 				<c:forEach var="det" items="${command.challanMasterDto.echallanItemDetDto}" varStatus="status">
								<tr id="firstItemRow" class="firstItemRow" >
									
									<td width="10%"><form:input type="text"
										class="form-control required-control" name="itemNo${e}" disabled="true"
										id="itemNo${e}" path="challanMasterDto.echallanItemDetDto[${e}].itemNo" /></td>
									
									<td width="20%">
									<form:select
										path="challanMasterDto.echallanItemDetDto[${e}].itemName"
										cssClass="form-control mandColorClass" disabled="true"
										id="itemName${e}">
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
										class="form-control" name="itemDesc${e}" disabled="true"
										id="itemDesc${e}" path="challanMasterDto.echallanItemDetDto[${e}].itemDesc" 
										placeholder="${enterItemDesc}" />
									</td>
									
									<td width="20%"><form:input type="number"
										class="form-control required-control" name="itemQuantity${e}"
										id="itemQuantity${e}" path="challanMasterDto.echallanItemDetDto[${e}].itemQuantity" 
										placeholder="Enter the Quantity" disabled="true" /></td>
										
									<td width="10%">
									<spring:message code="EChallan.enter.storeLocation" text="Enter the Storage Location" var="enterStoreLocation" />
									<form:input type="text"
										class="form-control required-control" name="storeId${e}"
										id="storeId${e}" path="challanMasterDto.echallanItemDetDto[${e}].storeId" 
										placeholder="${enterStoreLocation}" disabled="true" /></td>
										
									<td width="10%"> <form:input type="text"
										class="form-control hasNumber" name="challanAmt${e}"
										id="challanAmt${e}" path="challanMasterDto.echallanItemDetDto[${e}].itemAmount"
										onblur="addTotalAmount(this);"
										disabled="${command.saveMode eq 'V' }"/></td>			
								</tr>
								<c:set var="e" value="${e + 1}"  />
								</c:forEach>
							</tbody>
						</table>
						</div>
						
						<div class="form-group">
							<label for="text-1"
								class="col-sm-2 control-label challanAmt required-control"> <spring:message
									code="EChallan.challanAmount" text="Challan Amount" />
							</label>
							<div class="col-sm-4">
								<spring:message code="EChallan.enter.challanAmt" text="Enter the Challan Amount" var="enterChallanAmt" />
								<form:input type="text" class="form-control hasNumber challanAmt" id="challanTotalAmt"
									path="challanMasterDto.challanAmt" value=""
									placeholder="${enterChallanAmt}" maxlength="7" readonly="true"/>
							</div>
						</div>
						
						<!-- Challan Details Table Ends -->
						<c:if test="${command.saveMode eq 'E' ? true : false }">
						<div class="panel panel-default">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						</div>
						</c:if>

						<div class="text-center margin-top-25">
							<c:if test="${command.saveMode eq 'E' ? true : false }">							
							<button type="button" class="btn btn-success"
									title="<spring:message code="EChallan.submit" text="Submit" />"
									onclick="challanPayment(this);">
								<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
									<spring:message code="EChallan.submit" text="Submit" />
							</button>
							</c:if>
						
							<button type="button" class="btn btn-danger"
								title="<spring:message code="EChallan.back" text="Back" />"
								onclick="window.location.href='EChallanPayment.html'">
								<i class="fa fa-arrow-left padding-right-5" aria-hidden="true"></i>
								<spring:message code="EChallan.back" text="Back" />
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>

	</div>