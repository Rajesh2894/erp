<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/property/amalgamation.js"></script>

<!-- End JSP Necessary Tags -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="property.Amalgamation" text=""/></strong></h2>				
				<!-- <div class="additional-btn"> -->
					<apptags:helpDoc url="AmalgamationForm.html"></apptags:helpDoc>		
				<!-- </div> -->
	</div>
	
	<!-- End Main Page Heading -->
		
	<!-- Start Widget Content -->
	<div class="widget-content padding">
	
	<!-- Start mand-label -->
		<div class="mand-label clearfix">
			<span><spring:message code="property.propDetails" text=""/></span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<form:form action="AmalgamationForm.html"
					class="form-horizontal form" name="AmalgamationForm"
					id="AmalgamationForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
 			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div> 
			
			<form:hidden path="successMessage" id="successMessage"/>
			
			<div id="searchDetails">
 			<div class="form-group">
 				<apptags:input labelCode="property.ParentPropertyNo" path="amalgamationDto.parentPropNo" isMandatory="true" cssClass="mandColorClass"></apptags:input> 
				<apptags:input labelCode="property.PropertyNumberAmalgamated" path="amalgamationDto.amalgmatedPropNo" isMandatory="true" cssClass="mandColorClass hasPropNo alphaNumeric"></apptags:input>	 
	
 			</div>
			<strong><p class="text-center text-small padding-bottom-10 text-red-1 "><spring:message code="property.OR"></spring:message></p></strong>
			<div class="form-group">
				<apptags:input labelCode="property.ParentOldPropertyNo" path="amalgamationDto.parentOldPropNo" isMandatory="true" cssClass="mandColorClass"></apptags:input>
				<apptags:input labelCode="property.OldPropertyNumbersAmalgamated" path="amalgamationDto.amalgmatedOldPropNo" isMandatory="true" cssClass="mandColorClass"></apptags:input>
			</div>
			
			<div class="form-group">
						<div class="text-center padding-bottom-10">
							<button type="button" class="btn btn-blue-2" id="serchBtn"
								onclick="SearchButton(this)">
								<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search" />
							</button> 	
							
							<button type="button" class="btn btn-warning" id="Reset"
								onclick="resetDetails()">
								<spring:message code="property.reset" />
							</button>
							<%-- Defect #155250 --%>
							<apptags:backButton url="AdminHome.html"></apptags:backButton>
						</div>						
			</div>
			</div>
	
	<c:if test="${command.provisionalAssesmentMstDto ne null}">
	
			<div class="accordion-toggle">
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse " href="#ParentPropertyDetail"><spring:message code="property.prentPropDetails" text=""/></a>
				</h4>
					
				<div class="panel-collapse collapse in" id="ParentPropertyDetail">
						
					<table  class="table table-striped table-bordered ">
                    
                    <tr>                  
                        <th width="15%"><spring:message code="property.PropertyNo" text="Prop NO"/></th>
						<th width="15%"><spring:message code="property.OldPropertyNo" text="Old prop no"/></th>
						<th width="20%"><spring:message code="property.OwnerName" text=""/></th>
						<th width="25%"><spring:message code="property.Address" text=""/></th>
						<th width="15%"><spring:message code="property.totalplot" text="Total plot Area"/></th>
<%-- 						<th width="15%"><spring:message code="property.TotalBuiltupArea" text=""/></th> --%>
                    </tr>
                     <tbody> 
                     <tr>
                        <td><form:input id="PropNo" path="amalParentPropDto.assNo" class=" form-control" readonly="true"/></td>
                        <td><form:input id="OldPropNo" path="amalParentPropDto.assOldpropno" class=" form-control" readonly="true"/></td>
                        <td><form:input id="OwnerName" path="amalParentPropDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class=" form-control" readonly="true"/> </td>
                        <td><form:input id="Address" path="" value="${command.amalParentPropDto.assAddress},${command.amalParentPropDto.locationName},${command.amalParentPropDto.assPincode}" class=" form-control" readonly="true"/></td>
                        <td><form:input id="TotalPlotArea" path="amalParentPropDto.assPlotArea" class=" form-control" readonly="true"/> </td>
<%--                         <td><form:input id="TotalBuiltArea" path="amalParentPropDto.assBuitAreaGr" class=" form-control" readonly="true"/></td>                        	 --%>
                     </tr>  
                     </tbody>
                     </table>                        
				</div>
				
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#amalgamatedProperty"><spring:message code="property.PropertiesAmalgamatedInfo" text=""/></a>
				</h4>
					
				<div class="panel-collapse collapse in" id="amalgamatedProperty">
						
					<table  class="table table-striped table-bordered ">
                    
                    <tr>                  
                        <th width="15%"><spring:message code="property.PropertyNo" text=""/></th>
						<th width="15%"><spring:message code="property.OldPropertyNo" text=""/></th>
						<th width="20%"><spring:message code="property.OwnerName" text=""/></th>
						<th width="25%"><spring:message code="property.Address" text=""/></th>
						<th width="15%"><spring:message code="property.totalplot" text="Total plot Area"/></th>
<%-- 						<th width="15%"><spring:message code="property.TotalBuiltupArea" text=""/></th> --%>
                    </tr>
                    <tbody>
                      <c:forEach var="propDetail" items="${command.getProvisionalAssesmentMstDtoList()}" varStatus="status" > 
                        <tr>
                          <td><form:input path="" value="${propDetail.assNo}" class=" form-control" readonly="true"/> </td>
                          <td><form:input path="" value="${propDetail.assOldpropno}" class=" form-control" readonly="true"/></td>
                           <td><form:input id="OwnerName" path="" value="${propDetail.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName}" class=" form-control" readonly="true"/> </td>
                           <td><form:input path="" value="${propDetail.assAddress},${propDetail.locationName},${propDetail.assPincode}" class=" form-control" readonly="true"/></td>
                           <td><form:input path="" value="${propDetail.assPlotArea}" class=" form-control" readonly="true"/></td>
<%--                            <td><form:input path="" value="${propDetail.assBuitAreaGr}" class=" form-control" readonly="true"/></td> --%>
                          </tr> 
                        </c:forEach>  
                     </tbody>
                     </table>                        
				</div>				
				</div>

					<div class="text-center padding-top-10">
					
					<button class="btn btn-blue-2" type="button"  onclick="editDetails(this)" id="edit">
											<spring:message code="property.EditDetails" text=""/></button>
					</div>
					</c:if>
		</form:form>
		<!-- End Form -->
	</div>
	<!-- End Widget Content -->
</div>
<!-- End Main Page Heading -->
</div>
<!-- Start Content here -->
	