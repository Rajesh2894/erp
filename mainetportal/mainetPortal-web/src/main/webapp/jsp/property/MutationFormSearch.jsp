<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/property/mutation.js"></script>

<div id="validationDiv">

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="propertyTax.Mutation" text="Mutation"/></strong></h2>				
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="property.Help"/></span></i></a>
				</div>
	</div>
	
	<!-- End Main Page Heading -->
		
<!-- Start Widget Content -->
	<div class="widget-content padding">
	
		
		<!-- Start mand-label -->
		<div class="mand-label clearfix">
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">*</i><spring:message code="property.ismandatory"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<form:form action="MutationForm.html"
					class="form-horizontal form" name="MutationForm"
					id="MutationForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			<div class="padding-10"><strong><i><spring:message code="property.EnterDetailstosearchproperty" /></i></strong></div>
			
			
			<!-- Start Each Section -->
			<div class="form-group">

					<apptags:input labelCode="property.ChangeInAss.EnterPropertyNo" path="provisionalAssesmentMstDto.assNo" maxlegnth="16" cssClass="mandColorClass hasNumber" ></apptags:input>
					<apptags:input labelCode="property.OldPropertyNo" path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="16" cssClass="mandColorClass hasNumber"></apptags:input>	
					
                   	
					<!--Search Button-->
                   	<div class="text-center padding-15 clear">                   	
                   	<button type="button" class="btn btn-warning" onclick="getMutationDetail()"><spring:message code="property.search" text=""/></button>	
                   	</div>
                   	
			</div> 
			<!-- End Each Section -->
			
<div id="mutation">
</div>		
			
			
			
			
		</form:form>
		<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
		</div>
		<!-- End Widget  here -->							
</div>
<!-- End of Content -->
</div>

