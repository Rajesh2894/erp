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
			<h2><spring:message code="admin.qlGridCaption" /></h2>
			<apptags:helpDoc url="AdminQuickLink.html"></apptags:helpDoc>
		</div>
		<c:if test="${command.makkerchekkerflag  ne 'C'}">
			<div class="widget-content padding ">
				<div class="text-center padding-bottom-10">
					 <a href="javascript:void(0);"
						class="btn btn-success"
						onclick="openForm('AdminQuickLinkForm.html')"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.add"></spring:message></a>
						<a href="CitizenHome.html" class="btn btn-danger" id="AdminFaqback"><spring:message
								code="bckBtn" text="Back" /></a>
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
							<apptags:jQgrid id="quickLinkPending"
						url="AdminQuickLink.html?QuickLink_LIST_PEN" mtype="post"
						gridid="gridAdminQuickLinkFormPen" editurl="AdminQuickLinkForm.html"
						colHeader="admin.qlFormLinkOrder,admin.qlTitleEn,admin.qlFormTitleTextReg"
						colModel="[
								{name : 'linkOrder',index : 'linkOrder', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleEg',index : 'linkTitleEg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleReg',index : 'linkTitleReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.qlGridCaption" isChildGrid="false"
						 hasViewDet="false" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" hasEdit="true"/>
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="quickLinkAuthenticated"
						url="AdminQuickLink.html?QuickLink_LIST_APP" mtype="post"
						gridid="gridAdminQuickLinkFormApp" editurl="AdminQuickLinkForm.html"
						colHeader="admin.qlFormLinkOrder,admin.qlTitleEn,admin.qlFormTitleTextReg"
						colModel="[
								{name : 'linkOrder',index : 'linkOrder', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleEg',index : 'linkTitleEg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleReg',index : 'linkTitleReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.qlGridCaption" isChildGrid="false"
						 hasViewDet="false" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" hasEdit="true"/>
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="quickLinkRejected"
						url="AdminQuickLink.html?QuickLink_LIST_REJ" mtype="post"
						gridid="gridAdminQuickLinkFormRej" editurl="AdminQuickLinkForm.html"
						colHeader="admin.qlFormLinkOrder,admin.qlTitleEn,admin.qlFormTitleTextReg"
						colModel="[
								{name : 'linkOrder',index : 'linkOrder', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleEg',index : 'linkTitleEg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleReg',index : 'linkTitleReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.qlGridCaption" isChildGrid="false"
						 hasViewDet="false" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" hasEdit="true"/>
					</div>
					
					</div>
			</div>
		</c:if>	
		  <c:if test="${command.makkerchekkerflag eq 'C'}">
			<div class="widget-content padding ">
				
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
							<apptags:jQgrid id="quickLinkPending"
						url="AdminQuickLink.html?QuickLink_LIST_PEN" mtype="post"
						gridid="gridAdminQuickLinkFormPen" editurl="AdminQuickLinkForm.html" deleteURL="AdminQuickLinkForm.html"
						colHeader="admin.qlFormLinkOrder,admin.qlTitleEn,admin.qlFormTitleTextReg"
						colModel="[
								{name : 'linkOrder',index : 'linkOrder', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleEg',index : 'linkTitleEg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleReg',index : 'linkTitleReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.qlGridCaption" isChildGrid="false"
						 hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="quickLinkAuthenticated"
						url="AdminQuickLink.html?QuickLink_LIST_APP" mtype="post"
						gridid="gridAdminQuickLinkFormApp" editurl="AdminQuickLinkForm.html" deleteURL="AdminQuickLinkForm.html"
						colHeader="admin.qlFormLinkOrder,admin.qlTitleEn,admin.qlFormTitleTextReg"
						colModel="[
								{name : 'linkOrder',index : 'linkOrder', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleEg',index : 'linkTitleEg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleReg',index : 'linkTitleReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.qlGridCaption" isChildGrid="false"
						 hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="quickLinkRejected"
						url="AdminQuickLink.html?QuickLink_LIST_REJ" mtype="post"
						gridid="gridAdminQuickLinkFormRej" editurl="AdminQuickLinkForm.html" deleteURL="AdminQuickLinkForm.html"
						colHeader="admin.qlFormLinkOrder,admin.qlTitleEn,admin.qlFormTitleTextReg"
						colModel="[
								{name : 'linkOrder',index : 'linkOrder', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleEg',index : 'linkTitleEg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'linkTitleReg',index : 'linkTitleReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="admin.qlGridCaption" isChildGrid="false"
						 hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					</div>
			</div>
		</c:if>	
</div>
</div>
