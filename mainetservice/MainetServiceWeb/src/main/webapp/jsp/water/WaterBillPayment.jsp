<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
 <script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script>
	$(document).ready(function(e) {

		$("#payAmount").keyup(function() {
			$("#amountToPay").val($("#payAmount").val());
		});

		$("#amountToPay").val($("#payAmount").val());
		
		 $('#manualReceiptdate').datepicker({
		    	dateFormat : 'dd/mm/yy',
		    	changeMonth : true,
		    	changeYear : true,    	
		    	yearRange : "-100:-0",
		        });
		 
		
	});
	

	function serachWaterBillPaymentData(obj) {
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var url = 'WaterBillPayment.html?serachWaterBillPayment';

		$(theForm).attr('action', url);

		$(theForm).submit();
	}

	function resetwaterManual(element){
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'WaterManualReceiptEntry.html');
		$("#postMethodForm").submit();
		$('.error-div').hide();
	}
	
	function resetbillPayment(element) {
		$("#WaterBillPaymentId").submit();
	}

	function saveData(element) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'Y') {
			return saveOrUpdateForm(element, "Bill Payment done successfully!",
					'WaterBillPayment.html?redirectToPay', 'saveform');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(element, "Bill Payment done successfully!",
					'WaterBillPayment.html?PrintReport', 'saveform');
		} else {
			return saveOrUpdateForm(element, "Bill Payment done successfully!",
					'WaterBillPayment.html', 'saveform');
		}
	}

	jQuery('.maxLength30').keyup(function() {
		$(this).attr('maxlength', '60');

	});
	

	function resetCommonForm() {
		value = "CommonManualReceiptEntry.html";
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', value);
		$("#postMethodForm").submit();
	}
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.bill.collection" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="WaterBillPayment.html" method="post"
				class="form-horizontal" name="WaterBillPayment"
				id="WaterBillPaymentId">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.nodues.connectionNo" /></label>
					<div class="col-sm-4">
						<form:input path="ccnNumber" cssClass="form-control" id="connum" readonly="${command.showMode eq 'Y' ? true : false }"/>
					</div>
					<c:if test="${command.receiptType eq 'M' && command.showMode eq 'N'}">
						<apptags:date labelCode="Manual Receipt Date"
									datePath="manualReceiptdate" fieldclass="datepicker"
									isMandatory="true"></apptags:date>
					</c:if>
					
					<c:if test="${command.receiptType ne 'M'}">
					<div class="col-sm-6">
						<button type="button" class="btn btn-info"
							onclick="return serachWaterBillPaymentData(this);">
							<i class="fa fa-search"></i>
							<spring:message code="water.search" text="Search"/>
						</button>
						
						<button type="button" class="btn btn-warning"
							onclick="resetbillPayment(this)">
							<spring:message code="water.btn.reset" text="Reset"/>
						</button>
						<button type="button" class="btn btn-danger"
								onclick="window.location.href='AdminHome.html'">
								<spring:message code="water.btn.back" text="Back"/>
						</button>	
					</div>
					
					</c:if>

				</div>
				
				<c:if test="${command.receiptType eq 'M'}">
					<div class="text-center padding-top-10">
					<c:if test="${command.showMode eq 'N'}">
						<button type="button" class="btn btn-info"
							onclick="return serachWaterBillPaymentData(this);">
							<i class="fa fa-search"></i>
							<spring:message code="water.search" text="Search" />
						</button>
						<button type="Reset" class="btn btn-warning" onclick="resetwaterManual(this)">
							<spring:message code="water.btn.reset" text="Reset" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="water.btn.back" text="Back"/>
						</button>
						</c:if>
					</div>
				</c:if>

				<div class="panel-group accordion-toggle"
					id="#accordion_single_collapse">
					<c:if
						test="${(command.billMas.csIdn ne null) or (not empty command.message && command.message ne null)}">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse"
										href="#ConnectionDetails">Connection Details</a>
								</h4>
							</div>

							<div id="#ConnectionDetails" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">
										<label class="col-sm-2 control-label" for="CsName"> <spring:message
												code="water.bill.payment.consumer.name" text="Consumer Name" />
										</label>
										<div class="col-sm-4">
											<c:if
												test="${ not empty command.applicantDetailDto.applicantMiddleName}">
												<form:input type="text" class="form-control" path=""
													value="${command.applicantDetailDto.applicantFirstName} ${command.applicantDetailDto.applicantMiddleName} ${command.applicantDetailDto.applicantLastName}"
													readonly="true"></form:input>
											</c:if>
											<c:if
												test="${empty command.applicantDetailDto.applicantMiddleName}">
												<form:input type="text" class="form-control" path=""
													value="${command.applicantDetailDto.applicantFirstName} ${command.applicantDetailDto.applicantLastName}"
													readonly="true"></form:input>
											</c:if>
										</div>
										<%-- 	<label class="col-sm-2 control-label"> <spring:message
							code="" text="Application No."/>
					</label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control"
							path="apmApplicationId" readonly="true" ></form:input>
					</div> --%>
									</div>




									<%-- <div class="form-group">
					<label class="col-sm-2 control-label" for="billingFlatNo">
						<spring:message code="water.flatOrbuildingNo" />
					</label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control"
							path="applicantDetailDto.flatBuildingNo" id="billingFlatNo" readonly="true"></form:input>
					</div>
					<label class="col-sm-2 control-label"> <spring:message
							code="water.buildingName" />
					</label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control"
							path="applicantDetailDto.buildingName" id="billingBuildingName" readonly="true"></form:input>
					</div>
				</div> --%>
									<div class="form-group">
										<%-- <label class="col-sm-2 control-label"> <spring:message
							code="water.roadName" />
					</label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control"
							path="applicantDetailDto.roadName" id="billingRoadName" readonly="true"></form:input>
					</div> --%>
										<label class="col-sm-2 control-label "> <spring:message
												code="water.areaName" />
										</label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												path="applicantDetailDto.areaName" id="billingAreaName"
												readonly="true"></form:input>
										</div>
									</div>

								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse"
										href="#PayableAmountDetails">Receipt Amount Details</a>
								</h4>
							</div>

							<div id="PayableAmountDetails" class="panel-collapse collapse in">
								<div class="panel-body">


									<c:if
										test="${command.taxes ne null && not empty command.taxes}">
										<%-- <div class="table-responsive margin-top-10">
			<table class="table table-bordered table-condensed table-striped">
				<tr>
					<th width="100">Sr. No.</th>
					<th>Tax Name</th>
					<th>Tax Amount</th>
				</tr>
				<c:forEach items="${command.taxes}" var="data" varStatus="status">
					<tr>
						<td><c:out value="${status.index+1}"></c:out></td>
						<td><c:out value="${data.key}"></c:out></td>
						<td><c:out value="${data.value}"></c:out></td>
					</tr>
				</c:forEach>
			</table>
		</div> --%>
										<div class="table-responsive">
											<table class="table table-bordered table-condensed">

												<tr>
													<th><spring:message
															code="water.waterBillGeneration.TaxDescription" /></th>
													<th><spring:message
															code="water.waterBillGeneration.BalanceArrears" /></th>
													<th><spring:message
															code="water.waterBillGeneration.CurrentTax" /></th>
													<th><p class="text-right">
															<spring:message
																code="water.waterBillGeneration.CurrentBalanceTax" />
														</p></th>
													<th><p class="text-right">
															<spring:message
																code="water.waterBillGeneration.TotalBalance" />
														</p></th>
												</tr>
												<c:forEach items="${command.taxes}" var="billDet"
													varStatus="detStatus">
													<tr>
														<td>${billDet.taxdescription}</td>
														<td><p class="text-right">${billDet.arrearTaxAmount}</p></td>
														<td><p class="text-right">${billDet.taxAmount}</p></td>
														<td><p class="text-right">${billDet.balabceTaxAmount}</p></td>
														<td><p class="text-right">${billDet.total}</p></td>
													</tr>
												</c:forEach>
												<tr>
													<th colspan="3"><p class="text-right">
															<spring:message
																code="water.waterBillGeneration.AdjustmentEntry" />
														</p></th>
													<td colspan="2"><p class="text-right">0.00</p></td>
												</tr>
												<tr>
													<th colspan="3"><p class="text-right">
															<spring:message
																code="water.waterBillGeneration.AdjustedAmount" />
														</p></th>
													<td colspan="2"><p class="text-right">${command.excessAmount}</p></td>
												</tr>
												<tr>
													<th colspan="3"><p class="text-right">
															<spring:message
																code="water.waterBillGeneration.BalanceExcessAmount" />
														</p></th>
													<td colspan="2"><p class="text-right">
															${command.balanceExcessAmount}</p></td>
												</tr>
												<tr>
													<th colspan="3"><p class="text-right">
															<spring:message
																code="water.waterBillGeneration.RebateAmount" />
														</p></th>
													<td colspan="2"><p class="text-right">${command.rebateAmount}</p></td>
												</tr>

											</table>
										</div>

									</c:if>

									<c:if test="${command.rebateMessage eq 'Y'}">
										<div class="form-group">
											<label class="col-sm-10 control-label text-red"><spring:message
													code="water.rebate.message" /></label>
										</div>
									</c:if>
									<div class="form-group padding-top-10">
										<label class="col-sm-2 control-label"><spring:message
												code="water.waterBillGeneration.TotalReceivable" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="billMas.bmTotalOutstanding"
													cssClass="form-control text-right" id="totalPayable"
													readonly="true" />
												<label class="input-group-addon"><i
													class="fa fa-inr"></i></label>
											</div>
										</div>
										<label class="col-sm-2 control-label"><spring:message
												code="water.waterBillGeneration.EnterReceiptAmount" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="payAmount"
													cssClass="form-control mandColorClass text-right"
													maxlength="10" id="payAmount"
													 />
												<label class="input-group-addon"><i
													class="fa fa-inr"></i></label>
											</div>
										</div>
									</div>
									<c:if
										test="${not empty command.message && command.message ne null }">
										<div class="form-group">
											<label class="col-sm-10 text-red">${command.message}</label>
										</div>
									</c:if>
								</div>
							</div>
						</div>
						
						<c:if test="${command.receiptType eq 'M'}">
<div class="form-group padding-top-10" >	
			<apptags:input labelCode="Manual Receipt No" path="manualReceiptNo"></apptags:input>
			
			<%-- <apptags:date labelCode="Manual Receipt Date"
									datePath="manualReceiptdate" 
									fieldclass="trimDateTime" isMandatory="true" readonly="true"></apptags:date> --%>



<apptags:input labelCode="Manual Receipt Date" path="manualReceiptDateString" isReadonly="true"></apptags:input>

								<%-- <apptags:date labelCode="Manual Receipt Date"
									datePath="manualReceiptdate" fieldclass="datepicker"
									isMandatory="true" readonly="true"></apptags:date> --%>
							</div>
			
			
		<div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th> <spring:message
																		code="water.serialNo" text="Sr No" />
															</th>
															<th> <spring:message
																		code="water.docName" text="Document Name" />
															</th>
															<th> <spring:message
																		code="water.status" text="Status" />
															</th>
															<th> <spring:message
																		code="water.uploadText" text="Upload" />
															</th>
														</tr>
															<tr>
																<td>1</td>
																<td>Manual Receipt</td>
																	<td> <spring:message
																				code="water.doc.mand" />
																	</td>
																<td><div id="docs_0" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																			currentCount="0" />
																	</div>
																	</td>
															</tr>
													</tbody>
												</table>
											</div>
										</div>									
			

</c:if>					
						<div class="panel panel-default">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						</div>
						<div class="text-center padding-top-10">
							<button type="button" class="btn btn-success btn-submit"
								onclick="saveData(this)">
								<spring:message code="water.btn.submit" />
							</button>
							<button type="button" class="btn btn-danger"
								onclick="window.location.href='AdminHome.html'">
								<spring:message code="water.btn.back" />
							</button>
							<c:if test="${command.receiptType eq 'M' && command.showMode eq 'Y'}">
							<button type="button" class="btn btn-warning"
							onclick="resetCommonForm()">
							<spring:message code="water.btn.reset" />
						</button>
						</c:if>
						
						</div>
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
</div>
