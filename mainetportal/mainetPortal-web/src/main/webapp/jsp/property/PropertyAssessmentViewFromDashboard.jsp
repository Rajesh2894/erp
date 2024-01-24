<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
 <script src="js/mainet/validation.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/property/unitDetails.js"></script>
<script src="js/property/selfAssessment.js"></script>

<!-- Start Content here -->
<style>
input[type="text"].form-control {
	border: 0;
	font-weight: 600;
	border-bottom: 1px dotted #333;
	padding-left: 0;
	background: none;
}
input[type="text"].form-control:focus {
	outline: none;
}
textarea.form-control {
	border: 0;
	font-weight: 600;
	border-bottom: 1px dotted #333;
	padding-left: 0;
	background: none;
}
textarea.form-control:focus {
	outline: none;
}
select.form-control {
	border: 0;
	font-weight: 600;
	border-bottom: 1px dotted #333;
	padding-left: 0;
	background: none;
}
select.form-control:focus {
	outline: none;
}
    
</style>

<div class="content" >

<div class="AuthContent">
<div class="widget">
       <div class="widget-header">
       <c:if test="${command.getAssType() ne 'NC' && command.getAssType() ne 'C'}">  
			<h2><strong><spring:message code="propertyView.SelfAssesmentView" /></strong></h2>	
			</c:if>
			<c:if test="${command.getAssType() eq 'NC'}">  
			<h2>No Change in Assessment View</h2>
			</c:if>
			<c:if test="${command.getAssType() eq 'C'}">  
			<h2><strong><spring:message code="property.ChangeInAssessmentView" text="View Page"/></strong></h2>			
			</c:if>
			
						
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="property.Help"/></span></i></a>
				</div>
		</div>	
		
        <div class="widget-content padding">
		<form:form action="SelfAssessmentForm.html" class="form-horizontal form" name="frmSelfAssessmentForm" id="frmSelfAssessmentForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>

			<!-- Start Each Section -->
		<c:if test="${command.getIntgrtionWithBP() eq 'Y'}">
			<div class="form-group">
				<apptags:input labelCode="property.buildingpermission" path="" isDisabled="true"></apptags:input> 
				<apptags:input labelCode="propertydetails.PropertyNo." path="provisionalAssesmentMstDto.assNo" isDisabled="true"></apptags:input>
			</div>
			
			<div class="form-group">
				<apptags:input labelCode="property.LandPermissionNo." path="" isDisabled="true"></apptags:input>
			</div>
       </c:if>
       
<!----------------------------Owner Details (First owner will be the primary owner)----------------------------------------------------->
           
            <div class="accordion-toggle ">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
				code="property.Ownershipdetail" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="OwnshipDetail">
			
			<div class="form-group">
				<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="property.ownershiptype" /></label>
				<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssOwnerTypeName"  id="ownershiptype" class="form-control" disabled="true"/>
				<form:hidden  path="ownershipPrefix"  id="ownershipId" class="form-control"/>
				
				</div>
		    </div>
		   
		    
		<c:choose>
		<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
	 	<table id="singleOwnerTable" class="table table-striped table-bordered ">
        <tbody>
        <tr>
                   
        <th width="20%"><spring:message code="ownersdetail.ownersname" /></th>
		<th width="9%"><spring:message code="ownersdetail.gender" /></th>
		<th width="9%"><spring:message code="ownerdetails.relation" /></th>
		<th width="20%"><spring:message code="ownersdetails.GuardianName" /></th>
		<th width="10%"><spring:message code="ownersdetail.mobileno" /></th>
		<th width="10%"><spring:message code="property.email" /></th>
		<th width="12%"><spring:message code="ownersdetail.adharno" /></th>
		<th width="10%"><spring:message code="ownersdetail.pancard" /></th>                    	 
        </tr>
                		
      <tr>
		<td>
			<form:input id="assoOwnerName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="true"/>
		</td>
									
		<td class="ownerDetails">
				<form:input id="ownerGender" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssGenderId"   class="form-control" disabled="true"/>				
		</td>
									
		<td class="ownerDetails">		
			<form:input id="ownerRelation" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssRelationId"  class="form-control" disabled="true"/>
		</td>
							
		<td class="mand">
			<form:input id="assoGuardianName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control" disabled="true" />   
		</td>
	
		<td>
			<form:input id="assoMobileno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" disabled="true"/>   
		</td>
		
		<td><form:input id="emailId" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" disabled="true"/>   
		</td>
									
		<td class="ownerDetails">
			<form:input id="assoAddharno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control "  disabled="true"/>
		</td>
		<td class="companyDetails">
			<form:input id="assoPanno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control"  disabled="true"/>
		</td>
	</tr>
	</tbody>
    </table>
	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">	
		 <c:set var="a" value='0'/>
		 
					<table id="jointOwnerTable" class="table text-left table-striped table-bordered">
						<tbody>
								<tr>
									<th width="17%"><spring:message code="ownersdetail.ownersname" /></th>
									<th width="10%"><spring:message code="ownersdetail.gender" /></th>
									<th width="8%" ><spring:message code="ownerdetails.relation" /></th>
									<th width="17%"><spring:message code="ownersdetails.GuardianName" /></th>
									<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
									<th width="11%"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email" /></th>
									<th width="12%"><spring:message code="ownersdetail.adharno" /></th>
									<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
								</tr>

						<c:forEach var="ownershipTypeList" items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}">
            			<tr>
                		 <td>${ownershipTypeList.assoOwnerName}</td>
                		 <td>${ownershipTypeList.proAssGenderId}</td>
                		 <td>${ownershipTypeList.proAssRelationId}</td>
                		 <td>${ownershipTypeList.assoGuardianName}</td>
                		 <td>${ownershipTypeList.propertyShare}</td>
                		 <td>${ownershipTypeList.assoMobileno}</td>
                		 <td>${ownershipTypeList.eMail}</td>
                		 <td>${ownershipTypeList.assoAddharno}</td>
                		 <td>${ownershipTypeList.assoPanno}</td>
            			</tr>
                		 </c:forEach>
                		

							
						</tbody>
					</table>
					</c:when>
				<c:otherwise>
			<table id="companyDetailTable" class="table text-left table-striped table-bordered">
					<tbody>					
						<tr>
									<th width="25%"><spring:message code="property.NameOf" /> ${command.getOwnershipTypeValue()}</th>
									<th width="25%"><spring:message code="ownersdetail.contactpersonName" /></th>
									<th width="10%"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email" /></th>
									<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
						</tr>
						
 						<tr>
 						<td> 
 						<form:input id="assoOwnerName_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="true"/> 
 						</td> 
						
 						<td> 
 						<form:input id="assoGuardianName_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control " disabled="true" />  
						</td> 
								
 						<td><form:input id="assoMobileno_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" disabled="true" />   
 						</td> 
 						
 						<td><form:input id="emailId_${d}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" disabled="true"/>   
						</td>
								
						<td class="companyDetails"> 
						<form:input id="assoPanno_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control" disabled="true" /> 
						</td> 
 						</tr> 								
					</tbody>
					</table>				
					</c:otherwise>	
					</c:choose>
				 </div>
        
				
				<!--  Property Details           -->
			
           		  <div class="form-group margin-top-15">
                  
	              	<label for="oldProNo" class="col-sm-2 control-label "><spring:message code="propertydetails.oldpropertyno"/></label>
	             	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assOldpropno" type="text" id="oldProNo" class="form-control" disabled="true"/></div>
	             	
            	  	<apptags:input labelCode="property.landType" path="provisionalAssesmentMstDto.assLandTypeDesc" isDisabled="true"></apptags:input>
            	  	<form:hidden  path="landTypePrefix" class="form-control landValue"/> 
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
				<form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" class="form-control mandColorClass" id="displayEnteredKhaNo" disabled="true"/>
				</div>
		</div>
		
		<div class="form-group">
		<div class="text-center padding-top-10">
		<c:if test="${command.getAssType() eq 'VA'}">  
		<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										Fetch Land Details</button> 
		</c:if>	
		
		</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
			
		
	<c:if test="${command.getAssType() ne 'VA'}">  

	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#khasra">Khasra Details</a>
	</h4>
	<div class="panel-collapse collapse in" id="khasra">
	
	<div class="form-group">		
	<apptags:input labelCode="property.landDetails.ownerFirstName" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ocdname"></apptags:input>	
	<apptags:input labelCode="property.landDetails.fathersFirstName" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ofather"></apptags:input>
	
	</div>						
	
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
			<c:if test="${command.getAssType() eq 'VA'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										Fetch Details</button> 
			</c:if>	
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
	<c:if test="${command.getAssType() ne 'VA'}">  

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
			<c:if test="${command.getAssType() eq 'VA'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										Fetch Details</button> 
			</c:if>	
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
	<c:if test="${command.getAssType() ne 'VA'}">	

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

            	
<!--------------------------------------------------------------------Property Address Details-------------------------------------------->
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#propertyAddress"><spring:message
				code="property.Propertyaddress" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="propertyAddress">
			<div class="form-group">			                
	              	<label for="address" class="col-sm-2 control-label "><spring:message code="property.propertyaddress"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assAddress" type="text" id="address" class="form-control" disabled="true"/></div>
	              	<label for="location" class="col-sm-2 control-label "><spring:message code="property.location"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.locationName" type="text" id="location" class="form-control" disabled="true"/></div>
      	
            </div> 
        
                      
            <div class="form-group">			                
	              	<label for="pincode" class="col-sm-2 control-label "><spring:message code="property.pincode"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assPincode" type="text" id="pincode" class="form-control" disabled="true"/></div>
	              	
            </div>
            </div>
                
<!----------------------------------------------------Correspondence Address Details------------------------------------------------------------------>
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#correspondenceAddress"><spring:message
				code="property.correspondenceaddress" /></a>
			</h4>
            
			<div class="panel-collapse collapse in" id="correspondenceAddress">	
			<div class="form-group">                 
	              	<label for="correspondenceaddress" class="col-sm-2 control-label "><spring:message code="property.correspondenceaddress"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assCorrAddress" type="text" id="correspondenceaddress" class="form-control" disabled="true"/></div>
	              	<label for="pincode" class="col-sm-2 control-label"><spring:message code="property.pincode"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assCorrPincode"  id="pincode" class="form-control hasNumber" disabled="true"/></div>
            </div>            
            
          </div>
  	
        	
<!----------------------------------------------------------Tax-Zone details------------------------------------------------------ --> 
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#taxZone"><spring:message
				code="property.taxZoneDetails" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="taxZone">
			<div class="panel-body">
				<div class="form-group">
			
				<apptags:input labelCode="property.propertyZone" path="provisionalAssesmentMstDto.assWardDesc1" isDisabled="true"></apptags:input>
				
				<c:if test="${command.provisionalAssesmentMstDto.assWard2 ne null}">			
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc2" isDisabled="true"></apptags:input>
				</c:if>
				</div>
				<div class="form-group">
				<c:if test="${command.provisionalAssesmentMstDto.assWard3 ne null}">
				
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc3" isDisabled="true"></apptags:input>
				</c:if>
				<c:if test="${command.provisionalAssesmentMstDto.assWard4 ne null}">
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc4" isDisabled="true"></apptags:input>
				</c:if>
				</div>
				<div class="form-group">
				<c:if test="${command.provisionalAssesmentMstDto.assWard5 ne null}">
				
				<apptags:input labelCode="property.propertyWard" path="provisionalAssesmentMstDto.assWardDesc5" isDisabled="true"></apptags:input>
				</c:if>
				</div>
				<div class="form-group">
				<apptags:input labelCode="unitdetails.RoadType" path="provisionalAssesmentMstDto.proAssdRoadfactorDesc" isDisabled="true"></apptags:input>
				</div>
			
			</div>
			</div>
        	
<!-----------------------------------------------------------Land / Building Details------------------------------------------------------------ -->
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#BuildingDetails"><spring:message
				code="property.buildingdetails" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="BuildingDetails">
		
			<div class="form-group">
		
			<label for="proAssAcqDate" class="col-sm-2 control-label"><spring:message code="property.yearofacquisition"/></label>
	    	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssAcqDateFormat" type="text" id="proAssAcqDate" class="form-control" disabled="true"/></div>
			
			
			<label for="totalplot" class="col-sm-2 control-label "><spring:message code="property.totalplot"/></label>
	        <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assPlotArea" type="text" id="totalplot" class="form-control" disabled="true"/></div>
	              							
			</div>			
			
		</div>
	
		
<!----------------------------------------------------Unit Details---------------------------------------------------------------- -->
		
 			<h4 class="margin-top-10 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#UnitDetail"><spring:message code="property.unitdetails"/></a>
 			</h4>
		<div class="panel-collapse collapse in" id="UnitDetail">
			<table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails">
                    <tbody>
                        <tr>
 							<th width="7%"><spring:message code="unitdetails.year"/></th>
                        	<th width="4%"><spring:message code="unitdetails.unitno"/></th> 	
                        	<th width="10%"><spring:message code="unitdetails.floorno"/></th>
                        	<th width="10%"><spring:message code="unitdetails.dateofConstruction"/></th>                     
                        	<th width="20%"><spring:message code="unitdetails.constructiontype"/></th>
                        	<th width="12%"><spring:message code="unitdetails.usagefactor"/></th>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype2 ne null }">
                        	<th width="12%">Usage sub type</th>   
                        	</c:if>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype3 ne null}">
                        	<th width="12%">Usage sub type</th>   
                        	</c:if>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype4 ne null}">
                        	<th width="12%">Usage sub type</th>   
                        	</c:if>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype5 ne null}">
                        	<th width="12%">Usage sub type</th>   
                        	</c:if>
        	 				<th width="12%"><spring:message code="unitdetails.taxable"/></th> 
        	 				<th width="9%"><spring:message code="unitdetails.rate"/></th>
             	 			<c:if test="${command.getAssMethod() eq 'ARV'}">
             	 			<th width="12%"><spring:message code="unitdetails.ARV"/><br><spring:message code="unitdetails.ARVFormula"/></th>
             	 			<th width="12%"><spring:message code="unitdetails.maintCharge"/><br><spring:message code="unitdetails.maintChargeFormula"/></th>
							<th width="12%"><spring:message code="unitdetails.RV"/><br><spring:message code="unitdetails.RVFormula"/></th>
							</c:if>
							<c:if test="${command.getAssMethod() eq 'CV'}">
							<th width="10%"><spring:message code="unitdetails.CV"/></th>
							</c:if>
             	 			<th width="6%"><spring:message code="unit.ViewMoreDetails"/></th>
                        </tr>
                        
                         
                  <c:forEach var="unitDetails" items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}" varStatus="status" >
                       	<tr>
	                        <td class="text-center">${unitDetails.proFaYearIdDesc}</td>                                                                                                          
	                        <td class="text-center">${unitDetails.assdUnitNo}</td>
	                        <td class="text-center">${unitDetails.proFloorNo} </td>
	                        <td class="text-center">${unitDetails.proAssdConstructionDate}</td>
	                   
	                        <td class="text-center">${unitDetails.proAssdConstruTypeDesc}</td>
	                        <td class="text-center">${unitDetails.proAssdUsagetypeDesc} </td>
	                        <c:if test="${unitDetails.proAssdUsagetypeDesc2 ne null}">
	                          <td class="text-center">${unitDetails.proAssdUsagetypeDesc2} </td>
	                        </c:if>
	                        <c:if test="${unitDetails.proAssdUsagetypeDesc3 ne null}">
	                          <td class="text-center">${unitDetails.proAssdUsagetypeDesc3} </td>
	                        </c:if>
	                        
	                        <c:if test="${unitDetails.proAssdUsagetypeDesc4 ne null}">
	                          <td class="text-center">${unitDetails.proAssdUsagetypeDesc4} </td>
	                        </c:if>
	                        
	                        <c:if test="${unitDetails.proAssdUsagetypeDesc5 ne null}">
	                          <td class="text-center">${unitDetails.proAssdUsagetypeDesc5} </td>
	                        </c:if>
	                        	                        
	                    	<td class="text-center">${unitDetails.assdBuildupArea}</td>
	                    	 <td class="text-center">${unitDetails.assdStdRate} </td>
	                    	<c:if test="${command.getAssMethod() eq 'ARV'}">
	                    	<td class="text-center">${unitDetails.assdAlv} </td>
	                    		<td class="text-center">${unitDetails.maintainceCharge} </td>
	                        <td class="text-center">${unitDetails.assdRv}</td>
	                        </c:if>
	                        <c:if test="${command.getAssMethod() eq 'CV'}">
	                    	<td class="text-center">${unitDetails.assdCv}</td>
	                    	</c:if>
	                    	<td class="text-center"><a class="clickable btn btn-success btn-xs click_advance" data-toggle="collapse" data-target="#group-of-rows-${status.count-1}" aria-expanded="false" aria-controls="group-of-rows-${status.count-1}"><i class="fa fa-caret-down" aria-hidden="true"></i></a></td>	                           
	                    	
    					</tr>

            
				         <tr class="secondUnitRow collapse in previewTr hideDetails" id="group-of-rows-${status.count-1}">
				            <td colspan="12">
				
				               <legend class="text-left"><spring:message code="unitdetails.AdditionalUnitDetails" />
								</legend>
				            
				             	<div class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12">
					             	<div class="form-group" >   					           			
					           								           		
					
										<label for="proAssdOccupancyType" class="col-sm-2 control-label"><spring:message code="unitdetails.occupancy"/> </label>
										<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].proAssdOccupancyTypeDesc"  id="proAssdOccupancyType" class="form-control" disabled="true"/></div>										
										
										
										<label for="occupierName" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
			          		    		<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control" id="occupierName" disabled="true"/></div>
									</div>
									
									<div class="form-group" >   					           			
										<label for="natureOfProperty" class="col-sm-2 control-label"><spring:message code="property.selectNatureOfProperty"/> </label>
										<div class="col-sm-2">
										<form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfpropertyDesc1"  id="natureOfProperty" class="form-control" disabled="true"/>
										</div>
									</div>
													
								</div>
				         	</td>
				         </tr>
                   </c:forEach>

               </tbody>
			</table>
      </div>
              
               
<!---------------------------------------------- unitSpecific Additional Info------------------------------------------------------------- -->
<c:if test="${not empty command.provAsseFactDtlDto}"> 
	         
		<h4 class="margin-top-10 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#unitSpecificInfo"><spring:message code="unit.UnitSpecificAddiInfo"/></a>
 		</h4>
		<div class="panel-collapse collapse in" id="unitSpecificInfo">      
 			
 			<table id="unitSpecificInfoTable" class="table table-striped table-bordered ">
            <tbody>
            <tr>
            	 <th width="20%"><spring:message code="unit.Factor"/></th>
                 <th width="10%"><spring:message code="unit.ApplicableUnitNo"/></th>
                 <th width="30%"><spring:message code="unit.FactorValue"/></th>
            </tr>
            
            <c:forEach var="factorDetails" items="${command.getProvAsseFactDtlDto()}" >
            <tr>
              <td>${factorDetails.proAssfFactorIdDesc}</td>                                                                                                          
              <td>${factorDetails.unitNoFact}</td>
              <td>${factorDetails.proAssfFactorValueDesc}</td>
             </tr>				
			</c:forEach>
			 </tbody>
			</table>	
		</div>
 
  </c:if>
  
<!---------------------------------------------------   Tax Calculation ---------------------------------------------------------------------->
 
<c:if test="${command.getApmMode() ne 'DRAFT'}"> 
   <h4 class="margin-top-10 margin-bottom-10 panel-title contentRemove">
   <a data-toggle="collapse" href="#TaxCalculation">Tax Calculation</a>
    <a style="color: blue; text-decoration:underline;" onclick="downloadSheet()">(Download year wise tax details)</a>
    </h4>         
    <div class="panel-collapse collapse in" id="TaxCalculation">      
     
      <c:set var="totPayAmt" value="0"/>
            <c:forEach var="entry" items="${command.getDisplayMap()}">
            <c:set var="taxValue" value="${entry.value}"/>
            <c:set var="totArrTax" value="0"/>
            <c:set var="totCurrTax" value="0"/>
            <c:set var="totTotTax" value="0"/>
            <div class="table-responsive">
              <table class="table table-striped table-condensed table-bordered">
                <tbody>
                  <tr>
                    <th colspan="5">${entry.key}</th>
                  </tr>
                  <tr>
                    <th width="50"><spring:message code="propertyTax.SrNo"/></th>
                    <th width="400"><spring:message code="propertyTax.TaxName"/></th>
                    <th width="200" class="text-right"><spring:message code="propertyTax.Arrears"/></th>
                    <th width="200" class="text-right"><spring:message code="propertyTax.CurrentYear"/></th>
                    <th width="200" class="text-right"><spring:message code="propertyTax.Total"/></th>
                  </tr>
                   <c:choose>
  				<c:when test="${empty taxValue}">
		  				  <td>--</td>
		                  <td>--</td>
		                  <td class="text-right">--</td>
		                  <td class="text-right">--</td>
		                  <td class="text-right">--</td>
  					 </c:when>
  				<c:otherwise>
                <c:forEach var="tax" items="${taxValue}"  varStatus="status">
                  <tr>
                  <c:set var="totArrTax" value="${totArrTax+tax.getArrearsTaxAmt()}" > </c:set>
                  <c:set var="totCurrTax" value="${totCurrTax+tax.getCurrentYearTaxAmt()}" > </c:set>
                  <c:set var="totTotTax" value="${totTotTax+tax.getTotalTaxAmt()}" > </c:set>
                  <c:set var="totPayAmt" value="${totTotTax+tax.getTotalTaxAmt()}" > </c:set>
                  <td>${status.count}</td>
                  <td>${tax. getTaxDesc()}</td>
                  <td style="text-align:right;">${tax.getArrearsTaxAmt()}</td>
                  <td style="text-align:right;">${tax.getCurrentYearTaxAmt()}</td>
                  <td style="text-align:right;">${tax.getTotalTaxAmt()}</td>
                </tr>
                </c:forEach>
                </c:otherwise>
                </c:choose>
                  <tr>
                    <th colspan="2" class="text-right"><spring:message code="propertyTax.Total"/></th>
                    <th style="text-align:right;">${totArrTax}</th>
					<th style="text-align:right;">${totCurrTax}</th>
					<th style="text-align:right;">${totTotTax}</th>
                  </tr>
                </tbody>
              </table>
            </div>
            </c:forEach>

            <div class="table-responsive margin-top-10">
              <table class="table table-striped table-bordered">
                <tr>
                  <th width="500"><spring:message code="propertyTax.TotalTaxPayable"/></th>
                  <th width="500" style="text-align:right;">${command.provisionalAssesmentMstDto.billTotalAmt}</th>
                </tr>
              </table>
            </div>
          </div>      
 
        <h4 class="margin-top-10 margin-bottom-10 panel-title">
        <a data-toggle="collapse" href="#Payment">
        Payment</a></h4>
            <div class="panel-collapse collapse in" id="Payment">      
            <div class="form-group hidden-print">

      <!--       N=new Assessment
            C=Change in Assessment
            NC=No Change in Assessment
            A=Authorize -->
            <c:if test="${command.getAssType() eq 'N' || command.getAssType() eq 'NC' || command.getAssType() eq 'C' || command.getAssType() eq 'VA'}">  
				
             	<label for="billTotamount" class="col-sm-2 control-label "><spring:message code="propertyView.payAmt"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.billTotalAmt" type="text" id="billTotamount" class="form-control " disabled="true"/></div>
	              	
            </c:if>
            <c:if test="${command.getAssType() eq 'A'}">  
             	<label for="billTotamount" class="col-sm-2 control-label "><spring:message code="propertyView.paidAmt"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.billPaidAmt" type="text" id="billTotamount" class="form-control " disabled="true"/></div>
            </c:if>
            </div>
            </div>
          
</c:if>

<!----------------------------------------------------    Document Upload ------------------------------------------------------------->

<%-- <c:if test="${not empty command.checkList}"> 

<h4 class="margin-top-0 margin-bottom-10 panel-title"> <a data-toggle="collapse" href="#DocumentUpload"><spring:message code="propertyTax.DocumentsUpload"/></a></h4>
                
                <div id="DocumentUpload" class="panel-collapse collapse in">
                  <div class="panel-body">
                    
										<div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th> <spring:message
																		code="water.serialNo" text="Sr No" />
															</th>
															<th> <spring:message
																		code="water.docName" text="Document Name" />
															</th>
															<th> <spring:message
																		code="water.status" text="Status" />
															</th>
															<th> <spring:message
																		code="water.uploadText" text="Upload" />
															</th>
														</tr>
														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">
															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}</td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}</td>
																	</c:otherwise>
																</c:choose>
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td> <spring:message
																				code="water.doc.mand" />
																	</td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td> <spring:message
																				code="water.doc.opt" />
																	</td>
																</c:if>
																<td><div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="ALL_UPLOAD_VALID_EXTENSION"
																			currentCount="${lk.index}" />
																	</div>
																	</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>									
                  </div>
                </div>
    
          
        </c:if> --%> 
         <c:if test="${not empty command.docList}">	  
        <h4 class="margin-top-0 margin-bottom-10 panel-title"> <a data-toggle="collapse" href="#DocumentDownLoad"><spring:message code="propertyTax.UploadDocumnet"/></a></h4>
                
                <div id="DocumentDownLoad" class="panel-collapse collapse in"> 
                        
							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="water.serialNo" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="water.docName" text="Document Name" /></label></th>
												     <th><label class="tbold"><spring:message
																code="water.download"/></label></th>
												</tr>

												<c:forEach items="${command.docList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lk.count}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.documentName}</label></td>
															</c:when>
															<%-- <c:otherwise>
																<td><label>${lookUp.documentName}</label></td>
															</c:otherwise> --%>
														</c:choose>
														  <td>
														  <c:set var="links" value="${fn:substringBefore(lookUp.uploadedDocumentPath, lookUp.descriptionType)}" />
															<apptags:filedownload filename="${lookUp.descriptionType}" filePath="${lookUp.uploadedDocumentPath}"  actionUrl="SelfAssessmentForm.html?Download"></apptags:filedownload>
					                                    </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
					 </c:if>     
          </div>
          

<%-- <c:if test="${command.getAssType() eq 'N' || command.getAssType() eq 'NC' ||  command.getAssType() eq 'C' || command.getAssType() eq 'VA'}">  
       <div class="panel panel-default">
			<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />	
		</div>	
</c:if> --%>
		
		
       
        <!-- Start button -->


      <div class="text-center padding-top-10">
<c:set var="assType" value="${command.getAssType()}"></c:set>
              			
						<c:if test="${command.getAssType() eq 'NC'}">  
						<button type="button" class="btn btn-warning"
							onclick="window.location.href='NoChangeInAssessment.html'" id="back">
							<spring:message code="" text="Back"/>
						</button>
						</c:if>								
						
						<c:if test="${command.getAssType() eq 'VA'}">  												
						<a href="CitizenHome.html" class="btn btn-danger" ><spring:message code="bt.back"/></a>					
						</c:if>
						
						<c:if test="${command.provisionalAssesmentMstDto.getAssStatus() eq 'SD'}">
						<button class="btn btn-blue-2" type="button"  onclick="editSelfAssForm(this)" id="submit">
										<spring:message code="property.Edit"/></button>
						
						</c:if>
						
						<c:if test="${command.getAssType() eq 'C'}">  
						<button class="btn btn-blue-2" type="button"  onclick="editChangeAssForm(this)" id="submit">
										<spring:message code="property.Edit"/></button>
						</c:if>						
		<!--  End button -->
		</div>
		
 </form:form>  
  </div>      
		</div>
		</div>
 </div>
