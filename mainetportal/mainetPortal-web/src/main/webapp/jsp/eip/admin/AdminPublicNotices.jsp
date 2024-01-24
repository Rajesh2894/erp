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
<div class="content">
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="admin.publicNotice" /></h2>
        </div>
        <apptags:helpDoc url="PublicNotices.html"></apptags:helpDoc>
    <c:if test="${command.makkerchekkerflag ne 'C'}">	          
		<div class="widget-content padding">
			<div class="text-center padding-bottom-10">
					<a href="javascript:void(0);"  onclick="openForm('AdminPublicNoticesForm.html')"class="btn btn-success"> <i class="fa fa-plus-circle"></i> <spring:message code="admin.addBm"/></a>
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
						<apptags:jQgrid id="publicNoticePending"
						url="PublicNotices.html?SEARCH_RESULTS_PEN" mtype="post"
						gridid="gridAdminPublicNoticesFormPen" editurl="AdminPublicNoticesForm.html" deleteURL="AdminPublicNoticesForm.html"
						colHeader="admin.publicNotice.IssueDate,admin.publicNotice.NoticeSubjectEn,admin.publicNotice.NoticeSubjectReg"
						colModel="[
						{name : 'issueDateNew',index : 'issueDateNew', editable : false,sortable : false,search : true, align : 'center',formatter:'date',sorttype: 'date', formatoptions:{srcformat: 'd/m/Y',newformat: 'd/m/Y'}},
								{name : 'noticeSubEn',index : 'noticeSubEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'noticeSubReg',index : 'noticeSubReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.publicNoticeList" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" hasEdit="true"/>
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="publicNoticeAuthenticated"
						url="PublicNotices.html?SEARCH_RESULTS_APP" mtype="post"
						gridid="gridAdminPublicNoticesFormApp" editurl="AdminPublicNoticesForm.html" deleteURL="AdminPublicNoticesForm.html"
						colHeader="admin.publicNotice.IssueDate,admin.publicNotice.NoticeSubjectEn,admin.publicNotice.NoticeSubjectReg"
						colModel="[
						{name : 'issueDateNew',index : 'issueDateNew', editable : false,sortable : false,search : true, align : 'center',formatter:'date',sorttype: 'date', formatoptions:{srcformat: 'd/m/Y',newformat: 'd/m/Y'}},
								{name : 'noticeSubEn',index : 'noticeSubEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'noticeSubReg',index : 'noticeSubReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.publicNoticeList" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" hasEdit="true"/>
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="publicNoticeRejected"
						url="PublicNotices.html?SEARCH_RESULTS_REJ" mtype="post"
						gridid="gridAdminPublicNoticesFormRej" editurl="AdminPublicNoticesForm.html" deleteURL="AdminPublicNoticesForm.html"
						colHeader="admin.publicNotice.IssueDate,admin.publicNotice.NoticeSubjectEn,admin.publicNotice.NoticeSubjectReg"
						colModel="[
						{name : 'issueDateNew',index : 'issueDateNew', editable : false,sortable : false,search : true, align : 'center',formatter:'date',sorttype: 'date', formatoptions:{srcformat: 'd/m/Y',newformat: 'd/m/Y'}},
								{name : 'noticeSubEn',index : 'noticeSubEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'noticeSubReg',index : 'noticeSubReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.publicNoticeList" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" hasEdit="true"/>
					</div>
					
					</div>
					
			</div>
	</c:if>
	
	 <c:if test="${command.makkerchekkerflag eq 'C'}">	   
	<div class="widget-content padding">
			<%-- <div class="text-center padding-bottom-10">
				<a href="javascript:void(0);"  onclick="openForm('AdminPublicNoticesForm.html')"class="btn btn-success"> <i class="fa fa-plus-circle"></i> <spring:message code="admin.addBm"/></a>
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
							<apptags:jQgrid id="publicNoticePending"
						url="PublicNotices.html?SEARCH_RESULTS_PEN" mtype="post"
						gridid="gridAdminPublicNoticesFormPen" editurl="AdminPublicNoticesForm.html" deleteURL="AdminPublicNoticesForm.html"
						colHeader="admin.publicNotice.IssueDate,admin.publicNotice.NoticeSubjectEn,admin.publicNotice.NoticeSubjectReg"
						colModel="[
						{name : 'issueDateNew',index : 'issueDateNew', editable : false,sortable : false,search : true, align : 'center',formatter:'date',sorttype: 'date', formatoptions:{srcformat: 'd/m/Y',newformat: 'd/m/Y'}},
								{name : 'noticeSubEn',index : 'noticeSubEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'noticeSubReg',index : 'noticeSubReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.publicNoticeList" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="publicNoticeAuthenticated"
						url="PublicNotices.html?SEARCH_RESULTS_APP" mtype="post"
						gridid="gridAdminPublicNoticesFormApp" editurl="AdminPublicNoticesForm.html" deleteURL="AdminPublicNoticesForm.html"
						colHeader="admin.publicNotice.IssueDate,admin.publicNotice.NoticeSubjectEn,admin.publicNotice.NoticeSubjectReg"
						colModel="[
						{name : 'issueDateNew',index : 'issueDateNew', editable : false,sortable : false,search : true, align : 'center',formatter:'date',sorttype: 'date', formatoptions:{srcformat: 'd/m/Y',newformat: 'd/m/Y'}},
								{name : 'noticeSubEn',index : 'noticeSubEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'noticeSubReg',index : 'noticeSubReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.publicNoticeList" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="publicNoticeRejected"
						url="PublicNotices.html?SEARCH_RESULTS_REJ" mtype="post" 
						gridid="gridAdminPublicNoticesFormRej" editurl="AdminPublicNoticesForm.html" deleteURL="AdminPublicNoticesForm.html"
						colHeader="admin.publicNotice.IssueDate,admin.publicNotice.NoticeSubjectEn,admin.publicNotice.NoticeSubjectReg"
						colModel="[
						{name : 'issueDateNew',index : 'issueDateNew', editable : false,sortable : false,search : true, align : 'center',formatter:'date',sorttype: 'date', formatoptions:{srcformat: 'd/m/Y',newformat: 'd/m/Y'}},
								{name : 'noticeSubEn',index : 'noticeSubEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'noticeSubReg',index : 'noticeSubReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.publicNoticeList" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					</div>
					
			</div>
	</c:if>		
			
		</div>
</div>	