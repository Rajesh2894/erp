
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>



<div id="heading_wrapper">

	<div id="heading_bredcrum">
		<ul>
			<li><a href="./"><spring:message code="menu.home"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="menu.eip"/></a></li>
			<li>&gt;</li>
			<li><a href="javascript:void(0);"><spring:message code="eip.master"/></a></li>
			<li>&gt;</li>
			<li>ZonalOffice</li>
		</ul>
	</div>

</div>
<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				<form:form action="ZonalOfficeDetail.html" 
					name="frmZonalOffice" id="frmZonalOffice">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="form-elements">
						<span class="otherlink"> <a href="javascript:void(0);"
							class="btn btn-primary" onclick="openForm('ZonalOfficeEntryForm.html')">Add
							
							
							</a>
						</span>
					</div>
				</form:form>
				
				<div class="grid-class" id="zonalOfficeEntryFormGrid">
					<apptags:jQgrid id="zonalOfficeEntryForm"
						url="ZonalOfficeDetail.html?ZonalOffice_LIST" mtype="post"
						gridid="gridZonalOfficeEntryForm"
						colHeader="eip.zone.officeNo,eip.zone.titleEn,eip.zone.titleMr"
						colModel="[
						    {name : 'officeno',index : 'officeno',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'nameEn',index : 'nameEn',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'nameReg',index : 'nameReg',editable : false,sortable : false,search : false,  align : 'center'}
							]"
						height="200" 
						caption="eip.zone.zoneOffice" 
						isChildGrid="false"
						hasActive="true"
						hasEdit="true"
                        showInDialog="false"  
						hasViewDet="false" 
						hasDelete="true"
						loadonce="false" 
						sortCol="rowId" 
						showrow="true" />
				</div>

			</div>
		</div>

	</div>
</div>
				