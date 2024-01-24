<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/water/newWaterConnectionForm.js"></script>
<script>
$(document).ready(function() {

	/* if($('#hiddenConsumerSame').val()=="Y")
	{
	$("#isConsumer").attr("checked",true);
	}
	else
		{
		$("#isConsumer").attr("checked",false);
		} */

	 
 	 /* var chkConsumer = $('#isConsumer').is(':checked');
	 if(chkConsumer)
		 {
	
		 $("#hideConsumerDetails").hide();
		 $("#cbillingAreaName").data('rule-required',false);
			$("#cbillingPinCode").data('rule-required',false);

			$("#csName").data('rule-required',false);
			$("#csLname").data('rule-required',false);
			$("#csTitle").data('rule-required',false);
			
		 }
	 else
		 {
		
		 $("#hideConsumerDetails").show();
		 $("#cbillingAreaName").data('rule-required',true);
		 $("#cbillingPinCode").data('rule-required',true);

			$("#csName").data('rule-required',true);
			$("#csLname").data('rule-required',true);
			$("#csTitle").data('rule-required',true);
		 } */
	 

	
	if($('#hiddenBillingSame').val()=="Y")
	{
	$("#billing").attr("checked",true);
	}
	else
		{
		$("#billing").attr("checked",false);
		}
		
	
		
	var isWithoutProprty = $('#isWithoutPropId').val();
		if('Y' == isWithoutProprty) {
			$('#propertyParentId').hide();
			$('#ownerParentId').hide();
		}
		
	var connectionType = $('#connectionType').val();
	if('T' == connectionType) {
		$("#fromtoperiod").show();
		$("#numberofday").show();
		$("#fromdate").data('rule-required', true);
		$("#todate").data('rule-required', true);
	}
		
	var chkBilling = $('#billing').is(':checked');
	 var checkHiddenBilling = $('#hiddenBillingSame').val() == 'Y';
	 if(chkBilling || checkHiddenBilling)
				 {
			
				/*  $("#hideBillingDetails").hide(); */
				 $("#billingAreaName").data('rule-required',false);
					$("#billingPinCode").data('rule-required',false);
					
					$('#hiddenBillingSame').val("Y");
					
					$('#cShouseNoBilling').val($('#cShouseNo').val());
					$('#billingAreaName').val($('#csAddress1').val());
					$('#billingPinCode').val($('#csPinCode').val());
					$('#cSLandmarkBilling').val($('#cSLandmark').val());
					$('#csMobileNoBilling').val($('#csMobileNo').val());
					$('#csEmailIdBilling').val($('#csEmailId').val()); 
					$('#districtBilling').val($('#csDistrict').val()); 
					$('#coBDwzid1').val($('#codDwzid1').val()); 
					
					$('#cShouseNoBilling').attr("disabled", true);
					$('#billingAreaName').attr("disabled", true);
					$('#billingPinCode').attr("disabled", true);
					$('#cSLandmarkBilling').attr("disabled", true);
					$('#csMobileNoBilling').attr("disabled", true);
					$('#csEmailIdBilling').attr("disabled", true);
					$('#districtBilling').attr("disabled", true); 
					$('#coBDwzid1').attr("disabled", true);
					
					
					/* $("#billingAreaName").data('rule-required', false);
					$("#billingPinCode").data('rule-required', false); */
	
			
		 }
	 else
		 {
		 
		/*  $('#hiddenBillingSame').val("N")
			//$("#hideBillingDetails").show();
			
			$('#cShouseNoBilling').val('');
			$('#billingAreaName').val('');
			$('#billingPinCode').val('');
			$('#cSLandmarkBilling').val('');
			$('#csMobileNoBilling').val('');
			$('#csEmailIdBilling').val('');
			
			$('#cShouseNoBilling').attr("disabled", false);
			$('#billingAreaName').attr("disabled", false);
			$('#billingPinCode').attr("disabled", false);
			$('#cSLandmarkBilling').attr("disabled", false);
			$('#csMobileNoBilling').attr("disabled", false);
			$('#csEmailIdBilling').attr("disabled", false); */
			
			/* $("#billingAreaName").data('rule-required', true);
			$("#billingPinCode").data('rule-required', true); */
		
		/*  $("#hideBillingDetails").show(); */
		 $("#billingAreaName").data('rule-required',true);
		 $("#billingPinCode").data('rule-required',true);
		 }
					var cnt = $('#tbl1 tr').length - 1;
					var cntConnection = $('#tbl2 tr').length-1;
                    $("#addOwner").click(function(){
						var countOwner=cnt-1;
			            if($('#ownerTitle'+countOwner).val()!='0' && $('#ownerFName'+countOwner).val()!='' && $('#ownerLName'+countOwner).val()!='' && $('#ownerGender'+countOwner).val()!='0')
			                {
										var row = '<td>'
												+ '<c:set var="baseLookupCode" value="TTL" />'
												+ '<select name="csmrInfo.ownerList['+cnt+'].ownerTitle" id="ownerTitle'+cnt+'" class="form-control" data-rule-required="true">'
												+ '<option value="">'
												+ "Select Title"
												+ '</option>'
												+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
												+ '	<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
												+ '</c:forEach>'
												+ '</select>'
												+ '</td>	'
												+ '<td><input type="text" class="form-control" name="csmrInfo.ownerList['+cnt+'].ownerFirstName" id="ownerFName'+cnt+'" data-rule-required="true"></input></td>'
												+ '<td><input  type="text" class="form-control" name="csmrInfo.ownerList['+cnt+'].ownerMiddleName" id="ownerMName'+cnt+'"></input></td>'
												+ '<td><input  type="text" class="form-control" name="csmrInfo.ownerList['+cnt+'].ownerLastName" id="ownerLName'+cnt+'" data-rule-required="true"></input></td>'
												+'<td>'
												+ '<c:set var="baseLookupCode" value="GEN" />'
												+ '<select name="csmrInfo.ownerList['+cnt+'].gender" id="ownerGender'+cnt+'" class="form-control" data-rule-required="true">'
												+ '<option value="">'
												+ "Select Title"
												+ '</option>'
												+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
												+ '	<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
												+ '</c:forEach>'
												+ '</select>'
												+ '</td>'
												+ '<td><input  type="text" class="form-control hasNumber" name="csmrInfo.ownerList['+cnt+'].caoUID" id="ownerUID'+cnt+'"></input></td>';
										$('#tbl1 tr').last().after('<tr id="tr'+cnt+'" class="ownerClass">'+ row
												+ '<td><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger btn-sm" data-original-title="Delete Owner" id="deleteOwner" onclick="removeRow('
												+ cnt+ ')"><i class="fa fa-trash"></i></a></td></tr>');
							                   cnt++;
							                   reorderOwner();
                                     }
                               else
	                            {
	                          showErrormsgboxTitle("Please Enter the All Mandatory fields");
	                            }
                               });

					
				
					$("#addConnection").click(function(){
						 var checked = $('#ExistingConnection').is(':checked');
						 if(checked){
							 var count=cntConnection-1;
								if($('#consumerNo'+count).val()!='')
							{
							 var row= 
								    '<td><input type="text" class="form-control" name="csmrInfo.linkDetails['+cntConnection+'].lcOldccn" onblur="checkDuplicateCcn('+cntConnection+')" id="consumerNo'+cntConnection+'"></input></td>'+
								    '<td>  <c:set var="baseLookupCode" value="CSZ" />'
									+ '<select name="csmrInfo.linkDetails['+cntConnection+'].lcOldccnsize" id="noOfTaps'+cntConnection+'" class="form-control">'
									+ '<option value="0">'
									+ "Select Connection Size"
									+ '</option>'
									+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
									+ '		<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
									+ '</c:forEach>'
									+ '</select></td>'+
								   '<td><input  type="text" class="form-control" name="csmrInfo.linkDetails['+cntConnection+'].ccnOutStandingAmt"  id="noOfTaps'+cntConnection+'"></input></td>';
							  $('#tbl2 tr').last().after('<tr id="row'+cntConnection+'" class="appendableClass">'+row+'<td><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger btn-sm" data-original-title="Delete Connection" id="deleteConnection" onclick="removeConnection('+cntConnection+')"><i class="fa fa-trash"></i></a></td></tr>');
								cntConnection++;
								reorderConnection();
                          }
							else
								{
								showErrormsgboxTitle("Old Connection No. Should not be empty before adding new Row");
								}
							
						}
					});
					
					
					$('#csCcncategory1').change(
							function() {
								var conCatCode1 = $('#csCcncategory1').find(':selected').get(0).attributes[0];
								$('#connectionType').val(connectionType);
								if('WP' == conCatCode1.value || 'T' == conCatCode1.value) {
									$('#propertyParentId').hide();
									$('#ownerParentId').hide();
									$('#isWithoutPropId').val('Y');

								}
								else {
									$('#propertyParentId').show();
									$('#ownerParentId').show();
									$('#isWithoutPropId').val('N');
								}
					});
					 
					
				});


</script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="water.WaterConnection" />
				</h2>
				<apptags:helpDoc url="NewWaterConnectionForm.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="water.fieldwith" /> <i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" />
					</span>
				</div>
				<form:form action="NewWaterConnectionForm.html"
					class="form-horizontal form" name="frmNewWaterForm"
					id="frmNewWaterForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="isBillingSame" id="hiddenBillingSame" />
					<%-- <form:hidden path="isConsumerSame" id="hiddenConsumerSame" /> --%>
					<form:hidden path="" id="propOutStanding"
						value="${command.propOutStanding}" />
					<form:hidden path="" id="propNoOptionalFlag"
						value="${command.propNoOptionalFlag}" />
					<form:hidden path="isWithoutProp" id="isWithoutPropId" />
					<form:hidden path="csmrInfo.typeOfApplication" id="connectionType" />
					<form:hidden path="" id="sudaEnv" value="${command.sudaEnv}" />


					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;"></div>
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<%-- <apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail> --%>


						<%-- <div class="panel panel-default">
							<div class="panel-heading">
								<h4 id="consumerDiv" class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#waterconsumerdetails"> <spring:message
											code="water.consumer.details" />
									</a>
								</h4>
							</div>
							<div id="waterconsumerdetails" class="panel-collapse collapse">
								<div class="form-group padding-top-10">
									<div class="col-sm-5">
										<label class="checkbox-inline"> <form:checkbox
												path="reqDTO.existingConsumerNumber"
												value="ExistingConnection" id="ExistingConnection" /> <spring:message
												code="water.existing.consumer" />

										</label>
									</div>
									<div class="checkbox col-sm-3">
										<label class="margin-left-30"> <form:checkbox
												path="reqDTO.existingPropertyNo" value="ExistingProperty"
												id="ExistingProperty" /> <spring:message
												code="water.existing.property" />
										</label>
									</div>
									<div class="col-sm-2">
										<form:input name="" type="text" class="form-control"
											path="reqDTO.propertyNo" id="propertyNo"
											placeholder="Property Number"></form:input>
									</div>
									<div class="col-sm-2">
										<form:input class="form-control" readonly="true"
											path="reqDTO.propertyOutStanding" id="propertyOutstanding"
											placeholder="Outstanding Amount"></form:input>
									</div>

								</div>
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-bordered" id="tbl2">
											<tr>
												<th><spring:message code="water.ConnectionNo" /> <span
													class="mand">*</span></th>
												<th><spring:message code="water.ConnectionSize" /></th>
												<th><spring:message code="water.NoofTaps" /></th>
												<th><a data-toggle="tooltip" data-placement="top"
													title="" class="btn btn-blue-2 btn-sm"
													data-original-title="<spring:message code="water.add"/>"
													id="addConnection"><i class="fa fa-plus"></i></a></th>
											</tr>
											<c:choose>
												<c:when test="${empty command.csmrInfo.linkDetails}">
													<tr id="row0" class="appendableClass">
														<td><form:input type="text" class="form-control"
																path="csmrInfo.linkDetails[0].lcOldccn" id="consumerNo0"></form:input></td>
														<td>
															<form:input type="text" class="form-control"
											path="csmrInfo.csCcnsize" id="connectionSize"></form:input>
															<c:set var="baseLookupCode" value="CSZ" /> <form:select
																path="csmrInfo.linkDetails[0].lcOldccnsize"
																class="form-control" id="connectionSize0">
																<form:option value=" ">
																	<spring:message code="water.sel.connectionsize" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select>
														</td>
														<td><form:input type="text"
																class="form-control hasNumber"
																path="csmrInfo.linkDetails[0].lcOldtaps" id="noOfTaps0"></form:input></td>
														<td><a data-toggle="tooltip" data-placement="top"
															title="" class="btn btn-danger btn-sm"
															data-original-title="<spring:message code="water.delete"/>"
															id=deleteConnection onclick="removeConnection(0)"><i
																class="fa fa-trash"></i></a></td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${command.csmrInfo.linkDetails}"
														var="details" varStatus="status">
														<tr id="row${status.count-1}" class="appendableClass">
															<td><form:input type="text" class="form-control"
																	path="csmrInfo.linkDetails[${status.count-1}].lcOldccn"
																	id="consumerNo${status.count-1}"></form:input></td>
															<td><c:set var="baseLookupCode" value="CSZ" /> <form:select
																	path="csmrInfo.linkDetails[${status.count-1}].lcOldccnsize"
																	class="form-control"
																	id="connectionSize${status.count-1}">
																	<form:option value=" ">
																		<spring:message code="water.sel.connectionsize" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
															<td><form:input type="text"
																	class="form-control hasNumber"
																	path="csmrInfo.linkDetails[${status.count-1}].lcOldtaps"
																	id="noOfTaps${status.count-1}"></form:input></td>
															<td><a data-toggle="tooltip" data-placement="top"
																title="" class="btn btn-danger btn-sm"
																data-original-title="<spring:message code="water.delete"/>"
																id="deleteConnection"
																onclick="removeConnection(${status.count-1})"><i
																	class="fa fa-trash"></i></a></td>
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</table>
									</div>
								</div>
							</div>
						</div> --%>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#ConsumerDetails"> <spring:message
											code="water.owner.details.consumerAddress"
											text="Consumer Details" />
									</a>
								</h4>
							</div>
							<div id="ConsumerDetails" class="panel-collapse">
								<div class="panel-body">
									<div class="form-group">

										<div class="col-sm-6">
											<%-- <label class="checkbox-inline" for="isConsumer"> <form:checkbox
													path="reqDTO.isConsumer" value="Y" id="isConsumer" /> <spring:message
													code="water.isConsumerSame"
													text="Is Applicant is Consumer?" />
											</label> --%>
										</div>
									</div>
							<div id="hideConsumerDetails">
										<div class="form-group">
											<c:if test="${command.sudaEnv eq 'Y'}">
												<label class="col-sm-2 control-label required-control">
													<spring:message code="water.connectiontype"
														text="Connection Type" />
												</label>
												<div class="col-sm-4">
													<form:select path="csmrInfo.bplFlag"
														class="form-control changeParameterClass" id="bplNo"
														data-rule-required="true" onchange="getConnectionSize();">
														<form:option value="N" selected="selected">
															<spring:message code="water.connection.type.normal" text="Normal" />
														</form:option>
														<form:option value="Y">
															<spring:message code="water.connection.type.bhagirathi" text="Bhagirathi Connection" />
														</form:option>
													</form:select>
												</div>
											  </c:if>
											  <c:if test="${command.sudaEnv ne 'Y'}">
											  	<label class="col-sm-2 control-label required-control">
													<spring:message code="water.dataentry.bhagirathi.connection"
														text="Is Bhagirathi connection" />
												</label>
												<div class="col-sm-4">
												  <form:select path="csmrInfo.bplFlag"
														class="form-control changeParameterClass" id="bplNo"
														data-rule-required="true" onchange="getConnectionSize();">
														<form:option value="0">
															<spring:message code="water.dataentry.select" text="Select" />
														</form:option>
														<form:option value="Y">
															<spring:message code="" text="Yes" />
														</form:option>
														<form:option value="N" selected="selected">
															<spring:message code="" text="No" />
														</form:option>
													</form:select>
												</div>	
											</c:if>	
											
											<div id="bpldiv">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="applicantinfo.label.bplno" /></label>
											<div class="col-sm-4">

												<form:input name="" type="text"
													class="form-control required-control" path="csmrInfo.bplNo"
													id="bplNumber" maxlength="16" />

												</div>
											</div>
										
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="firstName"><spring:message
													code="water.dataentry.consumer.name" text="Consumer Name" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasSpecialChara" readonly="${disabled}"
													path="csmrInfo.csName" id="csFirstName"
													data-rule-required="true"></form:input>
											</div>
											
											<label class="col-sm-2 control-label required-control" for="cSfatherGuardian"><spring:message
														code="water.dataentry.fatherName" text="Father/Guardian Name" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control"
													path="csmrInfo.fatherName" id="cSFatherGuardianName"
													></form:input>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label" for="gender"><spring:message
													code="applicantinfo.label.gender" /></label>
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="csmrInfo.csGender" cssClass="form-control"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="false" />
												
											<label class="col-sm-2 control-label" for="cShouseNo"><spring:message
													code="water.dataentry.HouseNo" text="House Number" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control"
													path="csmrInfo.houseNumber" id="cShouseNo"
													maxlength="12"></form:input>
											</div>
											
											<%-- <label class="col-sm-2 control-label" for="cbillingRoadName"><spring:message
												code="water.dataentry.district" text="Disctrict" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control "
												path="csmrInfo.csRdcross" id="csAddress2"></form:input>
										</div> --%>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="cbillingAreaName"><spring:message
													code="water.dataentry.address" text="Address" /></label>
											<div class="col-sm-4">
												<form:textarea name="" type="text"
													class="form-control hasSpecialCharAndNumber" path="csmrInfo.csAdd"
													id="csAddress1"></form:textarea>
											</div>
											<c:if test="${command.sudaEnv eq 'Y'}">
												<label class="col-sm-2 control-label required-control"
													for="csDistrict"><spring:message code="water.dataentry.district"
														text="District" /></label>
												<apptags:lookupField items="${command.getLevelData('DIS')}"
													path="csmrInfo.csDistrict" 
													cssClass="form-control district chosen-select-no-results"
													selectOptionLabelCode="Select" hasId="true" isMandatory="false" />
											</c:if>	
										</div>

										<div class="form-group">
											
											<label class="col-sm-2 control-label required-control"
												for="cbillingPinCode"><spring:message
													code="water.pincode" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasPincode hideElement"
													path="csmrInfo.csCpinCode" id="csPinCode" maxlength="6"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="cSLandmark"><spring:message
													code="water.dataentry.landmark" text="Landmark" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control"
													path="csmrInfo.landmark" id="cSLandmark"
												></form:input>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="csMobileNo"><spring:message
													code="applicantinfo.label.mobile" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasMobileNo"
													path="csmrInfo.csContactno" id="csMobileNo"
													data-rule-required="true" data-rule-minlength="10"
													data-rule-maxlength="10"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="csEmailId"><spring:message
													code="applicantinfo.label.email" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasemailclass"
													path="csmrInfo.csEmail" id="csEmailId"
													data-rule-email="true"></form:input>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label" for="cSaadharNo"><spring:message
														code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control hasNumber hideElement"
													path="csmrInfo.aadharBilling" id="cSaadharNo"
													
													maxlength="12"></form:input>
											</div>
										</div>

									</div>
									<div class="form-group">
										<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.codDwzid"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass"/>
									</div>
									
									<div class="form-group">
										<apptags:lookupFieldSet baseLookupCode="CCG" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.csCcncategory"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass" 
											/>
									
									</div>	
									
									<div class="form-group" id="fromtoperiod">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.fromPeriod" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input type="text"
													class="form-control disDate mandColorClass hideElement"
													id="fromdate" path="csmrInfo.fromDate" />
												<label class="input-group-addon" for="fromd"><i
													class="fa fa-calendar"></i><span class="hide">Date</span></label>
											</div>


										</div>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.toPeriod" /></label>

										<div class="col-sm-4">
											<div class="input-group">
												<form:input type="text"
													class="form-control distDate mandColorClass hideElement"
													id="todate" path="csmrInfo.toDate" />
												<label class="input-group-addon" for="tod"><i
													class="fa fa-calendar"></i><span class="hide">Date</span></label>
											</div>


										</div>
										</div>

								</div>
								
						<%-- <div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#waterconnectiondetails"> <spring:message
											code="water.connectiondetails" />
									</a>
								</h4>
							</div>
							<div id="waterconnectiondetails" class="panel-collapse">
								<div class="panel-body">

									<div class="form-group">
										
										<apptags:lookupFieldSet baseLookupCode="CCG" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.csCcncategory"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass" 
											/>
											
									</div>


									<div class="form-group">

										<apptags:lookupFieldSet baseLookupCode="CCG" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.csCcncategory"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass" />

										<label class="col-sm-2 control-label required-control">
											<spring:message code="water.dataentry.consumer.type" text="Consumer Types"/>
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.typeOfApplication"
												class="form-control changeParameterClass"
												id="typeOfApplication" data-rule-required="true">
												<form:option value="">
													<spring:message code="water.sel.typeAppl" />
												</form:option>
												<form:option value="P">
													<spring:message code="water.perm" />
												</form:option>
												<form:option value="T">
													<spring:message code="water.temp" />
												</form:option>
											</form:select>
										</div>

									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control">
											<spring:message code="water.dataentry.consumer.type"
												text="Consumer type" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.typeOfApplication"
												class="form-control changeParameterClass"
												id="typeOfApplication" data-rule-required="true">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<form:option value="P">
													<spring:message code="water.perm" text="Permanent" />
												</form:option>
												<form:option value="T">
													<spring:message code="water.temp" text="Temporary" />
												</form:option>
											</form:select>
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label required-control">
												<spring:message code="water.dataentry.existing.connection"
													text="Is Existing Connection" />
											</label>
											<div class="col-sm-4">
												<form:select path="reqDTO.existingConsumerNumber"
													class="form-control changeParameterClass"
													id="ExistingConnection" data-rule-required="true">
													<form:option value="">
														<spring:message code="" text="Select" />
													</form:option>
													<form:option value="Y">
														<spring:message code="" text="Yes" />
													</form:option>
													<form:option value="N">
														<spring:message code="" text="No" />
													</form:option>
												</form:select>
											</div>
											<div class="col-sm-4">
												<label class="checkbox-inline"> <form:checkbox
														path="reqDTO.existingConsumerNumber"
														value="ExistingConnection" id="ExistingConnection" /> <spring:message
														code="water.existing.consumer" />

												</label>
											</div>
										</div>
									</div>


										<div class="form-group " >

										<apptags:lookupFieldSet baseLookupCode="NOD" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.trmGroup6"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" cssClass="form-control changeParameterClass" />

						 </div>  

									<div class="form-group" id="fromtoperiod">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.fromPeriod" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input type="text"
													class="form-control Moredatepicker mandColorClass hideElement"
													id="fromdate" path="csmrInfo.fromDate" />
												<label class="input-group-addon" for="fromdate"><i
													class="fa fa-calendar"></i><span class="hide">Date</span></label>
											</div>


										</div>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.toPeriod" /></label>

										<div class="col-sm-4">
											<div class="input-group">
												<form:input type="text"
													class="form-control Moredatepicker mandColorClass hideElement"
													id="todate" path="csmrInfo.toDate" />
												<label class="input-group-addon" for="todate"><i
													class="fa fa-calendar"></i><span class="hide">Date</span></label>
											</div>


										</div>

									</div>

									<div class="panel-body">
										<div class="table-responsive">
											<table class="table table-bordered" id="tbl2">
												<thead>
													<tr>
														<th><spring:message code="water.ConnectionNo" /> <span
															class="mand">*</span></th>
														<th><spring:message code="water.ConnectionSize" /></th>
														<th><spring:message code="" text="OutStanding Amount" /></th>
														<th><a data-placement="top" title="Add"
															class="btn btn-blue-2 btn-sm"
															data-original-title="<spring:message code="water.add"/>"
															id="addConnection"><i class="fa fa-plus"></i></a></th>
													</tr>
												</thead>
												<tbody>
													<c:choose>
														<c:when test="${empty command.csmrInfo.linkDetails}">
															<tr id="row0" class="appendableClass">
																<td><form:input type="text"
																		class="form-control hasNumber" minLength="9"
																		maxlength="9" path="csmrInfo.linkDetails[0].lcOldccn"
																		onblur="checkDuplicateCcn(0)" id="consumerNo0"></form:input></td>
																<td><c:set var="baseLookupCode" value="CSZ" /> <form:select
																		path="csmrInfo.linkDetails[0].lcOldccnsize"
																		class="form-control" id="connectionSize0">
																		<form:option value=" ">
																			<spring:message code="water.sel.connectionsize" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select></td>
																<td><form:input type="text"
																		class="form-control hasNumber"
																		path="csmrInfo.linkDetails[0].ccnOutStandingAmt"
																		id="noOfTaps0"></form:input></td>
																<td><a data-placement="top" title="Delete"
																	class="btn btn-danger btn-sm"
																	data-original-title="<spring:message code="water.delete"/>"
																	id=deleteConnection onclick="removeConnection(0)"><i
																		class="fa fa-trash"></i></a></td>
															</tr>
														</c:when>
														<c:otherwise>
															<c:forEach items="${command.csmrInfo.linkDetails}"
																var="details" varStatus="status">
																<tr id="row${status.count-1}" class="appendableClass">
																	<td><form:input type="text"
																			class="form-control hasNumber" minLength="9"
																			maxlength="9"
																			path="csmrInfo.linkDetails[${status.count-1}].lcOldccn"
																			onblur="checkDuplicateCcn(${status.count-1})"
																			id="consumerNo${status.count-1}"></form:input></td>
																	<td><c:set var="baseLookupCode" value="CSZ" /> <form:select
																			path="csmrInfo.linkDetails[${status.count-1}].lcOldccnsize"
																			class="form-control"
																			id="connectionSize${status.count-1}">
																			<form:option value=" ">
																				<spring:message code="water.sel.connectionsize" />
																			</form:option>
																			<c:forEach
																				items="${command.getLevelData(baseLookupCode)}"
																				var="lookUp">
																				<form:option value="${lookUp.lookUpId}"
																					code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																			</c:forEach>
																		</form:select></td>
																	<td><form:input type="text"
																			class="form-control hasNumber"
																			path="csmrInfo.linkDetails[${status.count-1}].ccnOutStandingAmt"
																			id="noOfTaps${status.count-1}"></form:input></td>
																	<td><a data-placement="top" title="Delete"
																		class="btn btn-danger btn-sm"
																		data-original-title="<spring:message code="water.delete"/>"
																		id="deleteConnection"
																		onclick="removeConnection(${status.count-1})"><i
																			class="fa fa-trash"></i></a></td>
																</tr>
															</c:forEach>
														</c:otherwise>
													</c:choose>
												</tbody>
											</table>
										</div>
									</div>





									<div class="form-group" id="numberofday">
									<label class="col-sm-2 control-label required-control">Select Days</label>
									<div class="col-sm-4" >
									<c:set var="baseLookupCode" value="NOD" /> <form:select
											path="csmrInfo.trmGroup6"
											class="form-control" >
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.lookUpCode}</form:option>
											</c:forEach>
										</form:select>
									</div>
									
									</div> 
									<div class="form-group">
										
										<apptags:lookupFieldSet baseLookupCode="CCG" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.csCcncategory"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass" 
											/>
											
									</div>
									<div class="form-group">
									<div class="col-sm-4">
												<label class="checkbox-inline"> <form:checkbox
														path="reqDTO.existingConsumerNumber" value="Y"
														id="ExistingConnection" /> <spring:message
														code="water.existing.consumer" />

												</label>
											</div>
										</div>
										
									<div class="form-group">
										<label class="col-sm-2 control-label"> <spring:message
												code="water.dataentry.is.tax.payer"
												text="Is Income Tax Payer" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.csTaxPayerFlag"
												class="form-control changeParameterClass" id="taxPayerFlag">
												<form:option value="N" text="No">
													<spring:message code="" text="No" />
												</form:option>
												<form:option value="Y">
													<spring:message code="" text="Yes" />
												</form:option>
												<form:option value="N">
													<spring:message code="" text="No" />
												</form:option>
											</form:select>
										</div>
										<div id="pandiv1" class="pan_element">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.dataentry.pan.number" text="PAN Number" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control required-control"
													data-rule-minlength="10" path="reqDTO.applicantDTO.panNo"
													id="panNo" onblur="fnValidatePAN(this)" maxlength="10" />
											</div>
										</div>
									</div>

									<div class="form-group ">

										<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.trmGroup"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass" />

										<div id="trans-restaurant" class="p_element">
											<label class="control-label col-sm-2 required-control" for="">
												<spring:message code="water.dataentry.noTable"
													text="No. of Tables" />
											</label>
											<div class="col-sm-4">
												<form:input name="" type="text" disabled="true"
													id="restaurantNumber" class="form-control hasNumber"
													path="csmrInfo.csNoofusers"></form:input>
											</div>
										</div>

										<div id="trans-hotel" class="p_element">
											<label class="control-label col-sm-2 required-control" for="">
												<spring:message code="water.dataentry.noRoom"
													text="No. of rooms" />
											</label>
											<div class="col-sm-4">
												<form:input name="" type="text" disabled="true"
													id="hotelNumber" class="form-control hasNumber"
													path="csmrInfo.csNoofusers"></form:input>
											</div>
										</div>
										<c:set var="baseLookupCode" value="CSZ" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="csmrInfo.csCcnsize"
											cssClass="form-control changeParameterClass"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="eip.select" isMandatory="true" />

									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csCcnsize"><spring:message
												code="water.dataentry.connection.size"
												text="Connection Size (in inches)" /></label>

										<div class="col-sm-4" id="notBhagirathi">
											<form:select path="csmrInfo.csCcnsize" class="form-control"
												id="withoutBhagiRathi">
												<c:set var="baseLookupCode" value="CSZ" />
												<form:option value="">
													<spring:message code='master.selectDropDwn' text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookup">
													<form:option value="${lookup.lookUpId}"
														code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>
										<div class="col-sm-4" id="isBhagirathi">
											<form:select path="csmrInfo.csCcnsize" class="form-control"
												id="withBhagiRathi"
												onchange="validateBhagirathiConnection();">
												<c:set var="baseLookupCode" value="CSZ" />
												<form:option value="">
													<spring:message code='master.selectDropDwn' text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookup">
													<c:choose>
														<c:when test="${lookup.lookUpCode eq '0.5'}">
															<form:option value="${lookup.lookUpId}"
																selected="selected" code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${lookup.lookUpId}"
																code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
														</c:otherwise>
													</c:choose>

												</c:forEach>
											</form:select>
										</div>
									</div>


									<div class="form-group">
										<label class="col-sm-2 control-label"><spring:message
												code="water.plumber.details" /></label>
										<div class="radio col-sm-4">
											  <label> <form:radiobutton path="csmrInfo.csPtype"
													value="U" id="ULBRegister" checked="true" /> <spring:message
													code="water.plumber.ulb" text="ULB's Licensed Plumber"/>
											</label> <label> <form:radiobutton path="csmrInfo.csPtype"
													value="L" id="NotRegister" /> <spring:message
													code="water.plumber.notreg" />
											</label>  
										</div>
										<label class="col-sm-2 control-label" for="plumberName"><spring:message
												code="" text="Plumber Name" /></label>
										<div class="col-sm-4">

											<form:input name="" type="text" class="form-control"
												path="reqDTO.plumberName" id="plumberName"></form:input>
										</div>
										<div id="ulbPlumber">
											<div class="col-sm-4">
												<form:select path="csmrInfo.plumId" class="form-control"
													id="plumber">
													<form:option value="">
														<spring:message code="water.dataentry.select"
															text="Select" />
													</form:option>
													<c:forEach items="${command.plumberList}" var="lookUp">
														<form:option value="${lookUp.plumberId}">${lookUp.plumberFullName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										<div id="licensePlumber">
											<div class="col-sm-4">
												<form:select path="csmrInfo.plumId" class="form-control"
													id="licPlumber">
													<form:option value="">
														<spring:message code="water.dataentry.select"
															text="Select" />
													</form:option>
													<c:forEach items="${command.plumberList}" var="lookUp">
														<form:option value="${lookUp.plumberId}">${lookUp.plumberFullName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>





									</div>



								</div>
							</div>
						</div> --%>
						
						<div class="panel panel-default" id="propertyParentId">
							<div class="panel-heading">
								<h4 id="propertyDetailsDiv" class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#waterpropertydetails"> <spring:message
											code="water.dataentry.property.details"
											text="Property Details" />
									</a>
								</h4>
							</div>
							<div id="waterpropertydetails">
								<div class="panel-body">
									<div class="form-group">
									<c:if test="${command.propNoOptionalFlag eq 'Y' }">
									<label class="col-sm-2 control-label"><spring:message
												code="water.dataentry.property.number"
												text="Property number" /></label>
												<div class="col-sm-4">
											<form:input type="text" class="form-control"
												onblur="getPropertyDetails(this)" path="reqDTO.propertyNo"
												id="propertyNo" readonly="" data-rule-required="false"></form:input>
										</div>
									</c:if>
									<c:if test="${command.propNoOptionalFlag eq 'N' }">
									<label class="col-sm-2 control-label required-control"><spring:message
												code="water.dataentry.property.number"
												text="Property number" /></label>
												<div class="col-sm-4">
											<form:input type="text" class="form-control"
												onblur="getPropertyDetails(this)" path="reqDTO.propertyNo"
												id="propertyNo" readonly="" data-rule-required="true"></form:input>
										</div>
									</c:if>

										<%-- <label class="col-sm-2 control-label required-control">
											<spring:message code="water.bhagarathi"
												text="Is bhagirathi Connection?" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.bplFlag"
												class="form-control changeParameterClass" id="bplNo"
												data-rule-required="true" onchange="getConnectionSize();">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<form:option value="Y">
													<spring:message code="" text="Yes" />
												</form:option>
												<form:option value="N">
													<spring:message code="" text="No" />
												</form:option>
											</form:select>
										</div> --%>

									</div>
									<div class="form-group">
									<c:if test="${command.propNoOptionalFlag eq 'Y' }">
									<label class="col-sm-2 control-label"><spring:message
												code="water.connection.outStanding.amount" text="Property OutStanding Amount" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control text-right"
												path="csmrInfo.totalOutsatandingAmt" id="" readonly="true"></form:input>
										</div>
									</c:if>
									<c:if test="${command.propNoOptionalFlag eq 'N' }">
									<label class="col-sm-2 control-label required-control"><spring:message
												code="water.connection.outStanding.amount" text="Property OutStanding Amount" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control text-right"
												path="csmrInfo.totalOutsatandingAmt" id="" readonly="true"></form:input>
										</div>
									</c:if>
										<%-- <div id="bpldiv">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="applicantinfo.label.bplno" /></label>
											<div class="col-sm-4">

												<form:input name="" type="text"
													class="form-control required-control" path="csmrInfo.bplNo"
													id="bplNumber" maxlength="16" />

											</div>
										</div> --%>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"><spring:message
												code="water.connection.usage.type" text="Property Usage Type" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.propertyUsageType" id="propertyUsageType"
												readonly="true"></form:input>
										</div>
										<label class="col-sm-2 control-label"><spring:message
												code="water.property.occupancyType" text="Property Occupancy Type" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.occupancyType" id="" readonly="true"></form:input>
										</div>
									</div>
								</div>
							</div>
						</div>


						<!--Connection Details-->

						<%-- <apptags:applicantDetail wardZone="WWZ"></apptags:applicantDetail> --%>
						<%-- 				<jsp:include page="/jsp/mainet/applicantDetails.jsp"></jsp:include> --%>

						<div class="panel panel-default OwnerDetails" id="ownerParentId">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#OwnerDetails">
										<spring:message code="water.dataentry.owner.details"
											text="Owner Details" />
									</a>
								</h4>
							</div>
							<c:if test="${command.propNoOptionalFlag eq 'N' }">
							<div id="OwnerDetails">
								<div class="panel-body">

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csOname"><spring:message
												code="water.dataentry.owner.name" text="Owner Name" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												path="csmrInfo.csOname" id="csOname" readonly="true"
												data-rule-required="true"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="gender"><spring:message
												code="applicantinfo.label.gender" /></label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="csmrInfo.csOGender" cssClass="form-control"
											disabled="true" hasChildLookup="false" hasId="true"
											showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="false" />
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csOadd"><spring:message
												code="water.dataentry.Address" text="Address" /></label>
										<div class="col-sm-4">
											<form:textarea name="" type="text" class="form-control "
												readonly="true" path="csmrInfo.csOadd" id="csOadd"
												data-rule-required="true"></form:textarea>
										</div>
										<label class="col-sm-2 control-label" for="csOcityName"><spring:message
											code="water.dataentry.district" text="District" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control" disabled="true"
											path="csmrInfo.csOrdcross" id="csOcityName"></form:input>
									</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="opincode"><spring:message code="water.pincode" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" readonly="true"
												class="form-control hasNumber hideElement"
												path="csmrInfo.opincode" id="opincode" maxlength="6"
												data-rule-required="true"></form:input>
										</div>
										<%-- <label class="col-sm-2 control-label" for="adharNo"><spring:message
												code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												readonly="true" path="reqDTO.applicantDTO.aadharNo"
												id="aadharNo" maxlength="12"></form:input>
										</div> --%>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="mobileNo"><spring:message
												code="applicantinfo.label.mobile" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.csOcontactno" id="mobileNo" readonly="true"
												data-rule-required="true" data-rule-minlength="10"
												data-rule-maxlength="10"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="emailId"><spring:message
												code="applicantinfo.label.email" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.csOEmail" id="emailId" readonly="true"
												data-rule-email="true"></form:input>
										</div>
									</div>
								</div>
							</div>
							</c:if>
							
							<c:if test="${command.propNoOptionalFlag eq 'Y' }">
							<div id="OwnerDetails">
								<div class="panel-body">

									<div class="form-group">
										<label class="col-sm-2 control-label"
											for="csOname"><spring:message
												code="water.dataentry.owner.name" text="Owner Name" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												path="csmrInfo.csOname" id="csOname" disabled="true"
												data-rule-required="false"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="gender"><spring:message
												code="applicantinfo.label.gender" /></label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="csmrInfo.csOGender" cssClass="form-control"
											disabled="true" hasChildLookup="false" hasId="true"
											showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="false" />
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											for="csOadd"><spring:message
												code="water.dataentry.Address" text="Address" /></label>
										<div class="col-sm-4">
											<form:textarea name="" type="text" class="form-control "
												disabled="true" path="csmrInfo.csOadd" id="csOadd"
												data-rule-required="false"></form:textarea>
										</div>
										<label class="col-sm-2 control-label"
											for="opincode"><spring:message code="water.pincode" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" disabled="true"
												class="form-control hasNumber hideElement"
												path="csmrInfo.opincode" id="opincode" maxlength="6"
												data-rule-required="flase"></form:input>
										</div>
										<%-- <label class="col-sm-2 control-label" for="csOcityName"><spring:message
											code="water.dataentry.district" text="District" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control" disabled="true"
											path="csmrInfo.csOrdcross" id="csOcityName"></form:input>
									</div> --%>
									</div>
									<%-- <div class="form-group">
										
										<label class="col-sm-2 control-label" for="adharNo"><spring:message
												code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												disabled="true" path="reqDTO.applicantDTO.aadharNo"
												id="aadharNo" maxlength="12"></form:input>
										</div>
									</div> --%>
									<div class="form-group">
										<label class="col-sm-2 control-label"
											for="mobileNo"><spring:message
												code="applicantinfo.label.mobile" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.csOcontactno" id="mobileNo" disabled="true"
												data-rule-required="false" data-rule-minlength="10"
												data-rule-maxlength="10"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="emailId"><spring:message
												code="applicantinfo.label.email" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.csOEmail" id="emailId" disabled="true"
												data-rule-email="true"></form:input>
										</div>
									</div>
								</div>
							</div>
							</c:if>
							
						</div>
						
						

						<%-- <div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#ConsumerDetails"> <spring:message
											code="water.owner.details.consumerAddress"
											text="Consumer Details" />
									</a>
								</h4>
							</div>
							<div id="ConsumerDetails" class="panel-collapse">
								<div class="panel-body">
									<div class="form-group">

										<div class="col-sm-6">
											<label class="checkbox-inline" for="isConsumer"> <form:checkbox
													path="reqDTO.isConsumer" value="Y" id="isConsumer" /> <spring:message
													code="water.isConsumerSame"
													text="Is Applicant is Consumer?" />
											</label>
										</div>
									</div>
									<div id="hideConsumerDetails">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="firstName"><spring:message
													code="water.dataentry.consumer.name" text="Consumer Name" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasCharacter" readonly="${disabled}"
													path="csmrInfo.csName" id="csFirstName"
													data-rule-required="true"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="gender"><spring:message
													code="applicantinfo.label.gender" /></label>
											<c:set var="baseLookupCode" value="GEN" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="csmrInfo.csGender" cssClass="form-control"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="false" />
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="cbillingAreaName"><spring:message
													code="water.dataentry.address" text="Address" /></label>
											<div class="col-sm-4">
												<form:textarea name="" type="text" class="form-control "
													path="csmrInfo.csAdd" id="csAddress1"></form:textarea>
											</div>
											<label class="col-sm-2 control-label" for="cbillingRoadName"><spring:message
												code="water.dataentry.district" text="Disctrict" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control "
												path="csmrInfo.csRdcross" id="csAddress2"></form:input>
										</div>
										</div>


										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="cbillingPinCode"><spring:message
													code="water.pincode" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasPincode hideElement"
													path="csmrInfo.csCpinCode" id="csPinCode" maxlength="6"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="cSaadharNo"><spring:message
													code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control"
													path="reqDTO.applicantDTO.aadharNo" id="cSaadharNo"
													maxlength="12"></form:input>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="csMobileNo"><spring:message
													code="applicantinfo.label.mobile" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasMobileNo"
													path="csmrInfo.csContactno" id="csMobileNo"
													data-rule-required="true" data-rule-minlength="10"
													data-rule-maxlength="10"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="csEmailId"><spring:message
													code="applicantinfo.label.email" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasemailclass"
													path="csmrInfo.csEmail" id="csEmailId"
													data-rule-email="true"></form:input>
											</div>
										</div>

									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label " for="annualRent"><spring:message
												code="annual.rent" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text"
												class="form-control hideElement" path="csmrInfo.annualRent"
												id="annualRent" maxlength="7"></form:input>
										</div>
										<div class="col-sm-6">
											<label class="checkbox-inline" for="taxPayer"> <form:checkbox
													path="csmrInfo.csTaxPayerFlag" value="Y" id="taxPayer" />
												<spring:message code="taxPayer" />
											</label>
										</div>

									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label" for=""><spring:message
												code="" text="ARV" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control hasNumber"
												path="csmrInfo.arv" id="arv"></form:input>
										</div>
										
										<label class="col-sm-2 control-label" for=""><spring:message
														code="" text="PTIN" /></label>
												<div class="col-sm-4">
													<form:input type="text" class="form-control"
														path="csmrInfo.ptin" id="ptin"></form:input>
												</div>
												
											</div>

										<div class="form-group">
										<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.codDwzid"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass"/>
									</div>
										<div class="form-group">
											<apptags:lookupFieldSet baseLookupCode="CCG" hasId="true"
												showOnlyLabel="false" pathPrefix="csmrInfo.csCcncategory"
												isMandatory="true" hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true" cssClass="form-control"
												disabled="false" />
										</div>

										<div class="form-group">

											<label class="col-sm-2 control-label"><spring:message
													code="water.nwwtrcnctn.noOfFlats" text="No of flats" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control hasNumber"
													path="csmrInfo.noOfFlats" id="noOfFlats" maxlength="5"></form:input>
											</div>
											<label class="col-sm-2 control-label"
												><spring:message code="water.nwwtrcnctn.noOffamilies" text="No of families"/></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasNumber"
													path="csmrInfo.noOffmls" id="noOffamilies" maxlength="5"></form:input>
											</div>

										</div>
										
										<div class="form-group">

											<label class="col-sm-2 control-label"><spring:message
													code="water.nwwtrcnctn.noOfMembers" text="No of members" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control hasNumber"
													path="csmrInfo.noOfmembrs" id="noOfMembers" maxlength="5"></form:input>
											</div>
										</div>
									</div>
								</div>
						</div> --%>







						<%-- <div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#BillingDetails"> <spring:message
											code="water.owner.details.buldingadd" />
									</a>
								</h4>
							</div>
							<div id="BillingDetails" class="panel-collapse">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-sm-6">
											<label class="checkbox-inline" for="billing"> <form:checkbox
													path="reqDTO.isBillingAddressSame" value="Billing"
													id="billing" /> <spring:message code="water.isBillingSame" />
											</label>
										</div>
									</div>
									<div id="hideBillingDetails">

										<div class="form-group">

											<label class="col-sm-2 control-label required-control"
												for="billingAreaName"><spring:message
													code="address.line1" text="Address Line1" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control "
													path="csmrInfo.csBadd" id="billingAreaName"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="billingCityName"><spring:message
													code="address.line2" text="Address Line2" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control "
													path="csmrInfo.csBcityName" id="billingCityName"></form:input>
											</div>
											<label class="col-sm-2 control-label required-control"
												for="billingPinCode"><spring:message
													code="water.pincode" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasPincode hideElement"
													path="csmrInfo.bpincode" id="billingPinCode" maxlength="6"></form:input>
											</div>
										</div>


										<div class="form-group">

											<label class="col-sm-2 control-label" for="billingRoadName"><spring:message
													code="address.line3" text="Address Line3" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control "
													path="csmrInfo.csBrdcross" id="billingRoadName"></form:input>
											</div>
											<label class="col-sm-2 control-label required-control"
												for="billingPinCode"><spring:message
													code="water.pincode" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasNumber hideElement"
													path="csmrInfo.bpincode" id="billingPinCode" maxlength="6"></form:input>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div> --%>

						<%-- <div class="panel panel-default OwnerDetails">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#OwnerDetails">
										<spring:message code="water.owner.details" />
									</a>
								</h4>
							</div>
							<div id="OwnerDetails" class="panel-collapse collapse">
								<div class="panel-body">

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csOtitle"><spring:message
												code="applicantinfo.label.title" /></label>
										<c:set var="baseLookupCode" value="TTL" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="csmrInfo.csOtitle" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" />
										<label class="col-sm-2 control-label required-control"
											for="csOname"><spring:message
												code="applicantinfo.label.firstname" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												path="csmrInfo.csOname" id="csOname"
												data-rule-required="true"></form:input>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" for="csOmname"><spring:message
												code="applicantinfo.label.middlename" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												path="csmrInfo.csOmname" id="middleName"></form:input>
										</div>
										<label class="col-sm-2 control-label required-control"
											for="csOlname"><spring:message
												code="applicantinfo.label.lastname" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												path="csmrInfo.csOlname" id="csOlname"
												data-rule-required="true"></form:input>
										</div>
									</div>


									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="csOadd"><spring:message code="address.line1"
												text="Address Line1" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control "
												path="csmrInfo.csOadd" id="csOadd" data-rule-required="true"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="csOcityName"><spring:message
												code="address.line2" text="Address Line2" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control "
												path="csmrInfo.csOcityName" id="csOcityName"></form:input>
										</div>
									</div>


									<div class="form-group">


										<label class="col-sm-2 control-label" for="billingRoadName"><spring:message
												code="address.line3" text="Address Line3" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control "
												path="csmrInfo.csOrdcross" id="billingRoadName"></form:input>
										</div>
										<label class="col-sm-2 control-label required-control"
											for="opincode"><spring:message code="water.pincode" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text"
												class="form-control hasNumber hideElement"
												path="csmrInfo.opincode" id="opincode" maxlength="6"
												data-rule-required="true"></form:input>
										</div>

									</div>






								</div>
							</div>
						</div>
 --%>










						<div class="panel panel-default ownerDetails">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#additionalOwner"> <spring:message
											code="water.additionalOwner" />
									</a>
								</h4>
							</div>
							<div id="additionalOwner" class="panel-collapse">
								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-bordered" id="tbl1">
											<tr>
												<th><spring:message code="water.title" /> <span
													class="mand">*</span></th>
												<th><spring:message code="water.owner.details.fname" />
													<span class="mand">*</span></th>
												<th><spring:message code="water.owner.details.mname" /></th>
												<th><spring:message code="water.owner.details.lname" />
													<span class="mand">*</span></th>
												<th><spring:message code="water.owner.details.gender"
														text="Gender" /> <span class="mand">*</span></th>
												<th><spring:message code="water.owner.details.uid"
														text="Adhar No." /></th>
												<th><a data-toggle="tooltip" data-placement="top"
													title="" class="btn btn-blue-2 btn-sm"
													data-original-title="Add Owner" id="addOwner"><i
														class="fa fa-plus"></i></a></th>
											</tr>
											<c:choose>
												<c:when test="${empty command.csmrInfo.ownerList}">
													<tr id="tr0" class="ownerClass">
														<td><c:set var="baseLookupCode" value="TTL" /> <form:select
																path="csmrInfo.ownerList[0].ownerTitle"
																class="form-control" id="ownerTitle0">
																<form:option value="">
																	<spring:message code="water.sel.title" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input name="" type="text"
																class="form-control hasCharacter"
																path="csmrInfo.ownerList[0].ownerFirstName"
																id="ownerFName0"></form:input></td>
														<td><form:input name="" type="text"
																class="form-control hasCharacter"
																path="csmrInfo.ownerList[0].ownerMiddleName"
																id="ownerMName0"></form:input></td>
														<td><form:input name="" type="text"
																class="form-control hasCharacter"
																path="csmrInfo.ownerList[0].ownerLastName"
																id="ownerLName0"></form:input></td>
														<td><c:set var="baseLookupCode" value="GEN" /> <form:select
																path="csmrInfo.ownerList[0].gender" class="form-control"
																id="ownerGender0">
																<form:option value="">
																	<spring:message code="water.sel.gen" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input name="" type="text"
																class="form-control hasNumber"
																path="csmrInfo.ownerList[0].caoUID" id="ownerUID0"></form:input></td>
														<td><a data-toggle="tooltip" data-placement="top"
															title="" class="btn btn-danger btn-sm"
															data-original-title="Delete Owner" id=deleteOwner
															onclick="removeRow(0)"><i class="fa fa-trash"></i></a></td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${command.csmrInfo.ownerList}"
														var="details" varStatus="status">
														<tr id="tr${status.count-1}" class="ownerClass">
															<td><c:set var="baseLookupCode" value="TTL" /> <form:select
																	path="csmrInfo.ownerList[${status.count-1}].ownerTitle"
																	class="form-control" id="ownerTitle${status.count-1}">
																	<form:option value="">
																		<spring:message code="water.sel.title" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
															<td><form:input name="" type="text"
																	class="form-control hasCharacter"
																	path="csmrInfo.ownerList[${status.count-1}].ownerFirstName"
																	id="ownerFName${status.count-1}"></form:input></td>
															<td><form:input name="" type="text"
																	class="form-control hasCharacter"
																	path="csmrInfo.ownerList[${status.count-1}].ownerMiddleName"
																	id="ownerMName${status.count-1}"></form:input></td>
															<td><form:input name="" type="text"
																	class="form-control hasCharacter"
																	path="csmrInfo.ownerList[${status.count-1}].ownerLastName"
																	id="ownerLName${status.count-1}"></form:input></td>
															<td><c:set var="baseLookupCode" value="GEN" /> <form:select
																	path="csmrInfo.ownerList[${status.count-1}].gender"
																	class="form-control" id="ownerGender${status.count-1}">
																	<form:option value="">
																		<spring:message code="water.sel.gen" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
															<td><form:input name="" type="text"
																	class="form-control hasNumber"
																	path="csmrInfo.ownerList[${status.count-1}].caoUID"
																	id="ownerUID${status.count-1}"></form:input></td>
															<td><a data-toggle="tooltip" data-placement="top"
																title="" class="btn btn-danger btn-sm"
																data-original-title="Delete Owner" id=deleteOwner
																onclick="removeRow(${status.count-1})"><i
																	class="fa fa-trash"></i></a></td>
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</table>
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#waterconnectiondetails"> <spring:message
											code="water.connectiondetails" />
									</a>
								</h4>
							</div>
							<div id="waterconnectiondetails" class="panel-collapse">
								<div class="panel-body">

								<%-- <div class="form-group">

										<apptags:lookupFieldSet baseLookupCode="CCG" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.csCcncategory"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass" />

										<label class="col-sm-2 control-label required-control">
											<spring:message code="water.dataentry.consumer.type" text="Consumer Types"/>
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.typeOfApplication"
												class="form-control changeParameterClass"
												id="typeOfApplication" data-rule-required="true">
												<form:option value="">
													<spring:message code="water.sel.typeAppl" />
												</form:option>
												<form:option value="P">
													<spring:message code="water.perm" />
												</form:option>
												<form:option value="T">
													<spring:message code="water.temp" />
												</form:option>
											</form:select>
										</div>

									</div> --%>
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label required-control">
											<spring:message code="water.dataentry.consumer.type"
												text="Consumer type" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.typeOfApplication"
												class="form-control changeParameterClass"
												id="typeOfApplication" data-rule-required="true">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<form:option value="P">
													<spring:message code="water.perm" text="Permanent" />
												</form:option>
												<form:option value="T">
													<spring:message code="water.temp" text="Temporary" />
												</form:option>
											</form:select>
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label required-control">
												<spring:message code="water.dataentry.existing.connection"
													text="Is Existing Connection" />
											</label>
											<div class="col-sm-4">
												<form:select path="reqDTO.existingConsumerNumber"
													class="form-control changeParameterClass"
													id="ExistingConnection" data-rule-required="true">
													<form:option value="">
														<spring:message code="" text="Select" />
													</form:option>
													<form:option value="Y">
														<spring:message code="" text="Yes" />
													</form:option>
													<form:option value="N">
														<spring:message code="" text="No" />
													</form:option>
												</form:select>
											</div>
											<div class="col-sm-4">
												<label class="checkbox-inline"> <form:checkbox
														path="reqDTO.existingConsumerNumber"
														value="ExistingConnection" id="ExistingConnection" /> <spring:message
														code="water.existing.consumer" />

												</label>
											</div>
										</div>
									</div> --%>


									<%-- 	<div class="form-group " >

										<apptags:lookupFieldSet baseLookupCode="NOD" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.trmGroup6"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" cssClass="form-control changeParameterClass" />

						 </div>   --%>

									<%-- <div class="form-group" id="fromtoperiod">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.fromPeriod" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input type="text"
													class="form-control Moredatepicker mandColorClass hideElement"
													id="fromdate" path="csmrInfo.fromDate" />
												<label class="input-group-addon" for="fromdate"><i
													class="fa fa-calendar"></i><span class="hide">Date</span></label>
											</div>


										</div>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.toPeriod" /></label>

										<div class="col-sm-4">
											<div class="input-group">
												<form:input type="text"
													class="form-control Moredatepicker mandColorClass hideElement"
													id="todate" path="csmrInfo.toDate" />
												<label class="input-group-addon" for="todate"><i
													class="fa fa-calendar"></i><span class="hide">Date</span></label>
											</div>


										</div>

									</div> --%>

									<div class="panel-body">
										<div class="table-responsive">
											<table class="table table-bordered" id="tbl2">
												<thead>
													<tr>
														<th><spring:message code="water.ConnectionNo" /> <span
															class="mand">*</span></th>
														<th><spring:message code="water.ConnectionSize" /></th>
														<th><spring:message code="outstanding.prop" text="OutStanding Amount" /></th>
														<th><a data-placement="top" title="Add"
															class="btn btn-blue-2 btn-sm"
															data-original-title="<spring:message code="water.add"/>"
															id="addConnection"><i class="fa fa-plus"></i></a></th>
													</tr>
												</thead>
												<tbody>
													<c:choose>
														<c:when test="${empty command.csmrInfo.linkDetails}">
															<tr id="row0" class="appendableClass">
																<td><form:input type="text"
																		class="form-control hasNumber" minLength="9"
																		maxlength="9" path="csmrInfo.linkDetails[0].lcOldccn"
																		onblur="checkDuplicateCcn(0)" id="consumerNo0"></form:input></td>
																<td><c:set var="baseLookupCode" value="CSZ" /> <form:select
																		path="csmrInfo.linkDetails[0].lcOldccnsize"
																		class="form-control" id="connectionSize0">
																		<form:option value=" ">
																			<spring:message code="water.sel.connectionsize" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select></td>
																<td><form:input type="text"
																		class="form-control hasNumber"
																		path="csmrInfo.linkDetails[0].ccnOutStandingAmt"
																		id="noOfTaps0"></form:input></td>
																<td><a data-placement="top" title="Delete"
																	class="btn btn-danger btn-sm"
																	data-original-title="<spring:message code="water.delete"/>"
																	id=deleteConnection onclick="removeConnection(0)"><i
																		class="fa fa-trash"></i></a></td>
															</tr>
														</c:when>
														<c:otherwise>
															<c:forEach items="${command.csmrInfo.linkDetails}"
																var="details" varStatus="status">
																<tr id="row${status.count-1}" class="appendableClass">
																	<td><form:input type="text"
																			class="form-control hasNumber" minLength="9"
																			maxlength="9"
																			path="csmrInfo.linkDetails[${status.count-1}].lcOldccn"
																			onblur="checkDuplicateCcn(${status.count-1})"
																			id="consumerNo${status.count-1}"></form:input></td>
																	<td><c:set var="baseLookupCode" value="CSZ" /> <form:select
																			path="csmrInfo.linkDetails[${status.count-1}].lcOldccnsize"
																			class="form-control"
																			id="connectionSize${status.count-1}">
																			<form:option value=" ">
																				<spring:message code="water.sel.connectionsize" />
																			</form:option>
																			<c:forEach
																				items="${command.getLevelData(baseLookupCode)}"
																				var="lookUp">
																				<form:option value="${lookUp.lookUpId}"
																					code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																			</c:forEach>
																		</form:select></td>
																	<td><form:input type="text"
																			class="form-control hasNumber"
																			path="csmrInfo.linkDetails[${status.count-1}].ccnOutStandingAmt"
																			id="noOfTaps${status.count-1}"></form:input></td>
																	<td><a data-placement="top" title="Delete"
																		class="btn btn-danger btn-sm"
																		data-original-title="<spring:message code="water.delete"/>"
																		id="deleteConnection"
																		onclick="removeConnection(${status.count-1})"><i
																			class="fa fa-trash"></i></a></td>
																</tr>
															</c:forEach>
														</c:otherwise>
													</c:choose>
												</tbody>
											</table>
										</div>
									</div>





									<%-- <div class="form-group" id="numberofday">
									<label class="col-sm-2 control-label required-control">Select Days</label>
									<div class="col-sm-4" >
									<c:set var="baseLookupCode" value="NOD" /> <form:select
											path="csmrInfo.trmGroup6"
											class="form-control" >
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.lookUpCode}</form:option>
											</c:forEach>
										</form:select>
									</div>
									
									</div>  --%>
									<%-- <div class="form-group">
										
										<apptags:lookupFieldSet baseLookupCode="CCG" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.csCcncategory"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass" 
											/>
											
									</div> --%>
									<div class="form-group">
									<div class="col-sm-4">
												<label class="checkbox-inline"> <form:checkbox
														path="reqDTO.existingConsumerNumber" value="Y"
														id="ExistingConnection" /> <spring:message
														code="water.existing.consumer" />

												</label>
											</div>
										</div>
										
									<div class="form-group">
										<label class="col-sm-2 control-label"> <spring:message
												code="water.dataentry.is.tax.payer"
												text="Is Income Tax Payer" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.csTaxPayerFlag"
												class="form-control" id="taxPayerFlag">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<form:option value="Y">
													<spring:message code="" text="Yes" />
												</form:option>
												<form:option value="N">
													<spring:message code="" text="No" />
												</form:option>
											</form:select>
										</div>
										<div id="pandiv1" class="pan_element">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="water.dataentry.pan.number" text="PAN Number" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control required-control"
													data-rule-minlength="10" path="reqDTO.applicantDTO.panNo"
													id="panNo" onblur="fnValidatePAN(this)" maxlength="10" />
											</div>
										</div>
									</div>

									<div class="form-group ">

										<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.trmGroup"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control" />

										<div id="trans-restaurant" class="p_element">
											<label class="control-label col-sm-2 required-control" for="">
												<spring:message code="water.dataentry.noTable"
													text="No. of Tables" />
											</label>
											<div class="col-sm-4">
												<form:input name="" type="text" disabled="true"
													id="restaurantNumber" class="form-control hasNumber"
													path="csmrInfo.csNoofusers"></form:input>
											</div>
										</div>

										<div id="trans-hotel" class="p_element">
											<label class="control-label col-sm-2 required-control" for="">
												<spring:message code="water.dataentry.noRoom"
													text="No. of rooms" />
											</label>
											<div class="col-sm-4">
												<form:input name="" type="text" disabled="true"
													id="hotelNumber" class="form-control hasNumber"
													path="csmrInfo.csNoofusers"></form:input>
											</div>
										</div>
										<%-- <c:set var="baseLookupCode" value="CSZ" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="csmrInfo.csCcnsize"
											cssClass="form-control changeParameterClass"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="eip.select" isMandatory="true" /> --%>

									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csCcnsize"><spring:message
												code="water.dataentry.connection.size"
												text="Connection Size (in inches)" /></label>

										<div class="col-sm-4" id="notBhagirathi">
											<form:select path="csmrInfo.csCcnsize" class="form-control"
												id="withoutBhagiRathi">
												<c:set var="baseLookupCode" value="CSZ" />
												<form:option value="">
													<spring:message code='master.selectDropDwn' text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookup">
													<form:option value="${lookup.lookUpId}"
														code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>
										<div class="col-sm-4" id="isBhagirathi">
											<form:select path="csmrInfo.csCcnsize" class="form-control"
												id="withBhagiRathi"
												onchange="validateBhagirathiConnection();">
												<c:set var="baseLookupCode" value="CSZ" />
												<form:option value="">
													<spring:message code='master.selectDropDwn' text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookup">
													<c:choose>
														<c:when test="${lookup.lookUpCode eq '0.5'}">
															<form:option value="${lookup.lookUpId}"
																selected="selected" code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${lookup.lookUpId}"
																code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
														</c:otherwise>
													</c:choose>

												</c:forEach>
											</form:select>
										</div>
									</div>


									<div class="form-group">
										<label class="col-sm-2 control-label"><spring:message
												code="water.plumber.details" /></label>
										<div class="radio col-sm-4">
											<label> <form:radiobutton path="csmrInfo.csPtype"
													value="U" id="ULBRegister" checked="true" /> <spring:message
													code="water.plumber.reg" />
											</label> <label> <form:radiobutton path="csmrInfo.csPtype"
													value="L" id="NotRegister" /> <spring:message
													code="water.plumber.notreg" />
											</label>
										</div>
										<label class="col-sm-2 control-label required-control"
											for="plumberName"><spring:message
												code="water.plumber.name" /></label>
										<%-- <div class="col-sm-4">

											<form:input name="" type="text" class="form-control"
												path="reqDTO.plumberName" id="plumberName"></form:input>
										</div> --%>
										<div id="ulbPlumber">
											<div class="col-sm-4">
												<form:select path="csmrInfo.plumId" class="form-control"
													id="plumber">
													
													<c:forEach items="${command.plumberList}" var="lookUp">
														<form:option selected="selected" value="${lookUp.plumberId}">${lookUp.plumberFName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										<div id="licensePlumber">
											<div class="col-sm-4">
												<form:select path="csmrInfo.plumId" class="form-control"
													id="licPlumber" disabled="true">
													
													<c:forEach items="${command.plumberList}" var="lookUp">
														<form:option selected="selected" value="${lookUp.plumberId}">${lookUp.plumberFullName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>





									</div>



								</div>
							</div>
						</div>

						
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#BillingDetails"> <spring:message
											code="water.owner.details.buldingadd" />
									</a>
								</h4>
							</div>
							<div id="BillingDetails" class="panel-collapse">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-sm-6">
											<label class="checkbox-inline" for="billing"> <form:checkbox
													path="reqDTO.isBillingAddressSame" value="Billing"
													id="billing" /> <spring:message code="water.isBillingSame" />
											</label>
										</div>
										<c:if test="${command.sudaEnv eq 'Y'}">
											<div class="col-sm-6 text-right">
												<label class="checkbox-inline" for=""> <form:checkbox
														path="" value=""
														id="" /> <spring:message code="water.isBillingAddress.map" />
												</label>
											</div>
										</c:if>
									</div>
									<div id="hideBillingDetails">

										<div class="form-group">
										
											<label class="col-sm-2 control-label" for="cShouseNo"><spring:message
													code="water.dataentry.HouseNo" text="House Number" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control"
													path="csmrInfo.houseNumberBilling" id="cShouseNoBilling"
													maxlength="12"></form:input>
											</div>

											
											<%-- <label class="col-sm-2 control-label" for="billingCityName"><spring:message
													code="address.line2" text="Address Line2" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control "
													path="csmrInfo.csBcityName" id="billingCityName"></form:input>
											</div> --%>
											<label class="col-sm-2 control-label required-control"
												for="billingAreaName"><spring:message
													code="address.line1" text="Address Line1" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control "
													path="csmrInfo.csBadd" id="billingAreaName"></form:input>
											</div>
											
										</div>
										<div  class="form-group">
												<c:if test="${command.sudaEnv eq 'Y'}">
													<label class="col-sm-2 control-label required-control"
														for="billingDistrict"><spring:message code="water.dataentry.district"
															text="District" /></label>
													<apptags:lookupField items="${command.getLevelData('DIS')}"
														path="csmrInfo.districtBilling" 
														cssClass="form-control district"
														selectOptionLabelCode="Select" hasId="true" isMandatory="false" />
														
													<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
														showOnlyLabel="false" pathPrefix="csmrInfo.coBDwzid"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														cssClass="form-control changeParameterClass"/>
												
												</c:if>
												
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="billingPinCode"><spring:message
													code="water.pincode" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasNumber hideElement"
													path="csmrInfo.bpincode" id="billingPinCode" maxlength="6"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="cSLandmark"><spring:message
													code="water.landMark" text="Landmark" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control"
													path="csmrInfo.landmarkBilling" id="cSLandmarkBilling"
												></form:input>
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="csMobileNo"><spring:message
													code="applicantinfo.label.mobile" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasMobileNo"
													path="csmrInfo.contactNoBilling" id="csMobileNoBilling"
													data-rule-required="true" data-rule-minlength="10"
													data-rule-maxlength="10"></form:input>
											</div>
											<label class="col-sm-2 control-label" for="csEmailId"><spring:message
													code="applicantinfo.label.email" /></label>
											<div class="col-sm-4">
												<form:input type="text" class="form-control hasemailclass"
													path="csmrInfo.emailBilling" id="csEmailIdBilling"
													data-rule-email="true"></form:input>
											</div>
										</div>


										<%-- <div class="form-group">

											<label class="col-sm-2 control-label" for="billingRoadName"><spring:message
													code="address.line3" text="Address Line3" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control "
													path="csmrInfo.csBrdcross" id="billingRoadName"></form:input>
											</div>
											<label class="col-sm-2 control-label required-control"
												for="billingPinCode"><spring:message
													code="water.pincode" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text"
													class="form-control hasNumber hideElement"
													path="csmrInfo.bpincode" id="billingPinCode" maxlength="6"></form:input>
											</div>

										</div> --%>
									</div>
								</div>
							</div>
						</div>

						<div class="padding-top-10 text-center" id="chekListChargeId">
							<button type="button" class="btn btn-success" id="proceedId"
								onclick="getChecklistAndCharges(this)">
								<spring:message code="water.btn.proceed" />
							</button>
							<button type="Reset" class="btn btn-warning" id="resetform">
								<spring:message code="water.btn.reset" />
							</button>
						</div>

						<c:if test="${not empty command.checkList}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 id="applicantDetails" class="panel-title">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse"
											href="#waterformappdetails"><spring:message
												code="water.documentattchmnt" /><span>
												<%-- Defect #148243 --%>
												<spring:message
													code="water.uploadfile.validtn" text="(Upload File upto 5MB and only pdf,doc,docx,jpeg,jpg,png,gif,bmp)" />
										</span></a>
									</h4>
								</div>

								<div id="waterformappdetails" class="panel-collapse collapse in">
									<div class="panel-body">

										<div class="overflow margin-top-10 margin-bottom-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th><spring:message code="water.serialNo"
																	text="Sr No" /></th>
															<th><spring:message code="water.docName"
																	text="Document Name" /></th>
															<th><spring:message code="water.docDesc"
																		text="Document Description" /></th>
															<th><spring:message code="water.status"
																	text="Status" /></th>
															<th width="500"><spring:message
																	code="water.uploadText" text="Upload" /></th>
														</tr>

														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">

															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}
																		<c:if test="${lookUp.doc_DESC_ENGL eq 'Affidavit' and command.sudaEnv eq 'Y'}">
																			<a href="./assets/docs/Affidavit.docx" class="margin-left-10">Click Here to Download Format</a>
																		</c:if></td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}</td>
																	</c:otherwise>
																</c:choose>
																<c:choose>
																		<c:when test="${lookUp.docDes ne null and command.sudaEnv eq 'Y'}">
																			<td>
																				<form:select 
																				path="checkList[${lk.index}].docDescription"
																				 class="form-control" id="docTypeSelect_${lk.index}">
																				<form:option value="">
																					<spring:message code="" text="select" />
																				</form:option>
																				<c:set var="baseLookupCode" value="${lookUp.docDes}" />
																				<c:forEach items="${command.getLevelData(baseLookupCode)}" var="docLookup">	
																					<form:option value="${docLookup.lookUpDesc}" >${docLookup.lookUpDesc}</form:option>
																				</c:forEach>
																			    </form:select>
																		    </td>
																	    </c:when>
																		<c:otherwise>
																			<td><div> <form:input name="" type="text" id="docDesc_${lk.index}" path="checkList[${lk.index}].docDescription"></form:input></div>	</td>
																		</c:otherwise>
																	</c:choose>
																
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td><spring:message code="water.doc.mand" /></td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td><spring:message code="water.doc.opt" /></td>
																</c:if>
																<td>
																	<div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
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
							</div>
						</c:if>
						<form:hidden path="free" id="free" />


					</div>
					<div class="text-center" id="divSubmit">
						<button type="button" class="btn btn-success"
							onclick="showViewFormJsp(this)" id="showView">
							<spring:message code="save.water.continue" />
						</button>

						<input type="button" class="btn btn-danger"
							onclick="window.location.href='CitizenHome.html'"
							value="<spring:message code="water.btn.cancel"/>" />
							
					    <button type="Reset" class="btn btn-warning" id="resetform"
							onclick="window.location.href='NewWaterConnectionForm.html'">
						    <spring:message code="water.btn.reset" />
						</button>
					</div>


				</form:form>
			</div>
		</div>
	</div>
</div>
