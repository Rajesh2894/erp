<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/property/mutation.js"></script>
<script type="text/javascript" src="js/common/dsc.js"></script>
 
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
				<strong><spring:message code="propertyTax.Mutation"
						text="Mutation" /></strong>
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="MutationPrintCertificate.html"></apptags:helpDoc>
			</div>
		</div>

		<div class="widget-content padding">
			<form:form action="MutationPrintCertificate.html"
				class="form-horizontal form" name="MutationPrintCertificate"
				id="MutationPrintCertificate">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="form-group">
					<spring:message code="property.PleaseEnterPropertyNo"
						text="Please enter property no" var="propNo" />
					<spring:message code="property.PleaseEnterOldPropertyNo"
						text="Please enter old property no" var="oldPropNo" />
					<apptags:input labelCode="property.ChangeInAss.EnterPropertyNo"
						path="provisionalAssesmentMstDto.assNo" cssClass="preventSpace"
						placeholder="${propNo}"></apptags:input>
					<apptags:input labelCode="property.ChangeInAss.oldpid"
						path="provisionalAssesmentMstDto.assOldpropno"
						cssClass="preventSpace" placeholder="${oldPropNo}"></apptags:input>
				</div>

				<div class="form-group">
					<spring:message code="property.ApplicationNo" text="Application no"
						var="appNo" />
					<apptags:input labelCode="Application no"
						path="provisionalAssesmentMstDto.apmApplicationId"
						cssClass="preventSpace" placeholder="${appNo}"></apptags:input>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" id="serchBtn"
						onclick="SearchMutation(this)">
						<i class="fa fa-search"></i>
						<spring:message code="property.changeInAss.Search" />
					</button>

					<button type="button" class="btn btn-warning resetSearch"
						onclick="window.location.href = 'MutationPrintCertificate.html'">
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
				expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('C1','CRD',${UserSession.organisation}).getOtherField()" var="otherField"/>
					<table class="table table-striped table-bordered"
						id="MutationPrintCertificateTable">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message
										code="propertyTax.SrNo" text="Sr.No" /></th>
								<th width="15%" align="center"><spring:message
										code="property.PropertyNo" text="Property No" /></th>
								<th width="10%" align="center"><spring:message
										code="property.ApplicationNo" text="Application No" /></th>
								<th width="10%" align="center"><spring:message
										code="property.receiptno" text="Receipt No" /></th>
								<th width="10%" align="center"><spring:message
										code="property.CertificateNo" text="Certificate No" /></th>
								<th width="20%" align="center"><spring:message
										code="" text="Print Receipt" /></th>
								<th width="40%" align="center"><spring:message
								code="" text="Print Certificate" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${data}" var="birth" varStatus="item">
								<tr>
									<td class="text-center">${item.count}</td>
									<td>${birth.proAssNo}</td>
									<td>${birth.apmApplicationId}</td>
									<td>${birth.receiptNo}</td>
									<td>${birth.certificateNo}</td>
									<td class="text-center">
									<button class="btn btn-primary btn-sm" type="button"
											onclick="printReceipt('${birth.proAssNo}',${birth.receiptId})">
											<spring:message code="property.downloadReceipt" />
										</button>
										<button class="btn btn-primary btn-sm" type="button"
											onclick="printLoiMutationReceipt('${birth.proAssNo}',${birth.loiReceiptId})">
											<spring:message code="" text="Download Loi Receipt" />
										</button>
									</td>
									<td class="text-center">
										 <c:if test="${not empty birth.certificateNo}">
										 <button type="button" class="btn btn-primary btn-sm"
												onclick="signCertificate('${birth.proAssNo}','${birth.certificateNo}',${birth.apmApplicationId},'MutationPrintCertificate.html','${otherField}')">
												<spring:message code="property.sign.certificate" text="Sign Certificate" />
											</button>
											<button type="button" class="btn btn-primary btn-sm"
												onclick="email('${birth.proAssNo}','${birth.certificateNo}',${birth.apmApplicationId},'MutationPrintCertificate.html')">
												<spring:message code="property.emailCertificate" />
											</button>
											<c:if test="${not empty birth.attachDocsList}">
												<c:forEach items="${birth.attachDocsList}" var="lookUp">
													<c:if test="${lookUp.idfId ne null && birth.apmApplicationId eq lookUp.idfId}">
														<button type="button" title="Download"
															class="btn btn-primary btn-sm">
															<apptags:filedownload filename="${lookUp.attFname}"
																dmsDocId="${lookUp.dmsDocId}"
																filePath="${lookUp.attPath}"
																actionUrl="MutationPrintCertificate.html?Download" />
														</button>
													</c:if>
												</c:forEach>
											</c:if>
											<c:if test="${empty birth.attachDocsList}">
												<button type="button" class="btn btn-primary btn-sm"
													onclick="printCertificate('${birth.proAssNo}','${birth.certificateNo}')">
													<spring:message code="property.downloadCertificate" />
												</button>
											</c:if>
											
											<%-- <button type="button" class="btn btn-primary btn-sm"
												onclick="uploadSignCertificate(${birth.apmApplicationId})">
												<spring:message code="property.uploadSignCertificate" text="Upload Signed Certificate" />
											</button> --%>
											
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>