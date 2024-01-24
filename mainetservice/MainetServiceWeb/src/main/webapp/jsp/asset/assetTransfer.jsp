<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetTransfer.js"></script>
<c:set var="assetFlag"
			value="${userSession.moduleDeptCode == 'AST' ? true : false}" />
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="asset.transfer.header" text="Asset Transfer" />
				</h2>
				<apptags:helpDoc url="AssetSearch.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="astTransfer" action="AssetTransfer.html"
					method="post" class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>						
					<form:hidden path="transDTO.assetCode" id="assetCodeSet"/>
					<form:hidden path="audit.empId" />
					<form:hidden path="audit.empIpMac" />
					<form:hidden path="transDTO.orgId" />
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
											code="asset.transfer.header" /></a>
								</h4>
							</div>
							
							<div id="Applicant" class="panel-collapse collapse in">
								<div class="panel-body">	
									<div class="form-group">
									<c:choose>
								
									  <c:when test="${command.clickFromNode}">
									  	<label class="col-sm-2 control-label" for="assetId"> <spring:message
												code="asset.transfer.assetId" text=" Asset Name/Code" /> <span><i
												class="text-red-1">*</i></span>
										</label>
										<div class="col-sm-4 ">
											<form:select path="transDTO.assetSrNo" id="assetSrNo"
												cssClass="form-control chosen-select-no-results"
												class="form-control mandColorClass" disabled="${(mode != null) && (mode eq 'APR')}"
												data-rule-required="true" onchange="getAssetData(this);">
												<form:option value=""><spring:message code="asset.info.select" text="Select" /></form:option>
												<c:forEach items="${command.lookUpList}" var="lookup">
													<form:option value="${lookup.lookUpId}">${lookup.lookUpType}</form:option>
												</c:forEach>
											</form:select>
										</div>
									  </c:when>
									  <c:otherwise>
									 
									    <label class="col-sm-2 control-label" for="assetId"> <spring:message
												code="asset.transfer.assetId" text=" Asset Name/Code" /> <span><i
												class="text-red-1">*</i></span>
										</label>
										<div class="col-sm-4 ">
											<form:select path="transDTO.assetSrNo" id="assetSrNo"
												cssClass="form-control chosen-select-no-results"
												class="form-control mandColorClass" disabled="${((mode != null) && (mode eq 'APR')) || command.completedFlag eq 'Y'}"
												data-rule-required="true" onchange="getAssetData(this);">
												<form:option value=""><spring:message code="asset.info.select" text="Select" /></form:option>
												<c:forEach items="${command.lookUpList}" var="lookup">
													<form:option value="${lookup.lookUpId}">${lookup.lookUpType}</form:option>
												</c:forEach>
											</form:select>
										</div>
									  </c:otherwise>
									</c:choose>
										<c:choose>
											<c:when test="${assetFlag}">
												<apptags:textArea labelCode="asset.transfer.description"
													isDisabled="${(mode != null) && (mode eq 'APR') || !clickFromNode}"
													path="transDTO.assetDesc" cssClass="alphaNumeric"></apptags:textArea>

											</c:when>
											<c:otherwise>

												<c:set var="baseLookupCodeACL"
													value="${userSession.moduleDeptCode == 'AST' ? 'ACL':'ICL'}" />
												<label class="col-sm-2 control-label " for="assetgroup">
													<spring:message code="asset.information.hardwareName" />
												</label>
												<apptags:lookupField
													items="${command.getLevelData(baseLookupCodeACL)}"
													path="transDTO.assetClass2" disabled="true"
													cssClass="form-control chosen-select-no-results"
													hasChildLookup="false" hasId="true" showAll="false"
													changeHandler="changeAssetTypeOrClass()"
													selectOptionLabelCode="Select" isMandatory="false" />
											</c:otherwise>

										</c:choose>

									</div>
									<div class="form-group">
										<label class="control-label col-sm-2" for=""> <spring:message
												code="asset.transfer.department" />
										</label>
										<div class="col-sm-4">
											<form:select path="transDTO.department" disabled="${(mode != null) && (mode eq 'APR') || !clickFromNode}"
												cssClass="form-control mandColorClass" id="dpDeptId">
												<form:option value="">
													<spring:message code='asset.info.select' text="Select" />
												</form:option>
												<c:if test="${userSession.languageId eq 1}">
													<c:forEach items="${command.departmentsList}"
														var="departments">
														<form:option value="${departments.dpDeptid }"
															code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
													</c:forEach>
												</c:if>
												<c:if test="${userSession.languageId eq 2}">
													<c:forEach items="${command.departmentsList}"
														var="departments">
														<form:option value="${departments.dpDeptid }"
															code="${departments.dpDeptcode }">${departments.dpNameMar }</form:option>
													</c:forEach>
												</c:if>
											</form:select>
										</div>

										<%-- <label class="col-sm-2 control-label" for="costcenter">
											<spring:message code="asset.transfer.costCenter" />
										</label>
										<div class="col-sm-4">
											<form:select path="transDTO.currentCostCenter" disabled="${(mode != null) && (mode eq 'APR') || !clickFromNode}"
												cssClass="form-control" id="transferCostCenterId">
												<form:option value="">
													<spring:message code='asset.info.select' text="Select" />
												</form:option>
												<c:if
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:forEach items="${command.accountHead}"
														var="costCenterList">
														<form:option value="${costCenterList.lookUpId }"
															code="${costCenterList.lookUpId}">${costCenterList.lookUpDesc}</form:option>
													</c:forEach>
												</c:if>
												<c:if
													test="${userSession.getCurrent().getLanguageId() ne 1}">
													<c:forEach items="${command.accountHead}"
														var="costCenterList">
														<form:option value="${costCenterList.lookUpId }"
															code="${costCenterList.lookUpId}">${costCenterList.descLangSecond}</form:option>
													</c:forEach>
												</c:if>

											</form:select>
										</div> --%>
									</div>
									<%-- 									<div class="form-group">
										<apptags:input labelCode="asset.transfer.employeeId"
											isDisabled="true" path="transDTO.currentEmployee"
											cssClass="form-control hasNumber"></apptags:input>
										<apptags:input labelCode="asset.transfer.custodian"
											isDisabled="true" path="transDTO.custodian"></apptags:input>
									</div> --%>
									<div class="form-group">
										<label class="control-label col-sm-2 " for=""> <spring:message
												code="asset.transfer.employeeId" />
										</label>
										<div class="col-sm-4">
											<form:select path="transDTO.currentEmployee" disabled="${(mode != null) && (mode eq 'APR') || !clickFromNode}"
												cssClass="form-control mandColorClass" id="employeeId">
												<form:option value="">
													<spring:message code='asset.info.select' text="Select" />
												</form:option>
												<c:forEach items="${command.empList}" var="employeeList">
													<form:option value="${employeeList.empId }" code="">${employeeList.empname }</form:option>
												</c:forEach>
											</form:select>
										</div>
										<apptags:input labelCode="asset.transfer.location" isDisabled="${(mode != null) && (mode eq 'APR') || !clickFromNode}"
											path="transDTO.currentLocationDesc" 
											cssClass="form-control hasCharacter"></apptags:input>
											
											
											
									</div>
									
									
									
									
									
									
									<%-- <div class="form-group">
										<apptags:input labelCode="asset.transfer.location"
											isDisabled="true" path="transDTO.currentLocationDesc"
											cssClass="form-control hasCharacter"></apptags:input>
									</div> --%>
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label" for=""><spring:message
												code="asset.transfer.docDate" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													id="transferDocDate" path="transDTO.docDate"
													isMandatory="false"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
										<label class="col-sm-2 control-label" for=""><spring:message
												code="asset.transfer.postDate" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													id="transferPostDate" path="transDTO.postDate"
													isMandatory="false"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div> --%>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="typeOfTransfer"><spring:message
												code="asset.transfer.transferType" /></label>
										<div class="col-sm-5">
											<label class="radio-inline"> <form:radiobutton
													path="transDTO.transferType" value="trans-emp"
													class="radio_button" data-ptag="trans-emp"
													disabled="${(mode != null) && (mode eq 'APR')}" /> <spring:message
													code="asset.transfer.toemployee" />
											<%-- </label> <label class="radio-inline"> <form:radiobutton
													path="transDTO.transferType" value="trans-loc"
													class="radio_button" data-ptag="trans-loc"
													disabled="${(mode != null) && (mode eq 'APR')}" /> <spring:message
													code="asset.transfer.tolocation" />
											</label> --%>
											<%-- <label class="radio-inline"> <form:radiobutton
													path="transDTO.transferType" value="trans-cost"
													class="radio_button" data-ptag="trans-cost"
													label="Transfer to CostCenter" />
											</label> --%>
											<%-- <label class="radio-inline"> <form:radiobutton
													path="transDTO.transferType" value="trans-dept"
													class="radio_button" data-ptag="trans-dept"
													disabled="${(mode != null) && (mode eq 'APR')}" /> <spring:message
													code="asset.transfer.todepartment" />
											</label> --%>
										</div>
									</div>
									<div class="form-group">

										<div id="trans-emp" class="p_element">
											<label class="control-label col-sm-2 required-control" for="">
												<spring:message code="asset.transfer.new.Employee" />
											</label>
											<div class="col-sm-4">
												<form:select path="transDTO.transferEmployee"
													disabled="${(mode != null) && (mode eq 'APR') || (command.completedFlag eq 'Y')}" 
													cssClass="form-control mandColorClass chosen-select-no-results"
													id="transEmpId" onchange="showempDesignation()">
													<form:option value="">
														<spring:message code='asset.info.select' text="Select" />
													</form:option>
													<c:forEach items="${command.empList}" var="employeeList">
														<form:option value="${employeeList.empId }" code="">${employeeList.empname }</form:option>
													</c:forEach>
												</form:select>
											</div>
											<label class="control-label col-sm-2 required-control" for="">
												<spring:message code="asset.transfer.designation" />
											</label>
											<div class="col-sm-4">
												<form:input path="transDTO.empDesignation"
													id="empdesignationId" class="form-control" readonly="true"></form:input>
											</div>
										</div>
										<div id="trans-loc" class="p_element">
											<label class="control-label col-sm-2 required-control" for="">
												<spring:message code="asset.transfer.new.Location" />
											</label>
											<div class="col-sm-4">
												<form:select path="transDTO.transferLocation"
													disabled="${(mode != null) && (mode eq 'APR')}"
													cssClass="form-control mandColorClass chosen-select-no-results"
													id="transLocId">
													<form:option value="">
														<spring:message code='asset.info.select' text="Select" />
													</form:option>
													<c:if test="${userSession.languageId eq 1}">
														<c:forEach items="${command.locList}" var="locationList">
															<form:option value="${locationList.locId }" code="">${locationList.locNameEng }</form:option>
														</c:forEach>
													</c:if>
													<c:if test="${userSession.languageId eq 2}">
														<c:forEach items="${command.locList}" var="locationList">
															<form:option value="${locationList.locId }" code="">${locationList.locNameReg }</form:option>
														</c:forEach>
													</c:if>
												</form:select>
											</div>
										</div>

										<div id="trans-dept" class="p_element">

											<label class="col-sm-2 control-label required-control"
												for="transDpDeptId"> <spring:message
													code="asset.transfer.new.department" />
											</label>
											<%-- <div class="col-sm-4">
												<form:select path="transDTO.transferCostCenter"
													disabled="false" cssClass="form-control mandColorClass chosen-select-no-results"
													id="newTransferCostCenterId">
													<form:option value="">
														<spring:message code='' text="Select" />
													</form:option>
													<c:forEach items="${command.accountHead}"
														var="costCenterList">
														<form:option value="${costCenterList.lookUpId }"
															code="${costCenterList.lookUpId}">${costCenterList.lookUpDesc}</form:option>
													</c:forEach>
												</form:select>
											</div> --%>
											<div class="col-sm-4">
												<form:select path="transDTO.transferDepartment"
													disabled="${(mode != null) && (mode eq 'APR')}"
													cssClass="form-control mandColorClass chosen-select-no-results" id="transDpDeptId">
													<form:option value="">
														<spring:message code='asset.info.select' text="Select" />
													</form:option>
													<c:if
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<c:forEach items="${command.departmentsList}"
															var="departments">
															<form:option value="${departments.dpDeptid }"
																code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
														</c:forEach>
													</c:if>
													<c:if
														test="${userSession.getCurrent().getLanguageId() ne 1}">
														<c:forEach items="${command.departmentsList}"
															var="departments">
															<form:option value="${departments.dpDeptid }"
																code="${departments.dpDeptcode }">${departments.dpNameMar }</form:option>
														</c:forEach>
													</c:if>
												</form:select>
											</div>
										</div>

									</div>
									<div class="form-group">
										<%-- <apptags:textArea labelCode="asset.transfer.remark"
											maxlegnth="100" path="transDTO.remark" isMandatory="false"
											cssClass="alphaNumeric"
				
											isDisabled="${(mode != null) && (mode eq 'APR')}"></apptags:textArea>
										--%>

										<label class="col-sm-2 control-label " for="remark"><spring:message
												code="asset.transfer.remark" text="Remark" /></label>
										<div class="col-sm-4">
											<form:textarea name="" cols="" rows="" class="form-control"
												id="remark" path="transDTO.remark" maxlength="100"
												onkeyup="countChar(this,100,'remk')"
												onfocus="countChar(this,100,'remk')"
												disabled="${(mode != null) && (mode eq 'APR') || (command.completedFlag eq 'Y')}"></form:textarea>
												<c:if test="${(mode == null) && (mode ne 'APR') }">
											<div class="pull-right">
												<spring:message code="charcter.remain"
													text="characters remaining " />
												<span id="remk">100</span>
											</div>
											</c:if>
										</div>


										<%-- <c:if test="${assetFlag && (command.clickFromNode ||  mode eq 'APR')}"> --%>
											<c:if test="${assetFlag}">
											<c:choose>
											<c:when test="${ mode eq 'APR'}">
											<label for="" class="col-sm-2 control-label"> <spring:message
											code="asset.transfer.download.document" text="Download Document" /></label>
											</c:when>
											<c:otherwise>
											<label for="" class="col-sm-2 control-label"> <spring:message
											code="asset.transfer.upload.documet" text="Upload Document" /></label>
											</c:otherwise>
											</c:choose>
											
									<c:set var="count" value="0" scope="page"></c:set>
										<div class="col-sm-4">
											<c:if test="${ mode ne 'APR'}">
												<apptags:formField fieldType="7"
													fieldPath="attachments[${count}].uploadedDocumentPath"
													currentCount="${count}" folderName="${count}"
													isDisabled="${ mode eq 'APR'}"
													fileSize="CHECK_COMMOM_MAX_SIZE" showFileNameHTMLId="true"
													isMandatory="true" maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
													cssClass="clear">
												</apptags:formField>
											</c:if>
											<c:if
												test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
												<input type="hidden" name="deleteFileId"
													value="${command.attachDocsList[0].attId}">
												
												<apptags:filedownload
													filename="${command.attachDocsList[0].attFname}"
													filePath="${command.attachDocsList[0].attPath}"
													actionUrl="AssetTransfer.html?Download"></apptags:filedownload>
											</c:if>
											<c:if
												test="${mode eq 'APR' && (command.attachDocsList eq null  ||   empty command.attachDocsList )}">
											<small class="text-blue-2"><spring:message code="asset.transfer.noDoc" text="No Documents Attached" /> </small>
											</c:if>
										</div>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</div>
					<c:if test='${(mode == null) && (command.completedFlag eq "N") }'>
						<div class="text-center">
							<button type="button" class="button-input btn btn-success" title='<spring:message code="asset.transfer.save" />'
								name="button" value="Save" onclick="saveTransfer(this)"
								id="save">
								<spring:message code="asset.transfer.save" />
							</button>
							<c:choose>
							  <c:when test="${command.clickFromNode}">
									<button type="button" class="btn btn-warning" title='<spring:message code="back.msg" text="Back" />'
										onclick="window.location.href='${userSession.moduleDeptCode == 'AST' ? 'AssetTransfer.html':'ITAssetTransfer.html'}'">
										<spring:message code="reset.msg" text="Reset" />
									</button>
									<button type="button" class="btn btn-danger" id="back" title='<spring:message code="back.msg" text="Back" />'
										onclick="window.location.href='AdminHome.html'">
										<spring:message code="back.msg" text="Back" />
									</button>
								</c:when>
							  <c:otherwise>
									<button type="Reset" class="btn btn-warning" title='<spring:message code="reset.msg" text="Reset" />'
										onclick="resetTransfer()">
										<spring:message code="reset.msg" text="Reset" />
									</button>
									<apptags:backButton url="AssetSearch.html"></apptags:backButton>	
									
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</form:form>
			</div>
			<c:if test='${(mode != null) && (mode eq "APR")}'>
				<form:form action="AssetTransfer.html" id="assetTransferApproval"
					method="post" class="form-horizontal">
					<div class="widget-content padding panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>
					</div>
					<div class="text-center widget-content padding">
						<button type="button" id="save" class="btn btn-success btn-submit" title='<spring:message code="asset.transfer.save" text="Save" />'
							onclick="saveTransferAction(this);">
							<spring:message code="asset.transfer.save" text="Save" />
						</button>
						
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="asset.information.back" text="Back" />'
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<spring:message code="asset.information.back" text="Back" />
						</button>
					</div>
				</form:form>
				</c:if>
				
				<c:if test='${(command.completedFlag eq "Y")}'>
				<div class="text-center widget-content padding">
				<button type="button" class="button-input btn btn-danger" title='<spring:message code="asset.information.back" text="Back" />'
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<spring:message code="asset.information.back" text="Back" />
						</button>
						</div>
				</c:if>
				<!-- Defect #3086 Resolution-->
				<%-- 				<div class="form-group">
					<form:form action="AssetTransfer.html" id="assetTransferApproval"
						method="post">
						<!-- It gets enable only when approval status is pending -->
						<apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>

						<button type="button" id="save" class="btn btn-success"
							onclick="saveTransferAction(this);">
							<spring:message code="master.save" text="" />
						</button>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<spring:message code="scheme.master.back" text="" />
						</button>
					</form:form>
				</div> --%>
			<%-- </c:if> --%>
		</div>
	</div>
</div>

<script type="text/javascript">

$('.radio_button').change(function() {
	manipulateDropdowns();
});

//D#38594 change dropdown value
function manipulateDropdowns(){
	var departmentList = [],locationList=[],employeeList=[];
	let transferType = $('input[name="transDTO.transferType"]:checked').val();
	
	if(transferType == 'trans-emp' && transferType != undefined){
		<c:forEach var="employee" items="${command.empList}" varStatus="i">
		employeeList.push({
			id:'${employee.empId}',
			code:'',
			desc:'${employee.empname}'
		});
	</c:forEach>
	
	//remove selected employee from employeeList
	 let empIndexNo = employeeList.findIndex(function(emp){
		    return emp.id == $('#employeeId').val();
		});
	
	 empIndexNo !=-1 ? employeeList.splice(empIndexNo,1):'';
	// appendDropdown('transEmpId',employeeList);
	}else if(transferType == 'trans-loc' && transferType != undefined){
		<c:forEach var="location" items="${command.locList}" varStatus="i">
			locationList.push({
				id:'${location.locId}',
				code:'',
				desc:'${userSession.getCurrent().getLanguageId()}' == 1 ? '${location.locNameEng}' : '${location.locNameReg}'
		});
		</c:forEach>
		
		 let locIndexNo = locationList.findIndex(function(loc){
			    return loc.desc == $('#currentLocationDesc').val();
			});
		
		 locIndexNo !=-1 ? locationList.splice(locIndexNo,1):'';
		 appendDropdown('transLocId',locationList);
		
	}else if(transferType == 'trans-dept' && transferType != undefined){
		<c:forEach var="dept" items="${command.departmentsList}" varStatus="i">
			departmentList.push({
				id:'${dept.dpDeptid}',
				code:'${dept.dpDeptcode}',
				desc:'${userSession.getCurrent().getLanguageId()}' == 1 ? '${dept.dpDeptdesc}' : '${dept.dpNameMar}'
			});
		</c:forEach>
		
		//remove selected department from departmentList
		 let deptIndexNo = departmentList.findIndex(function(dept){
			    return dept.id == $('#dpDeptId').val();
			});
		 deptIndexNo !=-1 ? departmentList.splice(deptIndexNo,1):'';
		 appendDropdown('transDpDeptId',departmentList);
	}
}

//function for append options into dropdowns
function appendDropdown(id,list){
	debugger;
	$('#'+id+'').html('');
	$('#'+id+'').append($("<option></option>").attr("value","").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	$.each(list, function(index, value) {
		 $('#'+id+'').append($("<option></option>").attr("value",value.id).attr("code",value.code).text(value.desc));
	});
	$('#'+id+'').trigger('chosen:updated');	
}

</script>