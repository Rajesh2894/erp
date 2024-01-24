<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/updateSalesDetails.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.updatesaledetails"
						text="Update Sale Details" /></strong>
			</h2>
			<apptags:helpDoc url="UpdateSaleDetails.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand" /><i
					class="text-red-1">* </i> <spring:message
						code="solid.waste.mand.field" /> </span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="UpdateSaleDetails.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="UpdateSaleDetailsForm" id="id_UpdateSaleDetailsForm">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="swm.updatesaledetails"
										text="Update Sale Details" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:choose>
									<c:when test="">
									</c:when>
									<c:otherwise>
										<div class="form-group">

											<apptags:date fieldclass="datepicker"
												labelCode="swm.day.wise.month.report.date" datePath=""
												cssClass="datepicker ">
											</apptags:date>
											<label class="col-sm-2 control-label required-control"
												for="desposalsite"><spring:message
													code="swm.dsplsite" /> </label>
											<div class="col-sm-4">
												<form:select path=""
													class="form-control mandColorClass chosen-select-no-results"
													label="Select" id="deId">
													<form:option value="0">
														<spring:message code="solid.waste.select" text="select" />
													</form:option>
													<c:forEach items="${command.disposalMasterList}"
														var="lookup">
														<form:option value="${lookup.deId}">${lookup.deName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control" for=""><spring:message
													code="swm.purchaser" text="Purchaser Name" /></label>
											<div class="col-sm-4">
												<form:select path=""
													class="form-control  chosen-select-no-results"
													label="Select" id="empId">
													<form:option value="0">
														<spring:message code="solid.waste.select" text="select" />
													</form:option>
													<c:forEach items="${command.vendorList}" var="lookup">
														<form:option value="${lookup.vmVendorid}">${lookup.vmVendorname}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a2" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"> <spring:message
											code="swm.breakup" text="Break Up " /></a>
								</h4>
							</div>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="id_updateSaleDetailsTable">
										<thead>
											<tr>
												<th scope="col" width="5%"><spring:message
														code="population.master.srno" text="Sr.No." /></th>


												<th scope="col" width="10%"><spring:message
														code="swm.wasteRate.itemList" text="Item List" /></th>


												<th scope="col" width="10%"><spring:message
														code="swm.sales.update.Details.stock.quantity"
														text="Stock Quantity" /></th>

												<th scope="col" width="10%"><spring:message
														code="swm.wasteRate.rate" text="Rate" /></th>											
												<th scope="col" width="10%"><spring:message
														code="swm.wasteRate.solid.quantity" text="Sold Quantity" /></th>
												<th scope="col" width="10%"><spring:message
														code="swm.wasteRate.solid.amount" text="Amount" /></th>

												<th scope="col" width="5%%"><a
													href="javascript:void(0);" data-toggle="tooltip"
													title="Add" data-placement="top"
													onclick="addEntryData('id_updateSaleDetailsTable');"
													class=" btn btn-success btn-sm"><i
														class="fa fa-plus-circle"></i></a></th>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
												<th align="right"><spring:message
														code="swm.wasteRate.solid.total.amount"
														text="Total Amount :" /></th>
												<th align="right">
													<div class="input-group" align="right">
														<input type="text"
															class="form-control mandColorClass text-right"
															id="id_total" disabled>
													</div>
												</th>											
												<th></th>												
											</tr>
										</tfoot>
										<tbody>
											<tr>
												<td align="center" width="5%"><form:input path=""
														cssClass="form-control mandColorClass " id="sequence${d}"
														value="${d+1}" disabled="true" /></td>

												<td align="center" width="10%"><form:select path=""
														cssClass="form-control mandColorClass" id="item${d}"
														onchange="" disabled="" data-rule-required="true">
														<form:option value="">Select</form:option>
														<c:forEach items="${command.getSecondLevelData('WTY',3)}"
															var="lookup">
															<form:option value="${lookup.lookUpId}"
																code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>

														</c:forEach>
													</form:select></td>

												<td align="center" width="10%">
													<div class="input-group">
														<form:input path=""
															cssClass="form-control  mandColorClass hasDecimal text-center"
															onchange="" id="stockquantity${d }" disabled="" />
														<span class="input-group-addon"><spring:message
																code="swm.kilo" text="Kgs" /></span>
													</div>
												</td>
												<td align="center" width="10%">
													<div class="input-group">
														<form:input path=""
															cssClass="form-control  mandColorClass hasDecimal text-center"
															onblur="getAmount()" id="rate${d }" disabled="" />
														<span class="input-group-addon"><spring:message
																code="swm.wasteRate.solid.perkg" text="Per Kg" /></span>
													</div>
												</td>
												<td align="center" width="10%">
													<div class="input-group">
														<form:input path=""
															cssClass="form-control  mandColorClass hasDecimal text-center"
															onblur="getAmount()" id="soldquantity${d }" disabled="" />
														<span class="input-group-addon"><spring:message
																code="swm.kilo" text="Kgs" /></span>
													</div>
												</td>
												<td align="center" width="10%">
													<div class="input-group">
														<form:input path=""
															cssClass="form-control  mandColorClass hasDecimal text-right"
															onchange="" id="amount${d }" disabled="" />
													</div>
												</td>
												<td align="center" width="5%"><a
													class="btn btn-danger btn-sm delButton" title="Delete"
													onclick="deleteEntry('id_updateSaleDetailsTable',$(this),'sequence${d}')">
														<i class="fa fa-minus"></i>
												</a></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="Proceed(this)" id="btnSave">
						<spring:message code="solid.waste.submit" text="Submit" />
					</button>
					<apptags:resetButton></apptags:resetButton>
					<apptags:backButton url="UpdateSaleDetails.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
