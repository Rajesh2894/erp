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
<script type="text/javascript"
	src="js/trade_license/tradeLicenseReportFormat.js"></script>
<script type="text/javascript" src="js/common/dsc.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<style>
.btn.btn-primary > a {
color: #ffffff;
}
</style>

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
			<form:form action="TradeLicenseReportFormat.html"
				class="form-horizontal" id="tradeLicensePrint"
				name="tradeLicensePrint">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">
					<spring:message code="license.no" text="Application no" var="appNo" />
					<apptags:input labelCode="license.no"
						path="tradeDetailDTO.trdLicno" cssClass="preventSpace"></apptags:input>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" id="serchBtn"
						onclick="SearchLicense(this)">
						<i class="fa fa-search"></i>
						<spring:message code="property.changeInAss.Search" />
					</button>

					<button type="button" class="btn btn-warning resetSearch"
						onclick="window.location.href = 'TradeLicenseReportFormat.html?PrintCertificate'">
						<spring:message code="property.reset" text="Reset" />
					</button>
				</div>
				
				<div>
				<select name="ddl1" id="ddl1">
				<option value="0">Select Certificate</option>
				</select>
				</div>

				<div class="table-responsive clear">
				<spring:eval
				expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('C3','CRD',${UserSession.organisation}).getOtherField()" var="otherField"/>
					<table class="table table-striped table-bordered"
						id="LicensePrintCertificateTable">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message
										code="propertyTax.SrNo" text="Sr.No" /></th>
								<th width="20%" align="center"><spring:message
										code="trade.applicationNo" text="Application No" /></th>
								<th width="20%" align="center"><spring:message
										code="license.no" text="License No" /></th>
								<th width="30%" align="center"><spring:message code="print"
										text="Print" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${data}" var="birth" varStatus="item">
								<tr>
									<td class="text-center">${item.count}</td>
									<td>${birth.apmApplicationId}</td>
									<td>${birth.trdLicno}</td>
									<td class="text-center">
									<button type="button" class="btn btn-primary btn-sm"
										 onclick="signCertificate('${birth.trdLicno}','${birth.trdLicno}',${birth.apmApplicationId},'TradeLicenseReportFormat.html','${otherField}')">
										 <spring:message code="property.sign.certificate" text="Sign Certificate" />
										</button>
										
										<button type="button" class="btn btn-primary btn-sm"
											onclick="email('${birth.trdLicno}')">
											<spring:message code="trade.emailCertificate" />
										</button>
										
									<c:if
											test="${not empty command.attachDocsList}">
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<%-- <c:if test="${(birth.trdLicno eq lookUp.idfId)}"> --%>
													<button type="button" title="Download" class="btn btn-primary btn-sm">
														<apptags:filedownload filename="${lookUp.attFname}"
															dmsDocId="${lookUp.dmsDocId}"
															filePath="${lookUp.attPath}"
															actionUrl="TradeLicenseReportFormat.html?Download" />
													</button>
												<%-- </c:if> --%>
											</c:forEach>
										</c:if> <c:if test="${empty command.attachDocsList}">
											<button type="button" class="btn btn-primary btn-sm"
												onclick="printCertificate('${birth.trdLicno}')">
												<spring:message code="trade.downloadCertificate" />
											</button>
										</c:if>
										
										<%-- <button type="button" class="btn btn-primary btn-sm"
											onclick="uploadSignCertificate('${birth.trdLicno}')">
											<spring:message code="trade.uploadSignCertificate"
												text="Upload Signed Certificate" />
										</button> --%></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>
