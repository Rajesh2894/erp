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
	     <div class="widget-header"><h2><strong><spring:message code="dComGrid.Title"/></strong></h2></div>
	     <apptags:helpDoc url="AdminDeputyCommissioner.html"></apptags:helpDoc>
			 <c:if test="${command.makkerchekkerflag ne 'C'}">
			<div class="widget-content padding">
				<div class="text-center padding-bottom-10">
						<a href="javascript:void(0);"class="btn btn-blue-2" onclick="openForm('AdminDeputyCommissionerForm.html')"> <i class="fa fa-plus-circle"></i> <spring:message code="eip.add" /></a>
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
						<apptags:jQgrid id="AdminDeputyCommissionerForm" url="AdminDeputyCommissioner.html?DeputyCommissioner_PEN"
						mtype="post" gridid="gridAdminDeputyCommissionerFormPen" editurl="AdminDeputyCommissionerForm.html"
						colHeader="Profiles on Dy. Mayor(English),Profiles on Dy. Mayor(Regional)"
						colModel="[
								{name : 'pNameEn',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'pNameReg',index : 'linkTitleReg', editable : false,sortable : false,search : false, align : 'center' }
								
				  ]"
						height="200" caption="dComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="AdminDeputyCommissionerForm" url="AdminDeputyCommissioner.html?DeputyCommissioner_APP"
						mtype="post" gridid="gridAdminDeputyCommissionerFormApp" editurl="AdminDeputyCommissionerForm.html"
						colHeader="Profiles on Dy. Mayor(English),Profiles on Dy. Mayor(Regional)"
						colModel="[
								{name : 'pNameEn',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'pNameReg',index : 'linkTitleReg', editable : false,sortable : false,search : false, align : 'center' }
								
				  ]"
						height="200" caption="dComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="AdminDeputyCommissionerForm" url="AdminDeputyCommissioner.html?DeputyCommissioner_REJ"
						mtype="post" gridid="gridAdminDeputyCommissionerFormRej" editurl="AdminDeputyCommissionerForm.html"
						colHeader="Profiles on Dy. Mayor(English),Profiles on Dy. Mayor(Regional)"
						colModel="[
								{name : 'pNameEn',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'pNameReg',index : 'linkTitleReg', editable : false,sortable : false,search : false, align : 'center' }
								
				  ]"
						height="200" caption="dComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					</div>
					
			</div>
			</c:if>
			  <c:if test="${command.makkerchekkerflag eq 'C'}">
			  <div class="widget-content padding ">
			   <%--  <div class="text-center padding-bottom-10">
						<a href="javascript:void(0);"class="btn btn-blue-2" onclick="openForm('AdminMayorForm.html')"> <i class="fa fa-plus-circle"></i> <spring:message code="eip.add" /></a>
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
						<apptags:jQgrid id="AdminDeputyCommissionerForm" url="AdminDeputyCommissioner.html?DeputyCommissioner_PEN"
						mtype="post" gridid="gridAdminDeputyCommissionerFormPen" editurl="AdminDeputyCommissionerForm.html" deleteURL="AdminDeputyCommissionerForm.html"
						colHeader="Profiles on Dy. Mayor(English),Profiles on Dy. Mayor(Regional)"
						colModel="[
								{name : 'pNameEn',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'pNameReg',index : 'linkTitleReg', editable : false,sortable : false,search : false, align : 'center' }
								
				  ]"
						height="200" caption="dComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="AdminDeputyCommissionerForm" url="AdminDeputyCommissioner.html?DeputyCommissioner_APP"
						mtype="post" gridid="gridAdminDeputyCommissionerFormApp" editurl="AdminDeputyCommissionerForm.html" deleteURL="AdminDeputyCommissionerForm.html"
						colHeader="Profiles on Dy. Mayor(English),Profiles on Dy. Mayor(Regional)"
						colModel="[
								{name : 'pNameEn',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'pNameReg',index : 'linkTitleReg', editable : false,sortable : false,search : false, align : 'center' }
								
				  ]"
						height="200" caption="dComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="AdminDeputyCommissionerForm" url="AdminDeputyCommissioner.html?DeputyCommissioner_REJ"
						mtype="post" gridid="gridAdminDeputyCommissionerFormRej" editurl="AdminDeputyCommissionerForm.html" deleteURL="AdminDeputyCommissionerForm.html"
						colHeader="Profiles on Dy. Mayor(English),Profiles on Dy. Mayor(Regional)"
						colModel="[
								{name : 'pNameEn',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'pNameReg',index : 'linkTitleReg', editable : false,sortable : false,search : false, align : 'center' }
								
				  ]"
						height="200" caption="dComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					</div>
			</div>
			  </c:if>

		</div>
</div>