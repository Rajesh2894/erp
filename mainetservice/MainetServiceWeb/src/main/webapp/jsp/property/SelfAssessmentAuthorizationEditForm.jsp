<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- <script src="js/property/propertyTax.js" type="text/javascript"></script> -->
<script type="text/javascript" 	src="js/property/propertyAuthorization.js"></script>
<!-- <script type="text/javascript" src="js/property/ownerDetail.js"></script> -->



<ol class="breadcrumb">
	<li><a href="AdminHome.html"><span class="hide">Home</span><i
			class="fa fa-home"></i></a></li>
	<li>Self Assessment Authorization Form</li>
	<li>Self-Assessment Authorization Edit Form</li>
</ol>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>Self Assessment Form</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"><span class="hide">Help</span></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>
			<form:form action="SelfAssessmentAuthorizationForm.html"
				class="form-horizontal form" name="SelfAssessmentAuthorizationEditForm"
				id="SelfAssessmentAuthorizationForm">

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="property.buildingpermission" /> </label>
					<div class="col-sm-4">
						<form:select id="" path=""
							class="form-control chosen-select-no-results">
							<form:option value="">
								<spring:message code="property.sel.optn" text="" />
							</form:option>
						</form:select>
					</div>
				</div>
				
				<form:hidden path="entity.apmApplicationId" value="${command.entity.apmApplicationId}"/>
				<form:hidden path="entity.proAssNo" value="${command.entity.proAssNo}"/>
				
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					
					
					<jsp:include
						page="/jsp/property/SelfAssessmentOwnershipDetailAuthForm.jsp"></jsp:include>

					<jsp:include
						page="/jsp/property/SelfAssessmentPropertyDetailsAuthForm.jsp"></jsp:include>

					<jsp:include
						page="/jsp/property/SelfAssessmentPropertyAddressAuthForm.jsp"></jsp:include>

					<jsp:include
						page="/jsp/property/SelfAssessmentLastPaymentDetailAuthForm.jsp"></jsp:include>

					<jsp:include
						page="/jsp/property/SelfAssessmentBuildingDetailAuthForm.jsp"></jsp:include>

					<jsp:include
						page="/jsp/property/SelfAssessmentUnitDetailAuthForm.jsp"></jsp:include>

					<jsp:include
						page="/jsp/property/SelfAssessmentMappingFactorAuthForm.jsp"></jsp:include>


				</div>
				<div class="text-center padding-10">
					<button type="button" class="btn btn-success btn-submit" id="ProceedSelfAssForm">Proceed</button>
				</div>

			</form:form>
		</div>
	</div>
</div>