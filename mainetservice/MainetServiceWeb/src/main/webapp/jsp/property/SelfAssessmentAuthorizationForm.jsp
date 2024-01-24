<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/property/propertyAuthorization.js"></script>
<!-- <script type="text/javascript" src="js/property/ownerDetail.js"></script> -->
<div class="contentTot">
	<ol class="breadcrumb">
		<li><a href="AdminHome.html"><span class="hide">Home</span><i
				class="fa fa-home"></i></a></li>
		<li>Self Assessment Form</li>
		<li>Self Assessment Authorization</li>
	</ol>

	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>Self Assessment Authorization</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide">Help</span></i></a>
				</div>
			</div>
			<div class="widget-content padding">

				<form:form action="SelfAssessmentAuthorization.html"
					class="form-horizontal form" name="SelfAssessmentAuthorizationForm"
					id="SelfAssessmentAuthorizationForm">

					<div class="form-group">

						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="propertydetails.ApplicationNo" /></label>
						<div class="col-sm-4">
							<form:input class="form-control" path="entity.apmApplicationId"
								id="applicationNo" />
						</div>

						<label for="text-1492065773053" class="col-sm-2 control-label"><spring:message
								code="propertydetails.propertyno" /></label>
						<div class="col-sm-4">
							<form:input id="propertyNo" path="entity.proAssNo" class="form-control" />
						</div>

					</div>

					<div class="form-group">

						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="property.OwnerName" /></label>
						<div class="col-sm-4">
							<form:input id="ownerName" path="entity.assOwnerName" class="form-control" />
						</div>

						<apptags:lookupFieldSet cssClass="form-control required-control"
							baseLookupCode="PTP" hasId="true"
							pathPrefix="entity.proAssPropType" isMandatory="true" />
					</div>

					<div class="form-group">

						<apptags:lookupFieldSet cssClass="form-control required-control"
							baseLookupCode="WZB" hasId="true"
							pathPrefix="entity.proAssWard" isMandatory="true" />

					</div>

					<div class="form-group">

						<%-- <label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="unitdetails.usagefactor" /></label>
						<div class="col-sm-4">
							<form:input path="entity1.assUsagefactor" class="form-control" />
						</div>

						<label for="text-1492065773053" class="col-sm-2 control-label"><spring:message
								code="unitdetails.usagesubtype" /></label>
						<div class="col-sm-4">
							<form:input path="entity1.assUsagesubtype" class="form-control" />
						</div> --%>
						
						<apptags:lookupFieldSet
						cssClass="form-control required-control" baseLookupCode="USA"
						hasId="true" pathPrefix="entity.assdUsagetype" isMandatory="true"
						/>

					</div>

					<div class="form-group">

						<label for="text-1492065734927" class="col-sm-2 control-label"><spring:message
								code="propertydetails.oldpropertyno" /></label>
						<div class="col-sm-4">
							<form:input id="oldPropertyNo" path="entity.proAssOldpropno" class="form-control" />
						</div>

					</div>

					<div class="text-center" id="processBtn">
						<input type="button" id="searchPropertyBasisBtn"
							class="btn btn-success" value="Search" /> <input type="button"
							class="btn btn-warning Clear" id="resetBasisBtn" value="Reset"></input>
					</div>

					<div class="" id="searchData"></div>
				</form:form>
			</div>
		</div>
	</div>
</div>