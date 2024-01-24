<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>
setTimeout(function(){
	$('.tab-content .active').not(':first').removeClass('active'); 
},100) 
	function getStatus2(event, obj) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode == '13') {

			findAll(obj);

		}
	}
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="admin.adminFAQHeader" /> 
			</h2>
		</div>
		<apptags:helpDoc url="AdminFAQSearch.html"></apptags:helpDoc> 
<c:if test="${command.makkerchekkerflag ne 'C'}">		
		<div class="widget-content padding ">

			<form:form action="AdminFAQSearch.html" method="post" class="form">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

<div class="text-center padding-bottom-10">
	<a href="javascript:void(0);" onclick="openForm('AdminFAQ.html')" class="btn btn-success">
		<i class="fa fa-plus-circle"> </i> <spring:message code="eip.add"></spring:message></a>		
		<a href="CitizenHome.html" class="btn btn-danger" id="AdminFaqback"><spring:message
								code="bckBtn" text="Back" /></a>			
</div>

				


			</form:form>
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
							<apptags:jQgrid id="adminFAQPending"
					url="AdminFAQSearch.html?FAQ_PEN" editurl="AdminFAQ.html" mtype="post"
					gridid="gridAdminFAQPen"
					colHeader="admin.questionEn,admin.ansEn,admin.questionReg,admin.ansReg"
					colModel="[	{name : 'questionEn',index : 'questionEn',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerEn',index : 'answerEn',editable : false,sortable : false,search : true,align : 'center',width :'150'},
										{name : 'questionReg',index : 'questionReg',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerReg',index : 'answerReg',editable : false,sortable : false,search : true,align : 'center',width :'150'}
										]"
					sortCol="rowId" isChildGrid="false" hasActive="false"
					hasViewDet="false" hasEdit="true" hasDelete="false" height="200" showrow="true"
					caption="admin.adminFAQHeader" loadonce="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="adminFAQAuthenticated"
					url="AdminFAQSearch.html?FAQ_APP" editurl="AdminFAQ.html" mtype="post"
					gridid="gridAdminFAQApp"
					colHeader="admin.questionEn,admin.ansEn,admin.questionReg,admin.ansReg"
					colModel="[	{name : 'questionEn',index : 'questionEn',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerEn',index : 'answerEn',editable : false,sortable : false,search : true,align : 'center',width :'150'},
										{name : 'questionReg',index : 'questionReg',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerReg',index : 'answerReg',editable : false,sortable : false,search : true,align : 'center',width :'150'}
										]"
					sortCol="rowId" isChildGrid="false" hasActive="false"
					hasViewDet="false" hasEdit="true" hasDelete="false" height="200" showrow="true"
					caption="admin.adminFAQHeader" loadonce="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="adminFAQRejected"
					url="AdminFAQSearch.html?FAQ_REJ" editurl="AdminFAQ.html" mtype="post"
					gridid="gridAdminFAQRej"
					colHeader="admin.questionEn,admin.ansEn,admin.questionReg,admin.ansReg"
					colModel="[	{name : 'questionEn',index : 'questionEn',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerEn',index : 'answerEn',editable : false,sortable : false,search : true,align : 'center',width :'150'},
										{name : 'questionReg',index : 'questionReg',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerReg',index : 'answerReg',editable : false,sortable : false,search : true,align : 'center',width :'150'}
										]"
					sortCol="rowId" isChildGrid="false" hasActive="false"
					hasViewDet="false" hasEdit="true" hasDelete="false" height="200" showrow="true"
					caption="admin.adminFAQHeader" loadonce="true" />
					</div>
					
					</div>
				
		</div>
</c:if>	
<c:if test="${command.makkerchekkerflag  eq 'C'}">	
    
		<div class="widget-content padding">
			
			
			<form:form action="AdminFAQSearch.html" method="post" class="form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				
				<%-- <div class="text-center padding-bottom-10">
					<a href="javascript:void(0);" onclick="openForm('AdminFAQ.html')" class="btn btn-success">
						<i class="fa fa-plus-circle"> </i> <spring:message code="eip.add"></spring:message></a>					
				</div> --%>
				
				
			</form:form>
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
						<apptags:jQgrid id="adminFAQPending"
					url="AdminFAQSearch.html?FAQ_PEN" mtype="post"
					gridid="gridAdminFAQPen" editurl="AdminFAQ.html" deleteURL="AdminFAQ.html"
					colHeader="admin.questionEn,admin.ansEn,admin.questionReg,admin.ansReg"
					colModel="[	{name : 'questionEn',index : 'questionEn',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerEn',index : 'answerEn',editable : false,sortable : false,search : true,align : 'center',width :'150'},
										{name : 'questionReg',index : 'questionReg',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerReg',index : 'answerReg',editable : false,sortable : false,search : true,align : 'center',width :'150'}
										]"
					sortCol="rowId" isChildGrid="false" hasActive="false"
					hasViewDet="false" hasEdit="true" hasDelete="true" height="200" showrow="true"
					caption="admin.adminFAQHeader" loadonce="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="adminFAQAuthenticated"
					url="AdminFAQSearch.html?FAQ_APP" editurl="AdminFAQ.html" deleteURL="AdminFAQ.html" mtype="post"
					gridid="gridAdminFAQApp"
					colHeader="admin.questionEn,admin.ansEn,admin.questionReg,admin.ansReg"
					colModel="[	{name : 'questionEn',index : 'questionEn',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerEn',index : 'answerEn',editable : false,sortable : false,search : true,align : 'center',width :'150'},
										{name : 'questionReg',index : 'questionReg',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerReg',index : 'answerReg',editable : false,sortable : false,search : true,align : 'center',width :'150'}
										]"
					sortCol="rowId" isChildGrid="false" hasActive="false"
					hasViewDet="false" hasEdit="true" hasDelete="true" height="200" showrow="true"
					caption="admin.adminFAQHeader" loadonce="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="adminFAQRejected"
					url="AdminFAQSearch.html?FAQ_REJ" editurl="AdminFAQ.html" deleteURL="AdminFAQ.html" mtype="post"
					gridid="gridAdminFAQRej" 
					colHeader="admin.questionEn,admin.ansEn,admin.questionReg,admin.ansReg"
					colModel="[	{name : 'questionEn',index : 'questionEn',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerEn',index : 'answerEn',editable : false,sortable : false,search : true,align : 'center',width :'150'},
										{name : 'questionReg',index : 'questionReg',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerReg',index : 'answerReg',editable : false,sortable : false,search : true,align : 'center',width :'150'}
										]"
					sortCol="rowId" isChildGrid="false" hasActive="false"
					hasViewDet="false" hasEdit="true" hasDelete="true" height="200" showrow="true"
					caption="admin.adminFAQHeader" loadonce="true" />
					</div>
					
					</div>
			</div>
		</div>
</c:if>		
	</div>
</div>
