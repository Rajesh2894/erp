<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/additionalServices/nocforbuildingpermission.js"></script>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="NOC For Building Permission"  code="NOCBuildingPermission.nocforbuildingpermissions" />
					<%-- <apptags:helpDoc url="NOCForBuildingPermission.html"></apptags:helpDoc> --%>
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">

				<form:form action="NOCForBuildingPermission.html"
					id="frmnocbuildpermission" method="POST" commandName="command"
					class="form-horizontal form">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<h4>
						<spring:message text="Applicant Information " code="NOCBuildingPermission.applicationInformation" />
					</h4>
					<div class="form-group">
						<div class="col-sm-4"></div>
						<div class="col-sm-2"></div>
						<div class="form-group">

							<%-- <c:if test="${command.getGisValue() eq 'Y'}">
								<button type="button" class="btn btn-success btn-submit"
									onclick=" window.open('${command.gISUri}&uniqueid=${command.nocBuildingPermissionDto.apmApplicationId}')"
									id="">
									<spring:message text="Map NOC Building Permission on GIS map"
										code="map.noc.building.gis" />
								</button>
								<button class="btn btn-blue-2" type="button"
									onclick=" window.open('${command.gISUri}&id=${command.nocBuildingPermissionDto.apmApplicationId} ')"
									id="">
									<spring:message text="View NOC Building Permission on GIS map"
										code="view.noc.building.gis" />
								</button>
							</c:if> --%>
						</div>

						<label for="brDob" class="col-sm-2 control-label required-control"><spring:message
								code="NOCBuildingPermission.applicationDate" text="Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="nocBuildingPermissionDto.date"
									cssClass="form-control mandColorClass" id="brDob"
									readonly="true" />
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</div>

				
									<label class="col-sm-2 control-label required-control"
							for="applicantTitle"><spring:message
								code="NOCBuildingPermission.title" text="Text"  /></label>
							 </label>

						<div class="col-sm-4">
							<form:select path="nocBuildingPermissionDto.titleId" disabled="${command.saveMode eq 'V'}"
								class="form-control chosen-select-no-results" id="titleId">
								
								<c:set var="baseLookupCode" value="TTL" />
								<form:option value="0">
									<spring:message code="Select" text="Select" />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
								
							</form:select>
						</div>

					</div>

					<div class="form-group">
						<apptags:input labelCode="NOCBuildingPermission.fName"
							cssClass="mandColorClass hasNoSpace"
							isDisabled="${command.saveMode eq 'V'}"
							path="nocBuildingPermissionDto.fName" isMandatory="true"
							maxlegnth="100">
						</apptags:input>

						<apptags:input labelCode="NOCBuildingPermission.mName"
							cssClass="mandColorClass hasNoSpace"
							isDisabled="${command.saveMode eq 'V'}"
							path="nocBuildingPermissionDto.mName" maxlegnth="100"></apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="NOCBuildingPermission.lName"
							cssClass="mandColorClass hasNoSpace"
							isDisabled="${command.saveMode eq 'V'}"
							path="nocBuildingPermissionDto.lName" isMandatory="true"
							maxlegnth="100"></apptags:input>

						<label class="col-sm-2 control-label required-control"><spring:message
								code="NOCBuildingPermission.Sex" text="Gender" /></label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField items="${command.getLevelData('GEN')}"
							path="nocBuildingPermissionDto.sex"
							disabled="${command.saveMode eq 'V' ? true : false }"
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
							disabled="${command.saveMode eq 'V' ? true : false }"
							cssClass="form-control chosen-select-no-results"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="applicantinfo.label.select"
							isMandatory="true" />

						<apptags:input labelCode="NOCBuildingPermission.applicantAddress"
							cssClass="hasNumClass form-control" isMandatory="true"
							isDisabled="${command.saveMode eq 'V'}"
							path="nocBuildingPermissionDto.applicantAddress" maxlegnth="500"></apptags:input>


						<%-- <label class="control-label col-sm-2  required-control"
							for="text-1">Department Name </label>
						<div class="col-sm-4">
							<form:select path="nocBuildingPermissionDto.deptId"
								class="form-control mandColorClass chosen-select-no-results"
								label="Select Department Name" id="deptId"
								disabled="${command.saveMode eq 'V' ? true : false }"
								onchange="refreshServiceData(this,'NOCForBuildingPermission.html','refreshServiceData')">
								<!-- Here the option is being loaded in the drop down list using forEach loop  -->
								<form:option value="" selected="true">Select Department Name</form:option>
								<c:forEach items="${command.deptList}" var="dept">
									<form:option value="${dept.dpDeptid }"
										code="${dept.dpDeptcode }">${dept.dpDeptdesc }</form:option>
								</c:forEach>

							</form:select>
						</div> --%>

					</div>

					<%-- <div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code='master.serviceName' text="service name" /><span
							class="mand">*</span></label>
						<div class="col-sm-4"aaz
								disabled="${command.saveMode eq 'V' ? true : false }"
								class="form-control mandColorClass chosen-select-no-results"
								id="serviceId">

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
								code="NOCBuildingPermission.contactNumber" text="Contact Nubmer" /></label>
						<div class="col-sm-4">
							<form:input id="contactNumber"
								path="nocBuildingPermissionDto.contactNumber"
								cssClass="form-control hasNumber" maxLength="10"
								disabled="${command.saveMode eq 'V' ? true : false }"/>
								 
						</div>

						<label class="col-sm-2 control-label" for=""><spring:message
								code="NOCBuildingPermission.applicantEmail" text="Applicant-Email-ID" /></label>
						<div class="col-sm-4">
							<form:input id="applicantEmailId"
							 path="nocBuildingPermissionDto.applicantEmail"
								cssClass="form-control hasemailclass text-left"
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

						<label for="refDob" class="col-sm-2 control-label required-control "><spring:message
								code="NOCBuildingPermission.referenceDate" text="Reference Date" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="nocBuildingPermissionDto.refDate"
								
									cssClass="form-control mandColorClass" fieldclass="datepicker" id="refDob"
									 isMandatory="true" readonly="false"     disabled="${command.saveMode eq 'V' ? true : false }"></form:input>
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
							cssClass="hasNumClass form-control" isMandatory="true"
							path="nocBuildingPermissionDto.fNo" 
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
								cssClass="form-control chosen-select-no-results" id="location"
								data-rule-required="true">
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
							path="nocBuildingPermissionDto.address" maxlegnth="1000"></apptags:input>

						<label for="plotArea"
							class="col-sm-2 control-label required-control"><spring:message
								code="NOCBuildingPermission.plotArea" text="Plot Area" /></label>
						<div class="col-sm-4">
							<form:input id="plotArea"
								path="nocBuildingPermissionDto.plotArea"
								cssClass="form-control text-left"
								disabled="${command.saveMode eq 'V' ? true : false }"
								onkeypress="return hasAmount(event, this, 5, 2)" />
						</div>
					</div>

					<div class="form-group">

						<label for="NOCBuildingPermission.coveredArea"
							class="col-sm-2 control-label required-control "><spring:message code="NOCBuildingPermission.coveredArea"
								text="Covered Area" /></label>
						<div class="col-sm-4">
							<form:input id="builtUpArea" path="nocBuildingPermissionDto.builtUpArea"
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


                   <c:if test="${command.saveMode ne 'V'}">
				    <div class="form-group" >
						<label class="col-sm-2 control-label" for=""><spring:message
								code="accounts.vendormaster.uploadDocument" text=" Upload" /></label>

							<div class="col-sm-4">
								 <apptags:formField fieldType="7"
									fieldPath="" currentCount="0"
									showFileNameHTMLId="true" folderName="0"
									fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
									maxFileCount="QUESTION_FILE_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
								</apptags:formField> -
			 <small class="text-blue-2">  <spring:message text=" (Upload file upto 5MB) "   code="NOCBuildingPermission.uploadformat"/>                        
						
						</small>
								
							</div>
						</c:if>


	             <c:if test="${command.saveMode eq 'V'}">

						<div class="form-group">

							<apptags:input labelCode="NOCBuildingPermission.purchaserName"
								cssClass="hasNumClass  mandColorClass form-control"
								isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
								path="nocBuildingPermissionDto.purchaserName" maxlegnth="100">
							</apptags:input>

							<apptags:input labelCode="NOCBuildingPermission.purchaserAddress"
								cssClass="hasNumClass mandColorClass form-control"
								isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
								path="nocBuildingPermissionDto.purchaserAddress" maxlegnth="100"></apptags:input>
						</div>

						<div class="form-group">

							<apptags:input labelCode="NOCBuildingPermission.sellerName"
								cssClass="hasNumClass  mandColorClass form-control"
								isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
								path="nocBuildingPermissionDto.sellerName" maxlegnth="100">
							</apptags:input>

							<apptags:input labelCode="NOCBuildingPermission.sellerAddress"
								isMandatory="true"
								cssClass="hasNumClass  mandColorClass form-control"
								isDisabled="${command.saveMode eq 'V'}"
								path="nocBuildingPermissionDto.sellerAddress" maxlegnth="100"></apptags:input>

						</div>


						<div class="form-group">

							<apptags:input labelCode="NOCBuildingPermission.east"
								cssClass="hasNumClass  mandColorClass form-control"
								isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
								path="nocBuildingPermissionDto.east" maxlegnth="100">
							</apptags:input>

							<apptags:input labelCode="NOCBuildingPermission.west"
								cssClass="hasNumClass  mandColorClass form-control"
								isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
								path="nocBuildingPermissionDto.west" maxlegnth="100"></apptags:input>

						</div>

						<div class="form-group">

							<apptags:input labelCode="NOCBuildingPermission.north"
								cssClass="hasNumClass  mandColorClass form-control"
								isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
								path="nocBuildingPermissionDto.north" maxlegnth="100">
							</apptags:input>

							<apptags:input labelCode="NOCBuildingPermission.south"
								cssClass="hasNumClass   mandColorClass form-control"
								isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
								path="nocBuildingPermissionDto.south" maxlegnth="100"></apptags:input>

						</div>
						
						<div class="form-group">
					<apptags:input labelCode="NOCBuildingPermission.propertyDescription"
						cssClass="hasNumClass form-control" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}"
						path="nocBuildingPermissionDto.proDesc" maxlegnth="1000"></apptags:input>

					<label for="malabaCharges"
						class="col-sm-2 control-label required-control"><spring:message
							code="NOCBuildingPermission.malabaCharges" text="MALABA CHARGE"   /></label>
					<div class="col-sm-4">
						<form:input id="malabaCharge" path="nocBuildingPermissionDto.malabaCharge"
							cssClass="form-control text-left" maxlegnth="5" isMandatory="true"
							disabled="${command.saveMode eq 'V' ? true : false }" 
							onkeypress="return hasAmount(event, this, 5, 2)" 
							/>
							
					</div>
				</div>
				
				
				
				<div class="form-group">
						<apptags:date fieldclass="datepicker"
							labelCode="NOCBuildingPermission.saleDeedDate"
							isDisabled="${command.saveMode eq 'V'}"
							datePath="nocBuildingPermissionDto.saleDate" isMandatory="true"  
							
							cssClass="custDate mandColorClass date">
						</apptags:date>

                 <apptags:textArea labelCode="Remark" isMandatory="true"
						path="nocBuildingPermissionDto.birthRegremark"
					isDisabled="${command.saveMode eq 'V'}"
						cssClass="hasNumClass form-control" maxlegnth="100" />
				


						
					</div>
					
					
					
					<div class="form-group">
					<apptags:date fieldclass="datepicker"
						labelCode="NOCBuildingPermission.scrutinyDate" 
						isDisabled="${command.saveMode eq 'V'}"
						datePath="nocBuildingPermissionDto.scrutinyDate" isMandatory="true"
						cssClass="custDate mandColorClass date">
					</apptags:date>

					


				</div>
					
						
<%-- 						<h4>
							<spring:message code="opinion.document" text="Opinion Document" />
						</h4>
						<div class="table-responsive">
							<table class="table table-bordered table-striped"
								id="cfcattachdoc">
								<tr>
									<th><spring:message code="lgl.document.docName"
											text="Document Name" /></th>
									<th><spring:message code="uploaded.document"
											text="Uploaded Document " /></th>
								</tr>
								<c:forEach items="${command.attachDocsList}" var="lookUp">
									<tr>
										<td>${lookUp.attFname}</td>
										<td><apptags:filedownload filename="${lookUp.attFname}"
												filePath="${lookUp.attPath}"
												actionUrl="NOCForBuildingPermission.html?Download" /></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						
						
						<div class="form-group">

							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#deathRegCor-2"> <spring:message
											code="TbDeathregDTO.attacheddoc" text="Attached Documents" />
									</a>
								</h4>
							</div>
 --%>
                <c:if test="${not empty command.documentList }">
							<div class="col-sm-12 text-left">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="attachDocs">
										<tr>
											<th><spring:message code="scheme.document.name" text="" /></th>
											<th><spring:message code="scheme.view.document" text="" /></th>
										</tr>
										<c:forEach items="${command.documentList}" var="lookUp">
											<tr>
										        <td>${lookUp.attFname}</td>
										       <td><apptags:filedownload filename="${lookUp.attFname}"
												         filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}"
												     actionUrl="NOCForBuildingPermission.html?Download" /></td>
									</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</c:if>	
							
						</div>
						

					</c:if>	

					<div class="form-group">

						<c:if test="${command.getGisValue() eq 'Y'}">
							<button type="button" class="btn btn-success btn-submit"
								onclick=" window.open('${command.gISUri}&uniqueid=${command.nocBuildingPermissionDto.apmApplicationId}')"
								id="">
								<spring:message text="Map NOC Building Permission on GIS map"
									code="map.noc.building.gis" />
							</button>
							<button class="btn btn-blue-2" type="button"
								onclick=" window.open('${command.gISUri}&id=${command.nocBuildingPermissionDto.apmApplicationId} ')"
								id="">
								<spring:message text="View NOC Building Permission on GIS map"
									code="view.noc.building.gis" />
							</button>
						</c:if>
					</div>

					<%-- <c:if test="${command.saveMode ne 'V'}">
						<c:if test="${not empty command.checkList}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title table" id="">
										<a data-toggle="collapse" class="" href="#deathRegCor-1">
											<spring:message code="NOCBuildingPermission.document"
												text="Upload Documents" />
										</a>
									</h4>
								</div>
								<div class="panel-body">
									<fieldset class="fieldRound">
										<div class="overflow">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped"
													id="NOCTable">
													<tbody>
														<tr>
															<th><label class="tbold"><spring:message
																		code="label.checklist.srno" text="Sr No" /></label></th>
															<th><label class="tbold"><spring:message
																		code="label.checklist.docname"
																		text="Document Required" /></label></th>
															<th><label class="tbold"><spring:message
																		code="label.checklist.status" text="Status" /></label></th>
															<th><label class="tbold"><spring:message
																		code="label.checklist.upload" text="Upload" /></label></th>
														</tr>

														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">

															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																		<td><label>${lookUp.doc_DESC_ENGL}</label></td>
																	</c:when>
																	<c:otherwise>
																		<c:set var="docName" value="${lookUp.doc_DESC_Mar }" />
																		<td><label>${lookUp.doc_DESC_Mar}</label></td>
																	</c:otherwise>
																</c:choose>
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td>${lookUp.descriptionType}<spring:message
																			code="NOCBuildingPermission.mandatory" text="Mandatory" /></td>
																</c:if>


																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td>${lookUp.descriptionType}<spring:message
																			code="" text="Optional" /></td>
																</c:if>
																<td>

																	<div id="docs_${lk}">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_BND"
																			checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																			checkListDesc="${docName}" currentCount="${lk.index}" />
																	</div>

																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</fieldset>
								</div>
							</div>
						</c:if>
					</c:if>
					
 --%>					


					<div class="text-center margin-top-15">
						<c:if test="${command.saveMode ne 'V'}">
							<button type="button" class="btn btn-green-3" title="Proceed"
								onclick="saveData(this)">
								<spring:message code="NOCBuildingPermission.proceed" text="Save" />
								<i class="fa fa-arrow-right padding-left-5"
									aria-hidden="true"></i>
									
							</button>

							<button type="button" class="btn btn-warning resetSearch"
								onclick="openForm('NOCForBuildingPermission.html','add')">
								<spring:message code="account.bankmaster.reset" text="Reset" />
							</button>
						</c:if>
						<apptags:backButton url="NOCForBuildingPermission.html"></apptags:backButton>

					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
