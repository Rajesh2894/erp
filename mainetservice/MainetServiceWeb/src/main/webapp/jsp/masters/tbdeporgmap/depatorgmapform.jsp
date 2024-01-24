<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">

	var organisationId = '';

	$(function () {
	    $("#childGrid").jqGrid({
	        url: "DepartmentOrgMap.html?getChildGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("dept.master.deptLoc.deptId"), getLocalMessage("dept.master.deptLoc.deptName"), getLocalMessage("master.tbdeporgmap.DepartmentName"), getLocalMessage("master.serviceActive"),getLocalMessage("master.grid.column.action")],
	        colModel: [
	            { name: "dpDeptid", width: 40, sortable: true, search:true,searchoptions: { "sopt": ["bw", "eq"] } },
	            { name: "dpDeptdesc", width: 100, sortable: true, search:true,searchoptions: { "sopt": ["bw", "eq"] } },
	            { name: "dpDeptdescReg", width: 100, sortable: true },
	            { name: 'deptStatus', index: 'deptStatus', width: 30, align: 'center', sortable: false,search : false,edittype:'checkbox',formatter:checkboxFormatter
      			},
      			{ name: 'mapId', index: 'mapId', width: 30, align: 'center !important', sortable: false,search : false,formatter:deleteDepartment}],
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
	        editurl:"DepartmentOrgMap.html?update",
	        caption: getLocalMessage("dept.master.deptLoc.gridCap")
	    });
	    jQuery("#childGrid").jqGrid('navGrid','#pagered',{edit:false,add:false,del:false,search:true,refresh:true}); 
		$("#pagered_left").css("width", "");
	}); 

	function deleteDepartment(cellValue, options, rowdata, action) {
		return "<a href='#' return false; class='btn btn-danger btn-sm deleteClass' title='Delete' value="+rowdata.mapId+" data="+rowdata.dpDeptid+"><i class='fa fa-trash' aria-hidden='true'></i></a>";
	}
	
	$(function() {		
		$(document).on('click', '.deleteClass', function() {
				$("#errorDivServiceCheckList").hide();
				var mapId = $(this).attr('value');
				var deptId = $(this).attr('data');
				
				var url =  "DepartmentOrgMap.html?checkEmpExists";
				var requestData = {
					"mapId" : mapId
				}
				var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', '');
				if(returnData == false){
					
					var url =  "DepartmentOrgMap.html?checkDeptOfficeLocation";
					var requestData = {
						"deptId" : deptId
					}
					var isMapped=__doAjaxRequestForSave(url, 'post', requestData, false,'', '');
					if(isMapped==true){
						showValidationErrorBox();
					}else{
						showDelConfirmBox(mapId);
					}
				}else{
					showDelConfirmBox(0);
				}
		});
			
	});
	 
	
	function showValidationErrorBox(){

		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
			message	+='<h4 class=\"text-blue-2 padding-10 padding-bottom-0 text-center\">'+getLocalMessage("master.orgDeptMap.valmsg.noDel")+'</h4>';
			 message	+='<div class=\'text-center padding-bottom-10\'>'+	
			'</div>';
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function showDelConfirmBox(mapId){

		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls = 'Yes';
		
		if(mapId == 0){ 
			message	+='<h4 class=\"text-blue-2 padding-10 padding-bottom-0 text-center\">'+getLocalMessage("master.orgDeptMap.valmsg.canNotDel")+'</h4>';
			 message	+='<div class=\'text-center padding-bottom-10\'>'+	
			'</div>';
		}else{
			message	+='<h4 class=\"text-blue-2 padding-10 padding-bottom-0 text-center\">'+getLocalMessage("master.orgDeptMap.valmsg.areYouSure")+'</h4>';
			 message	+='<div class=\'text-center padding-bottom-10\'>'+	
			'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2\'    '+ 
			' onclick="deleteData('+mapId+')"/>'+	
			'</div>';
		}
				
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);
	}
	
	function deleteData(mapId){
		$('#errorDivComparamMas').hide();
		var url = "DepartmentOrgMap.html?delete";
		var requestData = {"mapId" : mapId}
		$.ajax({
			url : url,
			datatype: "json",
	        mtype: "POST",
			data : requestData,
			success : function(response) {
					$.fancybox.close();
					$("#childGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showCmprmError(errorList);
			}
		});
		
		$.fancybox.close();
		$("#grid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
	}
	
	
		function mapDepOrg(){
			
			$('#errorDivComparamMas').hide();
			var url = "DepartmentOrgMap.html?addChildForm";
			 var errorList = [];
			$.ajax({
				url : url,
				success : function(response) {
					var respErrorMsg = $(response).find('#errormsg').val();
					if(respErrorMsg == null || respErrorMsg == ""){
						var divName = '.child-popup-dialog';
						$(divName).removeClass('ajaxloader');
						$(divName).html(response);
						showMsgModalBox(divName);
					}else{
						errorList.push(getLocalMessage(respErrorMsg));
						showCmprmError(errorList);
					}
					
				},
				error : function(xhr, ajaxOptions, thrownError) {
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showCmprmError(errorList);
				}
			});				
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
	function closeOutErrBox(){
		$('.error-div').hide();
	}
	
	function validateData(obj) {
		
		var errorList = [];
		if($("#tbComparamDet_cpdIdDcg").val() == '' ){
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;Please Select an Organisation</li>';

			errMsg += '</ul>';
			$(".error-div").html(errMsg);					
			$(".error-div").show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			
			errorList = [];
			return false;
		} else {
			var	formName =	findClosestElementId(obj, 'form');
			var theForm	=	'#'+formName;
			var requestData = __serializeForm(theForm);
			var url	=	$(theForm).attr('action');		
			var returnData=__doAjaxRequestForSave(url, 'post', requestData, false,'', obj);
			
			if($.isPlainObject(returnData)) {
				showDeptConfirmBox();
			} else {
				$("#content").html(returnData);
				$("#content").show();
				$('.error-div').hide();
				return false;
			}		
		}
		return false;
	}
	
	function showDeptConfirmBox(){
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
	}
	
	function proceed() {
		window.location.href='DepartmentOrgMap.html';
	}
	
	function checkboxFormatter(cellvalue, options, rowObject) {
		if(rowObject.deptStatus == 'A'){
			return "<a title='Department is Active' alt='Department is Active' value='A' class='fa fa-check-circle fa-2x green' href='#'></a>";		
		} else{			
			return "<a title='Department is InActive' alt='Department is InActive' value='A' class='fa fa-times-circle fa-2x red' href='#'></a>";
		}
	}

	
	function getDepartmentData(obj) {
		var url = "DepartmentOrgMap.html?getDepartmentData";
		var orgId = $(obj).val();
		var reqData = "organisationId="+orgId;
		$.ajax({
			url : url,
			data : reqData,
			success : function(response) {
				$("#childGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showCmprmError(errorList);
			}
		});
	}
	
	function showCmprmError(errorList){
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$("#errorDivComparamMas").html(errMsg);
		$('#errorDivComparamMas').show();
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div id="heading_wrapper" class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="master.orgDeptMap" text="Organization Department Map"/>
			<!-- <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div> -->
            </h2>
            <apptags:helpDoc url="DepartmentOrgMap.html" helpDocRefURL="DepartmentOrgMap.html"></apptags:helpDoc>
		</div>	
		<c:url value="${saveAction}" var="url_form_submit" />		
		<div class="widget-content padding">
		<form:form method="post" action="${url_form_submit}" name="departmentForm" id="departmentForm" class="form-horizontal">
			
			<div class="error-div warning-div alert alert-danger alert-dismissible" style="display:none;" id="errorDivComparamMas">
			</div>
			<c:if test="${mode != 'create'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				<form:hidden path="" />
			</c:if>

			<div class="text-center padding-top-10">
				<input type="BUTTON" id="createData" value="<spring:message code='master.addButton'/>" class="btn btn-success btn-submit" onclick="mapDepOrg()"/>				
			</div>
			
			<div class="table padding-top-10">
				<table id="childGrid"></table>
				<div id="pagered"></div> 
			</div>

		</form:form>
		</div>
	</div>	
</div>