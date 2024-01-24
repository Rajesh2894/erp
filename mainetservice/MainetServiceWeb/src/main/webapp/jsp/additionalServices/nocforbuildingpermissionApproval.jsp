<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/additionalServices/nocforbuildingpermissionApproval.js"></script>





<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="NOC For Building Permission"  code="NOCBuildingPermission.nocforbuildingpermissions" />
				<%-- <apptags:helpDoc url="NOCForBuildingPermissionApproval.html"></apptags:helpDoc> --%>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
					class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
				</a>
			</div>
		</div>
		<div class="widget-content padding">

			<form:form action="NOCForBuildingPermissionApproval.html"
				id="frmnocbuildpermission" method="POST" commandName="command"
				class="form-horizontal form">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<h4>
					<spring:message text="Applicant Information "   code="NOCBuildingPermission.applicationInformation"/>
				</h4>
				<div class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="NOCBuildingPermission.applicationDate" readonly="true"
						datePath="nocBuildingPermissionDto.date" isMandatory="true"
						cssClass="custDate mandColorClass date">
					</apptags:date>

					<label class="col-sm-2 control-label required-control"
						for="applicantTitle">  <spring:message
								code="NOCBuildingPermission.title" text="Title" />   </label>
					<c:set var="baseLookupCode" value="TTL" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="nocBuildingPermissionDto.titleId" disabled="true"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						isMandatory="true" />

				</div>


				<div class="form-group">


					<apptags:input labelCode="NOCBuildingPermission.fName"
						isDisabled="true"
						cssClass="mandColorClass hasNoSpace"
						path="nocBuildingPermissionDto.fName" isMandatory="true"
						maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="NOCBuildingPermission.mName"
						isDisabled="true"
						cssClass="mandColorClass hasNoSpace"
						path="nocBuildingPermissionDto.mName" maxlegnth="100"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:input labelCode="NOCBuildingPermission.lName"
						isDisabled="true"
						cssClass="mandColorClass hasNoSpace"
						path="nocBuildingPermissionDto.lName" isMandatory="true"
						maxlegnth="100"></apptags:input>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NOCBuildingPermission.Sex" text="sex" /></label>
					<c:set var="baseLookupCode" value="GEN" />
					<apptags:lookupField items="${command.getLevelData('GEN')}"
						path="nocBuildingPermissionDto.sex" disabled="true"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" />
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NOCBuildingPermission.applicationType"
							text="Applicant Type" /></label>
					<c:set var="baseLookupCode" value="APT" />
					<apptags:lookupField items="${command.getLevelData('APT')}"
						path="nocBuildingPermissionDto.applicationType"
						cssClass="form-control chosen-select-no-results" disabled="true"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" />

					<apptags:input labelCode="NOCBuildingPermission.applicantAddress"
						cssClass="hasNumClass form-control"
						isDisabled="${command.saveMode eq 'V'}"
						path="nocBuildingPermissionDto.applicantAddress" maxlegnth="500"></apptags:input>

					<%-- <label class="control-label col-sm-2  required-control"
						for="text-1">Department Name </label>
					<div class="col-sm-4">
						<form:select path="nocBuildingPermissionDto.deptId"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select Department Name" disabled="true" id="deptId"
							onchange="refreshServiceData(this,'NOCForBuildingPermission.html','refreshServiceData')">
							<!-- Here the option is being loaded in the drop down list using forEach loop  -->
							<form:option value="" selected="true">Select Department Name</form:option>
							<c:forEach items="${command.deptList}" var="dept">
								<form:option value="${dept.dpDeptid }"
									code="${dept.dpDeptcode }">${dept.dpDeptdesc }</form:option>
							</c:forEach>

						</form:select>
					</div> --%>
					<!-- onchange ="getServiceList(this,'rtsService.html','getServiceList')" -->
				</div>

				<%-- <div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code='master.serviceName' text="service name" /><span
						class="mand">*</span></label>
					<div class="col-sm-4">
						<form:select path="nocBuildingPermissionDto.serviceId"
							class="form-control mandColorClass chosen-select-no-results"
							disabled="true" id="serviceId">

							<form:option value="" selected="true">Select Service Name</form:option>
							<c:forEach items="${command.serviceMstList}" var="ser">
								<form:option value="${ser.smServiceId }"
									code="${ser.smServiceId }">${ser.smServiceName }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div> --%>

				<div class="form-group">

					<label for="Contact_Number" class="col-sm-2 control-label "><spring:message
							code="NOCBuildingPermission.contactNumber" text="Contact Numer" /></label>
					<div class="col-sm-4">
						<form:input id="contactNumber"
							path="nocBuildingPermissionDto.contactNumber"
							cssClass="form-control hasNumber" maxLength="10"
							disabled="${command.saveMode eq 'V' ? true : false }"
							/>
					</div>


					<label class="col-sm-2 control-label" for=""><spring:message
							code="NOCBuildingPermission.applicantEmail" text="Applicant-Email-ID" /></label>
					<div class="col-sm-4">
						<form:input path="nocBuildingPermissionDto.applicantEmail"
							cssClass="form-control hasemailclass text-left"
							id="applicantEmailId" readonly="true" 
							disabled="${command.saveMode eq 'V' ? true : false }" />
					</div>

				</div>

				<h4>
					<spring:message text="Service Information " code="NOCBuildingPermission.serviceInformation" />
				</h4>


				<div class="form-group">
					<apptags:input labelCode="NOCBuildingPermission.referenceNo"
						cssClass="hasNumClass form-control"
						path="nocBuildingPermissionDto.refNo" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="40"></apptags:input>

					<label for="refDob" class="col-sm-2 control-label "><spring:message
							code="NOCBuildingPermission.referenceDate" text="Reference Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="nocBuildingPermissionDto.refDate"
								cssClass="form-control mandColorClass" fieldclass="datepicker" id="refDob"
								readonly="true" isMandatory="true" />
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<apptags:input
						labelCode="NOCBuildingPermission.buildingPermissionAppNo"
						cssClass="hasNumClass form-control"
						isDisabled="true"

						path="nocBuildingPermissionDto.apmApplicationId"
						isMandatory="true" maxlegnth="20"></apptags:input>

					<apptags:input labelCode="NOCBuildingPermission.fileNo"
						cssClass="hasNumClass form-control"
						path="nocBuildingPermissionDto.fNo" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="20"></apptags:input>
				</div>

				<div class="form-group">
					<apptags:input labelCode="NOCBuildingPermission.khasaraNo"
						cssClass="hasNumClass form-control"
						path="nocBuildingPermissionDto.surveyNo"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="20"></apptags:input>

					<apptags:input labelCode="NOCBuildingPermission.khataNo"
						cssClass="hasNumClass form-control"
						isDisabled="${command.saveMode eq 'V'}"
						path="nocBuildingPermissionDto.plotNo" maxlegnth="20"></apptags:input>
				</div>

				<div class="form-group">
					<apptags:input labelCode="NOCBuildingPermission.citiySurveyNo"
						cssClass="hasNumClass form-control"
						isDisabled="${command.saveMode eq 'V'}"
						path="nocBuildingPermissionDto.citiySurveyNo" maxlegnth="100"></apptags:input>

					<label class="col-sm-2 control-label " for="location"><spring:message
							code="NOCBuildingPermission.location" text="location" /></label>
					<div class="col-sm-4">
						<form:select path="nocBuildingPermissionDto.location"
							disabled="${command.saveMode eq 'V' ? true : false }"
							cssClass="form-control chosen-select-no-results" id="location" data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${locations}" var="loc">
								<form:option value="${loc.locId}">${loc.locNameEng}-${loc.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">
					<apptags:input labelCode="NOCBuildingPermission.property_address"
						cssClass="hasNumClass form-control"
						isDisabled="${command.saveMode eq 'V'}"
						path="nocBuildingPermissionDto.address" maxlegnth="100"></apptags:input>

					<label for="plotArea"
						class="col-sm-2 control-label required-control"><spring:message
							code="NOCBuildingPermission.plotArea" text="Plot Area" /></label>
					<div class="col-sm-4">
						<form:input id="plotArea" path="nocBuildingPermissionDto.plotArea"
							cssClass="form-control text-left"
							disabled="${command.saveMode eq 'V' ? true : false }"
							onkeypress="return hasAmount(event, this, 10, 2)" />
					</div>
				</div>

				<div class="form-group">

					<label for="NOCBuildingPermission.coveredArea"
						class="col-sm-2 control-label "><spring:message code="NOCBuildingPermission.coveredArea"
							text="Covered Area" /></label>
					<div class="col-sm-4">
						<form:input id="" path="nocBuildingPermissionDto.builtUpArea"
							cssClass="form-control text-left" isMandatory="true"
							disabled="${command.saveMode eq 'V' ? true : false }"
							onkeypress="return hasAmount(event, this, 10, 2)" />
					</div>

					<label class="col-sm-2 control-label " for="NOC Type"><spring:message
							code="NOCBuildingPermission.nocType" text="NOC Type" /></label>
					<c:set var="baseLookupCode" value="NOC" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="nocBuildingPermissionDto.nocType"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select"
						disabled="${command.saveMode eq 'V' ? true : false }" />
				</div>

				<div class="form-group">
                               
						
                      <label class="col-sm-2 control-label required-control " for="USAGE Type"><spring:message
						code="NOCBuildingPermission.usageType" text="USAGE Type" /></label>
                          <c:set var="baseLookupCode" value="NUT" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="nocBuildingPermissionDto.usageType1" 
							cssClass="form-control chosen-select-no-results"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="applicantinfo.label.select"
							disabled="${command.saveMode eq 'V' ? true : false }" />
					</div>

				<div class="form-group">

					<apptags:input labelCode="NOCBuildingPermission.purchaserName"
						isDisabled="false"
						cssClass="hasNumClass mandColorClass form-control" isMandatory="true"
						path="nocBuildingPermissionDto.purchaserName" maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="NOCBuildingPermission.purchaserAddress"
						isDisabled="false"
						cssClass="hasNumClass  mandColorClass form-control" isMandatory="true"
						path="nocBuildingPermissionDto.purchaserAddress" maxlegnth="100"></apptags:input>

				</div>


				<div class="form-group">

					<apptags:input labelCode="NOCBuildingPermission.sellerName"
						isDisabled="false"
						cssClass="hasNumClass  mandColorClass form-control" isMandatory="true"
						path="nocBuildingPermissionDto.sellerName" maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="NOCBuildingPermission.sellerAddress" isMandatory="true"
						isDisabled="false"
						cssClass="hasNumClass  mandColorClass form-control" 
						path="nocBuildingPermissionDto.sellerAddress" maxlegnth="100"></apptags:input>

				</div>


				<div class="form-group">

					<apptags:input labelCode="NOCBuildingPermission.east"
						isDisabled="false"
						cssClass="hasNumClass  mandColorClass form-control"  isMandatory="true"
						path="nocBuildingPermissionDto.east" maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="NOCBuildingPermission.west"
						isDisabled="false"
						cssClass="hasNumClass  mandColorClass form-control"  isMandatory="true"
						path="nocBuildingPermissionDto.west" maxlegnth="100"></apptags:input>

				</div>


				<div class="form-group">


					<apptags:input labelCode="NOCBuildingPermission.north"
						isDisabled="false"
						cssClass="hasNumClass  mandColorClass form-control" isMandatory="true"
						path="nocBuildingPermissionDto.north" maxlegnth="100">
					</apptags:input>

					<apptags:input labelCode="NOCBuildingPermission.south"
						isDisabled="false"
						cssClass="hasNumClass  mandColorClass form-control" isMandatory="true"
						path="nocBuildingPermissionDto.south" maxlegnth="100"></apptags:input>

				</div>
				
				<div class="form-group">
					<apptags:input labelCode="NOCBuildingPermission.propertyDescription"
						cssClass="hasNumClass form-control" isMandatory="true"
						
						path="nocBuildingPermissionDto.proDesc" maxlegnth="1000"></apptags:input>

					<label for="plotArea"
						class="col-sm-2 control-label required-control"><spring:message
							code="NOCBuildingPermission.malabaCharges" text="MALABA CHARGE" /></label>
					<div class="col-sm-4">
						<form:input id="malabaCharge" path="nocBuildingPermissionDto.malabaCharge"
							cssClass="form-control text-left" maxlegnth="5"
							isMandatory="true"
							onkeypress="return hasAmount(event, this, 5, 2)" />
					</div>
				</div>
				
				
				
				<div class="form-group">
						<apptags:date fieldclass="datepicker"
							labelCode="NOCBuildingPermission.saleDeedDate"
							datePath="nocBuildingPermissionDto.saleDate" isMandatory="true"  
							  
							cssClass="custDate mandColorClass date">
						</apptags:date>

                 <apptags:textArea labelCode="NOCBuildingPermission.remark" isMandatory="true"
						path="nocBuildingPermissionDto.birthRegremark"
					
						cssClass="hasNumClass form-control" maxlegnth="100" />
				


						
					</div>
					
					<c:if test="${command.lastChecker}" >
					
					<div class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="NOCBuildingPermission.scrutinyDate" 
						datePath="nocBuildingPermissionDto.scrutinyDate" isMandatory="true"
						cssClass="custDate mandColorClass date">
					</apptags:date>
					</div></c:if>
		
				<c:if test="${not empty command.fetchDocumentList}">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class="" href="#deathRegCor-1"> <spring:message
										code="TbDeathregDTO.attacheddoc" text="Attached Documents" />
								</a>
							</h4>
						</div>


						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="scheme.document.name" text="" /></th>
										<th><spring:message code="scheme.view.document" text="" /></th>
									</tr>
									<c:forEach items="${command.fetchDocumentList}" var="lookUp">
										<tr>
											<td align="center">${lookUp.attFname}</td>
											<td align="center"><apptags:filedownload
													filename="${lookUp.attFname}" filePath="${lookUp.attPath}"
													dmsDocId="${lookUp.dmsDocId}" actionUrl="NOCForBuildingPermission.html?Download">
												</apptags:filedownload></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</c:if>

				<div class="form-group">
					<%-- <c:choose>
						<c:when test="${CheckFinalApp eq true}"> --%>
					<apptags:radio radioLabel="Approve,Reject"
						radioValue="APPROVED,REJECTED" isMandatory="true"
						labelCode="NOCBuildingPermission.status" path="nocBuildingPermissionDto.birthRegstatus"
						defaultCheckedValue="APPROVED">
					</apptags:radio>
					<%-- <br />
						</c:when>
						<c:otherwise>
							<apptags:radio radioLabel="Approve"  radioValue="APPROVED"
							
								isMandatory="true" labelCode="Status"
								path="nocBuildingPermissionDto.birthRegstatus"
								defaultCheckedValue="APPROVED">
							</apptags:radio>
							
							
							
							<br />
						</c:otherwise>
					</c:choose> --%>
					<%-- <apptags:textArea labelCode="Remark" isMandatory="true"
						path="nocBuildingPermissionDto.birthRegremark"
					
						cssClass="hasNumClass form-control" maxlegnth="100" />
				</div> --%>

				<%-- <c:if test="${CheckFinalApp eq true}"> --%>
				<div class="form-group" id="reload">
					<label class="col-sm-2 control-label" for="ExcelFileUpload"><spring:message
							code="NOCBuildingPermission.document" 
							text=" Upload Documents " /><i class="text-red-1"></i></label>
					<c:set var="count" value="0" scope="page"></c:set>
					<div class="col-sm-2 text-left">
						<apptags:formField fieldType="7"
							fieldPath="attachments[${count}].uploadedDocumentPath"
							currentCount="${count}" folderName="${count}"
							fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
							 maxFileCount="QUESTION_FILE_COUNT"
							validnFunction="ALL_UPLOAD_VALID_EXTENSION" cssClass="clear">
						</apptags:formField>
						
						</small>
						 -
						 <small class="text-blue-2">  <spring:message text=" (Upload Excel,pdf,.txt upto 5MB) "   code="NOCBuildingPermission.uploadsize"/>                        
						
						</small>
					</div>

				</div>
				<%-- </c:if> --%>

				<div class="text-center padding-top-10">

					<button type="button" class="btn btn-green-3" title="Submit"
						onclick="saveApprovalData(this)  ">
						<!-- <i class="fa padding-left-4" aria-hidden="true"></i> -->
						<spring:message code="NOCBuildingPermission.save" text="Save" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
