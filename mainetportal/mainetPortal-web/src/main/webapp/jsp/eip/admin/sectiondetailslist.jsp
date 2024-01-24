<%@ taglib tagdir="/WEB-INF/tags" prefix="apptags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<script>
setTimeout(function(){
	$('.tab-content .active').not(':first').removeClass('active'); 
},100) 
function editDetails(rowId)
{
	var	url	=	'SectionDetails.html?EditDetails';
	var data	=	'rowId='+rowId;
	var returnData=__doAjaxRequest(url, 'post',data, false);	
	$(formDivName).html(returnData);
	$(formDivName).show();

	return false;
}

function deleteDetail(formName,actionParam)
{
	deleteRecord(formName,actionParam);	
}
function goBack()
{
	var	url	=	'SectionDetails.html?CancelForm';
	var returnData=__doAjaxRequest(url, 'post',{}, false);	
	$(formDivName).html(returnData);
	$(formDivName).show();
	
	return false;
} 
 </script>

<apptags:breadcrumb></apptags:breadcrumb>
	
<div class="content animated">
	<div class="widget">
	     <div class="widget-header">
	     <h2><spring:message code="section.header" text="section.header"/></h2>
	     <apptags:helpDoc url="SectionDetails.html"></apptags:helpDoc>
	     </div>
			<div class="widget-content padding">
			<div class="clearfix" id="content">
				<div class="form-div">
					<c:if test="${command.makkerchekkerflag ne 'C'}">
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
							<apptags:jQgrid url="SectionDetails.html?SEARCH_RESULT_PEN" mtype="post"
									colHeader="section.ModuleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : true,sorttype:'number',search : true,align : 'center',width :'100'},
												{name : 'subLinkNameEn', index : 'subLinkNameEn',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameRg', index : 'subLinkNameRg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionDetailsPen" 
									editurl="SectionDetails.html"
									height="200" 
									id="SectionDetailsPending" 
									loadonce="true" 
									hasViewDet="false"	
									hasEdit="true"
									viewAjaxRequest="true"								
									caption="section.header"/>
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid url="SectionDetails.html?SEARCH_RESULT_APP" mtype="post"
									colHeader="section.ModuleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : true, sorttype:'number', search : true,align : 'center',width :'100'},
												{name : 'subLinkNameEn', index : 'subLinkNameEn',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameRg', index : 'subLinkNameRg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionDetailsApp" 
									editurl="SectionDetails.html"
									height="200" 
									id="SectionDetailsAuthenticated" 
									loadonce="true" 
									hasViewDet="false"	
									hasEdit="true"
									viewAjaxRequest="true"								
									caption="section.header"/>
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid url="SectionDetails.html?SEARCH_RESULT_REJ" mtype="post"
									colHeader="section.ModuleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameEn', index : 'subLinkNameEn',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameRg', index : 'subLinkNameRg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionDetailsRej" 
									editurl="SectionDetails.html"
									height="200" 
									id="SectionDetailsRejected" 
									loadonce="true" 
									hasViewDet="false"	
									hasEdit="true"
									viewAjaxRequest="true"								
									caption="section.header"/>
					</div>
					
					</div>
					</c:if>
					<c:if test="${command.makkerchekkerflag eq 'C'}">
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
					<apptags:jQgrid url="SectionDetails.html?SEARCH_RESULT_PEN" mtype="post"
									colHeader="section.ModuleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : true, sorttype:'number',search : true,align : 'center',width :'100'},
												{name : 'subLinkNameEn', index : 'subLinkNameEn',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameRg', index : 'subLinkNameRg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionDetailsPen" 
									editurl="SectionDetails.html"
									height="200" 
									id="SectionDetailsPending" 
									loadonce="true" 
									hasViewDet="false"	
									hasEdit="true"
									viewAjaxRequest="true"	
									hasDelete="false"							
									caption="section.header"/>
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
					<apptags:jQgrid url="SectionDetails.html?SEARCH_RESULT_APP" mtype="post"
									colHeader="section.ModuleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : true, sorttype:'number',search : true,align : 'center',width :'100'},
												{name : 'subLinkNameEn', index : 'subLinkNameEn',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameRg', index : 'subLinkNameRg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionDetailsApp" 
									editurl="SectionDetails.html"
									height="200" 
									id="SectionDetailsAuthenticated" 
									loadonce="true" 
									hasViewDet="false"	
									hasEdit="true"
									viewAjaxRequest="true"	
									hasDelete="false"							
									caption="section.header"/>
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
					<apptags:jQgrid url="SectionDetails.html?SEARCH_RESULT_REJ" mtype="post"
									colHeader="section.ModuleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg" 
									isChildGrid="false" 
									colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameEn', index : 'subLinkNameEn',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameRg', index : 'subLinkNameRg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]" 
									gridid="gridSectionDetailsRej" 
									editurl="SectionDetails.html"
									height="200" 
									id="SectionDetailsRejected" 
									loadonce="true" 
									hasViewDet="false"	
									hasEdit="true"
									viewAjaxRequest="true"	
									hasDelete="false"							
									caption="section.header"/>
					</div>
					
					</div>
					
					</c:if>
				</div>
				
		</div>
		
		</div>
	    </div>
	    </div>