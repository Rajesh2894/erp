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
<script type="text/javascript" src="js/property/ownershipDetailsForm.js"></script>
<div  id="ownerType">

<div class="panel panel-default  ">
<!-- <div id="owner"> -->
<c:choose>
<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#SoTable"><spring:message
				code="property.Ownerdetail" /></a> 
	</h4>

	 	<table id="SoTable" class="table table-striped table-bordered ">
                    <tbody>
                        <tr>                  
                        	<th width="20%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
							<th width="9%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
							<th width="9%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
							<th width="20%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
							<th width="10%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
							<th width="10%"><spring:message code="property.email" /></th>
							<th width="12%" class="required-control"><spring:message code="ownersdetail.adharno" /></th>
							<th width="10%"><spring:message code="ownersdetail.pancard" />                    	 
                        </tr>
                	 </tbody>
                        <tr>
									<td>
										<form:input id="assoOwnerName" 
											path="command.propTransferDto.propTransferOwnerList[0].ownerName" class="form-control mandColorClass hasSpecialChara preventSpace"
											data-rule-required="true" placeholder="Please Enter Owners Name" maxlength="200" />
									</td>
									
									
									 <td class="ownerDetails">
										<c:set var="baseLookupCode" value="GEN" /> 
										<form:select path="command.propTransferDto.propTransferOwnerList[0].genderId" class="form-control mandColorClass" data-rule-selectDropValidation="true" id="ownerGender_${d}">
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
										<form:select path="command.propTransferDto.propTransferOwnerList[0].relationId" class="form-control mandColorClass" data-rule-selectDropValidation="true" id="ownerRelation_${d}">
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
								path="command.propTransferDto.propTransferOwnerList[0].guardianName" class="form-control mandColorClass hasSpecialChara preventSpace"
								 data-rule-required="true" placeholder="Please Enter Guardian Name" maxlength="500"/>   
								</td>
								
								
								<td><form:input id="assoMobileno"
										path="command.propTransferDto.propTransferOwnerList[0].mobileno" class="hasMobileNo form-control mandColorClass"
										 data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
								</td>
								
								<td><form:input id="emailId"
										path="command.propTransferDto.propTransferOwnerList[0].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true"/>   
								</td>

								<td class="ownerDetails">
									<form:input id="assoAddharno"
									path="command.propTransferDto.propTransferOwnerList[0].addharno" class="form-control mandColorClass hasAadharNo" maxlength="12"
									data-rule-minlength="12" type="text"/>
								</td>
								<td class="companyDetails">
								 		<form:input id="pannumber" path="command.propTransferDto.propTransferOwnerList[0].panno"
										class="form-control text-uppercase hasNoSpace" maxLength="10"
										onchange="fnValidatePAN(this)" />
								 </td>
								<form:hidden id="assoPrimaryOwn"
									path="command.propTransferDto.propTransferOwnerList[0].otype" value="P" />								
						</tr>     
                     </table>
		 	 	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">
				 <c:choose>	
				 	<c:when test="${not empty command.getPropTransferDto().getPropTransferOwnerList()}" > 
						 <h4 class="margin-top-0 margin-bottom-10 panel-title">
						 <a data-toggle="collapse" href="#jointOwnerTable"><spring:message
								code="property.Owner(s)detail" /></a> 
						 </h4>
				 
							<table id="joTable" class="table text-left table-striped table-bordered">
								<tbody>
		
										<tr>
											<th width="17%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
											<th width="10%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
											<th width="8%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
											<th width="17%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
											<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
											<th width="11%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
											<th width="10%"><spring:message code="property.email" /></th>
											<th width="13%" class="required-control"><spring:message code="ownersdetail.adharno" /></th>
											<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
											<th width="8%"><spring:message code="property.add/delete" /></th>
										</tr>
									
										<c:forEach  items="${command.getPropTransferDto().getPropTransferOwnerList()}" varStatus="status" >
										
										<tr class="jointOwner">
											<td>
												<c:set var="d" value="0" scope="page" />
												<form:input id="assoOwnerName_${status.count-1}" 
													path="command.propTransferDto.propTransferOwnerList[${status.count-1}].ownerName" class="form-control mandColorClass hasSpecialChara preventSpace"
													data-rule-required="true" placeholder="Please Enter" maxlength="200"/>
											</td>
											
											<td class="ownerDetails">
												<c:set var="baseLookupCode" value="GEN" /> 
												<form:select path="command.propTransferDto.propTransferOwnerList[${status.count-1}].genderId" class="form-control mandColorClass" id="ownerGender_${status.count-1}" data-rule-selectDropValidation="true">
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
												<form:select path="command.propTransferDto.propTransferOwnerList[${status.count-1}].relationId" class="form-control mandColorClass" id="ownerRelation_${status.count-1}" data-rule-selectDropValidation="true">
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
										path="command.propTransferDto.propTransferOwnerList[${status.count-1}].guardianName" class="form-control mandColorClass hasSpecialChara preventSpace"
										 data-rule-required="true" placeholder="Please Enter" maxlength="500"/>   
										</td>
										
										<td>
											<form:input id="assoPropertyShare_${status.count-1}"
											path="command.propTransferDto.propTransferOwnerList[${status.count-1}].propertyShare" class="form-control mandColorClass hasNumber"
											maxlength="3" />
										</td>
										
										<td><form:input id="assoMobileno_${status.count-1}"
												path="command.propTransferDto.propTransferOwnerList[${status.count-1}].mobileno" class="hasMobileNo form-control mandColorClass"
												data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
										</td>
										
										<td><form:input id="emailId_${status.count-1}"
										path="command.propTransferDto.propTransferOwnerList[${status.count-1}].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true"/>   
										</td>
								
										<td class="ownerDetails">
											<form:input id="assoAddharno_${status.count-1}"
											path="command.propTransferDto.propTransferOwnerList[${status.count-1}].addharno" class="form-control mandColorClass hasAadharNo"
											 placeholder="Please Enter" maxlength="12" data-rule-required="true" data-rule-maxlength="12" data-rule-minlength="12" />
										</td>
										<td class="companyDetails">
											<form:input id="pannumber${status.count-1}"
											path="command.propTransferDto.propTransferOwnerList[${status.count-1}].panno" class="form-control hasPanno text-uppercase hasNoSpace" maxlength="10" onchange="fnValidatePAN(this)"/>
										</td>
									
										<td>
											<a href="javascript:void(0);" title="Add" class="addOwner btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
											<a href="javascript:void(0);" title="Delete" class="remOwner btn btn-danger btn-sm" id="deleteOwnerRow_${status.count-1}"><i class="fa fa-trash-o"></i></a>
										</td>
											<form:hidden id="assoPrimaryOwn"
											path="command.propTransferDto.propTransferOwnerList[${status.count-1}].otype"  value="P" />
										
										<%-- 	<form:hidden path="command.ownerDetailTableCount" id="ownerDetail"/> --%>
										
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:when>
					
						<c:otherwise>		
							 <h4 class="margin-top-0 margin-bottom-10 panel-title">
					 		 <a data-toggle="collapse" href="#jointOwnerTable"><spring:message
								code="property.Owner(s)detail" /></a> 
					 		 </h4>
			 
							<table id="joTable" class="table text-left table-striped table-bordered">
							<tbody>	
									<tr>
										<th width="17%" class="required-control"><spring:message code="ownersdetail.ownersname" /></th>
										<th width="10%" class="required-control"><spring:message code="ownersdetail.gender" /></th>
										<th width="8%" class="required-control"><spring:message code="ownerdetails.relation" /></th>
										<th width="17%" class="required-control"><spring:message code="ownersdetails.GuardianName" /></th>
										<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
										<th width="11%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
										<th width="10%"><spring:message code="property.email" /></th>
										<th width="13%" class="required-control"><spring:message code="ownersdetail.adharno" /></th>
										<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
										<th width="8%"><spring:message code="property.add/delete" /></th>
									</tr>
								
									<tr class="jointOwner">
										<td>
											<c:set var="d" value="0" scope="page" />
											<form:input id="assoOwnerName_${d}" 
												path="command.propTransferDto.propTransferOwnerList[0].ownerName" class="form-control mandColorClass hasSpecialChara preventSpace"
												data-rule-required="true" placeholder="Please Enter" maxlength="200"/>
										</td>
										
										<td class="ownerDetails">
											<c:set var="baseLookupCode" value="GEN" /> 
											<form:select path="command.propTransferDto.propTransferOwnerList[0].genderId" class="form-control mandColorClass" id="ownerGender_${d}" data-rule-selectDropValidation="true">
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
											<form:select path="command.propTransferDto.propTransferOwnerList[0].relationId" class="form-control mandColorClass" id="ownerRelation_${d}" data-rule-selectDropValidation="true">
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
									<form:input id="assoGuardianName_${d}"
									path="command.propTransferDto.propTransferOwnerList[0].guardianName" class="form-control mandColorClass hasSpecialChara preventSpace"
									 data-rule-required="true" placeholder="Please Enter" maxlength="500"/>   
									</td>
									
									<td>
										<form:input id="assoPropertyShare_${d}"
										path="command.propTransferDto.propTransferOwnerList[0].propertyShare" class="form-control mandColorClass hasNumber"
										maxlength="3"/>
									</td>
									
									<td><form:input id="assoMobileno_${d}"
											path="command.propTransferDto.propTransferOwnerList[0].mobileno" class="hasMobileNo form-control mandColorClass"
											data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
									</td>	
									
									<td><form:input id="emailId_${d}"
										path="command.propTransferDto.propTransferOwnerList[0].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true"/>   
									</td>									
									
									<td class="ownerDetails">
										<form:input id="assoAddharno_${d}"
										path="command.propTransferDto.propTransferOwnerList[0].addharno" class="form-control mandColorClass hasAadharNo"
										maxlength="12" data-rule-maxlength="12" data-rule-minlength="12"/>
									</td>
									<td class="companyDetails">
										<form:input id="pannumber${d}"
										path="command.propTransferDto.propTransferOwnerList[0].panno" class="form-control hasPanno text-uppercase hasNoSpace" maxlength="10" onchange="fnValidatePAN(this)" />
									</td>
								
									<td>
										<a href="javascript:void(0);" title="Add" class="addOwner btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" title="Delete" class="remOwner btn btn-danger btn-sm" id="deleteOwnerRow_0"><i class="fa fa-trash-o"></i></a>
									</td>
										<form:hidden id="assoPrimaryOwn"
										path="command.propTransferDto.propTransferOwnerList[0].otype"  value="P" />
										
										<form:hidden path="command.ownerDetailTableCount" id="ownerDetail"/>								
																
									</tr>								
							</tbody>
							</table>						
						</c:otherwise>
					</c:choose>
			</c:when>
			
	<c:when test="${command.getOwnershipPrefix() eq 'JO'}">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#jointOwnerTable"><spring:message
				code="property.Owner(s)detail" /></a>   
	</h4>				
				
	</c:when>	
			
		<c:otherwise>
	
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#OtherTable"><spring:message
				code="property.Ownerdetail" /></a> 
				</h4>
				
				<table id="OtherTable" class="table text-left table-striped table-bordered">

					<tbody>				
						<tr>
									<th width="25%" class="required-control"><spring:message code="property.NameOf" /> ${command.getOwnershipTypeValue()}</th>
									<th width="25%" class="required-control"><spring:message code="ownersdetail.contactpersonName" /></th>
									<th width="15%" class="required-control"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email" /></th>
									<th width="15%"><spring:message code="ownersdetail.pancard" /></th>
						</tr>
						
 						<tr>
 									<td> 									
 										<form:input id="assoOwnerName"  
 											path="command.propTransferDto.propTransferOwnerList[0].ownerName" class="form-control mandColorClass hasSpecialChara preventSpace" 
 											data-rule-required="true" placeholder="Please Enter" maxlength="200" /> 
 									</td> 
						
 								<td> 
 								<form:input id="assoGuardianName" 
 								path="command.propTransferDto.propTransferOwnerList[0].guardianName" class="form-control mandColorClass hasSpecialChara preventSpace"  
 								 data-rule-required="true" placeholder="Please Enter" maxlength="500"/>  
								</td> 
								
 								<td><form:input id="assoMobileno" 
 										path="command.propTransferDto.propTransferOwnerList[0].mobileno" class="hasMobileNo form-control mandColorClass" 
										data-rule-maxlength="10" data-rule-minlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
 								</td>
 								
 								<td><form:input id="emailId"
										path="command.propTransferDto.propTransferOwnerList[0].eMail" class="hasemailclass form-control preventSpace" maxlegnth="254" data-rule-email="true"/>   
								</td> 
								
								<td class="companyDetails"> 
								
									<form:input id="pannumber" 
									path="command.propTransferDto.propTransferOwnerList[0].panno" class="form-control hasPanno text-uppercase preventSpace" maxlength="10" onchange="fnValidatePAN(this)"/> 
								</td> 
									<form:hidden id="assoPrimaryOwn"
									path="command.propTransferDto.propTransferOwnerList[0].otype"  value="P" />
 					</tr> 								
					</tbody>
					</table>	
		</c:otherwise>	
					</c:choose>
					</div>
					
				</div>
<script>
$(document).ready(function(){
	   jQuery.validator.addMethod("selectDropValidation", function(value, element) {
    	    return this.optional(element) || (parseFloat(value) > 0);
       }, getLocalMessage("common.empty.validation.message"));
	
});
</script>				
<!-- </div>  -->