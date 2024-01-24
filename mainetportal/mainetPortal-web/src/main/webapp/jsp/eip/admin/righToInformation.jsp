<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<div id="heading_wrapper">

	<div id="heading_bredcrum">
		<ul>
			<li><a href="CitizenHome.html"><spring:message code="menu.home"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="menu.eip"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="eip.quicklink"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="eip.download"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="eip.rti"/></a></li>
			
		</ul>
	</div>

</div>
<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				<form:form action="rightToInformation.html" name="frmrightToInformation" id="rightToInformation">
				
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="form-elements">
						<span class="otherlink">
						 <a href="javascript:void(0);"
							class="btn btn-primary" onclick="openForm('RightToInfoEntryForm.html')">Add</a>
						</span>
						</div>
				</form:form>
				<div class="grid-class" id="RightToInfoGrid">
					<apptags:jQgrid id="rightToInformation"
						url="RightToInformation.html?RIGHT_TO_INFO_LIST" mtype="post"
						gridid="gridRightToInfoEntryForm"
						colHeader="rti.depName,eip.rti.subjectHn,eip.rti.subjectMr"
						colModel="[
						    {name : 'deptId.dpDeptdesc',index : 'deptId.dpDeptdesc',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'rtiInfDescEng',index : 'rtiInfDescEng',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'rtiInfDescReg',index : 'rtiInfDescEng',editable : false,sortable : false,search : false,  align : 'center'}
					  		]"
						height="200" 
						caption="eip.rti.infoGrid" 
						isChildGrid="false"
						hasActive="true"
						hasEdit="true"
                        showInDialog="false"  
						hasViewDet="false" 
						hasDelete="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true"
						 />
				</div>
			</div>
	   </div>
</div>
				</div>
				