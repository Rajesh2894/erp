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

<!-- <script src="js/property/unitSpecificAdditionalInfo.js"></script> -->
<script src="js/property/ownershipDetailsForm.js"></script>

<form:form action="SelfAssessmentForm.html"
					class="form-horizontal form" name="SelfAssessmentOwnershipType"
					id="SelfAssessmentOwnershipType">


<div class="panel panel-default  ">

	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#Owner(s)_Detail"><spring:message
				code="property.Owner(s)detail" /></a> 
	</h4>

<c:choose>
<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
	 	<table id="singleOwnerTable" class="table table-striped table-bordered ">
                    <tbody>
                        <tr>
                   
                        	<th width="80"><spring:message code="ownersdetail.ownersname" /><span class="mand">*</span></th>
							<th width="80"><spring:message code="ownersdetail.gender" /><span class="mand">*</span></th>
							<th width="80"><spring:message code="ownerdetails.relation" /><span class="mand">*</span></th>
							<th width="80"><spring:message code="ownersdetails.GuardianName" /><span class="mand">*</span></th>
							<th width="80"><spring:message code="ownersdetail.mobileno" /><span class="mand">*</span></th>
							<th width="80"><spring:message code="ownersdetail.adharno" /><span class="mand">*</span></th>
							<th width="80"><spring:message code="ownersdetail.pancard" />                    	 
                        </tr>
                
                        <tr>
									<td>

										<form:input id="assoOwnerName" 
											path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass"
											data-rule-required="true" placeholder="Please Enter"  />
									</td>
									
									<td class="ownerDetails">
										<c:set var="baseLookupCode" value="GEN" /> 
										<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" class="form-control mandColorClass" id="ownerGender_${d}">
										<form:option value="0">
										<spring:message code="property.sel.optn" />
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
										<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" class="form-control mandColorClass" id="ownerRelation_${d}">
										<form:option value="0">
										<spring:message code="property.sel.optn" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
										</form:select>
									</td>
							
								<td class="mand">
								<form:input id="assoGuardianName_${d}"
								path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass"
								 data-rule-required="true" placeholder="Please Enter" />   
								</td>
								
								
								<td ><form:input id="assoMobileno_${d}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control mandColorClass"
										data-rule-maxlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
								</td>
									
								
								<td class="ownerDetails">
									<form:input id="assoAddharno_${d}"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control mandColorClass"
									 placeholder="Please Enter" />
								</td>
								<td class="companyDetails">
									<form:input id="assoPanno_${d}"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control" placeholder="Please Enter"
									 />
								</td>
								
						</tr>
                        
                        
                        </tbody>
                     </table>
		 	 	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">	
					<table id="jointOwnerTable" class="table text-left table-striped table-bordered">
						<tbody>

								<tr>
									<th width="80"><spring:message code="ownersdetail.ownersname" /><span class="mand">*</span></th>
									<th width="80"><spring:message code="ownersdetail.gender" /><span class="mand">*</span></th>
									<th width="80"><spring:message code="ownerdetails.relation" /><span class="mand">*</span></th>
									<th width="80"><spring:message code="ownersdetails.GuardianName" /><span class="mand">*</span></th>
									<th width="80"><spring:message code="ownerdetails.PropertyShare" /><span class="mand">*</span></th>	
									<th width="80"><spring:message code="ownersdetail.mobileno" /><span class="mand">*</span></th>
									<th width="80"><spring:message code="ownersdetail.adharno" /><span class="mand">*</span></th>
									<th width="80"><spring:message code="ownersdetail.pancard" /></th>
									<th width="80"><spring:message code="property.add/delete" /></th>
								</tr>
							
								<tr class="jointOwner">
									<td>
										<c:set var="d" value="0" scope="page" />
									<%-- 	<form:input type="hidden" id="" path="tableOwnerList[${d}].assoType" value="O"/> --%>
										<form:input id="assoOwnerName_${d}" 
											path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass"
											data-rule-required="true" placeholder="Please Enter" />
									</td>
									
									<td class="ownerDetails">
										<c:set var="baseLookupCode" value="GEN" /> 
										<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" class="form-control mandColorClass" id="ownerGender_${d}">
										<form:option value="0">
										<spring:message code="property.sel.optn" />
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
										<form:select path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" class="form-control mandColorClass" id="ownerRelation_${d}">
										<form:option value="0">
										<spring:message code="property.sel.optn" />
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
								path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass"
								 data-rule-required="true" placeholder="Please Enter"/>   
								</td>
								
								<td>
									<form:input id="assoPropertyShare_${d}"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].propertyShare" class="form-control mandColorClass"
									 placeholder="Please Enter"/>
								</td>
								
								<td><form:input id="assoMobileno_${d}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control mandColorClass"
										data-rule-maxlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
								</td>
									
								
								<td class="ownerDetails">
									<form:input id="assoAddharno_${d}"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control mandColorClass"
									 placeholder="Please Enter" />
								</td>
								<td class="companyDetails">
									<form:input id="assoPanno_${d}"
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control" placeholder="Please Enter" />
								</td>
							
								<td>
									<a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
									<a href="javascript:void(0);" title="Delete" class="remCF btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></a>
								</td>
								
								</tr>

							
						</tbody>
					</table>
					</c:when>
				<c:otherwise>
			<table id="companyDetailTable" class="table text-left table-striped table-bordered">

					<tbody>
					
						<tr>
									<th width="80"><spring:message code="ownersdetail.companyname" /><span class="mand">*</span></th>
									<th width="80"><spring:message code="ownersdetail.contactpersonName" /><span class="mand">*</span></th>
									<th width="80"><spring:message code="ownersdetail.mobileno" /><span class="mand">*</span></th>
									<th width="80"><spring:message code="ownersdetail.pancard" /></th>
						</tr>
						
 						<tr>
 									<td> 
 									
 										<form:input id="assoOwnerName_${d}"  
 											path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control mandColorClass" 
 											data-rule-required="true" placeholder="Please Enter"  /> 
 									</td> 
						
 								<td > 
 								<form:input id="assoGuardianName_${d}" 
 								path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control mandColorClass" 
 								 data-rule-required="true" placeholder="Please Enter"/>  
								</td> 
								
 								<td><form:input id="assoMobileno_${d}" 
 										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control mandColorClass" 
										data-rule-maxlength="10" maxlength="10" data-rule-required="true" placeholder="Please Enter"/>   
 								</td> 
								
								<td class="companyDetails"> 
								
									<form:input id="assoPanno_${d}" 
									path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control" placeholder="Please Enter"  /> 
								</td> 
 					</tr> 
								
					</tbody>
					</table>
					
					
					</c:otherwise>	
					</c:choose>
					
					
				</div>
				
</form:form>