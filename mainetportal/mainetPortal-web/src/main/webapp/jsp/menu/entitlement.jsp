<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/menu/jquery.cookie.js"></script>
<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css">
<script src="js/menu/jquery.dynatree.js"></script>
<script src="js/menu/entitlement.js"></script>
<script>
//Transalator Function
$( document ).ready(function() {
	   
	   
	   var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
			$("#groupDescE").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#groupDescE").val().trim();
				if(no_spl_char!='')
				{
					
					commonlanguageTranslate(no_spl_char,'groupDescR',event,'');
				}else{
					$("#groupDescR").val('');
				}
			});
		}
});

</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="menu.form.title" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
		
			<div class="clearfix" id="content">
			
			
				<div class="form-div">
					<form:form method="post" action="entitlement.html?saveForm"
						name="frmmanageRoleForm" id="frmmanageRoleForm"
						class="form-horizontal">

						
						
						<spring:message code="menu.entitle.addNode" var="addNode" />
						<spring:message code="menu.entitle.deactiveNode" var="deActiveNode" />
						<spring:message code="menu.entitle.upadte" var="update" />
						<spring:message code="menu.entitle.edit" var="editNode" />
						<spring:message code="menu.entitle.back" var="backBtn" />
						<spring:message code="menu.entitle.activeOrInactive" var="activeOrInactive" />

						<div class="text-center margin-bottom-10">
						  <span id="existTempBtn">
						    <a href="javascript:void(0);" class="btn btn-blue-1" onclick="showTemplate('C');"><spring:message code="menu.new.template.link" /></a>
						  </span>
					    <span id="createTempBtn">
					   		 <a href="javascript:void(0);" class="btn btn-blue-1" onclick="showTemplate('E');"><spring:message code="menu.exist.template.link" /></a>
					    </span>
						    <input type="button" id="updateNodeBtn" class="btn btn-info" value='${update}'>
							<input type="button" id="entitleAdd" class="btn btn-blue-2" value='${addNode}'> 
							<input type="button" id="entitleEdit" class="btn btn-danger" value='${editNode}'>
							<input type="button" id="activeNewNodeBtn" class="btn btn-primary" value='${activeOrInactive}'>
							<input type="button" id="dataEntitleBtn" class="btn btn-primary" value='Transactional Entitlement'>
						</div>
			            <jsp:include page="/jsp/tiles/validationerror.jsp" />
			
						<c:if test="${command.successMsg eq true}">
							<div class="success-div alert alert-success alert-dismissible margin-bottom-20">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
								<ul>
									<li><spring:message code="menu.create.role.success" /></li>
								</ul>
							</div>
						</c:if>

						<div id="existTemp">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control" id="selectLAbel"> <spring:message
									code="menu.roleCode.label" />
							</label>
							<div class="col-sm-4">
								<select id="roleSelect" class="form-control"></select>
								<p>
									
								</p>
							</div>

						</div>
						</div>

						<div id="createTemp">
						
						<div class="form-group">
						
								
							<label class="col-sm-2 control-label required-control"> <spring:message
									code="menu.new.roleCode.label" />
							</label>
							<div class="col-sm-4">
								<input type="text" name="roleName"
									class="hasNotAllowSpecialLang form-control" id="roleName" />
								
							</div>
						</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"> <spring:message
									code="menu.role.description.label" /> <spring:message
									code="menu.langType.english.label" /> :
							</label>
							<div class="col-sm-4">
								<input type="text" class="hasNotAllowSpecialLang form-control"
									name="groupDescE" id="groupDescE" />
							</div>
							<label class="col-sm-2 control-label"> <spring:message
									code="menu.role.description.label" /> <spring:message
									code="menu.langType.reg.label" /> :
							</label>
							<div class="col-sm-4">
								<input type="text" name="groupDescR" class="form-control"
									id="groupDescR" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="menu.structure.label" /> :</label>
						<div class="col-sm-10">
							<div id="tree" class="notes createStructure">
								<c:set var="node" value="${command.entitlements}" scope="request" />
								<jsp:include page="entitlementDynaTree.jsp"></jsp:include>
							</div>
						</div>
						<div class="col-sm-10">
							<div class="existStructure notes"></div>
 						</div>
 						</div>
 						<spring:message code="menu.entitle.save" var="saveRole" />
						

						<input type="hidden" name="menuIds" id="menuIds" /> <input
							type="hidden" name="deActiveNodes" id="deActiveNodes" /> <input
							type="hidden" name="checkAction" id="checkAction" />

						<div class="text-center clearfix padding-top-10">
							<input type="button" id="entitleSubmit" class="btn btn-success" value='${saveRole}'>

							<apptags:backButton url="CitizenHome.html" /> 

						</div>
                       </form:form>
				</div>
			</div>
		</div>
	</div>
</div>