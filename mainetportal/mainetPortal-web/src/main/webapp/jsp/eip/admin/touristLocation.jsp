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
			
			<li><spring:message code="eip.tour.loc.Title" /></li>
		</ul>
	</div>

</div>
<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				<form:form action="TouristLocation.html" name="frmrtouristLocation" id="frmrtouristLocation">
				
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="form-elements">
						<span class="otherlink">
						 <a href="javascript:void(0);"
							class="btn btn-primary" onclick="openForm('TouristLocationForm.html')">Add</a>
						</span>
						</div>
				</form:form>
				<div class="grid-class" id="TouristLocationInfoGrid">
					<apptags:jQgrid id="touristLocInfo"
						url="TouristLocation.html?LOCATION_LIST" mtype="post"
						gridid="gridTouristLocationForm"
						colHeader="eip.tour.loc.SHNameEN,eip.tour.loc.SHNameMR"
						colModel="[
						    {name : 'shortdescEn',index : 'shortdescEn',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'shortdescReg',index : 'shortdescReg',editable : false,sortable : false,search : false,  align : 'center'}
					  		]"
						height="200" 
						caption="eip.tour.loc.gridCaption" 
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
				