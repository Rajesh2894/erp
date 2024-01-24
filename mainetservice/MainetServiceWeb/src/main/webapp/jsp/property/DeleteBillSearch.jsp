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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/DeleteBillSearch.js"></script>



<!-- End JSP Necessary Tags -->
	<!-- Start Main Page Heading -->
	<div class="content">
	      <div class="widget">
        <div class="widget-header">
          <h2><strong><spring:message code="" text="Bill Deletion"/></strong></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"> <span><spring:message code="property.ChangeInAss.EnterPropertyNo"/>
           <i class="text-red-1"><spring:message code="property.ChangeInAss.OR"/></i> <spring:message code="property.ChangeInAss.OLDPID"/></span> </div>
          <!-- Start Form -->
		<form:form action="PropertyBillDeletion.html"
					class="form-horizontal form" name="PropertyDeleteBill"
					id="propertyDeleteBill">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			<!-- Start Each Section -->
		<div class="form-group">			                
	              	<label for="address" class="col-sm-2 control-label "><spring:message code="property.ChangeInAss.EnterPropertyNo"/></label>
	              	<div class="col-sm-4"><form:input path="" type="text" id="propNo" class="form-control"/></div>      					
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
					
		</form:form>
			</div>
			</div>
</div>

