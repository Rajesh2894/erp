
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/masters/vendorMaster/vendorMaster.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
	
<script>
	$(document).ready(
			function() {
				
				jQuery(function($) {
					$.mask.definitions['~'] = '[+-]';
					$('#vendor_vmuidno').mask('9999 9999 9999');

				});

				$("input").on("keypress", function(e) {
					if (e.which === 32 && !this.value.length)
						e.preventDefault();
				});

				
				$("#MSMENo").hide();
				var cpdVendorSubType = $("#cpdVendorSubType option:selected").attr("code");
				if(cpdVendorSubType=="MSME"||cpdVendorSubType=='MSME'){
					$("#MSMENo").show();
				}
				$("#cpdVendortype")
						.change(
								function() {
									debugger;
									var vendorCode = $(
											"#cpdVendortype option:selected")
											.attr("code");

									if (vendorCode == 'E') {
										$('#emp_IdDiv').show();
										$('#vendorvmvendorname').hide();
										$('#emp_Idl').show();
										$('#vendorvmvendornamel').hide();

									} else {

										$('#emp_IdDiv').hide();
										$('#vendorvmvendorname').show();
										$('#emp_Idl').hide();
										$('#vendorvmvendornamel').show();
									}
validTenant(vendorCode);
								});

				if ($('#sliMode').val() == "L") {

					if ($('#form_AddEditMode').val() == "create") {
						$('#listOfTbAcFunctionMasterItems').prop('disabled',
								false).trigger("chosen:updated");
						$('#functionId').prop('disabled', false).trigger(
								"chosen:updated");
					}

					$('#priHeadDiv').show();
					//$('#priHeadLbl').addClass('required-control');
				} else {
					$('#priHeadDiv').hide();
					$('#listOfTbAcFunctionMasterItems').prop('disabled', true)
							.trigger("chosen:updated");
					$('#functionId').prop('disabled', true).trigger(
							"chosen:updated");
				}
var vendorCode = $(
					"#cpdVendortype option:selected")
					.attr("code");
				 validTenant(vendorCode);
			});
	$('#emp_IdDiv').hide();
	$('#emp_Idl').hide();
	
	function validTenant(vendorCode){
	
		 if(vendorCode == 'T' || ($('#sudaEnv').val() == "true"||$('#sudaEnv').val() == 'true')){
			 	$('.required-control').next().children().removeClass('mandColorClass').attr("required", false);			
				$("#functionIdlbl").removeClass( "required-control")		
				$('.required-control').next().children().removeClass('mandColorClass').attr("required", false);			
				$("#listOfTbAcFunctionMasterItemslbl").removeClass( "required-control")		
				
	if (($('#sudaEnv').val() == "true" || $('#sudaEnv').val() == 'true')) {
				$("#listOfTbAcFunctionMasterItemslbl").addClass(
						"required-control");
				$("#listOfTbAcFunctionMasterItems").addClass("mandColorClass");
			}
		} else {
			$("#functionIdlbl").addClass("required-control");
			$("#functionId").addClass("mandColorClass");
			$("#listOfTbAcFunctionMasterItemslbl").addClass("required-control");
			$("#listOfTbAcFunctionMasterItems").addClass("mandColorClass");
		}
	}
	function showConfirmBox() {
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = getLocalMessage('account.proceed.btn');
		var savSucc = getLocalMessage('account.record.saved');

		message += '<div class="form-group"><p>' + savSucc + '</p>';
		message += '<p style=\'text-align:center;margin: 5px;\'>'
				+ '<br/><input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="proceed()"/>' + '</p></div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}

	function proceed() {
		window.location.href = "javascript:openRelatedForm('Vendormaster.html');";
	}

	function setEmployeeName() {
		var employeeName = $("#emp_Id option:selected").text();
		$("#vendor_vmvendorname").val(employeeName);
		$("#vendor_vmVendornamePayto").val(employeeName);
	}

	function setpayTo() {
		if ($(vendor_vmVendornamePayto).val().length == 0) {
			var payTocopy = document.getElementById("vendor_vmvendorname").value;
			document.getElementById("vendor_vmVendornamePayto").value = payTocopy;
		}
	}
</script>

<c:if test="${ShowBreadCumb ==false}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>
   <div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
<!-- Start info box -->
<div class="widget">
	<div class="widget-header" id="hiddenDiv">
		<h2>
			<spring:message code="accounts.vendor.master" text="Vendor Master" />
		</h2>
	</div>
	<div class="widget-content padding">
		<c:url value="${saveAction}" var="url_form_submit" />
		<c:url value="${mode}" var="form_mode" />

		<%-- <form:form class="form-horizontal" modelAttribute="tbAcVendormaster"
			cssClass="form-horizontal" method="POST" action="${url_form_submit}"
			id="vendorMasterFrm"> --%>
		<form:form class="form-horizontal" modelAttribute="tbAcVendormaster"
			cssClass="form-horizontal" method="POST" action="Vendormaster.html"
			id="vendorMasterFrm">
		<input type="hidden" value="${url_form_submit}" id="formAction">
			<div class="alert alert-danger alert-dismissible error-div"
				id="vendorErrorDiv"></div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<i class='fa fa-exclamation-circle'></i> <span id="errorId"></span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />

			<c:if test="${mode == 'View'}">
				<SCRIPT type="text/javascript">
					$(document)
							.ready(
									function() {
										$('.error-div').hide();
										$('#editOriew').find('*').attr(
												'disabled', 'disabled');
										$('#editOriew').find('*').addClass(
												"disablefield");
										$('#vendor_vmvendorcode').attr(
												'disabled', 'disabled');
										$('#vendor_vmvendorcode').addClass(
												"disablefield");
										$('#vendor_vmVendornamePayto').attr(
												'disabled', 'disabled');
										$('#vendor_vmVendornamePayto')
												.addClass("disablefield");
										$('#vendor_vendortype').attr(
												'disabled', 'disabled');
										$('#vendor_vendortype').addClass(
												"disablefield");
										$('#vendor_vmvendorname').attr(
												'disabled', 'disabled');
										$('#vendor_vmvendorname').addClass(
												"disablefield");
										$('#vendor_vmpannumber').attr(
												'disabled', 'disabled');
										$('#vendor_vmpannumber').addClass(
												"disablefield");
										$('#vendor_mobileNo').attr('disabled',
												'disabled');
										$('#vendor_mobileNo').addClass(
												"disablefield");
										$('#vendoremailId').attr('disabled',
												'disabled');
										$('#vendoremailId').addClass(
												"disablefield");
										$('#vendor_pfAcNumber').attr('disabled',
												'disabled');
										$('#vendor_pfAcNumber').addClass(
												"disablefield");
										$('#cbbankName').attr('disabled',
												'disabled');
										$('#cbbankName').addClass(
												"disablefield");
										$('#vendor_rtgsvendorflag').attr(
												'disabled', 'disabled');
										$('#vendor_rtgsvendorflag').addClass(
												"disablefield");
										$('#vmCpdStatus').attr('disabled',
												'disabled');
										$('#vmCpdStatus').addClass(
												"disablefield");
										$('#vendor_ifsccode').attr('disabled',
												'disabled');
										$('#vendor_ifsccode').addClass(
												"disablefield");
										$('#vendor_bankaccountnumber').attr(
												'disabled', 'disabled');
										$('#vendor_bankaccountnumber')
												.addClass("disablefield");
										$('#vendor_remark').attr('disabled',
												'disabled');
										$('#vendor_remark').addClass(
												"disablefield");
										$('#cpdVendortype').attr('disabled',
												'disabled');
										$('#cpdVendortype').addClass(
												"disablefield");
										$('#cpdVendorSubType').attr('disabled',
												'disabled');
										$('#cpdVendorSubType').addClass(
												"disablefield");
										$('#vendor_tinnumber').attr('disabled',
												'disabled');
										$('#vendor_tinnumber').addClass(
												"disablefield");
										$('#vendor_tinnumber').attr('disabled',
												'disabled');
										$('#vendor_tinnumber').addClass(
												"disablefield");
										$('#vendor_vmuidno').attr('disabled',
												'disabled');
										$('#vendor_vmuidno').addClass(
												"disablefield");
										$('#vendor_vvmvendoradd').attr(
												'disabled', 'disabled');
										$('#vendor_vvmvendoradd').addClass(
												"disablefield");
										$('#vendor_cpdVendorSubType').attr(
												'disabled', 'disabled');
										$('#vendor_cpdVendorSubType').addClass(
												"disablefield");
										$('#vendor_rtgsvendorflag').attr(
												'disabled', 'disabled');
										$('#vendor_rtgsvendorflag').addClass(
												"disablefield");
										$('#isdeleted').attr('disabled',
												'disabled');
										$('#isdeleted')
												.addClass("disablefield");
									
									});
				</SCRIPT>
			</c:if>
			<c:if test="${mode == 'update'}">
				<SCRIPT type="text/javascript">
					$(document).ready(function() {
						vendor_vmvendorname.readOnly = false;
					});
				</SCRIPT>
			</c:if>


			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
			<form:hidden path="vmVendorid" id="vendorId" />
			<form:hidden path="userId" />
			<form:hidden path="langId" />
			<form:hidden path="orgid" />
			<form:hidden path="vmPanNumberTemp" />
			<input type="hidden" id="form_AddEditMode" value="${form_mode}">
			<form:hidden path="sliMode" id="sliPrefixMode" />
			<form:hidden path="removeFileById" id="removeFileById" />
			<form:hidden path="" id="sudaEnv" value="${SudaEnv}" />
			<%-- <form:hidden path="rtgsvendorflag" id="rtgsvendorflag"/> --%>

			<c:if test="${form_mode ne 'create' }">
				<div class="form-group">

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="accounts.vendormaster.vendorcode"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="vendor_vmvendorcode" path="vmVendorcode"
							class="form-control" maxLength="20" readonly="true"
							data-rule-required="true" />
					</div>

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="accounts.vendormaster.vendorStatus"></spring:message>
					</label>
					<div class="col-sm-4">
						<c:choose>
							<c:when test="${form_mode eq 'create' }">
								<form:select path="vmCpdStatus" cssClass="form-control"
									readonly="true" data-rule-required="true">
									<c:forEach items="${vendorStatusAcIn}" var="lookup">
										<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}	</form:option>
									</c:forEach>
								</form:select>
							</c:when>
							<c:otherwise>
								<form:select path="vmCpdStatus" cssClass="form-control"
									data-rule-required="true">
									<c:forEach items="${vendorStatusAcIn}" var="lookup">
										<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}	</form:option>
									</c:forEach>
								</form:select>
							</c:otherwise>
						</c:choose>

					</div>

				</div>
			</c:if>
				
				<div class = "form-group">
				<label class="control-label col-sm-2"><spring:message
						code="account.pfms.id" text="PFMS Vendor ID" /></label>
				<div class="col-sm-4">
					<form:input id="pfmsVendorId" path="pfmsVendorId" class="form-control"
						maxLength="20"  />
				</div>
				</div>
			<div class="form-group">

				<label class="control-label col-sm-2 required-control"> <spring:message
						code="accounts.vendormaster.vendortype"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:select id="cpdVendortype" path="cpdVendortype"
						class="form-control chosen-select-no-results"
						data-rule-required="true"
						onchange="getVendorTypeWisePrimaryAcHead(this)">
						<form:option value="">
							<spring:message code="master.selectDropDwn" />
						</form:option>
						<c:forEach items="${venderType}" varStatus="status"
							var="levelParent">
							<c:if test="${userSession.languageId eq 1}">
							<form:option code="${levelParent.lookUpCode}"
								value="${levelParent.lookUpId}">${levelParent.descLangFirst}</form:option>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
							<form:option code="${levelParent.lookUpCode}"
								value="${levelParent.lookUpId}">${levelParent.descLangSecond}</form:option>
							</c:if>
						</c:forEach>
					</form:select>
				</div>

				<label class="control-label col-sm-2 required-control"> <spring:message
						code="accounts.vendormaster.vendorSubType"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:select id="cpdVendorSubType" path="cpdVendorSubType"
						class="form-control chosen-select-no-results"
						data-rule-required="true" onchange="populateMSME(this)">
						<form:option value="">
							<spring:message code="master.selectDropDwn" />
						</form:option>
						<c:forEach items="${vendorStatus}" varStatus="status"
							var="levelChild">
							<c:if test="${userSession.languageId eq 1}">
							<form:option code="${levelChild.lookUpCode}"
								value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
							<form:option code="${levelChild.lookUpCode}"
								value="${levelChild.lookUpId}">${levelChild.descLangSecond}</form:option>
							</c:if>
						</c:forEach>
					</form:select>
				</div>


			</div>
			<div class="form-group" id="MSMENo">
				<label class="control-label col-sm-2"> <spring:message
						code="accounts.vendormaster.msmeNo" text="MSME No."></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="vendor_msmeNo" path="msmeNo"
						class="form-control " maxlength="20"  />
				</div>


			</div>
			<div class="form-group">

				<label id=emp_Idl class="control-label col-sm-2"><spring:message
						code="accounts.chequebookleaf.employee_name"></spring:message><span
					class="mand">*</span></label>
				<div id=emp_IdDiv class="col-sm-4">
					<form:select id="emp_Id" path="empId" class="form-control"
						onchange="setEmployeeName()">
						<form:option value="">
							<spring:message code="master.selectDropDwn"></spring:message>
						</form:option>>
							<c:forEach items="${emplist}" var="empMstData">
							<form:option value="${empMstData.empId}">${empMstData.empname}</form:option>
								${vendor_vmvendorname}=${empMstData.empname}
							</c:forEach>
					</form:select>
				</div>

				<label id=vendorvmvendornamel
					class="control-label col-sm-2 required-control"> <spring:message
						code="accounts.vendormaster.vendorName"></spring:message>
				</label>
				<div id=vendorvmvendorname class="col-sm-4">
					<form:input id="vendor_vmvendorname" path="vmVendorname"
						class="form-control" data-rule-required="true"
						data-rule-maxLength="200" onchange="setpayTo()" />

				</div>

				<label class="control-label col-sm-2 "> <spring:message
						code="accounts.vendormaster.payTo"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="vendor_vmVendornamePayto" path="vmVendornamePayto"
						class="form-control" maxLength="200" />
				</div>


			</div>

			<div class="form-group">
				<label class="control-label col-sm-2"> <spring:message
						code="accounts.vendormaster.mobileNumber"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="vendor_mobileNo" path="mobileNo"
						class="form-control hasMobileNo" data-rule-number="10"
						data-rule-minLength="10" data-rule-maxLength="10" />
				</div>
				<label class="control-label col-sm-2"> <spring:message
						code="accounts.vendormaster.emailId"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="vendoremailId" path="emailId" class="form-control"
						data-rule-email="true" />

				</div>

			</div>




			<div class="form-group">
				<label class="control-label col-sm-2 "> <spring:message
						code="accounts.vendormaster.aadharNo"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input path="vmUidNo" id="vendor_vmuidno"
						cssClass="form-control" maxlength="14" />
				</div>

				<label class="control-label col-sm-2"><spring:message
						code="accounts.vendormaster.gstno" text="GST Number" /></label>
				<div class="col-sm-4">
					<form:input id="gstNumber" path="vmGstNo" class="form-control"
						maxLength="15" placeholder="Ex: 22AAAAA0000A1Z5" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2"> <spring:message
						code="accounts.vendormaster.tinNo">
					</spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="vendor_tinnumber" path="tinNumber"
						class="form-control" maxLength="20" />
				</div>
				<label class="control-label col-sm-2 "> <spring:message
						code="accounts.vendormaster.panNo">
					</spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="vendor_vmpannumber" path="vmPanNumber"
						class="form-control text-uppercase " maxLength="10"
						onchange="fnValidatePAN(this)" />
				</div>
			</div>


			<div class="form-group">
				<label class="control-label col-sm-2"> <spring:message
						code="accounts.vendormaster.bankname">
					</spring:message><!-- <span class="mand">*</span> -->
				</label>

				<div class="col-sm-4">
					<form:select id="bankId" path="bankId" 
						class="form-control chosen-select-no-results" data-rule-required="false">
						<form:option value="">
							<spring:message code="master.selectDropDwn" />
						</form:option>
						<c:forEach items="${custBankList}" var="bankId">
							<form:option value="${bankId.key}">${bankId.value}</form:option>
						</c:forEach>
					</form:select>
				</div>

				<label class="control-label col-sm-2"> <spring:message
						code="accounts.vendormaster.bankAccountNumber"></spring:message>
						<!-- <span class="mand">*</span> -->
				</label>
				<div class="col-sm-4">
					<form:input id="vendor_bankaccountnumber" path="bankaccountnumber"
						class="form-control hasNumber" maxLength="20" data-rule-required="false" />
				</div>


			</div>

			<div class="form-group">

				<c:if test="${functionStatus == 'Y'}">
					<!-- Defect #91157 start-->
					<c:if test="${form_mode eq 'create' ||  empty tbAcVendormaster.functionId}">
						<label class="control-label col-sm-2 required-control" id="functionIdlbl"
							for="functionId"><spring:message
								code="account.budget.code.master.functioncode" /></label>

						<div class="col-sm-4">
							<form:select id="functionId" path="functionId"
								cssClass="form-control mandColorClass chosen-select-no-results"
								>
								<form:option value="">
									<spring:message code="master.selectDropDwn" text="Select" />
								</form:option>
								<c:forEach items="${listOfTbAcPrimaryMasterItems}"
									varStatus="status" var="functionItem">
									<form:option value="${functionItem.key}"
										code="${functionItem.key}">${functionItem.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>

					<c:if test="${form_mode ne 'create' && not  empty tbAcVendormaster.functionId}">
						<label class="control-label col-sm-2 required-control" id="functionIdlbl"
							for="functionId"><spring:message
								code="account.budget.code.master.functioncode" /></label>

						<div class="col-sm-4">
							<form:hidden path="functionId" id="functionid" />
							<form:select id="functionId" path="functionId"
								cssClass="form-control mandColorClass chosen-select-no-results"
								 disabled="true">
								<form:option value="">
									<spring:message code="master.selectDropDwn" text="Select" />
								</form:option>
								<c:forEach items="${listOfTbAcPrimaryMasterItems}"
									varStatus="status" var="functionItem">
									<form:option value="${functionItem.key}"
										code="${functionItem.key}">${functionItem.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</c:if>

				</c:if>

				<c:if test="${form_mode eq 'create' || empty tbAcVendormaster.pacHeadId}">

					<input type="hidden" value="${sliMode}" id="sliMode" />
					<div id="priHeadDiv">
						
								<label class="control-label col-sm-2 required-control"
									for="listOfTbAcFunctionMasterItems" id=""><spring:message
										code="accounts.Secondaryhead.PrimaryObjectcode"
										text="PrimaryObjectcode" /></label>
							
						<div class="col-sm-4">
							<form:select id="listOfTbAcFunctionMasterItems" path="pacHeadId"
								cssClass="listOfTbAcFunctionMasterItems form-control chosen-select-no-results mandColorClass"
							data-rule-required="true"	onchange="">
								<form:option value="">
									<spring:message code="master.selectDropDwn" text="Select" />
								</form:option>
								<c:forEach items="${listOfTbAcFunctionMasterItems}"
									varStatus="status" var="functionItem">
									<form:option value="${functionItem.key}"
										code="${functionItem.value}">${functionItem.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

				</c:if>

				<c:if test="${form_mode ne 'create' && not empty tbAcVendormaster.pacHeadId}">
					<input type="hidden" value="${sliMode}" id="sliMode" />
					<div id="priHeadDiv">

					
								<label class="control-label col-sm-2 required-control"
									for="listOfTbAcFunctionMasterItems" id=""><spring:message
										code="accounts.Secondaryhead.PrimaryObjectcode"
										text="PrimaryObjectcode" /></label>
							
						<div class="col-sm-4">
							<form:hidden path="pacHeadId" id="pacHeadId" />
							<form:select id="listOfTbAcFunctionMasterItems" path="pacHeadId"
								cssClass="listOfTbAcFunctionMasterItems form-control chosen-select-no-results mandColorClass"
								data-rule-required="true" disabled="true">
								<form:option value="">
									<spring:message code="master.selectDropDwn" text="Select" />
								</form:option>
								<c:forEach items="${listOfTbAcFunctionMasterItems}"
									varStatus="status" var="functionItem">
									<form:option value="${functionItem.key}"
										code="${functionItem.value}">${functionItem.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</c:if>
					<!-- Defect #91157 end-->
			</div>

			<div class="form-group">
				<c:choose>
					<c:when test="${SudaEnv}">
						<label class="control-label col-sm-2 "> <spring:message
								code="accounts.vendormaster.vendorClass"></spring:message>
						</label>
					</c:when>
					<c:otherwise>
						<label class="control-label col-sm-2 required-control"> <spring:message
								code="accounts.vendormaster.vendorClass"></spring:message>
						</label>
					</c:otherwise>
				</c:choose>

				<div class="col-sm-4">

					<form:select path="vendorClass"
						class="form-control chosen-select-no-results"
						data-rule-required="${!SudaEnv}" id="vendor_venClassName">
						<form:option value="">
							<spring:message code="master.selectDropDwn" text="Select" />
						</form:option>
						<c:forEach items="${vendorClass}" var="venClass">
							<form:option value="${venClass.lookUpId}"
								code="${venClass.lookUpCode}">${venClass.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</div>



				<c:choose>
					<c:when test="${SudaEnv}">
						<label class="control-label col-sm-2 "> <spring:message
								code="accounts.vendormaster.address"></spring:message>
						</label>
					</c:when>
					<c:otherwise>
						<label class="control-label col-sm-2 required-control"> <spring:message
								code="accounts.vendormaster.address"></spring:message>
						</label>
					</c:otherwise>
				</c:choose>


				<div class="col-sm-4">
					<form:textarea id="vendor_vvmvendoradd" path="vmVendoradd"
						class="form-control" data-rule-required="${!SudaEnv}"
						data-rule-maxLength="200" />
				</div>

			</div>

			<div class="form-group">
			<label class="control-label col-sm-2" for="">
					<spring:message code="account.pf.accno"
						text="PF Account Number"></spring:message>
				</label>
				<div class="col-sm-4">
				<form:input path="pfAcNumber" id="pfAcNumber" cssClass = "form-control" maxlength="40"/>
				</div>
			
				<label class="control-label col-sm-2" for="rtgsvendorflag">
					<spring:message code="accounts.vendormaster.allowdirectpayment"
						text="Allow Direct Payment"></spring:message>
				</label>
				<div class="col-sm-4">
					<!-- <input type="checkbox" id="rtgsvendorflag" name="rtgsvendorflag" path="rtgsvendorflag" value="Y"> -->
					<label class="checkbox-inline padding-left-20"> <form:checkbox
							id="rtgsvendorflag" path="rtgsvendorflag" value="Y"
							class="padding-left-10" />
					</label>
				</div>
			</div>

			<%-- <div class="form-group" id="reload">
				<label class="col-sm-2 control-label" for=""><spring:message
						code="accounts.vendormaster.uploadDocument" text=" Upload" /></label>

				<div class="col-sm-4 text-left">
					<form:hidden path="attach[0].documentName" />
					<apptags:formField fieldType="7"
						fieldPath="attach[0].uploadedDocumentPath" currentCount="0"
						showFileNameHTMLId="true" folderName="0"
						fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
						maxFileCount="CHECK_LIST_MAX_COUNT"
						validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
					</apptags:formField>

					<small class="text-blue-2">(Upload file upto 5MB )</small>
				</div>
				
				<c:if test="${mode ne 'create'}">
						<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocuments">
									<tr>
										<th><spring:message code="scheme.document.name" text="Document Name" /></th>
										<th><spring:message code="scheme.view.document" text="View Documents" /></th>
										<th><spring:message code="scheme.action" text="Action"></spring:message>
										</th>
									</tr>
									<c:forEach items="${tbAcVendormaster.attachDocsList}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}"
													filePath="${lookUp.attPath}"
													actionUrl="Vendormaster.html?Download" /></td>
											<td class="text-center"><a href='#' id="deleteFile"
												onclick="return false;" class="btn btn-danger btn-sm"><i
													class="fa fa-trash"></i></a> <form:hidden path=""
													value="${lookUp.attId}" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
				</c:if>
			</div> --%>





			<div class="form-group">
				<c:if test="${form_mode ne 'create' }">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="accounts.vendormaster.remark"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:textarea id="vendor_remark" path="remark"
							class="form-control" maxLength="200" data-rule-required="true" />
					</div>
				</c:if>
			</div>

			<c:choose>

				<c:when test="${form_mode eq 'create'}">
					<div class="form-group">
						<div class="text-center padding-bottom-20" id="divSubmit">
							<button type="button" class="btn btn-success btn-submit" id="submit"
								onclick="saveDataVendor(this)">
								<spring:message code="master.save" text="Save" />
							</button>
							<button type="button" class="btn btn-warning "
								onclick="clearVendorForm()">
								<spring:message code="water.btn.reset" />
							</button>
							<input type="button" class="btn btn-danger"
								onclick="javascript:openRelatedForm('Vendormaster.html');"
								value="<spring:message code="back.msg" text="Back"/>" />
						</div>
					</div>

				</c:when>
				<c:otherwise>
					<div class="text-center padding-bottom-20" id="divSubmit">
						<c:if test="${mode != 'View'}">
							<button type="button" class="btn btn-success btn-submit" id="submitEdit"
								onclick="saveDataVendor(this)">
								<spring:message code="master.save" text="Save" />
							</button>
						</c:if>
						<input type="button" class="btn btn-danger"
							onclick="javascript:openRelatedForm('Vendormaster.html');"
							value="<spring:message code="back.msg" text="Back"/>"
							id="cancelEdit" />
					</div>
				</c:otherwise>
			</c:choose>
		</form:form>
	</div>
</div>
