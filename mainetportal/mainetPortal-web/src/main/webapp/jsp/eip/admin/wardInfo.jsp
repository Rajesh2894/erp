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
			
			<li><spring:message code="eip.ward.title" /></li>
		</ul>
	</div>

</div>
<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				<form:form action="WardInformation.html" name="frmrightToInformation" id="rightToInformation">
				
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="form-elements">
						<span class="otherlink">
						 <a href="javascript:void(0);"
							class="btn btn-primary" onclick="openForm('WardForm.html')">Add</a>
						</span>
						</div>
				</form:form>
				<div class="grid-class" id="WardInfoGrid">
					<apptags:jQgrid id="wardInformation"
						url="WardInformation.html?WARD_LIST" mtype="post"
						gridid="gridWardForm"
						colHeader="eip.ward.NameEN,eip.ward.NameMR"
						colModel="[
						    {name : 'prabhagnameen',index : 'prabhagnameen',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'prabhagnamereg',index : 'prabhagnamereg',editable : false,sortable : false,search : false,  align : 'center'}
					  		]"
						height="200" 
						caption="eip.ward.infoGrid" 
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
				