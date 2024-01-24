<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/property/propertyBillRevise.js" type="text/javascript"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/selfAssessment.js" ></script>   
<script type="text/javascript" src="js/property/ownershipDetailsForm.js"></script>
<div  id="dataDiv">
<!-- End JSP Necessary Tags -->

<script>
$(document).ready(function(){
	$('.lessthancurrdate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});
	
	prepareDateTag();
});

 function prepareDateTag() {
		var dateFields = $('.lessthancurrdate');
		dateFields.each(function () {
			var fieldValue = $(this).val();
			if (fieldValue.length > 10) {
				$(this).val(fieldValue.substr(0, 10));
			}
		});
	}

</script>
<apptags:breadcrumb></apptags:breadcrumb>  

<!-- Start Content here -->
 <div class="content">
 <div class="widget">
<!-- Start Main Page Heading -->
       
       <div class="widget-content padding">
       <!-- Start Form -->
			<form:form action="PropertyBillRevise.html"
					class="form-horizontal form" name="changeAssessmentSearch"
					id="changeAssessmentSearch">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
          	<div class="mand-label clearfix"><span><spring:message code="property.ChangeInAss.EnterPropertyNo"/>
           <i class="text-red-1"><spring:message code="property.ChangeInAss.OR"/></i> <spring:message code="property.ChangeInAss.OLDPID"/></span> </div>
          	
          	
			
			<!-- Start Each Section -->				
				<div class="form-group" >
	
				 <label class="col-sm-2 control-label required-control" for="assNo"><spring:message code="property.ChangeInAss.EnterPropertyNo"/></label>
					<div class="col-sm-3">
						<form:input path="provisionalAssesmentMstDto.assNo"  class="form-control mandColorClass" id="assNo"/>
					</div>
					
					<div class="col-sm-2 text-center"><i class="text-red-1"><spring:message code="property.OR"/></i></div>
					<label class="col-sm-2 control-label required-control" for="assOldpropno"><spring:message code="property.ChangeInAss.oldpid"/></label>
					
					<div class="col-sm-3">					
						<form:input path="provisionalAssesmentMstDto.assOldpropno"  class="form-control mandColorClass" id="assOldpropno"/>
					</div>

				</div>
						<div class="text-center padding-bottom-10">
								 <button type="button" class="btn btn-blue-2" id="serchBtn"
									onclick="SearchButton(this)">
									<spring:message code="property.changeInAss.Search"/>
								</button>
								<%-- Defect #155250 --%>
		                   		<button type="button" class="btn btn-warning" onclick="javascript:openRelatedForm('PropertyBillRevise.html');" title="Reset">
									<spring:message code="rstBtn" text="Reset" />
								</button>
								<apptags:backButton url="AdminHome.html"></apptags:backButton> 
						<c:if test="${not empty command.provisionalAssesmentMstDto.assesssmentCategory}">
								<button type="button" class="btn btn-danger" id="back"
									onclick="backToMain(this)">
									<spring:message code="property.Back" text="Back"/>
								</button>
						</c:if>	
								 	 		
						</div>

			<!-- End Each Section -->
			</form:form>
			<!-- End Form -->

</div> </div>
</div>
</div>
