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

function loadChildGridData(){
	 $("#childGrid").jqGrid({
	        url: "CustbanksMaster.html?getChildGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("master.tbcustbanksmas.BankCode"), getLocalMessage("master.tbcustbanksmas.BranchName"), getLocalMessage("master.tbcustbanksmas.City"), getLocalMessage("locationMas.address"), getLocalMessage("master.tbcustbanksmas.PGFlag"), getLocalMessage("master.editSelected")],
	        colModel: [
	            { name: "cbBankcode", sortable: true },
	            { name: "cbBranchname", sortable: false },
	            { name: "cbCity", sortable: false },
	            { name: "cbAddress", sortable: false },
	            { name: "pgFlag", sortable: false },
	            { name: 'cbBankid', index: 'cbBankid', align: 'center', sortable: false,formatter:editData}],
	        pager: "#pagered",
	        rowNum: 5,
	        rowList: [5, 10, 20, 30],
	        sortname: "cmBankid",
	        sortorder: "desc",
	        height:'auto',
	        viewrecords: true,
	        gridview: true,
	        loadonce: true,
	        jsonReader : {
	            root: "rows",
	            page: "page",
	            total: "total",
	            records: "records", 
	            repeatitems: false,
	           }, 
	        autoencode: true,
	        editurl:"CustbanksMaster.html?update",
	        caption: "Customer Bank Entry List"
	    }); 
}

	$(function () {
	   
	}); 

	function editData(cellValue, options, rowdata, action) {
	    return "<a href='#'  return false; class='editData'><img src='css/images/edit.png' width='20px' alt='Edit Bank Details' title='Edit bank Details' /></a>";
	}
	
$(function() {
		
		$(document).on('click', '.editData', function() {
			
		    var bankId = $(this).closest('tr').find('td:eq(0)').text();
			var url = "CustbanksMaster.html?editChildForm";
			var requestData = "bankCode="+bankId;
			$.ajax({
				url : url,
				data : requestData,
				success : function(response) {
					var divName = '.child-popup-dialog';
					
					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
					//prepareTags();
					showMsgModalBox(divName);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});		    
		    return false;
		  });
	});
	
		
		$("#createData").click(function(){
			
			var cmBankname = $("#tbCustbanksMas_cmBankname").val();
			
			if(cmBankname == ''){
				//alert('please enter Bank Name');
				var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+getLocalMessage('master.bank.name.empty')+'</li></ul>';
				$("#errorDivCustBankMas").html(errMsg);
				$("#errorDivCustBankMas").show();
				return false;
			}
			
			var url = "CustbanksMaster.html?addChildForm";
			var returnData = "cmBankname="+cmBankname;

			$.ajax({
				url : url,
				data : returnData,
				success : function(response) {
					
					var divName = '.child-popup-dialog';
					
					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
					//prepareTags();
					showMsgModalBox(divName);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});				
				
		});
			
	function closeOutErrBox(){
		$('.error-div').hide();
	}
	
	function showMsgModalBox(childDialog) {
		
		$.fancybox({
			type : 'inline',
			href : childDialog,
			openEffect : 'elastic', // 'elastic', 'fade' or 'none'
			helpers : {
				overlay : {
					closeClick : false
				}
			},
			keys : {
				close : null
			}
		});	
		return false;
	}
	
	function validateData(obj) {
		
		if($("#tbCustbanksMas_cmBankname").val() == ''){
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'+getLocalMessage('master.bank.name.empty')+'</li></ul>';
			
			$("#errorDivCustBankMas").html(errMsg);
			$("#errorDivCustBankMas").show();
			return false;
		
		} else {
			var	formName =	findClosestElementId(obj, 'form');
			var theForm	=	'#'+formName;
			var requestData = __serializeForm(theForm);
			var url	=	$(theForm).attr('action');			
			var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
			
			if($.isPlainObject(returnData)) {
				showConfirmBox();
			} else {				
				$("#heading_wrapper").html(returnData);
				$("#heading_wrapper").show();
				$(".error-div").removeClass("hide");
				return false;
			}
		}
		return false;
	}
	
	function showConfirmBox(){
		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Proceed';
		
		message	+='<p>Form Submitted Successfully</p>';
		 message	+='<p style=\'text-align:center;margin: 5px;\'>'+	
		'<br/><input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
		' onclick="proceed()"/>'+	
		'</p>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
		
		return false;
	}

	function proceed() {
		window.location.href='CustbanksMaster.html';
	}
	
</script>

<div id="heading_wrapper">

	<div class="form-div">
	
		<c:url value="${saveAction}" var="url_form_submit" />
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="common.master.cust.bank" text="Cust Bank"/><b><spring:message code="common.master.custbank.master" text="Master"/> </b></h2>
        </div>
        <div class="widget-content padding">
        <div class="alert alert-danger alert-dismissible error-div" style="display:none;" id="errorDivCustBankMas"></div>
	
		<form:form method="post" action="${url_form_submit}" name="hormaster" id="hormaster" class="form-horizontal">
			
			<c:if test="${mode != 'create'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				<form:hidden path="cmBankid" />
				<form:hidden path="userId" />
				<form:hidden path="langId" />
			</c:if>
			
			<div class="form-group">
				<label for="tbCustbanksMas_cmBankname" class="col-sm-2 control-label required-control"><spring:message code="tbCustbanksMas.cmBankname"/></label>
				<div class="col-sm-4">					
					<form:input id="tbCustbanksMas_cmBankname" path="cmBankname" class="form-control" maxLength="500"  />
				</div>
			</div>
			
			<div class="padding-10 text-center">		
				<input type="BUTTON" id="createData" value="Create Data" class="btn btn-success"/>				
			</div>
			
				<table id="childGrid">
					 <script>loadChildGridData();</script>
				</table>
				<div id="pagered"></div> 
			
			<div class="padding-10 text-center">
				<input type="submit" class="btn btn-success btn-submit" value="<spring:message code="save"/>" onclick="return validateData(this);">
				<input type="button" class="btn btn-default" value="Back" onclick="window.location.href='CustbanksMaster.html'" />
			
			</div>
			
		</form:form>
	</div>
</div>
	</div>
	</div>
