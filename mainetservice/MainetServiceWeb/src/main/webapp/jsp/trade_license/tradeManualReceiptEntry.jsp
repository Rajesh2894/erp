<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/trade_license/tradeManualReceiptEntry.js"></script>
<div id="dataDiv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<!-- Start Content here -->
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="trade.manualRecpt" />
				</h2>

			</div>
			<div class="widget-content padding">
				<form:form action="TradeManualReceiptEntry.html"
					class="form-horizontal" name="TradeManualReceiptEntry"
					id="TradeManualReceiptEntry">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<div class="form-group">
						<apptags:input labelCode="license.details.newLicenseNo"
							path="tradeMasterDetailDTO.trdLicno"></apptags:input>
						<apptags:input labelCode="license.details.OldLicenseNo"
							path="tradeMasterDetailDTO.trdOldlicno"></apptags:input>
					</div>

					<div class="form-group">
						<div class="text-center padding-bottom-10">
							<button type="button" class="btn btn-blue-2" id="serchBtn"
								onclick="SearchButton(this)">
								<i class="fa fa-search"></i>
								<spring:message code="bt.search" text="Search" />
							</button>

							<button type="button" id="reset"
								onclick="window.location.href='TradeManualReceiptEntry.html'"
								class="btn btn-warning" title="Reset">
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
								<spring:message code="bt.clear" text="Reset" />
							</button>

							<button type="button" id="back"
								class="button-input btn btn-danger" name="button-Cancel"
								value="Cancel" style=""
								onclick="window.location.href='AdminHome.html'"
								id="button-Cancel">
								<i class="fa fa-chevron-circle-left padding-right-5"></i>
								<spring:message code="bt.backBtn" text="Back" />
							</button>

						</div>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>