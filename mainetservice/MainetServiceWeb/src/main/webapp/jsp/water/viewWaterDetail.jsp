<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/water/viewWaterDetails.js"></script>

<div id="viewWaterDetails">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="" text="Water Connection Details" />
				</h2>
			</div>

			<div class="widget-content padding">

				<form:form action="ViewWaterDetails.html" name="ViewWaterDetails"
					id="ViewWaterDetails" class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
						
					<c:if test="${command.showForm eq 'N'}">
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="water.dataentry.property.number" text="Property number" /></label>
							<div class="col-sm-4">
								<form:input type="text"
									class="form-control preventSpace alphaNumeric"
									path="entrySearchDto.propertyNo" id="propertyNo"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="water.dataentry.connection.number"
									text="Connection number" /></label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control preventSpace"
									path="entrySearchDto.csCcn" id="connectionNo" maxlength="20"
									minlength="9"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="firstName"><spring:message
									code="water.dataentry.consumer.name" text="Consumer Name" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									path="entrySearchDto.csName" id="csFirstName"
									></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="applicantinfo.label.mobile" /></label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control"
									path="entrySearchDto.csContactno" id="csMobileNo"
									data-rule-minlength="10" data-rule-maxlength="10"></form:input>
							</div>
						</div>

						<div class="form-group wardZone">
							<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
								showOnlyLabel="false" pathPrefix="entrySearchDto.codDwzid"
								 hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true"
								cssClass="form-control required-control " showAll="false"
								showData="true" />
						</div>
						<div class="padding-top-10 text-center">
							
									<button type="button" class="btn btn-info"
										onclick="return searchWaterDetail(this);">
										<i class="fa fa-search"></i>
										<spring:message code="water.search" />
									</button>
						</div>

						<div class="table-responsive">
							<div class="table-responsive margin-top-10">
								<table
									class="table table-striped table-condensed table-bordered"
									id="advertiserTable">
									<thead>
										<tr>
											<th><spring:message code="" text="Property Number" /></th>
											<th><spring:message code="" text="Connection Number" /></th>
											<th><spring:message code="" text="Consumer Name" /></th>
											<th><spring:message code="" text="Mobile No" /></th>
											<th><spring:message code="" text="Address" /></th>
											<th><spring:message code="" text="Action" /></th>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${command.entrySearchDtoList}"
											var="searchDto">
											<tr>
												<td>${searchDto.propertyNo }</td>
												<td>${searchDto.csCcn }</td>
												<td>${searchDto.csName }</td>
												<td>${searchDto.csContactno }</td>
												<td>${searchDto.csAdd }</td>
												<td class="text-center">
													<button type="button" class="btn btn-blue-2 btn-sm"
														title="View Advertiser Master"
														onclick="viewWaterDetail(${searchDto.csCcn})">
														<i class="fa fa-eye"></i>
													</button>

												</td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
						</div>

					</c:if>
					<c:if test="${command.showForm eq 'Y'}">

						<div class="accordion-toggle">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a1"> <spring:message
										code="" text="Property Details" /></a>
							</h4>
							<div id="a1" class="panel-collapse collapse in">
								<div class="form-group">
									<apptags:input labelCode="Property Number"
										path="viewConnectionDto.propertyNo" isReadonly="true"></apptags:input>
									<apptags:input labelCode="Is bhagirathi Connection ?"
										path="viewConnectionDto.bplFlag" isReadonly="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="Total OutStanding Amount"
										path="viewConnectionDto.totalOutsatandingAmt" isReadonly="true"></apptags:input>
								</div>
							</div>

						</div>
						<div class="accordion-toggle">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a2"><spring:message code=""
										text="Consumer Details" /></a>
							</h4>
							<div id="a2" class="panel-collapse collapse in">
								<div class="form-group">
									<apptags:input labelCode="Consumer Name"
										path="viewConnectionDto.csName" isReadonly="true"></apptags:input>
									<apptags:input labelCode="Gender"
										path="viewConnectionDto.csGender" isReadonly="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="Address"
										path="viewConnectionDto.csAdd" isReadonly="true"></apptags:input>
									<apptags:input labelCode="Pincode"
										path="viewConnectionDto.csCpinCode" isReadonly="true"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="Mobile No"
										path="viewConnectionDto.csContactno" isReadonly="true"></apptags:input>
									<apptags:input labelCode="Email Id"
										path="viewConnectionDto.csEmail" isReadonly="true"></apptags:input>
								</div>
							</div>
						</div>

						<%-- <div class="accordion-toggle">

							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a3"> <spring:message
										code="" text="Consumer Details" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse in">
								<div class="form-group">
									<apptags:input labelCode="Zone"
										path="viewConnectionDto.codDwzid1" isReadonly="true"></apptags:input>
									<apptags:input labelCode="Ward"
										path="viewConnectionDto.codDwzid2" isReadonly="true"></apptags:input>
								</div>
							</div>

						</div>
						<div class="accordion-toggle">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a4"> <spring:message
										code="" text="Connection Details" />
								</a>
							</h4>
							<div id="a4" class="panel-collapse collapse in">
								<div class="form-group">
									<apptags:input labelCode="Consumer Type" path=""></apptags:input>
								</div>

							</div>
						</div> --%>


						<c:if
							test="${not empty  command.viewConnectionDto.viewBillMasList}">
							<div class="accordion-toggle">
							<h4 class="panel-title table margin-top-10" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a5"><spring:message
										code="property.BillHistory" text="Bill History" /></a>
							</h4>
							<div id="a5" class="panel-collapse collapse in">

								<table id="BillDetails"
									class="table table-striped table-bordered margin-top-10">

									<tr>
										<th width="10%"><spring:message code="propertyTax.SrNo" /></th>
										<th width="10%"><spring:message
												code="property.Year(From-To)" text="Year(From-To)" /></th>
										<th width="12%"><spring:message
												code="prop.bill.print.due" text="Due Date" /></th>
										<th width="12%"><spring:message
												code="property.TotalDemand" text="Total Demand" /></th>
										<th width="12%"><spring:message
												code="property.BalanceAmount" text="Balance Amount" /></th>
										<th width="12%"><spring:message code="" text="Surcharge" /></th>
										<th width="12%"><spring:message
												code="property.Rebate(If Any)" text="Rebate(If Any)" /></th>
										<th colspan="2"><spring:message code="property.action"
												text="Action" /></th>

									</tr>

									<tbody>
										<c:forEach var="billMasList"
											items="${command.viewConnectionDto.viewBillMasList}"
											varStatus="status">
											<tr>
												<td>${status.count}</td>
												<td>${billMasList.bmCcnOwner}</td>
												<td>${billMasList.bmRemarks}</td>
												<td>${billMasList.bmTotalAmount}</td>
												<td>${billMasList.bmTotalOutstanding}</td>
												<td>${billMasList.surcharge}</td>
												<td>${billMasList.bmToatlRebate}</td>
												<td class="text-center"><button
														class="btn btn-primary btn-sm" type="button"
														onclick="ViewBillDetails(${billMasList.bmIdno})">
														<spring:message code="property.ViewTaxDetails"
															text="View Tax Details" />
													</button></td>
											 <td class="text-center"><button
													class="btn btn-primary btn-sm" type="button"
													onclick="downoladBill(${billMasList.bmIdno})">Download
													Bill</button></td> 

											</tr>
										</c:forEach>
									</tbody>

								</table>
							</div>
							</div>
						</c:if>

						<c:if test="${not empty  command.collectionDetails}">
						<div class="accordion-toggle">
							<h4 class="panel-title table margin-top-10" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a6"><spring:message
										code="property.PaymentHistory" text="Payment History" /></a>
							</h4>
							<div id="a6" class="panel-collapse collapse in">
								<table id="CollectionDetails"
									class="table table-striped table-bordered margin-top-10">

									<tr>

										<th width="10%"><spring:message code="propertyTax.SrNo" /></th>
										<th width="10%"><spring:message code="property.receiptno"
												text="Receipt No" /></th>
										<th width="12%"><spring:message
												code="property.receiptdate" text="Receipt Date" /></th>
										<th width="12%"><spring:message
												code="prop.rec.print.pay.mode" text="Payment Mode" /></th>
										<th width="12%"><spring:message code="property.Amount"
												text="Amount" /></th>
										<th width="12%">Action</th>
									</tr>

									<tbody>
										<c:forEach var="collection"
											items="${command.collectionDetails}" varStatus="status">
											<tr>
												<td>${status.count}</td>
												<td>${collection.lookUpParentId}</td>
												<td>${collection.lookUpCode}</td>
												<td>${collection.lookUpType}</td>
												<td>${collection.otherField}</td>
												<td class="text-center"><button
														class="btn btn-primary btn-sm" type="button"
														onclick="downoladReceipt(${collection.lookUpId},${collection.lookUpParentId})">Download
														Receipt</button></td>

											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
                         </div>
						</c:if>
						<div class="padding-top-10 text-center">
							<button type="button" class="btn btn-danger" id="back"
								onclick="window.location.href='AdminHome.html'">
								<spring:message code="adh.back" text="Back"></spring:message>
							</button>
						</div>
					</c:if>

				</form:form>
			</div>

		</div>
	</div>
</div>