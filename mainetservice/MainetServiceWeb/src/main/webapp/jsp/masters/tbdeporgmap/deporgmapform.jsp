<%@page language="java" contentType="text/html; charset=ISO-8859-1" 
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
		
		var deptData = $("#tbOrganisation_orgid").val();
		
		if(deptData == '' ){
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
			errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp\''+getLocalMessage("master.orgDeptMap.valmsg.selDept")+'\'</li>';

			errMsg += '</ul>';
			$("#errorDivOrgDet").html(errMsg);					
			$("#errorDivOrgDet").show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			
			errorList = [];
			return false;
		}

		var requestData = {
				"dpDeptid" 		: $("#tbOrganisation_orgid").val(),
				"dpDeptdesc" 	: $("#dpDeptdesc").val(),
				"dpNameMar" 	: $("#dpNameMar").val()}
		
		$.ajax({
			url : 'DepartmentOrgMap.html?createChildData',
			data : requestData,			
			success : function(response) {
				_closeChildForm('.child-popup-dialog');
				$.fancybox.close();
				showConfirmBox();
				$("#childGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});
		
	}
	
	
	function showConfirmBox(){

		var	errMsgDiv		=	'.msg-dialog-box';
		var message='';
		var cls ='OK';
		message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+getLocalMessage("master.orgDeptMap.recordSaved")+'</h4>';
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
	
	function setOrgName(obj){
		$("#dpDeptdesc").val($("#tbOrganisation_orgid").find('option:selected').text());
		$("#dpNameMar").val($("#tbOrganisation_orgid").find('option:selected').attr('code'));
	}
</script>
<div class="common-popup">
<div class="content">
	<div class="widget margin-bottom-0 min-width-400">

		<div class="widget-content padding">	
			
			<form:form method="post" action="" name="orgDetForm" id="orgDetForm" class="form-horizontal">
				<div class="mand-label clearfix"><span><spring:message code="contract.breadcrumb.fieldwith" text="Field with"/><i class="text-red-1">*</i><spring:message code="common.master.mandatory" text="is mandatory"/></span></div>
				<div class="error-div warning-div alert alert-danger alert-dismissible" style="display:none;" id="errorDivOrgDet">
				</div>
				
				<div class="form-group">
				<input type="hidden" value="${errormsg}" id="errormsg"/>
				
					<label for="tbOrganisation_orgid" class="col-sm-4 control-label required-control"><spring:message code="contract.label.department" text="Department"/></label>
					<div class="col-sm-8">
						<form:select id="tbOrganisation_orgid" path="dpDeptid" cssClass="form-control" onchange="setOrgName(this)">
							<form:option value=""><spring:message code="common.master.select.dept" text="Select Department"/></form:option>
							<c:forEach items="${deptList}" var="department">
							 <c:if test="${userSession.languageId eq 1}">
							 <form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
							 </c:if>	
							 <c:if test="${userSession.languageId eq 2}">
							  <form:option value="${department.dpDeptid}">${department.dpNameMar}</form:option>
							 </c:if>	 
							</c:forEach>
						</form:select>
						
						<form:errors id="tbDepartment_cpdIdDcg_errors" path="dpDeptid" cssClass="label label-danger" />
						<form:hidden path="dpDeptdesc" id="dpDeptdesc"/>
						<form:hidden path="dpNameMar" id="dpNameMar"/>
					</div>
				</div>
				<div class="clear padding-top-10 text-center">		
					<input type="button" class="btn btn-success btn-submit" value="<spring:message code="save"/>" onclick="saveDepartmentData(this);"/>
				</div>
			</form:form>
		</div>
</div>
</div>
</div>