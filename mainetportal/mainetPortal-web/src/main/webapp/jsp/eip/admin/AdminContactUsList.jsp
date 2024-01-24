<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/chargeMaster.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<script>
setTimeout(function(){
	$('.tab-content .active').not(':first').removeClass('active'); 
	},100) 
</script>
<div class="content">
	<div class="widget">
	     <div class="widget-header"><h2><spring:message code="eip.citizen.footer.contactUs"/></h2></div>
	     <apptags:helpDoc url="AdminContactUsList.html"/>
		<c:if test="${command.makkerchekkerflag ne 'C'}">
			<div class="widget-content padding ">
				<div class="text-center padding-bottom-10">
				 <a href="javascript:void(0);"
							class="btn btn-blue-2" onclick="openForm('AdminContactUs.html')"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.add" /></a>
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
							<apptags:jQgrid id="eipAdminContactUsPending"
						url="AdminContactUsList.html?SEARCH_RESULTS_PEN" mtype="post"
						gridid="gridAdminContactUsPen" editurl="AdminContactUs.html" deleteURL="AdminContactUs.html"
						colHeader="eip.admin.contactUs.contacttype,eip.admin.contactUs.seqno,cms.depName,eip.admin.contactUs.name,Employee.designation.dsgid,eip.admin.contact.contactno,eip.admin.contactUs.emailAddress"
						 colModel="[
						           {name : 'flag',index : 'flag', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'sequenceNo',index : 'sequenceNo', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'departmentEn',index : 'departmentEn', editable : false,sortable : false,search : true, align : 'center' },
						           {name : 'contactNameEn',index : 'contactNameEn', editable : false,sortable : false,search : true, align : 'center' },
								   {name : 'designationEn',index : 'designationEn', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'telephoneNo1En',index : 'telephoneNo1En', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'email1',index : 'email1', editable : false,sortable : false,search : true, align : 'center'}
								 ]"
						height="200" 
						caption="eip.admin.contact.list" 
						isChildGrid="false"
						hasActive="false" 
						hasViewDet="false" 
						hasDelete="false"
						hasEdit="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
						<apptags:jQgrid id="eipAdminContactUsAuthenticated"
						url="AdminContactUsList.html?SEARCH_RESULTS_APP" mtype="post"
						gridid="gridAdminContactUsApp" editurl="AdminContactUs.html" deleteURL="AdminContactUs.html"
						colHeader="eip.admin.contactUs.contacttype,eip.admin.contactUs.seqno,cms.depName,eip.admin.contactUs.name,Employee.designation.dsgid,eip.admin.contact.contactno,eip.admin.contactUs.emailAddress"
						 colModel="[
						           {name : 'flag',index : 'flag', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'sequenceNo',index : 'sequenceNo', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'departmentEn',index : 'departmentEn', editable : false,sortable : false,search : true, align : 'center' },
						           {name : 'contactNameEn',index : 'contactNameEn', editable : false,sortable : false,search : true, align : 'center' },
								   {name : 'designationEn',index : 'designationEn', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'telephoneNo1En',index : 'telephoneNo1En', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'email1',index : 'email1', editable : false,sortable : false,search : true, align : 'center'}
								 ]"
						height="200" 
						caption="eip.admin.contact.list" 
						isChildGrid="false"
						hasActive="false" 
						hasViewDet="false" 
						hasDelete="false"
						hasEdit="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="eipAdminContactUsRejected"
						url="AdminContactUsList.html?SEARCH_RESULTS_REJ" mtype="post"
						gridid="gridAdminContactUsRej" editurl="AdminContactUs.html" deleteURL="AdminContactUs.html"
						colHeader="eip.admin.contactUs.contacttype,eip.admin.contactUs.seqno,cms.depName,eip.admin.contactUs.name,Employee.designation.dsgid,eip.admin.contact.contactno,eip.admin.contactUs.emailAddress"
						 colModel="[
						           {name : 'flag',index : 'flag', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'sequenceNo',index : 'sequenceNo', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'departmentEn',index : 'departmentEn', editable : false,sortable : false,search : true, align : 'center' },
						           {name : 'contactNameEn',index : 'contactNameEn', editable : false,sortable : false,search : true, align : 'center' },
								   {name : 'designationEn',index : 'designationEn', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'telephoneNo1En',index : 'telephoneNo1En', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'email1',index : 'email1', editable : false,sortable : false,search : true, align : 'center'}
								 ]"
						height="200" 
						caption="eip.admin.contact.list" 
						isChildGrid="false"
						hasActive="false" 
						hasViewDet="false" 
						hasDelete="false"
						hasEdit="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
					
					</div>
					
			</div>
			</c:if>
			
			<c:if test="${command.makkerchekkerflag eq 'C'}">
			<div class="widget-content padding ">
				<%-- <div class="text-center padding-bottom-10">
				 <a href="javascript:void(0);"
							class="btn btn-blue-2" onclick="openForm('AdminContactUs.html')"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.add" /></a>
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
							<apptags:jQgrid id="eipAdminContactUsPending"
						url="AdminContactUsList.html?SEARCH_RESULTS_PEN" mtype="post"
						gridid="gridAdminContactUsPen" editurl="AdminContactUs.html" deleteURL="AdminContactUs.html"
						colHeader="eip.admin.contactUs.contacttype,eip.admin.contactUs.seqno,cms.depName,eip.admin.contactUs.name,Employee.designation.dsgid,eip.admin.contact.contactno,eip.admin.contactUs.emailAddress"
						 colModel="[
						           {name : 'flag',index : 'flag', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'sequenceNo',index : 'sequenceNo', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'departmentEn',index : 'departmentEn', editable : false,sortable : false,search : true, align : 'center' },
						           {name : 'contactNameEn',index : 'contactNameEn', editable : false,sortable : false,search : true, align : 'center' },
								   {name : 'designationEn',index : 'designationEn', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'telephoneNo1En',index : 'telephoneNo1En', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'email1',index : 'email1', editable : false,sortable : false,search : true, align : 'center'}
								 ]"
						height="200" 
						caption="eip.admin.contact.list" 
						isChildGrid="false"
						hasActive="false" 
						hasViewDet="false" 
						hasDelete="true"
						hasEdit="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="Authenticated">
					<apptags:jQgrid id="eipAdminContactUsAuthenticated"
						url="AdminContactUsList.html?SEARCH_RESULTS_APP" mtype="post"
						gridid="gridAdminContactUsApp" editurl="AdminContactUs.html" deleteURL="AdminContactUs.html"
						colHeader="eip.admin.contactUs.contacttype,eip.admin.contactUs.seqno,cms.depName,eip.admin.contactUs.name,Employee.designation.dsgid,eip.admin.contact.contactno,eip.admin.contactUs.emailAddress"
						 colModel="[
						           {name : 'flag',index : 'flag', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'sequenceNo',index : 'sequenceNo', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'departmentEn',index : 'departmentEn', editable : false,sortable : false,search : true, align : 'center' },
						           {name : 'contactNameEn',index : 'contactNameEn', editable : false,sortable : false,search : true, align : 'center' },
								   {name : 'designationEn',index : 'designationEn', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'telephoneNo1En',index : 'telephoneNo1En', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'email1',index : 'email1', editable : false,sortable : false,search : true, align : 'center'}
								 ]"
						height="200" 
						caption="eip.admin.contact.list" 
						isChildGrid="false"
						hasActive="false" 
						hasViewDet="false" 
						hasDelete="true"
						hasEdit="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
				
					<div class="tab-pane fade active" id="Rejected">
						<apptags:jQgrid id="eipAdminContactUsRejected"
						url="AdminContactUsList.html?SEARCH_RESULTS_REJ" mtype="post"
						gridid="gridAdminContactUsRej" editurl="AdminContactUs.html" deleteURL="AdminContactUs.html"
						colHeader="eip.admin.contactUs.contacttype,eip.admin.contactUs.seqno,cms.depName,eip.admin.contactUs.name,Employee.designation.dsgid,eip.admin.contact.contactno,eip.admin.contactUs.emailAddress"
						 colModel="[
						           {name : 'flag',index : 'flag', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'sequenceNo',index : 'sequenceNo', editable : false,sortable : false,search : true, align : 'center',width :'90' },
						           {name : 'departmentEn',index : 'departmentEn', editable : false,sortable : false,search : true, align : 'center' },
						           {name : 'contactNameEn',index : 'contactNameEn', editable : false,sortable : false,search : true, align : 'center' },
								   {name : 'designationEn',index : 'designationEn', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'telephoneNo1En',index : 'telephoneNo1En', editable : false,sortable : false,search : true, align : 'center'},
								   {name : 'email1',index : 'email1', editable : false,sortable : false,search : true, align : 'center'}
								 ]"
						height="200" 
						caption="eip.admin.contact.list" 
						isChildGrid="false"
						hasActive="false" 
						hasViewDet="false" 
						hasDelete="true"
						hasEdit="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
					</div>
					
					</div>
					
				
			</div>
			</c:if>

		</div>
	</div>
