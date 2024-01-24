<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/property/mutationIntimationSearch.js"></script>


<!-- End JSP Necessary Tags -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<div class="content">
	<div class="widget">
<%-- 	<div class="widget-header">
				<h2><strong><spring:message code="property.dataEntry" text="Data Entry Form"/></strong></h2>				
				<div class="additional-btn">
					<apptags:helpDoc url="DataEntrySuite.html"></apptags:helpDoc>	
				</div>
	</div> --%>

	<div class="widget-content padding">
	
		<div class="mand-label clearfix">
			<span><spring:message code="property.ChangeInAss.EnterPropertyNo" text="Enter Property No"/><i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i> <spring:message code="property.OldPropertyNo" text="Old Property No"/> <i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i> <spring:message code="ownersdetail.mobileno" text="Mobile No"/>
			<i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i> <spring:message code="property.OwnerName" text="Owner Name"/><i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i> <spring:message code="property.ward/Zone" text="ward/Zone"/> <i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i><spring:message code="property.location" text="Location"/>
			</span>
		</div>
 		
		<!-- Start Form -->
		<form:form action="MutationIntimation.html"
					class="form-horizontal form" name="MutationIntimation"
					id="MutationIntimation">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
		<div class="form-group">
				<spring:message code="property.PleaseEnterPropertyNo" text="Please enter property no" var="propNo"/>
				<apptags:input labelCode="property.ChangeInAss.EnterPropertyNo" path="mutationIntimationDto.propertyno" cssClass="preventSpace" placeholder="${propNo}"></apptags:input>
				

		</div>
				
			<div class="form-group" >
				<spring:message code="property.PleaseEnterOwnerName" text="Please enter owner Name" var="ownerName"/>
				<spring:message code="property.PleaseEnterMobileNo" text="Please enter mobile no" var="mobileNo"/>
			
				<apptags:input labelCode="property.OwnerName" path="mutationIntimationDto.excuClaimName" cssClass="preventSpace" placeholder="${ownerName}"></apptags:input>
				<apptags:input labelCode="ownersdetail.mobileno" path="mutationIntimationDto.excuClaimMobileNo" cssClass="preventSpace" placeholder="${mobileNo}"></apptags:input>
			
			</div>
			<div class="form-group" >
				<spring:message code="mut.Inti.mutNo" text="Please enter Registration No" var="ownerName"/>
				<spring:message code="mut.Inti.regNo" text="Please enter Mutation no" var="mobileNo"/>
			
				<apptags:input labelCode="mut.Inti.regNo" path="mutationIntimationDto.registrationNo" cssClass="preventSpace" placeholder="${ownerName}"></apptags:input>
				<apptags:input labelCode="mut.Inti.mutNo" path="mutationIntimationDto.mutationOrderNo" cssClass="preventSpace" placeholder="${mobileNo}"></apptags:input>
			
			</div>
						

		
			<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2" id="serchBtn"
									onclick="SearchDESProperties(this)">
						<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/>
						</button> 	
						
						<%-- <button type="button" class="btn btn-blue-2" 
									onclick="Reset()"><spring:message code="property.reset" text="Reset"/>
						</button> --%>
				<%-- 		<button type="button" class="btn btn-success btn-submit" 
									onclick="addDataEntryDetails(this)"><i class="fa fa-plus-circle"></i><spring:message code="property.DataEntryForm.Add" text="Add"/>
						</button> --%>
						<button type="button" class="btn btn-warning" 
									onclick="resetDESSeachGrid()"><spring:message code="property.reset" text="Reset"/>
						</button>
						
			</div>
			
			
			<apptags:jQgrid id="MutationIntimation"
					url="MutationIntimation.html?SEARCH_GRID_RESULTS" mtype="post"
					gridid="gridMutationIntimation"
					colHeader="propertydetails.PropertyNo.,property.OwnerName,mut.Inti.village,mut.Inti.khasraPlot,mut.Inti.regNoDate"
					colModel="[
								{name : 'propertyno',index : 'proertyNo',editable : false,sortable : true,search : true, align : 'center'},
								{name : 'currentOwner',index : 'oldPid',editable : false,sortable : true,search : true, align : 'center'},
								{name : 'village',index : 'ownerName',editable : false,sortable : false,search : true, align : 'center'},
								{name : 'khasraPloatNo',index : 'mobileno',editable : false,sortable : false,search : false, align : 'center'},	
								{name : 'regNoAndDate',index : 'mobileno',editable : false,sortable : false,search : false, align : 'center'}						
								]"
					height="200" caption="" isChildGrid="false" hasActive="false"
					hasViewDet="true" hasEdit="false" hasDelete="false"
					loadonce="false" sortCol="rowId" showrow="true" />
						
		
			
			
		</form:form>
	</div>
	</div>
</div>