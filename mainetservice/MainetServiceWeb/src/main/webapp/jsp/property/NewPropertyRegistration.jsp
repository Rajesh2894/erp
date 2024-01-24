<!-- Start JSP Necessary Tags -->
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
<div id="validationDiv">
<script type="text/javascript" src="js/property/newPropRegOwner.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="property.newPropertyRegistration"/></strong></h2>				
				
					<apptags:helpDoc url="NewPropertyRegistration.html"></apptags:helpDoc>	
				
	</div>
	
	<!-- End Main Page Heading -->
		
<!-- Start Widget Content -->
	<div class="widget-content padding">
	
		
		<!-- Start mand-label -->
		<div class="mand-label clearfix">
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">* </i><spring:message code="property.ismandatory"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<form:form action="NewPropertyRegistration.html"
					class="form-horizontal form" name="NewPropertyRegistration"
					id="NewPropertyRegistration">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			<input type="hidden" id="ownershipCode" />
			<input type="hidden" value="${command.orgId}" id="orgId"/>
			<input type="hidden" value="${command.deptId}" id="deptId"/>
			
			<!-- Start Each Section -->
			<c:if test="${command.getIntgrtionWithBP() eq 'Y'}">
			<div class="padding-10"><strong><i><spring:message code="property.EnterDetailstosearchproperty" /></i></strong></div>
			<div class="form-group">
					
					<label class="col-sm-2 control-label"><spring:message code="property.searchType" />
					</label>
				   
				   <c:set var="baseLookupCode" value="SRT" />
							<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="" cssClass="form-control changeParameterClass"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="select"/>
					
                   <apptags:input labelCode="property.searchNumber" path=""></apptags:input>
                   	
                   	<!-- Start button -->
                   	<div class="text-center padding-15 clear">                   	
                   	<button type="button" class="btn btn-warning"><spring:message code="property.search" text="" /></button>	
                   	</div>
                   	<!-- End button -->	
			</div> 
			</c:if>
			<!-- End Each Section -->
			
			<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						
					<div class="accordion-toggle ">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
									code="property.Ownershipdetail" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="OwnshipDetail">
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
						</div>
						<form:hidden path="selfAss" id="selfAss"/>
					</div>
					<div id="owner">
					
					</div>					
						
						<jsp:include page="/jsp/property/NewPropertyRegistrationLandDetails.jsp"></jsp:include>
						
						<jsp:include page="/jsp/property/NewPropertyPropertyAddressForm.jsp"></jsp:include>
			
<%-- 						<jsp:include page="/jsp/property/SelfAssessmentLastPaymentDetailForm.jsp"></jsp:include>
 --%>						
						<jsp:include page="/jsp/property/NewPropertyTaxZoneDetails.jsp"></jsp:include>
						
						<jsp:include page="/jsp/property/NewPropertyBuildingDetails.jsp"></jsp:include>
						
						<jsp:include page="/jsp/property/NewPropRegUnit.jsp"></jsp:include>
															
			
			
			<!-- Start button -->
			<div class="text-center padding-10">
			<c:if test="${command.getAssType() eq 'R'}">  
				<button type="button" class="btn btn-success"
				onclick="getCheckList(this)" id="checkList"><spring:message code="unit.proceed"/></button>
				</c:if>
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
				
		<%-- 		<c:if test="${command.getSelfAss() eq 'Y'}">  
				<input 	type="button" class="btn btn-warning" value="Reset" onclick="resetFormData(this)" id="Reset"/>	
				</c:if>		 --%>	
				
				<div class="text-center padding-10">
				<c:if test="${command.getAssType() eq 'R'}">  
						<button type="button" class="btn btn-success"
				onclick="confirmToProceed(this)" id="proceed"><spring:message code="unit.proceed"/></button>
				</c:if>
				
			<c:if test="${command.getAssType() eq 'A'}">  
			<button type="button" class="btn btn-success"
				onclick="compareBeforeAfterAuth(this)" id="btnCompare"><spring:message code="property.propProceed"/></button>
			</c:if> 
		
			</div>
			<!--  End button -->
			
			
		</form:form>
		<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
		</div>
		<!-- End Widget  here -->							
</div>
<!-- End of Content -->
</div>

