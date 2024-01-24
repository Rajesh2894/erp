<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<!-- <script type="text/javascript" src="js/land_estate/landEstate.js"></script> -->
<script type="text/javascript" src="js/land_estate/landEstateBill.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="land.rent.pay" text="Rent Payment" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="fiels.mandatory.message" text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="LandAcquisition.html" cssClass="form-horizontal"
				id="landAcqId">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="land.rent.receiptDetails" text="Receipt Details" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
							<div class="form-group">
								
								<%-- <apptags:date fieldclass="datepicker" labelCode="land.rent.receiptDate" isMandatory="true"
										datePath="acquisitionDto.acqDate"
										readonly="${command.saveMode eq 'VIEW'}"
										isDisabled="${command.saveMode eq 'VIEW'}"></apptags:date>
										 --%>
										 <label class="col-sm-2 control-label required-control" for=""
											id="dispoDate"><spring:message
												code="land.rent.receiptDate" /></label>
										 	<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker" placeholder="dd-mm-yyyy"
													id="receiptDate" data-label="#dispoDate"
													path="" isMandatory="true"
													onchange=""></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
										 
										 
										 
										 
								<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.rent.category" /><span><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="" id="category"
											cssClass="form-control chosen-select-no-results" isMandatory="true" 
										class="form-control mandColorClass" data-rule-required="true">
											<form:option value="0">Rent Receipt</form:option>
											
										</form:select>
									</div>
							</div>
							
							<div class="form-group">
							<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.rent.Contact" /><span><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="" id="contractName"
											cssClass="form-control chosen-select-no-results" isMandatory="true" 
										class="form-control mandColorClass" data-rule-required="true">
											<form:option value="0">Select Contact Number</form:option>
										</form:select>
									</div>
									<apptags:input labelCode="land.rent.payName" placeholder="Enter Payer Name" 
										path="acquisitionDto.lnDesc" cssClass="hasCharacter" maxlegnth="100"
										isMandatory="true"></apptags:input>
							</div>
							<div class="form-group">
							<apptags:input labelCode="land.rent.mobile" placeholder="Enter Mobile Number"
										path="acquisitionDto.lnDesc" cssClass="hasNumber1" maxlegnth="100"></apptags:input>
							<apptags:input labelCode="land.rent.email" placeholder="Enter  Email ID"
										path="acquisitionDto.lnDesc" cssClass="hasemailclass" maxlegnth="100"></apptags:input>
							</div>
							
							<div class="form-group">
							<apptags:input labelCode="land.rent.manualReceiptNo" placeholder="Enter Manual Receipt Number"
										path="acquisitionDto.lnDesc" cssClass="hasNumber1" maxlegnth="100"></apptags:input>
							<apptags:input labelCode="land.rent.narration" placeholder="Enter Narration"
										path="acquisitionDto.lnDesc" cssClass="alphaNumeric" maxlegnth="100"></apptags:input>
							
							</div>
						</div>
					</div>
				</div>
				<!-----------------------------Receipt Head  ---------------------------->
				
					<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a2"><spring:message code="land.rent.receiptHead" text="Receipt Head" /> </a>
								</h4>
							</div>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body padding-top-0">
								<!-- <div class="table-responsive clear"> -->
					<table class="table table-striped table-bordered"
						id="">
						<thead>
							<tr>
								
								<th class="text-center"><spring:message
										code="land.rent.receiptHead" text="Receipt Head" /><span><i
											class="text-red-1">*</i></span></th>
								<th class="text-center"><spring:message
										code="land.rent.receiptAmt" text="Receipt Amount" /><span><i
											class="text-red-1">*</i></span></th>
								<th class="text-center"><spring:message
										code="land.acq.summary.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-center"><form:select path="" id="receiptHead"
											cssClass="form-control chosen-select-no-results" isMandatory="true" 
										class="form-control mandColorClass" data-rule-required="true">
											<form:option value="0">Select Receipt Head</form:option>
										</form:select></td>
									<td class="text-center"><form:input path="" placeholder="Enter Receipt Amount" id="amt" cssClass="hasNumber1"/></td>
									
									<td class="text-center">
										<button type="button" class="btn btn-blue-2"
											name="button-plus" id="button-plus"
											onclick="getActionForDefination(${land.lnaqId},'VIEW')"
											title="<spring:message code="land.acq.summary.view" text="View"></spring:message>">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</button>

										<button type="button" class="btn btn-danger"
											onclick="getActionForDefination(${land.lnaqId},'EDIT')"
											title="<spring:message code="land.acq.summary.edit" text="Edit"></spring:message>">
											<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
										</button>
									</td>
									</tr>
						
						</tbody>
					</table>
				    <!--  </div> -->
				  </div>
			</div>
		</div>
			<!-----------------------------Receipt Mode  ---------------------------->
			<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a3"><spring:message code="land.rent.receiptMode" text="Receipt Mode" /> </a>
								</h4>
							</div>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body padding-top-0">
								<!-- <div class="table-responsive clear"> -->
					<table class="table table-striped table-bordered"
						id="">
						<thead>
							<tr>
								
								<th class="text-center"><spring:message
										code="land.rent.mode " text="Mode" /><span><i
											class="text-red-1">*</i></span></th>
								<th class="text-center"><spring:message
										code="land.rent.bankName" text="Bank Name" /><span><i
											class="text-red-1">*</i></span></th>
								<th class="text-center"><spring:message
										code="land.rent.instrumentNo" text="Instrument Number" /><span><i
											class="text-red-1">*</i></span></th>
								<th class="text-center"><spring:message
										code="land.rent.instrumentDate" text="Instrument Date" /><span><i
											class="text-red-1">*</i></span></th>
								<th class="text-center"><spring:message
										code="land.rent.totalAmt" text="Total Amount " /></th>
								
							</tr>
						</thead>
						<tbody>
							
								<tr>
									
									<td class="text-center"><form:select path="" id="mode"
											cssClass="form-control chosen-select-no-results" isMandatory="true" 
										class="form-control mandColorClass" data-rule-required="true">
											<form:option value="0">Select Mode</form:option>
										</form:select></td>
									<td class="text-center"><form:select path="" id="bankName"
											cssClass="form-control chosen-select-no-results" isMandatory="true" 
										class="form-control mandColorClass" data-rule-required="true">
											<form:option value="0">Select Bank Name</form:option>
											<c:forEach items="${command.bankList}" var="bnk">
												<form:option value="${bnk.bankId}">${bnk.bank}</form:option>
											</c:forEach>
										</form:select></td>
									<td class="text-center"><form:input path="" placeholder="Enter Insrumental Number" id="instrumentalNo" cssClass="hasNumber1"/></td>
									<td class="text-center"><form:input class="form-control datepicker" placeholder="dd-mm-yyyy"
													id="instrumentalNo1" 
													data-label="#dispoDate"
													path="" isMandatory="true"
													onchange=""></form:input></td>
									<td class="text-center"><form:input path="" disabled="true"/></td>
								</tr>
						
						</tbody>
					</table>
				  <!--  </div> -->
				</div>
				</div>
				</div>
			</div>
			       <!-- Start button -->
								<div class="text-center">
									<c:if test="${command.saveMode ne 'VIEW'}">
										<button type="button" class="btn btn-success" title="Submit"
											onclick="saveBillPayForm(this)">
											<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
											<spring:message code="bt.save" text="Submit" />
										</button>
									</c:if>
									<%-- <c:if test="${command.saveMode eq 'ADD'}"> --%>
										<button type="button"
											onclick="billPayment('LandBill.html','billPayment');"
											class="btn btn-warning" title="Reset">
											<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
											<spring:message code="bt.clear" text="Reset" />
										</button>
									<%-- </c:if> --%>
									<button type="button" class="button-input btn btn-danger"
										name="button-Cancel" value="Cancel" style=""
										onclick="window.location.href='LandAcquisition.html'"
										id="button-Cancel">
										<i class="fa fa-chevron-circle-left padding-right-5"></i>
										<spring:message code="bt.backBtn" text="Back" />
									</button>
								</div>
								<!-- End button -->
			</form:form>
			<!-- End Form -->
			</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->