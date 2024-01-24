<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/commonNotice.js"></script>
<script src="js/mainet/CustomSearch.js"></script>
<script src="js/market/MaketCommonScriptFunction.js"></script>

<div class="form-div clear">


		<ul class="breadcrumbs">
	   	  <li><a href="CitizenHome.html"><spring:message code="menu.home"/></a></li>
	      <li><a href="javascript:void(0);"><spring:message code="onlineservices.market"/></a></li>
	      <li class="active"><a href="CommonShowCauseNoticeEntryForm.html"><spring:message code="market.showCauseEntry"/></a></li>
		</ul>
		
		<h1><spring:message code="" text="Show Cause Notice Entry Form"/></h1>
		<div class="clear" id="content">
		<div class="mand-label">
							<spring:message code="MandatoryMsg" text="MandatoryMsg" />
	</div> 
		
			<form:form action="CommonShowCauseNoticeEntryForm.html" name="frmMasterForm" id="frmMaster" cssClass="form">
			
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			
			<div class="regheader">
			<spring:message code="ptdept.selCriteria"/>
		</div>
			<div class="form-elements clear padding_10">
							<apptags:lookupFieldSet baseLookupCode="MFW" hasId="true" pathPrefix="entity.lmWardid" isMandatory="false" />
			</div>
			<div class="form-elements clear padding_10" >
			<div class="element">
			<label for=""><spring:message code="market.fromdate"/> : </label>
			<apptags:dateField fieldclass="fromDateClass" datePath="commonNoticeDTO.fromDate"  cssClass="subsize" />
			</div>
			
			<div class="element">
			<label for=""><spring:message code="market.todate"/> : </label>
			<apptags:dateField fieldclass="toDateClass" datePath="commonNoticeDTO.toDate"  cssClass="subsize" />
			</div>
			</div>
			
			<div class="form-elements margin_top_5 clear">
				<span class="otherlink">

			 <a href="javascript:void(0);" class="css_btn" onclick="SearchVieweRelatedData(this)"><spring:message code="market.search"/></a> 
			 <a href="javascript:void(0);" class="css_btn" onclick="emptyForm(this)"><spring:message code="market.reset"/></a>
			<a href="javascript:void(0);" class="css_btn" onclick="openForm('MarketShowCauseNotice.html')"><spring:message code="market.addDetails"/></a>					

				</span>
			</div>
			<div class="margin_top_5">
			<apptags:jQgrid id="MarketShowCauseEntry"
				url="CommonShowCauseNoticeEntryForm.html?SEARCH_RESULTS" mtype="post"
				gridid="gridMarketShowCauseNotice"
				colHeader="market.noticeNo,market.licenseNo,market.noticeType"
				colModel="[									
							{name : 'noticeNo',index : 'noticeNo',editable : false,sortable : true,search : true,align : 'center',width :70},
							{name : 'licenseNo',index : 'licenseNo',editable : false,sortable : true,search : true,align : 'center',width :80},
							{name : 'noticeTypePrefix',index : 'noticeTypePrefix',editable : false,sortable : true,search : true,align : 'center',width :150}
						]"
				sortCol="rowId" isChildGrid="false" hasActive="false"
				hasViewDet="true" hasDelete="false" height="450" showrow="true"
				caption="market.showCause" loadonce="true" />
			</div>
			</form:form>
	
		</div>
</div>