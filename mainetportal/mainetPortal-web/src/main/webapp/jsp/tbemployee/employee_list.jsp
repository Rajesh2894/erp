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
<script>

var empId = '';
function searchEmployeeData()
{
	$('.error-div').hide();
	
	var errorList = [];
	
	if ($.trim($(getElemId('deptId')).val()) == '') {
		errorList.push(getLocalMessage("Please select Department"));
	}
	
	if (errorList.length == 0) 
	{
	var deptId=$("#deptId").val();
	var locId=$("#locationId").val();
	var designId=$("#designId").val();
	var url = "EmployeeMaster.html?getEmployeeData"
	var requestData = {"deptId" : deptId,
			"designId" : designId
			};
	 var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content");
	 $.ajax({
			url : url,
			data : requestData,
			datatype: "json",
			beforeSend : function(xhr){if(header && header != null){xhr.setRequestHeader(header, token);}},
			type : 'POST',
			success : function(response) {
				if(response == "success")
				$("#employeegrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
				else{
					$("#employeegrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
					showErrormsgboxTitle(getLocalMessage("No records found for selected search criteria"));
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
	                   getLocalMessage("employee.department"),
	                   getLocalMessage("employee.rolecode"),/* "Status", *//* getLocalMessage('master.grid.column.action'), */
	                   getLocalMessage("master.view"),
	               	   getLocalMessage("eip.commons.bt.edit")
	               	  ],
	        colModel: [
				{ name: "fullName", width: 40, sortable: true, search:true },
				{ name: "emploginname", width: 40, sortable: true, search:true },            
	            { name: "deptName", width: 40, sortable: true, search:true },
	            //df# 120068 rolecode needs to be shown in place of designation -->starts
	            { name: "grCode", width: 40, sortable: true, search:true },
	          //df# 120068 rolecode needs to be shown in place of designation -->ends
	            /* { name: "isDeleted", align: 'center !important', width: 20, sortable: false, search:false,formatter : statusFormatter }, */
	            /* { name: 'empId', index: 'empId', 
	            	align: 'center !important', sortable: false, 
	            	width : 40,formatter:actionFormatter,search : false
	            } */
	            { name: 'empId', index: '', align: 'center !important', sortable: false, width : 10,formatter:actionView,search : false},
	            { name: 'empId', index: '', align: 'center !important', sortable: false, width : 10,formatter:actionEdit,search : false}
	        ],
	        pager: "#pagered",
	        rowNum: 20,
	        rowList: [5, 10, 20, 50,100],
	        sortname: "empId",
	        sortorder: "desc",
	        height:'auto',
	        /* width:'1256', */
	        width:'auto',
	        shrinkToFit: true,
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
	        caption: "Employee List"
	    });	    
	    jQuery("#employeegrid").jqGrid('navGrid', '#pagered', { edit : false,add : false,del : false,refresh : true});
		$("#pagered_left").css("width", "");
	}); 
	
	
/* function actionFormatter(cellvalue, options, rowObject){
	//var isSuperAdmin = $('isSuperAdmin').val();
	if(rowObject.designName == getLocalMessage("organisation.designation")){
		return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewEmployee('"+rowObject.empId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a>";
	}else{
		return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewEmployee('"+rowObject.empId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a> "+
		"<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editEmployee('"+rowObject.empId+"','update')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
	}
} */
function actionView(cellvalue, options, rowObject){
	return "<a class='btn btn-blue-3 btn-sm' title='View' onclick=\"viewEmployee('"+rowObject.empId+"','view')\"><i class='fa fa-eye' aria-hidden='true'></i></a>";
}
function actionEdit(cellvalue, options, rowObject){
	return "<a class='btn btn-warning btn-sm' title='Edit' onclick=\"editEmployee('"+rowObject.empId+"','update')\"><i class='fa fa-pencil' aria-hidden='true'></i></a> ";
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
		 var token = $("meta[name='_csrf']").attr("content");
		 var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url : url,
			data : requestData,
			beforeSend : function(xhr){if(header && header != null){xhr.setRequestHeader(header, token);}},
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
	 var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url : url,
		data : requestData,
		beforeSend : function(xhr){if(header && header != null){xhr.setRequestHeader(header, token);}},
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
		var errorList = [];
		
		/* if ($.trim($(getElemId('deptId')).val()) == '') {
			errorList.push(getLocalMessage("Please select Department"));
		}
		if ($.trim($(getElemId('locationId')).val()) == '') {
			errorList.push(getLocalMessage("Please select Location"));
		}
		if ($.trim($(getElemId('designId')).val()) == '') {
			errorList.push(getLocalMessage("Please select Designation"));
		} */
		
		if (errorList.length == 0) 
		{
		var url = "EmployeeMaster.html?addEmployeeData";
		var deptId=$("#deptId").val();
		var designId=$("#designId").val();
		var requestData = {"deptId" : deptId,
				"designId" : designId};
		var token = $("meta[name='_csrf']").attr("content");
		 var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url : url,
			data : requestData,
			beforeSend : function(xhr){if(header && header != null){xhr.setRequestHeader(header, token);}},
			type : 'POST',
			success : function(response) {
				
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showEmpError(errorList);
			}
		});	
	    
		}
		else {
			showEmpError(errorList);			
		}
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
          <h2><spring:message code="common.employee.master" text="Employee Master"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
		
		<form:form method="post" action="EmployeeMaster.html" name="employeeMaster" id="employeeMaster" class="form-horizontal">
			
			  <div class="error-div alert alert-danger alert-dismissible"></div>
			 <div id="parentDataDivId">
			 
			 
			<%-- <div class="form-group">
			
			<input type="hidden" value="${errorMsg}" id="errorMsg"/>
			<input type="hidden" value="${isSuperAdmin}" id="isSuperAdmin"/>
                <label class="col-sm-2 control-label required-control">Department</label>
             	<div class="col-sm-4">
             		<c:if test="${userSession.languageId eq 1}">
	              		<form:select id="deptId" path="" cssClass="form-control chosen-select-no-results">
							<form:option value="" >Select Department</form:option>
							<c:forEach items="${departmentlist}" var="departMstData">						
								<form:option value="${departMstData.dpDeptid}">${departMstData.dpDeptdesc}</form:option>						
							</c:forEach> 
						</form:select>
					</c:if>
					 <c:if test="${userSession.languageId eq 2}">
					 <form:select id="deptId" path="" cssClass="form-control chosen-select-no-results">
						<form:option value="" >Select Department</form:option>
						<c:forEach items="${departmentlist}" var="departMstData">						
							<form:option value="${departMstData.dpDeptid}">${departMstData.dpNameMar}</form:option>						
						</c:forEach> 
					</form:select>
					 </c:if>
					</div>
				
				<label class="col-sm-2 control-label required-control">Designation</label>
              	<div class="col-sm-4">
              		<form:select id="designId" path="" cssClass="form-control chosen-select-no-results">
						<form:option value="" >Select Designation</form:option>
					</form:select>
				</div>
				
			</div>	 --%>
		
		
	<div class="text-center margin-bottom-10">
	
		<%-- <input type="button" class="btn btn-blue-2" value="<spring:message code="" text="Search"/>" onclick="return searchEmployeeData()"/>
		<input type="button" class="btn btn-warning" value="Reset" onclick="resetEmpForm()"/> --%>
		<span class="otherlink"> 
			<a href="javascript:void(0);" class="btn btn-success" id="addButton">
			<spring:message code="admin.addBm" text="Add"/></a>
		</span>
	</div>
		<table id="employeegrid"></table>
		<div id="pagered"></div> 
		</div>
		
	</form:form>
	</div>
	</div>
	</div>
	
