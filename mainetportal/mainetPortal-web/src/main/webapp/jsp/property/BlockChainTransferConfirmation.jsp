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
									<spring:message code="" text="Owner Details" />
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
											<th><spring:message code="" text="Property No" /></th>
											<th><spring:message code="ownerdetail.Ownername"
													text="Ownership Detail" /></th>
											<th><spring:message code="" text="Owner Contact No" /></th>
											<th><spring:message code="ownerdetail.OwnerAdd"
													text="Owner Address" /></th>
											<th><spring:message code="" text="Date" /></th>
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
											<td align="center">${command.blockChainDTO.propNo}</td>
											<td align="center">${command.blockChainDTO.ownerDetails[0].ownerName}</td>
											<td align="center">${command.blockChainDTO.ownerDetails[0].ownerContactNo}</td>
											<td align="center">${command.blockChainDTO.ownerDetails[0].ownerAddress}</td>
											<td align="center">${command.blockChainDTO.ownerDetails[0].ownerPANNo}</td>

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
									<spring:message code="property.transferTo" text="Transfer To" />
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

											<th><spring:message code="" text="Owner Name" /></th>
										
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
											<tr>										
											<td align="center">${command.blockChainDTO.witnessDetails[2].witnessName}</td>
											
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
				</div>

				<div class="text-center clear padding-10">
			<%-- 		<button type="button" class="btn btn-black" onclick="" id="btnSave">
						<spring:message code="" text="City Survey Verify" />
					</button> --%>
					<button type="button" class="btn btn-success" 
						id="btnSave">
						<spring:message code="" text="City Survey Verified" />
					</button>
				</div>

				<div class="text-center clear padding-10">
			<%-- 		<button type="button" class="btn btn-success"
						onclick="initiateTransfer('BlockChain.html', 'initiateTransferConfirmation')" id="btnSave">
						<spring:message code="" text="Submit" />
					</button>
					<button type="button" class="btn btn-black"
						onclick="printReport('BlockChain.html', 'blockChainPrint',witnessName${1})"
						id="btnSave">
						<spring:message code="" text="Print" />
					</button> --%>
				<%-- 	<button type="button" class="btn btn-danger"
						onclick="back('BlockChain.html', 'back')" id="btnSave">
						<spring:message code="" text="Back" />
						</button> --%>
						<apptags:backButton url="BlockChain.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>





