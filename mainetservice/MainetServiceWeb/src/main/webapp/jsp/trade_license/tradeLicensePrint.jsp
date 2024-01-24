<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/trade_license/tradeLicenseReportFormat.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="trade.license.print"
					text="Trade License Print" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="TradeLicenseReportFormat.html" class="form-horizontal"
				id="tradeLicensePrint" name="tradeLicensePrint">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<%-- <form:hidden path="orgId" id="orgId" /> --%>
				
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="license.no" /></label>
					<div class="col-sm-4">
						<form:select path="tradeDetailDTO.trdLicno" id="trdLicno"
							class="form-control mandColorClass chosen-select-no-results"
							onchange="">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.tradeMasterDetailDTO}"
								var="activeProjName">
								<form:option value="${activeProjName.trdLicno}"
									code="${activeProjName.trdLicno}">${activeProjName.trdLicno}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
				</div>
				
				
				<div class="text-center clear padding-10">
				
				<button type="button" class="btn btn-success btn-submit"  id="save" onclick="viewLicense(this);"><spring:message code="trade.submit" /></button>
					<%-- <button type="button" id="save" class="btn btn-success btn-submit"
						onclick="viewWorkReport(this);">
						<spring:message code="mileStone.submit" text="Submit" />
					</button> --%>
					<button type="Reset" class="btn btn-warning" id="resetform"
										onclick="resetLicenseForm()">
										<spring:message code="trade.reset" />
									</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
