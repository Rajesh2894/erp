<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/property/propertyAssessmentType.js"></script> 
<div class="dataDiv">


<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	
	<div class="widget-header">
				<h2><strong>Assessment Type</strong></h2>				
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
		<form:form action="PropertyAssessmentType.html"
					class="form-horizontal form" name="propertyAssessmentType"
					id="propertyAssessmentType">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
			
			
				<div class="form-group">
					 <label class="col-sm-2 control-label">Assessment Type<span class="mand">*</span>
								</label>

					<div class="col-sm-3">
									<form:select path=""
											onchange="getAssessmentType()" class="form-control mandColorClass" id="selectedAssType" data-rule-required="true">
												<form:option value="0">Select Assessment Type</form:option>
												<form:option value="1" code="C">Change In Assessment</form:option>
												<form:option value="2" code="NC">No Change In Assessment</form:option>
									</form:select>
					</div>
					
					
				</div>	
				<div class="form-group">
					<div class="ShowAssessmentType">
					
					</div>
				</div>
	
		</form:form>
	</div>
	</div>
</div>
</div>