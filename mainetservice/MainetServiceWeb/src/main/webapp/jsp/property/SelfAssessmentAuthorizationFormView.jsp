<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/property/propertyAuthorization.js"></script>
<div id="contentTot">
	<ol class="breadcrumb">
		<li><a href="AdminHome.html"><span class="hide">Home</span><i
				class="fa fa-home"></i></a></li>
		<li>Self Assessment Form</li>
		<li>Self Assessment Authorization</li>
		<li>Self Assessment Authorization View</li>
	</ol>

	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>Self Assessment View</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide">Help</span></i></a>
				</div>
			</div>

			<div class="widget-content padding">

				<form:form action="SelfAssessmentAuthorization.html"
					class="form-horizontal form" name="SelfAssessmentAuthorizationForm"
					id="SelfAssessmentAuthorizationFormId">

					<form:hidden path="apmApplicationId" value="${entity.apmApplicationId}"/>
					<form:hidden path="proAssNo" value="${entity.proAssNo}"/>
				
					<div class="form-group">

						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.buildingpermission" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.tppApprovalNo}" path="tppApprovalNo" disabled="true"  />
						</div>

					</div>

					<h4>Owner Details</h4>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.OwnerType" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.assOwnerTypeDesc}" path="proAssOwnerType" disabled="true" />
						</div>
					</div>

					<div class="table-responsive">
						<table class="table table-striped table-condensed table-bordered">
							<tr>
								<th>Owners Name</th>
								<th>Gender</th>
								<th>Husband / Father Name</th>
								<th>Mobile No.</th>
								<th>Aadhar No.</th>
							</tr>
							
							<c:forEach items="${command.listOfProvisionalAssesmentOwnerDtl}" var="currOwner">
							<tr>
								<td>${currOwner.proAssoOwnerName}</td>
								<td>${currOwner.proAssoGenderDesc}</td>
								<td>${currOwner.proAssoFathusName}</td>
								<td>${currOwner.proAssoMobileno}</td>
								<td>${currOwner.proAssoAddharno}</td>
							</tr>
							</c:forEach>
						</table>
					</div>

					<h4>Property Details</h4>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="propertydetails.oldpropertyno" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssOldpropno}" path="proAssOldpropno" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="propertydetails.csnno" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.tppPlotNoCs}" path="tppPlotNoCs" disabled="true" />
						</div>
					</div>

					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.SurveyNumber" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.tppSurveyNumber}" path="tppSurveyNumber" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="propertydetails.khatano" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.tppKhataNo}" path="tppKhataNo" disabled="true" />
						</div>
					</div>

					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="propertydetails.tojino" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.tppTojiNo}" path="tppTojiNo" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="propertydetails.plotno" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.tppPlotNo}" path="tppPlotNo" disabled="true" />
						</div>
					</div>

					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="propertydetails.streetno" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssStreetNo}" path="proAssStreetNo" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="propertydetails.village" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.tppVillageMauja}" path="tppVillageMauja" disabled="true" />
						</div>
					</div>

					<h4>Property Address Details</h4>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.propertyaddress" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssAddress}" path="proAssAddress" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.location" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.locationDesc}" path="locId" disabled="true" />
						</div>
					</div>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.pincode" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssPincode}" path="proAssPincode" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.email" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssCorrEmail}" path="proAssCorrEmail" disabled="true" />
						</div>
					</div>

					<h4>Correspondence Address Details</h4>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.propertyaddress" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssCorrAddress}" path="proAssCorrAddress" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.location" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.locationDesc}" path="locId" disabled="true" />
						</div>
					</div>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.pincode" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssCorrPincode}" path="proAssCorrPincode" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.email" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssCorrEmail}" path="proAssCorrEmail" disabled="true" />
						</div>
					</div>

					<h4>Land / Building Details</h4>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.yearofacquisition" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssAcqDate}" path="proAssAcqDate" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.propertytype" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.assPropertyType1Desc}" path="proAssPropType1" disabled="true" />
						</div>
					</div>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.totalplot" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssPlotArea}" path="proAssPlotArea" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.buildup" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" value="${command.proAssBuitAreaGr}" path="proAssBuitAreaGr" disabled="true" />
						</div>
					</div>

					<h4>Unit Details</h4>
					<div class="table-responsive">
						<table class="table table-striped table-condensed table-bordered">
							<tbody>
								<tr>
									<th>Sr. No.</th>
									<th>Unit Type</th>
									<th>Floor No.</th>
									<th>Date of Construction</th>
									<th>Usage Type</th>
									<th>Usage Sub Type</th>
									<th>Construction Type</th>
									<th>Occupancy Type</th>
									<th>Taxable Area</th>
									<th>Rent</th>
									<th>Rate</th>
									<th>ARV</th>
									<th>RV</th>
								</tr>
								<c:forEach items="${command.provisionalAssesmentDetails}" var="currUnit" varStatus="loop">
								  <%-- <c:forEach items="${command.listOfTbAsAssesmentFactorDetail}" var="currUnitFactorDetail"> --%>
									<tr>
										<td>${loop.index+1}</td>
										<td>${currUnit.assdUnitTypeDesc}</td>
										<td>${currUnit.assdFloorNoDesc}</td>
										<td>${currUnit.proAssdYearConstruction}</td>
										<td>${currUnit.assdUsagetype1Desc}</td>
										<td>${currUnit.assdUsagetype2Desc}</td>
										<td>${currUnit.assdConstruTypeDesc}</td>
										<td>${currUnit.assdOccupancyTypeDesc}</td>
										<td>${currUnit.proAssdBuildupArea}</td>
										<td>${currUnit.proAssdAnnualRent}</td>
										<td>${currUnit.proAssdStdRate}</td>
										<td>${currUnit.proAssdAlv}</td>
										<td>${currUnit.proAssdRv}</td>
									</tr>
								  <%-- </c:forEach> --%>
							</c:forEach>
								<tr>
									<th class="text-right" colspan="11">Total</th>
									<th>${command.assdAlvTotal}</th>
									<th>${command.assdRvTotal}</th>
								</tr>
							</tbody>
						</table>
					</div>

					<h4>Unit Specific additional Information</h4>
					<div class="table-responsive">
						<table class="table table-striped table-condensed table-bordered">
							<tbody>
								<tr>
									<th>Unit No.</th>
									<th>Factor</th>
									<th>Factor Value</th>
									<th>Start Date</th>
									<th>End Date</th>
								</tr>
								<c:forEach items="${command.provisionalAssesmentDetails}" var="currUnitList" varStatus="loop">
								   <c:forEach items="${currUnitList.listOfProvAsAssesmentFactorDetail}" var="currUnitFactorDetail"> 
										<tr>
											<td>${loop.index+1}</td>
											<td>${currUnitFactorDetail.proAssfFactorDesc}</td>
											<td>${currUnitFactorDetail.proAssfFactorValueDesc}</td>
											<td>${currUnitFactorDetail.proAssfStartDate}</td>
											<td>${currUnitFactorDetail.proAssfEndDate}</td>
										</tr>
								   </c:forEach> 
							</c:forEach>
							</tbody>
						</table>

					</div>

					<h4>Last Payment Details</h4>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.LastPayment" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" path="proAssLpReceiptAmt" value="${command.proAssLpReceiptAmt}" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.receiptno" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" path="proAssLpReceiptNo" value="${command.proAssLpReceiptNo}"  disabled="true" />
						</div>
					</div>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.receiptamount" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" path="proAssLpReceiptAmt" value="${command.proAssLpReceiptAmt}"  disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.receiptdate" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" path="proAssLpReceiptDate" value="${command.proAssLpReceiptDate}" disabled="true" />
						</div>
					</div>
					<div class="form-group">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.lastpaymentupto" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" path="proAssLpYear" value="${command.proAssLpYear}" disabled="true" />
						</div>
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.BillingCycle" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" path="proAssLpBillCycle" value="${command.proAssLpBillCycleDesc}" disabled="true" />
						</div>
					</div>

					<h4>Uploaded Documents</h4>
					<div class="table-responsive">
						<table class="table table-striped table-condensed table-bordered">
							<tbody>
								<tr>
									<th>Sr. No.</th>
									<th>Document Name</th>
									<th>Uploaded Document</th>
								</tr>
								
								<c:forEach items="${command.listOfFileUploadDetails}" var="currDocObj" varStatus="currDocObjCnt">
									
									<tr>
										<td>${currDocObjCnt.index+1}</td>
										<td>${currDocObj.clmDesc}</td>
										<td>${currDocObj.attFname}</td>
									</tr>
									
								</c:forEach>
								
							</tbody>
						</table>
					</div>

					<h4>Tax Calculation</h4>
					<div class="table-responsive">
						<table class="table table-striped table-condensed table-bordered">
							<tbody>
								<tr>
									<th colspan="5" class="text-left">a. Tax Wise Details</th>
								</tr>
								<tr>
									<th width="50">Sr. No.</th>
									<th width="400">Tax Name</th>
									<th width="200" class="text-right">Arrears (Previous Years)</th>
									<th width="200" class="text-right">Current year</th>
									<th width="200" class="text-right">Total</th>
								</tr>
								<tr>
									<td>1</td>
									<td>General Tax</td>
									<td class="text-right">1</td>
									<td class="text-right">2</td>
									<td class="text-right">3</td>
								</tr>
								<tr>
									<td>2</td>
									<td>Water charge</td>
									<td class="text-right">1</td>
									<td class="text-right">2</td>
									<td class="text-right">3</td>
								</tr>
								<tr>
									<td>3</td>
									<td>Water Benefit Tax</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>4</td>
									<td>Sewerage Tax</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>5</td>
									<td>Sewerage Benefit Tax</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>6</td>
									<td>Education Cess</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>7</td>
									<td>State Education Cess</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>8</td>
									<td>Employment Guarantee Cess</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>9</td>
									<td>Tree Cess</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>10</td>
									<td>Street Tax</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<th colspan="4" class="text-right">Total</th>
									<th class="text-right">6</th>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered">
							<tr>
								<th colspan="5" class="text-left">b. Exemptions</th>
							</tr>
							<tr>
								<th width="50">Sr. No.</th>
								<th width="400">Tax Name</th>
								<th width="200" class="text-right">Arrears (Previous Years)</th>
								<th width="200" class="text-right">Current year</th>
								<th width="200" class="text-right">Total</th>
							</tr>
							<tr>
								<td>1</td>
								<td>Exemption on General Tax</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>2</td>
								<td>Exemption on Education Cess</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>3</td>
								<td>Exemption on State Education Cess</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>4</td>
								<td>Exemption on Employment Guarantee Cess</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>5</td>
								<td>Exemption on Street Tax</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<th colspan="4" class="text-right">Total</th>
								<th></th>
							</tr>
						</table>
					</div>

					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered">
							<tr>
								<th colspan="5" class="text-left">c. Rebate</th>
							</tr>
							<tr>
								<th width="50">Sr. No.</th>
								<th width="800">Tax Name</th>
								<th width="200" class="text-right">Total</th>
							</tr>
							<tr>
								<td>1</td>
								<td>Rain Water Harvesting</td>
								<td></td>
							</tr>
							<tr>
								<td>2</td>
								<td>Early bird Discount</td>
								<td></td>
							</tr>
							<tr>
								<th colspan="2" class="text-right">Total</th>
								<th></th>
							</tr>
						</table>
					</div>

					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered">
							<tr>
								<th colspan="5" class="text-left">d. Penalty / Interest</th>
							</tr>
							<tr>
								<th width="50">Sr. No.</th>
								<th width="800">Tax Name</th>
								<th width="200" class="text-right">Total</th>
							</tr>
							<tr>
								<td>1</td>
								<td>Interest</td>
								<td></td>
							</tr>
							<tr>
								<td>2</td>
								<td>Penalty for Late submission</td>
								<td></td>
							</tr>
							<tr>
								<th colspan="2" class="text-right">Total</th>
								<th></th>
							</tr>
						</table>
					</div>

					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-bordered">
							<tr>
								<th width="500" class="text-left">Total Tax Payable (a - b-
									c + d)</th>
								<th width="500">325200</th>
							</tr>
						</table>
					</div>

					<h4 class="hidden-print">Payment</h4>
					<div class="form-group hidden-print">
						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.receiptamount" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" path="" disabled="true" />
						</div>
					</div>

					<div class="form-group hidden-print">
						<label class="control-label col-sm-2" for="PaymentMode">Select
							Payment Mode</label>
						<div class="col-sm-4">
							<label class="radio-inline"> <input name="Payment"
								type="radio" value=""> Online
							</label> <label class="radio-inline"> <input name="Payment"
								type="radio" value=""> Offline
							</label> <label class="radio-inline"> <input name="Payment"
								type="radio" value=""> Pay @ ULB Counter
							</label>
						</div>
					</div>

					<div class="text-center" id="processBtn">
						<input type="button" id="searchPropertyBasisBtn"
							class="btn btn-success" value="Submit" /> 
						<input type="button"
							class="btn btn-blue-2" id="EditSelfAssForm" value="Edit"></input>
						<button type="button" onclick="printContent('receipt')"
							class="btn btn-primary hidden-print"><i
							class="fa fa-print"></i> Print</button>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>