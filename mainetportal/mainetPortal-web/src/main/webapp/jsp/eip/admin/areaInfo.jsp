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
			<li><a href="javascript:void(0);"><spring:message code="eip.zoneinfo"/></a></li>
			<li><spring:message code="eip.area.title" /></li>
		</ul>
	</div>

</div>
<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				<form:form action="AreaInformation.html" name="frmrightToInformation" id="rightToInformation">
				
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="form-elements">
						<span class="otherlink">
						 <a href="javascript:void(0);"
							class="btn btn-primary" onclick="openForm('AreaForm.html')">Add</a>
						</span>
						</div>
				</form:form>
				<div class="grid-class" id="AreaInfoGrid">
					<apptags:jQgrid id="areaInformation"
						url="AreaInformation.html?AREA_LIST" mtype="post"
						gridid="gridAreaForm"
						colHeader="eip.area.NameEN,eip.area.NameMR,eip.area.DescEN,eip.area.DescHN"
						colModel="[
						    {name : 'shortdescen',index : 'shortdescen',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'shortdescreg',index : 'shortdescreg',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'longdescen',index : 'longdescen',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'longdescreg',index : 'slongdescreg',editable : false,sortable : false,search : false,  align : 'center'}
					  		]"
						height="200" 
						caption="eip.area.infoGrid" 
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
				