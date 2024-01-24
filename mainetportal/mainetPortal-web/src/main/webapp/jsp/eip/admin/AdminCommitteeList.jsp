<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				<div class="form-elements">

					<span class="otherlink"> <a href="javascript:void(0);"
						class="btn btn-primary"
						onclick="openForm('AdminCommitteeForm.html')">Add</a>
					</span>
				</div>

				<div class="grid-class" id="quickAdminCommitteeForm">
					<apptags:jQgrid id="AdminCommitteeForm"
						url="AdminCommitteeList.html?CommitteeList" mtype="post"
						gridid="gridAdminCommitteeForm"
						colHeader="adminCommitteGrid.NameEn,adminCommitteGrid.NameReg"
						colModel="[
								{name : 'pNameEn',index : 'linkTitleEg', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'pNameReg',index : 'linkTitleReg', editable : false,sortable : false,search : false, align : 'center' }
								
				  ]"
						height="200" caption="adminCommitteGrid.Title" isChildGrid="false"
						hasActive="false" hasViewDet="true" hasDelete="true"
						loadonce="false" sortCol="rowId" showrow="true" />
				</div>
			</div>
		</div>
	</div>
</div>