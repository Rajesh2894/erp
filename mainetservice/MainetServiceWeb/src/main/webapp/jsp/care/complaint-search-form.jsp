<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-reopen-feedback.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>


<script type="text/javascript">
$(document).ready(function(){
	$('.btnReset').click(function() {
			$('.compalint-error-div').hide();
		});
	
	$(".searchString ").on("keypress", function(e) {
		console.log(e.which +"   "+ this.value);
	    if (e.which === 13){
	        e.preventDefault();
	        if(this.value.length)
	         	searchAndViewComplaint(null);
	    }
	});
	
});

</script>

    <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown">
    
	 <div class="widget">
	   <div class="widget-header">
	      <h2><spring:message code="care.search" text="Search Grievance"/></h2>
	   </div>
	   <div class="widget-content padding">
	   
	   <apptags:helpDoc url="GrievanceDepartmentReopen.html"></apptags:helpDoc> 
	 
	      <form:form method="POST" action="GrievanceDepartmentReopen.html"
					commandName="command" 
					class="form-horizontal"
					id="form_grievanceReopen">
			
			<div class="compalint-error-div">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			</div>
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
	      	
	         <div class="panel-group accordion-toggle" id="accordion_single_collapse">
	            <div class="panel panel-default">
	            
	            <!-- Defect #154900 -->
	            <%-- <div class="panel-heading">
	            	<h4 class="panel-title">
	            		<a data-toggle="" class="" data-parent="#accordion_single_collapse" href="#a0">
	            			<spring:message code="care.search" text="Search Grievance"/>
	            		</a></h4>
	            </div> --%>
	    
	               <div id="a0" class="panel-collapse collapse in">
	                  <div class="panel-body">
	                     <div class="form-group">
							 <apptags:input labelCode="care.searchString" path="" isMandatory="true" cssClass="searchString" maxlegnth="25"></apptags:input>
		                 </div>
	                  </div>
	               </div>
	               <div class="text-center clear padding-10">
	               		<button type="button" class="btn btn-success btn-submit"
							title="Search"
							onclick="searchAndViewComplaint(null)">
							<spring:message code="care.btn.search" text="Search"/>
						</button>
		                <apptags:resetButton cssClass="btnReset"></apptags:resetButton>
		                <button type="button"
							onclick="window.location.href='AdminHome.html'"
							class="btn btn-danger">
							<spring:message code="care.back" text="Back" />
						</button>	
	               </div>
	            </div>
	         </div>
	         </form:form>
	   </div>
	</div>
   </div>