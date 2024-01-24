<div id="onlineServices" title="D2K Application" style="display: none" class="leanmodal"></div>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="js/mainet/dashboard/highcharts.js"></script>
<script src="js/mainet/dashboard/exporting.js"></script>

<ol class="breadcrumb">
	<li><i class="fa fa-home"></i> <spring:message code="menu.home" /></li>
</ol>
<script>
setTimeout(function(){
	$('.tab-content .active').not(':first').removeClass('active'); 
	},100) 
	document.onscroll = function() {
    if (window.innerHeight + window.scrollY+300 > document.body.clientHeight) {
       $('.mbr-arrow').css('display','none');
    }
    else{
    	$('.mbr-arrow').css('display','block');
    }
}
	$(document).ready(function() {
	    $(".mbr-arrow").click(function(event){
	        $('html, body').animate({scrollTop: '+=630px'}, 800);
	    });	    
	});
</script>

<div class="content">
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<input type="hidden" value='${EIPMenuManager.userType}' id="userType">
	<div class="animated slideInDown">
		<div class="widget">
			<div class="widget-header">
				<h2 id="statusHeading">
					<spring:message code="citizen.services.heading"></spring:message>
				</h2>
			</div>
				
			<div class="widget-content padding">
				<ul id="demo2" class="nav nav-tabs menu-tab" >
				
						<li class="active"><a href="#ToDoAdminFAQ" data-toggle="tab"><spring:message
									code="dashboard.FAQs" text="FAQs"/></a></li>
						<li><a href="#ToDoQuickLink" data-toggle="tab"><spring:message
									code="dashboard.quicklink" text="Quick Link" /></a></li>
						<li><a href="#ToDoSubLink" data-toggle="tab"><spring:message
									code="dashboard.sublink" text="SubLinks" /></a></li>
						<li><a href="#ToDoSectionDetail" data-toggle="tab"><spring:message
									code="dashboard.sections" text="Section Details" /></a></li>
						<li><a href="#ToDoAdminMayor" data-toggle="tab"><spring:message
									code="dashboard.committee1" text=""
									 /></a></li>
						<li><a href="#ToDoAdminDeputyMayor" data-toggle="tab"><spring:message
									code="dashboard.committee2" text=""
									/></a></li>
						<li><a href="#ToDoAdminCommissioner" data-toggle="tab">
						 <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}"> 
						 <spring:message code="dashboard.committee3" text="Secretary Info"/>
						 </c:if>
						  <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}"> 
						 <spring:message code="ulbdashboard.committee3" text="CMO Info"/>
						 </c:if>
						 </a></li>
					
					 <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}"> 
						<li><a href="#ToDoAdminDeputyCommissioner" data-toggle="tab"><spring:message
									code="dashboard.committee4" text="Director Info"
									 /></a></li>
					</c:if>
						<li><a href="#ToDoPublicNotice" data-toggle="tab"><spring:message
									code="dashboard.notice" text="Public Notices" /></a></li>
						<li><a href="#ToDoAdminContactUs" data-toggle="tab"><spring:message
									code="dashboard.contact" text="Contact Us" /></a></li>
						<li><a href="#ToDoEIPHomeImages" data-toggle="tab"><spring:message
									code="dashboard.slider" text="Home Page Slider Image"
									/></a></li>
						<li><a href="#ToDoEIPHomeLogoImages" data-toggle="tab"><spring:message
									code="dashboard.logo" text="Home Page Logo"/></a></li>
						<li><a href="#ToDoEIPAnnounce" data-toggle="tab"><spring:message
									code="dashboard.news" text="Recent Announcement" /></a></li>
				</ul>
				<spring:message code="dashboard.FAQs" text="FAQs" var="faq"/>
				<spring:message code="dashboard.quicklink" text="Quick Link"
					var="quicklink" />
				<spring:message code="dashboard.sublink" text="SubLinks"
					var="sublink" />
				<spring:message code="dashboard.sections" text="Section Details"
					var="sections" />
				<spring:message code="dashboard.committee1"
					text="CM Info" var="committee1" />
				<spring:message code="dashboard.committee2"
					text="Minister Info" var="committee2" />
					 <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
					<spring:message code="dashboard.committee3"
					text="Secretary Info" var="committee3" />
					</c:if>
					 <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">
					<spring:message code="ulbdashboard.committee3"
					text="Director Info" var="committee3" />
					</c:if>
				<spring:message code="dashboard.committee4"
					text="Director Info" var="committee4" />
				<spring:message code="dashboard.notice" text="Public Notices"
					var="notice" />
				<spring:message code="dashboard.contact" text="Contact Us"
					var="contact" />
				<spring:message code="dashboard.slider"
					text="Home Page Slider Image" var="slider" />
				<spring:message code="dashboard.logo" text="Home Page Logo"
					var="logo" />
				<spring:message code="dashboard.news" text="Recent Announcement"
					var="news" />
				<spring:message code="dashboard.heading" text="Checker Pending Task"
					var="dashHeading" />
				<input type="hidden" id="faq" value="${faq}" /> 
				<input type="hidden" id="quicklink" value="${quicklink}" /> 
				<input type="hidden" id="sublink" value="${sublink}" /> 
				<input type="hidden" id="sections" value="${sections}" /> 
				<input type="hidden" id="committee1" value="${committee1}" /> 
				<input type="hidden" id="committee2" value="${committee2}" />
				<input type="hidden" id="committee3" value="${committee3}" /> 
				<input type="hidden" id="committee4" value="${committee4}" /> 
				<input type="hidden" id="notice" value="${notice}" /> 
				<input type="hidden" id="contact" value="${contact}" /> 
				<input type="hidden" id="slider" value="${slider}" /> 
				<input type="hidden" id="logo" value="${logo}" /> 
				<input type="hidden" id="news" value="${news}" /> 
				<input type="hidden" id="dashHeading" value="${dashHeading}" />
				
					<%-- <input type="hidden" id="faq" value="${faq}" /> 
				<input type="hidden" id="quicklink" value="${quicklink}" /> 
				<input type="hidden" id="sublink" value="${sublink}" /> 
				<input type="hidden" id="sections" value="${sections}" /> 
				<input type="hidden" id="committee1" value="${committee1}" /> 
				<input type="hidden" id="committee2" value="${committee2}" /> 
				<input type="hidden" id="notice" value="${notice}" /> 
				<input type="hidden" id="contact" value="${contact}" /> 
				<input type="hidden" id="slider" value="${slider}" /> 
				<input type="hidden" id="logo" value="${logo}" /> 
				<input type="hidden" id="news" value="${news}" /> 
				<input type="hidden" id="dashHeading" value="${dashHeading}" /> --%>

				<div class="tab-content tab-boxed">

					<div class="tab-pane fade  active  in" id="ToDoAdminFAQ">
						<apptags:jQgrid id="adminFAQ" url="AdminFAQSearch.html?FAQ_PEN"
							mtype="post" gridid="gridAdminFAQ"
							colHeader="admin.questionEn,admin.ansEn,admin.questionReg,admin.ansReg"
							colModel="[	{name : 'questionEn',index : 'questionEn',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerEn',index : 'answerEn',editable : false,sortable : false,search : true,align : 'center',width :'150'},
										{name : 'questionReg',index : 'questionReg',editable : false,sortable : false,search : true,align : 'center',width :'100'},
										{name : 'answerReg',index : 'answerReg',editable : false,sortable : false,search : true,align : 'center',width :'150'}
										]"
							sortCol="rowId" isChildGrid="false" hasActive="false"
							hasViewDet="false" hasEdit="true" hasDelete="true" height="200"
							showrow="true" caption="admin.adminFAQHeader" loadonce="true"/>
					</div>

					<div class="tab-pane fade active" id="ToDoQuickLink">
						<apptags:jQgrid id="quickLink"
							url="AdminQuickLink.html?QuickLink_LIST_PEN" mtype="post"
							gridid="gridAdminQuickLinkForm"
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

					<div class="tab-pane fade active" id="ToDoSubLink">
						<apptags:jQgrid url="SectionEntry.html?SEARCH_RESULT_PEN"
							mtype="post"
							colHeader="SectionEntryFormModel.moduleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg"
							isChildGrid="false"
							colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : true,search : true ,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameEn', index : 'subLinkNameEn',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameRg', index : 'subLinkNameRg',editable : false,sortable : true,search : true,align : 'center',width :'100'}
											  ]"
							gridid="gridSectionEntryForm" height="200" id="SubLink"
							loadonce="true" hasEdit="true" hasDelete="true"
							caption="section.header" />
					</div>

					<div class="tab-pane fade active" id="ToDoSectionDetail">
						<apptags:jQgrid url="SectionDetails.html?SEARCH_RESULT_PEN"
							mtype="post"
							colHeader="section.ModuleName,section.FunctionName,section.LinkId,section.LinkEn,section.LinkRg"
							isChildGrid="false"
							colModel="[
												{name : 'moduleName.lookUpDesc', index : 'moduleName.lookUpDesc',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'functionName.lookUpDesc', index : 'functionName.lookUpDesc',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'id', index : 'id',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameEn', index : 'subLinkNameEn',editable : false,sortable : false,search : true,align : 'center',width :'100'},
												{name : 'subLinkNameRg', index : 'subLinkNameRg',editable : false,sortable : false,search : true,align : 'center',width :'100'}
											  ]"
							gridid="gridSectionDetails" height="200" id="SectionDetail"
							loadonce="true" hasViewDet="false" hasEdit="true"
							viewAjaxRequest="true" hasDelete="false" caption="section.header" />
					</div>

					<div class="tab-pane fade active" id="ToDoAdminMayor">
						<apptags:jQgrid id="AdminMayorForm"
							url="AdminMayor.html?Mayor_PEN" mtype="post"
							gridid="gridAdminMayorForm"
							colHeader="mayorGrid.NameEn,mayorGrid.NameReg"
							colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
							height="200" caption="mayorGrid.list" isChildGrid="false"
							hasActive="false" hasViewDet="false" hasEdit="true"
							hasDelete="true" loadonce="true" sortCol="rowId" showrow="true" />
					</div>

					<div class="tab-pane fade active" id="ToDoAdminDeputyMayor">
						<apptags:jQgrid id="AdminDeputyMayorForm"
							url="AdminDeputyMayorList.html?DeputyMayorList_PEN" mtype="post"
							gridid="gridAdminDeputyMayorForm"
							colHeader="dmayorGrid.NameEn,dmayorGrid.NameReg"
							colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
							height="200" caption="dmayorGrid.list" isChildGrid="false"
							hasActive="false" hasViewDet="false" hasEdit="true"
							hasDelete="true" loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					
					<div class="tab-pane fade active" id="ToDoAdminDeputyCommissioner">
						<apptags:jQgrid id="AdminDeputyCommissionerForm" url="AdminDeputyCommissioner.html?DeputyCommissioner_PEN"
						mtype="post" gridid="gridAdminDeputyCommissionerForm"
						colHeader="dComGrid.NameEn,dComGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'pNameReg',index : 'linkTitleReg', editable : false,sortable : false,search : false, align : 'center' }
								
				  ]"
						height="200" caption="dComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
					<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}"> 
						 <div class="tab-pane fade active" id="ToDoAdminCommissioner">
						<apptags:jQgrid id="AdminCommissionerForm"
						url="AdminCommissioner.html?Commissioner_PEN" mtype="post"
						gridid="gridAdminCommissionerForm"
						colHeader="ComGrid.NameEn,ComGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="ComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
						 </c:if>
					<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}"> 
					<div class="tab-pane fade active" id="ToDoAdminCommissioner">
						<apptags:jQgrid id="AdminCommissionerForm"
						url="AdminCommissioner.html?Commissioner_PEN" mtype="post"
						gridid="gridAdminCommissionerForm"
						colHeader="ulbComGrid.NameEn,ulbComGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'pNameEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'pNameReg',index : 'pNameReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
						height="200" caption="ulbComGrid.list" isChildGrid="false"
						hasActive="false" hasViewDet="false" hasEdit="true" hasDelete="true"
						loadonce="true" sortCol="rowId" showrow="true" />
					</div>
						 </c:if>
						
						
					<div class="tab-pane fade active" id="ToDoPublicNotice">
						<apptags:jQgrid id="publicNotice"
							url="PublicNotices.html?SEARCH_RESULTS_PEN" mtype="post"
							gridid="gridAdminPublicNoticesForm"
							colHeader="admin.publicNotice.IssueDate,admin.publicNotice.NoticeSubjectEn,admin.publicNotice.NoticeSubjectReg"
							colModel="[
						{name : 'issueDateNew',index : 'issueDateNew', editable : false,sortable : false,search : true, align : 'center',formatter:'date',sorttype: 'date', formatoptions:{srcformat: 'd/m/Y',newformat: 'd/m/Y'}},
								{name : 'noticeSubEn',index : 'noticeSubEn', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'noticeSubReg',index : 'noticeSubReg', editable : false,sortable : false,search : true, align : 'center' }
								
				  ]"
							height="200" caption="admin.publicNoticeList" isChildGrid="false"
							hasActive="false" hasViewDet="false" hasEdit="true"
							hasDelete="true" loadonce="true" sortCol="rowId" showrow="true" />
					</div>

					<div class="tab-pane fade active" id="ToDoAdminContactUs">
						<apptags:jQgrid id="eipAdminContactUs"
							url="AdminContactUsList.html?SEARCH_RESULTS_PEN" mtype="post"
							gridid="gridAdminContactUs"
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
							height="200" caption="eip.admin.contact.list" isChildGrid="false"
							hasActive="false" hasViewDet="false" hasDelete="true"
							hasEdit="true" loadonce="true" sortCol="rowId" showrow="true" />
					</div>

					<div class="tab-pane fade active" id="ToDoEIPHomeImages">
						<apptags:jQgrid id="eIPHomeImages"
							url="EIPHomeImagesSearch.html?SEARCH_RESULTS_PEN" mtype="post"
							gridid="gridEIPHomeImages"
							colHeader="eipAdminHomeImages.imageName,eipAdminHomeImages.ImageOrder"
							colModel="[	
								{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
								{name : 'hmImgOrder',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center'}
							]"
							sortCol="rowId" isChildGrid="false" hasActive="true"
							hasViewDet="false" hasEdit="true" hasDelete="true" height="150"
							showrow="true" caption="eipAdminHomeImages.GridCaption"
							loadonce="true" />
					</div>

					<div class="tab-pane fade active" id="ToDoEIPHomeLogoImages">
						<apptags:jQgrid id="eIPHomeLogoImages"
							url="EIPHomeImagesSearch.html?SEARCH_RESULTS_LOGO_PEN"
							mtype="post" gridid="gridEIPLogoImages"
							colHeader="eipAdminHomeImages.imageName,EIPHomeImages.ImageSideOrder"
							colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'moduleType',index : 'moduleType',editable : false,sortable : false,search : true,align : 'center'}
								]"
							sortCol="rowId" isChildGrid="false" hasActive="true"
							hasViewDet="false" hasEdit="true" hasDelete="true" height="150"
							showrow="true" caption="eipAdminHomeImages.GridCaption.logo"
							loadonce="true" />
					</div>

					<div class="tab-pane fade active" id="ToDoEIPAnnounce">
						<apptags:jQgrid id="eipAnnounce"
							url="EipAnnouncement.html?SEARCH_RESULTS_PEN" mtype="post"
							gridid="gridEipAnnouncementForm"
							colHeader="admin.EIPAnnouncement.announceDescEng,admin.EIPAnnouncement.announceDescReg,admin.EIPAnnouncement.attach,admin.EIPAnnouncement.image"
							colModel="[
								{name : 'announceDescEng',index : 'announceDescEng', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'announceDescReg',index : 'announceDescReg', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachName',index : 'attachName', editable : false,sortable : false,search : true, align : 'center' },
								{name : 'attachImageName',index : 'attachImageName', editable : false,sortable : false,search : true, align : 'center' }
				                ]"
							height="200" caption="admin.EipAnnouncement" isChildGrid="false"
							hasActive="true" hasViewDet="false" hasEdit="true"
							hasDelete="true" loadonce="true" sortCol="rowId" showrow="true" />
					</div>
				</div>
			</div>

			<div id="container" style="background: #123456 !important;"></div>
		</div>
		
		<div class="clearfix"></div>
			<!-- Start Added by ABM2144 -->
			<c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }" var="user">		
			<div class="container">			
			<div class="row">
			<div class="col-lg-4 col-lg-offset-8 col-md-5 col-md-offset-7 col-sm-6 col-sm-offset-6 col-xs-11 col-xs-offset-1" id="jumpTask">						
			<select id="selectedTask" data-content="" class="form-control chosen-select-no-results" aria-label="Search Task">
				<option value="0">Search Task</option>
				<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
					<c:forEach items="${menuRoleEntitlement.childList}" var="data">
					<c:if test="${masters.entitle.smfid eq  data.parentId}">
					<c:set var="action0" value="${data.entitle.smfaction}" />					
					<optgroup label="${masters.entitle.smfname}">
			    	<option value="${data.entitle.smfid}">${data.entitle.smfname}</option>
			    	<c:forEach items="${menuRoleEntitlement.childList}"	var="data1">
			    	<c:if test="${data.entitle.smfid eq  data1.parentId}">
			    	<c:set var="action1" value="${data1.entitle.smfaction}" />
			    	<option value="${data1.entitle.smfid}">${data1.entitle.smfname}</option>
			    	<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
					<c:if test="${data1.entitle.smfid eq  data2.parentId}">																	
					<c:set var="action2" value="${data2.entitle.smfaction}" />
					<option value="${data2.entitle.smfid}">${data2.entitle.smfname}</option>
					<c:forEach items="${menuRoleEntitlement.childList}"	var="data3">
					<c:if test="${data2.entitle.smfid eq  data3.parentId}">	
					<c:set var="action3" value="${data3.entitle.smfaction}" />
					<option value="${data3.entitle.smfid}">${data3.entitle.smfname}</option>
					</c:if></c:forEach>
					</c:if></c:forEach>
			    	</c:if></c:forEach>
			    	</optgroup> 
			    	</c:if></c:forEach>
				</c:forEach>
			</select>
			</div></div></div>				
    		</c:if>
    		<!-- End Added by ABM2144-->
    		
			<h2 id="dashmenu"><span class="hidden"><spring:message code="CitizenServices" text="CitizenServices"></spring:message></span></h2> 
			<div class="col-lg-12">
			<div id="sidebar-menu">
				<ul id="nav" class="has_sub">
						<li id="0" style="display:none"><a>Search Task</a></li>
						<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
						<li class="has_sub" id="${masters.entitle.smfid}">
						<a role="link" title="${masters.entitle.smfname}" id="go${masters.entitle.smfid}" name="go${masters.entitle.smfid}"  href="${masters.entitle.smfaction}" onclick="dodajAktywne(this);breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});"><span>${masters.entitle.smfname}</span> </a>
							
							<ul class="scroll-menu">
								<c:forEach items="${menuRoleEntitlement.childList}" var="data">
								   <c:if test="${masters.entitle.smfid eq  data.parentId}">
										 <c:set var="action0" value="${data.entitle.smfaction}" />
										
										<li class="navigation" id="${data.entitle.smfid}">
										<a role="link" title="${data.entitle.smfname}"  <c:choose> <c:when test="${fn:containsIgnoreCase(action0 , '.html')}">onclick="openRelatedForm('${action0}','this','${data.entitle.smfdescription}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise> href="${action0}" </c:otherwise></c:choose> > ${data.entitle.smfname} </a> 
											<ul class="scroll-menu1">
												<c:forEach items="${menuRoleEntitlement.childList}"	var="data1">
													
													<c:if test="${data.entitle.smfid eq  data1.parentId}">
														
														
														
												<c:set var="action1" value="${data1.entitle.smfaction}" />
												
												
												
												<li class="folder " id="${data1.entitle.smfid}"><a title="${data1.entitle.smfname}"  <c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}">onclick="openRelatedForm('${action1}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action1}"</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
															<ul>
																<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
																	<c:if test="${data1.entitle.smfid eq  data2.parentId}">
																	
																		<c:set var="action2" value="${data2.entitle.smfaction}" />
																		<li class="folder" id="${data2.entitle.smfid}"><a role="link" title="${data2.entitle.smfname}"  <c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}">onclick="openRelatedForm('${action2}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action2}"</c:otherwise></c:choose>>${data2.entitle.smfname}</a>
																			<ul>
																				<c:forEach items="${menuRoleEntitlement.childList}"	var="data3">
																					<c:if test="${data2.entitle.smfid eq  data3.parentId}">	
																							<c:set var="action3" value="${data3.entitle.smfaction}" />
																						<li class="folder" id="${data3.entitle.smfid}"><a role="link" title="${data3.entitle.smfname}" <c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}">onclick="openRelatedForm('${action3}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}+${data3.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action3}"</c:otherwise></c:choose>>${data3.entitle.smfname}</a></li>
																					</c:if>
																				</c:forEach>
																			</ul></li>
																	</c:if>
																</c:forEach>
															</ul></li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:if>
								</c:forEach>
							</ul>
							</li>
					</c:forEach>
			</ul>
			</div>
			
		</div>
		<div class="clearfix"></div>
	</div>
</div>

<script>

	$(window).on('load', function(){
		 var color=[];
		 var faq =$('#gridAdminFAQ >> tr').length-1;
		 var quicklink =$('#gridAdminQuickLinkForm >> tr').length-1;
		 var sublink =$('#gridSectionEntryForm >> tr').length-1;
		 var section =$('#gridSectionDetails >> tr').length-1;
		 var mayor =$('#gridAdminMayorForm >> tr').length-1;
		 var deputyMayor =$('#gridAdminDeputyMayorForm >> tr').length-1;
		 var commissioner =$('#gridAdminCommissionerForm >> tr').length-1;
		 var deputyCommissioner =$('#gridAdminDeputyCommissionerForm >> tr').length-1;
		 var notice =$('#gridAdminPublicNoticesForm >> tr').length-1;
		 var contact =$('#gridAdminContactUs >> tr').length-1;
		 var images =$('#gridEIPHomeImages >> tr').length-1;
		 var logo =$('#gridEIPLogoImages >> tr').length-1;
		 var news =$('#gridEipAnnouncementForm >> tr').length-1;
		 var browserdata=[];
						if (faq > 0) {
							browserdata.push({
								name : $("#faq").val(),
								y : faq,
							});
							color.push('#FE2D00');
						}
						if (quicklink > 0) {
							browserdata.push({
								name : $("#quicklink").val(),
								y : quicklink,
							});
							color.push('#3FAF00');
						}
						if (sublink > 0) {
							browserdata.push({
								name : $("#sublink").val(),
								y : sublink,
							});
							color.push('#0094AF');
						}
						if (section > 0) {
							browserdata.push({
								name : $("#sections").val(),
								y : section,
							});
							color.push('#2D65C2');
						}
						if (mayor > 0) {
							browserdata.push({
								name : $("#committee1").val(),
								y : mayor,
							});
							color.push('#B200FF');
						}
						if (deputyMayor > 0) {
							browserdata.push({
								name : $("#committee2").val(),
								y : deputyMayor,

							});
							color.push('#981046');
						}
						if (commissioner > 0) {
							browserdata.push({
								name : $("#committee3").val(),
								y : commissioner,
							});
							color.push('#0000FF');
						}
						if (deputyCommissioner > 0) {
							browserdata.push({
								name : $("#committee4").val(),
								y : deputyCommissioner,

							});
							color.push('#006400');
						}
						if (notice > 0) {
							browserdata.push({
								name : $("#notice").val(),
								y : notice,
							});
							color.push('#CFC800');
						}
						if (contact > 0) {
							browserdata.push({
								name : $("#contact").val(),
								y : contact,
							});
							color.push('#74FF00');
						}
						if (images > 0) {
							browserdata.push({
								name : $("#slider").val(),
								y : images,
							});
							color.push('#00E0FF');
						}
						if (logo > 0) {
							browserdata.push({
								name : $("#logo").val(),
								y : logo,
							});
							color.push('#C70039');
						}
						if (news > 0) {
							browserdata.push({
								name : $("#news").val(),
								y : news,
							});
							color.push('#4E0081');
						}
						Highcharts.setOptions({
							colors : color
						});

						$('#container').highcharts(
								{
									chart : {
										plotBackgroundColor : null,
										plotBorderWidth : null,
										plotShadow : false,
										type : 'pie'
									},
									title : {
										text : $("#dashHeading").val()
									},
									exporting : {
										enabled : false
									},
									tooltip : {
										useHTML : true,
										formatter : function() {
											return '<b>' + '<span>'
													+ this.point.name
													+ '</span> :</b> '
													+ this.point.y;
										}
									},
									noData : {
										attr : undefined,
										position : {
											"x" : 0,
											"y" : 0,
											"align" : "center",
											"verticalAlign" : "middle"
										},
										style : {
											"fontSize" : "15px",
											"fontWeight" : "bold",
											"color" : "#000000"
										},
										useHTML : false,
									},
									plotOptions : {
										pie : {
											allowPointSelect : true,
											cursor : 'pointer',
											dataLabels : {
												enabled : true,
												color : '#000000',
												connectorColor : '#000000',
												distance : 15,
												useHTML : true,
												formatter : function() {
													return '<b>'+this.point.name+' : '+this.point.y+'</b>';
												}
											},
											showInLegend : true,
											point : {
												events : {
													click : function(event) {
													},
												}
											}
										}
									},
									series : [ {
										type : 'pie',
										name : '#',                                                                              
										data : browserdata
									} ]
								});
					});
</script>

<!-- Down Arrow Start -->
	<div class="mbr-arrow hidden-sm-down">
		<a> <i class="fa fa-angle-down" aria-hidden="true"></i>
			<span><spring:message code="theme3.portal.scroll" text="Scroll"/></span>
		</a>
	</div>
<!-- Down Arrow End -->

<script>
$(window).on('load', function(){
   $("#selectedTask").chosen();
   $("#selectedTask_chosen").removeAttr("style");
   $("#selectedTask").on("change",function(){
	   var search = $("#selectedTask option:selected").val().trim();
	   $($("li[id="+search+"]").parentsUntil("ul#nav")).each(function() {if($(this).is("ul")){$(this).css("display","block")}});
 	   $('html, body').animate({
 		   scrollTop: $("li[id="+search+"] > a").addClass("flashi").offset().top-50}, 'slow'); 	   
   }) 
  });
</script>