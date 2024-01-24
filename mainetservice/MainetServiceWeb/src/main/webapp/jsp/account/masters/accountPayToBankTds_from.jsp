<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script>
$( document ).ready(function() {
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		minDate: $("#tds_ptbbanknamec").val(),
		maxDate: '-0d',
		changeYear: true
	});
	$('#divHeaderId').hide();
	});
	
$("#tbAcPayToBank").validate({
	
	onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}
});

	function saveDataBankTDS(element) {
		var errorList = [];

			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			var url = $(theForm).attr('action');
			debugger;
			var response= __doAjaxRequestValidationAccor(element,url, 'POST', requestData, false, '');
	 	    if(response != false){
	 	    	debugger;
	 	    	if ($.isPlainObject(response)) {
					showConfirmBox();
				} else {
					var divName = '.widget';
					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
					return false;
				}
	 	    }

	}



	function showConfirmBox(){
		var mode=$.trim($("#MODE_ID").val());
		var successMsg = getLocalMessage('account.bank.record.rubmitted.successfully');
		if(mode=='update'){
			successMsg = getLocalMessage('account.bank.record.updated.successfully');
		}
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = getLocalMessage('account.proceed.btn');
		 message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
		 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}

	function proceed() {

		window.location.href = "javascript:openRelatedForm('AccountPayToBankTDS.html');";
	}
	
	
	function setBranchNames(obj) { 
		 
		var errorList=[];
		var bankName = $.trim($("#bankName").val());
		$('#bankBranch').empty();
		
		if(bankName==='' ||bankName===0){

			var optionsAsString='0';
			 $("#bankBranch").append(optionsAsString);

		}else{
			  
			var url = "BankAccountMaster.html?getBranchNames" ;		
			var reqData = "bankName=" + bankName;
	       	var	branchDetails =__doAjaxRequestForSave(url, 'post', reqData, false,'', obj);

	       	var  optionsAsString='0';
	       	
			$("#bankBranch").html('');
			$('#bankBranch')
		    .append($("<option></option>")
		    .attr("value","").attr("code","")
		    .text(getLocalMessage('Select')));

	       	$.each(branchDetails, function(index){
				var lookUp=branchDetails[index];
				var codId=lookUp.lookUpId;
				var codDesc=lookUp.lookUpDesc;		
				$('#bankBranch').append($('<option>', {
				    value: codId,
				    text: codDesc
				}));
	          $('#bankBranch').chosen().trigger("chosen:updated");  
				
			});

			}
		}
	
	  function findduplicatecombinationexit() {
			debugger;
			$('.error-div').hide();
			var errorList = [];

			var theForm = '#frmMaster';
			var requestData = __serializeForm(theForm);
		
			if (errorList.length == 0) {
			
				var url = "AccountPayToBankTDS.html?getptbTdsTypeDuplicateData";

				var returnData = __doAjaxRequest(url, 'post', requestData, false);
				
				 if(returnData){
					 
					errorList.push("TDS Type Is Already Selected!"); 
					$('#tdsType').val("");
					$("#tdsType").val('').trigger('chosen:updated');
					
					var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

				  	$.each(errorList, function(index) {
				  		var errorMsg = '<ul>';
					    $.each(errorList, function(index){
					    	errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
					    });
					    errorMsg +='</ul>';	
					    $('#errorDivId').html(errorMsg);
					    $('#errorDivId').show();
						$('html,body').animate({ scrollTop: 0 }, 'slow');
				  	});
				  	return false;
				}
			}
		};
	
		function checkDeplicateBsrCodeExist() {
			
			$('.error-div').hide();
			var errorList = [];

			var theForm = '#frmMaster';
			var requestData = __serializeForm(theForm);
		
			var bankBranch = $.trim($("#bankBranch").val());
			if (bankBranch == 0 || bankBranch == ""){
				errorList.push(getLocalMessage('Please Select Branch Name'));
				$('#tds_ptbbsrcode').val("");
			}
			
			if (errorList.length > 0) {
				var errMsg = '<ul>';
				$.each(errorList, function(index) {
					errMsg += '<li>' + errorList[index] + '</li>';
				});
				errMsg += '</ul>';
				$('#errorDivId').html(errMsg);
				$('#errorDivId').show();
				return false;
			}
			
			if (errorList.length == 0) {
			
				var url = "AccountPayToBankTDS.html?getptbBSRCodeDuplicateData";

				var returnData = __doAjaxRequest(url, 'post', requestData, false);
				
				 if(returnData){
					 
					errorList.push("BSR code is already exist, againest the same Bank & Branch!"); 
					$('#tds_ptbbsrcode').val("");
					
					var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

				  	$.each(errorList, function(index) {
				  		var errorMsg = '<ul>';
					    $.each(errorList, function(index){
					    	errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
					    });
					    errorMsg +='</ul>';	
					    $('#errorDivId').html(errorMsg);
					    $('#errorDivId').show();
						$('html,body').animate({ scrollTop: 0 }, 'slow');
				  	});
				  	return false;
				}
			}
		};
		
</script>




<c:if test="${ShowBreadCumb ==false}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>



<!-- Start info box -->
<div class="widget" id="widget">

	<c:if test="${mode == 'create'}">
		<div class="widget-header" id="divHeaderId">
			<h2>
				<spring:message code="accounts.bankfortds.tds.master" text="TDS Master" />
			</h2>
		<apptags:helpDoc url="AccountPayToBankTDS.html" helpDocRefURL="AccountPayToBankTDS.html"></apptags:helpDoc>	
		</div>
	</c:if>

	<c:if test="${mode != 'create'}">
		<div class="widget-header">
			<h2>
				<spring:message code="accounts.bankfortds.tds.master" text="TDS Master" />
			</h2>
		<apptags:helpDoc url="AccountPayToBankTDS.html" helpDocRefURL="AccountPayToBankTDS.html"></apptags:helpDoc>		
		</div>
	</c:if>

	<div class="mand-label clearfix">
		<span><spring:message code="account.common.mandmsg"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="account.common.mandmsg1" text="is mandatory" /> </span>
	</div>
	<div class="widget-content padding">
		<c:url value="${saveAction}" var="url_form_submit" />
		<c:url value="${mode}" var="form_mode" />

		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbAcPayToBank" cssClass="form-horizontal"
			method="POST" action="${url_form_submit}">

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
				<SCRIPT>
					$(document)  
							.ready(
									function() {
										$('.error-div').hide();
										$('#editOriew').find('*').attr(
												'disabled', 'disabled');
										$('#editOriew').find('*').addClass(
												"disablefield");
										$('#tds_ptbBankcode').attr(
												'disabled', 'disabled');
										$('#tds_ptbBankcode').addClass(
												"disablefield");
										$('#payTo').attr(
												'disabled', 'disabled');
										$('#payTo').addClass(
												"disablefield");
										$('#bankName').attr(
												'disabled', 'disabled');
										$('#bankName').addClass(
												"disablefield");
										$('#bankBranch').attr(
												'disabled', 'disabled');
										$('#bankBranch').addClass(
												"disablefield");
										$('#tds_ptbbsrcode').attr(
												'disabled', 'disabled');
										$('#tds_ptbbsrcode').addClass(
												"disablefield");
										$('#sacHeadId').attr(
												'disabled', 'disabled');
										$('#sacHeadId').addClass(
												"disablefield");
										$('#ptbStatus').attr(
												'disabled', 'disabled');
										$('#ptbStatus').addClass(
												"disablefield");
										$('#isdeleted').attr('disabled',
												'disabled');
										$('#isdeleted')
												.addClass("disablefield");
									});
						</SCRIPT>
			</c:if>
			<c:if test="${mode == 'update'}">
				<SCRIPT>
					$(document)  
							.ready(
									function() {
										tds_ptbBankcode.readOnly=false;
										$("#sacHeadId").chosen();
										$("#bankName").chosen();
										$("#bankBranch").chosen();
										$("#payTo").chosen();				
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
			<form:hidden path="ptbId" />
			<form:hidden path="userId" />
			<form:hidden path="lmoddate" />
			<form:hidden path="lgIpMac" />
			<form:hidden path="" value="${mode}" id="MODE_ID" />


			<div class="form-group">

				<label class="control-label col-sm-2 required-control"> <spring:message
						code="accounts.bankfortds.tds.type" text="TDS Type"></spring:message>
				</label>
				<c:if test="${mode == 'create'}">
					<div class="col-sm-4">
						<form:select id="tdsType" path="ptbTdsType"
							class="form-control chosen-select-no-results"
							data-rule-required="true"
							onchange="findduplicatecombinationexit();">
							<form:option value="">
								<spring:message code="accounts.bankfortds.select.tds.type" text="Select TDS Type" />
							</form:option>
							<c:forEach items="${tdsTypeLookUp}" varStatus="status"
								var="levelParent">
								<form:option code="${levelParent.lookUpCode}"
									value="${levelParent.lookUpId}">${levelParent.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</c:if>

				<c:if test="${mode != 'create'}">
					<div class="col-sm-4">
						<form:select id="tdsType" path="ptbTdsType"
							class="form-control chosen-select-no-results"
							data-rule-required="true" disabled="true">
							<form:option value="">
								<spring:message code="accounts.bankfortds.select.tds.type" text="Select TDS Type" />
							</form:option>
							<c:forEach items="${tdsTypeLookUp}" varStatus="status"
								var="levelParent">
								<form:option code="${levelParent.lookUpCode}"
									value="${levelParent.lookUpId}">${levelParent.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
						<form:hidden id="tdsType" path="ptbTdsType" />
					</div>
				</c:if>

				<label class="control-label col-sm-2 required-control"><spring:message
						code="accounts.bankfortds.tds.account.head" text="TDS Account Head" /></label>
				<div class="col-sm-4">
					<form:select id="sacHeadId" path="sacHeadId" disabled="${viewMode}"
						cssClass="form-control mandColorClass chosen-select-no-results"
						data-rule-required="true">
						<form:option value="">
							<spring:message code="accounts.bankfortds.select.tds.account.head" text="Select TDS Account Head" />
						</form:option>
						<c:forEach items="${sacHeadMap}" varStatus="status" var="sacItem">
							<form:option value="${sacItem.key}" code="${sacItem.key}">${sacItem.value}</form:option>
						</c:forEach>
					</form:select>
				</div>
				
			</div>

			<div class="form-group">


				<label class="col-sm-2 control-label "><spring:message
						code="account.bankmaster.bankname" /></label>
				<div class="col-sm-4">
					<form:select path="ptbBankbranch"
						cssClass="form-control mandColorClass chosen-select-no-results"
						id="bankName" 
						onchange="setBranchNames();">
						<form:option value="">
							<spring:message code="accounts.bankfortds.select.bank.name" text="Select Bank Name" />
						</form:option>
						<c:forEach items="${bankNameList}" var="bName">
							<form:option value="${bName}">${bName}</form:option>
						</c:forEach>
					</form:select>
				</div>

				<label class="col-sm-2 control-label "><spring:message
						code="accounts.bankfortds.branch.details" text="Branch Details" /></label>
				<div class="col-sm-4">
					<form:select path="bankId"
						cssClass="form-control mandColorClass chosen-select-no-results"
						id="bankBranch">
						<form:option value="">
							<spring:message code="accounts.bankfortds.select.branch.name" text="Select Branch Name" />
						</form:option>
						<c:forEach items="${branchlookUp}" var="branch">
							<form:option value="${branch.lookUpId}">${branch.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<spring:message code="accounts.bankfortds.enter.bank.account.no" var="bankAccNoPlaceholder" text="Enter Bank Account No"/>
			<spring:message code="accounts.bankfortds.enter.bsr.code" var="bsrCodePlaceholder" text="Enter BSR Code"/>
			<div class="form-group">
				<label class="control-label col-sm-2 "> <spring:message
						code="accounts.bankfortds.bank.account.no" text="Bank Account No"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="tds_ptbBankcode" path="ptbBankAcNo" placeholder="${bankAccNoPlaceholder}"
						class="form-control hasNumber" maxLength="12"
						/>
					<!-- maxLength="12" -->
				</div>
				<label class="col-sm-2 control-label "><spring:message
						code="accounts.bankfortds.ptbbsrcode"></spring:message></label>
				<div class="col-sm-4">
					<form:input path="ptbBsrcode" cssClass="form-control" placeholder="${bsrCodePlaceholder}"
						onkeyup="checkDeplicateBsrCodeExist();" id="tds_ptbbsrcode"
						maxLength="15"  data-rule-minLength="7"
						data-rule-maxLength="7" />
					<!-- maxLength="15"  -->
				</div>
			</div>

			<div class="form-group">

				<label class="control-label col-sm-2 required-control"> <spring:message
						code="accounts.bankfortds.payable.to" text="Payable To"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:select id="payTo" path="vmVendorid"
						class="form-control chosen-select-no-results"
						data-rule-required="true">
						<form:option value="">
							<spring:message code="accounts.bankfortds.payable.to" text="Payable To" />
						</form:option>
						<c:forEach items="${vendorList}" var="vendorData">
							<form:option value="${vendorData.vmVendorid}">${vendorData.vmVendorcode} - ${vendorData.vmVendorname}</form:option>
						</c:forEach>
					</form:select>
				</div>
				
				<c:if test="${mode != 'create'}">
					<label class="control-label col-sm-2 required-control"><spring:message
							code="accounts.master.status" text="Status" /></label>
					<div class="col-sm-4 ">
						<form:select id="ptbStatus" path="ptbStatus"
							cssClass="form-control mandColorClass" disabled="${viewMode}"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="accounts.master.sel.status" text="Select Status" />
							</form:option>
							<c:forEach items="${activeDeActiveMap}" varStatus="status"
								var="activeItem">
								<form:option code="${activeItem.lookUpCode}"
									value="${activeItem.lookUpCode}">${activeItem.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</c:if>

			</div>

			<c:choose>

				<c:when test="${form_mode eq 'create'}">
					<div class="form-group">
						<div class="text-center" id="divSubmit">

							<button type="button" class="btn btn-success btn-submit" id="submit"
								onclick="saveDataBankTDS(this)">
								<spring:message code="account.bankmaster.save" text="Save" />
							</button>
							<input type="button" id="Reset"
								class="btn btn-warning createData" value="<spring:message code="reset.msg" text="Reset" />"></input> <input
								type="button" class="btn btn-danger"
								onclick="javascript:openRelatedForm('AccountPayToBankTDS.html');"
								value="<spring:message code="account.bankmaster.back" text="Back"/>" />

						</div>
					</div>

				</c:when>
				<c:otherwise>
					<div class="text-center" id="divSubmit">
						<c:if test="${mode != 'View'}">
							<button type="button" class="btn btn-success btn-submit" id="submitEdit"
								onclick="saveDataBankTDS(this)">
								<spring:message code="account.bankmaster.save" text="Save" />
							</button>
						</c:if>
						<input type="button" class="btn btn-danger"
							onclick="javascript:openRelatedForm('AccountPayToBankTDS.html');"
							value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />

					</div>
				</c:otherwise>
			</c:choose>


		</form:form>
	</div>
</div>
