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
 <script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/property/unitDetails.js"></script>
<script type="text/javascript" src="js/property/selfAssessment.js"></script>

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

@media print {
	@page {
		size: A4 landscape;
		margin: 0.5cm;
	}
	#btnPrint {
		display: none;
	}
	#removePrint {
		display: none;
	}
	
}
    
</style>

<c:if test="${command.getAssType() eq 'N' || command.getAssType() eq 'BIF'}">  
<apptags:breadcrumb></apptags:breadcrumb><br>
</c:if>

  <c:if test="${command.getAssType() eq 'A'}"> <%-- This breadcrumb is active only from dashboard view click --%>
<!-- PAGE BREADCRUM SECTION  -->
	<ol class="breadcrumb">
		<li><a href="AdminHome.html"><spring:message code="cfc.home" text="Home"/></a></li>
		<li><a href="javascript:void(0);"><spring:message code="property.property" text="Property tax"/></a></li>
		<li><a href="javascript:void(0);"><spring:message code="cfc.transaction" text="Transactions"/></a></li>
		<li class="active"><spring:message text="Property View" code="propety.Authorization" /></li>
	</ol><br>
 </c:if> 
<c:if test="${command.getAssType() eq 'A' || command.getAssType() eq 'N'}   ">  
<div class="content">
</c:if>

<div class="AuthContent">
<div class="widget">
       <div class="widget-header">

			<c:if test="${command.noOfDaysAuthEditFlag eq 'Y'}">
				<h3 align="center">This application is under period of editable
					form. This application cannot be approved</h3>
			</c:if>
			<c:if test="${command.getAssType() eq 'N'}">  
			<h2><strong><spring:message code="propertyView.SelfAssesmentView" /></strong></h2>	
		</c:if> 
		<c:if test="${command.getAssType() eq 'C'}">  			
			<h2><strong><spring:message code="property.ChangeInAssessmentView" text="View Page"/></strong></h2>			
		</c:if>
			
		<c:if test="${command.getAssType() eq 'NC'}">  
			<h2><strong><spring:message code="property.NoChangeInAssessmentView" text="No Change in Assessment View"/></strong></h2>
		</c:if>
		
		<c:if test="${command.getAssType() eq 'A'}">  
			<h2><strong><spring:message code="property.AuthorizationView" text="Authorization View"/></strong></h2>
		</c:if>
		
		<c:if test="${command.getAssType() eq 'BIF'}">  
			<h2><strong><spring:message code="property.BifurcationView" text="Bifurcation View"/></strong></h2>
		</c:if>
			<apptags:helpDoc url="SelfAssessmentForm.html"></apptags:helpDoc>	
		</div>	
		
        <div class="widget-content padding">
		<form:form action="SelfAssessmentForm.html" class="form-horizontal form" name="frmSelfAssessmentForm" id="frmSelfAssessmentForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>

			<!-- Start Each Section -->
			<c:if test="${command.getIntgrtionWithBP() eq 'Y'}">
			<div class="form-group">
				<apptags:input labelCode="property.buildingpermission" path="" isDisabled="true"></apptags:input> 
				<apptags:input labelCode="property.LandPermissionNo." path="" isDisabled="true"></apptags:input>
				
			</div>
			</c:if>
			<div class="form-group">
			<apptags:input labelCode="propertydetails.PropertyNo." path="provisionalAssesmentMstDto.assNo" isDisabled="true"></apptags:input>
			</div>
<!---------------------------------------- Owner Details (First owner will be the primary owner)-------------------------------->
           
            <div class="accordion-toggle">
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
				
<!-------------------------------------------------------------Land Details---------------------------------------------------------------------------------->
            
				
	
           		  <div class="form-group margin-top-15">
                  
	              	<label for="oldProNo" class="col-sm-2 control-label "><spring:message code="propertydetails.oldpropertyno"/></label>
	             	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assOldpropno" type="text" id="oldProNo" class="form-control" disabled="true"/></div>
	             	 <%-- <label for="csnNo" class="col-sm-2 control-label"><spring:message code="propertydetails.csnno"/></label>
	              	 <div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" id="csnNo" class="form-control" disabled="true"/></div> --%>
            	  
            	  	<apptags:input labelCode="property.landType" path="provisionalAssesmentMstDto.assLandTypeDesc" isDisabled="true"></apptags:input>            	  	
            	  	<form:hidden  path="landTypePrefix" class="form-control landValue"/> 
                    <form:hidden class="form-control" id="khasraNo" path="knownKhaNo"/>	<!--For entering value in khasra/plot field-->
		     	  </div>
          	   
         
     <c:choose>
	 <c:when test="${command.getLandTypePrefix() eq 'KPK'}">
		<div class="form-group">		
			<apptags:input labelCode="property.district" path="provisionalAssesmentMstDto.assDistrictDesc" isDisabled="true"></apptags:input>			
			<apptags:input labelCode="property.tahasil" path="provisionalAssesmentMstDto.assTahasilDesc" isDisabled="true"></apptags:input>
		</div>
		<div class="form-group">						
 			<apptags:input labelCode="propertydetails.village" path="provisionalAssesmentMstDto.tppVillageMaujaDesc" isDisabled="true"></apptags:input> 								
			<label class="col-sm-2 control-label"
									for="khasra"><spring:message code="propertydetails.csnno" text="Khasra No"/></label>
				<div class="col-sm-4">									
				<form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" class="form-control mandColorClass" id="displayEnteredKhaNo" />
				</div>
		</div>
		
		<div class="form-group">
		<div class="text-center padding-top-10">
		<c:if test="${command.getAssType() eq 'A' || command.getAssType() eq 'NC'}">  
		<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch land details"/></button> 
		</c:if>	
		
		</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
		
		
	<c:if test="${command.getAssType() ne 'A' && command.getAssType() ne 'NC'}">  

	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#khasra"><spring:message code="property.landDetails.KhasraDetails" text="Khasra Details"/></a>
	</h4>
	<div class="panel-collapse collapse in" id="khasra">
	
	<div class="form-group">		
	<apptags:input labelCode="property.landDetails.ownerFirstName" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ocdname"></apptags:input>	
<%-- 	<apptags:input labelCode="property.landDetails.ownerLastName" path="arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ocdname1"></apptags:input>				 --%>
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
				<apptags:input labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo"></apptags:input>
		</div>		
		
		<div class="form-group">
			<div class="text-center padding-top-10">
			<c:if test="${command.getAssType() eq 'A' || command.getAssType() eq 'NC'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch land details"/></button> 
			</c:if>	
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
	<c:if test="${command.getAssType() ne 'A' && command.getAssType() ne 'NC'}">  

			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#details"><spring:message code="property.landDetails.NajoolDetails" text="Najool Details"/></a>
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
				<apptags:input labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo"></apptags:input>
		</div>	
		
		<div class="form-group">
			<div class="text-center padding-top-10">
			<c:if test="${command.getAssType() eq 'A' || command.getAssType() eq 'NC'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch land details"/></button> 
			</c:if>	
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
	<c:if test="${command.getAssType() ne 'A' && command.getAssType() ne 'NC'}">	

			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#details"><spring:message code="property.landDetails.DiversionDetails" text="Diversion Details"/></a>
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
            	         

 			<div class="form-group">
 			<label class="col-sm-2 control-label"><spring:message code="property.building.permission" text="Building Permission"/></label>
			<div class="col-sm-4">
			<form:select class="form-control" path="provisionalAssesmentMstDto.buildingPermission" id="buildingPermission" disabled="true">
				<form:option value="Y"><spring:message code="unit.Yes" text="Yes"/></form:option>
				<form:option value="N"><spring:message code="Unit.No" text="No"/></form:option>
			</form:select>
			</div>
				<apptags:input cssClass="alphaNumeric preventSpace " labelCode="property.buildingpermission" 
					path="provisionalAssesmentMstDto.buildingPermissionNumber" maxlegnth="100" isDisabled="true"></apptags:input>	
			</div>           	
<!-- --------------------------------------------------- Property Address Details--------------------------------------------------------------->
			
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
      		<%-- <input type="hidden" value="${command.provisionalAssesmentMstDto.locId}" id="locId"/>
      		<input type="hidden" value="${command.orgId}" id="orgId"/>
			<input type="hidden" value="${command.deptId}" id="deptId"/> --%>
            </div> 
            <!-- <div id="wardZone">
		
			</div> -->

						<div class="form-group">
							<label for="pincode" class="col-sm-2 control-label "><spring:message
									code="property.pincode" /></label>
							<div class="col-sm-4">
								<form:input path="provisionalAssesmentMstDto.assPincode"
									type="text" id="pincode" class="form-control" disabled="true" />
							</div>
							<label class="col-sm-2 control-label " for="tppKhataNo"><spring:message
									code="property.khata.no" /></label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control"
									path="provisionalAssesmentMstDto.tppKhataNo" id="tppKhataNo"></form:input>
							</div>
							<%-- <apptags:input labelCode="unitdetails.RoadType" path="provisionalAssesmentMstDto.proAssdRoadfactorDesc" isDisabled="true"></apptags:input>  	 --%>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label " for="tppPlotNo"><spring:message
									code="property.house.no" /></label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control"
									path="provisionalAssesmentMstDto.tppPlotNo" id="tppPlotNo"></form:input>
							</div>
							<label class="col-sm-2 control-label " for="tppSurveyNumber"><spring:message
									code="property.khasra.no" /></label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control"
									path="provisionalAssesmentMstDto.tppSurveyNumber"
									id="tppSurveyNumber"></form:input>
							</div>
						</div>
					</div>
                
<!--------------------------------------------------Correspondence Address Details---------------------------------------------------------->
			
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
           <%--  <div class="form-group">                  
	              	<label for="email" class="col-sm-2 control-label "><spring:message code="property.email"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assCorrEmail" id="email" class="form-control" disabled="true"/></div>
	              	
            </div> --%>
          </div>
         
<!-------------------------------------------------Last Payment Details------------------------------------------------------------------------->
			
		<%-- 	<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#LastBillPayment"><spring:message
				code="property.lastpayment" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="LastBillPayment">			
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
			
        	
        	
<!----------------------------------------------------Land / Building Details ---------------------------------------------------------------------->
			
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
			
			<%-- <div class="form-group">
			<c:if test="${command.provisionalAssesmentMstDto.assPropType2 ne null}">
			<label for="proAssPropType2" class="col-sm-2 control-label"><spring:message code="property.propertyZone"/></label>
	    	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssPropType2" type="text" id="proAssPropType2" class="form-control" disabled="true"/></div>
			</c:if>
			
			<c:if test="${command.provisionalAssesmentMstDto.assPropType3 ne null}">
			<label for="proAssPropType3" class="col-sm-2 control-label"><spring:message code="property.propertyWard"/></label>
	    	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssPropType3" type="text" id="proAssPropType3" class="form-control" disabled="true"/></div>
			</c:if>
			</div>
			
			<div class="form-group">
			<c:if test="${command.provisionalAssesmentMstDto.assPropType4 ne null}">
			<label for="proAssPropType4" class="col-sm-2 control-label"><spring:message code="property.propertyWard"/></label>
	    	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssPropType4" type="text" id="proAssPropType4" class="form-control" disabled="true"/></div>
			</c:if>
			
			<c:if test="${command.provisionalAssesmentMstDto.assPropType5 ne null}">
			<label for="proAssPropType5" class="col-sm-2 control-label"><spring:message code="property.propertyWard"/></label>
	    	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssPropType5" type="text" id="proAssPropType5" class="form-control" disabled="true"/></div>
			</c:if>
			</div> --%>
			
			
			<%-- <div class="form-group">
					<label for="totalplot" class="col-sm-2 control-label "><spring:message code="property.totalplot"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assPlotArea" type="text" id="totalplot" class="form-control" disabled="true"/></div>
					
					<label for="buildup" class="col-sm-2 control-label "><spring:message code="property.buildup"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.assBuitAreaGr" type="text" id="buildup" class="form-control" disabled="true"/></div>
			</div> --%>
		</div>
	
		
<!-- ---------------------------------------------Unit Details --------------------------------------------------------------------------->
		
 			<h4 class="margin-top-10 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#UnitDetail"><spring:message code="property.unitdetails"/></a>
 			</h4>
			<div class="panel-collapse collapse in" id="UnitDetail">
			<c:set var="d" value="0" scope="page" />
			<table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails">
                   
                        <tr>
 							<th width="7%"><spring:message code="unitdetails.year"/></th>
                        	<th width="4%"><spring:message code="unitdetails.unitno"/></th> 	
<%--                         	<th width="7%"><spring:message code="unitdetails.unittype"/></th> --%>
                        	<th width="8%"><spring:message code="unitdetails.floorno"/></th>
                        	<th width="10%"><spring:message code="unitdetails.dateofConstruction"/></th>

									<c:if test="${command.assType ne 'NC' && command.assType ne 'C'}">
									<th width="10%"><spring:message code="" text="First Assessment Date"/></th>
									</c:if>
									
									<c:if test="${command.assType eq 'NC' || command.assType eq 'C'}">
									<th width="10%"><spring:message code="" text="Last Assessment Date"/></th>
									</c:if>
                        	
                     <%--    	<c:if test="${command.getAssType() eq 'C'}">                       	
                        	<th width="10%"><spring:message code="property.changeInAss.EffectiveDate"/></th>
                        	</c:if> --%>
                        	<th width="12%"><spring:message code="unitdetails.constructiontype"/></th>
                        	<th width="10%"><spring:message code="unitdetails.usagefactor"/></th>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype2 ne null }">
                        	<th width="12%"><spring:message code="property.Usagesubtype" text="Usage sub type"/></th>   
                        	</c:if>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype3 ne null}">
                        	<th width="12%"><spring:message code="property.Usagesubtype" text="Usage sub type"/></th>   
                        	</c:if>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype4 ne null}">
                        	<th width="12%"><spring:message code="property.Usagesubtype" text="Usage sub type"/></th>   
                        	</c:if>
                        	
                        	<c:if test="${command.provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype5 ne null}">
                        	<th width="12%"><spring:message code="property.Usagesubtype" text="Usage sub type"/></th>   
                        	</c:if>
        	 				<th width="12%"><spring:message code="unitdetails.taxable"/></th> 
        	 				<th width="5%"><spring:message code="unitdetails.rate"/></th>
             	 			<c:if test="${command.getAssMethod() eq 'ARV'}">
             	 			<th width="10%"><spring:message code="unitdetails.ARV"/><br><spring:message code="unitdetails.ARVFormula"/></th>
             	 			<th width="10%"><spring:message code="unitdetails.maintCharge"/></th>
							<th width="10%"><spring:message code="unitdetails.RV"/><br><spring:message code="unitdetails.RVFormula"/></th>
							</c:if>
							<c:if test="${command.getAssMethod() eq 'CV'}">
							<th width="10%"><spring:message code="unitdetails.CV"/></th>
							</c:if>
             	 			<th width="6%"><spring:message code="unit.ViewMoreDetails"/></th>
                        </tr>
                        
                          <tbody>
                  <c:forEach var="unitDetails" items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}" varStatus="status" >
                       	<tr>
	                        <td class="text-center">${unitDetails.proFaYearIdDesc}</td>                                                                                                          
	                        <td class="text-center">${unitDetails.assdUnitNo}</td>
<%-- 	                        <td class="text-center">${unitDetails.proAssdUnitType}</td> --%>
	                        <td class="text-center">${unitDetails.proFloorNo} </td>
	                        <td class="text-center">${unitDetails.proAssdConstructionDate}</td>
	                        
	                        <c:if test="${command.assType ne 'NC' && command.assType ne 'C'}">
	                         <td class="text-center">${unitDetails.firstAssesmentStringDate}</td>
	                        </c:if>
	                        
	                        <c:if test="${command.assType eq 'NC' || command.assType eq 'C'}">
	                         <td class="text-center">${unitDetails.lastAssesmentStringDate}</td>
	                        </c:if>
	                       
	                        <%-- <td class="text-center">${unitDetails.firstAssessmentDate}</td> --%>
	                        
	                        
	                   <%--      <c:if test="${command.getAssType() eq 'C'}">  
	                        <td class="text-center">${unitDetails.proAssEffectiveDateDesc}</td>
	                        </c:if> --%>
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
	                        	                        
	                    	<td class="text-center"><label id="assdArea${d}">${unitDetails.assdBuildupArea}</label></td>
	                    	
	                    	
	                    	<td id="" class="text-center">${unitDetails.assdStdRate} </td>
	                    	<c:if test="${command.getAssMethod() eq 'ARV'}">
	                    	<td class="text-center"><label id="alv${d}">${unitDetails.assdAlv}</label> </td>
	                    	<td class="text-center"><label id="maintananceCharge${d}">${unitDetails.maintainceCharge}</label> </td>
	                        <td class="text-center"><label id="rv${d}">${unitDetails.assdRv}</label></td>
	                        </c:if>
	                        <c:if test="${command.getAssMethod() eq 'CV'}">
	                    	<td class="text-center">${unitDetails.assdCv}</td>
	                    	</c:if>
	                    	<td class="text-center"><a class="clickable btn btn-success btn-xs click_advance" data-toggle="collapse" data-target="#group-of-rows-${status.count-1}" aria-expanded="false" aria-controls="group-of-rows-${status.count-1}"><i class="fa fa-caret-down" aria-hidden="true"></i></a></td>	                           
	                    	
    					</tr>

            
				         <tr class="secondUnitRow collapse in previewTr hideDetails" id="group-of-rows-${status.count-1}">
				            <td colspan="14">
				
				               <legend class="text-left"><spring:message code="unitdetails.AdditionalUnitDetails" />
								</legend>
				            
				             	<div class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12">
					             	<div class="form-group" >   					           			
					           			<%-- <label for="proAssdRoadfactor" class="col-sm-2 control-label"><spring:message code="unitdetails.RoadType"/> </label>
										<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].proAssdRoadfactorDesc"  id="proAssdRoadfactor" class="form-control" disabled="true"/></div> --%>					           		
										<label for="proAssdOccupancyType" class="col-sm-2 control-label"><spring:message code="unitdetails.occupancy"/></label>
										<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].proAssdOccupancyTypeDesc"  id="proAssdOccupancyType" class="form-control" disabled="true"/></div>
										
										<%-- <c:if test="${unitDetails.getProAssdOccupancyTypeDesc() eq 'Tenanted'}">
										<label for="assdAnnualRent" class="col-sm-2 control-label"><spring:message code="property.rent"/> </label>
										<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdAnnualRent" class="form-control" disabled="true"/></div>									
										</c:if> --%>
										<label for="occupierName" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
			          		    		<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control" id="occupierName" disabled="true"/></div>
									</div>
									
									<div class="form-group" >   					           			
										<label for="natureOfProperty" class="col-sm-2 control-label"><spring:message code="property.selectNatureOfProperty"/> </label>
										<div class="col-sm-2">
										<form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfpropertyDesc1"  id="natureOfProperty" class="form-control" disabled="true"/>
										</div>
									</div>
										   
									
									
									<%-- <div class="form-group">	  					           			
					           			<label for="occupierName" class="col-sm-2 control-label"><spring:message code="unitdetails.OccupierName"/></label>
			          		    		<div class="col-sm-2"><form:input path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName" type="text" class="form-control" id="occupierName" disabled="true"/></div>         			
					          		</div> --%>				
								</div>
				         	</td>
				         </tr>
				         <c:set var="d" value="${d + 1}" scope="page" />
                   </c:forEach>

               </tbody>
			</table>
      </div>
              
               
<!-- unitSpecific Additional Info -->
<c:if test="${not empty command.provAsseFactDtlDto[0].assfFactorValueId}"> 
	         
		<h4 class="margin-top-10 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#unitSpecificInfo"><spring:message code="unit.UnitSpecificAddiInfo"/></a>
 		</h4>
		<div class="panel-collapse collapse in" id="unitSpecificInfo">      
 			
 			<table id="unitSpecificInfoTable" class="table table-striped table-bordered ">
            
            <tr>
            	 <th width="20%"><spring:message code="unit.Factor"/></th>
                 <th width="10%"><spring:message code="unit.ApplicableUnitNo"/></th>
                 <th width="30%"><spring:message code="unit.FactorValue"/></th>
            </tr>
            <tbody>
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
  
<!--   Tax Calculation -->
 
   <h4 class="margin-top-10 margin-bottom-10 panel-title ">
   <a data-toggle="collapse" href="#TaxCalculation" class="contentRemove"><spring:message code="viewPropDetails.TaxCalculation" text="Tax Calculation"/></a>
    <a style="color: blue; text-decoration:underline;" onclick="downloadSheet()"><spring:message code="viewPropDetails.DownloadTaxDetails" text="(Download year wise tax details)"/></a>
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
                  <td class="text-right">${tax.getArrearsTaxAmt()}</td>
                  <td class="text-right">${tax.getCurrentYearTaxAmt()}</td>
                  <td class="text-right">${tax.getTotalTaxAmt()}</td>
                </tr>
                </c:forEach>
                </c:otherwise>
                </c:choose>
                  <tr>
                    <th colspan="2" class="text-right"><spring:message code="propertyTax.Total"/></th>
                    <th class="text-right">${totArrTax}</th>
					<th class="text-right">${totCurrTax}</th>
					<th class="text-right">${totTotTax}</th>
                  </tr>
                </tbody>
              </table>
            </div>
            </c:forEach>

            <div class="table-responsive margin-top-10">
              <table class="table table-striped table-bordered">
              <c:if test="${command.getManualRecptArrearPaidAmnt() gt 0}"> 
              <th width="500"><spring:message code="" text="Arrear Amount"/></th>
                  <th width="500" class="text-right">${command.manualRecptArrearPaidAmnt}</th>
              </c:if>
                <tr>
                  <th width="500"><spring:message code="propertyTax.TotalTaxPayable"/></th>
                  
                  	<c:choose>
	              	<c:when test="${command.getManualRecptArrearPaidAmnt() gt 0}">
	              	        <th width="500" class="text-right"><label id="totalTaxPayArrear">${command.totalTaxPayableWithArrears}</label> </th>
	              	</c:when>
	              	<c:otherwise>	              	
	                      <th width="500" class="text-right"><label id="totalTaxPay">${command.provisionalAssesmentMstDto.billTotalAmt}</label></th>
					</c:otherwise>
					</c:choose>
                </tr>
              </table>
            </div>
          </div>
   <div id="removePrint">    
            
<%-- <c:if test="${command.getAssType() ne 'BIF' && command.getSelfAss() ne 'BIF'}">  --%> 
 
        <h4 class="margin-top-10 margin-bottom-10 panel-title">
        <a data-toggle="collapse" href="#Payment">
        Payment</a></h4>
            <div class="panel-collapse collapse in" id="Payment">      
            <div class="form-group hidden-print">

      <!--       N=new Assessment
            C=Change in Assessment
            NC=No Change in Assessment
            A=Authorize -->
            <c:if test="${command.getAssType() eq 'N' || command.getAssType() eq 'NC' || command.getAssType() eq 'C' || command.getSelfAss() eq 'BIF'}">  

				<c:if test="${command.noOfDaysEditableFlag eq 'Y'}">
	            	<label for="partialAmt" class="col-sm-2 control-label"><spring:message code="" text="Last Amount Paid"/></label>
	              	<div class="col-sm-4"><form:input path="lastBillAmountPaid" type="text" id="lastBillAmountPaid" value="${command.lastBillAmountPaid}" class="form-control" readonly="true"/></div>       
	            </c:if>
             	<label for="billTotamount" class="col-sm-2 control-label "><spring:message code="propertyView.payAmt"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.billTotalAmt" type="text" id="billTotamount" class="form-control " disabled="true"/></div>
	            	<c:if test="${command.provisionalAssesmentMstDto.getBillTotalAmt() gt 0}">  
	            	<c:if test="${empty command.getAssesmentManualDate()}">  
	            	<c:choose>
	              	<c:when test="${command.provisionalAssesmentMstDto.partialAdvancePayStatus eq 'PAI'}">
	              	<label for="partialAmt" class="col-sm-2 control-label"><spring:message code="propertyView.amtToPay"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.billPartialAmt" type="text" id="partialAmt" value="${command.provisionalAssesmentMstDto.billTotalAmt}" class="form-control" readonly="true"/></div>       
	              	</c:when>
	              	<c:otherwise>	              	
	              	<label for="partialAmt" class="col-sm-2 control-label"><spring:message code="propertyView.amtToPay"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.billPartialAmt" type="text" id="partialAmt"  class="form-control hasNumber"/></div>       
					</c:otherwise>
					</c:choose>
					</c:if>
					</c:if>
            </c:if>
            <c:if test="${command.getAssType() eq 'A'}">  
             	<label for="billTotamount" class="col-sm-2 control-label "><spring:message code="propertyView.paidAmt"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.billPaidAmt" type="text" id="billTotamount" class="form-control" disabled="true"/></div>
            </c:if>
            </div>
            </div>
          <%-- </c:if> --%>
							<c:if test="${not empty command.getAssesmentManualDate()}"> 
								<h4 class="margin-top-10 margin-bottom-10 panel-title">
									<a data-toggle="collapse" href="#manualReceipt"> Manual
										Receipt Details</a>
								</h4>
								<div class="panel-collapse collapse in" id="manualReceipt">
									<div class="form-group hidden-print">
										<div class="form-group">
											<apptags:input labelCode="Manual Receipt No" path="assesmentManualNo"></apptags:input>
											<apptags:input labelCode="Manual Receipt Date"
												path="assesmentManualDate" isReadonly="true"
												cssClass="trimDateTime"></apptags:input>
										</div>
										<div class="form-group">
											<label for="partialAmt" class="col-sm-2 control-label"><spring:message
													code="" text="Manual Receipt Amount"/></label>
											<div class="col-sm-4">
												<form:input path="provisionalAssesmentMstDto.billPartialAmt"
													type="text" id="partialAmt" class="form-control hasNumber" />
											</div>
										</div>
									</div>
								</div>
							</c:if>
		
						<!--    Document Upload          -->

<c:if test="${command.getAssType() eq 'NC' || command.getAssType() eq 'C'}">
<c:if test="${not empty command.checkList}"> 

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
    
          
        </c:if>    
        
        </c:if> 
                         <c:if test="${not empty command.documentList}">	
                         <h4 class="margin-top-0 margin-bottom-10 panel-title"> <a data-toggle="collapse" href="#DocumentUpload"><spring:message code="propertyTax.UploadDocumnet"/></a></h4>
                         
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
															<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}" actionUrl="SelfAssessmentForm.html?previewDownload"></apptags:filedownload>
					                                    </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
						
					 </c:if>     
        
        
        
										
        
<c:if test="${command.provisionalAssesmentMstDto.getBillTotalAmt() gt 0}">  
	<c:if test="${command.getAssType() eq 'N' || command.getAssType() eq 'NC' ||  command.getAssType() eq 'C' || command.getSelfAss() eq 'BIF' }">  
       
		<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp"/>		
	</c:if>
</c:if>


<div>
<jsp:include page="/jsp/cfc/checklist/ChecklistVerificationApproval.jsp"/>
</div>



        <c:if test="${command.noOfDaysAuthEditFlag eq 'N'}"> 
		<c:if test="${command.getAssType() eq 'A' && command.approvalFlag ne 'APR' || command.getSelfAss() eq 'BIF'}">  
       	<apptags:CheckerAction></apptags:CheckerAction>
		</c:if>
		</c:if>
        </div>
       </div>      
        <!-- Start button -->


      <div class="text-center padding-top-10">
<c:set var="assType" value="${command.getAssType()}"></c:set>
					<c:if test="${command.noOfDaysEditableFlag ne 'Y' && command.noOfDaysAuthEditFlag ne 'Y'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="savePropertyFrom(this,'${assType}')" id="submit">
							<spring:message code="property.SaveProperty" />
						</button>
					</c:if>

					<c:if test="${command.noOfDaysEditableFlag eq 'Y'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveNoOfDaysEditProperty(this)" id="submit">
							<spring:message code="property.SaveProperty" />
						</button>
					</c:if>
					<c:if test="${command.noOfDaysAuthEditFlag eq 'Y'}">
					<div class="text-center padding-top-10">
						<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="" text="Back"></spring:message>
						</button>
						</div>
					</c:if>
						<c:if test="${command.getAssType() eq 'NC'}">  
						
						<c:if test="${not empty command.provisionalAssesmentMstDto.assesssmentCategory}">						
						
						<button type="button" class="btn btn-dange"
							onclick="window.location.href='NoChangeInAssessment.html'" id="back">
							<spring:message code="property.Back" text="Back"/>
						</button>
						</c:if>
						
						
						
						<c:if test="${empty command.provisionalAssesmentMstDto.assesssmentCategory}">						
						<button type="button" class="btn btn-danger"
				         id="backToSearch"><spring:message code="property.Back" text="Back"/></button>
				        </c:if>
						
						</c:if>
						
						<!--N and A in case of new Assessment  and Authorization  -->
						<c:if test="${(command.assType eq 'N') && (command.selfAss ne 'BIF')}">  						
              			<button class="btn btn-blue-2" type="button"  onclick="editSelfAssForm(this)" id="submit">
										<spring:message code="property.Edit"/></button> 
						</c:if>
						<c:if test="${command.noOfDaysEditableFlag ne 'Y' && command.noOfDaysAuthEditFlag ne 'Y'}">
						<c:if test="${(command.assType eq 'A' && command.lastChecker eq true) && (command.selfAss ne 'BIF') && (command.approvalFlag ne 'APR') }">  						
              			<button class="btn btn-blue-2" type="button"  onclick="editSelfAssForm(this)" id="submit">
										<spring:message code="property.specNotGenBut"/></button> 
						</c:if>
						</c:if>
						
						<c:if test="${(command.assType eq 'A' && command.lastChecker eq true) && (command.selfAss ne 'BIF') && (command.approvalFlag eq 'APR') }">  						
              			<button class="btn btn-blue-2" type="button"  onclick="editSelfAssForm(this)" id="submit">
										<spring:message code="property.Edit"/></button> 
						</c:if>
						
						<c:if test="${(command.assType eq 'BIF' || command.selfAss eq 'BIF') && command.lastChecker eq true}">  						
              			<button class="btn btn-blue-2" type="button"  onclick="editSelfAssBifurcationForm(this)" id="submit">
										<spring:message code="property.Edit"/></button> 
						</c:if>
						
						<%-- <c:if test="${command.getAssType() eq 'C'}">  
						<button class="btn btn-blue-2" type="button"  onclick="editChangeAssForm(this)" id="submit">
										<spring:message code="property.Edit"/></button>
						</c:if> --%>			
							<input type='button' class="btn btn-danger" id='btnPrint' value='Print'	onclick='window.print()' />
						
									
		<!--  End button -->
		</div>
	
 </form:form>  
		</div>
		</div>
 </div>
 	</div>
<c:if test="${command.getAssType() eq 'A'}">  
</div>
</c:if>
