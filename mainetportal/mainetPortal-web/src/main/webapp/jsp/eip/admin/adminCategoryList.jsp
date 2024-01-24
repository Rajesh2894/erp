<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<div id="heading_wrapper">
		
	<div id="heading_bredcrum">
		<ul>
	   	  <li><a href="CitizenHome.html"><spring:message code="menu.home"/></a></li>
	      <li>&gt;</li>
	      <li><a href="javascript:void(0);"><spring:message code="menu.eip"/></a></li>
	      <li>&gt;</li>
	      <li><a href="javascript:void(0);"><spring:message code="eip.master"/></a></li>
	      <li>&gt;</li>
	      <li><spring:message code="admin.categoryList.Title"/></li>
		</ul>
	</div>
		
</div>

<br/>
<br/>


<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
			
			
				<div class="form-elements">
					<span class="otherlink"><a href="javascript:void(0);" class="btn btn-primary" onclick="openForm('AdminCategoryForm.html')">Add</a></span>
				</div>
	
				<div class="grid-class" id="AdminCategoryGrid">
					<apptags:jQgrid id="AdminCategory" 
									url="AdminCategory.html?CATEGORY_LIST"
									mtype="post" 
									gridid="gridAdminCategoryForm"
									colHeader="admin.categoryList.TitleEn,admin.categoryList.TitleReg"
									colModel="[
												{name : 'catTitleEng',index : 'catTitleEng', editable : false,sortable : false,search : false, align : 'center' },
												{name : 'catTitleReg',index : 'catTitleReg', editable : false,sortable : false,search : false, align : 'center' }
				  							  ]"
									height="200" caption="admin.categoryList.Title" isChildGrid="false"
									hasActive="true" hasViewDet="true" hasDelete="true"
									loadonce="false" sortCol="rowId" showrow="true" />
				</div>


			</div>
		</div>
	</div>
</div>