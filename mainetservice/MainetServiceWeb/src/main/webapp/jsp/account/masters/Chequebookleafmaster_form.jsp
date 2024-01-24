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
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
		
		$('.hasNumber').keyup(function () {
		    this.value = this.value.replace(/[^0-9.]/g,'');
		    $(this).attr('maxlength','13');
		});
		
	});
</script>

<script>
	function doSum() {
		var a = parseInt(document.getElementById("fromchequeNo").value, 0);
		var b = parseInt(document.getElementById("tochequeNo").value, 0);
		 var errorList = [];
		if (a > 0 && b > 0) {
			if (a > 0 && b >= a) {
				var sum = (b - a) + 1;
				document.getElementById("checkbookleave").value = sum;
			} else {
				errorList.push("Cheque Number To cannot be less than Cheque Number From ");
				document.getElementById("tochequeNo").value = "";
				document.getElementById("checkbookleave").value = "";
			}
			 
			 if(errorList.length > 0){ 
					var errMsg = '<ul>';
					$.each(errorList, function(index) {
						errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
					});
					errMsg += '</ul>';
					$('#errorId').html(errMsg);
					$('#errorDivId').show();
					$('html,body').animate({ scrollTop: 0 }, 'slow');
					return false;
			} 

			
		}
	}

	$(document)
			.ready(
					function() {
						

						
						
					});

	$(document).ready(function() {
		
		
		
		$("#dateOfIssue").datepicker({
		    dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			maxDate: '0',
		});
		$("#dateOfIssue").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    });

		$("#chkbookrtnDatetemp").datepicker({
		    dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			maxDate: '0',
		});
		$("#chkbookrtnDatetemp").keyup(function(e){
		    if (e.keyCode != 8){    
		        if ($(this).val().length == 2){
		            $(this).val($(this).val() + "/");
		        }else if ($(this).val().length == 5){
		            $(this).val($(this).val() + "/");
		        }
		     }
		    });
		
	});

	function displayMessageOnSubmit(successMsg){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = getLocalMessage('account.proceed');
		
		message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
		message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';
		 
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutCloseaccount(errMsgDiv);
	}

	function redirectToDishonorHomePage () {
		window.location.href = "javascript:openRelatedForm('Chequebookleafmaster.html');";
	}

	function showPopUpMsg(childDialog){
		$.fancybox({
	        type: 'inline',
	        href: childDialog,
	        openEffect  : 'elastic', 
	        closeBtn : false ,
	        helpers: {
				overlay : {
					closeClick : false
				}
			},
			 keys : {
				    close  : null
				  }
	    });
		return false;
	}
	
	var elementTemp;
	function saveDataChequbook(element) {
		elementTemp=element;
		var errorList = [];
		
		<c:if test="${mode eq 'update'}">
		 var chk = $('#checkbookReturn').is(':checked');
		 if(chk == false){
			errorList.push(getLocalMessage('account.validation.returned.flag'));
		 }
		
		 var issuerempid = $.trim($("#issuerempid").val());
		 if (issuerempid == 0 || issuerempid == ""){
			errorList.push(getLocalMessage('account.validation.issuer.name'));
		 }
		 var chkbookrtnDatetemp = $.trim($("#chkbookrtnDatetemp").val());
		 if(chkbookrtnDatetemp!=null)
			 {
			 errorList = validatedate(errorList,'chkbookrtnDatetemp');
			 	if (errorList.length == 0) {
			 					var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			 					if(response == "Y"){
			 						errorList.push("SLI Prefix is not configured");
			 					}else{
			 					var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			 					var date = new Date($("#chkbookrtnDatetemp").val().replace(pattern,'$3-$2-$1'));
			 					var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			 					if (date < sliDate) {
			 						errorList.push("cheque book date can not be less than SLI date");
			 					}
			 				}
			 		}
			 }
		 var dateOfIssue = $.trim($("#dateOfIssue").val());
		 if(dateOfIssue!=null)
			 {
			 errorList = validatedate(errorList,'dateOfIssue');
			 if (errorList.length == 0) {
					var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
					if(response == "Y"){
 						errorList.push("SLI Prefix is not configured");
 					}else{
					var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
					var date = new Date($("#dateOfIssue").val().replace(pattern,'$3-$2-$1'));
					var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
					if (date < sliDate) {
							errorList.push("Issue date can not be less than SLI date");
						}
 					}
				}
			 }
		 </c:if>
		 
		 var checkbookLeave = $.trim($("#checkbookleave").val());
		/*  if (checkbookLeave != null && checkbookLeave != ""){
			 if (checkbookLeave > 100 ) {
				 errorList.push(getLocalMessage('Please.enter.max.total.cheque.no.should.be.lessthan.or.equal.100.'));
				 document.getElementById("tochequeNo").value = "";
				 document.getElementById("checkbookleave").value = "";
			 }
		 } */
		 
		 if (errorList.length > 0) {
				var errMsg = '<ul>';
				$.each(errorList, function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
				});
				errMsg += '</ul>';

				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				return false;
		}
		 
		

		

		
			if(errorList.length <= 0){
				showConfirmBoxSave();
				/* var formName = findClosestElementId(element, 'form');
				var theForm = '#' + formName;
				var requestData = __serializeForm(theForm);
				var response = doActionWithParam(element,"Record Saved Successfully", requestData, 'Chequebookleafmaster.html');
			    if(response != false){
						var divName = '.widget';
						$(divName).removeClass('ajaxloader');
						$(divName).html(response);
						return false;
			    } */ 
			}
		
			
	}

	function showConfirmBoxSave(){
			
		
		var saveorAproveMsg=getLocalMessage('Do.you.want.to.save');
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = getLocalMessage('account.btn.save.yes');
		var no=getLocalMessage('account.btn.save.no');
		
		 message	+='<h4 class=\"text-center text-blue-2\">  '+saveorAproveMsg+'</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>  '+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="saveDataAndShowSuccessMsg()"/>   '+ 
		'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
		' onclick="closeConfirmBoxForm()"/>'+ 
		'</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutCloseaccount(errMsgDiv);
		
	}
	
	function saveDataAndShowSuccessMsg(){
		var formName = findClosestElementId(elementTemp, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = doActionWithParam(elementTemp,"Record Saved Successfully", requestData, 'Chequebookleafmaster.html');
	    if(response != false){
				var divName = '.widget';
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				return false;
	    }
		
	}
	
	function showConfirmBox() {
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = 'Proceed';

		message += '<div class="form-group"><p>Form Submitted Successfully</p>';
		message += '<p style=\'text-align:center;margin: 5px;\'>'
				+ '<br/><input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-success \'    '
				+ ' onclick="proceed()"/>' + '</p></div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
	}

	function proceed() {
		window.location.href = "javascript:openRelatedForm('Chequebookleafmaster.html');";
	}
	
	function findduplicatecombinationexit(obj) {
		
		$('.error-div').hide();
		var errorList = [];

		debugger;
		var theForm = '#tbAcChequebookleafMas';
		var requestData = __serializeForm(theForm);
		
		var bmBankid = $('#bmBankid').val();
		if (bmBankid == '') {
			errorList.push('Please Select Bank Name');
			$('#tochequeNo').val(""); 
		}

		var fromchequeNo = $('#fromchequeNo').val();
		if (fromchequeNo == '') {
			errorList.push('Please Enter Cheque Number From');
			$('#tochequeNo').val(""); 
		}
		
		var tochequeNo = $('#tochequeNo').val();
		
		if (errorList.length > 0) {
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});
			errMsg += '</ul>';
			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			return false;
		}
			 
		if (tochequeNo != '' && tochequeNo != null) {
			if (errorList.length == 0) {
				
				var url = "Chequebookleafmaster.html?getChequeBookDuplicateData";
	
				var returnData = __doAjaxRequest(url, 'post', requestData, false);
				
				 if(returnData){
					debugger; 
					errorList.push("Cheque Book Already Exist in range!"); 
					$('#fromchequeNo').val("");
					$('#tochequeNo').val(""); 
					$('#checkbookleave').val(""); 
		
					var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	
				  	$.each(errorList, function(index) {
				  		var errorMsg = '<ul>';
					    $.each(errorList, function(index){
					    	errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
					    });
					    errorMsg +='</ul>';	
					    $('#errorId').html(errorMsg);
					    $('#errorDivId').show();
						$('html,body').animate({ scrollTop: 0 }, 'slow');
				  	});
				  	return false;
				}
			}
		}
	};
	
</script>



<div class="form-div" id="content">
	<div class="widget">

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>

			<c:url value="${saveAction}" var="url_form_submit" />
			<c:url value="${mode}" var="form_mode" />
			<!------------------------------ Form Area Starts Here	---------------------------------------->

			<form:form class="form-horizontal" id="tbAcChequebookleafMas"
				modelAttribute="tbAcChequebookleafMas" cssClass="form-horizontal"
				method="POST" action="${url_form_submit}">

				<form:hidden path="" value="${keyTest}" id="keyTest" />
				<form:hidden path="hasError" />
				<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
				<div class="warning-div alert alert-danger alert-dismissible hide"
					id="errorDivScrutiny">
					<button type="button" class="close" aria-label="Close"
						onclick="closeErrBox()">
						<span aria-hidden="true">&times;</span>
					</button>
					<ul>
						<li><form:errors path="*" /></li>
					</ul>
				</div>

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>


				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="chequebookId" />
				<form:hidden path="userId" />
				<form:hidden path="langId" />
				<form:hidden path="orgid" />
				<form:hidden path="lmoddate" />

				<c:if test="${mode eq 'update'}">

					<form:hidden id="updateMode" value="update" path="" />

				</c:if>

				<div class="form-group">
					<c:if test="${mode ne 'create'}">
						<label class="col-sm-2 control-label required-control"> <spring:message
								code="accounts.chequebookleaf.date"></spring:message></label>
						<div class="col-sm-4">
							<form:input id="rcptChqbookDatetemp" path="rcptChqbookDatetemp"
								class="form-control" readonly="true" />
						</div>
					</c:if>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"> <spring:message
							code="accounts.chequebookleaf.bank_name"></spring:message> <span
						class="mand">*</span></label>

					<div class="col-sm-4">

						<c:if test="${mode eq 'create'}">
							<form:select id="bmBankid" path="bmBankid"
								class="form-control mandColorClass chosen-select-no-results"
								data-rule-required="true">
								<form:option value="">
									<spring:message code="account.common.select" text="Select" />
								</form:option>
								<c:forEach items="${bankMap}" var="departMstData">
									<form:option value="${departMstData.key}"> ${departMstData.value}</form:option>
								</c:forEach>
							</form:select>
						</c:if>

						<c:if test="${mode ne 'create'}">

							<form:hidden path="bmBankidTemp" value="${bmBankidTemp}" />
							<form:hidden path="baAccountidTemp" value="${baAccountidTemp}" />
							<form:select id="bmBankid" path="bmBankid"
								class="form-control mandColorClass chosen-select-no-results"
								disabled="true">
								<form:option value="">
									<spring:message code="account.select" text="Select" />
								</form:option>
								<c:forEach items="${bankMap}" varStatus="status"
									var="departMstrData">
									<form:option value="${departMstrData.key}">${departMstrData.value}</form:option>
								</c:forEach>
							</form:select>

						</c:if>


					</div>

					<label class="col-sm-2 control-label required-control"> <spring:message
							code="accounts.chequebookleaf.cheque_number_from"></spring:message></label>
					<div class="col-sm-4">
						<c:choose>
							<c:when test="${form_mode eq 'create'}">
								<form:input type="text" id="fromchequeNo" path="fromChequeNo"
									onblur="doSum()" class="form-control hasNumber mandColorClass"
									maxLength="16" data-rule-required="true" />
							</c:when>
							<c:otherwise>
								<form:input type="text" id="fromchequeNo" path="fromChequeNo"
									onblur="doSum()" class="form-control hasNumber mandColorClass"
									maxLength="16" readonly="true" />
							</c:otherwise>
						</c:choose>
					</div>

				</div>







				<div class="form-group">

					<label class="col-sm-2 control-label required-control"> <spring:message
							code="accounts.chequebookleaf.cheque_number_to"></spring:message></label>
					<div class="col-sm-4">
						<c:choose>
							<c:when test="${form_mode eq 'create'}">
								<form:input type="text" id="tochequeNo" path="toChequeNo"
									onblur="doSum()" onkeyUp="findduplicatecombinationexit(this)"
									class="form-control hasNumber mandColorClass" maxLength="16"
									data-rule-required="true" />
							</c:when>
							<c:otherwise>
								<form:input type="text" id="tochequeNo" path="toChequeNo"
									onblur="doSum()" class="form-control hasNumber mandColorClass"
									maxLength="16" readonly="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<label class="col-sm-2 control-label"> <spring:message
							code="accounts.chequebookleaf.total_Cheque_no"></spring:message></label>
					<div class="col-sm-4">
						<form:input type="text" id="checkbookleave" path="checkbookLeave"
							class="form-control mandColorClass" maxLength="16"
							readonly="true" />
					</div>
				</div>
				<c:if test="${mode eq 'update' or mode eq 'View'}">
					<h4 class="margin-top-0"><spring:message
								code="accounts.issuance.details" text="Issuance Details"></spring:message></h4>
					<div class="form-group">




						<label class="col-sm-2 control-label required-control"> <spring:message
								code="accounts.chequebookleaf.issuer_name"></spring:message>
						</label>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${form_mode eq 'create' or mode eq 'update'}">
									<form:select id="issuerempid" path="issuerEmpid"
										class="form-control mandColorClass chosen-select-no-results"
										data-rule-required="true">
										<form:option value="">
											<spring:message
												code="accounts.chequebookleaf.select_issuer_name"></spring:message>
										</form:option>
										<c:forEach items="${emplist}" var="issempData">
											<form:option value="${issempData.empId}">${issempData.empname}</form:option>
										</c:forEach>
									</form:select>
								</c:when>
								<c:otherwise>
									<form:select id="issuerempid" path="issuerEmpid"
										cssClass="form-control mandColorClass" disabled="true">
										<form:option value="">
											<spring:message
												code="accounts.chequebookleaf.select_issuer_name"></spring:message>
										</form:option>
										<c:forEach items="${emplist}" var="issempData">
											<form:option value="${issempData.empId}">${issempData.empname}</form:option>
										</c:forEach>
									</form:select>
								</c:otherwise>
							</c:choose>
						</div>
						<label class="col-sm-2 control-label required-control"> <spring:message
								code="accounts.chequebookleaf.date.of.issue"></spring:message>
						</label>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${mode eq 'update'}">
									<form:input id="dateOfIssue" path="chequeIssueDateTemp"
										class="form-control cal datepicker mandColorClass"
										data-rule-required="true"  maxlength="10"/>
								</c:when>
								<c:otherwise>
									<form:input id="dateOfIssue" path="chequeIssueDateTemp"
										class="form-control cal datepicker mandColorClass"
										disabled="true" />
								</c:otherwise>
							</c:choose>
						</div>




					</div>
				</c:if>






				<c:if test="${mode eq 'update' or mode eq 'View'}">
					<h4 class="margin-top-0"><spring:message
								code="accounts.chequebookleaf.Return.details" text="Return Details"></spring:message></h4>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"> <spring:message
								code="accounts.chequebookleaf.cheque_return"></spring:message>
						</label>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${mode eq 'update'}">
									<div class="checkbox-inline">
										<form:checkbox path="checkBookReturn" value="Y"
											id="checkbookReturn" data-rule-required="true" />
									</div>
								</c:when>
								<c:otherwise>
									<div class="checkbox-inline">
										<form:checkbox path="checkBookReturn" value="Y"
											id="checkbookReturn" disabled="true" />
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"> <spring:message
								code="accounts.chequebookleaf.cheque_return_date"></spring:message>
						</label>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${mode eq 'update'}">
									<form:input id="chkbookrtnDatetemp" path="ChkbookRtnDatetemp"
										class="form-control cal datepicker" maxlength="10" />
								</c:when>
								<c:otherwise>
									<form:input id="chkbookrtnDatetemp" path="ChkbookRtnDatetemp"
										class="form-control cal datepicker" disabled="true" />
								</c:otherwise>
							</c:choose>
						</div>

						<label class="col-sm-2 control-label required-control"> <spring:message
								code="accounts.chequebookleaf.remark"></spring:message>
						</label>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${mode eq 'update'}">
									<form:textarea path="returnRemark"
										class="form-control mandColorClass" id="remark"
										data-rule-required="true" />
								</c:when>
								<c:otherwise>
									<form:textarea path="returnRemark"
										class="form-control mandColorClass" id="remark"
										disabled="true" />
								</c:otherwise>
							</c:choose>


						</div>

					</div>

				</c:if>
				<c:choose>
					<c:when test="${form_mode eq 'create'}">
						<div class="form-group">
							<div class="text-center" id="divSubmit">

								<button type="button" class="btn btn-success btn-submit" id="submit"
									onclick="saveDataChequbook(this)">
									<spring:message code="account.bankmaster.save" text="Save" />
								</button>

								<input type="button" id="Reset"
									class="btn btn-warning createData" value="<spring:message code="account.bankmaster.reset" text="Reset"/>">
									</input> <input
									type="button" class="btn btn-danger"
									onclick="javascript:openRelatedForm('Chequebookleafmaster.html');"
									value="<spring:message code="account.bankmaster.back" text="Back"/>" />

							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="text-center" id="divSubmit">
							<c:if test="${mode != 'View'}">
								<button type="button" class="btn btn-success btn-submit" id="submitEdit"
									onclick="saveDataChequbook(this)">
									<spring:message code="account.bankmaster.save" text="Save" />
								</button>
							</c:if>
							<input type="button" class="btn btn-danger"
								onclick="javascript:openRelatedForm('Chequebookleafmaster.html');"
								value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />

						</div>
					</c:otherwise>
				</c:choose>
			</form:form>
			<!--Add Section End Here-->
		</div>
	</div>
</div>


