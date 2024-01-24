<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>
setTimeout(function(){
	$('.tab-content .active').not(':first').removeClass('active'); 
},100) 
</script>

 <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}"> 
     <spring:message code="ComGrid.Title" var="ministerTitle"/>
     </c:if>
     <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}"> 
     <spring:message code="ulbComGrid.Title" var="ministerTitle"/>
     </c:if>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
	     <div class="widget-header"><h2><strong>${ministerTitle}</strong></h2></div>
	     <apptags:helpDoc url="AdminCommissioner.html"></apptags:helpDoc>
	      <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}"> 
     <spring:message code="ComGrid.NameEn" var="gridTitleEn"/>
     <spring:message code="ComGrid.NameReg" var="gridTitleReg"/>
     </c:if>
     <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}"> 
    <spring:message code="ulbComGrid.NameEn" var="gridTitleEn"/>
     <spring:message code="ulbComGrid.NameReg" var="gridTitleReg"/>,
     </c:if>
	     <c:if test="${command.makkerchekkerflag ne 'C'}">
			<div class="widget-content padding">
				<div class="text-center padding-bottom-10">
					 <a href="javascript:void(0);" class="btn btn-blue-2" onclick="openForm('AdminCommissionerForm.html')"> <i class="fa fa-plus-circle"></i> <spring:message code="eip.add" /></a>
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
							<apptags:jQgrid id="AdminCommissionerPending"
						url="AdminCommissioner.html?Commissioner_PEN" mtype="post"
						gridid="gridAdminCommissionerFormPen" editurl="AdminCommissionerForm.html"
						colHeader="${gridTitleEn},${gridTitleReg}"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="ComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasDelete="false" hasEdit="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="AdminCommissionerPending"
						url="AdminCommissioner.html?Commissioner_APP" mtype="post"
						gridid="gridAdminCommissionerFormApp" editurl="AdminCommissionerForm.html"
						colHeader="${gridTitleEn},${gridTitleReg}"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="ComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasDelete="false" hasEdit="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="AdminCommissionerPending"
						url="AdminCommissioner.html?Commissioner_REJ" mtype="post"
						gridid="gridAdminCommissionerFormRej" editurl="AdminCommissionerForm.html"
						colHeader="${gridTitleEn},${gridTitleReg}"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="ComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasDelete="false" hasEdit="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					</div>
			</div>
		</c:if>
		<c:if test="${command.makkerchekkerflag eq 'C'}">	
		
		<div class="widget-content padding">
				<%-- <div class="text-center padding-bottom-10">
					 <a href="javascript:void(0);" class="btn btn-success" onclick="openForm('AdminCommissionerForm.html')"> <i class="fa fa-plus-circle"></i> <spring:message code="eip.add" /></a>
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
							<apptags:jQgrid id="AdminCommissionerPending"
						url="AdminCommissioner.html?Commissioner_PEN" mtype="post"
						gridid="gridAdminCommissionerFormPen" editurl="AdminCommissionerForm.html" deleteURL="AdminCommissionerForm.html"
						colHeader="${gridTitleEn},${gridTitleReg}"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="ComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasDelete="true" hasEdit="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="AdminCommissionerPending"
						url="AdminCommissioner.html?Commissioner_APP" mtype="post"
						gridid="gridAdminCommissionerFormApp" editurl="AdminCommissionerForm.html" deleteURL="AdminCommissionerForm.html"
						colHeader="${gridTitleEn},${gridTitleReg}"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="ComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasDelete="true" hasEdit="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="AdminCommissionerPending"
						url="AdminCommissioner.html?Commissioner_REJ" mtype="post"
						gridid="gridAdminCommissionerFormRej" editurl="AdminCommissionerForm.html" deleteURL="AdminCommissionerForm.html"
						colHeader="${gridTitleEn},${gridTitleReg}"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="ComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasDelete="true" hasEdit="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					</div>
					
			</div>
		</c:if>	
		</div>
	</div>
