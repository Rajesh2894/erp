<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/updateDataEntry.js"></script>

<div id="validationDiv">

	<!-- Start breadcrumb Tags -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<!-- End breadcrumb Tags -->

	<!-- Start Content here -->
	<div class="content">
		<!-- Start Main Page Heading -->
		<div class="widget">
		<div class="mand-label clearfix">
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">* </i><spring:message code="property.ismandatory"/>
			</span>
		</div>
			<div class="widget-header">
				<h2>
					<strong><spring:message code="" text="Update Data Entry" /></strong>
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message
									code="property.Help" /></span></i></a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form action="UpdateDataEntrySuite.html"
					class="form-horizontal form" name="UpdateDataEntrySuite"
					id="UpdateDataEntrySuite">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="oldPropNo"><spring:message
								code="propertydetails.oldpropertyno" /></label>
						<div class="col-sm-4">
							<form:input path="provisionalAssesmentMstDto.assOldpropno"
								id="oldPropNo" class="form-control" disabled="false" />
						</div>
						<label class="col-sm-2 control-label" for="houseNo"><spring:message
								code="property.house.no" /></label>
						<div class="col-sm-4">
							<form:input path="provisionalAssesmentMstDto.tppPlotNo"
								id="tppPlotNo" class="form-control" disabled="false" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label" for="ownershiptype"><spring:message
								code="property.ownershiptype" /></label>
						<div class="col-sm-4">
							<form:input path="provisionalAssesmentMstDto.proAssOwnerTypeName"
								id="ownershiptype" class="form-control" disabled="true" />
							<form:hidden path="ownershipPrefix" id="ownershipId"
								class="form-control" />
						</div>
					</div>
					
					<!----------------------------Owner Details------------------------------->
		<div class="accordion-toggle">
            <h4 class="margin-top-0 margin-bottom-10 panel-title">
				<a data-toggle="collapse" href="#OwnshipDetail"><spring:message
				code="property.Ownerdetail" /></a>
			</h4>
     
	<div class="panel-collapse collapse in" id="OwnshipDetail">
	<c:set var="e" value="0" scope="page" />
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
			<form:input id="assoOwnerName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="false"/>
		</td>
									
		<td>
		<div>									
									<spring:message code="bill.Select" text="Select" var="gender"/>
									
									<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].genderId" cssClass="form-control mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="${gender}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/> </div>
				<%-- <form:input id="proAssGenderId" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssGenderId"   class="form-control" disabled="flase"/> --%>				
		</td>
									
		<td>	
		
		<div>
										<spring:message code="bill.Select" text="Select" var="Relation"/>
									
										<c:set var="baseLookupCode" value="REL" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].relationId" cssClass="form-control mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="${Relation}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/></div>	
			<%-- <form:input id="proAssRelationId" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssRelationId"  class="form-control" disabled="flase"/> --%>
		</td>
							
		<td>
			<form:input id="assoGuardianName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control" disabled="false" />   
		</td>
	
		<td>
			<form:input id="assoMobileno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" maxlength="10" disabled="false"/>   
		</td>
		
		<td><form:input id="eMail" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" disabled="false"/>   
		</td>
									
		<td>
			<form:input id="assoAddharno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno" class="form-control " maxlength="12"  disabled="false"/>
		</td>
		<td class="companyDetails">
			<form:input id="assoPanno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control"  disabled="false"/>
		</td>
	</tr>
	<c:set var="e" value="${e + 1}" scope="page" />
	</tbody>
    </table>
	</c:when>
		 	 	
		 <c:when test="${command.getOwnershipPrefix() eq 'JO'}">	
		 <%-- <c:set var="a" value='0'/> --%>
		 
					<table id="jointOwnerTable" class="table text-left table-striped table-bordered">
						<tbody>
								<tr>
									<th width="3%"><spring:message code="" text="Sr.No"/></th>
									<th width="14%"><spring:message code="ownersdetail.ownersname" /></th>
									<th width="10%"><spring:message code="ownersdetail.gender" /></th>
									<th width="8%" ><spring:message code="ownerdetails.relation" /></th>
									<th width="17%"><spring:message code="ownersdetails.GuardianName" /></th>
									<th width="5%"><spring:message code="ownerdetails.PropertyShare" /></th>	
									<th width="11%"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email" /></th>
									<th width="12%"><spring:message code="ownersdetail.adharno" /></th>
									<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
								</tr>
						<c:forEach var="provisionalAssesmentOwnerDtlDtoList" items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}">
						<%-- <c:set var="e" value="0" scope="page" /> --%>
            			<tr>
            			 <td>
            			  	<form:input path="" id="${e}" value="${e + 1}"	cssClass="required-control form-control" disabled="true" />
						</td>
						<td>
            			  	<form:input path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].assoOwnerName" id="assoOwnerName${e}"
													cssClass="required-control form-control" disabled="false" />
						</td>
						<td>
						
						<div>									
									<spring:message code="bill.Select" text="Select" var="gender"/>
									
									<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].genderId" cssClass="form-control mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="${gender}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/> </div>
										
            			  	<%-- <form:input path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].proAssGenderId" id="proAssGenderId${e}"
													cssClass="required-control form-control" disabled="false" /> --%>
						</td>
						<td>
						
						<div>
										<spring:message code="bill.Select" text="Select" var="Relation"/>
									
										<c:set var="baseLookupCode" value="REL" />
										<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].relationId" cssClass="form-control mandColorClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="${Relation}"  hasTableForm="true" isMandatory="true" isNotInForm="true"/></div>	
										
										
            			  	<%-- <form:input path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].proAssRelationId" id="proAssRelationId${e}"
													cssClass="required-control form-control" disabled="false" /> --%>
						</td>
						<td>
            			  	<form:input path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].assoGuardianName" id="assoGuardianName${e}"
													cssClass="required-control form-control" disabled="false" />
						</td>
						<td>
            			  	<form:input path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].propertyShare" id="propertyShare${e}"
													cssClass="required-control form-control" disabled="false" />
						</td>
						<td>
            			  	<form:input path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].assoMobileno" id="assoMobileno${e}"
													cssClass="required-control form-control hasNumber" disabled="false" maxlength="10"/>
						</td>
						<td>
            			  	<form:input path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].eMail" id="eMail${e}"
													cssClass="required-control form-control" disabled="false" />
						</td>
						<td>
            			  	<form:input path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].assoAddharno" id="assoAddharno${e}"
													cssClass="required-control form-control hasNumber" disabled="false" maxlength="12" />
						</td>
						<td>
            			  	<form:input path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[${e}].assoPanno" id="assoPanno${e}"
													cssClass="required-control form-control" disabled="false" />
						</td>
            			</tr>
            			<c:set var="e" value="${e + 1}" scope="page" />
                		 </c:forEach>
						</tbody>
					</table>
					</c:when>
				<c:otherwise>
			<table id="ownerTable" class="table text-left table-striped table-bordered">
			
					<tbody>					
						<tr>
									<th width="25%"><spring:message code="property.NameOf" /> ${command.getOwnershipTypeValue()}</th>
									<th width="25%"><spring:message code="ownersdetail.contactpersonName" /></th>
									<th width="10%"><spring:message code="ownersdetail.mobileno" /></th>
									<th width="10%"><spring:message code="property.email" /></th>
									<th width="10%"><spring:message code="ownersdetail.pancard" /></th>
						</tr>
						<%-- <c:set var="e" value="0" scope="page" /> --%>
 						<tr>
 						<td> 
 						<form:input id="assoOwnerName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName" class="form-control" disabled="false"/> 
 						</td> 
						
 						<td> 
 						<form:input id="assoGuardianName" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName" class="form-control " disabled="false" />  
						</td> 
								
 						<td><form:input id="assoMobileno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno" class="hasNumber form-control" maxlength="10" disabled="false" />   
 						</td> 
 						
 						<td><form:input id="eMail" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail" class="hasemailclass form-control preventSpace" disabled="false"/>   
						</td>
								
						<td class="companyDetails"> <form:input id="assoPanno" path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno" class="form-control" disabled="false" /> 
						</td> 
 						</tr> 								
					<c:set var="e" value="${e + 1}" scope="page" />
					</tbody>
					</table>				
					</c:otherwise>	
					</c:choose>
					</div>
					</div>
					
					<!---------------------------End Owner Details---------------------------->
					
					<!---------------------------Property Address Location-------------------------->
				<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#propertyAddress"><spring:message
								code="property.Propertyaddress" /></a>
						</h4>
					<div class="panel-collapse collapse in" id="propertyAddress">
					
					<div class="form-group">
						<apptags:textArea labelCode="property.propertyaddress" path="provisionalAssesmentMstDto.assAddress" maxlegnth="500" cssClass="preventSpace" isMandatory="true"></apptags:textArea>
						<apptags:select cssClass="chosen-select-no-results" labelCode="property.location" items="${command.location}" path="provisionalAssesmentMstDto.locId" isLookUpItem="true" selectOptionLabelCode="property.selectLocation" isMandatory="true">
						</apptags:select>		
					</div>
					
					<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="proAssPincode"><spring:message code="property.pincode"/></label>
					<div class="col-sm-4">
						<form:input  type="text" class="form-control hasPincode"
							 path="provisionalAssesmentMstDto.assPincode" id="assPincode" maxlength="6" data-rule-digits="true" data-rule-minlength="6" data-rule-maxlength="6"></form:input>
					</div>
					
					</div>
		
					</div>
					</div>
					
					<!-------------------------End Property Address Location------------------------>
					
					<!--------------------------------Upload Documents------------------------------>
					<div class="panel-group accordion-toggle" id="accordion_single_collapse">
						<div class="panel panel-default">
						<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="property.documentsupload" text="Documents Upload" /></a>
						</h4>
						</div>
					
					<div id="a1" class="panel-collapse collapse in">
						<form:hidden path="" id="removeCommonFileById" />

						<div class="panel-body">

							<c:if test="${fn:length(command.attachDocsList)>0}">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="deleteCommonDoc">
										<tr>
											<th width="" align="center"><spring:message
													code="ser.no" text="" /><input type="hidden" id="srNo"></th>
											<th scope="col" width="64%" align="center"><spring:message
													code="work.estimate.document.description"
													text="Document Description" /></th>
											<th scope="col" width="30%" align="center"><spring:message
													code="scheme.view.document" /></th>
											<c:if test="${command.modeType ne 'V'}">
												<th scope="col" width="8%"><spring:message
														code="scheme.action" text=""></spring:message></th>
											</c:if>
										</tr>
										<c:set var="e" value="0" scope="page" />
										<c:forEach items="${command.attachDocsList}" var="lookUp">
											<tr>
												<td>${e+1}</td>
												<td>${lookUp.dmsDocName}</td>
												<td><apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="DataEntrySuite.html?Download" /></td>
												<c:if test="${command.modeType eq 'V'}">
													<td class="text-center"><a href='#'
														id="deleteCommonFile" onclick="return false;"
														class="btn btn-danger btn-sm"><i class="fa fa-trash"></i></a>
														<form:hidden path="" value="${lookUp.attId}" /></td>
												</c:if>
											</tr>
											<c:set var="e" value="${e + 1}" scope="page" />
										</c:forEach>
									</table>
								</div>
								<br>
							</c:if>

							<div id="doCommonFileAttachment">
								<div class="table-responsive">
									<c:set var="cd" value="0" scope="page" />
							
									<c:if test="${command.modeType eq 'V'}">
									<c:set var="cd" value="0" scope="page" />
									
										<table class="table table-bordered table-striped"
											id="attachCommonDoc">
											<tr>
												<th><spring:message
														code="work.estimate.document.description"
														text="Document Description" /></th>
												<th><spring:message code="work.estimate.upload"
														text="Upload Document" /></th>
												<th scope="col" width="8%"><a
													onclick='doCommonFileAttachment(this);'
													class="btn btn-blue-2 btn-sm addButton"><i
														class="fa fa-plus-circle"></i></a></th>
											</tr>

									<c:choose>
									
									<c:when test="${empty command.getCommonFileAttachment()}"> 
											<tr class="appendableUploadClass">
												<td><form:input
														path="commonFileAttachment[${cd}].doc_DESC_ENGL"
														class=" form-control" /></td>
												<td class="text-center"><apptags:formField
														fieldType="7"
														fieldPath="commonFileAttachment[${cd}].doc_DESC_ENGL"
														currentCount="${cd}" showFileNameHTMLId="true"
														fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
														maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
														callbackOtherTask="callbackOtherTask()">
													</apptags:formField></td>
												<td class="text-center"><a href='#' id="0_file_${cd}"
													onclick="doFileDelete(this)"
													class='btn btn-danger btn-sm delButton'><i
														class="fa fa-trash"></i></a></td>
											</tr>
											</c:when>
											<c:otherwise>
											
											<c:forEach items="${command.getCommonFileAttachment()}" var="lookUp">
											<tr class="appendableUploadClass">
												<c:set var="cd" value="0" scope="page" />
												<td><form:input
														path="commonFileAttachment[${cd}].doc_DESC_ENGL"
														class=" form-control" /></td>
												<td class="text-center"><apptags:formField
														fieldType="7"
														fieldPath="commonFileAttachment[${cd}].doc_DESC_ENGL"
														currentCount="${cd}" showFileNameHTMLId="true"
														fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
														maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
														callbackOtherTask="callbackOtherTask()">
													</apptags:formField></td>
												<td class="text-center"><a href='#' id="0_file_${cd}"
													onclick="doFileDelete(this)"
													class='btn btn-danger btn-sm delButton'><i
														class="fa fa-trash"></i></a></td>
														<c:set var="cd" value="${cd+1}" scope="page" />
											</tr>
											</c:forEach>
											</c:otherwise>
											</c:choose>
										</table>
								</c:if> 
								</div>
							</div>
						</div>
						</div>
						</div>
					</div>
				
					<!--------------------------------End Upload Documents----------------------------->
					<!--Search Button-->
					<div class="text-center padding-15 clear">
						<button type="button" class="btn btn-success btn-submit"
							onclick="updateDataEntry(this)" id="submit">
							<spring:message code="property.update" text="Update" />
						</button>

						<button type="button" class="btn btn-danger"
							data-original-title="Back"
							onclick="window.location.href='UpdateDataEntrySuite.html'">
							<spring:message code="property.Back" text="Back"></spring:message>
						</button>
					</div>
				</form:form>
				<!-- End Form -->
			</div>
			<!-- End Widget Content here -->
		</div>
		<!-- End Widget  here -->
	</div>
	<!-- End of Content -->
</div>

