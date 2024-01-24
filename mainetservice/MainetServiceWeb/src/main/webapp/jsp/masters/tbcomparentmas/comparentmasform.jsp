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
		var formMode = $("#formModeId").val();

		var isEditDefault = $("#isEditDefault").val();

		if (formMode == 'EDIT') {

			if ($('#cpmEditValue').is(':checked')) {
				$("#tbComparentMas_comValue").attr('readonly', false);
			} else {
				$("#tbComparentMas_comValue").attr('readonly', true);
			}
			
			if ($('#cpmEditDesc').is(':checked')) {
				$("#tbComparentMas_comDesc").attr('readonly', false);
				$("#tbComparentMas_comDesc_reg").attr('readonly', false);
			} else {
				$("#tbComparentMas_comDesc").attr('readonly', true);
				$("#tbComparentMas_comDesc_reg").attr('readonly', true);
			}
		}
	});

	function checkForSpace(checkVal) {
		var hasSpace = /\s/g.test(checkVal);
		return hasSpace;
	}

	function saveComparentMas(obj) {

		var errorList = [];
		var comDesc = $("#tbComparentMas_comDesc").val();
		var comDesReg = $("#tbComparentMas_comDesc_reg").val();
		var comValue = $("#tbComparentMas_comValue").val();

		if ($.trim(comDesc) == '') {
			errorList.push(getLocalMessage("prefix.error.detail.desc"));
		}
		if ($.trim(comDesReg) == '') {
			errorList.push(getLocalMessage("prefix.error.detail.descMar"));
		}
		if (comValue == '' || checkForSpace(comValue)) {
			errorList.push(getLocalMessage("prefix.error.detail.value"));
		}
		if (errorList.length > 0) {
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

			$.each(errorList, function(index) {
				errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';
			$(".errorDivComparentMas").html(errMsg);
			$(".errorDivComparentMas").show();
			$('html .errorDivComparentMas,body').animate({
				scrollTop : 0
			}, 'slow');

			errorList = [];
			return false;
		}

		var requestData = __serializeForm('form');

		$.ajax({
					url : 'ComparamMaster.html?createComparentMasData',
					data : requestData,
					type : 'POST',
					success : function(response) {
						if (response == 0) {
							var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
							errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Duplicate Value already exists</li></ul>';

							$(".errorDivComparentMas").html(errMsg);
							$(".errorDivComparentMas").show();

							return false;
						} else if (response =="-1" || response ==-1) {
							var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
							errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Duplicate Description already exists</li></ul>';

							$(".errorDivComparentMas").html(errMsg);
							$(".errorDivComparentMas").show();

							return false;
						}  
						
						
						else {
							_closeChildForm('.child-popup-dialog');
							$.fancybox.close();
							$('#addChildbtn').prop("disabled", false);
							$("#comparentMasChildGrid").jqGrid('setGridParam',
									{
										datatype : 'json'
									}).trigger('reloadGrid');
						}
					},
					error : function(xhr, ajaxOptions, thrownError) {
						var errorList = [];
						errorList
								.push(getLocalMessage("admin.login.internal.server.error"));
						showError(errorList);
					}
				});

	}

	function toUpper(obj) {
		var comVal = $('#tbComparentMas_comValue').val().toUpperCase();
		$('#tbComparentMas_comValue').val(comVal);
	}
</script>


<div class="common-popup">

	<div class="widget margin-bottom-0">
		<div class="widget-header">
			<h2>
				<spring:message code="comparentMas.boxheader" text="Hierarchical Prefix Detail" />
			</h2>
		</div>
		<div class="widget-content padding">
			<c:url value="${saveAction}" var="url_form_submit" />
			<form:form method="post" action="${url_form_submit}"
				name="comparentMasForm" id="comparentMasForm"
				class="form-horizontal">
               <form:hidden path="" id="formModeId" value="${childFormMode}" />
				<div
					class="error-div warning-div alert alert-danger alert-dismissible errorDivComparentMas"
					style="display: none;" id="errorDivComparentMas"></div>

				<div class="form-group">
					<!-- The field label is defined in the messages file (for i18n) -->
					<label for="tbComparentMas_comDesc"
						class="col-sm-2 control-label required-control"> <spring:message
							code="tbComparentMas.comDesc" /></label>
					<div class="col-sm-4">
						<form:input id="tbComparentMas_comDesc" path="comDesc"
							class="form-control" maxLength="200" />
						<form:errors id="tbComparentMas_comDesc_errors" path="comDesc"
							cssClass="label label-danger" />
					</div>

					<label for="tbComparentMas_comDesc"
						class="col-sm-2 control-label required-control"> <spring:message
							code="tbComparentMas.comDescMar" /></label>
					<div class="col-sm-4">
						<form:input id="tbComparentMas_comDesc_reg" path="comDescMar"
							class="form-control" maxLength="200" />
						<form:errors id="tbComparentMas_comDesc_errors" path="comDescMar"
							cssClass="label label-danger" />
					</div>
				</div>

				<div class="form-group">
					<!-- The field label is defined in the messages file (for i18n) -->
					<label for="tbComparentMas_comValue"
						class="control-label col-sm-2 required-control"><spring:message
							code="tbComparentMas.comValue" /></label>
					<div class="col-sm-4">
						<form:input id="tbComparentMas_comValue" path="comValue"
							class="form-control" style="text-transform: uppercase;"
							onchange="toUpper(this)" />
						<form:errors id="tbComparentMas_comValue_errors" path="comValue"
							cssClass="label label-danger" />
					</div>
				</div>

				<div class="text-center">
					<input type="button" class="btn btn-success btn-submit"
						value="<spring:message code="save" />"
						onclick="saveComparentMas(this);" /> <input type="reset"
						id="reset" value="<spring:message code="reset.msg"/>"
						class="btn btn-warning" />
				</div>

			</form:form>

		</div>
	</div>
</div>
