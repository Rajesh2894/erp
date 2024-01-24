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
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/humanity/jquery.ui.all.css" />
<link href="css/grid-style.css" rel="stylesheet" type="text/css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/masters/tbcomparammas/comparamMasForm.js"></script>
<script type="text/javascript">
	jQuery('.hasCharacter').keyup(function () { 
    	this.value = this.value.replace(/[^a-z A-Z]/g,'');
	});
	jQuery('.hasAlphaNumeric').keyup(function () { 
    	this.value = this.value.replace(/[^a-z A-Z0-9]/g,'');
	});
</script>
	<div class="form-div">
	        <div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
	<c:url value="${saveAction}" var="url_form_submit" />	
	<form:form method="post" action="${url_form_submit}" name="commonMaster" id="commonMaster" class="form-horizontal">
		
		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDivComparamMas">
	  	</div>
		
		<form:hidden path="" value="${mode}" id="mainFormMode"/>
		<c:if test="${mode != 'create'}">
			<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
			<form:hidden path="cpmId" id="cpmId"/>
			<form:hidden path="userId" />
			<form:hidden path="langId" />
			<form:hidden path="cpmPrefix" />
			<form:hidden path="cpmType" />
			<form:hidden path="cpmModuleName" />
			<form:hidden path="lgIpMac" />
		</c:if>
		
		
		<input type="hidden" value="${isDefault}" id="isDefault"/>
		
		<form:hidden path="" value="" id="levelId"/>
		<h4><spring:message code="master.commonParam"/></h4>
             <div class="form-group">
             	<label class="col-sm-2 control-label  required-control"><spring:message code="master.depName"/></label>
              	<div class="col-sm-4">
              		<form:select id="tbComparamDet_cpmModuleName" path="cpmModuleName" cssClass="form-control mandClassColor chosen-select-no-results" disabled="${mode != 'create' ? true : false}">
						<form:option value="" ><spring:message code="common.master.select.dept" text="Select Department"/></form:option>
						<c:forEach items="${departmentList}" var="departmentData">
							<form:option value="${departmentData.dpDeptcode}">${departmentData.dpDeptdesc}</form:option>						
						</c:forEach>
					</form:select>
				</div>
	            <label class="col-sm-2 control-label required-control"><spring:message code="master.prefixType"/></label>
	            <div class="col-sm-4"><form:select id="tbComparamMas_cpmType" path="cpmType" cssClass="form-control mandClassColor" onchange="toggleDetailForm(this)" disabled="${mode != 'create' ? true : false}">
						<form:option value="" ><spring:message code="master.selectDropDwn"/></form:option>
						<form:options items="${prefixType}"/>
					</form:select>
	             </div>
             </div>
            <div class="form-group">
	             <label class="col-sm-2 control-label required-control">
	             <spring:message code="master.prefix"/></label>
	              	<div class="col-sm-4">
	              	<form:input id="tbComparamMas_cpmPrefix" path="cpmPrefix" style="text-transform: uppercase;" cssClass="form-control hasCharacter" maxLength="3" disabled="${mode != 'create' ? true : false}" onkeypress="return check_char(event,this);" onkeyup="getData()"/>
	             </div>
	             
	             <label class="col-sm-2 control-label  required-control"><spring:message code="master.desc"/></label>
	             <div class="col-sm-4">
	             <c:if test="${isDefault eq 'N' }">
	             	<form:textarea path="cpmDesc" id="tbComparamMas_cpmDesc" cssClass="form-control mandClassColor hasAlphaNumeric" disabled="true"/>
	             	<form:hidden path="cpmDesc"/>
	             </c:if>
	              <c:if test="${isDefault eq 'Y' }">   
		        	 <form:textarea path="cpmDesc" id="tbComparamMas_cpmDesc" cssClass="form-control mandClassColor hasAlphaNumeric"/>
	             </c:if>
		          </div>
             </div>	
             <div class="form-group">    
             	         <label class="col-sm-2 control-label"><spring:message code="common.master.action" text="Action"/></label>
             	<div class="col-sm-10">
             	<c:if test="${mode == 'create'}">
             		<label class="checkbox-inline"><form:checkbox path="cpmStatus" value="A" id="cpmStatus"/> <spring:message code="master.status"/></label>
             		<label class="checkbox-inline"><form:checkbox path="cpmReplicateFlag" value="Y" id="cpmReplicateFlag"/> <spring:message code="master.replicate"/></label>
             		<label class="checkbox-inline"><form:checkbox path="loadAtStartup" value="Y" id="loadAtStartup"/> <spring:message code="master.loatAtStrtup"/></label>
             	</c:if>
             	<c:if test="${mode != 'create' }">
             	 	<c:if test="${isDefault eq 'N' }">
             	 		<label class="checkbox-inline"><form:checkbox path="cpmStatus" id="cpmStatus" value="A" disabled="true"/> <spring:message code="master.status"/></label>				
						<form:hidden path="cpmStatus" value="${command.cpmStatus}"/>
						
						<label class="checkbox-inline"><form:checkbox path="cpmReplicateFlag" id="cpmReplicateFlag" value="Y" disabled="true"/> <spring:message code="master.replicate"/></label>				
						<form:hidden path="cpmReplicateFlag" value="${command.cpmReplicateFlag}"/> 
						
						<label class="checkbox-inline"><form:checkbox path="loadAtStartup" value="Y" id="loadAtStartup" disabled="true"/> <spring:message code="master.loatAtStrtup"/></label>
             			<form:hidden path="loadAtStartup" value="${command.loadAtStartup}"/>
             	 	</c:if>
             		<c:if test="${isDefault eq 'Y' }">
             			<label class="checkbox-inline"><form:checkbox path="cpmStatus" value="A" id="cpmStatus"/> <spring:message code="master.status"/></label>				
						<label class="checkbox-inline"><form:checkbox path="cpmReplicateFlag" value="Y" id="cpmReplicateFlag"/> <spring:message code="master.replicate"/></label>				
						<label class="checkbox-inline"><form:checkbox path="loadAtStartup" value="Y" id="loadAtStartup"/> <spring:message code="master.loatAtStrtup"/></label>           			
             		</c:if>          	
             	</c:if>            			
             	</div>
             </div>
             
             <div id="addRightsDiv">
             <h4><spring:message code="common.master.add.rights" text="Add Rights"/></h4>
			<div class="form-group">
			
			<label class="col-sm-2 control-label"><spring:message code="common.master.select.options" text="Select Options"/></label>
             	<div class="col-sm-10">
             		<label class="checkbox-inline"><form:checkbox path="cpmConfig" value="Y" id="cpmConfig" onclick="toggleAddDetails(this)"/> <spring:message code="master.addDetails"/></label>
					<label class="checkbox-inline"><form:checkbox path="cpmEditDesc" value="Y" id="cpmEditDesc" onclick="toggleEditDesc(this)"/> <spring:message code="master.editDesc"/></label>
					<label class="checkbox-inline"><form:checkbox path="cpmEditDefault" value="Y" id="cpmEditDefault" onclick="toggleEditDefault(this)"/> <spring:message code="master.editDefault"/></label>			
					<c:if test="${mode == 'update' }">	
					<label class="checkbox-inline"><form:checkbox path="cpmEditStatus" value="Y" id="cpmEditStatus" onclick="toggleStatus(this)"/> <spring:message code="master.editStatus"/></label>			
					</c:if>
					<label class="checkbox-inline"><form:checkbox path="cpmEditValue" value="Y" id="cpmEditValue" onclick="toggleEditValue(this)"/> <spring:message code="master.editval" text="Edit value"/></label>
					<form:hidden path="" id="isEditDesc"/>
					<form:hidden path="" id="isEditDefault"/>
					<form:hidden path="" id="isEditStatus"/>
					<form:hidden path="" id="isEditValue"/>
            
				</div>
			</div>
			</div>
         <div class="text-center padding-bottom-10" id="addBtnDiv">
			<input type="BUTTON" id="createData" value="<spring:message code="master.addButton"/>" class="btn btn-success"/>
		</div>
		
		<div id="comparamDetId">
			<table id="childGrid"></table>
			 <script>
				loadChildGridData();
			</script>
			<div id="pagered"></div>
		</div>
		
		<div id="comparentDetId" class="hide">
			<table id="comparentMasChildGrid"></table>
			<script>
				loadComparentMasChildGridData();
			</script>
			<div id="cmPagered"></div>
			<div id="parentDataDivId"></div>
			<div class="padding-10 text-center">
			<input type="BUTTON" id="addChildbtn" value="<spring:message code="add.msg"/>" class="btn btn-success hide" onclick="addChildData(this)"/>
			</div>
			<table id="comparentDetChildGrid"></table>
			<script>
				loadComparentDetChildGridData();
			</script>				
			<div id="cdPagered"></div>
		</div> 
		<div class="text-center padding-top-10">
			<input type="submit" id="saveBtn" class="btn btn-success btn-submit" value="<spring:message code="master.save"/>" onclick="return validateData(this)" >
			<c:if test="${mode == 'create'}">
			<input type="button" id="reset" value="<spring:message code="reset.msg"/>" class="btn btn-warning" onclick="resetPrefixMasterForm()"/>
			</c:if>		
			<input type="button" id ="backBtn" class="btn btn-danger" value="<spring:message code="back.msg"/>" onclick="window.location.href='ComparamMaster.html'" />
		</div>
	</form:form>
	</div>
	