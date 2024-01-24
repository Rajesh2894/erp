<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/file-upload.js"></script>
<script>
setTimeout(function(){
	$('.tab-content2 .active').not(':first').removeClass('active'); 
	$('.tab-content1 .active').not(':first').removeClass('active'); 
},100) 
function saveForm(element)
{
	return saveOrUpdateForm(element, 'Save success', 'EIPHomeImages.html', 'saveform');
}


</script>
<script src="js/mainet/chargemaster.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">			  
	<div class="widget">
	     <div class="widget-header"><h2><strong><spring:message code="eipAdminHomeImages.breadcrumb"/></strong></h2></div>
	     <apptags:helpDoc url="EIPHomeImagesSearch.html"></apptags:helpDoc> 
	     <c:if test="${command.makkerchekkerflag ne 'C'}">	
			<div class="widget-content padding form-horizontal">	
				<form:form action="EIPHomeImagesSearch.html" name="frmMasterForm" id="frmMasterForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp"/>
				</form:form>
				
				<!-- Main Tabs starts -->
				<ul class="nav nav-tabs home-page-images">
					<li class="active">
						<a href="#sliderDiv" data-toggle="tab"><spring:message code="eipAdminHomeImages.GridCaption" text="Slider Image List"/></a>
				</li>
				<li>
					<a href="#logoDiv" data-toggle="tab"><spring:message code="eipAdminHomeImages.GridCaption.logo" text="Logo Image List"/></a>
				    </li>
				</ul>
				<!-- Main Tabs ends -->
				
                <div class="tab-content home-page-main-content">
                	<!-- Slider Images List starts -->
					<div class="tab-pane fade in active" id="sliderDiv">
					         <div class="text-center">
								<span class="otherlink"> 
								<a href="javascript:void(0);"  onclick="openForm('EIPHomeImages.html')"class="btn btn-blue-2">  <i class="fa fa-plus-circle"> </i> <spring:message code="eip.add" /></a> 
								</span>
							 </div>
							<h4><spring:message code="eipAdminHomeImages.GridCaption" text="Slider Image List"/></h4> 
							
							<ul id="demo2" class="nav nav-tabs">
						<li class="active"><a href="#Pending" data-toggle="tab"><spring:message
									code="pending" text="Pending" /></a></li>
						<li><a href="#Authenticated" data-toggle="tab"><spring:message
									code="authenticated" text="Authenticated" /></a></li>
						<li><a href="#Rejected" data-toggle="tab"><spring:message
									code="rejected" text="Rejected" /></a></li>
					</ul>
					
					<div class="tab-content tab-boxed tab-content2">
						<div class="tab-pane fade active in" id="Pending">
								<apptags:jQgrid id="eIPHomeImagesPending"
						url="EIPHomeImagesSearch.html?SEARCH_RESULTS_PEN" mtype="post"
						gridid="gridEIPHomeImagesPen" editurl="EIPHomeImages.html"
						colHeader="eipAdminHomeImages.imageName,eipAdminHomeImages.ImageOrder,ProfileMaster.imageName"
						colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'hmImgOrder',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center'},
									{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
								]"
						sortCol="rowId" isChildGrid="false" hasActive="false"
						hasViewDet="false" hasEdit="true" hasDelete="false" height="150" showrow="true"
						caption="eipAdminHomeImages.GridCaption" loadonce="true" />
						</div>
						
						<div class="tab-pane fade active" id="Authenticated">
							<apptags:jQgrid id="eIPHomeImagesAuthenticated"
						url="EIPHomeImagesSearch.html?SEARCH_RESULTS_APP" mtype="post"
						gridid="gridEIPHomeImagesApp" editurl="EIPHomeImages.html"
						colHeader="eipAdminHomeImages.imageName,eipAdminHomeImages.ImageOrder,ProfileMaster.imageName"
						colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'hmImgOrder',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center'},
									{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
								]"
						sortCol="rowId" isChildGrid="false" hasActive="false"
						hasViewDet="false" hasEdit="true" hasDelete="false" height="150" showrow="true"
						caption="eipAdminHomeImages.GridCaption" loadonce="true" />
						</div>
					
						<div class="tab-pane fade active" id="Rejected">
							<apptags:jQgrid id="eIPHomeImagesRejected"
						url="EIPHomeImagesSearch.html?SEARCH_RESULTS_REJ" mtype="post"
						gridid="gridEIPHomeImagesRej" editurl="EIPHomeImages.html"
						colHeader="eipAdminHomeImages.imageName,eipAdminHomeImages.ImageOrder,ProfileMaster.imageName"
						colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'hmImgOrder',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center'},
									{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
								]"
						sortCol="rowId" isChildGrid="false" hasActive="false"
						hasViewDet="false" hasEdit="true" hasDelete="false" height="150" showrow="true"
						caption="eipAdminHomeImages.GridCaption" loadonce="true" />
						</div>
						</div>
						
					</div>
					<!-- Slider Images List ends -->
					
					<!-- Logo Images List starts -->
				    <div class="tab-pane fade" id="logoDiv">
					    <div class="text-center">
							<span class="otherlink"> 
							<a href="javascript:void(0);" onclick="openForm('EIPLogoImages.html')"class="btn btn-blue-2"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.add" /></a> 
							</span>
						 </div> 
					
						 <h4><spring:message code="eipAdminHomeImages.GridCaption.logo" text="Logo Image List"/></h4>
						 <ul id="demo2" class="nav nav-tabs">
							<li class="active"><a href="#logoPending" data-toggle="tab"><spring:message
										code="pending" text="Pending" /></a></li>
							<li><a href="#logoAuthenticated" data-toggle="tab"><spring:message
										code="logoAuthenticated" text="logoAuthenticated" /></a></li>
							<li><a href="#logoRejected" data-toggle="tab"><spring:message
										code="logoRejected" text="logoRejected" /></a></li>
						</ul>
				
						<div class="tab-content tab-boxed tab-content1">
							<div class="tab-pane fade active in" id="logoPending">
									 <apptags:jQgrid id="eIPHomeLogoImagesPending"
								url="EIPHomeImagesSearch.html?SEARCH_RESULTS_LOGO_PEN" mtype="post"
								gridid="gridEIPLogoImagesPen" editurl="EIPLogoImages.html"
								colHeader="eipAdminHomeImages.imageName,EIPHomeImages.ImageSideOrder,ProfileMaster.imageName"
								colModel="[	
											{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
											{name : 'moduleType',index : 'moduleType',editable : false,sortable : false,search : true,align : 'center'},
											{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
										]"
								sortCol="rowId" isChildGrid="false" hasActive="false"
								hasViewDet="false" hasEdit="true" hasDelete="false" height="150" showrow="true"
								caption="eipAdminHomeImages.GridCaption.logo" loadonce="true" />
							</div>
							
							<div class="tab-pane fade active" id="logoAuthenticated">
								 <apptags:jQgrid id="eIPHomeLogoImagesAuthenticated"
								url="EIPHomeImagesSearch.html?SEARCH_RESULTS_LOGO_APP" mtype="post"
								gridid="gridEIPLogoImagesApp" editurl="EIPLogoImages.html"
								colHeader="eipAdminHomeImages.imageName,EIPHomeImages.ImageSideOrder,ProfileMaster.imageName"
								colModel="[	
											{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
											{name : 'moduleType',index : 'moduleType',editable : false,sortable : false,search : true,align : 'center'},
											{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
										]"
								sortCol="rowId" isChildGrid="false" hasActive="false"
								hasViewDet="false" hasEdit="true" hasDelete="false" height="150" showrow="true"
								caption="eipAdminHomeImages.GridCaption.logo" loadonce="true" />
							</div>
						
							<div class="tab-pane fade active" id="logoRejected">
								 <apptags:jQgrid id="eIPHomeLogoImagesRejected"
								url="EIPHomeImagesSearch.html?SEARCH_RESULTS_LOGO_REJ" mtype="post"
								gridid="gridEIPLogoImagesRej" editurl="EIPLogoImages.html"
								colHeader="eipAdminHomeImages.imageName,EIPHomeImages.ImageSideOrder,ProfileMaster.imageName"
								colModel="[	
											{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
											{name : 'moduleType',index : 'moduleType',editable : false,sortable : false,search : true,align : 'center'},
											{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
										]"
								sortCol="rowId" isChildGrid="false" hasActive="false"
								hasViewDet="false" hasEdit="true" hasDelete="false" height="150" showrow="true"
								caption="eipAdminHomeImages.GridCaption.logo" loadonce="true" />
							</div>
						</div>
					
					 </div>
					<!-- Logo Images List ends -->
			 	</div>	  
			</div>
		</c:if>	
			
		<c:if test="${command.makkerchekkerflag eq 'C'}">	
			<div class="widget-content padding form-horizontal">	
				<form:form action="EIPHomeImagesSearch.html" name="frmMasterForm" id="frmMasterForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp"/>
				</form:form>
				
				<!-- Main Tabs starts -->
				<ul class="nav nav-tabs home-page-images">
					<li class="active">
						<a href="#sliderDiv" data-toggle="tab"><spring:message code="eipAdminHomeImages.GridCaption" text="Slider Image List"/></a>
				</li>
				<li>
					<a href="#logoDiv" data-toggle="tab"><spring:message code="eipAdminHomeImages.GridCaption.logo" text="Logo Image List"/></a>
				    </li>
				</ul>
				<!-- Main Tabs ends -->
				
				<div class="tab-content home-page-main-content">
					<!-- Slider Images List starts -->
					<div class="tab-pane fade in active" id="sliderDiv">
					         <%-- <div class="text-center">
								<span class="otherlink"> 
								<a href="javascript:void(0);"  onclick="openForm('EIPHomeImages.html')"class="btn btn-blue-2">  <i class="fa fa-plus-circle"> </i> <spring:message code="eip.add" /></a> 
								</span>
							 </div> --%>
							<h4><spring:message code="eipAdminHomeImages.GridCaption" text="Slider Image List"/></h4>
							<ul id="demo2" class="nav nav-tabs">
						<li class="active"><a href="#Pending" data-toggle="tab"><spring:message
									code="pending" text="Pending" /></a></li>
						<li><a href="#Authenticated" data-toggle="tab"><spring:message
									code="authenticated" text="Authenticated" /></a></li>
						<li><a href="#Rejected" data-toggle="tab"><spring:message
									code="rejected" text="Rejected" /></a></li>
					</ul>
					
					<div class="tab-content tab-boxed tab-content2">
						<div class="tab-pane fade active in" id="Pending">
								<apptags:jQgrid id="eIPHomeImagesPending"
						url="EIPHomeImagesSearch.html?SEARCH_RESULTS_PEN" mtype="post"
						gridid="gridEIPHomeImagesPen" editurl="EIPHomeImages.html" deleteURL="EIPHomeImages.html"
						colHeader="eipAdminHomeImages.imageName,eipAdminHomeImages.ImageOrder,ProfileMaster.imageName"
						colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'hmImgOrder',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center'},
									{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
								]"
						sortCol="rowId" isChildGrid="false" hasActive="false"
						hasViewDet="false" hasEdit="true" hasDelete="true" height="150" showrow="true"
						caption="eipAdminHomeImages.GridCaption" loadonce="true" />
						</div>
						
						<div class="tab-pane fade active" id="Authenticated">
								<apptags:jQgrid id="eIPHomeImagesAuthenticated"
						url="EIPHomeImagesSearch.html?SEARCH_RESULTS_APP" mtype="post"
						gridid="gridEIPHomeImagesApp" editurl="EIPHomeImages.html" deleteURL="EIPHomeImages.html"
						colHeader="eipAdminHomeImages.imageName,eipAdminHomeImages.ImageOrder,ProfileMaster.imageName"
						colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'hmImgOrder',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center'},
									{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
								]"
						sortCol="rowId" isChildGrid="false" hasActive="false"
						hasViewDet="false" hasEdit="true" hasDelete="true" height="150" showrow="true"
						caption="eipAdminHomeImages.GridCaption" loadonce="true" />
						</div>
					
						<div class="tab-pane fade active" id="Rejected">
								<apptags:jQgrid id="eIPHomeImagesRejected"
						url="EIPHomeImagesSearch.html?SEARCH_RESULTS_REJ" mtype="post"
						gridid="gridEIPHomeImagesRej" editurl="EIPHomeImages.html" deleteURL="EIPHomeImages.html"
						colHeader="eipAdminHomeImages.imageName,eipAdminHomeImages.ImageOrder,ProfileMaster.imageName"
						colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'hmImgOrder',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center'},
									{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
								]"
						sortCol="rowId" isChildGrid="false" hasActive="false"
						hasViewDet="false" hasEdit="true" hasDelete="true" height="150" showrow="true"
						caption="eipAdminHomeImages.GridCaption" loadonce="true" />
						</div>
						
						</div>
						 
					</div>
					<!-- Slider Images List ends -->
				    
				    <%-- <div class="text-center margin-top-10">
						<span class="otherlink"> 
						<a href="javascript:void(0);" onclick="openForm('EIPLogoImages.html')"class="btn btn-blue-2"> <i class="fa fa-plus-circle"> </i> <spring:message code="eip.add" /></a> 
						</span>
					 </div> --%>
					 
					 <!-- Logo Images List starts -->
					 <div class="tab-pane fade" id="logoDiv">
					    <h4><spring:message code="eipAdminHomeImages.GridCaption.logo" text="Logo Image List"/></h4>
					    <ul id="demo2" class="nav nav-tabs">
					<li class="active"><a href="#logoPending" data-toggle="tab"><spring:message
								code="pending" text="logoPending" /></a></li>
					<li><a href="#logoAuthenticated" data-toggle="tab"><spring:message
								code="authenticated" text="Authenticated" /></a></li>
					<li><a href="#logoRejected" data-toggle="tab"><spring:message
								code="logoRejected" text="logoRejected" /></a></li>
				</ul>
				
				<div class="tab-content tab-boxed tab-content1">
					<div class="tab-pane fade active in" id="logoPending">
							 <apptags:jQgrid id="eIPHomeLogoImagesPending"
						url="EIPHomeImagesSearch.html?SEARCH_RESULTS_LOGO_PEN" mtype="post"
						gridid="gridEIPLogoImagesPen" editurl="EIPLogoImages.html" deleteURL="EIPLogoImages.html"
						colHeader="eipAdminHomeImages.imageName,EIPHomeImages.ImageSideOrder,ProfileMaster.imageName"
						colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'moduleType',index : 'moduleType',editable : false,sortable : false,search : true,align : 'center'},
									{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
								]"
						sortCol="rowId" isChildGrid="false" hasActive="false"
						hasViewDet="false" hasEdit="true" hasDelete="true" height="150" showrow="true"
						caption="eipAdminHomeImages.GridCaption.logo" loadonce="true" />
					</div>
					
					<div class="tab-pane fade active" id="logoAuthenticated">
						 <apptags:jQgrid id="eIPHomeLogoImagesAuthenticated"
						url="EIPHomeImagesSearch.html?SEARCH_RESULTS_LOGO_APP" mtype="post"
						gridid="gridEIPLogoImagesApp" editurl="EIPLogoImages.html" deleteURL="EIPLogoImages.html"
						colHeader="eipAdminHomeImages.imageName,EIPHomeImages.ImageSideOrder,ProfileMaster.imageName"
						colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'moduleType',index : 'moduleType',editable : false,sortable : false,search : true,align : 'center'},
									{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
								]"
						sortCol="rowId" isChildGrid="false" hasActive="false"
						hasViewDet="false" hasEdit="true" hasDelete="true" height="150" showrow="true"
						caption="eipAdminHomeImages.GridCaption.logo" loadonce="true" />
					</div>
				
					<div class="tab-pane fade active" id="logoRejected">
						 <apptags:jQgrid id="eIPHomeLogoImagesRejected"
						url="EIPHomeImagesSearch.html?SEARCH_RESULTS_LOGO_REJ" mtype="post"
						gridid="gridEIPLogoImagesRej" editurl="EIPLogoImages.html" deleteURL="EIPLogoImages.html"
						colHeader="eipAdminHomeImages.imageName,EIPHomeImages.ImageSideOrder,ProfileMaster.imageName"
						colModel="[	
									{name : 'imageName',index : 'imageName',editable : false,sortable : false,search : true,align : 'center'},							
									{name : 'moduleType',index : 'moduleType',editable : false,sortable : false,search : true,align : 'center'},
									{name : 'imagePath',index : 'hmImgOrder',editable : false,sortable : false,search : true,align : 'center',formatter: showImageOnGrid}
								]"
						sortCol="rowId" isChildGrid="false" hasActive="false"
						hasViewDet="false" hasEdit="true" hasDelete="true" height="150" showrow="true"
						caption="eipAdminHomeImages.GridCaption.logo" loadonce="true" />
					</div>
					
					</div>
					    
					
					 </div>
					 <!-- Logo Images List ends -->
				 </div>	  
			</div>
		</c:if>	
	</div>	
	</div>				
						
				  			