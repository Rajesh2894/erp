<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>
setTimeout(function(){
	$('.tab-content .active').not(':first').removeClass('active'); 
},100) 
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content" >
	<div class="widget">
	     <div class="widget-header"><h2><strong><spring:message code="EIP.Announcement.Form" text="Recent Announcement" /></strong></h2></div>
			 <apptags:helpDoc url="EipAnnouncement.html"></apptags:helpDoc>
		<c:if test="${command.makkerchekkerflag  ne 'C'}">		
			<div class="widget-content padding">
				<div class="text-center padding-bottom-10">
					 <a onclick="openForm('EipAnnouncementForm.html')"class="btn btn-success"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.announcement.Add"></spring:message></a>
				</div>
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
					<apptags:jQgrid id="eipAnnouncePending"
						url="EipAnnouncement.html?SEARCH_RESULTS_PEN" mtype="post"
						gridid="gridEipAnnouncementFormPen" editurl="EipAnnouncementForm.html" deleteURL="EipAnnouncementForm.html"
						colHeader="admin.EIPAnnouncement.announceDescEng,admin.EIPAnnouncement.announceDescReg,admin.EIPAnnouncement.attach,admin.EIPAnnouncement.image"
						colModel="[
								{name : 'announceDescEng',index : 'announceDescEng', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'announceDescReg',index : 'announceDescReg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachName',index : 'attachName', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachImageName',index : 'attachImageName', editable : false,sortable : false,search : true, align : 'center' }
				                ]"
						height="200" 
						caption="admin.EipAnnouncement" 
						isChildGrid="false"
						hasActive="false" 
						hasViewDet="false" 
						hasEdit="true" 
						hasDelete="false"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="eipAnnounceAuthenticated"
						url="EipAnnouncement.html?SEARCH_RESULTS_APP" mtype="post"
						gridid="gridEipAnnouncementFormApp" editurl="EipAnnouncementForm.html" deleteURL="EipAnnouncementForm.html"
						colHeader="admin.EIPAnnouncement.announceDescEng,admin.EIPAnnouncement.announceDescReg,admin.EIPAnnouncement.attach,admin.EIPAnnouncement.image"
						colModel="[
								{name : 'announceDescEng',index : 'announceDescEng', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'announceDescReg',index : 'announceDescReg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachName',index : 'attachName', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachImageName',index : 'attachImageName', editable : false,sortable : false,search : true, align : 'center' }
				                ]"
						height="200" 
						caption="admin.EipAnnouncement" 
						isChildGrid="false"
						hasActive="false" 
						hasViewDet="false" 
						hasEdit="true" 
						hasDelete="false"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="eipAnnounceRejected"
						url="EipAnnouncement.html?SEARCH_RESULTS_REJ" mtype="post"
						gridid="gridEipAnnouncementFormRej" editurl="EipAnnouncementForm.html" deleteURL="EipAnnouncementForm.html"
						colHeader="admin.EIPAnnouncement.announceDescEng,admin.EIPAnnouncement.announceDescReg,admin.EIPAnnouncement.attach,admin.EIPAnnouncement.image"
						colModel="[
								{name : 'announceDescEng',index : 'announceDescEng', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'announceDescReg',index : 'announceDescReg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachName',index : 'attachName', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachImageName',index : 'attachImageName', editable : false,sortable : false,search : true, align : 'center' }
				                ]"
						height="200" 
						caption="admin.EipAnnouncement" 
						isChildGrid="false"
						hasActive="false" 
						hasViewDet="false" 
						hasEdit="true" 
						hasDelete="false"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
				
			</div>
			</div>
		</c:if>	
			<c:if test="${command.makkerchekkerflag  eq 'C'}">	
			<div class="widget-content padding">
				<%-- <div class="text-center padding-bottom-10">
					 <a onclick="openForm('EipAnnouncementForm.html')"class="btn btn-success"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.announcement.Add"></spring:message></a>
				</div> --%>
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
							<apptags:jQgrid id="eipAnnouncePending"
						url="EipAnnouncement.html?SEARCH_RESULTS_PEN" mtype="post"
						gridid="gridEipAnnouncementFormPen" editurl="EipAnnouncementForm.html" deleteURL="EipAnnouncementForm.html"
						colHeader="admin.EIPAnnouncement.announceDescEng,admin.EIPAnnouncement.announceDescReg,admin.EIPAnnouncement.attach,admin.EIPAnnouncement.image"
						colModel="[
								{name : 'announceDescEng',index : 'announceDescEng', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'announceDescReg',index : 'announceDescReg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachName',index : 'attachName', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachImageName',index : 'attachImageName', editable : false,sortable : false,search : true, align : 'center' }
				                ]"
						height="200" 
						caption="admin.EipAnnouncement" 
						isChildGrid="false"
						hasActive="true" 
						hasViewDet="false"
						hasEdit="true" 
						hasDelete="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="eipAnnounceAuthenticated"
						url="EipAnnouncement.html?SEARCH_RESULTS_APP" mtype="post"
						gridid="gridEipAnnouncementFormApp" editurl="EipAnnouncementForm.html" deleteURL="EipAnnouncementForm.html"
						colHeader="admin.EIPAnnouncement.announceDescEng,admin.EIPAnnouncement.announceDescReg,admin.EIPAnnouncement.attach,admin.EIPAnnouncement.image"
						colModel="[
								{name : 'announceDescEng',index : 'announceDescEng', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'announceDescReg',index : 'announceDescReg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachName',index : 'attachName', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachImageName',index : 'attachImageName', editable : false,sortable : false,search : true, align : 'center' }
				                ]"
						height="200" 
						caption="admin.EipAnnouncement" 
						isChildGrid="false"
						hasActive="true" 
						hasViewDet="false"
						hasEdit="true" 
						hasDelete="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="eipAnnounceRejected"
						url="EipAnnouncement.html?SEARCH_RESULTS_REJ" mtype="post"
						gridid="gridEipAnnouncementFormRej" editurl="EipAnnouncementForm.html" deleteURL="EipAnnouncementForm.html"
						colHeader="admin.EIPAnnouncement.announceDescEng,admin.EIPAnnouncement.announceDescReg,admin.EIPAnnouncement.attach,admin.EIPAnnouncement.image"
						colModel="[
								{name : 'announceDescEng',index : 'announceDescEng', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'announceDescReg',index : 'announceDescReg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachName',index : 'attachName', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachImageName',index : 'attachImageName', editable : false,sortable : false,search : true, align : 'center' }
				                ]"
						height="200" 
						caption="admin.EipAnnouncement" 
						isChildGrid="false"
						hasActive="true" 
						hasViewDet="false"
						hasEdit="true" 
						hasDelete="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
					
					</div>
				
			</div>
			</c:if>
		
	</div>
	</div>


