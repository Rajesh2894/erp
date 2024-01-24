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

	function saveCustBankData(obj){
		
		
		if($("#tbCustbanks_pgFlag").is(':checked')){
			$("#tbCustbanks_pgFlag").val('Y');
		}else{
			$("#tbCustbanks_pgFlag").val('N');
		}
				
		if($("#tbCustbanks_cbBankcode").val() == '' || $("#tbCustbanks_cbBankcode").val() == undefined){
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+getLocalMessage('master.bank.code.empty')+'</li></ul>';
			
			$("#errorDivCustBankDet").html(errMsg);
			$("#errorDivCustBankDet").show();
			return false;
		}
		
		if($("#tbCustbanks_cbBranchname").val() == '' || $("#tbCustbanks_cbBranchname").val() == undefined){
			
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+getLocalMessage('master.branch.name.empty')+'</li></ul>';
			
			$("#errorDivCustBankDet").html(errMsg);
			$("#errorDivCustBankDet").show();
			return false;	
		}
				
		if($("#tbCustbanks_cbCity").val() == '' || $("#tbCustbanks_cbCity").val() == undefined){
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+getLocalMessage('master.city.name.empty')+'</li></ul>';
			
			$("#errorDivCustBankDet").html(errMsg);
			$("#errorDivCustBankDet").show();
			return false;
		}
		
		if($("#tbCustbanks_cbAddress").val() == '' || $("#tbCustbanks_cbAddress").val() == undefined){
			
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+getLocalMessage('master.address.empty')+'</li></ul>';
			$("#errorDivCustBankDet").html(errMsg);
			$("#errorDivCustBankDet").show();
			return false;
		}
		
		var requestData = {
			"cbBankcode" 	: $("#tbCustbanks_cbBankcode").val(),
			"cbBranchname" 	: $("#tbCustbanks_cbBranchname").val(),
			"cbCity"      	: $("#tbCustbanks_cbCity").val(),
			"cbAddress"		: $("#tbCustbanks_cbAddress").val(),
			"pgFlag"		: $("#tbCustbanks_pgFlag").val(),
			"modeId"		: $("#modeId").val()
		}
			
		
		$.ajax({
			url : 'CustbanksMaster.html?createChildData',
			data : requestData,
			type : 'POST',			
			success : function(response) {
				
				if( response == "-1") {
					var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeOutErrBox()" width="32"/></div><ul><li>duplicate Bank Code</li></ul>';
					$("#errorDivCustBankDet").html(errMsg);
					$("#errorDivCustBankDet").show();
					return false;
				}
				
				_closeChildForm('.child-popup-dialog');
				$.fancybox.close();
				
				$("#childGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
		
	}
	
	function check_digit(e, obj) {
		var keycode;

		if (window.event)
			keycode = window.event.keyCode;
		else if (e) {
			keycode = e.which;
		} else {
			return true;
		}

		var fieldval = (obj.value);

		if (keycode == 8 || keycode == 9 || keycode == 13) {
			// back space, tab, delete, enter 
			return true;
		}
		if ((keycode >= 32 && keycode <= 47)
				|| (keycode >= 58 && keycode <= 126)) {
			return false;
		} else {
			return true;
		}
	}

</script>



<div class="common-popup">
		<div class="widget margin-bottom-0">
	
	<c:url value="${saveAction}" var="url_form_submit" />

	<div class="error-div" style="display:none;" id="errorDivCustBankDet"></div>
	<form:form method="post" action="${url_form_submit}" name="custBankForm" id="custBankForm" class="form-horizontal">
		
		<div class="widget-header">
		<h2><b><spring:message code="common.master.branch" text="Branch"/></b><spring:message code="contract.label.name" text="Name"/></h2>
		</div>
		<form:hidden path="formMode" id="modeId" />
	<div class="widget-content padding">
		
		<!-- DATA FIELD : cbBankcode -->
		<div class="form-group">
			<!-- The field label is defined in the messages file (for i18n) -->
			<label for="tbCustbanks_cbBankcode" class="col-sm-5 control-label required-control"><spring:message code="tbCustbanks.cbBankcode"/></label>
			<div class="col-sm-7">
				<form:input id="tbCustbanks_cbBankcode" path="cbBankcode" class="form-control mandClassColor" maxLength="20" onkeypress="return check_digit(event,this);" readonly="${tbCustbanks.formMode eq 'EDIT' ? true : false}"/>
				<form:errors id="tbCustbanks_cbBankcode_errors" path="cbBankcode" cssClass="label label-danger" />

			</div>
		</div>

		<!-- DATA FIELD : cbBranchname -->
		<div class="form-group">
			<!-- The field label is defined in the messages file (for i18n) -->
			<label for="tbCustbanks_cbBranchname" class="col-sm-5 control-label required-control"><spring:message code="tbCustbanks.cbBranchname"/></label>
			<div class="col-sm-7">
				<form:input id="tbCustbanks_cbBranchname" path="cbBranchname" class="form-control mandClassColor" maxLength="500"  />
				<form:errors id="tbCustbanks_cbBranchname_errors" path="cbBranchname" cssClass="label label-danger" />

			</div>
		</div>


		<!-- DATA FIELD : cbCity -->
		<div class="form-group">
			<!-- The field label is defined in the messages file (for i18n) -->
			<label for="tbCustbanks_cbCity" class="col-sm-5 control-label required-control"><spring:message code="tbCustbanks.cbCity"/></label>
			<div class="col-sm-7">
				<form:input id="tbCustbanks_cbCity" path="cbCity" class="form-control mandClassColor" maxLength="100"  />
				<form:errors id="tbCustbanks_cbCity_errors" path="cbCity" cssClass="label label-danger" />

			</div>
		</div>


		<!-- DATA FIELD : cbAddress -->
		<div class="form-group">
			<!-- The field label is defined in the messages file (for i18n) -->
			<label for="tbCustbanks_cbAddress" class="col-sm-5 control-label required-control"><spring:message code="tbCustbanks.cbAddress"/></label>
			<div class="col-sm-7">
				<form:input id="tbCustbanks_cbAddress" path="cbAddress" class="form-control mandClassColor" maxLength="250"  />
				<form:errors id="tbCustbanks_cbAddress_errors" path="cbAddress" cssClass="label label-danger" />

			</div>
		</div>

		<!-- DATA FIELD : pgFlag -->
		<div class="form-group">
			<!-- The field label is defined in the messages file (for i18n) -->
			<label for="tbCustbanks_pgFlag" class="col-sm-5 control-label required-control"><spring:message code="tbCustbanks.pgFlag"/></label>
			<div class="col-sm-7">
				<input type="checkbox" name="pgFlag" class="checkbox-inline nomargin" value="" id="tbCustbanks_pgFlag">
				<form:errors id="tbCustbanks_pgFlag_errors" path="pgFlag" cssClass="label label-danger" />

			</div>
		</div>
		
			<div class="text-center">
			<input type="button" class="btn btn-success btn-submit" value="<spring:message code="save"/>" onclick="saveCustBankData(this);"/>
		
		</div>
		</div>
		
	</form:form>
		
</div>
</div>

