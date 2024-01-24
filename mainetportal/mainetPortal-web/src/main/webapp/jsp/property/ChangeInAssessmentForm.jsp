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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/property/changeInAssessmentForm.js"></script>

<!-- Start Content here -->
<!-- Start Main Page Heading -->
<div class="content">
<div class="widget">
        <div class="widget-header">
          <h2><spring:message code="property.ChangeInAssessment"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="property.Help"/></span></a></div>
        </div>
        
        <div class="widget-content padding">
          <div class="mand-label clearfix"> <span><spring:message code="property.Fieldwith"/><i class="text-red-1">*</i><spring:message code="property.ismandatory"/></span> </div>
          
          <!-- Start Form -->
			<form:form action="ChangeInAssessmentForm.html"
					class="form-horizontal form" name="frmChangeAssessmentForm"
					id="frmChangeAssessmentForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>
			
			<!-- Start Each Section -->			
			<div class="accordion-toggle ">
 					<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
					code="property.Ownershipdetail" /></a>
					</h4>
					<div class="panel-collapse collapse in" id="OwnshipDetail">							
							<div class="form-group">
								<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="property.ownershiptype" /></label>
								<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssOwnerTypeName"  id="ownershiptype" class="form-control" readonly="true"/>
				 				<form:hidden  path="ownershipPrefix"  id="ownershipId" class="form-control"/> 								
								</div>
						    </div>					   
						    <jsp:include page="/jsp/property/changeInAssessmentOwnerDetails.jsp"></jsp:include> 					
					</div> 
				 
				 <!--  Property Details           -->
            
				<%-- <h4 class="margin-top-10 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#propertyDetail"><spring:message
				code="property.Propertydetail" /></a>
				</h4> --%>
				<div class="panel-collapse collapse in" id="propertyDetail">
	           		<div class="form-group">
	                  	<apptags:input labelCode="propertydetails.oldpropertyno" path="provisionalAssesmentMstDto.assOldpropno" isReadonly="true"></apptags:input>
	                  	<apptags:input labelCode="property.landType" path="provisionalAssesmentMstDto.assLandTypeDesc" isReadonly="true"></apptags:input>
	                  	<form:hidden  path="landTypePrefix" class="form-control landValue"/> 
	                  	
	            	</div>
	            </div> 	
<c:choose>
	 <c:when test="${command.getLandTypePrefix() eq 'KPK'}">
		<div class="form-group">		
		
			<apptags:input labelCode="property.district" path="provisionalAssesmentMstDto.assDistrictDesc" isDisabled="true"></apptags:input>			
			<apptags:input labelCode="property.tahasil" path="provisionalAssesmentMstDto.assTahasilDesc" isDisabled="true"></apptags:input>
		</div>
		<div class="form-group">						
 			<apptags:input labelCode="propertydetails.village" path="provisionalAssesmentMstDto.tppVillageMaujaDesc" isDisabled="true"></apptags:input> 								
			<label class="col-sm-2 control-label required-control"
									for="khasra">Khasra No</label>
				<div class="col-sm-4">									
				<form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" class="form-control mandColorClass" id="khasara" disabled="true"/>
				</div>
		</div>
		
		<div class="form-group">
		<div class="text-center padding-top-10">
		<c:if test="${command.getAssType() eq 'C'}">  
		<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										Fetch Land Details</button> 
		</c:if>	
		
		</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
		<!-- <div id="showApiDetails">
					
		</div> -->	
		
<!-- 		<div class="accordion-toggle "> -->
	<c:if test="${command.getAssType() ne 'C'}">  

	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#khasra">Khasra Details</a>
	</h4>
	<div class="panel-collapse collapse in" id="khasra">
	
	<div class="form-group">		
	<apptags:input labelCode="property.landDetails.ownerFirstName" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ocdname"></apptags:input>	
<%-- 	<apptags:input labelCode="property.landDetails.ownerLastName" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ocdname1"></apptags:input>				 --%>
	<apptags:input labelCode="property.landDetails.fathersFirstName" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ofather"></apptags:input>
	
	</div>					
			
	<%-- <div class="form-group">		
	<apptags:input labelCode="property.landDetails.fathersFirstName" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ofather"></apptags:input>
	<apptags:input labelCode="property.landDetails.FathersLastName" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ofather1"></apptags:input>				
	</div> --%>	
	
	<div class="form-group">		
	<apptags:input labelCode="property.landDetails.GenderOfOwner" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].gender"></apptags:input>
	<apptags:input labelCode="property.landDetails.CasteOfOwner" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ocastenm"></apptags:input>				
	</div>	
	
	<div class="form-group">		
	<apptags:input labelCode="property.landDetails.OwnerAddress" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].oaddr1"></apptags:input>
	<apptags:input labelCode="property.landDetails.OwnerMobileNo" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].mobileno"></apptags:input>				
	</div>	
	
	<div class="form-group">	
	 	<apptags:input labelCode="property.landDetails.LandArea" path="arrayOfKhasraDetails.KhasraDetails[0].Area"></apptags:input>
	 	<apptags:input labelCode="property.landDetails.OwnerType" path="arrayOfKhasraDetails.KhasraDetails[0].OwnerType"></apptags:input>
	
	</div>
	
 </div>
 </c:if>
<!--  </div> -->
</c:when>

<c:when test="${command.getLandTypePrefix() eq 'NZL'}">
		<div class="form-group">			
			<apptags:input labelCode="property.district" path="provisionalAssesmentMstDto.assDistrictDesc" isDisabled="true"></apptags:input>			
			<apptags:input labelCode="property.tahasil" path="provisionalAssesmentMstDto.assTahasilDesc" isDisabled="true"></apptags:input>		
		</div>
			
		<div class="form-group">			
				<apptags:input labelCode="propertydetails.village" path="provisionalAssesmentMstDto.tppVillageMaujaDesc" isDisabled="true"></apptags:input> 								
				<apptags:input labelCode="Mohalla" path="provisionalAssesmentMstDto.mohallaDesc" isDisabled="true"></apptags:input> 								
		</div>	
		
		<div class="form-group">
				<apptags:input labelCode="propertydetails.streetno" path="provisionalAssesmentMstDto.assStreetNoDesc" isDisabled="true"></apptags:input> 												
				<apptags:input labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo" isDisabled="true"></apptags:input>
		</div>		
		
		<div class="form-group">
			<div class="text-center padding-top-10">
			<c:if test="${command.getAssType() eq 'C'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										Fetch Details</button> 
			</c:if>	
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
	<c:if test="${command.getAssType() ne 'C'}">  

			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#details">Najool Details</a>
			</h4>
			<div class="panel-collapse collapse in" id="details">
			<div class="form-group">		
						<apptags:input labelCode="property.landDetails.OwnerName" isDisabled="true" path="arrayOfPlotDetails.PlotDetails[0].najoolOwnerDetails[0].najoolOwnerDetailsList[0].OwnerName"></apptags:input>	
						<apptags:input labelCode="property.landDetails.Department" isDisabled="true" path="arrayOfPlotDetails.PlotDetails[0].Department"></apptags:input>				
			</div>					
					
			<div class="form-group">		
						<apptags:input labelCode="property.landDetails.Area" isDisabled="true" path="arrayOfPlotDetails.PlotDetails[0].area_foot"></apptags:input>
						<apptags:input labelCode="property.landDetails.rights_type_nm" isDisabled="true" path="arrayOfPlotDetails.PlotDetails[0].rights_type_nm"></apptags:input>				
			</div>	
			
			<div class="form-group">		
						<apptags:textArea labelCode="property.landDetails.Remark" path="arrayOfPlotDetails.PlotDetails[0].remark" isDisabled="true"></apptags:textArea>			
			</div>
				
		 	</div>
	</c:if>
</c:when>
	


<c:when test="${command.getLandTypePrefix() eq 'DIV'}">

		<div class="form-group">			
			<apptags:input labelCode="property.district" path="provisionalAssesmentMstDto.assDistrictDesc" isDisabled="true"></apptags:input>			
			<apptags:input labelCode="property.tahasil" path="provisionalAssesmentMstDto.assTahasilDesc" isDisabled="true"></apptags:input>		
		</div>
			
		<div class="form-group">			
				<apptags:input labelCode="propertydetails.village" path="provisionalAssesmentMstDto.tppVillageMaujaDesc" isDisabled="true"></apptags:input> 								
				<apptags:input labelCode="Mohalla" path="provisionalAssesmentMstDto.mohallaDesc" isDisabled="true"></apptags:input> 								
		</div>	
		
		<div class="form-group">
				<apptags:input labelCode="propertydetails.streetno" path="provisionalAssesmentMstDto.assStreetNoDesc" isDisabled="true"></apptags:input> 												
				<apptags:input labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo" isDisabled="true"></apptags:input>
		</div>	
		
		<div class="form-group">
			<div class="text-center padding-top-10">
			<c:if test="${command.getAssType() eq 'C'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										Fetch Details</button> 
			</c:if>	
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
	<c:if test="${command.getAssType() ne 'C'}">	

			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#details">Diversion Details</a>
			</h4>
			<div class="panel-collapse collapse in" id="details">
			<div class="form-group">		
					<apptags:input labelCode="property.landDetails.OwnerName" isDisabled="true" path="arrayOfDiversionPlotDetails.DiversionPlotDetails[0].diversionOwnerDetails[0].diversionOwnerDetailsList[0].OwnerName"></apptags:input>	
					<apptags:input labelCode="property.landDetails.Department" isDisabled="true" path="arrayOfDiversionPlotDetails.DiversionPlotDetails[0].Department"></apptags:input>				
			</div>					
					
			<div class="form-group">		
					<apptags:input labelCode="property.landDetails.Area" isDisabled="true" path="arrayOfDiversionPlotDetails.DiversionPlotDetails[0].area_foot"></apptags:input>
					<apptags:input labelCode="property.landDetails.rights_type_nm" isDisabled="true" path="arrayOfDiversionPlotDetails.DiversionPlotDetails[0].rights_type_nm"></apptags:input>				
			</div>	
			<div class="form-group">		
					<apptags:textArea labelCode="property.landDetails.Remark" path="arrayOfDiversionPlotDetails.DiversionPlotDetails[0].remark" isDisabled="true"></apptags:textArea>			
			</div>
				
		 	</div>
	</c:if>
</c:when>
</c:choose>
	          
            
            
            <!--             Property Address Details-->
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#propertyAddress"><spring:message
					code="property.Propertyaddress" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="propertyAddress">
						<div class="form-group">
							<apptags:input labelCode="property.propertyaddress" path="provisionalAssesmentMstDto.assAddress" isReadonly="true"></apptags:input>
							<apptags:input labelCode="property.location" path="provisionalAssesmentMstDto.locationName" isReadonly="true"></apptags:input>			                
			            
			           <%--  <input type="hidden" value="${command.provisionalAssesmentMstDto.locId}" id="locId"/>
      					<input type="hidden" value="${command.orgId}" id="orgId"/>
						<input type="hidden" value="${command.deptId}" id="deptId"/> --%>
            			</div> 
		          <!--   <div id="wardZone">
					
					</div>   -->         
			            <div class="form-group">
			            	<apptags:input labelCode="property.pincode" path="provisionalAssesmentMstDto.assPincode" isReadonly="true"></apptags:input>	
<%-- 			            	<apptags:input labelCode="property.email" path="provisionalAssesmentMstDto.assEmail" isReadonly="true"></apptags:input>		                	              	 --%>
<%-- 			            	<apptags:input labelCode="unitdetails.RoadType" path="provisionalAssesmentMstDto.proAssdRoadfactorDesc" isDisabled="true"></apptags:input>  	 --%>
			            
			            </div>
	            </div>
                
			<!--         Correspondence Address Details     -->
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#correspondenceAddress"><spring:message
					code="property.correspondenceaddress" /></a>
				</h4>
            
				<div class="panel-collapse collapse in" id="correspondenceAddress">	
					<div class="form-group">     
							<apptags:input labelCode="property.correspondenceaddress" path="provisionalAssesmentMstDto.assCorrAddress" isReadonly="true"></apptags:input>            
			              	<apptags:input labelCode="property.pincode" path="provisionalAssesmentMstDto.assCorrPincode" isReadonly="true"></apptags:input>
			              
		            </div>            
		            <%-- <div class="form-group"> 
		            		<apptags:input labelCode="property.email" path="provisionalAssesmentMstDto.assCorrEmail" isReadonly="true"></apptags:input>                 	              		              	
		            </div> --%>
	            </div>
         
			<!--            Last Payment Details -->
			<%-- 
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#LastBillPayment"><spring:message
					code="property.lastpayment" /></a>
				</h4>
				
				<div class="panel-collapse collapse in" id="LastBillPayment">			
					<div class="form-group">
					
						<label class="col-sm-2 control-label">
						<spring:message code="property.billpayment" text=""/>
			<!-- 			<span class="mand">*</span> -->
						</label>
						<div class="col-sm-4">
						<label for="notApplicaple" class="radio-inline">
						<form:radiobutton path="" value="N" name="billPayment" id="notApplicaple" onchange="billPaymentValue()" />
						<spring:message code="property.na"/></label>
						<label for="manual" class="radio-inline">
						<form:radiobutton path="" value="M" name="billPayment"  id="manual" onchange="billPaymentValue()" />
						<spring:message code="property.manual"/></label>
						<label for="computerized" class="radio-inline">
						<form:radiobutton path="provisionalAssesmentMstDto.proAssBillPayment" value="C" name="billPayment" id="computerized"/>
						<spring:message code="property.computerized"/></label> 
						</div>	
				    </div>
				
		            
		            <div class="form-group"> 
		            	<apptags:input labelCode="property.receiptno" path="provisionalAssesmentMstDto.assLpReceiptNo" isReadonly="true"></apptags:input> 
		            	<apptags:input labelCode="property.receiptamount" path="provisionalAssesmentMstDto.assLpReceiptAmt" isReadonly="true"></apptags:input>               	              	          		           		
		            </div>
					
					 <div class="form-group">
					 	<apptags:input labelCode="property.receiptdate" path="provisionalAssesmentMstDto.assLpReceiptDate" isReadonly="true" cssClass="dateClass"></apptags:input>
					 	<apptags:input labelCode="property.lastpaymentupto" path="provisionalAssesmentMstDto.proAssLpYearDesc" isReadonly="true"></apptags:input>
					 	
					 </div>
				
					<div class="form-group"> 
						<apptags:input labelCode="property.billamount" path="provisionalAssesmentMstDto.billAmount" isReadonly="true"></apptags:input>                 
			             <apptags:input labelCode="property.OutStandingAmount" path="provisionalAssesmentMstDto.outstandingAmount" isReadonly="true"></apptags:input> 
			              	
		            </div>
	        	</div> --%>

       	
<!----------------------------------------------------------Tax-Zone details------------------------------------------------------ --> 
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#taxZone"><spring:message
				code="property.taxZoneDetails" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="taxZone">
			<div class="panel-body">
				<div class="form-group">
			
				<apptags:input labelCode="property.propertyZone" path="provisionalAssesmentMstDto.assWardDesc1" isReadonly="true"></apptags:input>
				
				<c:if test="${command.provisionalAssesmentMstDto.assWard2 ne null}">			
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc2" isReadonly="true"></apptags:input>
				</c:if>
				</div>
				<div class="form-group">
				<c:if test="${command.provisionalAssesmentMstDto.assWard3 ne null}">
				
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc3" isReadonly="true"></apptags:input>
				</c:if>
				<c:if test="${command.provisionalAssesmentMstDto.assWard4 ne null}">
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc4" isReadonly="true"></apptags:input>
				</c:if>
				</div>
				<div class="form-group">
				<c:if test="${command.provisionalAssesmentMstDto.assWard5 ne null}">
				
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc5" isReadonly="true"></apptags:input>
				</c:if>
				</div>
				<div class="form-group">
				<apptags:input labelCode="unitdetails.RoadType" path="provisionalAssesmentMstDto.proAssdRoadfactorDesc" isReadonly="true"></apptags:input>
				</div>
			
			</div>
			</div>
        	
        	
			<!--            Land / Building Details -->
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#BuildingDetails"><spring:message
					code="property.buildingdetails" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="BuildingDetails">
				
					<div class="form-group">
						<apptags:input labelCode="property.yearofacquisition" path="provisionalAssesmentMstDto.assAcqDate" isReadonly="true" cssClass="dateClass"></apptags:input>								
							
						<label class="col-sm-2 control-label required-control"><spring:message
												code="property.totalplot" text="" /></label>
						<div class="col-sm-4">						
						<form:input cssClass="form-control mandColorClass text-right"
												onkeypress="return hasAmount(event, this, 15, 2)"
												id="totalplot" path="provisionalAssesmentMstDto.assPlotArea"
												placeholder="999999.99"
												onchange="getAmountFormatInDynamic((this),'totalplot')"
												data-rule-required="true"></form:input>
						</div>		
					</div>
	
				</div>


<jsp:include page="/jsp/property/ChangeInAssessmentUnitDetailsForm.jsp"></jsp:include>

</div>
			<!-- Start button -->

		 	<div class="text-center padding-10">
				<button type="button" class="btn btn-success"
				onclick="proceed(this)" id="btnSave"><spring:message code="unit.proceed"/></button>
				
				<c:if test="${command.getSelfAss() eq 'Y'}">
				<input 	type="button" class="btn btn-warning" value="Reset" onclick="resetFormData(this)" />
				</c:if>
			</div> 			
			<!--  End button -->
</form:form>

<!--  End form -->
</div>
</div>
</div>

