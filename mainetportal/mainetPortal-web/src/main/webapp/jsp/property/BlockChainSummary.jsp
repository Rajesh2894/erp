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
				<strong><spring:message code="property.Ownershipdetail"
						text="OwnerShip Details" /></strong>
			</h2>
			<apptags:helpDoc url="BlockChain.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="BlockChain.html" commandName="command"
				class="form-horizontal form" name="blockChain"
				id="id_ownerShipDetails">
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->
				<div class="form-group">
					<label class="col-sm-2 control-label " for="Property No."><spring:message
							code="propertydetails.PropertyNo." /></label>
					<div class="col-sm-4">

						<form:input path="" cssClass="form-control " id="propNo"
							disabled="" />

					</div>
					</div>
<%-- 					<label class="col-sm-2 control-label " for="ownerName"><spring:message
							code="ownerdetail.Ownername" text="Owner Name" /></label>
					<div class="col-sm-4">

						<form:input path="" cssClass="form-control  hasCharacter"
							id="ownerName" disabled="" />

					</div>
				</div>
				<div class="form-group">
					<div class=" padding-top-10">
						<label class=" col-sm-12 text-center text-bold text-red" for=""><spring:message
								code="" text="OR" /></label>

					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label " for="oldPropNo"><spring:message
							code="propertydetails.oldpropertyno" text="Old Property No." /></label>
					<div class="col-sm-4">

						<form:input path="" cssClass="form-control " id="oldpropNo"
							disabled="" />

					</div>

				</div> --%>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchOwnershipDetails('BlockChain.html', 'searchPropertyOwnerDetails')">
						<i class="fa fa-search"></i>
						<spring:message code="property.search" text="Search" />
					</button>
				</div>
				<!-- End button -->
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="property.Ownershipdetail"
										text="Ownership Detail" />
								</a>
							</h4>
						</div>
						
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<div class="table-responsive margin-top-10">
										<table
											class="table table-striped table-condensed table-bordered"
											id="id_SummaryTable">
											<thead>
												<tr>
												<th><spring:message code=""
															text="Property No" /></th>
													<th><spring:message code="ownerdetail.Ownername"
															text="Owners Name" /></th>
													<th><spring:message code=""
															text="Owner Contact No" /></th>
													<th><spring:message code="ownerdetail.OwnerAdd"
															text="Owner Address" /></th>
													<th><spring:message code=""
															text="PAN No." /></th>
												</tr>
											</thead>
											<tbody>
											
													<tr>
														<td align="center">${command.blockChainDTO.propNo}</td>
														<td align="center">${command.blockChainDTO.ownerDetails[0].ownerName}</td>
														<td align="center">${command.blockChainDTO.ownerDetails[0].ownerContactNo}</td>
														<td align="center">${command.blockChainDTO.ownerDetails[0].ownerAddress}</td>
														<td align="center">${command.blockChainDTO.ownerDetails[0].ownerPANNo}</td>
													</tr>
											
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2"
						onclick="initiateTransfer('BlockChain.html', 'initiateTransfer',this)"
						id="intTransfer">
						<spring:message code="property.Initiate.Transfer"
							text="Initiate Transfer" />
					</button>
					<apptags:backButton url="BlockChain.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>





