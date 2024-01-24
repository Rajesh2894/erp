<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/file-upload.js"></script>
<script>
setTimeout(function(){
	$('.tab-content .active').not(':first').removeClass('active'); 
},100) 
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
	     <div class="widget-header"><h2><spring:message code="dmayorGrid.Title"/></h2></div>
	     <apptags:helpDoc url="AdminDeputyMayorList.html"></apptags:helpDoc>
	     <c:if test="${command.makkerchekkerflag ne 'C'}">
			<div class="widget-content padding ">
			    <div class="text-center padding-bottom-10">
				 <a href="javascript:void(0);" class="btn btn-success" onclick="openForm('AdminDeputyMayorForm.html')"><i class="fa fa-plus-circle"></i><spring:message
								code="menu.trans.add" text="Add" /> </a>
					
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
							<apptags:jQgrid id="AdminDeputyMayorPending"
						url="AdminDeputyMayorList.html?DeputyMayorList_PEN" mtype="post"
						gridid="gridAdminDeputyMayorFormPen"  editurl="AdminDeputyMayorForm.html"
						colHeader="dmayorGrid.NameEn,dmayorGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="dmayorGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="AdminDeputyMayorAuthenticated"
						url="AdminDeputyMayorList.html?DeputyMayorList_APP" mtype="post"
						gridid="gridAdminDeputyMayorFormApp"  editurl="AdminDeputyMayorForm.html"
						colHeader="dmayorGrid.NameEn,dmayorGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="dmayorGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="AdminDeputyMayorRejected"
						url="AdminDeputyMayorList.html?DeputyMayorList_REJ" mtype="post"
						gridid="gridAdminDeputyMayorFormRej"  editurl="AdminDeputyMayorForm.html"
						colHeader="dmayorGrid.NameEn,dmayorGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="dmayorGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					</div>
					
			</div>
			</c:if>
			<c:if test="${command.makkerchekkerflag eq 'C'}">
			<div class="widget-content padding ">
			    <!-- <div class="text-center padding-bottom-10">
				 <a href="javascript:void(0);" class="btn btn-success" onclick="openForm('AdminDeputyMayorForm.html')"><i class="fa fa-plus-circle"></i> Add</a>
				 	
				</div> -->
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
							<apptags:jQgrid id="AdminDeputyMayorPending"
						url="AdminDeputyMayorList.html?DeputyMayorList_PEN" mtype="post"
						gridid="gridAdminDeputyMayorFormPen"  editurl="AdminDeputyMayorForm.html" deleteURL="AdminDeputyMayorForm.html"
						colHeader="dmayorGrid.NameEn,dmayorGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="dmayorGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="AdminDeputyMayorAuthenticated"
						url="AdminDeputyMayorList.html?DeputyMayorList_APP" mtype="post"
						gridid="gridAdminDeputyMayorFormApp"  editurl="AdminDeputyMayorForm.html" deleteURL="AdminDeputyMayorForm.html"
						colHeader="dmayorGrid.NameEn,dmayorGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="dmayorGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="AdminDeputyMayorRejected"
						url="AdminDeputyMayorList.html?DeputyMayorList_REJ" mtype="post"
						gridid="gridAdminDeputyMayorFormRej" editurl="AdminDeputyMayorForm.html" deleteURL="AdminDeputyMayorForm.html"
						colHeader="dmayorGrid.NameEn,dmayorGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="dmayorGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					</div>
			</div>
			</c:if>
		</div>
</div>		
