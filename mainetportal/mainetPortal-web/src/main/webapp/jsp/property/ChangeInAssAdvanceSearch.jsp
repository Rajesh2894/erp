<!-- Start JSP Necessary Tags -->
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
<script src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->
<div class="AdvanceSearch">
	<!-- Start Main Page Heading -->
	      <div class="widget">
        <div class="widget-header">
          <h2><strong><spring:message code="property.ChangeInAss.AdvanceSearch"/></strong></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"> <span><spring:message code="property.ChangeInAss.EnterPropertyNo"/>
           <i class="text-red-1"><spring:message code="property.ChangeInAss.OR"/></i> <spring:message code="property.ChangeInAss.OLDPID"/></span> </div>
          <!-- Start Form -->
		<form:form action="ChangeInAssessmentForm.html"
					class="form-horizontal form" name="frmChangeAssessmentForm"
					id="frmChangeAssessmentForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			<!-- Start Each Section -->
		<div class="form-group" >
				<apptags:input labelCode="property.ChangeInAss.EnterPropertyNo" path="changeInAssOtp.proertyNo" maxlegnth="4"></apptags:input>  
				<apptags:input labelCode="property.ChangeInAss.oldpid" path="changeInAssOtp.oldPid" maxlegnth="4"></apptags:input>
		</div>
		
		<div class="form-group">
			<apptags:input labelCode="ownersdetail.ownersname" path="changeInAssOtp.ownerName"></apptags:input>
			<apptags:input labelCode="ownersdetail.mobileno" path="changeInAssOtp.mobileno"></apptags:input>
		</div>
		
		<div class="form-group">
			 <apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].proAssdUsagetype"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control " showAll="false" showData="true" />
                     	 	
		</div>
		<div class="form-group">
			<apptags:select cssClass="chosen-select-no-results" labelCode="property.location" items="${command.location}" path="provisionalAssesmentMstDto.locId" isMandatory="true" isLookUpItem="true" selectOptionLabelCode="select Location">
			</apptags:select>
			<apptags:input labelCode="property.ChangeInAss.PaymentStatus" path=""></apptags:input>
		</div>
		
		<div class="form-group">
			<apptags:input labelCode="property.ChangeInAss.LastApplicationNo" path=""></apptags:input>
		</div>
	
		<div class="form-group">
				<div class="text-center padding-bottom-10">
						 <button type="button" class="btn btn-success" id="serchBtn"
							onclick="">
							<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/>
						</button> 			
				
						 <apptags:resetButton></apptags:resetButton>		
				</div>
		</div>	
					<div class="form-group" id="grid">
						<table id="advanceSearchGrid"></table>
						<div id="pagered"></div>
					</div>
		</form:form>
			</div>
			</div>
</div>