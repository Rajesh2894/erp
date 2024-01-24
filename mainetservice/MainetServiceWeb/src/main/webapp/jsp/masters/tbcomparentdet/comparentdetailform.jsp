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
	$(document).ready(
			function() {
				var formMode = $("#formModeId").val();
				var parentPrefix = $("#prefixName").val().split(',');

				var prefixCount = parentPrefix.length;

				for (var counter = 0; counter < prefixCount; counter++) {

					if (parentPrefix[counter].indexOf("[") != -1
							|| parentPrefix[counter].indexOf("]") != -1) {

						var prefixTempstr = parentPrefix[counter].replace("[",
								"").replace("]", "");

						$("#formPrefixId_" + counter).text(
								prefixTempstr + " : ");

					} else {
						$("#formPrefixId_" + counter).text(
								parentPrefix[counter] + " : ");
					}

				}

				if (formMode == 'EDIT') {
					if ($("#cpdDefault").val() == 'Y') {
						$("#activeChkBox").prop("checked", true);
						$("#activeChkBox").attr("checked", "checked");

					} else {
						$("#activeChkBox").prop("checked", false);
						$("#activeChkBox").attr("checked", "");
					}
					if ($('#cpmEditValue').is(':checked')) {
						$("#tbComparentDet_codValue").attr('readonly', false);
					} else {
						$("#tbComparentDet_codValue").attr('readonly', true);
					}
					
					
					if ($('#cpmEditDesc').is(':checked')) {
						$("#tbComparentDet_codDesc").attr('readonly', false);
						$("#tbComparentDet_codDesc_reg").attr('readonly', false);
					} else {
						$("#tbComparentDet_codDesc").attr('readonly', true);
						$("#tbComparentDet_codDesc_reg").attr('readonly', true);
					}
					
					if ($('#cpmEditDefault').is(':checked')) {
						$('#activeChkBox').prop('disabled', false);
					} else {
						$('#activeChkBox').prop('disabled', true);
					}
				}
			});

	function getComparamLevelData(obj) {

		var countVal = $("#countId").val();

		var curId = obj.id.split("_")[1];

		for (var c = curId; c <= countVal; c++) {

			$('#parentDetDataId_' + (parseInt(c) + 1)).html("");
			$('#parentDetDataId_' + (parseInt(c) + 1)).append(
					$("<option></option>").attr("value", "").text('select'));

		}

		var selectedId = $(obj).val();

		var url = "ComparamMaster.html?getComparentNextLevelData";
		var requestData = "selectedId=" + selectedId;

		$
				.ajax({
					url : url,
					data : requestData,
					success : function(response) {

						$('#parentDetDataId_' + (obj.id.split("_")[1]) + 1)
								.html("");
						$('#parentDetDataId_' + (obj.id.split("_")[1]) + 1)
								.append(
										$("<option></option>")
												.attr("value", "").text(
														'select'));

						$
								.each(
										response,
										function(key, value) {

											$(
													'#parentDetDataId_'
															+ (parseInt(obj.id
																	.split("_")[1]) + 1))
													.append(
															$(
																	"<option></option>")
																	.attr(
																			"value",
																			key)
																	.text(value));

										});

						$("#comparentDetChildGrid").jqGrid('setGridParam', {
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

	function checkForSpace(checkVal) {
		var hasSpace = /\s/g.test(checkVal);
		return hasSpace;
	}

	function saveComparentDetail(obj) {
		var errorList = [];
		var parentcount = $('#countId').val();
		var codDesc = $("#tbComparentDet_codDesc").val();
		var codDescReg = $("#tbComparentDet_codDesc_reg").val();
		var codValue = $("#tbComparentDet_codValue").val();
		var codOthers = $('#tbComparentDet_codOthers').val();

		for (var i = 0; i <= parentcount; i++) {
			var label = '';
			if ($('#parentDetDataId_' + i).val() == '') {
				label = $('#formPrefixId_' + i).text();
				lblLen = label.length;
				errorList
						.push('Please enter ' + label.substring(0, lblLen - 2));
			}
		}

		if ($.trim(codDesc) == '') {
			errorList.push(getLocalMessage("prefix.error.detail.desc"));
		}
		if ($.trim(codDescReg) == '') {
			errorList.push(getLocalMessage("prefix.error.detail.descMar"));
		}
		if (codValue == '' || codValue == null) {
			errorList.push(getLocalMessage("prefix.error.detail.value"));
		}
		if (codOthers != '' && codOthers != null) {
			if (checkForSpace(codOthers)) {
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
			$(".errorDivComparentDet").html(errMsg);
			$(".errorDivComparentDet").show();
			$('html .errorDivComparentDet,body').animate({
				scrollTop : 0
			}, 'slow');

			errorList = [];
			return false;
		}

		var requestData = __serializeForm('form');
		$
				.ajax({
					url : 'ComparamMaster.html?createComparentDetailsData',
					data : requestData,
					type : 'POST',
					success : function(response) {
						if (response == -1 || response == "-1") {
							var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
							errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Duplicate Description already exists</li></ul>';

							$(".errorDivComparentDet").html(errMsg);
							$(".errorDivComparentDet").show();

							return false;
						} else if (response == -2 || response == "-2") {
							var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
							errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Duplicate Value already exists</li></ul>';

							$(".errorDivComparentDet").html(errMsg);
							$(".errorDivComparentDet").show();
							return false;
						}else if (response == 0 || response == "0") {

							var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
							errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Default value already exists</li></ul>';

							$(".errorDivComparentDet").html(errMsg);
							$(".errorDivComparentDet").show();
							return false;
							
							
							$("#activeChkBox").removeAttr('checked');
							$("#cpdDefault").val("N");
							return false;
						} else {
							_closeChildForm('.child-popup-dialog');
							$.fancybox.close();
							$("#comparentDetChildGrid").jqGrid('setGridParam',
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

	$('#cpdDefault').change(function() {

		if ($('input:checkbox[id=cpdDefault]').is(':checked')) {
			$('#activeChkBox').attr('checked', true);
			$('#chkBox').val(true);
			$("#activeChkBox").attr("checked", "checked");
		} else {
			$('#activeChkBox').attr('checked', false);
			$('#chkBox').val(false);
			$("#activeChkBox").attr("checked", "");

		}

	});

	function resetPrefixForm() {
		$('#tbComparentDet_codDesc').val('');
		$('#tbComparentDet_codDesc_reg').val('');
		$('#tbComparentDet_codValue').val('');
		$('#errorDivComparentDet').html('');
		$('#errorDivComparentDet').hide();

		var countId = $('#countId').val();
		for (var i = 0; i < countId; i++) {
			$('#parentDetDataId_' + i).val('');
		}
	}
</script>


<div class="common-popup">

	<div class="widget margin-bottom-0">
		<div class="widget-header">
			<h2>
				<spring:message
					code="common.master.hierarchical.prefix.level.detail"
					text="Hierarchical Prefix Level Detail" />
			</h2>
		</div>
		<div class="widget-content padding">
			<c:url value="${saveAction}" var="url_form_submit" />

			<form:form method="post" action="${url_form_submit}"
				name="comparentDetForm" id="comparentDetForm"
				class="form-horizontal">
				<div
					class="error-div warning-div alert alert-danger alert-dismissible errorDivComparentDet"
					style="display: none;" id="errorDivComparentDet"></div>
				<form:hidden path="level" value="${level}" id="parentLevelId" />
				<form:hidden path="" id="formModeId" value="${childFormMode}" />
				<form:hidden path="codId" id="codId"/>

				<input type="hidden" value="${parentDataIdLst}" id="parentDataIdLst" />

				<c:set value="0" var="count" scope="page"></c:set>
				<c:forEach items="${parentDataList}" var="parentDataList">
					<div class="form-group">
						<label class="control-label required-control col-sm-2"
							id="formPrefixId_${count}">:</label>
						<div class="col-sm-4">
							<input type="hidden" value="${count}"
								id="parentDetDataIdCount_${count}" /> <select
								id="parentDetDataId_${count}" class="form-control"
								onchange="getComparamLevelData(this)">
								<option value="">Select</option>
								<c:forEach var="parentData" items="${parentDataList}"
									varStatus="itemsRow">
									<option value="${ parentData.key}">${parentData.value } </option>
								</c:forEach>
							</select>
						</div>
					</div>
					<c:set value="${count+1}" var="count" scope="page"></c:set>

				</c:forEach>
				<input type="hidden" id="countId" value="${count}" />

				<!-- DATA FIELD : codDesc -->
				<div class="form-group">
					<label class="control-label required-control col-sm-2"
						for="tbComparentDet_codDesc"> <spring:message
							code="tbComparentDet.codDesc" /></label>
					<div class="col-sm-4">
						<form:input id="tbComparentDet_codDesc" path="codDesc"
							class="form-control" maxLength="400" />
						<form:errors id="tbComparentDet_codDesc_errors" path="codDesc"
							cssClass="label label-danger" />
					</div>

					<label class="control-label required-control col-sm-2"
						for="tbComparentDet_codDesc_reg"> <spring:message
							code="tbComparentDet.codDescMar" /></label>
					<div class="col-sm-4">
						<form:input id="tbComparentDet_codDesc_reg" path="codDescMar"
							class="form-control" maxLength="400" />
						<form:errors id="tbComparentDet_codDesc_errors" path="codDescMar"
							cssClass="label label-danger" />
					</div>
				</div>

				<!-- DATA FIELD : codValue -->
				<div class="form-group">
					<label class="control-label required-control col-sm-2"
						for="tbComparentDet_codValue"> <spring:message
							code="tbComparentDet.codValue" /></label>
					<div class="col-sm-4">
						<form:input id="tbComparentDet_codValue" path="codValue"
							class="form-control" maxLength=""
							style="text-transform: uppercase;" />
						<form:errors id="tbComparentDet_codValue_errors" path="codValue"
							cssClass="label label-danger" />
					</div>

					<label class="col-sm-2 control-label "><spring:message
							code="tbComparamDet.cpdOthers" /></label>
					<div class="col-sm-4">
						<form:input id="tbComparentDet_codOthers" path="codOthers"
							class="form-control" maxLength="60" />
						<form:errors id="tbComparentDet_codOthers_errors" path="codOthers"
							cssClass="label label-danger" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="tbComparamDet.cpdDefault" /></label>
					<div class="col-sm-4">
						<label class="checkbox-inline"> <form:checkbox
								path="chkBox" id="activeChkBox" /> <form:hidden
								path="cpdDefault" id="cpdDefault" /> <form:errors
								id="tbComparentDet_cpdDefault_errors" path="cpdDefault"
								cssClass="label label-danger" />
						</label>
					</div>
				</div>



				<div class="text-center">
					<input type="button" class="btn btn-success btn-submit"
						value="<spring:message code="save" />"
						onclick="saveComparentDetail(this);" /> <input type="reset"
						id="reset" value="<spring:message code="reset.msg"/>"
						class="btn btn-warning" />

				</div>

			</form:form>

		</div>
	</div>
</div>