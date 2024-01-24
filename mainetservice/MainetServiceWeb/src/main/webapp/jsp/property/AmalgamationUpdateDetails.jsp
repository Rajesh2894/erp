<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/property/amalgamation.js"></script>

<!-- End JSP Necessary Tags -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="property.Amalgamation" text=""/></strong></h2>				
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="property.Help"/></span></i></a>
				</div>
	</div>
	
	<!-- End Main Page Heading -->
		
	<!-- Start Widget Content -->
	<div class="widget-content padding">
		
		<!-- Start Form -->
		<form:form action="AmalgamationForm.html"
					class="form-horizontal form" name="AmalgamationFormDetails"
					id="AmalgamationFormDetails">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
 			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div> 
			
			<input type="hidden" value="${command.orgId}" id="orgId"/>
			<input type="hidden" value="${command.deptId}" id="deptId"/>
 			<div class="form-group">
 				<apptags:input labelCode="Property No" path="provisionalAssesmentMstDto.assNo" isDisabled="true"></apptags:input>
 			</div>
 			
 			<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
 			<div class="accordion-toggle ">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
									code="property.Ownershipdetail" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="OwnshipDetail">
							<div class="form-group" id="ownerTypeDiv">
								<label class="col-sm-2 control-label required-control"
									for="OwnershipDetail"><spring:message
										code="property.ownershiptype" /></label>
									<div class="col-sm-4">
									<c:set var="baseLookupCode" value="OWT" /> <form:select path="provisionalAssesmentMstDto.assOwnerType"
											onchange="getOwnerTypeInfo(this )" class="form-control mandColorClass" id="ownerTypeId" data-rule-required="true">
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
							<form:hidden path="ownershipPrefix"  id="ownerTypeCode" class="form-control"/>
							<form:hidden path="assType" id="assType"/>
							<form:hidden path="serviceShortCode" id="serviceShortCode"/>
							
							
						
						<div id="owner">
						
						</div>			
 					</div>
						
		  			</div>
<%--  					<jsp:include page="/jsp/property/SelfAssessmentPropertyDetailsForm.jsp"></jsp:include> --%>
			
				<jsp:include page="/jsp/property/NewPropertyRegistrationLandDetails.jsp"></jsp:include>
				
				<jsp:include page="/jsp/property/SelfAssessmentPropertyAddressForm.jsp"></jsp:include>
				
				<jsp:include page="/jsp/property/NewPropertyTaxZoneDetails.jsp"></jsp:include>
		
				<jsp:include page="/jsp/property/AmalgamationBuildingDetails.jsp"></jsp:include>
					
			<div class="text-center padding-10">
				<button type="button" class="btn btn-success"
				onclick="getAmalgCheckList(this)" id="checkList"><spring:message code="unit.proceed"/></button>

				<input 	type="button" class="btn btn-danger" value="Back" onclick="backToMainPage(this)" id="backToAmalgSearch"/>
				
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
 			
 			
 			<div class="text-center padding-10">
 				<button class="btn btn-success btn-submit" type="button"  onclick="saveData(this)" id="proceed">
										<spring:message code="property.SaveProceed"/></button>
 				 <c:if test="${command.getAssType() ne 'A'}">
			 	
				<input 	type="button" class="btn btn-danger" value="Back" onclick="backToMainPage(this)" id="back"/>
				</c:if>  
			</div>
 		</form:form>
 		
 		</div>
</div>
</div>