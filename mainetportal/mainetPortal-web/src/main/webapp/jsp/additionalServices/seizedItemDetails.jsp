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
					<form:form action="SeizedItemChallanEntry.html" commandName="command" method="POST" class="form-horizontal" name="echallanEntry"
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
						
						
						
						<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#ConsumerDetails"> <spring:message
											code="EChallan.offendersInfo"
											text="Offenders Information" />
									</a>
								</h4>
							</div>
							
							
							
						<%-- <h4>
							<spring:message code="EChallan.offendersInfo" text="Offenders Information" />
						</h4> --%>
						
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
									disabled="${command.saveMode eq 'V' ? true : false }"/>
							
							</div>
							<label for="text-1"
								class="col-sm-2 control-label forSeizedItems"> <spring:message
									code="EChallan.emailId" text="Email-Id" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control hasemailclass forSeizedItems" id="emailId"
									path="challanMasterDto.offenderEmail"
									placeholder="Enter the Email-Id" 
									disabled="${command.saveMode eq 'V' ? true : false }"/>
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
									disabled="${command.saveMode eq 'V' ? true : false }" />
							</div>
							
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.toArea" text="To Area" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control" id="challanToArea"
									path="challanMasterDto.toArea" placeholder="Enter to Area"
									disabled="${command.saveMode eq 'V' ? true : false }" />
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
									disabled="${command.saveMode eq 'V' ? true : false }"/>
							</div>
							<label for="text-1"
								class="col-sm-2 control-label required-control"> <spring:message
									code="EChallan.mobNo" text="Mobile Number" />
							</label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control hasNumber" maxlength="10" minlength="10"
									id="offenderMobNo" path="challanMasterDto.offenderMobNo"
									placeholder="Enter the Mobile No"
									disabled="${command.saveMode eq 'V' ? true : false }" />
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
									disabled="${command.saveMode eq 'V' ? true : false }"/>
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
											<apptags:filedownload filename="${lookUp.descriptionType}"
												filePath="${lookUp.uploadedDocumentPath}" 
												actionUrl="EChallanEntry.html?Download" showIcon="flase" />
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
								disabled="${command.saveMode eq 'V' ? true : false }"/>
							</div>
						</div>
						
						
						
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#ConsumerDetails"> <spring:message
											code="EChallan.seizedItemDetails"
											text="Seized Item Details" />
									</a>
								</h4>
							</div>
							
						
						<div class="form-group">
							<label for="text-1" class="col-sm-2 control-label "> <spring:message
									code="EChallan.raidNumber" text="Raid Number" />
							</label>
							<div class="col-sm-4 margin-top-5">
								<form:input type="text" class="form-control" name="raidNo"
									id="raidNo" path="challanMasterDto.raidNo" value=""
									disabled="${command.saveMode eq 'V' ? true : false }" />
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
										disabled="${command.saveMode eq 'V' ? true : false }" />
									<label class="input-group-addon" for="challanDate"><i
										class="fa fa-calendar"></i></label>
								</div>
							</div>
						</div>

						<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#ConsumerDetails"> <spring:message
											code="EChallan.seizedItemList"
											text="Seized Item List" />
									</a>
								</h4>
							</div>
							
						
						<div class="table-responsive">
						<table class="table table-bordered table-striped itemTable" id="itemTable">
							<thead>
								<tr>
									<th class="text-center"><spring:message
											code="EChallan.itemName" text="Item Name" /></th>
									<th class="text-center"><spring:message
											code="EChallan.quantity" text="Item Quantity" /></th>
									<th class="text-center"><spring:message
											code="EChallan.amount" text="Amount" /></th>
								</tr>
							</thead>
							<tbody>
			 				
			 				<c:set var="e" value="0" scope="page" />
									<c:forEach var="det" items="${command.challanMasterDto.echallanItemDetDto}" varStatus="status">
								<tr id="firstItemRow">
									
									<td>
									<form:select
										path="challanMasterDto.echallanItemDetDto[${e}].itemDesc"
										cssClass="form-control mandColorClass"
										id="itemDesc${e}" disabled="${command.saveMode eq 'V' ? true : false }">
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
									
									<td width="20%"><form:input type="number"
										class="form-control required-control" name="itemQuantity${e}"
										id="itemQuantity${e}" path="challanMasterDto.echallanItemDetDto[${e}].itemQuantity" 
										placeholder="Enter the Quantity" 
										disabled="${command.saveMode eq 'V' ? true : false }"/></td>
										
									<td width="30%"><%-- <form:input type="text"
										class="form-control" name="challanAmt${e}"
										id="challanAmt${e}" path="" 
										disabled="${command.saveMode eq 'V' ? true : false }"/> --%></td>			
								</tr>
								<c:set var="e" value="${e + 1}"  />
								</c:forEach>
							</tbody>
						</table>
						</div>
						<!-- Challan Details Table Ends -->

						<c:if test="${command.saveMode eq 'E'}">
							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="EChallan.remark" text="Remarks"/></label>
								<div class="col-sm-10">
									<form:textarea class="form-control"
										path="challanMasterDto.remark"></form:textarea>
								</div>
							</div>
						</c:if>

						<div class="text-center margin-top-25">
							<c:if test="${command.saveMode eq 'E'}">
								<button type="submit" class="btn btn-success"
									title="<spring:message code="EChallan.save" text="Save" />"
									onclick="proceedSave(this);">
									<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
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
	</div>