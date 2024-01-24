<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<script>

function saveForm(element)
{
	return saveOrUpdateForm(element,msg, 'ProjectDetail.html', 'saveform');
}


function openForm2(formUrl,actionParam)
{
	if (!actionParam) {
		
		actionParam = "add";
	}
	
	var divName	=	'.content-page';
	var ajaxResponse	=	doAjaxLoading(formUrl+'?'+actionParam,{},'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	
	
	
}
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="rti.add.aboutproject" /></strong>
			</h2>
			 <apptags:helpDoc url="AboutProject.html"></apptags:helpDoc>
		</div>
		 <c:if test="${command.makkerchekkerflag ne 'C'}">	
		<div class="widget-content padding ">

			<form:form action="AboutProject.html" method="post" class="form"
				name="frmAboutProject" id="frmAboutProject">
			
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div class="text-center padding-bottom-10">
					<a href="javascript:void(0);"
						onclick="openForm2('ProjectDetail.html')" class="btn btn-success"><i
						class="fa fa-plus-circle"></i>
					<spring:message code="eip.add"></spring:message></a>
				</div>
			</form:form>
			<ul id="demo2" class="nav nav-tabs">
					<li class="active"><a href="#Pending" data-toggle="tab"><spring:message
								code="" text="Pending" /></a></li>
					<li><a href="#Authenticated" data-toggle="tab"><spring:message
								code="" text="Authenticated" /></a></li>
					<li><a href="#Rejected" data-toggle="tab"><spring:message
								code="" text="Rejected" /></a></li>
				</ul>
				
			<div class="tab-content tab-boxed">
					<div class="tab-pane fade active in" id="Pending">
							<apptags:jQgrid id="AbtProjPending"
					url="AboutProject.html?ABOUT_PROJECT_LIST_PEN" mtype="post"
					gridid="gridProjectDetail1"
					colHeader="rti.projectTitleEng,rti.projectTitleReg,rti.projectInfoEng,projectInfoReg"
					colModel="[
						    {name : 'descTitleEng',index : 'descTitleEng',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descTitleReg',index : 'descTitleReg',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descInfoEng',index : 'descInfoEng',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descInfoReg',index : 'descInfoReg',editable : false,sortable : false,search : false,  align : 'center'}
							
					  		]"
					height="200" caption="rti.rmGridCaption" isChildGrid="false"
					hasActive="true" hasEdit="true" showInDialog="false"
					hasViewDet="false" hasDelete="false" loadonce="false"
					sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade" id="Authenticated">
						<apptags:jQgrid id="AbtProjAuthenticated"
					url="AboutProject.html?ABOUT_PROJECT_LIST_APP" mtype="post"
					gridid="gridProjectDetail2"
					colHeader="rti.projectTitleEng,rti.projectTitleReg,rti.projectInfoEng,projectInfoReg"
					colModel="[
						    {name : 'descTitleEng',index : 'descTitleEng',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descTitleReg',index : 'descTitleReg',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descInfoEng',index : 'descInfoEng',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descInfoReg',index : 'descInfoReg',editable : false,sortable : false,search : false,  align : 'center'}
							
					  		]"
					height="200" caption="rti.rmGridCaption" isChildGrid="false"
					hasActive="true" hasEdit="true" showInDialog="false"
					hasViewDet="false" hasDelete="false" loadonce="false"
					sortCol="rowId" showrow="true" />
					</div>
				
					<div class="tab-pane fade" id="Rejected">
						<apptags:jQgrid id="AbtProjRejected"
					url="AboutProject.html?ABOUT_PROJECT_LIST_REJ" mtype="post"
					gridid="gridProjectDetail3"
					colHeader="rti.projectTitleEng,rti.projectTitleReg,rti.projectInfoEng,projectInfoReg"
					colModel="[
						    {name : 'descTitleEng',index : 'descTitleEng',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descTitleReg',index : 'descTitleReg',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descInfoEng',index : 'descInfoEng',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descInfoReg',index : 'descInfoReg',editable : false,sortable : false,search : false,  align : 'center'}
							
					  		]"
					height="200" caption="rti.rmGridCaption" isChildGrid="false"
					hasActive="true" hasEdit="true" showInDialog="false"
					hasViewDet="false" hasDelete="false" loadonce="false"
					sortCol="rowId" showrow="true" />
					</div>
					
					</div>
				
			</div>
		</c:if>
	
	 <c:if test="${command.makkerchekkerflag eq 'C'}">	
		<div class="widget-content padding ">

			<form:form action="AboutProject.html" method="post" class="form"
				name="frmAboutProject" id="frmAboutProject">
			
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<%-- <div class="text-center padding-bottom-10">
					<a href="javascript:void(0);"
						onclick="openForm2('ProjectDetail.html')" class="btn btn-success"><i
						class="fa fa-plus-circle"></i>
					<spring:message code="eip.add"></spring:message></a>
				</div> --%>
				
				
			</form:form>
			<div class="table-responsive">

				<apptags:jQgrid id="projectDetail"
					url="AboutProject.html?ABOUT_PROJECT_LIST" mtype="post"
					gridid="gridProjectDetail"
					colHeader="rti.projectTitleEng,rti.projectTitleReg,rti.projectInfoEng,projectInfoReg"
					colModel="[
						    {name : 'descTitleEng',index : 'descTitleEng',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descTitleReg',index : 'descTitleReg',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descInfoEng',index : 'descInfoEng',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'descInfoReg',index : 'descInfoReg',editable : false,sortable : false,search : false,  align : 'center'}
							
					  		]"
					height="200" caption="rti.rmGridCaption" isChildGrid="false"
					hasActive="true" hasEdit="true" showInDialog="false"
					hasViewDet="false" hasDelete="true" loadonce="false"
					sortCol="rowId" showrow="true" />
			</div>
		</div>
		</c:if>
	</div>

</div>

