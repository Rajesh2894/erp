<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <script type="text/javascript" src="js/property/viewPropertyDetails.js"></script> -->
<script type="text/javascript">
$(document).ready(function() {
	reloadGrid('gridViewPropertyDetail');
});

function backToForm(){	
	debugger;
	
	var url = window.location.href;
	window.location.href = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("html")+4) +'?backToForm';
}

function SearchButton(element) {
		$(element).prop('disabled',true);
		var data = $('#ViewPropertyDetails').serialize();
		var URL = 'PropertyNoDuesCertificate.html?searchData';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		//openPopupWithResponse(returnData);
		$(formDivName).html(returnData);
		$(element).prop('disabled',false);
		reloadGrid('gridViewPropertyDetail');
		
}
</script>

<!-- End JSP Necessary Tags -->

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<div class="content">
	<div class="widget">
	<div class="widget-header">
				<h2><strong><spring:message code="viewPropDetails" text="View Property Details"/></strong></h2>				
				<div class="additional-btn">
					
				</div>
	</div>

	<div class="widget-content padding">
	
		<div class="mand-label clearfix">
			<span><spring:message code="property.ChangeInAss.EnterPropertyNo" text="Enter Property No "/><i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i> <spring:message code="property.OldPropertyNo" text="Old Property No"/> <i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i> <spring:message code="ownersdetail.mobileno" text="Mobile No"/>
			<i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i> <spring:message code="property.OwnerName" text="Owner Name"/><i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i> <spring:message code="property.ward/Zone" text="ward/Zone"/> <i class="text-red-1"><spring:message code="property.ChangeInAss.OR" text="OR"/></i><spring:message code="property.location" text="Location"/>
			</span>
		</div>
 		
		<!-- Start Form -->
		<form:form action="PropertyNoDuesCertificate.html"
					class="form-horizontal form" name="ViewPropertyDetail"
					id="ViewPropertyDetails">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			
		<div class="form-group">
				<spring:message code="property.PleaseEnterPropertyNo" text="Please enter property no" var="propNo"/>
				<spring:message code="property.PleaseEnterOldPropertyNo" text="Please enter old property no" var="oldPropNo"/>
				<apptags:input labelCode="property.ChangeInAss.EnterPropertyNo" path="searchDto.proertyNo" cssClass="preventSpace" placeholder="${propNo}"></apptags:input>
				<apptags:input labelCode="property.ChangeInAss.oldpid" path="searchDto.oldPid" cssClass="preventSpace" placeholder="${oldPropNo}"></apptags:input>
				

		</div>
			
			<strong><p class="text-center text-small padding-bottom-10 text-red-1 "><spring:message code="property.OR"></spring:message></p></strong>
	
			<div class="form-group" >
				<spring:message code="property.PleaseEnterOwnerName" text="Please enter owner Name" var="ownerName"/>
				<spring:message code="property.PleaseEnterMobileNo" text="Please enter mobile no" var="mobileNo"/>
			
				<apptags:input labelCode="property.OwnerName" path="searchDto.ownerName" cssClass="preventSpace" placeholder="${ownerName}"></apptags:input>
				<apptags:input labelCode="ownersdetail.mobileno" path="searchDto.mobileno" cssClass="preventSpace" placeholder="${mobileNo}"></apptags:input>
			
			</div>
			
			<strong><p class="text-center text-small padding-bottom-10 text-red-1 "><spring:message code="property.OR"></spring:message></p></strong>
			
			<div class="form-group wardZone">

									<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
										showOnlyLabel="false"
										pathPrefix="searchDto.assWard" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control " showAll="false"
										showData="true" />
								</div>
			
			<div class="form-group Loc">
									<apptags:select cssClass="chosen-select-no-results"
										labelCode="property.location" items="${command.location}"
										path="searchDto.locId" isMandatory="true"
										isLookUpItem="true" selectOptionLabelCode="select Location">
									</apptags:select>
									
									<apptags:input labelCode="property.flatNo" path="searchDto.flatNo" cssClass="preventSpace" placeholder="${flatNo}"></apptags:input>

								</div>
			
      
			<div class="form-group">
			<label for="road-type" class="col-sm-2 control-label"><spring:message code="unitdetails.RoadType"/> </label>
				          <div>
				          <c:set var="baseLookupCode" value="RFT" />
						  <apptags:lookupField  items="${command.getLevelData(baseLookupCode)}"
				 							path="searchDto.propLvlRoadType" cssClass="form-control" 
				 							hasChildLookup="false" hasId="true" showAll="false" 
											selectOptionLabelCode="prop.selectRoadFactor" /> 
						 </div>
				<label for="Construction Type" class="col-sm-2 control-label"><spring:message code="unitdetails.constructiontype"/> </label>
				<c:set var="baseLookupCode" value="CSC" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="searchDto.assdConstruType"
									 cssClass="form-control changeParameterClass mandColorClass"
									hasChildLookup="false" hasId="true" showAll="false" isMandatory="true"
									selectOptionLabelCode="Select Construction Type" />
									</div>

				<spring:eval
					expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('UTY','ENV',${UserSession.organisation}).getOtherField()"
					var="otherField" />
				<c:if test="${otherField eq 'Y' }">
					<div class="form-group usageType">
						<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
							showOnlyLabel="false" pathPrefix="searchDto.assdUsagetype"
							cssClass="form-control changeParameterClass mandColorClass"
							hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true" showAll="false"
							showData="true" columnWidth="10%" />
					</div>
				</c:if>


				<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2" id="serchBtn"
									onclick="SearchButton(this)">
						<i class="fa fa-search"></i><spring:message code="property.changeInAss.Search"/>
						</button> 	
						
						<button type="button" class="btn btn-blue-2" 
									onclick="backToForm()"><spring:message code="property.back" text="Back"/>
						</button>
			</div>
			
			
			<apptags:jQgrid id="ViewPropertyDetails"
					url="PropertyNoDuesCertificate.html?SEARCH_GRID_RESULTS" mtype="post"
					gridid="gridViewPropertyDetail"
					colHeader="propertydetails.PropertyNo.,propertydetails.oldpropertyno,property.OwnerName,Mobile Number,Flat No, Address"
					colModel="[
								{name : 'proertyNo',index : 'proertyNo',editable : false,sortable : true,search : true, align : 'center'},
								{name : 'oldPid',index : 'oldPid',editable : false,sortable : true,search : true, align : 'center'},
								{name : 'ownerName',index : 'ownerName',editable : false,sortable : false,search : true, align : 'center'},
								{name : 'mobileno',index : 'mobileno',editable : false,sortable : false,search : false, align : 'center'},
								{name : 'flatNo',index : 'flatNo',editable : false,sortable : false,search : false, align : 'center'},
								{name : 'address',index : 'address',editable : false,sortable : false,search : false, align : 'center'}									
								]"
					height="200" caption="" isChildGrid="false" hasActive="false"
					hasViewDet="false" hasEdit="false" hasDelete="false"
					loadonce="false" sortCol="rowId" showrow="true" />
						
					
			
			
		</form:form>
	</div>
	</div>
</div>