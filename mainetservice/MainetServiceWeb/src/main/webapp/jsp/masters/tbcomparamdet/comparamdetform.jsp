<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(document).ready(function() {
		var editValue = $("#isEditDesc").val();
		var formMode = $("#formModeId").val();
		
		var isEditDefault = $("#isEditDefault").val();

		if (formMode == 'EDIT') {
			
			 if($('#defaultChecked').val() == "Y"){
				 $('#cpdDefaultId').prop('checked', true);
				$('#tbComparamDet_cpdDefault').val('Y');
			 }
				
			
			if ($('#cpmEditDesc').is(':checked')) {
				$("#tbComparamDet_cpdDesc").attr('readonly', false);
				$("#tbComparamDet_cpdDescMar").attr('readonly', false);
			} else {
				$("#tbComparamDet_cpdDesc").attr('readonly', true);
				$("#tbComparamDet_cpdDescMar").attr('readonly', true);
			}

			if ($('#cpmEditValue').is(':checked')) {
				$("#tbComparamDet_cpdValue").attr('readonly', false);
			} else {
				$("#tbComparamDet_cpdValue").attr('readonly', true);
			}
			
		}

		if (formMode == 'ADD') {
			$("#tbComparamDet_cpdDesc").attr('readonly', false);
			$("#tbComparamDet_cpdDescMar").attr('readonly', false);
		}

		if ($('#cpmEditDefault').is(':checked')) {
			$("#cpdDefaultId").prop('disabled', false);
		} else {
			$("#cpdDefaultId").prop('disabled', true);
		}

		
	});

	function checkForSpace(checkVal){
		var hasSpace = /\s/g.test(checkVal);
		return hasSpace;
	}
	
	function saveComparamDetData(obj) {
		var errorList = [];
		
		var cpdDesc = $("#tbComparamDet_cpdDesc").val();
		var cpdDescReg = $("#tbComparamDet_cpdDescMar").val();
		var cpdValue = $("#tbComparamDet_cpdValue").val();
		var cpdOthers = $('#tbComparamDet_cpdOthers').val();
		
		if ($.trim( cpdDesc ) == '') {
			errorList.push(getLocalMessage("prefix.error.detail.desc"));
		}

		if ($.trim( cpdDescReg ) == '') {
			errorList.push(getLocalMessage("prefix.error.detail.descMar"));
		}
		if (cpdValue == '' || checkForSpace(cpdValue)) {
			errorList.push(getLocalMessage("prefix.error.detail.value"));
		}
		if(cpdOthers != '' && cpdOthers != null){
			if(checkForSpace(cpdOthers)){
				errorList.push(getLocalMessage("prefix.error.detail.others"));
			}
		}

		if (errorList.length > 0) {
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';
			$(".errorDivComparamDet").html(errMsg);
			$(".errorDivComparamDet").show();
			$('html .errorDivComparamDet,body').animate({
				scrollTop : 0
			}, 'slow');

			errorList = [];
			return false;
		}
		var requestData = __serializeForm('form');

		$
				.ajax({
					url : 'ComparamMaster.html?createChildData',
					data : requestData,
					type : 'POST',
					success : function(response) {
						if (response == '0' || response == 0) {

							var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
							errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Default value already exists</li>';
							errMsg += '</ul>';
							$("#errorDivComparamDet").html(errMsg);
							$("#errorDivComparamDet").show();

							$("#cpdDefaultId").removeAttr('checked');
							$("#tbComparamDet_cpdDefault").val("N");
							return false;
						} else if (response == -1 || response == '-1') {

							var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
							errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Duplicate Description already exists</li>';
							errMsg += '</ul>';
							$("#errorDivComparamDet").html(errMsg);
							$("#errorDivComparamDet").show();
							return false;
						}
						else if (response == -2 || response == '-2') {

							var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
							errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Duplicate value already exists</li>';
							errMsg += '</ul>';
							$("#errorDivComparamDet").html(errMsg);
							$("#errorDivComparamDet").show();
							return false;
						}
						_closeChildForm('.child-popup-dialog');
						$.fancybox.close();

						$("#childGrid").jqGrid('setGridParam', {
							datatype : 'json'
						}).trigger('reloadGrid');
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});
	}

	function toggleDefaultVal(obj) {
		if ($(obj).is(':checked')) {
			$("#tbComparamDet_cpdDefault").val("Y");
		} else {
			$("#tbComparamDet_cpdDefault").val("N");
		}
	}
	
	
	function resetPrefixForm(){
		$('#tbComparamDet_cpdDesc').val('');
		$('#tbComparamDet_cpdDescMar').val('');
		$('#tbComparamDet_cpdValue').val('');
		$('#tbComparamDet_cpdOthers').val('');
		$('#cpdDefaultId').prop('checked',false);
		$('#errorDivComparamDet').html('');
		$('#errorDivComparamDet').hide();
	}
	
</script>

<div class="content">
	<div class="widget margin-bottom-0">
		<div class="widget-header">
			<h2><spring:message code="tbComparamDet.nonHierPreDet" text="Non-Hierarchical Prefix Detail"/></h2>
		</div>
		<div class="widget-content padding">
			<div class="common-popup">
				<c:url value="${saveAction}" var="url_form_submit" />
				<form:form method="post" action="${url_form_submit}"
					name="custBankForm" id="custBankForm" class="form-horizontal">

					<div class="error-div warning-div alert alert-danger alert-dismissible errorDivComparamDet" style="display: none;" id="errorDivComparamDet"></div>
					<form:hidden path="" id="formModeId" value="${childFormMode}" />
					<form:hidden path="cpdId" id="cpdId"/>
					
					<div class="form-group">
						<label for="tbComparamDet_cpdDesc"
							class="col-sm-2 control-label required-control"><spring:message
								code="tbComparamDet.cpdDesc" text="Description"/></label>
						<div class="col-sm-4">
							<form:input id="tbComparamDet_cpdDesc" path="cpdDesc"
								class="form-control" maxLength="200" />
							<form:errors id="tbComparamDet_cpdDesc_errors" path="cpdDesc"
								cssClass="label label-danger" />
						</div>
						<label for="tbComparamDet_cpdDescMar"
							class="col-sm-2 control-label required-control"><spring:message
								code="tbComparamDet.cpdDescMar" text="Description(Reg)"/></label>
						<div class="col-sm-4">
							<form:input id="tbComparamDet_cpdDescMar" path="cpdDescMar"
								class="form-control" maxLength="270" />
							<form:errors id="tbComparamDet_cpdDescMar_errors"
								path="cpdDescMar" cssClass="label label-danger" />
						</div>
					</div>
					<div class="form-group">
						<label for="tbComparamDet_cpdValue"
							class="col-sm-2 control-label required-control"><spring:message
								code="tbComparamDet.cpdValue" text="Value"/></label>
						<div class="col-sm-4">
							<form:input id="tbComparamDet_cpdValue" path="cpdValue"
								class="form-control" maxLength="" style="text-transform: uppercase;"
								 />
							<form:errors id="tbComparamDet_cpdValue_errors" path="cpdValue"
								cssClass="label label-danger" />
						</div>
						
						<label for="tbComparamDet_cpdOthers"
							class="col-sm-2 control-label "><spring:message
								code="tbComparamDet.cpdOthers" text="Other Value"/></label>
						<div class="col-sm-4">
						<form:input id="tbComparamDet_cpdOthers" path="cpdOthers"
								class="form-control" maxLength="60" />
							<form:errors id="tbComparamDet_cpdOthers_errors" path="cpdOthers"
								cssClass="label label-danger" />
						</div>
					</div>
					<div class="form-group">
						<label for="tbComparamDet_cpdDefault"
							class="col-sm-2 control-label "><spring:message
								code="tbComparamDet.cpdDefault" text="Default"/></label>
						<div class="col-sm-4">
						<label class="checkbox-inline"><input type="checkbox" onclick="toggleDefaultVal(this)"
								id="cpdDefaultId"/> 
						<input type="hidden" name="cpdDefault"
								id="tbComparamDet_cpdDefault" value="N" />
							<form:errors id="tbComparamDet_cpdDefault_errors"
								path="cpdDefault" cssClass="label label-danger" /></label>
						</div>
						
						<input type="hidden" value="${defaultChecked}" id="defaultChecked">
					</div>
				
					<div class="text-center">						
						<input type="button" class="btn btn-success btn-submit"
							value="<spring:message code="save" text="Save"/>"
							onclick="saveComparamDetData(this);" />
						<input type="reset" id="reset" value="<spring:message code="reset.msg" text="Reset"/>" 
							class="btn btn-warning" />	
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
