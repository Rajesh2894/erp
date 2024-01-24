<%@page import="com.abm.mainet.common.util.UserSession"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="apptags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
setTimeout(function(){
	$('.tab-content .active').not(':first').removeClass('active'); 
},100) 

function getFunction(parent)
{
	var theForm	=	'';
	theForm	=	'#'+findClosestElementId(parent,'form');
	
	var data = __serializeForm(theForm);
	var url	=	'SectionEntry.html'+'?function';
	$('#functionId').empty();
	
	var lookUpList=__doAjaxRequest(url,'post',data,false,'json');
	var langId='<%=UserSession.getCurrent().getLanguageId()%>'
	var  optionsAsString="<option value= 0> <spring:message code="unit.SelectOption" text="Select Option"/></option>"
	for(var i = 0; i < lookUpList.length; i++)
	{
		if(langId==1)
	    	optionsAsString += "<option value='" + lookUpList[i].lookUpId + "' code='"+ lookUpList[i].lookUpId+"-"+ lookUpList[i].baAccountname +"'>" + lookUpList[i].descLangFirst+"</option>";
	    else
		    optionsAsString += "<option value='" + lookUpList[i].lookUpId + "' code='"+ lookUpList[i].lookUpId+"-"+ lookUpList[i].baAccountname +"'>" + lookUpList[i].descLangSecond+"</option>";    		
	}
	$("#functionId").append( optionsAsString );

	
}

function getFunctionCount(url,data){
	return __doAjaxRequest(url,'post',data,false,'json');
}

function goBack()
{
	var	url	=	'SectionEntryForm.html?Cancel';
	var returnData=__doAjaxRequest(url, 'post',{}, false);	
	$(formDivName).html(returnData);
	$(formDivName).show();
	
	return false;
}
function retainDetails(rowId)
{
	var	url	=	'SectionEntryForm.html?edit';
	var data	=	'rowId='+rowId;
	var response	=	__doAjaxRequest(url,'post',data,false,'html');
	$(formDivName).html(response);
}

</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated">
	<div class="widget">
	     <div class="widget-header">
		<h2><spring:message code="eip.addlink"/></h2>
		<apptags:helpDoc url="SectionEntry.html"></apptags:helpDoc>
		</div>
		 <c:if test="${command.makkerchekkerflag ne 'C'}">	
			<div class="widget-content padding ">
			
				
				
					<form:form action="SectionEntry.html?search" method="post" class="form-horizontal">
					    
					    <div class="form-group">
									
								<label class="col-sm-2 control-label" for="moduleId"><spring:message code="SectionEntryFormModel.moduleName"  text="SectionEntryFormModel.moduleName"/></label>
								 <apptags:lookupField items="${command.modules}" path="moduleId" cssClass="form-control" selectOptionLabelCode="eip.selectModule" hasId="true" changeHandler="getFunction(this)"/>
							
								<label class="col-sm-2 control-label" for="functionId"><spring:message code="SectionEntryFormModel.functionName" text="SectionEntryFormModel.functionName"/></label>
								  <apptags:lookupField items="${command.functions}" path="functionId" cssClass=" form-control" selectOptionLabelCode="eip.function.name" hasId="true"/> 
							
								<input type="hidden" id="langId" value="${langId}"/>	
						</div>
						<div class="text-center">							
								<a href="javascript:void(0);" class="btn btn-blue-2" onclick="openForm('SectionEntryForm.html')">
								<i class="fa fa-plus"></i> <spring:message code="section.addSection" text="section.addSection" /></a>
								<button  type="submit" class="btn btn-success"><i class="fa fa-search"></i> <spring:message code="eip.search"/></button>
							<spring:url var="cancelButtonURL" value="SectionEntry.html" />
						<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
								code="rstBtn" text="Reset" /></a>
						</div>
						
					</form:form>
					
					<br>
					<ul id="demo2" class="nav nav-tabs">
					<li class="active"><a href="#Pending" data-toggle="tab"><spring:message
								code="pending" text="Pending" /></a></li>
					<li><a href="#Authenticated" data-toggle="tab"><spring:message
								code="authenticated" text="Authenticated" /></a></li>
					<li><a href="#Rejected" data-toggle="tab"><spring:message
								code="rejected" text="Rejected" /></a></li>
				</ul>
				
				<div class="tab-content tab-boxed">
					<div class="tab-pane fade active in" id="Pending">
							<apptags:jQgrid url="SectionEntry.html?SEARCH_RESULT_PEN" mtype="post"
									colHeader="SectionEntryFormModel.moduleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuEng', index : 'custOrderMenuEng',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuReg', index : 'custOrderMenuReg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionEntryFormPen" 
									editurl="SectionEntryForm.html"
									height="200" 
									id="subLinkPending" 
									loadonce="true" 
									hasEdit="true"
									hasDelete="false"
									caption="section.header"/>
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid url="SectionEntry.html?SEARCH_RESULT_APP" mtype="post"
									colHeader="SectionEntryFormModel.moduleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuEng', index : 'custOrderMenuEng',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuReg', index : 'custOrderMenuReg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionEntryFormApp" 
									editurl="SectionEntryForm.html"
									height="200" 
									id="subLinkPending" 
									loadonce="true" 
									hasEdit="true"
									hasDelete="false"
									caption="section.header"/>
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid url="SectionEntry.html?SEARCH_RESULT_REJ" mtype="post"
									colHeader="SectionEntryFormModel.moduleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuEng', index : 'custOrderMenuEng',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuReg', index : 'custOrderMenuReg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionEntryFormRej" 
									editurl="SectionEntryForm.html"
									height="200" 
									id="subLinkRejected" 
									loadonce="true" 
									hasEdit="true"
									hasDelete="false"
									caption="section.header"/>
					</div>
					
					</div>
					
				</div>
				</c:if>
			  <c:if test="${command.makkerchekkerflag eq 'C'}">	
				<div class="widget-content padding ">
			
				
				
					<form:form action="SectionEntry.html?AdminFaqCheker/search" method="post" class="form-horizontal">
					    
					    <div class="form-group">
									
								<label class="col-sm-2 control-label"><spring:message code="SectionEntryFormModel.moduleName"  text="SectionEntryFormModel.moduleName"/></label>
								 <apptags:lookupField items="${command.modules}" path="moduleId" cssClass="form-control" selectOptionLabelCode="Select Module" hasId="true" changeHandler="getFunction(this)"/>
							
								<label class="col-sm-2 control-label"><spring:message code="SectionEntryFormModel.functionName" text="SectionEntryFormModel.functionName"/></label>
								 <apptags:lookupField items="${command.functions}" path="functionId" cssClass=" form-control" selectOptionLabelCode="Select Function" hasId="true"/>
						</div>
						<div class="text-center">							
								<!-- <a href="javascript:void(0);" class="btn btn-blue-2" onclick="openForm('SectionEntryForm.html')"> -->
								<%-- <i class="fa fa-plus"></i> <spring:message code="section.addSection" text="section.addSection" /></a> --%>								
								<button  type="submit" class="btn btn-success"><i class="fa fa-search"></i> <spring:message code="eip.search"/></button>
								<spring:url var="cancelButtonURL" value="SectionEntry.html?AdminFaqCheker" />
								<a role="button" class="btn btn-warning" href="${cancelButtonURL}">Reset</a>
						</div>
						
					</form:form>
					
					<br>
					<ul id="demo2" class="nav nav-tabs">
					<li class="active"><a href="#Pending" data-toggle="tab"><spring:message
								code="pending" text="Pending" /></a></li>
					<li><a href="#Authenticated" data-toggle="tab"><spring:message
								code="authenticated" text="Authenticated" /></a></li>
					<li><a href="#Rejected" data-toggle="tab"><spring:message
								code="rejected" text="Rejected" /></a></li>
				</ul>
				
				<div class="tab-content tab-boxed">
					<div class="tab-pane fade active in" id="Pending">
							<apptags:jQgrid url="SectionEntry.html?SEARCH_RESULT_PEN" mtype="post"
									colHeader="SectionEntryFormModel.moduleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true ,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuEng', index : 'custOrderMenuEng',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuReg', index : 'custOrderMenuReg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionEntryFormPen"
									editurl="SectionEntryForm.html" 
									deleteURL="SectionEntryForm.html" 
									height="200" 
									id="subLinkPending" 
									loadonce="true" 
									hasEdit="true"
									hasDelete="true"
									caption="section.header"/>
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid url="SectionEntry.html?SEARCH_RESULT_APP" mtype="post"
									colHeader="SectionEntryFormModel.moduleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true ,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuEng', index : 'custOrderMenuEng',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuReg', index : 'custOrderMenuReg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionEntryFormApp" 
									editurl="SectionEntryForm.html"
									deleteURL="SectionEntryForm.html" 
									height="200" 
									id="subLinkAuthenticated" 
									loadonce="true" 
									hasEdit="true"
									hasDelete="true"
									caption="section.header"/>
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid url="SectionEntry.html?SEARCH_RESULT_REJ" mtype="post"
									colHeader="SectionEntryFormModel.moduleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true ,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuEng', index : 'custOrderMenuEng',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'custOrderMenuReg', index : 'custOrderMenuReg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionEntryFormRej" 
									editurl="SectionEntryForm.html"
									deleteURL="SectionEntryForm.html" 
									height="200" 
									id="subLinkRejected" 
									loadonce="true" 
									hasEdit="true"
									hasDelete="true"
									caption="section.header"/>
					</div>
					
					</div>
					
					
				
				</div>
				</c:if>
				
		</div>
		
	</div>
	
