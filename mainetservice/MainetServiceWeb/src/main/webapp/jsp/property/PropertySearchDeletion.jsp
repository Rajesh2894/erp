<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/mainet/validation.js"></script>

<script type="text/javascript" src="js/property/PropertyDeletion.js"></script>

<!-- End JSP Necessary Tags -->
	<!-- Start Main Page Heading -->
	<div class="content">
	      <div class="widget">
        <div class="widget-header">
          <h2><strong><spring:message code="" text="Property Deletion"/></strong></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"> <span><spring:message code="property.ChangeInAss.EnterPropertyNo" text="Property No"/>
           <i class="text-red-1"><spring:message code="property.ChangeInAss.OR"/></i> <spring:message code="property.ChangeInAss.OLDPID"/></span> </div>
          <!-- Start Form -->
		<form:form action="PropertyDeletion.html"
					class="form-horizontal form" name="PropertyDeletion.html"
					id="PropertyDeletion">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			<!-- Start Each Section -->
		<div class="form-group">			                
	              	<label for="address" class="col-sm-2 control-label "><spring:message code="property.ChangeInAss.EnterPropertyNo"/></label>
	              	<div class="col-sm-4"><form:input path="searchDto.proertyNo" type="text" id="propNo" class="form-control"/></div>      					
          
	
	
	    <%--  <label class="col-sm-2 control-label required-control"><spring:message code="book.financialYear" text="Financial Year"/></label>
			<div class="col-sm-4">
				<form:select path="faYearId" onchange="" id="fayearId"  cssClass="form-control chosen-select-no-results">
					<form:option value="" ><spring:message text="Select Financial year" code="book.option.financialYear"/> </form:option>
					<c:forEach items="${command.fiancialYearList}" var="lookUp">
					 <form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
					 </c:forEach>
				</form:select>              
			</div> 
	 
	 --%>
	
	 </div> 
	
	
	
		<div class="form-group">
				<div class="text-center padding-bottom-10">
						 <button type="button" class="btn btn-success" id="deleteBtn"
							onclick="deletePropertyBy(this);">
							<spring:message code="" text="Delete"/>
						</button> 			
				
						 <apptags:resetButton></apptags:resetButton>		
				</div>
		</div>	
					
		</form:form>
			</div>
			</div>
</div>







































 <%-- <form action="PropertyDeletion.html" class="form-horizontal form" name="PropertyDeletion" id="PropertySearchDeletion">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
		    <div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			<div class="form-group">
		
			 <apptags:input labelCode="Property Number" path="" isMandatory="true" cssClass="form-control">
									
			</apptags:input>
			
			</div>
			
			<div class="form-group">
				<div class="text-center padding-bottom-10">
						 <button type="button" class="btn btn-success" id="serchBtn"
							onclick="searchPropetryForDelete()">
							<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/>
						</button> 			
				
						 <apptags:resetButton></apptags:resetButton>		
				</div>
		</div>	
			
			
			
</form>		 --%>	
		
				