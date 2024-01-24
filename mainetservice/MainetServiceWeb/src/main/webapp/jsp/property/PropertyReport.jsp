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
<script type="text/javascript" src="js/property/propertyReport.js"></script>

<style>
.sectionSeperator {
	border-bottom: 1px solid #123456;
	border-top: 1px solid #123456;

}
</style>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="property.report"/></strong></h2>				
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
		<form:form action="PropertyReport.html"
					class="form-horizontal form" name="propertyReport"
					id="propertyReport">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			
			
				<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message code="property.report.ReportName"/><span class="mand">*</span>
								</label>
								<div class="col-sm-4">
								<form:select id="reportName" class="form-control mandColorClass" path="" >
									<form:option value="0">Select Option</form:option>
									<form:option value="1">Demand</form:option>
									<form:option value="2">Collection </form:option>
									<form:option value="3">Outstanding</form:option>									
									<form:option value="4">Closing</form:option>
								</form:select>
								</div>
				</div>
			

							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message code="property.report.ReportType"/><span class="mand">*</span>
								</label>
								<div id="searchData col-sm-4">
									<label for="Summary" class="radio-inline "> <form:radiobutton
											path="specialNotGenSearchDto.noticeType" value="S"/><spring:message code="property.report.Summary"/></label> 
									<label for="Detail"
										class="radio-inline "> <form:radiobutton
											path="specialNotGenSearchDto.noticeType" value="D"/><spring:message code="property.report.Detail"/></label>

								</div>
							</div>

<!--------------------------------------------- Selection Criteria ------------------------------------------------------------->
					<div class="accordion-toggle ">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" href="#selectCriteria"><spring:message code="property.report.SelectionCriteria"/></a>
							</h4>
						</div>

						<div id="selectCriteria" class="panel-collapse collapse in">
							<br>
							<div class="form-group">

								<label class="col-sm-2 control-label"><spring:message code="property.report.SelectionCriteria"/><span class="mand">*</span>
								</label>
								<div id="searchData col-sm-4">
									<label for="SinMul" class="radio-inline "> <form:radiobutton
											path="specialNotGenSearchDto.specNotSearchType" value="SM"
											class="SinMul" onchange="showSingleMultiple()" /> <spring:message
											code="property.SingleORMultiple" /></label> 
									<label for="All"
										class="radio-inline "> <form:radiobutton
											path="specialNotGenSearchDto.specNotSearchType" value="AL"
											class="All" onchange="showSingleMultiple()" /> <spring:message
											code="property.All" /></label>

								</div>
							</div>

							<div class="form-group PropDetail">

								<apptags:textArea labelCode="Property No"
									path="specialNotGenSearchDto.propertyNo" isMandatory="true"
									cssClass="mandColorClass hasPropNo hasNumber"></apptags:textArea>
								<apptags:textArea labelCode="Old Property No"
									path="specialNotGenSearchDto.oldPropertyNo" isMandatory="true"
									cssClass="mandColorClass hasNumber"></apptags:textArea>
							</div>

							<div class="form-group PropDetail">
							<label  class="col-sm-2  required-control ">
							<spring:message code="property.report.FinancialYear"/></label>
							<div class="col-sm-4">
								<form:select path="specialNotGenSearchDto.finacialYear" id="finacialYear" class="form-control text-center mandColorClass displayYearList">
		  						<form:option value="0" label="Select Year"></form:option>
		  						<c:forEach items="${command.financialYearMap}" var="yearMap">
		  						<form:option value="${yearMap.key}" label="${yearMap.value}"></form:option>
		  						</c:forEach>
		 						</form:select>				
							</div>
							</div>


							<div class="sectionSeperator padding-top-15">
								<div class="form-group wardZone">

									<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
										showOnlyLabel="false"
										pathPrefix="specialNotGenSearchDto.assWard" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control " showAll="false"
										showData="true" />
								</div>

								<div class="col-sm-12 margin-bottom-5"><strong><p class="text-center text-small  text-red-1 ">
											<spring:message code="property.OR"></spring:message>
										</p></strong>
								</div>

								<div class="form-group Loc">
									<apptags:select cssClass="chosen-select-no-results"
										labelCode="property.location" items="${command.location}"
										path="specialNotGenSearchDto.locId" isMandatory="true"
										isLookUpItem="true" selectOptionLabelCode="select Location">
									</apptags:select>

								</div>
							</div>

							<div class="padding-top-15 ">
								<div class="form-group usageType">

									<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
										showOnlyLabel="false"
										pathPrefix="specialNotGenSearchDto.assdUsagetype"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control" showAll="false"
										showData="true" />
								</div>
							</div>
							
							<div class="form-group financialYear">
							<label  class="col-sm-2  required-control ">
							<spring:message code="property.report.FinancialYear"/></label>
							<div class="col-sm-4">
								<form:select path="specialNotGenSearchDto.finacialYear" id="finYear" class="form-control text-center mandColorClass displayYearList">
		  						<form:option value="0" label="Select Year"></form:option>
		  						<c:forEach items="${command.financialYearMap}" var="yearMap">
		  						<form:option value="${yearMap.key}" label="${yearMap.value}"></form:option>
		  						</c:forEach>
		 						</form:select>
							</div>
							</div>
						</div>

						</div>
						
						</div>
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
		</form:form>
		</div>
	</div>
	</div>