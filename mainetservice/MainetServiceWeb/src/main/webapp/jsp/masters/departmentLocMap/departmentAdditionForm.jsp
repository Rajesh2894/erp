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

	function saveDepartmentData(obj){
	
		var deptId = $('#dpDeptid').val();
		
		if(deptId != "" && deptId != null){
			var requestData = __serializeForm('form');
			
			$.ajax({
				url : 'TbDeptLocation.html?create',
				data : requestData,
				type : 'POST',			
				success : function(response) {
					_closeChildForm('.child-popup-dialog');
					$.fancybox.close();
					showConfirmBoxforSaved();
					$("#deptLocationGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
					
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});
		}else{
			var errorList = [];
			errorList.push(getLocalMessage("master.deptLocMap.valmsg.selDept"));
			showDeptLocErr(errorList);
		}

	}
	
	function setOrgName(obj){
		$("#dpDeptdesc").val($("#dpDeptid").find('option:selected').text());
	}
	
	function showDeptLocErr(errorList){
		var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox1()"><span aria-hidden="true">&times;</span></button><ul>';
		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';
		$("#errorDivDeptAdd").html(errMsg);
		$('#errorDivDeptAdd').show();
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}
	
	function closeErrBox1(){
		$("#errorDivDeptAdd").hide();
	}
	
	function showConfirmBoxforSaved(){

		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls ='OK';
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">Record Saved Successfully</h4>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
		' onclick="proceed()"/>'+
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
		 return false;
	}

	function proceed() {		
		$.fancybox.close();
	}
</script>
<div class="common-popup">
<div class="content">
	<div class="widget margin-bottom-0">

		<div class="widget-content padding">	
			<div class="error-div alert alert-danger alert-dismissible" style="display:block;" id="errorDivDeptAdd"></div>
			<form:form method="post" action="" name="orgDetForm" id="orgDetForm" class="form-horizontal">
				<div class="form-group">
					<label for="dpDeptid" class="col-sm-4 control-label"><spring:message code="contract.label.department" text="Department"/></label>
					<div class="col-sm-8">
						<form:select id="dpDeptid" path="dpDeptid" cssClass="form-control" onchange="setOrgName(this)">
							<form:option value=""><spring:message code="master.deptLocMap" text="Select Department"/></form:option>
							
							<c:forEach items="${deptList}" var="dept">
							 <c:if test="${userSession.languageId eq 1}">					
							    <form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
						     </c:if>
						     <c:if test="${userSession.languageId eq 2}">					
							    <form:option value="${dept.dpDeptid}">${dept.dpNameMar}</form:option>
						     </c:if>
							</c:forEach>
						</form:select>
						<form:errors id="tbDepartment_cpdIdDcg_errors" path="dpDeptid" cssClass="label label-danger" />
						<form:hidden path="dpDeptdesc" id="dpDeptdesc"/>
						
					</div>
				</div>
				<div class="text-center">		
					<input type="button" class="btn btn-success btn-submit" value="<spring:message code="save" text="Save"/>" onclick="saveDepartmentData(this);"/>
				</div>
			</form:form>
		</div>
</div>
</div>
</div>