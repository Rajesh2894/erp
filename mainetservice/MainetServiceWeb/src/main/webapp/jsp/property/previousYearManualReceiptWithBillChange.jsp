<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/property/previousYearManualReceiptWithBillChange.js"></script>

<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code=""
						text="Previous Year Manual Receipt With Bill Change" /></strong>
			</h2>

			<apptags:helpDoc url="PreviousYearManualReceiptWithBillChange.html"></apptags:helpDoc>

		</div>


		<div class="widget-content padding">


			<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /> </span>
			</div>

			<form:form action="SelfAssessmentForm.html"
				class="form-horizontal form" name="frmSelfAssessmentForm"
				id="frmSelfAssessmentForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

					<div class="form-group">
						<apptags:input labelCode="Property No"
							path="provisionalAssesmentMstDto.assNo"></apptags:input>
						<apptags:input labelCode="Old Property No"
							path="provisionalAssesmentMstDto.assOldpropno"></apptags:input>
					</div>

					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2" id="serchBtn"
							onclick="SearchButton(this)">
							<spring:message code="property.changeInAss.Search" />
						</button>
						<button type="button" class="btn btn-warning"
							onclick="javascript:openRelatedForm('PreviousYearManualReceiptWithBillChange.html');"
							title="Reset">
							<spring:message code="rstBtn" text="Reset" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>


					</div>
				<%-- <c:if test="${command.approvalFlag eq 'Y'}"> --%>

				<%-- <div id="" class="panel-collapse collapse in">
					<div class="panel-body">

						<div class="form-group">
							<apptags:input labelCode="propertydetails.PropertyNo."
								path="billPayDto.propNo" cssClass="mandColorClass"
								isReadonly="true"></apptags:input>
							<apptags:input labelCode="propertydetails.oldpropertyno"
								path="billPayDto.oldpropno" cssClass="mandColorClass"
								isReadonly="true"></apptags:input>
						</div>


						<div class="form-group">
							<apptags:input labelCode="ownerdetail.Ownername"
								path="billPayDto.ownerFullName" isDisabled="true"></apptags:input>
											         	<apptags:input labelCode="propertydetails.PropertyNo." path="billPayDto.propNo" isDisabled="true" ></apptags:input>
							<apptags:input labelCode="ownersdetail.mobileno"
								path="billPayDto.mobileNo" cssClass="mandColorClass hasNumber"
								maxlegnth="10"></apptags:input>
						</div>
						<div class="form-group">
						
						<apptags:date labelCode="Manual Receipt Date"
									datePath="manualReeiptDate" fieldclass="datepicker"
									isMandatory="true"></apptags:date>
							<apptags:input labelCode="Receipt Amount"
								path="manualRecptArrearPaidAmnt" cssClass="mandColorClass"></apptags:input>
						</div>
					</div>
				</div>

				<div class="text-center padding-bottom-10" id="updateButtons">
					<button type="button" class="btn btn-success btn-submit"
						id="proceed12" onclick="updaeReviseBill(this)">
						<spring:message code="" text="Update" />
					</button>

				</div> --%>
				<%-- </c:if> --%>

			</form:form>
		</div>
	</div>
</div>


