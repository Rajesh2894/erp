<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<apptags:breadcrumb></apptags:breadcrumb>

<a target="_blank" id="help" href="#" class="tooltips" onClick="helpDocsMsg();"><i class="fa fa-question-circle"><span>Help ?</span> </i></a>
<div class="content" >
	<div class="widget">
	     <div class="widget-header">  <h2><strong><spring:message code="admin.eventGrid.Title"/></strong></h2></div>
	     <apptags:helpDoc url="AdminEvents.html"/>
			<div class="widget-content padding ">
			<div class="form-div">
				<div class="text-right padding-bottom-10">
				<span class="otherlink"> <a href="javascript:void(0);"
						class="btn btn-success" onclick="openForm('AdminEventsForm.html')"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.add"></spring:message></a>
					</span>
				</div>
                <br/>
				<div class="table-responsive" id="AdminEventsGrid">
					<apptags:jQgrid id="AdminEvents" url="AdminEvents.html?Event_LIST"
						mtype="post" gridid="gridAdminEventsForm"
						colHeader="admin.eventGrid.Date,admin.eventGrid.HeadlineEn,admin.eventGrid.HeadlineReg"
						colModel="[
								{name : 'newsDate',index : 'newsDate', editable : false,sortable : false,search : false, align : 'center',formatter : dateTemplate},
								{name : 'shortDescEn',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'shortDescReg',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' }
								
				  ]"
						height="200" caption="admin.eventGrid.Title" isChildGrid="false"
						hasActive="true" hasViewDet="true" hasDelete="true"
						loadonce="false" sortCol="rowId" showrow="true" />
				</div>


			</div>
		</div>
	</div>
</div>