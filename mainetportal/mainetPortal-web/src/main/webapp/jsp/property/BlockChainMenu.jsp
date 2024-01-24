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
	<%-- 			<div class="form-group">
					<label class="col-sm-2 control-label " for="Property No."><spring:message
							code="propertydetails.PropertyNo." /></label>
					<div class="col-sm-4">

						<form:input path="" cssClass="form-control " id="propNo"
							disabled="" />

					</div>
					</div> --%>

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchOwnershipDetails('BlockChain.html', 'searchPropertyOwnerDetails')">
						<i class="fa fa-search"></i>
						<spring:message code="" text="Property Search" />
					</button>
				</div>
				<!-- End button -->
	<%-- 			<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2"
						onclick="initiateTransfer('BlockChain.html', 'initiateTransfer')"
						id="intTransfer">
						<spring:message code="property.Initiate.Transfer"
							text="Initiate Transfer" />
					</button>
				</div> --%>
				<div class="text-center clear padding-10">
				<button type="button" class="btn btn-warning "
						onclick="printReport('BlockChain.html', 'blockChainSearchPrint')" id="btnSave">
						<spring:message code="" text="Download Property Cards" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>





