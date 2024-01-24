<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

    <apptags:breadcrumb></apptags:breadcrumb>
<div class="content" >
	<div class="widget">
	     <div class="widget-header">  <h2><strong><spring:message code="eip.admin.eipAnnounce.Landing" /></strong></h2></div>
	     <apptags:helpDoc url="EipAnnouncementLanding.html"></apptags:helpDoc>
			<c:if test="${command.makkerchekkerflag ne 'C'}">
				<div class="widget-content padding ">
				<div class="text-center padding-bottom-10">
				 <a onclick="openForm('EipAnnouncementLandingForm.html')"class="btn btn-success"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.announcement.Add"></spring:message></a>
				</div>
				
				
				<div class="table-responsive" id="eipAnnounceLandingGrid">
					<apptags:jQgrid id="eipAnnounceLanding"
						url="EipAnnouncementLanding.html?SEARCH_RESULTS" mtype="post"
						gridid="gridEipAnnouncementLandingForm"
						colHeader="admin.EIPAnnouncement.announceDescEng,admin.EIPAnnouncement.announceDescReg,admin.EIPAnnouncement.attach"
						colModel="[
								{name : 'announceDescEng',index : 'announceDescEng', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'announceDescReg',index : 'announceDescReg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachment',index : 'attachment', editable : false,sortable : false,search : true, align : 'center' }
				                ]"
						height="200" 
						caption="eip.admin.EipAnnouncementLanding" 
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
			
			</c:if>	
			
			<c:if test="${command.makkerchekkerflag eq 'C'}">	
			<div class="widget-content padding ">
				<div class="text-right padding-bottom-10">
				 <a onclick="openForm('EipAnnouncementLandingForm.html')"class="btn btn-success"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.announcement.Add"></spring:message></a>
				<h4 class="text-right">
				 <strong class="text-right">  click   view  button  and  select <i class="fa fa-check-square-o" aria-hidden="true"></i> checker flag  </strong>
			    </h4>
				
				</div>
				
				
				<div class="table-responsive" id="eipAnnounceLandingGrid">
					<apptags:jQgrid id="eipAnnounceLanding"
						url="EipAnnouncementLanding.html?SEARCH_RESULTS" mtype="post"
						gridid="gridEipAnnouncementLandingForm"
						colHeader="admin.EIPAnnouncement.announceDescEng,admin.EIPAnnouncement.announceDescReg,admin.EIPAnnouncement.attach"
						colModel="[
								{name : 'announceDescEng',index : 'announceDescEng', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'announceDescReg',index : 'announceDescReg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'attachment',index : 'attachment', editable : false,sortable : false,search : false, align : 'center' }
				                ]"
						height="200" 
						caption="eip.admin.EipAnnouncementLanding" 
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
			</c:if>
		</div>
</div>