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
<script type="text/javascript" src="js/water/waterDataEntry.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	

		/* if($('#hiddenConsumerSame').val()=="Y")
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
			 } */

			 if ($('#hiddenConsumerSame').val() == "Y") {
					$("#CounsumerFlag").attr("checked", true);
				} else {
					$("#CounsumerFlag").attr("checked", false);
				}

				var chkConsumer = $('#CounsumerFlag').is(':checked');
				if (chkConsumer) {
					$("#CounsumerFlag").attr("checked", true);
				} else {
					$("#CounsumerFlag").attr("checked", false);
				}
				if (chkConsumer) {

					$("#hideConsumerDetails").hide();
					$("#billingAreaName").data('rule-required', false);
					$("#billingPinCode").data('rule-required', false);
					$("#billingCityName").data('rule-required', false);

				} else {

					$("#hideConsumerDetails").show();
					$("#billingAreaName").data('rule-required', true);
					$("#billingPinCode").data('rule-required', true);
					$("#billingCityName").data('rule-required', true);
				}

				$("#CounsumerFlag").click(
						function() {
							var chk = $('#CounsumerFlag').is(':checked');
							if (chk) {

								$("#hideConsumerDetails").hide();
								$('#hiddenConsumerSame').val("Y");
								$("#cbillingAreaName").data(
										'rule-required', false);
								$("#cbillingPinCode").data('rule-required',
										false);
								$("#cbillingCityName").data(
										'rule-required', false);

							} else {
								$('#hiddenConsumerSame').val("N")
								$("#hideConsumerDetails").show();
								$("#cbillingAreaName").data(
										'rule-required', true);
								$("#cbillingPinCode").data('rule-required',
										true);
								$("#cbillingCityName").data(
										'rule-required', true);
							}

						});

				$("#isConsumer").click(
						function() {

							var chkConsumer = $('#isConsumer').is(
									':checked');
							if (chkConsumer) {
								$('#hiddenConsumerSame').val("Y");
								$("#hideConsumerDetails").hide();
								$("#cbillingAreaName").data(
										'rule-required', false);
								$("#cbillingPinCode").data('rule-required',
										false);

							} else {
								$('#hiddenConsumerSame').val("N");
								$("#hideConsumerDetails").show();
								$("#cbillingAreaName").data(
										'rule-required', true);
								$("#cbillingPinCode").data('rule-required',
										true);
							}

						});
		
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
						var cntConnection = $('#tbl2 tr').length-1;
						$("#addConnection").click(function(){
							debugger;
							 var checked = $('#ExistingConnection').val();
							 if(checked == "Y"){
								 var count=cntConnection-1;
									if($('#consumerNo'+count).val()!='')
								{
								 var row= 
									    '<td><input type="text" class="form-control hasNumber" name="csmrInfo.linkDetails['+cntConnection+'].lcOldccn" onblur="checkDuplicateCcn('+cntConnection+')" id="consumerNo'+cntConnection+'"></input></td>'+
									    '<td>  <c:set var="baseLookupCode" value="CSZ" />'
										+ '<select name="csmrInfo.linkDetails['+cntConnection+'].lcOldccnsize" id="connectionSize'+cntConnection+'" class="form-control">'
										+ '<option value="0">'
										+ "Select Connection Size"
										+ '</option>'
										+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
										+ '		<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
										+ '</c:forEach>'
										+ '</select></td>'+
									   '<td><input  type="text" class="form-control" name="csmrInfo.linkDetails['+cntConnection+'].lcOldtaps"  id="noOfTaps'+cntConnection+'"></input></td>';
								  $('#tbl2 tr').last().after('<tr id="row'+cntConnection+'" class="appendableClass">'+row+'<td><a data-placement="top" title="Delete" class="btn btn-danger btn-sm" data-original-title="Delete Connection" id="deleteConnection" onclick="removeConnection('+cntConnection+')"><i class="fa fa-trash"></i></a></td></tr>');
									cntConnection++;
									reorderConnection();
                              }
								else
									{
									showErrormsgboxTitle("water.dataentry.old.con.num.empty");
									}
								
							}
						});
						 
						
					});


</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.data.entry" />
			</h2>
			<div class="additional-btn">
				<apptags:helpDoc url="WaterDataEntrySuite.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<form:form action="WaterDataEntrySuite.html"
				class="form-horizontal form" name="waterDataEntry"
				id="waterDataEntry">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<form:hidden path="isBillingSame" id="hiddenBillingSame" />
				<form:hidden path="isConsumerSame" id="hiddenConsumerSame" />
				<form:hidden path="" id="modeType" value="${command.modeType}" />
				<form:hidden path="" id="propNoOptionalFlag"  value="${command.propNoOptionalFlag}"/>

				<div class="panel-group accordion-toggle margin-bottom-0"
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
									<c:if test="${command.propNoOptionalFlag eq 'Y' }">
										<label class="col-sm-2 control-label"><spring:message
												code="water.dataentry.property.number"
												text="Property number" /></label>
									</c:if>
									<c:if test="${command.propNoOptionalFlag eq 'N' }">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.dataentry.property.number"
												text="Property number" /></label>
									</c:if>

									<%-- <div class="col-sm-2">
										<form:input type="text" class="form-control"
											 onblur="getPropertyDetails(this)"
											path="newConnectionDto.propertyNo" id="propertyNo"></form:input>
									</div> --%>
									<div class="col-sm-3">
										<form:input name="" type="text" class="form-control"
											path="newConnectionDto.propertyNo" id="propertyNo"></form:input>
									</div>


									<div class="col-sm-2">
										<button type="button" class="btn btn-success"
											onclick="getPropertyDetails(this)" id="searchPropNum">
											<strong class="fa fa-search"></strong>
											<spring:message code="water.search" />
										</button>

										<button type="button" class="btn btn-warning"
											id="resetConnection" onclick="resetConnDetails()">
											<spring:message code="rstBtn" />
										</button>

									</div>
									<label class="col-sm-2 control-label required-control">
										<spring:message code="water.dataentry.bhagirathi.connection"
											text="Is Bhagirathi connection" />
									</label>
									<div class="col-sm-3">
										<form:select path="csmrInfo.bplFlag"
											class="form-control changeParameterClass" id="bplNo"
											data-rule-required="true">
											<form:option value="">
												<spring:message code="water.dataentry.select" text="Select" />
											</form:option>
											<form:option value="Y">
												<spring:message code="water.yes" text="Yes" />
											</form:option>
											<form:option value="N" selected="selected">
												<spring:message code="water.no" text="No" />
											</form:option>
										</form:select>
									</div>

								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="" text="Property Usage Type" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="csmrInfo.propertyUsageType" id="propertyUsageType"
											readonly="true"></form:input>
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
									<%-- <label class="col-sm-2 control-label" for="csOcityName"><spring:message
											code="water.dataentry.district" text="District" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control" disabled="true"
											path="csmrInfo.csOrdcross" id="csOcityName"></form:input>
									</div> --%>
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
									<label class="col-sm-2 control-label" for="adharNo"><spring:message
											code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control hasNumber"
											readonly="true" path="newConnectionDto.applicantDTO.aadharNo"
											id="aadharNo" maxlength="12" minlength="12"></form:input>
									</div>
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
											path="csmrInfo.csOname" id="csOname" readonly="true"
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
											readonly="true" path="csmrInfo.csOadd" id="csOadd"
											data-rule-required="flase"></form:textarea>
									</div>
									<%-- <label class="col-sm-2 control-label" for="csOcityName"><spring:message
											code="water.dataentry.district" text="District" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control" disabled="true"
											path="csmrInfo.csOrdcross" id="csOcityName"></form:input>
									</div> --%>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"
										for="opincode"><spring:message code="water.pincode" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" readonly="true"
											class="form-control hasNumber hideElement"
											path="csmrInfo.opincode" id="opincode" maxlength="6"
											data-rule-required="false"></form:input>
									</div>
									<label class="col-sm-2 control-label" for="adharNo"><spring:message
											code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control hasNumber"
											readonly="true" path="newConnectionDto.applicantDTO.aadharNo"
											id="aadharNo" maxlength="12" minlength="12"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"
										for="mobileNo"><spring:message
											code="applicantinfo.label.mobile" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="csmrInfo.csOcontactno" id="mobileNo" readonly="true"
											data-rule-required="false" data-rule-minlength="10"
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
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 id="consumerDiv" class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#consumerDetails"> <spring:message
										code="water.dataentry.consumer.details"
										text="Consumer Details" />
								</a>
							</h4>
						</div>

						<div id="consumerDetails">
							<div class="panel-body">
								<div class="form-group">
									<div class="col-sm-6">
										<label class="checkbox-inline" for="CounsumerFlag"> <form:checkbox
												path="newConnectionDto.isConsumer" value="N"
												id="CounsumerFlag" /> <spring:message
												code="water.dataentry.is.consumer"
												text="Is consumer Details is same as owner Details" />
										</label>
									</div>
								</div>
								<div id="hideConsumerDetails">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="firstName"><spring:message
												code="water.dataentry.consumer.name" text="Consumer Name" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control"
												readonly="${disabled}" path="csmrInfo.csName"
												id="csFirstName" data-rule-required="true"></form:input>
										</div>
										<%-- <label class="col-sm-2 control-label" for="gender"><spring:message
												code="applicantinfo.label.gender" /></label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="csmrInfo.csGender" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="false" /> --%>
										<label class="col-sm-2 control-label required-control"
											for="cbillingAreaName"><spring:message
												code="water.dataentry.address" text="Address" /></label>
										<div class="col-sm-4">
											<form:textarea name="" type="text" class="form-control "
												path="csmrInfo.csAdd" id="csAddress1"></form:textarea>
										</div>
									</div>

									<div class="form-group">

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
												class="form-control hasNumber hideElement"
												path="csmrInfo.csCpinCode" id="csPinCode" maxlength="6"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="cSaadharNo"><spring:message
												code="water.dataentry.aadharNo" text="Aadhar Number" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text"
												class="form-control hasNumber" path="csmrInfo.csUid"
												id="cSaadharNo" maxlength="12" minlength="12"></form:input>
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
											<form:input type="text" class="form-control"
												path="csmrInfo.csEmail" id="csEmailId"
												data-rule-email="true"></form:input>
										</div>
									</div>
								</div>
							</div>
							<spring:eval
								expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('ARV','WEV',${UserSession.organisation}).getOtherField()"
								var="arvOtherField" />
							<c:if test="${arvOtherField eq 'Y' }">
								<div class="form-group">
									<label class="col-sm-2 control-label" for=""><spring:message
											code="" text="ARV" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control hasNumber"
											path="csmrInfo.arv" id="arv"></form:input>
									</div>

									<spring:eval
										expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('PTI','WEV',${UserSession.organisation}).getOtherField()"
										var="pTinOtherField" />
									<c:if test="${pTinOtherField eq 'Y' }">
										<label class="col-sm-2 control-label" for=""><spring:message
												code="" text="PTIN" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												path="csmrInfo.ptin" id="ptin"></form:input>
										</div>
									</c:if>
								</div>
							</c:if>
							<div class="form-group">
								<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
									showOnlyLabel="false" pathPrefix="csmrInfo.codDwzid"
									isMandatory="true" hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true"
									cssClass="form-control changeParameterClass" />
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#BillingDetails">
									<spring:message code="water.owner.details.buldingadd" />
								</a>
							</h4>
						</div>

						<div id="BillingDetails">
							<div class="panel-body">
								<div class="form-group">
									<div class="col-sm-6">
										<label class="checkbox-inline" for="billing"> <form:checkbox
												path="newConnectionDto.isBillingAddressSame" value="Billing"
												id="billing" /> <spring:message code="water.isBillingSame" />
										</label>
									</div>
								</div>

								<div id="hideBillingDetails">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="billingAreaName"><spring:message
												code="water.dataentry.address" text="Address" /></label>
										<div class="col-sm-4">
											<form:textarea name="" type="text" class="form-control "
												path="csmrInfo.csBadd" id="billingAreaName"></form:textarea>
										</div>
										<%-- <label class="col-sm-2 control-label" for="billingRoadName"><spring:message
												code="water.dataentry.district" text="Disctrict" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" class="form-control "
												path="csmrInfo.csBrdcross" id="billingRoadName"></form:input>
										</div> --%>

										<label class="col-sm-2 control-label required-control"
											for="billingPinCode"><spring:message
												code="water.pincode" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text"
												class="form-control hasNumber hideElement"
												path="csmrInfo.bpincode" id="billingPinCode" maxlength="6"></form:input>
										</div>
									</div>

									<%-- <div class="form-group">
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

						<div id="waterconnectiondetails">
							<div class="panel-body">
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
										<label class="col-sm-2 control-label required-control">
											<spring:message code="water.dataentry.existing.connection"
												text="Is Existing Connection" />
										</label>
										<div class="col-sm-4">
											<form:select path="newConnectionDto.existingConsumerNumber"
												class="form-control changeParameterClass"
												id="ExistingConnection" data-rule-required="true">
												<form:option value="">
													<spring:message code="water.dataentry.select" text="Select" />
												</form:option>
												<form:option value="Y">
													<spring:message code="water.yes" text="Yes" />
												</form:option>
												<form:option value="N">
													<spring:message code="water.no" text="No" />
												</form:option>
											</form:select>
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
											<label class="input-group-addon" for="fromdate"><i
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
											<label class="input-group-addon" for="todate"><i
												class="fa fa-calendar"></i><span class="hide"><spring:message
														code="demand.date" /></span></label>
										</div>
									</div>
								</div>

								<div class="panel-body">
									<div class="table-responsive">
										<table class="table table-bordered" id="tbl2">
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
											<c:choose>
												<c:when test="${empty command.csmrInfo.linkDetails}">
													<tr id="row0" class="appendableClass">
														<td><form:input type="text"
																class="form-control hasNumber"
																path="csmrInfo.linkDetails[0].lcOldccn"
																onblur="checkDuplicateCcn(0)" id="consumerNo0"></form:input></td>
														<td><c:set var="baseLookupCode" value="CSZ" /> <form:select
																path="csmrInfo.linkDetails[0].lcOldccnsize"
																class="form-control" id="connectionSize0"
																onfocus="this.oldIndex=this.selectedIndex"
																onchange="this.selectedIndex=this.oldIndex">
																<form:option value="0">
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
																	class="form-control hasNumber"
																	path="csmrInfo.linkDetails[${status.count-1}].lcOldccn"
																	onblur="checkDuplicateCcn(${status.count-1})"
																	id="consumerNo${status.count-1}" readonly="true"></form:input></td>
															<td><c:set var="baseLookupCode" value="CSZ" /> <form:select
																	path="csmrInfo.linkDetails[${status.count-1}].lcOldccnsize"
																	class="form-control"
																	onfocus="this.oldIndex=this.selectedIndex"
																	onchange="this.selectedIndex=this.oldIndex"
																	id="connectionSize${status.count-1}" readonly="true">
																	<form:option value="0">
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
																	id="noOfTaps${status.count-1}" readonly="true"></form:input></td>
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
											class="form-control changeParameterClass" id="taxPayerFlag">
											<form:option value="">
												<spring:message code="water.dataentry.select" text="Select" />
											</form:option>
											<form:option value="Y">
												<spring:message code="water.yes" text="Yes" />
											</form:option>
											<form:option value="N" selected="selected">
												<spring:message code="water.no" text="No" />
											</form:option>
										</form:select>
									</div>
									<div id="pandiv1" class="pan_element">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.dataentry.pan.number" text="PAN Number" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text"
												class="form-control required-control"
												data-rule-minlength="10" path="csmrInfo.csPanNo" id="panNo"
												onblur="fnValidatePAN(this)" maxlength="10" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="label-control col-sm-2 required-control"><spring:message
											code="con.physical.date" /></label>
									<div class="col-sm-4">
										<form:input path="csmrInfo.pcDate" maxlength="10"
											cssClass="installdatepicker cal form-control" id="pcDate" />
									</div>
									<label class="col-sm-2 control-label required-control"
										for="csCcnsize"><spring:message
											code="water.dataentry.connection.size"
											text="Connection Size (in inches)" /></label>
									<c:set var="baseLookupCode" value="CSZ" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="csmrInfo.csCcnsize"
										cssClass="form-control changeParameterClass"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="eip.select" isMandatory="true" />
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2 required-control"><spring:message
											code="MeterReadingDTO.meterType" /></label>
									<div>
										<c:set var="baseLookupCode" value="WMN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="csmrInfo.csMeteredccn" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="eip.select" isMandatory="true"
											changeHandler="meterDetail()" />
									</div>

									<label class="control-label col-sm-2 required-control"><spring:message
											code="con.status" /></label>
									<div>
										<c:set var="baseLookupCode" value="CNS" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="csmrInfo.csCcnstatus" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="eip.select" isMandatory="true" />
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
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default" id="meterDetails">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#watermeterdetails"> <spring:message
										code="water.dataentry.meter.details" text="Meter Details" />
								</a>
							</h4>
						</div>

						<div id="watermeterdetails">
							<div class="panel-body">
								<div class="form-group">
									<label class="label-control col-sm-2"><spring:message
											code="water.meterDet.mtrMake" /></label>
									<div class="col-sm-4">
										<form:input path="meterData.meterMake"
											cssClass="form-control hasCharacter" maxlength="39" />
									</div>
									<label class="label-control col-sm-2 required-control"><spring:message
											code="water.meterDet.metNo" /></label>
									<div class="col-sm-4">
										<form:input path="meterData.meterNumber"
											cssClass="form-control mandClassColor hasNumber"
											maxLength="19" />
									</div>
								</div>

								<div class="form-group">
									<label class="label-control col-sm-2 required-control"><spring:message
											code="water.meterDet.initalmtrRead" /></label>
									<div class="col-sm-4">
										<form:input path="meterData.initialMeterReading"
											placeholder="999999"
											cssClass="form-control mandClassColor hasNumber "
											maxLength="7" />
									</div>
									<label class="label-control col-sm-2 required-control"><spring:message
											code="water.meterDet.mtrMaxRead" /></label>
									<div class="col-sm-4">
										<form:input path="meterData.meterMaxReading"
											placeholder="999999" cssClass="form-control hasNumber "
											maxLength="7" />
									</div>
								</div>


								<div class="form-group">
									<label class="label-control col-sm-2"><spring:message
											code="water.meterDet.mtrCost" /></label>
									<div class="col-sm-4">
										<form:input cssClass="form-control mandColorClass text-right"
											onkeypress="return hasAmount(event, this, 15, 2)"
											id="totalplot" path="meterData.meterCost"
											placeholder="999999.99"
											onchange="getAmountFormatInDynamic((this),'totalplot')"
											data-rule-required="true"></form:input>
									</div>
									<label class="label-control col-sm-2 required-control"><spring:message
											code="water.meterDet.ownerShip" /></label>
									<c:set var="baseLookupCode" value="WMO" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="meterData.meterOwnerShip" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="eip.select" isMandatory="true" />
								</div>

								<div class="form-group">
									<label class="label-control col-sm-2 required-control"><spring:message
											code="water.meterDet.instlDt" /></label>
									<div class="col-sm-4">
										<form:input path="meterData.meterInstallationDate"
											maxlength="10" cssClass="datepicker cal form-control"
											id="meterInstallationDate" />
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
									href="#waterdistributiondetails"> <spring:message
										code="water.dataentry.distribution.details"
										text="Distribution Line Details" />
								</a>
							</h4>
						</div>

						<div id="waterdistributiondetails">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="water.dataentry.distribution.main.number"
											text="Distribution Main Line Number" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control"
											path="csmrInfo.distributionMainLineNumber"></form:input>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.dataentry.distribution.main.name"
											text="Distribution Main Line Name" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="csmrInfo.distributionMainLineName"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label "><spring:message
											code="water.dataentry.distribution.child.number"
											text="Distribution Child Line Number" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control"
											path="csmrInfo.distributionChildLineNumber"></form:input>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.dataentry.distribution.child.name"
											text="Distribution Child Line Name" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="csmrInfo.distributionChildLineName"></form:input>
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
									href="#waterdepositdetails"> <spring:message
										code="water.dataentry.security.deposit.details"
										text="Security Deposit Details" />
								</a>
							</h4>
						</div>

						<div id="waterdepositdetails">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="water.deposit.amount" /></label>
									<div class="col-sm-4">
										<form:input id="round" name="" type="text"
											class="form-control text-right"
											onkeypress="return hasAmount(event, this, 15, 2)"
											data-rule-required="true" path="csmrInfo.depositAmount"
											placeholder="999999.99"
											onchange="getAmountFormatInDynamic((this),'round')"></form:input>
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="water.ReceiptNo" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="csmrInfo.receiptNumber"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="label-control col-sm-2"><spring:message
											code="water.deposit.amount.date" /></label>
									<div class="col-sm-4">
										<form:input path="csmrInfo.depositDate" readonly="true"
											cssClass="Mostdatepicker cal form-control" id="depositDate" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<form:hidden path="modeType" id="modeType" />
					<div class="text-center" id="saveData">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveFormWithOutArrears(this)" id="submit">
							<spring:message code="water.dataentry.save" text="Save" />
						</button>

						<button type="button" class="btn btn-success btn-submit"
							onclick="saveWaterDataEntry(this)" id="submit">
							<spring:message code="water.dataentry.add.to.arrears"
								text="Add to Arrears" />
						</button>

						<input type="button" class="btn btn-danger"
							onclick="window.location.href='WaterDataEntrySuite.html'"
							value="<spring:message code="water.btn.cancel"/>" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
