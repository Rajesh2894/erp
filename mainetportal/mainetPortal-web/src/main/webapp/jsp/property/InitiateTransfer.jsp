<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="js/mainet/validation.js"></script>
<script src="js/property/blockChain.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="property.Initiate.Transfer"
						text="Initiate Transfer" /></strong>
			</h2>
			<apptags:helpDoc url="BlockChain.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
		<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /> </span>
			</div>
			<!-- Start Form -->
			<form:form action="BlockChain.html" commandName="command"
				class="form-horizontal form" name="Initiate Transfer"
				id="id_initiateTransfer">
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="property.transferTo" text="Transfer To" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="1" scope="page"></c:set>
								<table
									class="table table-striped table-condensed table-bordered"
									id="id_transferToTable">
									<thead>
										<tr>
											<th><spring:message code="ownerdetail.Ownername"
													text="Ownership Detail" /></th>
											<th><spring:message code="ownersdetail.contractNo"
													text="Owner Contact No." /></th>
											<th><spring:message code="ownerdetail.OwnerAdd"
													text="Owner Address" /></th>
											<th><spring:message code="property.PAN.TAN.No"
													text="PAN No./TAN No." /></th>
											<th scope="col" width="1%"><a href="javascript:void(0);"
												data-toggle="tooltip" title="Add" data-placement="top"
												onclick="addEntryData('id_transferToTable');"
												class=" btn btn-success btn-sm"><i
													class="fa fa-plus-circle"></i></a></th>
										</tr>
									</thead>
									<tbody>
									<%-- 	<c:forEach items="${blockChainDTO.ownerDetails}" var="prop"
											varStatus="loop"> --%>
											<tr>
											<form:hidden path="" id="sequence${d}"/>
												<td align="center"><form:input
														path="blockChainDTO.ownerDetails[${d}].ownerName"
														cssClass="form-control" id="ownerName${d}" disabled="" /></td>
												<td align="center"><form:input
														path="blockChainDTO.ownerDetails[${d}].ownerContactNo"
														cssClass="form-control" id="ownerContactNo${d}" disabled="" /></td>
												<td align="center"><form:textarea
														path="blockChainDTO.ownerDetails[${d}].ownerAddress"
														cssClass="form-control" id="ownerAddress${d}" disabled="" /></td>
												<td align="center"><form:input
														path="blockChainDTO.ownerDetails[${d}].ownerPANNo"
														cssClass="form-control" id="ownerPANNo${d}" disabled="" /></td>
												<td align="center" width="1%"><a
													class="btn btn-danger btn-sm delButton" title="Delete"
													onclick="deleteEntry('id_transferToTable',$(this),'sequence${d}')">
														<i class="fa fa-minus"></i>
												</a></td>

											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										<%-- </c:forEach> --%>
									</tbody>
								</table>

							</div>
						</div>






						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse2">
									<spring:message code="property.witness" text="Witness" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="1" scope="page"></c:set>
								<table
									class="table table-striped table-condensed table-bordered"
									id="id_witnessTable">
									<thead>
										<tr>
											<th><spring:message code="proprty.witness.Name"
													text="Witness Name" /></th>
											<th><spring:message code="proprty.witness.contactNo"
													text="Witness Contact No." /></th>
											<th><spring:message code="proprty.witness.Address"
													text="Witness Address" /></th>
											<th><spring:message code="property.PAN.TAN.No"
													text="PAN No./TAN No." /></th>
											<th scope="col" width="1%"><a href="javascript:void(0);"
												data-toggle="tooltip" title="Add" data-placement="top"
												onclick="addEntryData('id_witnessTable');"
												class=" btn btn-success btn-sm"><i
													class="fa fa-plus-circle"></i></a></th>
										</tr>
									</thead>
									<tbody>
										<%-- <c:forEach items="${blockChainDTO.witnessDetails}" var="prop"
											varStatus="loop"> --%>
											<tr>
											<form:hidden path="" id="seq${d}"/>
												<td align="center"><form:input
														path="blockChainDTO.witnessDetails[${d}].witnessName"
														cssClass="form-control" id="witnessName${d}" disabled="" /></td>
												<td align="center"><form:input
														path="blockChainDTO.witnessDetails[${d}].witnessContactNo"
														cssClass="form-control" id="witnessContactNo${d}" disabled="" /></td>
												<td align="center"><form:textarea
														path="blockChainDTO.witnessDetails[${d}].witnessAddress"
														cssClass="form-control" id="witnessAddress${d}" disabled="" /></td>
												<td align="center"><form:input
														path="blockChainDTO.witnessDetails[${d}].witnessPANNo"
														cssClass="form-control" id="witnessPANNo${d}" disabled="" /></td>
												<td align="center" width="1%"><a
													class="btn btn-danger btn-sm delButton" title="Delete"
													onclick="deleteEntry('id_witnessTable',$(this),'seq${d}')">
														<i class="fa fa-minus"></i>
												</a></td>

											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
									<%-- 	</c:forEach> --%>
									</tbody>
								</table>
							</div>
						</div>
					</div>


					<div class="form-group">
						<label class="col-sm-2 control-label " for=""><spring:message
								code="property.purposeofTransfer" text="Purpose of Transfer" /></label>
						<div class="col-sm-4">

							<form:input path="blockChainDTO.purpsTrans"
								cssClass="form-control  mandColorClass" id="purposeOfTransfer"
								disabled="" />

						</div>

						<apptags:input labelCode="property.actualTransferDate"
							isReadonly="true" cssClass="datepicker tripdate"
							path="blockChainDTO.actTransDate" isMandatory="true"
							isDisabled="" />

					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="property.marketValue" text="Market Value" /></label>
					<div class="col-sm-4">

						<form:input path="blockChainDTO.marketValue"
							cssClass="form-control  mandColorClass hasDecimal"
							id="marketValue" disabled="" />

					</div>
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="property.salesDeedValue" text="Sales Deed Value" /></label>
					<div class="col-sm-4">

						<form:input path="blockChainDTO.salesDeedValue"
							cssClass="form-control  mandColorClass hasDecimal"
							id="salesDeedValue" disabled="" />

					</div>
				</div>



				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="property.stampDutyCharge" text="Stamp Duty Charges" /></label>
					<div class="col-sm-4">

						<form:input path="blockChainDTO.stampDutyCharges"
							cssClass="form-control  mandColorClass hasDecimal"
							id="stampDutyCharge" disabled="" />

					</div>
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="property.registrationCharge" text="Registration Charges" /></label>
					<div class="col-sm-4">

						<form:input path="blockChainDTO.regiCharges"
							cssClass="form-control  mandColorClass hasDecimal"
							id="registrationCharge" disabled="" />

					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="property.otherCharge" text="Other Charges" /></label>
					<div class="col-sm-4">

						<form:input path="blockChainDTO.otherCharges"
							cssClass="form-control  mandColorClass hasDecimal"
							id="otherCharges" disabled="" />

					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label " for=""><spring:message
							code="property.registrationOfficeDetails"
							text="Registration Office Details" /></label>
					<div class="col-sm-10">
						<form:textarea name="blockChainDTO.regiOfficeDet" path="" cols=""
							rows="" class="form-control" id="regOfficeDetails"></form:textarea>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label " for="paymentReceived"><spring:message
							code="property.paymentReceived" text="Payment Recieved" /></label>
					<div class="col-sm-8">
						<label for="" class="radio-inline"> <form:checkbox
								path="blockChainDTO.paymentReceived" value="" onchange=""
								id="paymentReceived" />
						</label>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-success"
						onclick="Proceed(this)" id="btnSave">
						<spring:message code="" text="Submit" />
					</button>
					<apptags:backButton url="BlockChain.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>





