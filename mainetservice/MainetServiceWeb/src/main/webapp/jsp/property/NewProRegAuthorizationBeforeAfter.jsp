 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/newPropertyRegistration.js" ></script>   
<script src="js/property/newPropRegUnit.js" type="text/javascript"></script>
<script type="text/javascript" src="js/property/PropertyAuthorizationBeforeAfter.js"></script>

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

#companyDetailTable,#jointOwnerTable,#singleOwnerTable{    width: 780px !important;}

.box-shadow-right-1 { box-shadow: 1px 0px 0px #333;}
</style>



      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="property.NewPropertyRegistrationAuthorizationPreview"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> </div>
        </div>
        <div class="widget-content padding" id="receipt">
        <form:form action="SelfAssessmentForm.html" class="form-horizontal form" name="frmSelfAssessmentForm" id="frmSelfAssessmentForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
          <div class="row">
            <div class="col-sm-6 box-shadow-right-1">
			<!-- Start Each Section -->
			<div class="form-group">
				<apptags:input labelCode="property.buildingpermission" path="" isDisabled="true"></apptags:input> 
				<apptags:input labelCode="propertydetails.PropertyNo." path="authCompBeforeDto.assNo" isDisabled="true"></apptags:input>
			</div>
			
			<div class="form-group">
				<apptags:input labelCode="property.LandPermissionNo." path="" isDisabled="true"></apptags:input>
			</div>
       
<!----------------------------------------- Owner Details Before (First owner will be the primary owner)  ------------------------------->
           
            <div class="accordion-toggle ">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
				code="property.Ownershipdetail" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="OwnshipDetail">
			
			<div class="form-group">
				<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="property.ownershiptype" /></label>
				<div class="col-sm-4"><form:input path="authCompBeforeDto.proAssOwnerTypeName"  id="ownershiptype" class="form-control" disabled="true"/>
				<form:hidden  path="ownershipPrefix"  id="ownershipId" class="form-control"/>
				
				</div>
		    </div>
		   
		    
		<c:choose>
		<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
		<div class="table-responsive">
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
		<th width="10%"><spring:message code="ownersdetail.pancard" />                    	 
        </tr>
                		
      <tr>
		<td>
			<form:input id="assoOwnerName" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="true"/>
		</td>
									
		<td class="ownerDetails">
						<c:set var="baseLookupCode" value="GEN" /> 
						<form:select path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" class="form-control " id="ownerGender_${d}" disabled="true">
						<form:option value="0">
						<spring:message code="property.sel.optn" />
						</form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
						<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
						</form:select>
		</td>
									
		<td class="ownerDetails">
		<c:set var="baseLookupCode" value="REL" /> 
		<form:select path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" class="form-control " id="ownerRelation_${d}" disabled="true">
		<form:option value="0">
		<spring:message code="property.sel.optn" />
		</form:option>
		<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
		<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
		</c:forEach>
		</form:select>
		</td>
							
		<td class="mand">
		<form:input id="assoGuardianName_${d}" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control" disabled="true" />   
		</td>
	
		<td><form:input id="assoMobileno_${d}" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" disabled="true"/>   
		</td>
		
		<td><form:input id="emailId_${d}" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control" disabled="true"/>   
		</td>
								
		<td class="ownerDetails">
		<form:input id="assoAddharno_${d}" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control "  disabled="true"/>
		</td>
		<td class="companyDetails">
		<form:input id="assoPanno_${d}" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control"  disabled="true"/>
		</td>
	</tr>
	</tbody>
    </table>
    </div>
	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">	
		 <c:set var="a" value='0'/>
		 <div class="table-responsive">
		 
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

						<c:forEach var="ownershipTypeList" items="${command.getAuthCompBeforeDto().getProvisionalAssesmentOwnerDtlDtoList()}">
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
					</table></div>
					</c:when>
				<c:otherwise>
								 <div class="table-responsive">
				
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
 						<form:input id="assoOwnerName_${d}" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="true"/> 
 						</td> 
						
 						<td> 
 						<form:input id="assoGuardianName_${d}" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control " disabled="true" />  
						</td> 
								
 						<td><form:input id="assoMobileno_${d}" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" disabled="true" />   
 						</td> 
 						
 						<td><form:input id="emailId_${d}"
										path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control" disabled="true"/>   
					    </td>
								
						<td class="companyDetails"> 
						<form:input id="assoPanno_${d}" path="authCompBeforeDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control" disabled="true" /> 
						</td> 
 						</tr> 								
					</tbody>
					</table>
					</div>				
					</c:otherwise>	
					</c:choose>
				 </div>
        
				
<!----------------------------------------------------------Property Details Before-------------------------------------------------------------------------->
            
				<h4 class="margin-top-10 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#propertyDetail"><spring:message
				code="property.LandDetail" /></a>
				</h4>
	
			<div class="panel-collapse collapse in" id="propertyDetail">
					
					<div class="form-group">
                  
	              	<label for="oldProNo" class="col-sm-2 control-label "><spring:message code="propertydetails.oldpropertyno"/></label>
	             	 <div class="col-sm-4"><form:input path="authCompBeforeDto.assOldpropno" type="text" id="oldProNo" class="form-control" disabled="true"/></div>
	             	 
	             	 <apptags:input labelCode="property.landType" path="authCompBeforeDto.assLandTypeDesc" isDisabled="true"></apptags:input>
	             	 
	             	 <%-- <label for="csnNo" class="col-sm-2 control-label"><spring:message code="propertydetails.csnno"/></label>
	              	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" id="csnNo" class="form-control" disabled="true"/></div> --%>
            	  </div>
            	  
            	   <div class="form-group">
            	   		<apptags:input labelCode="property.district" path="authCompBeforeDto.assDistrict" isDisabled="true"></apptags:input>
    	             	 <apptags:input labelCode="property.tahasil" path="authCompBeforeDto.assTahasil" isDisabled="true"></apptags:input>
            	   		
            	   </div>
            	   
            	   <div class="form-group">
            	   		<label for="villageMauja" class="col-sm-2 control-label"><spring:message code="propertydetails.village"/></label>
	              		<div class="col-sm-4"><form:input path="authCompBeforeDto.tppVillageMauja" type="text" id="villageMauja" class="form-control" disabled="true"/></div>
            	  		<label for="csnNo" class="col-sm-2 control-label"><spring:message code="propertydetails.csnno"/></label>
	              	 	<div class="col-sm-4"><form:input path="authCompBeforeDto.tppPlotNoCs" type="text" id="csnNo" class="form-control" disabled="true"/></div>
            	   </div>
            	   
            	  <div class="form-group">
            	   		<apptags:input labelCode="property.halkaNo" path="authCompBeforeDto.assTahasil" isDisabled="true"></apptags:input>
            	   		<label for="plotNo" class="col-sm-2 control-label"><spring:message code="propertydetails.plotno"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.tppPlotNo" type="text" id="plotNo" class="form-control" disabled="true"/></div>
            	   
            		</div>
					
					<div class="form-group">           		
            		 	<apptags:input labelCode="property.propertyDeedNo" path="authCompBeforeDto.assDeedNo" isDisabled="true"></apptags:input>            		
            		</div>
			
           			<%-- <div class="form-group">
                  
	              	<label for="oldProNo" class="col-sm-2 control-label "><spring:message code="propertydetails.oldpropertyno"/></label>
	             	 <div class="col-sm-4"><form:input path="authCompBeforeDto.assOldpropno" type="text" id="oldProNo" class="form-control" disabled="true"/></div>
	             	 <label for="csnNo" class="col-sm-2 control-label"><spring:message code="propertydetails.csnno"/></label>
	              	 <div class="col-sm-4"><form:input path="authCompBeforeDto.tppPlotNoCs" type="text" id="csnNo" class="form-control" disabled="true"/></div>
            	  </div>
            	
            	 <div class="form-group">
                  
	            	 <label for="surveyProNo" class="col-sm-2 control-label "><spring:message code="propertydetails.surveyno."/></label>
	             	 <div class="col-sm-4"><form:input path="authCompBeforeDto.tppSurveyNumber" type="text" id="surveyProNo" class="form-control" disabled="true"/></div>
	             	 <label for="khataNo" class="col-sm-2 control-label"><spring:message code="propertydetails.khatano"/></label>
	              	 <div class="col-sm-4"><form:input path="authCompBeforeDto.tppKhataNo" type="text" id="khataNo" class="form-control" disabled="true"/></div>
	              	 <label for="plotNo" class="col-sm-2 control-label"><spring:message code="propertydetails.plotno"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.tppPlotNo" type="text" id="plotNo" class="form-control" disabled="true"/></div>
            	</div> --%>
            	
            	
            	<%-- <div class="form-group">
                  
	              	<label for="tojiNo" class="col-sm-2 control-label "><spring:message code="propertydetails.tojino" /></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.tppTojiNo" type="text" id="tojiNo" class="form-control" disabled="true" /></div>
	              	<label for="plotNo" class="col-sm-2 control-label"><spring:message code="propertydetails.plotno"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.tppPlotNo" type="text" id="plotNo" class="form-control" disabled="true"/></div>
            	</div> --%>
            	
            	
            	<%-- <div class="form-group">
                  
	              	<label for="streetNo" class="col-sm-2 control-label "><spring:message code="propertydetails.streetno"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assStreetNo" type="text" id="streetNo" class="form-control" disabled="true"/></div>
	              	<label for="villageMauja" class="col-sm-2 control-label"><spring:message code="propertydetails.village"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.tppVillageMauja" type="text" id="villageMauja" class="form-control" disabled="true"/></div>
            	</div> --%>      	
            </div>
           
            	
<!-----------------------------------------------------------Property Address Details Before-------------------------------------------------------------->
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#propertyAddress"><spring:message
				code="property.Propertyaddress" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="propertyAddress">
			<div class="form-group">			                
	              	<label for="address" class="col-sm-2 control-label "><spring:message code="property.propertyaddress"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assAddress" type="text" id="address" class="form-control" disabled="true"/></div>
	              	<label for="location" class="col-sm-2 control-label "><spring:message code="property.location"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.locationName" type="text" id="location" class="form-control" disabled="true"/></div>
       					<%-- <input type="hidden" value="${command.provisionalAssesmentMstDto.locId}" id="locId"/>
     					<input type="hidden" value="${command.orgId}" id="orgId"/>
						<input type="hidden" value="${command.deptId}" id="deptId"/> --%>
            </div> 
            <!-- <div id="wardZone">
		
			</div> -->
                     
            <div class="form-group">			                
	              	<label for="pincode" class="col-sm-2 control-label "><spring:message code="property.pincode"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assPincode" type="text" id="pincode" class="form-control" disabled="true"/></div>
	              	<%-- <label for="email" class="col-sm-2 control-label "><spring:message code="property.email"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assEmail" id="email" class="form-control" disabled="true"/></div> --%>
<%-- 	              	<apptags:input labelCode="unitdetails.RoadType" path="authCompBeforeDto.proAssdRoadfactorDesc" isDisabled="true"></apptags:input>  	 --%>
	              	
            </div>
            </div>
                
			<!--         Correspondence Address Details     -->
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#correspondenceAddress"><spring:message
				code="property.correspondenceaddress" /></a>
			</h4>
            
			<div class="panel-collapse collapse in" id="correspondenceAddress">	
			<div class="form-group">                 
	              	<label for="correspondenceaddress" class="col-sm-2 control-label "><spring:message code="property.correspondenceaddress"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assCorrAddress" type="text" id="correspondenceaddress" class="form-control" disabled="true"/></div>
	              	<label for="pincode" class="col-sm-2 control-label"><spring:message code="property.pincode"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assCorrPincode"  id="pincode" class="form-control hasNumber" disabled="true"/></div>
            </div>            
            <%-- <div class="form-group">                  
	              	<label for="email" class="col-sm-2 control-label "><spring:message code="property.email"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assCorrEmail" id="email" class="form-control" disabled="true"/></div>
	              	
            </div> --%>
          </div>
         
<!-- ------------------------------------------  Last Payment Before Details ------------------------------------------------------------------------->
			
		<%-- 	<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#LastBillPayment"><spring:message
				code="property.lastpayment" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="LastBillPayment">			
			<div class="form-group">                 
	              	<label for="LastPayment" class="col-sm-2 control-label "><spring:message code="propertyView.LastPayment"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.proAssBillPaymentDesc" type="text" id="LastPayment" class="form-control" disabled="true"/></div>
				
            </div>
            
            <div class="form-group">                 
	              	<label for="receiptno" class="col-sm-2 control-label "><spring:message code="property.receiptno"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assLpReceiptNo" type="text" id="receiptno" class="form-control" disabled="true"/></div>              	
	              	<label for="receiptamount" class="col-sm-2 control-label"><spring:message code="property.receiptamount"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assLpReceiptAmt" type="text" id="receiptamount" class="form-control hasNumber" disabled="true"/></div>          		           		
            </div>
			
			 <div class="form-group">
			 	<label for="receiptdate" class="col-sm-2 control-label"><spring:message code="property.receiptdate"/></label>
	            <div class="col-sm-4"><form:input path="authCompBeforeDto.proAssLpReceiptDateFormat" type="text" id="proAssLpReceiptDateFormat" class="form-control" disabled="true"/></div>			 					 		
                 <label for="lastpaymentMadeUpto" class="col-sm-2 control-label"><spring:message code="property.lastpaymentupto"/></label>
	             <div class="col-sm-4"><form:input path="authCompBeforeDto.proAssLpYearDesc" type="text" id="lastpaymentMadeUpto" class="form-control" disabled="true" /></div>
			</div>
		
			<div class="form-group">                  
	              	<label for="billamount" class="col-sm-2 control-label "><spring:message code="property.billamount"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.billAmount" type="text" id="billamount" class="form-control " disabled="true"/></div>
	              	<label for="outstandingAmount" class="col-sm-2 control-label"><spring:message code="property.OutStandingAmount"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.outstandingAmount" type="text" id="outstandingAmount" class="form-control" disabled="true" /></div>       
            </div>
        	</div> --%>
        	
<!-------------------------------------------------------------------------TaxZone details---------------------------------------------------- -->
        	
        	<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#taxZone"><spring:message
				code="property.taxZoneDetails" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="taxZone">
        	<div class="panel-body">
			<div class="form-group">
		
			<apptags:input labelCode="property.propertyZone" path="authCompBeforeDto.assWardDesc1" isDisabled="true"></apptags:input>
			
			<c:if test="${command.authCompBeforeDto.assWard2 ne null}">			
			<apptags:input labelCode="property.propertyWard" path="authCompBeforeDto.assWardDesc2" isDisabled="true"></apptags:input>
			</c:if>
			</div>
			<div class="form-group">
			<c:if test="${command.authCompBeforeDto.assWard3 ne null}">
			
			<apptags:input labelCode="property.propertyWard" path="authCompBeforeDto.assWardDesc3" isDisabled="true"></apptags:input>
			</c:if>
			<c:if test="${command.authCompBeforeDto.assWard4 ne null}">
			<apptags:input labelCode="property.propertyWard" path="authCompBeforeDto.assWardDesc4" isDisabled="true"></apptags:input>
			</c:if>
			</div>
			<div class="form-group">
			<c:if test="${command.authCompBeforeDto.assWard5 ne null}">
			
			<apptags:input labelCode="property.propertyWard" path="authCompBeforeDto.assWardDesc5" isDisabled="true"></apptags:input>
			</c:if>
			</div>
			<div class="form-group">
			<apptags:input labelCode="unitdetails.RoadType" path="authCompBeforeDto.proAssdRoadfactorDesc" isDisabled="true"></apptags:input>
			</div>
			<%-- <div class="form-group">
					<label for="totalplot" class="col-sm-2 control-label "><spring:message code="property.totalplot"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assPlotArea" type="text" id="totalplot" class="form-control" disabled="true"/></div>
					
					<label for="buildup" class="col-sm-2 control-label "><spring:message code="property.buildup"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assBuitAreaGr" type="text" id="buildup" class="form-control" disabled="true"/></div>
			</div> --%>
		</div>
        	
        	
        	</div>
        	
        	
        	
        	
        	
<!----------------------------------------------------- Building Details Before ----------------------------------------------------->
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#BuildingDetails"><spring:message
				code="property.buildingdetails" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="BuildingDetails">
		
			<div class="form-group">
		
			<label for="proAssAcqDate" class="col-sm-2 control-label"><spring:message code="property.yearofacquisition"/></label>
	    	<div class="col-sm-4"><form:input path="authCompBeforeDto.proAssAcqDateFormat" type="text" id="proAssAcqDate" class="form-control" disabled="true"/></div>
				
			<label for="totalplot" class="col-sm-2 control-label "><spring:message code="property.totalplot"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assPlotArea" type="text" id="totalplot" class="form-control" disabled="true"/></div>
		<%-- 	<label for="propertytype" class="col-sm-2 control-label"><spring:message code="property.propertytype"/></label>
	    	<div class="col-sm-4"><form:input path="authCompBeforeDto.proAssPropType" type="text" id="propertytype" class="form-control" disabled="true"/></div> --%>
	
			</div>
			
			<%-- <div class="form-group">
					<label for="totalplot" class="col-sm-2 control-label "><spring:message code="property.totalplot"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assPlotArea" type="text" id="totalplot" class="form-control" disabled="true"/></div>
					
					<label for="buildup" class="col-sm-2 control-label "><spring:message code="property.buildup"/></label>
	              	<div class="col-sm-4"><form:input path="authCompBeforeDto.assBuitAreaGr" type="text" id="buildup" class="form-control" disabled="true"/></div>
			</div> --%>
		</div>
	
		
<!-- 			Unit Details -->
		
 			<h4 class="margin-top-10 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#UnitDetail"><spring:message code="property.unitdetails"/></a>
 			</h4>
		<div class="panel-collapse collapse in" id="UnitDetail">
		<div class="table-responsive">
			<table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails">
                    <tbody>
                        <tr>
 							<th width="7%"><spring:message code="unitdetails.year"/></th>
                        	<th width="4%"><spring:message code="unitdetails.unitno"/></th> 	
<%--                         	<th width="7%"><spring:message code="unitdetails.unittype"/></th> --%>
                        	<th width="10%"><spring:message code="unitdetails.floorno"/></th>
                        	<th width="10%"><spring:message code="unitdetails.dateofConstruction"/></th>
                        	<th width="12%"><spring:message code="unitdetails.usagefactor"/></th>
                        	<th width="20%"><spring:message code="unitdetails.constructiontype"/></th>                        	
             	 			<th width="12%"><spring:message code="unitdetails.taxable"/></th> 
             	 			<th width="6%"><spring:message code="unit.ViewMoreDetails"/></th>
                        </tr>
                        
                         
                        <c:forEach var="unitDetails" items="${command.getAuthCompBeforeDto().getProvisionalAssesmentDetailDtoList()}" varStatus="status" >
                       	<tr>
                        <td class="text-center">${unitDetails.proFaYearIdDesc}</td>                                                                                                          
                        <td class="text-center">${unitDetails.assdUnitNo}</td>
<%--                         <td class="text-center">${unitDetails.proAssdUnitType}</td> --%>
                        <td class="text-center">${unitDetails.proFloorNo} </td>
                        <td class="text-center">${unitDetails.proAssdConstructionDate}</td>
     
                        <td class="text-center">${unitDetails.proAssdUsagetypeDesc} </td>
                        <td class="text-center">${unitDetails.proAssdConstruTypeDesc}</td>
                    	<td class="text-center">${unitDetails.assdBuildupArea}</td>
                    	<td class="text-center"><a class="clickable btn btn-success btn-xs click_advance" data-toggle="collapse" data-target="#group-of-rows-${status.count-1}" aria-expanded="false" aria-controls="group-of-rows-${status.count-1}"><i class="fa fa-caret-down" aria-hidden="true"></i></a></td>	                           
                    	
    					</tr>

            
          <tr class="secondUnitRowBefore collapse in previewTr hideDetails" id="group-of-rows-${status.count-1}">
            <td colspan="12">

               <legend class="text-left"><spring:message code="unitdetails.AdditionalUnitDetails" />
				</legend>
            
             <div class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12">
             	<div class="form-group" >   
           			
           			<%-- <label for="proAssdRoadfactor" class="col-sm-2 control-label"><spring:message code="unitdetails.RoadType"/> </label>
					<div class="col-sm-2"><form:input path="authCompBeforeDto.provisionalAssesmentDetailDtoList[${status.count-1}].proAssdRoadfactorDesc"  id="proAssdRoadfactor" class="form-control" disabled="true"/></div> --%>
           		

					<label for="occupancy" class="col-sm-2 control-label"><spring:message code="unitdetails.occupancy"/> </label>
					<div class="col-sm-2"><form:input path="authCompBeforeDto.provisionalAssesmentDetailDtoList[${status.count-1}].proAssdOccupancyTypeDesc"  id="proAssdOccupancyType" class="form-control" disabled="true"/></div>

					<%-- <label for="monthly-rent" class="col-sm-2 control-label"><spring:message code="property.rent"/> </label>
					<div class="col-sm-2"><form:input path="authCompBeforeDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdAnnualRent" class="form-control" id="monthly-rent" disabled="true"/></div> --%>
				
					<label for="occupierName" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
			        <div class="col-sm-2"><form:input path="authCompBeforeDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control" id="occupierName" disabled="true"/></div> 
				</div>
				
				<%-- <div class="form-group">	    
 					<label for="occupierName" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
			        <div class="col-sm-2"><form:input path="authCompBeforeDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control" id="occupierName" disabled="true"/></div> 
           			
          		</div> --%>		
          		
          		<div class="form-group">	    
 					<label for="natureOfProperty" class="col-sm-2 control-label"><spring:message code="property.selectNatureOfProperty"/></label>
			        <div class="col-sm-2"><form:input path="authCompBeforeDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfpropertyDesc1" type="text" class="form-control" id="natureOfProperty" disabled="true"/></div> 
           			
          		</div>		
			</div>
         </td>
         </tr>
                    </c:forEach>

               </tbody>
</table></div>
               </div>
              
               
<!--------------------------------------------------- unitSpecific Additional Info Before ----------------------------------------------------->
<c:if test="${not empty command.authCompFactDto}"> 
	         
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
            
            <c:forEach var="factorDetails" items="${command.getAuthCompFactDto()}" >
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
 

<!-------------------------------------------------------     Uploaded Document     ------------------------------------------------------->

                         <c:if test="${not empty command.documentList}">	
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

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose>
														  <td>
														  <c:set var="links" value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
															<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}" actionUrl="SelfAssessmentForm.html?Download"></apptags:filedownload>
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
            </div>
       
          <div class="col-sm-6">
       
<!-----------------------------------------Owner Details After (First owner will be the primary owner)  ------------------------------------->
           
            <div class="accordion-toggle ">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#OwnshipDetailAfter"><spring:message
				code="property.Ownershipdetail" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="OwnshipDetailAfter">
			
			<div class="form-group">
				<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="property.ownershiptype" /></label>
				<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssOwnerTypeName"  id="ownershiptype" class="form-control" disabled="true"/>
				<form:hidden  path="ownershipPrefix"  id="ownershipId" class="form-control"/>
				
				</div>
		    </div>
		   
		    
		<c:choose>
		<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
		<div class="table-responsive">		
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
		<th width="10%"><spring:message code="ownersdetail.pancard" />                    	 
        </tr>
                		
      <tr>
		<td>
			<form:input id="assoOwnerName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="true"/>
		</td>
									
		<td class="ownerDetails">
						<c:set var="baseLookupCode" value="GEN" /> 
						<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" class="form-control " id="ownerGender_${d}" disabled="true">
						<form:option value="0">
						<spring:message code="property.sel.optn" />
						</form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
						<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
						</form:select>
		</td>
									
		<td class="ownerDetails">
		<c:set var="baseLookupCode" value="REL" /> 
		<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" class="form-control " id="ownerRelation_${d}" disabled="true">
		<form:option value="0">
		<spring:message code="property.sel.optn" />
		</form:option>
		<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
		<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
		</c:forEach>
		</form:select>
		</td>
							
		<td class="mand">
		<form:input id="assoGuardianName_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control" disabled="true" />   
		</td>
	
		<td ><form:input id="assoMobileno_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" disabled="true"/>   
		</td>
		
		<td><form:input id="emailId_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control" disabled="true"/>   
		</td>
									
		<td class="ownerDetails">
		<form:input id="assoAddharno_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control "  disabled="true"/>
		</td>
		<td class="companyDetails">
		<form:input id="assoPanno_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control"  disabled="true"/>
		</td>
	</tr>
	</tbody>
    </table>
    </div>
	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">	
		 <c:set var="a" value='0'/>
		 		 <div class="table-responsive">
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
					</div>
					</c:when>
				<c:otherwise>
			<div class="table-responsive">
			
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
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control" disabled="true"/>   
							</td>
								
						<td class="companyDetails"> 
						<form:input id="assoPanno_${d}" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control" disabled="true" /> 
						</td> 
 						</tr> 								
					</tbody>
					</table></div>				
					</c:otherwise>	
					</c:choose>
				 </div>
        
				
<!------------------------------------------------------  Property Details After  ------------------------------------------------------------ -->
            
				<h4 class="margin-top-10 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#propertyDetailAfter"><spring:message
				code="property.Propertydetail" /></a>
				</h4>
	
			<div class="panel-collapse collapse in" id="propertyDetailAfter">
					
					<div class="form-group">
                  
	              	<label for="oldProNo" class="col-sm-2 control-label "><spring:message code="propertydetails.oldpropertyno"/></label>
	             	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assOldpropno" type="text" id="oldProNo" class="form-control" disabled="true"/></div>
	             	 
	             	 <apptags:input labelCode="property.landType" path="provisionalAssesmentMstDto.assLandTypeDesc" isDisabled="true"></apptags:input>
	             	 
	             	 <%-- <label for="csnNo" class="col-sm-2 control-label"><spring:message code="propertydetails.csnno"/></label>
	              	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" id="csnNo" class="form-control" disabled="true"/></div> --%>
            	  </div>
            	  
            	   <div class="form-group">
            	   		<apptags:input labelCode="property.district" path="provisionalAssesmentMstDto.assDistrict" isDisabled="true"></apptags:input>
    	             	 <apptags:input labelCode="property.tahasil" path="provisionalAssesmentMstDto.assTahasil" isDisabled="true"></apptags:input>
            	   		
            	   </div>
            	   
            	   <div class="form-group">
            	   		<label for="villageMauja" class="col-sm-2 control-label"><spring:message code="propertydetails.village"/></label>
	              		<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppVillageMauja" type="text" id="villageMauja" class="form-control" disabled="true"/></div>
            	  		<label for="csnNo" class="col-sm-2 control-label"><spring:message code="propertydetails.csnno"/></label>
	              	 	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" id="csnNo" class="form-control" disabled="true"/></div>
            	   </div>
            	   
            	  <div class="form-group">
            	   		<apptags:input labelCode="property.halkaNo" path="provisionalAssesmentMstDto.assTahasil" isDisabled="true"></apptags:input>
            	   		<label for="plotNo" class="col-sm-2 control-label"><spring:message code="propertydetails.plotno"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppPlotNo" type="text" id="plotNo" class="form-control" disabled="true"/></div>
            	   
            		</div>
					
					<div class="form-group">           		
            		 	<apptags:input labelCode="property.propertyDeedNo" path="provisionalAssesmentMstDto.assDeedNo" isDisabled="true"></apptags:input>            		
            		</div>
			
			
			
           			<%-- <div class="form-group">
                  
	              	<label for="oldProNo" class="col-sm-2 control-label "><spring:message code="propertydetails.oldpropertyno"/></label>
	             	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assOldpropno" type="text" id="oldProNo" class="form-control" disabled="true"/></div>
	             	 <label for="csnNo" class="col-sm-2 control-label"><spring:message code="propertydetails.csnno"/></label>
	              	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" id="csnNo" class="form-control" disabled="true"/></div>
            	  </div>
            	
            	 <div class="form-group">
                  
	            	 <label for="surveyProNo" class="col-sm-2 control-label "><spring:message code="propertydetails.surveyno."/></label>
	             	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppSurveyNumber" type="text" id="surveyProNo" class="form-control" disabled="true"/></div>
	             	 <label for="khataNo" class="col-sm-2 control-label"><spring:message code="propertydetails.khatano"/></label>
	              	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppKhataNo" type="text" id="khataNo" class="form-control" disabled="true"/></div>
            		 <label for="plotNo" class="col-sm-2 control-label"><spring:message code="propertydetails.plotno"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppPlotNo" type="text" id="plotNo" class="form-control" disabled="true"/></div>
            	</div> --%>
            	
            	
            	<%-- <div class="form-group">
                  
	              	<label for="tojiNo" class="col-sm-2 control-label "><spring:message code="propertydetails.tojino" /></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppTojiNo" type="text" id="tojiNo" class="form-control" disabled="true" /></div>
	              	<label for="plotNo" class="col-sm-2 control-label"><spring:message code="propertydetails.plotno"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppPlotNo" type="text" id="plotNo" class="form-control" disabled="true"/></div>
            	</div> --%>
            	
            	
            	<%-- <div class="form-group">
                  
	              	<label for="streetNo" class="col-sm-2 control-label "><spring:message code="propertydetails.streetno"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assStreetNo" type="text" id="streetNo" class="form-control" disabled="true"/></div>
	              	<label for="villageMauja" class="col-sm-2 control-label"><spring:message code="propertydetails.village"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppVillageMauja" type="text" id="villageMauja" class="form-control" disabled="true"/></div>
            	</div> --%>      	
            </div>
           
            	
<!----------------------------------------Property Address Details After------------------------------------------------------------------------------>
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#propertyAddressAfter"><spring:message
				code="property.Propertyaddress" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="propertyAddressAfter">
			<div class="form-group">			                
	              	<label for="address" class="col-sm-2 control-label "><spring:message code="property.propertyaddress"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assAddress" type="text" id="address" class="form-control" disabled="true"/></div>
	              	<label for="location" class="col-sm-2 control-label "><spring:message code="property.location"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.locationName" type="text" id="location" class="form-control" disabled="true"/></div>
      
      
      					<%-- <input type="hidden" value="${command.provisionalAssesmentMstDto.locId}" id="locId"/>
      					<input type="hidden" value="${command.orgId}" id="orgId"/>
						<input type="hidden" value="${command.deptId}" id="deptId"/> --%>
            </div> 
           <!--  <div id="wardZone">
		
			</div> -->
                       
            <div class="form-group">			                
	              	<label for="pincode" class="col-sm-2 control-label "><spring:message code="property.pincode"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assPincode" type="text" id="pincode" class="form-control" disabled="true"/></div>
	              	<%-- <label for="email" class="col-sm-2 control-label "><spring:message code="property.email"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assEmail" id="email" class="form-control" disabled="true"/></div> --%>
<%-- 	              	<apptags:input labelCode="unitdetails.RoadType" path="provisionalAssesmentMstDto.proAssdRoadfactorDesc" isDisabled="true"></apptags:input>  	 --%>
	              	
            </div>
            </div>
                
<!------------------------------------------------------------------ Correspondence Address Details  After --------------------------------------------   -->
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#correspondenceAddressAfter"><spring:message
				code="property.correspondenceaddress" /></a>
			</h4>
            
			<div class="panel-collapse collapse in" id="correspondenceAddressAfter">	
			<div class="form-group">                 
	              	<label for="correspondenceaddress" class="col-sm-2 control-label "><spring:message code="property.correspondenceaddress"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assCorrAddress" type="text" id="correspondenceaddress" class="form-control" disabled="true"/></div>
	              	<label for="pincode" class="col-sm-2 control-label"><spring:message code="property.pincode"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assCorrPincode"  id="pincode" class="form-control hasNumber" disabled="true"/></div>
            </div>            
            <%-- <div class="form-group">                  
	              	<label for="email" class="col-sm-2 control-label "><spring:message code="property.email"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assCorrEmail" id="email" class="form-control" disabled="true"/></div>
	              	
            </div> --%>
          </div>
         
<!----------------------------------------------- Last Payment Details After ------------------------------------------------------------------------>
			
			<%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#LastBillPaymentAfter"><spring:message
				code="property.lastpayment" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="LastBillPaymentAfter">			
			<div class="form-group">                 
	              	<label for="LastPayment" class="col-sm-2 control-label "><spring:message code="propertyView.LastPayment"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssBillPaymentDesc" type="text" id="LastPayment" class="form-control" disabled="true"/></div>
				
            </div>
            
            <div class="form-group">                 
	              	<label for="receiptno" class="col-sm-2 control-label "><spring:message code="property.receiptno"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assLpReceiptNo" type="text" id="receiptno" class="form-control" disabled="true"/></div>              	
	              	<label for="receiptamount" class="col-sm-2 control-label"><spring:message code="property.receiptamount"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assLpReceiptAmt" type="text" id="receiptamount" class="form-control hasNumber" disabled="true"/></div>          		           		
            </div>
			
			 <div class="form-group">
			 	<label for="receiptdate" class="col-sm-2 control-label"><spring:message code="property.receiptdate"/></label>
	            <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssLpReceiptDateFormat" type="text" id="proAssLpReceiptDateFormat" class="form-control" disabled="true"/></div>			 					 		
                 <label for="lastpaymentMadeUpto" class="col-sm-2 control-label"><spring:message code="property.lastpaymentupto"/></label>
	             <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssLpYearDesc" type="text" id="lastpaymentMadeUpto" class="form-control" disabled="true" /></div>
			</div>
		
			<div class="form-group">                  
	              	<label for="billamount" class="col-sm-2 control-label "><spring:message code="property.billamount"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.billAmount" type="text" id="billamount" class="form-control " disabled="true"/></div>
	              	<label for="outstandingAmount" class="col-sm-2 control-label"><spring:message code="property.OutStandingAmount"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.outstandingAmount" type="text" id="outstandingAmount" class="form-control" disabled="true" /></div>       
            </div>
        	</div> --%>
        	
        	
<!-------------------------------------------------------------------------TaxZone details---------------------------------------------------- -->
        	
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
			<%-- <div class="form-group">
					<label for="totalplot" class="col-sm-2 control-label "><spring:message code="property.totalplot"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assPlotArea" type="text" id="totalplot" class="form-control" disabled="true"/></div>
					
					<label for="buildup" class="col-sm-2 control-label "><spring:message code="property.buildup"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assBuitAreaGr" type="text" id="buildup" class="form-control" disabled="true"/></div>
			</div> --%>
		</div>
        	
        	
        	</div>
        	
<!----------------------------------------------------------- Unit Details after ---------------------------------------------------------->
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#BuildingDetailsAfter"><spring:message
				code="property.buildingdetails" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="BuildingDetailsAfter">
		
			<div class="form-group">
		
			<label for="proAssAcqDate" class="col-sm-2 control-label"><spring:message code="property.yearofacquisition"/></label>
	    	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssAcqDateFormat" type="text" id="proAssAcqDate" class="form-control" disabled="true"/></div>
				
			<label for="totalplot" class="col-sm-2 control-label "><spring:message code="property.totalplot"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assPlotArea" type="text" id="totalplot" class="form-control" disabled="true"/></div>
			<%-- <label for="propertytype" class="col-sm-2 control-label"><spring:message code="property.propertytype"/></label>
	    	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssPropType" type="text" id="propertytype" class="form-control" disabled="true"/></div> --%>
	
			</div>
			
			<%-- <div class="form-group">
					<label for="totalplot" class="col-sm-2 control-label "><spring:message code="property.totalplot"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assPlotArea" type="text" id="totalplot" class="form-control" disabled="true"/></div>
					
					<label for="buildup" class="col-sm-2 control-label "><spring:message code="property.buildup"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assBuitAreaGr" type="text" id="buildup" class="form-control" disabled="true"/></div>
			</div> --%>
		</div>
	
		
<!-- 			Unit Details -->
		
 			<h4 class="margin-top-10 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#UnitDetail"><spring:message code="property.unitdetails"/></a>
 			</h4>
		<div class="panel-collapse collapse in" id="UnitDetail">
		<div class="table-responsive">
			<table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails">
                    <tbody>
                        <tr>
 							<th width="7%"><spring:message code="unitdetails.year"/></th>
                        	<th width="4%"><spring:message code="unitdetails.unitno"/></th> 	
<%--                         	<th width="7%"><spring:message code="unitdetails.unittype"/></th> --%>
                        	<th width="10%"><spring:message code="unitdetails.floorno"/></th>
                        	<th width="10%"><spring:message code="unitdetails.dateofConstruction"/></th>

                        	<th width="12%"><spring:message code="unitdetails.usagefactor"/></th>
                        	<th width="20%"><spring:message code="unitdetails.constructiontype"/></th>                        	
             	 			<th width="12%"><spring:message code="unitdetails.taxable"/></th> 
             	 			<th width="6%"><spring:message code="unit.ViewMoreDetails"/></th>
                        </tr>
                        
                         
                        <c:forEach var="unitDetails" items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}" varStatus="status" >
                       	<tr>
                        <td class="text-center">${unitDetails.proFaYearIdDesc}</td>                                                                                                          
                        <td class="text-center">${unitDetails.assdUnitNo}</td>
<%--                         <td class="text-center">${unitDetails.proAssdUnitType}</td> --%>
                        <td class="text-center">${unitDetails.proFloorNo} </td>
                        <td class="text-center">${unitDetails.proAssdConstructionDate}</td>

                        <td class="text-center">${unitDetails.proAssdUsagetypeDesc} </td>
                        <td class="text-center">${unitDetails.proAssdConstruTypeDesc}</td>
                    	<td class="text-center">${unitDetails.assdBuildupArea}</td>
                    	<td class="text-center"><a class="clickable btn btn-success btn-xs click_advance" data-toggle="collapse" data-target="#group-of-rows-after-${status.count-1}" aria-expanded="false" aria-controls="group-of-rows-after-${status.count-1}"><i class="fa fa-caret-down" aria-hidden="true"></i></a></td>	                           
                    	
    					</tr>

            
          <tr class="secondUnitRowAfter collapse in previewTr hideDetails" id="group-of-rows-after-${status.count-1}">
            <td colspan="12">

               <legend class="text-left"><spring:message code="unitdetails.AdditionalUnitDetails" />
				</legend>
            
             <div class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12">
             	<div class="form-group" >   
           		
					<%-- <label for="proAssdRoadfactor" class="col-sm-2 control-label"><spring:message code="unitdetails.RoadType"/> </label>
					<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].proAssdRoadfactorDesc"  id="proAssdRoadfactor" class="form-control" disabled="true"/></div> --%>
					
					<label for="occupancy" class="col-sm-2 control-label"><spring:message code="unitdetails.occupancy"/> </label>
					<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].proAssdOccupancyTypeDesc"  id="proAssdOccupancyType" class="form-control" disabled="true"/></div>

					<%-- <label for="monthly-rent" class="col-sm-2 control-label"><spring:message code="property.rent"/> </label>
					<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdAnnualRent" class="form-control" disabled="true" id="monthly-rent"/></div> --%>
				
					<label for="occupierName" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
			        <div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control" id="occupierName" disabled="true"/></div> 
				</div>
				
				<%-- <div class="form-group">	    
 					
           			<label for="occupierName" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
			        <div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control" id="occupierName" disabled="true"/></div> 
           			
          		</div> --%>		
          		
          		<div class="form-group">	    
 					<label for="natureOfProperty" class="col-sm-2 control-label"><spring:message code="property.selectNatureOfProperty"/></label>
			        <div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfpropertyDesc1" type="text" class="form-control" id="natureOfProperty" disabled="true"/></div> 
           			
          		</div>		
				</div>
         </td>
         </tr>
                    </c:forEach>

               </tbody>
</table></div>
               </div>
              
               
<!------------------------------------------------------------ unitSpecific Additional Info After ------------------------------------------------------>
<c:if test="${not empty command.provAsseFactDtlDto}"> 
	         
		<h4 class="margin-top-10 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#unitSpecificInfoAfter"><spring:message code="unit.UnitSpecificAddiInfo"/></a>
 		</h4>
		<div class="panel-collapse collapse in" id="unitSpecificInfoAfter">      
 			
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

<!--     Uploaded Document     -->  
                         <c:if test="${not empty command.documentList}">	
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

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose>
														  <td>
														  <c:set var="links" value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
															<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}" actionUrl="SelfAssessmentForm.html?Download"></apptags:filedownload>
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
          </div>
          </div>
<apptags:CheckerAction></apptags:CheckerAction>
			<div class="text-center padding-top-10">
			<c:set var="assType" value="${command.getAssType()}"></c:set>
			              			<button type="button" class="btn btn-success btn-submit"
										onclick="saveAuthorizationWithEdit(this,'${assType}')" id="submit">
										<spring:message code="property.SaveProperty"/>
									</button>
			              			<button class="btn btn-blue-2" type="button"  onclick="editSelfAssForm(this)" id="submit">
													Back</button> 
			</div>  
          </form:form>
		  </div>
        </div>
