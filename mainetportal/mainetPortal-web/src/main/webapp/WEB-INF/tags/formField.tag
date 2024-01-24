<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag" %>

<%@ attribute name="fieldType" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="fieldPath" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="labelCode" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="labelCodeRg" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="isPassword" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="isMandatory" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="hasId" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="isReadonly" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="isDisabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="showFileNameHTMLId" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="maxlength" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="validnFunction" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="fileSize" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="currentCount" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="maxFileCount" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="folderName" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="removeDeleteOption" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="allowClassicStyle" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="checkListMandatoryDoc" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="checkListDesc" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="checkListId" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="checkListMStatus" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="checkListSStatus" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="checkListSrNo" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="fileErrorMsg" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="hasChildlist" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="randNum" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="callbackOtherTask" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="minSize" required="false" rtexprvalue="false" type="java.lang.Long"%>
<%@ attribute name="checklistDocSize" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="checkListDocDesc" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="multiple" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<jsp:useBean id="stringResolver" class="com.abm.mainet.common.util.StringUtility"/>

<c:if test="${fieldType==MainetConstants.TEXT_FIELD}">
	<c:set var="id" value="${stringResolver.replaceAllDotPrefix(fieldPath)}"/>
		
		<label class="col-sm-2 control-label" for="${id}_en"><spring:message code="${labelCode}" text="${labelCode}"/>
		<c:if test="${isMandatory}"><span class="mand">*</span></c:if>
		</label>
		<div class="col-sm-4">
		<form:input id="${id}_en" path="${fieldPath}_en" cssClass="${cssClass}" maxlength="${maxlength}"/>		
		</div>
		<label class="col-sm-2 control-label" for="${id}_rg" class="fontfix"><spring:message code="${labelCodeRg}" text="${labelCodeRg}"/>
		<c:if test="${isMandatory}"><span class="mand">*</span></c:if>
		</label>
		<div class="col-sm-4">
		<form:input id="${id}_rg" path="${fieldPath}_rg" cssClass="${cssClass} fontfix" maxlength="${maxlength}"/>		
	</div>
</c:if>  

<c:if test="${fieldType==MainetConstants.LINK_FIELD}">
	<c:set var="id" value="${stringResolver.replaceAllDotPrefix(fieldPath)}"/>
		
		<label class="col-sm-2 control-label" for="${id}_en"><spring:message code="${labelCode}" text="${labelCode}"/>
		<c:if test="${isMandatory}"><span class="mand">*</span></c:if>
		</label>
		<div class="col-sm-4">
		<form:input id="${id}_en" path="${fieldPath}" cssClass="${cssClass}" maxlength="${maxlength}"/>		
		</div>
		
</c:if>  

<c:if test="${fieldType==MainetConstants.TEXT_AREA}">
	<label class="col-sm-2 control-label" for="${id}_en"><spring:message code="${labelCode}" text="${labelCode}"/>
	<c:if test="${isMandatory}"><span class="mand">*</span></c:if>
	</label>
	<div class="col-sm-4">	
		<form:textarea id="${id}_en" path="${fieldPath}_en" cssClass="${cssClass}" maxlength="${maxlength}" />		
	</div>
	<label class="col-sm-2 control-label" for="${id}_rg" class="fontfix"><spring:message code="${labelCodeRg}" text="${labelCodeRg}"/>
	<c:if test="${isMandatory}"><span class="mand">*</span></c:if>
	</label>
	<div class="col-sm-4">
		<form:textarea id="${id}_rg" path="${fieldPath}_rg" cssClass="${cssClass} fontfix " maxlength="${maxlength}"/>		
	</div>
</c:if>

<c:if test="${fieldType==MainetConstants.DROP_DOWN_BOX}">
	<apptags:selectField items="${null}" selectOptionLabelCode="Select" fieldPath="${fieldPath}"/>
</c:if>

<c:if test="${fieldType==MainetConstants.DATE_PICKER}">
		<label class="col-sm-2 control-label" for="${fieldPath}"><spring:message code="${labelCode}" text="${labelCode}"/>
		<c:if test="${isMandatory}"><span class="mand">*</span></c:if>
		</label>
		<div class="col-sm-4">
		 	<div class="input-group">
		 		  <apptags:dateField fieldclass="datepicker" cssClass="form-control datepicker" datePath="${fieldPath}"  />
                  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
              </div>
		</div>
</c:if>

<c:if test="${fieldType==MainetConstants.ATTACHMENT_FIELD || fieldType==MainetConstants.PROFILE_IMG || fieldType== 6}">
 
	<c:set var="isProfileImae" value="${fieldType==MainetConstants.PROFILE_IMG}" />
	<c:set var="idappender" value="${randNum}" />
	<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
	<c:set var="id" value="${stringResolver.replaceAllDotPrefix(fieldPath)}"/>	
	
	<label class="col-sm-2 control-label" for="${id}_${idappender}"><spring:message code="${labelCode}" text="${labelCode}"/>
	<c:if test="${isMandatory}"><span class="mand">*</span></c:if>
	</label>
	
	<div class="col-sm-1 fileUpload fileinput fileinput-new"> 
	<span class="btn btn-darkblue-2 btn-file" >
		<spring:message code="bt.choosefile" />		
		<c:choose>
		<c:when test="${not empty callbackOtherTask }">
		<label for="${id}_${idappender}"><input maxsize="${fileSize}" type="file" name="${id}_${idappender}[]" id="${id}_${idappender}" class="customfileupload fileUploadClass" onchange="doFileUploading(this,${id}_${idappender},'${id}_${idappender}_file_list',${isProfileImae},'${validnFunction}','${fileSize}','${fileErrorMsg}','${currentCount}','${maxFileCount}');${callbackOtherTask};"/></label>
		
		</c:when>
		<c:otherwise>
		<label for="${id}_${idappender}"><input maxsize="${fileSize}" type="file" name="${id}_${idappender}[]" id="${id}_${idappender}" class="customfileupload fileUploadClass" onchange="doFileUploading(this,${id}_${idappender},'${id}_${idappender}_file_list',${isProfileImae},'${validnFunction}','${fileSize}','${fileErrorMsg}','${currentCount}','${maxFileCount}');"/></label>
		
		</c:otherwise>
		</c:choose>
			</span>
	
	
	<c:choose>
		<c:when test="${hasChildlist}">
			<c:set value="${id}].${stringResolver.getStringAfterChar('.',fieldPath)}" var="abc"/>
			<c:set value="${stringResolver.getStringForUpload(abc)}" var="multiUploadString" />
			<input type="hidden" id="elemPath_${id}_${idappender}" value="${multiUploadString}"/>
			<input type="hidden" id="hiddenElemPath${currentCount}" value="${multiUploadString}"/>
			<input type="hidden" id="hiddenHtmlPath${currentCount}" value="${id}_${idappender}_file_list"/>
			<input type="hidden" id="hiddenrandnum${currentCount}" value="${randNum}"/>
		</c:when>
		<c:otherwise>
			<input type="hidden" id="elemPath_${id}_${idappender}" value="${stringResolver.getStringAfterChar('.',fieldPath)}"/>
			<input type="hidden" id="hiddenrandnum" value="${randNum}"/>	
		</c:otherwise>
	</c:choose>
		
	</div>	
	<c:if test="${showFileNameHTMLId}">	
		<div id="${id}_${idappender}_file_list"></div>
	</c:if>

</c:if>

<c:if test="${fieldType==7  }">
 
	 <%-- <label for=""><spring:message code="${labelCode}" text="${labelCode}"/></label> --%>	 
	 
<!-- <div class="fileinput fileinput-new" data-provides="fileinput">
  <span class="btn btn-default btn-file">
  	<span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="..."></span>
  <span class="fileinput-filename"></span>
  <a href="#" class="close fileinput-exists" data-dismiss="fileinput" style="float: none"><i class="fa fa-times-circle"></i></a>
</div> -->
	<c:set var="callback" value=""/>
    <c:if test="${not empty callbackOtherTask}">
		<c:set var="callback" value="${callbackOtherTask}"/>
	</c:if>
	 <div class="fileUpload fileinput fileinput-new" data-provides="fileinput"> 
		<span>
		
			  <span class="btn btn-darkblue-2 btn-file">
				<span class="fileinput-new">					
					<frameworktag:input id="file" aria-label="upload button" path="${fieldPath}" fileType="file" validnfn="${validnFunction}" currentCount="${currentCount}" 			
					folderName="${folderName}" removeDeleteOption="${removeDeleteOption}"	minSize="${minSize}" checklistDocSize="${checklistDocSize}"					
					allowClassicStyle="${allowClassicStyle}" fileSize="${fileSize}" maxFileCount="${maxFileCount}"	
					checkListMandatoryDoc="${checkListMandatoryDoc}" 
					checkListDesc="${checkListDesc}" checkListId="${checkListId}" checkListDocDesc="${checkListDocDesc}" checkListMStatus="${checkListMStatus}" 
					checkListSStatus="${checkListSStatus}" checkListSrNo="${checkListSrNo}" callbackOtherTask="${callback}" multiple="${multiple}" />	
				</span>
				<span class="fileinput-exists"><spring:message code="bt.choosefile" />	</span>	
				</span>		
		</span>	
	</div>		
	
		<c:if test="${not empty labelCode}">
			<c:set var="style" value="" />
		</c:if>		
		
		<c:if test="${showFileNameHTMLId}">	
			<div id="file_list_${currentCount}" ${style}></div>
		</c:if>	
	
</c:if>
