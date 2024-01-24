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
<script type="text/javascript" src="js/property/changeInAssessmentForm.js" ></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<c:if test="${empty command.provisionalAssesmentMstDto.assesssmentCategory}">
  <apptags:breadcrumb></apptags:breadcrumb>
</c:if>
<div class="content"> 
<div class="widget">
 <c:if test="${empty  command.provisionalAssesmentMstDto.assesssmentCategory}">
        <div class="widget-header"> 
          <h2><b><spring:message code="property.ChangeInAssessment"/></b></h2> 
          <%-- <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="property.Help"/></span></a></div> --%>
      	<apptags:helpDoc url="ChangeInAssessmentForm.html"></apptags:helpDoc>	
      
       </div> 
  </c:if>   
  
     
        <div class="widget-content padding">
          
          <!-- Start Form -->
			<form:form action="ChangeInAssessmentForm.html"
					class="form-horizontal form" name="frmChangeAssessmentForm"
					id="frmChangeAssessmentForm">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			<form:hidden path="assType" id="assType"/>
			<form:hidden path="selfAss" id="selfAss"/>
			
			<!-- Start Each Section -->			
			<div class="accordion-toggle ">
				<spring:message
					code="property.changeInAss.note" />
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
					code="property.Ownershipdetail" /></a>
				</h4>
					<div class="panel-collapse collapse in" id="OwnshipDetail">	
					<c:if test="${command.noOfDaysEditableFlag ne 'Y'}">
					<div class="form-group">
								<label class="col-sm-2 control-label" for="ownershiptype"><spring:message code="property.ownershiptype" /></label>
								<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.proAssOwnerTypeName"  id="ownershiptype" class="form-control" readonly="${command.noOfDaysEditableFlag eq 'N' ? true : false }"/>
				 				<form:hidden  path="ownershipPrefix"  id="ownershipId" class="form-control"/>
				 				<form:hidden  path="noOfDaysEditableFlag"  id="noOfDaysEditableFlag"/>
				 												
								</div>
						    </div>	
					</c:if>						
						    
						    <c:if test="${command.noOfDaysEditableFlag eq 'Y'}">
						    <div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="OwnershipDetail"><spring:message
										code="property.ownershiptype" /></label>
									<div class="col-sm-4">
									<c:set var="baseLookupCode" value="OWT" /> <form:select path="provisionalAssesmentMstDto.assOwnerType"
											onchange="getOwnerTypeDetails()" class="form-control mandColorClass" id="ownerTypeId" data-rule-required="true">
												<form:option value="">
													<spring:message code="property.sel.optn.ownerType" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
									</div>		
							</div>	
							</c:if>	   
						    <jsp:include page="/jsp/property/changeInAssessmentOwnerDetails.jsp"></jsp:include> 					
					</div> 
				 
<!-------------------------------------------------------------Land Details------------------------------------------------------------------ -->
            
			
				<div class="panel-collapse collapse in margin-top-15" id="propertyDetail">
				
				
						<div class="form-group">
								<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.oldpropertyno" path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="20" isReadonly="true"></apptags:input>				
								<apptags:input cssClass="alphaNumeric preventSpace" labelCode="property.landType" path="provisionalAssesmentMstDto.assLandTypeDesc"  isReadonly="true"></apptags:input>
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
									for="khasra"><spring:message code="propertydetails.csnno" text="Khasra No"/></label>
				<div class="col-sm-4">									
				<form:input path="provisionalAssesmentMstDto.tppPlotNoCs" type="text" class="form-control mandColorClass" id="khasara" disabled="true"/>
				</div>
		</div>
		
		<div class="form-group">
		<div class="text-center padding-top-10">
		<c:if test="${command.getAssType() eq 'C'}">  
		<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch land details"/></button> 
		</c:if>	
		
		</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
			
		
	<c:if test="${command.getAssType() ne 'C'}">  

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
				<apptags:input labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo" isDisabled="true"></apptags:input>
		</div>		
		
		<div class="form-group">
			<div class="text-center padding-top-10">
			<c:if test="${command.getAssType() eq 'C'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch land details"/></button> 
			</c:if>	
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
	<c:if test="${command.getAssType() ne 'C'}">  

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
				<apptags:input labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo" isDisabled="true"></apptags:input>
		</div>	
		
		<div class="form-group">
			<div class="text-center padding-top-10">
			<c:if test="${command.getAssType() eq 'C'}">  
			<button class="btn btn-blue-2" type="button"  onclick="getLandApiDetails(this)" id="getApiDetails">
										<spring:message code="property.fetchLandTypeDetails.button" text="Fetch land details"/></button> 
			</c:if>	
		
			</div>
		</div>
		<div id="showAuthApiDetails">
					
		</div>
	<c:if test="${command.getAssType() ne 'C'}">	

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
				
	           			
	            </div> 
            
            
<!------------------------------------------------------------Property Address Details------------------------------------------------------->
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#propertyAddress"><spring:message
					code="property.Propertyaddress" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="propertyAddress">
						<div class="form-group">
							<apptags:input labelCode="property.propertyaddress" path="provisionalAssesmentMstDto.assAddress" isReadonly="${command.noOfDaysEditableFlag eq 'N' ? true : false }"></apptags:input>
							<apptags:input labelCode="property.location" path="provisionalAssesmentMstDto.locationName" isReadonly="${command.noOfDaysEditableFlag eq 'N' ? true : false }"></apptags:input>			                
			           
			           <%--  <input type="hidden" value="${command.provisionalAssesmentMstDto.locId}" id="locId"/>
      					<input type="hidden" value="${command.orgId}" id="orgId"/>
						<input type="hidden" value="${command.deptId}" id="deptId"/>
            			</div> 
			            <div id="wardZone">
					
						</div>  --%>
			
             			</div>
                  
			            <div class="form-group">
			            	<apptags:input labelCode="property.pincode" path="provisionalAssesmentMstDto.assPincode" isReadonly="${command.noOfDaysEditableFlag eq 'N' ? true : false }"></apptags:input>	
<%-- 	              			<apptags:input labelCode="unitdetails.RoadType" path="provisionalAssesmentMstDto.proAssdRoadfactorDesc" isDisabled="true"></apptags:input>  	 --%>
			            </div>
	            </div>
                
<!------------------------------------------------Correspondence Address Details --------------------------------------------------------------------->
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#correspondenceAddress"><spring:message
					code="property.correspondenceaddress" /></a>
				</h4>
            
				<div class="panel-collapse collapse in" id="correspondenceAddress">	
					<div class="form-group">     
							<apptags:input labelCode="property.correspondenceaddress" path="provisionalAssesmentMstDto.assCorrAddress" isReadonly="${command.noOfDaysEditableFlag eq 'N' ? true : false }"></apptags:input>            
			              	<apptags:input labelCode="property.pincode" path="provisionalAssesmentMstDto.assCorrPincode" isReadonly="${command.noOfDaysEditableFlag eq 'N' ? true : false }"></apptags:input>
			              
		            </div>            
		           <%--  <div class="form-group"> 
		            		<apptags:input labelCode="property.email" path="provisionalAssesmentMstDto.assCorrEmail" isReadonly="true"></apptags:input>                 	              		              	
		            </div> --%>
	            </div>
         
<!------------------------------------------------------------Last Payment Details------------------------------------------------------------------------>
			
				<%-- <h4 class="margin-top-0 margin-bottom-10 panel-title">
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
						<form:radiobutton path="provisionalAssesmentMstDto.proAssBillPayment" value="N" name="billPayment" id="notApplicaple"  disabled="true"/>
						<spring:message code="property.na"/></label>
						<label for="manual" class="radio-inline">
						<form:radiobutton path="provisionalAssesmentMstDto.proAssBillPayment" value="M" name="billPayment"  id="manual" disabled="true"/>
						<spring:message code="property.manual"/></label>
						<label for="computerized" class="radio-inline">
						<form:radiobutton path="provisionalAssesmentMstDto.proAssBillPayment" value="C" name="billPayment" id="computerized"  disabled="true"/>
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
	        	
	        	
<!---------------------------------------------------------------------Tax-Zone Details---------------------------------------------------------------->
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#taxZoneDetails"><spring:message
					code="property.taxZoneDetails" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="taxZoneDetails">
<%-- 					<c:set var="orgId" value="${userSession.getCurrent().organisation.orgid}" /> --%>
					<div class="form-group">
						<apptags:lookupFieldSet cssClass="form-control required-control"
								baseLookupCode="WZB" hasId="true" pathPrefix="provisionalAssesmentMstDto.assWard"  
								hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true" showAll="false" isMandatory="true" disabled="${command.noOfDaysEditableFlag eq 'N' ? true : false }"/>
					
					</div>
					<div class="form-group">
						<label for="road-type" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.RoadType"/> </label>
							          <div>
							          <c:set var="baseLookupCode" value="RFT" />
									  <apptags:lookupField  items="${command.getLevelData(baseLookupCode)}"
							 							path="provisionalAssesmentMstDto.propLvlRoadType" cssClass="form-control" 
							 							hasChildLookup="false" hasId="true" showAll="false" 
														selectOptionLabelCode="Select Road Factor" isMandatory="true" disabled="${command.noOfDaysEditableFlag eq 'N' ? true : false }"/> 
									 </div>
									 
									<%--  <div class="col-sm-4">
			 						
			 						 	
			 						 <spring:message code="property.propNote"/><a href="WZB/${orgId}_fileDoc.jpg" target="_blank"><spring:message code="property.clickHere"/></a>	
									 </div> --%>
					</div>
			
				</div>

        	
<!-------------------------------------------------Land / Building Details ------------------------------------------------------------>
			
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#BuildingDetails"><spring:message
					code="property.buildingdetails" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="BuildingDetails">
				
					<div class="form-group">
							<c:if test="${command.noOfDaysEditableFlag ne 'Y'}">
								<apptags:input labelCode="property.yearofacquisition"
									path="provisionalAssesmentMstDto.assAcqDate"
									isReadonly="${command.noOfDaysEditableFlag eq 'N' ? true : false }"
									cssClass="dateClass"></apptags:input>


							</c:if>
							<c:if test="${command.noOfDaysEditableFlag eq 'Y'}">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="property.yearofacquisition" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="provisionalAssesmentMstDto.assAcqDate"
											readonly="false"
											class="lessthancurrdate form-control mandColorClass dateClass addColor"
											id="proAssAcqDate" onChange="getFinancialYear();"
											data-rule-required="true" placeholder="DD/MM/YYYY"
											autocomplete="off" />
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
										<form:hidden path="leastFinYear" id="minFinancialYear" />
									</div>
								</div>
							</c:if>

							<label class="col-sm-2 control-label required-control"><spring:message
										code="property.totalplot" text="" /></label>
							<div class="col-sm-4">						
						<form:input cssClass="form-control mandColorClass text-right"
												onkeypress="return hasAmount(event, this, 15, 2)"
												id="totalplot" path="provisionalAssesmentMstDto.assPlotArea"
												placeholder="999999.99"
												onchange="getAmountFormatInDynamic((this),'totalplot')"
												data-rule-required="true" disabled="${command.noOfDaysEditableFlag eq 'N' ? true : false }"></form:input>
						</div>
						
						<%-- <apptags:lookupFieldSet cssClass="form-control required-control"
							baseLookupCode="PTP" hasId="true" pathPrefix="provisionalAssesmentMstDto.assPropType"  
							hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true" showAll="false" isMandatory="true"/> --%>			
					</div>
					<%-- <div class="form-group">
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
				
						<label class="col-sm-2 control-label required-control"><spring:message
												code="property.buildup" text="" /></label>
						<div class="col-sm-4">						
						<form:input cssClass="form-control mandColorClass text-right "
												onkeypress="return hasAmount(event, this, 15, 2)"
												id="buildup" path="provisionalAssesmentMstDto.assBuitAreaGr"
												placeholder="999999.99"
												onchange="getAmountFormatInDynamic((this),'buildup')"
												data-rule-required="true"></form:input>
						</div>
					</div> --%>
			
				</div>

<jsp:include page="/jsp/property/ChangeInAssessmentUnitDetailsForm.jsp"></jsp:include>


		<div class="text-center padding-10">
				<button type="button" class="btn btn-success"
				onclick="fetchCheckList(this)" id="checkList"><spring:message code="unit.proceed"/></button>
				<button type="button" class="btn btn-danger"
				onclick="backToSearch()" id="backToSearchPage"><spring:message code="property.Back" text="Back"/></button>
		</div>

			
<div id="checkListDiv">
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
				</div>
			</div>	
			<!-- Start button -->

		 	<div class="text-center padding-10">
				<button type="button" class="btn btn-success"
				onclick="proceed(this)" id="changeProceed"><spring:message code="unit.proceed"/></button>
				
				<c:if test="${command.getSelfAss() eq 'Y'}">
				<input 	type="button" class="btn btn-warning" value="Reset" onclick="resetFormData(this)" />
				</c:if>
				
				<button type="button" class="btn btn-danger"
				onclick="backToSearch()" id="backToSearch"><spring:message code="property.Back" text="Back"/></button>
				
					
			</div> 		
			
			<!--  End button -->
</form:form>

<!--  End form -->
</div>
</div>
<c:if test="${empty command.provisionalAssesmentMstDto.assesssmentCategory}">
 </div> 
</c:if>
