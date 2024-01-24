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
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<c:set var="langId" value="${userSession.languageId}"/> 
<script type="text/javascript">

$(document).ready(function() {
	// #116501
	//$('#locationId').attr('disabled',true);
	//$('#designId').attr('disabled',true);
	//$('#gmid').attr('disabled',true);
	var envFlag = $("#envFlag").val();
	if (envFlag == 'Y'){
		
		$("#orgid").change(function() {
		$('#masId').html('');
		$('#masId').append($("<option></option>").attr("value","").text('Select'));
			var orgid = $("#orgid").val();
			var postdata = 'orgid=' + orgid;
			
			var json = __doAjaxRequest('EmployeeMaster.html?getMasterDetail','POST', postdata, false, 'json');
			 $.each(json, function( index, value) {
				 if(value.id != null){
						 $("#masId").append($("<option></option>")
								 .attr("value",value.id)
								 .text(value.name)).trigger("chosen:updated");
				 }
			 });
			 
			 var json = __doAjaxRequest('EmployeeMaster.html?getDeptIdByOrgIdForSFAC','POST', postdata, false, 'json');
			   
			    $('#deptIdSfac').val(json);
		});
	}
	
$("#deptId").change(function() {
	var language=${langId};
	var deptId = $(this).val();

	$('#locationId').attr('disabled',false);
	$('#designId').attr('disabled',false);
	$('#gmid').attr('disabled',false);
	
	var selectobject=document.getElementById('locationId');
	var userObject=document.getElementById('designId');
	$(selectobject).find('option:gt(0)').remove();
	$(userObject).find('option:gt(0)').remove();
	
	if (deptId > 0 && deptId!='')
	{
		var postdata = 'deptId=' + deptId;
		
		var json = __doAjaxRequest('EmployeeMaster.html?locationList','POST', postdata, false, 'json');
		
		var userJson = __doAjaxRequest('EmployeeMaster.html?designationList','POST', postdata, false, 'json');
	 
		 $.each(json, function( index, value) {
			 if(value.locId != null){
				 
				 if(language==1)
					{
					 $("#locationId")
					 .append($("<option></option>")
							 .attr("value",value.locId)
							 .text(value.locNameEng));
					}else{
						$("#locationId")
						 .append($("<option></option>")
								 .attr("value",value.locId)
								 .text(value.locNameReg));
					}
				
			 }
		 });
		
		$.each(userJson, function( index, value) {
			 if(value.dsgid != null){
				 
				 if(language==1)
					{
					 $("#designId")
					 .append($("<option></option>")
							 .attr("value",value.dsgid)
							 .text(value.dsgname));
					}else{
						$("#designId")
						 .append($("<option></option>")
								 .attr("value",value.dsgid)
								 .text(value.dsgnameReg));
					}
				
			 }
		 });
		
		 $(".chosen-select-no-results").trigger("chosen:updated");
	
	}
			
		});
});


var empId = '';
function searchEmployeeData()
{
	$('.error-div').hide();
	/* 119809 */
	var errorList = [];
	var deptId=$("#deptId").val();
	var locId=$("#locationId").val();
	var designId=$("#designId").val();
	var gmid=$("#gmid").val();
	var orgid=$("#orgid").val();
	var masId=$("#masId").val();
	var envFlag = $("#envFlag").val();
	if (envFlag == 'Y'){
		if ($.trim($(getElemId('orgid')).val()) == '' && $.trim($(getElemId('deptId')).val()) == '' && $.trim($(getElemId('gmid')).val()) == '' &&  $.trim($(getElemId('designId')).val()) == '' && $.trim($(getElemId('locationId')).val()) == '') {
			errorList.push(getLocalMessage("emp.error.validate.data"));
		}
	} else{
     if ($.trim($(getElemId('deptId')).val()) == '' && $.trim($(getElemId('gmid')).val()) == '' &&  $.trim($(getElemId('designId')).val()) == '' && $.trim($(getElemId('locationId')).val()) == '') {
	errorList.push(getLocalMessage("emp.error.validate.data"));
     } 
   }
	
	if (errorList.length == 0) 
	{
 	if (envFlag == 'Y'){
	var url = "EmployeeMaster.html?getEmployeeDataForSfac"
	var requestData = {"deptId" : deptId,
			"locId" : locId,
			"designId" : designId,
			"gmid" : gmid,
			"orgid" : orgid,
			"masId" :masId
			};
	}else{ 
		var url = "EmployeeMaster.html?getEmployeeData"
			var requestData = {"deptId" : deptId,
					"locId" : locId,
					"designId" : designId,
					"gmid" : gmid
					};
	}
	 $.ajax({
			url : url,
			data : requestData,
			datatype: "json",
			type : 'POST',
			success : function(response) {
				if(response == "success")
				$("#employeegrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				else{
					$("#employeegrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
					showErrormsgboxTitle(getLocalMessage("emp.error.notValid.noRecord"));
				}
					
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
	} else {
		showEmpError(errorList);
	}
 
}


$(function () {
	$('.error-div').hide();
	    $("#employeegrid").jqGrid({
	        url: "EmployeeMaster.html?loadEmployeeGridData",
	        datatype: "json",
	        mtype: "GET",
	        colNames: [getLocalMessage("employee.name"),getLocalMessage("employee.loginname"), 
	                   getLocalMessage("employee.department"),getLocalMessage("employee.location"), 
	                   getLocalMessage("employee.designation"),/* "Status", */getLocalMessage('master.grid.column.action')],
	        colModel: [
				{ name: "fullName", width: 50, sortable: true, search:true },
				{ name: "emploginname", width: 50, sortable: true, search:true },            
	            { name: "deptName", width: 50, sortable: true, search:true },
	            { name: "location", width: 50, sortable: true, search:true },
	            { name: "designName", width: 50, sortable: true, search:true },
	            /* { name: "isDeleted", align: 'center !important', width: 20, sortable: false, search:false,formatter : statusFormatter }, */
	            { name: 'empId', index: 'empId', 
	            	align: 'center !important', sortable: false, 
	            	width : 40,formatter:actionFormatter,search : false
	            }
	        ],
	        pager: "#pagered",
	        rowNum: 20,
	        rowList: [5, 10, 20, 50,100],
	        sortname: "empId",
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
	        caption: getLocalMessage("employee.lbl.gridHeader")
	    });	    
	    jQuery("#employeegrid").jqGrid('navGrid', '#pagered', { edit : false,add : false,del : false,refresh : true});
		$("#pagered_left").css("width", "");
	}); 
	
	
function actionFormatter(cellvalue, options, rowObject){
	var isSuperAdmin = $("#isSuperAdmin").val();
	console.log('isSuperAdmin :'+isSuperAdmin);
	
	if($("#isSuperAdmin").val() == 'Y' && !(rowObject.designName == getLocalMessage("organisation.designation"))){
		return "<a class='btn btn-blue-3 btn-sm' title='"+getLocalMessage("employee.view")+"' onclick=\"viewEmployee('"+rowObject.empId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "+
		"<a class='btn btn-warning btn-sm' title='"+getLocalMessage("employee.edit")+"' onclick=\"editEmployee('"+rowObject.empId+"','update')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";		
     }else{		
		return "<a class='btn btn-blue-3 btn-sm' title='"+getLocalMessage("employee.view")+"' onclick=\"viewEmployee('"+rowObject.empId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a>";
	}
}

function statusFormatter(cellvalue, options, rowObject){
	if (rowObject.isDeleted == '0') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"+rowObject.isDeleted+"'  alt='Employee is Active' title='Employee is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"+rowObject.isDeleted+"' alt='Employee is  InActive' title='Employee is InActive'></a>";
	}
}
	
function viewEmployee(empId){
		var url = "EmployeeMaster.html?editEmployeeForm";
		var requestData = "empId="+empId+"&flag="+'N';
		$.ajax({
			url : url,
			data : requestData,
			type : 'POST',
			success : function(response) {
				
				var divName = '.content';		
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	
	    
	    return false;
}

function editEmployee(empId){
	var url = "EmployeeMaster.html?editEmployeeForm";
	var requestData = "empId="+empId+"&flag="+'Y';
	$.ajax({
		url : url,
		data : requestData,
		type : 'POST',
		success : function(response) {
			
			var divName = '.content';
			
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
    
    return false;
}

function deleteEmployee(empId){
	showConfirmBoxEmployee(empId);	
}
	

	$(document).on('click', '#addButton', function() {
	
		var url = "EmployeeMaster.html?addEmployeeData";
				var requestData ={};
		$.ajax({
			url : url,
			data : requestData,
			type : 'POST',
			success : function(response) {
				
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				alert(errorList);
				showEmpError(errorList);
			}
		});	
	  });


function showConfirmBoxEmployee(empId){
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = 'Yes';
	
	message	+='<h4 class=\"text-center text-blue-2 padding-10\">Are you sure want to delete?</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>'+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="deleteDataEmployee('+empId+')"/>'+
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}


function deleteDataEmployee(empId){
	var url = "EmployeeMaster.html?delete";
	var requestData = "empId="+empId;
	$.ajax({
		url : url,
		data : requestData,
		type : 'POST',
		success : function(response) {
			
			console.log('Hhh:::'+$('#errorMsg').val());
			alert(response);
			
			$("#employeegrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		},
		error : function(xhr, ajaxOptions, thrownError) {
			var errorList = [];
			errorList.push(getLocalMessage("admin.login.internal.server.error"));
			showError(errorList);
		}
	});	
    
    return false;
}




function closeErrBox(){
	$(".error-div").hide();
}

function showEmpError(errorList){
	
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('.error-div').html(errMsg);
	$('.error-div').show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	 return false;
}

function resetEmpForm(){
	window.location.href = 'EmployeeMaster.html';	
}
</script>
<apptags:breadcrumb></apptags:breadcrumb>	
	<div class="content" id="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="master.empMaster" text="Employee Master"/></h2>
          <apptags:helpDoc url="EmployeeMaster.html" helpDocRefURL="EmployeeMaster.html"></apptags:helpDoc>
        </div>
        <div class="widget-content padding">
		<div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
		<form:form method="post" action="EmployeeMaster.html" name="employeeMaster" id="employeeMaster" class="form-horizontal">
		<input type="hidden" value="${envFlag}" id="envFlag" />
			<input type="hidden" value="${errorMsg}" id="errorMsg"/>
			 <input type="hidden" value="${isSuperAdmin}" id="isSuperAdmin"/>
			  <form:hidden path="" id="deptIdSfac"/>
			  <div class="error-div alert alert-danger alert-dismissible"></div>
			 <div id="parentDataDivId">
			
		<c:if test="${envFlag == 'N'}">
			<div class="form-group">
		  <label class="col-sm-2 control-label required-control"><spring:message code="employee.department" text="Department"/></label>
             	<div class="col-sm-4">
             		
	              		<form:select id="deptId" path="" cssClass="form-control chosen-select-no-results">
							<form:option value="" ><spring:message code="employee.select.department" text="Select Department"/></form:option>
							<c:forEach items="${departmentlist}" var="departMstData">	
							<c:if test="${userSession.languageId eq 1}">					
								<form:option value="${departMstData.dpDeptid}">${departMstData.dpDeptdesc}</form:option>	
							</c:if>
							<c:if test="${userSession.languageId eq 2}">	
							 <form:option value="${departMstData.dpDeptid}">${departMstData.dpNameMar}</form:option>		
							</c:if>				
							</c:forEach> 
						</form:select>
										
					</div>
				
				<label class="col-sm-2 control-label required-control"><spring:message code="employee.location" text="Location"/></label>
				<div class="col-sm-4">
              		<form:select id="locationId" path="" cssClass="form-control chosen-select-no-results">
						<form:option value="" ><spring:message code="employee.select.location" text="Select Location"/></form:option>
								<c:forEach items="${locationlist}" var="locationData">
									<c:if test="${userSession.languageId eq 1}">
										<form:option value="${locationData.locId}">${locationData.locNameEng}</form:option>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
										<form:option value="${locationData.locId}">${locationData.locNameReg}</form:option>
									</c:if>
								</c:forEach>
							</form:select>
				</div>
			</div>	
			
			
			<div class="form-group">
                <label class="col-sm-2 control-label required-control"><spring:message code="employee.designation" text="Designation"/></label>
              	<div class="col-sm-4">
              		<form:select id="designId" path="" cssClass="form-control chosen-select-no-results">
						<form:option value="" ><spring:message code="employee.select.designation" text="Select Designation"/></form:option>
							<c:forEach items="${designlist}" var="designData">
									<c:if test="${userSession.languageId eq 1}">
										<form:option value="${designData.dsgid}">${designData.dsgname}</form:option>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
										<form:option value="${designData.dsgid}">${designData.dsgnameReg}</form:option>
									</c:if>
								</c:forEach>
					</form:select>
				</div>
				
		
				 <label class="col-sm-2 control-label "><spring:message code="master.grpName" text="Group Name"/></label>
              	<div class="col-sm-4">
              		<form:select id="gmid" path="" cssClass="form-control chosen-select-no-results">
						<form:option value="" ><spring:message code="master.selectGrpName" text="Select Group"/></form:option>
						<form:options items="${groupMap}" />
					</form:select>
				</div>
		
			</div>
			
			</c:if>


	  <c:if test="${envFlag == 'Y'}">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="emp.organisation.type" text="Organisation Type" /></label>
							<div class="col-sm-4">
									<c:if test="${userSession.languageId eq 1}">
										<form:select id="orgid" path=""
											cssClass="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code="master.selectDropDwn" text="Select" />
											</form:option>
											<c:forEach items="${orgList}" var="org">
												<form:option value="${org.orgid}">${org.oNlsOrgname}</form:option>
											</c:forEach>
										</form:select>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
										<form:select id="orgid" path=""
											cssClass="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code="master.selectDropDwn" text="Select" />
											</form:option>
											<c:forEach items="${orgList}" var="org">
												<form:option value="${org.orgid}">${org.oNlsOrgnameMar}</form:option>
											</c:forEach>
										</form:select>
									</c:if>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="emp.organisation.Name" text="Organisation Name" /></label>
							<div class="col-sm-4">
								<form:select id="masId" path=""
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="comparentMas.select" text="Select" />
									</form:option>
								</form:select>
							</div>


						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="employee.designation" text="Designation" /></label>
							<div class="col-sm-4">
								<form:select id="designId" path=""
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="employee.select.designation"
											text="Select Designation" />
									</form:option>
									<c:forEach items="${designlist}" var="designData">
										<c:if test="${userSession.languageId eq 1}">
											<form:option value="${designData.dsgid}">${designData.dsgname}</form:option>
										</c:if>
										<c:if test="${userSession.languageId eq 2}">
											<form:option value="${designData.dsgid}">${designData.dsgnameReg}</form:option>
										</c:if>
									</c:forEach>
								</form:select>
							</div>


							<label class="col-sm-2 control-label "><spring:message
									code="master.grpName" text="Group Name" /></label>
							<div class="col-sm-4">
								<form:select id="gmid" path=""
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="master.selectGrpName"
											text="Select Group" />
									</form:option>
									<form:options items="${groupMap}" />
								</form:select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="employee.location" text="Location" /></label>
							<div class="col-sm-4">
								<form:select id="locationId" path=""
									cssClass="form-control chosen-select-no-results">
									<form:option value="">
										<spring:message code="employee.select.location"
											text="Select Location" />
									</form:option>
									<c:forEach items="${locationlist}" var="locationData">
										<c:if test="${userSession.languageId eq 1}">
											<form:option value="${locationData.locId}">${locationData.locNameEng}</form:option>
										</c:if>
										<c:if test="${userSession.languageId eq 2}">
											<form:option value="${locationData.locId}">${locationData.locNameReg}</form:option>
										</c:if>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</c:if>


		<div class="text-center margin-bottom-10">
	
		<input type="button" class="btn btn-blue-2" value="<spring:message code="master.search" text="Search"/>" onclick="return searchEmployeeData()"/>
		<input type="button" class="btn btn-warning" value="<spring:message code="reset.msg" text="Reset"/>" onclick="resetEmpForm()"/>
		<span class="otherlink"> 
			<a href="javascript:void(0);" class="btn btn-success" id="addButton">
			<spring:message code="add.msg" text="Add"/></a>
		</span>
	</div>
		<table id="employeegrid"></table>
		<div id="pagered"></div> 
		</div>
		
	</form:form>
	</div>
	</div>
	</div>
	
