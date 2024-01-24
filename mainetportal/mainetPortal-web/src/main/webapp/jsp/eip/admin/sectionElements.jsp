<%@ taglib tagdir="/WEB-INF/tags" prefix="apptags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<script>

$(document).ready(function(){	
	$('#dd_txt_area').hide();	
	$(".csvopt").on("keyup",function(){$(this).val($(this).val().replace(/\s*,\s*/ig, ','));});
	
	if($("#fieldType").val()==3){
		$('#dd_txt_area').show();
	}else{
		$('#dd_txt_area').hide();
	}
});

function fn_show(obj) {	
	if(obj.value == 3){
		$('#dd_txt_area').show();
	}else{
		$('#dd_txt_area').hide();
	}
}



function openUpdateForm(formName,actionParam,rowId)
{
	
	var theForm	=	'#'+formName;
	
	var divName	=	formDivName;
	
	var url	=	$(theForm).attr('action');
	
	if (!actionParam) 
	{
		
	}
	else
	{
		url+='?'+actionParam;
	}
	
	
	
	var requestData = 'rowId='+rowId;
	var ajaxResponse	=	doAjaxLoading(url, requestData, 'html', divName);
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	
	/*prepareTags();*/
	
	$(divName).show();
	
	
}
$(document).ready(function(){
	
	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";

	if(pathname.indexOf(txt) > -1) {
		$(".checker").show();
		$(".macker").hide();
		$("#checkerFlag1").val("Y");
	} else {
		$(".checker").hide();
		$(".macker").show();
		$("#checkerFlag1").val("N");
		}
		
	
});
$(function() {
	$("#command").validate();
});
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated"> 
	<div class="widget">
	     <div class="widget-header">  
	    	 <h2>${command.entity.subLinkNameEn}</h2>
	     </div>
			<div class="widget-content padding">

<c:if test="${command.entity.hasSubLink eq 'N'}">
</c:if>

<c:if test="${command.listMode}">
<div>
<c:set var="sec"><spring:message code='admin.Section'/></c:set>
<c:set var="successMsg"><spring:message code='admin.save.successmsg'/></c:set>
					<c:choose>
						<c:when test="${command.editMode eq true  }">
							<c:choose>

								<c:when
									test="${(command.entity.checkerFlag1 ne 'N') and (not empty command.entity.chekkerflag ) and (command.entity.chekkerflag eq 'Y')   }">
									<c:set var="successMsg">
										<spring:message code='admin.approve.successmsg' />
									</c:set>
								</c:when>
								<c:when
									test="${(command.entity.checkerFlag1 ne 'N') and (not empty command.entity.chekkerflag ) and (command.entity.chekkerflag eq 'N')  }">
									<c:set var="successMsg">
										<spring:message code='admin.reject.successmsg' />
									</c:set>
								</c:when>
								<c:otherwise>
									<c:set var="successMsg">
										<spring:message code='admin.update.successmsg' />
									</c:set>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:set var="successMsg">
								<spring:message code='admin.save.successmsg' />
							</c:set>
						</c:otherwise>
					</c:choose>
					<div class="text-center  padding-bottom-20">
<form:form action="SectionEntryForm.html">
<form:hidden path="entity.checkerFlag1" id="checkerFlag1"/>
	<apptags:submitButton pageSpecSuccessMsg= "${successMsg }" entityLabelCode="${sec}" buttonLabel="eip.dept.section" successUrl="SectionEntry.html?AdminFaqCheker" cssClass="btn btn-success checker"/>
	<apptags:submitButton pageSpecSuccessMsg= "${successMsg }" entityLabelCode="${sec}" buttonLabel="eip.dept.section" successUrl="SectionEntry.html" cssClass="btn btn-success macker"/>
	<input type="button" class="btn btn-danger" value="<spring:message code="eip.dept.cancel" />" onclick="retainDetails('${command.entity.id}')"/> 
</form:form>
</div>
</div>
</c:if>
<c:choose>
	<c:when test="${command.listMode}">
		<table class="table table-bordered table-striped margin_top_10 arial">
			<tr>
				<th style="width:20px"><spring:message code="section.fieldOrder" text="Field Order"/></th>
				<th><spring:message code="section.FieldEn" text="Field Name (English)"/></th>
				<th><spring:message code="section.FieldRg" text="Field Name (Hindi)"/></th>
				<th><spring:message code="section.Delete" text="Delete"/></th>
			</tr>
		
			<c:forEach items="${command.entity.subLinkFieldMappings}" var="subLinkFieldMap">
				<c:if test="${subLinkFieldMap.isDeleted eq 'N'}">
						<tr>
							<td><a href="javascript:void(0);" onclick="return openUpdateForm('frmSectionEntryForm${subLinkFieldMap.rowId}','EditElement',${subLinkFieldMap.rowId});">${subLinkFieldMap.orderNo}</a></td>
							<td><a href="javascript:void(0);" onclick="return openUpdateForm('frmSectionEntryForm${subLinkFieldMap.rowId}','EditElement',${subLinkFieldMap.rowId});">${subLinkFieldMap.fieldNameEn}</a></td>
							<td><a href="javascript:void(0);" onclick="return openUpdateForm('frmSectionEntryForm${subLinkFieldMap.rowId}','EditElement',${subLinkFieldMap.rowId});">${subLinkFieldMap.fieldNameRg}</a></td>
							<td><a href="javascript:void(0);" onclick="return deleteElementForLink('frmSectionEntryForm${subLinkFieldMap.rowId}','DeleteElement',${subLinkFieldMap.rowId});"><img alt="Delete" src="css/images/delete.png" width="17"/></a></td>
							
							<form:form  action="SectionEntryForm.html" name="frmSectionEntryForm${subLinkFieldMap.rowId}" id="frmSectionEntryForm${subLinkFieldMap.rowId}" method="post" class="form">
								<input type="hidden" name="rowId" value="${subLinkFieldMap.rowId}"/>
							</form:form>
								
							
						</tr>
				</c:if>
			</c:forEach>
			<c:if test="${command.entity.subLinkFieldMappings.size() eq 0}">
				<tr>
					<td colspan="4" class="text-center"><spring:message code="section.no.records" text="No record available."/></td>			
				</tr>
			</c:if>
		</table>
		
		<c:if test="${command.entity.hasSubLink eq 'N'}">
		
		<div class="text-center padding-top-20">
			<a href="javascript:void(0);" onclick="return openForm('SectionEntryForm.html','AddElement');" class="btn btn-blue-2 macker"><i class="fa fa-plus"></i> <spring:message code="section.AddElement" text="section.AddElement"/></a>
		</div>
		
		</c:if>
		
	</c:when>
	
	<c:otherwise>
	
		<form:form action="SectionEntryForm.html" method="post" class="form-horizontal">
			
			<jsp:include page="/jsp/tiles/validationerror.jsp"/>
	  
	  <c:choose>   
	  <c:when test="${sectionType3 eq'EEE'}"> 
		 <div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="section.FieldEn" text="section.FieldEn"/><span class="mand">*</span> :</label>
					<div class="col-sm-4">
	                  <apptags:inputField fieldPath="subLinkFieldMapping.fieldNameEn" cssClass="form-control"/>
					</div>
 					
				
					<label class="col-sm-2 control-label"><spring:message code="section.FieldRg" text="section.FieldRg"/> <span class="mand">*</span> :</label>
				    <div class="col-sm-4">		
					    <apptags:inputField fieldPath="subLinkFieldMapping.fieldNameRg" cssClass="form-control"/>
					</div>
				
			</div>

					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message code="section.FieldType" text="section.FieldType" />
						<span class="mand">*</span> :</label>
						<div class="col-sm-4">

							<c:set var="type" value="${command.subLinkFieldMapping.fieldType}" />					
							<c:if test="${not empty fieldType2 }">
							<c:set var="fieldType" value="${command.getNonHierarchicalLookUpObject(fieldType2[0]).lookUpCode}" />
							</c:if>
							<c:if test="${empty fieldType2 }">
							<c:set var="lookupId" value="${command.subLinkFieldMapping.sectionType}" />
							<c:set var="fieldType" value="${command.getNonHierarchicalLookUpObject(lookupId).lookUpCode}" />
							</c:if>
							<c:if test="${empty fieldType}">
								<c:set var="fieldType" value="${command.findFieldType(type)}" />
							</c:if>
							<%-- <apptags:selectField items="${command.getFieldTypes(fieldType)}" isLookUpItem="true" fieldPath="subLinkFieldMapping.fieldType"
								selectOptionLabelCode="Select Field Type" cssClass="form-control" changeHandler="fn_show(this);"/> --%>
							<apptags:selectField isLookUpItem="true" changeHandler="fn_show(this);" items="${command.getFieldTypes(fieldType)}" hasId="true" fieldPath="subLinkFieldMapping.fieldType" selectOptionLabelCode="Select Field Type" cssClass="form-control" />
							
						
						</div>
						
						 	<label class="control-label col-sm-2 required-control"><spring:message code="section.fieldOrder" text="Field Order"/></label>
							
						 	<div class="col-sm-4">
								<form:input path="subLinkFieldMapping.orderNo" cssClass="form-control texboxcase mandClassColor"/>
				        	</div>
				        <div class="clearfix"></div>	
						<div id="dd_txt_area" class="padding-top-5">
							<label class="control-label col-sm-2 required-control"><spring:message code="section.CSVDrpEN" text="Enter Option Value"/></label>
							<div class="col-sm-4">
								<form:textarea path="subLinkFieldMapping.dropDownOptionEn" cssClass=" form-control texboxcase csvopt mandClassColor"/>
							</div>
							<label class="control-label col-sm-2 required-control"><spring:message code="section.CSVDrpREG" text="Enter Option Value"/></label>
							<div class="col-sm-4">
								<form:textarea path="subLinkFieldMapping.dropDownOptionReg" cssClass=" form-control texboxcase csvopt mandClassColor"/>
							</div><div class="clearfix"></div>
						</div>
								
						<label class="col-sm-2 control-label">
							<spring:message code="section.Mandatory" text="section.Mandatory" />
						</label>
									
						<div class="col-sm-6 padding-top-7">
							<form:checkbox path="subLinkFieldMapping.isMandatory" value="Y" />
						</div>
					</div>

				</c:when>
		 </c:choose>
		 	<c:set var="order" value="${subLinkFieldMapping.orderNo}" />
		 	
			<c:forEach items="${sectionType2}" var="lookUpssectionType2" varStatus="lk"> 
			
		       <c:forEach items="${command.sectionType}" var="lookUpssectionType" varStatus="lk2"> 
		   
					
				  <c:if test="${lookUpssectionType2 == lookUpssectionType.lookUpId }"> 
					   <div class="form-group">
							<label class="control-label col-sm-2 required-control"><spring:message code="section.FieldEn" text="section.FieldEn"/></label>
							<div class="col-sm-4">
							<c:set var="FieldNameEnglish" value="${command.getAppSession().getMessage('eip.admin.section.FieldNameEnglish') }" />
								<form:input path="subLinkFieldMapping.subLinkFieldlist[${ lk.index}].fieldNameEn" cssClass=" form-control texboxcase mandClassColor" data-rule-required="true" data-msg-required="${FieldNameEnglish}"/>
							</div>
							
						
							<label class="control-label col-sm-2 required-control"><spring:message code="section.FieldRg" text="section.FieldRg"/></label>
							<div class="col-sm-4">
							<c:set var="FieldNameRegional" value="${command.getAppSession().getMessage('eip.admin.section.FieldNameRegional') }" />
 							   <form:input path="subLinkFieldMapping.subLinkFieldlist[${ lk.index}].fieldNameRg" cssClass=" form-control texboxcase mandClassColor" data-rule-required="true" data-msg-required="${FieldNameRegional}"/>
							</div>
				     </div>
					
					<div class="form-group">
								<label class="control-label col-sm-2 required-control"><spring:message code="section.FieldType" text="section.FieldType"/></label>
								<div class="col-sm-4"> 
								 
									 <apptags:selectField changeHandler="fn_show(this);" items="${command.getFieldTypes(lookUpssectionType.lookUpCode )}" isLookUpItem="true"  fieldPath="subLinkFieldMapping.subLinkFieldlist[${ lk.index}].fieldType" selectOptionLabelCode="Select Field Type" cssClass=" form-control"/> 
								</div>
																
								 	<label class="control-label col-sm-2"><spring:message code="section.fieldOrder" text="Order"/></label>
									
								 	<div class="col-sm-4">
										<form:input path="subLinkFieldMapping.orderNo" value="0.0" cssClass="form-control texboxcase mandClassColor"/>
						        	</div>
						        <div class="clearfix"></div>	
								<div id="dd_txt_area" class="padding-top-5">
									<label class="control-label col-sm-2 required-control"><spring:message code="section.CSVDrpEN" text="Enter Option Value"/></label>
									<div class="col-sm-4">
										<form:textarea path="subLinkFieldMapping.dropDownOptionEn" cssClass=" form-control texboxcase csvopt mandClassColor"/>
									</div>
									<label class="control-label col-sm-2 required-control"><spring:message code="section.CSVDrpREG" text="Enter Option Value"/></label>
									<div class="col-sm-4">
										<form:textarea path="subLinkFieldMapping.dropDownOptionReg" cssClass=" form-control texboxcase csvopt mandClassColor"/>
									</div><div class="clearfix"></div>
								</div>
								
								<label class="col-sm-2 control-label">
									<spring:message code="section.Mandatory" text="section.Mandatory" />
								</label>					
								<div class="col-sm-6 padding-top-7">
									<form:checkbox path="subLinkFieldMapping.isMandatory" value="Y" />									
								</div>	
					</div>
					
				 </c:if> 
					</c:forEach>
					</c:forEach> 
			<div class="text-center">
				<input type="button"  class="btn btn-success" value="<spring:message code="section.saveElement"  text="section.saveElement"/>" onclick="return saveOrUpdateForm(this,false, undefined,'saveElement');"/>
				<input type="button" class="btn btn-danger" value="<spring:message code="section.cancelElement"  text="section.cancelElement"/>" onclick="return goBack();"/>
			</div>
		
		</form:form>
	
	</c:otherwise>
</c:choose>
</div>
</div>
</div>
