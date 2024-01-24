<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<apptags:breadcrumb></apptags:breadcrumb>
		
<h1></h1>

<div class="content" >
	<div class="widget">
	     <div class="widget-header">  <h2><strong><spring:message code="feedback.formTitle" /></strong></h2></div>
	     <apptags:helpDoc url="AdminFeedback.html"></apptags:helpDoc>
			<div class="widget-content padding ">
		 	<div class="text-center padding-bottom-10">
			<a href="CitizenHome.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="bckBtn" /></a>
			</div>
	<div class="grid-class" id="adminFeedbackGrid">
		<apptags:jQgrid url="AdminFeedback.html?Feedback_LIST" mtype="post"
			colHeader="feedback.name,feedback.MoblieExtension,feedback.Mobile,feedback.Email,feedback.comments,feedback.categeory,feedback.attPath" isChildGrid="false"
			colModel="[							
								{name:'fdUserName',index:'fdUserName',editable : false,sortable : false,search : true, align : 'center' },
								{name:'moblieExtension',index:'moblieExtension',editable : false,sortable : false,search : true, align : 'center'},
								{name:'mobileNo',index:'mobileNo',editable : false,sortable : false,search : true, align : 'center'},
								{name:'emailId',index:'emailId',editable : false,sortable : false,search : true, align : 'center'},
								{name:'feedBackDetails',index : 'feedBackDetails', editable : false,sortable : false,search : true, align :'left' },
								{name:'categoryTypeName',index : 'categoryTypeName', editable : false,sortable : false,search : true, align :'left' },
								{name:'attPath',index:'attPath',editable : false,sortable : false,search : true, align : 'center',formatter: editLink}
				  ]"
			gridid="gridAdminFeedbackForm" id="AdminFeedbackForm" height="200" caption="feedback.gridTitle" 
			hasActive="false" hasViewDet="true" viewAjaxRequest="true" hasDelete="true" loadonce="true"
			sortCol="rowId" showrow="true" />
			
	</div>

</div>
</div>
</div>