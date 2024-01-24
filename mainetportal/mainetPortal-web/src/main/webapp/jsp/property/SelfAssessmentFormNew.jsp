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
<script src="js/property/selfAssessment.js" ></script>   
<script src="js/property/ownershipDetailsForm.js"></script>

<div id="validationDiv">

<!-- Start Content here -->
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="property.selfassessment" text=""/></strong></h2>				
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="property.Help" text="property.Help"/></span></i></a>
				</div>
	</div>
	
	<!-- End Main Page Heading -->
		
<!-- Start Widget Content -->
	<div class="widget-content padding">
	
		
		<!-- Start mand-label -->
		<div class="mand-label clearfix">
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">*</i>&nbsp;&nbsp;<spring:message code="property.ismandatory"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<form:form action="SelfAssessmentForm.html"
					class="form-horizontal form" name="frmSelfAssessmentForm"
					id="frmSelfAssessmentForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:hidden path="assType" id="assType"/>
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
			<input type="hidden" value="${command.orgId}" id="orgId"/>
			<input type="hidden" value="${command.deptId}" id="deptId"/>
			<input type="hidden" id="ownershipCode" />
			<!-- Start Each Section -->
			<c:if test="${command.getIntgrtionWithBP() eq 'Y'}">
						<div class="padding-10 "><strong><i><spring:message code="property.EnterDetailstosearchproperty" /></i></strong></div>
			
			<div class="form-group">
					
					<label class="col-sm-2 control-label"><spring:message code="property.searchType" />
					</label>
				   
				    <c:set var="baseLookupCode" value="SRT" />
							<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="" cssClass="form-control changeParameterClass"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="select" isMandatory="false"/>
					
                   <apptags:input labelCode="property.searchNumber" path=""></apptags:input>
           	</div>  
                 	
                   	<!-- Start button -->
                   	<div class="text-center padding-15 clear">                   	
                   	<button type="button" class="btn btn-warning"><spring:message code="property.search" text="" /></button>	
                   	</div>
                   	<!-- End button -->	
			 </c:if>
			<!-- End Each Section -->
			
			<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						
					
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
									code="property.Ownershipdetail" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="OwnshipDetail">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="OwnershipDetail"><spring:message
										code="property.ownershiptype" /></label>
									<div class="col-sm-4">
									<c:set var="baseLookupCode" value="OWT" /> <form:select path="provisionalAssesmentMstDto.assOwnerType"
											onchange="getOwnerTypeDetails()" class="form-control mandColorClass" id="ownerTypeId" data-rule-required="true">
												<form:option value="">
													<spring:message code="property.sel.optn.ownerType" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
									</div>		
							</div>
						</div>
						<form:hidden path="selfAss" id="selfAss"/>
					
					<div id="owner">
					
					</div>					
						
						<jsp:include page="/jsp/property/SelfAssessmentPropertyDetailsForm.jsp"></jsp:include>
						
						<jsp:include page="/jsp/property/SelfAssessmentPropertyAddressForm.jsp"></jsp:include>
						
						<jsp:include page="/jsp/property/NewPropertyTaxZoneDetails.jsp"></jsp:include>
									
						<jsp:include page="/jsp/property/SelfAssessmentBuildingDetailForm.jsp"></jsp:include>
						
						<jsp:include page="/jsp/property/SelfAssessmentUnitDetailForm.jsp"></jsp:include> 
						
						<%-- <jsp:include page="/jsp/property/SelfAssessmentPropertyDetailsForm.jsp"></jsp:include>
						
						<jsp:include page="/jsp/property/SelfAssessmentPropertyAddressForm.jsp"></jsp:include>
			
						<jsp:include page="/jsp/property/SelfAssessmentLastPaymentDetailForm.jsp"></jsp:include>
						
						<jsp:include page="/jsp/property/SelfAssessmentBuildingDetailForm.jsp"></jsp:include>
						
						<jsp:include page="/jsp/property/SelfAssessmentUnitDetailForm.jsp"></jsp:include> --%>
															
			</div>
			<!-- Start button -->
			<div class="text-center padding-10">
			<c:if test="${command.getAssType() eq 'N' || command.getAssType() eq 'VA'}"> 
				<button type="button" class="btn btn-success"
				onclick="confirmToProceed(this)" id="btnSave"><spring:message code="unit.proceed"/></button>
				
				<c:if test="${command.getSelfAss() eq 'Y'}">  
				<input 	type="button" class="btn btn-warning" value="Reset" onclick="resetFormData(this)" id="Reset"/>	
				
				<button type="button" class="btn btn-danger" id="back"
								onclick="history.back()">
								<spring:message code="propertyBill.Back"></spring:message>
							</button>

				</c:if>			
	
			</c:if>
				
			<%-- <c:if test="${command.getAssType() eq 'VA'}">  
					<button type="button" class="btn btn-success"
					onclick="viewAssessmentDetails(this)" id="btnCompare"><spring:message code="unit.proceed" text="Proceed"/></button>
			</c:if>	 --%>	
			
			</div>
			
			<!--  End button -->
			
			
		</form:form>
		<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
		</div>
		<!-- End Widget  here -->							
</div>
<!-- End of Content -->


