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
<script type="text/javascript" src="js/property/mutationIntimationSearch.js"></script>



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
#jointOwnerTable select.form-control{border: 1px solid #ccc !important;border-bottom: 1px solid #ccc !important;}
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


<div class="content">

<div class="AuthContent">
<div class="widget">
       <div class="widget-header">

		
			<h2><strong>Mutation Intimation</strong></h2>
		
		</div>	
		
        <div class="widget-content padding">
		<form:form action="MutationIntimation.html" class="form-horizontal form" Rname="mutationIntimationView" id="mutationIntimationView">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>


	
       
           
            <div class="accordion-toggle ">

            	         
            	
<!-- ---------------------------------------------------------------------Land Details---------------------------------------------------------------------------->
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
			<a data-toggle="collapse" href="#landDetails"><spring:message
				code="property.LandDetail" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="landDetails">
			<div class="form-group">
			<apptags:input labelCode="propertydetails.PropertyNo." path="mutationIntimationViewDto.propertyno" isDisabled="true"></apptags:input>
			<apptags:input labelCode="property.propertytype" path="mutationIntimationViewDto.propertyType" isDisabled="true"></apptags:input>
			
			</div>
			
			<div class="form-group">
			<apptags:input labelCode="mut.Inti.mutType" path="mutationIntimationViewDto.mutationType" isDisabled="true"></apptags:input>
			<apptags:input labelCode="property.landType" path="mutationIntimationViewDto.landType" isDisabled="true"></apptags:input>
			
			</div>
			
			<div class="form-group">
			<apptags:input labelCode="property.district" path="mutationIntimationViewDto.district" isDisabled="true"></apptags:input>
			<apptags:input labelCode="property.tahasil" path="mutationIntimationViewDto.tehsil" isDisabled="true"></apptags:input>
			
			</div>
				<div class="form-group">
			<apptags:input labelCode="propertydetails.village" path="mutationIntimationViewDto.village" isDisabled="true"></apptags:input>
			<apptags:input labelCode="mut.Inti.khasraPlot" path="mutationIntimationViewDto.khasraPloatNo" isDisabled="true"></apptags:input>
			
			</div>
			<div class="form-group">
			<apptags:input labelCode="property.landDetails.Area" path="mutationIntimationViewDto.totalArea" isDisabled="true"></apptags:input>
			
			</div>
            </div>
                
<!------------------------------------------------------------------------------Registration  Details---------------------------------------------------------->
			
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#regNoDetail"><spring:message
				code="mut.Inti.regNoDetail" /></a>
			</h4>
            
			<div class="panel-collapse collapse in" id="regNoDetail">	
			<div class="form-group">
			<apptags:input labelCode="mut.Inti.regNo" path="mutationIntimationViewDto.registrationNo" isDisabled="true"></apptags:input>
			<apptags:input labelCode="mut.Inti.regDate" path="mutationIntimationViewDto.registrationDate" cssClass="dateClass" isDisabled="true"></apptags:input>
			
			</div>
			<div class="form-group">
			<apptags:input labelCode="mut.Inti.docExDate" path="mutationIntimationViewDto.docExecutaionDate"  cssClass="dateClass" isDisabled="true"></apptags:input>
			
			</div>   
			<c:if test="${command.mutationIntimationViewDto.registrationDocument ne null }">      
			<div class="form-group">
			<label class="col-sm-2 control-label required-control" for="proAssPincode"><spring:message
				code="mut.Inti.regDoc" /></label>
			<div class="col-sm-4">
			<a href="javascript:void(0);" onclick="downloadRegDoc()" class="text-blue-2"><i class="fa fa-download"></i> <spring:message code="mut.Inti.downDoc"/></a>
			</div>			
			</div>  
			</c:if>
          </div>



<!----------------------------------------------------------Mutation details------------------------------------------------------ --> 

			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#mutationDetails"><spring:message
				code="mut.Inti.mutationDetail" /></a>
			</h4>
            
			<div class="panel-collapse collapse in" id="mutationDetails">	
			<div class="form-group">
			<apptags:input labelCode="mut.Inti.mutNo" path="mutationIntimationViewDto.mutationOrderNo" isDisabled="true"></apptags:input>
			<apptags:input labelCode="mut.Inti.mutDate" path="mutationIntimationViewDto.mutationDate" cssClass="dateClass" isDisabled="true"></apptags:input>
			</div>  
			<c:if test="${command.mutationIntimationViewDto.mutationDocument ne null }">
						<div class="form-group">
			<label class="col-sm-2 control-label required-control" for="proAssPincode"><spring:message
				code="mut.Inti.mutDoc" /></label>
			<div class="col-sm-4">
			<a href="javascript:void(0);" onclick="downloadMutDoc()" class="text-blue-2"><i class="fa fa-download"></i> <spring:message code="mut.Inti.downDoc"/></a>
			</div>		
			</div>  
			</c:if>
          </div>
          
        	
<!----------------------------------------------------------Executant Detail------------------------------------------------------ --> 
			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#excDetail"><spring:message
				code="mut.Inti.excDetail" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="excDetail">
				<table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails margin-bottom-10">
                    <tbody>
                        <tr>
 							<th width="7%"><spring:message code="mut.Inti.personName"/></th>
                        	<th width="4%"><spring:message code="property.landDetails.fathersFirstName"/></th> 	
                        	<th width="10%"><spring:message code="ownersdetail.gender"/></th>
                        	<th width="10%"><spring:message code="property.Address"/></th>
                        	<th width="20%"><spring:message code="property.landDetails.OwnerMobileNo"/></th>
                        </tr>
                        
                         
                  <c:forEach var="excutant" items="${command.getMutationIntimationViewDto().getExecutantList()}" varStatus="status" >
                       	<tr>
	                        <td class="text-center">${excutant.personName}</td>                                                                                                          
	                        <td class="text-center">${excutant.fatherName}</td>
	                        <td class="text-center">${excutant.gender} </td>
	                        <td class="text-center">${excutant.address}</td>
	                        <td class="text-center">${excutant.mobileno}</td>
	                    
    					</tr>
                   </c:forEach>

               </tbody>
			</table>
			</div>
<!---------------------------------------------------------- Claimant Detail------------------------------------------------------ --> 
<%-- 			<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#clamDetail"><spring:message
				code="mut.Inti.clamDetail" /></a>
			</h4>
			<div class="panel-collapse collapse in" id="clamDetail">
							<table id="unitDetailTable" class="table table-striped table-bordered appendableClass unitDetails">
                    <tbody>
                        <tr>
 							<th width="7%"><spring:message code="mut.Inti.personName"/></th>
                        	<th width="4%"><spring:message code="property.landDetails.fathersFirstName"/></th> 	
                        	<th width="10%"><spring:message code="ownersdetail.gender"/></th>
                        	<th width="10%"><spring:message code="property.Address"/></th>
                        	<th width="20%"><spring:message code="property.landDetails.OwnerMobileNo"/></th>
                        </tr>
                        
                         
                  <c:forEach var="claimant" items="${command.getMutationIntimationViewDto().getClaimantList()}" varStatus="status" >
                       	<tr>
	                          <td class="text-center">${claimant.personName}</td>                                                                                                          
	                        <td class="text-center">${claimant.fatherName}</td>
	                        <td class="text-center">${claimant.gender} </td>
	                        <td class="text-center">${claimant.address}</td>
	                        <td class="text-center">${claimant.mobileno}</td>
	                    
    					</tr>
                   </c:forEach>

               </tbody>
			</table>
			</div> --%>


<!-------------------------------------------------------------------New Owner Details----------------------------------------------------------------------------->
						 <h4 class="margin-top-0 margin-bottom-10 panel-title">
						 <a data-toggle="collapse" href="#Owner(s)_Detail"><spring:message
								code="mut.Inti.clamDetail" /></a> 
						 </h4>		
					
						
						 <div class="panel-collapse collapse in" id="NewOwnshipDetail">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="OwnershipDetail"><spring:message
										code="property.ownershiptype" /></label>
									<div class="col-sm-4">
									<c:set var="baseLookupCode" value="OWT" /> 
									<form:hidden  path="ownershipPrefixNew"  id="ownershipNewId" class="form-control"/>
									<form:select path="propTransferDto.ownerType"
											onchange="getOwnerTypeInfo()" class="form-control mandColorClass" id="ownerInfo" 
											  disabled="${command.getOwnershipPrefix() eq 'JO' ?true : false}" data-rule-required="true" >
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
						</div> 
<%-- 						<form:hidden path="selfAss" id="selfAss"/> --%>
								
					<c:choose>
<c:when test="${command.getOwnershipPrefix() eq 'SO'}">

	 	<table id="singleOwnerTable" class="table table-striped table-bordered margin-bottom-10">
                    <tbody>
                        <tr>                  
                        	<th width="20%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
							<th width="9%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
							<th width="9%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
							<th width="20%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
							<th width="10%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
							<th width="10%"><spring:message code="property.email" /></th>
							<th width="12%"><spring:message code="ownersdetail.adharno" /></th>
							<th width="10%"><spring:message code="ownersdetail.pancard" />                    	 
                        </tr>
                	 </tbody>
                        <tr>
									<td>
										<form:input id="assoOwnerName" 
											path="propTransferDto.propTransferOwnerList[0].ownerName" class="form-control mandColorClass hasCharacter preventSpace"
											data-rule-required="true" disabled="true" placeholder="Please Enter Owners Name" maxlength="200" />
									</td>
									
									
									 <td class="ownerDetails">
										<c:set var="baseLookupCode" value="GEN" /> 
										<form:select path="propTransferDto.propTransferOwnerList[0].genderId" disabled="true" class="form-control mandColorClass" data-rule-required="true" id="ownerGender_${d}">
										<form:option value="0">
										<spring:message code="property.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
										</form:select>
									</td> 
									
									<td class="ownerDetails">
										<c:set var="baseLookupCode" value="REL" /> 
										<form:select path="propTransferDto.propTransferOwnerList[0].relationId" class="form-control mandColorClass" data-rule-required="true" id="ownerRelation_${d}">
										<form:option value="0">
										<spring:message code="property.sel.optn.relation" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
										</form:select>
									</td>
							
								<td class="mand">
								<form:input id="assoGuardianName"
								path="propTransferDto.propTransferOwnerList[0].guardianName" class="form-control mandColorClass hasCharacter preventSpace" disabled="true"
								 data-rule-required="true" placeholder="Please Enter Guardian Name" maxlength="500"/>   
								</td>
								
								
								<td><form:input id="assoMobileno"
										path="propTransferDto.propTransferOwnerList[0].mobileno" class="hasMobileNo form-control mandColorClass"
										 data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter" disabled="true"/>   
								</td>
								
								<td><form:input id="emailId"
										path="propTransferDto.propTransferOwnerList[0].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true"/>   
								</td>

								<td class="ownerDetails">
									<form:input id="assoAddharno"
									path="propTransferDto.propTransferOwnerList[0].addharno" class="form-control mandColorClass hasAadharNo" maxlength="12"
									data-rule-minlength="12" type="text"/>
								</td>
								<td class="companyDetails">
								 		<form:input id="pannumber" path="propTransferDto.propTransferOwnerList[0].panno"
										class="form-control text-uppercase hasNoSpace" maxLength="10"
										onchange="fnValidatePAN(this)" />
								 </td>
								<form:hidden id="assoPrimaryOwn"
									path="propTransferDto.propTransferOwnerList[0].otype" value="P" />								
						</tr>     
                     </table>
		 	 	</c:when>
		 	 	
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">
				 <c:choose>	
				 	<c:when test="${not empty command.getPropTransferDto().getPropTransferOwnerList()}" > 

				 
							<table id="jointOwnerTable" class="table text-left table-striped table-bordered margin-bottom-10">
								<tbody>
		
										<tr>
											<th width="17%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
											<th width="10%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
											<th width="8%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
											<th width="17%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
											<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
											<th width="11%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
											<th width="10%"><spring:message code="property.email" /></th>
											<th width="13%"><spring:message code="ownersdetail.adharno" /></th>
											<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
										</tr>
									
										<c:forEach  items="${command.getPropTransferDto().getPropTransferOwnerList()}" varStatus="status" >
										
										<tr class="jointOwner">
											<td>
												<c:set var="d" value="0" scope="page" />
												<form:input id="assoOwnerName_${status.count-1}" 
													path="propTransferDto.propTransferOwnerList[${status.count-1}].ownerName" class="form-control mandColorClass hasCharacter preventSpace"
													data-rule-required="true" placeholder="Please Enter" maxlength="200" disabled="true"/>
											</td>
											
											<td class="ownerDetails">
												<c:set var="baseLookupCode" value="GEN" /> 
												<form:select path="propTransferDto.propTransferOwnerList[${status.count-1}].genderId" class="form-control mandColorClass" disabled="true" id="ownerGender_${status.count-1}">
												<form:option value="0">
												<spring:message code="property.sel.optn.gender" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
												</form:select>
											</td>
											
											<td class="ownerDetails">
												<c:set var="baseLookupCode" value="REL" /> 
												<form:select path="propTransferDto.propTransferOwnerList[${status.count-1}].relationId" class="form-control mandColorClass" id="ownerRelation_${status.count-1}">
												<form:option value="0">
												<spring:message code="property.sel.optn.relation" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
												</form:select>
											</td>
									
										<td>
										<form:input id="assoGuardianName_${status.count-1}"
										path="propTransferDto.propTransferOwnerList[${status.count-1}].guardianName" disabled="true" class="form-control mandColorClass hasCharacter preventSpace"
										 data-rule-required="true" placeholder="Please Enter" maxlength="500"/>   
										</td>
										
										<td>
											<form:input id="assoPropertyShare_${status.count-1}"
											path="propTransferDto.propTransferOwnerList[${status.count-1}].propertyShare" class="form-control mandColorClass hasNumber"
											maxlength="3" />
										</td>
										
										<td><form:input id="assoMobileno_${status.count-1}"
												path="propTransferDto.propTransferOwnerList[${status.count-1}].mobileno" disabled="true" class="hasMobileNo form-control mandColorClass"
												data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
										</td>
										
										<td><form:input id="emailId_${status.count-1}"
										path="propTransferDto.propTransferOwnerList[${status.count-1}].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true"/>   
										</td>
								
										<td class="ownerDetails">
											<form:input id="assoAddharno_${status.count-1}"
											path="propTransferDto.propTransferOwnerList[${status.count-1}].addharno" class="form-control mandColorClass hasAadharNo"
											 placeholder="Please Enter" maxlength="12" data-rule-required="true" data-rule-maxlength="12" data-rule-minlength="12" />
										</td>
										<td class="companyDetails">
											<form:input id="pannumber${status.count-1}"
											path="propTransferDto.propTransferOwnerList[${status.count-1}].panno" class="form-control hasPanno text-uppercase hasNoSpace" maxlength="10" onchange="fnValidatePAN(this)"/>
										</td>
									
											<form:hidden id="assoPrimaryOwn"
											path="propTransferDto.propTransferOwnerList[${status.count-1}].otype"  value="P" />
										
										<%-- 	<form:hidden path="command.ownerDetailTableCount" id="ownerDetail"/> --%>
										
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:when>
						</c:choose>
						</c:when>
						
					</c:choose>
    				
					
<!-----------------------------------------------Transfer Details-------------------------------------------------------------------->
					<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#transferDetails"><spring:message code="propertyTax.mutation.TransferDetails"/></a>
					</h4>
				 <div class="panel-collapse collapse in" id="transferDetails">
					 <div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message code="propertyTax.mutation.TransferType"/></label>
									<div class="col-sm-4">
									<c:set var="baseLookupCode" value="TFT" /> <form:select path="propTransferDto.transferType"
											 class="form-control changeParameterClass mandColorClass"  data-rule-required="true" disabled="true" >
												<form:option value="">
													<spring:message code="bill.Select" text="Select"/>
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
									</div>
		
							<label class="col-sm-2 control-label required-control"><spring:message code="propertyTax.mutation.TransferDate" text="Actual Transfer Date"/></label>
							<div class="col-sm-4">
							<div class="input-group"> 
									<form:input path="propTransferDto.actualTransferDate" class="lessthancurrdate form-control mandColorClass dateClass" disabled="true"  id="actualTransferDate" data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off"/>
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>			
							</div>
							
							</div>
					</div>
					<div class="form-group">
					
						<label class="col-sm-2 control-label" for="marketValue"><spring:message code="property.MarketValue" text="Market Value"/></label>
							<div class="col-sm-4">
							<form:input  type="text" class="form-control mandColorClass hasNumber"
				 					path="propTransferDto.marketValue"  id="marketValue"></form:input>
							</div>
							
							<label class="col-sm-2 control-label" for="salesDeedValue"><spring:message code="property.SalesDeedValue" text="Sales Deed Value"/></label>
							<div class="col-sm-4">
							<form:input  type="text" class="form-control mandColorClass hasNumber"
				 					path="propTransferDto.salesDeedValue"  id="salesDeedValue"></form:input>
							</div>
						
					</div> 
					</div>       

	<!--   Tax Calculation -->
<c:if test="${not empty command.propTransferDto.charges}">  
   <h4 class="margin-top-10 margin-bottom-10 panel-title ">
   <a data-toggle="collapse" href="#TaxCalculation" class="contentRemove"><spring:message code="property.Charges" text="Charges"/></a>
    </h4>         
    <div class="panel-collapse collapse in" id="TaxCalculation">      
     
      <c:set var="totPayAmt" value="0"/>
            <div class="table-responsive">
              <table class="table table-striped table-condensed table-bordered">
                <tbody>
                  <tr>
                    <th width="50"><spring:message code="propertyTax.SrNo"/></th>
                    <th width="600"><spring:message code="propertyTax.TaxName"/></th>
                    <th width="400"><spring:message code="propertyTax.Total"/></th>
                  </tr>
	<!-- 	  				  <td>--</td>
		                  <td>--</td>
		                  <td class="text-right">--</td>
		                  <td class="text-right">--</td>
		                  <td class="text-right">--</td> -->
                <c:forEach var="tax" items="${command.propTransferDto.charges}"  varStatus="status">
                  <tr>
                  <td>${status.count}</td>
                  <td>${tax. getTaxDesc()}</td>
                  <td class="text-right">${tax.getTotalTaxAmt()}</td>
                </tr>
                </c:forEach>
<%--                   <tr>
                    <th colspan="2" class="text-right"><spring:message code="propertyTax.Total"/></th>
                    <th class="text-right">${totArrTax}</th>
					<th class="text-right">${totCurrTax}</th>
					<th class="text-right">${totTotTax}</th>
                  </tr> --%>
                </tbody>
              </table>
            </div>

            <div class="table-responsive margin-top-10">
              <table class="table table-striped table-bordered">
                <tr>
                  <th width="500"><spring:message code="propertyTax.TotalTaxPayable"/></th>
                  <th width="500" class="text-right">${command.propTransferDto.billTotalAmt}</th>
                </tr>
              </table>
            </div>
          </div>
      </c:if> 		
			
	 <c:if test="${command.getAppliChargeFlag() eq 'Y' && command.getSaveButFlag() eq 'Y' && not empty command.propTransferDto.charges}">  
<%-- 	        <h4 class="margin-top-10 margin-bottom-10 panel-title">
        <a data-toggle="collapse" href="#Payment">
        Payment</a></h4>
            <div class="panel-collapse collapse in" id="Payment">      
            <div class="form-group hidden-print">
             	<label for="billTotamount" class="col-sm-2 control-label "><spring:message code="propertyView.payAmt"/></label>
	              	<div class="col-sm-4"><form:input path="propTransferDto.billTotalAmt" type="text" id="billTotamount" class="form-control " disabled="true"/></div>
	              	<label for="partialAmt" class="col-sm-2 control-label"><spring:message code="propertyView.amtToPay"/></label>
	              	<div class="col-sm-4"><form:input path="provisionalAssesmentMstDto.billPartialAmt" type="text" id="partialAmt" class="form-control hasNumber"/></div>       
            </div>
            </div>	 --%>	
		<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp"/>		
	</c:if>


      <div class="text-center padding-top-10">
           			<button type="button" class="btn btn-success btn-submit"
							onclick="savePropertyFrom(this)" id="saveMut">
							<spring:message code="propertyTax.mutation.saveMutation" text="Save Mutation"/>
						</button>
						
				
						<button type="button" class="btn btn-danger"
				         id="backToSearch" onclick="backToPreviousPage(this)"><spring:message code="property.Back" text="Back"/></button>
	
							<input type='button' id='btnPrint' value='Print'
		style='width: 100px; position: fixed; bottom: 10px; left: 47%; z-index: 111;'
		onclick='window.print()' />
						
									
		<!--  End button -->
		</div>
	</div>
 </form:form>  
		</div>
		</div>
 </div>
 	</div>
