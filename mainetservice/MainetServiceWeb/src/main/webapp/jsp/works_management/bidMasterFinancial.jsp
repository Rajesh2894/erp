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
<script type="text/javascript" src="js/works_management/bidMaster.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2>
				<spring:message code="works.financial.details" text="Financial Details" />
			</h2>
			<apptags:helpDoc url="BIDEntry.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="BIDEntry.html" class="form-horizontal" name=""
				id="tenderInitiation">
				<form:hidden path="workEstimateAmt" id="workEstimateAmt" />
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="saveMode" id="saveMode" />

				<div class="form-group">

					<label for="text-1" class="col-sm-2 control-label"><spring:message
							code="works.bidderName" text="Bidder Name" /> </label>
					<div class="col-sm-4">
						<form:input cssClass="form-control " readonly="true"
							path="bidMasterDto.bidIdDesc" onkeypress="" id="" />
					</div>
				</div>


				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 type="h4" class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a3"> <spring:message
									code="works.financial.details" text="Financial Details" />
							</a>
						</h4>
					</div>
					<div id="a3" class="panel-collapse collapse in">
						<div class="panel-body padding-top-0">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-striped table-condensed table-bordered"
								id="financialevaluation">

								<thead>
									<tr>
										<th width="7%"><spring:message code="ser.no" text="Sr. No." />
											<i class="text-red-1"></i></th>
										<th width="20%"><spring:message code='tender.type'
												text="Tender Type" /><span class="mand">*</span></th>
										<th width="20%"><spring:message code="works.percentage.type"
												text="Percentage Type" /> <i class="text-red-1">*</i></th>
										<th width="20%"><spring:message code="works.percentage.amount"
												text="Percentage/Amount" /> <i class="text-red-1">*</i></th>
										<th width="15%"><spring:message code="works.quoted.amount"
												text="Quoted Amount" /> <i class="text-red-1">*</i></th>
										<%-- <th width="20%"><spring:message code="" text="Action" />
											</th> --%>

									</tr>
								</thead>
								<tbody>

									<tr class="finanClass">
										<td><form:input path="" id="sNo${d}" value="${d + 1}"
												readonly="true" cssClass="form-control " /></td>

										<td class="tenderTypes"><form:select
												path="bidMasterDto.commercialBIDDetailDtos[${d}].tenderType"
												cssClass="form-control" id="tenderType${d}"
												onchange="calculateTotalAmount(${d}),checkTenderType()"
												data-rule-required="true" disabled="${command.saveMode eq 'V'}">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${valueTypeList}" var="payType">
													<form:option value="${payType.lookUpId }"
														code="${payType.lookUpCode}">${payType.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select
												path="bidMasterDto.commercialBIDDetailDtos[${d}].percenttype"
												cssClass="form-control" id="percenttype${d}"
												data-rule-required="true" disabled="${command.saveMode eq 'V'}">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
												<c:forEach items="${valueTypeAmount}" var="payType">
													<form:option value="${payType.lookUpId }"
														code="${payType.lookUpCode}">${payType.lookUpDesc}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input
												path="bidMasterDto.commercialBIDDetailDtos[${d}].percentvalue"
												type="text" class="form-control" 
												onkeyup="formatDecimalInput(this, 2); calculateTotalAmount(${d});"
												maxLength="100" disabled="${command.saveMode eq 'V'}"
												id="percentvalue${d}" /></td>

										<td><form:input
												path="bidMasterDto.commercialBIDDetailDtos[${d}].quotedPrice"
												type="text" class="form-control" maxLength="100" readonly="true"
												id="quotedPrice${d}" disabled="${command.saveMode eq 'V'}" /></td>

										<!-- <td class="text-center"><a href="javascript:void(0);"
											class="btn btn-success addtable btn-sm"
											onclick="addfinancialData();">
										<i class="fa fa-plus-circle" id=""></i></a>

										  <a href="javascript:void(0);"
											class="btn btn-danger btn-sm delButton"
											onclick="deletefinancial($(this),'removedIds');"><i
												class="fa fa-minus" id=""></i></a></td> -->
									</tr>
									<c:set var="d" value="" scope="page" />
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				
				<div id="itemRateDivision">
					<h4>
						<spring:message code="" text="Item Details" />
					</h4>
					<div class="panel-default">
						<div class="panel-collapse collapse in" id="UnitDetail">
							<div class=" clear padding-10">
								<c:set var="d" value="0" scope="page" />
								<table id="itemDetailsTableID" summary="Item Details" class="table table-bordered table-striped rcm">
									<thead>
										<tr>
											<th width="5%"><spring:message code="" text="Sr.No" /></th>
											<th width="15%"><spring:message code="" text="Item Name" />
													<i class="text-red-1">*</i></th>
											<th width="10%"><spring:message code="" text="Quantity Required" />
													<i class="text-red-1">*</i></th>
											<th width="15%"><spring:message code="" text="Per Unit Rate" />
													<i class="text-red-1">*</i></th></th>
											<th width="10%"><spring:message code="" text="Amount" />
													<i class="text-red-1">*</i></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${command.bidMasterDto.itemRateBidDetailDtos}" var="data" varStatus="index">
											<tr class="itemDetailRow">
												<td class="text-center"><input type="text" class="form-control text-center" disabled="true"
													id="SrNo${d}" value="${d+1}" /></td>
													
												<td><form:hidden path="bidMasterDto.itemRateBidDetailDtos[${d}].itemId" id="itemId${d}" />
													<form:input path="bidMasterDto.itemRateBidDetailDtos[${d}].itemName" id="itemName${d}"
														class="form-control mandColorClass"
														data-rule-required="true" readonly="true" /></td>
												
												<td><form:input path="bidMasterDto.itemRateBidDetailDtos[${d}].quantity"
														id="quantity${d}" type="text" class="form-control" readonly="true" /></td>
													
												<td><form:input path="bidMasterDto.itemRateBidDetailDtos[${d}].perUnitRate" id="perUnitRate${d}"
														onchange="calculateTotal()" class="form-control mandColorClass" data-rule-required="true" /></td>
												
												<td><form:input path="bidMasterDto.itemRateBidDetailDtos[${d}].amount" 
														id="amount${d}" class="form-control mandColorClass text-right" data-rule-required="true" 
														readonly="true" /></td>	
											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<td align="right" colspan="4"><b><spring:message
														code="work.estimate.total.amount" text="Total Amount" /></b></td>
											<td><form:input  type="input" class="form-control mandColorClass text-right"
													readonly="true" id="quotedPrice" path="" /></td>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
				</div>
				
				<div class="text-center margin-bottom-10">
				<c:if test="${command.saveMode ne 'V'}">
					<button type="button" class="btn btn-success"
						onclick="saveTechForm(this)"
						title="<spring:message code="works.management.save" text="Save"></spring:message>">
						<i class=" padding-right-5" aria-hidden="true"></i>
						<spring:message code="works.management.save" text="Save"></spring:message>
					</button>
				</c:if>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="resetForm()" id="button-Cancel"
						title="<spring:message code="works.management.back" text="Back"></spring:message>"
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="Back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>
