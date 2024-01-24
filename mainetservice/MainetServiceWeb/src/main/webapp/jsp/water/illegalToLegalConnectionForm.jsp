<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/water/illegalToLegalConnection.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		if($('#hiddenConsumerSame').val()=="Y")
		{
		$("#isConsumer").attr("checked",true);
		}
		else
			{
			$("#isConsumer").attr("checked",false);
			}

		 
	 	 var chkConsumer = $('#isConsumer').is(':checked');
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
			 }
		 

		
		if($('#hiddenBillingSame').val()=="Y")
		{
		$("#billing").attr("checked",true);
		}
		else
			{
			$("#billing").attr("checked",false);
			}
		 var chkBilling = $('#billing').is(':checked');
		 if(chkBilling)
			 {
		
			 $("#hideBillingDetails").hide();
			 $("#billingAreaName").data('rule-required',false);
				$("#billingPinCode").data('rule-required',false);
				
			 }
		 else
			 {
			
			 $("#hideBillingDetails").show();
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
						 
						
					});


</script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>Water Connection</h2>
				<apptags:helpDoc url="IllegalToLegalConnection.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="water.fieldwith" /> <i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" />
					</span>
				</div>
				<form:form action="IllegalToLegalConnection.html"
					class="form-horizontal form" name="frmNewWaterForm"
					id="frmNewWaterForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="isBillingSame" id="hiddenBillingSame" />
					<form:hidden path="isConsumerSame" id="hiddenConsumerSame" />
					<form:hidden path="" id="propOutStanding"
						value="${command.propOutStanding}" />
					<form:hidden path="" id="existingNumber"
						value="${command.reqDTO.existingConsumerNumber}" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<div class="panel panel-default">
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
										<label class="col-sm-2 control-label required-control"><spring:message
												code="" text="Illegal Notice No." /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												onblur="getNoticeDetails(this)"
												path="csmrInfo.csIllegalNoticeNo" id="csIllegalNoticeNo"
												readonly="${command.scrutinyFlag eq 'Y' ? true:false}"
												data-rule-required="true"></form:input>
										</div>

										<label class="col-sm-2 control-label required-control">
											<spring:message code="water.dataentry.bhagirathi.connection"
												text="Is Bhagirathi connection" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.bplFlag"
												class="form-control changeParameterClass" id="bplNo"
												data-rule-required="true" onchange="getConnectionSize();"
												disabled="${command.scrutinyFlag eq 'Y' ? true:false}">
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

									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.dataentry.property.number"
												text="Property number" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												onblur="getPropertyDetails(this)" path="reqDTO.propertyNo"
												id="propertyNo" readonly="true" data-rule-required="true"></form:input>
										</div>
										<%-- <label class="col-sm-2 control-label"><spring:message
												code="" text="Property OutStanding Amount" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control text-right"
												path="csmrInfo.totalOutsatandingAmt" id="" readonly="true"></form:input>
										</div> --%>
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
								</div>
							</div>
						</div>


						<div class="panel panel-default OwnerDetails">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#OwnerDetails">
										<spring:message code="water.dataentry.owner.details"
											text="Owner Details" />
									</a>
								</h4>
							</div>
							<div id="OwnerDetails">
								<div class="panel-body">

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csOname"><spring:message
												code="water.dataentry.owner.name" text="Owner Name" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												path="csmrInfo.csOname" id="csOname" disabled="true"
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
												disabled="true" path="csmrInfo.csOadd" id="csOadd"
												data-rule-required="true"></form:textarea>
										</div>
										<label class="col-sm-2 control-label required-control"
											for="opincode"><spring:message code="water.pincode" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" disabled="true"
												class="form-control hasNumber hideElement"
												path="csmrInfo.opincode" id="opincode" maxlength="6"
												data-rule-required="true"></form:input>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="mobileNo"><spring:message
												code="applicantinfo.label.mobile" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.csOcontactno" id="mobileNo" disabled="true"
												data-rule-required="true" data-rule-minlength="10"
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
						</div>

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
							<div id="ConsumerDetails" class="panel-collapse collapse">
								<div class="panel-body">
									<c:if test="${command.scrutinyFlag ne 'Y'}">
										<div class="form-group">

											<div class="col-sm-6">
												<label class="checkbox-inline" for="isConsumer"> <form:checkbox
														path="reqDTO.isConsumer" value="Y" id="isConsumer" /> <spring:message
														code="water.isConsumerSame"
														text="Is Applicant is Consumer?" />
												</label>
											</div>
										</div>
									</c:if>
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
												<form:textarea name="" type="text"
													class="form-control hasAddressClass" path="csmrInfo.csAdd"
													id="csAddress1"></form:textarea>
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
									<%-- <div class="form-group">
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

									</div> --%>

									<div class="form-group">
										<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.codDwzid"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass"
											disabled="${command.scrutinyFlag eq 'Y' ? true:false}" />
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
							<div id="BillingDetails" class="panel-collapse collapse">
								<div class="panel-body">
									<c:if test="${command.scrutinyFlag ne 'Y'}">
										<div class="form-group">
											<div class="col-sm-6">
												<label class="checkbox-inline" for="billing"> <form:checkbox
														path="reqDTO.isBillingAddressSame" value="Billing"
														id="billing" /> <spring:message
														code="water.isBillingSame" />
												</label>
											</div>
										</div>
									</c:if>
									<div id="hideBillingDetails">

										<div class="form-group">

											<label class="col-sm-2 control-label required-control"
												for="billingAreaName"><spring:message
													code="address.line1" text="Address Line1" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" class="form-control "
													path="csmrInfo.csBadd" id="billingAreaName"></form:input>
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
						</div>

						<%-- <div class="panel panel-default ownerDetails">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"
										href="#additionalOwner"> <spring:message
											code="water.additionalOwner" />
									</a>
								</h4>
							</div>
							<div id="additionalOwner" class="panel-collapse collapse">
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
						</div> --%>

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
							<div id="waterconnectiondetails" class="panel-collapse collapse">
								<div class="panel-body">




									<div class="form-group">
										<label class="col-sm-2 control-label required-control">
											<spring:message code="water.dataentry.consumer.type"
												text="Consumer type" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.typeOfApplication"
												class="form-control changeParameterClass"
												id="typeOfApplication" data-rule-required="true"
												disabled="${command.scrutinyFlag eq 'Y' ? true:false}">
												<form:option value="">
													<spring:message code="water.dataentry.select" />
												</form:option>
												<form:option value="P">
													<spring:message code="water.perm" />
												</form:option>
												<form:option value="T">
													<spring:message code="water.temp" />
												</form:option>
											</form:select>
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
									<div class="form-group">
										<label class="col-sm-2 control-label"> <spring:message
												code="water.dataentry.is.tax.payer"
												text="Is Income Tax Payer" />
										</label>
										<div class="col-sm-4">
											<form:select path="csmrInfo.csTaxPayerFlag"
												class="form-control changeParameterClass" id="taxPayerFlag"
												disabled="${command.scrutinyFlag eq 'Y' ? true:false}">
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
													id="panNo" onblur="fnValidatePAN(this)" maxlength="10"
													readonly="${command.scrutinyFlag eq 'Y' ? true:false}" />
											</div>
										</div>
									</div>

									<div class="form-group ">

										<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
											showOnlyLabel="false" pathPrefix="csmrInfo.trmGroup"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control changeParameterClass"
											disabled="${command.scrutinyFlag eq 'Y' ? true:false}" />

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

									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="csCcnsize"><spring:message
												code="water.dataentry.connection.size"
												text="Connection Size (in inches)" /></label>

										<div id="notBhagirathi">
											<div class="col-sm-4">
												<form:select path="csmrInfo.csCcnsize" class="form-control"
													id="withoutBhagiRathi"
													disabled="${command.scrutinyFlag eq 'Y' ? true:false}">
													<form:option value="">
														<spring:message code='master.selectDropDwn' />
													</form:option>
													<c:forEach items="${command.getLevelData('CSZ')}"
														var="lookup">
														<form:option value="${lookup.lookUpId}"
															code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										<div id="isBhagirathi">
											<div class="col-sm-4">
												<form:select path="csmrInfo.csCcnsize" class="form-control"
													id="withBhagiRathi"
													onchange="validateBhagirathiConnection();"
													disabled="${command.scrutinyFlag eq 'Y' ? true:false}">
													<form:option value="">
														<spring:message code='master.selectDropDwn' />
													</form:option>
													<c:forEach items="${command.getLevelData('CSZ')}"
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
										<label class="col-sm-2 control-label required-control"><spring:message
												code="" text="Illegal Connection From Period" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input type="text"
													class="form-control  mandColorClass datepicker"
													id="csIllegalDate" path="csmrInfo.csIllegalDate"
													onkeyup="clearInput($(this));" />
												<label class="input-group-addon" for="fromd"><i
													class="fa fa-calendar"></i><span class="hide">Date</span></label>
											</div>


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
												code="water.plumber.licno" /></label>
										<%-- <div class="col-sm-4">

											<form:input name="" type="text" class="form-control"
												path="reqDTO.plumberName" id="plumberName"></form:input>
										</div> --%>
										<div id="ulbPlumber">
											<div class="col-sm-4">
												<form:select path="csmrInfo.plumId" class="form-control"
													id="plumber">
													<form:option value="">
														<spring:message code="water.dataentry.select" />
													</form:option>
													<c:forEach items="${command.plumberList}" var="lookUp">
														<form:option value="${lookUp.plumId}">${lookUp.plumFname} ${lookUp.plumMname} ${lookUp.plumLname}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										<div id="licensePlumber">
											<div class="col-sm-4">
												<form:select path="csmrInfo.plumId" class="form-control"
													id="licPlumber">
													<form:option value="">
														<spring:message code="water.dataentry.select" />
													</form:option>
													<c:forEach items="${command.plumberList}" var="lookUp">
														<form:option value="${lookUp.plumId}">${lookUp.plumFname} ${lookUp.plumMname} ${lookUp.plumLname}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>





									</div>



								</div>
							</div>
						</div>
						<c:if test="${command.scrutinyFlag ne 'Y'}">

							<div class="padding-top-10 text-center" id="chekListChargeId">
								<button type="button" class="btn btn-success" id="proceedId"
									onclick="getChecklistAndCharges(this)">
									<spring:message code="water.btn.proceed" />
								</button>
								<button type="Reset" class="btn btn-warning" id=""
									onclick="window.location.href='IllegalToLegalConnection.html'">
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
													code="water.documentattchmnt" /><small class="text-blue-2"><spring:message
														code="uploadLimitMsg" /></small></a>
										</h4>
									</div>

									<div id="waterformappdetails"
										class="panel-collapse collapse in">
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
																			<td>${lookUp.doc_DESC_ENGL}</td>
																		</c:when>
																		<c:otherwise>
																			<td>${lookUp.doc_DESC_Mar}</td>
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

						</c:if>

					</div>



					<c:if test="${command.scrutinyFlag ne 'Y'}">

						<div class="text-center" id="divSubmit">
							<button type="button" class="btn btn-success"
								onclick="showViewFormJsp(this)" id="showView">
								<spring:message code="save.water.continue" />
							</button>

							<input type="button" class="btn btn-danger"
								onclick="window.location.href='AdminHome.html'"
								value="<spring:message code="water.btn.cancel"/>" />
						</div>
					</c:if>

					<c:if test="${command.scrutinyFlag eq 'Y'}">
						<div class="text-center">
							<button type="button" class="btn btn-blue-2"
								onclick="showConfirmBoxForSave(this)" id="submitdiv">
								<spring:message code="water.btn.submit" />
							</button>
							<input type="button" class="btn btn-danger"
								onclick="window.location.href='AdminHome.html'"
								value="<spring:message code="water.btn.cancel"/>" />
						</div>
					</c:if>

				</form:form>
			</div>
		</div>
	</div>
</div>
