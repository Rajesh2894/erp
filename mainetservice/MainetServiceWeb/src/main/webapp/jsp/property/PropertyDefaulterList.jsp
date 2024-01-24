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
<script type="text/javascript" src="js/property/defaulterList.js"></script> 

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="property.DefaulterListReport" /></strong></h2>				
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
		<form:form action="PropertyDefaulterList.html"
					class="form-horizontal form" name="defaulterList"
					id="defaulterList">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>

				<div class="accordion-toggle ">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" href="#defaultSelectCriteria"><spring:message code="property.report.SelectionCriteria"/></a>
							</h4>
						</div>

						<div id="defaultSelectCriteria" class="panel-collapse collapse in">
						
								<div class="form-group wardZone margin-top-10">

									<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
										showOnlyLabel="false"
										pathPrefix="specialNotGenSearchDto.assWard" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control " showAll="false"
										showData="true" />
										</div>

								<div class="col-sm-12 margin-bottom-5">	<strong><p class="text-center text-small  text-red-1 ">
											<spring:message code="property.OR"></spring:message>
										</p></strong></div>
							



								<div class="form-group Loc">
									<apptags:select cssClass="chosen-select-no-results"
										labelCode="property.location" items="${command.location}"
										path="specialNotGenSearchDto.locId" isMandatory="true"
										isLookUpItem="true" selectOptionLabelCode="select Location">
									</apptags:select>

								</div>
							
							
							<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message code="property.DefaulterListReport.amount"/></label>
									<div class="col-sm-4">						
										<form:input cssClass="form-control mandColorClass text-right "
										onkeypress="return hasAmount(event, this, 15, 2)"
										id="amount" path="specialNotGenSearchDto.amount"
										placeholder="999999.99"
										onchange="getAmountFormatInDynamic((this),'billAmount')"
										data-rule-required="true"></form:input>
									</div>
								
									<apptags:input labelCode="property.DefaulterListReport.noOfDefaulter" path="specialNotGenSearchDto.noOfDefaulter" isMandatory="true"></apptags:input>
							</div>
					
					
				<!--------------------start Button--------------- -->
					
					<div class="form-group">
							<div class="text-center padding-bottom-10">
								<button type="button" class="btn btn-blue-2 " id="submit"
									onclick="formSubmit(this)">								
									<spring:message code="propertyBill.Submit" />
								</button>
				
							
							
								<button type="button" class="btn btn-warning " id="resetBtn"
									onclick="resetForm(this)" value="Reset">		
									<spring:message code="property.reset" />
								</button>
							</div>
					</div>
				</div>	
				<!-------------------End Button -------------------->
				</div>
			
	</div>
	</form:form>
</div>
</div>
</div>