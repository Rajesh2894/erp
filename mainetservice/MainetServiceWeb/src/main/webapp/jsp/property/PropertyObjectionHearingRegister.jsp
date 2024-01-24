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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/PropertyAssessmentList.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><b>Objection & Hearing Register</b></h2>				
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
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">* </i><spring:message code="property.ismandatory"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<form:form action="PropertyObjectionHearingRegister.html"
					class="form-horizontal form" name="PropertyObjectionHearingRegister"
					id="PropertyObjectionHearingRegister">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			
			<!--------------------------------------------- Selection Criteria ------------------------------------------------------------->
					<div class="accordion-toggle ">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" href="#a1"><spring:message code="property.report.SelectionCriteria"/></a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
			
						<div class="form-group padding-top-10">
 							<apptags:radio radioLabel="Single/Multiple, All" radioValue="SM,AL" labelCode="property.report.SelectionCriteria" path="specialNotGenSearchDto.specNotSearchType" isMandatory="true" changeHandler="showSingleMultiple()"></apptags:radio> 
 						</div>
 					
						
						<div class="form-group">
						Objection Type====================
<%-- 							<apptags:select labelCode="Objection Type" items="" path="" selectOptionLabelCode="Selection Objection Type"></apptags:select> --%>
						
						</div>
						
						<div class="form-group PropDetail">
							<apptags:textArea labelCode="propertydetails.PropertyNo." path="specialNotGenSearchDto.propertyNo" isMandatory="true" cssClass="hasNumber"></apptags:textArea>
							<apptags:textArea labelCode="propertydetails.oldpropertyno" path="specialNotGenSearchDto.oldPropertyNo" isMandatory="true" cssClass="hasNumber"></apptags:textArea>
						</div>
						
				
						
						<div class="form-group wardZone">

									<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
										showOnlyLabel="false"
										pathPrefix="specialNotGenSearchDto.assWard" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control " showAll="false"
										showData="true" />
						</div>
						
						<div class="form-group">
							
									<label for="fromDate" class="col-sm-2 control-label required-control">From Date</label>
									<div class="col-sm-4">
									<form:input type="text" path="" class="form-control lessthancurrdate mandColorClass" id="fromDate" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off" />																		
									</div>
									<label for="toDate" class="col-sm-2 control-label required-control">To Date</label>
									<div class="col-sm-4">
									<form:input type="text" path="" class="form-control lessthancurrdate mandColorClass" id="toDate" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off" />
									</div>
						</div>
						
						
						</div>
						
						<div class="form-group">
							<div class="text-center padding-bottom-10">
								<button type="button" class="btn btn-blue-2 " id="submit"
									onclick="submitAssessmentList(this)">								
									<spring:message code="propertyBill.Submit" />
								</button>

								<button type="button" class="btn btn-warning " id="resetBtn"
									onclick="resetForm(this)" value="Reset">		
									<spring:message code="property.reset" />
								</button>
							</div>
						</div>
						
						
					</div>
					</div>
		</form:form>
		
	</div>
	
	</div>
</div>