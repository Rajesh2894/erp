<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/additionalServices/duplicateReceiptForm.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="receipt.title" text=" Receipt Details" />
			</h2>
			<apptags:helpDoc url="DuplicatePaymentReceipt.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="cfc.mandatory.message"
						text="Field with * is mandatory" /></span>
			</div>
			<form:form id="duplicatePaymentReceipt" action="DuplicatePaymentReceipt.html" method="post"
				class="form-horizontal">
				<form:hidden path="rmRcptid" id="rmRcptid"/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>



				<h4 class="margin-top-0">
					<spring:message code="NHP.applicant.detail" text="Applicant Detail" />
				</h4>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.title" text="Title" /></label>
					<div class="col-sm-4">
						<form:select path="cfcApplicationMst.apmTitle"
							class="form-control" id="titleId">
							<c:set var="baseLookupCode" value="TTL" />

							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.first.name" text="First Name" /></label>
					<div class="col-sm-4">
						<form:input path="cfcApplicationMst.apmFname" id="firstName"
							class="form-control mandColorClass hasNameClass" maxlength="100" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.middle.name" text="Middle Name" /></label>
					<div class="col-sm-4">
						<form:input path="cfcApplicationMst.apmMname" id="midName"
							class="form-control mandColorClass hasNameClass" maxlength="100" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.last.name" text="Last Name" /></label>
					<div class="col-sm-4">
						<form:input path="cfcApplicationMst.apmLname" id="lName"
							class="form-control mandColorClass hasNameClass" maxlength="100" />
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.gender" text="Gender" /></label>
					<div class="col-sm-4">
						<form:select path="cfcApplicationMst.apmSex" class="form-control"
							id="gender">
							<c:set var="baseLookupCode" value="GEN" />
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpCode}"
									code="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
					<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.address.mobileNumber" text="Mobile Number" /></label>

					<div class="col-sm-4">
						<form:input path="cfcAppAddressEntity.apaMobilno"
							id="mobNumber" class="form-control mandColorClass hasNumber"
							maxlength="10" />
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.address.emailId" text="Email ID" /></label>

					<div class="col-sm-4">
						<form:input path="cfcAppAddressEntity.apaEmail"
							id="emailId" class="form-control mandColorClass" maxlength="50" />
					</div>

				</div>
				
				
					<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="cfc.receipt.address" text="Address" /></label>

					<div class="col-sm-4">
						<form:input path="cfcAppAddressEntity.apaAreanm"
							id="apaAreanm" class="form-control mandColorClass" maxlength="100" />
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="NHP.address.pinCode" text="PinCode" /></label>
					<div class="col-sm-4">
						<form:input path="cfcAppAddressEntity.apaPincode"
							id="pinCode" class="form-control mandColorClass hasNumber"
							maxlength="6" />
					</div>

				</div>
				
				<h4 class="margin-top-0"><spring:message code="cfc.dup.receipt.form" text="Receipt Detail" /></h4>
				
				
				<div class="form-group">

					<label for="text-1" class="col-sm-2 control-label required-control"><spring:message
							code="receipt.dept" text="Department" /> </label>
					<div class="col-sm-4">

						<form:select path="receiptMasBean.dpDeptId" class="form-control chosen-select-no-results"
							label="Select" id="deptId">
							 <c:choose>
								<c:when test="${command.langId eq 1}">
								<form:option value=""><spring:message code="cfc.report.select" text="Select" /></form:option>
									<c:forEach items="${command.departmentList}" var="dept">
						        		<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpDeptdesc}</form:option>
						        	</c:forEach>
								</c:when>
								<c:otherwise>
								<form:option value=""><spring:message code="cfc.report.select"  text="Select" /></form:option>
									<c:forEach items="${command.departmentList}" var="dept">
							        	<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpNameMar}</form:option>
						         	</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="receipt.finYear" text="Financial Year"></spring:message>
					</label>

					<div class="col-sm-4">
						<%-- <div class="input-group">
							<form:input name="" Class="datepicker form-control" path="receiptMasBean.rmDate" id="rmDate" 
								maxLength="10" /> <label for="rmDatetemp"
								class="input-group-addon"> <i class="fa fa-calendar"></i><span
								class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span><input
								type="hidden" id="rmDate"></label>
						</div> --%>
						<form:select path="receiptMasBean.details"
							class="form-control chosen-select-no-results" label="Select"
							id="finYearId">

							<form:option value="">
								<spring:message code="cfc.report.select" text="Select" />
							</form:option>
							<c:forEach items="${command.finYearList}" var="finYear">
								<form:option value="${finYear}" code="${finYear}">${finYear}</form:option>
							</c:forEach>

						</form:select>
					</div>
					
					
				</div>
				<p align="center">
					<strong><i class="text-red-1"><spring:message
								code="trd.loircpt.note"
								text="You Must  Enter LOI Number or Reciept Number" /></i></strong>
				</p>
				<div class="form-group">
				<label class="control-label col-sm-2 required-control"> <spring:message
							code="receipt.no"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.rmReceiptNo" id="rmRcptno" class="form-control" maxLength="12" />
					</div>
				<label class="col-sm-2 control-label required-control"><spring:message
							code="cfc.loi.no" text="LOI Number" /></label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.rmLoiNo" id="loiNo" class="form-control hasNumber"
							maxlength="32" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label  col-sm-2"> <spring:message
							code="receipt.amount" text="Receipt Amount" />
					</label>
					<div class="col-sm-4">
					<form:input path="receiptMasBean.rmAmount" id="rmAmount" class="form-control" onkeypress="return hasAmount(event, this, 12, 2)" />
					</div>
					<label class="control-label  col-sm-2"> <spring:message
							code="receipt.name"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.rmReceivedfrom" id="rmReceivedfrom" class="form-control hasNameClass" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="receipt.appno" text="Application No." /></label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.apmApplicationId" id="appNo" class="form-control hasNumber"
							maxlength="100" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="receipt.refno" text="Reference No" /></label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.refId" id="refNo" class="form-control hasNumber"
							maxlength="100" />
					</div>
				</div>

                <form:hidden path="paymentCheck" id="paymentCheck"/>
				<c:if test="${command.paymentCheck ne null && command.paymentCheck eq 'Y'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="cfc.field.name.amounttopay" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" path=""
								value="${command.offlineDTO.amountToShow}" maxlength="12"
								readonly="true"></form:input>
							<a class="fancybox fancybox.ajax text-small text-info"
								href="DuplicatePaymentReceipt.html?showChargeDetails"><spring:message
									code="cfc.lable.name.chargedetail" /> <i
								class="fa fa-question-circle "></i></a>
						</div>
					</div>

					<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
				</c:if>



				<c:if test="${command.paymentCheck ne null}">

					<div class="padding-bottom-10 text-center">

						<button type="button" class="btn btn-submit" id="continueForm"
							onclick="proceedToSaveDetails(this);">
							<spring:message code="NHP.submit"  text="Submit"/>
						</button>

						<button type="button" class="btn btn-danger" id="back"
							onclick="backPage()">
							<spring:message code="NHP.back" text="Back"></spring:message>
						</button>

					</div>

				</c:if>


				<c:if test="${command.paymentCheck eq null}">
					<div class="padding-top-10 text-center">
						<button type="button" class="btn btn-submit" id="continueForm"
							onclick="getCharges(this);">
							<spring:message code="NHP.proceed" text="Proceed"/>
						</button>

						<button type="button" class="btn btn-warning" title="Reset"
							onclick="ResetForm()">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="NHP.reset" text="Reset"></spring:message>
						</button>
						<button type="button" class="btn btn-danger" id="back"
							onclick="backPage()">
							<spring:message code="receipt.back" text="Back"></spring:message>
						</button>

					</div>
				</c:if>
				
			</form:form>

		</div>

	</div>
</div>
